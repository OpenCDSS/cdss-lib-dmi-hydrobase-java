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

import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.IO.PropList;
import RTi.Util.String.StringUtil;

public class HydroBase_GUI_AgriculturalCASSLivestockStats_InputFilter_JPanel
extends InputFilter_JPanel
{

/**
Create an InputFilter_JPanel for creating where clauses
for HydroBase_AgriculturalCASSLivestockStats queries.  This is used by TSTool.
@param hdmi HydroBaseDMI instance.
@return a JPanel containing InputFilter instances for 
HydroBase_AgriculturalCASSLivestockStats queries.
@exception Exception if there is an error.
*/
public HydroBase_GUI_AgriculturalCASSLivestockStats_InputFilter_JPanel (
							HydroBaseDMI hbdmi )
throws Exception
{	// For now do these queries here with available low-level code...

	Vector v = hbdmi.readAgriculturalCASSLivestockStatsList (
		(InputFilter_JPanel)null,	// Where clauses
					null,	// county
					null,	// commodity
					null,	// practice
					null,	// date1
					null,	// date2,
					true );	// Distinct
	int size = 0;
	if ( v != null ) {
		size = v.size();
	}
	Vector commodity_Vector = new Vector();
	Vector type_Vector = new Vector();
	String commodity, type;
	HydroBase_AgriculturalCASSLivestockStats agstats = null;
	int commodity_size = 0, type_size = 0, j = 0;
	boolean found = false;
	for ( int i = 0; i < size; i++ ) {
		agstats = (HydroBase_AgriculturalCASSLivestockStats)
			v.elementAt(i);
		commodity = agstats.getCommodity();
		type = agstats.getType();
		commodity_size = commodity_Vector.size();
		type_size = type_Vector.size();
		found = false;
		for ( j = 0; j < commodity_size; j++ ) {
			if (	commodity.equalsIgnoreCase(
				(String)commodity_Vector.elementAt(j))){
				found = true;
				break;
			}
		}
		if ( !found ) {
			commodity_Vector.addElement(commodity);
		}
		found = false;
		for ( j = 0; j < type_size; j++ ) {
			if (	type.equalsIgnoreCase(
				(String)type_Vector.elementAt(j))){
				found = true;
				break;
			}
		}
		if ( !found ) {
			type_Vector.addElement(type);
		}
	}

	Vector county_data_Vector = hbdmi.getCountyRef();
	Vector county_Vector = new Vector ( county_data_Vector.size() );
	Vector county_internal_Vector = new Vector ( county_data_Vector.size());
	size = county_data_Vector.size();
	HydroBase_CountyRef county;
	for ( int i = 0; i < size; i++ ) {
		county = (HydroBase_CountyRef)county_data_Vector.elementAt(i);
		county_Vector.addElement (
			county.getCounty() + ", " + county.getST() );
		county_internal_Vector.addElement (county.getCounty() );
	}

	// REVISIT - remove hard-code later
	//Vector st_Vector = new Vector(1);	// Hard-code for now.
	//st_Vector.addElement ( "CO" );
	//Vector st_internal_Vector = new Vector(1);
	//st_internal_Vector.addElement ( "CO" );
	Vector input_filters = new Vector(8);
	input_filters.addElement ( new InputFilter ( "", "",
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
	input_filters.addElement ( filter );
	input_filters.addElement ( new InputFilter (
		"Commodity", "agricultural_cass_livestock_stats.commodity",
		"commodity",
		StringUtil.TYPE_STRING,
		commodity_Vector, commodity_Vector, true ) );
	input_filters.addElement ( new InputFilter (
		"Type", "agricultural_cass_livestock_stats.Type",
		"type",
		StringUtil.TYPE_STRING,
		type_Vector, type_Vector, true ) );
	PropList filter_props = new PropList ( "InputFilter" );
	filter_props.set ( "NumFilterGroups=3" );
	setToolTipText (
		"<HTML>HydroBase queries can be filtered" +
		"<BR>based on county agricultural statistics data." +
		"</HTML>" );
	setInputFilters ( input_filters, filter_props );
}

}
