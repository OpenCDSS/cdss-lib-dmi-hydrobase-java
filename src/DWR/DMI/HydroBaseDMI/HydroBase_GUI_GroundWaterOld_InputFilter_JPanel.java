// HydroBase_GUI_GroundWaterOld_InputFilter_JPanel - Input filter for ground water queries.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2019 Colorado Department of Natural Resources

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

//-----------------------------------------------------------------------------
// HydroBase_GUI_GroundWaterOld_InputFilter_JPanel - Input filter for ground 
//	water queries.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 2005-01-10	J. Thomas Sapienza, RTi	Initial version.
// 2005-02-01	JTS, RTi		Renamed from
//					HydroBase_InputFilter_JPanel_GroundWater
// 2005-06-28	JTS, RTi		Renamed from HydroBase_GUI_GroundWater_
//					InputFilter_JPanel.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.event.MouseListener;

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilterCriterionType;
import RTi.Util.GUI.InputFilter_JPanel;

import RTi.Util.String.StringUtil;

/**
This class is an input filter for querying ground water data in the Ground Water GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_GroundWaterOld_InputFilter_JPanel
extends InputFilter_JPanel {

/**
Constructor.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location
entry text field is clicked in.  Cannot be null.
*/
public HydroBase_GUI_GroundWaterOld_InputFilter_JPanel(HydroBaseDMI dmi, MouseListener listener) {
	String rd = dmi.getFieldRightEscape();
	String ld = dmi.getFieldLeftEscape();

	InputFilter filter = null;

	List<InputFilter> filters = new Vector<InputFilter>();

	filters.add(new InputFilter("", "", StringUtil.TYPE_STRING, null, null, false));

	// create the input filter for the PLSS Location
	filter = new InputFilter(
		HydroBase_GUI_Util._PLSS_LOCATION_LABEL,
		HydroBase_GUI_Util._PLSS_LOCATION, 
		HydroBase_GUI_Util._PLSS_LOCATION, StringUtil.TYPE_STRING,
		null, null, false);
	// all constraints other than EQUALS are removed because PLSS Locations
	// are compared in a special way
	filter.removeConstraint(InputFilterCriterionType.INPUT_ONE_OF.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_STARTS_WITH.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_ENDS_WITH.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_CONTAINS.toString());
	// the PLSS Location text field is not editable because users must go
	// through the PLSS Location JDialog to build a location
	filter.setInputJTextFieldEditable(false);
	// this listener must be set up so that the location builder dialog
	// can be opened when the PLSS Location text field is clicked on.
	filter.addInputComponentMouseListener(listener);
	filter.setInputComponentToolTipText("Click in this field to build "
		+ "a PLSS Location to use as a query constraint.");
	filter.setInputJTextFieldWidth(20);
	filters.add(filter);

	filters.add(new InputFilter("Structure ID",
		HydroBase_GUI_Util._STRUCTURE_TABLE_NAME + "." + ld 
		+ "id" + rd, "id", StringUtil.TYPE_INTEGER,
		null, null, false));
	filters.add(new InputFilter("Structure Name",
		HydroBase_GUI_Util._STRUCTURE_TABLE_NAME + "." + ld 
		+ "str_name" + rd, "str_name", StringUtil.TYPE_STRING,
		null, null, false));

	setToolTipText("<html>HydroBase queries can be filtered<br>based on ground water data.</html>");
	setInputFilters(filters, 3, 4);
}

}
