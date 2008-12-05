// ----------------------------------------------------------------------------
// HydroBase_TableModel_AgriculturalCASSCropStats - Table Model for a Vector of 
//	HydroBase_AgriculturalCASSCropStats objects.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2005-02-10	J. Thomas Sapienza, RTi	Initial version.
// 2005-04-29	JTS, RTi		Added finalize().
// 2005-06-28	JTS, RTi		Removed the unused DMI parameter.
// 2005-07-06	Steven A. Malers, RTi	Change precision on acres to .0.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;

/**
This class is a table model for displaying Colorado agstats data.
*/
public class HydroBase_TableModel_AgriculturalCASSCropStats 
extends HydroBase_TableModel {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 13;

/**
References to the columns.
*/
public final static int 
	COL_ST = 		0,
	COL_COUNTY = 		1,
	COL_COMMODITY =		2,
	COL_PRACTICE = 		3,
	COL_CAL_YEAR = 		4,
	COL_PLANTED = 		5,
	COL_PLTDHARV = 		6,
	COL_HARVESTED = 	7,
	COL_PLTDYLD = 		8,
	COL_YIELD = 		9,
	COL_YIELDUNIT = 	10,
	COL_PRODUCTION = 	11,
	COL_PRODUCTIONUNIT = 	12;

/**
Constructor.  This builds the Model for displaying the given agstats results.
@param results the results that will be displayed in the table.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_AgriculturalCASSCropStats(List results)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_AgriculturalCASSCropStats "
			+ "constructor.");
	}
	_rows = results.size();
	_data = results;
}

/**
Cleans up data members.
*/
public void finalize()
throws Throwable {
	super.finalize();
}

/**
From AbstractTableModel.  Returns the class of the data stored in a given column.
@param columnIndex the column for which to return the data class.
*/
public Class getColumnClass (int columnIndex) {
	switch (columnIndex) {
		case COL_ST:			return String.class;
		case COL_COUNTY:		return String.class;
		case COL_COMMODITY:		return String.class;
		case COL_PRACTICE:		return String.class;
		case COL_CAL_YEAR:		return Integer.class;
		case COL_PLANTED:		return Double.class;
		case COL_PLTDHARV:		return Double.class;
		case COL_HARVESTED:		return Double.class;
		case COL_PLTDYLD:		return Double.class;
		case COL_YIELD:			return Double.class;
		case COL_YIELDUNIT:		return String.class;
		case COL_PRODUCTION:		return Double.class;
		case COL_PRODUCTIONUNIT:	return String.class;
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
		case COL_ST:		return "\n\nSTATE";
		case COL_COUNTY:	return "\n\nCOUNTY";
		case COL_COMMODITY:	return "\n\nCOMMODITY";
		case COL_PRACTICE:	return "\n\nPRACTICE";
		case COL_CAL_YEAR:	return "\nCALENDAR\nYEAR";
		case COL_PLANTED:	return "\nPLANTED\n(ACRE)";
		case COL_PLTDHARV:	return "PLANTED\nHARVESTED\n(ACRE)";
		case COL_HARVESTED:	return "\nHARVESTED\n(ACRE)";
		case COL_PLTDYLD:	return "\nPLANTED\nYIELD";
		case COL_YIELD:		return "\n\nYIELD";
		case COL_YIELDUNIT:	return "\nYIELD\nUNITS";
		case COL_PRODUCTION:	return "\n\nPRODUCTION";
		case COL_PRODUCTIONUNIT:return "\nPRODUCTION\nUNIT";
		default:		return " ";
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

	widths[COL_ST] = 4;
	widths[COL_COUNTY] = 8;
	widths[COL_COMMODITY] = 15;
	widths[COL_PRACTICE] = 25;
	widths[COL_CAL_YEAR] = 7;
	widths[COL_PLANTED] = 7;
	widths[COL_PLTDHARV] = 8;
	widths[COL_HARVESTED] = 8;
	widths[COL_PLTDYLD] = 6;
	widths[COL_YIELD] = 5;
	widths[COL_YIELDUNIT] = 5;
	widths[COL_PRODUCTION] = 9;
	widths[COL_PRODUCTIONUNIT] = 9;

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
		case COL_ST:		return "%-40s";
		case COL_COUNTY:	return "%-40s";
		case COL_COMMODITY:	return "%-40s";
		case COL_PRACTICE:	return "%-40s";
		case COL_CAL_YEAR:	return "%8d";
		case COL_PLANTED:	return "%10.0f";
		case COL_PLTDHARV:	return "%10.0f";
		case COL_HARVESTED:	return "%10.0f";
		case COL_PLTDYLD:	return "%10.3f";
		case COL_YIELD:		return "%10.3f";
		case COL_YIELDUNIT:	return "%-40s";
		case COL_PRODUCTION:	return "%10.0f";
		case COL_PRODUCTIONUNIT:return "%-40s";
		default:		return "%-8s";
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

	HydroBase_AgriculturalCASSCropStats c = (HydroBase_AgriculturalCASSCropStats)_data.get(row);
	switch (col) {
		case COL_ST:		return c.getST();
		case COL_COUNTY:	return c.getCounty();
		case COL_COMMODITY:	return c.getCommodity();
		case COL_PRACTICE:	return c.getPractice();
		case COL_CAL_YEAR:	return new Integer(c.getCal_year());
		case COL_PLANTED:	return new Double(c.getPlanted());
		case COL_PLTDHARV:	return new Double(c.getPltdHarv());
		case COL_HARVESTED:	return new Double(c.getHarvested());
		case COL_PLTDYLD:	return new Double(c.getPltdYld());
		case COL_YIELD:		return new Double(c.getYield());
		case COL_YIELDUNIT:	return c.getYieldUnit();
		case COL_PRODUCTION:	return new Double(c.getProduction());
		case COL_PRODUCTIONUNIT:return c.getProductionUnit();
		default:		return "";
	}
}

}