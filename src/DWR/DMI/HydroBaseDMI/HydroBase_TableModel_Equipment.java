// ----------------------------------------------------------------------------
// HydroBase_TableModel_Equipment - Table Model for a Vector of 
//	HydroBase_Equipment objects.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-04-09	J. Thomas Sapienza, RTi	Initial version.
// 2003-05-13	JTS, RTi		Row numbers (the 0th column) now are
//					not affected by column sorting.
// 2004-01-21	JTS, RTi		Removed 0th column in order to use the 
//					new JWorksheet column header system.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Date;
import java.util.List;

import RTi.Util.Time.DateTime;

/**
This class is a table model for displaying equipment data.
*/
public class HydroBase_TableModel_Equipment 
extends HydroBase_TableModel {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 5;

/**
References to columns.
*/
public final static int
	COL_EQUIP_NUM =		0,
	COL_STRUCTURE_NUM =	1,
	COL_DATE_INSTALLED =	2,
	COL_MEAS_DEVICE = 	3,
	COL_RECORDER = 		4;

/**
Constructor.  This builds the Model for displaying the given equipment results.
@param results the results that will be displayed in the table.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_Equipment(List results)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_Equipment constructor.");
	}
	_rows = results.size();
	_data = results;
}

/**
Returns the class of the data stored in a given column.
@param columnIndex the column for which to return the data class.
*/
public Class getColumnClass (int columnIndex) {
	switch (columnIndex) {
		case COL_EQUIP_NUM:		return Integer.class;
		case COL_STRUCTURE_NUM:		return Integer.class;
		case COL_DATE_INSTALLED:	return String.class;
		case COL_MEAS_DEVICE:		return String.class;
		case COL_RECORDER:		return String.class;
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
		case COL_EQUIP_NUM:		return "\nEQUIP NUM";
		case COL_STRUCTURE_NUM:		return "STRUCTURE\nNUM";
		case COL_DATE_INSTALLED:	return "DATE\nINSTALLED";
		case COL_MEAS_DEVICE:		return "MEAS DEVICE";
		case COL_RECORDER:		return "RECORDER";
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
		case  COL_EQUIP_NUM:		return "%8d";
		case  COL_STRUCTURE_NUM:	return "%8d";
		case  COL_DATE_INSTALLED:	return "%-10s";
		case  COL_MEAS_DEVICE:		return "%-20.20s";
		case  COL_RECORDER:		return "%-20.20s";
		default:			return "%-8s";
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

	HydroBase_Equipment e = (HydroBase_Equipment)_data.get(row);
	switch (col) {
		case COL_EQUIP_NUM:		
			return new Integer(e.getEquip_num());	
		case COL_STRUCTURE_NUM:		
			return new Integer(e.getStructure_num());
		case COL_DATE_INSTALLED:
			return parseDate(e.getDate_installed());
		case COL_MEAS_DEVICE:		
			return e.getMeas_device();
		case COL_RECORDER:		
			return e.getRecorder();
		default:	
			return "";
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
	widths[COL_EQUIP_NUM] = 	10;
	widths[COL_STRUCTURE_NUM] = 	14;
	widths[COL_DATE_INSTALLED] = 	11;
	widths[COL_MEAS_DEVICE] = 	12;
	widths[COL_RECORDER] = 		15;
	return widths;
}

private String parseDate(Date d) {
	if (d == null) {
		return "";
	}

	return (new DateTime(d)).toString(DateTime.FORMAT_YYYY_MM_DD);
}

}
