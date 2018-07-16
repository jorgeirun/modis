/*
 * MRTSwathBatchHelp.java
 *
 * Created on July 16, 2010, 12:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.lpdaac.mrtswathbatch;

/**
 *
 * @author mmerritt
 */
public class MRTSwathBatchHelp {
   
   /** Creates a new instance of MRTSwathBatchHelp */
   public MRTSwathBatchHelp() {
   }
   
   public static void DisplayUsage() {
      
      String [] usage = {
         "",
         "MrtSwathBatch  [ -? | -h,--help ]",
         " or",
         "MrtSwathBatch [ -o,--outdir path ] [ -t,--type <batch|script|cscript> ]",
         "              [ -D,--debug ] [-s,--skipbad ] [ -b,--batch batchfile ]",
         "              [ -v,--version ] -f,--file textfile | ( -d,--dir directory",
         "              [ -g,--geo directory] ) -p,--prmfile file",
         "",
         " where:",
         "  -b,--batch          : The batch file base name.  The defualt is",
         "                        \"mrtswathbatch\".  An extension is added based",
         "                        on the -t,--type.",
         "  -d,--dir directory  : Specify a directory that contains the list",
         "                        of HDF files to process.  At the very least, the",
         "                        granules must reside in this directory.  The",
         "                        geolocation files may reside in this directory as",
         "                        well, but if they are located elsewhere, then the",
         "                        -g,-geo switch can be used to specify a directory",
         "                        where they may be located.  This switch cannot be",
         "                        used with the -f,--file switch.",
         "  -D,--debug          : Print debug information.",
         "  -f,--file textfile  : The input text file name that contains a",
         "                        list of HDF files to process.  It must contain",
         "                        both the granules and geolocation files.  It",
         "                        cannot be used with the -d,--dir or -g,--geo",
         "                        switch.",
         "  -g,--geo            : Specify a different directory for geolocation",
         "                        files.  This switch can be used in conjuntion the",
         "                        the -d,--dir switch, but cannot be used with the",
         "                        -f,--file switch.",
         "  -o,--outdir path    : Alternate output path where the parameter",
         "                        files will be created.  The default is to use a",
         "                        path created under the directory where the input",
         "                        hdf files are specified.  That sub-directory",
         "                        name will be called \"prm\".  The use of this",
         "                        switch indicates that all files will be placed",
         "                        under this directory.",
         "  -p,--prmfile file   : The input parameter file which will be used",
         "                        as the template for the generated parameter",
         "                        files.",
         "  -s,--skipbad        : Skip files that are not in the expected format",
         "                        when reading the text file.  The default is to ",
         "                        halt the program.  The expected file format is:",
         "                        <shorname>.A<YYYYDDD>.*.HDF",
         "  -t,--type <batch|   : Force to either a batch, script, or cscript",
         "             script|    file.  The default is to create the appropriate",
         "             cscript>   script based off of OS.  The cscript creates a",
         "                        script, but still uses drive letters.  For best",
         "                        results, use appropriate paths in all files related",
         "                        to the OS that this application is run on.",
         " -v,--version         : Print version number and date created then exit.",
         ""
      };
      
      for( int i=0; i < usage.length; ++i )
         System.out.println(usage[i]);
   }
}