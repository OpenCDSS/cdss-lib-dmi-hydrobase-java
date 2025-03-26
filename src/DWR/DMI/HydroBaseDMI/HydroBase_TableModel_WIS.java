// HydroBase_TableModel_WIS - Table Model for a WIS display.

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

import java.awt.Color;

import java.util.List;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.JWorksheet_CellAttributes;

/**
This class is a table model for displaying wis data.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_WIS 
extends HydroBase_TableModel<HydroBase_WISData> {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 12;

/**
Whether the data is editable.
*/
private boolean __editable = true;

/**
The GUI on which the worksheet is shown.
*/
private HydroBase_GUI_WIS __parentWIS;

/**
The worksheet which uses this table model.
*/
private JWorksheet __worksheet;

/**
Cell attributes for different kinds of cells in the worksheet.
*/
private JWorksheet_CellAttributes 
	__labelAttr,
	__commentAttr,
	__knownPointAttr,
	__formulaAttr,
	__computedAttr,
	__stationImportAttr;

/**
Constructor.  This builds the Model for displaying the wis data.
@param data the data that will be displayed in the table.
@param wis the wis in which the table will appear.
@param editable whether the data can be edited or not.
@throws Exception if invalid data were passed in.
*/
public HydroBase_TableModel_WIS(List<HydroBase_WISData> data, HydroBase_GUI_WIS wis,
boolean editable)
throws Exception {
	if (data == null) {
		throw new Exception ("Invalid data list passed to HydroBase_TableModel_WIS constructor.");
	}
	_rows = data.size();
	_data = data;
	__editable = editable;
	__parentWIS = wis;

	initialize();
}

