//------------------------------------------------------------------------------
// HydroBase_GUI_AgriculturalCASSLivestockStats_InputFilter_JPanel - input
//	filter panel for HydroBase_AgriculturalCASSLivestockStats queries
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// History:
//
// 2006-10-31	Steven A. Malers, RTi	Initial version as modified copy of
//					...CropStats...
//------------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.String.StringUtil;

@SuppressWarnings("serial")
public class HydroBase_GUI_AgriculturalCASSLivestockStats_InputFilter_JPanel
extends InputFilter_JPanel
{

/**
Datastore for input filter.
*/
private HydroBaseDataStore __dataStore = null;

/**
Create an InputFilter_JPanel for creating where clauses
for HydroBase_AgriculturalCASSLivestockStats queries.  This is used by TSTool.
@param dataStore HydroBase datastore for database connection.
@return a JPanel containing InputFilter instances for 
HydroBase_AgriculturalCASSLivestockStats queries.
@exception Exception if there is an error.
*/
public HydroBase_GUI_AgriculturalCASSLivestockStats_InputFilter_JPanel ( HydroBaseDataStore dataStore )
throws Exception
{	__dataStore = dataStore;
    HydroBaseDMI hbdmi = (HydroBaseDMI)dataStore.getDMI();
    // For now do these queries here with available low-level code...

	List<HydroBase_AgriculturalCASSLivestockStats> v = hbdmi.readAgriculturalCASSLivestockStatsList (
		(InputFilter_JPanel)null,	// Where clauses
					null,	// county
					null,	// commodity
					null,	// type
					null,	// date1
					null,	// date2,
					true );	// Distinct
	int size = 0;
	if ( v != null ) {
		size = v.size();
	}
	List<String> commodity_Vector = new Vector<String>();
	List<String> type_Vector = new Vector<String>();
	String commodity, type;
	HydroBase_AgriculturalCASSLivestockStats agstats = null;
	int commodity_size = 0, type_size = 0, j = 0;
	boolean found = false;
	for ( int i = 0; i < size; i++ ) {
		agstats = v.get(i);
		commodity = agstats.getCommodity();
		type = agstats.getType();
		commodity_size = commodity_Vector.size();
		type_size = type_Vector.size();
		found = false;
		for ( j = 0; j < commodity_size; j++ ) {
			if ( commodity.equalsIgnoreCase((String)commodity_Vector.get(j))){
				found = true;
				break;
			}
		}
		if ( !found ) {
			commodity_Vector.add(commodity);
		}
		found = false;
		for ( j = 0; j < type_size; j++ ) {
			if ( type.equalsIgnoreCase( (String)type_Vector.get(j))){
				found = true;
				break;
			}
		}
		if ( !found ) {
			type_Vector.add(type);
		}
	}

	List<HydroBase_CountyRef> county_data_Vector = hbdmi.getCountyRef();
	List<String> county_Vector = new Vector<String> ( county_data_Vector.size() );
	List<String> county_internal_Vector = new Vector<String> ( county_data_Vector.size());
	size = county_data_Vector.size();
	HydroBase_CountyRef county;
	for ( int i = 0; i < size; i++ ) {
		county = county_data_Vector.get(i);
		county_Vector.add ( county.getCounty() + ", " + county.getST() );
		county_internal_Vector.add (county.getCounty() );
	}

	// TODO - remove hard-code later
	//Vector st_Vector = new Vector(1);	// Hard-code for now.
	//st_Vector.addElement ( "CO" );
	//Vector st_internal_Vector = new Vector(1);
	//st_internal_Vector.addElement ( "CO" );
	List<InputFilter> input_filters = new Vector<InputFilter>(8);
	input_filters.add ( new InputFilter ( "", "",
			StringUtil.TYPE_STRING,
			null, null, true ) );	// Blank to disable filter
	//filter = new InputFilter (
		//"State Abbreviation",
		//"agricultural_cass_livestock_stats.st",
		//StringUtil.TYPE_STRING,
		//st_Vector, st_internal_Vector, true );
	//input_filters.addElement ( filter );
	InputFilter filter = new InputFilter (
		"County", "agricultural_cass_livestock_stats.county",
		"county",
		StringUtil.TYPE_STRING,
		county_Vector, county_internal_Vector, true );
	filter.setTokenInfo(",",0);	// Counties show name, state
	input_filters.add ( filter );
	input_filters.add ( new InputFilter (
		"Commodity", "agricultural_cass_livestock_stats.commodity",
		"commodity",
		StringUtil.TYPE_STRING,
		commodity_Vector, commodity_Vector, true ) );
	input_filters.add ( new InputFilter (
		"Type", "agricultural_cass_livestock_stats.Type",
		"type",
		StringUtil.TYPE_STRING,
		type_Vector, type_Vector, true ) );
	setToolTipText (
		"<html>HydroBase queries can be filtered<br>based on county agricultural statistics data.</html>" );
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