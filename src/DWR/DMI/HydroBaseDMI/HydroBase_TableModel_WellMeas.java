// HydroBase_TableModel_WellMeas - Table Model for a Vector of HydroBase_StructureGeolocStructMeasType well meas objects

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
This class is a table model for displaying ground water data in the HydroBase_GUI_GroundWater GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_WellMeas 
extends HydroBase_TableModel<HydroBase_StructureGeolocStructMeasTypeView> {

/**
Number of columns in the table model.
*/
private int __COLUMNS = 9;

/**
Used to refer internally to columns, for 20050701 and greater databases.
*/
public final static int 
	COL_WD = 	0,
	COL_ID = 	1,
	COL_USGS_ID = 	2,
	COL_USBR_ID = 	3,
	COL_STRNAME = 	4,
	COL_ELEVATION = 5,
	COL_LOCATION = 	6,
	COL_START = 	7,
	COL_END = 	8;

/**
Constructor.  This builds the Model for displaying the given ground water results.
@param results the results that will be displayed in the table.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_WellMeas(List<HydroBase_StructureGeolocStructMeasTypeView> results)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_WellMeas constructor.");
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
		case COL_WD:		return Integer.class;
		case COL_ID:		return Integer.class;
		case COL_USGS_ID:	return String.class;
		case COL_USBR_ID:	return String.class;
		case COL_STRNAME:	return String.class;
		case COL_ELEVATION:	return Double.class;
		case COL_LOCATION:	return String.class;
		case COL_START:		return Integer.class;
		case COL_END:		return Integer.class;
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
		case COL_WD:		return "\n\nWD";
		case COL_ID:		return "\n\nID";
		case COL_USGS_ID:	return "\n\nUSGS ID";
		case COL_USBR_ID:	return "\n\nUSBR ID";
		case COL_STRNAME:	return "\n\nNAME";
		case COL_ELEVATION:	return "DATUM\nELEVATION\n(FT)";
		case COL_LOCATION:	return "\n\nLOCATION";
		case COL_START:		return "\n\nSTART";
		case COL_END:		return "\n\nEND";	
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
		case COL_WD:		return "%2d";
		case COL_ID:		return "%2d";
		case COL_USGS_ID:	return "%-20s";
		case COL_USBR_ID:	return "%-20s";
		case COL_STRNAME:	return "%-40s";
		case COL_ELEVATION:	return "%8.2f";
		case COL_LOCATION:	return "%24.24s";
		case COL_START:		return "%8d";
		case COL_END:		return "%8d";
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
Returns the data that should be placed in the JTable at the given row and column.
@param row the row for which to return data.
@param col the column for which to return data.
@return the data that should be placed in the JTable at the given row and col.
*/
public Object getValueAt(int row, int col) {
	if (_sortOrder != null) {
		row = _sortOrder[row];
	}

	HydroBase_StructureGeolocStructMeasTypeView g = _data.get(row);
	switch (col) {
		case COL_WD:		
			return Integer.valueOf(g.getWD());
		case COL_ID:		
			return Integer.valueOf(g.getID());
		case COL_USGS_ID:	
			return g.getUsgs_id();
		case COL_USBR_ID:	
			return g.getUsbr_id();
		case COL_STRNAME:	
			return g.getStr_name();
		case COL_ELEVATION:	
			return Double.valueOf(g.getElev());
		case COL_LOCATION:	
			return HydroBase_Util.buildLocation(g.getPM(),
				g.getTS(), g.getTdir(), g.getRng(), 
				g.getRdir(), g.getSec(), g.getSeca(), 
				g.getQ160(), g.getQ40(), g.getQ10());
		case COL_START:		
			return Integer.valueOf(g.getStart_year());
		case COL_END:		
		return Integer.valueOf(g.getEnd_year());
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

	widths[COL_WD] = 	2;
	widths[COL_ID] = 	4;
	widths[COL_USGS_ID] =	12;
	widths[COL_USBR_ID] =	12;
	widths[COL_STRNAME] = 	22;
	widths[COL_ELEVATION] = 8;
	widths[COL_LOCATION] =	16;
	widths[COL_START] = 	4;
	widths[COL_END] = 	4;
	return widths;
}

}
