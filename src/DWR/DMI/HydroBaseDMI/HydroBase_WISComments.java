// HydroBase_WISComments - Class to hold data from the HydroBase wis_comments table.

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
	if (DMIUtil.isMissing(_wis_num) && HydroBase_Util.isMissing(_wis_num) && DMIUtil.isMissing(wc._wis_num) && HydroBase_Util.isMissing(wc._wis_num)) {
		// placeholder
	}
	else if (DMIUtil.isMissing(_wis_num) && HydroBase_Util.isMissing(_wis_num)) {
		return -1;
	}
	else if (DMIUtil.isMissing(wc._wis_num) && HydroBase_Util.isMissing(wc._wis_num)) {
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
