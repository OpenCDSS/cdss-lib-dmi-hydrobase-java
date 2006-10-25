// ----------------------------------------------------------------------------
// HydroBase_GroundWaterWellsGeophlogs.java - Class to hold data from 
//	the HydroBase geophlogs table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-17	J. Thomas Sapienza, RTi	Initial version.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2005-06-21	JTS, RTi		Added variables for new view queries.
// 2005-06-29	JTS, RTi		Now extends 
//					HydroBase_GroundWaterWellsView
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Date;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase geophlogs table.
*/
public class HydroBase_GroundWaterWellsGeophlogs 
extends HydroBase_GroundWaterWellsView {

protected int _structure_num =	DMIUtil.MISSING_INT;
protected int _logid = 		DMIUtil.MISSING_INT;
protected int _glogtop = 	DMIUtil.MISSING_INT;
protected int _glogbase = 	DMIUtil.MISSING_INT;
protected int _well_num = 	DMIUtil.MISSING_INT;
protected String _aquifer_name =	DMIUtil.MISSING_STRING;
protected int _glogthickness = 	DMIUtil.MISSING_INT;
protected boolean _orig_1986 = 	false;
protected String _comment = 	DMIUtil.MISSING_STRING;
protected Date _modified = 	DMIUtil.MISSING_DATE;
protected String _user = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_GroundWaterWellsGeophlogs() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_aquifer_name = null;
	_comment = null;
	_modified = null;
	_user = null;
	super.finalize();
}

/**
Returns _aquifer_name
@return _aquifer_name
*/
public String getAquifer_name() {
	return _aquifer_name;
}

/**
Returns _comment
@return _comment
*/
public String getComment() {
	return _comment;
}

/**
Returns _glogbase
@return _glogbase
*/
public int getGlogbase() {
	return _glogbase;
}

/**
Returns _glogthickness
@return _glogthickness
*/
public int getGlogthickness() {
	return _glogthickness;
}

/**
Returns _glogtop
@return _glogtop
*/
public int getGlogtop() {
	return _glogtop;
}

/**
Returns _logid
@return _logid
*/
public int getLogid() {
	return _logid;
}

/**
Returns _modified
@return _modified
*/
public Date getModified() {	
	return _modified;
}

/**
Returns _orig_1986
@return _orig_1986
*/
public boolean getOrig_1986() {
	return _orig_1986;
}

/**
Returns _structure_num
@return _structure_num
*/
public int getStructure_num() {
	return _structure_num;
}

/**
Returns _user
@return _user
*/
public String getUser() {
	return _user;
}

/**
Returns _well_num
@return well_num
*/
public int getWell_num() {
	return _well_num;
}

/**
Sets _aquifer_name
@param aquifer_name
*/
public void setAquifer_name(String aquifer_name) {
	_aquifer_name = aquifer_name;
}

/**
Sets _comment
@param comment 
*/
public void setComment(String comment) {
	_comment = comment;
}

/**
Sets _glogbase
@param glogbase value to put into _glogbase
*/
public void setGlogbase(int glogbase) {
	_glogbase = glogbase;
}

/**
Sets _glogthickness
@param glogthickness
*/
public void setGlogthickness(int glogthickness) {
	_glogthickness = glogthickness;
}

/**
Sets _glogtop
@param glogtop value to put into _glogtop
*/
public void setGlogtop(int glogtop) {
	_glogtop = glogtop;
}

/**
Sets _logid
@param logid value to put into _logid
*/
public void setLogid(int logid) {
	_logid = logid;
}

/**
Sets _modified
@param modified
*/
public void setModified(Date modified) {
	_modified = modified;
}

/**
Sets _orig_1986
@param orig_1986
*/
public void setOrig_1986(boolean orig_1986) {
	_orig_1986 = orig_1986;
}

/**
Sets _structure_num
@param structure_num value to put into _structure_num
*/
public void setStructure_num(int structure_num) {
	_structure_num = structure_num;
}

/**
Sets _user
@param user
*/
public void setUser(String user) {
	_user = user;
}

/**
Sets _well_num
@param well_num
*/
public void setWell_num(int well_num) {	
	_well_num = well_num;
}

}
