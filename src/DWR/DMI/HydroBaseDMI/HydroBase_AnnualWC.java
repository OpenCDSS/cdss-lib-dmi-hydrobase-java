// ----------------------------------------------------------------------------
// HydroBase_AnnualWC.java - Class to hold data from the HydroBase annual_wc 
//	table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-18	J. Thomas Sapienza, RTi	Initial version from 
//					HBStructureAnnualWaterClassRecord.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase annual_wc table.
*/
public class HydroBase_AnnualWC 
extends HydroBase_AnnualAmt {

protected String _S = 	DMIUtil.MISSING_STRING;
protected int _F = 	DMIUtil.MISSING_INT;
protected String _U = 	DMIUtil.MISSING_STRING;
protected String _T = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_AnnualWC() {
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
@param U value to put into _U
*/
public void setU(String U) {
	_U = U;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_AnnualWC {"		+ "\n" + 
		"S: " + _S + "\n" +
		"F: " + _F + "\n" + 
		"U: " + _U + "\n" + 
		"T: " + _T + "\n}\n" +
		super.toString();
}

}
