// ----------------------------------------------------------------------------
// HydroBase_Mapfile.java - Class to hold data from the HydroBase mapfile table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-10	J. Thomas Sapienza, RTi	Initial version from HBMapFile.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase mapfile table.
*/
public class HydroBase_Mapfile 
extends DMIDataObject {

protected int _mapfile_num = 		DMIUtil.MISSING_INT;
protected int _structure_num = 		DMIUtil.MISSING_INT;
protected int _map_file_no = 		DMIUtil.MISSING_INT;
protected String _map_suffix = 		DMIUtil.MISSING_STRING;
protected String _map_supp_stmt = 	DMIUtil.MISSING_STRING;
protected String _map_file = 		DMIUtil.MISSING_STRING;
protected Date _map_file_date = 	DMIUtil.MISSING_DATE;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_Mapfile() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_map_suffix = null;
	_map_supp_stmt = null;
	_map_file = null;
	_map_file_date = null;
	_modified = null;
	super.finalize();
}

/**
Returns _map_file
@return _map_file
*/
public String getMap_file() {
	return _map_file;
}

/**
Returns _map_file_date
@return _map_file_date
*/
public Date getMap_file_date() {
	return _map_file_date;
}

/**
Returns _map_file_no
@return _map_file_no
*/
public int getMap_file_no() {
	return _map_file_no;
}

/**
Returns _map_suffix
@return _map_suffix
*/
public String getMap_suffix() {
	return _map_suffix;
}

/**
Returns _map_supp_stmt
@return _map_supp_stmt
*/
public String getMap_supp_stmt() {
	return _map_supp_stmt;
}

/**
Returns _mapfile_num
@return _mapfile_num
*/
public int getMapfile_num() {
	return _mapfile_num;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
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
Sets _map_file
@param map_file value to put into _map_file
*/
public void setMap_file(String map_file) {
	_map_file = map_file;
}

/**
Sets _map_file_date
@param map_file_date value to put into _map_file_date
*/
public void setMap_file_date(Date map_file_date) {
	_map_file_date = map_file_date;
}

/**
Sets _map_file_no
@param map_file_no value to put into _map_file_no
*/
public void setMap_file_no(int map_file_no) {
	_map_file_no = map_file_no;
}

/**
Sets _map_suffix
@param map_suffix value to put into _map_suffix
*/
public void setMap_suffix(String map_suffix) {
	_map_suffix = map_suffix;
}

/**
Sets _map_supp_stmt
@param map_supp_stmt value to put into _map_supp_stmt
*/
public void setMap_supp_stmt(String map_supp_stmt) {
	_map_supp_stmt = map_supp_stmt;
}

/**
Sets _mapfile_num
@param mapfile_num value to put into _mapfile_num
*/
public void setMapfile_num(int mapfile_num) {
	_mapfile_num = mapfile_num;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
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
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_Mapfile {"		+ "\n" + 
		"Mapfile_num:   " + _mapfile_num + "\n" + 
		"Structure_num: " + _structure_num + "\n" + 
		"Map_file_no:   " + _map_file_no + "\n" + 
		"Map_suffix:    " + _map_suffix + "\n" + 
		"Map_supp_stmt: " + _map_supp_stmt + "\n" + 
		"Map_file:      " + _map_file + "\n" + 
		"Map_file_date: " + _map_file_date + "\n" + 
		"Modified:      " + _modified + "\n" + 
		"User_num:      " + _user_num + "\n}\n";
}

}
