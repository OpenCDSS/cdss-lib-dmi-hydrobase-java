// HydroBase_MonthlyPcpn - Class to hold data from the HydroBase monthly_pcpn table.

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
// HydroBase_MonthlyPcpn.java - Class to hold data from the HydroBase 
//	monthly_pcpn table.
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
Class to store data from the the HydroBase monthly_pcpn table.
*/
public class HydroBase_MonthlyPcpn 
extends HydroBase_MonthlyBase {

protected double _total_pcpn = DMIUtil.MISSING_DOUBLE;

/**
Constructor.
*/
public HydroBase_MonthlyPcpn() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	super.finalize();
}

/**
Returns _total_pcpn
@return _total_pcpn
*/
public double getTotal_pcpn() {
	return _total_pcpn;
}

/**
Sets _total_pcpn
@param total_pcpn value to put into _total_pcpn
*/
public void setTotal_pcpn(double total_pcpn) {
	_total_pcpn = total_pcpn;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_MonthlyPcpn {"		+ "\n" + 
		"Total_pcpn: " + _total_pcpn + "\n}\n"
		+ super.toString();
}

}
