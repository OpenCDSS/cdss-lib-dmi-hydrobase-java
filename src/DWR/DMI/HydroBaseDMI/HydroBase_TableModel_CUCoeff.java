// HydroBase_TableModel_CUCoeff - Table Model for a Vector of HydroBase_CUCoeff objects.

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
// HydroBase_TableModel_CUCoeff - Table Model for a Vector of 
//	HydroBase_CUCoeff objects.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-05-29	J. Thomas Sapienza, RTi	Initial version.
// 2004-01-20	JTS, RTi		Removed 0th column in order to use the 
//					new JWorksheet column header system.
// 2005-04-29	JTS, RTi		Added finalize().
// 2005-06-28	JTS, RTi		Removed the unused DMI parameter.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;

/**
This class is a table model for displaying cu coeff data.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_CUCoeff 
extends HydroBase_TableModel<HydroBase_CUCoeff> {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 3;

/**
References to columns.
*/
public final static int
	COL_NAME = 		0,
	COL_USE = 		1,
	COL_EFFICIENCY = 	2;

/**
Constructor.  This builds the Model for displaying the given Net Amounts results.
@param results the results that will be displayed in the table.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_CUCoeff(List<HydroBase_CUCoeff> results)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_CUCoeff constructor.");
	}
	_rows = results.size();
	_data = results;
}

/**
Returns the class of the data stored in a given column.
@param columnIndex the column for which to return the data class.
*/
public Class<?> getColumnClass (int columnIndex) {
	switch (columnIndex) {
		case COL_NAME:		return String.class;
		case COL_USE:		return Double.class;
		case COL_EFFICIENCY:	return Double.class;
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
		case COL_NAME:		return "CONSUMPTIVE\nUSE\nNAME";
		case COL_USE:		return "\nUSE/DAY\nGALLONS";
		case COL_EFFICIENCY:	return "CONSUMPTIVE\nUSE\nEFFICIENCY";
		default:		return " ";
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
		case  COL_NAME:		return "%-15s";
		case  COL_USE:		return "%8.2f";
		case  COL_EFFICIENCY:	return "%8.2f";
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
Returns the data that should be placed in the JTable
at the given row and column.
@param row the row for which to return data.
@param col the column for which to return data.
@return the data that should be placed in the JTable at the given row and col.
*/
public Object getValueAt(int row, int col) {
	if (_sortOrder != null) {
		row = _sortOrder[row];
	}

	HydroBase_CUCoeff c = _data.get(row);
	switch (col) {
		case COL_NAME:		return c.getConsname();
		case COL_USE:		return new Double(c.getConsuse());
		case COL_EFFICIENCY:	return new Double(c.getCU_coeff());
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
	widths[COL_NAME] = 		13;
	widths[COL_USE] = 		7;
	widths[COL_EFFICIENCY] =	10;
	return widths;
}

}
