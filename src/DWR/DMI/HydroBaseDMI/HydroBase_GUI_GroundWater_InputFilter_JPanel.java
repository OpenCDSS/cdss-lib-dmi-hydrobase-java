// HydroBase_GUI_GroundWater_InputFilter_JPanel - Input filter for geophlog ground water queries.

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

import java.awt.event.MouseListener;

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilterCriterionType;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

/**
This class is an input filter for querying geophlog ground water data in the Ground Water GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_GroundWater_InputFilter_JPanel
extends InputFilter_JPanel {

/**
HydroBase datastore used by the filter.
*/
private HydroBaseDataStore __dataStore = null;

/**
Constructor.
@param dataStore the dataStore to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location
entry text field is clicked in.  Cannot be null.
*/
public HydroBase_GUI_GroundWater_InputFilter_JPanel( HydroBaseDataStore dataStore, MouseListener listener, boolean tstool)
{   String routine = getClass().getName();
    __dataStore = dataStore;
    HydroBaseDMI dmi = (HydroBaseDMI)dataStore.getDMI();
	InputFilter filter = null;

	List<InputFilter> filters = new Vector<>();

	filters.add(new InputFilter("", "", StringUtil.TYPE_STRING, null, null, false));

	// Fill in the water district data for input filters.

	List<HydroBase_WaterDistrict> district_data_Vector = dmi.getWaterDistricts();
	List<String> district_Vector = new Vector<> ( district_data_Vector.size() );
	List<String> district_internal_Vector = new Vector<>(district_data_Vector.size());
	HydroBase_WaterDistrict wd;
	int size = district_data_Vector.size();
	for ( int i = 0; i < size; i++ ) {
		wd = district_data_Vector.get(i);
		district_Vector.add (wd.getWD() + " - "+wd.getWd_name());
		district_internal_Vector.add ("" + wd.getWD() );
	}

	// Fill in the division data for input filters.

	List<HydroBase_WaterDivision> division_data_Vector = dmi.getWaterDivisions();
	List<String> division_Vector = new Vector<> ( 7 );
	List<String> division_internal_Vector = new Vector<> ( 7 );
	HydroBase_WaterDivision div;
	size = division_data_Vector.size();
	for ( int i = 0; i < size; i++ ) {
		div =(HydroBase_WaterDivision)division_data_Vector.get(i);
		division_Vector.add (div.getDiv() + " - " + div.getDiv_name());
		division_internal_Vector.add ("" + div.getDiv() );
	}

	if (tstool) {
		filters.add(new InputFilter("Basin",
			"", "basin", StringUtil.TYPE_STRING,
			null, null, false));
	}

    List<HydroBase_CountyRef> county_data_Vector = dmi.getCountyRef();
    List<String> county_Vector = new Vector<> ( county_data_Vector.size() );
    List<String> county_internal_Vector = new Vector<> ( county_data_Vector.size());
    for ( HydroBase_CountyRef county: county_data_Vector ) {
        county_Vector.add ( county.getCounty() + ", " + county.getST() );
        county_internal_Vector.add (county.getCounty() );
    }
    filter = new InputFilter (
        "County Name", "county", "county",
        StringUtil.TYPE_STRING,
        county_Vector, county_internal_Vector, true );
    filter.setTokenInfo(",",0);
    filters.add ( filter );
	
    if (tstool) {
        List<String> dsList = new Vector<String>();
        try {
            dsList = dmi.readGroundWaterWellsMeasTypeListDistinctDataSource();
            filter = new InputFilter (
                "Data Source", "data_source", "data_source",
                StringUtil.TYPE_STRING,
                dsList, dsList, true );
                filters.add ( filter );
        }
        catch ( Exception e ) {
            Message.printWarning(2,routine,e);
        }
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
		// Create the input filter for the location.
		filter = new InputFilter(
			HydroBase_GUI_Util._LOCATION,
			HydroBase_GUI_Util._PLSS_LOCATION,
			HydroBase_GUI_Util._PLSS_LOCATION,
			StringUtil.TYPE_STRING,
			null, null, false);
		// All constraints other than EQUALS are removed because
		// PLSS Locations are compared in a special way.
		filter.removeConstraint(InputFilterCriterionType.INPUT_ONE_OF.toString());
		filter.removeConstraint(InputFilterCriterionType.INPUT_STARTS_WITH.toString());
		filter.removeConstraint(InputFilterCriterionType.INPUT_ENDS_WITH.toString());
		filter.removeConstraint(InputFilterCriterionType.INPUT_CONTAINS.toString());
		// The PLSS Location text field is not editable because users
		// must go through the PLSS Location JDialog to build a location.
		filter.setInputJTextFieldEditable(false);
		// This listener must be set up so that the location builder
		// dialog can be opened when the PLSS Location text field is clicked on.
		filter.addInputComponentMouseListener(listener);
		filter.setInputComponentToolTipText( "Click in this field to build a location to use as a query constraint.");
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

	setToolTipText("<html>HydroBase queries can be filtered<br>based on ground water data.</html>");
	setInputFilters(filters, 3, 12);
}

/**
Return the datastore used with the filter.
*/
public HydroBaseDataStore getDataStore () {
    return __dataStore;
}

}