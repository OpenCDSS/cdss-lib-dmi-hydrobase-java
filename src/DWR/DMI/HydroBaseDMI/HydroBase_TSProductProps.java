// HydroBase_TSProductProps - class to store information from the TSProduct table

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

/**
Class to store data from the Area table
*/
public class HydroBase_TSProductProps 
extends DMIDataObject {

protected int _TSProduct_num = 	DMIUtil.MISSING_INT;
protected String _Property = 	DMIUtil.MISSING_STRING;
protected String _Value = 	DMIUtil.MISSING_STRING;
protected int _Sequence = 	DMIUtil.MISSING_INT;

/**
HydroBase_TSProductProps constructor.
*/
public HydroBase_TSProductProps () {
	super();
}

/**
Returns _Property
@return _Property
*/
public String getProperty() {
	return _Property;
}

/**
Returns _Sequence
@return _Sequence
*/
public int getSequence() {
	return _Sequence;
}

/**
Returns _Value
@return _Value
*/
public String getValue() {
	return _Value;
}

/**
Returns _TSProduct_num
@return _TSProduct_num
*/
public int getTSProduct_num() {
	return _TSProduct_num;
}

/**
Sets _Property
@param Property value to put into _Property
*/
public void setProperty(String Property) {
	 _Property = Property;
}

/**
Sets _Sequence
@param Sequence value to put into _Identifier
*/
public void setSequence(int Sequence) {
	 _Sequence = Sequence;
}

/**
Sets _Value
@param Value value to put into _Value
*/
public void setValue(String Value) {
	 _Value = Value;
}

/**
Sets _TSProduct_num
@param TSProduct_num value to put into _TSProduct_num
*/
public void setTSProduct_num(int TSProduct_num) {
	 _TSProduct_num = TSProduct_num;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_TSProductProps{" 			+ "\n" + 
		"TSProduct_num: " + _TSProduct_num + "\n" +
		"Property:      '" + _Property + "'\n" + 
		"Value:         '" + _Value + "'\n" + 
		"Sequence:      " + _Sequence + "\n}\n";
}

}
