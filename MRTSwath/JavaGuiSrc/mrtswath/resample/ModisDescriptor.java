/******************************************************************************
    NAME:     ModisDescriptor.class
    PURPOSE:  The purpose of this class is to create a data container to
              contain all of information necessary for resampler to execute
              properly. Most of functions are invoked by ModisModel class.
              It attempts to create an image of several layers to isolate the
              data model from view.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    Java is inherently platform indepentent so the compiled byte code can be
    executed on any of platforms (e.g. Windows, Unix, and Linux). Virtually,
    there is no limitation on running Java byte codes. However there is
    compiler requirement regarding JDK package (version 2.0+).

    PROJECT:  MRTSwath
    NOTES:
    1. This class contains many dispatching functions in response to invocation
       from ModisModel.class and offers an appearance of a delayed processing.

******************************************************************************/
package mrtswath.resample;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ModisDescriptor
{
    /*
     * FileOpenType
     */
    final public static int BAD_FILE_MODE = 0;
    final public static int FILE_WRITE_MODE = 1;
    final public static int FILE_READ_MODE = 2;

    /*
     * FileType
     */
    final public static int BAD_FILE_TYPE = 0;
    final public static int MULTIFILE = 1;
    final public static int HDFEOS = 2;
    final public static int GEOTIFF = 3;

    /*
     * DataType
     */
    final public static int BAD_DATA_TYPE = 0;
    final public static int ASCII = 1;
    final public static int INT8 = 2;
    final public static int UINT8 = 3;
    final public static int INT16 = 4;
    final public static int UINT16 = 5;
    final public static int INT32 = 6;
    final public static int UINT32 = 7;
    final public static int FLOAT32 = 8;

    /*
     * ProjectionType
     */
    final public static int BAD_PROJECTION_TYPE = 0;
    final public static int PROJ_GEO = 1;
    final public static int PROJ_HAM = 2;
    final public static int PROJ_IGH = 3;
    final public static int PROJ_ISIN = 4;
    final public static int PROJ_LA = 5;
    final public static int PROJ_LCC = 6;
    final public static int PROJ_MOL = 7;
    final public static int PROJ_PS = 8;
    final public static int PROJ_SIN = 9;
    final public static int PROJ_UTM = 10;
    final public static int PROJ_TM = 11;
    final public static int PROJ_AEA = 12;
    final public static int PROJ_MERC = 13;
    final public static int PROJ_ER = 14;

    /*
     * ResamplingType
     */
    final public static int BAD_RESAMPLING_TYPE = 0;
    final public static int NN = 1;
    final public static int BI = 2;
    final public static int CC = 3;

    /*
     * Output Data Type
     */
    final public static int DT_BAD_DATA_TYPE = 0;
    final public static int DT_SAME_AS_INPUT = 1;
    final public static int DT_CHAR8 = 2;
    final public static int DT_UINT8 = 3;
    final public static int DT_INT8 = 4;
    final public static int DT_UINT16 = 5;
    final public static int DT_INT16 = 6;

    /*
     * Output Pixel Size Units
     */
    final public static int BAD_OUTPUT_PIXEL_SIZE_UNIT  = 0;
    final public static int METERS = 1;
    final public static int FEET = 2;
    final public static int DEGREES = 3;
    final public static int ARC_SEC = 4;

    /*
     * CornerType
     */
    final public static int UL = 0;
    final public static int UR = 1;
    final public static int LL = 2;
    final public static int LR = 3;

    /*
     * Spatial Subset
     */
    final public static int BAD_SPATIAL_SUBSET_TYPE = 0;
    final public static int SPACE_LAT_LON = 1;
    final public static int SPACE_LINE_SAMPLE = 2;
    final public static int SPACE_PROJ_XY = 3;

    /* The name of the parameter (.prm) file, if provided. */
    private String parameterFilename;

    /* The name of the input file (.hdf), if provided. */
    private String inputFilename;

    /* The name of the geolocation file (.hdf), if provided. */
    private String geolocFilename;

    /* The name of the output parameter (.prm) file. */
    private String outputFilename;

    /* The pixel size to generate for the resampled image.  If null
       or empty String, then use input size. */
    private String outputPixelSize;
    
    /* The type of the ellipsoid selected from the combo box (default is
       WGS84). */
    private String Ellipsoid = "WGS 1984";
    
    /* The input ellipsoid */
    private String InputEllipsoid;

    /* The input file type (HDF-EOS) */
    private int inputFileType;

    /* The output file type (raw binary, HDF-EOS, GeoTiff) */
    private int outputFileType;

    /* The input data type  (byte, int, etc.) */
    private int inputDataType;

    /* The output data type  (byte, int, etc.) */
    private int outputDataType;

    /* number of bands in image */
    private int nbands;

    /* array[nbands] of false/true */
    private boolean[] selectedBands;

    /* array[nbands] of info about each band */
    private BandType[] bandinfo;

    /* overall image size (corner points - lat/long in decimal degrees) */
    private double[][] inputImageExtent = new double[4][2];

    /* spatial subset type (BAD_SPATIAL_SUBSET_TYPE, SPACE_LAT_LON,
       SPACE_LINE_SAMPLE, SPACE_PROJ_XY) */
    private int spatialsubsetType;

    /* Check whether ellipsoid exists in the file */
    private boolean EllipsoidOption;
   
    /* Check whether UTM zone exists in the file */
    private boolean UtmOption;

    /* spatial subset corner points (UL, UR, LL, LR in lat/long) */
    private double[][] cornerPoints = new double[4][2];
    private double[][] gringPoints = new double[4][2];
    private double[][] cornerXYPoints = new double[4][2];
    private boolean bGringExist1, bGringExist2, bGringExist3, bGringExist4;
    private boolean bCornerXYExist1, bCornerXYExist2, bCornerXYExist3,
        bCornerXYExist4;

    /* highest rez spatial subset corner points (UL, UR, LL, LR in row/col) */
    private int[][] inputCornerPoints = new int[4][2];

    /* Input projection type (ISIN, GEO, UTM, etc.) */
    private int inputProjectionType;

    /* Output projection type (ISIN, GEO, UTM, etc.) */
    private int outputProjectionType;

    /* UTM zone (-60, ..., 60) */
    private int utmZone = 0;
    private int InpututmZone = 0;

    /* Selected output pixel size unit  (METERS, FEET, DEGREES, ARC_SEC.) */
    private int outputPixelSizeUnit;

    /* resampling type (NN, BI, CC) */
    private int resamplingType;

    /* array of 15 projection parameters */
    private double[] inputProjectionParameters = new double[15];
    private double[] outputProjectionParameters = new double[15];

    private String[] inputBandNamesArray;
    private int[] inputNumOfLinesArray;
    private double[] inputPixelSizeArray;
    private int[] inputNumOfSamplesArray;
    private String[] inputDataTypeArray;
    private double[] inputMinValueArray;
    private double[] inputMaxValueArray;
    private double[] inputBackgroundFillArray;
    private String[] sULArray = new String[2];
    private String[] sLRArray = new String[2];

    /* Input ellipsoid code */
    private int inputEllipsoidCode;

    /* Output ellipsoid code */
    private int outputEllipsoidCode;

    /* Input zone code */
    private int inputZoneCode;

    /* Output zone code */
    private int outputZoneCode;

    /* Input projection info structure for Geolib */
    private ProjInfo inProjectionInfo;
    
    /* Output projection info structure for Geolib */
    private ProjInfo outProjectionInfo;

    /* file information for handling raw binary and multiple GeoTIFF outputs */
    private OutFileType outputFileInfo;

    /* the number of output files actually written */
    private int nfilesOut;

/******************************************************************************
    NAME:     ModisDescriptor
    PURPOSE:  Constructor--do nothing
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public ModisDescriptor()
    {
    }

/******************************************************************************
    NAME:     getOutputPixelSizeUnitString
    PURPOSE:  To look up output pixel size unit for given ID

    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private java.lang.String getOutputPixelSizeUnitString(int pixelSizeUnit)
    {
        switch (pixelSizeUnit)
        {
          case METERS:
             return "METERS";
          case FEET:
             return "FEET";
          case DEGREES:
             return "DEGREES";
          case ARC_SEC:
             return "ARC-SEC";
          default:
             return "BAD_OUTPUT_PIXEL_SIZE_UNIT";
        }
    }

/******************************************************************************
    NAME:     getMaximumLines
    PURPOSE:  To get maximum number of lines

    RETURN VALUE:  integer
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
   public int getMaximumLines()
   {
      int largest = 0;
      for (int i=0; i < getNumBands(); ++i)
      {
         if (inputNumOfLinesArray[i] > largest)
         {
            largest = inputNumOfLinesArray[i];
         }
      }
      return largest;
   }

/******************************************************************************
    NAME:     getMaximumSamples
    PURPOSE:  To get maximum number of samples

    RETURN VALUE:  integer
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
   public int getMaximumSamples()
   {
      int largest = 0;
      for (int i=0; i < getNumBands(); ++i)
      {
         if (inputNumOfSamplesArray[i] > largest)
         {
            largest = inputNumOfSamplesArray[i];
         }
      }
      return largest;
   }

/******************************************************************************
    NAME:     getProjectionTypeInt
    PURPOSE:  To look up projection type ID for given projection type string

    RETURN VALUE:  integer
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private int getProjectionTypeInt(String s)
        throws IllegalArgumentException
    {
        if ( s.equals("ISINUS"))
        {
            return (PROJ_ISIN);
        }
        else if (s.equals("ALBERS"))
        {
            return (PROJ_AEA);
        }
        else if (s.equals("EQRECT"))
        {
            return (PROJ_ER);
        }
        else if (s.equals("GEO"))
        {
            return (PROJ_GEO);
        }
        else if (s.equals("HAMMER"))
        {
            return (PROJ_HAM);
        }
        else if (s.equals("GOODE"))
        {
            return (PROJ_IGH);
        }
        else if (s.equals("LAMAZ"))
        {
            return (PROJ_LA);
        }
        else if (s.equals("MERCAT"))
        {
            return (PROJ_MERC);
        }
        else if (s.equals("MOLL"))
        {
            return (PROJ_MOL);
        }
        else if (s.equals("LAMCC"))
        {
            return (PROJ_LCC);
        }
        else if (s.equals("PS"))
        {
            return (PROJ_PS);
        }
        else if (s.equals("SNSOID"))
        {
            return (PROJ_SIN);
        }
        else if (s.equals("TM"))
        {
            return (PROJ_TM);
        }
        else if (s.equals("UTM"))
        {
            return (PROJ_UTM);
        }
        else
        {
            setInputProjectionType(BAD_PROJECTION_TYPE);
            throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     getProjectionTypeString
    PURPOSE:  To look up projection type string by given projection ID

    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public java.lang.String getProjectionTypeString(int projType)
    {
        switch (projType)
        {
            case PROJ_AEA: return "ALBERS";
            case PROJ_ER: return "EQRECT";
            case PROJ_GEO: return "GEO";
            case PROJ_HAM: return "HAMMER";
            case PROJ_IGH: return "GOODE";
            case PROJ_ISIN: return "ISINUS";
            case PROJ_LA: return "LAMAZ";
            case PROJ_LCC: return "LAMCC";
            case PROJ_MOL: return "MOLL";
            case PROJ_MERC: return "MERCAT";
            case PROJ_PS: return "PS";
            case PROJ_SIN: return "SNSOID";
            case PROJ_UTM: return "UTM";
            case PROJ_TM: return "TM";
            default: return "BAD_PROJECTION_TYPE";
        }
    }

/******************************************************************************
    NAME:     getSpatialSubsetType
    PURPOSE:  Get the currently active spatial subset type.

    RETURN VALUE:  SPACE_LAT_LON if lat/long
                   SPACE_LINE_SAMPLE if line/sample
                   SPACE_PROJ_XY if proj x/y
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getSpatialSubsetType()
    {
        return spatialsubsetType;
    }

/******************************************************************************
    NAME:     getSpatialSubsetTypeString
    PURPOSE:  To retrieve the spatial subset type string

    RETURN VALUE:   String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getSpatialSubsetTypeString(int ssType)
        throws IllegalArgumentException
    {
        switch (ssType)
        {
            case SPACE_LAT_LON:
                return "LAT_LONG";
            case SPACE_LINE_SAMPLE:
                return "LINE_SAMPLE";
            case SPACE_PROJ_XY:
                return "PROJ_COORDS";
            default:
                throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     read2dPoint
    PURPOSE:  To read 2 points from tokenstream

    RETURN VALUE:  String []
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private java.lang.String[] read2dPoint(java.io.StreamTokenizer tok,
        java.lang.String parmName, int errorId) throws ReadException
    {
        return read2dPoint(tok, parmName, errorId, false);
    }

/******************************************************************************
    NAME:     read2dPoint
    PURPOSE:  To actually read and parse 2 points (overloaded function)

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private java.lang.String[] read2dPoint(java.io.StreamTokenizer tok,
        java.lang.String parmName, int errorId, boolean optional)
        throws ReadException
    {
        String s;
        int tokenType;
        String sArray[] = new String[2];

        try
        {
            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if ( tokenType != StreamTokenizer.TT_WORD)
                throw new ReadException(errorId);
            if ( !s.equals(parmName) )
            {
                if ( !optional )
                {
                   throw new ReadException(errorId);
                }
                else
                {
                   tok.pushBack();
                   return null;
                }
            }

            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if ( tokenType != StreamTokenizer.TT_WORD || !s.equals("("))
                throw new ReadException(errorId);
            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if ( tokenType != StreamTokenizer.TT_WORD)
                throw new ReadException(errorId);
            sArray[0] = s;

            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if ( tokenType != StreamTokenizer.TT_WORD)
                throw new ReadException(errorId);
            sArray[1] = s;

            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if ( tokenType != StreamTokenizer.TT_WORD ||  !s.equals(")"))
                throw new ReadException(errorId);
        }
        catch (IOException e)
        {
            throw new ReadException(errorId);
        }

        return sArray;
    }

/******************************************************************************
    NAME:    read2dPoint
    PURPOSE: To invoke read2dPoint (an overloaded function)

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private java.lang.String[] read2dPoint(java.io.StreamTokenizer tok,
        int errorId) throws ReadException
    {
        return read2dPoint(tok, errorId, false);
    }

/******************************************************************************
    NAME:     read2dPoint
    PURPOSE:  To really read from StreamTokenizer (an overloaded function)

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private java.lang.String[] read2dPoint(java.io.StreamTokenizer tok,
        int errorId, boolean optional) throws ReadException
    {
        String s;
        int tokenType;
        String sArray[] = new String[2];

        try
        {
            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if ( tokenType != StreamTokenizer.TT_WORD ||  !s.equals("("))
                throw new ReadException(errorId);

            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if ( tokenType != StreamTokenizer.TT_WORD)
                throw new ReadException(errorId);
            sArray[0] = s;

            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if ( tokenType != StreamTokenizer.TT_WORD)
                throw new ReadException(errorId);
            sArray[1] = s;

            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if ( tokenType != StreamTokenizer.TT_WORD ||  !s.equals(")"))
            {
                throw new ReadException(errorId);
            }
        }
        catch (IOException e)
        {
            throw new ReadException(errorId);
        }

        return sArray;
    }

/******************************************************************************
    NAME:     read2dPointParam
    PURPOSE:  To really read from StreamTokenizer (an overloaded function)

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private java.lang.String[] read2dPointParam(java.io.StreamTokenizer tok,
        int errorId, boolean optional) throws ReadException
    {
        String s;
        int tokenType;
        String sArray[] = new String[2];

        try
        {
            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if (tokenType != StreamTokenizer.TT_WORD)
                throw new ReadException(errorId);
            sArray[0] = s;

            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if (tokenType != StreamTokenizer.TT_WORD)
                throw new ReadException(errorId);
            sArray[1] = s;
        }
        catch (IOException e)
        {
            throw new ReadException(errorId);
        }

        return sArray;
    }

/******************************************************************************
    NAME:     readPixelSizeAndUnit
    PURPOSE:  To read pixel size and unit

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private java.lang.String[] readPixelSizeAndUnit(
        java.io.StreamTokenizer tok, int errorId) throws ReadException
    {
        return readPixelSizeAndUnit(tok, errorId, false);
    }

/******************************************************************************
    NAME:     readPixelSizeAndUnit
    PURPOSE:  To read pixel size and unit from StreamTokenizer

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private java.lang.String[] readPixelSizeAndUnit(
        java.io.StreamTokenizer tok, int errorId, boolean optional)
        throws ReadException
    {
        String s;
        int tokenType;
        String sArray[] = new String[2];
        tok.eolIsSignificant(true);
        try
        {
            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if ( tokenType != StreamTokenizer.TT_WORD)
                throw new ReadException(errorId);
            sArray[0] = s;

            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if (tokenType != StreamTokenizer.TT_WORD )
            {
                if (tokenType == StreamTokenizer.TT_EOL )
                    sArray[1] = " ";
                else
                    throw new ReadException(errorId);
            }
            else
                sArray[1] = s;
        }
        catch (IOException e)
        {
            throw new ReadException(errorId);
        }
        tok.eolIsSignificant(false);
        return sArray;
    }

/******************************************************************************
    NAME:     readHdfHeader
    PURPOSE:  To read HDF file header and convert .hdf file to .hdr file by
              calling C-module hdf2hdr.exe

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void readHdfHeader()     throws ReadException
    {
        String hdfFilename = getInputFilename();

        File inputFile = new File (hdfFilename);
        if (!inputFile.exists())
        {
            throw new ReadException (hdfFilename,
	        ErrorHandler.ERROR_FILE_NOT_EXIST);
        }
        else
        {
            {
                final Runtime rt = Runtime.getRuntime();
                String hdrFilename = null;
                Process p;
                try
                {
                   String cmd [] = { "hdf2hdr", hdfFilename };
                   p = rt.exec(cmd);
                   InputStream is = p.getInputStream();
                   InputStreamReader isr = new InputStreamReader(is);
                   BufferedReader br = new BufferedReader(isr);
                   hdrFilename = br.readLine();
                }
                catch (java.io.IOException e)
                {
                    throw new ReadException (hdfFilename,
                        ErrorHandler.ERROR_EXE_FILE_NOT_EXIST);
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

                if (processReturn == 0)
                {
                    setInputFilename("TmpHdr.hdr");
                    readHeaderFile();

                    File tempFile= new File(hdrFilename);
                    tempFile.delete();

                    setInputFilename(hdfFilename);
                }
            }
        }
    }

/******************************************************************************
    NAME:     consumeComment
    PURPOSE:  Consume a comment--read all characters up to and including the
              end of line and discard them.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private int consumeComment(java.io.StreamTokenizer inputTok)
        throws IOException
    {
        int ttype=0;

        inputTok.eolIsSignificant(true);
        while (true)
        {
            ttype = inputTok.nextToken();
            if ( ttype == StreamTokenizer.TT_EOL ||
                 ttype == StreamTokenizer.TT_EOF )
            {
                break;
            }
        }
        inputTok.eolIsSignificant(false);
        return ttype;
   }

/******************************************************************************
    NAME:     parseDefaultFieldValue
    PURPOSE:  To parse the default field value
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private java.lang.String[] parseDefaultFieldValue (
        java.io.StreamTokenizer inputTok, int fieldSize, String defaultVal,
        int errorId) throws ReadException
    {
        String []fieldValueArray = new String [fieldSize];
        int tokenType;
        String s;
        int i = 0;
        boolean continue_processing = true;

        while (continue_processing)
        {
            try
            {
                tokenType = readNextNonCommentToken(inputTok);
                if ( tokenType != StreamTokenizer.TT_WORD )
                    throw new ReadException(errorId);
                s = inputTok.sval;
                if (tokenType == StreamTokenizer.TT_EOL ||
                    tokenType == StreamTokenizer.TT_EOF)
                    continue_processing = false;
                else if (i < fieldSize)
                {
                    fieldValueArray[i++] = s;
                    if (i == fieldSize)
                        continue_processing = false;
                }
            }
            catch (IOException e)
            {
                throw new ReadException(errorId);
            }
            catch (IllegalArgumentException e)
            {
                throw new ReadException(errorId);
            }
        }

        if (i < fieldSize)
        {
            for (int j = i; j < fieldSize; j++)
                fieldValueArray[j] = defaultVal;
        }

        return fieldValueArray;
    }

/******************************************************************************
    NAME:     parseSDSFieldValues
    PURPOSE:  To parse the input SDS field values
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
      1. SDS band names will be specified as SDSName followed by another
         SDSName or a list of bands.
         EV_500_RefSB, 0, 1, 1; EV_500_RefSB_Uncert_Indexes, 1, 1, 0; Latitude;
         Longitude
******************************************************************************/
    private void parseSDSFieldValues (
        java.io.StreamTokenizer inputTok, int fieldSize, int errorId)
        throws ReadException
    {
        int tokenType;
        int ival;
        int i, j;
        int nsds = 0;
        int ntotal_bands = 0;
        String s;
        String []input_bandnames = new String[fieldSize];
        String []sdslist = new String [fieldSize];
        String []bandnames = new String[fieldSize * 100];
        int nbands[] = new int [fieldSize];
        int sds_bands[][] = new int [fieldSize][100];
            /* no more than 100 bands per SDS */
        boolean process_sds = true;
        boolean process_bands = true;
        boolean band_found = true;

        /* loop through the string of SDSs and bands */
        inputTok.eolIsSignificant(true);
        while (process_sds)
        {
            /* get the next SDS name */
            try
            {
                tokenType = readNextNonCommentToken(inputTok);
            }
            catch (IOException e)
            {
                throw new ReadException(errorId);
            }
            catch (IllegalArgumentException e)
            {
                throw new ReadException(errorId);
            }

            if (tokenType == StreamTokenizer.TT_EOL ||
                tokenType == StreamTokenizer.TT_EOF)
            {
                /* We're done if at the end of line or end of file */
                break;
            }
            if (tokenType != StreamTokenizer.TT_WORD)
                throw new ReadException(errorId);
            sdslist[nsds] = inputTok.sval;

            /* initialize the band count for this SDS */
            nbands[nsds] = 0;

            /* get the next token, either a band value or SDS name */
            try
            {
                tokenType = readNextNonCommentToken(inputTok);
            }
            catch (IOException e)
            {
                throw new ReadException(errorId);
            }
            catch (IllegalArgumentException e)
            {
                throw new ReadException(errorId);
            }

            if (tokenType == StreamTokenizer.TT_EOL ||
                tokenType == StreamTokenizer.TT_EOF)
            {
                /* We're done if at the end of line or end of file */
                nsds++;
                break;
            }

            s = inputTok.sval;
            if (s == null)
            {
                /* For whatever reason, ';' comes up as a null character.
                   We will skip it and the next thing should be an SDS name */
                process_sds = true;
            }
            else if (s.equals("0") || s.equals("1"))
            {  /* Next token is an SDS band value */
                /* Push the token back in the stream so that it is grabbed
                   at the beginning of the SDS band read loop */
                process_bands = true;
                inputTok.pushBack();

                while (process_bands)
                {
                    /* get the SDS band value (0 or 1) */
                    try
                    {
                        tokenType = readNextNonCommentToken(inputTok);
                    }
                    catch (IOException e)
                    {
                        throw new ReadException(errorId);
                    }
                    catch (IllegalArgumentException e)
                    {
                        throw new ReadException(errorId);
                    }

                    s = inputTok.sval;
                    if (s == null)
                    {
                        /* For whatever reason, ';' comes up as a null
                           character.  We will skip it and the next thing
                           should be an SDS name */
                        process_bands = false;
                    }
                    else if (s.equals("0") || s.equals("1"))
                    {  /* SDS band value */
                        if (s.equals("0"))
                            sds_bands[nsds][nbands[nsds]] = 0;
                        else
                            sds_bands[nsds][nbands[nsds]] = 1;

                        /* increment the band count for this SDS */
                        nbands[nsds]++;
                    }
                    else
                    {  /* SDS name */
                        /* Push the token back in the stream so that it is
                           grabbed at the beginning of this SDS read loop */
                        inputTok.pushBack();
                        process_bands = false;
                    }
                }  /* while process_bands */
            }
            else
            {  /* Next token is an SDS name */
                /* Push the token back in the stream so that it is grabbed
                   at the beginning of this SDS read loop */
                process_sds = true;
                inputTok.pushBack();
            }

            /* increment the SDS count */
            nsds++;
        }  /* while process_sds */

        /* reset the tokenizer so that the , and ; are no longer ordinary
           token characters */
        inputTok.eolIsSignificant(false);

        /* Determine the actual band names for our HDF file */
        ntotal_bands = 0;
        for (i = 0; i < nsds; i++)
        {
            /* if no bands then use the SDS name only */
            if (nbands[i] == 0)
                bandnames[ntotal_bands++] = sdslist[i];
            else
            {
                /* loop through the bands and tag an "_b#" to the SDS name
                   for the current band name (if the band value is '1') */
                for (j = 0; j < nbands[i]; j++)
                {
                    if (sds_bands[i][j] == 1)
                    {
                        bandnames[ntotal_bands++] = sdslist[i] + "_b" + j;
                    }
                }
            }
        }

        /* Get the input band names */
        input_bandnames = getInputBandNamesArray();

        /* Loop through the bands in the image and unselect the ones that
           are not in the bandnames list */
        for (i = 0; i < fieldSize; i++)
        {
            /* Is the input_bandnames band in the bandnames list? */
            band_found = false;
            for (j = 0; j < ntotal_bands; j++)
            {
                if (input_bandnames[i].equals(bandnames[j]))
                {
                    band_found = true;
                    break;
                }
            }

            /* If found then setSelectedBand to true, otherwise set it
               to false */
            if (band_found)
                setSelectedBand(i, true);
            else
                setSelectedBand(i, false);
        }
    }

/******************************************************************************
    NAME:     parseFieldValue
    PURPOSE:  To parse the field value
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:        ModisTool
    NOTES:
******************************************************************************/
    private java.lang.String[] parseFieldValue (
       java.io.StreamTokenizer inputTok, int fieldSize, int errorId)
       throws ReadException
    {
        String []fieldValueArray = new String [fieldSize];
        int tokenType;
        String s;
        try
        {
            tokenType = readNextNonCommentToken(inputTok);
            if ( tokenType != StreamTokenizer.TT_WORD )
            {
                throw new ReadException(errorId);
            }
            s = inputTok.sval;

            if ( !s.equals("(") )
            {
                throw new ReadException(errorId);
            }
        }
        catch (IOException e)
        {
            throw new ReadException(errorId);
        }

        for (int i=0; i<fieldSize; ++i)
        {
            try
            {
                tokenType = readNextNonCommentToken(inputTok);
                if ( tokenType != StreamTokenizer.TT_WORD )
                    throw new ReadException(errorId);
                fieldValueArray[i] = inputTok.sval;
            }
            catch (IOException e)
            {
                throw new ReadException(errorId);
            }
            catch (IllegalArgumentException e)
            {
                throw new ReadException(errorId);
            }
        }

        try
        {
            tokenType = readNextNonCommentToken(inputTok);
            if ( tokenType != StreamTokenizer.TT_WORD )
            {
                throw new ReadException(errorId);
            }
            s = inputTok.sval;
            if ( !s.equals(")") )
            {
                throw new ReadException(errorId);
            }
        }
        catch (IOException e)
        {
            throw new ReadException(errorId);
        }

        return fieldValueArray;
    }


/******************************************************************************
    NAME:     fieldsErrorCheck
    PURPOSE:  To check possible error spelling in some digit fields
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private void fieldsErrorCheck (String[] sFieldValueArray, int size,
        int errorId) throws ReadException
    {
        for (int i=0; i<size; i++)
        {
            String str = sFieldValueArray[i];
            for (int k = 0; k < str.length(); k++)
            {
                char ch = str.charAt(k);
                if ((!Character.isDigit(ch)) && ( ch != '-') && ( ch != '.'))
                    throw new ReadException (errorId);
            }
        }
    }

   private StreamTokenizer setupTokenizer( StreamTokenizer inputTok, boolean wsignore ) {
      inputTok.resetSyntax();

      inputTok.whitespaceChars((int)' ',(int)' ');
      inputTok.whitespaceChars((int)'\t',(int)'\t');
      inputTok.whitespaceChars((int)'\n',(int)'\n');
      inputTok.whitespaceChars((int)'\r',(int)'\r');

      inputTok.whitespaceChars((int)'=', (int)'=');
      inputTok.whitespaceChars((int)',', (int)',');

      inputTok.wordChars('a','z');
      inputTok.wordChars('A','Z');
      inputTok.wordChars('_','_');
      inputTok.wordChars('0','9');
      inputTok.wordChars('-','-');
      inputTok.wordChars('.','.');
      inputTok.wordChars('#','#');
      inputTok.wordChars('\\','\\');
      inputTok.wordChars('/','/');
      inputTok.wordChars(':',':');
      inputTok.wordChars('(','(');
      inputTok.wordChars(')',')');

      inputTok.eolIsSignificant(false);
      inputTok.commentChar('#');

      return inputTok;
   }
    
/******************************************************************************
    NAME:     initTokenRead
    PURPOSE:  To initialize StreamTokenizer by defining some proper parses
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private StreamTokenizer initTokenRead (Reader fileReader)
    {
      StreamTokenizer inputTok = new StreamTokenizer(fileReader);
      return setupTokenizer(inputTok, false);
    }

/******************************************************************************
    NAME:     readHeaderFile
    PURPOSE:  Reads a Header file (.hdr) and populates corresponding data
              fields in the current object

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void readHeaderFile() throws ReadException
    {
        Reader fileReader;
        StreamTokenizer inputTok;
        String str;
        int nfiles;
        String s;
        String[] sArray;
        String[] sFieldValueArray;
        int tokenType;

        setSpatialSubsetType(SPACE_LAT_LON);
        readHeaderNumBands();
        readEllipsoid();
        readUtmZone();
        
        try
        {
            fileReader = new FileReader(getInputFilename());
        }
        catch (IOException e)
        {
            throw new ReadException(ErrorHandler.ERROR_OPEN_INPUTHEADER);
        }
                
        inputTok = initTokenRead (fileReader);
        READ_LOOP: while (true) // not end of file
        {
            try
            {
                tokenType = readNextNonCommentToken(inputTok);
                if (tokenType == StreamTokenizer.TT_EOF)
                    break READ_LOOP;
                else if ( tokenType != StreamTokenizer.TT_WORD )
                    throw new ReadException(ErrorHandler.ERROR_GENERAL);

                s = inputTok.sval;
                                
                if (s.toUpperCase().compareTo("UL_CORNER_LATLON") == 0)
                {
                    sArray = read2dPoint(inputTok,
                        ErrorHandler.ERROR_READ_CORNERS);
                    sULArray = sArray;
                    try
                    {
                        fieldsErrorCheck (sArray, sArray.length,
                            ErrorHandler.ERROR_READ_CORNERS);
                    }
                    catch (ReadException e)
                    {
                        throw e;
                    }
                    setInputImageExtent(UL,sArray);
                    setCornerPoint(UL,sArray);
                }
                else if (s.toUpperCase().compareTo("UR_CORNER_LATLON") == 0)
                {
                    sArray = read2dPoint(inputTok,
                        ErrorHandler.ERROR_READ_CORNERS);
                    try
                    {
                        fieldsErrorCheck (sArray, sArray.length,
                            ErrorHandler.ERROR_READ_CORNERS);
                    }
                    catch (ReadException e)
                    {
                        throw e;
                    }
                    setInputImageExtent(UR,sArray);
                    setCornerPoint(UR,sArray);
                }
                else if (s.toUpperCase().compareTo("LL_CORNER_LATLON") == 0)
                {
                    sArray = read2dPoint(inputTok,
                        ErrorHandler.ERROR_READ_CORNERS);
                    try
                    {
                        fieldsErrorCheck (sArray, sArray.length,
                            ErrorHandler.ERROR_READ_CORNERS);
                    }
                    catch (ReadException e) {  throw e;  }

                    setInputImageExtent(LL,sArray);
                    setCornerPoint(LL,sArray);
                }
                else if (s.toUpperCase().compareTo("LR_CORNER_LATLON") == 0)
                {
                    sArray = read2dPoint(inputTok,
                        ErrorHandler.ERROR_READ_CORNERS);
                    sLRArray = sArray;
                    try
                    {
                        fieldsErrorCheck (sArray, sArray.length,
                            ErrorHandler.ERROR_READ_CORNERS);
                    }
                    catch (ReadException e) {  throw e;  }
                    setInputImageExtent(LR,sArray);
                    setCornerPoint(LR,sArray);
                }
                else if (s.toUpperCase().compareTo("NBANDS") == 0)
                    readNextNonCommentToken(inputTok);
                else if (s.toUpperCase().compareTo("BANDNAMES") == 0)
                    inputBandNamesArray = parseFieldValue (inputTok,
                        getNumBands(), ErrorHandler.ERROR_NBANDS_FIELD);
                else if (s.toUpperCase().compareTo("NLINES") == 0)
                {
                    sFieldValueArray = parseFieldValue (inputTok,
                        getNumBands(), ErrorHandler.ERROR_NLINES_VALUE);
                    inputNumOfLinesArray = new int [getNumBands()];
                    for (int i = 0; i < getNumBands(); i++)
                        inputNumOfLinesArray[i] =
                            Integer.parseInt(sFieldValueArray[i]);
                }
                else if (s.toUpperCase().compareTo("NSAMPLES") == 0)
                {
                    inputNumOfSamplesArray = new int [getNumBands()];
                    sFieldValueArray = parseFieldValue (inputTok,
                        getNumBands(), ErrorHandler.ERROR_NSAMPLES_VALUE);
                    for (int i = 0; i < getNumBands(); i++)
                        inputNumOfSamplesArray[i] =
                            Integer.parseInt(sFieldValueArray[i]);
                }
                else if (s.toUpperCase().compareTo("DATA_TYPE") == 0)
                {
                    inputDataTypeArray = parseFieldValue (inputTok,
                        getNumBands(), ErrorHandler.ERROR_DATA_TYPE);
                }
                else if (s.toUpperCase().compareTo("MIN_VALUE") == 0)
                {
                    inputMinValueArray = new double [getNumBands()];
                    sFieldValueArray = parseFieldValue (inputTok,
                        getNumBands(), ErrorHandler.ERROR_MIN_VALUE);
                    for (int i = 0; i < getNumBands(); i++)
                        inputMinValueArray[i] = Double.valueOf(
                            sFieldValueArray[i]).doubleValue();
                }
                else if (s.toUpperCase().compareTo("MAX_VALUE") == 0)
                {
                    inputMaxValueArray = new double [getNumBands()];
                    sFieldValueArray = parseFieldValue (inputTok,
                        getNumBands(), ErrorHandler.ERROR_MAX_VALUE);
                    for (int i = 0; i < getNumBands(); i++)
                        inputMaxValueArray[i] = Double.valueOf(
                            sFieldValueArray[i]).doubleValue();
                }
                else if (s.toUpperCase().compareTo("BACKGROUND_FILL") == 0)
                {
                    sFieldValueArray = parseFieldValue (inputTok,
                        getNumBands(), ErrorHandler.ERROR_BACKGROUND_FILL);
                    inputBackgroundFillArray = new double [getNumBands()];
                    for (int i = 0; i < getNumBands(); i++)
                        inputBackgroundFillArray[i] = Double.valueOf(
                            sFieldValueArray[i]).doubleValue();
                }
            }
            catch (IOException e)
            {
                throw new ReadException(ErrorHandler.ERROR_GENERAL);
            }
            catch (IllegalArgumentException e)
            {
                throw new ReadException(ErrorHandler.ERROR_GENERAL);
            }
        }       // end of while loop

        try
        {
            fileReader.close();
        }
        catch (IOException e) {}
    }

/******************************************************************************
    NAME:     readHeaderNumBands
    PURPOSE:  Reads a Header file (.hdr) and looks specifically for the
              NUMBANDS property.  Once it finds that property, it reads the
              corresponding value and sets the numBands property of the
              descriptor.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void readHeaderNumBands() throws ReadException
    {
        Reader fileReader;
        StreamTokenizer inputTok;
        String s;
        int tokenType;
        try
        {
            fileReader = new FileReader(getInputFilename());
        }
        catch (IOException e)
        {
            throw new ReadException(ErrorHandler.ERROR_OPEN_INPUTHEADER);
        }

        inputTok = initTokenRead (fileReader);

        READ_LOOP: while (true) // not end of file
        {
            try
            {
                tokenType = readNextNonCommentToken(inputTok);
                if (tokenType == StreamTokenizer.TT_EOF)
                    break READ_LOOP;
                else if ( tokenType != StreamTokenizer.TT_WORD )
                    throw new ReadException(ErrorHandler.ERROR_GENERAL);
                s = inputTok.sval;

                // determine total number of bands
                if (s.toUpperCase().compareTo("NBANDS") == 0)
                {
                    s = readHeaderProperty( inputTok,
                        ErrorHandler.ERROR_TOTALBANDS_VALUE);
                    try
                    {
                        setNumBands(s);
                    }
                    catch (IllegalArgumentException e)
                    {
                        throw new ReadException(
                            ErrorHandler.ERROR_TOTALBANDS_FIELD);
                    }

                    if ( getNumBands() < 1 )
                        throw new ReadException(
                            ErrorHandler.ERROR_TOTALBANDS_VALUE);
                }
            }
            catch (IOException e)
            {
                throw new ReadException(ErrorHandler.ERROR_GENERAL);
            }
            catch (IllegalArgumentException e)
            {
                throw new ReadException(ErrorHandler.ERROR_GENERAL);
            }
        }
        try
        {
            fileReader.close();
        }
        catch (IOException e) {}
    }
                
/******************************************************************************
    NAME:     readEllipsoid
    PURPOSE:  Reads a Header file (.hdr) and looks specifically for the
              ellipsoid property.  Once it finds that property, it reads the
              corresponding value and sets the ellipsoid property of the
              descriptor.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void readEllipsoid() throws ReadException
    {
        Reader fileReader;
        StreamTokenizer inputTok;
        String s;
        int tokenType;
        try
        {
            fileReader = new FileReader(getInputFilename());
        }
        catch (IOException e)
        {
            throw new ReadException(ErrorHandler.ERROR_OPEN_INPUTHEADER);
        }

        inputTok = initTokenRead (fileReader);
        READ_LOOP: while (true) // not end of file
        {
            try
            {
                tokenType = readNextNonCommentToken(inputTok);
                if (tokenType == StreamTokenizer.TT_EOF)
                    break READ_LOOP;
                else if ( tokenType != StreamTokenizer.TT_WORD )
                    throw new ReadException(ErrorHandler.ERROR_GENERAL);

                s = inputTok.sval;
                if (s.toUpperCase().compareTo("ELLIPSOID") == 0)
                {
                    EllipsoidOption = true;
                    s = readHeaderProperty( inputTok,
                        ErrorHandler.ERROR_GENERAL);
                    try
                    {
                        setInputEllipsoid(s);
                    }
                    catch (IllegalArgumentException e)
                    {
                        throw new ReadException(ErrorHandler.ERROR_GENERAL);
                    }
                }
            }
            catch (IOException e)
            {
                throw new ReadException(ErrorHandler.ERROR_GENERAL);
            }
            catch (IllegalArgumentException e)
            {
                throw new ReadException(ErrorHandler.ERROR_GENERAL);
            }
        }
        try
        {
            fileReader.close();
        }
        catch (IOException e) {}
    }
        
/******************************************************************************
    NAME:     check
    PURPOSE:  checks whether the ellipsoid is present in the input file
    RETURN VALUE:  Vector
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public boolean check()
    {
        return  EllipsoidOption;
    }
    
/******************************************************************************
    NAME:     uncheck
    PURPOSE:  checks whether the ellipsoid code is present in the input file
    RETURN VALUE:  Vector
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void uncheck()
    {
        EllipsoidOption = false;
    } 

/******************************************************************************
    NAME:     check1
    PURPOSE:  checks whether the UTMZone is present in the input file
    RETURN VALUE:  Vector
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public boolean check1()
    {
        return  UtmOption;
    }
   
/******************************************************************************
    NAME:     check1
    PURPOSE:  checks whether the UTMZone is present in the input file
    RETURN VALUE:  Vector
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void uncheck1()
    {
        UtmOption = false;
    }
                
/******************************************************************************
    NAME:     readUtmZone
    PURPOSE:  Reads a Header file (.hdr) and looks specifically for the
              UTM zone property.  Once it finds that property, it reads the
              corresponding value and sets the utm zone code property of the
              descriptor.
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void readUtmZone() throws ReadException
    {
        Reader fileReader;
        StreamTokenizer inputTok;
        String s;
        int tokenType;
        try
        {
            fileReader = new FileReader(getInputFilename());
        }
        catch (IOException e)
        {
            throw new ReadException(ErrorHandler.ERROR_OPEN_INPUTHEADER);
        }

        inputTok = initTokenRead (fileReader);
        READ_LOOP: while (true) // not end of file
        {
            try
            {
                tokenType = readNextNonCommentToken(inputTok);
                if (tokenType == StreamTokenizer.TT_EOF)
                    break READ_LOOP;
                else if ( tokenType != StreamTokenizer.TT_WORD )
                    throw new ReadException(ErrorHandler.ERROR_GENERAL);

                s = inputTok.sval;
                if (s.toUpperCase().compareTo("UTM_ZONE") == 0)
                {
                    UtmOption = true;
                    s = readHeaderProperty(inputTok,
                        ErrorHandler.ERROR_GENERAL);
                    try
                    {
                        setInputUTMZone(s);
                    }
                    catch (IllegalArgumentException e)
                    {
                        throw new ReadException(ErrorHandler.ERROR_GENERAL);
                    }
                }
            }
            catch (IOException e)
            {
                throw new ReadException(ErrorHandler.ERROR_GENERAL);
            }
            catch (IllegalArgumentException e)
            {
                throw new ReadException(ErrorHandler.ERROR_GENERAL);
            }
        }
        try
        {
            fileReader.close();
        }
        catch (IOException e) {}
    }
        
/******************************************************************************
    NAME:     readParameterFile
    PURPOSE:  Reads a Parameter file (.prm) and populates
              corresponding data fields in the current object.
 
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void readParameterFile(boolean readheader) throws ReadException
    {
        Reader fileReader;
        StreamTokenizer inputTok;
        String str,s1,s2,s3,s4;
        String s;
        String[] sArray;
        String temp;
        String[] sFieldValueArray;
        int tokenType;
        short totalNumOfRequiredFields = 0;
        boolean bOutputPixelSizeFieldNotExist = true;

        try
        {
            fileReader = new FileReader(getParameterFilename());
        }
        catch (IOException e)
        {
            throw new ReadException(ErrorHandler.ERROR_OPEN_INPUTPAR);
        }
        inputTok = initTokenRead (fileReader);

        boolean subsetOption = false;
        boolean projParamOption = false;
        boolean spatialsubsetTypeOption = false;
        boolean ULCornerOption = false;
        boolean LRCornerOption = false;
        boolean resampleTypeOption = false;
        boolean outputDataTypeOption = false;
        boolean UtmZoneOption = false;
        boolean requireInputFileName = false;
        boolean requireGeolocFileName = false;
        boolean requireOutputPrjType = false;
        boolean requireOutputFileName = false;
        boolean requireOutputFileType = false;

        while (true)    /* not end of file */
        {
            try {
                /* Get the next token */
                tokenType = inputTok.nextToken();
                if (tokenType == StreamTokenizer.TT_EOF)
                {
                    break;
                }
                s = inputTok.sval;

                /* Read the input filename - required */
                if (s.toUpperCase().compareTo("INPUT_FILENAME") == 0)
                {
                    requireInputFileName = true;
                    s = readHeaderFileProperty(inputTok,
                        ErrorHandler.ERROR_INPUTNAME_FIELD);
                    totalNumOfRequiredFields++;
                    setInputFilename(s);

                    s = s.toLowerCase();
                    if (s.endsWith(".hdf"))
                    {
                        setInputFileType(HDFEOS);
                    }
                    else
                    {
                        setInputFileType(BAD_FILE_TYPE);
                        throw new ReadException(
                            ErrorHandler.ERROR_INPUT_EXTENSION);
                    }

                    if (readheader)
                    {
                        try
                        {
                            readHdfHeader();
                        }
                        catch (ReadException e)
                        {
                            if (e.getErrorId() !=
                                ErrorHandler.ERROR_INPUT_EXTENSION)
                                throw e;
                        }
                    }
                }
                /* Read the geolocation filename - required */
                else if (s.toUpperCase().compareTo("GEOLOCATION_FILENAME") == 0)
                {
                    requireGeolocFileName = true;
                    s = readHeaderFileProperty(inputTok,
                        ErrorHandler.ERROR_GEOLOCNAME_FIELD);
                    totalNumOfRequiredFields++;
                    setGeolocFilename(s);
                }
                /* Read the input spectral subset - optional */
                else if (s.toUpperCase().compareTo("INPUT_SDS_NAME") == 0)
                {
                    subsetOption = true;
                    parseSDSFieldValues (inputTok, getNumBands(),
                        ErrorHandler.ERROR_SPECTRALSUB_FIELD);
                }
                /* Read the output spatial subset - optional */
                else if (s.toUpperCase().compareTo(
                    "OUTPUT_SPATIAL_SUBSET_TYPE") == 0)
                {
                    spatialsubsetTypeOption = true;
                    s = readHeaderProperty(inputTok,
                        ErrorHandler.ERROR_SPATIALSUBSET_TYPE);
                    try
                    {
                        if ( s.equals("LAT_LONG"))
                        {
                            setSpatialSubsetType(SPACE_LAT_LON);
                        }
                        else if (s.equals("LINE_SAMPLE"))
                        {
                            setSpatialSubsetType(SPACE_LINE_SAMPLE);
                        }
                        else if (s.equals("PROJ_COORDS"))
                        {
                            setSpatialSubsetType(SPACE_PROJ_XY);
                        }
                    }
                    catch (IllegalArgumentException e)
                    {
                        throw new ReadException(
                            ErrorHandler.ERROR_SPATIALSUBSET_TYPE);
                    }
                }

                /*
                 * determine spatial subsetting:
                 * OUTPUT_SPACE_UPPER_LEFT_CORNER (LONG LAT) = (long lat)
                 * OUTPUT_SPACE_LOWER_RIGHT_CORNER (LONG LAT) = (long lat)
                 *              - OR -
                 * OUTPUT_SPACE_UPPER_LEFT_CORNER (X Y) = (proj_x proj_y)
                 * OUTPUT_SPACE_LOWER_RIGHT_CORNER (X Y) = (proj_x proj_y)
                 *              - OR -
                 * OUTPUT_SPACE_UPPER_LEFT_CORNER (SAMPLE LINE) = (sample line)
                 * OUTPUT_SPACE_LOWER_RIGHT_CORNER (SAMPLE LINE) = (sample line)
                 */
                /* Read the output UL corner - optional */
                else if ((s.toUpperCase().compareTo(
                    "OUTPUT_SPACE_UPPER_LEFT_CORNER") == 0))
                {
                    ULCornerOption = true;

                    /* Strip off the (LONG LAT), (X Y), or (SAMPLE LINE)
                       tokens */
                    tokenType = inputTok.nextToken();
                    tokenType = inputTok.nextToken();

                    /* Read the decimal/integer values */
                    sArray = read2dPointParam(inputTok,
                        ErrorHandler.ERROR_SPATIALSUB_FIELD, true);
                    try
                    {
                        fieldsErrorCheck (sArray, sArray.length,
                            ErrorHandler.ERROR_SPATIALSUB_FIELD);
                    }
                    catch (ReadException e)
                    {
                        throw e;
                    }

                    if (sArray != null)
                    {
                        try
                        {
                            /* The parameter file lists the corner points
                               for UL and LR as LONG/LAT or X/Y. The
                               setCornerPoint function expects LAT/LONG or
                               Y/X. So, flip them before the setCornerPoint
                               call. */
                            temp = sArray[0];
                            sArray[0] = sArray[1];
                            sArray[1] = temp;
                            setCornerPoint(UL, sArray);
                        }
                        catch (IllegalArgumentException e)
                        {
                            throw new ReadException(
                                ErrorHandler.ERROR_SPATIALSUB_FIELD);
                        }
                    }
                }
                /* Read the output LR corner - optional */
                else if ((s.toUpperCase().compareTo(
                    "OUTPUT_SPACE_LOWER_RIGHT_CORNER") == 0))
                {
                    LRCornerOption = true;

                    /* Strip off the (LONG LAT), (X Y), or (SAMPLE LINE)
                       tokens */
                    tokenType = inputTok.nextToken();
                    tokenType = inputTok.nextToken();

                    /* Read the decimal/integer values */
                    sArray = read2dPointParam(inputTok,
                        ErrorHandler.ERROR_SPATIALSUB_FIELD, true);
                    try
                    {
                        fieldsErrorCheck (sArray, sArray.length,
                            ErrorHandler.ERROR_SPATIALSUB_FIELD);
                    }
                    catch (ReadException e)
                    {
                         throw e;
                    }

                    if (sArray != null)
                    {
                         try
                         {
                            /* The parameter file lists the corner points
                               for UL and LR as LONG/LAT or X/Y. The
                               setCornerPoint function expects LAT/LONG or
                               Y/X. So, flip them before the setCornerPoint
                               call. */
                            temp = sArray[0];
                            sArray[0] = sArray[1];
                            sArray[1] = temp;
                            setCornerPoint(LR,sArray);
                         }
                         catch (IllegalArgumentException e)
                         {
                            throw new ReadException(
                                ErrorHandler.ERROR_SPATIALSUB_FIELD);
                         }
                    }
                }
                /* Read the output filename - required */
                else if (s.toUpperCase().compareTo("OUTPUT_FILENAME") == 0)
                {
                    requireOutputFileName = true;
                    s = readHeaderFileProperty(inputTok,
                        ErrorHandler.ERROR_OUTPUTNAME_FIELD);
                    totalNumOfRequiredFields++;
                    if ( getOutputFilename() == null )
                        setOutputFilename( s );
                }
                /* Read the output file type - required */
                else if (s.toUpperCase().compareTo("OUTPUT_FILE_FORMAT") == 0)
                {
                    requireOutputFileType = true;
                    s = readHeaderProperty(inputTok,
                        ErrorHandler.ERROR_OUTPUTTYPE_FIELD);
                    totalNumOfRequiredFields++;
                    if (s.toUpperCase().compareTo("RB_FMT") == 0)
                    {
                        setOutputFileType(MULTIFILE);
                    }
                    else if (s.toUpperCase().compareTo("HDF_FMT") == 0)
                    {
                        setOutputFileType(HDFEOS);
                    }
                    else if (s.toUpperCase().compareTo("GEOTIFF_FMT") == 0)
                    {
                        setOutputFileType(GEOTIFF);
                    }
                    else
                        throw new ReadException(
                            ErrorHandler.ERROR_OUTPUTTYPE_FIELD);
                }
                /* Read the resampling type - optional */
                else if (s.toUpperCase().compareTo("KERNEL_TYPE") == 0)
                {
                    resampleTypeOption = true;

                    /* Strip the (CC/BI/NN) token */
                    s = readHeaderProperty(inputTok,
                        ErrorHandler.ERROR_RESAMPLE_FIELD);

                    /* Get the actual resampling string */
                    s = readHeaderProperty(inputTok,
                        ErrorHandler.ERROR_RESAMPLE_FIELD);
                    try
                    {
                        if (s.toUpperCase().compareTo("CC") == 0)
                            setResamplingType("CUBIC_CONVOLUTION");
                        else if (s.toUpperCase().compareTo("NN") == 0)
                            setResamplingType("NEAREST_NEIGHBOR");
                        else if (s.toUpperCase().compareTo("BI") == 0)
                            setResamplingType("BILINEAR");
                    }
                    catch (IllegalArgumentException e)
                    {
                        throw new ReadException(
                            ErrorHandler.ERROR_RESAMPLE_TYPE);
                    }
                }
                /* Read the output projection type - required */
                else if (s.toUpperCase().compareTo("OUTPUT_PROJECTION_NUMBER")
                    == 0)
                {
                    requireOutputPrjType = true;
                    totalNumOfRequiredFields++;
                    s = readHeaderProperty(inputTok,
                        ErrorHandler.ERROR_PROJECTION_TYPE);
                    try
                    {
                        setOutputProjectionType(s.toUpperCase());
                    }
                    catch (IllegalArgumentException e)
                    {
                        throw new ReadException(
                            ErrorHandler.ERROR_PROJECTION_TYPE);
                    }
                }
                /* read projection parameters - optional */
                else if (s.toUpperCase().compareTo(
                    "OUTPUT_PROJECTION_PARAMETER") == 0)
                {
                    projParamOption = true;
                    sFieldValueArray = parseDefaultFieldValue (inputTok, 15,
                        "0.0", ErrorHandler.ERROR_PROJPARAMS_FIELD);
                    try 
                    {
                        fieldsErrorCheck (sFieldValueArray, 15,
                            ErrorHandler.ERROR_PROJPARAMS_FIELD);
                    }
                    catch (ReadException e)
                    {
                        throw e;
                    }

                    for (int i=0; i<15; i++)
                        setOutputProjectionParameter(i, sFieldValueArray[i]);
                }
                /* Ellipsoid - optional */
                else if (s.toUpperCase().compareTo("OUTPUT_PROJECTION_SPHERE")
                    == 0)
                {
                    s = readHeaderProperty(inputTok,
                        ErrorHandler.ERROR_ELLIPSOID);
                    if (s.length() > 0)
                    {
                        setOutputEllipsoidCode(Integer.valueOf(s).intValue());
                    }
                }
                /* UTM zone - optional */
                else if (s.toUpperCase().compareTo("OUTPUT_PROJECTION_ZONE")
                    == 0)
                {
                    s = readHeaderProperty(inputTok,
                        ErrorHandler.ERROR_UTM_ZONE);
                    if ( s.length()>0)
                        setUTMZone(Integer.parseInt(s));
                }
                /* Read the output data type - optional */
                else if (s.toUpperCase().compareTo("OUTPUT_DATA_TYPE")
                    == 0)
                {
                    outputDataTypeOption = true;
                    totalNumOfRequiredFields++;
                    s = readHeaderProperty(inputTok,
                        ErrorHandler.ERROR_DATA_TYPE);
                    {
                        try
                        {
                            setOutputDataType(s.toUpperCase());
                        }
                        catch (IllegalArgumentException e)
                        {
                            throw new ReadException(
                                ErrorHandler.ERROR_DATA_TYPE);
                        }
                    }
                }
                /* Read the output pixel size - optional */
                else if (s.toUpperCase().compareTo("OUTPUT_PIXEL_SIZE") == 0)
                {
                    try
                    {
                        sArray = readPixelSizeAndUnit(inputTok,
                            ErrorHandler.ERROR_OUTPUTPIXELSIZE_FIELD, true);
                    }
                    catch (ReadException e)
                    {
                        throw e;
                    }

                    if ( sArray != null )
                    {
                        setOutputPixelSize( sArray[0] );
                        bOutputPixelSizeFieldNotExist = false;
                        if (sArray[1] != " ")
                            setOutputPixelSizeUnit( sArray[1].toUpperCase() );
                    }
                }
            }
            catch (IOException e)
            {
            }
            catch (IllegalArgumentException e)
            {
                throw new ReadException(ErrorHandler.ERROR_DATA_TYPE);
            }
        }       /* end of while loop */

        /* Handle the UL and LR corners */
        if ( ULCornerOption && LRCornerOption && spatialsubsetTypeOption )
        {
             if ( getSpatialSubsetType() == SPACE_LAT_LON ||
                  getSpatialSubsetType() == SPACE_PROJ_XY )
             {
                 double[] pt = new double[2];
                 pt[0] = 0.0;
                 pt[1] = 0.0;
                 setCornerPoint(UR,pt);
                 setCornerPoint(LL,pt);
             }
             else
             {
                 double[] ulPt = getCornerPoint(UL);
                 double[] lrPt = getCornerPoint(LR);
                 double[] urPt = { ulPt[0], lrPt[1] };
                 double[] llPt = { lrPt[0], ulPt[1] };

                 setCornerPoint(UR,urPt);
                 setCornerPoint(LL,llPt);
             }

             /* determine spatial subsetting */
             setInputCornerPoint(UL,0,0);
             setInputCornerPoint(UR,0,getBandInfo(0).getNumSamples() - 1);
             setInputCornerPoint(LL,getBandInfo(0).getNumLines()-1,0);
             setInputCornerPoint(LR, getBandInfo(0).getNumLines()-1,
                 getBandInfo(0).getNumSamples()-1);

             /* Set corner points for bands */
             for (int i=0; i < getNumBands(); i++)
             {
                 final double[] outCornerPt = {0.0, 0.0};
                 final BandType band = getBandInfo(i);

                 for (int j=0; j < 4; ++j)
                 {
                     final int[] pt = getInputCornerPoint(j);
                     final int[] newPt = new int[2];
                     for (int k=0; k < 2; ++k)
                         newPt[k] = pt[k];
                     band.setInputCornerPoint(j,newPt);
                     band.setOutputCornerPoint(j,outCornerPt);
                 }
             }
        }

        if ( requireInputFileName == false)
            throw new ReadException(
                ErrorHandler.ERROR_MISSING_INPUT_FILE_NAME);
        if ( requireGeolocFileName == false)
            throw new ReadException(
                ErrorHandler.ERROR_MISSING_GEOLOC_FILE_NAME);
        if ( requireOutputFileName == false)
            throw new ReadException(
                ErrorHandler.ERROR_MISSING_OUTPUT_FILE_NAME);
        if ( requireOutputFileType == false)
            throw new ReadException(
                ErrorHandler.ERROR_MISSING_OUTPUT_FILE_TYPE);
        if ( requireOutputPrjType == false)
            throw new ReadException(
                ErrorHandler.ERROR_MISSING_OUTPUT_PROJ_TYPE);
        if ( (ULCornerOption && LRCornerOption) && !spatialsubsetTypeOption )
            throw new ReadException(
                ErrorHandler.ERROR_MISSING_SPATIAL_SUBSET_TYPE);

        /* not exist in the .prm */
        if (subsetOption == false)
        {
            for (int i = 0; i < getNumBands(); i++)
                setSelectedBand(i,true);
        }
        if (projParamOption == false)
        {
            for (int i=0; i<15; i++)
                setOutputProjectionParameter(i, "0.0");
        }
        if (! resampleTypeOption )
            setResamplingType("NEAREST_NEIGHBOR");
        if (! outputDataTypeOption )
            setOutputDataType("SAME_AS_INPUT");
        if (! ULCornerOption)
        {
            setCornerPoint(UL, sULArray);
            setSpatialSubsetType("INPUT_LAT_LONG");
        }
        if (! LRCornerOption)
        {
            setCornerPoint(LR, sLRArray);
            setSpatialSubsetType("INPUT_LAT_LONG");
        }

        try
        {
            fileReader.close();
        }
        catch (IOException e) {}
    }

