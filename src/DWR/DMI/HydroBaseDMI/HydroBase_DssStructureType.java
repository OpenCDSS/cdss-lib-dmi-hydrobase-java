package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data for HydroBase DSS structure types as returned by
usp_CDSS_refStructureType_Sel stored procedure.
*/
public class HydroBase_DssStructureType 
extends DMIDataObject {

protected String _str_type = DMIUtil.MISSING_STRING;
protected String _str_type_desc = DMIUtil.MISSING_STRING;
protected String _rpt_code = DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_DssStructureType() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_str_type = null;
	_str_type_desc = null;
	_rpt_code = null;
	
	finalize();
}

/**
Returns _rpt_code
@return _rpt_code
*/
public String getRpt_code() {
	return _rpt_code;
}

/**
Returns _str_type
@return _str_type
*/
public String getStr_type() {
	return _str_type;
}

/**
Returns _str_type_desc
@return _str_type_desc
*/
public String getStr_type_desc() {
	return _str_type_desc;
}

/**
Sets _rpt_code
@param rpt_code value to put into _rpt_code
*/
public void setRpt_code(String rpt_code) {
	_rpt_code = rpt_code;
}

/**
Sets _str_type
@param str_type value to put into _str_type
*/
public void setStr_type(String str_type) {
	_str_type = str_type;
}

/**
Sets _str_type_desc
@param str_type_desc
*/
public void setStr_type_desc(String str_type_desc) {
	_str_type_desc = str_type_desc;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_StrType {"		+ "\n" + 
		"Str_type:      " + _str_type + "\n" + 
		"Str_type_desc: " + _str_type_desc + "\n" +
		"Rpt_code:      " + _rpt_code + "\n}\n";
}

}