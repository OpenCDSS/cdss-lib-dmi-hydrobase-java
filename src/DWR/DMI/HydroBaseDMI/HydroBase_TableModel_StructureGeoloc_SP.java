// HydroBase_TableModel_StructureGeoloc_SP - class that handles the table model for the GUI for
// the times that StructureGeoloc is queried when using SPFlex stored procedures.

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
This class is a table model for displaying structure geoloc data in the 
HydroBase_GUI_Structure GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_StructureGeoloc_SP 
extends HydroBase_TableModel<HydroBase_StructureView> {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 25;

/**
Used to refer internally to the columns.
*/
public final static int 
	COL_DIV =			0,
	COL_WD = 			1,
	COL_ID = 			2,
	COL_NAME = 			3,
	COL_LOCATION =			4,
	COL_SOURCE = 			5,
	COL_STRMILE = 			6,
	COL_OWNER = 			7,
	COL_TYPE = 			8,
	COL_CIU = 			9,
	COL_TRANSBSN =			10,
	COL_DCR_RATE_ABS = 		11,
	COL_DCR_RATE_COND = 		12,
	COL_DCR_RATE_APEX_ABS = 	13,
	COL_DCR_RATE_APEX_COND = 	14,
	COL_DCR_RATE_TOTAL = 		15,
	COL_DCR_VOL_ABS = 		16,
	COL_DCR_VOL_COND = 		17,
	COL_DCR_VOL_APEX_ABS = 		18,
	COL_DCR_VOL_APEX_COND = 	19,
	COL_DCR_VOL_TOTAL = 		20,
	COL_UTMX = 			21,
	COL_UTMY = 			22,
	COL_LONGITUDE =			23,
	COL_LATITUDE = 			24;

/**
A reference to an open dmi object (for use in pulling out some lookup table
information).
*/
private HydroBaseDMI __dmi = null;

/**
Constructor.  This builds the Model for displaying the given structure geoloc results.
@param results the results that will be displayed in the table.
@param dmi a reference to the dmi object used to query for the results.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_StructureGeoloc_SP(List<HydroBase_StructureView> results, HydroBaseDMI dmi)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results list passed to " 
			+ "HydroBase_TableModel_StructureGeoloc_SP "
			+ "constructor.");
	}
	if (dmi == null || (!dmi.isOpen())) {
		throw new Exception ("Invalid dmi (null or unopened) passed to"
			+ "HydroBase_TableModel_StructureGeoloc_SP "
			+ "constructor.");
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
		case COL_DIV:			return Integer.class;
		case COL_WD:			return Integer.class;
		case COL_ID:			return Integer.class;
		case COL_NAME:			return String.class;
		case COL_LOCATION:		return String.class;
		case COL_SOURCE:		return String.class;
		case COL_STRMILE:		return Double.class;
		case COL_OWNER:			return String.class;
		case COL_TYPE:			return String.class;
		case COL_CIU:			return String.class;
		case COL_TRANSBSN:		return Integer.class;
		case COL_DCR_RATE_ABS:		return Double.class;
		case COL_DCR_RATE_COND:		return Double.class;
		case COL_DCR_RATE_APEX_ABS:	return Double.class;
		case COL_DCR_RATE_APEX_COND:	return Double.class;
		case COL_DCR_RATE_TOTAL:	return Double.class;
		case COL_DCR_VOL_ABS:		return Double.class;
		case COL_DCR_VOL_COND:		return Double.class;
		case COL_DCR_VOL_APEX_ABS:	return Double.class;
		case COL_DCR_VOL_APEX_COND:	return Double.class;
		case COL_DCR_VOL_TOTAL:		return Double.class;
		case COL_UTMX:			return Double.class;
		case COL_UTMY:			return Double.class;
		case COL_LONGITUDE:		return Double.class;
		case COL_LATITUDE:		return Double.class;
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
		case COL_DIV:			return "\n\nDIV";
		case COL_WD:			return "\n\nWD";
		case COL_ID:			return "\n\nID";
		case COL_NAME:			return "\n\nSTRUCTURE NAME";
		case COL_LOCATION:		return "\n\nLOCATION";
		case COL_SOURCE:		return "\n\nWATER SOURCE";
		case COL_STRMILE:		return "\nSTREAM\nMILE";
		case COL_OWNER:			return "\n\nOWNER";
		case COL_TYPE:			return "\nSTRUCTURE\nTYPE";
		case COL_CIU:			return "\nCURRENTLY\nIN USE";
		case COL_TRANSBSN:		return "\nTRANS-\nBASIN";
		case COL_DCR_RATE_ABS:	return "DECREED\nRATE\nABS (CFS)";
		case COL_DCR_RATE_COND:	return "DECREED\nRATE\nCOND (CFS)";
		case COL_DCR_RATE_APEX_ABS:    return "DECREED\nRATE APEX\n" +
							"ABS (CFS)";
		case COL_DCR_RATE_APEX_COND:  return "DECREED\nRATE APEX\n" +
							"COND (CFS)";
		case COL_DCR_RATE_TOTAL:	return "DECREED\nRATE\n" +
							"TOTAL (CFS)";
		case COL_DCR_VOL_ABS:	return "DECREED\nVOL\nABS (ACFT)";
		case COL_DCR_VOL_COND:	return "DECREED\nVOL\nCOND (ACFT)";
		case COL_DCR_VOL_APEX_ABS: return "DECREED\nVOL APEX\n" +
						"ABS (ACFT)";
		case COL_DCR_VOL_APEX_COND:    return "DECREED\nVOL APEX\n" +
						"COND (ACFT)";
		case COL_DCR_VOL_TOTAL:	return "DECREED\nVOL\nTOTAL (ACFT)";
		case COL_UTMX:			return "\n\nUTM X";
		case COL_UTMY:			return "\n\nUTM Y";
		case COL_LONGITUDE:	      return "\nLONGITUDE\n(DEC. DEG.)";
		case COL_LATITUDE:	       return "\nLATITUDE\n(DEC. DEG.)";
		default:			return " ";
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
		"<html>The water district in which the structure exists, for" +
		" administrative purposes.<BR>" +
		"The WD and ID (WDID) together are unique.</html>";
	tips[COL_ID] = 
		"<html>The structure identifier within the water district." +
		"<BR>The WD and ID (WDID) together are unique.</html>";
	tips[COL_NAME] = "Structure name.";
	tips[COL_LOCATION] = "Structure legal location.";
	tips[COL_SOURCE] = "Water source for the structure.";
	tips[COL_STRMILE] =
		"Stream mile (from the state line) at which the structure " +
		"is located.";
	tips[COL_OWNER] = "Owner of the structure.";
	tips[COL_TYPE] = "Structure type.";
	tips[COL_CIU] =
		"<HTML>Indicates if the structure is currently in use (CIU):"+
		"<BR>  A = Active structure with contemporary diversion " +
			"records."+
		"<BR>  B = Structure abandoned by the court."+
		"<BR>  C = Conditional structure."+
		"<BR>  D = Duplicate, ID is no longer used."+
		"<BR>  F = Structure used as FROM number - located in another "+
			"district." +
		"<BR>  H = Historical structure only - no longer exists or has "
			+ "records, but has historical data." +
		"<BR>  I = Inactive structures which physically exist but no " +
			"diversion records are kept." +
		"<BR>  N = Non-existent structure with no contemporary or "+
			"historical records." +
		"<BR>  U = Active structures but diversion records are not " +
			"maintained.</HTML>";
	tips[COL_TRANSBSN] = "Indicates whether a structure is a transbasin " +
			"structure (1) or not (0).";
	tips[COL_DCR_RATE_ABS] =
			"Sum of net amount absolute flow rights (CFS).";
	tips[COL_DCR_RATE_COND] =
			"Sum of net amount conditional flow rights (CFS).";
	tips[COL_DCR_RATE_APEX_ABS] =
			"Sum of alternate point net amount absolute flow " +
			"rights (CFS).";
	tips[COL_DCR_RATE_APEX_COND] =
			"Sum of alternate point net amount conditional flow "+
			"rights (CFS).";
	tips[COL_DCR_RATE_TOTAL] =
		"<HTML>Total of all net amount flow rights (CFS).</HTML>";
	tips[COL_DCR_VOL_ABS] =
			"Sum of net amount absolute storage rights (ACFT).";
	tips[COL_DCR_VOL_COND] =
			"Sum of net amount conditional storage rights (ACFT).";
	tips[COL_DCR_VOL_APEX_ABS] =
			"Sum of alternate point net amount absolute storage " +
			"rights (ACFT).";
	tips[COL_DCR_VOL_APEX_COND] =
			"Sum of alternate point net amount conditional storage"+
			" rights (ACFT).";
	tips[COL_DCR_VOL_TOTAL] =
		"<HTML>Total of all net amount storage rights (ACFT).</HTML>";
	tips[COL_UTMX] = "UTM X coordinate.";
	tips[COL_UTMY] = "UTM Y coordinate.";
	tips[COL_LONGITUDE] = "Longitude, decimal degrees.";
	tips[COL_LATITUDE] = "Latitude, decimal degrees.";
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

	widths[COL_DIV] =			2;
	widths[COL_WD] = 			2;
	widths[COL_ID]= 			6;
	widths[COL_NAME] = 			20;
	widths[COL_LOCATION] =			16;
	widths[COL_SOURCE] = 			15;
	widths[COL_STRMILE] = 			6;
	widths[COL_OWNER] = 			12;
	widths[COL_TYPE] = 			9;
	widths[COL_CIU] = 			8;
	widths[COL_TRANSBSN] =			6;
	widths[COL_DCR_RATE_ABS] = 		7;
	widths[COL_DCR_RATE_COND] = 		7;
	widths[COL_DCR_RATE_APEX_ABS] = 	7;
	widths[COL_DCR_RATE_APEX_COND] = 	8;
	widths[COL_DCR_RATE_TOTAL] = 		8;
	widths[COL_DCR_VOL_ABS] = 		7;
	widths[COL_DCR_VOL_COND] = 		8;
	widths[COL_DCR_VOL_APEX_ABS] = 		7;
	widths[COL_DCR_VOL_APEX_COND] = 	8;
	widths[COL_DCR_VOL_TOTAL] = 		9;
	widths[COL_UTMX] = 			10;
	widths[COL_UTMY] = 			10;
	widths[COL_LONGITUDE] = 		8;
	widths[COL_LATITUDE] =			8;
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
		case COL_DIV:			return "%8d";
		case COL_WD:			return "%8d";
		case COL_ID:			return "%8d";
		case COL_NAME:			return "%-40s";
		case COL_LOCATION:		return "%24.24s";
		case COL_SOURCE:		return "%-40s";
		case COL_STRMILE:		return "%7.2f";
		case COL_OWNER:			return "%-40s";
		case COL_TYPE:			return "%-40s";
		case COL_CIU:			return "%-1s";
		case COL_TRANSBSN:		return "%8d";
		case COL_DCR_RATE_ABS:		return "%7.4f";
		case COL_DCR_RATE_COND:		return "%7.4f";
		case COL_DCR_RATE_APEX_ABS:	return "%7.4f";
		case COL_DCR_RATE_APEX_COND:	return "%7.4f";
		case COL_DCR_RATE_TOTAL:	return "%7.4f";
		case COL_DCR_VOL_ABS:		return "%7.4f";
		case COL_DCR_VOL_COND:		return "%7.4f";
		case COL_DCR_VOL_APEX_ABS:	return "%7.4f";
		case COL_DCR_VOL_APEX_COND:	return "%7.4f";
		case COL_DCR_VOL_TOTAL:		return "%7.4f";
		case COL_UTMX:			return "%13.6f";
		case COL_UTMY:			return "%13.6f";
		case COL_LONGITUDE:		return "%11.6f";
		case COL_LATITUDE:		return "%11.6f";
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

	HydroBase_StructureView sv = _data.get(row);
	switch (col) {
		case COL_DIV:
			return Integer.valueOf(sv.getDiv());
		case COL_WD:		
			return Integer.valueOf(sv.getWD());
		case COL_ID:		
			return Integer.valueOf(sv.getID());
		case COL_NAME:		
			return sv.getStr_name();
		case COL_LOCATION:
			return HydroBase_Util.buildLocation(
				sv.getPM(), sv.getTS(), sv.getTdir(),
				sv.getRng(), sv.getRdir(), sv.getSec(), 
				sv.getSeca(), sv.getQ160(), sv.getQ40(),
				sv.getQ10());
		case COL_SOURCE:	
			return sv.getStrname();
		case COL_STRMILE:	
			return Double.valueOf(sv.getStr_mile());
		case COL_OWNER:		
			return sv.getFull_name();
		case COL_TYPE:		
			return __dmi.getStructureTypeDescription(
				sv.getStr_type());
		case COL_CIU:		
			return sv.getCiu();
		case COL_TRANSBSN:	
			return Integer.valueOf(sv.getTransbsn());
		case COL_DCR_RATE_ABS:
			return Double.valueOf(sv.getDcr_rate_abs());
		case COL_DCR_RATE_COND:
			return Double.valueOf(sv.getDcr_rate_cond());
		case COL_DCR_RATE_APEX_ABS:
			return Double.valueOf(sv.getDcr_rate_APEX_abs());
		case COL_DCR_RATE_APEX_COND:
			return Double.valueOf(sv.getDcr_rate_APEX_cond());
		case COL_DCR_RATE_TOTAL:
			return Double.valueOf(sv.getDcr_rate_total());
		case COL_DCR_VOL_ABS:	
			return Double.valueOf(sv.getDcr_vol_abs());
		case COL_DCR_VOL_COND:
			return Double.valueOf(sv.getDcr_vol_cond());
		case COL_DCR_VOL_APEX_ABS:
			return Double.valueOf(sv.getDcr_vol_APEX_abs());
		case COL_DCR_VOL_APEX_COND:
			return Double.valueOf(sv.getDcr_vol_APEX_cond());
		case COL_DCR_VOL_TOTAL:
			return Double.valueOf(sv.getDcr_vol_total());
		case COL_UTMX:		
			return Double.valueOf(sv.getUtm_x());
		case COL_UTMY:		
			return Double.valueOf(sv.getUtm_y());
		case COL_LONGITUDE:	
			return Double.valueOf(sv.getLongdecdeg());
		case COL_LATITUDE:	
			return Double.valueOf(sv.getLatdecdeg());
		default:		return " ";
	}
}

}