// HydroBase_GUI_Station_InputFilter_JPanel - Input filter for station queries.

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
// HydroBase_GUI_Station_InputFilter_JPanel - Input filter for station queries.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 2005-01-10	J. Thomas Sapienza, RTi	Initial version.
// 2005-02-01	JTS, RTi		Renamed from 
//					HydroBase_InputFilter_JPanel_Station
// 2005-02-23	JTS, RTi		Changed to only have 2 filter groups.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;

import RTi.Util.String.StringUtil;

/**
This class is an input filter for querying station data from the Station GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_Station_InputFilter_JPanel
extends InputFilter_JPanel {

/**
Constructor.
@param dmi the dmi to use to connect to the database.  Cannot be null.
*/
public HydroBase_GUI_Station_InputFilter_JPanel(HydroBaseDMI dmi) {
	String rd = dmi.getFieldRightEscape();
	String ld = dmi.getFieldLeftEscape();

	List<InputFilter> filters = new Vector<InputFilter>();

	String stationTableName = HydroBase_GUI_Util._STATION_TABLE_NAME + "." + ld;
	String geolocTableName = HydroBase_GUI_Util._GEOLOC_TABLE_NAME + "." + ld;
	String measTypeTableName = HydroBase_GUI_Util._MEASTYPE_TABLE_NAME + "." + ld;

	filters.add(new InputFilter("", "", StringUtil.TYPE_STRING, null, null, false));

	filters.add(new InputFilter("Abbreviation",
		stationTableName + "abbrev" + rd, "abbrev", 
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Contr Area",
		stationTableName + "contr_area" + rd, "contr_area",
		StringUtil.TYPE_DOUBLE, null, null, false));		

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
		geolocTableName + "cty" + rd, "cty", StringUtil.TYPE_STRING,
		v1, v2, false));
		
	filters.add(new InputFilter("Data Source",
		measTypeTableName + "data_source" + rd, "data_source",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Drain Area",
		stationTableName + "drain_area" + rd, "drain_area",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Elevation",
		geolocTableName + "elev" + rd, "elev",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("End Year",
		measTypeTableName + "end_year" + rd, "end_year",
		StringUtil.TYPE_INTEGER, null, null, false));		
	filters.add(new InputFilter("HUC",
		geolocTableName + "huc" + rd, "huc", StringUtil.TYPE_STRING,
		null, null, false));
	filters.add(new InputFilter("Latitude",
		geolocTableName + "latdecdeg" + rd, "latdecdeg",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Longitude",
		geolocTableName + "longdecdeg" + rd, "longdecdeg",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Start Year",
		measTypeTableName + "start_year" + rd, "start_year",
		StringUtil.TYPE_INTEGER, null, null, false));
	filters.add(new InputFilter("State",
		geolocTableName + "st" + rd, "st",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Station ID",
		stationTableName + "station_id" + rd, "station_id",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Station Name",
		stationTableName + "station_name" + rd, "station_name",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("UTM X",
		geolocTableName + "utm_x" + rd, "utm_x",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("UTM Y",
		geolocTableName + "utm_y" + rd, "utm_y",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("WD",
		geolocTableName + "wd" + rd, "wd",
		StringUtil.TYPE_INTEGER, null, null, false));
		
	setToolTipText("<html>HydroBase queries can be filtered<br>based on station data.</html>");
	setInputFilters(filters, 2, 18);
}

}
