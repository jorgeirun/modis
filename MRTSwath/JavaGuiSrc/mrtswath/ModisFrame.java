/******************************************************************************
    NAME:     ModisFrame.java
    PURPOSE:  The purpose of this class is to create a GUI interface for
              application and allow clients to communicate with application.
              It collects user inputs and dispatch (propogate) the appropriate
              processing along to other objects or classes such as controller,
              model, descriptor.  
              The design of this class is to construct a view (GUI) based on
              the concept of document/view architecture in an attempt to
              isolate the view interface from data model perspective.

    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    Java is inherently platform indepentent so the compiled byte code can be
    executed on any of platforms (e.g. Windows, Unix, and Linux). Virtually,
    there is no limitation on running Java byte codes. However there is a
    compiler requirement regarding JDK package (version 2.0+).

    PROJECT:  MRTSwath
    NOTES:
    1. The Graphical User Interface (GUI) design) in this application is based
       on Java swing family class. It extends/derives from JFrame by
       appropriate customization.
******************************************************************************/
package mrtswath;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.JToolBar;
import javax.swing.ImageIcon;
import javax.swing.AbstractButton;


public class ModisFrame extends JFrame
{
    private static ModisFrame instance = null;
    private boolean doSetCornerPoints = false;
    private boolean mShown = false;

    /**
     * A reference to the controller for this frame.  It is a
     * Singleton, so there is only one ModisController per
     * executable instance.
     */
    private ModisController controller = ModisController.getInstance();
    private ModisModel model = ModisModel.getInstance();
    private StatusDialog statusDialog;

    // IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
    // every time the form is saved. All manual changes will be overwritten.
    //
    // BEGIN GENERATED CODE
    /* member declarations */
    javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();
    javax.swing.JMenu jMenuFile = new javax.swing.JMenu();
    javax.swing.JMenuItem jMenuFileOpenInputFile = new javax.swing.JMenuItem();
    javax.swing.JMenuItem jMenuFileOpenGeolocFile = new javax.swing.JMenuItem();
    javax.swing.JMenuItem jMenuFileSpecifyOutputFile =
        new javax.swing.JMenuItem();
    javax.swing.JSeparator jMenuFileSeparator1 = new javax.swing.JSeparator();
    javax.swing.JMenuItem jMenuFileLoadParameters = new javax.swing.JMenuItem();
    javax.swing.JMenuItem jMenuFileSaveParameters = new javax.swing.JMenuItem();
    javax.swing.JSeparator jMenuFileSeparator2 = new javax.swing.JSeparator();
    javax.swing.JMenuItem jMenuFileExit = new javax.swing.JMenuItem();
    javax.swing.JMenu jMenuAction = new javax.swing.JMenu();
    javax.swing.JMenuItem jMenuActionRun = new javax.swing.JMenuItem();
    javax.swing.JMenu jMenuHelp = new javax.swing.JMenu();
    javax.swing.JMenuItem jMenuHelpAbout = new javax.swing.JMenuItem();
    javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
    javax.swing.JPanel inputPane = new javax.swing.JPanel();
    javax.swing.JTextField inputFileNameText = new javax.swing.JTextField();
    javax.swing.JButton openInputFileButton = new javax.swing.JButton();
    javax.swing.JTextField geolocFileNameText = new javax.swing.JTextField();
    javax.swing.JButton openGeolocFileButton = new javax.swing.JButton();
    javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
    javax.swing.JScrollPane detailScrollPane = new javax.swing.JScrollPane();
    javax.swing.JTextArea detailTextArea = new javax.swing.JTextArea();
    javax.swing.JList availableBandsList = new javax.swing.JList();
    javax.swing.JList selectedBandsList = new javax.swing.JList();
    javax.swing.JScrollPane availableBandsScrollPane =
        new javax.swing.JScrollPane();
    javax.swing.JScrollPane selectedBandsScrollPane =
        new javax.swing.JScrollPane();
    javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
    javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
    javax.swing.JButton addBandButton = new javax.swing.JButton();
    javax.swing.JButton removeBandButton = new javax.swing.JButton();
    javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
    javax.swing.JComboBox spatialSubsetCombo = new javax.swing.JComboBox();
    javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
    javax.swing.JLabel jLabel7 = new javax.swing.JLabel();
    javax.swing.JLabel availableBandCount = new javax.swing.JLabel();
    javax.swing.JLabel selectedBandCount = new javax.swing.JLabel();
    javax.swing.JLabel yCoordLabel = new javax.swing.JLabel();
    javax.swing.JLabel jLabel9 = new javax.swing.JLabel();
    javax.swing.JLabel jLabel10 = new javax.swing.JLabel();
    javax.swing.JLabel resolutionLabelLabel = new javax.swing.JLabel();
    javax.swing.JLabel resolutionLabel = new javax.swing.JLabel();
    javax.swing.JLabel xCoordLabel = new javax.swing.JLabel();
 
    // Output data type
    javax.swing.JLabel dataTypeLabel = new javax.swing.JLabel();
    javax.swing.JComboBox dataTypeCombo = new javax.swing.JComboBox();

    // Output pixel size grid
    javax.swing.JTextField pixelSizeText = new javax.swing.JTextField();
    javax.swing.JLabel pixelSizeLabel = new javax.swing.JLabel();
    javax.swing.JTextField pixelSizeUnitText = new javax.swing.JTextField();

    javax.swing.JTextField startYCoordLLText = new javax.swing.JTextField();
    javax.swing.JTextField startXCoordLLText = new javax.swing.JTextField();
    javax.swing.JTextField endYCoordLLText = new javax.swing.JTextField();
    javax.swing.JTextField endXCoordLLText = new javax.swing.JTextField();
 
    javax.swing.JTextField startYCoordLSText = new javax.swing.JTextField();
    javax.swing.JTextField startXCoordLSText = new javax.swing.JTextField();
    javax.swing.JTextField endYCoordLSText = new javax.swing.JTextField();
    javax.swing.JTextField endXCoordLSText = new javax.swing.JTextField();
 
    javax.swing.JTextField startYCoordXYText = new javax.swing.JTextField();
    javax.swing.JTextField startXCoordXYText = new javax.swing.JTextField();
    javax.swing.JTextField endYCoordXYText = new javax.swing.JTextField();
    javax.swing.JTextField endXCoordXYText = new javax.swing.JTextField();

    javax.swing.JButton viewMetadataButton = new javax.swing.JButton();
    javax.swing.JLabel jLabel18 = new javax.swing.JLabel();
    javax.swing.JLabel jLabel17 = new javax.swing.JLabel();
    javax.swing.JLabel jLabel12 = new javax.swing.JLabel();
    javax.swing.JPanel destinationPane = new javax.swing.JPanel();
    javax.swing.JLabel jLabel11 = new javax.swing.JLabel();
    javax.swing.JComboBox outputFormatCombo = new javax.swing.JComboBox();
    javax.swing.JLabel jLabel13 = new javax.swing.JLabel();
    javax.swing.JComboBox resamplingCombo = new javax.swing.JComboBox();
    javax.swing.JLabel jLabel14 = new javax.swing.JLabel();
    javax.swing.JComboBox projectionCombo = new javax.swing.JComboBox();
    javax.swing.JButton projectionParametersButton = new javax.swing.JButton();
    javax.swing.JButton saveOutputFileButton = new javax.swing.JButton();
    javax.swing.JLabel jLabel15 = new javax.swing.JLabel();
    javax.swing.JTextField outputFileNameText = new javax.swing.JTextField();
    javax.swing.JLabel jLabel8 = new javax.swing.JLabel();
    javax.swing.JLabel jLabel16 = new javax.swing.JLabel();
    javax.swing.JPanel commandPane = new javax.swing.JPanel();
    javax.swing.JButton runButton = new javax.swing.JButton();
    javax.swing.JButton exitButton = new javax.swing.JButton();
    javax.swing.JButton loadParametersButton = new javax.swing.JButton();
    javax.swing.JButton saveParametersButton = new javax.swing.JButton();
    javax.swing.JLabel parameterFileNameLabel = new javax.swing.JLabel();
    javax.swing.JTextField parameterFileNameText =
        new javax.swing.JTextField();
    // END GENERATED CODE

    private int prevOutputFileType = -1;
    private int prevResamplingType = -1;
    private int prevDataType = -1;
    private int prevSpatialSubsetType = -1;
    private int prevOutputProjType = -1;
    private int prevOutputPixelSizeUnits = -1;
    private String prevOutputPixelSize = "";

    private boolean startLoadParameterFile = false;
    private boolean startLoadHeaderFile = false;
    private int startLoadFirstHeaderFile = 0;
    private boolean  bLoadHdrFile = true;
    private double [][]cornerPoints = new double [4][2];

/******************************************************************************
    NAME:     ModisFrame
    PURPOSE:  Constructor - make it private to enforce singleton
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
   private ModisFrame()
   {
   }

/******************************************************************************
    NAME:     browseInputFiles()
    PURPOSE:  Displays a file chooser dialog box for the user to select
              an input file. If a file is selected (i.e., the dialog box
              is not cancelled), the name is passed to the controller.

    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private String browseInputFiles()
    {
        JFileChooser chooser =
            new JFileChooser(controller.getCurrentDirectory());
        try
        {
            File inputFile = new File(model.getInputFilename());
            chooser.setSelectedFile(inputFile);
        }
        catch (Exception e) {}

        chooser.setFileFilter(new javax.swing.filechooser.FileFilter()
        {
            public boolean accept(java.io.File f)
            {
                final String fileName = f.getName().toLowerCase();
                return fileName.endsWith(".hdf") ||
                    f.isDirectory();
            }

            public String getDescription()
            {
                 return "*.hdf";
            }
         });

         int chooseResult = chooser.showOpenDialog(this);
         if ( chooseResult == JFileChooser.APPROVE_OPTION )
         {
             try
             {
                 String s = chooser.getSelectedFile().getCanonicalPath();
                 if ( s.toLowerCase().endsWith(".hdf") )
                 {
                     File inputFile = new File (s);
                     if (!inputFile.exists())
                     {
                         complain("OpenInputFile","Input File does not exist!");
                         return "";
                     }

                     setInputFileName(s);
                     return s;
                 }
                 else
                 {
                     complain("Input file name error",
                         "Invalid file extension -- must be '.hdf'");
                     return "";
                 }
             }
             catch(java.io.IOException e) {/* oops */}
         }

         return "";
    }

/******************************************************************************
    NAME:     browseGeolocFiles()
    PURPOSE:  Displays a file chooser dialog box for the user to select
              a geolocation file. If a file is selected (i.e., the dialog box
              is not cancelled), the name is passed to the controller.

    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private String browseGeolocFiles()
    {
        JFileChooser chooser =
            new JFileChooser(controller.getCurrentDirectory());
        try
        {
            File geolocFile = new File(model.getGeolocFilename());
            chooser.setSelectedFile(geolocFile);
        }
        catch (Exception e) {}

        chooser.setFileFilter(new javax.swing.filechooser.FileFilter()
        {
            public boolean accept(java.io.File f)
            {
                final String geolocName = f.getName().toLowerCase();
                return geolocName.endsWith(".hdf") ||
                    f.isDirectory();
            }

            public String getDescription()
            {
                 return "*.hdf";
            }
         });

         int chooseResult = chooser.showOpenDialog(this);
         if ( chooseResult == JFileChooser.APPROVE_OPTION )
         {
             try
             {
                 String s = chooser.getSelectedFile().getCanonicalPath();
                 if ( s.toLowerCase().endsWith(".hdf") )
                 {
                     File geolocFile = new File (s);
                     if (!geolocFile.exists())
                     {
                         complain("OpenGeolocFile",
                             "Geolocation file does not exist!");
                         return "";
                     }

                     setGeolocFileName(s);
                     return s;
                 }
                 else
                 {
                     complain("Geolocation file name error",
                         "Invalid file extension -- must be '.hdf'");
                     return "";
                 }
             }
             catch(java.io.IOException e) {/* oops */}
         }

         return "";
    }

/******************************************************************************
    NAME:     browseOutputFiles()
    PURPOSE:  Displays a file chooser dialog box for the user to select
              an output file. If a file is selected (i.e., the dialog box
              is no cancelled), the name is passed to the controller.

    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private String browseOutputFiles()
    {
        JFileChooser chooser =
            new JFileChooser(controller.getCurrentDirectory());
        chooser.setDialogType(JFileChooser.CUSTOM_DIALOG);
        chooser.setApproveButtonText("Select");
        chooser.setApproveButtonToolTipText("Set output file name");

        chooser.setFileFilter(new javax.swing.filechooser.FileFilter()
        {
            public boolean accept(java.io.File f)
            {
                final String fileName = f.getName().toLowerCase();
                return fileName.endsWith(".hdr") ||
                    fileName.endsWith(".hdf") ||
                    fileName.endsWith(".tif") ||
                    f.isDirectory();
            }

            public String getDescription()
            {
                return "*.hdr, *.hdf, *.tif";
            }
        });
        int chooseResult = chooser.showDialog(this,"Select");

        if ( chooseResult == JFileChooser.APPROVE_OPTION )
        {
            try
            {
                String s = chooser.getSelectedFile().getCanonicalPath();
                if ( s.toLowerCase().endsWith(".hdr") ||
                     s.toLowerCase().endsWith(".hdf") ||
                     s.toLowerCase().endsWith(".tif") )
                {
                    setOutputFileName(s);
                    return s;               
                }                   
                else
                {
                    complain("Input file name error",
                    "Please choose a valid file extension (.hdr, .hdf, .tif)!");
                    return "";
                }
            }
            catch(java.io.IOException e) {/* oops */}
        }

        return "";
    }


