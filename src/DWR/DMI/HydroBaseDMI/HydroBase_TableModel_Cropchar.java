// ----------------------------------------------------------------------------
// HydroBase_TableModel_Cropchar - Table Model for a Vector of 
//	HydroBase_Cropchar objects.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-05-29	J. Thomas Sapienza, RTi	Initial version.
// 2004-01-20	JTS, RTi		Removed 0th column in order to use the 
//					new JWorksheet column header system.
// 2004-02-10	JTS, RTi		Removed the GIS Irrig Crop Num field.
// 2005-04-29	JTS, RTi		Added finalize().
// 2005-06-28	JTS, RTi		Removed the unused DMI parameter.
// 2005-07-06	Steven A. Malers, RTi	Review headings.  Add tool tips.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;

/**
This class is a table model for displaying crop char data.
*/
public class HydroBase_TableModel_Cropchar 
extends HydroBase_TableModel {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 14;

/**
References to the columns.
*/
public static final int 
	COL_CROP_NAME =		0,
	COL_METHOD =		1,
	COL_TEMP_EARLY_MOIST =	2,
	COL_TEMP_LATE_MOIST =	3,
	COL_INIT_ROOT =		4,
	COL_MAX_ROOT =		5,
	COL_MADLEVEL =		6,
	COL_MAXAPPDEP =		7,
	COL_DAYS_TO_COVER =	8,
	COL_PLANTING_DATE =	9,
	COL_HARVEST_DATE =	10,
	COL_DAYS_TO_CUT =	11,
	COL_DAYS_BETWEEN_CUTS = 12,	 
	COL_SEASON_LENGTH =	13;

/**
Constructor.  This builds the Model for displaying the given crop char results.
@param results the results that will be displayed in the table.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_Cropchar(List results)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_Cropchar constructor.");
	}
	_rows = results.size();
	_data = results;
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	super.finalize();
}

/**
Returns the class of the data stored in a given column.
@param columnIndex the column for which to return the data class.
*/
public Class getColumnClass (int columnIndex) {
	switch (columnIndex) {
		case COL_CROP_NAME:		return String.class;
		case COL_METHOD:		return String.class;
		case COL_TEMP_EARLY_MOIST:	return Double.class;
		case COL_TEMP_LATE_MOIST:	return Double.class;
		case COL_INIT_ROOT:		return Double.class;
		case COL_MAX_ROOT:		return Double.class;
		case COL_MADLEVEL:		return Double.class;
		case COL_MAXAPPDEP:		return Double.class;
		case COL_DAYS_TO_COVER:		return Integer.class;
		case COL_PLANTING_DATE:		return String.class;
		case COL_HARVEST_DATE:		return String.class;
		case COL_DAYS_TO_CUT:		return Integer.class;
		case COL_DAYS_BETWEEN_CUTS:	return Integer.class;
		case COL_SEASON_LENGTH:		return Integer.class;
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
		case COL_CROP_NAME:		return "\n\n\nCROP TYPE";
		case COL_METHOD:		return "\n\n\nMETHOD";
		case COL_TEMP_EARLY_MOIST:	
			return "TEMP\nEARLY\nMOIST\n(DEG F)";
		case COL_TEMP_LATE_MOIST:	
			return "TEMP\nLATE\nMOIST\n(DEG F)";
		case COL_INIT_ROOT:		return "\nINIT\nROOT\n(IN)";
		case COL_MAX_ROOT:		return "\nMAX\nROOT\n(IN)";
		case COL_MADLEVEL:		return "\nMAX\nSOIL\nDEF. (%)";
		case COL_MAXAPPDEP:		return "MAX\nAPP\nDEP\n(IN)";
		case COL_DAYS_TO_COVER:		return "DAYS\nTO\nFULL\nCOVER";
		case COL_PLANTING_DATE: 	return "PLANTING\nMON/DAY";
		case COL_HARVEST_DATE:		return "HARVEST\nMON/DAY";
		case COL_DAYS_TO_CUT:		return "DAYS\nTO\n1ST\nCUT";
		case COL_DAYS_BETWEEN_CUTS:	return "\nDAYS\nBETW\nCUTS";
		case COL_SEASON_LENGTH:	 return "LENGTH\nOF\nSEASON\n(DAYS)";
		default:	return " ";
	}
}

/**
Returns the text to be assigned to worksheet tooltips.
@return a String array of tool tips.
*/
public String[] getColumnToolTips() {
	String[] tips = new String[__COLUMNS];

	tips[COL_CROP_NAME] = 
		"Crop name.";
	tips[COL_METHOD] =
		"Indicates source of crop characteristic data.";
	tips[COL_TEMP_EARLY_MOIST] =
		"Temperature early moisture (DEG F).";
	tips[COL_TEMP_LATE_MOIST] =
		"Temperature late moisture (DEG F).";
	tips[COL_INIT_ROOT] =
		"Initial root zone depth (IN).";
	tips[COL_MAX_ROOT] =
		"Maximum root zone depth (IN).";
	tips[COL_MADLEVEL] = 
		"Management allowable deficit level (%).";
	tips[COL_MAXAPPDEP] = 
		"Maximum application depth (IN).";
	tips[COL_DAYS_TO_COVER] =
		"Days to full cover.";
	tips[COL_PLANTING_DATE] = 
		"Planting date.";
	tips[COL_HARVEST_DATE] = 
		"Harvest date.";
	tips[COL_DAYS_TO_CUT] = 
		"Days to first cut (alfalfa).";
	tips[COL_DAYS_BETWEEN_CUTS] = 
		"Days between first and second cut (alfalfa).";
	tips[COL_SEASON_LENGTH] =
		"Length of season, days.";

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
	widths[COL_CROP_NAME] = 	15;
	widths[COL_METHOD] = 		19;
	widths[COL_TEMP_EARLY_MOIST] = 	6;
	widths[COL_TEMP_LATE_MOIST] = 	6;
	widths[COL_INIT_ROOT] = 	5;
	widths[COL_MAX_ROOT] = 		5;
	widths[COL_MADLEVEL] = 		5;
	widths[COL_MAXAPPDEP] = 	5;
	widths[COL_DAYS_TO_COVER] = 	5;
	widths[COL_PLANTING_DATE] = 	7;
	widths[COL_HARVEST_DATE] = 	7;
	widths[COL_DAYS_TO_CUT] = 	5;
	widths[COL_DAYS_BETWEEN_CUTS] =	5;
	widths[COL_SEASON_LENGTH] = 	5;
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
		case COL_CROP_NAME:		return "%-21s";
		case COL_METHOD:		return "%-40s";
		case COL_TEMP_EARLY_MOIST:	return "%8.2f";
		case COL_TEMP_LATE_MOIST:	return "%8.2f";
		case COL_INIT_ROOT:		return "%8.2f";
		case COL_MAX_ROOT:		return "%8.2f";
		case COL_MADLEVEL:		return "%8.2f";
		case COL_MAXAPPDEP:		return "%8.2f";
		case COL_DAYS_TO_COVER:		return "%8d";
		case COL_PLANTING_DATE: 	return "%-5s";
		case COL_HARVEST_DATE:		return "%-5s";
		case COL_DAYS_TO_CUT:		return "%8d";
		case COL_DAYS_BETWEEN_CUTS:	return "%8d";
		case COL_SEASON_LENGTH:		return "%8d";
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
Returns the data that should be placed in the JTable
at the given row and column.
@param row the row for which to return data.
@param col the column for which to return data.
@return the data that should be placed in the JTable at the given row and col.
*/
public Object getValueAt(int row, int col) {
	if (_sortOrder != null) {
		row = _sortOrder[row];
	}

	HydroBase_Cropchar c = (HydroBase_Cropchar)_data.get(row);
	switch (col) {
		case COL_CROP_NAME:		
			return c.getCropname();
		case COL_METHOD:		
			return c.getMethod_desc();
		case COL_TEMP_EARLY_MOIST:	
			return new Double(c.getTempearlymoisture());
		case COL_TEMP_LATE_MOIST:	
			return new Double(c.getTemplatemoisture());
		case COL_INIT_ROOT:		
			return new Double(c.getInitialroot());
		case COL_MAX_ROOT:		
			return new Double(c.getMaxroot());
		case COL_MADLEVEL:		
			return new Double(c.getMadlevel());
		case COL_MAXAPPDEP:		
			return new Double(c.getMaxappdepth());
		case COL_DAYS_TO_COVER:		
			return new Integer(c.getDaystofullcover());
		case COL_PLANTING_DATE:		
			return "" + c.getPlantingmon() + "/"
				+ c.getPlantingday();
		case COL_HARVEST_DATE:	
			return "" + c.getHarvestmon() + "/" + c.getHarvestday();
		case COL_DAYS_TO_CUT:	
			return new Integer(c.getDaystofirstcut());
		case COL_DAYS_BETWEEN_CUTS:	
			return new Integer(c.getDaysbetweencuts());
		case COL_SEASON_LENGTH:	
			return new Integer(c.getLengthofseason());
		default:	return "";
	}
}

}
