//------------------------------------------------------------------------------
// HydroBase_GUI_CUPopulation_InputFilter_JPanel - input
//	filter panel for HydroBase_CUPopulation queries
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// History:
//
// 2004-08-28	Steven A. Malers, RTi	Initial version as modified copy of
//					...CASSLivestock_InputFilter...	
//------------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.IO.PropList;
import RTi.Util.String.StringUtil;

public class HydroBase_GUI_CUPopulation_InputFilter_JPanel
extends InputFilter_JPanel
{

/**
Create an InputFilter_JPanel for creating where clauses
for HydroBase_CUPopulation queries.  This is used by TSTool.
@param hdmi HydroBaseDMI instance.
@return a JPanel containing InputFilter instances for HydroBase_CUPopulation
queries.
@exception Exception if there is an error.
*/
public HydroBase_GUI_CUPopulation_InputFilter_JPanel ( HydroBaseDMI hbdmi )
throws Exception
{	Vector v = hbdmi.readCUPopulationList (
		(InputFilter_JPanel)null,	// Where clauses
					null,	// area_type
					null,	// area_name
					null,	// pop_type
					null,	// date1
					null,	// date2,
					true );	// ** Distinct **
	int size = 0;
	if ( v != null ) {
		size = v.size();
	}
	Vector area_type_Vector = new Vector();
	Vector area_name_Vector = new Vector();
	Vector pop_type_Vector = new Vector();
	String area_type, area_name, pop_type;
	HydroBase_CUPopulation cupop = null;
	int area_type_size = 0, area_name_size = 0, pop_type_size = 0, j = 0;
	boolean found = false;
	for ( int i = 0; i < size; i++ ) {
		cupop = (HydroBase_CUPopulation)v.elementAt(i);
		area_type = cupop.getArea_type();
		area_name = cupop.getArea_name();
		pop_type = cupop.getPop_type();
		area_type_size = area_type_Vector.size();
		area_name_size = area_name_Vector.size();
		pop_type_size = pop_type_Vector.size();
		found = false;
		for ( j = 0; j < area_type_size; j++ ) {
			if (	area_type.equalsIgnoreCase(
				(String)area_type_Vector.elementAt(j))){
				found = true;
				break;
			}
		}
		if ( !found ) {
			area_type_Vector.addElement(area_type);
		}
		found = false;
		for ( j = 0; j < area_name_size; j++ ) {
			if (	area_name.equalsIgnoreCase(
				(String)area_name_Vector.elementAt(j))){
				found = true;
				break;
			}
		}
		if ( !found ) {
			area_name_Vector.addElement(area_name);
		}
		found = false;
		for ( j = 0; j < pop_type_size; j++ ) {
			if (	pop_type.equalsIgnoreCase(
				(String)pop_type_Vector.elementAt(j))){
				found = true;
				break;
			}
		}
		if ( !found ) {
			pop_type_Vector.addElement(pop_type);
		}
	}

	Vector input_filters = new Vector(3);
	input_filters.addElement ( new InputFilter ( "", "",
			StringUtil.TYPE_STRING,
			null, null, true ) );	// Blank to disable filter
	// REVISIT SAM 2006-11-03
	// Really need to have area_name cause a cascading filter on
	// area_name choices, but don't have a way to do this right now.
	input_filters.addElement ( new InputFilter (
		"Area Type", "CUPopulation.area_type", "area_type",
		StringUtil.TYPE_STRING,
		area_type_Vector, area_type_Vector, true ) );
	input_filters.addElement ( new InputFilter (
		"Area Name", "CUPopulation.area_name", "area_name",
		StringUtil.TYPE_STRING,
		area_name_Vector, area_name_Vector, true ) );
	input_filters.addElement ( new InputFilter (
		"Population Type", "CUPopulation.pop_type", "pop_type",
		StringUtil.TYPE_STRING,
		pop_type_Vector, pop_type_Vector, true ) );
	PropList filter_props = new PropList ( "InputFilter" );
	filter_props.set ( "NumFilterGroups=3" );
	setToolTipText (
		"<HTML>HydroBase queries can be filtered" +
		"<BR>based on population data." +
		"</HTML>" );
	setInputFilters ( input_filters, filter_props );
}

}
