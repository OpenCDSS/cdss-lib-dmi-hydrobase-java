// HydroBase_TableModel_Contact - Table Model for a Vector of HydroBase_Contact objects.

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
// HydroBase_TableModel_Contact - Table Model for a Vector of 
//	HydroBase_Contact objects.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-10-02	J. Thomas Sapienza, RTi	Initial version.
// 2004-01-20	JTS, RTi		Removed 0th column in order to use the 
//					new JWorksheet column header system.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;

/**
This class is a table model for displaying contact data.
*/
public class HydroBase_TableModel_Contact 
extends HydroBase_TableModel {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 4;

/**
References to columns.
*/
public final static int 
	COL_MEANS = 	0,
	COL_TRY = 	1,
	COL_PHONE = 	2,
	COL_OTHER = 	3;

/**
Constructor.  This builds the Model for displaying the given contact data.
@param results the results that will be displayed in the table.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_Contact(List results)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_Contact constructor.");
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
		case COL_MEANS:		return String.class;
		case COL_TRY:		return String.class;
		case COL_PHONE:		return String.class;
		case COL_OTHER:		return String.class;
		default:		return String.class;
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
		case COL_MEANS:		return "CONTACT MEANS";
		case COL_TRY:		return "TRY FIRST";
		case COL_PHONE:		return "PHONE NUMBER";
		case COL_OTHER:		return "OTHER";
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
		case  COL_MEANS:	return "%-20s";
		case  COL_TRY:		return "%-20s";
		case  COL_PHONE:	return "%-20s";
		case  COL_OTHER:	return "%-20s";
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

	HydroBase_Contact c = (HydroBase_Contact)_data.get(row);
	switch (col) {
		case COL_MEANS:		return c.getContact_type();
		case COL_TRY:		return c.getFirst_contact();
		case COL_PHONE:		
			String s = c.getPhone_ext();
			String ext = "";
			if (s.length() > 0) {
				ext = ", ext. " + s;
			}
			return c.getPhone_number() + ext;
		case COL_OTHER:		return c.getContact_text();
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
	widths[COL_MEANS] = 	12;
	widths[COL_TRY] = 	12;
	widths[COL_PHONE] = 	13;
	widths[COL_OTHER] = 	8;
	return widths;
}

}
