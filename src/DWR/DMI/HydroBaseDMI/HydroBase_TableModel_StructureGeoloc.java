// HydroBase_TableModel_StructureGeoloc - class that handles the table model for the GUI for the times that StructureGeoloc is queried.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2025 Colorado Department of Natural Resources

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

package DWR.DMI.HydroBaseDMI;

import java.util.List;

/**
This class is a table model for displaying sttructure geoloc data in the 
HydroBase_GUI_Structure GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_StructureGeoloc 
extends HydroBase_TableModel<HydroBase_StructureGeoloc> {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 16;

/**
Used to refer internally to the columns.
*/
public final static int 
	COL_WD = 		0,
	COL_ID = 		1,
	COL_NAME = 		2,
	COL_LOCATION =		3,
	COL_SOURCE = 		4,
	COL_STRMILE = 		5,
	COL_OWNER = 		6,
	COL_TYPE = 		7,
	COL_CIU = 		8,
	COL_TRANSBSN =		9,
	COL_CAPACITY = 		10,
	COL_UNITS = 		11,
	COL_UTMX = 		12,
	COL_UTMY = 		13,
	COL_LONGITUDE =		14,
	COL_LATITUDE = 		15;

/**
A reference to an open dmi object (for use in pulling out some lookup table information).
*/
private HydroBaseDMI __dmi = null;

/**
Constructor.  This builds the Model for displaying the given structure geoloc results.
@param results the results that will be displayed in the table.
@param dmi a reference to the dmi object used to query for the results.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_StructureGeoloc(List<HydroBase_StructureGeoloc> results, HydroBaseDMI dmi) 
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_StructureGeoloc constructor.");
	}
	if (dmi == null || (!dmi.isOpen())) {
		throw new Exception ("Invalid dmi (null or unopened) passed to"
			+ "HydroBase_TableModel_StructureGeoloc constructor.");
	}
	__dmi = dmi;
	_rows = results.size();
	_data = results;
}

/**
Returns the class of the data stored in a given column.
@param columnIndex the column for which to return the data class.
*/
public Class<?> getColumnClass (int columnIndex) {
	switch (columnIndex) {
		case COL_WD:		return Integer.class;
		case COL_ID:		return Integer.class;
		case COL_NAME:		return String.class;
		case COL_LOCATION:	return String.class;
		case COL_SOURCE:	return String.class;
		case COL_STRMILE:	return Double.class;
		case COL_OWNER:		return String.class;
		case COL_TYPE:		return String.class;
		case COL_CIU:		return String.class;
		case COL_TRANSBSN:	return Integer.class;
		case COL_CAPACITY:	return Double.class;
		case COL_UNITS:		return String.class;
		case COL_UTMX:		return Double.class;
		case COL_UTMY:		return Double.class;
		case COL_LONGITUDE:	return Double.class;
		case COL_LATITUDE:	return Double.class;
		default:		return String.class;
	}
}

/**
Returns the number of columns of data.
@return the number of columns of data.
*/
public int getColumnCount() {
	return __COLUMNS;
}

/**
Returns the name of the column at the given position.
@return the name of the column at the given position.
*/
public String getColumnName(int columnIndex) {
	switch (columnIndex) {
		case COL_WD:		return "\nWD";
		case COL_ID:		return "\nID";
		case COL_NAME:		return "\nSTRUCTURE NAME";
		case COL_LOCATION:	return "\nLOCATION";
		case COL_SOURCE:	return "\nWATER SOURCE";
		case COL_STRMILE:	return "STREAM\nMILE";
		case COL_OWNER:		return "\nOWNER";
		case COL_TYPE:		return "STRUCTURE\nTYPE";
		case COL_CIU:		return "CURRENTLY\nIN USE";
		case COL_TRANSBSN:	return "TRANS-\nBASIN";
		case COL_CAPACITY:	return "DECREED\nCAPACITY";
		case COL_UNITS:		return "DECREED\nUNITS";
		case COL_UTMX:		return "\nUTM X";
		case COL_UTMY:		return "\nUTM Y";
		case COL_LONGITUDE:	return "LONGITUDE\n(DEC. DEG.)";
		case COL_LATITUDE:	return "LATITUDE\n(DEC. DEG.)";
		default:		return " ";
	}
}

