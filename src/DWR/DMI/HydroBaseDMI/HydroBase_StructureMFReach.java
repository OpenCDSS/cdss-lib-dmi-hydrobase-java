// HydroBase_StructureMFReach - Class to hold data from the HydroBase mfreach and structure tables.

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

import java.util.Date;

/**
Class to store data from the HydroBase mfreach and structure tables.
*/
public class HydroBase_StructureMFReach 
extends HydroBase_Structure {

protected int _structure_num = 	DMIUtil.MISSING_INT;
protected double _mfr_rate = 	DMIUtil.MISSING_DOUBLE;
protected double _mfr_vol = 	DMIUtil.MISSING_DOUBLE;
protected Date _modified = 	DMIUtil.MISSING_DATE;
protected int _user_num = 	DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_StructureMFReach() {
	super();
}

/**
Returns _mfr_rate
@return _mfr_rate
*/
public double getMfr_rate() {
	return _mfr_rate;
}

/**
Returns _mfr_vol
@return _mfr_vol
*/
public double getMfr_vol() {
	return _mfr_vol;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Sets _mfr_rate
@param mfr_rate value to put into _mfr_rate
*/
public void setMfr_rate(double mfr_rate) {
	_mfr_rate = mfr_rate;
}

/**
Sets _mfr_vol
@param mfr_vol value to put into _mfr_vol
*/
public void setMfr_vol(double mfr_vol) {
	_mfr_vol = mfr_vol;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _user_num
@param user_num value to put into _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_StructureMFReach {"		+ "\n" + 
		"Structure_num: " + _structure_num + "\n" + 
		"Mfr_rate:      " + _mfr_rate + "\n" + 
		"Mfr_vol:       " + _mfr_vol + "\n" + 
		"Modified:      " + _modified + "\n" + 
		"User_num:      " + _user_num + "\n}\n"
		+ super.toString();
}

}
