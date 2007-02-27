// ----------------------------------------------------------------------------
// HydroBase_TableModel_NetAmts - Table Model for a Vector of 
//	HydroBase_NetAmts objects.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-03-05	J. Thomas Sapienza, RTi	Initial version.
// 2003-03-07	JTS, RTi		Added initial sorting code.
// 2003-03-10	JTS, RTi		Added table column width code.
// 2003-03-11	JTS, RTi		Added code for column 'HALF SEC'
// 2003-03-20	JTS, RTi		Revised after SAM's review.
// 2003-05-13	JTS, RTi		Row numbers (the 0th column) now are
//					not affected by column sorting.
// 2004-01-19	JTS, RTi		Dates are now returned as Strings.
// 2004-01-20	JTS, RTi		Removed 0th column in favor of the new
//					JWorksheet column header system.
// 2004-06-01	JTS, RTi		Converted the location to be a 
//					single string.
// 2005-04-06	JTS, RTi		Adjusted column names and sizes.
// 2005-04-29	JTS, RTi		Added finalize().
// 2005-07-06	Steven A. Malers, RTi	Review headers and add tooltips.
// 2005-07-11	JTS, RTi		Added the strtype field.
// 2005-11-15	JTS, RTi		Added div column.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------
// EndHeader

package DWR.DMI.HydroBaseDMI;

import java.util.Date;
import java.util.Vector;

import RTi.Util.Time.DateTime;

