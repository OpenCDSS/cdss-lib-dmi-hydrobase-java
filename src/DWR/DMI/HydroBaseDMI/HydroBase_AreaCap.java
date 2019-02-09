// HydroBase_AreaCap - Class to hold data from the HydroBase area_cap table.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2019 Colorado Department of Natural Resources

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

// ----------------------------------------------------------------------------
// HydroBase_AreaCap.java - Class to hold data from the HydroBase area_cap 
//	table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-05	J. Thomas Sapienza, RTi	Initial version from HB_AreaCapacity.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase area_cap table.
*/
public class HydroBase_AreaCap 
extends DMIDataObject {

protected int _ac_num = 		DMIUtil.MISSING_INT;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected int _div = 			DMIUtil.MISSING_INT;
protected int _wd = 			DMIUtil.MISSING_INT;
protected int _id = 			DMIUtil.MISSING_INT;
protected double _elevation = 		DMIUtil.MISSING_DOUBLE;
protected double _gage_height = 	DMIUtil.MISSING_DOUBLE;
protected double _surface_area = 	DMIUtil.MISSING_DOUBLE;
protected double _volume = 		DMIUtil.MISSING_DOUBLE;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_AreaCap() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_modified = null;
	
	super.finalize();
}

/**
Returns _ac_num.
@return _ac_num.
*/
public int getAc_num() {
	return _ac_num;
}

/**
Returns _structure_num.
@return _structure_num.
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _div.
@return _div.
*/

public int getDiv() {
	return _div;
}

/**
Returns _wd.
@return _wd.
*/

public int getWD() {
	return _wd;
}

/**
Returns _id.
@return _id.
*/

public int getID() {
	return _id;
}

/**
Returns _elevation.
@return _elevation.
*/

public double getElevation() {
	return _elevation;
}

/**
Returns _gage_height.
@return _gage_height.
*/
public double getGage_height() {
	return _gage_height;
}

/**
Returns _surface_area.
@return _surface_area.
*/
public double getSurface_area() {
	return _surface_area;
}

/**
Returns _volume.
@return _volume.
*/
public double getVolume() {
	return _volume;
}

/**
Returns _modified.
@return _modified.
*/
public Date getModified() {
	return _modified;
}

/**
Returns _user_num.
@return _user_num.
*/
public int getUser_num() {
	return _user_num;
}


/**
Sets _ac_num.
@param ac_num value to put in _ac_num.
*/
public void setAc_num(int ac_num) {
	 _ac_num = ac_num;
}

/**
Sets _structure_num.
@param structure_num value to put in _structure_num.
*/
public void setStructure_num(int structure_num) {
	 _structure_num = structure_num;
}

/**
Sets _div.
@param div value to put in _div.
*/
public void setDiv(int div) {
	 _div = div;
}

/**
Sets _wd.
@param wd value to put in _wd.
*/
public void setWD(int wd) {
	 _wd = wd;
}

/**
Sets _id.
@param id value to put in _id.
*/
public void setID(int id) {
	 _id = id;
}

/**
Sets _elevation.
@param elevation value to put in _elevation.
*/
public void setElevation(double elevation) {
	 _elevation = elevation;
}

/**
Sets _gage_height.
@param gage_height value to put in _gage_height.
*/
public void setGage_height(double gage_height) {
	 _gage_height = gage_height;
}

/**
Sets _surface_area.
@param surface_area value to put in _surface_area.
*/
public void setSurface_area(double surface_area) {
	 _surface_area = surface_area;
}

/**
Sets _volume.
@param volume value to put in _volume.
*/
public void setVolume(double volume) {
	 _volume = volume;
}

/**
Sets _modified.
@param modified value to put in _modified.
*/
public void setModified(Date modified) {
	 _modified = modified;
}

/**
Sets _user_num.
@param user_num value to put in _user_num.
*/
public void setUser_num(int user_num) {
	 _user_num = user_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_AreaCap {"				+ "\n" + 
		"Ac_num:        " + _ac_num			+ "\n" +
		"Structure_num: " + _structure_num 		+ "\n" +
		"Div:           " + _div 			+ "\n" +
		"WD:            " + _wd				+ "\n" +
		"ID:            " + _id				+ "\n" +
		"Elevation:     " + _elevation 			+ "\n" +
		"Gage_height:   " + _gage_height		+ "\n" +
		"Surface_area:  " + _surface_area 		+ "\n" +
		"Volume:        " + _volume 			+ "\n" +
		"Modified:      " + _modified 			+ "\n" +
		"User_num:      " + _user_num 			+ "\n}\n";

}

}