/**
Returns the text to be assigned to worksheet tooltips.
@return a String array of tool tips.
*/
public String[] getColumnToolTips()
{	String[] tips = new String[__COLUMNS];

	tips[COL_WD] =
		"<html>The water district in which the structure exists, for" +
		" administrative purposes.<BR>" +
		"The WD and ID (WDID) is unique.</html>";
	tips[COL_ID] =
		"<html>The structure identifier within the water district." +
		"The WD and ID (WDID) is unique.</html>";
	tips[COL_NAME] = "Structure name.";
	tips[COL_LOCATION] = "Structure legal location.";
	tips[COL_SOURCE] = "Water source for the structure.";
	tips[COL_STRMILE] =
		"Stream mile (from the state line) at which the structure " +
		"is located.";
	tips[COL_OWNER] = "Owner of the structure.";
	tips[COL_TYPE] = "Structure type.";
	tips[COL_CIU] =
		"<HTML>Indicates if the structure is currently in use (CIU):"+
		"<BR>A = Active structure with contemporary diversion records."+
		"<BR>B = Structure abandoned by the court."+
		"<BR>C = Conditional structure."+
		"<BR>D = Duplicate, ID is no longer used."+
		"<BR>F = Structure used as FROM number - located in another " +
			"district." +
		"<BR>H = Historical structure only - no longer exists or has " +
			"records, but has historical data." +
		"<BR>I = Inactive structures which physically exist but no " +
			"diversion records are kept." +
		"<BR>N = Non-existent structure with no contemporary or "+
			"historical records." +
		"<BR>U = Active structures but diversion records are not " +
			"maintained.</HTML>";
	tips[COL_TRANSBSN] = "Indicates whether a structure is a transbasin " +
			"structure (1) or not (0).";
	tips[COL_CAPACITY] = "Sum of net amount rights, depending on " +
			"structure type and units.";
	tips[COL_UNITS] = "Decreed capacity units.";
	tips[COL_UTMX] = "UTM X coordinate.";
	tips[COL_UTMY] = "UTM Y coordinate.";
	tips[COL_LONGITUDE] = "Longitude, decimal degrees.";
	tips[COL_LATITUDE] = "Latitude, decimal degrees.";
	return tips;
}

/**
Returns an array containing the widths (in number of characters) that the 
fields in the table should be sized to.
@return an integer array containing the widths for each field.
*/
public int[] getColumnWidths() {
	int[] widths = new int[__COLUMNS];
	for (int i = 0; i < __COLUMNS; i++) {
		widths[i] = 0;
	}
	widths[COL_WD] = 		2;
	widths[COL_ID]= 		6;
	widths[COL_NAME] = 		20;
	widths[COL_LOCATION] =		16;
	widths[COL_SOURCE] = 		15;
	widths[COL_STRMILE] = 		6;
	widths[COL_OWNER] = 		12;
	widths[COL_TYPE] = 		9;
	widths[COL_CIU] = 		8;
	widths[COL_TRANSBSN] =		6;
	widths[COL_CAPACITY] = 		7;
	widths[COL_UNITS] = 		6;
	widths[COL_UTMX] = 		10;
	widths[COL_UTMY] = 		10;
	widths[COL_LONGITUDE] = 	8;
	widths[COL_LATITUDE] =		8;
	return widths;
}

/**
Returns the format that the specified column should be displayed in when
the table is being displayed in the given table format.
@param column column for which to return the format.
@return the format (as used by StringUtil.formatString() in which to display the
column.
*/
public String getFormat(int column) {
	switch (column) {
		case COL_WD:		return "%8d";
		case COL_ID:		return "%8d";
		case COL_NAME:		return "%-40s";
		case COL_LOCATION:	return "%24.24s";
		case COL_SOURCE:	return "%-40s";
		case COL_STRMILE:	return "%7.2f";
		case COL_OWNER:		return "%-40s";
		case COL_TYPE:		return "%-40s";
		case COL_CIU:		return "%-1s";
		case COL_TRANSBSN:	return "%8d";
		case COL_CAPACITY:	return "%7.4f";
		case COL_UNITS:		return "%-4s";
		case COL_UTMX:		return "%13.6f";
		case COL_UTMY:		return "%13.6f";
		case COL_LONGITUDE:	return "%11.6f";
		case COL_LATITUDE:	return "%11.6f";
		default:		return "%-8s";
	}
}

/**
Returns the number of rows of data in the table.
*/
public int getRowCount() {
	return _rows;
}

/**
Returns the data that should be placed in the JTable at the given row and column.
@param row the row for which to return data.
@param col the column for which to return data.
@return the data that should be placed in the JTable at the given row and col.
*/
public Object getValueAt(int row, int col) {
	if (_sortOrder != null) {
		row = _sortOrder[row];
	}

	HydroBase_StructureGeoloc r = _data.get(row);
	switch (col) {
		case COL_WD:		return Integer.valueOf(r.getWD());
		case COL_ID:		return Integer.valueOf(r.getID());
		case COL_NAME:		return r.getStr_name();
		case COL_LOCATION:
			return HydroBase_Util.buildLocation(
				r.getPM(), r.getTS(), r.getTdir(),
				r.getRng(), r.getRdir(), r.getSec(), 
				r.getSeca(), r.getQ160(), r.getQ40(),
				r.getQ10());
		case COL_SOURCE:	return r.getStrname();
		case COL_STRMILE:	return Double.valueOf(r.getStr_mile());
		case COL_OWNER:		return r.getFull_name();
		case COL_TYPE:		return __dmi
						.getStructureTypeDescription(
						r.getStr_type());
		case COL_CIU:		return r.getCiu();
		case COL_TRANSBSN:	return Integer.valueOf(r.getTransbsn());
		case COL_CAPACITY:	return Double.valueOf(r.getDcr_capacity());
		case COL_UNITS:		return r.getDcr_unit();
		case COL_UTMX:		return Double.valueOf(r.getUtm_x());
		case COL_UTMY:		return Double.valueOf(r.getUtm_y());
		case COL_LONGITUDE:	return Double.valueOf(r.getLongdecdeg());
		case COL_LATITUDE:	return Double.valueOf(r.getLatdecdeg());
		default:		return " ";
	}
}

}
