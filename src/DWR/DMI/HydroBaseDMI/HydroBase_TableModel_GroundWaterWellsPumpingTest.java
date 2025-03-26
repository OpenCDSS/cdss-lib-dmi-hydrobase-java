// HydroBase_TableModel_GroundWaterWellsPumpingTest - Table Model for a Vector of HydroBase_GroundWaterWellsView objects

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
This class is a table model for displaying pumping test data in the HydroBase_GUI_GroundWater GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_GroundWaterWellsPumpingTest 
extends HydroBase_TableModel<HydroBase_GroundWaterWellsPumpingTest> {

/**
Reference to the columns.
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
	COL_PT_TYPE =		9,
	COL_PT_SOURCE =		10,
	COL_TEST_DATE =		11,
	COL_TESTTIME =		12,
	COL_TSWL =		13,
	COL_TFWL =		14,
	COL_TESTQ =		15,
	COL_TRANS =		16,
	COL_K =			17,
	COL_STORATIVITY =	18,
	COL_LEAKANCE =		19,
	COL_TOPTESTINT =	20,
	COL_BASETESTINT =	21,
	COL_DRAWDOWN =		22,
	COL_PTMON =		23,
	COL_PTOBS =		24,
	COL_PTMULTIPLE =	25,
	COL_UTM_X =		26,
	COL_UTM_Y =		27,
	COL_LATDECDEG = 	28,
	COL_LONDECDEG = 	29,
	COL_DSS_AQUIFER1 = 	30,
	COL_DSS_AQUIFER2 = 	31,
	COL_DSS_AQUIFER_COMMENT = 32;		
 
/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 33;

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
public HydroBase_TableModel_GroundWaterWellsPumpingTest(List<HydroBase_GroundWaterWellsPumpingTest> results, List<HydroBase_Aquifer> aquifers)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_GroundWaterWellsPumpingTest constructor.");
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
		case COL_LOC_NUM:	return String.class;
		case COL_SITE_ID:	return String.class;	
		case COL_PERMIT_INFO:	return String.class;
		case COL_LOCATION:	return String.class;
		case COL_ELEV:		return Double.class;
		case COL_DEPTH:		return Integer.class;
		case COL_PT_TYPE:	return String.class;
		case COL_PT_SOURCE:	return String.class;
		case COL_TEST_DATE:	return String.class;
		case COL_TESTTIME:	return Double.class;
		case COL_TSWL:		return Double.class;
		case COL_TFWL:		return Double.class;
		case COL_TESTQ:		return Double.class;
		case COL_TRANS:		return Integer.class;
		case COL_K:		return String.class;
		case COL_STORATIVITY:	return String.class;
		case COL_LEAKANCE:	return String.class;
		case COL_TOPTESTINT:	return Integer.class;
		case COL_BASETESTINT:	return Integer.class;
		case COL_DRAWDOWN:	return Double.class;
		case COL_PTMON:		return Integer.class;
		case COL_PTOBS:		return Integer.class;
		case COL_PTMULTIPLE:	return Integer.class;
		case COL_UTM_X:		return Double.class;
		case COL_UTM_Y:		return Double.class;
		case COL_LATDECDEG:	return Double.class;
		case COL_LONDECDEG:	return Double.class;
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
		case COL_LOC_NUM:	return "\n\nLOC NUM";
		case COL_SITE_ID:	return "\n\nSITE ID";	
		case COL_PERMIT_INFO:	return "\n\nPERMIT INFO";
		case COL_LOCATION:	return "\n\nLOCATION";
		case COL_ELEV:		return "WELL\nELEVATION\n(FT)";
		case COL_DEPTH:		return "WELL\nDEPTH\n(FT)";
		case COL_PT_TYPE:	return "\n\nTEST TYPE";
		case COL_PT_SOURCE:	return "\n\nDATA SOURCE";
		case COL_TEST_DATE:	return "\n\nTEST DATE";
		case COL_TESTTIME:	return "\nDURATION\n(HRS)";
		case COL_TSWL:		return "STATIC\nLEVEL\n(FT)";
		case COL_TFWL:		return "FINAL\nLEVEL\n(FT)";
		case COL_TESTQ:		return "AVERAGE\nFLOW\n(GPM)";
		case COL_TRANS:		return "\nTRANS.\n(GPD/FT)";
		case COL_K:		return "HYD.\nCOND.\n(FT/DAY)";
		case COL_STORATIVITY:	return "\n\nSTORATIVITY";
		case COL_LEAKANCE:	return "\n\nLEAKANCE";
		case COL_TOPTESTINT:	return "TOP\nINTERVAL\n(FT)";
		case COL_BASETESTINT:	return "BASE\nINTERVAL\n(FT)";
		case COL_DRAWDOWN:	return "WATER\nLEVEL\nCHANGE (FT)";
		case COL_PTMON:		return "INCLUDES\nMONITOR\nWELL";
		case COL_PTOBS:		return "INCLUDES\nOBSERVED\nWELL";
		case COL_PTMULTIPLE:	return "MULTIPLE\nTESTS\nAVAILABLE";
		case COL_UTM_X:		return "\n\nUTM X";
		case COL_UTM_Y:		return "\n\nUTM Y";
		case COL_LATDECDEG:	return "\nLATITUDE\n(DEC. DEG.)";	
		case COL_LONDECDEG:	return "\nLONGITUDE\n(DEC. DEG.)";
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
		"Legal location, feet.";
	tips[COL_ELEV] = 
		"Well elevation, feet.";
	tips[COL_DEPTH] =
		"Well depth.";
	tips[COL_PT_TYPE] = 
		"Pumping test type.";
	tips[COL_PT_SOURCE] = 
		"Test data source";
	tips[COL_TEST_DATE] = 
		"Date of test.";
	tips[COL_TESTTIME] = 
		"Time in hours that the test was conducted.";
	tips[COL_TSWL] =
		"<HTML>Pre-test static water level measured in feet from " +
		"ground level.<BR>Pressure head above ground level is given " +
		"as a negative value.</HTML>";
	tips[COL_TFWL] = 
		"<HTML>Post-test final water level measured in feet from " +
		"ground level.<BR>Pressure head above ground level is given " +
		"as a negative value.</HTML>";
	tips[COL_TESTQ] = 
		"Average testing discharge rate measured in gallons per " +
		"minute.";
	tips[COL_TRANS] = 
		"Estimated transmissivity in gallons per day per foot" +
		" (gpd/ft).";
	tips[COL_K] = 
		"Hydraulic conductivity measured in feet per day (ft/day).";
	tips[COL_STORATIVITY] = 
		"<HTML>Storativity (dimensionless - can only be calculated " +
		"from confined aquifer tests<BR>with one or more monitoring " +
		"wells).</HTML>";
	tips[COL_LEAKANCE] = 
		"Composite leakance between aquifer layers in units of 1/Day.";
	tips[COL_TOPTESTINT] = 
		"Top of tested interval (FT).";
	tips[COL_BASETESTINT] = 
		"Base of tested interval (FT).";
	tips[COL_DRAWDOWN] = 
		"<HTML>Change in feet between pretest water level and end of"+
		" test water level.<BR>Pressure head above ground level is " +
		"given as a negative number.</HTML>";
	tips[COL_PTMON] = 
		"Indicates observation point available for test.";
	tips[COL_PTOBS] = 
		"<HTML>Indicates if the pump test included observation well " +
		"data.<BR>Observation wells must be screened in the same " +
		"aquifer as the pumping well.</HTML>";
	tips[COL_PTMULTIPLE] = 
		"Flag indicating the presence of multiple pump tests " +
		"available for a well.";
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
	widths[COL_LOC_NUM] = 20;
	widths[COL_SITE_ID] = 11;	
	widths[COL_PERMIT_INFO] = 12;
	widths[COL_LOCATION] = 17;
	widths[COL_ELEV] = 7;
	widths[COL_DEPTH] = 6;
	widths[COL_PT_TYPE] = 		12;
	widths[COL_PT_SOURCE] = 	15;
	widths[COL_TEST_DATE] = 	8;
	widths[COL_TESTTIME] = 		7;
	widths[COL_TSWL] = 		5;
	widths[COL_TFWL] = 		4;
	widths[COL_TESTQ] = 		7;
	widths[COL_TRANS] = 		6;
	widths[COL_K] = 		6;
	widths[COL_STORATIVITY] =	9;
	widths[COL_LEAKANCE] = 		7;
	widths[COL_TOPTESTINT] = 	7;
	widths[COL_BASETESTINT] = 	7;
	widths[COL_DRAWDOWN] = 		8;
	widths[COL_PTMON] = 		7;
	widths[COL_PTOBS] = 		8;
	widths[COL_PTMULTIPLE] = 	10;
	widths[COL_UTM_X] = 10;
	widths[COL_UTM_Y] = 10;
	widths[COL_LATDECDEG] =	10;
	widths[COL_LONDECDEG] =	10;	
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
		case COL_LOC_NUM:	return "%-20s";
		case COL_SITE_ID:	return "%-20s";
		case COL_PERMIT_INFO:	return "%-20s";
		case COL_LOCATION:	return "%24.24s";
		case COL_ELEV:		return "%10.1f";
		case COL_DEPTH:		return "%8d";
		case COL_PT_TYPE:	return "%-25s";
		case COL_PT_SOURCE:	return "%-25s";
		case COL_TEST_DATE:	return "%-10s";
		case COL_TESTTIME:	return "%8.2f";
		case COL_TSWL:		return "%8.2f";
		case COL_TFWL:		return "%8.2f";
		case COL_TESTQ:		return "%8.1f";
		case COL_TRANS:		return "%8d";
		case COL_K:		return "%12.3f";
		case COL_STORATIVITY:	return "%12.6f";
		case COL_LEAKANCE:	return "%12.5f";
		case COL_TOPTESTINT:	return "%8d";
		case COL_BASETESTINT:	return "%8d";
		case COL_DRAWDOWN:	return "%8.2f";
		case COL_PTMON:		return "%8d";
		case COL_PTOBS:		return "%8d";
		case COL_PTMULTIPLE:	return "%8d";
		case COL_UTM_X:		return "%13.6f";
		case COL_UTM_Y:		return "%13.6f";
		case COL_LATDECDEG:	return "%13.6f";
		case COL_LONDECDEG:	return "%13.6f";		
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

	HydroBase_GroundWaterWellsPumpingTest p = _data.get(row);
	switch (col) {
		case COL_DIV:
			return Integer.valueOf(p.getDiv());
		case COL_WD:
			return Integer.valueOf(p.getWD());
		case COL_WELL_NAME:	
			return p.getWell_name();
		case COL_LOC_NUM:
			return p.getLoc_num();
		case COL_SITE_ID:
			return p.getSite_id();
		case COL_PERMIT_INFO:
			String s = "";
			int i = p.getPermitno();
			if (!DMIUtil.isMissing(i) && !HydroBase_Util.isMissing(i)) {
				s += i;
			}

			s += "-";

			String temp = p.getPermitsuf();
			if (!DMIUtil.isMissing(temp)) {
				s += temp;
			}
			
			s += "-";

			temp = p.getPermitrpl();
			if (!DMIUtil.isMissing(temp)) {
				s += temp;
			}
			return s;
		case COL_LOCATION:
			return HydroBase_Util.buildLocation(p.getPM(),
				p.getTS(), p.getTdir(), p.getRng(), 
				p.getRdir(), p.getSec(), p.getSeca(), 
				p.getQ160(), p.getQ40(), p.getQ10());		
		case COL_ELEV:
			return Double.valueOf(p.getElev());
		case COL_DEPTH:
			return Integer.valueOf(p.getWell_depth());
		case COL_PT_TYPE:	return p.getPttype();
		case COL_PT_SOURCE:	return p.getPtsource();
		case COL_TEST_DATE:	
			if (DMIUtil.isMissing(p.getTestdate())) {
				return "";
			}
			return (new DateTime(p.getTestdate())).toString(
				DateTime.FORMAT_YYYY_MM_DD);
		case COL_TESTTIME:	return Double.valueOf(p.getTesttime());
		case COL_TSWL:		return Double.valueOf(p.getTswl());
		case COL_TFWL:		return Double.valueOf(p.getTfwl());
		case COL_TESTQ:		return Double.valueOf(p.getTestq());
		case COL_TRANS:		return Integer.valueOf(p.getTrans());
		case COL_K:		return p.getK();
		case COL_STORATIVITY:	return p.getStorativity();
		case COL_LEAKANCE:	return p.getLeakance();
		case COL_TOPTESTINT:	return Integer.valueOf(p.getToptestint());
		case COL_BASETESTINT:	return Integer.valueOf(p.getBasetestint());
		case COL_DRAWDOWN:	return Double.valueOf(p.getDrawdown());
		case COL_PTMON:		return Integer.valueOf(p.getPtmon());
		case COL_PTOBS:		return Integer.valueOf(p.getPtobs());
		case COL_PTMULTIPLE:	return Integer.valueOf(p.getPtmultiple());
		case COL_UTM_X:
			return Double.valueOf(p.getUtm_x());
		case COL_UTM_Y:
			return Double.valueOf(p.getUtm_y());
		case COL_LATDECDEG:
			return Double.valueOf(p.getLatdecdeg());
		case COL_LONDECDEG:
			return Double.valueOf(p.getLongdecdeg());			

		case COL_DSS_AQUIFER1:	return p.getDSS_aquifer1();
		case COL_DSS_AQUIFER2:	return p.getDSS_aquifer2();
		case COL_DSS_AQUIFER_COMMENT:		
			return p.getDSS_aquifer_comment();
			
		default:		return "";
	}
}

}
