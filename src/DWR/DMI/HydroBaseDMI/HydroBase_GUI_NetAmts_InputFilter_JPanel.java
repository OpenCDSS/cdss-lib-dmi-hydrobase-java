// HydroBase_GUI_NetAmts_InputFilter_JPanel - Input filter for net amounts queries.

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
// HydroBase_GUI_NetAmts_InputFilter_JPanel - Input filter for net amounts 
//	queries.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 2005-01-10	J. Thomas Sapienza, RTi	Initial version.
// 2005-02-01	JTS, RTi		Renamed from
//					HydroBase_InputFilter_JPanel_NetAmts
// 2005-02-08	JTS, RTi		* Added the listener to the constructor.
//					* Added all fields from the GUI.
// 2005-02-11	JTS, RTi		Added boolean to constructor which can
//					be set to false if not doing a 
//					query by example (eg, the water rights
//					gui was opened from the structure gui).
// 2005-07-11	JTS, RTi		Now use structure.strtype instead of
//					xstrtype.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.event.MouseListener;

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;

import RTi.Util.String.StringUtil;

/**
This class is an input filter for querying net amts data from the Water Rights GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_NetAmts_InputFilter_JPanel
extends InputFilter_JPanel {

/**
Constructor.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location
entry text field is clicked in.  Cannot be null.
@param queryByExample if true, then the filter should be usable.  If false,
then the GUI was opened from another screen with particular query settings 
that should not be changed.
*/
public HydroBase_GUI_NetAmts_InputFilter_JPanel(HydroBaseDMI dmi, 
MouseListener listener, boolean queryByExample) {
	if (queryByExample) {
		setupNormalFilters(dmi, listener);
	}
	else {
		setupStructureFilter(dmi);
	}
}