/******************************************************************************
    NAME:     browseParameterFiles
    PURPOSE:  Displays a file chooser dialog box for the user to select
              a parameter (.prm) file. If a file is selected (i.e., the dialog
              box is not cancelled), the name is passed to the controller.

    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private String browseParameterFiles(boolean forRead)
    {
        int chooseResult = 0;
        JFileChooser chooser =
            new JFileChooser(controller.getCurrentDirectory());

        chooser.setFileFilter(new javax.swing.filechooser.FileFilter()
        {
            public boolean accept(java.io.File f)
            {
                final String fileName = f.getName().toLowerCase();
                return fileName.endsWith(".prm") ||
                    f.isDirectory();
            }

            public String getDescription()
            {
               return "*.prm";
            }
        });

        if ( forRead )
        {
            chooser.setDialogTitle("Open Parameter File");
            chooseResult = chooser.showOpenDialog(this);
        }
        else
        {
            chooser.setDialogTitle("Save Parameter File");
            chooseResult = chooser.showSaveDialog(this);
        }
      
        if ( chooseResult == JFileChooser.APPROVE_OPTION )
        {
            try
            {
                String pathName = chooser.getSelectedFile().getCanonicalPath();
                final String lcPathName = pathName.toLowerCase();

                int dotIndex = lcPathName.lastIndexOf('.');

                if ( dotIndex > 0)
                {
                    if ( lcPathName.endsWith(".prm") )
                    {
                        pathName = pathName.substring(0, dotIndex);
                        pathName = pathName + ".prm";
                    }
                }
                else
                {
                    pathName = pathName + ".prm";
                }   
            
                if ( forRead ) // load the parameter file
                {
                    // check the existence of the parameter file. if it 
                    // doesn't exist, do not set the parameter file name in
                    // the parameter file text box
                    File prmFile = new File (pathName);
                    if ( !prmFile.exists())
                    {
                        complain("Error reading .prm file",
                            "The selected .prm file doesn't exist!");
                        return "";
                    }
                    setParameterFileName(pathName);
                    return pathName;        
                }
                else
                {
                    File prm = new File(pathName);
                    if(prm.exists())
                    {
                        JOptionPane outputOverwrite = new JOptionPane(
                            "The selected .prm file already exists. Overwrite?",
                            JOptionPane.QUESTION_MESSAGE,
                            JOptionPane.YES_NO_OPTION);
                        JDialog dialog = outputOverwrite.createDialog(this,
                            "Question");
                        dialog.setVisible(true);
                          
                        int optionValue = ((Integer)
                            outputOverwrite.getValue()).intValue();
                        if (optionValue == JOptionPane.YES_OPTION)
                        /* if user choses to overwite the output file */
                        {
                            parameterFileNameText.setText(pathName);
                            setParameterFileName(pathName);
                            return pathName;
                        }
                        else if (optionValue == JOptionPane.NO_OPTION)
                        /* promt user to input another output filename */
                        {
                            JOptionPane.showMessageDialog(this,
                                "Select another file, please...");
                        }
                    }
                    else
                    {
                        parameterFileNameText.setText(pathName);
                        setParameterFileName(pathName);
                        return pathName;
                    }  
                }
            }
            catch(java.io.IOException e) {/* oops */}
        }

        return "";
    }

/******************************************************************************
    NAME:     closeAndDispose()
    PURPOSE:  To clean up and get ready to exit

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void closeAndDispose()
    {
        setVisible(false);
        dispose();
        System.exit(0);
    }

/******************************************************************************
    NAME:     complain
    PURPOSE:  To pop up message box when exception occurs

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void complain(String title, String text)
    {
        JOptionPane msg = new JOptionPane(text);
        JDialog dialog = msg.createDialog(this,title);
        dialog.setVisible(true);
    }

/******************************************************************************
    NAME:     displayStatus
    PURPOSE:  To display Status in a status dialog

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void displayStatus(String s)
    {
        statusDialog.append(s);
    }

/******************************************************************************
    NAME:     showStatusWindow
    PURPOSE:  To pop up a status dialog 

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void showStatusWindow()
    {
        statusDialog.setVisible(true);
    }

/******************************************************************************
    NAME:     getInstance
    PURPOSE:  To garantee that there is one and only instance of ModisFrame
              object floating around.

    RETURN VALUE:  ModisFrame
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
    1. This is the "class" member function, can be invoked by class name.
******************************************************************************/
    public static ModisFrame getInstance()
    {
        if ( instance == null )
        {
            instance = new ModisFrame();
        }

        return instance;
    }

/******************************************************************************
    NAME:     getOutputFormat
    PURPOSE:  To get Output Format

    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    final public String getOutputFormat()
    {
        Object item = outputFormatCombo.getSelectedItem();
        final String retval;

        if ( item == null )
        {
            retval = "";
        }
        else
        {
            retval = item.toString();
        }

        return retval;
    }

/******************************************************************************
    NAME:     getResamplingType
    PURPOSE:  To get Resampling Type

    RETURN VALUE:   String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    final public String getResamplingType()
    {
        Object item = resamplingCombo.getSelectedItem();
        final String retval;

        if ( item == null )
        {
            retval = "";
        }
        else
        {
            retval = item.toString();
        }
        return retval;
    }

/******************************************************************************
    NAME:     getSpatialSubsetType
    PURPOSE:  To get Spatial Subset Type

    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    final public String getSpatialSubsetType()
    {
        Object item = spatialSubsetCombo.getSelectedItem();
        final String retval;

        if ( item == null )
        {
            retval = "";
        }
        else
        {
            retval = item.toString();
        }

        return retval;
    }

/******************************************************************************
    NAME:     getProjectionType
    PURPOSE:  To get Projection Type

    RETURN VALUE: String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    final public String getProjectionType()
    {
        Object item = projectionCombo.getSelectedItem();
        final String retval;

        if ( item == null )
        {
            retval = "";
        }
        else
        {
            retval = item.toString();
        }

        return retval;
    }

/******************************************************************************
    NAME:     getDataType
    PURPOSE:  To get Data Type

    RETURN VALUE: String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    final public String getDataType()
    {
        Object item = dataTypeCombo.getSelectedItem();
        final String retval;

        if ( item == null )
        {
            retval = "";
        }
        else
        {
            retval = item.toString();
        }

        return retval;
    }

/******************************************************************************
    NAME:     inputFileNameTextActivity
    PURPOSE:  To monitor input File Name Text Activity

    RETURN VALUE:  int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private void inputFileNameTextActivity()
    {
        openInputFileButton.setEnabled(inputFileNameText.getText().length()>0);
        model.setInputFilename(inputFileNameText.getText());
    }

/******************************************************************************
    NAME:     geolocFileNameTextActivity
    PURPOSE:  To monitor input Geoloc Name Text Activity

    RETURN VALUE:  int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private void geolocFileNameTextActivity()
    {
        openGeolocFileButton.setEnabled(geolocFileNameText.getText().length()>0);
        model.setGeolocFilename(geolocFileNameText.getText());
    }

/******************************************************************************
    NAME:     fillGUIInputFromHeaderData()
    PURPOSE:  To fill in the appropriate visible components on GUI based on
              the data from header data.
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void fillGUIInputFromHeaderData()
    {
        doSetCornerPoints = false;

        /**
         * Populate the band list.  HDR files don't have
         * the selected/not selected array, so all bands are
         * selected.
         */
        selectedBandsList.setEnabled(true);
        ChooserListModel selectedLm = new ChooserListModel();
        selectedBandsList.setModel(selectedLm);
        availableBandsList.setEnabled(true);
        availableBandsList.setModel(new ChooserListModel());

        final Vector bands = model.getBandList();
        Enumeration mrtenum = bands.elements();
        while ( mrtenum.hasMoreElements())
        {
            selectedLm.addElement(mrtenum.nextElement());
        }

        addBandButton.setEnabled(false);
        removeBandButton.setEnabled(false);

        selectedBandCount.setText(Integer.toString(bands.size()));
        availableBandCount.setText("0");

        spatialSubsetCombo.setEnabled(true);

        startXCoordLSText.setText("");
        startYCoordLSText.setText("");
        endXCoordLSText.setText("");
        endYCoordLSText.setText("");

        startXCoordLLText.setText("");
        startYCoordLLText.setText("");
        endXCoordLLText.setText("");
        endYCoordLLText.setText("");

        startXCoordXYText.setText("");
        startYCoordXYText.setText("");
        endXCoordXYText.setText("");
        endYCoordXYText.setText("");

        startXCoordLSText.setEnabled(true);
        startYCoordLSText.setEnabled(true);
        endXCoordLSText.setEnabled(true);
        endYCoordLSText.setEnabled(true);

        startXCoordLLText.setEnabled(true);
        startYCoordLLText.setEnabled(true);
        endXCoordLLText.setEnabled(true);
        endYCoordLLText.setEnabled(true);

        startXCoordXYText.setEnabled(true);
        startYCoordXYText.setEnabled(true);
        endXCoordXYText.setEnabled(true);
        endYCoordXYText.setEnabled(true);

        viewMetadataButton.setEnabled(true);
        saveOutputFileButton.setEnabled(true);
        jMenuFileSpecifyOutputFile.setEnabled(true);    

        if ( model.getSpatialSubsetType() == ModisModel.SPACE_LAT_LON )
        {
            model.setSpatialSubsetType(1);
            startYCoordLLText.setText(
                Double.toString(model.getCornerCoordinate(0,0)));
            startXCoordLLText.setText(
                Double.toString(model.getCornerCoordinate(0,1)));

            endYCoordLLText.setText(
                Double.toString(model.getCornerCoordinate(3,0)));
            endXCoordLLText.setText(
                Double.toString(model.getCornerCoordinate(3,1)));

            startYCoordLSText.setText("0");
            startXCoordLSText.setText("0");
            endYCoordLSText.setText(Integer.toString(model.getMaximumLines()
                - 1));
            endXCoordLSText.setText(Integer.toString(model.getMaximumSamples()
                - 1));
            resolutionLabel.setText(
                Integer.toString(model.getMaximumLines()) + "x" +
                Integer.toString(model.getMaximumSamples()));

            startXCoordLSText.setVisible(false);
            startYCoordLSText.setVisible(false);
            endXCoordLSText.setVisible(false);
            endYCoordLSText.setVisible(false);

            startXCoordXYText.setVisible(false);
            startYCoordXYText.setVisible(false);
            endXCoordXYText.setVisible(false);
            endYCoordXYText.setVisible(false);

            startXCoordLLText.setVisible(true);
            startYCoordLLText.setVisible(true);
            endXCoordLLText.setVisible(true);
            endYCoordLLText.setVisible(true);
        }
        else if (model.getSpatialSubsetType() == ModisModel.SPACE_LINE_SAMPLE)
        {
            model.setSpatialSubsetType(2);
            startYCoordLSText.setText(
                Long.toString((long)model.getCornerCoordinate(0,0)));
            startXCoordLSText.setText(
                Long.toString((long)model.getCornerCoordinate(0,1)));

            endYCoordLSText.setText(
                Long.toString((long)model.getCornerCoordinate(3,0)));
            endXCoordLSText.setText(
                Long.toString((long)model.getCornerCoordinate(3,1)));

            startYCoordLLText.setText(
                Double.toString(model.getInputImageExtent(0)[0]));
            startXCoordLLText.setText(
                Double.toString(model.getInputImageExtent(0)[1]));

            endYCoordLLText.setText(
                Double.toString(model.getInputImageExtent(3)[0]));
            endXCoordLLText.setText(
                Double.toString(model.getInputImageExtent(3)[1]));

            startXCoordLSText.setVisible(true);
            startYCoordLSText.setVisible(true);
            endXCoordLSText.setVisible(true);
            endYCoordLSText.setVisible(true);

            startXCoordLLText.setVisible(false);
            startYCoordLLText.setVisible(false);
            endXCoordLLText.setVisible(false);
            endYCoordLLText.setVisible(false);

            startXCoordXYText.setVisible(false);
            startYCoordXYText.setVisible(false);
            endXCoordXYText.setVisible(false);
            endYCoordXYText.setVisible(false);
        }
        else if ( model.getSpatialSubsetType() == ModisModel.SPACE_PROJ_XY )
        {
            model.setSpatialSubsetType(3);
            startYCoordXYText.setText("0.0");
            startXCoordXYText.setText("0.0");
            endYCoordXYText.setText("0.0");
            endXCoordXYText.setText("0.0");

            startXCoordXYText.setVisible(true);
            startYCoordXYText.setVisible(true);
            endXCoordXYText.setVisible(true);
            endYCoordXYText.setVisible(true);

            startXCoordLLText.setVisible(false);
            startYCoordLLText.setVisible(false);
            endXCoordLLText.setVisible(false);
            endYCoordLLText.setVisible(false);

            startXCoordLSText.setVisible(false);
            startYCoordLSText.setVisible(false);
            endXCoordLSText.setVisible(false);
            endYCoordLSText.setVisible(false);
        }

        setSpatialSubsetType(model.getSpatialSubsetType());
        detailTextArea.setText("");
        detailTextArea.append("Total Number of Bands: ");
        detailTextArea.append(Integer.toString(model.getNumBands()));
        detailTextArea.append("\n");

        detailTextArea.append("Data Type: ( " );
        String []ss = model.getInputDataTypeArray();
        int j;
        for (j = 0; j < model.getNumBands()-1; j++)
            detailTextArea.append(ss[j] + ", ");
        detailTextArea.append(ss[j] + " )\n");

        detailTextArea.append("Number of lines: ( " );
        int[] ii = model.getInputNumOfLinesArray();
        for (j = 0; j < model.getNumBands()-1; j++)
            detailTextArea.append(ii[j] + ", ");
        detailTextArea.append(ii[j] + " )\n");

        detailTextArea.append("Number of samples: ( " );
        ii = model.getInputNumOfSamplesArray();
        for (j = 0; j < model.getNumBands()-1; j++)
            detailTextArea.append(ii[j] + ", ");
        detailTextArea.append(ii[j] + " )\n");

        detailTextArea.append("Lat/Long of Upper-Left Corner: ( ");
        detailTextArea.append(
        Double.toString(model.getInputImageExtent(0)[0]));
        detailTextArea.append(" ");
        detailTextArea.append(
            Double.toString(model.getInputImageExtent(0)[1]));
        detailTextArea.append(" )\n");

        detailTextArea.append("Lat/Long of Upper-Right Corner: ( ");
        detailTextArea.append(
            Double.toString(model.getInputImageExtent(1)[0]));
        detailTextArea.append(" ");
        detailTextArea.append(
            Double.toString(model.getInputImageExtent(1)[1]));
        detailTextArea.append(" )\n");

        detailTextArea.append("Lat/Long of Lower-Left Corner: ( ");
        detailTextArea.append(
            Double.toString(model.getInputImageExtent(2)[0]));
        detailTextArea.append(" ");
        detailTextArea.append(
            Double.toString(model.getInputImageExtent(2)[1]));
        detailTextArea.append(" )\n");

        detailTextArea.append("Lat/Long of Lower-Right Corner: ( ");
        detailTextArea.append(
            Double.toString(model.getInputImageExtent(3)[0]));
        detailTextArea.append(" ");
        detailTextArea.append(
            Double.toString(model.getInputImageExtent(3)[1]));
        detailTextArea.append(" )\n");
     
        detailTextArea.setCaretPosition(0);
        if (model.getSpatialSubsetType() == ModisModel.SPACE_LAT_LON)
        {
            startYCoordLLText.setText(
                Double.toString(model.getCornerCoordinate(0,0)));
            startXCoordLLText.setText(
                Double.toString(model.getCornerCoordinate(0,1)));

            endYCoordLLText.setText(
                Double.toString(model.getCornerCoordinate(3,0)));
            endXCoordLLText.setText(
                Double.toString(model.getCornerCoordinate(3,1)));

            startXCoordLSText.setVisible(false);
            startYCoordLSText.setVisible(false);
            endXCoordLSText.setVisible(false);
            endYCoordLSText.setVisible(false);

            startXCoordXYText.setVisible(false);
            startYCoordXYText.setVisible(false);
            endXCoordXYText.setVisible(false);
            endYCoordXYText.setVisible(false);

            startXCoordLLText.setVisible(true);
            startYCoordLLText.setVisible(true);
            endXCoordLLText.setVisible(true);
            endYCoordLLText.setVisible(true);
        }
        else if (model.getSpatialSubsetType() == ModisModel.SPACE_LINE_SAMPLE)
        {
            startYCoordLSText.setText(
                Long.toString((long)model.getCornerCoordinate(0,0)));
            startXCoordLSText.setText(
                Long.toString((long)model.getCornerCoordinate(0,1)));

            endYCoordLSText.setText(
                Long.toString((long)model.getCornerCoordinate(3,0)));
            endXCoordLSText.setText(
                Long.toString((long)model.getCornerCoordinate(3,1)));

            startXCoordLSText.setVisible(true);
            startYCoordLSText.setVisible(true);
            endXCoordLSText.setVisible(true);
            endYCoordLSText.setVisible(true);

            startXCoordLLText.setVisible(false);
            startYCoordLLText.setVisible(false);
            endXCoordLLText.setVisible(false);
            endYCoordLLText.setVisible(false);

            startXCoordXYText.setVisible(false);
            startYCoordXYText.setVisible(false);
            endXCoordXYText.setVisible(false);
            endYCoordXYText.setVisible(false);
        }
        else if (model.getSpatialSubsetType() == ModisModel.SPACE_PROJ_XY)
        {
            startYCoordXYText.setText(
                Double.toString(model.getCornerCoordinate(0,0)));
            startXCoordXYText.setText(
                Double.toString(model.getCornerCoordinate(0,1)));

            endYCoordXYText.setText(
                Double.toString(model.getCornerCoordinate(3,0)));
            endXCoordXYText.setText(
                Double.toString(model.getCornerCoordinate(3,1)));

            startXCoordXYText.setVisible(true);
            startYCoordXYText.setVisible(true);
            endXCoordXYText.setVisible(true);
            endYCoordXYText.setVisible(true);

            startXCoordLLText.setVisible(false);
            startYCoordLLText.setVisible(false);
            endXCoordLLText.setVisible(false);
            endYCoordLLText.setVisible(false);

            startXCoordLSText.setVisible(false);
            startYCoordLSText.setVisible(false);
            endXCoordLSText.setVisible(false);
            endYCoordLSText.setVisible(false);
        }

