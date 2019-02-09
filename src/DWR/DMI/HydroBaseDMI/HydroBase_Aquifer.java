// HydroBase_Aquifer - Class to hold data from the HydroBase aquifer table.

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

// ----------------------------------------------------------------------------
// HydroBase_Aquifer.java - Class to hold data from the HydroBase aquifer table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2005-11-21	J. Thomas Sapienza, RTi	Initial version.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase aquifer table.
*/
public class HydroBase_Aquifer 
extends DMIDataObject {

protected int _aquifer_num = 		DMIUtil.MISSING_INT;
protected String _aquifer_code = 	DMIUtil.MISSING_STRING;
protected String _aquifer_name = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_Aquifer() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_aquifer_code = null;
	_aquifer_name = null;
	super.finalize();
}

/**
Returns _aquifer_code
@return _aquifer_code
*/
public String getAquifer_code() {
	return _aquifer_code;
}

/**
Returns _aquifer_name
@return _aquifer_name
*/
public String getAquifer_name() {
	return _aquifer_name;
}

/**
Returns _aquifer_num
@return _aquifer_num
*/
public int getAquifer_num() {
	return _aquifer_num;
}

/**
Sets _aquifer_code
@param aquifer_code value to put into _aquifer_code
*/
public void setAquifer_code(String aquifer_code) {
	_aquifer_code = aquifer_code;
}

/**
Sets aquifer_name
@param aquifer_name value to put into _aquifer_name
*/
public void setAquifer_name(String aquifer_name) {
	_aquifer_name = aquifer_name;
}

/**
Sets _aquifer_num
@param aquifer_num value to put into _aquifer_num
*/
public void setAquifer_num(int aquifer_num) {
	 _aquifer_num = aquifer_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_Aquifer {"		+ "\n" + 
		"   Aquifer_num:   " + _aquifer_num + "\n" + 
		"   Aquifer_code: '" + _aquifer_code + "'\n" + 
		"   Aquifer_name: '" + _aquifer_name + "'\n}\n";
}

}
