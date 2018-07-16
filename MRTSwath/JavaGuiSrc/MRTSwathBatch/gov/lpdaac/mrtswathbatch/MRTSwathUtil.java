/*
 * MRTSwathUtil.java
 *
 * Created on July 16, 2010, 12:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.lpdaac.mrtswathbatch;

import java.io.File;

/**
 *
 * @author mmerritt
 */
public class MRTSwathUtil {
   
   /** Creates a new instance of MRTSwathUtil */
   public MRTSwathUtil() {
   }
   
   static public String removeQuotes(String str) {
      while( str.startsWith("\"") ) {
         str = str.substring(1);
      }
      while( str.endsWith("\"") ) {
         str = str.substring(str.length() - 1);
      }
      return str;
   }
   
   static public String createFile( String path, String file,
           MRTSwathBatchInfo.ScriptType stype ) {
      String mfile = MRTSwathUtil.createNormalizedPath(path, stype) + file;
      if( stype == MRTSwathBatchInfo.ScriptType.BATCH ||
              stype == MRTSwathBatchInfo.ScriptType.CSCRIPT ) {
         mfile = mfile.replaceAll("/", "\\\\");
      }
      return addQuotesIfSpaces(mfile);
   }

   static public String createNormalizedPath(String path, 
           MRTSwathBatchInfo.ScriptType stype ) {
      String cyg = "";
      String drive = "";
      
      path = path + ( !(path.endsWith("\\") || path.endsWith("/")) ? 
                 File.separatorChar : "" );
      path = path.replaceAll("\\\\", "/");
      
      if( stype == MRTSwathBatchInfo.ScriptType.BATCH || 
              stype == MRTSwathBatchInfo.ScriptType.CSCRIPT ) {
         if( path.length() > 10 ) {
            cyg = path.substring(0,10);
            if( cyg.equalsIgnoreCase("/cygdrive/") ) {
              drive = path.substring(10,11);
              path = path.replaceFirst(cyg + drive, drive + ":" );
            }
         }
      } else if( stype == MRTSwathBatchInfo.ScriptType.SCRIPT ) {
         if( path.length() > 1 && path.substring(1,2) == ":" ) {
            path = path.substring(2);
         }
         if( path.length() > 10 ) {
            cyg = path.substring(0,10);
            if( cyg.equalsIgnoreCase("/cygdrive/") ) {
               path = path.substring(10);
            }
         }
      }
      return path;
   }
   
   static public String createOSPath(String path) {
      path = path.replaceAll("\\\\", "/");
      if( MRTSwathUtil.isWindows() ) {
         if( path.length() > 10 ) {
            String cyg = path.substring(0,10);
            if( cyg.equalsIgnoreCase("/cygdrive/") ) {
              String drive = path.substring(10,11);
              path = path.replaceFirst(cyg + drive, drive + ":" );
            }
         }
         path = path.replaceAll("/", "\\\\");
      } else {
         if( path.length() > 1 && path.substring(1,2) == ":" ) {
            path = path.substring(2);
         }
      }
      return path;
   }
   
   static public Boolean isWindows() {
      String os = System.getProperty("os.name");
      if( os.length() >= 3 ) {
         os = os.substring(0,3);
         if( os.equalsIgnoreCase("WIN") ) {
            return true;
         }
      }
      return false;
   }
   
   static public Boolean isMac() {
      String os = System.getProperty("os.name");
      if( os.length() >= 3 ) {
         os = os.substring(0,3);
         if( os.equalsIgnoreCase("MAC") ) {
            return true;
         }
      }
      return false;
   }
   
   static public String addQuotesIfSpaces(String file) {
      if( file.contains(" ") )
         return "\"" + file + "\"";
      return file;
   }
   
   static public String getEOL(MRTSwathBatchInfo.ScriptType stype) {
      return (stype == MRTSwathBatchInfo.ScriptType.BATCH ? "\r\n" : (isMac() ? "\r" : "\n"));
   }

   static public String geRemoveCommand(MRTSwathBatchInfo.ScriptType stype) {
      return (stype == MRTSwathBatchInfo.ScriptType.BATCH ? "del" : "rm");
   }
   
   static public String getBatchFileName(MRTSwathBatchInfo.ScriptType stype) {
      return "mrtswathbatch" + (stype == MRTSwathBatchInfo.ScriptType.BATCH ? ".bat" : "");
   }
   
}