/*        projectionParametersButton.setEnabled(true);
        outputFormatCombo.setEnabled(true);
        resamplingCombo.setEnabled(true);
        projectionCombo.setEnabled(true);
        dataTypeCombo.setEnabled(true);
*/

        setSaveRunEnablements();
        doSetCornerPoints = true;
        parameterFileNameText.setText("");
    }


/******************************************************************************
    NAME:     fillGUIFromParamFile()
    PURPOSE:  To load parameter data and fill in the GUI.
    RETURN VALUE:

    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development
    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void fillGUIFromParamFile()
    {
        fillGUIInputFromHeaderData();
        final ChooserListModel slm =
            (ChooserListModel)selectedBandsList.getModel();
        final ChooserListModel alm =
            (ChooserListModel)availableBandsList.getModel();

        inputFileNameText.setText(model.getInputFilename());
        geolocFileNameText.setText(model.getGeolocFilename());

        /* Set spectral band selections to reflect parameter file settings.
         * Header file reader just puts everything in the selected list by
         * default, so we have to move unselected bands out. */
        /* get all of the bands from selected list, actually get all of bands
         * by default */
        final Vector bandsList = model.getBandList();

        for (int i = 0; i < model.getNumBands(); i++)
        {
            /* if not selected, then remove it accordingly */
            if ( !model.isSelectedBand(i) )
            {
                final String notSelectedBandName =
                    (String)bandsList.elementAt(i);
                int bandIndex = -1;
                Enumeration mrtenum = bandsList.elements();

                for (int listIndex = 0; listIndex < slm.getSize(); listIndex++)
                {
                    String listValue = (String)slm.getElementAt(listIndex);
                    if (notSelectedBandName.equals(listValue))
                    {
                        bandIndex = listIndex;
                        break;
                    }
                }

                if (bandIndex >= 0)
		{   /* if already exists in the selected list */
                    final Object item = slm.getElementAt(bandIndex);
                    /* remove from selected list */
                    slm.removeElementAt(bandIndex);
                    /* add it into the available list */
                    alm.addElement(item);
                }
            }
            parameterFileNameText.setText(model.getParameterFilename());
        }

        selectedBandCount.setText(Integer.toString(slm.getSize()));
        availableBandCount.setText(Integer.toString(alm.getSize()));

        /* ready to load the output-panel side values from parameter file */
        fillGUIOutputPanelFromParamFile ();
    }

/******************************************************************************
    NAME:     fillGUIOutputPanelFromParamFile()
    PURPOSE:  To fill the output panel only based on the data from parameter
              file

    RETURN VALUE: void
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Shujing Jia                    Java  Refactoring
    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
******************************************************************************/
    private void fillGUIOutputPanelFromParamFile ()
    {
        outputFileNameText.setText(model.getOutputFilename());

        int i;
        int outputFormat = model.getOutputFormat();
        ComboBoxModel outputFormatCbm = outputFormatCombo.getModel();
        final int numOutputFormats = outputFormatCbm.getSize();

        for (i=0; i< numOutputFormats; i++)
        {
            final ModisModel.TextIdPair item =
                (ModisModel.TextIdPair)outputFormatCbm.getElementAt(i);
            if (item.getId() == outputFormat)
            {
                outputFormatCombo.setSelectedIndex(i);
                break;
            }
        }
        prevOutputFileType= outputFormatCombo.getSelectedIndex();

        // fill in resampling combox
        int resamplingType = model.getResamplingType();
        ComboBoxModel resamplingTypeCbm = resamplingCombo.getModel();
        final int numResamplingTypes = resamplingTypeCbm.getSize();
        for (i=0; i< numResamplingTypes; ++i)
        {
            final ModisModel.TextIdPair item =
                (ModisModel.TextIdPair)resamplingTypeCbm.getElementAt(i);
            if (item.getId() == resamplingType)
            {
                resamplingCombo.setSelectedIndex(i);     // trigger event
                break;
            }
        }
        prevResamplingType = resamplingCombo.getSelectedIndex();

        // fill in Output Projection Type combox
        int projectionType = model.getOutputProjectionType();
        ComboBoxModel projectionTypeCbm = projectionCombo.getModel();
        final int numProjectionTypes = projectionTypeCbm.getSize();

        for (i=0; i<numProjectionTypes; i++)
        {
            final ModisModel.TextIdPair item =
                (ModisModel.TextIdPair)projectionTypeCbm.getElementAt(i);
            if (item.getId() == projectionType)
            {
                projectionCombo.setSelectedIndex(i);    // trigger event
                break;
            }
        }
        prevOutputProjType = projectionCombo.getSelectedIndex();
        setSaveRunEnablements();

        // fill in Output Data Type combox
        int dataType = model.getOutputDataType();
        ComboBoxModel dataTypeCbm = dataTypeCombo.getModel();
        final int numDataTypes = dataTypeCbm.getSize();

        for (i=0; i<numDataTypes; i++)
        {
            final ModisModel.TextIdPair item =
                (ModisModel.TextIdPair)dataTypeCbm.getElementAt(i);
            if (item.getId() == dataType)
            {
                dataTypeCombo.setSelectedIndex(i);    // trigger event
                break;
            }
        }
        prevDataType = dataTypeCombo.getSelectedIndex();
        setSaveRunEnablements();

        // check output pixel size
        if (model.getOutputPixelSize() !=  null)
        {
            pixelSizeText.setText(model.getOutputPixelSize());
            prevOutputPixelSize = model.getOutputPixelSize();
        }

        // set the default pixel size unit (GEO--DEGREES, others--METERS)
        if (model.getOutputProjectionTypeString() == "GEO")
            pixelSizeUnitText.setText("degrees");
        else
            pixelSizeUnitText.setText("meters");
    }

/******************************************************************************
    NAME:     outputFileNameTextActivity
    PURPOSE:  To respond to output file name text box each time when it changes
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development
******************************************************************************/
    private void outputFileNameTextActivity()
    {
        model.setOutputFilename(outputFileNameText.getText());
    }

/******************************************************************************
    NAME:     pixelSizeTextActivity
    PURPOSE:  To respond to output pixel size text box each time when it changes
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development
******************************************************************************/
    private void pixelSizeTextActivity()
    {
        String s = pixelSizeText.getText();
        for (short i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c != '.')
            {
                if (! Character.isDigit(c))
                {
                    /* show a message box */
                    complain ("Typing error...",
                        "Pixel size field must be a number greater " +
                        "than zero. Please try again...");
                    return ;
                }
            }
        }

        model.setOutputPixelSize(pixelSizeText.getText());
        prevOutputPixelSize = model.getOutputPixelSize();
    }

