// ----------------------------------------------------------------------------
// HydroBase_RefCIU - Class to hold data from the HydroBase 
//	ref_ciu table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2004-07-09	J. Thomas Sapienza, RTi	Initial version.
// 2005-02-04	JTS, RTi		Added short_desc field.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase meas_type table.
*/
public class HydroBase_RefCIU 
extends DMIDataObject {

protected String _code = 		DMIUtil.MISSING_STRING;
protected String _description = 	DMIUtil.MISSING_STRING;
protected String _rpt_code = 		DMIUtil.MISSING_STRING;
protected String _short_desc = 		DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_RefCIU() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_code = null;
	_description = null;
	_rpt_code = null;
	_short_desc = null;
	super.finalize();
}

/**
Returns _code
@return _code
*/
public String getCode() {
	return _code;
}

/**
Returns _description
@return _description
*/
public String getDescription() {
	return _description;
}

/**
Returns _rpt_code
@return _rpt_code
*/
public String getRpt_code() {
	return _rpt_code;
}

/**
Returns _short_desc
@return _short_desc
*/
public String getShort_desc() {
	return _short_desc;
}

/**
Sets _code
@param code value to put into _code
*/
public void setCode(String code) {
	_code = code;
}

/**
Sets _description
@param description value to put into _description
*/
public void setDescription(String description) {
	_description = description;
}

/**
Sets _rpt_code
@param rpt_code value to put into _rpt_code
*/
public void setRpt_code(String rpt_code) {
	_rpt_code = rpt_code;
}

/**
Sets _short_desc
@param short_desc value to put into _short_desc
*/
public void setShort_desc(String short_desc) {
	_short_desc = short_desc;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_RefCIU {"		+ "\n" + 
		"Code:        '" + _code 	+ "'\n" +
		"Description: '" + _description + "'\n" +
		"Rpt_code:    '" + _rpt_code 	+ "'\n}\n";
}

}
