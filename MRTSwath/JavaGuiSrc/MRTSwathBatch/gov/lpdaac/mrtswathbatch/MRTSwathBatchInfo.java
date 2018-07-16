/*
 * MRTSwathBatchInfo.java
 *
 * Created on July 16, 2010, 12:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.lpdaac.mrtswathbatch;

/**
 *
 * @author mmerritt
 */
public class MRTSwathBatchInfo {
   
   public static enum ScriptType { NOT_DEFINED, BATCH, SCRIPT, CSCRIPT;
   public String value() {return name();}
   public static ScriptType fromValue(String s) { return valueOf(s);} };
   
   private String textFileName = "";
   private String hdfDirName = "";
   private String geoDirName = "";
   private String prmFileName = "";
   private ScriptType scriptType = ScriptType.NOT_DEFINED;
   private String outputDirectory = "";
   private int debug = 0;
   private Boolean skipBad = false;
   private String batchFileName = "";
   
   private final static String inputName = "INPUT_FILENAME";
   private final static String outputName = "OUTPUT_FILENAME";
   private final static String geolocName = "GEOLOCATION_FILENAME";
   private final static String outFileType = "OUTPUT_FILE_FORMAT";
   private static final String version = "1.0";
   
   /** Creates a new instance of MrtBatchInfo */
   public MRTSwathBatchInfo() {
   }

   public String getTextFileName() {
      return textFileName;
   }

   public void setTextFileName(String textFileName) {
      this.textFileName = textFileName;
   }

   public String getPrmFileName() {
      return prmFileName;
   }

   public void setPrmFileName(String prmFileName) {
      this.prmFileName = prmFileName;
   }

   public ScriptType getScriptType() {
      return scriptType;
   }

   public void setScriptType(ScriptType scriptType) {
      this.scriptType = scriptType;
   }

   public String getOutputDirectory() {
      return outputDirectory;
   }

   public void setOutputDirectory(String outputDirectory) {
      this.outputDirectory = outputDirectory;
   }

   public int getDebug() {
      return debug;
   }

   public void setDebug(int debug) {
      this.debug = debug;
   }
   
   public Boolean isDebug() {
      if( debug != 0 )
         return true;
      return false;
   }
   
   public void resetScriptType() {
      if( scriptType == ScriptType.NOT_DEFINED ) {
         if( MRTSwathUtil.isWindows() ) {
            scriptType = ScriptType.BATCH;
         } else {
            scriptType = ScriptType.SCRIPT;
         }
      }
   }
   
   public void showInfo(Boolean dependOnDebug) {
      if( dependOnDebug && !isDebug() )
         return;
      System.out.println("Input text file      : " + 
         (this.getTextFileName().length() == 0 ?
            "Not used" : this.getTextFileName()) );
      System.out.println("Geolocation directory: " + 
         (this.getGeoDirName().length() == 0 ?
            "Not Used" : this.getGeoDirName()) );
      System.out.println("Input directory      : " + 
         (this.getHdfDirName().length() == 0 ?
            "Not used" : this.getHdfDirName()) );
      System.out.println("Input prm file       : " + this.getPrmFileName() );
      System.out.println("Script type          : " + 
         this.getScriptType().value() );
      System.out.println("Output directory     : " + 
         (this.getOutputDirectory().length() == 0 ? 
            "Specified by path(s) in text file." : this.getOutputDirectory()) );
      System.out.println("Batch file name      : " + this.getBatchFileName());
      System.out.println("Skip bad files       : " +
         (this.isSkipBad() ? "True" : "False") );
      System.out.println("Debug is             : " +
         (this.isDebug() ? "On" : "Off") );
   }

   public Boolean isSkipBad() {
      return skipBad;
   }

   public void setSkipBad(Boolean skipBad) {
      this.skipBad = skipBad;
   }

   public String getBatchFileName() {
      return (batchFileName.length() == 0 ? "mrtswathbatch" : batchFileName) + 
              (getScriptType() == MRTSwathBatchInfo.ScriptType.BATCH ? ".bat" : "");
   }

   public void setBatchFileName(String batchFileName) {
      this.batchFileName = batchFileName;
   }

   public static String getInputName() {
      return inputName;
   }

   public static String getOutputName() {
      return outputName;
   }

   public static String getGeolocationName() {
      return geolocName;
   }
   
   public static String getOutputFileType() {
      return outFileType;
   }

   public static String getVersion() {
      return version;
   }

   public String getHdfDirName() {
      return hdfDirName;
   }

   public void setHdfDirName(String hdfDirName) {
      this.hdfDirName = hdfDirName;
   }   

   public String getGeoDirName() {
      return geoDirName;
   }

   public void setGeoDirName(String geoDirName) {
      this.geoDirName = geoDirName;
   }
   
}
