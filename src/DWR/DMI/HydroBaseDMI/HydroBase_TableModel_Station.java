// HydroBase_TableModel_Station - Table Model for a Vector of HydroBase_StationGeolocMeasType objects.

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
This class is a table model for displaying station data in the 
HydroBase_GUI_Station GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_Station 
extends HydroBase_TableModel<HydroBase_StationGeolocMeasType> {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 19;

/**
References to the column numbers.
*/
public static final int	
	COL_WD =		0,
	COL_ID =		1,
	COL_ABBREV = 		2,
	COL_NAME = 		3,
	COL_DATA_SOURCE =	4,
	COL_DATA_TYPE =	 	5,
	COL_TIME_STEP =		6,
	COL_START =		7,
	COL_END =		8,
	COL_COUNTY = 		9,
	COL_STATE = 		10,
	COL_HUC = 		11,
	COL_ELEVATION = 	12,
	COL_UTMX = 		13,
	COL_UTMY = 		14,
	COL_LONGITUDE = 	15,
	COL_LATITUDE = 		16,
	COL_DRAIN = 		17,
	COL_CONTR = 		18;
	

/**
A reference to an open dmi object (for use in pulling out some lookup table
information).
*/
private HydroBaseDMI __dmi = null;

/**
If not null, the data type to display instead of that read from the database.
*/
private String __dataType = null;

/**
If not null, the time step to display instead of that read from the database.
*/
private String __timeStep = null;

/**
Constructor.  This builds the Model for displaying the given station results.
@param results the results that will be displayed in the table.
@param dmi a reference to the dmi object used to query for the results.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_Station(List<HydroBase_StationGeolocMeasType> results, HydroBaseDMI dmi)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_Station constructor.");
	}
	if (dmi == null || (dmi.isOpen() == false)) {
		throw new Exception ("Invalid dmi (null or unopened) passed to"
			+ "HydroBase_TableModel_Station constructor.");
	}
	__dmi = dmi;
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
		case COL_ID:		return String.class;
		case COL_ABBREV:	return String.class;
		case COL_NAME:		return String.class;
		case COL_DATA_SOURCE:	return String.class;
		case COL_DATA_TYPE:	return String.class;
		case COL_TIME_STEP:	return String.class;
		case COL_START:		return Integer.class;
		case COL_END:		return Integer.class;
		case COL_COUNTY:	return String.class;
		case COL_STATE:		return String.class;
		case COL_HUC:		return String.class;
		case COL_ELEVATION:	return Double.class;
		case COL_UTMX:		return Double.class;
		case COL_UTMY:		return Double.class;
		case COL_LONGITUDE:	return Double.class;
		case COL_LATITUDE:	return Double.class;
		case COL_DRAIN:		return Double.class;
		case COL_CONTR:		return Double.class;
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
		case COL_ABBREV:	return "\n\nABBREVIATION";
		case COL_NAME:		return "\n\nSTATION NAME";
		case COL_DATA_SOURCE:	return "\nDATA\nSOURCE";
		case COL_DATA_TYPE:	return "\n\nDATA TYPE";
		case COL_TIME_STEP:	return "\nTIME\nSTEP";
		case COL_START:		return "\n\nSTART";		
		case COL_END:		return "\n\nEND";
		case COL_COUNTY:	return "\n\nCOUNTY";
		case COL_STATE:		return "\n\nSTATE";
		case COL_HUC:		return "\n\nHUC";
		case COL_ELEVATION:	return "\n\nELEVATION";
		case COL_UTMX:		return "\n\nUTM X";
		case COL_UTMY:		return "\n\nUTM Y";
		case COL_LONGITUDE:	return "\nLONGITUDE\n(DEC. DEG.)";
		case COL_LATITUDE:	return "\nLATITUDE\n(DEC. DEG.)";
		case COL_DRAIN:		return "DRAIN\nAREA\n(SQMI)";
		case COL_CONTR:		return "CONTRIB.\nAREA\n(SQMI)";
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
		case COL_WD:		return "%8d";
		case COL_ID:		return "%-8s";
		case COL_ABBREV:	return "%-8s";
		case COL_NAME:		return "%-48s";
		case COL_DATA_SOURCE:	return "%-6s";
		case COL_DATA_TYPE:	return "%-10s";
		case COL_TIME_STEP:	return "%-9s";
		case COL_START:		return "%4d";
		case COL_END:		return "%4d";
		case COL_COUNTY:	return "%-20s";
		case COL_STATE:		return "%-2s";
		case COL_HUC:		return "%-8s";
		case COL_ELEVATION:	return "%10.1f";
		case COL_UTMX:		return "%13.6f";
		case COL_UTMY:		return "%13.6f";
		case COL_LONGITUDE:	return "%12.6f";
		case COL_LATITUDE:	return "%12.6f";
		case COL_DRAIN:		return "%8.2f";
		case COL_CONTR:		return "%8.2f";
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

	HydroBase_StationGeolocMeasType s = _data.get(row);
	switch (col) {
		case COL_WD:		return Integer.valueOf(s.getWD());
		case COL_ID:		return s.getStation_id();
		case COL_ABBREV:	return s.getAbbrev();
		case COL_NAME:		return s.getStation_name();
		case COL_DATA_SOURCE:	return s.getData_source();
		case COL_DATA_TYPE:	
					if (__dataType != null) {
						return __dataType;
					}
					String str = s.getVax_field();
					if (str != null 
						&& !(str.trim().equals(""))) {
						return s.getMeas_type() + "-"
							+ str;
					}
					else {
						return s.getMeas_type();
					}
		case COL_TIME_STEP:	
					if (__timeStep != null) {
						return __timeStep;
					}
					return s.getTime_step();
		case COL_START:		return Integer.valueOf(s.getStart_year());
		case COL_END:		return Integer.valueOf(s.getEnd_year());
		case COL_COUNTY:	return __dmi.lookupCountyName(
						s.getCty());
		case COL_STATE:		return s.getST();
		case COL_HUC:		return s.getHUC();
		case COL_ELEVATION:	return Double.valueOf(s.getElev());
		case COL_UTMX:		return Double.valueOf(s.getUtm_x());
		case COL_UTMY:		return Double.valueOf(s.getUtm_y());
		case COL_LONGITUDE:	return Double.valueOf(s.getLongdecdeg());
		case COL_LATITUDE:	return Double.valueOf(s.getLatdecdeg());
		case COL_DRAIN:		return Double.valueOf(s.getDrain_area());
		case COL_CONTR:		return Double.valueOf(s.getContr_area());
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
	widths[COL_WD] = 		2;
	widths[COL_ID] = 		8;
	widths[COL_ABBREV] = 		11;
	widths[COL_NAME] = 		40;
	widths[COL_DATA_SOURCE] = 	6;
	widths[COL_DATA_TYPE] = 	12;
	widths[COL_TIME_STEP] = 	4;
	widths[COL_START] = 		4;
	widths[COL_END] = 		3;
	widths[COL_COUNTY] = 		7;
	widths[COL_STATE] = 		5;
	widths[COL_HUC] = 		6;
	widths[COL_ELEVATION] = 	9;
	widths[COL_UTMX] = 		9;
	widths[COL_UTMY] = 		9;
	widths[COL_LONGITUDE] = 	8;
	widths[COL_LATITUDE] = 		8;
	widths[COL_DRAIN] = 		6;
	widths[COL_CONTR] = 		6;
	return widths;
}

/**
Sets the alternate data type to display, rather than the one read from the
database.
@param dataType the alternate data type to display.
*/
public void setDataType(String dataType) {
	__dataType = dataType;
}

/**
Sets the alternate time step to display, rather than the one read from the
database.
*/
public void setTimeStep(String timeStep) {
	__timeStep = timeStep;
}

}
