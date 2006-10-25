// ----------------------------------------------------------------------------
// HydroBase_DrawingArea_WISDiagram - the drawing area onto which the 
//	wis diagram is drawn.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2003-12-15	J. Thomas Sapienza, RTi	Initial changelog.  
// 2004-05-27	JTS, RTi		Renamed from 
//					HydroBase_DrawingArea_WISNetwork
//					to HydroBase_DrawingArea_WISDiagram.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.GR.GRAspect;
import RTi.GR.GRJComponentDrawingArea;
import RTi.GR.GRLimits;
import RTi.GR.GRUnits;

/**
Class that is used as the drawing area for a WIS Diagram.
*/
public class HydroBase_DrawingArea_WISDiagram 
extends GRJComponentDrawingArea {

/**
Constructor.
@param dev the HydroBase_Device_WISDiagram to use this drawing area with.
@param drawingLimits the drawingLimits of the drawing area.
*/
public HydroBase_DrawingArea_WISDiagram(HydroBase_Device_WISDiagram dev, 
GRLimits drawingLimits) {
	super(dev, "HydroBase_DrawingArea_WISDiagram", GRAspect.TRUE, 
	drawingLimits, GRUnits.DEVICE, GRLimits.DEVICE, drawingLimits);
}

}
