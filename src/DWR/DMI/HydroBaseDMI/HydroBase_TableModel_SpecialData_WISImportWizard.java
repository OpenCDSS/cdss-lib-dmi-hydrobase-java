// HydroBase_TableModel_WISImportWizard - Table Model for the tables in the WIS import wizard.

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

import java.util.List;

/**
This class is a table model for displaying data for the tables in the wis import wizard.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_SpecialData_WISImportWizard
extends HydroBase_TableModel<HydroBase_SpecialData> {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 10;

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
public HydroBase_TableModel_SpecialData_WISImportWizard(List<HydroBase_SpecialData> results) 
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_WISImportWizard constructor.");
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
Returns the data that should be placed in the JTable at the given row and column.
@param row the row for which to return data.
@param col the column for which to return data.
@return the data that should be placed in the JTable at the given row and col.
*/
public Object getValueAt(int row, int col) {
	if (_sortOrder != null) {
		row = _sortOrder[row];
	}
	HydroBase_SpecialData sp = _data.get(row);
	switch (col) {		
		case COL_SD_WIS_NUM:	
			return Integer.valueOf(sp.getWis_num());
		case COL_SD_ID:	
			return sp.getIdentifier();
		case COL_SD_UNITS:	
			return sp.getUnits();
		case COL_SD_DESC:	
			return sp.getMeas_desc();
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
