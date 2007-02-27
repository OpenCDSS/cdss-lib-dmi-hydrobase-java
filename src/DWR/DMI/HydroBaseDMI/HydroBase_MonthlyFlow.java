// ----------------------------------------------------------------------------
// HydroBase_MonthlyFlow.java - Class to hold data from the HydroBase 
//	monthly_flow table.
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
Class to store data from the HydroBase monthly_flow table.
*/
public class HydroBase_MonthlyFlow 
extends HydroBase_MonthlyBase {

protected double _max_q_cfs = 	DMIUtil.MISSING_DOUBLE;
protected double _min_q_cfs = 	DMIUtil.MISSING_DOUBLE;
protected double _total_q_af = 	DMIUtil.MISSING_DOUBLE;

/**
Constructor.
*/
public HydroBase_MonthlyFlow() {
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
Returns _max_q_cfs
@return _max_q_cfs
*/
public double getMax_q_cfs() {
	return _max_q_cfs;
}

/**
Returns _min_q_cfs
@return _min_q_cfs
*/
public double getMin_q_cfs() {
	return _min_q_cfs;
}

/**
Returns _total_q_af
@return _total_q_af
*/
public double getTotal_q_af() {
	return _total_q_af;
}

/**
Sets _max_q_cfs
@param max_q_cfs value to put into _max_q_cfs
*/
public void setMax_q_cfs(double max_q_cfs) {
	_max_q_cfs = max_q_cfs;
}

/**
Sets _min_q_cfs
@param min_q_cfs value to put into _min_q_cfs
*/
public void setMin_q_cfs(double min_q_cfs) {
	_min_q_cfs = min_q_cfs;
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
	return "HydroBase_MonthlyFlow {"		+ "\n" + 
		"Max_q_cfs:  " + _max_q_cfs + "\n" +
		"Min_q_cfs:  " + _min_q_cfs + "\n" + 
		"Total_q_af: " + _total_q_af + "\n}\n";
}

}
