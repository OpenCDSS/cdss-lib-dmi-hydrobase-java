// HydroBase_DrawingArea_WISDiagram - the drawing area onto which the wis diagram is drawn.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2023 Colorado Department of Natural Resources

CDSS HydroBase Database Java Library is free software:  you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    CDSS HydroBase Database Java Library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with CDSS HydroBase Database Java Library.  If not, see <https://www.gnu.org/licenses/>.

NoticeEnd */

package DWR.DMI.HydroBaseDMI;

import RTi.GR.GRAspectType;
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
	super(dev, "HydroBase_DrawingArea_WISDiagram", GRAspectType.TRUE, 
	drawingLimits, GRUnits.DEVICE, GRLimits.DEVICE, drawingLimits);
}

}