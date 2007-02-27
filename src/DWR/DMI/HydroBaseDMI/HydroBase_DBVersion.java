//------------------------------------------------------------------------------
// HydroBase_DBVersion - data object for HydroBase db_version table
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// Notes:	(1)	This class has no knowledge of the database itself
//			(aside from its own data members), and there is no
//			knowledge of the connection with the database.
//------------------------------------------------------------------------------
// History:
// 
// 2002-10-27	Steven A. Malers, RTi	Initial version - copy
//					HydroBase_Crop.java and modify.
// 2003-01-05	SAM, RTi		Update based on changes to the DMI
//					package.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//------------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Date;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
This class provides storage for data from the HydroBase db_version table.
The data and methods in this class correspond to the table entities described
in the HydroBase data dictionary.
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
public HydroBase_DBVersion()
{	super();
}

/**
Finalize before garbage collection.
@exception Throwable if there is an error.
*/
protected void finalize ()
throws Throwable
{	_version_type = null;
	_version_date = null;
	_version_comment = null;
	super.finalize();
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

} // End of Hydrobase_DBVersion