/**
Sets up filters for regular GUI operation when query by example must be done.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location
entry text field is clicked in.  Cannot be null.
*/
private void setupNormalFilters(HydroBaseDMI dmi, MouseListener listener) {
	String rd = dmi.getFieldRightEscape();
	String ld = dmi.getFieldLeftEscape();

	InputFilter filter = null;

	List<InputFilter> filters = new Vector<InputFilter>();

	String tableName = HydroBase_GUI_Util._NET_AMOUNTS_TABLE_NAME + "." + ld;

	filters.add(new InputFilter("", "", StringUtil.TYPE_STRING,	null, null, false));

	filter = new InputFilter("Adj Date",
		tableName + "adj_date" + rd, "adj_date", StringUtil.TYPE_STRING,
		null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);
	filters.add(filter);

	filters.add(new InputFilter("Adj Type",
		tableName + "adj_type" + rd, "adj_type", StringUtil.TYPE_STRING,
		null, null, false));
	filters.add(new InputFilter("Administration Number",
		tableName + "admin_no" + rd, "admin_no", StringUtil.TYPE_DOUBLE,
		null, null, false));
	
	filter = new InputFilter("Apro Date",
		tableName + "apro_date" + rd, "apro_date", 
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);

	List<HydroBase_CountyRef> counties = dmi.getCountyRef();
	HydroBase_CountyRef county = null;
	int size = counties.size();
	List<String> v1 = new Vector<String>();
	List<String> v2 = new Vector<String>();
	for (int i = 0; i < size; i++) {
		county = counties.get(i);
		if (county.getCty() > 0) {
			v1.add(county.getCounty());
			v2.add("" + county.getCty());
		}
	}
		
	filters.add(new InputFilter("County",
		tableName + "cty" + rd, "cty", StringUtil.TYPE_STRING,
		v1, v2, false));

	filters.add(new InputFilter("Decreed Rate (Abs), cfs",
		tableName + "net_rate_abs" + rd, "net_rate_abs",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Decreed Rate (APEX), cfs",
		tableName + "net_rate_apex" + rd, "net_rate_apex",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Decreed Rate (Cond), cfs",
		tableName + "net_rate_cond" + rd, "net_rate_cond",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Decreed Volume (Abs), acft",
		tableName + "net_vol_abs" + rd, "net_vol_abs",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Decreed Volume (APEX), acft",
		tableName + "net_vol_apex" + rd, "net_vol_apex",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Decreed Volume (Cond), acft",
		tableName + "net_vol_cond" + rd, "net_vol_cond",
		StringUtil.TYPE_DOUBLE, null, null, false));

	// create the input filter for the PLSS Location
	filter = new InputFilter("Location",
		"PLSS_Location", "PLSS_Location", StringUtil.TYPE_STRING,
		null, null, false);
	// all constraints other than EQUALS are removed because PLSS Locations
	// are compared in a special way
	filter.removeConstraint(InputFilter.INPUT_ONE_OF);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);
	// the PLSS Location text field is not editable because users must go
	// through the PLSS Location JDialog to build a location
	filter.setInputJTextFieldEditable(false);
	// this listener must be set up so that the location builder dialog
	// can be opened when the PLSS Location text field is clicked on.
	filter.addInputComponentMouseListener(listener);
	filter.setInputComponentToolTipText("Click in this field to build a PLSS Location to use as a query constraint.");
	filter.setInputJTextFieldWidth(20);
	filters.add(filter);

	filters.add(new InputFilter("Order Number",
		tableName + "order_no" + rd, "order_no", 
		StringUtil.TYPE_INTEGER, null, null, false));		

	filter = new InputFilter("Padj Date",
		tableName + "padj_date" + rd, "padj_date",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);				
	filters.add(filter);

	filters.add(new InputFilter("Prior Case Number",
		tableName + "pri_case_no" + rd, "pri_case_no",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Structure ID",
		tableName + "id" + rd, "id", StringUtil.TYPE_INTEGER,
		null, null, false));

	List<HydroBase_DssStructureType> strTypes = dmi.getDssStructureTypeList();
	HydroBase_DssStructureType strType = null;
	v1 = new Vector<String>();
	v2 = new Vector<String>();
	for (int i = 0; i < strTypes.size(); i++) {
		strType = strTypes.get(i);
		v1.add(strType.getStr_type() + " - " + strType.getStr_type_desc());
		v2.add(strType.getStr_type());
	}
		
	filters.add(new InputFilter("Structure Type",
		ld + "structure.strtype" + rd, "strtype", 
		StringUtil.TYPE_STRING, v1, v2, false));
	filters.add(new InputFilter("Use",
		tableName + "use" + rd, "use", StringUtil.TYPE_STRING,
		null, null, false));
	filters.add(new InputFilter("Water Right Name",
		tableName + "wr_name" + rd, "wr_name", StringUtil.TYPE_STRING,
		null, null, false));
	filters.add(new InputFilter("Water Source",
		tableName + "wd_stream_name" + rd, "wd_stream_name",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("WD",
		tableName + "wd" + rd, "wd",
		StringUtil.TYPE_INTEGER, null, null, false));		

	setToolTipText("<html>HydroBase queries can be filtered<br>based on ground water data.</html>");
	setInputFilters(filters, 3, 22);
}

/**
Sets up filters for GUI operation when query by example cannot be done.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location
entry text field is clicked in.  Cannot be null.
*/
private void setupStructureFilter(HydroBaseDMI dmi) {
	String rd = dmi.getFieldRightEscape();
	String ld = dmi.getFieldLeftEscape();

	InputFilter filter = null;

	List<InputFilter> filters = new Vector<InputFilter>();

	String tableName = HydroBase_GUI_Util._NET_AMOUNTS_TABLE_NAME + "." + ld;

	filter = new InputFilter("Structure Number",
		tableName + "structure_num" + rd, "structure_num", 
		StringUtil.TYPE_INTEGER, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_BETWEEN);
	filter.removeConstraint(InputFilter.INPUT_LESS_THAN);
	filter.removeConstraint(InputFilter.INPUT_GREATER_THAN);
	filter.removeConstraint(InputFilter.INPUT_LESS_THAN_OR_EQUAL_TO);
	filter.removeConstraint(InputFilter.INPUT_GREATER_THAN_OR_EQUAL_TO);
	filter.setInputJTextFieldEditable(false);
	filters.add(filter);

	setToolTipText("<html>HydroBase queries can be filtered<br>based on ground water data.</html>");
	setInputFilters(filters, 1, 1);
}

}
