/******************************************************************************
    NAME:     Class ModisSwathTool
    PURPOSE:  The purpose of this class is to provide an entry point (main
              function) to start.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Reason
    -------  ----     ---------------------------    --------------------
                      Gregg T. Stubbendieck, Ph.D.   Original development
                      Gail Schmidt                   Modified for MRTSwath

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    Java is inherently platform indepentent so the compiled byte code can be
    executed on any of platforms (e.g. Windows, Unix, and Linux). Virtually,
    there is no limitation on running Java byte codes. However there is a
    compiler requirement regarding JDK package (version 2.0+).

    PROJECT:  Modis Re-Projection Tool
    NOTES:
******************************************************************************/
package mrtswath;

import javax.swing.*;
import java.io.*;

public class ModisSwathTool
{
    private ModisController controller;

/******************************************************************************
    NAME:     ModisSwathTool()
    PURPOSE:  To create an instance of controller and initialize it.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public ModisSwathTool()
    {
        try
        {
            controller = ModisController.getInstance();
            controller.initialize();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

/******************************************************************************
    NAME:       main()
    PURPOSE:  This is the main entry point.  It instantiates an object of
              ModisSwathTool, which in turn instantiates controller.  It takes
              over argument.

    RETURN VALUE:
    PROGRAM HISTORY
    Version  Date     Programmer                     Code  Reason
    -------  ----     ---------------------------    ----  --------------------
                      Gregg T. Stubbendieck, Ph.D.   Java  Original development

    COMPUTER HARDWARE AND/OR SOFTWARE LIMITATIONS:
    PROJECT:  MRTSwath
    NOTES:
******************************************************************************/
    public static void main(String[] args)
    {
        new ModisSwathTool();
    }
}
