/*
 * MRTSwathManager.java
 *
 * Created on July 16, 2010, 12:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.lpdaac.mrtswathbatch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mmerritt
 */
public class MRTSwathManager {
   
   private MRTSwathText txt = null;
   private MRTSwathPrm prm = null;
   private ArrayList<MRTSwathInterval> intervals = null;
   private ArrayList<String> batchLines = null;
   private MRTSwathBatchInfo bi = null;

   
   /** Creates a new instance of MRTSwathManager */
   public MRTSwathManager(MRTSwathBatchInfo batchInfo) {
      bi = batchInfo;
      txt = new MRTSwathText(bi);
      prm = new MRTSwathPrm(bi);
      intervals = new ArrayList<MRTSwathInterval>();
      batchLines = new ArrayList<String>();
   }
   
   public int parseForHdf() {
      return txt.parseForHDF();
   }
   
   public int parsePrmFile() {
      return prm.parsePrmFile();
   }
   
   public int createPrmFiles() {
      ArrayList<MRTSwathShortName> shortnames = txt.getShortNames();
      
      // For each shortname, create a parameter file.
      // The batch file will consist of multiple runs
      // of swath2grid.
      
      // First get the list of granules and find related geolocation
      // file.
           
      for( int i = 0; i < shortnames.size(); i++ ) {
         MRTSwathShortName shortname = shortnames.get(i);
         ArrayList<MRTSwathDate> dates = shortname.getList();
         
         if( shortname.getNShortName().matches("M[O|Y]D03") ) {
            continue;
         }
         
         for( int j = 0; j < dates.size(); ++j ) {
            MRTSwathDate date = dates.get(j);
            boolean insert_ival = false;
            // We have an interval.  The shortname and date.
            // This porgram allows files to be across mutiple
            // paths, but, only oone output directory will be created
            // for the group.  So use the first directory found.
            
            // Most of this code was stolen from MRTBatch.  It needed
            // files to be grouped into intervals, the code here still
            // uses those existing existing structures, but MRTSwathBatch
            // needs to take the information from those structures and 
            // flatten it out.
            
            MRTSwathInterval ival = new MRTSwathInterval(bi);
            String outputDir = bi.getOutputDirectory();
            ival.setPrmDir(outputDir);
            ival.setShortName(shortname.getNShortName());
            ival.setPDate(date.getNDate());
            ArrayList<MRTSwathFiles> paths = date.getList();
            for( int k = 0; k < paths.size(); ++k ) {
               MRTSwathFiles path = paths.get(k);
               if( outputDir.length() == 0 ) {
                  outputDir = path.getPath();
                  if( outputDir.length() == 0 )
                     outputDir = new File(
                             System.getProperty("user.dir")).getAbsolutePath();
                  if( !(outputDir.endsWith("\\") || outputDir.endsWith("/")) )
                     outputDir += File.separatorChar;
                  outputDir += "prm";
                  ival.setPrmDir(outputDir);
               }
               // Create and add to the list of files that will create
               // the interval.
               ArrayList<String> files = path.getList();
               for( int l = 0; l < files.size(); ++l ) {
                  String file = files.get(l);
                  
                  // Find respective geolocation file:
                  ArrayList<MRTSwathShortName> sns = txt.getShortNames();
                  
                  for( int ii = 0; ii < sns.size(); ii++ ) {
                     MRTSwathShortName sn = sns.get(ii);
                     
                     if( sn.getNShortName().matches("M[O|Y]D03") && 
                           file.substring(0,3).equals(sn.getNShortName().substring(0,3)) ) {
                        ArrayList<MRTSwathDate> ds = sn.getList();

                        for( int jj = 0; jj < ds.size(); ++jj ) {
                           MRTSwathDate d = ds.get(jj);

                           if( date.getNDate().equals(d.getNDate()) ) {
                               ArrayList<MRTSwathFiles> ps = d.getList();
                               for( int kk = 0; kk < ps.size(); ++kk ) {
                                  MRTSwathFiles p = ps.get(kk);

                                  ArrayList<String> fs = p.getList();
                                  for( int ll = 0; ll < fs.size(); ++ll ) {
                                     ival.addFile(
                                        MRTSwathUtil.createFile(
                                             path.getPath(), files.get(l),
                                             bi.getScriptType()),
                                        MRTSwathUtil.createFile(
                                             p.getPath(), fs.get(ll), 
                                             bi.getScriptType()) );
                                     insert_ival = true;
                                     continue;
                                  }
                                  continue;
                               }
                           }
                        }
                     }
                  }
               }
            }
            if( insert_ival ) {
               intervals.add(ival);
            }
         }
      }
      
      // For each interval...
      batchLines.clear();
      if( bi.isDebug() ) {
         System.out.println("* Intervals created: " + intervals.size());
      }
      for( int i = 0; i < intervals.size(); ++i ) {
         MRTSwathInterval iv = intervals.get(i);
         if( iv.createPrmDir() != 0 )
            return 1;
         if( iv.createSwath(prm.getPrmLines()) != 0 ) 
            return 1;

         batchLines.add("swath2grid -pf=" + iv.getSwathPrmName());
         batchLines.add("");
      }
      
      return 0;
   }
   
   public int createBatch() {
      String batch = bi.getBatchFileName();
      File m = new File(batch);
      Writer output = null;
      String eol = MRTSwathUtil.getEOL(bi.getScriptType());
      
      if( bi.isDebug() ) {
         System.out.println("* Creating batch file: " + batch);
      }
      
      try {
         output = new BufferedWriter(new FileWriter(m));
         //FileWriter always assumes default encoding is OK!
         for( int i = 0; i <batchLines.size(); ++i ) {
            output.write( batchLines.get(i) + eol);
         }
      } catch (IOException ex) {
         System.err.println( "ERROR: Error writing to "
                 + batch + ": " +
                 (ex.getMessage() == null ?
                    (ex.getCause() == null ? "unknown" :
                       ex.getCause()) : ex.getMessage()) );
         return 1;
      } finally {
         if( output != null ) {
            try {
               output.close();
            } catch (IOException ex) {
               System.err.println( "WARNING: Error closing file: " + batch );
            }
         }
      }
      
      return 0;
   }
}
