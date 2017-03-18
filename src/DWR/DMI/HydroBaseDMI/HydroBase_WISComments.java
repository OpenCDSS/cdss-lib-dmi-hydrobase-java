// ----------------------------------------------------------------------------
// HydroBase_WISComments.java - Class to hold data from the HydroBase 
//	wis_comments table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-12	J. Thomas Sapienza, RTi	Initial version from HBWISComments.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2005-07-18	JTS, RTi		Added comparable for sorting.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Date;

/**
Class to store data from the HydroBase wis_comments table.
*/
public class HydroBase_WISComments 
extends DMIDataObject 
implements Comparable<HydroBase_WISComments> {

protected int _wis_num = 	DMIUtil.MISSING_INT;
protected Date _set_date = 	DMIUtil.MISSING_DATE;
protected Date _archive_date = 	DMIUtil.MISSING_DATE;
protected String _comment = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_WISComments() {
	super();
}

public int compareTo(HydroBase_WISComments wc) {
	if (DMIUtil.isMissing(_wis_num) && DMIUtil.isMissing(wc._wis_num)) {
		// placeholder
	}
	else if (DMIUtil.isMissing(_wis_num)) {
		return -1;
	}
	else if (DMIUtil.isMissing(wc._wis_num)) {
		return 1;
	}
	else if (_wis_num < wc._wis_num) {
		return -1;
	}
	else if (_wis_num > wc._wis_num) {
		return 1;
	}

	if (DMIUtil.isMissing(_set_date) && DMIUtil.isMissing(wc._set_date)) {
		// placeholder
	}
	else if (DMIUtil.isMissing(_set_date)) {
		return -1;
	}
	else if (DMIUtil.isMissing(wc._set_date)) {
		return 1;
	}
	else {
		return _set_date.compareTo(wc._set_date);
	}

	return 0;
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_set_date = null;
	_archive_date = null;
	_comment = null;
	
	super.finalize();
}

/**
Returns _archive_date
@return _archive_date
*/
public Date getArchive_date() {
	return _archive_date;
}

/**
Returns _comment
@return _comment
*/
public String getComment() {
	return _comment;
}

/**
Returns _set_date
@return _set_date
*/
public Date getSet_date() {
	return _set_date;
}

/**
Returns _wis_num
@return _wis_num
*/
public int getWis_num() {
	return _wis_num;
}

/**
Sets _archive_date
@param archive_date value to put into _archive_date
*/
public void setArchive_date(Date archive_date) {
	_archive_date = archive_date;
}

/**
Sets _comment
@param comment value to put into _comment
*/
public void setComment(String comment) {
	_comment = comment;
}

/**
Sets _set_date
@param set_date value to put into _set_date
*/
public void setSet_date(Date set_date) {
	_set_date = set_date;
}

/**
Sets _wis_num
@param wis_num value to put into _wis_num
*/
public void setWis_num(int wis_num) {
	_wis_num = wis_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_WISComments {"		+ "\n" + 
		"Wis_num:      " + _wis_num + "\n" + 
		"Set_date:     " + _set_date + "\n" + 
		"Archive_date: " + _archive_date + "\n" + 
		"Comment:      " + _comment + "\n}\n";
}

}