/******************************************************************************
    NAME:     readHeaderProperty
    PURPOSE:  To read header property
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private String readHeaderProperty(StreamTokenizer tok,String propName,
        int errorId) throws ReadException
    {
        return readHeaderProperty(tok,propName,errorId,false);
    }

/******************************************************************************
    NAME:     readNextNonCommentToken
    PURPOSE:  To parse and skip comment lines
    RETURN VALUE:                            integer
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private int readNextNonCommentToken(StreamTokenizer tok) throws IOException
    {
        int tokenType = tok.nextToken();
        while ( tokenType == StreamTokenizer.TT_WORD &&
                tok.sval.charAt(0) == '#' )
        {
            tokenType = consumeComment(tok);
            if ( tokenType != StreamTokenizer.TT_EOF)
            {
                tokenType = tok.nextToken();
            }
        }
        return tokenType;
    }

/******************************************************************************
    NAME:     readHeaderProperty
    PURPOSE:  To read header property

    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private String readHeaderProperty(StreamTokenizer tok,String propName,
        int errorId,boolean optional) throws ReadException
    {
        String s;
        int tokenType;
        try
        {
            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if ( tokenType != StreamTokenizer.TT_WORD)
            {
                if (tokenType == StreamTokenizer.TT_EOF && optional)
                   return null;
                throw new ReadException(errorId);
            }

            if ( !s.equals(propName) )
            {
                if ( !optional )
                   throw new ReadException(errorId);
                else
                {
                   tok.pushBack();
                   return null;
                }
            }
                
            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;
            if ( tokenType != StreamTokenizer.TT_WORD)
                throw new ReadException(errorId);
        }
        catch (IOException e)
        {
            throw new ReadException(errorId);
        }
        return s;
    }

/******************************************************************************
    NAME:     readHeaderProperty
    PURPOSE:  To read header property
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private String readHeaderProperty(StreamTokenizer tok, int errorId)
        throws ReadException
    {
        return readHeaderProperty(tok, errorId, false);
    }

/******************************************************************************
    NAME:     readHeaderProperty
    PURPOSE:  To read header property
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private String readHeaderProperty(StreamTokenizer tok, int errorId,
        boolean optional) throws ReadException
    {
        String s;
        int tokenType;

        try
        {
            tokenType = readNextNonCommentToken(tok);
            s = tok.sval;

            if ( tokenType != StreamTokenizer.TT_WORD)
                throw new ReadException(errorId);
        }
        catch (IOException e)
        {
            throw new ReadException(errorId);
        }
        return s;
    }

    
/******************************************************************************
    NAME:     readHeaderFileProperty
    PURPOSE:  To read header property of file names.
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private String readHeaderFileProperty(StreamTokenizer tok, int errorId)
       throws ReadException
    {
      String s = "";
      int tokenType;

      try {
         tokenType = readNextNonCommentToken(tok);
         s = tok.sval;

         // If we find a quote, then adjust the tokenizer to read in the
         // quoted string.
         if( s == null && tok.ttype == '"' ) {
            tok.quoteChar('"');
            tok.wordChars(' ',' ');
            tokenType = tok.nextToken();
            if ( tokenType != StreamTokenizer.TT_WORD ) {
               throw new ReadException(ErrorHandler.ERROR_MISMATCHED_QUOTE);
            }
            s = tok.sval;
            setupTokenizer(tok, false);
            tokenType = tok.nextToken();
            if( !(tok.sval == null && tok.ttype == '"' )) {
               throw new ReadException(ErrorHandler.ERROR_MISMATCHED_QUOTE);
            }
            return s;
         } else if( s != null ){
            tok.wordChars(' ',' ');
            tokenType = tok.nextToken();
            if ( tokenType != StreamTokenizer.TT_WORD &&
                    tokenType != StreamTokenizer.TT_EOL &&
                    tokenType != StreamTokenizer.TT_EOF ) {
               throw new ReadException(errorId);
            }
            s = s + " " + tok.sval;
            return s;
         }
        
         if ( tokenType != StreamTokenizer.TT_WORD && tokenType != '"' ) {
            throw new ReadException(errorId);
         }
      } catch (IOException e) {
         throw new ReadException(errorId);
      } finally {
         // Reset tokenizer.
         setupTokenizer(tok, false);
      }
      return s;
    }

/******************************************************************************
    NAME:     getParameterFilename
    PURPOSE:  To get parameter file name
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getParameterFilename()
    {
        return parameterFilename;
    }

/******************************************************************************
    NAME:     setParameterFilename
    PURPOSE:  To set parameter file name
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setParameterFilename(String value)
    {
        parameterFilename = value;
    }

/******************************************************************************
    NAME:     writeParameterFile
    PURPOSE:  To write a parameter file to disk
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRSwath
    NOTES:
******************************************************************************/
    public void writeParameterFile() throws WriteException
    {
        FileWriter fw;
        PrintWriter pw;
        StringBuffer buf = new StringBuffer();

        try
        {
            fw = new FileWriter(getParameterFilename());
            pw = new PrintWriter(fw);
            /*
             * input filename
             */
            pw.println();
            pw.print("INPUT_FILENAME = ");
            try
            {
                pw.println( getInputFilename() );
            }
            catch (IllegalArgumentException e)
            {
                throw new WriteException( "WritePrm", -2,
                    "Error: writing the input filename: "+
                    getInputFilename(),false);
            }
            /*
             * geolocation filename
             */
            pw.println();
            pw.print("GEOLOCATION_FILENAME = ");
            try
            {
                pw.println( getGeolocFilename() );
            }
            catch (IllegalArgumentException e)
            {
                throw new WriteException( "WritePrm", -2,
                    "Error: writing the geolocation filename: "+
                    getGeolocFilename(),false);
            }
            /*
             * spectral subset
             */
            pw.println();
            pw.print("INPUT_SDS_NAME = ");
            String sdsString = getSDSString();
            try
            {
                pw.println(sdsString);
            }
            catch (IllegalArgumentException e)
            {
                throw new WriteException( "WritePrm", -2,
                    "Error: writing the input SDS name(s): ", false);
            }
            /*
             * spatial subset type
             */
            pw.println();
            pw.print("OUTPUT_SPATIAL_SUBSET_TYPE = ");
            try
            {
                pw.println(getSpatialSubsetTypeString(getSpatialSubsetType()));
            }
            catch (IllegalArgumentException e)
            {
                throw new WriteException( "WritePrm", -2,
                    "Error: invalid spatial subset type: "+
                    getSpatialSubsetTypeString(getSpatialSubsetType()),false);
            }
            /*
             * spatial subset
             */
            if ( getSpatialSubsetType() == SPACE_LAT_LON )
                pw.print("OUTPUT_SPACE_UPPER_LEFT_CORNER (LONG LAT) = ");
            else if ( getSpatialSubsetType() == SPACE_PROJ_XY )
                pw.print("OUTPUT_SPACE_UPPER_LEFT_CORNER (X Y) = ");
            else
                pw.print("OUTPUT_SPACE_UPPER_LEFT_CORNER (SAMPLE LINE) = ");
            double[] ulCorner = getCornerPoint(UL);
            if ( getSpatialSubsetType() == SPACE_LAT_LON )
            {
                try
                {
                    pw.print(Double.toString(ulCorner[1]));
                    pw.print(" ");
                    pw.print(Double.toString(ulCorner[0]));
                }
                catch (IllegalArgumentException e)
                {
                    throw new WriteException( "WritePrm", -2,
                        "Error: writing UL lat/long corner", false);
                }
            }
            else if ( getSpatialSubsetType() == SPACE_PROJ_XY )
            {
                try
                {
                    pw.print(Double.toString(ulCorner[0]));
                    pw.print(" ");
                    pw.print(Double.toString(ulCorner[1]));
                }
                catch (IllegalArgumentException e)
                {
                    throw new WriteException( "WritePrm", -2,
                        "Error: writing UL proj. x/y corner", false);
                }
            }
            else
            {
                try
                {
                    pw.print(Integer.toString((int)ulCorner[1]));
                    pw.print(" ");
                    pw.print(Integer.toString((int)ulCorner[0]));
                }
                catch (IllegalArgumentException e)
                {
                    throw new WriteException( "WritePrm", -2,
                        "Error: writing UL line/sample corner", false);
                }
            }
            pw.println();

            if ( getSpatialSubsetType() == SPACE_LAT_LON )
                pw.print("OUTPUT_SPACE_LOWER_RIGHT_CORNER (LONG LAT) = ");
            else if ( getSpatialSubsetType() == SPACE_PROJ_XY )
                pw.print("OUTPUT_SPACE_LOWER_RIGHT_CORNER (X Y) = ");
            else
                pw.print("OUTPUT_SPACE_LOWER_RIGHT_CORNER (SAMPLE LINE) = ");
            double[] lrCorner = getCornerPoint(LR);
            if ( getSpatialSubsetType() == SPACE_LAT_LON )
            {
                try
                {
                    pw.print(Double.toString(lrCorner[1]));
                    pw.print(" ");
                    pw.println(Double.toString(lrCorner[0]));
                }
                catch (IllegalArgumentException e)
                {
                    throw new WriteException( "WritePrm", -2,
                        "Error: writing LR lat/long corner", false);
                }
            }
            else if ( getSpatialSubsetType() == SPACE_PROJ_XY )
            {
                try
                {
                    pw.print(Double.toString(lrCorner[0]));
                    pw.print(" ");
                    pw.println(Double.toString(lrCorner[1]));
                }
                catch (IllegalArgumentException e)
                {
                    throw new WriteException( "WritePrm", -2,
                        "Error: writing LR proj. x/y corner", false);
                }
            }
            else
            {
                try
                {
                    pw.print(Integer.toString((int)lrCorner[1]));
                    pw.print(" ");
                    pw.println(Integer.toString((int)lrCorner[0]));
                }
                catch (IllegalArgumentException e)
                {
                    throw new WriteException( "WritePrm", -2,
                        "Error: writing LR line/sample corner", false);
                }
            }
            /*
             * write output filename (without file extension)
             */
            pw.println();
            pw.print("OUTPUT_FILENAME = ");
            String outFilename = getOutputFilename();
            String outName = new String ();
            int dotIndex = outFilename.lastIndexOf('.');
            if (dotIndex > 0)
                outName = outFilename.substring(0, dotIndex);
            else
                outName = outFilename;
            try
            {
                pw.println(outName);
            }
            catch (IllegalArgumentException e)
            {
                throw new WriteException( "WritePrm", -2,
                    "Error: writing output filename: " + outName, false);
            }
            /*
             * write output file type
             */
            pw.print("OUTPUT_FILE_FORMAT = ");
            switch (getOutputFileType())
            {
                case MULTIFILE:
                    pw.println("RB_FMT");
                    break;
                case HDFEOS:
                    pw.println("HDF_FMT");
                    break;
                case GEOTIFF:
                    pw.println("GEOTIFF_FMT");
                    break;
            }
            /*
             * resampling type
             */
            pw.println();
            pw.print("KERNEL_TYPE (CC/BI/NN) = ");
            try
            {
                pw.println(getResamplingTypeString(getResamplingType()));
            }
            catch (IllegalArgumentException e)
            {
                throw new WriteException( "WritePrm", -2,
                    "Error: invalid resampling type: " +
                    getResamplingTypeString(getResamplingType()),
                    false);
            }
            /*
             * output projection type
             */
            pw.println();
            pw.print("OUTPUT_PROJECTION_NUMBER = ");
            try
            {
                pw.println(getOutputProjectionTypeString());
            }
            catch (IllegalArgumentException e)
            {
                throw new WriteException( "WritePrm", -3,
                    "Error: invalid projection type: "+
                    getOutputProjectionTypeString(), false);
            }
            /*
             * output projection parameters
             */
            pw.println();
            pw.print("OUTPUT_PROJECTION_PARAMETER =");
            for (int i=0; i<15; ++i)
            {
                pw.print(" ");
                pw.print(Double.toString(getOutputProjectionParameter(i)));
            }
            pw.println();
            /* 
             * printing the ellipsoid to the parameter file
             */
            pw.println();
            pw.print("OUTPUT_PROJECTION_SPHERE = ");
            try
            {
                pw.println(getEllipsoidParamNum());
            }
            catch (IllegalArgumentException e)
            {
                throw new WriteException( "WritePrm", -2,
                    "Error: bad output projection sphere: "+
                    getEllipsoidParamNum(), false);
            }
            /*
             * UTM zone
             */
            if(getOutputProjectionTypeString()=="UTM")
            {
                /*
                 * save the UTM zone
                 */
                if (utmZone >= -60 && utmZone <= 60)
                {
                    pw.println();
                    pw.print("OUTPUT_PROJECTION_ZONE = ");
                    try
                    {
                        pw.println(getUTMZone());
                    }
                    catch (IllegalArgumentException e)
                    {
                        throw new WriteException("WritePrm", -1,
                            "Error: invalid UTM zone: " + getUTMZone (),false);
                    }
                }
            }
            /*
             * data type (don't output if SAME_AS_INPUT)
             */
            if (getOutputDataType() != DT_SAME_AS_INPUT)
            {
                pw.println();
                pw.print("OUTPUT_DATA_TYPE = ");
                try
                {
                    pw.println(getOutputDataTypeString());
                }
                catch (IllegalArgumentException e)
                {
                    throw new WriteException( "WritePrm", -2,
                        "Error: bad output data type: "+
                        getOutputDataTypeString(), false);
                }
            }
            /*
             * save pixel size
             */
            if (outputPixelSize != null  && outputPixelSize.length() > 0)
            {
                pw.println();
                pw.print("OUTPUT_PIXEL_SIZE = ");
                try
                {
                    pw.println(getOutputPixelSize());
                }
                catch (IllegalArgumentException e)
                {
                    throw new WriteException( "WritePrm", -2,
                        "Error: writing output pixel size "+
                        getOutputPixelSize(), false);
                }
            }

            /* that's it! */
            pw.close();
            fw.close();
        }
        catch (IOException e)
        {
            throw new WriteException("WritePrm",-1,
                "IOException occurred writing parameter file: "+
                getParameterFilename(),false);
        }
    }

