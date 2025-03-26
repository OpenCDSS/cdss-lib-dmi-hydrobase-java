// HydroBase_WISSheetNameWISFormat - Class to hold data from the HydroBase wis_sheet_name, and wis_format tables.

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

import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase wis_sheet_name, and wis_format tables. 
Unlike HydroBase_WISFormat, this version does not contain extended information
used in the operational WIS (WIS imports and formulas).
*/
public class HydroBase_WISSheetNameWISFormat 
extends HydroBase_WISSheetName {

// REVISIT (JTS - 2005-03-09)
// when HydroBase_SheetNameSpecialData is removed, this will extend 
// HydroBase_WISSheetName instead
// Data members in WISFormat

// _wis_num is in the base class
protected int _wis_row = 	DMIUtil.MISSING_INT;
protected String _row_type = 	DMIUtil.MISSING_STRING;
protected String _row_label = 	DMIUtil.MISSING_STRING;
protected String _known_point =	DMIUtil.MISSING_STRING;
protected int _wdwater_num = 	DMIUtil.MISSING_INT;
protected int _wdwater_link = 	DMIUtil.MISSING_INT;
protected double _str_mile = 	DMIUtil.MISSING_DOUBLE;
protected int _structure_num = 	DMIUtil.MISSING_INT;
protected int _station_num = 	DMIUtil.MISSING_INT;
protected String _identifier = 	DMIUtil.MISSING_STRING;
protected double _gain_factor = DMIUtil.MISSING_DOUBLE;
// gain_factor was 'weight' in the old version of the code.

/**
Constructor.
*/
public HydroBase_WISSheetNameWISFormat() {
	super();
}

/**
Returns _gain_factor
@return _gain_factor
*/
public double getGain_factor() {
	return _gain_factor;
}

/**
Returns _identifier
@return _identifier
*/
public String getIdentifier() {
	return _identifier;
}

/**
Returns _known_point
@return _known_point
*/
public String getKnown_point() {
	return _known_point;
}

/**
Returns _row_label
@return _row_label
*/
public String getRow_label() {
	return _row_label;
}

/**
Returns _row_type
@return _row_type
*/
public String getRow_type() {
	return _row_type;
}

/**
Returns _station_num
@return _station_num
*/
public int getStation_num() {
	return _station_num;
}

/**
Returns _str_mile
@return _str_mile
*/
public double getStr_mile() {
	return _str_mile;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _wdwater_link
@return _wdwater_link
*/
public int getWdwater_link() {
	return _wdwater_link;
}

/**
Returns _wdwater_num
@return _wdwater_num
*/
public int getWdwater_num() {
	return _wdwater_num;
}

/**
Returns _wis_num
@return _wis_num
*/
public int getWis_num() {
	return _wis_num;
}

/**
Returns _wis_row
@return _wis_row
*/
public int getWis_row() {
	return _wis_row;
}

/**
Returns the knownpoint data value as a boolean (true if 'Y', false otherwise).
@param knownPoint 'Y' if known point 'N' otherwise
@return the knownpoint data value as a boolean (true if 'Y', false otherwise).
*/
public static boolean isKnownPoint(String knownPoint) {
	knownPoint = knownPoint.trim();
	if (knownPoint.equals("Y")) {
		return true;
	} else {
		return false;
	}
}

/**
Sets _gain_factor
@param gain_factor value to put into _gain_factor
*/
public void setGain_factor(double gain_factor) {
	_gain_factor = gain_factor;
}

/**
Sets _identifier
@param identifier value to put into _identifier
*/
public void setIdentifier(String identifier) {
	_identifier = identifier;
}

/**
Sets _known_point
@param known_point value to put into _known_point
*/
public void setKnown_point(String known_point) {
	_known_point = known_point;
}

/**
Sets _row_label
@param row_label value to put into _row_label
*/
public void setRow_label(String row_label) {
	_row_label = row_label;
}

/**
Sets _row_type
@param row_type value to put into _row_type
*/
public void setRow_type(String row_type) {
	_row_type = row_type;
}

/**
Sets _station_num
@param station_num value to put into _station_num
*/
public void setStation_num(int station_num) {
	_station_num = station_num;
}

/**
Sets _str_mile
@param str_mile value to put into _str_mile
*/
public void setStr_mile(double str_mile) {
	_str_mile = str_mile;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _wdwater_link
@param wdwater_link value to put into _wdwater_link
*/
public void setWdwater_link(int wdwater_link) {
	_wdwater_link = wdwater_link;
}

/**
Sets _wdwater_num
@param wdwater_num value to put into _wdwater_num
*/
public void setWdwater_num(int wdwater_num) {
	_wdwater_num = wdwater_num;
}

/**
Sets _wis_num
@param wis_num value to put into _wis_num
*/
public void setWis_num(int wis_num) {
	_wis_num = wis_num;
}

/**
Sets _wis_row
@param wis_row value to put into _wis_row
*/
public void setWis_row(int wis_row) {
	_wis_row = wis_row;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_WISSheetNameWISFormat {"	+ "\n" + 
		"Wis_num:        " + _wis_num + "\n" + 
		"Sheet_name:     " + _sheet_name + "\n" + 
		"Effective_date: " + _effective_date + "\n" + 
		"WD:             " + _wd + "\n" + 
		"Gain_method:    " + _gain_method  + "\n" + 
		"Sheet_type:     " + _sheet_type + "\n" + 
		"Comments:       " + _comments + "\n" +
		"Wis_row:       " + _wis_row + "\n" + 
		"Row_type:      " + _row_type + "\n" + 
		"Row_label:     " + _row_label + "\n" + 
		"Known_point:   " + _known_point + "\n" + 
		"Wdwater_num:   " + _wdwater_num + "\n" + 
		"Wdwater_link:  " + _wdwater_link + "\n" + 
		"Str_mile:      " + _str_mile + "\n" + 
		"Structure_num: " + _structure_num + "\n" + 
		"Station_num:   " + _station_num + "\n" + 
		"Identifier:    " + _identifier + "\n" + 
		"Gain_factor:   " + _gain_factor + "\n}\n";
}

}
