// HydroBase_TableModel_GroundWaterWellsWellMeas - Table Model for a Vector of HydroBase_GroundWaterWellsView well meas objects

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
// HydroBase_TableModel_GroundWaterWellsWellMeas - Table Model for a Vector of 
//	HydroBase_GroundWaterWellsView well meas objects
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2003-06-23	J. Thomas Sapienza, RTi	Initial version.
// 2005-07-05	Steven A. Malers, RTi	Review header and tooltips.
// 2005-07-06	JTS, RTi		* Added wd column.
//					* Added data_source column.
// 2005-11-15	JTS, RTi		* Added div column.
// 					* Added DSS_aquifer1, DSS_aquifer2,
//					  DSS_aquifer_comment columns.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;

import RTi.DMI.DMIUtil;

/**
This class is a table model for displaying ground water data in the HydroBase_GUI_GroundWater GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_GroundWaterWellsWellMeas 
extends HydroBase_TableModel<HydroBase_GroundWaterWellsView> {

/**
Number of columns in the table model.
*/
private int __COLUMNS = 19;

/**
Used to refer internally to columns.
*/
public final static int 
	COL_DIV =		0,
	COL_WD =		1,
	COL_WELL_NAME =		2,
	COL_LOC_NUM =		3,
	COL_SITE_ID =		4,
	COL_PERMIT_INFO =	5,
	COL_LOCATION =		6,
	COL_ELEV =		7,
	COL_DEPTH =		8,
	COL_DATA_SOURCE = 	9,
	COL_START = 		10,
	COL_END = 		11,
	COL_UTM_X =		12,
	COL_UTM_Y =		13,
	COL_LATDECDEG = 	14,
	COL_LONDECDEG = 	15,
	COL_DSS_AQUIFER1 = 	16,
	COL_DSS_AQUIFER2 = 	17,
	COL_DSS_AQUIFER_COMMENT = 18;	

/**
List of aquifers to display in the tooltips.
*/
private List<HydroBase_Aquifer> __aquifers = null;

/**
Constructor.  This builds the Model for displaying the given ground water results.
@param results the results that will be displayed in the table.
@param dmi a reference to the dmi object used to query for the results.  Cannot be null.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_GroundWaterWellsWellMeas(List<HydroBase_GroundWaterWellsView> results, List<HydroBase_Aquifer> aquifers)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_GroundWaterWellsWellMeas constructor.");
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
		case COL_LOC_NUM:	return String.class;
		case COL_SITE_ID:	return String.class;
		case COL_DATA_SOURCE:	return String.class;
		case COL_START:		return Integer.class;
		case COL_END:		return Integer.class;
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
		case COL_DIV:		return "\n\nDIV";
		case COL_WD:		return "\n\nWD";
		case COL_WELL_NAME:	return "\n\nWELL NAME";
		case COL_PERMIT_INFO:	return "\n\nPERMIT INFO";
		case COL_LOCATION:	return "\n\nLOCATION";
		case COL_UTM_X:		return "\n\nUTM X";
		case COL_UTM_Y:		return "\n\nUTM Y";
		case COL_LATDECDEG:	return "\nLATITUDE\n(DEC. DEG.)";	
		case COL_LONDECDEG:	return "\nLONGITUDE\n(DEC. DEG.)";
		case COL_ELEV:		return "WELL\nELEVATION\n(FT)";
		case COL_DEPTH:		return "WELL\nDEPTH\n(FT)";
		case COL_LOC_NUM:	return "\n\nLOC NUM";
		case COL_SITE_ID:	return "\n\nSITE ID";		
		case COL_DATA_SOURCE:	return "\nTIME SERIES\nDATA SOURCE";
		case COL_START:		return "\n\nSTART";
		case COL_END:		return "\n\nEND";
		case COL_DSS_AQUIFER1:	return "\nDSS\nAQUIFER 1";
		case COL_DSS_AQUIFER2:	return "\nDSS\nAQUIFER 2";
		case COL_DSS_AQUIFER_COMMENT:	return "\nDSS AQUIFER\nCOMMENT";
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
		"Well elevation, FT.";
	tips[COL_DEPTH] =
		"Well depth, FT.";
	tips[COL_DATA_SOURCE] = 
		"Data source for well measurement time series.";
	tips[COL_START] =
		"Time series starting year.";
	tips[COL_END] = 
		"Time series ending year.";
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

	widths[COL_DIV] = 		2;
	widths[COL_WD] = 		2;
	widths[COL_WELL_NAME] = 	19;
	widths[COL_PERMIT_INFO] = 	12;
	widths[COL_LOCATION] = 		17;
	widths[COL_UTM_X] = 		10;
	widths[COL_UTM_Y] = 		10;
	widths[COL_LATDECDEG] =		10;
	widths[COL_LONDECDEG] =		10;	
	widths[COL_ELEV] = 		7;
	widths[COL_DEPTH] =		6;
	widths[COL_LOC_NUM] = 		16;
	widths[COL_SITE_ID] = 		11;	
	widths[COL_DATA_SOURCE] = 	10;
	widths[COL_START] = 		4;
	widths[COL_END] = 		4;
	widths[COL_DSS_AQUIFER1] = 	11;
	widths[COL_DSS_AQUIFER2] = 	11;
	widths[COL_DSS_AQUIFER_COMMENT] = 15;	

	return widths;
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
		case COL_LOC_NUM:	return "%-20s";
		case COL_SITE_ID:	return "%-20s";		
		case COL_DATA_SOURCE:	return "%-20s";
		case COL_START:		return "%8d";
		case COL_END:		return "%8d";
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

	HydroBase_GroundWaterWellsView g = _data.get(row);
	switch (col) {
		case COL_DIV:	
			return new Integer(g.getDiv());
		case COL_WD:
			return new Integer(g.getWD());
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
			if (!DMIUtil.isMissing(temp)) {
				s += temp;
			}
			return s;
		case COL_LOCATION:
			return HydroBase_Util.buildLocation(g.getPM(),
				g.getTS(), g.getTdir(), g.getRng(), 
				g.getRdir(), g.getSec(), g.getSeca(), 
				g.getQ160(), g.getQ40(), g.getQ10());
		case COL_UTM_X:
			return new Double(g.getUtm_x());
		case COL_UTM_Y:
			return new Double(g.getUtm_y());
		case COL_LATDECDEG:
			return new Double(g.getLatdecdeg());
		case COL_LONDECDEG:
			return new Double(g.getLongdecdeg());			
		case COL_ELEV:
			return new Double(g.getElev());
		case COL_DEPTH:
			return new Integer(g.getWell_depth());
		case COL_LOC_NUM:
			return g.getLoc_num();
		case COL_SITE_ID:
			return g.getSite_id();		
		case COL_DATA_SOURCE:
			return g.getData_source();
		case COL_START:						
			return new Integer(g.getStart_year());
		case COL_END:		
			return new Integer(g.getEnd_year());
		case COL_DSS_AQUIFER1:	return g.getDSS_aquifer1();
		case COL_DSS_AQUIFER2:	return g.getDSS_aquifer2();
		case COL_DSS_AQUIFER_COMMENT:		
			return g.getDSS_aquifer_comment();			
		default:		
			return "";
	}
}

}
