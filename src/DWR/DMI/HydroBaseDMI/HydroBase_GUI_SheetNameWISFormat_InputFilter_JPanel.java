// HydroBase_GUI_SheetNameWISFormat_InputFilter_JPanel - input filter panel for HydroBase_SheetNameWISFormat data 

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
// HydroBase_GUI_SheetNameWISFormat_InputFilter_JPanel -
//	input filter panel for HydroBase_SheetNameWISFormat data 
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// History:
//
// 2004-08-27	Steven A. Malers, RTi	Implement to simplify generic code that
//					can use instanceof to figure out the
//					input filter panel type.
// 2004-08-29	SAM, RTi		Rename from "SheetName" to
//					"SheetNameWISFormat" to reflect the
//					objects being filtered.
// 2005-03-09	J. Thomas Sapienza, RTi	HydroBase_SheetName 	
//					  -> HydroBase_WISSheetName.
// 2005-04-05	JTS, RTi		Adapted the fields for use with 
//					stored procedures.
//------------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.String.StringUtil;

@SuppressWarnings("serial")
public class HydroBase_GUI_SheetNameWISFormat_InputFilter_JPanel
extends InputFilter_JPanel
{

/**
HydroBase datastore used by the filter.
*/
private HydroBaseDataStore __dataStore = null;

/**
Create an InputFilter_JPanel for creating where clauses
for HydroBase_SheetNameWISFormat queries.  This is used by TSTool.
@param dataStore HydroBase datastore for database connection.
@return a JPanel containing InputFilter instances for HydroBase_SheetNameWISFormat queries.
@exception Exception if there is an error.
*/
public HydroBase_GUI_SheetNameWISFormat_InputFilter_JPanel ( HydroBaseDataStore dataStore )
throws Exception
{	__dataStore = dataStore;
    HydroBaseDMI hbdmi = (HydroBaseDMI)dataStore.getDMI();
    List<String> sheet_name_Vector = new Vector<String>();	
	List<HydroBase_WISSheetName> sheet_names = hbdmi.readWISSheetNameList(-999, -999, null, null);
	int size = 0;
	if ( sheet_names != null ) {
		size = sheet_names.size();
	}
	for ( int i = 0; i < size; i++ ) {
		sheet_name_Vector.add ( sheet_names.get(i).getSheet_name() );
	}

	List<InputFilter> input_filters = new Vector<InputFilter>(8);
	input_filters.add ( new InputFilter (
		"", "",
		StringUtil.TYPE_STRING,
		null, null, true ) );	// Blank to disable filter
	InputFilter filter = new InputFilter (
		"Sheet Name", "sheet_name.sheet_name",
		"sheet_name",
		StringUtil.TYPE_STRING,
		sheet_name_Vector, sheet_name_Vector, true );
	input_filters.add ( filter );
	// TODO SAM 2004-05-19 - might want to add row identifier and
	// row label, but these are in separate tables that are difficult to
	// join with in a general filter

	setToolTipText ( "<html>HydroBase queries can be filtered<br>based on the Water Information Sheet (WIS) name.</html>" );
	setInputFilters ( input_filters, 1, -1 );
}

/**
Return the datastore used with the filter.
*/
public HydroBaseDataStore getDataStore ()
{
    return __dataStore;
}

}
