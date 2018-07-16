/*
 * MRTSwathBatch.java
 *
 * Created on July 16, 2010, 11:51 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.lpdaac.mrtswathbatch;

import java.io.File;
import java.util.Map;

/**
 *
 * @author mmerritt
 */
public class MRTSwathBatch {
   
   /**
    * Creates a new instance of MRTSwathBatch
    */
   public MRTSwathBatch() {
   }
   
   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      int err = 0;
      MRTSwathBatchInfo bi = new MRTSwathBatchInfo();
      
      if( processArgs(args, bi) != 0 )
         return;
      
      if( validateArgs(bi) != 0 )
         return;
      
      bi.resetScriptType();
      bi.showInfo(true);
      
      MRTSwathManager mgr = new MRTSwathManager(bi);
      
      if( mgr.parseForHdf() != 0 )
         return;
      
      if( mgr.parsePrmFile() != 0 )
         return;
      
      if( mgr.createPrmFiles() != 0 )
         return;
      
      if( mgr.createBatch() != 0 )
         return;
   }
   
      private static int processArgs(String[] args, MRTSwathBatchInfo bi) {
      int i;
      
      if( args.length == 0 ) {
         MRTSwathBatchHelp.DisplayUsage();
         return 1;
      }
      
      // Check for help.
      for( i = 0; i < args.length; ++i ) {
         if( args[i].equals("-?") ||
             args[i].equals("-h") ||
             args[i].equals("--help") ){
            MRTSwathBatchHelp.DisplayUsage();
            return 1;
         }
         if( args[i].equals("-v") || 
                 args[i].equals("--version") ) {
            System.out.println("MRTBatch v" + bi.getVersion());
            return 1;
         }
      }
      
      for( i = 0; i < args.length; ++i ) {
         // Ouput directory (optional)
         if( args[i].equals("-o") ||
             args[i].equals("--output") ) {
            if( i + 1 < args.length ) {
               bi.setOutputDirectory(args[++i]);
            } else {
               System.err.println( "ERROR: Expecting argument after the " + 
                       "-o,--output switch and found none.");
               return 1;
            }
         }
         // Script type (optional)
         if( args[i].equals("-t") ||
             args[i].equals("--type") ) {
            if( i + 1 < args.length ) {
               String str = args[++i];
               if( str.equalsIgnoreCase("BATCH") )
                  bi.setScriptType(MRTSwathBatchInfo.ScriptType.BATCH);
               else if( str.equalsIgnoreCase("SCRIPT") )
                  bi.setScriptType(MRTSwathBatchInfo.ScriptType.SCRIPT);
               else if( str.equalsIgnoreCase("CSCRIPT") )
                  bi.setScriptType(MRTSwathBatchInfo.ScriptType.CSCRIPT);
               else {
                  System.err.println("ERROR: Invalid type found after the " + 
                          "-t,--type switch.");
                  System.err.println("       Valid values are BATCH, SCRIPT, " +
                          "or CSCRIPT, but found \"" + str + "\"." );
                  return 1;
               }
            } else {
               System.err.println("ERROR: Expecting argument after the " +
                       "-t,--type switch and found none.");
               return 1;
            }
         }
         // Text file containing hdf files (required)
         if( args[i].equals("-f") ||
             args[i].equals("--file") ) {
            if( i + 1 < args.length ) {
               bi.setTextFileName(args[++i]);
            } else {
               System.err.println("ERROR: Expecting argument after the " +
                       "-f,--file switch and found none.");
               return 1;
            }
         }
         // HDF diretcory containing hdf files (required)
         if( args[i].equals("-d") ||
             args[i].equals("--dir") ) {
            if( i + 1 < args.length ) {
               bi.setHdfDirName(args[++i]);
            } else {
               System.err.println("ERROR: Expecting argument after the " +
                       "-d,--dir switch and found none.");
               return 1;
            }
         }
         // Geolocation diretcory containing hdf files (optional)
         if( args[i].equals("-g") ||
             args[i].equals("--geo") ) {
            if( i + 1 < args.length ) {
               bi.setGeoDirName(args[++i]);
            } else {
               System.err.println("ERROR: Expecting argument after the " +
                       "-g,--geo switch and found none.");
               return 1;
            }
         } 
         // Parameter file (required)
         if( args[i].equals("-p") ||
             args[i].equals("--prmfile") ) {
            if( i + 1 < args.length ) {
               bi.setPrmFileName(args[++i]);
            } else {
               System.err.println("ERROR: Expecting argument after the " +
                       "-p,--prmfile switch and found none.");
               return 1;
            }
         }
         // Batch file (optional)
         if( args[i].equals("-b") ||
             args[i].equals("--batch") ) {
            if( i + 1 < args.length ) {
               bi.setBatchFileName(args[++i]);
            } else {
               System.err.println("ERROR: Expecting argument after " +
                       "the -b,--batch switch and found none.");
               return 1;
            }
         }

         // Debug (optional)
         if( args[i].equals("-D") ||
             args[i].equals("--debug") ) {
            bi.setDebug(1);
         }
         // SkipBad (optional)
         if( args[i].equals("-s") ||
             args[i].equals("--skipbad") ) {
            bi.setSkipBad(true);
         }
      }
      
      if( bi.isDebug() ) {
        System.out.println("------------------- Properties ----------------------");
        for (Map.Entry<Object, Object> e : System.getProperties().entrySet()) {
           System.out.println(e);
        }
        System.out.println("---------------- End of Properties ------------------");
      }
      
      return 0;
   }

   private static int validateArgs(MRTSwathBatchInfo bi) {
      File f;
      String textName;
      String prmName;
      String outDir;
      String hdfDir;
      String geoDir;
      
      textName = bi.getTextFileName();
      hdfDir = bi.getHdfDirName();
      geoDir = bi.getGeoDirName();
      
      if( textName.length() == 0 && hdfDir.length() == 0 ) {
         System.err.println("ERROR: Either the -f,--file switch or the -d,--dir " +
                 "switch is required.");
         return 1;
      }
      if( textName.length() > 0 && hdfDir.length() > 0 ) {
         System.err.println("ERROR: Both the -f,--file and -d,--dir switch cannot " +
                 "be used together.");
         System.err.println("       Please use one or the other.");
         return 1;
      }
      if( textName.length() > 0 && geoDir.length() > 0 ) {
         System.err.println("ERROR: Both the -f,--file and -g,--geo switch cannot " +
                 "be used together.");
         System.err.println("       Please use one or the other, but the -d,--dir " +
                 "switch is also" );
         System.err.println("       required if using the -g,-geo switch.");
      }
      
      if( textName.length() > 0 ) {
         f = new File(textName);
         if( !f.exists() ) {
            System.err.println("ERROR: Could not find file \"" + textName + "\".");
            return 1;
         } else if( !f.isFile() ) {
            System.err.println("ERROR: \"" + textName + "\" is not a file.");
            return 1;
         }
      }

      if( hdfDir.length() > 0 ) {
         f = new File(hdfDir);
         if( !f.exists() ) {
            System.err.println("ERROR: Could not find directory \"" + hdfDir + "\".");
            return 1;
         } else if( !f.isDirectory() ) {
            System.err.println("ERROR: \"" + hdfDir + "\" is not a directory.");
            return 1;
         }
      }
      
      if( geoDir.length() > 0 ) {
         f = new File(geoDir);
         if( !f.exists() ) {
            System.err.println("ERROR: Could not find directory \"" + geoDir + "\".");
            return 1;
         } else if( !f.isDirectory() ) {
            System.err.println("ERROR: \"" + geoDir + "\" is not a directory.");
         }
      }
      
      if( hdfDir.length() > 0 && geoDir.length() == 0 ) {
         bi.setGeoDirName(hdfDir);
      }
      
      prmName = bi.getPrmFileName();
      if( prmName.length() == 0 ) {
         System.err.println("ERROR: -p,--prmfile switch is required.");
         return 1;
      }
      f = new File(prmName);
      if( !f.exists() ) {
         System.err.println("ERROR: Could not find prm file \"" + prmName + "\".");
         return 1;
      } else if( !f.isFile() ) {
         System.err.println("ERROR: The prm file \"" + prmName + "\" is not a file.");
         return 1;
      }
      
      outDir = bi.getOutputDirectory();
      if( outDir.length() != 0 ) {
         f = new File(outDir);
         if( f.exists() && !f.isDirectory() ) {
            System.err.println("ERROR: The ouput directory specified already exists but");
            System.err.println("       it is not a directory.");
            return 1;
         }
      }
      
      return 0;
   }
   
}
