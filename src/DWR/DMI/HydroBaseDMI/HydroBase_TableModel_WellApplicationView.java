// ----------------------------------------------------------------------------
// HydroBase_TableModel_WellApplicationView - Table Model for a Vector of 
//	HydroBase_WellApplicationView objects.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2005-02-09	J. Thomas Sapienza, RTi	Initial version.
// 2005-04-29	JTS, RTi		Added finalize().
// 2005-06-28	JTS, RTi		Removed unused DMI parameter.
// 2005-07-06	Steven A. Malers, RTi	Add tool tips.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------
// EndHeader

package DWR.DMI.HydroBaseDMI;

import java.util.Date;
import java.util.List;

import RTi.Util.Time.DateTime;

/**
This class is a table model for displaying well application data.
*/
public class HydroBase_TableModel_WellApplicationView
extends HydroBase_TableModel {

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 76;

/**
References to columns.
*/
public final static int
	COL_DIV =			0,
	COL_WD =			1,
	COL_PERMIT_NO =			2,
	COL_PERMIT_SUF =		3,
	COL_PERMIT_RPL =		4,
	COL_LOCATION =			5,
	COL_RECEIPT =			6,
	COL_WELL_NAME =			7,
	COL_STRUCTURE_NUM =		8,
	COL_SUBDIV_NUM =		9,
	COL_PYIELD =			10,
	COL_PDEPTH =			11,
	COL_PACREFT =			12,
	COL_CASE_NO =			13,
	COL_ELEV =			14,
	COL_AREA_IRR =			15,
	COL_IRR_MEAS =			16,
	COL_COMMENT =			17,
	COL_WELL_X_NO =			18,
	COL_WELL_X_SUF =		19,
	COL_WELL_X_RPL =		20,
	COL_SUBDIV_NAME =		21,
	COL_FILING =			22,
	COL_LOT =			23,
	COL_BLOCK =			24,
	COL_PARCEL_SIZE =		25,
	COL_PARCEL_NO =			26,
	COL_AQUIFER1 =			27,
	COL_AQUIFER2 =			28,
	COL_USE1 =			29,
	COL_USE2 =			30,
	COL_USE3 =			31,
	COL_DRILLER_LIC =		32,
	COL_PUMP_LIC =			33,
	COL_ACT_CODE =			34,
	COL_ACT_DATE =			35,
	COL_STAT_CODE =			36,
	COL_STAT_DATE =			37,
	COL_TRAN_CODE =			38,
	COL_TRAN_DATE =			39,
	COL_PERMIT_TYPE_NUM =		40,
	COL_ENGINEER =			41,
	COL_STATUTE =			42,
	COL_TPERF =			43,
	COL_BPERF = 			44,
	COL_ABREQ = 			45,
	COL_METER = 			46,
	COL_LOG = 			47,
	COL_MD = 			48,
	COL_BASIN = 			49,
	COL_VALID_PERMIT = 		50,
	COL_NPDATE = 			51,
	COL_EXDATE = 			52,
	COL_NOTICE_DATE = 		53,
	COL_YIELD = 			54,
	COL_ACREFT = 			55,
	COL_DEPTH = 			56,
	COL_LEVEL = 			57,
	COL_QUAL = 			58,
	COL_WELL_TYPE = 		59,
	COL_VALID_STRUC = 		60,
	COL_PIDATE = 			61,
	COL_WADATE = 			62,
	COL_SADATE =			63,
	COL_SBUDATE =			64,
	COL_ABRDATE =			65,
	COL_ABCODATE =			66,
	COL_NWCDATE =			67,
	COL_NBUDATE =			68,
	COL_WCDATE =			69,
	COL_PCDATE =			70,
	COL_GW_CONTROLLER_NUM =		71,
	COL_UTM_X =			72,
	COL_UTM_Y =			73,
	COL_LONG_DEC_DEG =		74,
	COL_LAT_DEC_DEG =		75;

/**
Constructor.  This builds the Model for displaying the given well results.
@param results the results that will be displayed in the table.
@param dmi a reference to the dmi object used to query for the results.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_WellApplicationView(List results)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_WellApplicationView constructor.");
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
		case COL_DIV:			return Integer.class;
		case COL_WD:			return Integer.class;
		case COL_PERMIT_NO:		return Integer.class;
		case COL_PERMIT_SUF:		return String.class;
		case COL_PERMIT_RPL:		return String.class;
		case COL_LOCATION:		return String.class;
		case COL_RECEIPT:		return String.class;
		case COL_WELL_NAME:		return String.class;
		case COL_STRUCTURE_NUM:		return Integer.class;
		case COL_SUBDIV_NUM:		return Integer.class;
		case COL_PYIELD:		return Double.class;
		case COL_PDEPTH:		return Integer.class;
		case COL_PACREFT:		return Double.class;
		case COL_CASE_NO:		return String.class;
		case COL_ELEV:			return Double.class;
		case COL_AREA_IRR:		return Double.class;
		case COL_IRR_MEAS:		return String.class;
		case COL_COMMENT:		return String.class;
		case COL_WELL_X_NO:		return Integer.class;
		case COL_WELL_X_SUF:		return String.class;
		case COL_WELL_X_RPL:		return String.class;
		case COL_SUBDIV_NAME:		return String.class;
		case COL_FILING:		return String.class;
		case COL_LOT:			return String.class;
		case COL_BLOCK:			return String.class;
		case COL_PARCEL_SIZE:		return Double.class;
		case COL_PARCEL_NO:		return String.class;
		case COL_AQUIFER1:		return String.class;
		case COL_AQUIFER2:		return String.class;
		case COL_USE1:			return String.class;
		case COL_USE2:			return String.class;
		case COL_USE3:			return String.class;
		case COL_DRILLER_LIC:		return String.class;
		case COL_PUMP_LIC:		return String.class;
		case COL_ACT_CODE:		return String.class;
		case COL_ACT_DATE:		return String.class;
		case COL_STAT_CODE:		return String.class;
		case COL_STAT_DATE:		return String.class;
		case COL_TRAN_CODE:		return String.class;
		case COL_TRAN_DATE:		return String.class;
		case COL_PERMIT_TYPE_NUM:	return Integer.class;
		case COL_ENGINEER:		return String.class;
		case COL_STATUTE:		return String.class;
		case COL_TPERF:			return Integer.class;
		case COL_BPERF:			return Integer.class;
		case COL_ABREQ:			return Integer.class;
		case COL_METER:			return Integer.class;
		case COL_LOG:			return Integer.class;
		case COL_MD:			return String.class;
		case COL_BASIN:			return String.class;
		case COL_VALID_PERMIT:		return Integer.class;
		case COL_NPDATE:		return String.class;
		case COL_EXDATE:		return String.class;
		case COL_NOTICE_DATE:		return String.class;
		case COL_YIELD:			return Double.class;
		case COL_ACREFT:		return Double.class;
		case COL_DEPTH:			return Integer.class;
		case COL_LEVEL:			return Integer.class;
		case COL_QUAL:			return Integer.class;
		case COL_WELL_TYPE:		return String.class;
		case COL_VALID_STRUC:		return Integer.class;
		case COL_PIDATE:		return String.class;
		case COL_WADATE:		return String.class;
		case COL_SADATE:		return String.class;
		case COL_SBUDATE:		return String.class;
		case COL_ABRDATE:		return String.class;
		case COL_ABCODATE:		return String.class;
		case COL_NWCDATE:		return String.class;
		case COL_NBUDATE:		return String.class;
		case COL_WCDATE:		return String.class;
		case COL_PCDATE:		return String.class;
		case COL_GW_CONTROLLER_NUM:	return Integer.class;
		case COL_UTM_X:			return Double.class;
		case COL_UTM_Y:			return Double.class;
		case COL_LONG_DEC_DEG:		return Double.class;
		case COL_LAT_DEC_DEG:		return Double.class;
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
		case COL_DIV:			return "DIV";
		case COL_WD:			return "WD";
		case COL_PERMIT_NO:		return "PERMIT #";
		case COL_PERMIT_SUF:		return "SFX";
		case COL_PERMIT_RPL:		return "REP";
		case COL_LOCATION:		return "LOCATION";
		case COL_RECEIPT:		return "RECEIPT";
		case COL_WELL_NAME:		return "WELL NAME";
		case COL_STRUCTURE_NUM:		return "STRUC #";
		case COL_SUBDIV_NUM:		return "SUBD #";
		case COL_PYIELD:		return "PYIELD";
		case COL_PDEPTH:		return "PDEPTH";
		case COL_PACREFT:		return "PACREFT";
		case COL_CASE_NO:		return "CASE #";
		case COL_ELEV:			return "ELEV";
		case COL_AREA_IRR:		return "AREA IRR";
		case COL_IRR_MEAS:		return "IRR MEAS";
		case COL_COMMENT:		return "COMMENT";
		case COL_WELL_X_NO:		return "WELLXNO";
		case COL_WELL_X_SUF:		return "WELLXSUF";
		case COL_WELL_X_RPL:		return "WELLXRPL";
		case COL_SUBDIV_NAME:		return "SUBD NAME";
		case COL_FILING:		return "FILING";
		case COL_LOT:			return "LOT";
		case COL_BLOCK:			return "BLOCK";
		case COL_PARCEL_SIZE:		return "PARCEL SIZE";
		case COL_PARCEL_NO:		return "PARCEL #";
		case COL_AQUIFER1:		return "AQUIFER 1";
		case COL_AQUIFER2:		return "AQUIFER 2";
		case COL_USE1:			return "USE 1";
		case COL_USE2:			return "USE 2";
		case COL_USE3:			return "USE 3";
		case COL_DRILLER_LIC:		return "DRILLER LIC";
		case COL_PUMP_LIC:		return "PUMP LIC";
		case COL_ACT_CODE:		return "ACTCODE";
		case COL_ACT_DATE:		return "ACTDATE";
		case COL_STAT_CODE:		return "STATCODE";
		case COL_STAT_DATE:		return "STATDATE";
		case COL_TRAN_CODE:		return "TRANCODE";
		case COL_TRAN_DATE:		return "TRANDATE";
		case COL_PERMIT_TYPE_NUM:	return "TYPE #";
		case COL_ENGINEER:		return "ENGINEER";
		case COL_STATUTE:		return "STATUTE";
		case COL_TPERF:			return "TPERF";
		case COL_BPERF:			return "BPERF";
		case COL_ABREQ:			return "ABREQ";
		case COL_METER:			return "METER";
		case COL_LOG:			return "LOG";
		case COL_MD:			return "MD";
		case COL_BASIN:			return "BASIN";
		case COL_VALID_PERMIT:		return "VALID PERMIT";
		case COL_NPDATE:		return "NPDATE";
		case COL_EXDATE:		return "EXDATE";
		case COL_NOTICE_DATE:		return "NOTICEDATE";
		case COL_YIELD:			return "YIELD (gpm)";
		case COL_ACREFT:		return "ACREFT";
		case COL_DEPTH:			return "DEPTH (ft)";
		case COL_LEVEL:			return "LEVEL";
		case COL_QUAL:			return "QUAL";
		case COL_WELL_TYPE:		return "WELLTYPE";
		case COL_VALID_STRUC:		return "VALIDSTRUC";
		case COL_PIDATE:		return "PIDATE";
		case COL_WADATE:		return "WADATE";
		case COL_SADATE:		return "SADATE";
		case COL_SBUDATE:		return "SBUDATE";
		case COL_ABRDATE:		return "ABRDATE";
		case COL_ABCODATE:		return "ABCODATE";
		case COL_NWCDATE:		return "NWCDATE";
		case COL_NBUDATE:		return "NBUDATE";
		case COL_WCDATE:		return "WCDATE";
		case COL_PCDATE:		return "PCDATE";
		case COL_GW_CONTROLLER_NUM:	return "GWCONTR #";
		case COL_UTM_X:			return "UTM X";
		case COL_UTM_Y:			return "UTM Y";
		case COL_LONG_DEC_DEG:		return "LONGITUDE";
		case COL_LAT_DEC_DEG:		return "LATITUDE";
		default:			return " ";
	}	
}

/**
Returns the text to be assigned to worksheet tooltips.
@return a String array of tool tips.
*/
public String[] getColumnToolTips() {
	String[] tips = new String[__COLUMNS];

	tips[COL_DIV] = "Water division";
	tips[COL_WD] = "Water district";
	tips[COL_PERMIT_NO] = "Well permit number.";
	tips[COL_PERMIT_SUF] = "Well suffix code.";
	tips[COL_PERMIT_RPL] = "Well replacement code.  " +
		"Contains an 'A' for exempt, and 'R' for non-exempt.";
	tips[COL_LOCATION] = "Legal location.";
	tips[COL_RECEIPT] = "Unique identifer.  Generated by cash register.";
	tips[COL_WELL_NAME] = "Owner's well designation.";
	tips[COL_STRUCTURE_NUM] =
		"<HTML>Structure number, if available.<BR>" +
		"Use should generally be avoided.</HTML>";
	tips[COL_SUBDIV_NUM] = "Subdivision identifier.";
	tips[COL_PYIELD] = "Proposed yield of well, GPM.";
	tips[COL_PDEPTH] = "Proposed depth of well, FT.";
	tips[COL_PACREFT] = "Proposed annual appropriation, ACFT.";
	tips[COL_CASE_NO] = "Water Court case number.";
	tips[COL_ELEV] = "Ground surface elevation, FT.";
	tips[COL_AREA_IRR] = "Area irrigated.";
	tips[COL_IRR_MEAS] =
		"Unit of measurement used by area_irr (ACRES or SQ. FT).";
	tips[COL_COMMENT] = "Comment.";
	tips[COL_WELL_X_NO] =
		"Cross reference to another well or permit number.";
	tips[COL_WELL_X_SUF] =
		"Cross reference to another well or permit suffix.";
	tips[COL_WELL_X_RPL] =
		"Cross reference to another well or permit replacement code.";
	tips[COL_SUBDIV_NAME] = "Subdivision name.";
	tips[COL_FILING] = "Filing number of subdivision.";
	tips[COL_LOT] = "Lot number in subdivision.";
	tips[COL_BLOCK] = "Block number in Subdivision.";
	tips[COL_PARCEL_SIZE] = "Size of parcel in acres.";
	tips[COL_PARCEL_NO] = "State parcel identifier number.";
	tips[COL_AQUIFER1] = "Aquifer in which well is located.";
	tips[COL_AQUIFER2] =
		"If well is located in two aquifers, name of second aquifer.";
	tips[COL_USE1] = "The permitted use of the well.";
	tips[COL_USE2] = "The permitted use of the well.";
	tips[COL_USE3] =
		"A use that supplements the use(s) selected in USE1, USE2.";
	tips[COL_DRILLER_LIC] = "Water well contractor's license number.";
	tips[COL_PUMP_LIC] = "License number of the pump installer.";
	tips[COL_ACT_CODE] = "<HTML>Activity code.  One of serveral main codes"+
		" used to specify main groups in the permitting process.<BR>" +
		"These codes reflect the INITIAL or FINAL STATUS of the " +
		"record.</HTML>";
	tips[COL_ACT_DATE] = "Date well permit application received.";
	tips[COL_STAT_CODE] = "Interim status of the application or permit.";
	tips[COL_STAT_DATE] =
		"Date corresponding to the status code action.";
	tips[COL_TRAN_CODE] = "Activity or status code.  Last action updated.";
	tips[COL_TRAN_DATE] =
		"Date of last trancode.  Last activity on application.";
	tips[COL_PERMIT_TYPE_NUM] =
		"<HTML>Description of the purpose of a permit:<BR>" +
		"1 = PERMIT TO CONSTRUCT A WELL<BR>" +
		"2 = REGISTRATION OF EXISTING WELL<BR>" +
		"3 = CHANGE/EXPANSION OF USE<BR>" +
		"4 = CHANGE/EXPANSION OF USE OF AN EXISTING WELL<BR>" +
		"5 = PERMIT TO USE AN EXISTING WELL<BR>" +
		"6 = PERMIT TO EXPOSE WATER IN A PIT<BR>" +
		"7 = REGISTRATION PURSUANT TO A DECREE<BR>" +
		"8 = PERMIT TO CONSTRUCT A GEOTHERMAL WELL<BR>" +
		"9 = PERMIT TO CONSTRUCT (DEEPEN) AN EXISTING WELL<BR>" +
		"10 = PERMIT TO CONSTRUCT A WELL (AMENDED)<BR>" +
		"11 = PERMIT TO USE AN EXISTING WELL (AMENDED)<BR>" +
		"12 = <BR>" +
		"13 = CHANGE OF USE<BR>" +
		"14 = CHANGE OF USE OF EXISTING WELL<BR>" +
		"15 = CORRECTION OF USE FOR AN EXISTING WELL<BR>" +
		"16 = PERMIT TO USE AN EXISTING WELL (REINSTATEMENT)<BR>" +
		"17 = PERMIT TO CONSTRUCT GEOEXCHANGE SYSTEM LOOP FIELDS" +
		"</HTML>";
	tips[COL_ENGINEER] = "Engineer who worked on application.";
	tips[COL_STATUTE] = "Statute under which the permit was issued " +
		"using the last four numbers of the chapter and " +
		"paragraph (i.e., 6023 or 1372).";
	tips[COL_TPERF] = "Depth to top of first perforated casing, FT.";
	tips[COL_BPERF] = "Depth to base of last perforated casing, FT.";
	tips[COL_ABREQ] = "Flag if the well requires plugging and sealing " +
		"upon construction of new well.";
	tips[COL_METER] =
		"Flag indicating totalizing flow meter required, installed.";
	tips[COL_LOG] =
		"Flag to indicate if a geophysical is required and received.";
	tips[COL_MD] =
		"Designated Groundwater Basin Management District number.";
	tips[COL_BASIN] = "Designated groundwater basin number.";
	tips[COL_VALID_PERMIT] =
		"Calculated values to determine if a well permit is valid.";
	tips[COL_NPDATE] =
		"Date the permit, denial (AD) or monitoring hole was issued.";
	tips[COL_EXDATE] = "Expiration date of well permit.";
	tips[COL_NOTICE_DATE] =
		"Notice sent to owner indicating permit has expired.";
	tips[COL_YIELD] = "Actual pumping rate in GPM.";
	tips[COL_ACREFT] = "Annual appropriation in acre feet.";
	tips[COL_DEPTH] = "Total depth of well, FT.";
	tips[COL_LEVEL] = "Depth to static water level, FT.";
	tips[COL_QUAL] =
		"Flag indicating if water quality information is available.";
	tips[COL_WELL_TYPE] = "Calculated values to determine if record is " +
		"exempt, nonexempt, or geothermal.";
	tips[COL_VALID_STRUC] = "Not used.";
	tips[COL_PIDATE] = "Date pump installation report received at DWR.";
	tips[COL_WADATE] =
		"Date Well Construction and Test Report was received at DWR.";
	tips[COL_SADATE] = "Date of first beneficial use.";
	tips[COL_SBUDATE] = "Date statement of beneficial use received.";
	tips[COL_ABRDATE] = "Date abandonment report received.";
	tips[COL_ABCODATE] = "Date well plugged and abandoned.";
	tips[COL_NWCDATE] = "Notice of well completion form receipt date.";
	tips[COL_NBUDATE] = "Date of commencement of beneficial use form "+
		"receipt date. (Non-Trib.).";
	tips[COL_WCDATE] = "Date well construction completed.";
	tips[COL_PCDATE] = "Date pump installation completed.";
	tips[COL_GW_CONTROLLER_NUM] = "Ground water controller number.";
	tips[COL_UTM_X] = "UTM X coordinate.";
	tips[COL_UTM_Y] = "UTM Y coordinate.";
	tips[COL_LONG_DEC_DEG] = "Longitude, decimal degrees.";
	tips[COL_LAT_DEC_DEG] = "Latitude, decimal degrees.";
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
	widths[COL_DIV] = 		3;
	widths[COL_WD] = 		2;
	widths[COL_PERMIT_NO] = 	10;
	widths[COL_PERMIT_SUF] = 	3;
	widths[COL_PERMIT_RPL] = 	3;
	widths[COL_LOCATION] =		16;
	widths[COL_RECEIPT] = 		8;
	widths[COL_WELL_NAME] = 	12;
	widths[COL_STRUCTURE_NUM] = 	7;
	widths[COL_SUBDIV_NUM] = 	6;
	widths[COL_PYIELD] = 		6;
	widths[COL_PDEPTH] = 		5;
	widths[COL_PACREFT] = 		7;
	widths[COL_CASE_NO] = 		8;
	widths[COL_ELEV] = 		5;
	widths[COL_AREA_IRR] = 		8;
	widths[COL_IRR_MEAS] = 		8;
	widths[COL_COMMENT] = 		20;
	widths[COL_WELL_X_NO] = 	7;
	widths[COL_WELL_X_SUF] = 	8;
	widths[COL_WELL_X_RPL] = 	8;
	widths[COL_SUBDIV_NAME] = 	12;
	widths[COL_FILING] = 		6;
	widths[COL_LOT] = 		5;
	widths[COL_BLOCK] = 		5;
	widths[COL_PARCEL_SIZE] = 	12;
	widths[COL_PARCEL_NO] = 	8;
	widths[COL_AQUIFER1] = 		9;
	widths[COL_AQUIFER2] = 		9;
	widths[COL_USE1] = 		5;
	widths[COL_USE2] = 		5;
	widths[COL_USE3] = 		5;
	widths[COL_DRILLER_LIC] = 	11;
	widths[COL_PUMP_LIC] = 		8;
	widths[COL_ACT_CODE] = 		7;
	widths[COL_ACT_DATE] = 		10;
	widths[COL_STAT_CODE] = 	8;
	widths[COL_STAT_DATE] = 	10;
	widths[COL_TRAN_CODE] = 	8;
	widths[COL_TRAN_DATE] = 	10;
	widths[COL_PERMIT_TYPE_NUM] = 	6;
	widths[COL_ENGINEER] = 		8;
	widths[COL_STATUTE] = 		7;
	widths[COL_TPERF] = 		5;
	widths[COL_BPERF] = 		5;
	widths[COL_ABREQ] =		5;
	widths[COL_METER] =		5;
	widths[COL_LOG] =		3;
	widths[COL_MD] =		2;
	widths[COL_BASIN] =		5;
	widths[COL_VALID_PERMIT] = 	12;
	widths[COL_NPDATE] =		10;
	widths[COL_EXDATE] = 		10;
	widths[COL_NOTICE_DATE] = 	10;
	widths[COL_YIELD] = 		11;
	widths[COL_ACREFT] = 		8;
	widths[COL_DEPTH] = 		10;
	widths[COL_LEVEL] = 		5;
	widths[COL_QUAL] = 		5;
	widths[COL_WELL_TYPE] = 	8;
	widths[COL_VALID_STRUC] = 	10;
	widths[COL_PIDATE] = 		10;
	widths[COL_WADATE] = 		10;
	widths[COL_SADATE] = 		10;
	widths[COL_SBUDATE] = 		10;
	widths[COL_ABRDATE] = 		10;
	widths[COL_ABCODATE] = 		10;
	widths[COL_NWCDATE] = 		10;
	widths[COL_NBUDATE] = 		10;
	widths[COL_WCDATE] = 		10;
	widths[COL_PCDATE] = 		10;
	widths[COL_GW_CONTROLLER_NUM] = 9;
	widths[COL_UTM_X] = 		13;
	widths[COL_UTM_Y] = 		13;
	widths[COL_LONG_DEC_DEG] = 	12;
	widths[COL_LAT_DEC_DEG] = 	12;
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
		case COL_DIV:			return "%2d";
		case COL_WD:			return "%2d";
		case COL_PERMIT_NO:		return "%10d";
		case COL_PERMIT_SUF:		return "%-3s";
		case COL_PERMIT_RPL:		return "%-1s";
		case COL_LOCATION:		return "%24.24s";
		case COL_RECEIPT:		return "%-8s";
		case COL_WELL_NAME:		return "%-60s";
		case COL_STRUCTURE_NUM:		return "%8d";
		case COL_SUBDIV_NUM:		return "%8d";
		case COL_PYIELD:		return "%8.2f";
		case COL_PDEPTH:		return "%8d";
		case COL_PACREFT:		return "%8.2f";
		case COL_CASE_NO:		return "%-20s";
		case COL_ELEV:			return "%8d";
		case COL_AREA_IRR:		return "%8.2f";
		case COL_IRR_MEAS:		return "%-7s";
		case COL_COMMENT:		return "%-255s";
		case COL_WELL_X_NO:		return "%8d";
		case COL_WELL_X_SUF:		return "%-3s";
		case COL_WELL_X_RPL:		return "%-1s";
		case COL_SUBDIV_NAME:		return "%-45s";
		case COL_FILING:		return "%-4s";
		case COL_LOT:			return "%-5s";
		case COL_BLOCK:			return "%-3s";
		case COL_PARCEL_SIZE:		return "%8.2f";
		case COL_PARCEL_NO:		return "%-30s";
		case COL_AQUIFER1:		return "%-4s";
		case COL_AQUIFER2:		return "%-4s";
		case COL_USE1:			return "%-1s";
		case COL_USE2:			return "%-1s";
		case COL_USE3:			return "%-1s";
		case COL_DRILLER_LIC:		return "%-15s";
		case COL_PUMP_LIC:		return "%-15s";
		case COL_ACT_CODE:		return "%-2s";
		case COL_ACT_DATE:		return "%-20s";
		case COL_STAT_CODE:		return "%-2s";
		case COL_STAT_DATE:		return "%-20s";
		case COL_TRAN_CODE:		return "%-2s";
		case COL_TRAN_DATE:		return "%-20s";
		case COL_PERMIT_TYPE_NUM:	return "%8d";
		case COL_ENGINEER:		return "%-3s";
		case COL_STATUTE:		return "%-4s";
		case COL_TPERF:			return "%8d";
		case COL_BPERF:			return "%8d";
		case COL_ABREQ:			return "%8d";
		case COL_METER:			return "%8d";
		case COL_LOG:			return "%8d";
		case COL_MD:			return "%-2s";
		case COL_BASIN:			return "%-2s";
		case COL_VALID_PERMIT:		return "%8d";
		case COL_NPDATE:		return "%-20s";
		case COL_EXDATE:		return "%-20s";
		case COL_NOTICE_DATE:		return "%-20s";
		case COL_YIELD:			return "%8.2f";
		case COL_ACREFT:		return "%8.2f";
		case COL_DEPTH:			return "%8d";
		case COL_LEVEL:			return "%8d";
		case COL_QUAL:			return "%8d";
		case COL_WELL_TYPE:		return "%-1s";
		case COL_VALID_STRUC:		return "%8d";
		case COL_PIDATE:		return "%-20s";
		case COL_WADATE:		return "%-20s";
		case COL_SADATE:		return "%-20s";
		case COL_SBUDATE:		return "%-20s";
		case COL_ABRDATE:		return "%-20s";
		case COL_ABCODATE:		return "%-20s";
		case COL_NWCDATE:		return "%-20s";
		case COL_NBUDATE:		return "%-20s";
		case COL_WCDATE:		return "%-20s";
		case COL_PCDATE:		return "%-20s";
		case COL_GW_CONTROLLER_NUM:	return "%8d";
		case COL_UTM_X:			return "%13.6f";
		case COL_UTM_Y:			return "%13.6f";
		case COL_LONG_DEC_DEG:		return "%12.6f";
		case COL_LAT_DEC_DEG:		return "%12.6f";
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

	HydroBase_WellApplicationView w = 
		(HydroBase_WellApplicationView)_data.get(row);
	switch (col) {
		case COL_DIV:		return new Integer(w.getDiv());
		case COL_WD:		return new Integer(w.getWD());
		case COL_PERMIT_NO:	return new Integer(w.getPermitno());
		case COL_PERMIT_SUF:	return w.getPermitsuf();
		case COL_PERMIT_RPL:	return w.getPermitrpl();
		case COL_LOCATION:	
			return HydroBase_Util.buildLocation(
				w.getPM(), w.getTS(), w.getTdir(),
				w.getRng(), w.getRdir(), w.getSec(),
				w.getSeca(), w.getQ160(), w.getQ40(),
				w.getQ10());
		case COL_RECEIPT:	return w.getReceipt();
		case COL_WELL_NAME:	return w.getWell_name();
		case COL_STRUCTURE_NUM:	
			return new Integer(w.getStructure_num());
		case COL_SUBDIV_NUM:	return new Integer(w.getSubdiv_num());
		case COL_PYIELD:	return new Double(w.getPyield());
		case COL_PDEPTH:	return new Integer(w.getPdepth());
		case COL_PACREFT:	return new Double(w.getPacreft());
		case COL_CASE_NO:	return w.getCase_no();
		case COL_ELEV:		return new Double(w.getElev());
		case COL_AREA_IRR:	return new Double(w.getArea_irr());
		case COL_IRR_MEAS:	return w.getIrr_meas();
		case COL_COMMENT:	return w.getComment();
		case COL_WELL_X_NO:	return new Integer(w.getWellxno());
		case COL_WELL_X_SUF:	return w.getWellxsuf();
		case COL_WELL_X_RPL:	return w.getWellxrpl();
		case COL_SUBDIV_NAME:	return w.getSubdiv_name();
		case COL_FILING:	return w.getFiling();
		case COL_LOT:		return w.getLot();
		case COL_BLOCK:		return w.getBlock();
		case COL_PARCEL_SIZE:	return new Double(w.getParcel_size());
		case COL_PARCEL_NO:	return w.getParcel_no();
		case COL_AQUIFER1:	return w.getAquifer1();
		case COL_AQUIFER2:	return w.getAquifer2();
		case COL_USE1:		return w.getUse1();
		case COL_USE2:		return w.getUse2();
		case COL_USE3:		return w.getUse3();
		case COL_DRILLER_LIC:	return w.getDriller_lic();
		case COL_PUMP_LIC:	return w.getPump_lic();
		case COL_ACT_CODE:	return w.getActcode();
		case COL_ACT_DATE:	return parseDate(w.getActdate());
		case COL_STAT_CODE:	return w.getStatcode();
		case COL_STAT_DATE:	return parseDate(w.getStatdate());
		case COL_TRAN_CODE:	return w.getTrancode();
		case COL_TRAN_DATE:	return parseDate(w.getTrandate());
		case COL_PERMIT_TYPE_NUM:	
			return new Integer(w.getPermit_type_num());
		case COL_ENGINEER:	return w.getEngineer();
		case COL_STATUTE:	return w.getStatute();
		case COL_TPERF:		return new Integer(w.getTperf());
		case COL_BPERF:		return new Integer(w.getBperf());
		case COL_ABREQ:		return new Integer(w.getAbreq());
		case COL_METER:		return new Integer(w.getMeter());
		case COL_LOG:		return new Integer(w.getLog());
		case COL_MD:		return w.getMD();
		case COL_BASIN:		return w.getBasin();
		case COL_VALID_PERMIT:	return new Integer(w.getValid_permit());
		case COL_NPDATE:	return parseDate(w.getNpdate());
		case COL_EXDATE:	return parseDate(w.getExdate());
		case COL_NOTICE_DATE:	return parseDate(w.getNoticedate());
		case COL_YIELD:		return new Double(w.getYield());
		case COL_ACREFT:	return new Double(w.getAcreft());
		case COL_DEPTH:		return new Integer(w.getDepth());
		case COL_LEVEL:		return new Integer(w.getLevel());
		case COL_QUAL:		return new Integer(w.getQual());
		case COL_WELL_TYPE:	return w.getWell_type();
		case COL_VALID_STRUC:	return new Integer(w.getValid_struc());
		case COL_PIDATE:	return parseDate(w.getPidate());
		case COL_WADATE:	return parseDate(w.getWadate());
		case COL_SADATE:	return parseDate(w.getSadate());
		case COL_SBUDATE:	return parseDate(w.getSbudate());
		case COL_ABRDATE:	return parseDate(w.getAbrdate());
		case COL_ABCODATE:	return parseDate(w.getAbcodate());
		case COL_NWCDATE:	return parseDate(w.getNwcdate());
		case COL_NBUDATE:	return parseDate(w.getNbudate());
		case COL_WCDATE:	return parseDate(w.getWcdate());
		case COL_PCDATE:	return parseDate(w.getPcdate());
		case COL_GW_CONTROLLER_NUM:	
			return new Integer(w.getGw_controller_num());
		case COL_UTM_X:		return new Double(w.getUtm_x());
		case COL_UTM_Y:		return new Double(w.getUtm_y());
		case COL_LONG_DEC_DEG:	return new Double(w.getLongdecdeg());
		case COL_LAT_DEC_DEG:	return new Double(w.getLatdecdeg());
		default:		return "";
	}
}

private String parseDate(Date d) {
	if (d == null) {
		return "";
	}

	return (new DateTime(d)).toString(DateTime.FORMAT_YYYY_MM_DD);
}

}
