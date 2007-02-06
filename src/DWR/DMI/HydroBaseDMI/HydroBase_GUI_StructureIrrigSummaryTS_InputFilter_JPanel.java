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

import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.IO.PropList;
import RTi.Util.String.StringUtil;

public class HydroBase_GUI_StructureIrrigSummaryTS_InputFilter_JPanel
extends InputFilter_JPanel
{

/**
Create an InputFilter_JPanel for creating where clauses
for HydroBase_StructureIrrigSummaryTS queries.  This is used by TSTool.
@param hdmi HydroBaseDMI instance.
@return a JPanel containing InputFilter instances for 
HydroBase_StructureIrrigSummaryTS queries.
@exception Exception if there is an error.
*/
public HydroBase_GUI_StructureIrrigSummaryTS_InputFilter_JPanel (
						HydroBaseDMI hbdmi )
throws Exception
{	// Fill in the district data for input filters...

	Vector district_data_Vector = hbdmi.getWaterDistricts();
	Vector district_Vector = new Vector ( district_data_Vector.size() );
	Vector district_internal_Vector=new Vector(district_data_Vector.size());
	HydroBase_WaterDistrict wd;
	int size = district_data_Vector.size();
	for ( int i = 0; i < size; i++ ) {
		wd = (HydroBase_WaterDistrict)district_data_Vector.elementAt(i);
		district_Vector.addElement (wd.getWD() + " - "+wd.getWd_name());
		district_internal_Vector.addElement ("" + wd.getWD() );
	}

	// Fill in the division data for input filters...

	Vector division_data_Vector = hbdmi.getWaterDivisions();
	Vector division_Vector = new Vector ( 7 );
	Vector division_internal_Vector = new Vector ( 7 );
	HydroBase_WaterDivision div;
	size = division_data_Vector.size();
	for ( int i = 0; i < size; i++ ) {
		div =(HydroBase_WaterDivision)division_data_Vector.elementAt(i);
		division_Vector.addElement (div.getDiv() + " - " +
			div.getDiv_name());
		division_internal_Vector.addElement ("" + div.getDiv() );
	}

	// Get crop data for filter use...

	Vector crop_data_Vector = hbdmi.getCropRef();
	Vector crop_Vector = new Vector ( 30 );
	Vector crop_internal_Vector = new Vector ( 30 );
	HydroBase_CropRef crop;
	size = crop_data_Vector.size();
	for ( int i = 0; i < size; i++ ) {
		crop = (HydroBase_CropRef)crop_data_Vector.elementAt(i);
		crop_Vector.addElement ( crop.getCrop_desc() );
		crop_internal_Vector.addElement ( crop.getCrop_desc() );
	}

	Vector input_filters = new Vector(6);
	input_filters.addElement ( new InputFilter (
		"", "",
		StringUtil.TYPE_STRING,
		null, null, true ) );	// Blank to disable filter
	InputFilter filter = new InputFilter (
		"District", "structure.wd", "wd",
		StringUtil.TYPE_INTEGER,
		district_Vector, district_internal_Vector, true );
		filter.setTokenInfo("-",0);
		input_filters.addElement ( filter );
	filter = new InputFilter (
		"Division", "structure.div", "div",
		StringUtil.TYPE_INTEGER,
		division_Vector, division_internal_Vector, true );
		filter.setTokenInfo("-",0);
		input_filters.addElement ( filter );
	input_filters.addElement ( new InputFilter (
		"Structure ID", "structure.id", "id",
		StringUtil.TYPE_INTEGER,
		null, null, true ) );
	input_filters.addElement ( new InputFilter (
		"Structure Name", "structure.str_name", "str_name",
		StringUtil.TYPE_STRING,
		null, null, true ) );
	input_filters.addElement (
		new InputFilter (
		"Land Use/Crop Type", "irrig_summary_ts.land_use", "land_use",
		StringUtil.TYPE_STRING,
		crop_Vector, crop_internal_Vector, true ) );
	PropList filter_props = new PropList ( "InputFilter" );
	filter_props.set ( "NumFilterGroups=3" );
	setToolTipText (
		"<HTML>HydroBase queries can be filtered" +
		"<BR>based on irrigation summary time series data." +
		"</HTML>" );
	// Call base class method...
	setInputFilters ( input_filters, filter_props );
}

}