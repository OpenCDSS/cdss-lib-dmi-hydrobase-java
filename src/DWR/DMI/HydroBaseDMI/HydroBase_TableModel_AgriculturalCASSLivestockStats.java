// HydroBase_TableModel_AgriculturalCASSLivestockStats - Table Model for a Vector of HydroBase_AgriculturalCASSLivestockStats objects.

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
This class is a table model for displaying Colorado agstats data.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_AgriculturalCASSLivestockStats 
extends HydroBase_TableModel<HydroBase_AgriculturalCASSLivestockStats> {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 6;

/**
References to the columns.
*/
public final static int 
	COL_ST = 		0,
	COL_COUNTY = 		1,
	COL_COMMODITY =		2,
	COL_TYPE = 		3,
	COL_CAL_YEAR = 		4,
	COL_HEAD = 		5;

/**
Constructor.  This builds the Model for displaying the given agstats results.
@param results the results that will be displayed in the table.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_AgriculturalCASSLivestockStats(List<HydroBase_AgriculturalCASSLivestockStats> results)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_AgriculturalCASSLivestockStats constructor.");
	}
	_rows = results.size();
	_data = results;
}

/**
From AbstractTableModel.  Returns the class of the data stored in a given column.
@param columnIndex the column for which to return the data class.
*/
public Class<?> getColumnClass (int columnIndex) {
	switch (columnIndex) {
		case COL_ST:			return String.class;
		case COL_COUNTY:		return String.class;
		case COL_COMMODITY:		return String.class;
		case COL_TYPE:			return String.class;
		case COL_CAL_YEAR:		return Integer.class;
		case COL_HEAD:			return Double.class;
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
		case COL_ST:		return "\n\nSTATE";
		case COL_COUNTY:	return "\n\nCOUNTY";
		case COL_COMMODITY:	return "\n\nCOMMODITY";
		case COL_TYPE:	return "\n\nTYPE";
		case COL_CAL_YEAR:	return "\nCALENDAR\nYEAR";
		case COL_HEAD:		return "\n\nHEAD";
		default:		return " ";
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

	widths[COL_ST] = 4;
	widths[COL_COUNTY] = 8;
	widths[COL_COMMODITY] = 15;
	widths[COL_TYPE] = 25;
	widths[COL_CAL_YEAR] = 7;
	widths[COL_HEAD] = 5;

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
		case COL_ST:		return "%-40s";
		case COL_COUNTY:	return "%-40s";
		case COL_COMMODITY:	return "%-40s";
		case COL_TYPE:	return "%-40s";
		case COL_CAL_YEAR:	return "%8d";
		case COL_HEAD:		return "%10.3f";
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
From AbstractTableModel.  Returns the data that should be placed in the JTable
at the given row and column.
@param row the row for which to return data.
@param col the column for which to return data.
@return the data that should be placed in the JTable at the given row and col.
*/
public Object getValueAt(int row, int col) {
	if (_sortOrder != null) {
		row = _sortOrder[row];
	}

	HydroBase_AgriculturalCASSLivestockStats c = _data.get(row);
	switch (col) {
		case COL_ST:		return c.getSt();
		case COL_COUNTY:	return c.getCounty();
		case COL_COMMODITY:	return c.getCommodity();
		case COL_TYPE:	return c.getType();
		case COL_CAL_YEAR:	return Integer.valueOf(c.getCal_year());
		case COL_HEAD:		return Integer.valueOf(c.getHead());
		default:		return "";
	}
}

}