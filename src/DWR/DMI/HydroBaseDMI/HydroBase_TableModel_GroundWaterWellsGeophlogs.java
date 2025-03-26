// HydroBase_TableModel_GroundWaterWellsGeophlogs - Table Model for a Vector of HydroBase_GroundWaterWellsView objects

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

import RTi.DMI.DMIUtil;

import RTi.Util.Time.DateTime;

/**
This class is a table model for displaying geophlogs data in the 
HydroBase_GUI_GroundWater GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_GroundWaterWellsGeophlogs 
extends HydroBase_TableModel<HydroBase_GroundWaterWellsGeophlogs> {

/**
Number of columns in the table model.
*/
private int __COLUMNS = 20;

/**
Used to refer internally to columns, for 20050701 and greater databases.
*/
public final static int 
	COL_DIV =		0,
	COL_WD = 		1,
	COL_WELL_NAME =		2,
	COL_LOC_NUM =		3,
	COL_SITE_ID =		4,	
	COL_PERMIT_INFO =	5,
	COL_LOCATION =		6,
	COL_ELEV =		7,
	COL_DEPTH =		8,
	COL_LOG_TYPE =		9,
	COL_LOG_SWL =		10,
	COL_LOG_DEPTH =		11,
	COL_LOG_DATE =		12,
	COL_UTM_X =		13,
	COL_UTM_Y =		14,	
	COL_LATDECDEG = 	15,
	COL_LONDECDEG = 	16,
	COL_DSS_AQUIFER1 = 	17,
	COL_DSS_AQUIFER2 = 	18,
	COL_DSS_AQUIFER_COMMENT = 19;	

/**
List of aquifers to display in the tooltips.
*/
private List<HydroBase_Aquifer> __aquifers = null;

/**
Constructor.  This builds the Model for displaying the given ground water 
results.
@param results the results that will be displayed in the table.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_GroundWaterWellsGeophlogs(List<HydroBase_GroundWaterWellsGeophlogs> results, List<HydroBase_Aquifer> aquifers)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_GroundWaterWellsGeophlogs constructor.");
	}

	_rows = results.size();
	_data = results;
	__aquifers = aquifers;
}

/**
Returns the class of the data stored in a given column.
@param columnIndex the column for which to return the data class.
*/
public Class<?> getColumnClass (int columnIndex) {
	switch (columnIndex) {
		case COL_DIV:		return Integer.class;
		case COL_WD:		return Integer.class;
		case COL_WELL_NAME:	return String.class;
		case COL_PERMIT_INFO:	return String.class;
		case COL_LOCATION:	return String.class;
		case COL_UTM_X:		return Double.class;
		case COL_UTM_Y:		return Double.class;
		case COL_LATDECDEG:	return Double.class;
		case COL_LONDECDEG:	return Double.class;		
		case COL_ELEV:		return Double.class;
		case COL_DEPTH:		return Integer.class;
		case COL_LOG_TYPE:	return String.class;
		case COL_LOG_SWL:	return Integer.class;
		case COL_LOG_DEPTH:	return Integer.class;
		case COL_LOG_DATE:	return String.class;
		case COL_LOC_NUM:	return String.class;
		case COL_SITE_ID:	return String.class;
		case COL_DSS_AQUIFER1:	return String.class;
		case COL_DSS_AQUIFER2:	return String.class;
		case COL_DSS_AQUIFER_COMMENT:	return String.class;		
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
		case COL_DIV:		return "\nDIV";
		case COL_WD:		return "\nWD";
		case COL_WELL_NAME:	return "\nWELL NAME";
		case COL_PERMIT_INFO:	return "\nPERMIT INFO";
		case COL_LOCATION:	return "\nLOCATION";
		case COL_UTM_X:		return "\nUTM X";
		case COL_UTM_Y:		return "\nUTM Y";
		case COL_LATDECDEG:	return "LATITUDE\n(DEC. DEG.)";	
		case COL_LONDECDEG:	return "LONGITUDE\n(DEC. DEG.)";
		case COL_ELEV:		return "WELL\nELEVATION";
		case COL_DEPTH:		return "WELL\nDEPTH";
		case COL_LOG_TYPE:	return "\nLOG TYPE";
		case COL_LOG_SWL:	return "\nLOG SWL";
		case COL_LOG_DEPTH:	return "LOG\nDEPTH";
		case COL_LOG_DATE:	return "\nLOG DATE";
		case COL_LOC_NUM:	return "\nLOC NUM";
		case COL_SITE_ID:	return "\nSITE ID";
		case COL_DSS_AQUIFER1:	return "DSS\nAQUIFER 1";
		case COL_DSS_AQUIFER2:	return "DSS\nAQUIFER 2";
		case COL_DSS_AQUIFER_COMMENT:	return "DSS AQUIFER\nCOMMENT";
		default:		return " ";
	}	
}