/******************************************************************************
    NAME:     getSDSString
    PURPOSE:  To determine the SDS parameter string for the parameter file
              e.g. SDSName1, 0, 1, 1; SDSName2; SDSName3, 1, 0, 1
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getSDSString()
    {
        String[] sdsBands = getInputBandNamesArray();
        String sdsString = new String ("");
        String[] sdsNames = new String[100];  // Swath products won't have
                                              // more than 100 SDS names
        int[] bandCounts = new int[100];      // Swath products won't have
                                              // more than 100 SDS names
        int[][] bandNums = new int[100][100]; // Swath products won't have
                                     // more than 100 SDS names and 100 bands
        String currSDSName = new String ();
        String tmpString = new String ();
        int currSDS = 0;
        int sdsLocation = 0;
        boolean sdsFound;
        boolean bandFound;

        /* Loop through all the bands */
        for (int i=0; i < getNumBands(); ++i)
        {
            if (isSelectedBand(i))
            {
                /* See if this is a 3D SDS (i.e. has "_b" in the filename) */
                int bandIndex = sdsBands[i].lastIndexOf("_b");
                if (bandIndex > 0)
                {
                    /* _b exists so this is a 3D SDS. Get the SDS substring.
                       If it doesn't already exist in the SDSNames array,
                       add it to the sdsNames list (with bands). If it
                       already exists, then add the band to the SDSName. */
                    currSDSName = sdsBands[i].substring(0,bandIndex);
                    sdsFound = false;
                    for (int j=0; j < currSDS; j++)
                    {
                        if (currSDSName.equals(sdsNames[j]))
                        {
                            /* SDS name is already in the list */
                            sdsFound = true;
                            sdsLocation = j;
                            break;
                        }
                    }

                    /* If SDS name is not in the SDS name list then add it */
                    if (!sdsFound)
                    {
                        /* Add the SDS name to the list */
                        sdsNames[currSDS] = sdsBands[i].substring(0,bandIndex);

                        /* Add the current band to the band list, starting
                           with # in _b# or _b## */
                        tmpString = sdsBands[i].substring(bandIndex+2,
                            sdsBands[i].length());
                        bandNums[currSDS][bandCounts[currSDS]] =
                            Integer.parseInt(tmpString.trim());
                        bandCounts[currSDS]++;

                        /* Increment the SDS pointer */
                        currSDS++;
                    }

                    /* OW, just add the current band to the band list for
                       the SDSName */
                    else
                    {
                        /* Add the current band to the found SDS band list,
                           starting with # in _b# or _b## */
                        tmpString = sdsBands[i].substring(bandIndex+2,
                            sdsBands[i].length());
                        bandNums[sdsLocation][bandCounts[sdsLocation]] =
                            Integer.parseInt(tmpString.trim());
                        bandCounts[sdsLocation]++;
                    }
                }
                else
                {
                    /* _b does not exist so this is a 2D SDS. Add it to
                       the sdsNames list (with no bands) */
                    sdsNames[currSDS] = sdsBands[i];
                    bandCounts[currSDS] = 0;

                    /* Increment the SDS pointer */
                    currSDS++;
                }
            }
        }

        /* Loop through the found SDSs */
        for (int i = 0; i < currSDS; i++)
        {
            /* Add the SDS name to the sdsString */
            sdsString += sdsNames[i];

            /* Loop through the bands in the SDS, if any */
            if (bandCounts[i] != 0)
            {
                /* Loop through the very last band number */
                for (int j = 0; j <= bandNums[i][bandCounts[i]-1]; j++)
                {
                    /* Bands that are specified should be filled with a 1, and
                       bands that are not specified should be filled with a 0
                       in the SDS parameter string. The band numbers that were
                       just read in are 0-based and should be in increasing
                       order. */
                    bandFound = false;
                    for (int k = 0; k < bandCounts[i]; k++)
                    {
                        if (j == bandNums[i][k])
                        {
                            bandFound = true;
                            break;  // stop looking once its found
                        }
                    }

                    /* If this band is in the bandNums list, then print
                       a 1. Otherwise print a 0. */
                    if (bandFound)
                        sdsString += ", 1";
                    else
                        sdsString += ", 0";
                }
            }

            /* If this is not the last SDS, then add a semi-colon to split
               the SDSs apart */
            if (i != currSDS-1)
                sdsString += "; ";
	}

        return sdsString;
    }

