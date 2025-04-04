// HydroBase_WaterDivision - Class to hold data from the HydroBase water_division table.

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
 * Static list of division numbers.
 */
protected final static int[] divisionNumbers = {
	1,
	2,
	3,
	4,
	5,
	6,
	7
};

/**
Static hashtable mapping division numbers as Integer to divisions string.
*/
protected final static Hashtable<Integer,String> divisionTable = new Hashtable<Integer,String>();

// map a name to a number for storage
static {
	int size = divisions.length;
	int num = 0;
	for ( int i=0; i<size; i++ ) {
		num = i + 1;
		divisionTable.put( Integer.valueOf( num ), divisions[i] );
	}	
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
	return divisionTable.get(Integer.valueOf(key));
}

/**
Returns the array of division names.
@return a String array of division names.
*/
public static String[] getDivisionNames() {
	return divisions;
}

/**
Returns the array of division numbers.
@return int array of division numbers.
*/
public static int[] getDivisionNumbers() {
	return divisionNumbers;
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