/******************************************************************************
    NAME:     populateCombo
    PURPOSE:  To populate the items for combox on the GUI

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private void populateCombo(JComboBox combo,Vector v)
    {
        Enumeration mrtenum = v.elements();
        while (mrtenum.hasMoreElements())
        {
            combo.addItem(mrtenum.nextElement());
        }
    }

/******************************************************************************
    NAME:     setCornerPoints
    PURPOSE:  To set corner points on GUI

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private void setCornerPoints()
    {
        if (doSetCornerPoints)
        {
            try
            {
                String startY;
                String startX;
                String endY;
                String endX;

                if (model.getSpatialSubsetType() == ModisModel.SPACE_LAT_LON)
                {
                    startY = startYCoordLLText.getText();
                    startX = startXCoordLLText.getText();
                    endY = endYCoordLLText.getText();
                    endX = endXCoordLLText.getText();
                }
                else if (model.getSpatialSubsetType() ==
                    ModisModel.SPACE_LINE_SAMPLE)
                {
                    startY = startYCoordLSText.getText();
                    startX = startXCoordLSText.getText();
                    endY = endYCoordLSText.getText();
                    endX = endXCoordLSText.getText();
                }
                else
                /* model.getSpatialSubsetType() == ModisModel.SPACE_PROJ_XY */
                {
                    startY = startYCoordXYText.getText();
                    startX = startXCoordXYText.getText();
                    endY = endYCoordXYText.getText();
                    endX = endXCoordXYText.getText();
                }

                double[] startPt = {
                    Double.valueOf(startY).doubleValue(),
                    Double.valueOf(startX).doubleValue() };
                double[] endPt = {
                    Double.valueOf(endY).doubleValue(),
                    Double.valueOf(endX).doubleValue() };

                cornerPoints[0] = startPt;
                cornerPoints[3] = endPt;
                model.setCornerPoint(0,startPt);
                model.setCornerPoint(3,endPt);

                double[] urPt = new double[2];
                double[] llPt = new double[2];
                if (model.getSpatialSubsetType() == ModisModel.SPACE_LAT_LON)
                {
                    urPt[0] = 0.0;
                    urPt[1] = 0.0;
                    llPt[0] = 0.0;
                    llPt[1] = 0.0;
                }
                else if (model.getSpatialSubsetType() ==
                    ModisModel.SPACE_LINE_SAMPLE)
                {
                    urPt[0] = startPt[0];
                    urPt[1] = endPt[1];
                    llPt[0] = endPt[0];
                    llPt[1] = startPt[1];
                }
                else
                /* model.getSpatialSubsetType() == ModisModel.SPACE_PROJ_XY */
                {
                    urPt[0] = 0.0;
                    urPt[1] = 0.0;
                    llPt[0] = 0.0;
                    llPt[1] = 0.0;
                }

                cornerPoints[1] = urPt;
                cornerPoints[2] = llPt;

                model.setCornerPoint(1,urPt);
                model.setCornerPoint(2,llPt);
            }
            catch (NumberFormatException e) {}

            setSaveRunEnablements();
        }
    }

