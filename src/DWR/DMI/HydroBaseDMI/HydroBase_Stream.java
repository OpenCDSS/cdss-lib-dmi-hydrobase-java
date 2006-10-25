// ----------------------------------------------------------------------------
// HydroBase_Stream.java - Class to hold data from the HydroBase stream table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-11	J. Thomas Sapienza, RTi	Initial version from
//					HBStream.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the the HydroBase stream table.
*/
public class HydroBase_Stream 
extends DMIDataObject {

protected int _stream_num = 	DMIUtil.MISSING_INT;
protected int _stream_no = 	DMIUtil.MISSING_INT;
protected int _str_trib_to = 	DMIUtil.MISSING_INT;
protected String _stream_name = DMIUtil.MISSING_STRING;
protected double _str_mile = 	DMIUtil.MISSING_DOUBLE;

/**
Constructor.
*/
public HydroBase_Stream() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_stream_name = null;

	super.finalize();
}

/**
Returns _str_mile
@return _str_mile
*/
public double getStr_mile() {
	return _str_mile;
}

/**
Returns _str_trib_to
@return _str_trib_to
*/
public int getStr_trib_to() {
	return _str_trib_to;
}

/**
Returns _stream_name
@return _stream_name
*/
public String getStream_name() {
	return _stream_name;
}

/**
Returns _stream_no
@return _stream_no
*/
public int getStream_no() {
	return _stream_no;
}

/**
Returns _stream_num
@return _stream_num
*/
public int getStream_num() {
	return _stream_num;
}

/**
Sets _str_mile
@param str_mile value to put _str_mile
*/
public void setStr_mile(double str_mile) {
	_str_mile = str_mile;
}

/**
Sets _str_trib_to
@param str_trib_to value to put _str_trib_to
*/
public void setStr_trib_to(int str_trib_to) {
	_str_trib_to = str_trib_to;
}

/**
Sets _stream_name
@param stream_name value to put _stream_name
*/
public void setStream_name(String stream_name) {
	_stream_name = stream_name;
}

/**
Sets _stream_no
@param stream_no value to put _stream_no
*/
public void setStream_no(int stream_no) {
	_stream_no = stream_no;
}

/**
Sets _stream_num
@param stream_num value to put _stream_num
*/
public void setStream_num(int stream_num) {
	_stream_num = stream_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_Stream {"		+ "\n" + 
		"Stream_num:  " + _stream_num + "\n" + 
		"Stream_no:   " + _stream_no + "\n" + 
		"Str_trib_to: " +_str_trib_to + "\n" + 
		"Stream_name: " + _stream_name + "\n" + 
		"Str_mile:    " + _str_mile + "\n}\n";
}

}
