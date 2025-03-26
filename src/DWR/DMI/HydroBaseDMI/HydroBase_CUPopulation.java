// HydroBase_CUPopulation - Class to hold data from the HydroBase CUPopulation table/view.

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