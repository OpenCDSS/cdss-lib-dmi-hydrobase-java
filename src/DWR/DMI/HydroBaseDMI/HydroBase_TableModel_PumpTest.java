// HydroBase_TableModel_PumpTest - Table Model for a Vector of HydroBase_PumpTest objects

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
// HydroBase_TableModel_PumpTest - Table Model for a Vector of 
//	HydroBase_PumpTest objects
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2004-02-09	J. Thomas Sapienza, RTi	Initial version.
// 2005-04-06	JTS, RTi		Adjusted column names and sizes.
// 2005-04-29	JTS, RTI		Added finalize().
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;

import RTi.DMI.DMIUtil;

import RTi.Util.Time.DateTime;

/**
This class is a table model for displaying pump test data in the 
HydroBase_GUI_GroundWater GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_PumpTest 
extends HydroBase_TableModel<HydroBase_GroundWaterWellsPumpingTest> {

/**
Reference to the columns.
*/
public final static int
	COL_WD = 		0,
	COL_ID = 		1,
	COL_STR_NAME = 		2,
	COL_LOCATION = 		3,
	COL_TSWL =		4,
	COL_TFWL =		5,
	COL_TESTQ =		6,
	COL_TESTTIME =		7,
	COL_TRANS =		8,
	COL_K =			9,
	COL_STORATIVITY =	10,
	COL_LEAKANCE =		11,
	COL_TOPTESTINT =	12,
	COL_BASETESTINT =	13,
	COL_DRAWDOWN =		14,
	COL_TEST_DATE =		15,
	COL_PT_SOURCE =		16,
	COL_PT_TYPE =		17,
	COL_PTMON =		18,
	COL_PTOBS =		19,
	COL_PTMULTIPLE =	20;
 
/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 21;