/**
Returns the class of the data stored in a given column.
@param columnIndex the column for which to return the data class.
*/
public Class<?> getColumnClass (int columnIndex) {
	switch (columnIndex) {
		case  0:	
			return Integer.class;
		case HydroBase_GUI_WIS.ROW_LABEL_COL:	
			return String.class;
		case HydroBase_GUI_WIS.POINT_FLOW_COL:	
			return Double.class;
		case HydroBase_GUI_WIS.NATURAL_FLOW_COL:
			return Double.class;
		case HydroBase_GUI_WIS.DELIVERY_FLOW_COL:	
			return Double.class;
		case HydroBase_GUI_WIS.GAIN_LOSS_COL:	
			return Double.class;
		case HydroBase_GUI_WIS.RELEASES_COL:	
			return Double.class;
		case HydroBase_GUI_WIS.PRIORITY_DIV_COL:	
			return Double.class;
		case HydroBase_GUI_WIS.DELIVERY_DIV_COL:	
			return Double.class;
		case HydroBase_GUI_WIS.TRIB_NATURAL_COL:	
			return Double.class;
		case HydroBase_GUI_WIS.TRIB_DELIVERY_COL:	
			return Double.class;
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
			return "%10.1f";
		case HydroBase_GUI_WIS.NATURAL_FLOW_COL:	
			return "%10.1f";
		case HydroBase_GUI_WIS.DELIVERY_FLOW_COL:	
			return "%10.1f";
		case HydroBase_GUI_WIS.GAIN_LOSS_COL:	
			return "%10.1f";
		case HydroBase_GUI_WIS.RELEASES_COL:	
			return "%10.1f";
		case HydroBase_GUI_WIS.PRIORITY_DIV_COL:	
			return "%10.1f";
		case HydroBase_GUI_WIS.DELIVERY_DIV_COL:	
			return "%10.1f";
		case HydroBase_GUI_WIS.TRIB_NATURAL_COL:	
			return "%10.1f";
		case HydroBase_GUI_WIS.TRIB_DELIVERY_COL:	
			return "%10.1f";
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
Returns the data that should be placed in the JTable at the given row and column.
@param row the row for which to return data.
@param col the column for which to return data.
@return the data that should be placed in the JTable at the given row and col.
*/
public Object getValueAt(int row, int col) {
	// make sure the row numbers are never sorted ...
	if (col == 0) {
		return Integer.valueOf(row + 1);
	}
	
	if (_sortOrder != null) {
		row = _sortOrder[row];
	}

	HydroBase_WISData w = (HydroBase_WISData)_data.get(row);
	switch (col) {
		case HydroBase_GUI_WIS.ROW_LABEL_COL:	
			return w.getRow_label();
		case HydroBase_GUI_WIS.POINT_FLOW_COL:	
			return Double.valueOf(w.getPoint_flow());
		case HydroBase_GUI_WIS.NATURAL_FLOW_COL:	
			return Double.valueOf(w.getNat_flow());
		case HydroBase_GUI_WIS.DELIVERY_FLOW_COL:
			return Double.valueOf(w.getDelivery_flow());
		case HydroBase_GUI_WIS.GAIN_LOSS_COL:	
			return Double.valueOf(w.getGain());
		case HydroBase_GUI_WIS.RELEASES_COL:	
			return Double.valueOf(w.getRelease());
		case HydroBase_GUI_WIS.PRIORITY_DIV_COL:	
			return Double.valueOf(w.getPriority_divr());
		case HydroBase_GUI_WIS.DELIVERY_DIV_COL:	
			return Double.valueOf(w.getDelivery_divr());
		case HydroBase_GUI_WIS.TRIB_NATURAL_COL:	
			return Double.valueOf(w.getTrib_natural());
		case HydroBase_GUI_WIS.TRIB_DELIVERY_COL:	
			return Double.valueOf(w.getTrib_delivery());
		case HydroBase_GUI_WIS.COMMENTS_COL:	
			return w.getComment();
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
Initializes cell style attributes.
*/
private void initialize() {
        //set cell styles
	__labelAttr = new JWorksheet_CellAttributes();
	__labelAttr.backgroundColor = Color.lightGray;
	__labelAttr.editable = false;
	__labelAttr.horizontalAlignment = JWorksheet.LEFT;

	__commentAttr = new JWorksheet_CellAttributes();
	__commentAttr.horizontalAlignment = JWorksheet.LEFT;

	__knownPointAttr = new JWorksheet_CellAttributes();
	__knownPointAttr.horizontalAlignment = JWorksheet.RIGHT;

	__formulaAttr = new JWorksheet_CellAttributes();
	__formulaAttr.foregroundColor = Color.red;
	__formulaAttr.backgroundColor = Color.lightGray;
	__formulaAttr.editable = false;
	__formulaAttr.horizontalAlignment = JWorksheet.RIGHT;

	__computedAttr = new JWorksheet_CellAttributes();
	__computedAttr.backgroundColor = Color.lightGray;
	__computedAttr.editable = false;
	__computedAttr.horizontalAlignment = JWorksheet.RIGHT;

	__stationImportAttr = new JWorksheet_CellAttributes();
	__stationImportAttr.foregroundColor = Color.blue;
	__stationImportAttr.horizontalAlignment = JWorksheet.RIGHT;	
}

/**
Returns whether the cell at the given position is editable or not.  In this
table model all columns above #2 are editable.
@param rowIndex unused
@param columnIndex the index of the column to check for whether it is editable.
@return whether the cell at the given position is editable.
*/
public boolean isCellEditable(int rowIndex, int columnIndex) {
	if (!__editable) {
		return false;
	}
	else {
		return true;
	}
}

public void setValueAt(Object o, int row, int col) {
	// o will always be passed in as a String -- convert accordingly
	HydroBase_WISData w = _data.get(row);
	boolean valueSet = false;
	boolean commentSet = false;
	switch (col) {
		case HydroBase_GUI_WIS.ROW_LABEL_COL:	
			w.setRow_label((String)o);	break;
		case HydroBase_GUI_WIS.COMMENTS_COL:	
			commentSet = true;
			w.setComment((String)o);	break;		
		case HydroBase_GUI_WIS.POINT_FLOW_COL:	
		case HydroBase_GUI_WIS.NATURAL_FLOW_COL:
		case HydroBase_GUI_WIS.DELIVERY_FLOW_COL:
		case HydroBase_GUI_WIS.GAIN_LOSS_COL:
		case HydroBase_GUI_WIS.RELEASES_COL:
		case HydroBase_GUI_WIS.PRIORITY_DIV_COL:
		case HydroBase_GUI_WIS.DELIVERY_DIV_COL:
		case HydroBase_GUI_WIS.TRIB_NATURAL_COL:
		case HydroBase_GUI_WIS.TRIB_DELIVERY_COL:			
			Double D = null;
			if (o instanceof String) {
				String s = (String)o;
				if (s.trim().equals("")) {
					D = Double.valueOf(DMIUtil.MISSING_DOUBLE);
				}
				else {
					D = Double.valueOf((String)o);
				}
			}
			else {
				if (o == null) {
					D = Double.valueOf(DMIUtil.MISSING_DOUBLE);
				}
				else {
					D = (Double)o;
				}
			}
			double d = D.doubleValue();
			switch (col) {
				case HydroBase_GUI_WIS.POINT_FLOW_COL:	
					if (w.getPoint_flow() != d) {
						valueSet = true;
						w.setPoint_flow(d);	
						__parentWIS
							.updateDiagramPointFlow(
							row, d);	
					}
					break;
				case HydroBase_GUI_WIS.NATURAL_FLOW_COL:	
					if (w.getNat_flow() != d) {
						valueSet = true;
						w.setNat_flow(d);	
					}
					break;
				case HydroBase_GUI_WIS.DELIVERY_FLOW_COL:	
					if (w.getDelivery_flow() != d) {
						valueSet = true;
						w.setDelivery_flow(d);	
					}
					break;
				case HydroBase_GUI_WIS.GAIN_LOSS_COL:	
					if (w.getGain() != d) {
						valueSet = true;
						w.setGain(d);		
					}
					break;
				case HydroBase_GUI_WIS.RELEASES_COL:	
					if (w.getRelease() != d) {
						valueSet = true;
						w.setRelease(d);	
					}
					break;
				case HydroBase_GUI_WIS.PRIORITY_DIV_COL:
					if (w.getPriority_divr() != d) {
						valueSet = true;
						w.setPriority_divr(d);	
					}
					break;
				case HydroBase_GUI_WIS.DELIVERY_DIV_COL:	
					if (w.getDelivery_divr() != d) {
						valueSet = true;
						w.setDelivery_divr(d);
					}
					break;
				case HydroBase_GUI_WIS.TRIB_NATURAL_COL:	
					if (w.getTrib_natural() != d) {
						valueSet = true;
						w.setTrib_natural(d);	
					}
					break;
				case HydroBase_GUI_WIS.TRIB_DELIVERY_COL:	
					if (w.getTrib_delivery() != d) {
						valueSet = true;
						w.setTrib_delivery(d);	
					}
					break;
			}
	}

	super.setValueAt(o, row, col);

	// only recompute WIS automatically if one of the numeric fields
	// was changed.
	if (valueSet) {
		__parentWIS.valueSet(row, col);
		__parentWIS.wisChanged(true);
	}

	// but if comments were set, mark dirty
	if (commentSet) {
		__parentWIS.wisChanged(true);
	}
}


public void setWorksheet(JWorksheet worksheet) {
	__worksheet = worksheet;
}

public void setCellContents(double contents, String format, int row, 
int col, String rowType, boolean isEntryCell, boolean isFormulaCell, 
boolean isImportCell, boolean isFirstCalc) {
/*
	if (row == 1 && col == 2) {
	__worksheet.except(1, 4);
	Message.printStatus(1, "",
		"'" + contents + "' row: " + row
		+ " col: " + col + " rt: " + rowType + " en? " + isEntryCell
		+ " fo? " + isFormulaCell + " im? " + isImportCell + " fi? "
		+ isFirstCalc);
	}
*/
	// always display formulae regardless of value
	if (isFormulaCell) {
	       	setCellContents(contents, format, row, col, __formulaAttr);
	}
	// always display imports regardless of value
	else if (isImportCell) {
		if (!isFirstCalc || DMIUtil.isMissing(contents) || HydroBase_Util.isMissing(contents)) {
			setCellContents(contents, row, col, __knownPointAttr);
		}
		else {	
	        	setCellContents(contents, format, row, col, __stationImportAttr);
		}
	}
	// do not display values for Stream and String row types
	// unless the cell is a formula
	else if (rowType.equals(HydroBase_GUI_WIS.STREAM) || rowType.equals(HydroBase_GUI_WIS.STRING)) {
		setCellContents(DMIUtil.MISSING_DOUBLE, format, row, col, __computedAttr);
	}	
	else if (isEntryCell) {
		// always display formulas regardless of value
		if (isFormulaCell) {
		       	setCellContents(contents, format, row, col, __formulaAttr);
		}
		// display contents of user entered cells
		// only if 0.0 was not entered
		else if (contents != 0.0 && col != HydroBase_GUI_WIS.POINT_FLOW_COL) {
			setCellContents(contents, row, col, __knownPointAttr);
		}
		else if (col == HydroBase_GUI_WIS.POINT_FLOW_COL) {
			setCellContents(contents, row, col, __knownPointAttr);
		}
	}
	else if (!isEntryCell) {
		// always display formulas regardless of value
		if (isFormulaCell) {
		       	setCellContents(contents, format, row, col, __formulaAttr);
		}
		// display contents regardless of value in these columns
		else if (col == HydroBase_GUI_WIS.POINT_FLOW_COL
			|| col == HydroBase_GUI_WIS.NATURAL_FLOW_COL
			|| col == HydroBase_GUI_WIS.DELIVERY_FLOW_COL
			|| col == HydroBase_GUI_WIS.GAIN_LOSS_COL) {
		       	setCellContents(contents, format, row, col, 
				__computedAttr);	
		}
		// for the remaining columns, display only if the 
		// contents of cell are not 0.0
		else if (contents != 0.0) {
		       	setCellContents(contents, format, row, col, __computedAttr);
		}
		// for contents = 0.0, used the missing data value
		// to force the cell to be formatted, but have
		// no value displayed.
		else if (contents == 0.0) {
		       	setCellContents(DMIUtil.MISSING_DOUBLE, format, row, col, __computedAttr);
		}
	}
}

/**
This function sets the contents of the specified cell without 
changing any of the other cell attributes.
@param contents cell contents as a String
@param row cell row
@param col cell column
@param rowType row type as defined in HydroBase_GUI_WIS
*/
public void setCellContents(String contents, int row, int col, String rowType) {
/*
	Message.printStatus(1, "",
		"'" + contents + "' row: " + row
		+ " col: " + col + " rt: " + rowType);
*/
	JWorksheet_CellAttributes ca = null;
	
	if (col == HydroBase_GUI_WIS.ROW_LABEL_COL) {
		ca = __labelAttr;
	}
	else if (col == HydroBase_GUI_WIS.COMMENTS_COL) {
		ca = __commentAttr;
	}
	else if (!rowType.equals(HydroBase_GUI_WIS.STRING) && !rowType.equals(HydroBase_GUI_WIS.STREAM)) {
		if (col < HydroBase_GUI_WIS.TRIB_NATURAL_COL) {
			ca = __computedAttr;
		}
		else {
			ca = __knownPointAttr;
                }
	}
	else {	
		ca = getCellStyle(rowType, true, false, col);
	}
       	setCellContents(contents, row, col, ca);
} 

/**
Sets the contents of the specified cell.
@param contents cell contents
@param row cell row
@param col cell column
@param ca the cell attributes to apply in the given cell
*/
public void setCellContents(String contents, int row, int col, 
JWorksheet_CellAttributes ca) {
	setValueAt(contents, row, col);
	__worksheet.setCellAttributes(row, col, ca);
}

/**
Set the contents of the specified cell.
@param contents	cell contents as a double.
@param row cell row.
@param col cell column.
@param ca JWorksheet_CellAttributes object.
*/
public void setCellContents(double contents, int row, int col, 
JWorksheet_CellAttributes ca) {
	setValueAt((Double.valueOf(contents)).toString(), row, col);
	__worksheet.setCellAttributes(row, col, ca);
}

/**
Sets the contents of the specified cell.
@param contents cell contents
@param format format code (e.g., %10.1f etc..).  If the format is "DRY", then
the cell contents are set to "DRY".  This is a special case for the WIS.
@param row cell row
@param col cell column
@param ca JWorksheet_CellAttributes object
*/
public void setCellContents(double contents, String format, int row, 
int col, JWorksheet_CellAttributes ca) {
	String text = null;

	if (format.equals("DRY")) {
		// Special case for WIS...
                text = "DRY";
	}
        else {	
		text = "" + contents;
	}
	setValueAt(text, row, col);
	__worksheet.setCellAttributes(row, col, ca);
}

/**
Returns the cell style based on the row type and column.
@param rowType row type as defined in HydroBase_GUI_WIS
@param isEntryCell true if the cell is a user entry cell, false otherwise
@param isFormula true if the cell is a formula cell, false othewise.
@param col column number
@return JWorksheet_CellAttributes object
*/
private JWorksheet_CellAttributes getCellStyle(String rowType, 
boolean isEntryCell, boolean isFormula, int col) {
	if (rowType.trim().equals("")) {
		rowType = HydroBase_GUI_WIS.STRING;
	}
	
	if (rowType.equals(HydroBase_GUI_WIS.STATION ) 
		|| rowType.equals(HydroBase_GUI_WIS.DIVERSION)
		|| rowType.equals(HydroBase_GUI_WIS.RESERVOIR)
		|| rowType.equals(HydroBase_GUI_WIS.MIN_FLOW_REACH)
		|| rowType.equals(HydroBase_GUI_WIS.OTHER)) {
                if (isEntryCell && col == HydroBase_GUI_WIS.POINT_FLOW_COL) {
                        return __knownPointAttr;
                }
		else if (isFormula) {
			return __formulaAttr;	
		}
                else if (!isEntryCell) {
                        return __computedAttr;
                }
                else {
                        return __knownPointAttr;
                }
        }
	else if (rowType.equals(HydroBase_GUI_WIS.STRING) && !isFormula) {
               return __computedAttr;
	}
	else if (rowType.equals(HydroBase_GUI_WIS.STREAM)) {
               return __computedAttr;
	}
        else {
		if (isFormula) {
			return __formulaAttr;	
		}
		else if (col == HydroBase_GUI_WIS.COMMENTS_COL) {
			return __commentAttr;
		}
                else if (!isEntryCell) {
                        return __computedAttr;
                }
                else {
                        return __knownPointAttr;
                }
        }
}

/**
This function changes the style of the specified cell to __computedAttr
@param row cell row
@param col cell column
*/
public void setCellAsComputedStyle(int row, int col) {
	setCellStyle(row, col, __computedAttr);
}

/**
This function changes the style of the specified cell to __formulaAttr
@param row cell row
@param col cell column
*/
public void setCellAsFormulaStyle(int row, int col) {
	this.setCellStyle(row, col, __formulaAttr);
}

/**
This function changes the style of the specified cell to __knownPointAttr
@param row cell row
@param col cell column
*/
public void setCellAsBaseflowStyle(int row, int col) {
	this.setCellStyle(row, col, __knownPointAttr);
}

/**
Set the cell style to the specified style.
@param row desired row to set
@param col desired colmun to set
@param ca JWorksheet_CellAttributes Object
*/
public void setCellStyle(int row, int col, JWorksheet_CellAttributes ca) {
	__worksheet.setCellAttributes(row, col, ca);
}

}
