// HydroBase_TableModel_IrrigatedAcres - Table Model for a Vector of HydroBase_IrrigTimeSeries objects

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
// HydroBase_TableModel_IrrigatedAcres - Table Model for a Vector of 
//	HydroBase_IrrigTimeSeries objects (used in the
//	HydroBase_IrrigatedAcres GUI)
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-09-24	J. Thomas Sapienza, RTi	Initial version.
// 2004-01-20	JTS, RTi		Removed 0th column in order to use the 
//					new JWorksheet row header system.
// 2004-05-12	JTS, RTi		Revised the columns drastically due
//					to revision of the GUI.
// 2004-09-22	SAM, RTi		Change HydroBase_ParcelUseTS to
//					HydroBase_ParcelUseTSStructureToParcel.
// 2005-07-08	SAM, RTi	 	* Clarify some column headings.
//					* Add a column multiplying the parcel
//					  acres by the irrigation percent.
//					* Add a column with the total by
//					  structure for the year.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;

/**
This class is a table model for displaying net amounts data in the HydroBase_GUI_OtherQuery GUI.
*/
public class HydroBase_TableModel_IrrigatedAcres 
extends HydroBase_TableModel {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 10;

/**
References to the columns.
*/
public final static int
	COL_YEAR = 		0,
	COL_DIV = 		1,
	COL_PARCEL_ID =		2,
	COL_PERIMETER =		3,
	COL_AREA = 		4,
	COL_CROP_TYPE =		5,
	COL_IRRIG_TYPE =	6,
	COL_PERCENT_IRRIG =	7,
	COL_STRUCTURE_AREA =	8,
	COL_STRUCTURE_AREA_SUM=	9;

/**
Constructor.  
@param results the results that will be displayed in the table.
@throws Exception if an invalid results were was passed in.
*/
public HydroBase_TableModel_IrrigatedAcres(List results)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_IrrigatedAcres constructor.");
	}
	_rows = results.size();
	_data = results;
}

/**
From AbstractTableModel.  Returns the class of the data stored in a given column.
@param columnIndex the column for which to return the data class.
*/
public Class<?> getColumnClass (int columnIndex) {
	switch (columnIndex) {
		case COL_YEAR:			return Integer.class;
		case COL_DIV:			return Integer.class;
		case COL_PARCEL_ID:		return Integer.class;
		case COL_PERIMETER:		return Double.class;
		case COL_AREA:			return Double.class;
		case COL_CROP_TYPE:		return String.class;	
		case COL_IRRIG_TYPE:		return String.class;
		case COL_PERCENT_IRRIG:		return Double.class;
		case COL_STRUCTURE_AREA:	return Double.class;
		case COL_STRUCTURE_AREA_SUM:	return Double.class;
		default:			return String.class;
	}
}

/**
From AbstractTableModel.  Returns the number of columns of data.
@return the number of columns of data.
*/
public int getColumnCount() {
	return __COLUMNS;
}

/**
From AbstractTableModel.  Returns the name of the column at the given position.
@return the name of the column at the given position.
*/
public String getColumnName(int columnIndex) {
	switch (columnIndex) {
		case COL_YEAR:			return "\n\n\nYEAR";
		case COL_DIV:			return "\n\n\nDIV";	
		case COL_PARCEL_ID:		return "\n\nPARCEL\nID";
		case COL_PERIMETER:		return "\n\nPERIMETER\n(M)";
		case COL_AREA:			return "\nPARCEL\nAREA\n(ACRE)";
		case COL_CROP_TYPE:		return "\n\nCROP\nTYPE";
		case COL_IRRIG_TYPE:		return "\n\nIRRIGATION\nTYPE";
		case COL_PERCENT_IRRIG:
			return "PARCEL\nPERCENT\nIRRIGATED\nBY STRUCTURE";
		case COL_STRUCTURE_AREA:
			return "PARCEL AREA\nIRRIGATED\nBY STRUCTURE\n(ACRE)";
		case COL_STRUCTURE_AREA_SUM:
			return "PARCEL AREA\nIRRIGATED\nBY STRUCTURE,\n" +
				"SUM (ACRE)";
		default: return " ";
	}	
}

