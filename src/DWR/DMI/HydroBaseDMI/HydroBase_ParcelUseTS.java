// ----------------------------------------------------------------------------
// HydroBase_ParcelUseTS.java - Class to hold data from the HydroBase
//			parcel_use_ts table
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2004-02-23	Steven A. Malers, RTi	Initial version.
// 2004-05-12	J. Thomas Sapienza, RTi	Added fields for joining with 
//					structure_to_parcel table.
// 2004-09-21	SAM, RTi		Remove structure_to_parcel data since
//					this does not follow the standard
//					naming convention.  Use the new
//					HydroBase_ParcelUseTSStructureToParcel
//					class instead.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase parcel_use_ts table.
*/
public class HydroBase_ParcelUseTS 
extends DMIDataObject {

protected int _parcel_num = 		DMIUtil.MISSING_INT;
protected int _div = 			DMIUtil.MISSING_INT;
protected int _cal_year = 		DMIUtil.MISSING_INT;
protected int _parcel_id = 		DMIUtil.MISSING_INT;
protected double _perimeter = 		DMIUtil.MISSING_DOUBLE;
protected double _area = 		DMIUtil.MISSING_DOUBLE;
protected String _land_use = 		DMIUtil.MISSING_STRING;
protected String _irrig_type = 		DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_ParcelUseTS() {
	super();
}

/**
Cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
*/
protected void finalize()
throws Throwable {
	_land_use = null;
	_irrig_type = null;
	
	super.finalize();
}

/**
Returns _area
@return _area
*/
public double getArea() {
	return _area;
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
Returns _irrig_type
@return _irrig_type
*/
public String getIrrig_type() {
	return _irrig_type;
}

/**
Returns _land_use
@return _land_use
*/
public String getLand_use() {
	return _land_use;
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
Returns _perimeter
@return _perimeter
*/
public double getPerimeter() {
	return _perimeter;
}

/**
Sets _area
@param area value to put into _area
*/
public void setArea(double area) {
	_area = area;
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
Sets _irrig_type
@param irrig_type value to put into _irrig_type
*/
public void setIrrig_type(String irrig_type) {
	_irrig_type = irrig_type;
}

/**
Sets _land_use
@param land_use value to put into _land_use
*/
public void setLand_use(String land_use) {
	_land_use = land_use;
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
Sets _perimeter
@param perimeter value to put into _perimeter
*/
public void setPerimeter(double perimeter) {
	_perimeter = perimeter;
}

/** 
Return a string representation of this object.
@return a string representation of this object.
*/
public String toString() {
	return "HydroBase_ParcelUseTS {"		+ "\n" + 
		"Parcel_num:         " + _parcel_num + "\n" + 
		"Div:                " + _div + "\n" + 
		"Cal_year:           " + _cal_year + "\n" + 
		"Parcel_id:          " + _parcel_id + "\n" + 
		"Perimeter:          " + _perimeter + "\n" + 
		"Area:               " + _area + "\n" + 
		"Land_use:           " + _land_use + "\n" + 
		"Irrig_type:         " + _irrig_type + "\n}";
}

}