/******************************************************************************
    NAME:     getInputFilename
    PURPOSE:  To get input file name
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getInputFilename()
    {
        return inputFilename;
    }

/******************************************************************************
    NAME:     getGeolocFilename
    PURPOSE:  To get geolocation file name
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getGeolocFilename()
    {
        return geolocFilename;
    }

/******************************************************************************
    NAME:     setInputFilename
    PURPOSE:  To set input file name
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputFilename(String value)
    {
        inputFilename = value;
    }

/******************************************************************************
    NAME:     setGeolocFilename
    PURPOSE:  To set geolocation file name
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setGeolocFilename(String value)
    {
        geolocFilename = value;
    }

/******************************************************************************
    NAME:     getOutputFilename
    PURPOSE:  To get output file name
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getOutputFilename()
    {
        return outputFilename;
    }

/******************************************************************************
    NAME:     getOutputPixelSize
    PURPOSE:  To get output pixel size

    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getOutputPixelSize()
    {
        return outputPixelSize;
    }

/******************************************************************************
    NAME:     setOutputPixelSize
    PURPOSE:  To set output pixel size
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputPixelSize(String value)
    {
        outputPixelSize = value;
    }

/******************************************************************************
    NAME:     getOutputPixelSizeUnit
    PURPOSE:  get output pixel size unit

    RETURN VALUE:  integer
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getOutputPixelSizeUnit()
    {
        return outputPixelSizeUnit;
    }

/******************************************************************************
    NAME:     getOutputPixelSizeUnitString
    PURPOSE:  get output pixel size unit string

    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getOutputPixelSizeUnitString()
    {
        return getOutputPixelSizeUnitString (getOutputPixelSizeUnit());
    }

/******************************************************************************
    NAME:     setOutputPixelSizeUnit
    PURPOSE:  set output pixel size unit for given integer value
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputPixelSizeUnit(int value)
    {
        outputPixelSizeUnit = value;
    }

/******************************************************************************
    NAME:     setOutputPixelSizeUnit
    PURPOSE:  set output pixel size unit for given string value
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputPixelSizeUnit(String s) throws IllegalArgumentException
    {
        if (s.equals("METERS"))
        {
            setOutputPixelSizeUnit(METERS);
        }
        else if (s.equals("FEET"))
        {
            setOutputPixelSizeUnit(FEET);
        }
        else if (s.equals("DEGREES"))
        {
            setOutputPixelSizeUnit(DEGREES);
        }
        else if (s.equals("ARC-SEC"))
        {
            setOutputPixelSizeUnit(ARC_SEC);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     setOutputFilename
    PURPOSE:  set output file name
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputFilename(String value)
    {
        outputFilename = value;
    }

/******************************************************************************
    NAME:     getInputFileType
    PURPOSE:  get input file type
    RETURN VALUE:                            integer
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getInputFileType()
    {
        return inputFileType;
    }

/******************************************************************************
    NAME:     setInputFileType
    PURPOSE:  set input file type
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputFileType(int value)
    {
        inputFileType = value;
    }

/******************************************************************************
    NAME:     getOutputFileType
    PURPOSE:  get output file type
    RETURN VALUE:  integer
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getOutputFileType()
    {
        return outputFileType;
    }

/******************************************************************************
    NAME:     setOutputFileType
    PURPOSE:  set output file type
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputFileType(int value)
    {
        outputFileType = value;
    }

/******************************************************************************
    NAME:     getInputDataType
    PURPOSE:  get input data type
    RETURN VALUE:  integer
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getInputDataType()
    {
        return inputDataType;
    }

/******************************************************************************
    NAME:     getInputDataTypeString
    PURPOSE:  get input data type string
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getInputDataTypeString()
    {
        int dataType = getInputDataType();

        if (dataType == INT8)
            return ("INT8");
        else if (dataType == UINT8)
            return ("UINT8");
        else if (dataType == INT16)
            return ("INT16");
        else if (dataType == UINT16)
            return ("UINT16");
        else if (dataType == INT32)
            return ("INT32");
        else if (dataType == UINT32)
            return ("UINT32");
        else if (dataType == FLOAT32)
            return ("FLOAT32");
        else
            throw new IllegalArgumentException();
    }

/******************************************************************************
    NAME:     setInputDataType
    PURPOSE:  set input data type
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputDataType(int value)
    {
        inputDataType = value;
    }

/******************************************************************************
    NAME:     getInputDataTypeArray
    PURPOSE:  get input data type array
    RETURN VALUE:  String[]
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String[] getInputDataTypeArray()
    {
        return inputDataTypeArray;
    }

/******************************************************************************
    NAME:     getInputPixelSizeArray
    PURPOSE:  get input pixel size array
    RETURN VALUE:  double[]
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public double[] getInputPixelSizeArray()
    {
        return inputPixelSizeArray;
    }

/******************************************************************************
    NAME:     getInputNumOfLinesArray
    PURPOSE:  get the input number of lines
    RETURN VALUE:  int[]
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int[] getInputNumOfLinesArray()
    {
        return inputNumOfLinesArray;
    }

/******************************************************************************
    NAME:     getInputNumOfSamplesArray
    PURPOSE:  get the input number of samples
    RETURN VALUE:  int[]
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int[] getInputNumOfSamplesArray()
    {
        return inputNumOfSamplesArray;
    }

/******************************************************************************
    NAME:     getInputBandNamesArray
    PURPOSE:  get input band name
    RETURN VALUE: String[]
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String[] getInputBandNamesArray()
    {
        return inputBandNamesArray;
    }

/******************************************************************************
    NAME:     getInputMinValueArray
    PURPOSE:  get input minimum value
    RETURN VALUE:  double[]
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public double[] getInputMinValueArray()
    {
        return inputMinValueArray;
    }

/******************************************************************************
    NAME:     getInputMaxValueArray
    PURPOSE:  get input maxmum value
    RETURN VALUE:  double[]
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public double[] getInputMaxValueArray()
    {
        return inputMaxValueArray;
    }

/******************************************************************************
    NAME:     getInputBackgroundFillArray
    PURPOSE:  get input background fill value
    RETURN VALUE:  double[]
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public double[] getInputBackgroundFillArray()
    {
        return inputBackgroundFillArray;
    }

/******************************************************************************
    NAME:     setInputDataType
    PURPOSE:  set input data type
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputDataType(String s)
        throws IllegalArgumentException
    {
        if (s.equals("INT8"))
        {
            setInputDataType(INT8);
        }
        else if (s.equals("UINT8"))
        {
            setInputDataType(UINT8);
        }
        else if (s.equals("INT16"))
        {
            setInputDataType(INT16);
        }
        else if (s.equals("UINT16"))
        {
            setInputDataType(UINT16);
        }
        else if (s.equals("INT32"))
        {
            setInputDataType(INT32);
        }
        else if (s.equals("UINT32"))
        {
            setInputDataType(UINT32);
        }
        else if (s.equals("FLOAT32"))
        {
            setInputDataType(FLOAT32);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     getOutputDataType
    PURPOSE:  get output data type
    RETURN VALUE:  int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getOutputDataType()
    {
        return outputDataType;
    }

/******************************************************************************
    NAME:     getOutputDataTypeString
    PURPOSE:  get output data type
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getOutputDataTypeString()
    {
        int dataType = getOutputDataType();

        if (dataType == DT_SAME_AS_INPUT)
            return ("SAME_AS_INPUT");
        else if (dataType == DT_CHAR8)
            return ("CHAR8");
        else if (dataType == DT_UINT8)
            return ("UINT8");
        else if (dataType == DT_INT8)
            return ("INT8");
        else if (dataType == DT_UINT16)
            return ("UINT16");
        else if (dataType == DT_INT16)
            return ("INT16");
        else
            throw new IllegalArgumentException();
    }

/******************************************************************************
    NAME:     getNumBands
    PURPOSE:  get the number of bands
    RETURN VALUE:  int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getNumBands()
    {
        return nbands;
    }

/******************************************************************************
    NAME:     setNumBands
    PURPOSE:  set the number of bands (an overloaded function)
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setNumBands(int value)
    {
        nbands = value;
        selectedBands = new boolean[nbands];
        bandinfo = new BandType[nbands];
        for (int i=0; i<nbands; ++i)
        {
            // by default, all bands are selected
            setSelectedBand(i,true);

            // create blank bandinfo entries
            setBandInfo(i, new BandType());
        }
    }

/******************************************************************************
    NAME:     setNumBands
    PURPOSE:  set the number of bands (an overloaded function)
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setNumBands(String s) throws IllegalArgumentException
    {
        try
        {
            int value = Integer.valueOf(s).intValue();
            setNumBands(value);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException();
        }
    }
        
/******************************************************************************
    NAME:     setUTMZone
    PURPOSE:  set the UTM zone (an overloaded function)
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setUTMZone(String s) throws IllegalArgumentException
    {
        try
        {
            int value = Integer.valueOf(s).intValue();
            setUTMZone(value);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     setInputUTMZone
    PURPOSE:  set the UTM zone (an overloaded function)
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputUTMZone(String s) throws IllegalArgumentException
    {
        try
        {
            int value = Integer.valueOf(s).intValue();
            setInputUTMZone(value);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     isSelectedBand
    PURPOSE:  To check to see if a band selected
    RETURN VALUE:  boolean
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public boolean isSelectedBand(int index)
    {
        return selectedBands[index];
    }

/******************************************************************************
    NAME:     setSelectedBand
    PURPOSE:  set selected bands
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setSelectedBand(int index, boolean value)
    {
        selectedBands[index] = value;
    }

/******************************************************************************
    NAME:     getBandInfo
    PURPOSE:  get infomation for the desired band
    RETURN VALUE:  BandType
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public BandType getBandInfo(int index)
    {
        return bandinfo[index];
    }

/******************************************************************************
    NAME:     getBandNum
    PURPOSE:  get the number of bands
    RETURN VALUE:  int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getBandNum(String bandName)
    {
        for (int i=0; i<inputBandNamesArray.length; ++i)
        {
            if ( bandName.equals(inputBandNamesArray[i]))
            {
                return i;
            }
        }
        return -1;
    }

/******************************************************************************
    NAME:     setBandInfo
    PURPOSE:  set band information
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    private void setBandInfo(int index,BandType value)
    {
        bandinfo[index] = value;
    }

/******************************************************************************
    NAME:     getInputCornerPoint
    PURPOSE:  get input corner point coordinates
    RETURN VALUE:  int[]
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int[] getInputCornerPoint(int index)
    {
        int[] retval = new int[2];
        retval[0] = inputCornerPoints[index][0];
        retval[1] = inputCornerPoints[index][1];
        return retval;
    }

/******************************************************************************
    NAME:     setInputCornerPoint
    PURPOSE:  set input corner point
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputCornerPoint(int index,int[] value)
    {
        inputCornerPoints[index][0] = value[0];
        inputCornerPoints[index][1] = value[1];
    }

/******************************************************************************
    NAME:     setInputCornerPoint
    PURPOSE:  To set input corner point coordinates
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputCornerPoint(int index,int row,int col)
    {
        int[] pt = { row, col };
        setInputCornerPoint(index,pt);
    }

/******************************************************************************
    NAME:     getGringPoint
    PURPOSE:  To retrieve gring points
    RETURN VALUE:  int[2] containing point coordinates
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public double[] getGringPoint(int index)
    {
        double[] retval = new double[2];
        retval[0] = gringPoints[index][0];
        retval[1] = gringPoints[index][1];
        return retval;
    }

/******************************************************************************
    NAME:     setGringPoint
    PURPOSE:  Mutator to set gring points
              value int[2] containing point coordinates
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setGringPoint(int index, double[] value)
    {
        gringPoints[index][0] = value[0];
        gringPoints[index][1] = value[1];
    }

/******************************************************************************
    NAME:     setGringPoint
    PURPOSE:  To set gring point
              value int[2] containing point coordinates
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setGringPoint(int index, String[] value)
        throws IllegalArgumentException
    {
        try
        {
            gringPoints[index][0] = Double.valueOf(value[0]).doubleValue();
            gringPoints[index][1] = Double.valueOf(value[1]).doubleValue();
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException();
        }
    }

 /******************************************************************************
    NAME:     getCornerXYPoint
    PURPOSE:  To retrieve corner XY points
    RETURN VALUE:  return int[2] containing point coordinates

    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public double[] getCornerXYPoint(int index)
    {
        double[] retval = new double[2];
        retval[0] = cornerXYPoints[index][0];
        retval[1] = cornerXYPoints[index][1];
        return retval;
    }

/******************************************************************************
    NAME:     setCornerXYPoint
    PURPOSE:  To set corner XY points
              param value int[2] containing point coordinates
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setCornerXYPoint(int index, double[] value)
    {
        cornerXYPoints[index][0] = value[0];
        cornerXYPoints[index][1] = value[1];
    }

/******************************************************************************
    NAME:    setCornerXYPoint
    PURPOSE: Overloaded function to set the corner XY points
             param value int[2] containing point coordinates
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setCornerXYPoint(int index, String[] value)
        throws IllegalArgumentException
    {
        try
        {
            cornerXYPoints[index][0] = Double.valueOf(value[0]).doubleValue();
            cornerXYPoints[index][1] = Double.valueOf(value[1]).doubleValue();
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     netCornerPoinP
    PURPOSE:  To retrieve corner points
    RETURN VALUE:  return int[2] containing point coordinates
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public double[] getCornerPoint(int index)
    {
        double[] retval = new double[2];
        retval[0] = cornerPoints[index][0];
        retval[1] = cornerPoints[index][1];
        return retval;
    }

/******************************************************************************
    NAME:     setCornerPoint
    PURPOSE:  Overloaded function to set the corner XY points
              param value int[2] containing point coordinates
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setCornerPoint(int index,double[] value)
    {
        cornerPoints[index][0] = value[0];
        cornerPoints[index][1] = value[1];
    }

/******************************************************************************
    NAME:     setCornerPoint
    PURPOSE:  Overloaded function to set the corner XY points
              param value int[2] containing point coordinates
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:        ModisTool
    NOTES:
******************************************************************************/
    public void setCornerPoint(int index,String[] value)
        throws IllegalArgumentException
    {
        try
        {
            cornerPoints[index][0] = Double.valueOf(value[0]).doubleValue();
            cornerPoints[index][1] = Double.valueOf(value[1]).doubleValue();
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     getInputImageExtent
    PURPOSE:  To retrieve the input image extent
    RETURN VALUE:  return int[2] containing point coordinates
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public double[] getInputImageExtent(int index)
    {
        double[] retval = new double[2];
        retval[0] = inputImageExtent[index][0];
        retval[1] = inputImageExtent[index][1];
        return retval;
    }

/******************************************************************************
    NAME:     setInputImageExtent
    PURPOSE:  To set image image extent
              param value int[2] containing point coordinates
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputImageExtent(int index,double[] value)
    {
        inputImageExtent[index][0] = value[0];
        inputImageExtent[index][1] = value[1];
    }

/******************************************************************************
    NAME:     setInputImageExtent
    PURPOSE:  Overloaded function to set the in put image extent
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputImageExtent(int index,String[] value)
            throws IllegalArgumentException
    {
        if ( value.length != 2 )
        {
            throw new IllegalArgumentException();
        }
        try
        {
            double[] arg = new double[2];
            arg[0] = Double.valueOf(value[0]).doubleValue();
            arg[1] = Double.valueOf(value[1 ]).doubleValue();
            setInputImageExtent(index,arg);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     getResamplingType
    PURPOSE:  To retrieve the resampling type
    RETURN VALUE:  integer
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getResamplingType()
    {
        return resamplingType;
    }

/******************************************************************************
    NAME:     getResamplingTypeString
    PURPOSE:  To retrieve the resampling type string
    RETURN VALUE:   String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getResamplingTypeString(int resType)
        throws IllegalArgumentException
    {
        switch (resType)
        {
            case NN:
                return "NN";
            case BI:
                return "BI";
            case CC:
                return "CC";
            default:
                throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     setResamplingType
    PURPOSE:  To set the resampling type
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setResamplingType(int value)
    {
        resamplingType = value;
    }

/******************************************************************************
    NAME:     setResamplingType
    PURPOSE:  Mutator to set resampling type
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setResamplingType(String s) throws IllegalArgumentException
    {
        if ( s.equals("NN") || s.equals("NEAREST_NEIGHBOR"))
        {
            setResamplingType(NN);
        }
        else if (s.equals("BI") || s.equals("BILINEAR"))
        {
            setResamplingType(BI);
        }
        else if (s.equals("CC") || s.equals("CUBIC_CONVOLUTION"))
        {
            setResamplingType(CC);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     getOutputDataTypeString
    PURPOSE:  To retrieve the output data type string
    RETURN VALUE:   String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getOutputDataTypeString(int dataType)
        throws IllegalArgumentException
    {
        switch (dataType)
        {
            case DT_SAME_AS_INPUT:
                return "SAME_AS_INPUT";
            case DT_CHAR8:
                return "CHAR8";
            case DT_UINT8:
                return "UINT8";
            case DT_INT8:
                return "INT8";
            case DT_UINT16:
                return "UINT16";
            case DT_INT16:
                return "INT16";
            default:
                throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     setOutputDataType
    PURPOSE:  Mutator to set data type
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputDataType(String s) throws IllegalArgumentException
    {
        if (s.equals("SAME_AS_INPUT"))
        {
            setOutputDataType(DT_SAME_AS_INPUT);
        }
        else if (s.equals("CHAR8"))
        {
            setOutputDataType(DT_CHAR8);
        }
        else if (s.equals("UINT8"))
        {
            setOutputDataType(DT_UINT8);
        }
        else if (s.equals("INT8"))
        {
            setOutputDataType(DT_INT8);
        }
        else if (s.equals("UINT16"))
        {
            setOutputDataType(DT_UINT16);
        }
        else if (s.equals("INT16"))
        {
            setOutputDataType(DT_INT16);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     setOutputDataType
    PURPOSE:  To set output data type
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputDataType(int value)
    {
        outputDataType = value;
    }

/******************************************************************************
    NAME:     setSpatialSubsetType
    PURPOSE:  To set the spatial subset type
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setSpatialSubsetType(int value)
    {
        spatialsubsetType = value;
    }

/******************************************************************************
    NAME:     setSpatialSubsetType
    PURPOSE:  Mutator to set spatial subset type
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setSpatialSubsetType(String s) throws IllegalArgumentException
    {
        if ( s.equals("INPUT_LAT_LONG"))
        {
            setSpatialSubsetType(SPACE_LAT_LON);
        }
        else if (s.equals("INPUT_LINE_SAMPLE"))
        {
            setSpatialSubsetType(SPACE_LINE_SAMPLE);
        }
        else if (s.equals("OUTPUT_PROJ_COORDS"))
        {
            setSpatialSubsetType(SPACE_PROJ_XY);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     getInputProjectionParameter
    PURPOSE:  To retrieve the input projection parameter
    RETURN VALUE:  double
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public double getInputProjectionParameter(int index)
    {
        return inputProjectionParameters[index];
    }

/******************************************************************************
    NAME:     setInputProjectionParameter
    PURPOSE:  To set in put projection parameter
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputProjectionParameter(int index,double value)
    {
        inputProjectionParameters[index] = value;
    }

/******************************************************************************
    NAME:     setInputProjectionParameter
    PURPOSE:  Mutator to set input projection parameter
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputProjectionParameter(int index,String value)
        throws IllegalArgumentException
    {
        try
        {
            double number = Double.valueOf(value).doubleValue();
            setInputProjectionParameter(index,number);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     getOutputProjectionParameter
    PURPOSE:  To retrieve output projection parameter
    RETURN VALUE:  double
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public double getOutputProjectionParameter(int index)
    {
        return outputProjectionParameters[index];
    }

/******************************************************************************
    NAME:     setOutputProjectionParameter
    PURPOSE:  To set output projection parameter
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputProjectionParameter(int index,double value)
    {
        outputProjectionParameters[index] = value;
    }

/******************************************************************************
    NAME:     setOutputProjectionParameter
    PURPOSE:  To set output projection parameter
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputProjectionParameter(int index,String value)
        throws IllegalArgumentException
    {
        try
        {
            double number = Double.valueOf(value).doubleValue();
            setOutputProjectionParameter(index,number);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException();
        }
    }

/******************************************************************************
    NAME:     getInputEllipsoidCode
    PURPOSE:  To retrieve the input ellipsoid code
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getInputEllipsoidCode()
    {
        return inputEllipsoidCode;
    }

/******************************************************************************
    NAME:     setInputEllipsoidCode
    PURPOSE:  To set input ellipsoid code
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputEllipsoidCode(int value)
    {
        inputEllipsoidCode = value;
    }

/******************************************************************************
    NAME:     getOutputEllipsoidCode
    PURPOSE:  To retrieve output ellipsoid code
    RETURN VALUE:  int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getOutputEllipsoidCode()
    {
        return outputEllipsoidCode;
    }

/******************************************************************************
    NAME:     setOutputEllipsoidCode
    PURPOSE:  To set output ellipsoid code
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputEllipsoidCode(int value)
    {
        if(value == 0)
        {
            Ellipsoid = "Clarke 1866";
        }
        else if(value == 1)
        {
            Ellipsoid = "Clarke 1880";
        }
        else if(value == 2)
        {
            Ellipsoid = "Bessel";
        }
        else if(value == 3)
        {
            Ellipsoid = "International 1967";
        }
        else if(value == 4)
        {
            Ellipsoid = "International 1909";
        }
        else if(value == 5)
        {
            Ellipsoid = "WGS 1972";
        }
        else if(value == 6)
        {
            Ellipsoid = "Everest";
        }
        else if(value == 7)
        {
            Ellipsoid = "WGS 1966";
        }
        else if(value == 8)
        {
            Ellipsoid = "WGS 1984";
        }
        else if(value == 9)
        {
            Ellipsoid = "Airy";
        }
        else if(value == 10)
        {
            Ellipsoid = "Modified Everest";
        }
        else if(value == 11)
        {
            Ellipsoid = "Modified Airy";
        }
        else if(value == 12)
        {
            Ellipsoid = "Walbeck";
        }
        else if(value == 13)
        {
            Ellipsoid = "Southeast Asia";
        }
        else if(value == 14)
        {
            Ellipsoid = "Australian National";
        }
        else if(value == 15)
        {
            Ellipsoid = "Krassovsky";
        }
        else if(value == 16)
        {
            Ellipsoid = "Hough";
        }
        else if(value == 17)
        {
            Ellipsoid = "Mercury 1960";
        }
        else if(value == 18)
        {
            Ellipsoid = "Modified Mercury 1968";
        }
        else if(value == 19)
        {
            Ellipsoid = "Sphere 19";
        }
        else if(value == 20)
        {
            Ellipsoid = "MODIS Sphere";
        }
        else if(value == 21)
        {
            Ellipsoid = "No Ellipsoid";
        }
        else 
        { 
            Ellipsoid = "";
        }
    }

/******************************************************************************
    NAME:     getInputZoneCode
    PURPOSE:  To retrieve input zone code
    RETURN VALUE:  int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development
    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getInputZoneCode()
    {
        return inputZoneCode;
    }

/******************************************************************************
    NAME:     setInputZoneCode
    PURPOSE:  To set input zone code
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputZoneCode(int value)
    {
        inputZoneCode = value;
    }

/******************************************************************************
    NAME:     getOutputZoneCode
    PURPOSE:  To set output zone code
    RETURN VALUE:  int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getOutputZoneCode()
    {
        return outputZoneCode;
    }

/******************************************************************************
    NAME:     setOutputZoneCode
    PURPOSE:  To set output zone code
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputZoneCode(int value)
    {
        outputZoneCode = value;
    }

/******************************************************************************
    NAME:     getInProjectionInfo
    PURPOSE:  To retrieve the projection info object
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public ProjInfo getInProjectionInfo()
    {
        return inProjectionInfo;
    }

/******************************************************************************
    NAME:     setInProjectionInfo
    PURPOSE:  To set projection info
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInProjectionInfo(ProjInfo value)
    {
        inProjectionInfo = value;
    }

/******************************************************************************
    NAME:     getOutProjectionInfo
    PURPOSE:  To retrieve outprojection info
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public ProjInfo getOutProjectionInfo()
    {
        return outProjectionInfo;
    }

/******************************************************************************
    NAME:     setOutProjectionInfo
    PURPOSE:  To set outprojection info
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutProjectionInfo(ProjInfo value)
    {
        outProjectionInfo = value;
    }

/******************************************************************************
    NAME:     getOutputFileInfo
    PURPOSE:  To retrieve output info
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public OutFileType getOutputFileInfo()
    {
        return outputFileInfo;
    }

/******************************************************************************
    NAME:     setOutputFileInfo
    PURPOSE:  To set output file info
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputFileInfo(OutFileType value)
    {
        outputFileInfo = value;
    }

/******************************************************************************
    NAME:     getNumFilesOut
    PURPOSE:  To retrieve number file out
    RETURN VALUE:  int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getNumFilesOut()
    {
        return nfilesOut;
    }

/******************************************************************************
    NAME:     setNumFilesOut
    PURPOSE:  To set number of files out
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setNumFilesOut(int value)
    {
        nfilesOut = value;
    }

/******************************************************************************
    NAME:     getInputProjectionType
    PURPOSE:  To retrieve input projection type
    RETURN VALUE:   int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development
    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getInputProjectionType()
    {
        return inputProjectionType;
    }

/******************************************************************************
    NAME:     getInputProjectionTypeString
    PURPOSE:  To retrieve input projection type string
    RETURN VALUE:   String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getInputProjectionTypeString()
    {
        return getProjectionTypeString(getInputProjectionType());
    }

/******************************************************************************
    NAME:     setInputProjectionType
    PURPOSE:  To set input projection type
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputProjectionType(int value)
    {
        inputProjectionType = value;
    }

/******************************************************************************
    NAME:     setInputProjectionType
    PURPOSE:  Mutator to set input projection Type
    RETURN VALUE:  none
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputProjectionType(String s) throws IllegalArgumentException
    {
        setInputProjectionType(getProjectionTypeInt(s));
    }

/******************************************************************************
    NAME:     getOutputProjectionType
    PURPOSE:  To retrieve input projection type
    RETURN VALUE:  int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------

                      Gregg T. Stubbendieck, Ph.D.   Java  Original development
    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:        ModisTool
    NOTES:
******************************************************************************/
    public int getOutputProjectionType()
    {
        return outputProjectionType;
    }

/******************************************************************************
    NAME:     getOutputProjectionTypeString
    PURPOSE:  To retrieve Output Projection Type String
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getOutputProjectionTypeString()
    {
        return getProjectionTypeString(getOutputProjectionType());
    }

/******************************************************************************
    NAME:     getInputEllipsoid
    PURPOSE:  To retrieve ellipsoid
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getInputEllipsoid()
    {
        return InputEllipsoid;
    }
        
/******************************************************************************
    NAME:     getEllipsoid
    PURPOSE:  To retrieve ellipsoid
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getEllipsoid()
    {
        return Ellipsoid;
    }

/******************************************************************************
    NAME:     getEllipsoidInt
    PURPOSE:  To retrieve ellipsoid value
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:

    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getEllipsoidInt()
    {
        if(Ellipsoid.equals("Airy"))
        {
            return 0;
        }
        else if(Ellipsoid.equals("Australian National"))
        {
            return 1;
        }
        else if(Ellipsoid.equals("Bessel"))
        {
            return 2;
        }
        else if(Ellipsoid.equals("Clarke 1866"))
        {
            return 3;
        }
        else if(Ellipsoid.equals("Clarke 1880"))
        {
            return 4;
        }
        else if(Ellipsoid.equals("Everest"))
        {
            return 5;
        }
        else if(Ellipsoid.equals("GRS 1980"))
        {
            return 6;
        }
        else if(Ellipsoid.equals("Hough"))
        {
            return 7;
        }
        else if(Ellipsoid.equals("International 1909"))
        {
            return 8;
        }
        else if(Ellipsoid.equals("International 1967"))
        {
            return 9;
        }
        else if(Ellipsoid.equals("Krassovsky"))
        {
            return 10;
        }
        else if(Ellipsoid.equals("Mercury 1960"))
        {
            return 11;
        }
        else if(Ellipsoid.equals("Modified Airy"))
        {
            return 12;
        }
        else if(Ellipsoid.equals("Modified Everest"))
        {
            return 13;
        }
        else if(Ellipsoid.equals("Modified Mercury 1968"))
        {
            return 14;
        }
        else if(Ellipsoid.equals("MODIS Sphere"))
        {
            return 15;
        }
        else if(Ellipsoid.equals("Southeast Asia"))
        {
            return 16;
        }
        else if(Ellipsoid.equals("Sphere 19"))
        {
            return 17;
        }
        else if(Ellipsoid.equals("Walbeck"))
        {
            return 18;
        }
        else if(Ellipsoid.equals("WGS 1966"))
        {
            return 19;
        }
        else if(Ellipsoid.equals("WGS 1972"))
        {
            return 20;
        }
        else if(Ellipsoid.equals("WGS 1984"))
        {
            return 21;
        }
        else if(Ellipsoid.equals("No Ellipsoid"))
        {
            return 22;
        }
        else 
        { 
            return 23;
        }
    }
    
/******************************************************************************
    NAME:     getEllipsoidParamNum
    PURPOSE:  To retrieve ellipsoid number for the parameter file
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:

    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getEllipsoidParamNum()
    {
        if(Ellipsoid.equals("Clarke 1866"))
        {
            return 0;
        }
        else if(Ellipsoid.equals("Clarke 1880"))
        {
            return 1;
        }
        else if(Ellipsoid.equals("Bessel"))
        {
            return 2;
        }
        else if(Ellipsoid.equals("International 1967"))
        {
            return 3;
        }
        else if(Ellipsoid.equals("International 1909"))
        {
            return 4;
        }
        else if(Ellipsoid.equals("WGS 1972"))
        {
            return 5;
        }
        else if(Ellipsoid.equals("Everest"))
        {
            return 6;
        }
        else if(Ellipsoid.equals("WGS 1966"))
        {
            return 7;
        }
        else if(Ellipsoid.equals("GRS 1980") || Ellipsoid.equals("WGS 1984"))
        {
            return 8;
        }
        else if(Ellipsoid.equals("Airy"))
        {
            return 9;
        }
        else if(Ellipsoid.equals("Modified Everest"))
        {
            return 10;
        }
        else if(Ellipsoid.equals("Modified Airy"))
        {
            return 11;
        }
        else if(Ellipsoid.equals("Walbeck"))
        {
            return 12;
        }
        else if(Ellipsoid.equals("Southeast Asia"))
        {
            return 13;
        }
        else if(Ellipsoid.equals("Australian National"))
        {
            return 14;
        }
        else if(Ellipsoid.equals("Krassovsky"))
        {
            return 15;
        }
        else if(Ellipsoid.equals("Hough"))
        {
            return 16;
        }
        else if(Ellipsoid.equals("Mercury 1960"))
        {
            return 17;
        }
        else if(Ellipsoid.equals("Modified Mercury 1968"))
        {
            return 18;
        }
        else if(Ellipsoid.equals("Sphere 19"))
        {
            return 19;
        }
        else if(Ellipsoid.equals("MODIS Sphere"))
        {
            return 20;
        }
        else if(Ellipsoid.equals("No Ellipsoid"))
        {
            return 21;
        }
        else
        {
            return 22;
        }
    }
    
/******************************************************************************
    NAME:     setEllipsoid
    PURPOSE:  To set the ellipsoid
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setEllipsoid(String value)
    {
        Ellipsoid = value;
    }
        
/******************************************************************************
    NAME:     setInputEllipsoid
    PURPOSE:  To set the input ellipsoid
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputEllipsoid(String value)
    {
        InputEllipsoid = value;
    }
        
/******************************************************************************
    NAME:     setOutputProjectionType
    PURPOSE:  To set output projection type
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputProjectionType(int value)
    {
        outputProjectionType = value;
    }

/******************************************************************************
    NAME:     setOutputProjectionType
    PURPOSE:  Mutator to set output projection type
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setOutputProjectionType(String s)
    {
        setOutputProjectionType(getProjectionTypeInt(s));
    }

/******************************************************************************
    NAME:     setUTMZone
    PURPOSE:  To set UTm zone specified by user in GUI
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setUTMZone(int value)
    {
        utmZone = value;
    }

/******************************************************************************
    NAME:     setUTMZone
    PURPOSE:  To set UTm zone specified by user in GUI
    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public void setInputUTMZone(int value)
    {
        InpututmZone = value;
    }

/******************************************************************************
    NAME:     getUTMZone
    PURPOSE:  To retrieve UTM zone
    RETURN VALUE:  int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getUTMZone()
    {
        return utmZone;
    }

/******************************************************************************
    NAME:     getUTMZone
    PURPOSE:  To retrieve UTM zone
    RETURN VALUE:  int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getInputUTMZone()
    {
        return InpututmZone;
    }

/******************************************************************************
    NAME:     getUTMZoneString
    PURPOSE:  To retrieve UTM zone string
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getUTMZoneString()
    {
        if (utmZone >= -60 && utmZone <= 60)
            return Integer.toString(getUTMZone());
        else
            return "";
    }

/******************************************************************************
    NAME:     getInputBandName
    PURPOSE:  To retrieve inpout band name
    RETURN VALUE:   String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:        ModisTool
    NOTES:
******************************************************************************/
    public String getInputBandName (int index)
    {
        return inputBandNamesArray[index];
    }

/******************************************************************************
    NAME:     getInputNumLine
    PURPOSE:  To retrieve input number of lines
    RETURN VALUE:   int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getInputNumLine(int index)
    {
        return inputNumOfLinesArray[index];
    }

/******************************************************************************
    NAME:     getInputNumSample
    PURPOSE:  To retrieve input number sample
    RETURN VALUE:   int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public int getInputNumSample(int index)
    {
        return inputNumOfSamplesArray[index];
    }

/******************************************************************************
    NAME:     getInputPixelSize
    PURPOSE:  To retrieve input pixel size
    RETURN VALUE:   int
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public double getInputPixelSize(int index)
    {
        return inputPixelSizeArray[index];
    }

/******************************************************************************
    NAME:     getInputDataType
    PURPOSE:  To retrieve input data type
    RETURN VALUE:  String
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public String getInputDataType (int index)
    {
        return inputDataTypeArray[index];
    }

/******************************************************************************
    NAME:     getInputMinValue
    PURPOSE:  To retrieve input min value
    RETURN VALUE:   double
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public double getInputMinValue (int index)
    {
        return inputMinValueArray[index];
    }

/******************************************************************************
    NAME:     getInputMaxValue
    PURPOSE:  To retrieve input max value
    RETURN VALUE:  double
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public double getInputMaxValue(int index)
    {
        return inputMaxValueArray[index];
    }

/******************************************************************************
    NAME:     getInputBackgroundFill
    PURPOSE:  To retrieve input background fill
    RETURN VALUE:  double
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public double getInputBackgroundFill(int index)
    { 
        return inputBackgroundFillArray[index];
    }
}