/**
Returns the text to be assigned to worksheet tooltips.
@return a String array of tool tips.
*/
public String[] getColumnToolTips()
{	String[] tips = new String[__COLUMNS];

	tips[COL_YEAR] = "Calendar year for crop/parcel data.";
	tips[COL_DIV] = "Water division for crop/parcel data.";
	tips[COL_PARCEL_ID] =
		"Parcel identifier (unique for year and division).";
	tips[COL_PERIMETER] = "Parcel perimiter (M).";
	tips[COL_AREA] = "Parcel area (ACRE).";
	tips[COL_CROP_TYPE] = "Crop grown on parcel.";
	tips[COL_IRRIG_TYPE] = "Irrigation method for parcel.";
	tips[COL_PERCENT_IRRIG] = 
		"Percent of parcel irrigated by the structure.";
	tips[COL_STRUCTURE_AREA] = "Parcel area irrigated by the structure.";
	tips[COL_STRUCTURE_AREA_SUM] = 
		"Total parcel area irrigated by the structure, for the "
			+ "year (same for all records in the year).";

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

	widths[COL_YEAR] = 4;
	widths[COL_DIV] = 3;
	widths[COL_PARCEL_ID] = 5;
	widths[COL_PERIMETER] = 8;
	widths[COL_AREA] = 7;
	widths[COL_CROP_TYPE] = 10;
	widths[COL_IRRIG_TYPE] = 7;
	widths[COL_PERCENT_IRRIG] = 10;
	widths[COL_STRUCTURE_AREA] = 10;
	widths[COL_STRUCTURE_AREA_SUM] = 10;
	
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
		case COL_YEAR:			return "%8d";
		case COL_DIV:			return "%8d";
		case COL_PARCEL_ID:		return "%8d";
		case COL_PERIMETER:		return "%10.3f";
		case COL_AREA:			return "%10.3f";
		case COL_CROP_TYPE:		return "%-20s";
		case COL_IRRIG_TYPE:		return "%-20s";
		case COL_PERCENT_IRRIG:		return "%5.3f";
		case COL_STRUCTURE_AREA:	return "%10.3f";
		case COL_STRUCTURE_AREA_SUM:	return "%10.3f";
		default:			return "%-8s";
	}
}

/**
From AbstractTableModel.  Returns the number of rows of data in the table.
*/
public int getRowCount() {
	return _rows;
}

/**
From AbstractTableModel.  Returns the data that should be placed in the JTable at the given row and column.
@param row the row for which to return data.
@param col the column for which to return data.
@return the data that should be placed in the JTable at the given row and col.
*/
public Object getValueAt(int row, int col) {
	if (_sortOrder != null) {
		row = _sortOrder[row];
	}

	HydroBase_ParcelUseTSStructureToParcel pts = (HydroBase_ParcelUseTSStructureToParcel)_data.get(row);
	switch (col) {
		case COL_YEAR:		return new Integer(pts.getCal_year());
		case COL_DIV:		return new Integer(pts.getDiv());
		case COL_PARCEL_ID:	return new Integer(pts.getParcel_id());
		case COL_PERIMETER:	return new Double(pts.getPerimeter());
		case COL_AREA:		return new Double(pts.getArea());
		case COL_CROP_TYPE:	return pts.getLand_use();
		case COL_IRRIG_TYPE:	return pts.getIrrig_type();
		case COL_PERCENT_IRRIG:
			return new Double(pts.getPercent_irrig() * 100);
		case COL_STRUCTURE_AREA:
			return new Double(pts.getArea()*pts.getPercent_irrig());
		case COL_STRUCTURE_AREA_SUM:
			// For the current instance's year, add all records to
			// get a total.  The sum will be the same for all
			// records.
			int year = pts.getCal_year();
			HydroBase_ParcelUseTSStructureToParcel pts2 = null;
			double area, sum = 0.0;
			for ( int i = 0; i < _rows; i++ ) {
				pts2 = (HydroBase_ParcelUseTSStructureToParcel)_data.get(i);
				if ( pts2.getCal_year() != year ) {
					continue;
				}
				area = pts2.getArea()*pts2.getPercent_irrig();
				sum += area;
			}
			return new Double ( sum );
		default: return "";
	}
}

}
