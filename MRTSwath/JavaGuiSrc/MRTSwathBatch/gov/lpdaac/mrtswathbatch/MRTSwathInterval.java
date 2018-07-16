/*
 * MRTSwathInterval.java
 *
 * Created on July 16, 2010, 2:12 PM
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
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author mmerritt
 */
public class MRTSwathInterval {
   
   private String prmDir = "";
   private HashMap<String, String> swathList = null;
   private String shortName = "";
   private String pDate = "";
   private String outputExt = "";
   private MRTSwathBatchInfo bi = null;
   
   private static String prmFile = "_swath2grid.prm";
   private static String outNamePart = "_grid";
   
   /** Creates a new instance of MRTSwathInterval */
   public MRTSwathInterval(MRTSwathBatchInfo batchInfo) {
      swathList = new HashMap<String, String>();
      bi = batchInfo;
   }
   
   public int createPrmDir() {
      if( bi.isDebug() ) {
         System.out.println("  Creating directory if it does not exist: " + 
                 getPrmDir());
      }
      File p = new File(getPrmDir());
      if( p.exists() && !p.isDirectory() ) {
         System.err.println("ERROR: Need \"" + getPrmDir() + 
                 "\" to be a directory, but it isn't.");
         return 1;
      }
      if( !p.exists() ) {
         if( !p.mkdirs() ) {
            System.err.println("ERROR: Could not create directory: " + 
                    getPrmDir() );
            return 1;
         }
      }
      return 0;
   }
      
   public int createSwath(ArrayList<String> prmLines) {
      String prmfile = getSwathPrmName();
      File m = new File(prmfile);
      Writer output = null;
      String inputFileName = getInputFileName();
      String geoFileName = getGeoFileName(inputFileName);
      String outputFileName = getOutputFileName();
      String eol = MRTSwathUtil.getEOL(bi.getScriptType());

      if( bi.isDebug() ) {
         System.out.println("* Creating swath2grid parameter file: " + prmfile );
         System.out.println("  " + bi.getInputName() + " = " + inputFileName );
         System.out.println("  " + bi.getGeolocationName() + " = " + geoFileName );
         System.out.println("  " + bi.getOutputName() + " = " + outputFileName );
      }
      
      try {
         output = new BufferedWriter(new FileWriter(m));
         //FileWriter always assumes default encoding is OK!
         for( int i = 0; i < prmLines.size(); ++i ) {
            String line = prmLines.get(i);
            String ucline = line.toUpperCase();
            if( ucline.indexOf( bi.getInputName() ) != -1 ) {
               line = line + inputFileName;
            } else if( ucline.indexOf( bi.getGeolocationName() ) != -1 ) {
               line = line + geoFileName;
            } else if( ucline.indexOf( bi.getOutputName() ) != -1 ) {
               line = line + outputFileName;
            }
            output.write( line + eol);
         }
      } catch (IOException ex) {
         System.err.println( "ERROR: Error writing to "
                 + prmfile + ": " +
                 (ex.getMessage() == null ?
                    (ex.getCause() == null ? "unknown" :
                       ex.getCause()) : ex.getMessage()) );
         return 1;
      } finally {
         if( output != null ) {
            try {
               output.close();
            } catch (IOException ex) {
               System.err.println( "WARNING: Error closing file: " + prmfile );
            }
         }
      }
      
      return 0;
   }
   
   public void addFile(String file, String geo) {
      swathList.put(file, geo);
   }
   
   public String getPrmDir() {
      return prmDir;
   }
   
   public void setPrmDir(String prmDir) {
      if( !(prmDir.endsWith("\\") || prmDir.endsWith("/")) )
         this.prmDir = prmDir + File.separatorChar;
      else
         this.prmDir = prmDir;
   }
     
   public String getSwathPrmName() {
      return MRTSwathUtil.addQuotesIfSpaces(
              getPrmDir() + getShortName() + "." + getPDate() + prmFile);
   }
   
   public String getInputFileName() {
      Set<String> s = swathList.keySet();
      Iterator<String> i = s.iterator();
      while( i.hasNext() ) {
         String key = i.next();
         return MRTSwathUtil.addQuotesIfSpaces(key);
      }
      return "";
   }
   
   public String getGeoFileName( String key ) {
      return MRTSwathUtil.addQuotesIfSpaces(swathList.get(key));
   }
     
   public String getOutputFileName() {
      String inputName = getInputFileName();
      String [] parts = inputName.split("[.]");
      String outName = "";
      int i;
      
      if( parts.length > 0 ) {
         for( i = 0; i < parts.length - 1; ++i ) {
            outName += parts[i];
         }
      }
      int idx = outName.lastIndexOf('/');
      if( idx == -1 )
         idx = outName.lastIndexOf('\\');
      String outNoDir = outName;
      if( idx != -1 ) {
         outNoDir = outName.substring(idx + 1);
      }
      return MRTSwathUtil.addQuotesIfSpaces(
              getPrmDir() + outNoDir + outNamePart );
   }
   
   public String getShortName() {
      return shortName;
   }

   public void setShortName(String shortName) {
      this.shortName = shortName;
   }

   public String getPDate() {
      return pDate;
   }

   public void setPDate(String pDate) {
      this.pDate = pDate;
   }

   public String getOutputExt() {
      return outputExt;
   }

   public void setOutputExt(String outputExt) {
      this.outputExt = outputExt;
   }
   
}