/**
Returns the text to be assigned to worksheet tooltips.
@return a String array of tool tips.
*/
public String[] getColumnToolTips() {
	String[] tips = new String[__COLUMNS];

	tips[COL_DIV] = 
		"Water division.";
	tips[COL_WD] = 
		"Water district.";
	tips[COL_WELL_NAME] =
		"Well name.";
	tips[COL_LOC_NUM] =
		"Location number, generally based on location.";
	tips[COL_SITE_ID] =
		"Site ID (USGS identifier).";
	tips[COL_PERMIT_INFO] =
		"Concatenated permit number, suffix, and replacement.";
	tips[COL_LOCATION] =
		"Legal location.";
	tips[COL_ELEV] = 
		"Well elevation, feet.";
	tips[COL_DEPTH] =
		"Well depth, feet.";
	tips[COL_LOG_TYPE] = 
		"Log type.";
	tips[COL_LOG_SWL] = 
		"Log SWL.";
	tips[COL_LOG_DEPTH] = 
		"Log depth, feet.";
	tips[COL_LOG_DATE] = 
		"Log date.";
	tips[COL_UTM_X] =
		"UTM X coordinate.";
	tips[COL_UTM_Y] =
		"UTM Y coordinate.";
	tips[COL_LATDECDEG] =
		"Latitude in decimal degrees.";
	tips[COL_LONDECDEG] =
		"Longitude in decimal degrees.";		

	tips[COL_DSS_AQUIFER1] = 
		"<html>Aquifer determined during decision support<br>"
		+ "system implementation.<p>"
		+ HydroBase_GUI_Util.getAquiferListForToolTip(__aquifers)
		+ "</html>";
	tips[COL_DSS_AQUIFER2] = 
		"<html>Aquifer determined during decision support<br>"
		+ "system implementation.<p>"
		+ HydroBase_GUI_Util.getAquiferListForToolTip(__aquifers)
		+ "</html>";
	tips[COL_DSS_AQUIFER_COMMENT] = 
		"<html>Aquifer comment determined during decision support<br>"
		+ "system implementation.</html>";

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

	widths[COL_DIV] = 2;
	widths[COL_WD] = 2;
	widths[COL_WELL_NAME] = 19;
	widths[COL_PERMIT_INFO] = 12;
	widths[COL_LOCATION] = 17;
	widths[COL_UTM_X] = 10;
	widths[COL_UTM_Y] = 10;
	widths[COL_LATDECDEG] =	10;
	widths[COL_LONDECDEG] =	10;	
	widths[COL_ELEV] = 7;
	widths[COL_DEPTH] = 6;
	widths[COL_LOG_TYPE] = 10;
	widths[COL_LOG_SWL] = 10;
	widths[COL_LOG_DEPTH] = 7;
	widths[COL_LOG_DATE] = 8;
	widths[COL_LOC_NUM] = 16;
	widths[COL_SITE_ID] = 11;
	widths[COL_DSS_AQUIFER1] = 	11;
	widths[COL_DSS_AQUIFER2] = 	11;
	widths[COL_DSS_AQUIFER_COMMENT] = 15;

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
		case COL_WELL_NAME:	return "%-20s";
		case COL_PERMIT_INFO:	return "%-20s";
		case COL_LOCATION:	return "%-20s";
		case COL_UTM_X:		return "%13.6f";
		case COL_UTM_Y:		return "%13.6f";
		case COL_LATDECDEG:	return "%13.6f";
		case COL_LONDECDEG:	return "%13.6f";		
		case COL_ELEV:		return "%10.1f";
		case COL_DEPTH:		return "%8d";
		case COL_LOG_TYPE:	return "%-20s";
		case COL_LOG_SWL:	return "%-20s";
		case COL_LOG_DEPTH:	return "%8d";
		case COL_LOG_DATE:	return "%-20s";
		case COL_LOC_NUM:	return "%-20s";
		case COL_SITE_ID:	return "%-20s";
		case COL_DSS_AQUIFER1:	return "%-20s";
		case COL_DSS_AQUIFER2:	return "%-20s";
		case COL_DSS_AQUIFER_COMMENT:	return "%-40s";		
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

	HydroBase_GroundWaterWellsGeophlogs g = _data.get(row);
	switch (col) {
		case COL_DIV:
			return Integer.valueOf(g.getDiv());
		case COL_WD:
			return Integer.valueOf(g.getWD());
		case COL_WELL_NAME:	
			return g.getWell_name();
		case COL_PERMIT_INFO:
			String s = "";
			int i = g.getPermitno();
			if (!DMIUtil.isMissing(i) && !HydroBase_Util.isMissing(i)) {
				s += i;
			}

			s += "-";

			String temp = g.getPermitsuf();
			if (!DMIUtil.isMissing(temp)) {
				s += temp;
			}
			
			s += "-";

			temp = g.getPermitrpl();
			if (!DMIUtil.isMissing(temp) ) {
				s += temp;
			}
			return s;
		case COL_LOCATION:
			return HydroBase_Util.buildLocation(g.getPM(),
				g.getTS(), g.getTdir(), g.getRng(), 
				g.getRdir(), g.getSec(), g.getSeca(), 
				g.getQ160(), g.getQ40(), g.getQ10());		
		case COL_UTM_X:
			return Double.valueOf(g.getUtm_x());
		case COL_UTM_Y:
			return Double.valueOf(g.getUtm_y());
		case COL_LATDECDEG:
			return Double.valueOf(g.getLatdecdeg());
		case COL_LONDECDEG:
			return Double.valueOf(g.getLongdecdeg());			
		case COL_ELEV:
			return Double.valueOf(g.getElev());
		case COL_DEPTH:
			return Integer.valueOf(g.getWell_depth());
		case COL_LOG_TYPE:
			return g.getLog_type();
		case COL_LOG_SWL:
			return Integer.valueOf(g.getLog_swl());
		case COL_LOG_DEPTH:
			return Integer.valueOf(g.getLog_depth());
		case COL_LOG_DATE:
			if (g.getLog_date() == null) {
				return null;
			}
			DateTime dt = new DateTime(g.getLog_date());
			return dt.toString(DateTime.FORMAT_YYYY_MM_DD);
		case COL_LOC_NUM:
			return g.getLoc_num();
		case COL_SITE_ID:
			return g.getSite_id();
	
		case COL_DSS_AQUIFER1:	return g.getDSS_aquifer1();
		case COL_DSS_AQUIFER2:	return g.getDSS_aquifer2();
		case COL_DSS_AQUIFER_COMMENT:		
			return g.getDSS_aquifer_comment();
	
		default:		
			return "";
	}
}

}