/**
Constructor.  This builds the Model for displaying the given pump test results.
@param results the results that will be displayed in the table.
@throws Exception if invalid results were passed in.
*/
public HydroBase_TableModel_PumpTest(List<HydroBase_GroundWaterWellsPumpingTest> results)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_PumpTest constructor.");
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
		case COL_STR_NAME:	return String.class;
		case COL_LOCATION:	return String.class;
		case COL_TSWL:		return Double.class;
		case COL_TFWL:		return Double.class;
		case COL_TESTQ:		return Double.class;
		case COL_TESTTIME:	return Double.class;
		case COL_TRANS:		return Integer.class;
		case COL_K:		return String.class;
		case COL_STORATIVITY:	return String.class;
		case COL_LEAKANCE:	return String.class;
		case COL_TOPTESTINT:	return Integer.class;
		case COL_BASETESTINT:	return Integer.class;
		case COL_DRAWDOWN:	return Double.class;
		case COL_TEST_DATE:	return String.class;
		case COL_PT_SOURCE:	return String.class;
		case COL_PT_TYPE:	return String.class;
		case COL_PTMON:		return Integer.class;
		case COL_PTOBS:		return Integer.class;
		case COL_PTMULTIPLE:	return Integer.class;
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
		case COL_STR_NAME:	return "\n\nNAME";
		case COL_LOCATION:	return "\n\nLOCATION";
		case COL_TSWL:		return "STATIC\nLEVEL\n(FT)";
		case COL_TFWL:		return "FINAL\nLEVEL\n(FT)";
		case COL_TESTQ:		return "AVERAGE\nFLOW\n(GPM)";
		case COL_TESTTIME:	return "\nDURATION\n(HRS)";
		case COL_TRANS:		return "\nTRANS.\n(GPD/FT)";
		case COL_K:		return "HYD.\nCOND.\n(FT/D)";
		case COL_STORATIVITY:	return "\n\nSTORATIVITY";
		case COL_LEAKANCE:	return "\n\nLEAKANCE";
		case COL_TOPTESTINT:	return "TOP\nINTERVAL\n(FT)";
		case COL_BASETESTINT:	return "BASE\nINTERVAL\n(FT)";
		case COL_DRAWDOWN:	return "WATER\nLEVEL\nCHANGE";
		case COL_TEST_DATE:	return "\n\nTEST DATE";
		case COL_PT_SOURCE:	return "\n\nDATA SOURCE";
		case COL_PT_TYPE:	return "\n\nTEST TYPE";
		case COL_PTMON:		return "INCLUDES\nMONITOR\nWELL";
		case COL_PTOBS:		return "INCLUDES\nOBSERVED\nWELL";
		case COL_PTMULTIPLE:	return "MULTIPLE\nTESTS\nAVAILABLE";
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
		case COL_STR_NAME:	return "%-40s";
		case COL_LOCATION:	return "%24.24s";
		case COL_TSWL:		return "%8.2f";
		case COL_TFWL:		return "%8.2f";
		case COL_TESTQ:		return "%8.1f";
		case COL_TESTTIME:	return "%8.2f";
		case COL_TRANS:		return "%8d";
		case COL_K:		return "%12.3f";
		case COL_STORATIVITY:	return "%12.6f";
		case COL_LEAKANCE:	return "%12.5f";
		case COL_TOPTESTINT:	return "%8d";
		case COL_BASETESTINT:	return "%8d";
		case COL_DRAWDOWN:	return "%8.2f";
		case COL_TEST_DATE:	return "%-10s";
		case COL_PT_SOURCE:	return "%-25s";
		case COL_PT_TYPE:	return "%-25s";
		case COL_PTMON:		return "%8d";
		case COL_PTOBS:		return "%8d";
		case COL_PTMULTIPLE:	return "%8d";
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
		case COL_WD:		return new Integer(p.getWD());
		case COL_ID:		return new Integer(p.getID());
		case COL_STR_NAME:	return p.getStr_name();
		case COL_LOCATION:	
			return HydroBase_Util.buildLocation(
				p.getPM(), p.getTS(), p.getTdir(),
				p.getRng(), p.getRdir(), p.getSec(),
				p.getSeca(), p.getQ160(), p.getQ40(),
				p.getQ10());
		case COL_TSWL:		return new Double(p.getTswl());
		case COL_TFWL:		return new Double(p.getTfwl());
		case COL_TESTQ:		return new Double(p.getTestq());
		case COL_TESTTIME:	return new Double(p.getTesttime());
		case COL_TRANS:		return new Integer(p.getTrans());
		case COL_K:		return p.getK();
		case COL_STORATIVITY:	return p.getStorativity();
		case COL_LEAKANCE:	return p.getLeakance();
		case COL_TOPTESTINT:	return new Integer(p.getToptestint());
		case COL_BASETESTINT:	return new Integer(p.getBasetestint());
		case COL_DRAWDOWN:	return new Double(p.getDrawdown());
		case COL_TEST_DATE:	
			if (DMIUtil.isMissing(p.getTestdate())) {
				return "";
			}
			return (new DateTime(p.getTestdate())).toString(
				DateTime.FORMAT_YYYY_MM_DD);
		case COL_PT_SOURCE:	return p.getPtsource();
		case COL_PT_TYPE:	return p.getPttype();
		case COL_PTMON:		return new Integer(p.getPtmon());
		case COL_PTOBS:		return new Integer(p.getPtobs());
		case COL_PTMULTIPLE:	return new Integer(p.getPtmultiple());
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
	widths[COL_ID] = 		4;
	widths[COL_STR_NAME] = 		22;
	widths[COL_LOCATION] =		16;
	widths[COL_TSWL] = 		5;
	widths[COL_TFWL] = 		4;
	widths[COL_TESTQ] = 		7;
	widths[COL_TESTTIME] = 		7;
	widths[COL_TRANS] = 		6;
	widths[COL_K] = 		5;
	widths[COL_STORATIVITY] =	9;
	widths[COL_LEAKANCE] = 		7;
	widths[COL_TOPTESTINT] = 	7;
	widths[COL_BASETESTINT] = 	7;
	widths[COL_DRAWDOWN] = 		6;
	widths[COL_TEST_DATE] = 	8;
	widths[COL_PT_SOURCE] = 	10;
	widths[COL_PT_TYPE] = 		12;
	widths[COL_PTMON] = 		7;
	widths[COL_PTOBS] = 		8;
	widths[COL_PTMULTIPLE] = 	10;
	return widths;
}

}
