package DWR.DMI.HydroBaseDMI;

import java.util.List;

/**
This class is a table model for displaying net amounts data in the HydroBase_GUI_OtherQuery GUI.
*/
public class HydroBase_TableModel_Wells extends HydroBase_TableModel
{

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 7;

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
	COL_PERCENT_YIELD = 6;

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
public Class getColumnClass (int columnIndex) {
	switch (columnIndex) {
		case COL_YEAR: return Integer.class;
		case COL_DIV: return Integer.class;
		case COL_PARCEL_ID: return Integer.class;
		case COL_CLASS: return Integer.class;
		case COL_DISTANCE: return Double.class;
		case COL_PRORATED_YIELD: return Double.class;
		case COL_PERCENT_YIELD: return Double.class;
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
		case COL_DISTANCE: return "PARCEL\nTO\nWELL\nDIST (M)";
		case COL_PRORATED_YIELD: return "PRORATED\nWELL\nYIELD\n(GPM)";
		case COL_PERCENT_YIELD: return "\nPERCENT\nWELL\nYIELD";
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
	tips[COL_DISTANCE] = "Distance from parcel centroid to well (M).";
	tips[COL_PRORATED_YIELD] = "Well capacity prorated to parcel supply (GPM).";
	tips[COL_PERCENT_YIELD] = "Well capacity prorated to parcel supply (%).";

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
	switch (col) {
		case COL_YEAR: return new Integer(well.getCal_year());
		case COL_DIV: return new Integer(well.getDiv());
		case COL_PARCEL_ID: return new Integer(well.getParcel_id());
		case COL_CLASS: return new Integer(well.get_Class());
		case COL_DISTANCE: return new Double(well.getDistance());
		case COL_PRORATED_YIELD: return new Double(well.getProrated_yield());
		case COL_PERCENT_YIELD: return new Double(100.0*well.getPercent_yield());

		default: return "";
	}
}

}