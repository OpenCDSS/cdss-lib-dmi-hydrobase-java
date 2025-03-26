// HydroBase_TableModel_Transact - class that handles the table model for the GUI for the times that Transact is queried.

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

import java.util.Date;
import java.util.List;

import RTi.Util.Time.DateTime;

/**
This class is a table model for displaying transact data in the HydroBase_GUI_WaterRights GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_TableModel_Transact 
extends HydroBase_TableModel<HydroBase_Transact> {

/**
Order the columns in legal format.
*/
public static final int LEGAL = 0;
/**
Order the columns in summary format.
*/
public static final int SUMMARY = 1;
/**
Order the columns in associated rights format.
*/
public static final int ASSOCIATED_RIGHTS = 2;
/**
Order the columns in augmented plans format.
*/
public static final int AUG_PLANS = 3;
/**
Order the columns in transfer rights format.
*/
public static final int TRANSFER_RIGHTS = 4;

/**
Number of columns in the table model.
*/
private final static int __COLUMNS = 33;

/**
A reference to an open dmi object (for use in pulling out some lookup table
information).
*/
private HydroBaseDMI __dmi = null;

/**
Constructor.  This builds the Model for displaying the given transact results.
@param results the results that will be displayed in the table.
@param dmi a reference to the dmi object used to query for the results.
@throws Exception if an invalid results or dmi was passed in.
*/
public HydroBase_TableModel_Transact(List<HydroBase_Transact> results, HydroBaseDMI dmi, int type)
throws Exception {
	if (results == null) {
		throw new Exception ("Invalid results Vector passed to " 
			+ "HydroBase_TableModel_Transact constructor.");
	}
	if (dmi == null || (dmi.isOpen() == false)) {
		throw new Exception ("Invalid dmi (null or unopened) passed to"
			+ "HydroBase_TableModel_Transact constructor.");
	}
	__dmi = dmi;
	_rows = results.size();
	_data = results;
	_type = type;
}

