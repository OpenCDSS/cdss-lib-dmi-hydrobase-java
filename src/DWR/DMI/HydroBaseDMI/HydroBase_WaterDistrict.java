// ----------------------------------------------------------------------------
// HydroBase_WaterDistrict - class for HydroBase WaterDistrict objects to hold
//	data from the HydroBase water_district table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2002-10-27	Steven A. Malers, RTi	Initial version.  Split code out of
//					HydroBaseDMI and review functionality.
//					This class should be an improved version
//					of the older HBWaterDistrict class.
//					For now only include the static methods
//					(no water_district table data).
// 2002-11-08	SAM, RTi		Change the parseWDID() method to return
//					an array of int rather than String to
//					streamline the use of the returned
//					values.  Also overload to take an
//					int[] as input to reuse the array.
// 2003-02-13	J. Thomas Sapienza, RTi	Added the data members and get and set
//					methods.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2007-02-26	SAM, RTi		Clean up based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Vector;

import RTi.Util.Message.Message;
import RTi.Util.String.StringUtil;

/**
Class to store data from the HydroBase water_district table.
*/
public class HydroBase_WaterDistrict 
extends DMIDataObject {

protected int _wd = DMIUtil.MISSING_INT;
protected int _div = DMIUtil.MISSING_INT;
protected String _wd_name = DMIUtil.MISSING_STRING;
protected String _tab_trib = DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_WaterDistrict() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_wd_name = null;
	_tab_trib = null;
	
	super.finalize();
}

/**
Form a WDID string from its parts.  The default is a 7-character id.
@param wd Water district.
@param id Identifier.
*/
public static String formWDID (int wd, int id) {
	return formWDID (7, wd, id);
}

/**
Form a WDID string from its parts.
@param length Length of the WDID.  If less than or equal to 2, the default is 7.
@param wd Water district.
@param id Identifier.
*/
public static String formWDID ( int length, int wd, int id ) {
	Integer iwd, iid;

	iwd = new Integer (wd);
	iid = new Integer (id);
	Vector v = new Vector (2,1);
	v.addElement(iwd);
	v.addElement(iid);
	if ((length <= 2) || (length >= 12)) {
		return StringUtil.formatString (v, "%02d%05d");
	}
	else {
		return StringUtil.formatString (v, "%02d%0" + (length-2)+"d");
	}
}
 
/**
Form a WDID string from its parts.  The default is a 7-character id.
@param wd Water district
@param id Identifier
*/
public static String formWDID ( String wd, String id )
{	Vector v = new Vector (2,1);
	v.addElement(wd);
	v.addElement(id);
	String wdid = StringUtil.formatString (v, "%02d%05d");
	return wdid;
}

/**
Form a WDID string from its parts.
@param length the length to which the WDID should be built.
@param wd Water district
@param id Identifier
*/
public static String formWDID (int length, String wd, String id)
{	Vector v = new Vector (2, 1);
	v.addElement(wd);
	v.addElement(id);
	int idSize = length - 2;
	String wdid = StringUtil.formatString(v, "%02d%0" + idSize + "d");
	return wdid;
}

/**
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _tab_trib
@return _tab_trib
*/
public String getTab_trib() {
	return _tab_trib;
}

/**
Returns _wd
@return _wd
*/
public int getWD() {
	return _wd;
}

/**
Returns _wd_name
@return _wd_name
*/
public String getWd_name() {
	return _wd_name;
}

/**
Determine whether a String identifier is a WDID.  The parseWDID() method is called to test the string.
@return true if the String could be a WDID.
@param wdid Candidate WDID string.
*/
public static boolean isWDID ( String wdid )
{	try {
		parseWDID ( wdid );
		return true;
	}
	catch ( Exception e ) {
		return false;
	}
}

