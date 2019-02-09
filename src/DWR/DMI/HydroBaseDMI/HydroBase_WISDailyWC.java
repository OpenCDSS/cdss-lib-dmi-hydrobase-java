// HydroBase_DailyWC - Class to hold data from the HydroBase wis_daily_wc table.

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
// HydroBase_DailyWC.java - Class to hold data from the HydroBase 
//	wis_daily_wc table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-10-16	J. Thomas Sapienza, RTi	Initial version.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase wis_daily_wc table.
*/
public class HydroBase_WISDailyWC 
extends HydroBase_DailyWC {

protected int _wis_dailywc_num =	DMIUtil.MISSING_INT;
protected int _wis_num = 		DMIUtil.MISSING_INT;
protected String _wis_column = 		DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_WISDailyWC() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_wis_column = null;
	super.finalize();
}

/**
Returns _wis_column
@return _wis_column
*/
public String getWis_column() {
	return _wis_column;
}

/**
Returns _wis_dailywc_num
@return _wis_dailywc_num
*/
public int getWis_dailywc_num() {
	return _wis_dailywc_num;
}

/**
Returns _wis_num
@return _wis_num
*/
public int getWis_num() {
	return _wis_num;
}

/**
Sets _wis_column
@param wis_column value to put into _wis_column
*/
public void setWis_column(String wis_column) {
	_wis_column = wis_column;
}

/**
Sets _wis_dailywc_num
@param wis_dailywc_num value to put into _wis_dailywc_num
*/
public void setWis_dailywc_num(int wis_dailywc_num) {
	_wis_dailywc_num = wis_dailywc_num;
}

/**
Sets _wis_num
@param wis_num value to put into _wis_num
*/
public void setWis_num(int wis_num) {
	_wis_num = wis_num;
}

public String toString() {
	return 
		"Wis_column:      '" + _wis_column + "'\n" +
		"Wis_dailywc_num: " + _wis_dailywc_num + "\n" + 
		"wis_num:         " + _wis_num + "\n" + 
		super.toString();
}

}
