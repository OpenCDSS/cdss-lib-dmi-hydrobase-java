// ----------------------------------------------------------------------------
// HydroBase_StructureToParcel.java - Class to hold data from the HydroBase
//			structure_to_parcel table
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2004-03-03	Steven A. Malers, RTi	Initial version.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Date;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase structure_to_parcel table.
*/
public class HydroBase_StructureToParcel extends DMIDataObject
{

protected int _parcel_num = 		DMIUtil.MISSING_INT;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected int _primary_flag = 		DMIUtil.MISSING_INT;
protected double _percent_irrig = 	DMIUtil.MISSING_DOUBLE;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

// Used only by software - is not in the database.

protected String _structure_id =	DMIUtil.MISSING_STRING;
protected int _div =			DMIUtil.MISSING_INT;
protected int _parcel_id =		DMIUtil.MISSING_INT;
protected int _cal_year =		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_StructureToParcel() {
	super();
}

/**
Cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
*/
protected void finalize()
throws Throwable {
	_modified = null;
	_structure_id = null;
	super.finalize();
}

/**
Returns _cal_year
@return _cal_year
*/
public int getCal_year() {
	return _cal_year;
}

/**
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _parcel_id
@return _parcel_id
*/
public int getParcel_id() {
	return _parcel_id;
}

/**
Returns _parcel_num
@return _parcel_num
*/
public int getParcel_num() {
	return _parcel_num;
}

/**
Returns _percent_irrig
@return _percent_irrig
*/
public double getPercent_irrig() {
	return _percent_irrig;
}

/**
Returns _primary_flag
@return _primary_flag
*/
public int getPrimary_flag() {
	return _primary_flag;
}

/**
Returns _structure_id
@return _structure_id
*/
public String getStructure_id() {
	return _structure_id;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Sets _cal_year
@param cal_year value to put into _cal_year
*/
public void setCal_year(int cal_year) {
	_cal_year = cal_year;
}

/**
Sets _div
@param div value to put into _div
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _parcel_id
@param parcel_id value to put into _parcel_id
*/
public void setParcel_id(int parcel_id) {
	_parcel_id = parcel_id;
}

/**
Sets _parcel_num
@param parcel_num value to put into _parcel_num
*/
public void setParcel_num(int parcel_num) {
	_parcel_num = parcel_num;
}

/**
Sets _percent_irrig
@param percent_irrig value to put into _percent_irrig
*/
public void setPercent_irrig(double percent_irrig) {
	_percent_irrig = percent_irrig;
}

/**
Sets _primary_flag
@param primary_flag value to put into _primary_flag
*/
public void setPrimary_flag(int primary_flag) {
	_primary_flag = primary_flag;
}

/**
Sets _structure_id
@param structure_id value to put into _structure_id
*/
public void setStructure_id(String structure_id) {
	_structure_id = structure_id;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _user_num
@param user_num value to put into _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}

/** 
Return a string representation of this object.
@return a string representation of this object.
*/
public String toString() {
	return "HydroBase_StructureToParcel {"		+ "\n" + 
		"Parcel_num:         " + _parcel_num + "\n" + 
		"Structure_num:      " + _structure_num + "\n" + 
		"Primary_flag:       " + _primary_flag + "\n" + 
		"Percent_irrig:      " + _percent_irrig + "\n" + 
		"Modified:           " + _modified + "\n" + 
		"User_num:           " + _user_num + "\n" + 
		"Structure_id:       " + _structure_id + "\n" + 
		"Div:                " + _div + "\n" + 
		"Cal_year:           " + _cal_year + "\n" + 
		"Parcel_id:          " + _parcel_id + "}";
}

}
