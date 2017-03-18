//-----------------------------------------------------------------------------
// HydroBase_GUI_Structure_InputFilter_JPanel - Input filter for structure 
//	queries.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 2005-01-10	J. Thomas Sapienza, RTi	Initial version.
// 2005-01-31	JTS, RTi		* Location queries are now handled via
//					  Input Filters.
//					* A MouseListener is now passed to the
//					  constructor for doing location builds.
// 2005-02-01	JTS, RTi		Renamed from 
//					HydroBase_InputFilter_JPanel_Structure
// 2005-02-03	JTS, RTi		Added secondary where clauses for
//					the SPFlex queries.
// 2005-06-28	JTS, RTi		* Removed "Decreed Amount" from the 
//					  stored procedure filters.
//					* CIU now displays the code abbreviation
//					  next to the description.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.event.MouseListener;

import java.util.List;
import java.util.Vector;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;

import RTi.Util.String.StringUtil;

/**
This class is an input filter for querying structure data from the Structure GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_Structure_InputFilter_JPanel
extends InputFilter_JPanel {

/**
Constructor.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location
entry text field is clicked in.  Cannot be null.
*/
public HydroBase_GUI_Structure_InputFilter_JPanel(HydroBaseDMI dmi, 
MouseListener listener) {
	if (dmi.useStoredProcedures()) {
		setupStoredProceduresFilters(dmi, listener);
	}
	else {
		setupOldFilters(dmi, listener);
	}
}

