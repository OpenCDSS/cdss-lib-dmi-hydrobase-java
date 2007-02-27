// ----------------------------------------------------------------------------
// HydroBase_CropRef.java - Class to hold data from the HydroBase 
//	crop_ref table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-17	J. Thomas Sapienza, RTi	Initial version.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase crop_ref table.
*/
public class HydroBase_CropRef 
extends DMIDataObject {

protected String _crop_code = DMIUtil.MISSING_STRING;
protected String _crop_desc = DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_CropRef() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_crop_code = null;
	_crop_desc = null;
	super.finalize();
}

/**
Returns _crop_code
@return _crop_code
*/
public String getCrop_code() {
	return _crop_code;
}

/**
Returns _crop_desc
@return _crop_desc
*/
public String getCrop_desc() {
	return _crop_desc;
}

/**
Sets _crop_code
@param crop_code value to put into _crop_code
*/
public void setCrop_code(String crop_code) {
	_crop_code = crop_code;
}

/**
Sets _crop_desc
@param crop_desc value to put into _crop_desc
*/
public void setCrop_desc(String crop_desc) {
	_crop_desc = crop_desc;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_CropRef {"		+ "\n" + 
		"Crop_code: " + _crop_code + "\n" + 
		"Crop_desc: " + _crop_desc + "\n}\n";
}

}
