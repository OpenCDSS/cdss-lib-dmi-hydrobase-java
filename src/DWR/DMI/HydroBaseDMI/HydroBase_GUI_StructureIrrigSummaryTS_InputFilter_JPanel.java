//------------------------------------------------------------------------------
// HydroBase_GUI_StructureIrrigSummaryTS_InputFilter_JPanel -
//	input filter panel for HydroBase_StructureIrrigSummaryTS data 
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

public class HydroBase_GUI_StructureIrrigSummaryTS_InputFilter_JPanel
extends InputFilter_JPanel
{

/**
HydroBase datastore used by the filter.
*/
private HydroBaseDataStore __dataStore = null;
    
/**
Create an InputFilter_JPanel for creating where clauses
for HydroBase_StructureIrrigSummaryTS queries.  This is used by TSTool.
@param dataStore HydroBase datastore for database connection.
@return a JPanel containing InputFilter instances for HydroBase_StructureIrrigSummaryTS queries.
@exception Exception if there is an error.
*/
public HydroBase_GUI_StructureIrrigSummaryTS_InputFilter_JPanel ( HydroBaseDataStore dataStore )
throws Exception
{	__dataStore = dataStore;
    HydroBaseDMI hbdmi = (HydroBaseDMI)dataStore.getDMI();
    // Fill in the district data for input filters...

	List district_data_Vector = hbdmi.getWaterDistricts();
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

	List division_data_Vector = hbdmi.getWaterDivisions();
	List division_Vector = new Vector ( 7 );
	List division_internal_Vector = new Vector ( 7 );
	HydroBase_WaterDivision div;
	size = division_data_Vector.size();
	for ( int i = 0; i < size; i++ ) {
		div =(HydroBase_WaterDivision)division_data_Vector.get(i);
		division_Vector.add (div.getDiv() + " - " + div.getDiv_name());
		division_internal_Vector.add ("" + div.getDiv() );
	}

	// Get crop data for filter use...

	List crop_data_Vector = hbdmi.getCropRef();
	List crop_Vector = new Vector ( 30 );
	List crop_internal_Vector = new Vector ( 30 );
	HydroBase_CropRef crop;
	size = crop_data_Vector.size();
	for ( int i = 0; i < size; i++ ) {
		crop = (HydroBase_CropRef)crop_data_Vector.get(i);
		crop_Vector.add ( crop.getCrop_desc() );
		crop_internal_Vector.add ( crop.getCrop_desc() );
	}

	List input_filters = new Vector(6);
	input_filters.add ( new InputFilter (
		"", "",
		StringUtil.TYPE_STRING,
		null, null, true ) );	// Blank to disable filter
	InputFilter filter = new InputFilter (
		"District", "structure.wd", "wd",
		StringUtil.TYPE_INTEGER,
		district_Vector, district_internal_Vector, true );
		filter.setTokenInfo("-",0);
		input_filters.add ( filter );
	filter = new InputFilter (
		"Division", "structure.div", "div",
		StringUtil.TYPE_INTEGER,
		division_Vector, division_internal_Vector, true );
		filter.setTokenInfo("-",0);
		input_filters.add ( filter );
	input_filters.add ( new InputFilter (
		"Structure ID", "structure.id", "id",
		StringUtil.TYPE_INTEGER,
		null, null, true ) );
	input_filters.add ( new InputFilter (
		"Structure Name", "structure.str_name", "str_name",
		StringUtil.TYPE_STRING,
		null, null, true ) );
	input_filters.add (
		new InputFilter (
		"Land Use/Crop Type", "irrig_summary_ts.land_use", "land_use",
		StringUtil.TYPE_STRING,
		crop_Vector, crop_internal_Vector, true ) );
	setToolTipText ( "<html>HydroBase queries can be filtered<br>based on irrigation summary time series data.</html>" );
	// Call base class method...
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