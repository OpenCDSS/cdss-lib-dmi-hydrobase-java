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
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

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
