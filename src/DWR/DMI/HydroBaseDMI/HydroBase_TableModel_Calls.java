// ----------------------------------------------------------------------------
// HydroBase_TableModel_Calls - Table Model for a Vector of 
//	HydroBase_Calls objects.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-03-28	J. Thomas Sapienza, RTi	Initial version.
// 2003-03-31	JTS, RTi		Fixed error in formatting DateTime 
//					values.
// 2003-05-13	JTS, RTi		Row numbers (the 0th column) now are
//					not affected by column sorting.
// 2004-01-18	JTS, RTi		Apro date is now returned as a String.
// 2004-01-20	JTS, RTi		Removed 0th column in order to use the 
//					new JWorksheet row header system.
// 2005-04-06	JTS, RTi		Adjusted column names and sizes.
// 2005-04-29	JTS, RTi		Added finalize().
// 2005-06-28	JTS, RTi		Removed the unused DMI parameter.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Date;
import java.util.Vector;

import RTi.DMI.DMIUtil;

import RTi.Util.Time.DateTime;

/**
This class is a table model for displaying calls data in the 
HydroBase_GUI_Calls GUI.
*/
public class HydroBase_TableModel_Calls 
extends HydroBase_TableModel {

/**
References to the columns.
*/
public final static int
	COL_START = 	0,
	COL_END = 	1,
	COL_SOURCE = 	2,
	COL_WDID = 	3,
	COL_NAME = 	4,
	COL_APRO = 	5,
	COL_ADMIN_NO = 	6,
	COL_DCR_AMT = 	7,
	COL_DISTRICTS = 8,
	COL_SET = 	9,
	COL_RELEASE = 	10;
/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 11;
	
/**
Constructor.  This builds the Model for displaying the given calls.
@param results the results that will be displayed in the table.
@param dmi a reference to the dmi object used to query for the results.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_Calls(Vector results) 
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_Calls constructor.");
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
		case COL_START:		return String.class;
		case COL_END:		return String.class;
		case COL_SOURCE:	return String.class;
		case COL_WDID:		return String.class;
		case COL_NAME:		return String.class;
		case COL_APRO:		return String.class;
		case COL_ADMIN_NO:	return Double.class;
		case COL_DCR_AMT:	return String.class;
		case COL_DISTRICTS:	return String.class;
		case COL_SET:		return String.class;
		case COL_RELEASE:	return String.class;
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
		case COL_START:		return "\nSTART DATE/TIME";
		case COL_END:		return "\nEND DATE/TIME";
		case COL_SOURCE:	return "\nWATER SOURCE";
		case COL_WDID:		return "\nWDID";
		case COL_NAME:		return "\nSTRUCTURE NAME";
		case COL_APRO:		return "APPROPRIATION\nDATE";
		case COL_ADMIN_NO:	return "ADMINISTRATION\nNO";
		case COL_DCR_AMT:	return "DECREED\nAMOUNT";
		case COL_DISTRICTS:	return "DISTRICTS AFFECTED";
		case COL_SET:		return "SET COMMENTS";
		case COL_RELEASE:	return "RELEASE COMMENTS";
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
		case COL_START:		return "%-16s";
		case COL_END:		return "%-16s";
		case COL_SOURCE:	return "%-24s";
		case COL_WDID:		return "%-7s";
		case COL_NAME:		return "%-28s";
		case COL_APRO:		return "%-10s";
		case COL_ADMIN_NO:	return "%12.5f";
		case COL_DCR_AMT:	return "%-20s";
		case COL_DISTRICTS:	return "%-30s";
		case COL_SET:		return "%-60s";
		case COL_RELEASE:	return "%-60s";
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

	HydroBase_Calls c = (HydroBase_Calls)_data.elementAt(row);
	Date d;
	switch (col) {
		case COL_START:		
			d = c.getDate_time_set();
			if (DMIUtil.isMissing(d)) {
				return "";
			}
			else {
				return (new DateTime(d))
					.toString(
					DateTime.FORMAT_YYYY_MM_DD_HH_mm);
			}
		case COL_END:		
			d = c.getDate_time_released();
			if (DMIUtil.isMissing(d)) {
				return "ACTIVE";
			}
			else {
				return (new DateTime(d))
					.toString(
					DateTime.FORMAT_YYYY_MM_DD_HH_mm);
			}
		case COL_SOURCE:	return c.getStrname();
		case COL_WDID:	
			String wds = null;
			String ids = null;
			int wd = c.getWD();
			if (DMIUtil.isMissing(wd)) {
				wds = "";
			}
			else {
				wds = "" + wd;
			}			
			int id = c.getID();
			if (DMIUtil.isMissing(id)) {
				ids = "";
			}
			else {
				ids = "" + id;
			}
			return HydroBase_WaterDistrict.formWDID(wds, ids);	
		case COL_NAME:		return c.getStr_name();
		case COL_APRO:		return (new DateTime(
						c.getApro_date())).toString(
						DateTime.FORMAT_YYYY_MM_DD);
		case COL_ADMIN_NO:	return new Double(c.getAdminno());
		case COL_DCR_AMT:	return c.getDcr_amt();
		case COL_DISTRICTS:	return c.getDistricts_affected();
		case COL_SET:		return c.getSet_comments();
		case COL_RELEASE:	return c.getRelease_comments();
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
	widths[COL_START] = 	12;
	widths[COL_END] = 	12;
	widths[COL_SOURCE] = 	24;
	widths[COL_WDID] = 	6;
	widths[COL_NAME] = 	18;
	widths[COL_APRO] = 	13;
	widths[COL_ADMIN_NO] = 	13;
	widths[COL_DCR_AMT] = 	8;
	widths[COL_DISTRICTS] = 15;
	widths[COL_SET] = 	20;
	widths[COL_RELEASE] =	20;
	return widths;
}

}
