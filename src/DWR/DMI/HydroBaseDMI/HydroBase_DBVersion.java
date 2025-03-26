// HydroBase_DBVersion - data object for HydroBase db_version table

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

import java.util.Date;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
This class provides storage for data from the HydroBase db_version table.
The data and methods in this class correspond to the table entities described in the HydroBase data dictionary.
*/
public class HydroBase_DBVersion
extends DMIDataObject {

protected int _version_num = 		DMIUtil.MISSING_INT;
protected String _version_type =	DMIUtil.MISSING_STRING;
protected int _version_id = 		DMIUtil.MISSING_INT;
protected Date _version_date = 		DMIUtil.MISSING_DATE;
protected String _version_comment =	DMIUtil.MISSING_STRING;

/**
Construct and initialize to empty strings and missing data.
*/
public HydroBase_DBVersion() {
	super();
}

public String getVersion_comment() {
	return _version_comment;
}

public Date getVersion_date() {
	return _version_date;
}

public int getVersion_id() {
	return _version_id;
}

public int getVersion_num() {
	return _version_num;
}

public String getVersion_type() {
	return _version_type;
}

public void setVersion_comment ( String version_comment )
{	_version_comment = version_comment;
}

public void setVersion_date ( Date version_date ) {
	_version_date = version_date;
}

public void setVersion_id ( int version_id ) {
	_version_id  = version_id ;
}

public void setVersion_num ( int version_num ) {
	_version_num = version_num;
}

public void setVersion_type ( String version_type ) {
	_version_type = version_type;
}

public String toString() {
	return "HydroBase_DBVersion {" 			+ "\n" +
		"version_num:    " + _version_num	+ "\n" +
		"version_type:   " + _version_type	+ "\n" +
		"version_id:     " + _version_id	+ "\n" +
		"version_date:   " + _version_date 	+ "\n" +
		"version_comment:" + _version_comment	+ "};";
}

}