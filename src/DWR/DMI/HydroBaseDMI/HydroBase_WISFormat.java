// HydroBase_WISFormat - Class to hold data from the HydroBase wis_format table.

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

import java.util.List;
import java.util.Vector;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase wis_format table.
*/
public class HydroBase_WISFormat 
extends DMIDataObject {

protected int _wis_num = 	DMIUtil.MISSING_INT;
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


protected static int	__COLS = 11;	// number of columns in WIS
// the following member variables are not saved to the database, but assist in
// building WIS
protected List<HydroBase_WISFormula> _wisFormula;		// Cell formulas for a row. Elements
					// represent Point Flow, Natural Flow
					// Delivery Flow, and Gain/Loss, etc...
					// columns respectively. Each element
					// represents an HydroBase_WISFormula 
					// object.
List<HydroBase_WISImport> _wisImport;		// Cell imports for a row. Elements
					// represent Point Flow, Natural Flow
					// Delivery Flow, and Gain/Loss, etc...
					// columns respectively. Each element
					// represents an HydroBase_WISImport 
					// object.
List<Boolean> _isEntryCell;		// contains a boolean value for each
					// column in the row. true denotes
					// a cell whose value is user specified,
					// while false represents a cell
					// which is computed

/**
Constructor.
*/
public HydroBase_WISFormat() {
	super();
	_wisFormula =  new Vector<HydroBase_WISFormula>();
	_wisImport =  new Vector<HydroBase_WISImport>();
	_isEntryCell =  new Vector<Boolean>();
	for (int curCol = 0; curCol < __COLS + 1; curCol++) {
		_wisFormula.add(new HydroBase_WISFormula());
		_wisImport.add(new HydroBase_WISImport());
		if (curCol < 6) {
			_isEntryCell.add(Boolean.valueOf(false));
		}
		else {
			_isEntryCell.add(Boolean.valueOf(true));
		}
	}
}

/**
Clears the formulas Vector.
*/
public void clearFormulas() {
	for (int curCol = 0; curCol < __COLS + 1; curCol++) {
		_wisFormula.set(curCol, new HydroBase_WISFormula());
	}
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
Gets the wis formula for a column.
@param col the column for which to return the wis formula.
@return the wis formula for a column.
*/
public HydroBase_WISFormula getWISFormula(int col) {
	return (HydroBase_WISFormula)_wisFormula.get(col);
}

/**
Gets the wis import for a column.
@param col the column for which to return the wis formula.
@return the wis import for a column.
*/
public HydroBase_WISImport getWISImport(int col) {
	return (HydroBase_WISImport)_wisImport.get(col);
}

/**
Returns whether the column is an entry cell.
@param col the column to check.
@return true if the column is an entry cell.
*/
public boolean isEntryCell(int col) {
	return ((Boolean)_isEntryCell.get(col)).booleanValue();
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
Sets the wis formula for the specified column.
@param wisFormula the formula to set.
@param col the column to set the formula in.
*/
public void setWISFormula(HydroBase_WISFormula wisFormula, int col) {
	_wisFormula.set(col, wisFormula );
}

/**
Sets the wis import for the specified column.
@param wisImport the import to set.
@param col the column to set the import in.
*/
public void setWISImport(HydroBase_WISImport wisImport, int col) {
	_wisImport.set(col, wisImport );
}

/**
Sets a column as an entry cell.
@param col the column to set as an entry cell.
@param b whether to set it as an entry cell or not.
*/
public void setIsEntryCell(int col, boolean b) {
	_isEntryCell.set(col,Boolean.valueOf(b));
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_WISFormat {"		+ "\n" + 
		"Wis_num:       " + _wis_num + "\n" + 
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
