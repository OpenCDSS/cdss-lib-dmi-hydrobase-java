// ----------------------------------------------------------------------------
// HydroBase_CellRenderer - The cell renderer to be used for tables in
//	HydroBase.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2005-06-22	J. Thomas Sapienza, RTi	Initial version.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.Component;

import java.util.Date;

import javax.swing.JTable;
import javax.swing.SwingConstants;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.JWorksheet_AbstractExcelCellRenderer;
import RTi.Util.GUI.JWorksheet_AbstractRowTableModel;

import RTi.Util.String.StringUtil;

/**
This class is the class from which other Cell Renderers for HydroBase
should be built.
*/
public class HydroBase_CellRenderer
extends JWorksheet_AbstractExcelCellRenderer {

/**
The table model this cell renderer works with.
*/
JWorksheet_AbstractRowTableModel __tableModel = null;

/**
Constructor. 
@param tableModel the table model this cell renderer will work with.
*/
public HydroBase_CellRenderer(JWorksheet_AbstractRowTableModel tableModel) {
	__tableModel = tableModel;
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	__tableModel = null;
	super.finalize();
}

/**
Returns the format for a given column.
@param column the colum for which to return the format.
@return the format (as used by StringUtil.format) for a column.
*/
public String getFormat(int column) {
	return __tableModel.getFormat(column);
}

/**
Renders a value for a cell in a JTable.  This method is called automatically
by the JTable when it is rendering its cells.  This overrides some code from
DefaultTableCellRenderer.
@param table the JTable (in this case, JWorksheet) in which the cell
to be rendered will appear.
@param value the cell's value to be rendered.
@param isSelected whether the cell is selected or not.
@param hasFocus whether the cell has focus or not.
@param row the row in which the cell appears.
@param column the column in which the cell appears.
@return a properly-rendered cell that can be placed in the table.
*/
public Component getTableCellRendererComponent(JTable table, Object value,
boolean isSelected, boolean hasFocus, int row, int column) {
	String str = "";
 	if (value != null) {
		str = value.toString();
	}
	
	int abscolumn = ((JWorksheet)table).getAbsoluteColumn(column);
	
	String format = getFormat(abscolumn);
	
	int justification = SwingConstants.LEFT;

	if (value instanceof Integer) {
		if (DMIUtil.isMissing(((Integer)value).intValue())) {
			str = "";
		} 
		else {
			justification = SwingConstants.RIGHT;
			str = StringUtil.formatString(value, format);
		}
	}	
	else if (value instanceof Double) {		
		if (DMIUtil.isMissing(((Double)value).doubleValue())) {
			str = "";
		}	
		else {
			justification = SwingConstants.RIGHT;
			str = StringUtil.formatString(value, format);
		}
	}
	else if (value instanceof Date) {
		justification = SwingConstants.LEFT;		
		// FYI: str has been set above with str = value.toString()
	}
	else if (value instanceof String) {
		justification = SwingConstants.LEFT;
		str = StringUtil.formatString(value, format);
	}
	else {
		justification = SwingConstants.LEFT;
	}

	str = str.trim();

	// call DefaultTableCellRenderer's version of this method so that
	// all the cell highlighting is handled properly.
	super.getTableCellRendererComponent(table, str, 
		isSelected, hasFocus, row, column);	

	int tableAlignment = ((JWorksheet)table).getColumnAlignment(abscolumn);
	if (tableAlignment != JWorksheet.DEFAULT) {
		justification = tableAlignment;
	}
		
	setHorizontalAlignment(justification);
	setFont(((JWorksheet)table).getCellFont());

	return this;
}

}
