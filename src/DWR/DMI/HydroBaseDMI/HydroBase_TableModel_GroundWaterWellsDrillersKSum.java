// HydroBase_TableModel_GroundWaterWellsDrillersKSum - Table Model for a Vector of HydroBase_GroundWaterWellsView objects

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
// HydroBase_TableModel_GroundWaterWellsDrillersKSum - Table Model for a Vector 
//	of HydroBase_GroundWaterWellsView objects for the 20050701 pump test
//	data.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2005-06-29	J. Thomas Sapienza, RTi	Initial version.
// 2005-07-05	Steven A. Malers, RTi	* Cleanup headers and tool tips, pending
//					  more information from the State.
//					* Remove well number.
//					* Add "dunctopbasek".
// 2005-07-06	JTS, RTi		Add wd column.
// 2005-11-15	JTS, RTi		* Added div column.
// 					* Added DSS_aquifer1, DSS_aquifer2,
//					  DSS_aquifer_comment columns.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;

import RTi.DMI.DMIUtil;

/**
This class is a table model for displaying drillers k sum data in the HydroBase_GUI_GroundWater GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_GroundWaterWellsDrillersKSum 
extends HydroBase_TableModel<HydroBase_GroundWaterWellsDrillersKSum> {

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
	COL_DUNCTOP	= 7,
	COL_DUNCBASE	= 8,
	COL_DUNCTOPBASEK= 9,
	COL_DCONTOP	= 10,
	COL_D500	= 11,
	COL_DCON500K	= 12,
	COL__2D500	= 13,
	COL_D1000	= 14,
	COL_D500TO1000K	= 15,
	COL__2D1000	= 16,
	COL__1500	= 17,
	COL_D1000_1500K	= 18,
	COL__2D1500	= 19,
	COL_D2000	= 20,
	COL_D1500_2000K	= 21,
	COL__2D2000	= 22,
	COL_D2500	= 23,
	COL_D2000_2500	= 24,
	COL__2D2500	= 25,
	COL_D3000	= 26,
	COL_D2500_3000	= 27,
	COL__2D3000	= 28,
	COL_D3500	= 29,
	COL_D3000_3500K	= 30,
	COL__2D3500	= 31,
	COL_D4000	= 32,
	COL_D3500_4000K	= 33,
	COL_UTM_X 	= 34,
	COL_UTM_Y 	= 35,
	COL_LATDECDEG	= 36,
	COL_LONDECDEG	= 37,
	COL_DSS_AQUIFER1 = 38,
	COL_DSS_AQUIFER2 = 39,
	COL_DSS_AQUIFER_COMMENT = 40;

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 41;

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
public HydroBase_TableModel_GroundWaterWellsDrillersKSum(List<HydroBase_GroundWaterWellsDrillersKSum> results, List<HydroBase_Aquifer> aquifers)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_GroundWaterWellsDrillersKSum "
			+ "constructor.");
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
		case COL_DUNCTOP:	return Integer.class;
		case COL_DUNCBASE:	return Integer.class;
		case COL_DUNCTOPBASEK:	return Integer.class;
		case COL_DCONTOP:	return Integer.class;
		case COL_D500:		return Integer.class;
		case COL_DCON500K:	return Integer.class;
		case COL__2D500:	return Integer.class;
		case COL_D1000:		return Integer.class;
		case COL_D500TO1000K:	return Integer.class;
		case COL__2D1000:	return Integer.class;
		case COL__1500:		return Integer.class;
		case COL_D1000_1500K:	return Integer.class;
		case COL__2D1500:	return Integer.class;
		case COL_D2000:		return Integer.class;
		case COL_D1500_2000K:	return Integer.class;
		case COL__2D2000:	return Integer.class;
		case COL_D2500:		return Integer.class;
		case COL_D2000_2500:	return Integer.class;
		case COL__2D2500:	return Integer.class;
		case COL_D3000:		return Integer.class;
		case COL_D2500_3000:	return Integer.class;
		case COL__2D3000:	return Integer.class;
		case COL_D3500:		return Integer.class;
		case COL_D3000_3500K:	return Integer.class;
		case COL__2D3500:	return Integer.class;
		case COL_D4000:		return Integer.class;
		case COL_D3500_4000K:	return Integer.class;
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
		case COL_DUNCTOP:	return "\nDUNCTOP";
		case COL_DUNCBASE:	return "\nDUNCBASE";
		case COL_DUNCTOPBASEK:	return "\nDUNCTOPBASEK";
		case COL_DCONTOP:	return "\nDCONTOP";
		case COL_D500:		return "\nD500";
		case COL_DCON500K:	return "\nDCON500K";
		case COL__2D500:	return "\n_2D500";
		case COL_D1000:		return "\nD1000";
		case COL_D500TO1000K:	return "D500\nTO 1000K";
		case COL__2D1000:	return "\n_2D1000";
		case COL__1500:		return "\n_1500";
		case COL_D1000_1500K:	return "\nD1000_1500K";
		case COL__2D1500:	return "\n_2D1500";
		case COL_D2000:		return "\nD2000";
		case COL_D1500_2000K:	return "\nD1500_2000K";
		case COL__2D2000:	return "\n_2D2000";
		case COL_D2500:		return "\nD2500";
		case COL_D2000_2500:	return "\nD2000_2500";
		case COL__2D2500:	return "\n_2D2500";
		case COL_D3000:		return "\nD3000";
		case COL__2D3000:	return "\n_2D3000";
		case COL_D2500_3000:	return "\nD2500_3000";
		case COL_D3500:		return "\nD3500";
		case COL_D3000_3500K:	return "\nD3000_3500K";
		case COL__2D3500:	return "\n_2D3500";
		case COL_D4000:		return "\nD4000";
		case COL_D3500_4000K:	return "\nD3500_4000K";
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
		"Division.";
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
	tips[COL_DUNCTOP] = 
		"Equals zero-reference point for driller's log data.";
	tips[COL_DUNCBASE] = 
		"<HTML>Depth in feet to first impermeable geologic unit of " +
		"10 feet or greater.<BR>" +
		"In wells that do not penetrate such a unit, " +
		"this value equals the total depth of the well.</HTML>";
	tips[COL_DUNCTOPBASEK] = 
		"<HTML>Net saturated thickness in feet from the top of the " +
		"water table<BR>" +
		"to the base of the unconfined aquifer or to the total depth "+
		"of the well.</HTML>";
	tips[COL_DCONTOP] = 
		"<HTML>Depth in feet to the first impermeable geologic unit " +
		"of 10 feet or greater.<BR>" +
		"Only filled in for wells that penetrate a confining layer.<BR>"
		+ "Will be equal to DUNCBASE if well penetrates a confining "+
		"layer.</HTML>";
	tips[COL_D500] = 
		"<HTML>Lower reference point for interval from the top of the"+
		" confined aquifer to 500 feet.<BR>" +
		"Equal to well total depth if the well penetrates a " +
		"confining layer and well total depth less than 500.</HTML>";
	tips[COL_DCON500K] = 
		"<HTML>Net saturated thickness in feet from the top of the "+
		"confined aquifer to total depth (if less than 500 feet)<BR>" +
		"or 500 feet (if total depth greater than 500 feet).</HTML>";
	tips[COL__2D500] = 
		"Upper reference point for interval from 500-1000 feet.";
	tips[COL_D1000] = 
		"Lower reference point for interval from 500-1000 feet.";
	tips[COL_D500TO1000K] = 
		"<HTML>Net saturated thickness in feet from 500 feet to " +
		"total depth (if less than 1000 feet)<BR>" +
		"or 1000 feet (if total depth greater than 1000).</HTML>";
	tips[COL__2D1000] = 
		"Upper reference point for interval from 1000-1500 feet.";
	tips[COL__1500] = 
		"Lower reference point for interval from 1000-1500 feet.";
	tips[COL_D1000_1500K] = 
		"<HTML>Net saturated thickness in feet from 1000 feet to " +
		"total depth (if less than 1500)<BR>" +
		"or 1500 feet (if total depth greater than 1500).</HTML>";
	tips[COL__2D1500] = 
		"Upper reference point for interval from 1500-2000 feet.";
	tips[COL_D2000] = 
		"Lower reference point for interval from 1500-2000 feet.";
	tips[COL_D1500_2000K] = 
		"<HTML>Net saturated thickness in feet from 1500 feet to " +
		"total depth (if less than 2000)<BR>" +
		"or 2000 feet (if total depth greater than 2000).</HTML>";
	tips[COL__2D2000] = 
		"Upper reference point for interval from 2000-2500 feet.";
	tips[COL_D2500] = 
		"Lower reference point for interval from 2000-2500 feet.";
	tips[COL_D2000_2500] = 
		"<HTML>Net saturated thickness is feet from 2000 feet to " +
		"total depth (if less than 2500)<BR>" +
		"or 2500 feet (if total depth greater than 2500).</HTML>";
	tips[COL__2D2500] = 
		"Upper reference point for interval from 2500-3000 feet.";
	tips[COL_D3000] = 
		"Lower reference point for interval from 2500-3000 feet.";
	tips[COL_D2500_3000] = 
		"<HTML>Net saturated thickness in feet from 2500 feet to " +
		"total depth (if less than 3000)<BR>" +
		"or 3000 feet (if total depth greater than 3000).</HTML>";
	tips[COL__2D3000] = 
		"Upper reference point for interval from 3000-3500 feet.";
	tips[COL_D3500] = 
		"Lower reference point for interval from 3000-3500 feet.";
	tips[COL_D3000_3500K] = 
		"<HTML>Net saturated thickness in feet from 3000 feet to " +
		"total depth (if less than 3500)<BR>" +
		"or 3500 feet (if total depth greater than 3500).</HMTL>";
	tips[COL__2D3500] = 
		"Upper reference point for interval from 3500-4000 feet.";
	tips[COL_D4000] = 
		"Lower reference point for interval from 3500-4000 feet.";
	tips[COL_D3500_4000K] = 
		"<HTML>Net saturated thickness in feet from 3500 feet to " +
		"total depth (if less than 4000)<BR>" +
		"or 4000 feet (if total depth greater than 4000).";
	tips[COL_UTM_X] =
		"UTM X coordinate.";
	tips[COL_UTM_Y] =
		"UTM Y coordinate.";
	tips[COL_LATDECDEG] =
		"Latitude in decimal degrees.";
	tips[COL_LONDECDEG] =
		"Longitude in decimal degrees.";

	tips[COL_DSS_AQUIFER1] = 
		"<html>  Aquifer determined during decision support<br>"
		+ "  system implementation.<p>"
		+ HydroBase_GUI_Util.getAquiferListForToolTip(__aquifers)
		+ "</html>";
	tips[COL_DSS_AQUIFER2] = 
		"<html>  Aquifer determined during decision support<br>"
		+ "  system implementation.<p>"
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
	widths[COL_DUNCTOP] = 		8;
	widths[COL_DUNCBASE] =		8;
	widths[COL_DUNCTOPBASEK] =	11;
	widths[COL_DCONTOP] = 		8;
	widths[COL_D500] = 		8;
	widths[COL_DCON500K] = 		8;
	widths[COL__2D500] = 		8;
	widths[COL_D1000] = 		8;
	widths[COL_D500TO1000K] = 	8;
	widths[COL__2D1000] = 		8;
	widths[COL__1500] = 		8;
	widths[COL_D1000_1500K] = 	9;
	widths[COL__2D1500] = 		8;
	widths[COL_D2000] = 		8;
	widths[COL_D1500_2000K] = 	9;
	widths[COL__2D2000] = 		8;
	widths[COL_D2500] = 		8;
	widths[COL_D2000_2500] =	8;
	widths[COL__2D2500] = 		8;
	widths[COL_D3000] = 		8;
	widths[COL_D2500_3000] =	8;
	widths[COL__2D3000] = 		8;
	widths[COL_D3500] = 		8;
	widths[COL_D3000_3500K] =	9;
	widths[COL__2D3500] = 		8;
	widths[COL_D4000] = 		8;
	widths[COL_D3500_4000K] = 	9;
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
		case COL_DUNCTOP:	return "%8d";
		case COL_DUNCBASE:	return "%8d";
		case COL_DUNCTOPBASEK:	return "%8d";
		case COL_DCONTOP:	return "%8d";
		case COL_D500:		return "%8d";
		case COL_DCON500K:	return "%8d";
		case COL__2D500:	return "%8d";
		case COL_D1000:		return "%8d";
		case COL_D500TO1000K:	return "%8d";
		case COL__2D1000:	return "%8d";
		case COL__1500:		return "%8d";
		case COL_D1000_1500K:	return "%8d";
		case COL__2D1500:	return "%8d";
		case COL_D2000:		return "%8d";
		case COL_D1500_2000K:	return "%8d";
		case COL__2D2000:	return "%8d";
		case COL_D2500:		return "%8d";
		case COL_D2000_2500:	return "%8d";
		case COL__2D2500:	return "%8d";
		case COL_D3000:		return "%8d";
		case COL_D2500_3000:	return "%8d";
		case COL__2D3000:	return "%8d";
		case COL_D3500:		return "%8d";
		case COL_D3000_3500K:	return "%8d";
		case COL__2D3500:	return "%8d";
		case COL_D4000:		return "%8d";
		case COL_D3500_4000K:	return "%8d";
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

	HydroBase_GroundWaterWellsDrillersKSum d = _data.get(row);
	switch (col) {
		case COL_DIV:
			return new Integer(d.getDiv());
		case COL_WD:
			return new Integer(d.getWD());
		case COL_WELL_NAME:	
			return d.getWell_name();
		case COL_PERMIT_INFO:
			String s = "";
			int i = d.getPermitno();
			if (!DMIUtil.isMissing(i)) {
				s += i;
			}

			s += "-";

			String temp = d.getPermitsuf();
			if (!DMIUtil.isMissing(temp)) {
				s += temp;
			}
			
			s += "-";

			temp = d.getPermitrpl();
			if (!DMIUtil.isMissing(temp)) {
				s += temp;
			}
			return s;
		case COL_LOCATION:
			return HydroBase_Util.buildLocation(d.getPM(),
				d.getTS(), d.getTdir(), d.getRng(), 
				d.getRdir(), d.getSec(), d.getSeca(), 
				d.getQ160(), d.getQ40(), d.getQ10());
		case COL_UTM_X:
			return new Double(d.getUtm_x());
		case COL_UTM_Y:
			return new Double(d.getUtm_y());
		case COL_LATDECDEG:
			return new Double(d.getLatdecdeg());
		case COL_LONDECDEG:
			return new Double(d.getLongdecdeg());
		case COL_DUNCTOP:	return new Integer(d.getDunctop());
		case COL_DUNCBASE:	return new Integer(d.getDuncbase());
		case COL_DUNCTOPBASEK:	return new Integer(d.getDunctopbasek());
		case COL_DCONTOP:	return new Integer(d.getDcontop());
		case COL_D500:		return new Integer(d.getD500());
		case COL_DCON500K:	return new Integer(d.getDcon500k());
		case COL__2D500:	return new Integer(d.get_2d500());
		case COL_D1000:		return new Integer(d.getD1000());
		case COL_D500TO1000K:	return new Integer(d.getD500to1000k());
		case COL__2D1000:	return new Integer(d.get_2d1000());
		case COL__1500:		return new Integer(d.get_1500());
		case COL_D1000_1500K:	return new Integer(d.getD1000_1500k());
		case COL__2D1500:	return new Integer(d.get_2d1500());
		case COL_D2000:		return new Integer(d.getD2000());
		case COL_D1500_2000K:	return new Integer(d.getD1500_2000k());
		case COL__2D2000:	return new Integer(d.get_2d2000());
		case COL_D2500:		return new Integer(d.getD2500());
		case COL_D2000_2500:	return new Integer(d.getD2000_2500());
		case COL__2D2500:	return new Integer(d.get_2d2500());
		case COL_D3000:		return new Integer(d.getD3000());
		case COL_D2500_3000:	return new Integer(d.getD2500_3000());
		case COL__2D3000:	return new Integer(d.get_2d3000());
		case COL_D3500:		return new Integer(d.getD3500());
		case COL_D3000_3500K:	return new Integer(d.getD3000_3500k());
		case COL__2D3500:	return new Integer(d.get_2d3500());
		case COL_D4000:		return new Integer(d.getD4000());
		case COL_D3500_4000K:	return new Integer(d.getD3500_4000k());
		case COL_LOC_NUM: 	return d.getLoc_num();
		case COL_SITE_ID:	return d.getSite_id();
		case COL_DSS_AQUIFER1:	return d.getDSS_aquifer1();
		case COL_DSS_AQUIFER2:	return d.getDSS_aquifer2();
		case COL_DSS_AQUIFER_COMMENT:		
			return d.getDSS_aquifer_comment();
		default:		return "";
	}
}

}