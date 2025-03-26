// HydroBase_Crop - Class to store data from the HydroBase crop table.

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
This class provides storage for data from the HydroBase crop table.
The data and methods in this class correspond to the table entities described in the HydroBase data dictionary.
Use the HBCropQuery class to query instances of this class.
*/
public class HydroBase_Crop
extends DMIDataObject {

protected String _id = 			DMIUtil.MISSING_STRING;
protected String _landuse = 		DMIUtil.MISSING_STRING;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected int _year = 			DMIUtil.MISSING_INT;
protected double _acres_total = 	DMIUtil.MISSING_DOUBLE;

/**
Construct and initialize to empty strings and missing data from HBData.
*/
public HydroBase_Crop() {
	super();
}

/**
@deprecated use getAcres_total().
*/
public double getacres_total() {
	return _acres_total;
}

public double getAcres_total() {
	return _acres_total;
}

public String getID() {
	return _id;
}

/**
@deprecated use getLanduse().
*/
public String getlanduse() {
	return _landuse;
}

public String getLanduse() {
	return _landuse;
}

/**
@deprecated use getStructure_num().
*/
public int getstructure_num() {
	return _structure_num;
}

public int getStructure_num() {
	return _structure_num;
}

/**
@deprecated use getYear().
*/
public int getyear() {
	return _year;
}

public int getYear() {
	return _year;
}

/**
@deprecated use setAcres_total().
*/
public void setacres_total(double acres_total) {
	_acres_total = acres_total;
}

public void setAcres_total(double acres_total) {
	_acres_total = acres_total;
}

public void setID(String id) {
	_id = id;
}

/**
@deprecated use setLanduse().
*/
public void setlanduse(String landuse) {
	_landuse = landuse;
}

public void setLanduse(String landuse) {
	_landuse = landuse;
}

/**
@deprecated use setStructure_num().
*/
public void setstructure_num(int structure_num) {
	_structure_num = structure_num;
}

public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
@deprecated use setYear().
*/
public void setyear(int year) {
	_year = year;
}

public void setYear(int year) {
	_year = year;
}


public String toString() {
	return "HydroBase_Crop{" 			+ "\n" +
		"id:            " + _id		+ "\n" +
		"structure_num: " + _structure_num	+ "\n" +
		"landuse:       " + _landuse		+ "\n" +
		"year:          " + _year 		+ "\n" +
		"acres_total:   " + _acres_total	+ "};";
}

}