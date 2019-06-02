// HydroBase_TableModel_StationView - Table Model for a Vector of HydroBase_StationView objects.

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
// HydroBase_TableModel_StationView - Table Model for a Vector of 
//	HydroBase_StationView objects.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2005-02-09	J. Thomas Sapienza, RTi	Initial version.
// 2005-07-06	Steven A. Malers, RTi	Add tool tips.  Review headers.
// 2005-11-15	JTS, RTi		Added div column.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;

/**
This class is a table model for displaying station data in the 
HydroBase_GUI_Station GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_StationView 
extends HydroBase_TableModel<HydroBase_StationView> {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 20;

/**
References to the column numbers.
*/
public static final int	
	COL_DIV = 0,
	COL_WD = 1,
	COL_ID = 2,
	COL_ABBREV = 3,
	COL_NAME = 4,
	COL_DATA_SOURCE = 5,
	COL_DATA_TYPE = 6,
	COL_TIME_STEP = 7,
	COL_START = 8,
	COL_END = 9,
	COL_COUNTY = 10,
	COL_STATE = 11,
	COL_HUC = 12,
	COL_ELEVATION = 13,
	COL_UTMX = 14,
	COL_UTMY = 15,
	COL_LONGITUDE = 16,
	COL_LATITUDE = 17,
	COL_DRAIN = 18,
	COL_CONTR = 19;
	
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
public HydroBase_TableModel_StationView(List<HydroBase_StationView> results, HydroBaseDMI dmi)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results list passed to " 
			+ "HydroBase_TableModel_StationView constructor.");
	}
	if (dmi == null || (dmi.isOpen() == false)) {
		throw new Exception ("Invalid dmi (null or unopened) passed to"
			+ "HydroBase_TableModel_StationView constructor.");
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
		case COL_DIV:		return Integer.class;
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
		case COL_DIV:		return "\n\nDIV";
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
		case COL_ELEVATION:	return "\nELEVATION\n(FT)";
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
Returns the text to be assigned to worksheet tooltips.
@return a String array of tool tips.
*/
public String[] getColumnToolTips() {
	String[] tips = new String[__COLUMNS];

	tips[COL_DIV] = "Water division.";
	tips[COL_WD] = "Water district.";
	tips[COL_ID] = "Identifier used by data source.";
	tips[COL_ABBREV] = "State of Colorado Satellite Monitoring System abbreviation.";
	tips[COL_NAME] = "Station name.";
	tips[COL_DATA_SOURCE] =	"Data source for time series.";
	tips[COL_DATA_TYPE] = "Time series data type.";
	tips[COL_TIME_STEP] = "<HTML>Time series time step (data interval).<BR>" +
		"Irregular is typically real-time data.</HTML>";
	tips[COL_START] = "<HTML>Starting year for time series.<BR>" +
		"May not be available for real-time data.</HTML>";
	tips[COL_END] = "<HTML>Ending year for time series.<BR>" +
		"May not be available for real-time data.</HTML>";
	tips[COL_COUNTY] = "County where station is located.";
	tips[COL_STATE] = "State where station is located.";
	tips[COL_HUC] = "USGS Hydrologic Unit Code where station is located.";
	tips[COL_ELEVATION] = "Station elevation (FT).";
	tips[COL_UTMX] = "Station UTM X coordinate.";
	tips[COL_UTMY] = "Station UTM Y coordinate.";
	tips[COL_LONGITUDE] = "Station longitude, decimal degrees.";
	tips[COL_LATITUDE] = "Station latitude, decimal degrees.";
	tips[COL_DRAIN] = "Drainage area (SQMI), in particular for streamflow stations.";
	tips[COL_CONTR] = "<HTML>Any non-natural area that supplies water to the " +
		"station, in square miles,<BR>" +
		"in particular for streamflow stations.</HTML>";

	return tips;
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

	widths[COL_DIV] = 3;
	widths[COL_WD] = 2;
	widths[COL_ID] = 8;
	widths[COL_ABBREV] = 11;
	widths[COL_NAME] = 40;
	widths[COL_DATA_SOURCE] = 6;
	widths[COL_DATA_TYPE] = 12;
	widths[COL_TIME_STEP] = 4;
	widths[COL_START] = 4;
	widths[COL_END] = 3;
	widths[COL_COUNTY] = 7;
	widths[COL_STATE] = 5;
	widths[COL_HUC] = 6;
	widths[COL_ELEVATION] = 9;
	widths[COL_UTMX] = 9;
	widths[COL_UTMY] = 9;
	widths[COL_LONGITUDE] = 8;
	widths[COL_LATITUDE] = 8;
	widths[COL_DRAIN] = 6;
	widths[COL_CONTR] = 6;
	return widths;
}

/**
Returns the format that the specified column should be displayed in when
the table is being displayed in the given table format. 
@param column column for which to return the format.
@return the format (as used by StringUtil.formatString() in which to display the column.
*/
public String getFormat(int column) {
	switch (column) {
		case COL_DIV:		return "%8d";
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

	HydroBase_StationView s = _data.get(row);
	switch (col) {
		case COL_DIV:		return new Integer(s.getDiv());
		case COL_WD:		return new Integer(s.getWD());
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
		case COL_START:		return new Integer(s.getStart_year());
		case COL_END:		return new Integer(s.getEnd_year());
		case COL_COUNTY:	return __dmi.lookupCountyName(s.getCty());
		case COL_STATE:		return s.getST();
		case COL_HUC:		return s.getHUC();
		case COL_ELEVATION:	return new Double(s.getElev());
		case COL_UTMX:		return new Double(s.getUtm_x());
		case COL_UTMY:		return new Double(s.getUtm_y());
		case COL_LONGITUDE:	return new Double(s.getLongdecdeg());
		case COL_LATITUDE:	return new Double(s.getLatdecdeg());
		case COL_DRAIN:		return new Double(s.getDrain_area());
		case COL_CONTR:		return new Double(s.getContr_area());
		default:		return "";
	}
}

/**
Sets the alternate data type to display, rather than the one read from the database.
@param dataType the alternate data type to display.
*/
public void setDataType(String dataType) {
	__dataType = dataType;
}

/**
Sets the alternate time step to display, rather than the one read from the database.
*/
public void setTimeStep(String timeStep) {
	__timeStep = timeStep;
}

}
