//-----------------------------------------------------------------------------
// HBWISMath - class to assist in building and manipulating
//	       the WIS sheet formula language.
//-----------------------------------------------------------------------------
// History:
// 24 Nov 1997	Darrell L. Gillmeister, RTi Created initial version.
//-----------------------------------------------------------------------------
// 2003-10-08	J. Thomas Sapienza, RTi	* Updated for HydroBaseDMI.
//					* Rough draft of javadocs.	
// 2004-01-05	JTS, RTi		Added toString().
//-----------------------------------------------------------------------------
package DWR.DMI.HydroBaseDMI;

import java.util.List;
import java.util.Vector;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
This class is an object used to represent a piece of math in a formula.
*/
public class HydroBase_WISMath 
extends DMIDataObject {

/**
The operator (*, -, /, +, =)
*/
private	String _operator;

/**
The range expression (13.point Flow:15.point flow).
*/
private String _range;

/**
Column type (Point flow, gain loss, etc).
*/
private String _colType;

/**
The row label in the grid.
*/
private String _rowLabel;

/**
Unique identifier (e.g., for structures wdid:410401).
*/
private String _uniqueID;

/**
Row number the term refers to, subject to the dynamic nature of the grid.
Should only be relied upon when rows are not changing.
*/
private int _rowNum;

/**
Column number to which the term refers.
*/
private int _columnNum;

/**
Constant value.
*/
private double _constant;

/**
True if the expression refers to a range of cells, false otherwise.
*/
private boolean	_isRange;

/**
True if a number exists for this term.
*/
private boolean _isValid;

/**
True if term is a constant, false otherwise.
*/
private boolean _isConstant;

/**
Flags for parsing formulas.
*/
public final static int		
	UNIQUE_ID = 	0,
	LABEL = 	1,
	CONSTANT = 	2;

/**
Constructor.
*/
public HydroBase_WISMath() {
	initialize();
}

/**
Returns the column number.
@return the column number.
*/
public int getColumnNumber() {
	return _columnNum;
}

/**
Returns the column type.
@return the column type.
*/
public String getColumnType() {
	return _colType;
}

/**
Returns the constant value.
@return the constant value.
*/
public double getConstant() {
	return _constant;
}

/**
Returns whether it is valid.
@return whether it is valid.
*/
public boolean getIsValid() {
	return _isValid;
}

/**
Returns the row label.
@return the row label.
*/
public String getLabel() {
	return _rowLabel;
}
 
/**
Returns the operator.  
@return the operator.
*/
public String getOperator() {
	return _operator;
}

/**
Returns the range.
@return the range.
*/
public boolean getRange() {
	return _isRange;
}

/**
Returns the row number.
@return the row number.
*/
public int getRowNumber() {
	return _rowNum;
}
 
/**
Returns the syntax for a term within a formula given a HydroBase_WISMath object.
@param wisMath a HydroBase_WISMath object.
@param flag UNIQUE_ID if parsing unique ids.  LABEL if parsing row labels.
CONSTANT if parsing a constant.
@return the String representation of a term (e.g., + structure1.point flow).
*/
public static String getTerm(HydroBase_WISMath wisMath, int flag) {
	String syntax;

	// initialize variables
	syntax = "";

	if (flag == UNIQUE_ID) {
		syntax = wisMath.getUniqueID() + "." + wisMath.getColumnType();
	}
	else if (flag == LABEL) {
		syntax = wisMath.getLabel();
	}	
	else if (flag == CONSTANT) {
		syntax = "" + wisMath.getConstant();
	}
	
	return wisMath.getOperator()+ " " + syntax;
}

/**
Returns the unique id.
@return the unique id.
*/
public String getUniqueID() {
	return _uniqueID;
}

/**
Initializes data members.
*/
private void initialize() {
	_operator = new String();
	_range = new String();
	_colType = new String();
	_rowLabel = new String();
	_uniqueID = new String();
	_rowNum = DMIUtil.MISSING_INT;
	_columnNum = DMIUtil.MISSING_INT;
	_constant = DMIUtil.MISSING_DOUBLE;
	_isConstant = false;
	_isRange = false;
	_isValid = false;
}

/**
Determines if a String is an operator type.
@param s the String to compare.
@return true if the String is an operator, false otherwise.
*/
private static boolean isAnOperator(String s) {
	if (s.equals("+")|| s.equals("-")
		|| s.equals("/")|| s.equals("*")|| s.equals("=")) {
		return (true);
	}
	else {
		return (false);
	}
}

/**
Determines if the HydroBase_WISMath object is a constant term.
@return true if it is a constant term, false otherwise.
*/
public boolean isConstantTerm() {
	return (_isConstant);
}

/**
Takes a formula String and parses it into a Vector where elements represent
HydroBase_WISMath objects.
@param formula a formula String.
@param flag UNIQUE_ID if parsing unique ids, LABEL if parsing row labels.
@return a Vector of HydroBase_WISMath objects.
*/
public static List parseFormula(String formula, int flag) {
	// initialize variables
	List parse = new Vector();
	int size = formula.length();

	if (flag == LABEL) {
		return parseFormulaAsLabel(formula);
	}
	
	String curTerm = null;
	String curChar = null;
	String entireTerm = null;
	boolean foundColumn;
	boolean endOfTerm;
	Double curTermAsDouble;
	double finalValue;
	HydroBase_WISMath wisMath;
	// loop over formula String
	for (int i=0; i<size; i++) {
		curChar = String.valueOf(formula.charAt(i));
		curTerm = "";
		entireTerm = "";		

		foundColumn = false;
		if (isAnOperator(curChar)) {
			// instantiate an HydroBase_WISMath object and
			// set the operator
			wisMath = new HydroBase_WISMath();
			wisMath.setOperator(curChar);
			
			// loop until the next operator is located.
			// since i was evaluated, begin this loop at i+1
			endOfTerm = false;
			while (!endOfTerm) {
				i++;
				// do not attempt to loop over more characters
				// than are actually present in the formula 
				// String
				if (i == size) {
					break;
				}
		
				// get the current character and append 
				// the entireTerm String if the curChar 
				// is not an operator type.
				curChar = String.valueOf(formula.charAt(i));
				if (!isAnOperator(curChar)) {
					entireTerm += curChar;
				}

				// column arguments exist after the '.'
				if (curChar.equals(".")) {
					if (flag == UNIQUE_ID) {
						wisMath.setUniqueID(curTerm);
					}
					else if (flag == LABEL) {
						wisMath.setLabel(curTerm);
					}
					curTerm = "";
					foundColumn = true;
				}
				// for final terms...
				else if (foundColumn && i==size-1 ) {
					// end of the formula String is reached
					// add the last term to the parse Vector
					curTerm += curChar;
					endOfTerm = true;

					// if entire term successfully 
					// converts to a double then the 
					// term is a constant. otherwise
					// set the column type.
					try {
						curTermAsDouble = new Double(
							entireTerm.trim());
						finalValue = 
						curTermAsDouble.doubleValue();
						wisMath.setConstant(finalValue);
						wisMath.setIsConstant(true);

						// need to unset these
						if (flag == UNIQUE_ID) {
							wisMath.setUniqueID("");
						}
						else if (flag == LABEL) {
							wisMath.setLabel("");
						}
					}
					catch (NumberFormatException e) {	
						wisMath.setColumnType(
							curTerm.trim());
					}
					parse.add(wisMath);
					break;
				}
				// for interior terms...
				else if (foundColumn && isAnOperator(curChar)) {
					// end of the expression term is 
					// reached add the built term to 
					// the parse Vector
					endOfTerm = true;
					foundColumn = false;

					// if entire term successfully 
					// converts to a double then the 
					// term is a constant. otherwise
					// set the column type.
					try {
						curTermAsDouble = new Double(
							entireTerm.trim());
						finalValue = 
						  curTermAsDouble.doubleValue();
						wisMath.setConstant(finalValue);
						wisMath.setIsConstant(true);

						// need to unset these
						if (flag == UNIQUE_ID) {
							wisMath.setUniqueID("");
						}
						else if (flag == LABEL) {
							wisMath.setLabel("");
						}
					}
					catch (NumberFormatException e) {	
						wisMath.setColumnType(
							curTerm.trim());
					}
					parse.add(wisMath);
					i--;	
				}
				// for terms preceeding the '.'...
				else {
					// build the curFormula String
					curTerm += curChar;
				}
			}
		}
	}
	return parse;
}

/**
Takes a formula String and parses it into a Vector where objects represent
HydroBase_WISMath objects.
@param formula the formula String.
@return a Vector of HydroBase_WISMath objects.
*/
public static List parseFormulaAsLabel(String formula) {
	HydroBase_WISMath wisMath;
	boolean endOfTerm;
	String curTerm = null;
	String curChar = null;
	List parse = new Vector();
	int size = formula.length();

	// loop over formula String
	for (int i=0; i<size; i++) {
		curChar = String.valueOf(formula.charAt(i));
		curTerm = "";

		if (isAnOperator(curChar)) {
			// instantiate an HydroBase_WISMath object and
			// set the operator
			wisMath = new HydroBase_WISMath();
			wisMath.setOperator(curChar);

			// loop until the next operator is located.
			// since i was evaluated, begin this loop at i+1
			endOfTerm = false;
			while (!endOfTerm) {
				i++;

				// do not attempt to loop over more characters
				// than are actually present in the 
				// formula String final terms....
				if (i == size) {
					wisMath.setLabel(curTerm);
					parse.add(wisMath);
					endOfTerm = true;
					break;
				}
		
				curChar = String.valueOf(formula.charAt(i));
				// interior terms....
				if (isAnOperator(curChar)) {
					wisMath.setLabel(curTerm);
					parse.add(wisMath);
					endOfTerm = true;
					i--;	
				}
				// get the current character and append 
				// the curTerm String if the curChar is 
				// not an operator type.
				else {
					curTerm += curChar;
				}
			}
		}
	}		
	return parse;
}

/**
Sets the column number.
@param i value to set the column number to.
*/
public void setColumnNumber(int i) {
	_columnNum = i;
}

/**
Sets the constant.
@param d value to set the constant to.
*/
public void setConstant(double d) {
	_constant = d;
}

/**
Sets the column type.
@param s value to set the column type to.
*/
public void setColumnType(String s) {
	if (s != null) {
		_colType = s;
	}
}

/**
Sets whether it is constant.
@param b value to set the constant to.
*/
public void setIsConstant(boolean b) {
	_isConstant = b;
}

/**
Sets whether it is valid.
@param b whether it is valid.
*/
public void setIsValid(boolean b) {
	_isValid = b;
}

/**
Sets the label.
@param s value to set the label to.
*/
public void setLabel(String s) {
	if (s != null) {
		_rowLabel = s;
	}
}

/**
Sets the operator.
@param s value to set the operator to.
*/
public void setOperator(String s) {
	if (s != null) {
		_operator = s;
	}
	return;
}

/**
Sets the range.
@param b value to set the range to.
*/
public void setRange(boolean b) {
	_isRange = b;
	return;
}

/**
Sets the row number.
@param i value to set the row number to.
*/
public void setRowNumber(int i) {
	_rowNum = i;
}

/**
Sets the unique ID.
@param s value to set the unique id to.
*/
public void setUniqueID(String s) {
	if (s != null) {
		_uniqueID = s;
	}
	return;
}

/**
Returns a String representation of the object.
@return a String representation of the object.
*/
public String toString() {
	return "HydroBase_WISMath: {\n" + 
	"operator:   " + _operator + "\n" + 
	"range:      " + _range + "\n" + 
	"colType:    " + _colType + "\n" + 
	"rowLabel:   " + _rowLabel + "\n" + 
	"uniqueID:   " + _uniqueID + "\n" + 
	"rowNum:     " + _rowNum + "\n" + 
	"columnNum:  " + _columnNum + "\n" + 
	"constant:   " + _constant + "\n" + 
	"isRange:    " + _isRange + "\n" + 
	"isValid:    " + _isValid + "\n" + 
	"isConstant: " + _isConstant + "\n}\n";
}

}
