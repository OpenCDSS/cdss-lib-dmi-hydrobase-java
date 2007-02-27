// ----------------------------------------------------------------------------
// HydroBase_TableModel_WISBuilder - Table Model for a WIS display.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-11-20	J. Thomas Sapienza, RTi	Initial version.
// 2005-04-29	JTS, RTi		Added finalize().
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Vector;

/**
This class is a table model for displaying wis data.
*/
public class HydroBase_TableModel_WISBuilder 
extends HydroBase_TableModel {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 12;

/**
Constructor.
@param data the data for the worksheet.
@param wisBuilder the GUI in which this worksheet appears.
*/
public HydroBase_TableModel_WISBuilder(Vector data, 
HydroBase_GUI_WISBuilder wisBuilder)
throws Exception {
	if (data == null) {
		throw new Exception ("Invalid data Vector passed to " 
			+ "HydroBase_TableModel_WISBuilder constructor.");
	}
	_rows = data.size();
	_data = data;
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
		case  0:	
			return Integer.class;
		case HydroBase_GUI_WIS.ROW_LABEL_COL:	
			return String.class;
		case HydroBase_GUI_WIS.POINT_FLOW_COL:	
			return String.class;
		case HydroBase_GUI_WIS.NATURAL_FLOW_COL:
			return String.class;
		case HydroBase_GUI_WIS.DELIVERY_FLOW_COL:	
			return String.class;
		case HydroBase_GUI_WIS.GAIN_LOSS_COL:	
			return String.class;
		case HydroBase_GUI_WIS.RELEASES_COL:	
			return String.class;
		case HydroBase_GUI_WIS.PRIORITY_DIV_COL:	
			return String.class;
		case HydroBase_GUI_WIS.DELIVERY_DIV_COL:	
			return String.class;
		case HydroBase_GUI_WIS.TRIB_NATURAL_COL:	
			return String.class;
		case HydroBase_GUI_WIS.TRIB_DELIVERY_COL:	
			return String.class;
		case HydroBase_GUI_WIS.COMMENTS_COL:	
			return String.class;
		default:	
			return String.class;
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
		case  0:	
			return " ";
		case HydroBase_GUI_WIS.ROW_LABEL_COL:	
			return HydroBase_GUI_WIS.ROW_LABEL;
		case HydroBase_GUI_WIS.POINT_FLOW_COL:	
			return HydroBase_GUI_WIS.POINT_FLOW;
		case HydroBase_GUI_WIS.NATURAL_FLOW_COL:	
			return HydroBase_GUI_WIS.NATURAL_FLOW;
		case HydroBase_GUI_WIS.DELIVERY_FLOW_COL:		
			return HydroBase_GUI_WIS.DELIVERY_FLOW;
		case HydroBase_GUI_WIS.GAIN_LOSS_COL:	
			return HydroBase_GUI_WIS.GAIN_LOSS;
		case HydroBase_GUI_WIS.RELEASES_COL:	
			return HydroBase_GUI_WIS.RELEASES;
		case HydroBase_GUI_WIS.PRIORITY_DIV_COL:	
			return HydroBase_GUI_WIS.PRIORITY_DIV;
		case HydroBase_GUI_WIS.DELIVERY_DIV_COL:	
			return HydroBase_GUI_WIS.DELIVERY_DIV;
		case HydroBase_GUI_WIS.TRIB_NATURAL_COL:	
			return HydroBase_GUI_WIS.TRIB_NATURAL;
		case HydroBase_GUI_WIS.TRIB_DELIVERY_COL:	
			return HydroBase_GUI_WIS.TRIB_DELIVERY;
		case HydroBase_GUI_WIS.COMMENTS_COL:	
			return HydroBase_GUI_WIS.COMMENTS;
		default:	
			return " ";
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
		case  0:	
			return "%8d";	// row #
		case HydroBase_GUI_WIS.ROW_LABEL_COL:	
			return "%-20s";	
		case HydroBase_GUI_WIS.POINT_FLOW_COL:	
			return "%-20s";	
		case HydroBase_GUI_WIS.NATURAL_FLOW_COL:	
			return "%-20s";	
		case HydroBase_GUI_WIS.DELIVERY_FLOW_COL:	
			return "%-20s";	
		case HydroBase_GUI_WIS.GAIN_LOSS_COL:	
			return "%-20s";	
		case HydroBase_GUI_WIS.RELEASES_COL:	
			return "%-20s";	
		case HydroBase_GUI_WIS.PRIORITY_DIV_COL:	
			return "%-20s";	
		case HydroBase_GUI_WIS.DELIVERY_DIV_COL:	
			return "%-20s";	
		case HydroBase_GUI_WIS.TRIB_NATURAL_COL:	
			return "%-20s";	
		case HydroBase_GUI_WIS.TRIB_DELIVERY_COL:	
			return "%-20s";	
		case HydroBase_GUI_WIS.COMMENTS_COL:	
			return "%-20s";	
		default:	
			return "%-8s";
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
	// make sure the row numbers are never sorted ...
	if (col == 0) {
		return new Integer(row + 1);
	}
	
	if (_sortOrder != null) {
		row = _sortOrder[row];
	}

	HydroBase_WISData w = (HydroBase_WISData)_data.elementAt(row);
	switch (col) {
		case HydroBase_GUI_WIS.ROW_LABEL_COL:	
			return w.getRow_label() + w.getEtc();
		case HydroBase_GUI_WIS.GAIN_LOSS_COL:
			return w.getGainLoss();
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
	int i = 0;
	widths[i++] = 5;	// row #
	widths[i++] = 20;	// row label
	widths[i++] = 7;	// point flow
	widths[i++] = 6;	// nat flow
	widths[i++] = 5;	// delivery flow
	widths[i++] = 6;	// gain/loss
	widths[i++] = 6;	// trib natural
	widths[i++] = 6;	// trib delivery
	widths[i++] = 6;	// release
	widths[i++] = 6;	// priority divr
	widths[i++] = 6;	// delivery divr
	widths[i++] = 20;	// comments
	return widths;
}

/**
Returns whether the cell at the given position is editable or not.  In this
table model all columns above #2 are editable.
@param rowIndex unused
@param columnIndex the index of the column to check for whether it is editable.
@return whether the cell at the given position is editable.
*/
public boolean isCellEditable(int rowIndex, int columnIndex) {
	return false;
}

/**
Sets the value at the specified position.
@param o the value to set.
@param row the row to set the value in.
@param col the column to set the value in.
*/
public void setValueAt(Object o, int row, int col) {
	// o will always be passed in as a String -- convert accordingly
	HydroBase_WISData w = (HydroBase_WISData)_data.elementAt(row);
	switch (col) {
		case HydroBase_GUI_WIS.ROW_LABEL_COL:	
			w.setRow_label((String)o);	
			break;
		case HydroBase_GUI_WIS.COMMENTS_COL:	
			w.setComment((String)o);	
			break;		
		case HydroBase_GUI_WIS.GAIN_LOSS_COL:
			w.setGainLoss((String)o);
			break;
		case 99:
			w.setEtc((String)o);
			return;
	}

	super.setValueAt(o, row, col);
}

}
