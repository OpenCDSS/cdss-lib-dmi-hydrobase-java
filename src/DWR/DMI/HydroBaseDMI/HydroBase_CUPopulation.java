// ----------------------------------------------------------------------------
// HydroBase_CUPopulation.java - Class to hold data from the
//	HydroBase CUPopulation table/view.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2006-10-31	Steven A. Malers, RTi	Initial version from
//					HydroBase_AgriculturalCASSCropStats.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase CUPopulation table/view.
*/
public class HydroBase_CUPopulation 
extends DMIDataObject {

protected String _area_type = 	DMIUtil.MISSING_STRING;
protected String _area_name = 	DMIUtil.MISSING_STRING;
protected int _cal_year = 	DMIUtil.MISSING_INT;
protected String _pop_type = 	DMIUtil.MISSING_STRING;
protected int _population = 	DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_CUPopulation() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_area_type = null;
	_area_name = null;
	_pop_type = null;
	super.finalize();
}

/**
Returns _area_name.
@return _area_name.
*/
public String getArea_name() {
	return _area_name;
}

/**
Returns _area_type.
@return _area_type.
*/
public String getArea_type() {
	return _area_type;
}

/**
Returns _cal_year.
@return _cal_year.
*/
public int getCal_year() {
	return _cal_year;
}

/**
Returns _pop_type.
@return _pop_type.
*/
public String getPop_type() {
	return _pop_type;
}

/**
Returns _population.
@return _population.
*/
public int getPopulation() {
	return _population;
}

/**
Sets _area_name.
@param area_name value to put in _area_name.
*/
public void setArea_name(String area_name) {
	_area_name = area_name;
}

/**
Sets _area_type.
@param area_type value to put in _area_type.
*/
public void setArea_type(String area_type) {
	_area_type = area_type;
}

/**
Sets _cal_year.
@param cal_year value to put in _cal_year.
*/
public void setCal_year(int cal_year) {
	_cal_year = cal_year;
}

/**
Sets _pop_type.
@param pop_type value to put in _pop_type.
*/
public void setPop_type(String pop_type) {
	_pop_type = pop_type;
}

/**
Sets _population.
@param population value to put in _population.
*/
public void setPopulation(int population) {
	_population = population;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_AgriculturalCASSLivestockStats {"		+ "\n" +
		"Area_type: '" 	+ _area_type	+ "'\n" + 
		"Area_name: '" 	+ _area_name 	+ "'\n" + 
		"Cal_year: " 	+ _cal_year 	+ "\n" + 
		"Pop_type: '" 	+ _pop_type 	+ "'\n" + 
		"Population: "	+ _population 	+ "\n";
}

}
