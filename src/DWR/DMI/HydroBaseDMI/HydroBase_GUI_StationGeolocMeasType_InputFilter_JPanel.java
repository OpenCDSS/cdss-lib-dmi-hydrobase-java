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

import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.IO.PropList;
import RTi.Util.String.StringUtil;

public class HydroBase_GUI_StationGeolocMeasType_InputFilter_JPanel
extends InputFilter_JPanel
{

/**
Create an InputFilter_JPanel for creating where clauses
for HydroBase_StationGeolocMeasType queries.  This is used by TSTool.
@param hbdmi HydroBaseDMI instance.
@return a JPanel containing InputFilter instances for 
HydroBase_StationGeolocMeasType queries.
@exception Exception if there is an error.
*/
public HydroBase_GUI_StationGeolocMeasType_InputFilter_JPanel (
						HydroBaseDMI hbdmi )
throws Exception
{	Vector division_Vector = new Vector ( 7 );
	Vector division_internal_Vector = new Vector ( 7 );
	Vector district_data_Vector = hbdmi.getWaterDistricts();
	Vector district_Vector = new Vector ( district_data_Vector.size() );
	Vector district_internal_Vector=new Vector(district_data_Vector.size());
	Vector county_data_Vector = hbdmi.getCountyRef();
	Vector county_Vector = new Vector ( county_data_Vector.size() );
	Vector county_internal_Vector = new Vector ( county_data_Vector.size());
	// REVISIT - hard-code for now since no HydroBaseDMI global data.
	division_Vector.addElement ( "1 - South Platte" );
	division_Vector.addElement ( "2 - Arkansas" );
	division_Vector.addElement ( "3 - Rio Grande" );
	division_Vector.addElement ( "4 - Gunnison" );
	division_Vector.addElement ( "5 - Colorado" );
	division_Vector.addElement ( "6 - Yampa/White" );
	division_Vector.addElement ( "7 - San Juan/Dolores" );
	division_internal_Vector.addElement ( "1" );
	division_internal_Vector.addElement ( "2" );
	division_internal_Vector.addElement ( "3" );
	division_internal_Vector.addElement ( "4" );
	division_internal_Vector.addElement ( "5" );
	division_internal_Vector.addElement ( "6" );
	division_internal_Vector.addElement ( "7" );
	HydroBase_CountyRef county;
	int size = county_data_Vector.size();
	for ( int i = 0; i < size; i++ ) {
		county = (HydroBase_CountyRef)county_data_Vector.elementAt(i);
		county_Vector.addElement (
			county.getCounty() + ", " + county.getST() );
		county_internal_Vector.addElement (county.getCounty() );
	}
	HydroBase_WaterDistrict wd;
	size = district_data_Vector.size();
	for ( int i = 0; i < size; i++ ) {
		wd = (HydroBase_WaterDistrict)district_data_Vector.elementAt(i);
		district_Vector.addElement (wd.getWD() + " - "+wd.getWd_name());
		district_internal_Vector.addElement ("" + wd.getWD() );
	}

	Vector input_filters = new Vector(8);
	input_filters.addElement ( new InputFilter (
		"", "",
		StringUtil.TYPE_STRING,
		null, null, true ) );	// Blank to disable filter
	InputFilter filter = new InputFilter (
		"County Name", "geoloc.county",
		"county",
		StringUtil.TYPE_STRING,
		county_Vector, county_internal_Vector, true );
	filter.setTokenInfo(",",0);	// Counties show name, state
	input_filters.addElement ( filter );
	filter = new InputFilter (
		"District", "geoloc.wd",
		"wd",
		StringUtil.TYPE_INTEGER,
		district_Vector, district_internal_Vector, true );
	filter.setTokenInfo("-",0);
	input_filters.addElement ( filter );
	filter = new InputFilter (
		"Division", "geoloc.div",
		"div",
		StringUtil.TYPE_INTEGER,
		division_Vector, division_internal_Vector, true );
	filter.setTokenInfo("-",0);
	input_filters.addElement ( filter );
	input_filters.addElement ( new InputFilter (
		"HUC", "geoloc.huc", "huc",
		StringUtil.TYPE_STRING,
		null, null, true ) );
	// REVISIT - ..."Data Source";
	input_filters.addElement ( new InputFilter (
		"Station Abbreviation", "station.abbrev", "abbrev",
		StringUtil.TYPE_STRING,
		null, null, true ) );
	input_filters.addElement ( new InputFilter (
		"Station ID", "station.station_id", "station_id",
		StringUtil.TYPE_STRING,
		null, null, true ) );
	input_filters.addElement ( new InputFilter (
		"Station Name", "station.station_name", "station_name",
		StringUtil.TYPE_STRING,
		null, null, true ) );
	PropList filter_props = new PropList ( "InputFilter" );
	filter_props.set ( "NumFilterGroups=3" );
	setToolTipText (
		"<HTML>HydroBase queries can be filtered" +
		"<BR>based on station data." +
		"</HTML>" );
	setInputFilters ( input_filters, filter_props );
}

}
