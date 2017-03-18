//------------------------------------------------------------------------------
// HydroBase_GUI_AgriculturalNASSCropStats_InputFilter_JPanel - input filter
//	panel for HydroBase_AgriculturalNASSCropStats queries
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// History:
//
// 2004-08-28	Steven A. Malers, RTi	Implement to simplify generic code that
//					can use instanceof to figure out the
//					input filter panel type.
// 2005-04-05	J. Thomas Sapienza, RTi	Adapted the fields for use with 
//					stored procedures.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//------------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.String.StringUtil;

@SuppressWarnings("serial")
public class HydroBase_GUI_AgriculturalNASSCropStats_InputFilter_JPanel extends InputFilter_JPanel
{

/**
HydroBase datastore used by the filter.
*/
private HydroBaseDataStore __dataStore = null;

/**
Create an InputFilter_JPanel for creating where clauses
for HydroBase_AgriculturalNASSCropStats queries.  This is used by TSTool.
@param dataStore HydroBase datastore for database connection.
@return a JPanel containing InputFilter instances for HydroBase_AgriculturalNASSCropStats queries.
@exception Exception if there is an error.
*/
public HydroBase_GUI_AgriculturalNASSCropStats_InputFilter_JPanel ( HydroBaseDataStore dataStore )
throws Exception
{	__dataStore = dataStore;
    HydroBaseDMI hbdmi = (HydroBaseDMI)dataStore.getDMI();
    // For now do these queries here with available low-level code...

	List<HydroBase_AgriculturalNASSCropStats> v = hbdmi.readAgriculturalNASSCropStatsList (
					null,	// Where clauses
					null,	// county
					null,	// commodity
					null,	// date1
					null,	// date2,
					true );	// Distinct
	int size = 0;
	if ( v != null ) {
		size = v.size();
	}
	List<String> commodity_Vector = new Vector<String>();
	String commodity;
	HydroBase_AgriculturalNASSCropStats agstats = null;
	int commodity_size = 0, j = 0;
	boolean found = false;
	for ( int i = 0; i < size; i++ ) {
		agstats = v.get(i);
		commodity = agstats.getCommodity();
		commodity_size = commodity_Vector.size();
		found = false;
		for ( j = 0; j < commodity_size; j++ ) {
			if ( commodity.equalsIgnoreCase(commodity_Vector.get(j))){
				found = true;
				break;
			}
		}
		if ( !found ) {
			commodity_Vector.add(commodity);
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
		//"agricultural_nass_crop_stats.st",
		//StringUtil.TYPE_STRING,
		//st_Vector, st_internal_Vector, true );
	//input_filters.addElement ( filter );
	InputFilter filter = new InputFilter (
		"County", "agricultural_nass_crop_stats.county",
		"county",
		StringUtil.TYPE_STRING,
		county_Vector, county_internal_Vector, true );
	filter.setTokenInfo(",",0);	// Counties show name, state
	input_filters.add ( filter );
	input_filters.add ( new InputFilter (
		"Commodity", "agricultural_nass_crop_stats.commodity",
		"commodity",
		StringUtil.TYPE_STRING,
		commodity_Vector, commodity_Vector, true ) );
	setToolTipText (
		"<html>HydroBase queries can be filtered" +
		"<br>based on county agricultural statistics data." +
		"</html>" );
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