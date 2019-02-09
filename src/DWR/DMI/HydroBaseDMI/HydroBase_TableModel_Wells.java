// HydroBase_TableModel_Wells - This class is a table model for displaying net amounts data in the HydroBase_GUI_OtherQuery GUI.

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

package DWR.DMI.HydroBaseDMI;

import java.util.Date;
import java.util.List;

import RTi.DMI.DMIUtil;
import RTi.Util.Time.DateTime;

/**
This class is a table model for displaying net amounts data in the HydroBase_GUI_OtherQuery GUI.
*/
public class HydroBase_TableModel_Wells extends HydroBase_TableModel
{

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 17;

/**
References to the columns.
*/
public final static int
	// General
	COL_YEAR = 0,
	COL_DIV = 1,
	// Parcel...
	COL_PARCEL_ID = 2,
	COL_CLASS = 3,
	COL_DISTANCE = 4,
	COL_PRORATED_YIELD = 5,
	COL_PERCENT_YIELD = 6,
	COL_WELL_WD = 7,
	COL_WELL_ID = 8,
	COL_WELL_RECEIPT = 9,
	COL_WELL_NAME = 10,
	COL_WELL_YIELD = 11,
	COL_WELL_YIELD_APEX = 12,
	COL_WELL_PERMIT_DATE = 13,
	COL_WELL_APPRO_DATE = 14,
	COL_WELL_DEPTH = 15,
	COL_DITCHES_SERVED = 16;

/**
Constructor.  This  shows the expanded structure information needed by the StateDMI's parcel water supply tool.
@param results the results that will be displayed in the table.
@throws Exception if an invalid results were was passed in.
*/
public HydroBase_TableModel_Wells(List results )
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results list passed to HydroBase_TableModel_Wells constructor.");
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
		case COL_YEAR: return Integer.class;
		case COL_DIV: return Integer.class;
		case COL_PARCEL_ID: return Integer.class;
		case COL_CLASS: return Integer.class;
		case COL_DISTANCE: return Double.class;
		case COL_PRORATED_YIELD: return Double.class;
		case COL_PERCENT_YIELD: return Double.class;
		case COL_WELL_WD: return Integer.class;
		case COL_WELL_ID: return Integer.class;
		case COL_WELL_RECEIPT: return String.class;
		case COL_WELL_NAME: return String.class;
		case COL_WELL_YIELD: return Double.class;
		case COL_WELL_YIELD_APEX: return Double.class;
		case COL_WELL_PERMIT_DATE: return String.class;
		case COL_WELL_APPRO_DATE: return String.class;
		case COL_WELL_DEPTH: return Double.class;
		case COL_DITCHES_SERVED: return Integer.class;
		default: return String.class;
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
		case COL_YEAR: return "\n\n\nYEAR";
		case COL_DIV: return "\n\n\nDIV";
		case COL_PARCEL_ID: return "\n\nPARCEL\nID";
		case COL_CLASS: return "\nWELL\nMATCH\nCLASS";
		case COL_DISTANCE: return "PARCEL\nTO\nWELL\nDIST (FT)";
		case COL_PRORATED_YIELD: return "PRORATED\nWELL\nYIELD\n(GPM)";
		case COL_PERCENT_YIELD: return "\nPERCENT\nWELL\nYIELD";
		case COL_WELL_WD: return "\n\nWATER\nDISTRICT";
		case COL_WELL_ID: return "\nWELL\nID\n(IF WDID)";
		case COL_WELL_RECEIPT: return "\nWELL\nRECEIPT\n(IF PERMIT)";
		case COL_WELL_NAME: return "\n\nWELL\nNAME";
		case COL_WELL_YIELD: return "\nWELL\nYIELD\n(GPM)";
		case COL_WELL_YIELD_APEX: return "WELL\nAPEX\nYIELD\n(GPM)";
		case COL_WELL_PERMIT_DATE: return "WELL\nPERMIT\nDATE\n(IF PERMIT)";
		case COL_WELL_APPRO_DATE: return "WELL\nAPPROP.\nDATE\n(IF WDID)";
		case COL_WELL_DEPTH: return "\nWELL\nDEPTH\n(FT)";
		case COL_DITCHES_SERVED: return "\nNUMBER\nDITCHES\nSERVED";
		default: return " ";
	}	
}

