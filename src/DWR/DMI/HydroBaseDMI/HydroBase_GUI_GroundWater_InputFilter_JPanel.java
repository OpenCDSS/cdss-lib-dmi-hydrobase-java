//-----------------------------------------------------------------------------
// HydroBase_GUI_GroundWater_InputFilter_JPanel - Input filter for 
//	geophlog ground water queries.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 2005-06-22	J. Thomas Sapienza, RTi	Initial version.
// 2005-06-28	JTS, RTi		Renamed from HydroBase_GUI_GroundWater
//					Geophlogs_InputFilter_JPanel.
// 2005-11-17	JTS, RTi		Added new fields "Basin", 
//					"DSS_aquifer1", "DSS_aquifer2", 
//					"DSS_aquifer_comment".
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.event.MouseListener;

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;

import RTi.Util.IO.PropList;

import RTi.Util.String.StringUtil;

/**
This class is an input filter for querying geophlog ground water data in 
the Ground Water GUI.
*/
public class HydroBase_GUI_GroundWater_InputFilter_JPanel
extends InputFilter_JPanel {

/**
Constructor.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location
entry text field is clicked in.  Cannot be null.
*/
public HydroBase_GUI_GroundWater_InputFilter_JPanel(HydroBaseDMI dmi,
MouseListener listener, boolean tstool) {
	InputFilter filter = null;

	List filters = new Vector();

	filters.add(new InputFilter("", "", StringUtil.TYPE_STRING,
		null, null, false));

	// Fill in the water district data for input filters...

	List district_data_Vector = dmi.getWaterDistricts();
	List district_Vector = new Vector ( district_data_Vector.size() );
	List district_internal_Vector=new Vector(district_data_Vector.size());
	HydroBase_WaterDistrict wd;
	int size = district_data_Vector.size();
	for ( int i = 0; i < size; i++ ) {
		wd = (HydroBase_WaterDistrict)district_data_Vector.get(i);
		district_Vector.add (wd.getWD() + " - "+wd.getWd_name());
		district_internal_Vector.add ("" + wd.getWD() );
	}

	// Fill in the division data for input filters...

	List division_data_Vector = dmi.getWaterDivisions();
	List division_Vector = new Vector ( 7 );
	List division_internal_Vector = new Vector ( 7 );
	HydroBase_WaterDivision div;
	size = division_data_Vector.size();
	for ( int i = 0; i < size; i++ ) {
		div =(HydroBase_WaterDivision)division_data_Vector.get(i);
		division_Vector.add (div.getDiv() + " - " +
			div.getDiv_name());
		division_internal_Vector.add ("" + div.getDiv() );
	}

	if (tstool) {
		filters.add(new InputFilter("Basin", 
			"", "basin", StringUtil.TYPE_STRING,
			null, null, false));
	}

	if (tstool) {
		filter = new InputFilter (
			"District", "geoloc.wd", "wd",
			StringUtil.TYPE_INTEGER,
			district_Vector, district_internal_Vector, true );
		filter.setTokenInfo("-",0);
		filters.add ( filter );
	
		filter = new InputFilter (
			"Division", "geoloc.div", "div",
			StringUtil.TYPE_INTEGER,
			division_Vector, division_internal_Vector, true );
		filter.setTokenInfo("-",0);
		filters.add ( filter );
	}

	filters.add(new InputFilter("DSS Aquifer 1", 
		"", "dss_aquifer1", StringUtil.TYPE_STRING,
		null, null, false));
	filters.add(new InputFilter("DSS Aquifer 2", 
		"", "dss_aquifer2", StringUtil.TYPE_STRING,
		null, null, false));
		
	if (!tstool) {
		filters.add(new InputFilter("DSS Aquifer Comment", 
			"", "dss_aquifer_comment", StringUtil.TYPE_STRING,
			null, null, false));
	}

	if (listener != null) {
		// create the input filter for the  Location
		filter = new InputFilter(
			HydroBase_GUI_Util._LOCATION,
			HydroBase_GUI_Util._PLSS_LOCATION, 
			HydroBase_GUI_Util._PLSS_LOCATION, 
			StringUtil.TYPE_STRING,
			null, null, false);
		// all constraints other than EQUALS are removed because 
		// PLSS Locations are compared in a special way
		filter.removeConstraint(InputFilter.INPUT_ONE_OF);
		filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
		filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
		filter.removeConstraint(InputFilter.INPUT_CONTAINS);
		// the PLSS Location text field is not editable because users 
		// must go through the PLSS Location JDialog to build a location
		filter.setInputJTextFieldEditable(false);
		// this listener must be set up so that the location builder 
		// dialog can be opened when the PLSS Location text field 
		// is clicked on.
		filter.addInputComponentMouseListener(listener);
		filter.setInputComponentToolTipText(
			"Click in this field to build "
			+ "a location to use as a query constraint.");
		filter.setInputJTextFieldWidth(20);
		filters.add(filter);
	}

	// this only works with SP-based queries, so no SQL support is in here.

	filters.add(new InputFilter("Loc Num", 
		"", "locnum", StringUtil.TYPE_STRING,
		null, null, false));
	filters.add(new InputFilter("Permitno", 
		"", "permitno", StringUtil.TYPE_INTEGER,
		null, null, false));
	filters.add(new InputFilter("Permitrpl", 
		"", "permitrpl", StringUtil.TYPE_STRING,
		null, null, false));
	filters.add(new InputFilter("Permitsuf", 
		"", "permitsuf", StringUtil.TYPE_STRING,
		null, null, false));		
	filters.add(new InputFilter("Site ID",
		"", "site_id", StringUtil.TYPE_STRING,
		null, null, false));

	if (!tstool) {
		filters.add(new InputFilter("District",
			"", "wd", StringUtil.TYPE_INTEGER,
			null, null, false));
	}
		
	filters.add(new InputFilter("Well Name",
		"", "well_name", StringUtil.TYPE_STRING,
		null, null, false));		

	PropList filterProps = new PropList("InputFilter");
	filterProps.set("NumFilterGroups=3");
	filterProps.set("NumWhereRowsToDisplay=12");
	setToolTipText("<HTML>HydroBase queries can be filtered" 
		+ "<BR>based on ground water data.</HTML>");
	setInputFilters(filters, filterProps);
}

}