/**
Returns the class of the data stored in a given column.
@param columnIndex the column for which to return the data class.
*/
public Class<?> getColumnClass (int columnIndex) {
	switch (_type) {
	case ASSOCIATED_RIGHTS:
		switch (columnIndex) {
			case 0:		return Integer.class;	// div
			case 1:		return Integer.class;	// wd
			case 2:		return Integer.class;	// id
			case 3:		return String.class;	// water right n
			case 4:		return String.class;	// water source
			case 5:		return String.class;	// location
			case 6:		return String.class;	// county
			case 7:		return String.class;	// adj date
			case 8:		return String.class;	// padj date
			case 9:		return String.class;	// apro date
			case 10:	return Double.class;	// admin no
			case 11:	return Integer.class;	// order no
			case 12:	return String.class;	// prior no
			case 13:	return String.class;	// adj type
			case 14:	return String.class;	// use type
			case 15:	return Double.class;	// rate amt
			case 16:	return Double.class;	// vol amt
			case 17:	return String.class;	// aband
			case 18:	return String.class;	// status
			case 19:	return String.class;	// case no
			case 20:	return String.class;	// last due dil
			case 21:	return String.class;	// action commnt
			case 22:	return String.class;	// action update
			case 23:	return String.class;	// assoc type
			case 24:	return Integer.class;	// assoc wd
			case 25:	return Integer.class;	// assoc id
			case 26:	return String.class;	// aug role
			case 27:	return Integer.class;	// plan wd
			case 28:	return Integer.class;	// plan id
			case 29:	return String.class;	// trans type
			case 30:	return Integer.class;	// tran wd
			case 31:	return Integer.class;	// tran id
			case 32:	return String.class;	// struct type
			default:	return String.class;
		}
	case AUG_PLANS:
		switch (columnIndex) {
			case 0:		return Integer.class;	// div
			case 1:		return Integer.class;	// wd
			case 2:		return Integer.class;	// id
			case 3:		return String.class;	// water right n
			case 4:		return String.class;	// water source
			case 5:		return Double.class;	// rate amt
			case 6:		return Double.class;	// vol amt
			case 7:		return String.class;	// aband
			case 8:		return String.class;	// status 
			case 9:		return String.class;	// adj date
			case 10:	return String.class;	// padj date
			case 11:	return String.class;	// apro date
			case 12:	return Double.class;	// admin no
			case 13:	return Integer.class;	// order no
			case 14:	return String.class;	// prior no
			case 15:	return String.class;	// adj type
			case 16:	return String.class;	// use type
			case 17:	return String.class;	// case no
			case 18:	return String.class;	// last due dil
			case 19:	return String.class;	// action commnt
			case 20:	return String.class;	// action update
			case 21:	return String.class;	// location
			case 22:	return String.class;	// county
			case 23:	return String.class;	// assoc type
			case 24:	return Integer.class;	// assoc wd
			case 25:	return Integer.class;	// assoc id
			case 26:	return String.class;	// aug role
			case 27:	return Integer.class;	// plan wd
			case 28:	return Integer.class;	// plan id
			case 29:	return String.class;	// trans type
			case 30:	return Integer.class;	// tran wd
			case 31:	return Integer.class;	// tran id
			case 32:	return String.class;	// struct type
			default:	return String.class;
		}
	case LEGAL:
		switch (columnIndex) {
			case 0:		return Integer.class;	// div
			case 1:		return Integer.class;	// wd
			case 2:		return Integer.class;	// id
			case 3:		return String.class;	// water right n
			case 4:		return String.class;	// water source
			case 5:		return String.class;	// assoc type
			case 6:		return Integer.class;	// assoc wd
			case 7:		return Integer.class;	// assoc id
			case 8:		return String.class;	// location
			case 9:		return String.class;	// county
			case 10:	return String.class;	// adj date
			case 11:	return String.class;	// padj date
			case 12:	return String.class;	// apro date
			case 13:	return Double.class;	// admin no
			case 14:	return Integer.class;	// order no
			case 15:	return String.class;	// prior no
			case 16:	return String.class;	// adj type
			case 17:	return String.class;	// use type
			case 18:	return Double.class;	// rate amt
			case 19:	return Double.class;	// vol amt
			case 20:	return String.class;	// aband
			case 21:	return String.class;	// status
			case 22:	return String.class;	// case no
			case 23:	return String.class;	// last due dil
			case 24:	return String.class;	// action commnt
			case 25:	return String.class;	// action update
			case 26:	return String.class;	// aug role
			case 27:	return Integer.class;	// plan wd
			case 28:	return Integer.class;	// plan id
			case 29:	return String.class;	// trans type
			case 30:	return Integer.class;	// tran wd
			case 31:	return Integer.class;	// tran id
			case 32:	return String.class;	// struct type
			default:	return String.class;
		}
	case SUMMARY:
		switch (columnIndex) {
			case 0:		return Integer.class;	// div
			case 1:		return Integer.class;	// wd 
			case 2:		return Integer.class;	// id
			case 3:		return String.class;	// water right n
			case 4:		return String.class;	// water source
			case 5:		return String.class;	// aug role
			case 6:		return Integer.class;	// plan wd
			case 7:		return Integer.class;	// plan id
			case 8:		return String.class;	// location
			case 9:		return String.class;	// county
			case 10:	return String.class;	// adj date
			case 11:	return String.class;	// padj date
			case 12:	return String.class;	// apro date
			case 13:	return Double.class;	// admin no
			case 14:	return Integer.class;	// order no
			case 15:	return String.class;	// prior no
			case 16:	return String.class;	// adj type
			case 17:	return String.class;	// use type
			case 18:	return Double.class;	// rate amt
			case 19:	return Double.class;	// vol amt
			case 20:	return String.class;	// aband
			case 21:	return String.class;	// status
			case 22:	return String.class;	// case no
			case 23:	return String.class;	// last due dil
			case 24:	return String.class;	// action commnt
			case 25:	return String.class;	// action update
			case 26:	return String.class;	// assoc type
			case 27:	return Integer.class;	// assoc wd
			case 28:	return Integer.class;	// assoc id
			case 29:	return String.class;	// trans type
			case 30:	return Integer.class;	// tran wd
			case 31:	return Integer.class;	// tran id
			case 32:	return String.class;	// struct type
			default:	return String.class;
		}
	case TRANSFER_RIGHTS:
		switch (columnIndex) {
			case 0:		return Integer.class;	// div
			case 1:		return Integer.class;	// wd
			case 2:		return Integer.class;	// id
			case 3:		return String.class;	// water right n
			case 4:		return String.class;	// water source
			case 5:		return String.class;	// trans type
			case 6:		return Integer.class;	// tran wd
			case 7:		return Integer.class;	// tran id
			case 8:		return String.class;	// location
			case 9:		return String.class;	// county
			case 10:	return String.class;	// adj date
			case 11:	return String.class;	// padj date
			case 12:	return String.class;	// apro date
			case 13:	return Double.class;	// admin no
			case 14:	return Integer.class;	// order no
			case 15:	return String.class;	// prior no
			case 16:	return String.class;	// adj type
			case 17:	return String.class;	// use type
			case 18:	return Double.class;	// rate amt
			case 19:	return Double.class;	// vol  amt
			case 20:	return String.class;	// aband
			case 21:	return String.class;	// status
			case 22:	return String.class;	// case no
			case 23:	return String.class;	// last due dil
			case 24:	return String.class;	// action commnt
			case 25:	return String.class;	// action update
			case 26:	return String.class;	// assoc type
			case 27:	return Integer.class;	// assoc wd
			case 28:	return Integer.class;	// assoc id
			case 29:	return String.class;	// aug role
			case 30:	return Integer.class;	// plan wd
			case 31:	return Integer.class;	// plan id
			case 32:	return String.class;	// struct type
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
	case ASSOCIATED_RIGHTS:
	switch (columnIndex) {
		case 0:		return "\n\nDIV";
		case 1:		return "\n\nWD";
		case 2:		return "\n\nID";
		case 3:		return "\n\nWATER RIGHT NAME";
		case 4:		return "\n\nWATER SOURCE";
		case 5:		return "\n\nLOCATION";
		case 6:		return "\n\nCOUNTY";
		case 7:		return "\nADJ\nDATE";
		case 8:		return "\nPADJ\nDATE";
		case 9:		return "\nAPPROPRIATION\nDATE";
		case 10:	return "\nADMINISTRATION\nNUMBER";
		case 11:	return "\nORDER\nNUMBER";
		case 12:	return "\nPRIOR\nNUMBER";
		case 13:	return "\nADJ\nTYPE";
		case 14:	return "\nUSE\nTYPE";
		case 15:	return "RATE\nAMOUNT\n(CFS)";
		case 16:	return "VOLUME\nAMOUNT\n(ACFT)";
		case 17: 	return "\n\nABAND";
		case 18:	return "\n\nSTATUS";
		case 19:	return "\nCASE\nNUMBER";
		case 20:	return "\nLAST DUE\nDILIGENCE";
		case 21:	return "\n\nACTION COMMENT";
		case 22:	return "\nACTION\nUPDATE";
		case 23:	return "\nASSOCIATED\nTYPE";
		case 24:	return "\nASSOCIATED\nWD";
		case 25:	return "\nASSOCIATED\nID";
		case 26:	return "\nAUG\nROLE";
		case 27:	return "\nPLAN\nWD";
		case 28:	return "\nPLAN\nID";
		case 29:	return "\nTRANS\nTYPE";
		case 30:	return "\nTRAN\nWD";
		case 31:	return "\nTRAN\nID";
		case 32:	return "\nSTRUCT\nTYPE";
		default:	return " ";
	}
	case AUG_PLANS:
	switch (columnIndex) {
		case 0:		return "\n\nDIV";
		case 1:		return "\n\nWD";
		case 2:		return "\n\nID";
		case 3:		return "\n\nWATER RIGHT NAME";
		case 4:		return "\n\nWATER SOURCE";
		case 5:		return "RATE\nAMOUNT\n(CFS)";
		case 6:		return "VOLUME\nAMOUNT\n(ACFT)";
		case 7: 	return "\n\nABAND";
		case 8:		return "\n\nSTATUS";
		case 9: 	return "\nADJ\nDATE";
		case 10:	return "\nPADJ\nDATE";
		case 11:	return "\nAPPROPRIATION\nDATE";
		case 12:	return "\nADMINISTRATION\nNUMBER";
		case 13:	return "\nORDER\nNUMBER";
		case 14:	return "\nPRIOR\nNUMBER";
		case 15:	return "\nADJ\nTYPE";
		case 16:	return "\nUSE\nTYPE";
		case 17:	return "\nCASE\nNUMBER";
		case 18:	return "\nLAST DUE\nDILIGENCE";
		case 19:	return "\n\nACTION COMMENT";
		case 20:	return "\nACTION\nUPDATE";
		case 21:	return "\n\nLOCATION";
		case 22:	return "\n\nCOUNTY";
		case 23:	return "\nASSOCIATED\nTYPE";
		case 24:	return "\nASSOCIATED\nWD";
		case 25:	return "\nASSOCIATED\nID";
		case 26:	return "\nAUG\nROLE";
		case 27:	return "\nPLAN\nWD";
		case 28:	return "\nPLAN\nID";
		case 29:	return "\nTRANS\nTYPE";
		case 30:	return "\nTRAN\nWD";
		case 31:	return "\nTRAN\nID";
		case 32:	return "\nSTRUCT\nTYPE";
		default:	return " ";
	}
	case LEGAL:
	switch (columnIndex) {
		case 0:		return "\n\nDIV";
		case 1:		return "\n\nWD";
		case 2:		return "\n\nID";
		case 3:		return "\n\nWATER RIGHT NAME";
		case 4:		return "\n\nWATER SOURCE";
		case 5:		return "\nASSOCIATED\nTYPE";
		case 6:		return "\nASSOCIATED\nWD";
		case 7:		return "\nASSOCIATED\nID";
		case 8:		return "\n\nLOCATION";
		case 9:		return "\n\nCOUNTY";
		case 10:	return "\nADJ\nDATE";
		case 11:	return "\nPADJ\nDATE";
		case 12:	return "\nAPPROPRIATION\nDATE";
		case 13:	return "\nADMINISTRATION\nNUMBER";
		case 14:	return "\nORDER\nNUMBER";
		case 15:	return "\nPRIOR\nNUMBER";
		case 16:	return "\nADJ\nTYPE";
		case 17:	return "\nUSE\nTYPE";
		case 18:	return "RATE\nAMOUNT\n(CFS)";
		case 19:	return "VOLUME\nAMOUNT\n(ACFT)";
		case 20: 	return "\n\nABAND";
		case 21:	return "\n\nSTATUS";
		case 22:	return "\nCASE\nNUMBER";
		case 23:	return "\nLAST DUE\nDILIGENCE";
		case 24:	return "\n\nACTION COMMENT";
		case 25:	return "\nACTION\nUPDATE";
		case 26:	return "\nAUG\nROLE";
		case 27:	return "\nPLAN\nWD";
		case 28:	return "\nPLAN\nID";
		case 29:	return "\nTRANS\nTYPE";
		case 30:	return "\nTRAN\nWD";
		case 31:	return "\nTRAN\nID";
		case 32:	return "\nSTRUCT\nTYPE";
		default:	return " ";
	}	
	case SUMMARY:
	switch (columnIndex) {
		case 0:		return "\n\nDIV";
		case 1:		return "\n\nWD";
		case 2:		return "\n\nID";
		case 3:		return "\n\nWATER RIGHT NAME";
		case 4:		return "\n\nWATER SOURCE";
		case 5:		return "\nAUG\nROLE";
		case 6:		return "\nPLAN\nWD";
		case 7:		return "\nPLAN\nID";
		case 8:		return "\n\nLOCATION";
		case 9:		return "\n\nCOUNTY";
		case 10:	return "\nADJ\nDATE";
		case 11:	return "\nPADJ\nDATE";
		case 12:	return "\nAPPROPRIATION\nDATE";
		case 13:	return "\nADMINISTRATION\nNUMBER";
		case 14:	return "\nORDER\nNUMBER";
		case 15:	return "\nPRIOR\nNUMBER";
		case 16:	return "\nADJ\nTYPE";
		case 17:	return "\nUSE\nTYPE";
		case 18:	return "RATE\nAMOUNT\n(CFS)";
		case 19:	return "VOLUME\nAMOUNT\n(ACFT)";
		case 20: 	return "\n\nABAND";
		case 21:	return "\n\nSTATUS";
		case 22:	return "\nCASE\nNUMBER";
		case 23:	return "\nLAST DUE\nDILIGENCE";
		case 24:	return "\n\nACTION COMMENT";
		case 25:	return "\nACTION\nUPDATE";
		case 26:	return "\nASSOCIATED\nTYPE";
		case 27:	return "\nASSOCIATED\nWD";
		case 28:	return "\nASSOCIATED\nID";
		case 29:	return "\nTRANS\nTYPE";
		case 30:	return "\nTRAN\nWD";
		case 31:	return "\nTRAN\nID";
		case 32:	return "\nSTRUCT\nTYPE";
		default:	return " ";
	}		
	case TRANSFER_RIGHTS:
	switch (columnIndex) {
		case 0:		return "\n\nDIV";
		case 1:		return "\n\nWD";
		case 2:		return "\n\nID";
		case 3:		return "\n\nWATER RIGHT NAME";
		case 4:		return "\n\nWATER SOURCE";
		case 5:		return "\nTRANS\nTYPE";
		case 6:		return "\nTRAN\nWD";
		case 7:		return "\nTRAN\nID";
		case 8:		return "\n\nLOCATION";
		case 9:		return "\n\nCOUNTY";
		case 10:	return "\nADJ\nDATE";
		case 11:	return "\nPADJ\nDATE";
		case 12:	return "\nAPPROPRIATION\nDATE";
		case 13:	return "\nADMINISTRATION\nNUMBER";
		case 14:	return "\nORDER\nNUMBER";
		case 15:	return "\nPRIOR\nNUMBER";
		case 16:	return "\nADJ\nTYPE";
		case 17:	return "\nUSE\nTYPE";
		case 18:	return "RATE\nAMOUNT\n(CFS)";
		case 19:	return "VOLUME\nAMOUNT\n(ACFT)";
		case 20: 	return "\n\nABAND";
		case 21:	return "\n\nSTATUS";
		case 22:	return "\nCASE\nNUMBER";
		case 23:	return "\nLAST DUE\nDILIGENCE";
		case 24:	return "\n\nACTION COMMENT";
		case 25:	return "\nACTION\nUPDATE";
		case 26:	return "\nASSOCIATED\nTYPE";
		case 27:	return "\nASSOCIATED\nWD";
		case 28:	return "\nASSOCIATED\nID";
		case 29:	return "\nAUG\nROLE";
		case 30:	return "\nPLAN\nWD";
		case 31:	return "\nPLAN\nID";
		case 32:	return "\nSTRUCT\nTYPE";
		default:	return " ";
	}			
	}	
	return "";
}

/**
Returns the text to be assigned to worksheet tooltips.
@return a String array of tool tips.
*/
public String[] getColumnToolTips() {
	String[] tips = new String[__COLUMNS];

	// REVISIT SAM 2005-07-06 Need to make variable passed in...
	if ( _type == ASSOCIATED_RIGHTS ) {
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
		tips[8] = "Prior adjudiation date.";
		tips[9] = "Appropriation date.";
		tips[10] = "Administration number.";
		tips[11] = "Order number.";
		tips[12] = "District Court's old priority number.";
		tips[13] = "Adjudication type.";
		tips[14] = "<HTML>Use type:<BR> " +
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
		tips[15] =
			"The amount (CFS) as defined by a Water Court action.";
		tips[16] =
			"The amount (ACFT) as defined by a Water Court action.";
		tips[17] =
		"Describes the amount as being abandoned, AB; if applicable.";
		tips[18] = "The status of the amount, either Absolute (A), " +
			"Conditional (C), or Conditional Made Absolute (CA).";
		tips[19] = "The case number of the Water Court action.";
		tips[20] = "Case number of the last due diligence.";
		tips[21] = "This comment describes any issues worth noting " +
			"for the particular water right action.";
		tips[22] = "The date the row was inserted/modified.";
		tips[23] = "Describes the amount as being associated with " +
			"another right: alternate point (AP) or exchange (EX);"+
			" if applicable.";
		tips[24] = "The WD of the associated structure.";
		tips[25] = "The ID of the associated structure.";
		tips[26] = "<HTML>Describes the amount as related to an " +
			"augmentation plan,<BR>whereby the structure is " +
			"either Augmenting (A) or Replacing (R);" +
			" if applicable.</HTML>";
		tips[27] = "The water district of the plan.";
		tips[28] = "The ID of the plan.";
		tips[29] = "Describes the amount as being a transfer: " +
		"Transfer To (TT), or Transfer From (TF), if applicable.";
		tips[30] = "Transfer water district.";
		tips[31] = "Transfer structure ID.";
		tips[32] = "<HTML>Structure type:<BR>"+
			"0 = Other<BR>" +
			"1 = Ditch<BR>" +
			"2 = Well<BR>" +
			"3 = Reservoir<BR>" +
			"4 = Spring<BR>" +
			"5 = Seep<BR>" +
			"6 = Mine<BR>" +
			"7 = Pipeline<BR>" +
			"8 = Pump<BR>" +
			"9 = Power Plant<BR>" +
			"H = Headgate<BR>" +
			"JD = Jurisdictional Dam<BR>" +
			"MFR = Minimum Flow Reach<BR>" +
			"NJD = Non-Jurisdictional Dam<BR>" +
			"R = Reservoir<BR>" +
			"W = Well or Well field<BR>" +
			"WP = Well Permit<BR>" +
			"WU = Unpermitted Well</HTML>";
	}
	else if ( _type == AUG_PLANS ) {
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
		tips[5] = 
			"The amount (CFS) as defined by a Water Court action.";
		tips[6] =
			"The amount (ACFT) as defined by a Water Court action.";
		tips[7] =
		"Describes the amount as being abandoned, AB; if applicable.";
		tips[8] = "The status of the amount, either Absolute (A), " +
			"Conditional (C), or Conditional Made Absolute (CA).";
		tips[9] = "Adjudication date.";
		tips[10] = "Prior adjudiation date.";
		tips[11] = "Appropriation date.";
		tips[12] = "Administration number.";
		tips[13] = "Order number.";
		tips[14] = "District Court's old priority number.";
		tips[15] = "Adjudication type.";
		tips[16] = "<HTML>Use type:<BR> " +
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
		tips[17] = "The case number of the Water Court action.";
		tips[18] = "Case number of the last due diligence.";
		tips[19] = "This comment describes any issues worth noting " +
			"for the particular water right action.";
		tips[20] = "The date the row was inserted/modified.";
		tips[21] = "Legal location.";
		tips[22] = "County name.";
		tips[23] = "Describes the amount as being associated with " +
			"another right: alternate point (AP) or exchange (EX);"+
			" if applicable.";
		tips[24] = "The WD of the associated structure.";
		tips[25] = "The ID of the associated structure.";
		tips[26] = "<HTML>Describes the amount as related to an " +
			"augmentation plan,<BR>whereby the structure is " +
			"either Augmenting (A) or Replacing (R);" +
			" if applicable.</HTML>";
		tips[27] = "The water district of the plan.";
		tips[28] = "The ID of the plan.";
		tips[29] = "Describes the amount as being a transfer: " +
		"Transfer To (TT), or Transfer From (TF), if applicable.";
		tips[30] = "Transfer water district.";
		tips[31] = "Transfer structure ID.";
		tips[32] = "<HTML>Structure type:<BR>"+
			"0 = Other<BR>" +
			"1 = Ditch<BR>" +
			"2 = Well<BR>" +
			"3 = Reservoir<BR>" +
			"4 = Spring<BR>" +
			"5 = Seep<BR>" +
			"6 = Mine<BR>" +
			"7 = Pipeline<BR>" +
			"8 = Pump<BR>" +
			"9 = Power Plant<BR>" +
			"H = Headgate<BR>" +
			"JD = Jurisdictional Dam<BR>" +
			"MFR = Minimum Flow Reach<BR>" +
			"NJD = Non-Jurisdictional Dam<BR>" +
			"R = Reservoir<BR>" +
			"W = Well or Well field<BR>" +
			"WP = Well Permit<BR>" +
			"WU = Unpermitted Well</HTML>";
	}
	else if ( _type == LEGAL ) {
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
		tips[5] = "Describes the amount as being associated with " +
			"another right: alternate point (AP) or exchange (EX);"+
			" if applicable.";
		tips[6] = "The WD of the associated structure.";
		tips[7] = "The ID of the associated structure.";
		tips[8] = "Legal location.";
		tips[9] = "County name.";
		tips[10] = "Adjudication date.";
		tips[11] = "Prior adjudiation date.";
		tips[12] = "Appropriation date.";
		tips[13] = "Administration number.";
		tips[14] = "Order number.";
		tips[15] = "District Court's old priority number.";
		tips[16] = "Adjudication type.";
		tips[17] = "<HTML>Use type:<BR> " +
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
		tips[18] =
			"The amount (CFS) as defined by a Water Court action.";
		tips[19] =
			"The amount (ACFT) as defined by a Water Court action.";
		tips[20] =
		"Describes the amount as being abandoned, AB; if applicable.";
		tips[21] = "The status of the amount, either Absolute (A), " +
			"Conditional (C), or Conditional Made Absolute (CA).";
		tips[22] = "The case number of the Water Court action.";
		tips[23] = "Case number of the last due diligence.";
		tips[24] = "This comment describes any issues worth noting " +
			"for the particular water right action.";
		tips[25] = "The date the row was inserted/modified.";
		tips[26] = "<HTML>Describes the amount as related to an " +
			"augmentation plan,<BR>whereby the structure is " +
			"either Augmenting (A) or Replacing (R);" +
			" if applicable.</HTML>";
		tips[27] = "The water district of the plan.";
		tips[28] = "The ID of the plan.";
		tips[29] = "Describes the amount as being a transfer: " +
		"Transfer To (TT), or Transfer From (TF), if applicable.";
		tips[30] = "Transfer water district.";
		tips[31] = "Transfer structure ID.";
		tips[32] = "<HTML>Structure type:<BR>"+
			"0 = Other<BR>" +
			"1 = Ditch<BR>" +
			"2 = Well<BR>" +
			"3 = Reservoir<BR>" +
			"4 = Spring<BR>" +
			"5 = Seep<BR>" +
			"6 = Mine<BR>" +
			"7 = Pipeline<BR>" +
			"8 = Pump<BR>" +
			"9 = Power Plant<BR>" +
			"H = Headgate<BR>" +
			"JD = Jurisdictional Dam<BR>" +
			"MFR = Minimum Flow Reach<BR>" +
			"NJD = Non-Jurisdictional Dam<BR>" +
			"R = Reservoir<BR>" +
			"W = Well or Well field<BR>" +
			"WP = Well Permit<BR>" +
			"WU = Unpermitted Well</HTML>";
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
		tips[5] = "<HTML>Describes the amount as related to an " +
			"augmentation plan,<BR>whereby the structure is " +
			"either Augmenting (A) or Replacing (R);" +
			" if applicable.</HTML>";
		tips[6] = "The water district of the plan.";
		tips[7] = "The ID of the plan.";
		tips[8] = "Legal location.";
		tips[9] = "County name.";
		tips[10] = "Adjudication date.";
		tips[11] = "Prior adjudiation date.";
		tips[12] = "Appropriation date.";
		tips[13] = "Administration number.";
		tips[14] = "Order number.";
		tips[15] = "District Court's old priority number.";
		tips[16] = "Adjudication type.";
		tips[17] = "<HTML>Use type:<BR> " +
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
		tips[18] =
			"The amount (CFS) as defined by a Water Court action.";
		tips[19] =
			"The amount (ACFT) as defined by a Water Court action.";
		tips[20] =
		"Describes the amount as being abandoned, AB; if applicable.";
		tips[21] = "The status of the amount, either Absolute (A), " +
			"Conditional (C), or Conditional Made Absolute (CA).";
		tips[22] = "The case number of the Water Court action.";
		tips[23] = "Case number of the last due diligence.";
		tips[24] = "This comment describes any issues worth noting " +
			"for the particular water right action.";
		tips[25] = "The date the row was inserted/modified.";
		tips[26] = "Describes the amount as being associated with " +
			"another right: alternate point (AP) or exchange (EX);"+
			" if applicable.";
		tips[27] = "The WD of the associated structure.";
		tips[28] = "The ID of the associated structure.";
		tips[29] = "Describes the amount as being a transfer: " +
		"Transfer To (TT), or Transfer From (TF), if applicable.";
		tips[30] = "Transfer water district.";
		tips[31] = "Transfer structure ID.";
		tips[32] = "<HTML>Structure type:<BR>"+
			"0 = Other<BR>" +
			"1 = Ditch<BR>" +
			"2 = Well<BR>" +
			"3 = Reservoir<BR>" +
			"4 = Spring<BR>" +
			"5 = Seep<BR>" +
			"6 = Mine<BR>" +
			"7 = Pipeline<BR>" +
			"8 = Pump<BR>" +
			"9 = Power Plant<BR>" +
			"H = Headgate<BR>" +
			"JD = Jurisdictional Dam<BR>" +
			"MFR = Minimum Flow Reach<BR>" +
			"NJD = Non-Jurisdictional Dam<BR>" +
			"R = Reservoir<BR>" +
			"W = Well or Well field<BR>" +
			"WP = Well Permit<BR>" +
			"WU = Unpermitted Well</HTML>";
	}
	else if ( _type == TRANSFER_RIGHTS ) {
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
		tips[5] = "Describes the amount as being a transfer: " +
		"Transfer To (TT), or Transfer From (TF), if applicable.";
		tips[6] = "Transfer water district.";
		tips[7] = "Transfer structure ID.";
		tips[8] = "Legal location.";
		tips[9] = "County name.";
		tips[10] = "Adjudication date.";
		tips[11] = "Prior adjudiation date.";
		tips[12] = "Appropriation date.";
		tips[13] = "Administration number.";
		tips[14] = "Order number.";
		tips[15] = "District Court's old priority number.";
		tips[16] = "Adjudication type.";
		tips[17] = "<HTML>Use type:<BR> " +
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
		tips[18] =
			"The amount (CFS) as defined by a Water Court action.";
		tips[19] =
			"The amount (ACFT) as defined by a Water Court action.";
		tips[20] =
		"Describes the amount as being abandoned, AB; if applicable.";
		tips[21] = "The status of the amount, either Absolute (A), " +
			"Conditional (C), or Conditional Made Absolute (CA).";
		tips[22] = "The case number of the Water Court action.";
		tips[23] = "Case number of the last due diligence.";
		tips[24] = "This comment describes any issues worth noting " +
			"for the particular water right action.";
		tips[25] = "The date the row was inserted/modified.";
		tips[26] = "Describes the amount as being associated with " +
			"another right: alternate point (AP) or exchange (EX);"+
			" if applicable.";
		tips[27] = "The WD of the associated structure.";
		tips[28] = "The ID of the associated structure.";
		tips[29] = "<HTML>Describes the amount as related to an " +
			"augmentation plan,<BR>whereby the structure is " +
			"either Augmenting (A) or Replacing (R);" +
			" if applicable.</HTML>";
		tips[30] = "The water district of the plan.";
		tips[31] = "The ID of the plan.";
		tips[32] = "<HTML>Structure type:<BR>"+
			"0 = Other<BR>" +
			"1 = Ditch<BR>" +
			"2 = Well<BR>" +
			"3 = Reservoir<BR>" +
			"4 = Spring<BR>" +
			"5 = Seep<BR>" +
			"6 = Mine<BR>" +
			"7 = Pipeline<BR>" +
			"8 = Pump<BR>" +
			"9 = Power Plant<BR>" +
			"H = Headgate<BR>" +
			"JD = Jurisdictional Dam<BR>" +
			"MFR = Minimum Flow Reach<BR>" +
			"NJD = Non-Jurisdictional Dam<BR>" +
			"R = Reservoir<BR>" +
			"W = Well or Well field<BR>" +
			"WP = Well Permit<BR>" +
			"WU = Unpermitted Well</HTML>";
	}
	return tips;
}

public int[] getColumnWidths() {
	return getColumnWidths(ASSOCIATED_RIGHTS);
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
	case ASSOCIATED_RIGHTS:
		widths[i++] = 3;		// div
		widths[i++] = 2;		// wd
		widths[i++] = 3;		// id
		widths[i++] = 25;		// water right name
		widths[i++] = 20;		// water source
		widths[i++] = 16;		// location
		widths[i++] = 10;		// county
		widths[i++] = 6;		// adj date
		widths[i++] = 6;		// padj date
		widths[i++] = 11;		// apro date		
		widths[i++] = 11;		// admin no
		widths[i++] = 5;		// order no
		widths[i++] = 5;		// prior no
		widths[i++] = 3;		// adj type
		widths[i++] = 12;		// use type
		widths[i++] = 6;		// rate amt (CFS)
		widths[i++] = 6;		// vol amt (ACFT)
		widths[i++] = 5;		// aband
		widths[i++] = 6;		// status
		widths[i++] = 7;		// case no
		widths[i++] = 7;		// last due dil	
		widths[i++] = 40;		// action comment
		widths[i++] = 8;		// action update
		widths[i++] = 9;		// assoc type
		widths[i++] = 9;		// assoc wd
		widths[i++] = 9;		// assoc id
		widths[i++] = 3;		// aug role
		widths[i++] = 3;		// plan wd
		widths[i++] = 3;		// plan id
		widths[i++] = 4;		// trans type
		widths[i++] = 3;		// tran wd
		widths[i++] = 3;		// tran id
		widths[i++] = 5;		// struct type
		break;
	case AUG_PLANS:
		widths[i++] = 3;		// div
		widths[i++] = 2;		// wd
		widths[i++] = 3;		// id
		widths[i++] = 25;		// water right name
		widths[i++] = 20;		// water source
		widths[i++] = 6;		// rate amt (CFS)
		widths[i++] = 6;		// vol amt (ACFT)
		widths[i++] = 5;		// aband
		widths[i++] = 6;		// status
		widths[i++] = 6;		// adj date
		widths[i++] = 6;		// padj date
		widths[i++] = 11;		// apro date		
		widths[i++] = 11;		// admin no
		widths[i++] = 5;		// order no
		widths[i++] = 5;		// prior no
		widths[i++] = 3;		// adj type
		widths[i++] = 12;		// use type
		widths[i++] = 7;		// case no
		widths[i++] = 7;		// last due dil	
		widths[i++] = 40;		// action comment
		widths[i++] = 8;		// action update
		widths[i++] = 16;		// location
		widths[i++] = 10;		// county
		widths[i++] = 9;		// assoc type
		widths[i++] = 9;		// assoc wd
		widths[i++] = 9;		// assoc id
		widths[i++] = 3;		// aug role
		widths[i++] = 3;		// plan wd
		widths[i++] = 3;		// plan id
		widths[i++] = 4;		// trans type
		widths[i++] = 3;		// tran wd
		widths[i++] = 3;		// tran id
		widths[i++] = 5;		// struct type
		break;	
	case LEGAL:
		widths[i++] = 3;		// div
		widths[i++] = 2;		// wd
		widths[i++] = 3;		// id
		widths[i++] = 25;		// water right name
		widths[i++] = 20;		// water source	
		widths[i++] = 9;		// assoc type
		widths[i++] = 9;		// assoc wd
		widths[i++] = 9;		// assoc id
		widths[i++] = 16;		// location
		widths[i++] = 10;		// county
		widths[i++] = 6;		// adj date
		widths[i++] = 6;		// padj date
		widths[i++] = 11;		// apro date		
		widths[i++] = 11;		// admin no
		widths[i++] = 5;		// order no
		widths[i++] = 5;		// prior no
		widths[i++] = 3;		// adj type
		widths[i++] = 12;		// use type
		widths[i++] = 6;		// rate amt (CFS)
		widths[i++] = 6;		// vol amt (ACFT)
		widths[i++] = 5;		// aband
		widths[i++] = 6;		// status
		widths[i++] = 7;		// case no
		widths[i++] = 7;		// last due dil	
		widths[i++] = 40;		// action comment
		widths[i++] = 8;		// action update
		widths[i++] = 3;		// aug role		
		widths[i++] = 3;		// plan wd
		widths[i++] = 3;		// plan id
		widths[i++] = 4;		// trans type
		widths[i++] = 3;		// tran wd
		widths[i++] = 3;		// tran id
		widths[i++] = 5;		// struct type
		break;		
	case SUMMARY:
		widths[i++] = 3;		// div
		widths[i++] = 2;		// wd
		widths[i++] = 3;		// id
		widths[i++] = 25;		// water right name
		widths[i++] = 20;		// water source	
		widths[i++] = 3;		// aug role		
		widths[i++] = 3;		// plan wd
		widths[i++] = 3;		// plan id
		widths[i++] = 16;		// location
		widths[i++] = 10;		// county
		widths[i++] = 6;		// adj date
		widths[i++] = 6;		// padj date
		widths[i++] = 11;		// apro date		
		widths[i++] = 11;		// admin no
		widths[i++] = 5;		// order no
		widths[i++] = 5;		// prior no
		widths[i++] = 3;		// adj type
		widths[i++] = 12;		// use type
		widths[i++] = 6;		// rate amt (CFS)
		widths[i++] = 6;		// vol amt (ACFT)
		widths[i++] = 5;		// aband
		widths[i++] = 6;		// status
		widths[i++] = 7;		// case no
		widths[i++] = 7;		// last due dil	
		widths[i++] = 40;		// action comment
		widths[i++] = 8;		// action update
		widths[i++] = 9;		// assoc type
		widths[i++] = 9;		// assoc wd
		widths[i++] = 9;		// assoc id
		widths[i++] = 4;		// trans type
		widths[i++] = 3;		// tran wd
		widths[i++] = 3;		// tran id
		widths[i++] = 5;		// struct type
		break;		
	case TRANSFER_RIGHTS:
		widths[i++] = 3;		// div
		widths[i++] = 2;		// wd
		widths[i++] = 3;		// id
		widths[i++] = 25;		// water right name
		widths[i++] = 20;		// water source	
		widths[i++] = 4;		// trans type
		widths[i++] = 3;		// tran wd
		widths[i++] = 3;		// tran id
		widths[i++] = 16;		// location
		widths[i++] = 10;		// county
		widths[i++] = 6;		// adj date
		widths[i++] = 6;		// padj date
		widths[i++] = 11;		// apro date		
		widths[i++] = 11;		// admin no
		widths[i++] = 5;		// order no
		widths[i++] = 5;		// prior no
		widths[i++] = 3;		// adj type
		widths[i++] = 12;		// use type
		widths[i++] = 6;		// rate amt (CFS)
		widths[i++] = 6;		// vol amt (ACFT)
		widths[i++] = 5;		// aband
		widths[i++] = 6;		// status
		widths[i++] = 7;		// case no
		widths[i++] = 7;		// last due dil	
		widths[i++] = 40;		// action comment
		widths[i++] = 8;		// action update
		widths[i++] = 9;		// assoc type
		widths[i++] = 9;		// assoc wd
		widths[i++] = 9;		// assoc id
		widths[i++] = 3;		// aug role		
		widths[i++] = 3;		// plan wd
		widths[i++] = 3;		// plan id
		widths[i++] = 5;		// struct type
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
public String getFormat(int column, int tableFormat) {
	switch (tableFormat) {
	case ASSOCIATED_RIGHTS:
	switch (column) {
		case 0:		return "%8d";		// div
		case 1:		return "%8d";		// wd
		case 2:		return "%8d";		// id
		case 3:		return "%-40s";		// water right name
		case 4:		return "%-40s";		// water source
		case 5:		return "%-30.30s";	// location
		case 6:		return "%-20s";		// county
		case 7:		return "%-10s";		// adj date
		case 8:		return "%-10s";		// padj date
		case 9:		return "%-10s";		// apro date
		case 10:	return "%11.5f";	// admin no
		case 11:	return "%8d";		// order no
		case 12:	return "%-9s";		// prior no
		case 13:	return "%-1s";		// adj type
		case 14:	return "%-30s";		// use type
		case 15:	return "%12.4f";	// rate amt
		case 16:	return "%12.4f";	// vol amt
		case 17:	return "%-2s";		// aband
		case 18:	return "%-2s";		// status
		case 19:	return "%-9s";		// case no
		case 20:	return "%-9s";		// last due dil
		case 21:	return "%-200s";	// action comment
		case 22:	return "%-10s";		// action update
		case 23:	return "%-2s";		// assoc type
		case 24:	return "%8d";		// assoc wd
		case 25:	return "%8d";		// assoc id
		case 26:	return "%-1s";		// aug role
		case 27:	return "%8d";		// plan wd
		case 28:	return "%8d";		// plan id
		case 29:	return "%-2s";		// trans type
		case 30:	return "%8d";		// tran wd
		case 31:	return "%8d";		// tran id
		case 32:	return "%-10s";		// struct type
		default:	return "";
	}
	case AUG_PLANS:
	switch (column) {
		case 0:		return "%8d";		// div
		case 1:		return "%8d";		// wd
		case 2:		return "%8d";		// id
		case 3:		return "%-40s";		// water right name
		case 4:		return "%-40s";		// water source
		case 5:		return "%12.4f";	// rate amt
		case 6:		return "%12.4f";	// vol amt
		case 7:		return "%-2s";		// aband
		case 8:		return "%-2s";		// status
		case 9: 	return "%-10s";		// adj date
		case 10:	return "%-10s";		// padj date
		case 11:	return "%-10s";		// apro date
		case 12:	return "%11.5f";	// admin no
		case 13:	return "%8d";		// order no
		case 14:	return "%-9s";		// prior no
		case 15:	return "%-1s";		// adj type
		case 16:	return "%-30s";		// use type
		case 17:	return "%-9s";		// case no
		case 18:	return "%-9s";		// last due dil
		case 19:	return "%-200s";	// action comment
		case 20:	return "%-10s";		// action update
		case 21:	return "%-30.30s";	// location
		case 22:	return "%-20s";		// county
		case 23:	return "%-2s";		// assoc type
		case 24:	return "%8d";		// assoc wd
		case 25:	return "%8d";		// assoc id
		case 26:	return "%-1s";		// aug role
		case 27:	return "%8d";		// plan wd
		case 28:	return "%8d";		// plan id
		case 29:	return "%-2s";		// trans type
		case 30:	return "%8d";		// tran wd
		case 31:	return "%8d";		// tran id
		case 32:	return "%-10s";		// struct type
		default:	return "";
	}
	case LEGAL:
	switch (column) {
		case 0:		return "%8d";		// div
		case 1:		return "%8d";		// wd 
		case 2:		return "%8d";		// id
		case 3:		return "%-40s";		// water right name
		case 4:		return "%-40s";		// water source
		case 5:		return "%-2s";		// assoc type
		case 6:		return "%8d";		// assoc wd
		case 7:		return "%8d";		// assoc id
		case 8:		return "%-30.30s";	// location
		case 9:		return "%-20s";		// county
		case 10:	return "%-10s";		// adj date
		case 11:	return "%-10s";		// padj date
		case 12:	return "%-10s";		// apro date
		case 13:	return "%11.5f";	// admin no
		case 14:	return "%8d";		// order no
		case 15:	return "%-9s";		// prior no
		case 16:	return "%-1s";		// adj type
		case 17:	return "%-30s";		// use type
		case 18:	return "%12.4f";	// rate amt
		case 19:	return "%12.4f";	// vol amt
		case 20:	return "%-2s";		// aband
		case 21:	return "%-2s";		// status
		case 22:	return "%-9s";		// case no
		case 23:	return "%-9s";		// last due dil
		case 24:	return "%-200s";	// action comment
		case 25:	return "%-10s";		// action update
		case 26:	return "%-1s";		// aug role
		case 27:	return "%8d";		// plan wd
		case 28:	return "%8d";		// plan id
		case 29:	return "%-2s";		// trans type
		case 30:	return "%8d";		// tran wd
		case 31:	return "%8d";		// tran id
		case 32:	return "%-10s";		// struct type
		default:	return "";
	}
	case SUMMARY:
	switch (column) {
		case 0:		return "%8d";		// div
		case 1:		return "%8d";		// wd
		case 2:		return "%8d";		// id
		case 3:		return "%-40s";		// water right name
		case 4:		return "%-40s";		// water source
		case 5:		return "%-1s";		// aug role
		case 6:		return "%8d";		// plan wd
		case 7:		return "%8d";		// plan id
		case 8:		return "%-30.30s";	// location
		case 9:		return "%-20s";		// county
		case 10:	return "%-10s";		// adj date
		case 11:	return "%-10s";		// padj date
		case 12:	return "%-10s";		// apro date
		case 13:	return "%11.5f";	// admin no
		case 14:	return "%8d";		// order no
		case 15:	return "%-9s";		// prior no
		case 16:	return "%-1s";		// adj type
		case 17:	return "%-30s";		// use type
		case 18:	return "%12.4f";	// rate amt
		case 19:	return "%12.4f";	// vol amt
		case 20:	return "%-2s";		// aband
		case 21:	return "%-2s";		// status
		case 22:	return "%-9s";		// case no
		case 23:	return "%-9s";		// last due dil
		case 24:	return "%-200s";	// action comment
		case 25:	return "%-10s";		// action update
		case 26:	return "%-2s";		// assoc type
		case 27:	return "%8d";		// assoc wd
		case 28:	return "%8d";		// assoc id
		case 29:	return "%-2s";		// trans type
		case 30:	return "%8d";		// tran wd
		case 31:	return "%8d";		// tran id
		case 32:	return "%-10s";		// struct type
		default:	return "";
	}
	case TRANSFER_RIGHTS:
	switch (column) {
		case 0:		return "%8d";		// div
		case 1:		return "%8d";		// wd
		case 2:		return "%8d";		// id
		case 3:		return "%-40s";		// water right name
		case 4:		return "%-40s";		// water source
		case 5:		return "%-2s";		// trans type
		case 6:		return "%8d";		// tran wd
		case 7:		return "%8d";		// tran id
		case 8:		return "%-30.30s";	// location
		case 9:		return "%-20s";		// county
		case 10:	return "%-10s";		// adj date
		case 11:	return "%-10s";		// padj date
		case 12:	return "%-10s";		// apro date
		case 13:	return "%11.5f";	// admin no
		case 14:	return "%8d";		// order no
		case 15:	return "%-9s";		// prior no
		case 16:	return "%-1s";		// adj type
		case 17:	return "%-30s";		// use type
		case 18:	return "%12.4f";	// rate amt
		case 19:	return "%12.4f";	// vol amt
		case 20:	return "%-2s";		// aband
		case 21:	return "%-2s";		// status
		case 22:	return "%-9s";		// case no
		case 23:	return "%-9s";		// last due dil
		case 24:	return "%-200s";	// action comment
		case 25:	return "%-10s";		// action update
		case 26:	return "%-2s";		// assoc type
		case 27:	return "%8d";		// assoc wd
		case 28:	return "%8d";		// assoc id
		case 29:	return "%-1s";		// aug role
		case 30:	return "%8d";		// plan wd
		case 31:	return "%8d";		// plan id
		case 32:	return "%-10s";		// struct type
		default:	return "";
	}	
	}
	return "%-13s";
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

	HydroBase_Transact r = _data.get(row);
	switch (_type) {
	case ASSOCIATED_RIGHTS:
	switch (col) {
		case 0:		return Integer.valueOf(r.getDiv());
		case 1:		return Integer.valueOf(r.getWD());		
		case 2:		return Integer.valueOf(r.getID());
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
		case 10:	return Double.valueOf(r.getAdmin_no());
		case 11:	return Integer.valueOf(r.getOrder_no());
		case 12:	return r.getPrior_no();
		case 13:	return r.getAdj_type();
		case 14:	return r.getUse();
		case 15:	return Double.valueOf(r.getRate_amt());
		case 16:	return Double.valueOf(r.getVol_amt());
		case 17:	return r.getAband();
		case 18:	return r.getStatus_type();
		case 19:	return r.getCase_no();
		case 20:	return r.getLast_due_dil();
		case 21:	return r.getAction_comment();
		case 22:	return parseDate(r.getAction_update());
		case 23:	return r.getAssoc_type();
		case 24:	return Integer.valueOf(r.getAssoc_wd());
		case 25:	return Integer.valueOf(r.getAssoc_id());
		case 26:	return r.getAug_role();
		case 27:	return Integer.valueOf(r.getPlan_wd());
		case 28:	return Integer.valueOf(r.getPlan_id());
		case 29:	return r.getTransfer_type();
		case 30:	return Integer.valueOf(r.getTran_wd());
		case 31:	return Integer.valueOf(r.getTran_id());
		case 32:	return r.getStrtype();
		default:	return "";
	}
	case AUG_PLANS:
	switch (col) {
		case 0:		return Integer.valueOf(r.getDiv());
		case 1:		return Integer.valueOf(r.getWD());		
		case 2:		return Integer.valueOf(r.getID());
		case 3:		return r.getWr_name();
		case 4:		return r.getWd_stream_name();
		case 5:		return Double.valueOf(r.getRate_amt());
		case 6:		return Double.valueOf(r.getVol_amt());
		case 7:		return r.getAband();
		case 8:		return r.getStatus_type();
		case 9: 	return parseDate(r.getAdj_date());
		case 10:	return parseDate(r.getPadj_date());
		case 11:	return parseDate(r.getApro_date());
		case 12:	return Double.valueOf(r.getAdmin_no());
		case 13:	return Integer.valueOf(r.getOrder_no());
		case 14:	return r.getPrior_no();
		case 15:	return r.getAdj_type();
		case 16:	return r.getUse();
		case 17:	return r.getCase_no();
		case 18:	return r.getLast_due_dil();
		case 19:	return r.getAction_comment();
		case 20:	return parseDate(r.getAction_update());
		case 21:
			return HydroBase_Util.buildLocation(
				r.getPM(), r.getTS(), r.getTdir(),
				r.getRng(), r.getRdir(), r.getSec(),
				r.getSeca(), r.getQ160(), r.getQ40(),
				r.getQ10());			
		case 22:	return __dmi.lookupCountyName(r.getCty());
		case 23:	return r.getAssoc_type();
		case 24:	return Integer.valueOf(r.getAssoc_wd());
		case 25:	return Integer.valueOf(r.getAssoc_id());
		case 26:	return r.getAug_role();
		case 27:	return Integer.valueOf(r.getPlan_wd());
		case 28:	return Integer.valueOf(r.getPlan_id());
		case 29:	return r.getTransfer_type();
		case 30:	return Integer.valueOf(r.getTran_wd());
		case 31:	return Integer.valueOf(r.getTran_id());
		case 32:	return r.getStrtype();
		default:	return "";
	}
	case LEGAL:
	switch (col) {
		case 0:		return Integer.valueOf(r.getDiv());
		case 1:		return Integer.valueOf(r.getWD());		
		case 2:		return Integer.valueOf(r.getID());
		case 3:		return r.getWr_name();
		case 4:		return r.getWd_stream_name();
		case 5:		return r.getAssoc_type();
		case 6:		return Integer.valueOf(r.getAssoc_wd());
		case 7:		return Integer.valueOf(r.getAssoc_id());
		case 8:
			return HydroBase_Util.buildLocation(
				r.getPM(), r.getTS(), r.getTdir(),
				r.getRng(), r.getRdir(), r.getSec(),
				r.getSeca(), r.getQ160(), r.getQ40(),
				r.getQ10());			
		case 9:		return __dmi.lookupCountyName(r.getCty());
		case 10:	return parseDate(r.getAdj_date());
		case 11:	return parseDate(r.getPadj_date());
		case 12:	return parseDate(r.getApro_date());
		case 13:	return Double.valueOf(r.getAdmin_no());
		case 14:	return Integer.valueOf(r.getOrder_no());
		case 15:	return r.getPrior_no();
		case 16:	return r.getAdj_type();
		case 17:	return r.getUse();
		case 18:	return Double.valueOf(r.getRate_amt());
		case 19:	return Double.valueOf(r.getVol_amt());
		case 20:	return r.getAband();
		case 21:	return r.getStatus_type();
		case 22:	return r.getCase_no();
		case 23:	return r.getLast_due_dil();
		case 24:	return r.getAction_comment();
		case 25:	return parseDate(r.getAction_update());
		case 26:	return r.getAug_role();
		case 27:	return Integer.valueOf(r.getPlan_wd());
		case 28:	return Integer.valueOf(r.getPlan_id());
		case 29:	return r.getTransfer_type();
		case 30:	return Integer.valueOf(r.getTran_wd());
		case 31:	return Integer.valueOf(r.getTran_id());
		case 32:	return r.getStrtype();
		default:	return "";
	}
	case SUMMARY:
	switch (col) {
		case 0:		return Integer.valueOf(r.getDiv());
		case 1:		return Integer.valueOf(r.getWD());		
		case 2:		return Integer.valueOf(r.getID());
		case 3:		return r.getWr_name();
		case 4:		return r.getWd_stream_name();
		case 5:		return r.getAug_role();
		case 6:		return Integer.valueOf(r.getPlan_wd());
		case 7:		return Integer.valueOf(r.getPlan_id());
		case 8:		
			return HydroBase_Util.buildLocation(
				r.getPM(), r.getTS(), r.getTdir(),
				r.getRng(), r.getRdir(), r.getSec(),
				r.getSeca(), r.getQ160(), r.getQ40(),
				r.getQ10());			
		case 9:		return __dmi.lookupCountyName(r.getCty());
		case 10:	return parseDate(r.getAdj_date());
		case 11:	return parseDate(r.getPadj_date());
		case 12:	return parseDate(r.getApro_date());
		case 13:	return Double.valueOf(r.getAdmin_no());
		case 14:	return Integer.valueOf(r.getOrder_no());
		case 15:	return r.getPrior_no();
		case 16:	return r.getAdj_type();
		case 17:	return r.getUse();		
		case 18:	return Double.valueOf(r.getRate_amt());
		case 19:	return Double.valueOf(r.getVol_amt());
		case 20:	return r.getAband();
		case 21:	return r.getStatus_type();		
		case 22:	return r.getCase_no();
		case 23:	return r.getLast_due_dil();
		case 24:	return r.getAction_comment();
		case 25:	return parseDate(r.getAction_update());
		case 26:	return r.getAssoc_type();
		case 27:	return Integer.valueOf(r.getAssoc_wd());
		case 28:	return Integer.valueOf(r.getAssoc_id());
		case 29:	return r.getTransfer_type();
		case 30:	return Integer.valueOf(r.getTran_wd());
		case 31:	return Integer.valueOf(r.getTran_id());
		case 32:	return r.getStrtype();
		default:	return "";
	}
	case TRANSFER_RIGHTS:
	switch (col) {
		case 0:		return Integer.valueOf(r.getDiv());
		case 1:		return Integer.valueOf(r.getWD());		
		case 2:		return Integer.valueOf(r.getID());
		case 3:		return r.getWr_name();
		case 4:		return r.getWd_stream_name();
		case 5:		return r.getTransfer_type();
		case 6:		return Integer.valueOf(r.getTran_wd());
		case 7:		return Integer.valueOf(r.getTran_id());
		case 8:
			return HydroBase_Util.buildLocation(
				r.getPM(), r.getTS(), r.getTdir(),
				r.getRng(), r.getRdir(), r.getSec(),
				r.getSeca(), r.getQ160(), r.getQ40(),
				r.getQ10());			
		case 9:		return __dmi.lookupCountyName(r.getCty());
		case 10:	return parseDate(r.getAdj_date());
		case 11:	return parseDate(r.getPadj_date());
		case 12:	return parseDate(r.getApro_date());
		case 13:	return Double.valueOf(r.getAdmin_no());
		case 14:	return Integer.valueOf(r.getOrder_no());
		case 15:	return r.getPrior_no();
		case 16:	return r.getAdj_type();
		case 17:	return r.getUse();		
		case 18:	return Double.valueOf(r.getRate_amt());
		case 19:	return Double.valueOf(r.getVol_amt());
		case 20:	return r.getAband();
		case 21:	return r.getStatus_type();		
		case 22:	return r.getCase_no();
		case 23:	return r.getLast_due_dil();
		case 24:	return r.getAction_comment();
		case 25:	return parseDate(r.getAction_update());
		case 26:	return r.getAssoc_type();
		case 27:	return Integer.valueOf(r.getAssoc_wd());
		case 28:	return Integer.valueOf(r.getAssoc_id());
		case 29:	return r.getAug_role();
		case 30:	return Integer.valueOf(r.getPlan_wd());
		case 31:	return Integer.valueOf(r.getPlan_id());
		case 32:	return r.getStrtype();
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