/**
Returns the text to be assigned to worksheet tooltips.
@return a String array of tool tips.
*/
public String[] getColumnToolTips()
{	String[] tips = new String[__COLUMNS];

	tips[COL_YEAR] = "Calendar year for well/parcel data.";
	tips[COL_DIV] = "Water division for well/parcel data.";
	tips[COL_PARCEL_ID] = "Parcel identifier (unique for year and division).";
	tips[COL_CLASS] = "Indicates well to parcel matching classification.";
	tips[COL_DISTANCE] = "Distance from parcel centroid to well (FT).";
	tips[COL_PRORATED_YIELD] = "Well capacity prorated to parcel supply (GPM).";
	tips[COL_PERCENT_YIELD] = "Well capacity prorated to parcel supply (%).";
	tips[COL_WELL_WD] = "Well water district if well is a structure with a WDID.";
	tips[COL_WELL_ID] = "Well ID if well is a structure with a WDID.";
	tips[COL_WELL_RECEIPT] = "Well receipt number if well is a permit.";
	tips[COL_WELL_NAME] = "Well name.";
	tips[COL_WELL_YIELD] = "Well yield (GPM).";
	tips[COL_WELL_YIELD_APEX] = "Well alternate point/exchange (GPM).";
	tips[COL_WELL_PERMIT_DATE] = "Well permit date if well is a permit.";
	tips[COL_WELL_APPRO_DATE] = "Well appropriation date if well is a structure with a WDID.";
	tips[COL_WELL_DEPTH] = "Well depth (FT).";
	tips[COL_DITCHES_SERVED] = "Well number of ditches served.";

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
	widths[COL_CLASS] = 5;
	widths[COL_DISTANCE] = 7;
	widths[COL_PRORATED_YIELD] = 8;
	widths[COL_PERCENT_YIELD] = 8;
	widths[COL_WELL_WD] = 6;
	widths[COL_WELL_ID] = 6;
	widths[COL_WELL_RECEIPT] = 6;
	widths[COL_WELL_NAME] = 12;
	widths[COL_WELL_YIELD] = 6;
	widths[COL_WELL_YIELD_APEX] = 6;
	widths[COL_WELL_PERMIT_DATE] = 6;
	widths[COL_WELL_APPRO_DATE] = 6;
	widths[COL_WELL_DEPTH] = 6;
	widths[COL_DITCHES_SERVED] = 6;
	
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
		case COL_YEAR: return "%8d";
		case COL_DIV: return "%8d";
		case COL_PARCEL_ID: return "%8d";
		case COL_CLASS: return "%2d";
		case COL_DISTANCE: return "%10.0f";
		case COL_PRORATED_YIELD: return "%8.2f";
		case COL_PERCENT_YIELD: return "%8.2f";
		case COL_WELL_WD: return "%2d";
		case COL_WELL_ID: return "%8d";
		case COL_WELL_RECEIPT: return "%-12.12s";
		case COL_WELL_NAME: return "%-s";
		case COL_WELL_YIELD: return "%8.2f";
		case COL_WELL_YIELD_APEX: return "%8.2f";
		case COL_WELL_PERMIT_DATE: return "%s";
		case COL_WELL_APPRO_DATE: return "%s";
		case COL_WELL_DEPTH: return "%8.2f";
		case COL_DITCHES_SERVED: return "%3d";
		default: return "%-8s";
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

	HydroBase_Wells well = (HydroBase_Wells)_data.get(row);
	Date date = null;
	switch (col) {
		case COL_YEAR: return new Integer(well.getCal_year());
		case COL_DIV: return new Integer(well.getDiv());
		case COL_PARCEL_ID: return new Integer(well.getParcel_id());
		case COL_CLASS: return new Integer(well.get_Class());
		case COL_DISTANCE: return new Double(well.getDistance());
		case COL_PRORATED_YIELD: return new Double(well.getProrated_yield());
		case COL_PERCENT_YIELD: return new Double(100.0*well.getPercent_yield());
		case COL_WELL_WD: return new Integer(well.getWD());
		case COL_WELL_ID: return new Integer(well.getID());
		case COL_WELL_RECEIPT: return new String(well.getReceipt());
		case COL_WELL_NAME: return new String(well.getWell_name());
		case COL_WELL_YIELD: return new Double(well.getYield());
		case COL_WELL_YIELD_APEX: return new Double(well.getYield_apex());
		case COL_WELL_PERMIT_DATE:
			date = well.getPerm_date();
			if ( date == null ) {
				return DMIUtil.MISSING_DATE;
			}
			else {
				return "" + new DateTime(date).toString();
			}
		case COL_WELL_APPRO_DATE:
			date = well.getAppr_date();
			if ( date == null ) {
				return DMIUtil.MISSING_DATE;
			}
			else {
				return "" + new DateTime(date).toString();
			}
		case COL_WELL_DEPTH: return new Double(well.getDepth());
		case COL_DITCHES_SERVED: return new Integer(well.getDitches_served());
		default: return "";
	}
}

}