/******************************************************************************
    NAME:     setInitialEnablements
    PURPOSE:  To initialize various states for components on GUI
    RETURN VALUE:

    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInitialEnablements()
    {
        jMenuFileOpenInputFile.setEnabled(true);
        jMenuFileOpenGeolocFile.setEnabled(true);
        jMenuFileSpecifyOutputFile.setEnabled(false);
        jMenuFileLoadParameters.setEnabled(true);
        jMenuFileSaveParameters.setEnabled(false);
        jMenuFileExit.setEnabled(true);
        jMenuActionRun.setEnabled(true);
        jMenuHelpAbout.setEnabled(true);
        inputFileNameText.setEnabled(true);
        inputFileNameText.setEditable(false);
        geolocFileNameText.setEnabled(true);
        geolocFileNameText.setEditable(false);
        openGeolocFileButton.setEnabled(true);
        openInputFileButton.setEnabled(true);
        viewMetadataButton.setEnabled(false);
        detailTextArea.setEnabled(true);
        detailTextArea.setEditable(false);
        availableBandsList.setEnabled(false);
        selectedBandsList.setEnabled(false);
        addBandButton.setEnabled(false);
        removeBandButton.setEnabled(false);
        spatialSubsetCombo.setEnabled(false);
        outputFormatCombo.setEnabled(false);
        resamplingCombo.setEnabled(false);
        projectionCombo.setEnabled(false);
        projectionParametersButton.setEnabled(false);
        dataTypeCombo.setEnabled(false);
        saveOutputFileButton.setEnabled(false);
        jMenuFileSpecifyOutputFile.setEnabled(false);
        outputFileNameText.setEnabled(true);
        outputFileNameText.setEditable(false);
        runButton.setEnabled(false);
        jMenuActionRun.setEnabled(false);
        exitButton.setEnabled(true);
        loadParametersButton.setEnabled(true);
        saveParametersButton.setEnabled(false);
        parameterFileNameText.setEnabled(true);
        parameterFileNameText.setEditable(false);
    }

/******************************************************************************
    NAME:     setInputFileName
    PURPOSE:  To set Input File Name
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputFileName(String value)
    {
        inputFileNameText.setText(value);
        model.setInputFilename(value);
    }

/******************************************************************************
    NAME:     setGeolocFileName
    PURPOSE:  To set Geolocation File Name
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setGeolocFileName(String value)
    {
        geolocFileNameText.setText(value);
        model.setGeolocFilename(value);
    }

/******************************************************************************
    NAME:     setOutputFileName
    PURPOSE:  To set up output file name
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputFileName(String value)
    {
        outputFileNameText.setText(value);
        model.setOutputFilename(value);
        setOutputFileNameExtension();
    }

/******************************************************************************
    NAME:     setOutputFileNameExtension
    PURPOSE:  To parse and set Output File Name Extension
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputFileNameExtension()
    {
        int format = model.getOutputFormat();
        String extension;
        switch (format)
        {
            case ModisModel.MULTIFILE:
                extension = ".hdr";
                break;
            case ModisModel.HDFEOS:
                extension = ".hdf";
                break;
            case ModisModel.GEOTIFF:
                extension = ".tif";
                break;
            default:
                extension = "";
        }

        String outFile = model.getOutputFilename();
        if ( outFile != null)
        {
            int dotIndex = outFile.lastIndexOf('.');
            if (dotIndex >= 0 && !outFile.toLowerCase().endsWith(extension))
            {
                outFile = outFile.substring(0,dotIndex) + extension;
                outputFileNameText.setText(outFile);
                setOutputFileName(outFile);
            }
            else if ( dotIndex < 0 && outFile.length() > 0 )
            {
                setOutputFileName(outFile + extension);
            }
        }
    }

/******************************************************************************
    NAME:     setParameterFileName
    PURPOSE:  This is a dispatching (delegation) function.
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setParameterFileName(String value)
    {
        model.setParameterFilename(value);
    }

/******************************************************************************
    NAME:     setSaveRunEnablements
    PURPOSE:  To set Save Run Enablements.  The run button will not be
              enabled until all output fields are filled.
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private void setSaveRunEnablements()
    {
        boolean enb = false;

        enb = selectedBandsList.getModel().getSize() != 0;
        if (spatialSubsetCombo.getSelectedIndex() >= 0 )
        {
            if ( spatialSubsetCombo.getSelectedItem().toString().startsWith(
                "Input Line"))
            {
                enb &= startYCoordLSText.getText().length() != 0;
                enb &= startXCoordLSText.getText().length() != 0;
                enb &= endYCoordLSText.getText().length() != 0;
                enb &= endXCoordLSText.getText().length() != 0;
            }
            else if ( spatialSubsetCombo.getSelectedItem().toString().
                startsWith("Input Lat"))
            {
                enb &= startYCoordLLText.getText().length() != 0;
                enb &= startXCoordLLText.getText().length() != 0;
                enb &= endYCoordLLText.getText().length() != 0;
                enb &= endXCoordLLText.getText().length() != 0;
            }
            else if ( spatialSubsetCombo.getSelectedItem().toString().
                startsWith("Output Proj"))
            {
                enb &= startYCoordXYText.getText().length() != 0;
                enb &= startXCoordXYText.getText().length() != 0;
                enb &= endYCoordXYText.getText().length() != 0;
                enb &= endXCoordXYText.getText().length() != 0;
            }
        }
        else
        {
            enb = false;
        }
        enb &= outputFileNameText.getText().length() > 0;
        enb &= outputFormatCombo.getSelectedIndex() >= 0;
        enb &= resamplingCombo.getSelectedIndex() >= 0;
        enb &= projectionCombo.getSelectedIndex() >= 0;
        enb &= dataTypeCombo.getSelectedIndex() >= 0;
      
        projectionParametersButton.setEnabled(true);
        runButton.setEnabled(enb);
        jMenuActionRun.setEnabled(enb);
        saveParametersButton.setEnabled(enb);
        jMenuFileSaveParameters.setEnabled(enb);
    }

/******************************************************************************
    NAME:     setSpatialSubsetType
    PURPOSE:  To set up Spatial Subset

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private void setSpatialSubsetType(int subsetId)
    {
        model.setSpatialSubsetType(subsetId);

        ComboBoxModel cbm = spatialSubsetCombo.getModel();
        int numElements = cbm.getSize();
        for (int i=0; i<numElements; ++i)
        {
            final ModisModel.TextIdPair tip;
            tip = (ModisModel.TextIdPair)cbm.getElementAt(i);
            if (tip.getId() == subsetId)
            {
                spatialSubsetCombo.setSelectedIndex(i);
                break;
            }
        }
        setSaveRunEnablements();
    }

/******************************************************************************
    NAME:     initComponents
    PURPOSE:  To intialize the GUI components and set up layout for panels.
              It sets up all component's default states and event triggers.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
    This snippet code uses hard-coded layout, which is very inflexible and rigid
    design in terms of further modification and readablity. Therefore It needs
    more flexibility for layout management. A good startegy for a flexible GUI
    is to set a "smart" layout manager, which can be the combination
    of several Java layout managers.
******************************************************************************/
    public void initComponents() throws Exception
    {
        /* IMPORTANT: Source code between BEGIN/END comment pair will be
         * regenerated every time the form is saved. All manual changes will
         * be overwritten. */
        /* BEGIN GENERATED CODE */
        /* the following code sets the frame's initial state */
        menuBar.setVisible(true);
        menuBar.add(jMenuFile);
        menuBar.add(jMenuAction);
        menuBar.add(jMenuHelp);

        jMenuFile.setVisible(true);
        jMenuFile.setText("File");
        jMenuFile.setMnemonic('f');// shortcut key as f
        jMenuFile.add(jMenuFileOpenInputFile);
        jMenuFile.add(jMenuFileOpenGeolocFile);
        jMenuFile.add(jMenuFileSpecifyOutputFile);
        jMenuFile.add(jMenuFileSeparator1);
        jMenuFile.add(jMenuFileLoadParameters);
        jMenuFile.add(jMenuFileSaveParameters);
        jMenuFile.add(jMenuFileSeparator2);
        jMenuFile.add(jMenuFileExit);

        jMenuFileOpenInputFile.setVisible(true);
        jMenuFileOpenInputFile.setText("Open Input File");
        jMenuFileOpenInputFile.setMnemonic('o');

        jMenuFileOpenGeolocFile.setVisible(true);
        jMenuFileOpenGeolocFile.setText("Open Geolocation File");
        jMenuFileOpenGeolocFile.setMnemonic('g');

        jMenuFileSpecifyOutputFile.setVisible(true);
        jMenuFileSpecifyOutputFile.setText("Specify Output File");
        jMenuFileSpecifyOutputFile.setMnemonic('p');

        jMenuFileSeparator1.setVisible(true);

        jMenuFileLoadParameters.setVisible(true);
        jMenuFileLoadParameters.setText("Load Parameter File");
        jMenuFileLoadParameters.setMnemonic('l');

        jMenuFileSaveParameters.setVisible(true);
        jMenuFileSaveParameters.setText("Save Parameter File");
        jMenuFileSaveParameters.setMnemonic('s');

        jMenuFileSeparator2.setVisible(true);

        jMenuFileExit.setVisible(true);
        jMenuFileExit.setText("Exit");
        jMenuFileExit.setMnemonic('x');

        jMenuAction.setVisible(true);
        jMenuAction.setText("Action");
        jMenuAction.setMnemonic('a');
        jMenuAction.add(jMenuActionRun);
      
        jMenuActionRun.setVisible(true);
        jMenuActionRun.setText("Run"); 
        jMenuActionRun.setMnemonic('r');

        jMenuHelp.setVisible(true);
        jMenuHelp.setText("Help");
        jMenuHelp.setMnemonic('h');
        jMenuHelp.add(jMenuHelpAbout);

        jMenuHelpAbout.setVisible(true);
        jMenuHelpAbout.setText("About...");
        jMenuHelpAbout.setMnemonic('b');
      
        jLabel12.setSize(new java.awt.Dimension(90, 20));
        jLabel12.setVisible(true);
        jLabel12.setText("Source");
        jLabel12.setLocation(new java.awt.Point(10,0));

        /* define input pane */
        inputPane.setSize(new java.awt.Dimension(440, 570));
        inputPane.setVisible(true);
        inputPane.setLayout(null);
        inputPane.setLocation(new java.awt.Point(0, 20));
        inputPane.add(inputFileNameText);
        inputPane.add(geolocFileNameText);
        inputPane.add(openInputFileButton);
        inputPane.add(jLabel2);
        inputPane.add(detailScrollPane);
        inputPane.add(availableBandsScrollPane);
        inputPane.add(selectedBandsScrollPane);
        inputPane.add(jLabel3);
        inputPane.add(jLabel4);
        inputPane.add(addBandButton);
        inputPane.add(removeBandButton);
        inputPane.add(jLabel5);
        inputPane.add(spatialSubsetCombo);
        inputPane.add(jLabel6);
        inputPane.add(jLabel7);
        inputPane.add(availableBandCount);
        inputPane.add(selectedBandCount);
        inputPane.add(yCoordLabel);
        inputPane.add(jLabel9);
        inputPane.add(jLabel10);
        inputPane.add(resolutionLabel);
        inputPane.add(resolutionLabelLabel);
        inputPane.add(xCoordLabel);
        inputPane.add(startYCoordLLText);
        inputPane.add(startXCoordLLText);
        inputPane.add(endYCoordLLText);
        inputPane.add(endXCoordLLText);
        inputPane.add(startYCoordLSText);
        inputPane.add(startXCoordLSText);
        inputPane.add(endYCoordLSText);
        inputPane.add(endXCoordLSText);
        inputPane.add(startYCoordXYText);
        inputPane.add(startXCoordXYText);
        inputPane.add(endYCoordXYText);
        inputPane.add(endXCoordXYText);
        inputPane.add(viewMetadataButton);
        inputPane.add(jLabel17);
        inputPane.add(jLabel18);
        inputPane.add(openGeolocFileButton);

        jLabel17.setSize(new java.awt.Dimension(148, 20));
        jLabel17.setVisible(true);
        jLabel17.setText("Input File");
        jLabel17.setLocation(new java.awt.Point(20, 0));

        inputFileNameText.setSize(new java.awt.Dimension(240, 20));
        inputFileNameText.setVisible(true);
        inputFileNameText.setBackground(Color.white);
        inputFileNameText.setBorder(BorderFactory.createLineBorder(Color.black));
        inputFileNameText.setLocation(new java.awt.Point(20, 20));

        openInputFileButton.setSize(new java.awt.Dimension(170, 30));
        openInputFileButton.setVisible(true);
        openInputFileButton.setText("Open Input File ...");
        openInputFileButton.setMnemonic('o');
        openInputFileButton.setToolTipText(
            "Opens an Input file of type .hdf");
        openInputFileButton.setLocation(new java.awt.Point(265, 10));

        jLabel2.setSize(new java.awt.Dimension(130, 30));
        jLabel2.setVisible(true);
        jLabel2.setText("Input File Info:");
        jLabel2.setLocation(new java.awt.Point(20, 50));

        viewMetadataButton.setSize(new java.awt.Dimension(170, 30));
        viewMetadataButton.setVisible(true);
        viewMetadataButton.setText("View Metadata ...");
        viewMetadataButton.setMnemonic('v');
        viewMetadataButton.setLocation(new java.awt.Point(265, 50));
        viewMetadataButton.setEnabled(true);

        detailScrollPane.setSize(new java.awt.Dimension(400, 120));
        detailScrollPane.setVisible(true);
        detailScrollPane.setLocation(new java.awt.Point(20, 90));
        detailScrollPane.getViewport().add(detailTextArea);

        detailTextArea.setVisible(true);
      
        availableBandsScrollPane.setSize(new java.awt.Dimension(160, 100));
        availableBandsScrollPane.setVisible(true);
        availableBandsList.setVisible(true);
        availableBandsScrollPane.setViewportView(availableBandsList);
        availableBandsScrollPane.setLocation(new java.awt.Point(20, 250));

        selectedBandsScrollPane.setSize(new java.awt.Dimension(160, 100));
        selectedBandsScrollPane.setVisible(true);
        selectedBandsList.setVisible(true);
        selectedBandsScrollPane.setViewportView(selectedBandsList);
        selectedBandsScrollPane.setLocation(new java.awt.Point(260, 250));

        jLabel3.setSize(new java.awt.Dimension(170, 20));
        jLabel3.setVisible(true);
        jLabel3.setText("Available Bands:        ");
        jLabel3.setLocation(new java.awt.Point(20, 230));

        jLabel4.setSize(new java.awt.Dimension(180, 20));
        jLabel4.setVisible(true);
        jLabel4.setText("Selected Bands:          ");
        jLabel4.setLocation(new java.awt.Point(260, 230));
  
        addBandButton.setSize(new java.awt.Dimension(75, 25));
        addBandButton.setVisible(true);
        addBandButton.setText(">>");
        addBandButton.setLocation(new java.awt.Point(183, 270));

        removeBandButton.setSize(new java.awt.Dimension(75, 25));
        removeBandButton.setVisible(true);
        removeBandButton.setText("<<");
        removeBandButton.setLocation(new java.awt.Point(183, 310));

        jLabel5.setSize(new java.awt.Dimension(120, 20));
        jLabel5.setVisible(true);
        jLabel5.setText("Spatial Subset:");
        jLabel5.setLocation(new java.awt.Point(20, 385));

        spatialSubsetCombo.setSize(new java.awt.Dimension(270, 25));
        spatialSubsetCombo.setVisible(true);
        spatialSubsetCombo.setLocation(new java.awt.Point(140, 385));

        availableBandCount.setSize(new java.awt.Dimension(60, 20));
        availableBandCount.setVisible(true);
        availableBandCount.setLocation(new java.awt.Point(160, 230));

        selectedBandCount.setSize(new java.awt.Dimension(60, 20));
        selectedBandCount.setVisible(true);
        selectedBandCount.setLocation(new java.awt.Point(400, 230));

          
        jLabel9.setSize(new java.awt.Dimension(100, 20));
        jLabel9.setVisible(true);
        jLabel9.setText("UL Corner:");
        jLabel9.setLocation(new java.awt.Point(20, 450));

        jLabel10.setSize(new java.awt.Dimension(100, 20));
        jLabel10.setVisible(true);
        jLabel10.setText("LR Corner:");
        jLabel10.setLocation(new java.awt.Point(20, 480));

        int height = 20;
        int width = 150;
        int x_start1 = 105;
        int x_start2 = 260;

        yCoordLabel.setSize(new java.awt.Dimension(width, height));
        yCoordLabel.setVisible(true);
        yCoordLabel.setText("Latitude");
        yCoordLabel.setLocation(new java.awt.Point(x_start1, 430));
        xCoordLabel.setSize(new java.awt.Dimension(width, height));
        xCoordLabel.setVisible(true);
        xCoordLabel.setText("Longitude");
        xCoordLabel.setLocation(new java.awt.Point(x_start2, 430));

        startYCoordLLText.setSize(new java.awt.Dimension(width, height));
        startYCoordLLText.setVisible(true);
        startYCoordLLText.setBorder(BorderFactory.createLineBorder(Color.black));
        startYCoordLLText.setLocation(new java.awt.Point(x_start1, 450));
        startXCoordLLText.setSize(new java.awt.Dimension(width, height));
        startXCoordLLText.setVisible(true);
        startXCoordLLText.setBorder(BorderFactory.createLineBorder(Color.black));
        startXCoordLLText.setLocation(new java.awt.Point(x_start2, 450));

        endYCoordLLText.setSize(new java.awt.Dimension(width, height));
        endYCoordLLText.setVisible(true);
        endYCoordLLText.setBorder(BorderFactory.createLineBorder(Color.black));
        endYCoordLLText.setLocation(new java.awt.Point(x_start1, 480));
        endXCoordLLText.setSize(new java.awt.Dimension(width, height));
        endXCoordLLText.setVisible(true);
        endXCoordLLText.setBorder(BorderFactory.createLineBorder(Color.black));
        endXCoordLLText.setLocation(new java.awt.Point(x_start2, 480));

        startYCoordLSText.setSize(new java.awt.Dimension(width, height));
        startYCoordLSText.setVisible(true);
        startYCoordLSText.setBorder(BorderFactory.createLineBorder(Color.black));
        startYCoordLSText.setLocation(new java.awt.Point(x_start1, 450));
        startXCoordLSText.setSize(new java.awt.Dimension(width, height));
        startXCoordLSText.setVisible(true);
        startXCoordLSText.setBorder(BorderFactory.createLineBorder(Color.black));
        startXCoordLSText.setLocation(new java.awt.Point(x_start2, 450));

        endYCoordLSText.setSize(new java.awt.Dimension(width, height));
        endYCoordLSText.setVisible(true);
        endYCoordLSText.setBorder(BorderFactory.createLineBorder(Color.black));
        endYCoordLSText.setLocation(new java.awt.Point(x_start1, 480));
        endXCoordLSText.setSize(new java.awt.Dimension(width, height));
        endXCoordLSText.setVisible(true);
        endXCoordLSText.setBorder(BorderFactory.createLineBorder(Color.black));
        endXCoordLSText.setLocation(new java.awt.Point(x_start2, 480));

        startYCoordXYText.setSize(new java.awt.Dimension(width, height));
        startYCoordXYText.setVisible(true);
        startYCoordXYText.setBorder(BorderFactory.createLineBorder(Color.black));
        startYCoordXYText.setLocation(new java.awt.Point(x_start1, 450));
        startXCoordXYText.setSize(new java.awt.Dimension(width, height));
        startXCoordXYText.setVisible(true);
        startXCoordXYText.setBorder(BorderFactory.createLineBorder(Color.black));
        startXCoordXYText.setLocation(new java.awt.Point(x_start2, 450));

        endYCoordXYText.setSize(new java.awt.Dimension(width, height));
        endYCoordXYText.setVisible(true);
        endYCoordXYText.setBorder(BorderFactory.createLineBorder(Color.black));
        endYCoordXYText.setLocation(new java.awt.Point(x_start1, 480));
        endXCoordXYText.setSize(new java.awt.Dimension(width, height));
        endXCoordXYText.setVisible(true);
        endXCoordXYText.setBorder(BorderFactory.createLineBorder(Color.black));
        endXCoordXYText.setLocation(new java.awt.Point(x_start2, 480));

        jLabel18.setSize(new java.awt.Dimension(148, 20));
        jLabel18.setVisible(true);
        jLabel18.setText("Geolocation File");
        jLabel18.setLocation(new java.awt.Point(20, 515));

        geolocFileNameText.setSize(new java.awt.Dimension(240, 20));
        geolocFileNameText.setVisible(true);
        geolocFileNameText.setBackground(Color.white);
        geolocFileNameText.setBorder(BorderFactory.createLineBorder(Color.black));
        geolocFileNameText.setLocation(new java.awt.Point(20, 535));

        openGeolocFileButton.setSize(new java.awt.Dimension(170, 30));
        openGeolocFileButton.setVisible(true);
        openGeolocFileButton.setText("Open Geolocation File ...");
        openGeolocFileButton.setMnemonic('g');
        openGeolocFileButton.setToolTipText(
            "Opens the associated Geolocation file for the Input file");
        openGeolocFileButton.setLocation(new java.awt.Point(265, 525));

        /* define destination pane */
        destinationPane.setSize(new java.awt.Dimension(310, 390));
        destinationPane.setVisible(true);
        destinationPane.setLayout(null);
        destinationPane.setLocation(new java.awt.Point(440, 20));
        destinationPane.add(jLabel11);
        destinationPane.add(outputFormatCombo);
        destinationPane.add(jLabel13);
        destinationPane.add(resamplingCombo);
        destinationPane.add(jLabel14);
        destinationPane.add(projectionCombo);
        destinationPane.add(projectionParametersButton);
        destinationPane.add(dataTypeCombo);
        destinationPane.add(dataTypeLabel);
        destinationPane.add(pixelSizeLabel);
        destinationPane.add(pixelSizeText);
        destinationPane.add(pixelSizeUnitText);
        destinationPane.add(saveOutputFileButton);
        destinationPane.add(jLabel15);
        destinationPane.add(outputFileNameText);

        int x_start = 15;
        int y_start = 10;
        int lab_width = 290;
        int lab_height = 22;
        int com_width = 290;
        int com_height = 25;
        int seperation = 5;
        int y = y_start;
        saveOutputFileButton.setSize(new java.awt.Dimension(lab_width,
            com_height));
        saveOutputFileButton.setVisible(true);
        saveOutputFileButton.setText("Specify Output File ...");
        saveOutputFileButton.setMnemonic('p');
        saveOutputFileButton.setLocation(new java.awt.Point(x_start, y));

        jLabel15.setSize(new java.awt.Dimension(lab_width, lab_height));
        jLabel15.setVisible(true);
        jLabel15.setText("Output File");
        y += com_height + seperation;
        jLabel15.setLocation(new java.awt.Point(x_start, y));

        outputFileNameText.setSize(new java.awt.Dimension(com_width,
          com_height));
        outputFileNameText.setVisible(true);
        outputFileNameText.setBackground(Color.white);
        outputFileNameText.setBorder(BorderFactory.createLineBorder(
            Color.black));
        y += lab_height;
        outputFileNameText.setLocation(new java.awt.Point(x_start, y));

        jLabel11.setSize(new java.awt.Dimension(lab_width, lab_height));
        jLabel11.setVisible(true);
        jLabel11.setText("Output File Type");
        y += com_height + seperation;
        jLabel11.setLocation(new java.awt.Point(x_start, y));

        outputFormatCombo.setSize(new java.awt.Dimension(com_width,
            com_height));
        outputFormatCombo.setVisible(true);
        outputFormatCombo.setBorder(BorderFactory.createLineBorder(
            Color.lightGray));
        outputFormatCombo.setBackground(Color.lightGray);
        y += lab_height;
        outputFormatCombo.setLocation(new java.awt.Point(x_start,  y));

        jLabel13.setSize(new java.awt.Dimension(lab_width, lab_height));
        jLabel13.setVisible(true);
        jLabel13.setText("Resampling Type");
        y += com_height + seperation;
        jLabel13.setLocation(new java.awt.Point(x_start, y));

        resamplingCombo.setSize(new java.awt.Dimension(com_width, com_height));
        resamplingCombo.setVisible(true);
        resamplingCombo.setBackground(Color.lightGray);
        y += lab_height;
        resamplingCombo.setLocation(new java.awt.Point(x_start, y));

        jLabel14.setSize(new java.awt.Dimension(lab_width, lab_height));
        jLabel14.setVisible(true);
        jLabel14.setText("Output Projection Type");
        y += com_height + seperation;
        jLabel14.setLocation(new java.awt.Point(x_start, y));

        projectionCombo.setSize(new java.awt.Dimension(com_width, com_height));
        projectionCombo.setVisible(true);
        projectionCombo.setBackground(Color.lightGray);
        y += lab_height;
        projectionCombo.setLocation(new java.awt.Point(x_start, y));

        projectionParametersButton.setSize(new java.awt.Dimension(com_width,
            com_height));
        projectionParametersButton.setVisible(true);
        projectionParametersButton.setText("Edit Projection Parameters ...");
        projectionParametersButton.setMnemonic('e');
        y += com_height + seperation + 3;
        projectionParametersButton.setLocation(new java.awt.Point(x_start, y));

        dataTypeLabel.setSize(new java.awt.Dimension(lab_width, lab_height));
        dataTypeLabel.setVisible(true);
        dataTypeLabel.setText("Output Data Type");
        y += com_height + seperation;
        dataTypeLabel.setLocation(new java.awt.Point(x_start, y));

        dataTypeCombo.setSize(new java.awt.Dimension(com_width, com_height));
        dataTypeCombo.setVisible(true);
        dataTypeCombo.setBackground(Color.lightGray);
        y += lab_height;
        dataTypeCombo.setLocation(new java.awt.Point(x_start, y));

        pixelSizeLabel.setSize(new java.awt.Dimension(lab_width, lab_height));
        pixelSizeLabel.setVisible(true);
        pixelSizeLabel.setText("Output Pixel Size");
        y += com_height + seperation;
        pixelSizeLabel.setLocation(new java.awt.Point(x_start, y));

        pixelSizeText.setSize(new java.awt.Dimension(100, 22));
        pixelSizeText.setVisible(true);
        pixelSizeText.setBorder(BorderFactory.createLineBorder(Color.black));
        y += lab_height;
        pixelSizeText.setLocation(new java.awt.Point(x_start, y));

        pixelSizeUnitText.setSize(new java.awt.Dimension(55,22));
        pixelSizeUnitText.setLocation(new java.awt.Point(130,y));
        pixelSizeUnitText.setVisible(true);
        pixelSizeUnitText.setEnabled(true);
        pixelSizeUnitText.setEditable(false);

        jLabel8.setSize(new java.awt.Dimension(130, 20));
        jLabel8.setVisible(true);
        jLabel8.setText("Destination");
        jLabel8.setLocation(new java.awt.Point(450, 0));

        jLabel16.setSize(new java.awt.Dimension(120, 20));
        jLabel16.setVisible(true);
        jLabel16.setText("Commands");
        jLabel16.setLocation(new java.awt.Point(450, 417));

        /* define command pane */
        commandPane.setSize(new java.awt.Dimension(310, 153));
        commandPane.setVisible(true);
        commandPane.setLayout(null);
        commandPane.setLocation(new java.awt.Point(440,437));
        commandPane.add(runButton);
        commandPane.add(exitButton);
        commandPane.add(loadParametersButton);
        commandPane.add(saveParametersButton);
        commandPane.add(parameterFileNameLabel);
        commandPane.add(parameterFileNameText);

        loadParametersButton.setSize(new java.awt.Dimension(250, 23));
        loadParametersButton.setVisible(true);
        loadParametersButton.setText("Load Parameter File ...");
        loadParametersButton.setMnemonic('l');
        loadParametersButton.setLocation(new java.awt.Point(35, 10));

        saveParametersButton.setSize(new java.awt.Dimension(250, 23));
        saveParametersButton.setVisible(true);
        saveParametersButton.setText("Save Parameter File ...");
        saveParametersButton.setMnemonic('s');
        saveParametersButton.setLocation(new java.awt.Point(35, 42));

        parameterFileNameLabel.setSize(new java.awt.Dimension(250, 15));
        parameterFileNameLabel.setVisible(true);
        parameterFileNameLabel.setText("Parameter File");
        parameterFileNameLabel.setLocation(new java.awt.Point(35, 69));

        parameterFileNameText.setSize(new java.awt.Dimension(250, 23));
        parameterFileNameText.setVisible(true);
        parameterFileNameText.setLocation(new java.awt.Point(35, 84));
      
        runButton.setSize(new java.awt.Dimension(115, 23));
        runButton.setVisible(true);
        runButton.setText("Run");
        runButton.setMnemonic('r');
        runButton.setLocation(new java.awt.Point(35, 118));
      
        exitButton.setSize(new java.awt.Dimension(115, 23));
        exitButton.setVisible(true);
        exitButton.setText("Exit");
        exitButton.setMnemonic('x');
        exitButton.setLocation(new java.awt.Point(170, 118));
      
        setSize(new java.awt.Dimension(760,618));
        getContentPane().setLayout(null);

        setJMenuBar(menuBar);
        setTitle("mrtswath.ModisFrame");
        setLocation(new java.awt.Point(0, 0));
        getContentPane().add(inputPane);
        getContentPane().add(jLabel12);
        getContentPane().add(destinationPane);
        getContentPane().add(jLabel8);
        getContentPane().add(jLabel16);
        getContentPane().add(commandPane);

        /* set up event trigger (listner) for menu item "Open input file"*/
        jMenuFileOpenInputFile.addActionListener(
            new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                jMenuFileOpenInputFileActionPerformed(e);
            }
        });

        /* set up event trigger (listner) for menu item "Open geolocation file"*/
        jMenuFileOpenGeolocFile.addActionListener(
            new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                jMenuFileOpenGeolocFileActionPerformed(e);
            }
        });

        /* set up event trigger (listner) for menu item "Specify output file"*/
        jMenuFileSpecifyOutputFile.addActionListener(
            new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                jMenuFileSpecifyOutputFileActionPerformed(e);
            }
        });

        /* set up event trigger (listner) for menu item "Load Parameters" */
        jMenuFileLoadParameters.addActionListener(
            new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                jMenuFileLoadParametersActionPerformed(e);
            }
        });

        /* set up event trigger (listner) for menu item "SaveParameters" */
        jMenuFileSaveParameters.addActionListener(
            new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                jMenuFileSaveParametersActionPerformed(e);
            }
        });

        /* set up event trigger (listner) for menu item "Exit" */
        jMenuFileExit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                jMenuFileExitActionPerformed(e);
            }
        });

        /* set up event trigger (listner) for menu item "Run" */
        jMenuActionRun.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                jMenuActionRunActionPerformed(e);
            }
        });
         
        /* set up event trigger (listner) for menu item "About" in menu Help */
        jMenuHelpAbout.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                jMenuActionAboutActionPerformed(e);
            }
        });

        /* set up event trigger (listner)for start y text field */
        startYCoordLLText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                cornerTextFocusLost(e);
            }
        });

        /* set up event trigger (listner)for start x text field */
        startXCoordLLText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                cornerTextFocusLost(e);
            }
        });

        /* set up event trigger (listner)for end y text field */
        endYCoordLLText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                cornerTextFocusLost(e);
            }
        });

        /* set up event trigger (listner)for end x text field */
        endXCoordLLText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                cornerTextFocusLost(e);
            }
        });

        /* set up event trigger (listner)for start y text field */
        startYCoordLSText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                cornerTextFocusLost(e);
            }
        });

        /* set up event trigger (listner)for start x text field */
        startXCoordLSText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                cornerTextFocusLost(e);
            }
        });

        /* set up event trigger (listner)for end y text field */
        endYCoordLSText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                cornerTextFocusLost(e);
            }
        });

        /* set up event trigger (listner)for end x text field */
        endXCoordLSText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                cornerTextFocusLost(e);
            }
        });

        /* set up event trigger (listner)for start y text field */
        startYCoordXYText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                cornerTextFocusLost(e);
            }
        });

        /* set up event trigger (listner)for start x text field */
        startXCoordXYText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                cornerTextFocusLost(e);
            }
        });

        /* set up event trigger (listner)for end y text field */
        endYCoordXYText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                cornerTextFocusLost(e);
            }
        });

        /* set up event trigger (listner)for end x text field */
        endXCoordXYText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                cornerTextFocusLost(e);
            }
        });

        /* set up event trigger (listner)for input file name text field */
        inputFileNameText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                inputFileNameTextFocusLost(e);
            }
        });

        /* set up event trigger (listner)for "open input file button" */
        openInputFileButton.addActionListener(
            new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                openInputFileButtonActionPerformed(e);
            }
        });

        /* set up event trigger (listner)for geolocation file name text field */
        geolocFileNameText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                geolocFileNameTextFocusLost(e);
            }
        });

        /* set up event trigger (listner)for "open geolocation file button" */
        openGeolocFileButton.addActionListener(
            new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                openGeolocFileButtonActionPerformed(e);
            }
        });

        /* set up mouse event trigger (listner)for available bands list box */
        availableBandsList.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent e)
            {
                availableBandsListMousePressed(e);
            }
        });

        /* set up key event trigger (listner) for available bands list box */
        availableBandsList.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent e)
            {
                availableBandsListKeyPressed(e);
            }
        });

        /* set up mouse event trigger (listner)for selected bands list box */
        selectedBandsList.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent e)
            {
                selectedBandsListMousePressed(e);
            }
        });

        /* set up key event trigger (listner) for selected bands list box */
        selectedBandsList.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent e)
            {
                selectedBandsListKeyPressed(e);
            }
        });

        /* set up event trigger (listner)for the add band button */
        addBandButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                addBandButtonActionPerformed(e);
            }
        });

        /* set up event trigger (listner) for the remove band button */
        removeBandButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                removeBandButtonActionPerformed(e);
            }
        });

        /* set up event trigger (listner) for the spacial subset combo */
        spatialSubsetCombo.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent e)
            {
                spatialSubsetComboItemStateChanged(e);
            }
        });

        /* set up event trigger (listner)for the view metadata button */
        viewMetadataButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                viewMetadataButtonActionPerformed(e);
            }
        });

        /* set up event trigger (listner)for output format combo */
        outputFormatCombo.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent e)
            {
                outputFormatComboItemStateChanged(e);
            }
        });

        /* set up event trigger (listner)for resampling combo */
        resamplingCombo.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent e)
            {
                resamplingComboItemStateChanged(e);
            }
        });

        /* set up event trigger (listner)for projection combo */
        projectionCombo.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent e)
            {
                projectionComboItemStateChanged(e);
            }
        });

        /* set up event trigger (listner)for data type combo */
        dataTypeCombo.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent e)
            {
                dataTypeComboItemStateChanged(e);
            }
        });

        /* set up event trigger (listner)for projection parameters button */
        projectionParametersButton.addActionListener(
            new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                projectionParametersButtonActionPerformed(e);
            }
        });
         
        /* set up event trigger (listner)for save output file button */
        saveOutputFileButton.addActionListener(
            new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                saveOutputFileButtonActionPerformed(e);
            }
        });
        
        /* set up event trigger (listner) for the output file name text box */
        outputFileNameText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent e)
            {
                outputFileNameTextFocusLost(e);
            }
        });

        /* set up event trigger (listner) for the run button */
        runButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                runButtonActionPerformed(e);
            }
        });
         
        /* set up event trigger (listner) for the run button */
        exitButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                jMenuFileExitActionPerformed(e);
            }
        });
      
        /* set up event trigger (listner)for the load parmeters button */
        loadParametersButton.addActionListener(
            new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                loadParametersButtonActionPerformed(e);
            }
        });

        /* set up event trigger (listner) for the save parameters button */
        saveParametersButton.addActionListener(
            new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                saveParametersButtonActionPerformed(e);
            }
        });

        /* set up event trigger (listner)for the window */
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent e)
            {
                thisWindowClosing(e);
            }
        });
        /* END GENERATED CODE */

        inputPane.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        destinationPane.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        commandPane.setBorder(new EtchedBorder(EtchedBorder.RAISED));

        /* populate combos and no default item selected in combo except for the
           resampling combo box where the default value is set to nearest
           neighbor */
        populateCombo(outputFormatCombo,model.getOutputFormats());
        outputFormatCombo.setSelectedIndex(-1);
        populateCombo(projectionCombo,model.getProjectionTypes());
        projectionCombo.setSelectedIndex(-1);
        populateCombo(resamplingCombo,model.getResamplingTypes());
        resamplingCombo.setSelectedIndex(1);  // default set to NN
        populateCombo(dataTypeCombo,model.getDataTypes());
        dataTypeCombo.setSelectedIndex(0);  // default set to Input Data Type
        populateCombo(spatialSubsetCombo,model.getSpatialSubsets());
        spatialSubsetCombo.setSelectedIndex(-1);
        pixelSizeUnitText.setText("");

        /* set up event trigger (listner) for input file name text box */
        inputFileNameText.getDocument().addDocumentListener(
            new DocumentListener()
        {
            public void changedUpdate(DocumentEvent e)
            {
                inputFileNameTextActivity();
            }

            public void removeUpdate(DocumentEvent e)
            {
                inputFileNameTextActivity();
            }

            public void insertUpdate(DocumentEvent e)
            {
                inputFileNameTextActivity();
            }
        });

        /* set up event trigger (listner) for geoloc file name text box */
        geolocFileNameText.getDocument().addDocumentListener(
            new DocumentListener()
        {
            public void changedUpdate(DocumentEvent e)
            {
                geolocFileNameTextActivity();
            }

            public void removeUpdate(DocumentEvent e)
            {
                geolocFileNameTextActivity();
            }

            public void insertUpdate(DocumentEvent e)
            {
                geolocFileNameTextActivity();
            }
        });

        /* set up event trigger (listner) for output file name text box */
        outputFileNameText.getDocument().addDocumentListener(
            new DocumentListener()
        {
            public void changedUpdate(DocumentEvent e)
            {
                outputFileNameTextActivity();
            }
            public void removeUpdate(DocumentEvent e)
            {
                outputFileNameTextActivity();
            }
            public void insertUpdate(DocumentEvent e)
            {
                outputFileNameTextActivity();
            }
        });

        /* set up event trigger (listner)for pixel size text box */
        pixelSizeText.getDocument().addDocumentListener(new DocumentListener()
        {
            public void changedUpdate(DocumentEvent e)
            {
                pixelSizeTextActivity();
            }
            public void removeUpdate(DocumentEvent e)
            {
                pixelSizeTextActivity();
            }
            public void insertUpdate(DocumentEvent e)
            {
                pixelSizeTextActivity();
            }
        });

        DocumentListener cornerPointListener = new DocumentListener()
        {
            public void changedUpdate(DocumentEvent e)
            {
                setCornerPoints();
            }
            public void removeUpdate(DocumentEvent e)
            {
                setCornerPoints();
            }
            public void insertUpdate(DocumentEvent e)
            {
                setCornerPoints();
            }
         };

         /* set up event trigger (listner)*/
         startYCoordLLText.getDocument().addDocumentListener(
         cornerPointListener);
         /* set up event trigger (listner) */
         startXCoordLLText.getDocument().addDocumentListener(
         cornerPointListener);
         /* set up event trigger (listner)*/
         endYCoordLLText.getDocument().addDocumentListener(
         cornerPointListener);
         /* set up event trigger (listner)*/
         endXCoordLLText.getDocument().addDocumentListener(
         cornerPointListener);
         /* set up event trigger (listner)*/
         startYCoordLSText.getDocument().addDocumentListener(
         cornerPointListener);
         /* set up event trigger (listner)*/
         startXCoordLSText.getDocument().addDocumentListener(
         cornerPointListener);
         /* set up event trigger (listner)*/
         endYCoordLSText.getDocument().addDocumentListener(
         cornerPointListener);
         /* set up event trigger (listner)*/
         endXCoordLSText.getDocument().addDocumentListener(
         cornerPointListener);
         startYCoordXYText.getDocument().addDocumentListener(
         cornerPointListener);
         /* set up event trigger (listner)*/
         startXCoordXYText.getDocument().addDocumentListener(
         cornerPointListener);
         /* set up event trigger (listner)*/
         endYCoordXYText.getDocument().addDocumentListener(
         cornerPointListener);
         /* set up event trigger (listner)*/
         endXCoordXYText.getDocument().addDocumentListener(
         cornerPointListener);

         statusDialog = new StatusDialog(); // Class extends Frame
         statusDialog.initialize();
         setTitle("ModisSwathTool");
    }