/**
Builds a filter for querying data from an SQL database.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location
entry text field is clicked in.  Cannot be null.
*/
private void setupOldFilters(HydroBaseDMI dmi, 
MouseListener listener) {
	String rd = dmi.getFieldRightEscape();
	String ld = dmi.getFieldLeftEscape();

	List<InputFilter> filters = new Vector<InputFilter>();

	List<String> v1 = null;
	List<String> v2 = null;

	filters.add(new InputFilter("", "", StringUtil.TYPE_STRING, null, null, false));

	v1 = new Vector<String>();
	v2 = new Vector<String>();
	List<HydroBase_RefCIU> cius = dmi.getCIUVector();
	int size = cius.size();
	HydroBase_RefCIU ciu = null;
	for (int i = 0; i < size; i++) {
		ciu = cius.get(i);
		v1.add(ciu.getCode() + " - " + ciu.getDescription());
		v2.add(ciu.getCode());
	}

	String structureTableName = HydroBase_GUI_Util._STRUCTURE_TABLE_NAME + "." + ld;
	String geolocTableName = HydroBase_GUI_Util._GEOLOC_TABLE_NAME + "." + ld;

	InputFilter filter = new InputFilter("CIU",
		structureTableName + "ciu" + rd, "ciu", 
		StringUtil.TYPE_STRING, v1, v2, false);
	filter.setNumberInputJComboBoxRows(InputFilter.JCOMBOBOX_ROWS_DISPLAY_ALL);
	filters.add(filter);

/*
	filters.add(new InputFilter("Decreed Amount, acft",
		structureTableName + "XXX" + rd, StringUtil.TYPE_DOUBLE,
		null, null, false));
	filters.add(new InputFilter("Decreed Amount, any units",
		structureTableName + "XXX" + rd, StringUtil.TYPE_DOUBLE,
		null, null, false));
	filters.add(new InputFilter("Decreed Amount, cfs",
		structureTableName + "XXX" + rd, StringUtil.TYPE_DOUBLE,
		null, null, false));
*/

	filters.add(new InputFilter("Latitude",
		geolocTableName + "latdecdeg" + rd, "latdecdeg", 
		StringUtil.TYPE_DOUBLE, null, null, false));

	filters.add(new InputFilter("Longitude",
		geolocTableName + "longdecdeg" + rd, "longdecdeg", 
		StringUtil.TYPE_DOUBLE, null, null, false));

	filters.add(new InputFilter("Owner Name",
		"rolodex." + ld + "full_name" + rd, "full_name",
		StringUtil.TYPE_STRING, null, null, false));

	// create the input filter for the PLSS Location
	filter = new InputFilter(
		HydroBase_GUI_Util._PLSS_LOCATION_LABEL,
		HydroBase_GUI_Util._PLSS_LOCATION, 
		HydroBase_GUI_Util._PLSS_LOCATION, StringUtil.TYPE_STRING,
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
	filter.setInputComponentToolTipText("Click in this field to build "
		+ "a PLSS Location to use as a query constraint.");
	filter.setInputJTextFieldWidth(20);
	filters.add(filter);

	filters.add(new InputFilter("Source",
		"wd_water." + ld + "strname" + rd, "strname",
		StringUtil.TYPE_STRING, null, null, false));

	if (dmi.isDatabaseVersionAtLeast(HydroBaseDMI.VERSION_19990305)) {
		filters.add(new InputFilter("Stream Mile",
			geolocTableName + "str_mile" + rd, "str_mile", 
			StringUtil.TYPE_DOUBLE, null, null, false));
	}
	else {
		filters.add(new InputFilter("Stream Mile",
			structureTableName + "abbrev" + rd, "abbrev", 
			StringUtil.TYPE_DOUBLE, null, null, false));
	}

	filters.add(new InputFilter("Structure ID",
		structureTableName + "id" + rd, "id", StringUtil.TYPE_INTEGER,
		null, null, false));

	filters.add(new InputFilter("Structure Name",
		structureTableName + "str_name" + rd, "str_name", 
		StringUtil.TYPE_STRING, null, null, false));
	
	List<HydroBase_DssStructureType> structureTypes = dmi.getDssStructureTypeList();
	HydroBase_DssStructureType type = null;
	size = structureTypes.size();
	v1 = new Vector<String>();
	v2 = new Vector<String>();

	for (int i = 0; i < size; i++) {
		type = structureTypes.get(i);
		if (DMIUtil.isMissing(type.getRpt_code())) {
			v1.add(type.getStr_type_desc());
			v2.add(type.getStr_type());
		}
	}

	filter = new InputFilter("Structure Type",
		structureTableName + "str_type" + rd, "str_type", 
		StringUtil.TYPE_STRING, v1, v2, false);
	filter.setNumberInputJComboBoxRows(InputFilter.JCOMBOBOX_ROWS_DISPLAY_ALL);
	filters.add(filter);

	v1 = new Vector<String>();
	v1.add("1");

	filters.add(new InputFilter("Transbasin",
		structureTableName + "transbsn" + rd, "transbsn", 
		StringUtil.TYPE_STRING, v1, v1, false));

	filters.add(new InputFilter("UTM X",
		geolocTableName + "utm_x" + rd, "utm_x", 
		StringUtil.TYPE_DOUBLE, null, null, false));
	
	filters.add(new InputFilter("UTM Y",
		geolocTableName + "utm_y" + rd, "utm_y", 
		StringUtil.TYPE_DOUBLE, null, null, false));

	filters.add(new InputFilter("WD",
		structureTableName + "wd" + rd, "wd", 
		StringUtil.TYPE_INTEGER, null, null, false));

	setToolTipText("<html>HydroBase queries can be filtered<br>based on structure data.</html>");
	setInputFilters(filters, 3, 18);
}

/**
Builds a filter for querying data from a database that uses stored procedures.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location
entry text field is clicked in.  Cannot be null.
*/
private void setupStoredProceduresFilters(HydroBaseDMI dmi, 
MouseListener listener) {
	String rd = dmi.getFieldRightEscape();
	String ld = dmi.getFieldLeftEscape();

	List<InputFilter> filters = new Vector<InputFilter>();

	List<String> v1 = null;
	List<String> v2 = null;

	filters.add(new InputFilter("", "", StringUtil.TYPE_STRING, null, null, false));

	String structureTableName = HydroBase_GUI_Util._STRUCTURE_TABLE_NAME + "." + ld;
	String geolocTableName = HydroBase_GUI_Util._GEOLOC_TABLE_NAME + "." + ld;

	v1 = new Vector<String>();
	v2 = new Vector<String>();
	List<HydroBase_RefCIU> cius = dmi.getCIUVector();
	int size = cius.size();
	HydroBase_RefCIU ciu = null;
	for (int i = 0; i < size; i++) {
		ciu = cius.get(i);
		v1.add(ciu.getCode() + " - " + ciu.getShort_desc());
		v2.add(ciu.getCode());
	}

	InputFilter filter = new InputFilter("CIU",
		structureTableName + "ciu" + rd, "ciu", 
		StringUtil.TYPE_STRING, v1, v2, false);
	filter.setNumberInputJComboBoxRows(InputFilter.JCOMBOBOX_ROWS_DISPLAY_ALL);
	filters.add(filter);

	filters.add(new InputFilter("Decreed rate (abs)",
		"dcr_rate_abs", "dcr_rate_abs", StringUtil.TYPE_DOUBLE, 
		null, null, false));
	filters.add(new InputFilter("Decreed rate (cond)",
		"dcr_rate_cond", "dcr_rate_cond", StringUtil.TYPE_DOUBLE, 
		null, null, false));
	filters.add(new InputFilter("Decreed APEX rate (abs)",
		"dcr_rate_APEX_abs", "dcr_rate_APEX_abs", 
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Decreed APEX rate (cond)",
		"dcr_rate_APEX_cond", "dcr_rate_APEX_cond", 
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Decreed rate (total)",
		"dcr_rate_total", "dcr_rate_total", StringUtil.TYPE_DOUBLE, 
		null, null, false));
	filters.add(new InputFilter("Decreed vol (abs)",
		"dcr_vol_abs", "dcr_vol_abs", StringUtil.TYPE_DOUBLE, 
		null, null, false));
	filters.add(new InputFilter("Decreed vol (cond)",
		"dcr_vol_cond", "dcr_vol_cond", StringUtil.TYPE_DOUBLE, 
		null, null, false));
	filters.add(new InputFilter("Decreed APEX vol (abs)",
		"dcr_vol_apex_abs", "dcr_vol_apex_abs", StringUtil.TYPE_DOUBLE, 
		null, null, false));
	filters.add(new InputFilter("Decreed APEX vol (cond)",
		"dcr_vol_apex_cond", "dcr_vol_apex_cond", 
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Decreed vol (total)",
		"dcr_vol_total", "dcr_vol_total", StringUtil.TYPE_DOUBLE, 
		null, null, false));

	filters.add(new InputFilter("Latitude",
		geolocTableName + "latdecdeg" + rd, "latdecdeg", 
		StringUtil.TYPE_DOUBLE, null, null, false));

	// create the input filter for the PLSS Location
	filter = new InputFilter(
		HydroBase_GUI_Util._LOCATION,
		HydroBase_GUI_Util._PLSS_LOCATION, 
		HydroBase_GUI_Util._PLSS_LOCATION, StringUtil.TYPE_STRING,
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
	filter.setInputComponentToolTipText("Click in this field to build "
		+ "a location to use as a query constraint.");
	filter.setInputJTextFieldWidth(20);
	filters.add(filter);

	filters.add(new InputFilter("Longitude",
		geolocTableName + "longdecdeg" + rd, "longdecdeg", 
		StringUtil.TYPE_DOUBLE, null, null, false));

	filters.add(new InputFilter("Owner Name",
		"rolodex." + ld + "full_name" + rd, "full_name",
		StringUtil.TYPE_STRING, null, null, false));

	if (dmi.isDatabaseVersionAtLeast(HydroBaseDMI.VERSION_19990305)) {
		filters.add(new InputFilter("Stream Mile",
			geolocTableName + "str_mile" + rd, "str_mile", 
			StringUtil.TYPE_DOUBLE, null, null, false));
	}
	else {
		filters.add(new InputFilter("Stream Mile",
			structureTableName + "abbrev" + rd, "abbrev", 
			StringUtil.TYPE_DOUBLE, null, null, false));
	}

	filters.add(new InputFilter("Structure ID",
		structureTableName + "id" + rd, "id", 
		StringUtil.TYPE_INTEGER, null, null, false));

	filters.add(new InputFilter("Structure Name",
		structureTableName + "str_name" + rd, "str_name", 
		StringUtil.TYPE_STRING, null, null, false));
	
	List<HydroBase_DssStructureType> structureTypes = dmi.getDssStructureTypeList();
	HydroBase_DssStructureType type = null;
	size = structureTypes.size();
	v1 = new Vector<String>();
	v2 = new Vector<String>();

	for (int i = 0; i < size; i++) {
		type = structureTypes.get(i);
		if (DMIUtil.isMissing(type.getRpt_code())) {
			v1.add(type.getStr_type_desc());
			v2.add(type.getStr_type());
		}
	}

	filter = new InputFilter("Structure Type",
		structureTableName + "str_type" + rd, "str_type", 
		StringUtil.TYPE_STRING, v1, v2, false);
	filter.setNumberInputJComboBoxRows(InputFilter.JCOMBOBOX_ROWS_DISPLAY_ALL);
	filters.add(filter);

	v1 = new Vector<String>();
	v1.add("1");

	filters.add(new InputFilter("Transbasin",
		structureTableName + "transbsn" + rd, "transbsn", 
		StringUtil.TYPE_STRING, v1, v1, false));

	filters.add(new InputFilter("UTM X",
		geolocTableName + "utm_x" + rd, "utm_x", 
		StringUtil.TYPE_DOUBLE, null, null, false));
	
	filters.add(new InputFilter("UTM Y",
		geolocTableName + "utm_y" + rd, "utm_y", 
		StringUtil.TYPE_DOUBLE, null, null, false));

	filters.add(new InputFilter("Water Source",
		"wd_water." + ld + "strname" + rd, "strname",
		StringUtil.TYPE_STRING, null, null, false));

	filters.add(new InputFilter("WD",
		structureTableName + "wd" + rd, "wd", 
		StringUtil.TYPE_INTEGER, null, null, false));

	setToolTipText("<html>HydroBase queries can be filtered<br>based on structure data.</html>");
	setInputFilters(filters, 3, 26);
}

}