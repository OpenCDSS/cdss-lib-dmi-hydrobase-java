// HydroBase_MonthlyNflow - Class to hold data from the HydroBase monthly_nflow table.

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
// HydroBase_MonthlyNflow.java - Class to hold data from the HydroBase 
//	monthly_nflow table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-18	J. Thomas Sapienza, RTi	Initial version from
//					HBStationMonthTSRecord.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase monthly_nflow table.
*/
public class HydroBase_MonthlyNflow 
extends HydroBase_MonthlyBase {

protected String _crss_id = 	DMIUtil.MISSING_STRING;
protected String _cal_mon = 	DMIUtil.MISSING_STRING;
protected double _total_q_af = 	DMIUtil.MISSING_DOUBLE;

/**
Constructor.
*/
public HydroBase_MonthlyNflow() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_crss_id = null;
	_cal_mon = null;

	super.finalize();
}

/**
Returns _cal_mon
@return _cal_mon
*/
public String getCal_mon() {
	return _cal_mon;
}

/**
Returns _crss_id
@return _crss_id
*/
public String getCrss_id() {
	return _crss_id;
}

/**
Returns _total_q_af
@return _total_q_af
*/
public double getTotal_q_af() {
	return _total_q_af;
}

/**
Sets _cal_mon
@param cal_mon value to put into _cal_mon
*/
public void setCal_mon(String cal_mon) {
	_cal_mon = cal_mon;
}

/**
Sets _crss_id
@param crss_id value to put into _crss_id
*/
public void setCrss_id(String crss_id) {
	_crss_id = crss_id;
}

/**
Sets _total_q_af
@param total_q_af value to put into _total_q_af
*/
public void setTotal_q_af(double total_q_af) {
	_total_q_af = total_q_af;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_MonthlyNflow {"		+ "\n" + 
		"Crss_id:    " + _crss_id + "\n" +		
		"Cal_mon:    " + _cal_mon + "\n" + 
		"Total_q_af: " + _total_q_af + "\n}\n";
}

}