/**
This function takes a String consisting of the water district number and name and returns the water district
number.  The format of the districtNumber_name String consist of at most a two digit number in the left most
portion of the string followed by a space and a dash which separates the number from the water district name.
In short, the water district number may always be found in the first two positions of the districtNumber_name
String.  If the districtNumber_name begins with "Division" then a division number is present therefore
return the Division number precede by -99.
@return The water district number and name.
@param districtNumber_name Water district name.
*/
public static String parseWD ( String districtNumber_name )
{	String parsedValue = "";
	String wd = districtNumber_name.trim();

	if ( wd.startsWith("Division") ) {
		parsedValue = "-99" + wd.substring(9, 10);
	}
	else {
		parsedValue = wd.substring(0, 2);
	}
	return parsedValue.trim();
}

/**
Parse a WDID into its parts.  Until told otherwise, assume a WDID consists of
a two-digit WD and a 5-digit ID part.  This allows some room for growth.
The string can also contain the leading characters "wdid:", which will be stripped off before parsing.
@param wdid string to parse.
@return an int[2] array, the first being the WD and the second the ID.
@exception Exception if there is an error parsing the WDID (e.g., null data, non-integer values).
*/
public static int[] parseWDID ( String wdid )
throws Exception
{	return parseWDID ( wdid, null );
}

/**
Parse a WDID into its parts.  Until told otherwise, assume a WDID consists of
a two-digit WD and a 5-digit ID part.  This allows some room for growth.
The string can also contain the leading characters "wdid:", which will be stripped off before parsing.
@param wdid string to parse.
@param wdid_parts_reuse An existing int[] array with at least 2 elements that
will be used to return data.  Specify this if repeatedly parsing strings in order to minimize memory use.
@return an int[2] array, the first being the WD and the second the ID.
@exception Exception if there is an error parsing the WDID (e.g., null data, non-integer values).
*/
public static int[] parseWDID ( String wdid, int [] wdid_parts_reuse )
throws Exception
{	String	routine = "HydroBase_WaterDistrict.parseWDID", message;
	int	wl = 50;

	if ( wdid == null ) {
		message = "WDID string is null";
		Message.printWarning (wl, routine, message );
		throw new Exception ( message );
	}
	int [] wdid_parts = null;
	if ( wdid_parts_reuse != null ) {
		wdid_parts = wdid_parts_reuse;
	}
	else {
		wdid_parts = new int[2];
	}

	// If wdid starts with "wdid:", strip off the front and continue...

	if (wdid.regionMatches(true,0,"wdid:",0,5)) {
		if (wdid.length() == 5) {
			wdid = "";
		}
		else {
			wdid = wdid.substring(5);
		}
	}

	// Make sure that only digits are used...

	int length = wdid.length();
	for (int i = 0; i < length; i++) {
		if (!Character.isDigit(wdid.charAt(i))) {
			message = "WDID \"" + wdid + "\" is not all digits.  Unable to parse.";
			Message.printWarning (wl, routine, message );
			throw new Exception ( message );
		}
	}

	// Assume that the first two characters are the WD and the rest are the ID...

	String wd;
	String id;
	if (length < 3) {
		// Set the WD to the string and set the ID to zero!
		// or should the other extreme be assumed???
		wd = wdid;
		id = "0";
	}
	else {
		wd = wdid.substring(0,2);
		id = wdid.substring(2);
	}
	wdid_parts[0] = StringUtil.atoi ( wd );
	wdid_parts[1] = StringUtil.atoi ( id );
	return wdid_parts;
}

/**
Sets _div
@param div value to put into _div
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _tab_trib
@param tab_trib value to put into _tab_trib
*/
public void setTab_trib(String tab_trib) {
	_tab_trib = tab_trib;
}

/**
Sets _wd
@param wd value to put into _wd
*/
public void setWD(int wd) {
	_wd = wd;
}

/**
Sets _wd_name
@param wd_name value to put into _wd_name
*/
public void setWd_name(String wd_name) {
	_wd_name = wd_name;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_WaterDistrict {"		+ "\n" + 
		"WD:       " + _wd + "\n" + 
		"Div:      " + _div + "\n" + 
		"Wd_name:  " + _wd_name + "\n" + 
		"Tab_trib: " + _tab_trib + "\n}\n";
}	

}