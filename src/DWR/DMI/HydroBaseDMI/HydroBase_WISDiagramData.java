// ----------------------------------------------------------------------------
// HydroBase_WISDiagramData - class to hold data from the wis_diagram_data
//	table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2005-05-20	J. Thomas Sapienza, RTi	Initial version.
// 2004-05-27	JTS, RTi		* Renamed from HydroBase_WISNetworkData
//					  to HydroBase_WISDiagramData.
//					* Removed X and Y values.
//					* Added get() methods for pulling out
//					  the X, Y and TextPosition 
//					  data members, as these will be 
//					  need to be accessed easily for 
//					  non-annotation nodes drawn on the
//					  network diagram.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMI;
import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import RTi.GR.GRText;

/**
Class to store data from the HydroBase wis_diagram_data table.
*/
public class HydroBase_WISDiagramData 
extends DMIDataObject {

protected int _wis_num = 	DMIUtil.MISSING_INT;
protected String _id = 		DMIUtil.MISSING_STRING;
protected String _type = 	DMIUtil.MISSING_STRING;
protected String _props = 	DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_WISDiagramData() {
	super();
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_id = null;
	_props = null;
	_type = null;
	
	super.finalize();
}

/**
Returns the id.
@return the id.
*/
public String getID() {
	return _id;
}

/**
Pulls a single prop out of the proplist string.
@return the key=value pair, or null if none could be found.
*/
private String getPropValue(String key) {
	if (_props == null) {
		return null;
	}

	int index = _props.indexOf(key);

	if (index < 0) {
		return null;
	}
	
	String s = _props.substring(index);
	index = s.indexOf(";");
	if (index >= 0) {
		s = s.substring(0, index);
	}
	index = s.indexOf("=");
	if (index < 0) {
		return null;
	}
	return s.substring(index + 1);
}

/**
Returns the props.
@return the props.
*/
public String getProps() {
	return _props;
}

/**
Returns the text position from the proplist in integer format.  The order
corresponds to the order in GRText.
@return the text position from the proplist in integer format, or 1 if 
none could be found.
*/
public int getTextPosition() {
	String position = getPropValue("TextPosition");
	if (position == null) {
		return 1;
	}
	position = position.trim();
	
	String[] positions = GRText.getTextPositions();
	for (int i = 0; i < positions.length; i++) {
		if (positions[i].equalsIgnoreCase(position)) {
			return (i + 1);
		}
	}
	return 1;
}

/**
Returns the type.
@return the type.
*/
public String getType() {
	return _type;
}

/**
Returns the wis_num.
@return the wis_num.
*/
public int getWis_num() {
	return _wis_num;
}

/**
Returns the X value from the Points prop in the internal proplist, or -999.0
if it could not be retrieved.
@return the X value.
*/
public double getX() {
	String point = getPropValue("Point");
	if (point == null) {
		point = getPropValue("Points");
	}
	int index = point.indexOf(",");
	if (index < 0) {
		return DMIUtil.MISSING_DOUBLE;
	}

	String xs = point.substring(0, index);
	try {
		return (new Double(xs)).doubleValue();
	}
	catch (Exception e) {
		return DMIUtil.MISSING_DOUBLE;
	}
}

/**
Returns the Y value from the Point prop in the internal proplist, or -999.0
if it could not be retrieved.
@return the Y value.
*/
public double getY() {
	String point = getPropValue("Point");
	if (point == null) {
		point = getPropValue("Points");
	}
	int index = point.indexOf(",");
	if (index < 0) {
		return DMIUtil.MISSING_DOUBLE;
	}

	String ys = point.substring(index + 1);
	try {
		return (new Double(ys)).doubleValue();
	}
	catch (Exception e) {
		return DMIUtil.MISSING_DOUBLE;
	}
}

/**
Sets the id.
@param id the value to store in the id.
*/
public void setID(String id) {
	_id = id;
}

/**
Sets the props.
@param props the value to store in the props.
*/
public void setProps(String props) {
	_props = props;
}

/**
Sets the type.
@param type the value to store as the type.
*/
public void setType(String type) {
	_type = type;
}

/**
Sets the wis_num.
@param wis_num the value to store in the wis_num.
*/
public void setWis_num(int wis_num) {
	_wis_num = wis_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_WISDiagramData {"	+ "\n" + 
		"Wis_num: " + _wis_num 		+ "\n" + 
		"ID:      '" + _id 		+ "'\n" +
		"Type:    '" + _type 		+ "'\n" +
		"Props:   '" + _props		+ "'\n}\n";
}	

}
