// ----------------------------------------------------------------------------
// HydroBase_WaterDivision.java - Class to hold data from the HydroBase 
//	water_division table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-12	J. Thomas Sapienza, RTi	Initial version from HBWaterDivision.
// 2003-02-17	JTS, RTi		Added static getDivisionNumber method.
// 2003-02-24	JTS, RTi		- Corrected error in finalize() so that 
//					  super.finalize() gets called.
//					- Made the getDivisionNumber method
//					  more robust.
// 2003-03-03	JTS, RTi		Added the division table.
// 2003-03-17	JTS, RTi		Cleaned up, javadoc'd.
// 2003-03-26	JTS, RTi		Added null-checking to the 
//					getDivisionNumber() method.
// 2005-02-28	JTS, RTi		Changed private member variables to 
//					protected for doing unit tests.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import java.util.Hashtable;

/**
Class to store data from the HydroBase water_division table.
*/
public class HydroBase_WaterDivision 
extends DMIDataObject {

protected int _div = DMIUtil.MISSING_INT;
protected String _div_name = DMIUtil.MISSING_STRING;

/**
Constructor, typically used when attributes are added sequentially during database processing.
*/
public HydroBase_WaterDivision() {
	super();
}

/**
Constructor, typically used when instances must be manually defined.
@param div division number (1+)
@param name division name (e.g., "South Platte").
*/
public HydroBase_WaterDivision ( int div, String name )
{
    super();
    setDiv ( div );
    setDiv_name ( name );
}

/**
Static list of divisions that are commonly used, in format "Division 1:  South Platte".
*/
protected final static String[] divisions = {
    "Division 1: South Platte",
	"Division 2: Arkansas River",
	"Division 3: Rio Grande",
	"Division 4: Gunnison River",
	"Division 5: Colorado River",
	"Division 6: Yampa River",
	"Division 7: San Juan/Dolores River" };

/**
Static hashtable mapping division numbers as Integer to divisions string.
*/
protected final static Hashtable divisionTable = new Hashtable();

// map a name to a number for storage
static {
	int size = divisions.length;
	int num = 0;
	for ( int i=0; i<size; i++ ) {
		num = i + 1;
		divisionTable.put( new Integer( num ), divisions[i] );
	}	
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_div_name = null;
	
	super.finalize();
}

/**
Returns _div
@return _div
*/
public int getDiv() {
	return _div;
}

/**
Returns _div_name
@return _div_name
*/
public String getDiv_name() {
	return _div_name;
}

/**
Returns a division name for a given integer key.
@param key the key for which to return a division name.
@return a division name for a given integer key.
*/
public static String getDivisionName( int key ) {
	return (String)divisionTable.get(new Integer(key));
}

/**
Returns the list of division names.
@return a String array of division names.
*/
public static String[] getDivisionNames() {
	return divisions;
}

/**
Returns a division number given the division name.
@param name the name of the division for which to return the number.
@return the division number of the specified division.
@throws Exception if a null name was passed in.
*/
public static int getDivisionNumber( String name ) 
throws Exception {
	int num = DMIUtil.MISSING_INT;

	if (name == null) {
		throw new Exception("null parameter passed to getDivisionNumber");
	}

	for (int i = 0; i < divisions.length; i++) {
		num = i + 1;
		if ( name.equals( divisions[i] ) ) {
			num = i + 1;
			break;
		}
	}

	return num;
}

/**
Sets _div
@param div value to put into _div
*/
public void setDiv(int div) {
	_div = div;
}

/**
Sets _div_name
@param div_name value to put into _div_name
*/
public void setDiv_name(String div_name) {
	_div_name = div_name;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_WaterDivision {"		+ "\n" + 
		"Div:      " + _div + "\n" + 
		"Div_name: " + _div_name + "\n}\n";
}

}