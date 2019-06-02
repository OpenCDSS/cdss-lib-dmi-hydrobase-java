// HydroBase_TableModel_ReservoirMeasurement - Table Model for a Vector of HydroBase_ResMeas objects.

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
// HydroBase_TableModel_ReservoirMeasurement - Table Model for a Vector of 
//	HydroBase_ResMeas objects.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-09-26	J. Thomas Sapienza, RTi	Initial version.
// 2004-01-21	JTS, RTi		Removed 0th column in order to use the 
//					new JWorksheet column header system.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;

/**
This class is a table model for displaying reservoir measurement information.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_ReservoirMeasurement 
extends HydroBase_TableModel<HydroBase_ResMeas> {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 6;

/**
References to columns.
*/
public final static int 
	COL_DATE = 		0,
	COL_GAGE_HEIGHT =	1,
	COL_STORAGE = 		2,
	COL_INFLOW = 		3,
	COL_RELEASE = 		4,
	COL_EVAP_LOSS = 	5;

/**
Constructor.  This builds the Model for displaying the given reservoir measurement data.
@param results the results that will be displayed in the table.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_ReservoirMeasurement(List<HydroBase_ResMeas> results) 
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_ReservoirMeasurement constructor.");
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
		case COL_DATE:			return String.class;
		case COL_GAGE_HEIGHT:		return Double.class;
		case COL_STORAGE:		return Double.class;
		case COL_INFLOW:		return Double.class;
		case COL_RELEASE:		return Double.class;
		case COL_EVAP_LOSS:		return Double.class;
		default:			return String.class;
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
		case COL_DATE:			return "DATE";
		case COL_GAGE_HEIGHT:		return "GAGE HT. (FT)";
		case COL_STORAGE:		return "STORAGE (ACFT)";
		case COL_INFLOW:		return "INFLOW (ACFT)";
		case COL_RELEASE:		return "RELEASE (ACFT)";
		case COL_EVAP_LOSS:		return "EVAP LOSS (ACFT)";
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
		case COL_DATE:		return "%-8s";
		case COL_GAGE_HEIGHT:	return "%7.1f";
		case COL_STORAGE:	return "%7.1f";
		case COL_INFLOW:	return "%7.1f";
		case COL_RELEASE:	return "%7.1f";
		case COL_EVAP_LOSS:	return "%7.1f";
		default:		return "%-8s";
	}
}

/**
Returns the number of rows of data in the table.
@return the number of rows of data in the table.
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

	HydroBase_ResMeas r = _data.get(row);
	switch (col) {
		case COL_DATE:		return r.getDate_time();
		case COL_GAGE_HEIGHT:	return new Double(r.getGage_height());
		case COL_STORAGE:	return new Double(r.getStorage_amt());
		case COL_INFLOW:	return new Double(r.getFill_amt());
		case COL_RELEASE:	return new Double(r.getRelease_amt());
		case COL_EVAP_LOSS:	return new Double(r.getEvap_loss_amt());
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
	widths[COL_DATE] = 		6;
	widths[COL_GAGE_HEIGHT] =	10;
	widths[COL_STORAGE] = 		12;
	widths[COL_INFLOW] = 		12;
	widths[COL_RELEASE] = 		12;
	widths[COL_EVAP_LOSS] = 	14;
	return widths;
}

}
