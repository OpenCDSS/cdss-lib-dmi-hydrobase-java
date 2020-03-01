// HydroBase_TableModel_GroundWaterWellsVolcanics - Table Model for a Vector of HydroBase_GroundWaterWellsView objects

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
// HydroBase_TableModel_GroundWaterWellsVolcanics - Table Model for a Vector 
//	of HydroBase_GroundWaterWellsView objects for the 20050701 pump test
//	data.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2005-06-22	J. Thomas Sapienza, RTi	Initial version.
// 2005-07-05	Steven A. Malers, RTi	* Review header, tool tips.
//					* Remove well_num.
// 2005-07-06	JTS, RTi		Add wd column.
// 2005-11-15	JTS, RTi		* Added div column.
// 					* Added DSS_aquifer1, DSS_aquifer2,
//					  DSS_aquifer_comment columns.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;

import RTi.DMI.DMIUtil;

/**
This class is a table model for displaying volcanics data in the 
HydroBase_GUI_GroundWater GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_GroundWaterWellsVolcanics 
extends HydroBase_TableModel<HydroBase_GroundWaterWellsVolcanics> {

/**
Reference to the columns.
*/
public final static int
	COL_DIV 	= 0,
	COL_WD 		= 1,
	COL_WELL_NAME 	= 2,
	COL_LOC_NUM 	= 3,
	COL_SITE_ID 	= 4,
	COL_PERMIT_INFO = 5,
	COL_LOCATION 	= 6,
	COL_VOLTOP 	= 7,
	COL_VOLBASE 	= 8,
	COL_UTM_X 	= 9,
	COL_UTM_Y 	= 10,
	COL_LATDECDEG	= 11,
	COL_LONDECDEG	= 12,
	COL_DSS_AQUIFER1 = 	13,
	COL_DSS_AQUIFER2 = 	14,
	COL_DSS_AQUIFER_COMMENT = 15;			
 
/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 16;

/**
List of aquifers to display in the tooltips.
*/
private List<HydroBase_Aquifer> __aquifers = null;

/**
Constructor.  This builds the Model for displaying the given pump test results.
@param results the results that will be displayed in the table.
@param dmi a reference to the dmi object used to query for the results.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_GroundWaterWellsVolcanics(List<HydroBase_GroundWaterWellsVolcanics> results, List<HydroBase_Aquifer> aquifers)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_GroundWaterWellsVolcanics constructor.");
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
		case COL_VOLTOP:	return Integer.class;
		case COL_VOLBASE:	return Integer.class;
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
		case COL_VOLTOP:	return "\nVOLTOP";
		case COL_VOLBASE:	return "\nVOLBASE";
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
	tips[COL_VOLTOP] =
		"Feet from ground level to the top of a volcanic or " +
		"volcaniclastic geologic unit.";
	tips[COL_VOLBASE] =
		"Feet from ground level to the base of a volcanic or " +
		"volcaniclastic geologic unit.";
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
	
	widths[COL_DIV] =		2;
	widths[COL_WD] = 		2;
	widths[COL_WELL_NAME] = 	19;
	widths[COL_PERMIT_INFO] = 	12;
	widths[COL_LOCATION] = 		17;
	widths[COL_UTM_X] = 		10;
	widths[COL_UTM_Y] = 		10;
	widths[COL_LATDECDEG] =		10;
	widths[COL_LONDECDEG] =		10;
	widths[COL_VOLTOP] = 		8;
	widths[COL_VOLBASE] = 		8;
	widths[COL_LOC_NUM] =	 	16;
	widths[COL_SITE_ID] = 		11;
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
		case COL_VOLTOP:	return "%8d";
		case COL_VOLBASE:	return "%8d";
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

	HydroBase_GroundWaterWellsVolcanics v = _data.get(row);
	switch (col) {
		case COL_DIV:
			return new Integer(v.getDiv());
		case COL_WD:
			return new Integer(v.getWD());
		case COL_WELL_NAME:	
			return v.getWell_name();
		case COL_PERMIT_INFO:
			String s = "";
			int i = v.getPermitno();
			if (!DMIUtil.isMissing(i) && !HydroBase_Util.isMissing(i)) {
				s += i;
			}

			s += "-";

			String temp = v.getPermitsuf();
			if (!DMIUtil.isMissing(temp)) {
				s += temp;
			}
			
			s += "-";

			temp = v.getPermitrpl();
			if (!DMIUtil.isMissing(temp)) {
				s += temp;
			}
			return s;
		case COL_LOCATION:
			return HydroBase_Util.buildLocation(v.getPM(),
				v.getTS(), v.getTdir(), v.getRng(), 
				v.getRdir(), v.getSec(), v.getSeca(), 
				v.getQ160(), v.getQ40(), v.getQ10());
		case COL_UTM_X:
			return new Double(v.getUtm_x());
		case COL_UTM_Y:
			return new Double(v.getUtm_y());
		case COL_LATDECDEG:
			return new Double(v.getLatdecdeg());
		case COL_LONDECDEG:
			return new Double(v.getLongdecdeg());	
		case COL_VOLTOP:	return new Integer(v.getVoltop());
		case COL_VOLBASE:	return new Integer(v.getVolbase());
		case COL_LOC_NUM: 	return v.getLoc_num();
		case COL_SITE_ID:	return v.getSite_id();		
		case COL_DSS_AQUIFER1:	return v.getDSS_aquifer1();
		case COL_DSS_AQUIFER2:	return v.getDSS_aquifer2();
		case COL_DSS_AQUIFER_COMMENT:		
			return v.getDSS_aquifer_comment();
					
		default:		return "";
	}
}

}
