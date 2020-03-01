// HydroBase_TableModel_WISDiversionCoding - Table Model for the wis diversion coding gui.

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
// HydroBase_TableModel_WISDiversionCoding - Table Model for the wis diversion
//	coding gui.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-12-04	J. Thomas Sapienza, RTi	Initial version.
// 2004-01-21	JTS, RTi		Removed 0th column in order to use the 
//					new JWorksheet column header system.
// 2004-06-09	JTS, RTi		Worksheet data can now be set 
//					uneditable.
// 2005-04-29	JTS, RTi		Added finalize().
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JWorksheet;

/**
This class is a table model for displaying diversion coding.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_WISDiversionCoding 
extends HydroBase_TableModel<HydroBase_WISDailyWC> {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 7;

/**
Whether the data can be edited or not.
*/
private boolean __editable = false;

/**
References to columns.
*/
public final static int 
	COL_ID =	0,
	COL_S = 	1,
	COL_F = 	2,
	COL_U = 	3,
	COL_T = 	4,
	COL_OBS = 	5,
	COL_AMT = 	6;

private int __day = 0;

/**
The worksheet this table model is being used in.
*/
private JWorksheet __worksheet;

/**
Constructor.  This builds the Model for displaying the given diversion coding data.
@param results the results that will be displayed in the table.  CANNOT be null.
@param day day number for which the coding is being edited.
@throws Exception if invalid results were passed in.
*/
public HydroBase_TableModel_WISDiversionCoding(List<HydroBase_WISDailyWC> results, int day)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_WISDiversionCoding constructor.");
	}
	_data = results;
	_data.add(new HydroBase_WISDailyWC());
	_rows = _data.size();
	__day = day;
}

/**
Returns the class of the data stored in a given column.
@param columnIndex the column for which to return the data class.
*/
public Class<?> getColumnClass (int columnIndex) {
	switch (columnIndex) {
		case COL_ID:		return String.class;
		case COL_S:		return String.class;
		case COL_F:		return Integer.class;
		case COL_U:		return String.class;
		case COL_T:		return String.class;
		case COL_OBS:		return String.class;
		case COL_AMT:		return Double.class;
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
		case COL_ID:		return "STRUCTURE\nID";
		case COL_S:		return "\nSOURCE";
		case COL_F:		return "FROM\n(ID)";
		case COL_U:		return "\nUSE";
		case COL_T:		return "\nTYPE";
		case COL_OBS:		return "\nOBS";
		case COL_AMT:		return "AMOUNT\n(CFS)";
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
		case COL_ID:	return "%8s";
		case COL_S:	return "%-8s";
		case COL_F:	return "%8d";
		case COL_U:	return "%-8s";
		case COL_T:	return "%-8s";
		case COL_OBS:	return "%-8s";
		case COL_AMT:	return "%10.2f";
		default:	return "%-8s";
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
Calculates the total amount and returns it.
@return the total amount.
*/
public double getTotalAmount() {
	double total = 0;
	HydroBase_WISDailyWC wc = null;
	double d = 0;
	for (int i = 0; i < (_data.size() - 1); i++) {
		wc = _data.get(i);
		d = wc.getAmountForDay(__day);
		if (!DMIUtil.isMissing(d) && !HydroBase_Util.isMissing(d)) {
			total += d;
		}
	}
	return total;
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

	boolean lastRow = false;
	if (row == (_data.size() - 1)) {
		lastRow = true;
	}

	if (lastRow) {
		switch (col) {
			case COL_ID:	return "TOTAL";
			case COL_S:	return " ";
			case COL_F:	return new Integer(DMIUtil.MISSING_INT);
			case COL_U:	return " ";
			case COL_T:	return " ";
			case COL_OBS:	return " ";
			case COL_AMT:	return new Double(getTotalAmount());
			default:	return "";
		}
	}

	HydroBase_WISDailyWC wc = (HydroBase_WISDailyWC)_data.get(row);
	switch (col) {
		case COL_ID:	return "" + wc.getID();
		case COL_S:	return wc.getS();
		case COL_F:	return new Integer(wc.getF());
		case COL_U:	return wc.getU();
		case COL_T:	return wc.getT();
		case COL_OBS:	return wc.getObservationForDay(__day);
		case COL_AMT:	return new Double(wc.getAmountForDay(__day));
		default:	return "";
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
	widths[COL_ID] =	8;
	widths[COL_S] = 	22;
	widths[COL_F] = 	4;
	widths[COL_U] = 	16;
	widths[COL_T] = 	20;
	widths[COL_OBS] = 	13;
	widths[COL_AMT] = 	5;
	return widths;
}

/**
Determines whether a column is editable or not.
@param rowIndex the row to check
@param colIndex the column to check.
@return true if the column is editable.  False if not.
*/
public boolean isCellEditable(int rowIndex, int colIndex) {
	if (!__editable) {
		return false;
	}
	if (rowIndex == (_data.size() - 1)) {
		return false;
	}
	if (colIndex > 0) {
		return true;
	}
	return false;
}

/**
Sets the value at the specified position to the specified value.
@param value the value to set the cell to.
@param row the row of the cell for which to set the value.
@param col the col of the cell for which to set the value.
*/
public void setValueAt(Object value, int row, int col) {
	HydroBase_WISDailyWC wc = _data.get(row);
	switch (col) {
		case COL_S:	
		case COL_U:
		case COL_T:
		case COL_OBS:
			String s = (String)value;
			int index = s.indexOf(" - ");
			if (index > -1) {
				s = s.substring(0, index);
			}
			s = s.trim();
			if (col == COL_S) {
				wc.setS(s);
			}
			else if (col == COL_U) {
				wc.setU(s);
			}
			else if (col == COL_T) {
				wc.setT(s);
			}
			else if (col == COL_OBS) {
				wc.setObservationForDay(__day, s);
			}
			valueChanged(row, col, value);
			break;
		case COL_F:	
			try {
				int i = ((Integer)value).intValue();
				wc.setF(i);
			}
			catch (Exception e) {
				wc.setF(DMIUtil.MISSING_INT);
			}
			valueChanged(row, col, value);
			break;
		case COL_AMT:
			if (value == null || DMIUtil.isMissing((Double)value) || HydroBase_Util.isMissing((Double)value)) {
				wc.setAmountForDay(__day, 0);
			}
			else {
				double d = ((Double)value).doubleValue();
				wc.setAmountForDay(__day, d);
			}
			__worksheet.refresh();
			valueChanged(row, col, value);
			break;
	}

	super.setValueAt(value, row, col);
}

/**
Sets whether data can be edited or not.
@param editable whether data can be edited or not.
*/
public void setEditable(boolean editable) {
	__editable = editable;
}

/**
Sets the worksheet this table model is being used in.
@param worksheet the worksheet that uses this table model.
*/
public void setWorksheet(JWorksheet worksheet) {
	__worksheet = worksheet;
}

}
