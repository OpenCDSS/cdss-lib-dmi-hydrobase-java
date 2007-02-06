// ----------------------------------------------------------------------------
// HydroBase_CUCoeff.java - Class to hold data from the HydroBase 
//	cu_coeff table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-19	J. Thomas Sapienza, RTi	Initial version from HBCUCoefficient.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase cu_coeff table.
*/
public class HydroBase_CUCoeff 
extends DMIDataObject {

protected int _consnum = 	DMIUtil.MISSING_INT;
protected String _consname =	DMIUtil.MISSING_STRING;
protected double _consuse = 	DMIUtil.MISSING_DOUBLE;
protected double _cu_coeff = 	DMIUtil.MISSING_DOUBLE;

/**
Constructor.
*/
public HydroBase_CUCoeff() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_consname = null;
	
	super.finalize();
}

/**
Returns _consname.
@return _consname.
*/
public String getConsname() {
	return _consname;
}

/**
Returns _consnum.
@return _consnum.
*/
public int getConsnum() {
	return _consnum;
}

/**
Returns _consuse.
@return _consuse.
*/
public double getConsuse() {
	return _consuse;
}

/**
Returns _cu_coeff.
@return _cu_coeff.
*/
public double getCU_coeff() {
	return _cu_coeff;
}

/**
Sets _consname.
@param consname value to put into _consname.
*/
public void setConsname(String consname) {
	_consname = consname;
}

/**
Sets _consnum.
@param consnum value to put into _consnum.
*/
public void setConsnum(int consnum) {
	_consnum = consnum;
}

/**
Sets _consuse.
@param consuse value to put into _consuse.
*/
public void setConsuse(double consuse) {
	_consuse = consuse;
}

/**
Sets _cu_coeff.
@param cu_coeff value to put into _cu_coeff.
*/
public void setCU_coeff(double cu_coeff) {
	_cu_coeff = cu_coeff;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_CUCoeff {"				+ "\n" + 
		"Consnum:  " + _consnum 	+ "\n" + 
		"Consname: '" + _consname  	+ "'\n" + 
		"Consuse:  " + _consuse 	+ "\n" + 
		"CU_Coeff: " + _cu_coeff 	+ "\n}\n";
}

}