/******************************************************************************
    NAME:     addNotify
    PURPOSE:  To add notify for event purpose
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void addNotify()
    {
        super.addNotify();
        if (mShown) return;

        // resize frame to account for menubar
        JMenuBar jMenuBar = getJMenuBar();
        if (jMenuBar != null) {
            int jMenuBarHeight = jMenuBar.getPreferredSize().height;
            Dimension dimension = getSize();
            dimension.height += jMenuBarHeight;
            setSize(dimension);
        }
        mShown = true;
    }

/******************************************************************************
    NAME:     thisWindowClosing
    PURPOSE:  Close the window when the close box is clicked
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    void thisWindowClosing(java.awt.event.WindowEvent e)
    {
        controller.exitFrame();
    }

/******************************************************************************
    NAME:               spatialSubsetComboItemStateChanged
    PURPOSE:  To monitor the change of spatial Subset Combo Item State
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void spatialSubsetComboItemStateChanged(java.awt.event.ItemEvent e)
    {
        Object item = spatialSubsetCombo.getSelectedItem();
        if (item != null)
        {
            ModisModel.TextIdPair tip = (ModisModel.TextIdPair)item;
            int subsetId = tip.getId();

            if ( subsetId == ModisModel.SPACE_LAT_LON )
            {
                model.setSpatialSubsetType(1);
                yCoordLabel.setText("Latitude");
                xCoordLabel.setText("Longitude");

                startXCoordLLText.setVisible(true);
                startYCoordLLText.setVisible(true);
                endXCoordLLText.setVisible(true);
                endYCoordLLText.setVisible(true);

                startXCoordLSText.setVisible(false);
                startYCoordLSText.setVisible(false);
                endXCoordLSText.setVisible(false);
                endYCoordLSText.setVisible(false);

                startXCoordXYText.setVisible(false);
                startYCoordXYText.setVisible(false);
                endXCoordXYText.setVisible(false);
                endYCoordXYText.setVisible(false);
            }
            else if (subsetId == ModisModel.SPACE_LINE_SAMPLE)
            {
                model.setSpatialSubsetType(2);
                yCoordLabel.setText("Line");
                xCoordLabel.setText("Sample");

                startXCoordLSText.setVisible(true);
                startYCoordLSText.setVisible(true);
                endXCoordLSText.setVisible(true);
                endYCoordLSText.setVisible(true);

                startXCoordLLText.setVisible(false);
                startYCoordLLText.setVisible(false);
                endXCoordLLText.setVisible(false);
                endYCoordLLText.setVisible(false);

                startXCoordXYText.setVisible(false);
                startYCoordXYText.setVisible(false);
                endXCoordXYText.setVisible(false);
                endYCoordXYText.setVisible(false);
            }
            else if (subsetId == ModisModel.SPACE_PROJ_XY)
            {
                model.setSpatialSubsetType(3);
                yCoordLabel.setText("Proj. X");
                xCoordLabel.setText("Proj. Y");

                startXCoordXYText.setVisible(true);
                startYCoordXYText.setVisible(true);
                endXCoordXYText.setVisible(true);
                endYCoordXYText.setVisible(true);

                startXCoordLSText.setVisible(false);
                startYCoordLSText.setVisible(false);
                endXCoordLSText.setVisible(false);
                endYCoordLSText.setVisible(false);

                startXCoordLLText.setVisible(false);
                startYCoordLLText.setVisible(false);
                endXCoordLLText.setVisible(false);
                endYCoordLLText.setVisible(false);
            }
            model.setSpatialSubsetType(tip.getId());
        }
        setCornerPoints();
    }

/******************************************************************************
    NAME:     resamplingComboItemStateChanged
    PURPOSE:  To monitor the change of resampling Combo Item State

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void resamplingComboItemStateChanged(java.awt.event.ItemEvent e)
    {
        if (resamplingCombo.getSelectedIndex() >= 0)
        {
            final ModisModel.TextIdPair item =
                (ModisModel.TextIdPair)resamplingCombo.getSelectedItem();
            model.setResamplingType(item.getId());
            prevResamplingType = resamplingCombo.getSelectedIndex();
        }
        setSaveRunEnablements();
    }

/******************************************************************************
    NAME:               inputFileNameTextFocusLost
    PURPOSE:

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void inputFileNameTextFocusLost(java.awt.event.FocusEvent e)
    {
    }

/******************************************************************************
    NAME:               geolocFileNameTextFocusLost
    PURPOSE:

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void geolocFileNameTextFocusLost(java.awt.event.FocusEvent e)
    {
    }

/******************************************************************************
    NAME:               cornerTextFocusLost
    PURPOSE:

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void cornerTextFocusLost(java.awt.event.FocusEvent e)
    {
    }

/******************************************************************************
    NAME:     openInputFileButtonActionPerformed
    PURPOSE:  To monitor open Input File Button to see if there is an
              action performed.
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void openInputFileButtonActionPerformed(java.awt.event.ActionEvent e)
    {
        String s = browseInputFiles();
        if ( s != null && s.length() > 0 )
        {
            bLoadHdrFile = true;
            startLoadParameterFile = false;
            startLoadHeaderFile = true;
            startLoadFirstHeaderFile++;
            controller.loadFile(s);
        }
        setSaveRunEnablements();
    }

/******************************************************************************
    NAME:     openGeolocFileButtonActionPerformed
    PURPOSE:  To monitor open Geolocation File Button to see if there is an
              action performed.
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void openGeolocFileButtonActionPerformed(java.awt.event.ActionEvent e)
    {
        String s = browseGeolocFiles();
        if ( s != null && s.length() > 0 )
        {
            projectionParametersButton.setEnabled(true);
            outputFormatCombo.setEnabled(true);
            resamplingCombo.setEnabled(true);
            projectionCombo.setEnabled(true);
            dataTypeCombo.setEnabled(true);
        }

        setSaveRunEnablements();
    }

/******************************************************************************
    NAME:     addBandButtonActionPerformed
    PURPOSE:  To monitor the add band button to see if there is an action
              performed.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void addBandButtonActionPerformed(java.awt.event.ActionEvent e)
    {
        final int[] index = availableBandsList.getSelectedIndices();
        final int numSelected = index.length;
        final ChooserListModel availableLm = (ChooserListModel)
        availableBandsList.getModel();
        final ChooserListModel selectedLm = (ChooserListModel)
        selectedBandsList.getModel();
    
        for (int i= 0; i < numSelected; i++)
        {
            final Object selectedElement = availableLm.getElementAt(index[i]-i);
            availableLm.removeElementAt(index[i]-i);
            selectedLm.insertElementAt (selectedElement,
                getInsertPosition(selectedLm, selectedElement));
        }

        if (selectedLm.getSize() > 0)
        {
            removeBandButton.setEnabled(true);
        }

        if (availableLm.getSize() == 0 )
        {
            addBandButton.setEnabled(false);
        }
        selectedBandCount.setText(Integer.toString(
            selectedBandsList.getModel().getSize()));
        availableBandCount.setText(Integer.toString(
            availableBandsList.getModel().getSize()));

        Vector selected = new Vector();
        for (int i=0; i < selectedLm.getSize(); i++)
        {
            selected.addElement(selectedLm.getElementAt(i));
        }
        model.setSelectedBands(selected.elements());
        setSaveRunEnablements();
    }

/******************************************************************************
    NAME:     removeBandButtonActionPerformed
    PURPOSE:  To monitor remove band button to see if there is action performed

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void removeBandButtonActionPerformed(java.awt.event.ActionEvent e)
    {
        final int[] index = selectedBandsList.getSelectedIndices();
        final int numSelected = index.length;
        final ChooserListModel availableLm =
            (ChooserListModel) availableBandsList.getModel();
        final ChooserListModel selectedLm  =
            (ChooserListModel) selectedBandsList.getModel();
     
        for (int i= 0; i < numSelected; i++)
        {
            final Object selectedElement = selectedLm.getElementAt(index[i]-i);
            selectedLm.removeElementAt(index[i]-i);

            /* keep the same order as in the header file */
            availableLm.insertElementAt (selectedElement,
                getInsertPosition(availableLm, selectedElement));
        }

        if (availableLm.getSize() > 0) addBandButton.setEnabled(true);
        if (selectedLm.getSize() == 0 ) removeBandButton.setEnabled(false);

        selectedBandCount.setText(Integer.toString(selectedLm.getSize()));
        availableBandCount.setText(Integer.toString(availableLm.getSize()));

        Vector selected = new Vector();
        for (int i=0; i < selectedLm.getSize(); ++i)
        {
            selected.addElement(selectedLm.getElementAt(i));
        }
        model.setSelectedBands(selected.elements());
        setSaveRunEnablements();
    }