/**
This class is a table model for displaying net amounts data in the 
HydroBase_GUI_WaterRights GUI.
*/
public class HydroBase_TableModel_NetAmts 
extends HydroBase_TableModel {

/**
Used to refer to the Legal table display format.
*/
public static final int LEGAL = 0;
/**
Used to refer to the summary table display format.
*/
public static final int SUMMARY = 1;
/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 22;

/**
A reference to an open dmi object (for use in pulling out some lookup table
information).
*/
private HydroBaseDMI __dmi = null;

/**
Constructor.  This builds the Model for displaying the given Net Amounts
results.
@param results the results that will be displayed in the table.
@param dmi a reference to the dmi object used to query for the results.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_NetAmts(Vector results, HydroBaseDMI dmi, int type)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_NetAmts constructor.");
	}
	if (dmi == null || (dmi.isOpen() == false)) {
		throw new Exception ("Invalid dmi (null or unopened) passed to"
			+ "HydroBase_TableModel_NetAmts constructor.");
	}
	__dmi = dmi;
	_rows = results.size();
	_data = results;
	_type = type;
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	__dmi = null;
	super.finalize();
}

/**
Returns the class of the data stored in a given column.
@param columnIndex the column for which to return the data class.
*/
public Class getColumnClass (int columnIndex) {
	switch (_type) {
	case LEGAL:
		switch (columnIndex) {
		case 0:		return Integer.class;	// div
		case 1:		return Integer.class;	// wd
		case 2:		return Integer.class;	// id
		case 3:		return String.class;	// water right name
		case 4:		return String.class;	// water source
		case 5:		return String.class;	// location
		case 6:		return String.class;	// county
		case 7:		return String.class;	// adj date
		case 8:		return String.class;	// padj date
		case 9:		return String.class;	// apro date
		case 10:	return Double.class;	// admin no
		case 11:	return Integer.class;	// order no
		case 12:	return String.class;	// prior/case no
		case 13:	return String.class;	// adj type
		case 14:	return String.class; 	// use type
		case 15:	return Double.class;	// rate abs
		case 16:	return Double.class;	// vol abs
		case 17:	return Double.class;	// rate cond
		case 18:	return Double.class;	// vol cond
		case 19:	return Double.class;	// rate apex
		case 20:	return Double.class;	// vol apex
		case 21:	return String.class;	// strtype
		default:	return String.class;
	}
	case SUMMARY:
		switch (columnIndex) {
		case 0:		return Integer.class;	// div
		case 1:		return Integer.class;	// wd
		case 2:		return Integer.class;	// id
		case 3:		return String.class;	// water right name
		case 4:		return String.class;	// water source
		case 5:		return String.class;	// adj date
		case 6:		return String.class;	// padj date
		case 7:		return String.class;	// apro date
		case 8:		return Double.class;	// admin no
		case 9:		return Integer.class;	// order no
		case 10:	return String.class;	// prior/case no
		case 11:	return String.class;	// adj type
		case 12:	return String.class; 	// use type
		case 13:	return Double.class;	// rate abs
		case 14:	return Double.class;	// vol abs
		case 15:	return Double.class;	// rate cond
		case 16:	return Double.class;	// vol cond
		case 17:	return Double.class;	// rate apex
		case 18:	return Double.class;	// vol apex
		case 19:	return String.class;	// location
		case 20:	return String.class;	// county
		case 21:	return String.class;	// strtype
		default:	return String.class;	
		}
	}

	return String.class;
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
	switch (_type) {
	case LEGAL:
	switch (columnIndex) {
		case 0:		return "\n\nDIV";
		case 1:		return "\n\nWD";
		case 2:		return "\n\nID";
		case 3:		return "\n\nWATER RIGHT NAME";
		case 4:		return "\n\nWATER SOURCE";
		case 5:		return "\n\nLOCATION";
		case 6:		return "\n\nCOUNTY";
		case 7:		return "\n\nADJ DATE";
		case 8:		return "\n\nPADJ DATE";
		case 9:		return "\nAPPROPRIATION\nDATE";
		case 10:	return "\nADMINISTRATION\nNUMBER";
		case 11:	return "\nORDER\nNUMBER";
		case 12:	return "PRIOR/\nCASE\nNUMBER";
		case 13:	return "\nADJ\nTYPE";
		case 14:	return "\nUSE\nTYPE";
		case 15:	return "RATE\nABSOLUTE\n(CFS)";
		case 16:	return "VOLUME\nABSOLUTE\n(ACFT)";
		case 17:	return "RATE\nCONDITIONAL\n(CFS)";
		case 18:	return "VOLUME\nCONDITIONAL\n(ACFT)";
		case 19:	return "\nRATE APEX\n(CFS)";
		case 20:	return "VOLUME\nAPEX\n(ACFT)";
		case 21:	return "\nSTRUCTURE\nTYPE";
		default:	return " ";
	}
	case SUMMARY:
	switch (columnIndex) {
		case 0:		return "\n\nDIV";
		case 1:		return "\n\nWD";
		case 2:		return "\n\nID";
		case 3:		return "\n\nWATER RIGHT NAME";
		case 4:		return "\n\nWATER SOURCE";
		case 5:		return "\n\nADJ DATE";
		case 6:		return "\n\nPADJ DATE";
		case 7:		return "\nAPPROPRIATION\nDATE";		
		case 8:		return "\nADMINISTRATION\nNUMBER";
		case 9:		return "\nORDER\nNUMBER";
		case 10:	return "PRIOR/\nCASE\nNUMBER";
		case 11:	return "\nADJ\nTYPE";
		case 12:	return "\nUSE\nTYPE";
		case 13:	return "RATE\nABSOLUTE\n(CFS)";
		case 14:	return "VOLUME\nABSOLUTE\n(ACFT)";
		case 15:	return "RATE\nCONDITIONAL\n(CFS)";
		case 16:	return "VOLUME\nCONDITIONAL\n(ACFT)";
		case 17:	return "\nRATE APEX\n(CFS)";
		case 18:	return "VOLUME\nAPEX\n(ACFT)";
		case 19:	return "\n\nLOCATION";
		case 20:	return "\n\nCOUNTY";
		case 21:	return "\nSTRUCTURE\nTYPE";
		default:	return " ";
	}
	}	
	return " ";
}

/**
Returns the text to be assigned to worksheet tooltips.
@return a String array of tool tips.
*/
public String[] getColumnToolTips() {
	String[] tips = new String[__COLUMNS];

	if ( _type == LEGAL ) {
		tips[0] = "Water division.";
		tips[1] =
		"<html>The water district in which the structure exists, for" +
		" administrative purposes.<BR>" +
		"The WD and ID (WDID) is unique.</html>";
		tips[2] =
		"<html>The structure identifier within the water district." +
		"The WD and ID (WDID) is unique.</html>";
		tips[3] = "Water right name.";
		tips[4] = "Water source.";
		tips[5] = "Legal location.";
		tips[6] = "County name.";
		tips[7] = "Adjudication date.";
		tips[8] = "Prior adjudication date.";
		tips[9] = "Appropriation date.";
		tips[10] = "Administration number.";
		tips[11] = "Order number.";
		tips[12] = "<HTML>Prior case number.  From transact, " +
			"prior_no if not blank,<BR>otherwise case_no. " +
			"(Div. 5 always uses case_no).</HTML>";
		tips[13] = "Adjudication type.";
		tips[14] = "<HTML>Summarizes use codes as printed in the " +
			"tabulation report:<BR> " +
			"ACR = CUMULATIVE ACCRETION TO RIVER<BR>" +
			"ALL = ALL BENEFICIAL USES<BR>" +
			"AUG = AUGMENTATION<BR>" +
			"COM = COMMERCIAL<BR>" +
			"DEP = CUMULATIVE DEPLETION FROM RIVER<BR>" +
			"DOM = DOMESTIC<BR>" +
			"EVP = EVAPORATIVE<BR>" +
			"EXB = EXPORT FROM BASIN<BR>" +
			"EXS = EXPORT FROM STATE<BR>" +
			"FED = FEDERAL RESERVED<BR>" +
			"FIR = FIRE<BR>" +
			"FIS = FISHERY<BR>" +
			"GEO = GEOTHERMAL<BR>" +
			"HUO = HOUSEHOLD USE ONLY<BR>" +
			"IND = INDUSTRIAL<BR>" +
			"IRR = IRRIGATION<BR>" +
			"MIN = MINIMUM STREAMFLOW<BR>" +
			"MUN = MUNICIPAL<BR>" +
			"NET = NET EFFECT ON RIVER<BR>" +
			"OTH = OTHER<BR>" +
			"PWR = POWER GENERATION<BR>" +
			"RCH = RECHARGE<BR>" +
			"REC = RECREATION<BR>" +
			"SNO = SNOW MAKING<BR>" +
			"STK = STOCK<BR>" +
			"STO = STORAGE<BR>" +
			"TMX = TRANSMOUNTAIN EXPORT<BR>" +
			"WLD = WILDLIFE</HTML>.";
		tips[15] = "Absolute net amount in CFS.";
		tips[16] = "Absolute net amount in ACFT.";
		tips[17] = "Conditional net amount in CFS.";
		tips[18] = "Conditional net amount in ACFT.";
		tips[19] = "Alternate point/exchange net amount in CFS.";
		tips[20] = "Alternate point/exchange net amount in ACFT.";
		tips[21] = "";
	}
	else if ( _type == SUMMARY ) {
		tips[0] = "Water division.";
		tips[1] =
		"<html>The water district in which the structure exists, for" +
		" administrative purposes.<BR>" +
		"The WD and ID (WDID) is unique.</html>";
		tips[2] =
		"<html>The structure identifier within the water district." +
		"The WD and ID (WDID) is unique.</html>";
		tips[3] = "Water right name.";
		tips[4] = "Water source.";
		tips[5] = "Adjudication date.";
		tips[6] = "Prior adjudication date.";
		tips[7] = "Appropriation date.";
		tips[8] = "Administration number.";
		tips[9] = "Order number.";
		tips[10] = "<HTML>Prior case number.  From transact, " +
			"prior_no if not blank,<BR>otherwise case_no. " +
			"(Div. 5 always uses case_no).</HTML>";
		tips[11] = "Adjudication type.";
		tips[12] = "<HTML>Summarizes use codes as printed in the " +
			"tabulation report:<BR> " +
			"ACR = CUMULATIVE ACCRETION TO RIVER<BR>" +
			"ALL = ALL BENEFICIAL USES<BR>" +
			"AUG = AUGMENTATION<BR>" +
			"COM = COMMERCIAL<BR>" +
			"DEP = CUMULATIVE DEPLETION FROM RIVER<BR>" +
			"DOM = DOMESTIC<BR>" +
			"EVP = EVAPORATIVE<BR>" +
			"EXB = EXPORT FROM BASIN<BR>" +
			"EXS = EXPORT FROM STATE<BR>" +
			"FED = FEDERAL RESERVED<BR>" +
			"FIR = FIRE<BR>" +
			"FIS = FISHERY<BR>" +
			"GEO = GEOTHERMAL<BR>" +
			"HUO = HOUSEHOLD USE ONLY<BR>" +
			"IND = INDUSTRIAL<BR>" +
			"IRR = IRRIGATION<BR>" +
			"MIN = MINIMUM STREAMFLOW<BR>" +
			"MUN = MUNICIPAL<BR>" +
			"NET = NET EFFECT ON RIVER<BR>" +
			"OTH = OTHER<BR>" +
			"PWR = POWER GENERATION<BR>" +
			"RCH = RECHARGE<BR>" +
			"REC = RECREATION<BR>" +
			"SNO = SNOW MAKING<BR>" +
			"STK = STOCK<BR>" +
			"STO = STORAGE<BR>" +
			"TMX = TRANSMOUNTAIN EXPORT<BR>" +
			"WLD = WILDLIFE</HTML>.";
		tips[13] = "Absolute net amount in CFS.";
		tips[14] = "Absolute net amount in ACFT.";
		tips[15] = "Conditional net amount in CFS.";
		tips[16] = "Conditional net amount in ACFT.";
		tips[17] = "Alternate point/exchange net amount in CFS.";
		tips[18] = "Alternate point/exchange net amount in ACFT.";
		tips[19] = "Legal location.";
		tips[20] = "County name.";
		tips[21] = "";
	}	
	return tips;
}

public int[] getColumnWidths() {
	return getColumnWidths(LEGAL);
}

/**
Returns an array containing the widths (in number of characters) that the 
fields in the table should be sized to.
@param tableFormat the table display format for which to return the widths
@return an integer array containing the widths for each field.
*/
public int[] getColumnWidths(int tableFormat ) {
	int[] widths = new int[__COLUMNS];
	for (int i = 0; i < __COLUMNS; i++) {
		widths[i] = 0;
	}
	int i = 0;
	switch (tableFormat) {
	case LEGAL:
		widths[i++] = 3;	// div
		widths[i++] = 2;	// wd
		widths[i++] = 3;	// id
		widths[i++] = 24;	// water right name
		widths[i++] = 16;	// water source
		widths[i++] = 16;	// location
		widths[i++] = 9;	// county
		widths[i++] = 8;	// adj date
		widths[i++] = 8;	// padj date
		widths[i++] = 11;	// apro date
		widths[i++] = 11;	// admin no
		widths[i++] = 5;	// order no
		widths[i++] = 6;	// prior/case no
		widths[i++] = 3;	// adj type
		widths[i++] = 5;	// use type
		widths[i++] = 7;	// rate abs
		widths[i++] = 7;	// vol abs
		widths[i++] = 10;	// rate cond
		widths[i++] = 10;	// vol cond
		widths[i++] = 7;	// rate apex
		widths[i++] = 7;	// vol apex
		widths[i++] = 10;	// strtype
		break;
	case SUMMARY:
		widths[i++] = 3;	// div
		widths[i++] = 2;	// wd
		widths[i++] = 3;	// id 
		widths[i++] = 24;	// water right name
		widths[i++] = 16;	// water source
		widths[i++] = 8;	// adj date
		widths[i++] = 8;	// padj date
		widths[i++] = 11;	// apro date
		widths[i++] = 11;	// admin no
		widths[i++] = 5;	// order no
		widths[i++] = 6;	// prior/case no
		widths[i++] = 3;	// adj type
		widths[i++] = 5;	// use type
		widths[i++] = 7;	// rate abs
		widths[i++] = 7;	// vol abs
		widths[i++] = 10;	// rate cond
		widths[i++] = 10;	// vol cond
		widths[i++] = 7;	// rate apex
		widths[i++] = 7;	// vol apex
		widths[i++] = 16;	// location
		widths[i++] = 9;	// county
		widths[i++] = 10;	// strtype
		break;
	}
	return widths;
}

public String getFormat(int column) {
	return getFormat(column, _type);
}

/**
Returns the format that the specified column should be displayed in when
the table is being displayed in the given table format. 
@param column column for which to return the format.
@param tableFormat the table format in which the table is being displayed.
@return the format (as used by StringUtil.formatString() in which to display the
column.
*/
public String getFormat(int column, int tableFormat ) {
	switch (tableFormat) {
	case LEGAL:
		switch (column) {
			case 0:		return "%8d";	// div
			case 1:		return "%8d";	// wd
			case 2:		return "%8d";	// id
			case 3:		return "%-40s";	// water right name
			case 4:		return "%-40s";	// water source
			case 5:		return "%-30.30s"; // location
			case 6:		return "%-20s";	// county
			case 7:		return "%10s";	// adj datee
			case 8:		return "%10s";	// padj date
			case 9:		return "%10s";	// apro date	
			case 10:	return "%11.5f";// admin no
			case 11:	return "%8d";	// order no
			case 12:	return "%-9s";	// prior/case no
			case 13:	return "%-1s";	// adj type
			case 14:	return "%-30s";	// use type
			case 15:	return "%12.4f";// rate abs
			case 16:	return "%12.4f";// vol abs
			case 17:	return "%12.4f";// rate cond
			case 18:	return "%12.4f";// vol cond
			case 19:	return "%12.4f";// rate apex
			case 20:	return "%12.4f";// vol apex
			case 21:	return "%-10.10s";//strtype
		}
	case SUMMARY:
		switch (column) {
			case  0:	return "%8d";	// div
			case  1:	return "%8d";	// wd
			case  2:	return "%8d";	// id
			case  3:	return "%-40s";	// water right name
			case  4:	return "%-40s";	// water source
			case  5:	return "%10s";	// adj date
			case  6:	return "%10s";	// padj date
			case  7:	return "%10s";	// apro date
			case  8:	return "%11.5f";// admin no
			case  9:	return "%8d";	// order no
			case 10:	return "%-9s";	// prior/case no
			case 11:	return "%-1s";	// adj type
			case 12:	return "%-30s";	// use type
			case 13:	return "%12.4f";// rate abs
			case 14:	return "%12.4f";// vol abs
			case 15:	return "%12.4f";// rate cond
			case 16:	return "%12.4f";// vol cond
			case 17:	return "%12.4f";// rate apex
			case 18:	return "%12.4f";// vol apex
			case 19:	return "%-30.30s"; // location
			case 20:	return "%-20s";	// county
			case 21:	return "%-10.10s";//strtype
		}
	}
	return "%-8s";
}

/**
Returns the number of rows of data in the table.
@return the number of rows of data in the tabele.
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

	HydroBase_NetAmts r = (HydroBase_NetAmts)_data.elementAt(row);
	switch (_type) {
	case LEGAL:
	switch (col) {
		case 0:		return new Integer(r.getDiv());
		case 1:		return new Integer(r.getWD());
		case 2:		return new Integer(r.getID());
		case 3:		return r.getWr_name();
		case 4:		return r.getWd_stream_name();
		case 5:		
			return HydroBase_Util.buildLocation(
				r.getPM(), r.getTS(), r.getTdir(),
				r.getRng(), r.getRdir(), r.getSec(),
				r.getSeca(), r.getQ160(), r.getQ40(),
				r.getQ10());
		case 6:		return __dmi.lookupCountyName(r.getCty());
		case 7:		return parseDate(r.getAdj_date());
		case 8:		return parseDate(r.getPadj_date());
		case 9:		return parseDate(r.getApro_date());
		case 10:	return new Double(r.getAdmin_no());
		case 11:	return new Integer(r.getOrder_no());
		case 12:	return r.getPri_case_no();
		case 13:	return r.getAdj_type();
		case 14:	return r.getUse();
		case 15:	return new Double(r.getNet_rate_abs());
		case 16:	return new Double(r.getNet_vol_abs());
		case 17:	return new Double(r.getNet_rate_cond());
		case 18:	return new Double(r.getNet_vol_cond());
		case 19:	return new Double(r.getNet_rate_apex());
		case 20:	return new Double(r.getNet_vol_apex());
		case 21: 	return r.getStrtype();
		default:	return "";
	}
	case SUMMARY:
	switch (col) {
		case 0:		return new Integer(r.getDiv());
		case 1:		return new Integer(r.getWD());
		case 2:		return new Integer(r.getID());
		case 3:		return r.getWr_name();
		case 4:		return r.getWd_stream_name();
		case 5:		return parseDate(r.getAdj_date());
		case 6:		return parseDate(r.getPadj_date());
		case 7:		return parseDate(r.getApro_date());
		case 8:		return new Double(r.getAdmin_no());
		case 9:		return new Integer(r.getOrder_no());
		case 10:	return r.getPri_case_no();
		case 11:	return r.getAdj_type();
		case 12:	return r.getUse();
		case 13:	return new Double(r.getNet_rate_abs());
		case 14:	return new Double(r.getNet_vol_abs());
		case 15:	return new Double(r.getNet_rate_cond());
		case 16:	return new Double(r.getNet_vol_cond());
		case 17:	return new Double(r.getNet_rate_apex());
		case 18:	return new Double(r.getNet_vol_apex());
		case 19:	
			return HydroBase_Util.buildLocation(
				r.getPM(), r.getTS(), r.getTdir(),
				r.getRng(), r.getRdir(), r.getSec(),
				r.getSeca(), r.getQ160(), r.getQ40(),
				r.getQ10());
		case 20:	return __dmi.lookupCountyName(r.getCty());
		case 21: 	return r.getStrtype();
		default:	return "";
	}
	}
	return "";
}

private String parseDate(Date d) {
	if (d == null) {
		return "";
	}

	return (new DateTime(d)).toString(DateTime.FORMAT_YYYY_MM_DD);
}

}
