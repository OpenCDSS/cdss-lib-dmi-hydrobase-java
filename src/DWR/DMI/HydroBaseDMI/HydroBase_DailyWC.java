// HydroBase_DailyWC - Class to hold data from the HydroBase daily_wc table.

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
//	daily_wc table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-18	J. Thomas Sapienza, RTi	Initial version from
//					HBStructureDailyWaterClassRecord.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase daily_wc table.
*/
public class HydroBase_DailyWC 
extends HydroBase_DailyAmt {

protected String _S = 	DMIUtil.MISSING_STRING;
protected int _F = 	DMIUtil.MISSING_INT;
protected String _U = 	DMIUtil.MISSING_STRING;
protected String _T = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_DailyWC() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_S = null;
	_U = null;
	_T = null;
	super.finalize();
}

/**
Returns _F
@return _F
*/
public int getF() {
	return _F;
}

/**
Returns _S
@return _S
*/
public String getS() {
	return _S;
}

/**
Returns _T
@return _T
*/
public String getT() {
	return _T;
}

/**
Returns _U
@return _U
*/
public String getU() {
	return _U;
}

/**
Sets _F
@param F value to put into _F
*/
public void setF(int F) {
	_F = F;
}

/**
Sets _S
@param S value to put into _S
*/
public void setS(String S) {
	_S = S;
}

/**
Sets _T
@param T value to put into _T
*/
public void setT(String T) {
	_T = T;
}

/**
Sets _U
@param U value to put in _U
*/
public void setU(String U) {
	_U = U;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_DailyWC {"		+ "\n" + 
		"S: " + _S + "\n" + 
		"F: " + _F + "\n" + 
		"U: " + _U + "\n" + 
		"T: " + _T + "\n}\n" +
		super.toString();
}

}