/******************************************************************************
    NAME:     getInsertPosition
    PURPOSE:  To find out the position for insert list item.

    RETURN VALUE:  integer
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private int getInsertPosition (ChooserListModel lm, Object selectedElement)
    {
        int selectedElementIndex = model.getBandNum(selectedElement.toString());
        int [] index = new int [lm.getSize()];

        for (int i = 0; i < lm.getSize(); i++)
        {
            index[i] = model.getBandNum ((lm.getElementAt(i)).toString());
        }

        for (int i = 0; i < lm.getSize(); i++)
        {
            if (selectedElementIndex < index[i])
                return i;
        }

        return lm.getSize();
    }

/******************************************************************************
    NAME:     outputFormatComboItemStateChanged
    PURPOSE:  To monitor the state change of output format combo item

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void outputFormatComboItemStateChanged(java.awt.event.ItemEvent e)
    {
        if ( outputFormatCombo.getSelectedIndex() >= 0 )
        {
            ModisModel.TextIdPair item =
                (ModisModel.TextIdPair)outputFormatCombo.getSelectedItem();
            int format = item.getId();
            model.setOutputFormat(format);         
            prevOutputFileType = outputFormatCombo.getSelectedIndex();
            setOutputFileNameExtension();
            setSaveRunEnablements();
        }
    }

/******************************************************************************
    NAME:     projectionComboItemStateChanged
    PURPOSE:  To monitor the state change of projection combo item
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void projectionComboItemStateChanged(java.awt.event.ItemEvent e)
    {
        if ( projectionCombo.getSelectedIndex() >= 0 )
        {
            ModisModel.TextIdPair item = (ModisModel.TextIdPair)
                projectionCombo.getSelectedItem();
            if (model.getOutputProjectionType() != item.getId())
            {
                model.setOutputProjectionType(item.getId());
                prevOutputProjType = projectionCombo.getSelectedIndex();
                for (int i = 0; i < 15; i++)
                    model.setOutputProjectionParameter(i, 0.0);
                model.setUTMZone(0);
            }
         
            /* set the default units (GEO: DEGREES, others: METERS) for
             * the output pixel size */
            String str = model.getOutputProjectionTypeString();
            if (str.equals("GEO"))
                pixelSizeUnitText.setText("degrees");
            else
                pixelSizeUnitText.setText("meters");
        }

        setSaveRunEnablements();
    }

