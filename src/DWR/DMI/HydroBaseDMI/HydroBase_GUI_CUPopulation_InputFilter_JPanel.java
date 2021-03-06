// HydroBase_GUI_CUPopulation_InputFilter_JPanel - input filter panel for HydroBase_CUPopulation queries

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

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.String.StringUtil;

@SuppressWarnings("serial")
public class HydroBase_GUI_CUPopulation_InputFilter_JPanel
extends InputFilter_JPanel
{

/**
HydroBase datastore used by the filter.
*/
private HydroBaseDataStore __dataStore = null;

/**
Create an InputFilter_JPanel for creating where clauses for HydroBase_CUPopulation queries.  This is used by TSTool.
@param dataStore HydroBase datastore for database connection.
@return a JPanel containing InputFilter instances for HydroBase_CUPopulation queries.
@exception Exception if there is an error.
*/
public HydroBase_GUI_CUPopulation_InputFilter_JPanel ( HydroBaseDataStore dataStore )
throws Exception
{	__dataStore = dataStore;
    HydroBaseDMI hbdmi = (HydroBaseDMI)dataStore.getDMI();
    List<HydroBase_CUPopulation> v = hbdmi.readCUPopulationList (
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
	List<String> area_type_Vector = new Vector<String>();
	List<String> area_name_Vector = new Vector<String>();
	List<String> pop_type_Vector = new Vector<String>();
	String area_type, area_name, pop_type;
	HydroBase_CUPopulation cupop = null;
	int area_type_size = 0, area_name_size = 0, pop_type_size = 0, j = 0;
	boolean found = false;
	for ( int i = 0; i < size; i++ ) {
		cupop = v.get(i);
		area_type = cupop.getArea_type();
		area_name = cupop.getArea_name();
		pop_type = cupop.getPop_type();
		area_type_size = area_type_Vector.size();
		area_name_size = area_name_Vector.size();
		pop_type_size = pop_type_Vector.size();
		found = false;
		for ( j = 0; j < area_type_size; j++ ) {
			if ( area_type.equalsIgnoreCase(area_type_Vector.get(j))){
				found = true;
				break;
			}
		}
		if ( !found ) {
			area_type_Vector.add(area_type);
		}
		found = false;
		for ( j = 0; j < area_name_size; j++ ) {
			if ( area_name.equalsIgnoreCase(area_name_Vector.get(j))){
				found = true;
				break;
			}
		}
		if ( !found ) {
			area_name_Vector.add(area_name);
		}
		found = false;
		for ( j = 0; j < pop_type_size; j++ ) {
			if ( pop_type.equalsIgnoreCase(pop_type_Vector.get(j))){
				found = true;
				break;
			}
		}
		if ( !found ) {
			pop_type_Vector.add(pop_type);
		}
	}

	List<InputFilter> input_filters = new Vector<InputFilter>(3);
	input_filters.add ( new InputFilter ( "", "",
			StringUtil.TYPE_STRING,
			null, null, true ) );	// Blank to disable filter
	// REVISIT SAM 2006-11-03
	// Really need to have area_name cause a cascading filter on
	// area_name choices, but don't have a way to do this right now.
	input_filters.add ( new InputFilter (
		"Area Type", "CUPopulation.area_type", "area_type",
		StringUtil.TYPE_STRING,
		area_type_Vector, area_type_Vector, true ) );
	input_filters.add ( new InputFilter (
		"Area Name", "CUPopulation.area_name", "area_name",
		StringUtil.TYPE_STRING,
		area_name_Vector, area_name_Vector, true ) );
	input_filters.add ( new InputFilter (
		"Population Type", "CUPopulation.pop_type", "pop_type",
		StringUtil.TYPE_STRING,
		pop_type_Vector, pop_type_Vector, true ) );
	setToolTipText ( "<html>HydroBase queries can be filtered<br>based on population data.</html>" );
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
