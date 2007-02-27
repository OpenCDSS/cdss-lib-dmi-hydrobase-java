// ----------------------------------------------------------------------------
// HydroBase_LocType.java - Class to hold data from the HydroBase 
//	loc_type table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-10	J. Thomas Sapienza, RTi	Initial version from HBLocationType.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2003-10-01	JTS, RTi		Loc type variables were declared static!
//					Causing all sorts of weird problems --
//					corrected.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase loc_type table.
*/
public class HydroBase_LocType 
extends DMIDataObject {

protected String _loc_type =		DMIUtil.MISSING_STRING;
protected String _loc_type_desc = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_LocType() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_loc_type = null;
	_loc_type_desc = null;
	super.finalize();
}

/**
Returns _loc_type
@return _loc_type
*/
public String getLoc_type() {
	return _loc_type;
}

/**
Returns _loc_type_desc
@return _loc_type_desc
*/
public String getLoc_type_desc() {
	return _loc_type_desc;
}

/**
Sets _loc_type
@param loc_type value to put into _loc_type
*/
public void setLoc_type(String loc_type) {
	_loc_type = loc_type;
}

/**
Sets _loc_type_desc
@param loc_type_desc value to put into _loc_type_desc
*/
public void setLoc_type_desc(String loc_type_desc) {
	_loc_type_desc = loc_type_desc;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_LocType {"		+ "\n" + 
		"Loc_type:      " + _loc_type + "\n" +
		"Loc_type_desc: " + _loc_type_desc + "\n}\n";
}

}