/******************************************************************************
    NAME:     dataTypeComboItemStateChanged
    PURPOSE:  To monitor the state change of data type combo item
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void dataTypeComboItemStateChanged(java.awt.event.ItemEvent e)
    {
        if ( dataTypeCombo.getSelectedIndex() >= 0 )
        {
            ModisModel.TextIdPair item = (ModisModel.TextIdPair)
                dataTypeCombo.getSelectedItem();
            if (model.getOutputProjectionType() != item.getId())
            {
                model.setOutputDataType(item.getId());
                prevDataType = dataTypeCombo.getSelectedIndex();
            }
        }

      setSaveRunEnablements();
    }

/******************************************************************************
    NAME:     projectionParametersButtonActionPerformed
    PURPOSE:  To monitor the action of projection Parameters Button and
              process it accordingly.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void projectionParametersButtonActionPerformed(
        java.awt.event.ActionEvent e)
    {
        ProjParamDialog dialog = new ProjParamDialog(this);
        try
        {
            dialog.initComponents();
            ModisModel.TextIdPair item =
                (ModisModel.TextIdPair)projectionCombo.getSelectedItem();
            dialog.setupFields(item.getId());
        }
        catch (Exception exception) {}

        dialog.setVisible(true);
        dialog.getResult();
        dialog.dispose();
    }

/******************************************************************************
    NAME:     saveOutputFileButtonActionPerformed
    PURPOSE:  To monitor the action of save output file button and process it
              accordingly.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void saveOutputFileButtonActionPerformed(
        java.awt.event.ActionEvent e)
    {
        String s = browseOutputFiles();
        s = s.toLowerCase();
        {
            int outputType;
            if (s.endsWith(".tif"))
            {
                outputType = ModisModel.GEOTIFF;
            }
            else if (s.endsWith(".hdf"))
            {
                outputType = ModisModel.HDFEOS;
            }
            else
            {
                outputType = ModisModel.MULTIFILE;
            }
            ComboBoxModel cbm = outputFormatCombo.getModel();

            for (int i=0; i<cbm.getSize(); i++)
            {
                ModisModel.TextIdPair tip = (ModisModel.TextIdPair)
                    cbm.getElementAt(i);
                if (tip.getId() == outputType)
                {
                    outputFormatCombo.setSelectedIndex(i);
                    break;
                }
            }
        }

        setSaveRunEnablements();
    }

/******************************************************************************
    NAME:               outputFileNameTextFocusLost
    PURPOSE:

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void outputFileNameTextFocusLost(java.awt.event.FocusEvent e)
    {
    }

/******************************************************************************
    NAME:     runButtonActionPerformed
    PURPOSE:  To monitor if there is run button pressed and process it
              accordingly.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void runButtonActionPerformed(java.awt.event.ActionEvent e)
    {
        if((pixelSizeText.getText()).startsWith("-"))
        {
            /* show a message box */       
            JOptionPane.showMessageDialog(this,
                "Invalid input. Pixel size must be a positive number " +
                "greater than zero!\nPlease try again.");
            pixelSizeText.setText("");
            return;
        }

        for (int i = 0; i < (pixelSizeText.getText()).length(); i++)
        {
            char c = (pixelSizeText.getText()).charAt(i);
            if (!Character.isDigit(c) && c != '.')
            {
                /* show a message box */
                JOptionPane.showMessageDialog(this,"Invalid input. Pixel " +
                    "size must be a number!\nPlease try again.");
                pixelSizeText.setText("");
                return;
            }
        }
 
        String prevParameterFile = model.getParameterFilename();
        model.setParameterFilename("TmpParam.prm");
        File outputFileExist = new File (model.getOutputFilename());
        JOptionPane outputOverwrite = new JOptionPane(
            "The selected output file already exists. Overwrite?",
            JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (outputFileExist.exists())
        {
            JDialog dialog = outputOverwrite.createDialog(this,"Warning");
            dialog.setVisible(true);

            int optionValue = ((Integer) outputOverwrite.getValue()).intValue();
            if (optionValue == JOptionPane.YES_OPTION)
            {
                /* if user choses to overwite the output file */
                controller.writeParameterFile();
                model.setParameterFilename(prevParameterFile);
                controller.runResample();
            }
            else if (optionValue == JOptionPane.NO_OPTION)
            {
                /* promt user to input another output filename */
                JOptionPane.showMessageDialog(this,
                    "Select another output file, please...");
                outputFileNameText.setText("");
                runButton.setEnabled(false);
                jMenuActionRun.setEnabled(false);
                saveParametersButton.setEnabled(false);
            }
        }
        else
        {
            controller.writeParameterFile();
            model.setParameterFilename(prevParameterFile);
            controller.runResample();
        }
    }

/******************************************************************************
    NAME:     loadParametersButtonActionPerformed
    PURPOSE:  To monitor if there is load parameter button pressed and
              process it accordingly.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void loadParametersButtonActionPerformed(
        java.awt.event.ActionEvent e)
    {
        String s = browseParameterFiles(true);
        if ( s != null && s.length() > 0 )
        {
            bLoadHdrFile = false;
            startLoadParameterFile = true;
            startLoadHeaderFile = false;
            outputFileNameText.setText("");
            pixelSizeText.setText("");
            controller.loadFile(s);
            parameterFileNameText.setText(model.getParameterFilename());
        }
    }

/******************************************************************************
    NAME:     saveParametersButtonActionPerformed
    PURPOSE:  To monitor if there is save parameter button pressed and
              process it accordingly.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void saveParametersButtonActionPerformed(
        java.awt.event.ActionEvent e)
    {
        if((pixelSizeText.getText()).startsWith("-"))
        {
            /* show a message box */       
            JOptionPane.showMessageDialog(this,
                "Invalid input.\nPixel size must be a  positive number " +
                "greater than zero!\nPlease try again.");
            pixelSizeText.setText("");
            return;
        }
          
        String s = browseParameterFiles(false);
        s = s.toLowerCase();
        if ( s != null && s.length() > 0 )
        {
            String sizeText = pixelSizeText.getText();
            model.setOutputPixelSize (sizeText);
            controller.writeParameterFile();
            parameterFileNameText.setText(model.getParameterFilename());
        }
    }

/******************************************************************************
    NAME:     jMenuFileOpenInputFileActionPerformed
    PURPOSE:  To monitor if there is Menu File Open InputFile button pressed
              and delegate it accordingly.
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void jMenuFileOpenInputFileActionPerformed(
        java.awt.event.ActionEvent e)
    {
        openInputFileButtonActionPerformed(e);
    }

/******************************************************************************
    NAME:     jMenuFileOpenGeolocFileActionPerformed
    PURPOSE:  To monitor if there is Menu File Open GeolocFile button pressed
              and delegate it accordingly.
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void jMenuFileOpenGeolocFileActionPerformed(
        java.awt.event.ActionEvent e)
    {
        openGeolocFileButtonActionPerformed(e);
    }

/******************************************************************************
    NAME:               jMenuFileSpecifyOutputFileActionPerformed
    PURPOSE:

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
******************************************************************************/
    public void jMenuFileSpecifyOutputFileActionPerformed(
        java.awt.event.ActionEvent e)
    {
        saveOutputFileButtonActionPerformed(e);
    }

/******************************************************************************
    NAME:     jMenuFileLoadParametersActionPerformed
    PURPOSE:  To monitor if there is Menu File Load Parameters button pressed
              and process it accordingly.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void jMenuFileLoadParametersActionPerformed(
        java.awt.event.ActionEvent e)
    {
        String s = browseParameterFiles(true);
        if ( s != null && s.length() > 0 )
        {
            controller.loadFile(s);
            parameterFileNameText.setText(model.getParameterFilename());
        }
    }

/******************************************************************************
    NAME:     jMenuFileSaveParametersActionPerformed
    PURPOSE:  To monitor if the file save menu button is pressed and map it to
              the appropriate handler function.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void jMenuFileSaveParametersActionPerformed(
        java.awt.event.ActionEvent e)
    {
        saveParametersButtonActionPerformed(e);
    }
   
/******************************************************************************
    NAME:     jMenuFileExitActionPerformed
    PURPOSE:  To monitor if the file exit menu button is pressed and map it to
              the appropriate handler function.
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void jMenuFileExitActionPerformed(java.awt.event.ActionEvent e)
    {
        controller.exitFrame();
    }

/******************************************************************************
    NAME:     jMenuActionRunActionPerformed
    PURPOSE:  To monitor if the run menu button is pressed and map it to 
              appropriate handler function.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void jMenuActionRunActionPerformed(java.awt.event.ActionEvent e)
    {
        runButtonActionPerformed(e);
    }

/******************************************************************************
    NAME:     jMenuActionAboutActionPerformed
    PURPOSE:  To display a message box for About and version info.
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void jMenuActionAboutActionPerformed(java.awt.event.ActionEvent e)
    {
        JOptionPane.showMessageDialog(this,"MODIS Swath Reprojection Tool\n" +
            "Version 2.2\nSeptember 2010");
    }

    public void selectedBandsListKeyPressed(java.awt.event.KeyEvent e)
    {
    }

/******************************************************************************
    NAME:     selectedBandsListMouseClicked
    PURPOSE:  To monitor if there is selected Bands List Mouse Click and
              process it accordingly.
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void selectedBandsListMouseClicked(java.awt.event.MouseEvent e)
    {
        int index = selectedBandsList.getSelectedIndex();
        if ( index < 0 )
        {
            removeBandButton.setEnabled(false);
        }
        else
        {
            removeBandButton.setEnabled(true);
        }
    }

/******************************************************************************
    NAME:     availableBandsListKeyPressed
    PURPOSE:  To monitor if there is available Bands List Mouse Clicked and
              process it accordingly.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void availableBandsListKeyPressed(java.awt.event.KeyEvent e)
    {
        int index = availableBandsList.getSelectedIndex();
        if ( index < 0 )
        {
            addBandButton.setEnabled(false);
        }
        else
        {
            addBandButton.setEnabled(true);
        }
    }

/******************************************************************************
    NAME:     selectedBandsListMousePressed
    PURPOSE:  To monitor if there is selected Bands List Mouse Pressed and
              process it accordingly.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void availableBandsListMousePressed(java.awt.event.MouseEvent e)
    {
        int index = availableBandsList.getSelectedIndex();
        if ( index < 0 )
        {
            addBandButton.setEnabled(false);
        }
        else
        {
            addBandButton.setEnabled(true);
        }
    }

/******************************************************************************
    NAME:     selectedBandsListMousePressed
    PURPOSE:  To monitor if there is save parameter button pressed and process
              it accordingly.
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void selectedBandsListMousePressed(java.awt.event.MouseEvent e)
    {
        int index = selectedBandsList.getSelectedIndex();
        if ( index < 0 )
        {
            removeBandButton.setEnabled(false);
        }
        else
        {
            removeBandButton.setEnabled(true);
        }
    }

/******************************************************************************
    NAME:     viewMetadataButtonActionPerformed
    PURPOSE:  To monitor if there is view Metadata  button pressed and process
              it accordingly.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void viewMetadataButtonActionPerformed(java.awt.event.ActionEvent e)
    {
        String inFilename = inputFileNameText.getText();

        if (inFilename.endsWith(".hdf"))
        {
            String metFile = controller.runDump(inFilename);
            MetadataDialog md = new MetadataDialog(metFile.toString(),
                false);
            md.initialize();
            if (md.isFileRead())
            {
                md.setVisible(true);
            }
        }
        else 
        {
            JOptionPane.showMessageDialog(this,"Invalid Input file type");
        }
    }
}
