/*
 * MRTSwathPrm.java
 *
 * Created on July 16, 2010, 2:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.lpdaac.mrtswathbatch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author mmerritt
 */
public class MRTSwathPrm {
   
   private ArrayList<String> lines = null;
   private String outputType = "";

   private Boolean inputFound = false;
   private Boolean geoFound = false;
   private Boolean outputFound = false;
   private Boolean outputTypeFound = false;
   private MRTSwathBatchInfo bi = null;
      
   /** Creates a new instance of MRTSwathPrm */
   public MRTSwathPrm(MRTSwathBatchInfo batchInfo) {
      lines = new ArrayList<String>();
      bi = batchInfo;
   }
 
   private int addLine(String line) {
      String addLine = line;
      if( !line.startsWith("#") ) {
         String ucline = line.toUpperCase();
         if( ucline.indexOf( bi.getInputName() ) != -1 ) {
            addLine = bi.getInputName() + " = ";
            inputFound = true;
         } else if( ucline.indexOf( bi.getOutputName() ) != -1 ) {
            addLine = bi.getOutputName() + " = ";
            outputFound = true;
         } else if( ucline.indexOf( bi.getOutputFileType() ) != -1 ) {
            boolean err = false;
            int idx = ucline.indexOf("=");
            if( idx != -1 ) {
               String type = ucline.substring(idx + 1).trim();
               if( type.equals("GEOTIFF_FMT") || 
                    type.equals("HDF_FMT") || 
                    type.equals("RB_FMT") || 
                    type.equals("BOTH") ) 
                  outputType = type;
               else {
                  err = true;
               }
            } else {
               err = true;
            }
            if( err ) {
               System.out.println( "ERROR: Invalid " + bi.getOutputFileType() + 
                       "'s file type found." );
               System.out.println( "       Should be \"GEOTIF_FMT\", " +
                       "\"HDF_FMT\", \"RB_FMT\" or \"BOTH\"." );
               return 1;
            }
            addLine = bi.getOutputFileType() + " = " + outputType;
            outputTypeFound = true;
         } else if( ucline.indexOf(bi.getGeolocationName()) != -1 ) {
            addLine = bi.getGeolocationName() + " = ";
            geoFound = true;
         }
      } 
 
      lines.add(addLine);
      return 0;
   }
   
      public int parsePrmFile() {
      String filename = bi.getPrmFileName();
      
      if( bi.isDebug() ) {
         System.out.println("* Processing " + filename + "...");
      }
      
      inputFound = false;
      outputFound = false;
      outputTypeFound = false;
      geoFound = false;   
      outputType = "";
      
      BufferedReader input = null;
      try {
         //use buffering, reading one line at a time
         //FileReader always assumes default encoding is OK!
         input = new BufferedReader( new FileReader(filename) );
         String line = null; //not declared within while loop
         
         /*
          * readLine is a bit quirky :
          * it returns the content of a line MINUS the newline.
          * it returns null only for the END of the stream.
          * it returns an empty String if two newlines appear in a row.
          */
         while (( line = input.readLine()) != null){
            if( line != null ){
               if( addLine(line) != 0 )
                  return 1;
            }
         }
      } catch (FileNotFoundException ex) {
         System.err.println( "ERROR: File not found: " + filename );
         return 1;
      } catch (IOException ex){
         System.err.println( "ERROR: Error reading from "
                 + filename + ": " +
                 (ex.getMessage() == null ?
                    (ex.getCause() == null ? "unknown" :
                       ex.getCause()) : ex.getMessage()) );
         return 1;
      } finally {
         try {
            if (input!= null) {
               //flush and close both "input" and its underlying FileReader
               input.close();
            }
         } catch (IOException ex) {
            System.err.println( "WARNING: Error closing file: " + filename );
         }
      }
      
      if( inputFound == false ) {
         System.err.println( "ERROR: Could not find " + bi.getInputName() +
                 " in parameter file." );
         return 1;
      } else if( outputFound == false ) {
         System.err.println( "ERROR: Could not find " + bi.getOutputName() +
                 " in parameter file." );
         return 1;
      } else if( geoFound == false ) {
         System.err.println( "ERROR: Could not find " + bi.getGeolocationName() +
                 " in parameter file." );        
      } else if( outputTypeFound == false ) {
         System.err.println( "ERROR: Could not find " + bi.getOutputFileType() +
                 " in parameter file." );
      }

      if( bi.isDebug() ) {
         System.out.println("Output type found: " + getOutputType());
         displayLines();
      }
     
      return 0;      
   }
   
   public ArrayList<String> getPrmLines() {
      return lines;
   }

   public String getOutputType() {
      return outputType;
   }

   public void displayLines() {
      for( int i = 0; i < lines.size(); ++i ) {
         System.out.println(lines.get(i));
      }
   }
}
