// HydroBase_Contact - Class to hold data from the HydroBase contact table.

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
Class to store data from the HydroBase contact table.
*/
public class HydroBase_Contact
extends DMIDataObject {

protected int _contact_num = 		DMIUtil.MISSING_INT;
protected int _rolodex_num = 		DMIUtil.MISSING_INT;
protected String _contact_type = 	DMIUtil.MISSING_STRING;
protected String _phone_number = 	DMIUtil.MISSING_STRING;
protected String _phone_ext = 		DMIUtil.MISSING_STRING;
protected String _contact_text = 	DMIUtil.MISSING_STRING;
protected String _first_contact =	DMIUtil.MISSING_STRING;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_Contact() {
	super();
}

/**
Returns _contact_num
@return _contact_num
*/
public int getContact_num() {
	return _contact_num;
}

/**
Returns _contact_text
@return _contact_text
*/
public String getContact_text() {
	return _contact_text;
}

/**
Returns _contact_type
@return _contact_type
*/
public String getContact_type() {
	return _contact_type;
}

/**
Returns _first_contact
@return _first_contact
*/
public String getFirst_contact() {
	return _first_contact;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _phone_ext
@return _phone_ext
*/
public String getPhone_ext( ){
	return _phone_ext;
}

/**
Returns _phone_number
@return _phone_number
*/
public String getPhone_number() {
	return _phone_number;
}

/**
Returns _rolodex_num
@return _rolodex_num
*/
public int getRolodex_num() {
	return _rolodex_num;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Sets _contact_num
@param contact_num value to put into _contact_num
*/
public void setContact_num(int contact_num) {
	 _contact_num = contact_num;
}

/**
Sets _contact_text
@param contact_text value to put into _contact_text
*/
public void setContact_text(String contact_text) {
	 _contact_text = contact_text;
}

/**
Sets _contact_type
@param contact_type value to put into _contact_type
*/
public void setContact_type(String contact_type) {
	 _contact_type = contact_type;
}

/**
Sets _first_contact
@param first_contact value to put into _first_contact
*/
public void setFirst_contact(String first_contact) {
	 _first_contact = first_contact;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	 _modified = modified;
}

/**
Sets _phone_ext
@param phone_ext value to put into _phone_ext
*/
public void setPhone_ext(String phone_ext){
	 _phone_ext = phone_ext;
}

/**
Sets _phone_number
@param phone_number value to put into _phone_number
*/
public void setPhone_number(String phone_number) {
	 _phone_number = phone_number;
}

/**
Sets _rolodex_num
@param rolodex_num value to put into _rolodex_num
*/
public void setRolodex_num(int rolodex_num) {
	 _rolodex_num = rolodex_num;
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
	return "HydroBase_Contact {"				+ "\n" +
		"Contact_num:   " + _contact_num + "\n" +
		"Rolodex_num:   " + _rolodex_num + "\n" +
		"Contact_type:  '" + _contact_type + "'\n" +
		"Phone_number:  '" + _phone_number + "'\n" +
		"Phone_ext:     '" + _phone_ext + "'\n" +
		"Contact_text:  '" + _contact_text + "'\n" +
		"First_contact: '" + _first_contact + "'\n" +
		"Modified:      " + _modified + "\n" +
		"User_num:      " + _user_num + "\n}\n";
}

}