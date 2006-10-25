// ----------------------------------------------------------------------------
// HydroBase_TableModel_WISImportWizard - Table Model for the tables in the
//	WIS import wizard.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-11-30	J. Thomas Sapienza, RTi	Initial version.
// 2004-01-21	JTS, RTi		Removed 0th column in order to use the 
//					new JWorksheet column header system.
// 2005-05-03	JTS, RTi		Handles view objects now.
// 2005-05-09	JTS, RTi		Only HydroBase_StationView objects are
//					returned from station queries now.
// 2005-06-28	JTS, RTi		Removed unused DMI parameter.
// 2005-11-15	JTS, RTi		Added div column.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Vector;

/**
This class is a table model for displaying data for the tables in the
wis import wizard.
*/
public class HydroBase_TableModel_WISImportWizard 
extends HydroBase_TableModel {

/**
Used to refer to the kind of table model this is.
*/
public final static int
	SPECIAL_DATA = 0,
	STATION_GEOLOC_MEAS_TYPE = 1,
	WIS_FORMAT = 2;

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 10;

/**
The type of table model this is.
*/
private int __type = -1;

/**
References to columns.
*/
public final static int
	COL_SD_WIS_NUM =	0,
	COL_SD_ID =		1,
	COL_SD_UNITS =		2,
	COL_SD_DESC =		3,
	COL_SGMT_NAME =		4,
	COL_SGMT_VAX_FIELD =	5,
	COL_WF_ROW_LABEL =	6,
	COL_WF_IDENTIFIER =	7,
	COL_WF_WIS_NUM =	8,
	COL_WF_ROW_NUM =	9;

/**
Constructor.  This builds the Model for displaying the given data.
@param results the results that will be displayed in the table.
@throws Exception if an invalid results were passed in
*/
public HydroBase_TableModel_WISImportWizard(Vector results, int type) 
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_WISImportWizard constructor.");
	}
	_rows = results.size();
	_data = results;
	__type = type;
}

/**
Returns the class of the data stored in a given column.
@param columnIndex the column for which to return the data class.
*/
public Class getColumnClass (int columnIndex) {
	switch (columnIndex) {
		case  COL_SD_WIS_NUM:		return Integer.class;
		case  COL_SD_ID:		return String.class;
		case  COL_SD_UNITS:		return String.class;
		case  COL_SD_DESC:		return String.class;
		case  COL_SGMT_NAME:		return String.class;
		case  COL_SGMT_VAX_FIELD:	return String.class;
		case  COL_WF_ROW_LABEL:		return String.class;
		case COL_WF_IDENTIFIER:		return String.class;
		case COL_WF_WIS_NUM:		return Integer.class;
		case COL_WF_ROW_NUM:		return Integer.class;
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
		case  COL_SD_WIS_NUM:		return "WIS\nNUMBER";
		case  COL_SD_ID:		return "\nIDENTIFIER";
		case  COL_SD_UNITS:		return "\nUNITS";
		case  COL_SD_DESC:		
			return "\nMEASUREMENT DESCRIPTOR";
		case  COL_SGMT_NAME:		return "\nSTATION NAME";
		case  COL_SGMT_VAX_FIELD:	return "\nVAX FIELD";		
		case  COL_WF_ROW_LABEL:		return "\nROW LABEL";
		case COL_WF_IDENTIFIER:		return "\nDETAIL";
		case COL_WF_WIS_NUM:		return "WIS \nNUMBER";
		case COL_WF_ROW_NUM:		return "ROW\nNUMBER";		
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
		case  COL_SD_WIS_NUM:		return "%8d";
		case  COL_SD_ID:		return "%-20s";
		case  COL_SD_UNITS:		return "%-20s";
		case  COL_SD_DESC:		return "%-20s";
		case  COL_SGMT_NAME:		return "%-20s";
		case  COL_SGMT_VAX_FIELD:	return "%-20s";
		case  COL_WF_ROW_LABEL:		return "%-20s";
		case COL_WF_IDENTIFIER:		return "%-20s";
		case COL_WF_WIS_NUM:		return "%8d";
		case COL_WF_ROW_NUM:		return "%8d";
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
	if (__type == SPECIAL_DATA) {
		HydroBase_SpecialData sp = (HydroBase_SpecialData)
			_data.elementAt(row);
		switch (col) {		
			case COL_SD_WIS_NUM:	
				return new Integer(sp.getWis_num());
			case COL_SD_ID:	
				return sp.getIdentifier();
			case COL_SD_UNITS:	
				return sp.getUnits();
			case COL_SD_DESC:	
				return sp.getMeas_desc();
		}
	}
	else if (__type == STATION_GEOLOC_MEAS_TYPE) {
		HydroBase_StationView view = 
			(HydroBase_StationView)
			_data.elementAt(row);
		switch (col) {
			case COL_SGMT_NAME:		
				return view.getStation_name();
			case COL_SGMT_VAX_FIELD:	
				return view.getVax_field();
		}
	}
	else if (__type == WIS_FORMAT) {
		HydroBase_WISFormat wf = (HydroBase_WISFormat)
			_data.elementAt(row);
		switch (col) {
			case COL_WF_ROW_LABEL:
				return wf.getRow_label();
			case COL_WF_IDENTIFIER:
				return wf.getIdentifier();
			case COL_WF_WIS_NUM:
				return new Integer(wf.getWis_num());
			case COL_WF_ROW_NUM:
				return new Integer(wf.getWis_row());
		}

	}
	return null;
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
	widths[COL_SD_WIS_NUM] = 	7;
	widths[COL_SD_ID] = 		12;
	widths[COL_SD_UNITS] = 		5;
	widths[COL_SD_DESC] = 		20;
	widths[COL_SGMT_NAME] = 	40;
	widths[COL_SGMT_VAX_FIELD] = 	8;
	widths[COL_WF_ROW_LABEL] = 	30;
	widths[COL_WF_IDENTIFIER] = 	9;
	widths[COL_WF_WIS_NUM] = 	7;
	widths[COL_WF_ROW_NUM] = 	7;
	return widths;
}

}
