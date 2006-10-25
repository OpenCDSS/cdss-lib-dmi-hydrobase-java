// ----------------------------------------------------------------------------
// HydroBase_Aquifer.java - Class to hold data from the HydroBase aquifer table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2005-11-21	J. Thomas Sapienza, RTi	Initial version.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase aquifer table.
*/
public class HydroBase_Aquifer 
extends DMIDataObject {

protected int _aquifer_num = 		DMIUtil.MISSING_INT;
protected String _aquifer_code = 	DMIUtil.MISSING_STRING;
protected String _aquifer_name = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_Aquifer() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_aquifer_code = null;
	_aquifer_name = null;
	super.finalize();
}

/**
Returns _aquifer_code
@return _aquifer_code
*/
public String getAquifer_code() {
	return _aquifer_code;
}

/**
Returns _aquifer_name
@return _aquifer_name
*/
public String getAquifer_name() {
	return _aquifer_name;
}

/**
Returns _aquifer_num
@return _aquifer_num
*/
public int getAquifer_num() {
	return _aquifer_num;
}

/**
Sets _aquifer_code
@param aquifer_code value to put into _aquifer_code
*/
public void setAquifer_code(String aquifer_code) {
	_aquifer_code = aquifer_code;
}

/**
Sets aquifer_name
@param aquifer_name value to put into _aquifer_name
*/
public void setAquifer_name(String aquifer_name) {
	_aquifer_name = aquifer_name;
}

/**
Sets _aquifer_num
@param aquifer_num value to put into _aquifer_num
*/
public void setAquifer_num(int aquifer_num) {
	 _aquifer_num = aquifer_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_Aquifer {"		+ "\n" + 
		"   Aquifer_num:   " + _aquifer_num + "\n" + 
		"   Aquifer_code: '" + _aquifer_code + "'\n" + 
		"   Aquifer_name: '" + _aquifer_name + "'\n}\n";
}

}
