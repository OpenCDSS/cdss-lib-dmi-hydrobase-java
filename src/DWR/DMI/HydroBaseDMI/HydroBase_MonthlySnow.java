// ----------------------------------------------------------------------------
// HydroBase_MonthlySnow.java - Class to hold data from the HydroBase 
//	monthly_snow table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-18	J. Thomas Sapienza, RTi	Initial version from
//					HBStationMonthTSRecord.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase monthly_snow table.
*/
public class HydroBase_MonthlySnow 
extends HydroBase_MonthlyBase {

protected double _total_snow = DMIUtil.MISSING_DOUBLE;

/**
Constructor.
*/
public HydroBase_MonthlySnow() {
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
Returns _total_snow
@return _total_snow
*/
public double getTotal_snow() {
	return _total_snow;
}

/**
Sets _total_snow
@param total_snow value to put into _total_snow
*/
public void setTotal_snow(double total_snow) {
	_total_snow = total_snow;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_MonthlySnow {"		+ "\n" + 
		"Total_snow: " + _total_snow + "\n}\n"
		+ super.toString();
}

}
