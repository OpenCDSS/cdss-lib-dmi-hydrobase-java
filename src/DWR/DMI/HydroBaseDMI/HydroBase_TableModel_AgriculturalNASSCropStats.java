// HydroBase_TableModel_AgriculturalNASSCropStats - Table Model for a Vector of HydroBase_AgriculturalNASSCropStats objects.

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
// HydroBase_TableModel_AgriculturalNASSCropStats - Table Model for a Vector of 
//	HydroBase_AgriculturalNASSCropStats objects.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2005-02-10	J. Thomas Sapienza, RTi	Initial version.
// 2005-04-29	JTS, RTi		Added finalize().
// 2005-06-28	JTS, RTi		Removed the unused DMI parameter.
// 2005-07-06	Steven A. Malers, RTi	Change the output format for acres to
//					.0 and add ACRE to the heading.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;

/**
This class is a table model for displaying national agstats data.
*/
public class HydroBase_TableModel_AgriculturalNASSCropStats 
extends HydroBase_TableModel {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 6;

/**
References to the columns.
*/
public final static int 
	COL_ST =	0,
	COL_COUNTY = 	1,
	COL_COMMODITY = 2,
	COL_CAL_YEAR = 	3,
	COL_AG_AMT = 	4,
	COL_FLAG = 	5;

/**
Constructor.  This builds the Model for displaying the given agstats results.
@param results the results that will be displayed in the table.
@param dmi a reference to the dmi object used to query for the results.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_AgriculturalNASSCropStats(List results) 
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_AgriculturalNASSCropStats "
			+ "constructor.");
	}
	_rows = results.size();
	_data = results;
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	super.finalize();
}

/**
Returns the class of the data stored in a given column.
@param columnIndex the column for which to return the data class.
*/
public Class getColumnClass (int columnIndex) {
	switch (columnIndex) {
		case COL_ST:		return String.class;
		case COL_COUNTY:	return String.class;
		case COL_COMMODITY:	return String.class;
		case COL_CAL_YEAR:	return Integer.class;
		case COL_AG_AMT:	return Double.class;
		case COL_FLAG:		return String.class;
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
		case COL_ST:		return "\nSTATE";
		case COL_COUNTY:	return "\nCOUNTY";
		case COL_COMMODITY:	return "\nCOMMODITY";
		case COL_CAL_YEAR:	return "CALENDAR\nYEAR";
		case COL_AG_AMT:	return "AMOUNT\n(ACRE)";
		case COL_FLAG:		return "\nFLAG";
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
	widths[COL_CAL_YEAR] = 7;
	widths[COL_AG_AMT] = 7;
	widths[COL_FLAG] = 3;
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
		case COL_CAL_YEAR:	return "%8d";
		case COL_AG_AMT:	return "%10.0f";
		case COL_FLAG:		return "%-40s";
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
Returns the data that should be placed in the JTable at the given row and column.
@param row the row for which to return data.
@param col the column for which to return data.
@return the data that should be placed in the JTable at the given row and col.
*/
public Object getValueAt(int row, int col) {
	if (_sortOrder != null) {
		row = _sortOrder[row];
	}

	HydroBase_AgriculturalNASSCropStats n = (HydroBase_AgriculturalNASSCropStats)_data.get(row);
	switch (col) {
		case COL_ST:		return n.getST();
		case COL_COUNTY:	return n.getCounty();
		case COL_COMMODITY:	return n.getCommodity();
		case COL_CAL_YEAR:	return new Integer(n.getCal_year());
		case COL_AG_AMT:	return new Double(n.getAg_amt());
		case COL_FLAG:		return n.getFlag();
		default:		return "";
	}
}

}
