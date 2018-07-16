
/******************************************************************************
    NAME:     ModisController
    PURPOSE:  The purpose of this class is to serve as manager to control and
              run the appropriate C-functions, which in turn are executed.
              Standard C-language modules are invoked to run projection data
              computations.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    Java is inherently platform indepentent so the compiled byte code can be
    executed on any of platforms (e.g. Windows, Unix, and Linux). Virtually,
    there is no limitation on running Java byte codes. However there is
    compiler requirement regarding JDK package (version 2.0).

    PROJECT:  ModisModel
    NOTES:

******************************************************************************/

package mrtswath;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import mrtswath.resample.*;

class ModisController 
{
    private ModisFrame frame;
    private ModisModel model;
    private ErrorHandler error;
    private String currentDirectory = "";
    private String inputFileName = "";
    private String outputFileName = "";
    private String outputFormat = "";
    private String projectionType = "";
    private String dataType = "";
    private String resamplingType = "";
    private String str_spatialsubsetType = "";
    private JOptionPane statusOptionPane =
    new JOptionPane("");
    private static ModisController instance = null;

/******************************************************************************
    NAME:     ModisController
    PURPOSE:  Constructor--private so that the class can enforce the
              Singleton nature of this class.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private ModisController() {}
   
/******************************************************************************
    NAME:     getInstance
    PURPOSE:  Return a reference to the Singleton instance of this class.
    RETURN VALUE:  ModisController

    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
    1. This is targeted to enforce a single instance of object.

*******************************************************************************/
    public static ModisController getInstance()
    {
        if ( instance == null )
        {
            instance = new ModisController();
        }
        return instance;
    }

/******************************************************************************
    NAME:     convertHdfToHdr
    PURPOSE:  To invoke a program to convert HDF information to HDR, since HDR
              files can be read simply. Reading HDF would either require a
              native method to get into the HDF C libraries or a whole bunch
              of Java code.

    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    final public String convertHdfToHdr()
    {
        String hdfFilename = model.getInputFilename();
        File hdfFile = new File (hdfFilename);
        if (!hdfFile.exists())
        {
            frame.complain("ReadInputFile", "Input HDF file does not exist");
            return "";
        }

        final Runtime rt = Runtime.getRuntime();
        String hdrFilename = null;
        Process p;
        try
        {
            String [] cmd = { "hdf2hdr", hdfFilename };
            p = rt.exec(cmd);
        }
        catch (java.io.IOException e)
        {
            frame.complain("execute hdf2hdr:",
                "hdf2hdr executable does not exist or is not in your path");
            return null;
        }

        int processReturn = 0;
        try
        {
            processReturn = p.waitFor();
        }
        catch (InterruptedException e)
        {
            processReturn=-9999;
        }

        if (processReturn < 0)
        {
            frame.showStatusWindow();
            frame.displayStatus("hdf2hdr error: return=" +
                Integer.toString(processReturn) + "\n\n");
            return null;
        }
        else
        {
            return "TmpHdr.hdr";
        }
    }

/******************************************************************************
    NAME:     exitFrame
    PURPOSE:  To request to exit the ModisFrame--this causes the frame to be
              disposed and the executable to be exited normally.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    final public void exitFrame()
    {
        frame.closeAndDispose();
        System.exit(0);
    }

/******************************************************************************
    NAME:     getCurrentDirectory
    PURPOSE:  To get current directory

    RETURN VALUE:  File type
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    final public File getCurrentDirectory()
    {
        return new File(".");
    }

/******************************************************************************
    NAME:     initialize
    PURPOSE:  To initialize the controller.  Implicitly creates and initializes
              the frame.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void initialize()
        throws Exception
    {
        frame = ModisFrame.getInstance();
        model = ModisModel.getInstance();

        frame.initComponents();
        frame.setInitialEnablements();

        outputFormat = frame.getOutputFormat();
        str_spatialsubsetType = frame.getSpatialSubsetType();
        resamplingType = frame.getResamplingType();
        projectionType = frame.getProjectionType();
        dataType = frame.getDataType();
        frame.setVisible(true);
    }

/******************************************************************************
    NAME:     loadFile
    PURPOSE:  Store the specified input file name. If the filename length
              is greater than 0, read the input file by calling the correct
              routine based on the filename extension.
              @param value the new input file name

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    final public void loadFile(String inputFileName)
    {
        if ( inputFileName.length() > 0 )
        {
            // check the existance of the input file in the current directory
            File inputFile = new File (inputFileName);
            if (!inputFile.exists())
            {
                frame.complain("OpenInputFile", "Input File does not exist!");
                return;
            }
            else
            {
                try
                {
                    final String lcFileName = inputFileName.toLowerCase();
                    if (lcFileName.endsWith(".hdf"))
                    {
                        String hdrFilename = convertHdfToHdr();
                        if (hdrFilename=="")
                            return;
                        String prevInputFile = model.getInputFilename();
                        model.readHdrFile(hdrFilename);
                        model.setInputFilename(prevInputFile);

                        File tempFile= new File(hdrFilename);
                        tempFile.delete();
                        frame.fillGUIInputFromHeaderData();
                    }
                    else if (lcFileName.endsWith(".prm"))
                    {
                        loadPrmFile(inputFileName, true);

                        // Set the output fields to visible
                        frame.projectionParametersButton.setEnabled(true);
                        frame.outputFormatCombo.setEnabled(true);
                        frame.resamplingCombo.setEnabled(true);
                        frame.projectionCombo.setEnabled(true);
                        frame.dataTypeCombo.setEnabled(true);
                    }
                    else
                    {
                        frame.complain("Input file name error",
                            "Invalid file extension -- must be '.hdf'");
                        return;
                    }
                }
                catch (ReadException e)
                {
                    StringBuffer title = new StringBuffer();
                    if ( e.isFatal() )
                    {
                        title.append("Fatal ");
                    }
                    title.append("Error in Module: ");
                    title.append( e.getModule() );
                    frame.complain(title.toString(), e.getMessage());
                    if ( e.isFatal() )
                    {
                        System.exit(e.getErrorId());
                    }
                }
            }
        }
    }

/******************************************************************************
    NAME:     loadPrmFile
    PURPOSE:  To load parameter file. This function does a delegation invocation
              to model. It also fills many components on the GUI frames after
              parameter file is read.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void loadPrmFile(String inputFilename,boolean readheader)
    {
        try
        {
            model.readPrmFile(inputFilename,readheader);
        }
        catch (ReadException e)
        {
            StringBuffer title = new StringBuffer();

            // Show error message to user
            if ( e.isFatal() )
            {
                title.append("Fatal ");
            }
            title.append("Error in Module: ");
            title.append( e.getModule() );

            frame.complain(title.toString(),e.getMessage());
            if ( e.isFatal() )
            {
                System.exit(e.getErrorId());
            }
            else
            {
                // Don't try to load the data in the GUI because it's
                // messed up if we get to here.
                return;
            }
        }

        frame.fillGUIFromParamFile();
    }
   
/******************************************************************************
    NAME:     runDump
    PURPOSE:  Call dumpMeta for generating the metadata for viewing.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String runDump(String inputfilename)
    {
        StringBuffer hdfFilename = new StringBuffer();
        String filename;
        int slashIndex = inputfilename.lastIndexOf(System.getProperty(
            "file.separator"));
        final Runtime rt = Runtime.getRuntime();
         
        Process p;
        try
        {
           String cmd[] = { "dumpmeta", inputfilename, "TmpMeta.txt" };
           p = rt.exec(cmd);
        }
        catch (java.io.IOException e)
        {
            frame.complain("execute dumpmeta:",
                "dumpmeta executable does not exist or is not in your path");
            return null;
        }

        int processReturn = 0;
        try
        {
            processReturn = p.waitFor();
        }
        catch (InterruptedException e)
        {
            processReturn=-9999;
        }

        if (processReturn < 0)
        {
            frame.showStatusWindow();
            frame.displayStatus("dumpmeta error: return=" +
                Integer.toString(processReturn) + "\n\n");
            return null;
        }
        else
        {
            return "TmpMeta.txt";
        }
    }

/******************************************************************************
    NAME:     runResample
    PURPOSE:  Run the swath2grid program with the parameters as represented
              in the frame.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void runResample()
    {
        { 
            frame.showStatusWindow();
            Thread t = new Thread(new Runnable()
            {
                public void run()
                {
                    final Runtime rt = Runtime.getRuntime();
                    Process p;
                    try
                    {
                       String cmd [] = { "swath2grid", "-pf=TmpParam.prm" }; 
                       p = rt.exec(cmd);
                    }
                    catch (java.io.IOException e)
                    {  
                        frame.complain("execute swath2grid:",
                            "swath2grid does not exist!");
                        return;
                    }
                    int processReturn = 0;
                    final Process proc = p;

                    Thread outThread = new Thread(new Runnable()
                    {
                        public void run()
                        {
                            InputStream is = proc.getInputStream();
                            BufferedReader br = new BufferedReader(
                                new InputStreamReader(is));
                            char[] buffer = new char[10];
                            for (;;)
                            {
                                int numread = 0;
                                try
                                {
                                    numread = br.read(buffer, 0, 10);
                                    if (numread < 0)
                                    {
                                        break;
                                    }
                                    frame.displayStatus(new String(buffer,
                                        0, numread));
                                }
                                catch (IOException e) {}
                            }
                        }
                    }); //end of Thread outThread

                    outThread.start();
                    // outThread.destroy();
                    Thread errThread = new Thread(new Runnable()
                    {
                        public void run()
                        {
                            InputStream is = proc.getErrorStream();
                            BufferedReader br = new BufferedReader(
                                new InputStreamReader(is));
                            char[] buffer = new char[10];
                            for (;;)
                            {
                                try
                                {
                                    int numread = br.read(buffer,0,10);
                                    if (numread < 0)
                                    {
                                        break;
                                    }

                                    frame.displayStatus(new String(buffer,
                                        0, numread));
                                }
                                catch (IOException e) {}
                            }
                        }
                    }); //end of Thread errThread

                    errThread.start();
                    try
                    {
                        processReturn = p.waitFor();
                    }
                    catch (InterruptedException e)
                    {
                        processReturn=-9999;
                    }

                    // if abnormal termination
                    if (processReturn != 0)
                    {
                        frame.displayStatus("swath2grid error: return = "+
                            Integer.toString(processReturn) + " -- ");

                        if (processReturn < 0)
                            frame.displayStatus(
                                error.formalMessage[-processReturn] +"\n\n");
                        else
                            frame.displayStatus(
                                error.formalMessage[processReturn] +"\n\n");
                    }
                }  //end of the first "public void run()" function
            }); //end of Thread t

            t.start();
        }
    }

/******************************************************************************
    NAME:     saveParameters
    PURPOSE:  Save a parameter file with values as represented in the frame.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void saveParameters()
    {
    }

/******************************************************************************
    NAME:    setOutputFileName
    PURPOSE: Store the specified output file name.
             @param value the new output file name

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    final public void setOutputFileName(String value)
    {
        outputFileName = value;
        ModisFrame frame = ModisFrame.getInstance();
        frame.setOutputFileName(value);
    }

/******************************************************************************
    NAME:     setOutputFormat
    PURPOSE:  Store the specified output format name.
              @param value the new output format name
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    final public void setOutputFormat(Object value)
    {
        try
        {
            outputFormat = (String)value;
        }
        catch (ClassCastException e) {}
    }

/******************************************************************************
    NAME:     writeParameterFile
    PURPOSE:  Delegate to model to write to a prm file

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void writeParameterFile()
    {
        String s = model.getParameterFilename();
        if ( s ==  null )
        {
            frame.complain("No Parameter Filename",
                "Please specify a parameter filename ending with '.prm'");
            return;
        }
        s = s.toLowerCase();
        if ( s.endsWith(".prm") )
        {
            model.writeParameterFile();
        }
        else
        {
            frame.complain("Invalid Parameter Filename",
                "Please specify a parameter filename ending with '.prm'");
        }
    }
}
