// HydroBase_UserPreferences - Class to hold data from the HydroBase user_preferences table.

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
Class to store data from the HydroBase user_preferences table.
*/
public class HydroBase_UserPreferences 
extends DMIDataObject {

protected int _user_num = 	DMIUtil.MISSING_INT;
protected String _preference = 	DMIUtil.MISSING_STRING;
protected String _pref_value = 	DMIUtil.MISSING_STRING;
protected String _application = DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_UserPreferences() {
	super();
}

/**
Returns _application
@return _application
*/
public String getApplication() {
	return _application;
}

/**
Returns _pref_value
@return _pref_value
*/
public String getPref_value() {
	return _pref_value;
}

/**
Returns _preference
@return _preference
*/
public String getPreference() {
	return _preference;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Sets _application
@param application value to put into _application
*/
public void setApplication(String application) {
	_application = application;
}

/**
Sets _pref_value
@param pref_value value to put into _pref_value
*/
public void setPref_value(String pref_value) {
	_pref_value = pref_value;
}

/**
Sets _preference
@param preference value to put into _preference
*/
public void setPreference(String preference) {
	_preference = preference;
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
	return "HydroBase_UserPreferences {"		+ "\n" + 
		"User_num:    " + _user_num + "\n" +
		"Preference:  " + _preference + "\n" + 
		"Pref_value:  " + _pref_value + "\n" + 
		"Application: " + _application + "\n}\n";
}

}
