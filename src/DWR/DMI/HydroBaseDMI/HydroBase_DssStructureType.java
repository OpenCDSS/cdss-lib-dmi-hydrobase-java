// HydroBase_DssStructureType - Class to store data for HydroBase DSS structure types as returned by
// usp_CDSS_refStructureType_Sel stored procedure.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2025 Colorado Department of Natural Resources

CDSS HydroBase Database Java Library is free software:  you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

CDSS HydroBase Database Java Library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

You should have received a copy of the GNU General Public License
    along with CDSS HydroBase Database Java Library.  If not, see <https://www.gnu.org/licenses/>.

NoticeEnd */

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
