// ----------------------------------------------------------------------------
// HydroBase_StructureSmallDam.java - Class to hold data from the HydroBase 
//	small_dam table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-11	J. Thomas Sapienza, RTi	Initial version from HBSmallDam.
// 2003-02-24	JTS, RTi		- Corrected error in finalize() so that 
//					  super.finalize() gets called.
//					- HydroBase_SmallDam now extends
//					  HydroBase_Structure and was renamed
//					  accordingly.
// 2004-02-09	JTS, RTi		Renamed to match the naming scheme
//					for classes that extend other classes.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase small_dam table.
*/
public class HydroBase_StructureSmallDam 
extends HydroBase_Structure {

protected int _structure_num = 		DMIUtil.MISSING_INT;
protected int _receipt = 		DMIUtil.MISSING_INT;
protected String _small_dam_type = 	DMIUtil.MISSING_STRING;
protected Date _appl_date = 		DMIUtil.MISSING_DATE;
protected Date _compl_date = 		DMIUtil.MISSING_DATE;
protected String _title_no = 		DMIUtil.MISSING_STRING;
protected int _outlet_size = 		DMIUtil.MISSING_INT;
protected String _outlet_type = 	DMIUtil.MISSING_STRING;
protected int _height = 		DMIUtil.MISSING_INT;
protected int _spillway_height = 	DMIUtil.MISSING_INT;
protected int _spillway_width = 	DMIUtil.MISSING_INT;
protected int _drain_area = 		DMIUtil.MISSING_INT;
protected double _tank_capy = 		DMIUtil.MISSING_DOUBLE;
protected Date _modified = 		DMIUtil.MISSING_DATE;
protected int _user_num = 		DMIUtil.MISSING_INT;

/**
Constructor.
*/
public HydroBase_StructureSmallDam() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_small_dam_type = null;
	_appl_date = null;
	_compl_date = null;
	_title_no = null;
	_outlet_type = null;
	_modified = null;
	
	super.finalize();
}

/**
Returns _appl_date
@return _appl_date
*/
public Date getAppl_date() {
	return _appl_date;
}

/**
Returns _compl_date
@return _compl_date
*/
public Date getCompl_date() {
	return _compl_date;
}

/**
Returns _drain_area
@return _drain_area
*/
public int getDrain_area() {
	return _drain_area;
}

/**
Returns _height
@return _height
*/
public int getHeight() {
	return _height;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {
	return _modified;
}

/**
Returns _outlet_size
@return _outlet_size
*/
public int getOutlet_size() {
	return _outlet_size;
}

/**
Returns _outlet_type
@return _outlet_type
*/
public String getOutlet_type() {
	return _outlet_type;
}

/**
Returns _receipt
@return _receipt
*/
public int getReceipt() {
	return _receipt;
}

/**
Returns _small_dam_type
@return _small_dam_type
*/
public String getSmall_dam_type() {
	return _small_dam_type;
}

/**
Returns _spillway_height
@return _spillway_height
*/
public int getSpillway_height() {
	return _spillway_height;
}

/**
Returns _spillway_width
@return _spillway_width
*/
public int getSpillway_width() {
	return _spillway_width;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _tank_capy
@return _tank_capy
*/
public double getTank_capy() {
	return _tank_capy;
}

/**
Returns _title_no
@return _title_no
*/
public String getTitle_no() {
	return _title_no;
}

/**
Returns _user_num
@return _user_num
*/
public int getUser_num() {
	return _user_num;
}

/**
Sets _appl_date
@param appl_date value to put into _appl_date
*/
public void setAppl_date(Date appl_date) {
	_appl_date = appl_date;
}

/**
Sets _compl_date
@param compl_date value to put into _compl_date
*/
public void setCompl_date(Date compl_date) {
	_compl_date = compl_date;
}

/**
Sets _drain_area
@param drain_area value to put into _drain_area
*/
public void setDrain_area(int drain_area) {
	_drain_area = drain_area;
}

/**
Sets _height
@param height value to put into _height
*/
public void setHeight(int height) {
	_height = height;
}

/**
Sets _modified
@param modified value to put into _modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _outlet_size
@param outlet_size value to put into _outlet_size
*/
public void setOutlet_size(int outlet_size) {
	_outlet_size = outlet_size;
}

/**
Sets _outlet_type
@param outlet_type value to put into _outlet_type
*/
public void setOutlet_type(String outlet_type) {
	_outlet_type = outlet_type;
}

/**
Sets _receipt
@param receipt value to put into _receipt
*/
public void setReceipt(int receipt) {
	_receipt = receipt;
}

/**
Sets _small_dam_type
@param small_dam_type value to put into _small_dam_type
*/
public void setSmall_dam_type(String small_dam_type) {
	_small_dam_type = small_dam_type;
}

/**
Sets _spillway_height
@param spillway_height value to put into _spillway_height
*/
public void setSpillway_height(int spillway_height) {
	_spillway_height = spillway_height;
}

/**
Sets _spillway_width
@param spillway_width value to put into _spillway_width
*/
public void setSpillway_width(int spillway_width) {
	_spillway_width = spillway_width;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _tank_capy
@param tank_capy value to put into _tank_capy
*/
public void setTank_capy(double tank_capy) {
	_tank_capy = tank_capy;
}

/**
Sets _title_no
@param title_no value to put into _title_no
*/
public void setTitle_no(String title_no) {
	_title_no = title_no;
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
	return "HydroBase_StructureSmallDam {"		+ "\n" + 
		"Structure_num:   " + _structure_num + "\n" + 
		"Receipt:         " + _receipt + "\n" + 
		"Small_dam_type:  " + _small_dam_type + "\n" + 
		"Appl_date:       " + _appl_date + "\n" + 
		"Compl_date:      " + _compl_date + "\n" + 
		"Title_no:        " + _title_no + "\n" + 
		"Outlet_size:     " + _outlet_size + "\n" + 
		"Outlet_type:     " + _outlet_type + "\n" + 
		"Height:          " + _height + "\n" + 
		"Spillway_height: " + _spillway_height + "\n" + 
		"Spillway_width:  " + _spillway_width + "\n" + 
		"Drain_area:      " + _drain_area + "\n" + 
		"Tank_capy:       " + _tank_capy + "\n" + 
		"Modified:        " + _modified + "\n" + 
		"User_num:        " + _user_num + "\n;}\n"
		+ super.toString();
}

}
