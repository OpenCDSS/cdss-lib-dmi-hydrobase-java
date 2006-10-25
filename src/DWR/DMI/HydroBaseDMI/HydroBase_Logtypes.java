// ----------------------------------------------------------------------------
// HydroBase_Logtypes.java - Class to hold data from the HydroBase 
//	logtypes table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-17	J. Thomas Sapienza, RTi	Initial version.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase logtypes table.
*/
public class HydroBase_Logtypes 
extends DMIDataObject {

protected int _logid = 		DMIUtil.MISSING_INT;
protected String _logty = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_Logtypes() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_logty = null;	
	super.finalize();
}

/**
Returns _logid
@return _logid
*/
public int getLogid() {
	return _logid;
}

/**
Returns _logty
@return _logty
*/
public String getLogty() {
	return _logty;
}

/**
Sets _logid
@param logid value to put into _logid
*/
public void setLogid(int logid) {
	_logid = logid;
}

/**
Sets _logty
@param logty value to put into _logty
*/
public void setLogty(String logty) {
	_logty = logty;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_Logtypes {"		+ "\n" + 
		"Logid: " + _logid + "\n" + 
		"Logty: " + _logty + "\n}\n";
}

}
