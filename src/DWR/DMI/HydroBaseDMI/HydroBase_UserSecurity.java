// ----------------------------------------------------------------------------
// HydroBase_UserSecurity.java - Class to hold data from the HydroBase 
//	user_security table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-12	J. Thomas Sapienza, RTi	Initial version from HBUserSecurity.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase user_security table.
*/
public class HydroBase_UserSecurity 
extends DMIDataObject {

protected int _user_num = 	DMIUtil.MISSING_INT;
protected String _user_name = 	DMIUtil.MISSING_STRING;
protected String _application = DMIUtil.MISSING_STRING;
protected String _login = 	DMIUtil.MISSING_STRING;
protected String _password = 	DMIUtil.MISSING_STRING;
protected String _permissions = DMIUtil.MISSING_STRING;
protected int _rolodex_num = 	DMIUtil.MISSING_INT;
protected int _team_num = 	DMIUtil.MISSING_INT;
protected int _supervisor = 	DMIUtil.MISSING_INT;
protected int _active = 	DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_UserSecurity() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_user_name = null;
	_application = null;
	_login = null;
	_password = null;
	_permissions = null;
	
	super.finalize();
}

/**
Returns _active
@return _active
*/
public int getActive() {
	return _active;
}

/**
Returns _application
@return _application
*/
public String getApplication() {
	return _application;
}

/**
Returns _login
@return _login
*/
public String getLogin() {
	return _login;
}

/**
Returns _password
@return _password
*/
public String getPassword() {
	return _password;
}

/**
Returns _permissions
@return _permissions
*/
public String getPermissions() {
	return _permissions;
}

/**
Returns _rolodex_num
@return _rolodex_num
*/
public int getRolodex_num() {
	return _rolodex_num;
}

/**
Returns _supervisor
@return _supervisor
*/
public int getSupervisor() {
	return _supervisor;
}

/**
Returns _team_num
@return _team_num
*/
public int getTeam_num() {
	return _team_num;
}

/**
Returns _user_name
@return _user_name
*/
public String getUser_name() {
	return _user_name;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Sets _active
@param active value to put into _active
*/
public void setActive(int active) {
	 _active = active;
}

/**
Sets _application
@param application value to put into _application
*/
public void setApplication(String application) {
	 _application = application;
}

/**
Sets _login
@param login value to put into _login
*/
public void setLogin(String login) {
	 _login = login;
}

/**
Sets _password
@param password value to put into _password
*/
public void setPassword(String password) {
	 _password = password;
}

/**
Sets _permissions
@param permissions value to put into _permissions
*/
public void setPermissions(String permissions) {
	 _permissions = permissions;
}

/**
Sets _rolodex_num
@param rolodex_num value to put into _rolodex_num
*/
public void setRolodex_num(int rolodex_num) {
	 _rolodex_num = rolodex_num;
}

/**
Sets _supervisor
@param supervisor value to put into _supervisor
*/
public void setSupervisor(int supervisor) {
	 _supervisor = supervisor;
}

/**
Sets _team_num
@param team_num value to put into _team_num
*/
public void setTeam_num(int team_num) {
	 _team_num = team_num;
}

/**
Sets _user_name
@param user_name value to put into _user_name
*/
public void setUser_name(String user_name) {
	 _user_name = user_name;
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
	return "HydroBase_UserSecurity {"		+ "\n" + 
		"User_num:    " + _user_num + "\n" +
		"User_name:   " + _user_name + "\n" +
		"Application: " + _application + "\n" +
		"Login:       " + _login + "\n" +
		"Password:    " + _password + "\n" +
		"Permissions: " + _permissions + "\n" +
		"Rolodex_num: " + _rolodex_num + "\n" +
		"Team_num:    " + _team_num + "\n" +
		"Supervisor:  " + _supervisor + "\n" +
		"Active:      " + _active + "\n}\n";
}

}
