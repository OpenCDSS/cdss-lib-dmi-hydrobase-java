// HydroBase_WISFormula - Class to hold data from the HydroBase wis_formula table.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2019 Colorado Department of Natural Resources

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

// ----------------------------------------------------------------------------
// HydroBase_WISFormula.java - Class to hold data from the HydroBase 
//	wis_formula table.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2003-02-12	J. Thomas Sapienza, RTi	Initial version from HBWISFormula.
// 2003-02-24	JTS, RTi		Corrected error in finalize() so that 
//					super.finalize() gets called.
// 2003-10-08	JTS, RTI		Added Math support.
// 2005-03-09	JTS, RTi		Private data members changed to 
//					protected for JUnit comparison 
//					purposes.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;
import java.util.Vector;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase wis_formula table.
*/
public class HydroBase_WISFormula 
extends DMIDataObject {

// wis row num
protected int _wis_num = 		DMIUtil.MISSING_INT;
// wis row identifier
protected int _wis_row = 		DMIUtil.MISSING_INT;
protected String _column = 		DMIUtil.MISSING_STRING;
protected String _formula = 		DMIUtil.MISSING_STRING;
protected String _formulastring = 	DMIUtil.MISSING_STRING;
protected boolean _isFormulaEvaluated = 	false;
protected List<HydroBase_WISMath> _wisMath;

/**
Constructor.
*/
public HydroBase_WISFormula() {
	super();
	_wisMath = new Vector<HydroBase_WISMath>();
}

/**
Adds a term to the formula.
@param wisMath a term to add to the formula.
*/
public void addTerm(HydroBase_WISMath wisMath) {	
	_wisMath.add(wisMath);
}

/**
cleans up variables when the class is disposed of.  Sets all the member
variables (that aren't primitives) to null
@exception Throwable if an error occurs.
*/
protected void finalize()
throws Throwable {
	_column = null;
	_formula = null;
	_formulastring = null;
	_wisMath = null;	
	super.finalize();
}

/**
Returns _column
@return _column
*/
public String getColumn() {
	return _column;
}

/**
Returns _formula
@return _formula
*/
public String getFormula() {
	return _formula;
}

/**
Returns _formulastring.  Used to be getFormulaAsJLabel/getFormulaAsLabel.
@return _formulastring
*/
public String getFormulastring() {
	return _formulastring;
}

/**
Returns the number of terms.
@return the number of terms.
*/
public int getNumberOfTerms() {	
	return _wisMath.size();
}

/**
Returns the term at the specified location.  No checking is done to make sure
the location is out of bounds.
@param i the location within the terms vector to get the term.
@return the term at the specified location.
*/
public HydroBase_WISMath getTerm(int i) {	
	return (HydroBase_WISMath)_wisMath.get(i);
}

/**
Returns the terms of the formula.
@return the terms of the formula.
*/
public List<HydroBase_WISMath> getTerms() {	
	return _wisMath;
}

/**
Returns _wis_num
@return _wis_num
*/
public int getWis_num() {
	return _wis_num;
}

/**
Returns _wis_row
@return _wis_row
*/
public int getWis_row() {
	return _wis_row;
}

/**
Returns whether the formula is evaluated.
@return whether the formula is evaluated.
*/
public boolean isFormulaEvaluated() {
	return _isFormulaEvaluated;
}

/**
Sets _column
@param column column value to put into _column
*/
public void setColumn(String column) {
	_column = column;
}

/**
Sets _formula
@param formula value to put into _formula
*/
public void setFormula(String formula) {
	_formula = formula;
}

/**
Sets whether the formula is evaluated or not.
@param b whether the formula is evaluated or not.
*/
public void setFormulaEvaluated(boolean b) {
	_isFormulaEvaluated = b;
}

/**
Sets _formulastring
@param formulastring value to put into _formulastring
*/
public void setFormulastring(String formulastring) {
	_formulastring = formulastring;
}

/**
Sets the term at the specified location.
@param wisMath the term to set.
@param loc the location in the formula at which to set the term.
*/
public void setTerm(HydroBase_WISMath wisMath, int loc)  {	
	_wisMath.set(loc,wisMath);
}

/**
Sets _wis_num
@param wis_num value to put into _wis_num
*/
public void setWis_num(int wis_num) {
	_wis_num = wis_num;
}

/**
Sets _wis_row
@param wis_row value to put into _wis_row
*/
public void setWis_row(int wis_row) {
	_wis_row = wis_row;
}

/** 
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_WISFormula {"		+ "\n" + 
		"Wis_num:       " + _wis_num + "\n" + 
		"Wis_row:       " + _wis_row + "\n" + 
		"Column:        " + _column + "\n" + 
		"Formula:       " + _formula + "\n" + 
		"Formulastring: " + _formulastring + "\n}\n";
}

}
