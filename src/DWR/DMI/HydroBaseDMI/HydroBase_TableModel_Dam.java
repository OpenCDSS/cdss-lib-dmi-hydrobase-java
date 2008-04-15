// ----------------------------------------------------------------------------
// HydroBase_TableModel_Dam - Table Model for a Vector of 
//	HydroBase_Dam objects.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-10-06	J. Thomas Sapienza, RTi	Initial version.
// 2004-01-20	JTS, RTi		Removed 0th column in order to use the 
//					new JWorksheet row header system.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Vector;

import RTi.Util.Time.DateTime;

/**
This class is a table model for displaying dam data.
*/
public class HydroBase_TableModel_Dam 
extends HydroBase_TableModel {

/**
References to the columns.
*/
public final static int
	COL_SPILLWAY_NAME = 	0,
	COL_SPILLWAY_CAPACITY =	1,
	COL_FREEBOARD = 	2,
	COL_CODE = 		3,
	COL_SPILLWAY_TYPE = 	4,
	COL_WALL_SS = 		5,
	COL_WIDTH = 		6,
	COL_DATE = 		7,
	COL_INSPECTOR = 	8,
	COL_INSPECTION_TYPE = 	9,
	COL_OUTLET_NAME = 	10,
	COL_OUTLET_CAPACITY = 	11,
	COL_DIAMETER = 		12,
	COL_LENGTH = 		13,
	COL_OUTLET_TYPE = 	14,
	COL_DESCRIPTION = 	15,
	COL_EMERGENCY_DATE = 	16,
	COL_EMERGENCY_PLAN = 	17;

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 18;

/**
Constructor.  This builds the Model for displaying the given dam results.
@param results the results that will be displayed in the table.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_Dam(Vector results)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_Dam constructor.");
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
// spillway
		case COL_SPILLWAY_NAME:		return String.class;
		case COL_SPILLWAY_CAPACITY:	return Integer.class;
		case COL_FREEBOARD:		return Double.class;
		case COL_CODE:			return String.class;
		case COL_SPILLWAY_TYPE:		return String.class;
		case COL_WALL_SS:		return Double.class;
		case COL_WIDTH:			return Double.class;

// inspection
		case COL_DATE:			return String.class;
		case COL_INSPECTOR:		return String.class;
		case COL_INSPECTION_TYPE:	return String.class;

// outlet
		case COL_OUTLET_NAME:		return String.class;
		case COL_OUTLET_CAPACITY:	return Double.class;
		case COL_DIAMETER:		return Double.class;
		case COL_LENGTH:		return Double.class;
		case COL_OUTLET_TYPE:		return String.class;
		case COL_DESCRIPTION:		return String.class;

// emergency plan
		case COL_EMERGENCY_DATE:	return String.class;
		case COL_EMERGENCY_PLAN:	return String.class;

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
// spillway
		case COL_SPILLWAY_NAME:		return "SPILLWAY\nNAME";
		case COL_SPILLWAY_CAPACITY:	return "SPILLWAY\nCAPACITY";
		case COL_FREEBOARD:		return "\nFREEBOARD";
		case COL_CODE:			return "FLOW\nCODE";
		case COL_SPILLWAY_TYPE:		return "SPILLWAY\nTYPE";
		case COL_WALL_SS:		return "WALL\nSS";
		case COL_WIDTH:			return "\nWIDTH";

// inspection		
		case COL_DATE:			return "INSP. DATE";
		case COL_INSPECTOR:		return "INSPECTOR";
		case COL_INSPECTION_TYPE:	return "INSP. TYPE";

// outlet		
		case COL_OUTLET_NAME:		return "OUTLET\nNAME";
		case COL_OUTLET_CAPACITY:	return "OUTLET\nCAPACITY";
		case COL_DIAMETER:		return "\nDIAMETER";
		case COL_LENGTH:		return "\nLENGTH";
		case COL_OUTLET_TYPE:		return "OUTLET\nTYPE";
		case COL_DESCRIPTION:		return "OUTLET\nDESCRIPTION";

// emergency plan
		case COL_EMERGENCY_DATE:	return "PLAN DATE";
		case COL_EMERGENCY_PLAN:	return "REQUIRED";
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
// spillway
		case COL_SPILLWAY_NAME:		return "%-20s";
		case COL_SPILLWAY_CAPACITY:	return "%8.1f";
		case COL_FREEBOARD:		return "%8.1f";
		case COL_CODE:			return "%-5s";
		case COL_SPILLWAY_TYPE:		return "%-5s";
		case COL_WALL_SS:		return "%8.1f";
		case COL_WIDTH:			return "%8.1f";

// inspection 
		case COL_DATE:			return "%-20s";
		case COL_INSPECTOR:		return "%-20s";
		case COL_INSPECTION_TYPE:	return "%-20s";

// outlet
		case COL_OUTLET_NAME:		return "%-20s";
		case COL_OUTLET_CAPACITY:	return "%8.3f";
		case COL_DIAMETER:		return "%14.3f";
		case COL_LENGTH:		return "%15.3f";
		case COL_OUTLET_TYPE:		return "%-20s";
		case COL_DESCRIPTION:		return "%-20s";

// emergency plan
		case COL_EMERGENCY_DATE:	return "%-20s";
		case COL_EMERGENCY_PLAN:	return "%-20s";
		
		default:			return "%-8s";
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

	switch (col) {
		// spillway
		case COL_SPILLWAY_NAME:		
		case COL_SPILLWAY_CAPACITY:		
		case COL_FREEBOARD:		
		case COL_CODE:		
		case COL_SPILLWAY_TYPE:		
		case COL_WALL_SS:
		case COL_WIDTH:
			HydroBase_DamSpillway s = (HydroBase_DamSpillway)
				_data.elementAt(row);
			switch (col) {
				case COL_SPILLWAY_NAME:	
					return s.getSpillway_name();
				case COL_SPILLWAY_CAPACITY:	
					return new Integer(s.getCapacity());
				case COL_FREEBOARD:	
					return new Double(s.getFreeboard());
				case COL_CODE:	
					return s.getSply_code();
				case COL_SPILLWAY_TYPE:	
					return s.getSply_type();
				case COL_WALL_SS:	
					return new Double(
						s.getWall_side_slope());
				case COL_WIDTH:	
					return new Double(s.getWidth());
			}

		// inspection
		case COL_DATE:
		case COL_INSPECTOR:
		case COL_INSPECTION_TYPE:
			HydroBase_DamInspection i = (HydroBase_DamInspection)
				_data.elementAt(row);
			switch (col) {
				case COL_DATE:	
					DateTime dti = new DateTime(
						i.getInspect_date());
					dti.setPrecision(DateTime.PRECISION_DAY);
					return dti.toString();
				case COL_INSPECTOR:	
					return i.getInspect_login();
				case COL_INSPECTION_TYPE:	
					return i.getInspection_type();
			}

		// outlet
		case COL_OUTLET_NAME:
		case COL_OUTLET_CAPACITY:
		case COL_DIAMETER:
		case COL_LENGTH:
		case COL_OUTLET_TYPE:
		case COL_DESCRIPTION:
			HydroBase_DamOutlet o = (HydroBase_DamOutlet)
				_data.elementAt(row);
			switch (col) {
				case COL_OUTLET_NAME:	
					return o.getOutlet_name();
				case COL_OUTLET_CAPACITY:	
					return new Double(o.getCapacity());
				case COL_DIAMETER:	
					return new Double(o.getDiameter());
				case COL_LENGTH:	
					return new Double(o.getLength());
				case COL_OUTLET_TYPE:	
					return o.getType();
				case COL_DESCRIPTION:	
					return o.getDescription();
			}

		// emergency plan
		case COL_EMERGENCY_DATE:
		case COL_EMERGENCY_PLAN:
			HydroBase_EmergencyPlan e = (HydroBase_EmergencyPlan)
				_data.elementAt(row);
			switch (col) {
				case COL_EMERGENCY_DATE:	
					DateTime dto = new DateTime(
						e.getEp_date());
					dto.setPrecision(DateTime.PRECISION_DAY);
					return dto.toString();
				case COL_EMERGENCY_PLAN:	
					return e.getEplan();
			}
		
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
// spillway
	widths[COL_SPILLWAY_NAME] = 	15;
	widths[COL_SPILLWAY_CAPACITY] = 8;
	widths[COL_FREEBOARD] = 	8;
	widths[COL_CODE] = 		4;
	widths[COL_SPILLWAY_TYPE] = 	7;
	widths[COL_WALL_SS] = 		4;
	widths[COL_WIDTH] = 		4;

// inspection
	widths[COL_DATE] = 		8;
	widths[COL_INSPECTOR] = 	10;
	widths[COL_INSPECTION_TYPE] = 	10;

// outlet
	widths[COL_OUTLET_NAME] = 	15;
	widths[COL_OUTLET_CAPACITY] = 	7;
	widths[COL_DIAMETER] = 		7;
	widths[COL_LENGTH] = 		6;
	widths[COL_OUTLET_TYPE] = 	7;
	widths[COL_DESCRIPTION] = 	15;

// emergency plan
	widths[COL_EMERGENCY_DATE] = 	8;
	widths[COL_EMERGENCY_PLAN] = 	7;
	return widths;
}

}
