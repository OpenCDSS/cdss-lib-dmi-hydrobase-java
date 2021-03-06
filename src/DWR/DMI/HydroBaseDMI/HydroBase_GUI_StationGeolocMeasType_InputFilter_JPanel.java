// HydroBase_GUI_StationGeolocMeasType_InputFilter_JPanel - input filter panel for HydroBase_StationGeolocMeasType data 

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

//------------------------------------------------------------------------------
// HydroBase_GUI_StationGeolocMeasType_InputFilter_JPanel - input filter panel
//	for HydroBase_StationGeolocMeasType data 
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// History:
//
// 2004-08-27	Steven A. Malers, RTi	Implement to simplify generic code that
//					can use instanceof to figure out the
//					input filter panel type.
// 2005-04-05	J. Thomas Sapienza, RTi	Adapted the fields for use with 
//					stored procedures.
//------------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.String.StringUtil;

@SuppressWarnings("serial")
public class HydroBase_GUI_StationGeolocMeasType_InputFilter_JPanel
extends InputFilter_JPanel
{

/**
HydroBase datastore used by the filter.
*/
private HydroBaseDataStore __dataStore = null;

/**
Create an InputFilter_JPanel for creating where clauses
for HydroBase_StationGeolocMeasType queries.  This is used by TSTool.
@param dataStore HydroBase datastore for database connection.
@return a JPanel containing InputFilter instances for HydroBase_StationGeolocMeasType queries.
@exception Exception if there is an error.
*/
public HydroBase_GUI_StationGeolocMeasType_InputFilter_JPanel ( HydroBaseDataStore dataStore )
throws Exception
{	__dataStore = dataStore;
    HydroBaseDMI hbdmi = (HydroBaseDMI)dataStore.getDMI();
    List<String> division_Vector = new Vector<String> ( 7 );
	List<String> division_internal_Vector = new Vector<String> ( 7 );
	List<HydroBase_WaterDistrict> district_data_Vector = hbdmi.getWaterDistricts();
	List<String> district_Vector = new Vector<String> ( district_data_Vector.size() );
	List<String> district_internal_Vector = new Vector<String>(district_data_Vector.size());
	List<HydroBase_CountyRef> county_data_Vector = hbdmi.getCountyRef();
	List<String> county_Vector = new Vector<String> ( county_data_Vector.size() );
	List<String> county_internal_Vector = new Vector<String> ( county_data_Vector.size());
	// TODO - hard-code for now since no HydroBaseDMI global data.
	division_Vector.add ( "1 - South Platte" );
	division_Vector.add ( "2 - Arkansas" );
	division_Vector.add ( "3 - Rio Grande" );
	division_Vector.add ( "4 - Gunnison" );
	division_Vector.add ( "5 - Colorado" );
	division_Vector.add ( "6 - Yampa/White" );
	division_Vector.add ( "7 - San Juan/Dolores" );
	division_internal_Vector.add ( "1" );
	division_internal_Vector.add ( "2" );
	division_internal_Vector.add ( "3" );
	division_internal_Vector.add ( "4" );
	division_internal_Vector.add ( "5" );
	division_internal_Vector.add ( "6" );
	division_internal_Vector.add ( "7" );
	HydroBase_CountyRef county;
	int size = county_data_Vector.size();
	for ( int i = 0; i < size; i++ ) {
		county = (HydroBase_CountyRef)county_data_Vector.get(i);
		county_Vector.add ( county.getCounty() + ", " + county.getST() );
		county_internal_Vector.add (county.getCounty() );
	}
	HydroBase_WaterDistrict wd;
	size = district_data_Vector.size();
	for ( int i = 0; i < size; i++ ) {
		wd = (HydroBase_WaterDistrict)district_data_Vector.get(i);
		district_Vector.add (wd.getWD() + " - "+wd.getWd_name());
		district_internal_Vector.add ("" + wd.getWD() );
	}

	List<InputFilter> input_filters = new Vector<InputFilter>(8);
	input_filters.add ( new InputFilter (
		"", "",
		StringUtil.TYPE_STRING,
		null, null, true ) );	// Blank to disable filter
	InputFilter filter = new InputFilter (
		"County Name", "geoloc.county",
		"county",
		StringUtil.TYPE_STRING,
		county_Vector, county_internal_Vector, true );
	filter.setTokenInfo(",",0);	// Counties show name, state
	input_filters.add ( filter );
	filter = new InputFilter (
		"District", "geoloc.wd",
		"wd",
		StringUtil.TYPE_INTEGER,
		district_Vector, district_internal_Vector, true );
	filter.setTokenInfo("-",0);
	input_filters.add ( filter );
	filter = new InputFilter (
		"Division", "geoloc.div",
		"div",
		StringUtil.TYPE_INTEGER,
		division_Vector, division_internal_Vector, true );
	filter.setTokenInfo("-",0);
	input_filters.add ( filter );
	input_filters.add ( new InputFilter (
		"HUC", "geoloc.huc", "huc",
		StringUtil.TYPE_STRING,
		null, null, true ) );
	// TODO - ..."Data Source";
	input_filters.add ( new InputFilter (
		"Station Abbreviation", "station.abbrev", "abbrev",
		StringUtil.TYPE_STRING,
		null, null, true ) );
	input_filters.add ( new InputFilter (
		"Station ID", "station.station_id", "station_id",
		StringUtil.TYPE_STRING,
		null, null, true ) );
	input_filters.add ( new InputFilter (
		"Station Name", "station.station_name", "station_name",
		StringUtil.TYPE_STRING,
		null, null, true ) );
	setToolTipText ( "<html>HydroBase queries can be filtered<br>based on station data.</html>" );
	setInputFilters ( input_filters, 3, -1 );
}

/**
Return the datastore used with the filter.
*/
public HydroBaseDataStore getDataStore ()
{
    return __dataStore;
}

}
