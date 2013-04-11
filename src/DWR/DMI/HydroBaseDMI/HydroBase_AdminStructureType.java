package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data for HydroBase administrative structure types as returned by
usp_CDSS_refStrType_Sel stored procedure.
*/
public class HydroBase_AdminStructureType 
extends DMIDataObject {

protected String _strtype = DMIUtil.MISSING_STRING;
protected String _strtype_desc = DMIUtil.MISSING_STRING;
protected String _rpt_code = DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_AdminStructureType() {
	super();
}

/**
Returns _rpt_code
@return _rpt_code
*/
public String getRpt_code() {
	return _rpt_code;
}

/**
Returns _strtype
@return _strtype
*/
public String getStrtype() {
	return _strtype;
}

/**
Returns _str_type_desc
@return _str_type_desc
*/
public String getStrtype_desc() {
	return _strtype_desc;
}

/**
Sets _rpt_code
@param rpt_code value to put into _rpt_code
*/
public void setRpt_code(String rpt_code) {
	_rpt_code = rpt_code;
}

/**
Sets _strtype
@param strtype value to put into _strtype
*/
public void setStrtype(String strtype) {
	_strtype = strtype;
}

/**
Sets _str_type_desc
@param strtype_desc
*/
public void setStrtype_desc(String strtype_desc) {
	_strtype_desc = strtype_desc;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_StrType {"		+ "\n" + 
		"Str_type:      " + _strtype + "\n" + 
		"Str_type_desc: " + _strtype_desc + "\n" +
		"Rpt_code:      " + _rpt_code + "\n}\n";
}

}