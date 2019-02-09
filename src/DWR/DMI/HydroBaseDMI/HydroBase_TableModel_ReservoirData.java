// HydroBase_TableModel_ReservoirData - Table Model for a Vector of HydroBase_AreaCap objects.

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

// ----------------------------------------------------------------------------
// HydroBase_TableModel_ReservoirData - Table Model for a Vector of 
//	HydroBase_AreaCap objects.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-10-02	J. Thomas Sapienza, RTi	Initial version.
// 2004-01-21	JTS, RTi		Removed 0th column in order to use the 
//					new JWorksheet column header system.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;

/**
This class is a table model for displaying reservoir data in the 
HydroBase_GUI_ReservoirData GUI.
*/
public class HydroBase_TableModel_ReservoirData 
extends HydroBase_TableModel {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 4;

/**
References to columns.
*/
public final static int
	COL_ELEVATION = 	0,
	COL_GAGE_HEIGHT =	1,	
	COL_SURFACE_AREA = 	2,
	COL_VOLUME = 		3;

/**
Constructor.  This builds the Model for displaying the given reservoir data results.
@param results the results that will be displayed in the table.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_ReservoirData(List results)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_ReservoirData constructor.");
	}
	_rows = results.size();
	_data = results;
}

/**
From AbstractTableModel.  Returns the class of the data stored in a given
column.
@param columnIndex the column for which to return the data class.
*/
public Class getColumnClass (int columnIndex) {
	switch (columnIndex) {
		case COL_ELEVATION:		return Double.class;
		case COL_GAGE_HEIGHT:		return Double.class;
		case COL_SURFACE_AREA:		return Double.class;
		case COL_VOLUME:		return Double.class;
		default:			return String.class;
	}
}

/**
From AbstractTableModel.  Returns the number of columns of data.
@return the number of columns of data.
*/
public int getColumnCount() {
	return __COLUMNS;
}

/**
From AbstractTableModel.  Returns the name of the column at the given position.
@return the name of the column at the given position.
*/
public String getColumnName(int columnIndex) {
	switch (columnIndex) {
		case COL_ELEVATION:		return "ELEVATION (FT)";
		case COL_GAGE_HEIGHT:		return "GAGE HEIGHT (FT)";
		case COL_SURFACE_AREA:		return "SURFACE AREA (ACRES)";
		case COL_VOLUME:		return "VOLUME (AF)";
		default:			return " ";
	}	
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
		case  COL_ELEVATION:	return "%6.0f";
		case  COL_GAGE_HEIGHT:	return "%14.1f";
		case  COL_SURFACE_AREA:	return "%16.0f";
		case  COL_VOLUME:	return "%13.0f";
		default:		return "%-8s";
	}
}

/**
From AbstractTableModel.  Returns the number of rows of data in the table.
*/
public int getRowCount() {
	return _rows;
}

/**
From AbstractTableModel.  Returns the data that should be placed in the JTable at the given row and column.
@param row the row for which to return data.
@param col the column for which to return data.
@return the data that should be placed in the JTable at the given row and col.
*/
public Object getValueAt(int row, int col) {
	if (_sortOrder != null) {
		row = _sortOrder[row];
	}

	HydroBase_AreaCap a = (HydroBase_AreaCap)_data.get(row);
	switch (col) {
		case COL_ELEVATION:	return new Double(a.getElevation());
 		case COL_GAGE_HEIGHT:	return new Double(a.getGage_height());
		case COL_SURFACE_AREA:	return new Double(a.getSurface_area());
		case COL_VOLUME:	return new Double(a.getVolume());
		default:		return "";
	}
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
	widths[COL_ELEVATION] = 	11;
	widths[COL_GAGE_HEIGHT] = 	13;
	widths[COL_SURFACE_AREA] = 	17;
	widths[COL_VOLUME] = 		10;
	return widths;
}

}
