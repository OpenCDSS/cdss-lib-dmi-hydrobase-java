// ----------------------------------------------------------------------------
// HydroBase_MonthlyTemp.java - Class to hold data from the HydroBase 
//	monthly_temp table.
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
Class to store data from the HydroBase monthly_temp table.
*/
public class HydroBase_MonthlyTemp 
extends HydroBase_MonthlyBase {

protected double _avg_max_t =	DMIUtil.MISSING_DOUBLE;
protected double _avg_min_t = 	DMIUtil.MISSING_DOUBLE;
protected double _mean_t = 	DMIUtil.MISSING_DOUBLE;

/**
Constructor.
*/
public HydroBase_MonthlyTemp() {
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
Returns _avg_max_t
@return _avg_max_t
*/
public double getAvg_max_t() {
	return _avg_max_t;
}

/**
Returns _avg_min_t
@return _avg_min_t
*/
public double getAvg_min_t() {
	return _avg_min_t;
}

/**
Returns _mean_t
@return _mean_t
*/
public double getMean_t() {
	return _mean_t;
}

/**
Sets _avg_max_t
@param avg_max_t value to put into _avg_max_t
*/
public void setAvg_max_t(double avg_max_t) {
	_avg_max_t = avg_max_t;
}

/**
Sets _avg_min_t
@param avg_min_t value to put into _avg_min_t
*/
public void setAvg_min_t(double avg_min_t) {
	_avg_min_t = avg_min_t;
}

/**
Sets _mean_t
@param mean_t value to put into _mean_t
*/
public void setMean_t(double mean_t) {
	_mean_t = mean_t;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_MonthlyTemp {"		+ "\n" + 
		"Avg_max_t: " + _avg_max_t + "\n" +
		"Avg_min_t: " + _avg_min_t + "\n" + 
		"Mean_t:    " + _mean_t + "\n}\n";
}

}
