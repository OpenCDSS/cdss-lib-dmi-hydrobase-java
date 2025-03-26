// HydroBase_DamOutlet - Class to hold data from the HydroBase dam_outlet table.

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

import java.util.Date;

/**
Class to store data from the HydroBase dam_outlet table.
*/
public class HydroBase_DamOutlet
extends DMIDataObject {

protected int _outlet_num = 	DMIUtil.MISSING_INT;
protected int _structure_num = 	DMIUtil.MISSING_INT;
protected int _damid = 		DMIUtil.MISSING_INT;
protected String _outlet_name = DMIUtil.MISSING_STRING;
protected String _type = 	DMIUtil.MISSING_STRING;
protected double _diameter = 	DMIUtil.MISSING_DOUBLE;
protected double _length = 	DMIUtil.MISSING_DOUBLE;
protected String _description = DMIUtil.MISSING_STRING;
protected double _capacity = 	DMIUtil.MISSING_DOUBLE;
protected Date _modified = 	DMIUtil.MISSING_DATE;
protected int _user_num = 	DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_DamOutlet() {
	super();
}

/**
Returns _capacity
@return _capacity
*/
public double getCapacity() {
	return _capacity;
}

/**
Returns _damid
@return _damid
*/
public int getDamid() {
	return _damid;
}

/**
Returns _description
@return _description
*/
public String getDescription() {
	return _description;
}

/**
Returns _diameter
@return _diameter
*/
public double getDiameter() {
	return _diameter;
}

/**
Returns _length
@return _length
*/
public double getLength() {
	return _length;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _outlet_name
@return _outlet_name
*/
public String getOutlet_name() {
	return _outlet_name;
}

/**
Returns _outlet_num
@return _outlet_num
*/
public int getOutlet_num() {
	return _outlet_num;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _type
@return _type
*/
public String getType() {
	return _type;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}


/**
Sets _capacity
@param capacity value to put into _capacity
*/
public void setCapacity(double capacity) {
	_capacity = capacity;
}

/**
Sets _damid
@param damid value to put into _damid
*/
public void setDamid(int damid) {
	_damid = damid;
}

/**
Sets _description
@param description value to put into _description
*/
public void setDescription(String description) {
	_description = description;
}

/**
Sets _diameter
@param diameter value to put into _diameter
*/
public void setDiameter(double diameter) {
	_diameter = diameter;
}

/**
Sets _length
@param length value to put into _length
*/
public void setLength(double length) {
	_length = length;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _outlet_name
@param outlet_name value to put into _outlet_name
*/
public void setOutlet_name(String outlet_name) {
	_outlet_name = outlet_name;
}

/**
Sets _outlet_num
@param outlet_num value to put into _outlet_num
*/
public void setOutlet_num(int outlet_num) {
	_outlet_num = outlet_num;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _type
@param type value to put into _type
*/
public void setType(String type) {
	_type = type;
}

/**
Sets _user_num
@param user_num value to put into _user_num
*/
public void setUser_num(int user_num) {
	_user_num = user_num;
}


/**
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_DamOutlet {"		+ "\n" +
		"Outlet_num:    " + _outlet_num + "\n" +
		"Structure_num: " + _structure_num + "\n" +
		"Damid:         " + _damid + "\n" +
		"Outlet_name:   '" + _outlet_name + "'\n" +
		"Type:          '" + _type + "'\n" +
		"Diameter:      " + _diameter + "\n" +
		"Length:        " + _length + "\n" +
		"Description:   '" + _description + "'\n" +
		"Capacity:      " + _capacity + "\n" +
		"Modified:      " + _modified + "\n" +
		"User_num:      " + _user_num + "\n}\n";
}

}
