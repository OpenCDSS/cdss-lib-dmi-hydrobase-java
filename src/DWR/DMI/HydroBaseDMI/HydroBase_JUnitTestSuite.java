// ----------------------------------------------------------------------------
// HydroBase_JUnitTest -- this class sets up a series of tests to compare 
//	whether Stored Procedures' results match up with the expected 
//	results.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2005-01-26	J. Thomas Sapienza, RTi	Initial version.
// 2005-02 - 2005-03	JTS, RTi	Adding queries to test SQL versus
//					stored procedures.
// 2005-03-09	JTS, RTi		* Nothing was gained by the DMI_TestCase
//					  object this class was extending, so 
//					  that class was removed and this now 
//					  directly extends TestCase.
//					* Started adding code for testing delete
//					  and write.  
//					* All current testXXX() methods renamed
//					  to testReadXXX().
// 2005-04-07	JTS, RTi		Added a new test to the DBVersion test
//					for reading all records at once.
// 2005-06-08	JTS, RTi		Added code to test different versions 
//					of the databases.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.lang.reflect.Field;

import java.sql.SQLException;

import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import RTi.DMI.DMIUtil;

import RTi.Util.IO.IOUtil;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Table.DataTable;

import RTi.Util.Test.HydroBaseTestCase;

import RTi.Util.Time.DateTime;
import RTi.Util.Time.StopWatch;

/**
This class sets up a series of tests for HydroBase stored procedures to check
whether the stored procedures' results match up with the expected results.<p>
This class implements a TestCase object, which allows the JUnit TestRunner code
to execute the tests contained within.  The following is a rough sketch of
what happens when the TestRunner is run using this class:<p>
<ol>
<li>TestRunner calls the static "suite()" method to get a list of all the 
   tests within this class that need to be executed.  The suite() method will
   look for a file in the current working directory called 
   HydroBase_JUnitTest.txt and use it to get a list of all the tests that should
   be run.  If this file cannot be opened, all tests will be run.  This file
   needs to be in the current Java working directory (probably the directory
   in which the batch file is being run) because it is impossible to pass
   command-line parameters into this class.  The TestRunner class swallows all
   command-line parameters.</li>
<li>TestRunner will then instantiate a HydroBase_JUnitTestSuite() class for
   every test in the suite.  The instantiation of this class will use 
   internal reflection to find the method that has the name that matches the
   string of a test name in the suite.</li>
<li>The test is run and there are three possible outcomes:<p><ul>
	<li>A JUnit-related Exception is thrown.  This indicates a test failure.
	</li>
	<li>Some other kind of exception is thrown.  This means an internal
	   error occurred.</li>
	<li>No exceptions were thrown.  This means that the test completed
	   successfully.</li>
	</ul></li>
<li>Once all the tests have been run, the TestRunner summarizes the results.
</li>
*/
public class HydroBase_JUnitTestSuite 
extends HydroBaseTestCase {

/**
Reference to the dmi instance that will be used to do all the queries.<p>
<b>Note:</b> This member variable is static due to the way that the TestRunner
executes all the tests within this class.  Each test that is specified in the
suite() method is called by instantiating a new HydroBase_JUnitTestSuite
object and executing it within that.<p>
Therefore, there are three ways in which the DMI instantiation and connection
could be handled:<p>
<ul>
<li>The DMIs could be opened and closed within every testXXXX() method.  This
   would eliminate the need for the DMI data members to be static, but would
   come with a time penalty if more than one test were run at a time. 
   Connecting both __dmi1 and __dmi2 to a database takes between 15 and 20 
   seconds.</li>
<li>All the testXXXX() methods could be placed within a single overarching 
   test method, perhaps named testAll().  Then instead of executing lots of
   separate tests, this single test could be executed.  Inside testAll() the
   DMIs could be opened and closed and then be available to all the 
   database operations.  The downside of this is that it would then be 
   impossible to run single tests -- testAll() could only be called by itself.
   </li>
<li>The third option is what is being done here.  The DMI objects are static 
   and available across object instantiations.  They are opened once and closed
   once, but all database operations can use them.  The reason this was chosen
   was for the disadvantages given above, but also becuase:<p><ul>
   <li>It's simpler.</li>
   <li>The reason for containing the amount of static data is important in
      full applications, but this class will only be called by itself from 
      a batch file, so worrying about the internal memory consumption of a 
      single small class would be a waste of time in the big picture.</li>
</li>
<p>
The gist of this is that it's not the most efficient, nor necessarily the most
intuitive, way of declaring these variables, but it gets the job done and
it works.
*/
private static HydroBaseDMI __dmi1 = null;

/**
Reference to the dmi instance that will be used to do all the queries.  
See the Javadocs for member variable __dmi1 for an important note regarding
why this data member is static.
*/
private static HydroBaseDMI __dmi2 = null;

/**
Constructor.
@param name the name of the test to run.  
@see buildSuite()
*/
public HydroBase_JUnitTestSuite(String name) {
	super(name);
}

/**
Checks to see whether the Vectors of Integers are all the same values.  
Integer Vectors must be checked separately because the Integer Objects have
private members that are not accessible in the Object comparison code.
@param v1 the first Vector to check.  Can be null.
@param v2 the second Vector to check.  Can be null.
@throws Exception if there two Vectors do not contain the same data.
*/
public void assertIntegerVectorEquals(Vector v1, Vector v2) 
throws Exception {
	if (v1 != null && v2 != null) {
		Message.printStatus(1, "", "  Comparing vectors of " + v1.size()
			+ " and " + v2.size() + " elements.");
	}

	if (v1 == null || v2 == null) {
		if (v1 == null && v2 == null) {
			// null Vectors are basically the same
			return;
		}
		else if (v1 == null) {
			fail("Vector 1 is null and Vector 2 is not null.");
		}
		else {
			fail("Vector 1 is not null and Vector 2 is null.");
		}
	}

	int size1 = v1.size();
	int size2 = v2.size();

	if (size1 != size2) {
		fail("Vector 1 contains " + size1 + " elements and Vector 2 "
			+ "contains " + size2 + " elements.");
	}

	Integer I1 = null;
	Integer I2 = null;
	int i1 = -1;
	int i2 = -1;
	for (int i = 0; i < size1; i++) {
		I1 = (Integer)v1.elementAt(i);
		I2 = (Integer)v2.elementAt(i);

		i1 = I1.intValue();
		i2 = I2.intValue();

		if (i1 != i2) {
			fail("At Vector position " + i + ": "
				+ "the Object in Vector 1 has a value of " 
				+ i1 + " while the Object in Vector 2 has "
				 +"a value of " + i2);
		}
	}
}

/**
Checks to see if there are any differences in the data stored within two
objects, and if so returns a 3-element array containing the name of the field
in which there is a difference and values in each object. <p>
@param o1 the first Object to check the contents of.
@param o2 the second Object to check the contents of.
@return a 3-element array with information on any differences between the 
Objects, or null if there are no differences.<p>
If the array is returned, it contains:<p>
[0] - the name of the data member in which the objects differed<br>
[1] - the value of the data member in object o1<br>
[2] - the value of the data member in object o2
*/
public String[] getObjectDifference(Object o1, Object o2) 
throws Exception {
	Class c = o1.getClass();
	Field[] fields = c.getDeclaredFields();
	Object value1 = null;
	Object value2 = null;	
	for (int i = 0; i < fields.length; i++) {
		value1 = fields[i].get(o1);
		value2 = fields[i].get(o2);
		if (value1 == null && value2 == null) {
			// they are equal, go to the next field
		}
		else if (value1 == null) {
			String[] s = new String[3];
			s[0] = fields[i].getName();
			s[1] = null;
			s[2] = fields[i].get(o2).toString();
			return s;
		}
		else if (value2 == null) {
			String[] s = new String[3];
			s[0] = fields[i].getName();
			s[1] = fields[i].get(o1).toString();
			s[2] = null;
			return s;
		}
		else if (!(fields[i].get(o1).equals(fields[i].get(o2)))) {
			String[] s = new String[3];
			s[0] = fields[i].getName();
			s[1] = fields[i].get(o1).toString();
			s[2] = fields[i].get(o2).toString();
			return s;
		}
	}
	return null;
}

/**
Prints a header section for a group of tests.
@param text the name of the tests to be run.
*/
public static void header(String text) {
	Message.printStatus(1, "", "");
	Message.printStatus(1, "", "");
	Message.printStatus(1, "", "Running tests for " + text);
	Message.printStatus(1, "", "  -----------------------------------");
}

/**
Checks to see if there are any differences in the data stored within two
objects, return true if so and false if not.<p>
@param o1 the first Object to check the contents of.
@param o2 the second Object to check the contents of.
@return true if there are any differences in the contents of the objects, false
if not.
*/
public boolean objectContentsAreEqual(Object o1, Object o2) 
throws Exception {
	Class c1 = o1.getClass();
	Class c2 = o2.getClass();

	if (c1 != c2) {
		return false;
	}

	Field[] fields = c1.getDeclaredFields();
	Object value1 = null;
	Object value2 = null;
	Vector v = new Vector();
	for (int i = 0; i < fields.length; i++) {
		if (fields[i].getType() == v.getClass()) {
			// skip Vector data members
			continue;
		}

		value1 = fields[i].get(o1);
		value2 = fields[i].get(o2);
		if (value1 == null && value2 == null) {
			// they are equal, go to the next field
		}
		else if (value1 == null && value2 != null) {
//			Message.printStatus(1, "", "fail 1 '" 
//				+ value1 + "' '" + value2 + "'");
			return false;
		}
		else if (value1 != null && value2 == null) {
//			Message.printStatus(1, "", "fail 2 '" 
//				+ value1 + "' '" + value2 + "'");
			return false;
		}		
		else if (!(fields[i].get(o1).equals(fields[i].get(o2)))) {
//			Message.printStatus(1, "", "fail 3 '" 
//				+ value1 + "' '" + value2 + "'");
			return false;
		}
	}
	return true;
}

/**
Builds a suite of tests that this class can run.  In this case, it creates
a test for every query to be tested.  The first 'test' to run MUST be 
testOpenDMI() so that the DMI connection is made, and the last 'test' to run
MUST be testCloseDMI() so that the DMI is closed after the tests are run.
This method is inherited from the base TestCase class.
@return a TestSuite containing all the tests to be run.
*/
public static Test suite() {
	TestSuite testsToRun = new TestSuite();
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testOpenDMI"));

	if (IOUtil.fileExists("HydroBase_JUnitTests.txt")) {
		PropList props = new PropList("");
		props.add("Delimiter=\t");
		props.add("CommentLineIndicator=#");
		props.add("TrimStrings=true");
		try {
			DataTable table = DataTable.parseFile(
				"HydroBase_JUnitTests.txt", props);
	
			int num = table.getNumberOfRecords();
			String s = null;
			
			for (int i = 0; i < num; i++) {
				s = (String)table.getFieldValue(i, 0);
				if (!s.trim().equals("")) {
					testsToRun.addTest(
						new HydroBase_JUnitTestSuite(
						"test" + s));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	else {

	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadAgriculturalCASSCropStats"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadAgriculturalNASSCropStats"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadAnnualAmt"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadAnnualRes"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadAnnualWC"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadAreaCap"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadCalls"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadContact"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadCounty"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadCourtCase"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadCropChar"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadCropGrowth"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadCropRef"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadCUBlaneyCriddle"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadCUCoeff"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadCUMethod"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadDailyAmt"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadDailyStation"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadDailyWC"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadDam"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadDBVersion"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadDiversionComment"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadEmergencyPlan"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadEquipment"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadFrostDates"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadGeneralComment"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadGeoloc"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadLocType"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadMapfile"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadMeasType"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadMonthlyStation"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadNetAmts"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadParcelUse"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadPersonDetails"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadPumpTest"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadRefCIU"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadResEOM"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadResMeas"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadRolodex"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadRTMeas"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadSnowCrse"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadStation"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadStream"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadStrType"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadStructMeasType"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadStructure"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadTransact"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadTSProduct"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadTSProductProps"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadUnpermittedWells"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadUse"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadUserPreferences"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadUserSecurity"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadWaterDistrict"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadWaterDivision"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadWDWater"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadWISComments"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadWISDailyWC"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadWISData"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadWISDiagramData"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadWISFormat"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadWISImport"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadWISSheetName"));	
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadWellApplication"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadWellMeas"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadWells"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite("testReadWellToLayer"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadWellToParcel"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadWellToStructure"));
	testsToRun.addTest(new HydroBase_JUnitTestSuite(
		"testReadWellToParcelWellToStructure"));
	}

	testsToRun.addTest(new HydroBase_JUnitTestSuite("testCloseDMI"));
	return testsToRun;
}

public void testReadAgriculturalCASSCropStats()
throws Exception {
	header("Agricultural CASS Crop Stats");
	Vector v1 = __dmi1.readAgriculturalCASSCropStatsList(null, "Adams", 
		"Oats", null, null, null, false);
	Vector v2 = __dmi2.readAgriculturalCASSCropStatsList(null, "Adams", 
		"Oats", null, null, null, false);
	assertEquals(v1, v2);		

	v1 = __dmi1.readAgriculturalCASSCropStatsList(null, "Adams", "Oats", 
		null, null, null, true);
	v2 = __dmi2.readAgriculturalCASSCropStatsList(null, "Adams", "Oats", 
		null, null, null, true);
	assertEquals(v1, v2);		

	v1 = __dmi1.readAgriculturalCASSCropStatsList(null, "Larimer", null, 
		"Irrigated", null, null, false);
	v2 = __dmi2.readAgriculturalCASSCropStatsList(null, "Larimer", null, 
		"Irrigated", null, null, false);
	assertEquals(v1, v2);	

	v1 = __dmi1.readAgriculturalCASSCropStatsList(null, null, null, 
		"Irrigated", null, null, true);
	v2 = __dmi2.readAgriculturalCASSCropStatsList(null, null, null, 
		"Irrigated", null, null, true);
	assertEquals(v1, v2);	

	DateTime start = DateTime.parse("1970-01-01");
	DateTime end = DateTime.parse("1980-07-31");

	v1 = __dmi1.readAgriculturalCASSCropStatsList(null, "Larimer", null, 
		null, start, end, false);
	v2 = __dmi2.readAgriculturalCASSCropStatsList(null, "Larimer", null, 
		null, start, end, false);
	assertEquals(v1, v2);		

	v1 = __dmi1.readAgriculturalCASSCropStatsList(null, null, null, null,  
		start, end, true);
	v2 = __dmi2.readAgriculturalCASSCropStatsList(null, null, null, null,  
		start, end, true);
	assertEquals(v1, v2);			
}

public void testReadAgriculturalNASSCropStats()
throws Exception {
	header("Agricultural NASS Crop Stats");
	Vector v1 = __dmi1.readAgriculturalNASSCropStatsList(null, "Adams",
		"Orchards, Irrigated", null, null, false);
	Vector v2 = __dmi2.readAgriculturalNASSCropStatsList(null, "Adams",
		"Orchards, Irrigated", null, null, false);
	assertEquals(v1, v2);	

	v1 = __dmi1.readAgriculturalNASSCropStatsList(null, "Adams",
		"Orchards, Irrigated", null, null, true);
	v2 = __dmi2.readAgriculturalNASSCropStatsList(null, "Adams",
		"Orchards, Irrigated", null, null, true);
	assertEquals(v1, v2);	

	DateTime start = DateTime.parse("1980-01-01");
	DateTime end = DateTime.parse("1990-07-31");

	v1 = __dmi1.readAgriculturalNASSCropStatsList(null, null, null, 
		start, end, false);
	v2 = __dmi2.readAgriculturalNASSCropStatsList(null, null, null, 
		start, end, false);
	assertEquals(v1, v2);	

	v1 = __dmi1.readAgriculturalNASSCropStatsList(null, null, null, 
		start, end, true);
	v2 = __dmi2.readAgriculturalNASSCropStatsList(null, null, null, 
		start, end, true);
	assertEquals(v1, v2);		
}

public void testReadAnnualAmt() 
throws Exception {
	header("Annual Amt");

	DateTime start = DateTime.parse("1950-01-01");
	DateTime end = DateTime.parse("1980-07-31");
	
	Vector v1 = __dmi1.readAnnualAmtList(153466, start, null);
	Vector v2 = __dmi2.readAnnualAmtList(153466, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readAnnualAmtList(153466, null, end);
	v2 = __dmi2.readAnnualAmtList(153466, null, end);
	assertEquals(v1, v2);	

	v1 = __dmi1.readAnnualAmtList(153466, start, end);
	v2 = __dmi2.readAnnualAmtList(153466, start, end);
	assertEquals(v1, v2);		

	v1 = __dmi1.readAnnualAmtList(153466, null, null);
	v2 = __dmi2.readAnnualAmtList(153466, null, null);
	assertEquals(v1, v2);			
}

public void testReadAnnualRes() 
throws Exception {
	header("Annual Res");

	DateTime start = DateTime.parse("1985-01-01");
	DateTime end = DateTime.parse("1999-07-31");
	
	Vector v1 = __dmi1.readAnnualResList(63268, start, null);
	Vector v2 = __dmi2.readAnnualResList(63268, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readAnnualResList(63268, null, end);
	v2 = __dmi2.readAnnualResList(63268, null, end);
	assertEquals(v1, v2);	

	v1 = __dmi1.readAnnualResList(63268, start, end);
	v2 = __dmi2.readAnnualResList(63268, start, end);
	assertEquals(v1, v2);		

	v1 = __dmi1.readAnnualResList(63268, null, null);
	v2 = __dmi2.readAnnualResList(63268, null, null);
	assertEquals(v1, v2);			
}

public void testReadAnnualWC() 
throws Exception {
	header("Annual WC");

	DateTime start = DateTime.parse("1980-01-01");
	DateTime end = DateTime.parse("1997-07-31");
	
	Vector v1 = __dmi1.readAnnualWCList(119655, start, null);
	Vector v2 = __dmi2.readAnnualWCList(119655, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readAnnualWCList(119655, null, end);
	v2 = __dmi2.readAnnualWCList(119655, null, end);
	assertEquals(v1, v2);	

	v1 = __dmi1.readAnnualWCList(119655, start, end);
	v2 = __dmi2.readAnnualWCList(119655, start, end);
	assertEquals(v1, v2);		

	v1 = __dmi1.readAnnualWCList(119655, null, null);
	v2 = __dmi2.readAnnualWCList(119655, null, null);
	assertEquals(v1, v2);			
}

public void testReadAreaCap() 
throws Exception {
	header("Area Cap");
	Vector v2 = __dmi2.readAreaCapListForStructure_num(91727);
	Vector v1 = __dmi1.readAreaCapListForStructure_num(91727);
	assertEquals(v1, v2);
}

public void testReadCalls()
throws Exception {
	header("Calls");
	HydroBase_Calls c1 = __dmi1.readCallsForCall_num(1);
	HydroBase_Calls c2 = __dmi2.readCallsForCall_num(1);
	Vector v1 = new Vector();
	v1.add(c1);
	Vector v2 = new Vector();
	v2.add(c2);
	assertEquals(v1, v2);

	v1 = __dmi1.readCallsListForDiv(1, false);
	v2 = __dmi2.readCallsListForDiv(1, false);
	assertEquals(v1, v2);

	v1 = __dmi1.readCallsListForDiv(1, true);
	v2 = __dmi2.readCallsListForDiv(1, true);
	assertEquals(v1, v2);

	DateTime start = DateTime.parse("1998-01-01");
	DateTime end = DateTime.parse("1998-07-31");
	v1 = __dmi1.readCallsListForSetReleaseDates(start, end, 
		HydroBase_GUI_CallsQuery.CALLS);
	v2 = __dmi2.readCallsListForSetReleaseDates(start, end, 
		HydroBase_GUI_CallsQuery.CALLS);
	assertEquals(v1, v2);

	v1 = __dmi1.readCallsListForSetReleaseDates(start, end, 
		HydroBase_GUI_CallsQuery.EDIT_CALLS);
	v2 = __dmi2.readCallsListForSetReleaseDates(start, end, 
		HydroBase_GUI_CallsQuery.EDIT_CALLS);
	assertEquals(v1, v2);

	DateTime dt = DateTime.parse("1999-07-13");
	v1 = __dmi1.readCallsListForWISDiagram(1, 507, dt);
	v2 = __dmi2.readCallsListForWISDiagram(1, 507, dt);
	assertEquals(v1, v2);
}

public void testReadContact() 
throws Exception {
	header("Contact");
	Vector v1 = __dmi1.readContactListForRolodex_num(396510);
	Vector v2 = __dmi2.readContactListForRolodex_num(396510);
	assertEquals(v1, v2);
}

public void testReadCounty() 
throws Exception {
	header("County");
	Vector v1 = __dmi1.readCountyRefList();
	Vector v2 = __dmi2.readCountyRefList();
	assertEquals(v1, v2);
	Thread.sleep(1500);
}

public void testReadCourtCase() 
throws Exception {
	header("Court Case");
	Vector v1 = __dmi1.readCourtCaseListForStructure_num(7027);
	Vector v2 = __dmi2.readCourtCaseListForStructure_num(7027);
	assertEquals(v1, v2);
}

public void testReadCropChar() 
throws Exception {
	header("Crop Char");
	Vector v1 = __dmi1.readCropcharListForMethodDesc(
		"BLANEY-CRIDDLE_RIO_GRANDE", true);
	Vector v2 = __dmi2.readCropcharListForMethodDesc(
		"BLANEY-CRIDDLE_RIO_GRANDE", true);
	assertEquals(v1, v2);

	v1 = __dmi1.readCropcharListForMethodDesc(
		"BLANEY-CRIDDLE_RIO_GRANDE", false);
	v2 = __dmi2.readCropcharListForMethodDesc(
		"BLANEY-CRIDDLE_RIO_GRANDE", false);
	assertEquals(v1, v2);	

	v1 = __dmi1.readCropcharListForMethodDesc(null, false);
	v2 = __dmi2.readCropcharListForMethodDesc(null, false);
	assertEquals(v1, v2);	

	v1 = __dmi1.readCropcharListForMethodDesc(null, true);
	v2 = __dmi2.readCropcharListForMethodDesc(null, true);
		
	assertEquals(v1, v2);	

	v1 = __dmi1.readCropcharList();
	v2 = __dmi2.readCropcharList();
	assertEquals(v1, v2);
}

public void testReadCropGrowth() 
throws Exception {
	header("Crop Growth");
	Vector[] v1 = __dmi1.readCropGrowthList(null, "*");
	Vector[] v2 = __dmi2.readCropGrowthList(null, "*");
	assertEquals(v1[0], v2[0]);
	assertEquals(v1[1], v2[1]);
	assertEquals(v1[2], v2[2]);
}

public void testReadCropRef() 
throws Exception {
	header("Crop Ref");
	Vector v1 = __dmi1.readCropRefList();
	Vector v2 = __dmi2.readCropRefList();
	assertEquals(v1, v2);
}

public void testReadCUBlaneyCriddle()
throws Exception {
	header("CU Blaney Criddle");
	Vector v1 = __dmi1.readCUBlaneyCriddleListForMethodDesc(
		"BLANEY-CRIDDLE_RIO_GRANDE", true);
	Vector v2 = __dmi2.readCUBlaneyCriddleListForMethodDesc(
		"BLANEY-CRIDDLE_RIO_GRANDE", true);
	assertEquals(v1, v2);

	v1 = __dmi1.readCUBlaneyCriddleListForMethodDesc(
		"BLANEY-CRIDDLE_RIO_GRANDE", false);
	v2 = __dmi2.readCUBlaneyCriddleListForMethodDesc(
		"BLANEY-CRIDDLE_RIO_GRANDE", false);
	assertEquals(v1, v2);

	v1 = __dmi1.readCUBlaneyCriddleListForMethodDesc(null, false);
	v2 = __dmi2.readCUBlaneyCriddleListForMethodDesc(null, false);
	assertEquals(v1, v2);

	v1 = __dmi1.readCUBlaneyCriddleListForMethodDesc(null, true);
	v2 = __dmi2.readCUBlaneyCriddleListForMethodDesc(null, true);
	assertEquals(v1, v2);
}

public void testReadCUCoeff() 
throws Exception {
	header("CU Coeff");
	Vector v2 = __dmi2.readCUCoeffList();
	Vector v1 = __dmi1.readCUCoeffList();
	assertEquals(v1, v2);
}

public void testReadCUMethod() 
throws Exception {
	header("CU Method");
	Vector v1 = __dmi1.readCUMethodList(true);
	Vector v2 = __dmi2.readCUMethodList(true);
	assertEquals(v1, v2);

	v1 = __dmi1.readCUMethodList(false);
	v2 = __dmi2.readCUMethodList(false);
	assertEquals(v1, v2);
}

public void testReadDailyAmt() 
throws Exception {
	header("Daily Amt");
	DateTime start = DateTime.parse("1980-01-01");
	DateTime end = DateTime.parse("1990-01-01");	
	Vector v1 = __dmi1.readDailyAmtList(104041, start, end);
	Vector v2 = __dmi2.readDailyAmtList(104041, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readDailyAmtList(104041, start, null);
	v2 = __dmi2.readDailyAmtList(104041, start, null);
	assertEquals(v1, v2);	

	v1 = __dmi1.readDailyAmtList(104041, null, end);
	v2 = __dmi2.readDailyAmtList(104041, null, end);
	assertEquals(v1, v2);		

	v1 = __dmi1.readDailyAmtList(104041, null, null);
	v2 = __dmi2.readDailyAmtList(104041, null, null);
	assertEquals(v1, v2);		
}

public void testReadDailyStation() 
throws Exception {
	header("Daily Station");

	//////////////////////////////////////////////////////////
	DateTime start = DateTime.parse("1972-01-01");
	DateTime end = DateTime.parse("1978-01-01");	
	Vector v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_EVAP,
		1732, start, end);
	Vector v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_EVAP,
		1732, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_EVAP,
		1732, start, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_EVAP,
		1732, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_EVAP,
		1732, null, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_EVAP,
		1732, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_EVAP,
		1732, null, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_EVAP,
		1732, null, null);
	assertEquals(v1, v2);

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1951-01-01");
	end = DateTime.parse("1957-01-01");	
	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_FLOW, 
		2732, start, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_FLOW, 
		2732, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_FLOW, 
		2732, start, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_FLOW, 
		2732, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_FLOW, 
		2732, null, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_FLOW, 
		2732, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_FLOW, 
		2732, null, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_FLOW, 
		2732, null, null);
	assertEquals(v1, v2);

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1970-01-01");
	end = DateTime.parse("1980-01-01");	
	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_MAX_T, 
		1800, start, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_MAX_T, 
		1800, start, end);
	assertEquals(v1, v2);	

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_MAX_T, 
		1800, start, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_MAX_T, 
		1800, start, null);
	assertEquals(v1, v2);	

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_MAX_T, 
		1800, null, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_MAX_T, 
		1800, null, end);
	assertEquals(v1, v2);	

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_MAX_T, 
		1800, null, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_MAX_T, 
		1800, null, null);
	assertEquals(v1, v2);	

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1970-01-01");
	end = DateTime.parse("1980-01-01");	
	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_MIN_T, 
		2110, start, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_MIN_T, 
		2110, start, end);
	assertEquals(v1, v2);		

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_MIN_T, 
		2110, start, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_MIN_T, 
		2110, start, null);
	assertEquals(v1, v2);		

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_MIN_T, 
		2110, null, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_MIN_T, 
		2110, null, end);
	assertEquals(v1, v2);		

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_MIN_T, 
		2110, null, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_MIN_T, 
		2110, null, null);
	assertEquals(v1, v2);		

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1985-01-01");
	end = DateTime.parse("1995-01-01");	
	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_PCPN, 
		71, start, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_PCPN, 
		71, start, end);
	assertEquals(v1, v2);					

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_MIN_T, 
		2110, start, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_MIN_T, 
		2110, start, null);
	assertEquals(v1, v2);		

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_MIN_T, 
		2110, null, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_MIN_T, 
		2110, null, end);
	assertEquals(v1, v2);		

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_MIN_T, 
		2110, null, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_MIN_T, 
		2110, null, null);
	assertEquals(v1, v2);		

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1970-01-01");
	end = DateTime.parse("1980-01-01");	
	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_SNOW, 
		896, start, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_SNOW, 
		896, start, end);
	assertEquals(v1, v2);			

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_SNOW, 
		896, start, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_SNOW, 
		896, start, null);
	assertEquals(v1, v2);			

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_SNOW, 
		896, null, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_SNOW, 
		896, null, end);
	assertEquals(v1, v2);			

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_SNOW, 
		896, null, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_SNOW, 
		896, null, null);
	assertEquals(v1, v2);			

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1985-01-01");
	end = DateTime.parse("1995-01-01");	
	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_SOLAR, 
		5482, start, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_SOLAR, 
		5482, start, end);
	assertEquals(v1, v2);				

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_SOLAR, 
		5482, start, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_SOLAR, 
		5482, start, null);
	assertEquals(v1, v2);				

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_SOLAR, 
		5482, null, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_SOLAR, 
		5482, null, end);
	assertEquals(v1, v2);				

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_SOLAR, 
		5482, null, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_SOLAR, 
		5482, null, null);
	assertEquals(v1, v2);				

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1994-01-01");
	end = DateTime.parse("1997-01-01");	
	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_VP, 
		5478, start, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_VP, 
		5478, start, end);
	assertEquals(v1, v2);					

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_VP, 
		5478, start, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_VP, 
		5478, start, null);
	assertEquals(v1, v2);					

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_VP, 
		5478, null, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_VP, 
		5478, null, end);
	assertEquals(v1, v2);					

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_VP, 
		5478, null, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_VP, 
		5478, null, null);
	assertEquals(v1, v2);					

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1985-01-01");
	end = DateTime.parse("1995-01-01");	
	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_WIND, 
		5483, start, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_WIND, 
		5483, start, end);
	assertEquals(v1, v2);					

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_WIND, 
		5483, start, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_WIND, 
		5483, start, null);
	assertEquals(v1, v2);					

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_WIND, 
		5483, null, end);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_WIND, 
		5483, null, end);
	assertEquals(v1, v2);					

	v1 = __dmi1.readDailyStationData(__dmi1.__S_DAILY_WIND, 
		5483, null, null);
	v2 = __dmi2.readDailyStationData(__dmi2.__S_DAILY_WIND, 
		5483, null, null);
	assertEquals(v1, v2);					
}

public void testReadDailyWC() 
throws Exception {
	header("Daily WC");
	DateTime start = DateTime.parse("1960-01-01");
	DateTime end = DateTime.parse("1980-01-01");	
	Vector v1 = __dmi1.readDailyWCList(191271, start, end);
	Vector v2 = __dmi2.readDailyWCList(191271, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readDailyWCList(191271, start, null);
	v2 = __dmi2.readDailyWCList(191271, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readDailyWCList(191271, null, end);
	v2 = __dmi2.readDailyWCList(191271, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readDailyWCList(191271, null, null);
	v2 = __dmi2.readDailyWCList(191271, null, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readDailyWCListForStructure_numRecordType(45824, "H");
	v2 = __dmi2.readDailyWCListForStructure_numRecordType(45824, "H");
	assertEquals(v1, v2);

	v1 = __dmi1.readDailyWCListForStructure_numRecordType(42652, "R");
	v2 = __dmi2.readDailyWCListForStructure_numRecordType(42652, "R");
	assertEquals(v1, v2);
}

public void testReadDam() 
throws Exception {
	header("Dam");
	HydroBase_StructureDam d1 = __dmi1.readDamForStructure_num(117595);
	HydroBase_StructureDam d2 = __dmi2.readDamForStructure_num(117595);
	Vector v1 = new Vector();
	v1.add(d1);
	Vector v2 = new Vector();
	v2.add(d2);
	assertEquals(v1, v2);

	v1 = __dmi1.readDamInspectionListForStructure_num(117777);
	v2 = __dmi2.readDamInspectionListForStructure_num(117777);
	assertEquals(v1, v2);

	v1 = __dmi1.readDamOutletListForStructure_num(117777);
	v2 = __dmi2.readDamOutletListForStructure_num(117777);
	assertEquals(v1, v2);

	v1 = __dmi1.readDamSpillwayListForStructure_num(117598);
	v2 = __dmi2.readDamSpillwayListForStructure_num(117598);
	assertEquals(v1, v2);
}

public void testReadDBVersion() 
throws Exception {
	header("DB Version");
	Vector v1 = __dmi1.readDBVersionListForVersionType("data");
	Vector v2 = __dmi2.readDBVersionListForVersionType("data");
	assertEquals(v1, v2);

	v1 = __dmi1.readDBVersionListForVersionType(null);
	v2 = __dmi2.readDBVersionListForVersionType(null);
	assertEquals(v1, v2);	
}

public void testReadDiversionComment() 
throws Exception {
	header("Diversion Comment");
	DateTime start = DateTime.parse("1960-01-01");
	DateTime end = DateTime.parse("1980-01-01");		
	Vector v1 = __dmi1.readDiversionCommentList(1, start, end);
	Vector v2 = __dmi2.readDiversionCommentList(1, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readDiversionCommentList(1, start, null);
	v2 = __dmi2.readDiversionCommentList(1, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readDiversionCommentList(1, null, end);
	v2 = __dmi2.readDiversionCommentList(1, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readDiversionCommentList(1, null, null);
	v2 = __dmi2.readDiversionCommentList(1, null, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readDiversionCommentListForStructure_num(2);
	v2 = __dmi2.readDiversionCommentListForStructure_num(2);
	assertEquals(v1, v2);	
}

public void testReadEmergencyPlan() 
throws Exception {
	header("Emergency Plan");
	Vector v1 = __dmi1.readEmergencyPlanListForStructure_num(118115);
	Vector v2 = __dmi2.readEmergencyPlanListForStructure_num(118115);
	assertEquals(v1, v2);
}

public void testReadEquipment() 
throws Exception {
	header("Equipment");
	HydroBase_Equipment e1 = __dmi1.readEquipmentForStructure_num(72100);
	HydroBase_Equipment e2 = __dmi2.readEquipmentForStructure_num(72100);
	Vector v1 = new Vector();
	v1.add(e1);
	Vector v2 = new Vector();
	v2.add(e2);
	assertEquals(v1, v2);
}

public void testReadFrostDates() 
throws Exception {
	header("Frost Dates");
	DateTime start = DateTime.parse("1910-01-01");
	DateTime end = DateTime.parse("1930-01-01");		
	Vector v1 = __dmi1.readFrostDatesList(5442, start, end);
	Vector v2 = __dmi2.readFrostDatesList(5442, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readFrostDatesList(5442, start, null);
	v2 = __dmi2.readFrostDatesList(5442, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readFrostDatesList(5442, null, end);
	v2 = __dmi2.readFrostDatesList(5442, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readFrostDatesList(5442, null, null);
	v2 = __dmi2.readFrostDatesList(5442, null, null);
	assertEquals(v1, v2);
}

public void testReadGeneralComment()
throws Exception {
	header("General Comment");
	Vector v1 = __dmi1.readGeneralCommentListForStructure_num(94368);
	Vector v2 = __dmi2.readGeneralCommentListForStructure_num(94368);
	assertEquals(v1, v2);
}

public void testReadGeoloc()
throws Exception {
	header("Geoloc");
	HydroBase_Geoloc g1 = __dmi1.readGeolocForGeoloc_num(71141);
	HydroBase_Geoloc g2 = __dmi2.readGeolocForGeoloc_num(71141);
	Vector v1 = new Vector();
	v1.add(g1);
	Vector v2 = new Vector();
	v2.add(g2);
	assertEquals(v1, v2);
}

public void testReadGeophlogs()
throws Exception {
	header("Geophlogs");
	Vector v1 = __dmi1.readGroundWaterWellsGeophlogsList(null, null);
	Vector v2 = __dmi2.readGroundWaterWellsGeophlogsList(null, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readGeophlogsListForWell_num(1422);
	v2 = __dmi2.readGeophlogsListForWell_num(1422);
	assertEquals(v1, v2);
}

public void testReadLocType()
throws Exception {
	header("Loc Type");
	Vector v1 = __dmi1.readLocTypeList();
	Vector v2 = __dmi2.readLocTypeList();
	assertEquals(v1, v2);
}

public void testReadMapfile()
throws Exception {
	header("Map File");
	Vector v1 = __dmi1.readMapfileListForStructure_num(34920);
	Vector v2 = __dmi2.readMapfileListForStructure_num(34920);
	assertEquals(v1, v2);
}

public void testReadMeasType() 
throws Exception {
	header("Meas Type");
	Vector v1 = __dmi1.readMeasTypeDistinctList();
	Vector v2 = __dmi2.readMeasTypeDistinctList();
	assertEquals(v1, v2);

	v1 = __dmi1.readMeasTypeList(669, null, null, null, null);
	v2 = __dmi2.readMeasTypeList(669, null, null, null, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readMeasTypeList(669, "Streamflow", null, "Daily", "DWR"); 
	v2 = __dmi2.readMeasTypeList(669, "Streamflow", null, "Daily", "DWR");
	assertEquals(v1, v2);

	v1 = __dmi1.readMeasTypeList(-999, "RT_Misc", "GAGE_HT", null, null);
	v2 = __dmi2.readMeasTypeList(-999, "RT_Misc", "GAGE_HT", null, null);
	assertEquals(v1, v2);
}

public void testReadMonthlyStation() 
throws Exception {
	header("Monthly Station");

	//////////////////////////////////////////////////////////
	DateTime start = DateTime.parse("1950-01-01");
	DateTime end = DateTime.parse("1960-01-01");	
	Vector v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_EVAP,
		1785, start, end);
	Vector v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_EVAP,
		1785, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_EVAP,
		1785, start, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_EVAP,
		1785, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_EVAP,
		1785, null, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_EVAP,
		1785, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_EVAP,
		1785, null, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_EVAP,
		1785, null, null);
	assertEquals(v1, v2);

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1950-01-01");
	end = DateTime.parse("1960-01-01");	
	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_MAX_FLOW,
		3866, start, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_MAX_FLOW,
		3866, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_MAX_FLOW,
		3866, start, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_MAX_FLOW,
		3866, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_MAX_FLOW,
		3866, null, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_MAX_FLOW,
		3866, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_MAX_FLOW,
		3866, null, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_MAX_FLOW,
		3866, null, null);
	assertEquals(v1, v2);

	//////////////////////////////////////////////////////////
	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_MIN_FLOW,
		3866, start, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_MIN_FLOW,
		3866, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_MAX_FLOW,
		3866, start, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_MAX_FLOW,
		3866, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_MAX_FLOW,
		3866, null, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_MAX_FLOW,
		3866, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_MAX_FLOW,
		3866, null, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_MAX_FLOW,
		3866, null, null);
	assertEquals(v1, v2);

	//////////////////////////////////////////////////////////
	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_FLOW,
		3866, start, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_FLOW,
		3866, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_FLOW,
		3866, start, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_FLOW,
		3866, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_FLOW,
		3866, null, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_FLOW,
		3866, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_FLOW,
		3866, null, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_FLOW,
		3866, null, null);
	assertEquals(v1, v2);

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1950-01-01");
	end = DateTime.parse("1960-01-01");	
	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_NFLOW,
		5635, start, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_NFLOW,
		5635, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_NFLOW,
		5635, start, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_NFLOW,
		5635, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_NFLOW,
		5635, null, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_NFLOW,
		5635, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_NFLOW,
		5635, null, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_NFLOW,
		5635, null, null);
	assertEquals(v1, v2);

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1950-01-01");
	end = DateTime.parse("1960-01-01");	
	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_PCPN,
		453, start, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_PCPN,
		453, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_PCPN,
		453, start, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_PCPN,
		453, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_PCPN,
		453, null, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_PCPN,
		453, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_PCPN,
		453, null, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_PCPN,
		453, null, null);
	assertEquals(v1, v2);

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1950-01-01");
	end = DateTime.parse("1952-01-01");	
	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_SNOW,
		-999, start, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_SNOW,
		-999, start, end);
	assertEquals(v1, v2);

	/*
	Returns far too many records for our testing needs
	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_SNOW,
		-999, start, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_SNOW,
		-999, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_SNOW,
		-999, null, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_SNOW,
		-999, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_TOTAL_SNOW,
		-999, null, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_TOTAL_SNOW,
		-999, null, null);
	assertEquals(v1, v2);
	*/

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1950-01-01");
	end = DateTime.parse("1960-01-01");	
	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_AVG_MAX_T,
		2427, start, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_AVG_MAX_T,
		2427, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_AVG_MAX_T,
		2427, start, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_AVG_MAX_T,
		2427, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_AVG_MAX_T,
		2427, null, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_AVG_MAX_T,
		2427, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_AVG_MAX_T,
		2427, null, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_AVG_MAX_T,
		2427, null, null);
	assertEquals(v1, v2);

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1950-01-01");
	end = DateTime.parse("1960-01-01");	
	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_MEAN_T,
		2427, start, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_MEAN_T,
		2427, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_MEAN_T,
		2427, start, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_MEAN_T,
		2427, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_MEAN_T,
		2427, null, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_MEAN_T,
		2427, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_MEAN_T,
		2427, null, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_MEAN_T,
		2427, null, null);
	assertEquals(v1, v2);

	//////////////////////////////////////////////////////////
	start = DateTime.parse("1950-01-01");
	end = DateTime.parse("1960-01-01");	
	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_AVG_MIN_T,
		2427, start, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_AVG_MIN_T,
		2427, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_AVG_MIN_T,
		2427, start, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_AVG_MIN_T,
		2427, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_AVG_MIN_T,
		2427, null, end);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_AVG_MIN_T,
		2427, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readMonthlyStationData(__dmi1.__S_MONTHLY_AVG_MIN_T,
		2427, null, null);
	v2 = __dmi2.readMonthlyStationData(__dmi2.__S_MONTHLY_AVG_MIN_T,
		2427, null, null);
	assertEquals(v1, v2);
}
public void testReadNetAmts() 
throws Exception {
	header("Net Amts");

	Vector v1 = __dmi1.readNetAmtsList(70840, 8, 8146, true, null);
	Vector v2 = __dmi2.readNetAmtsList(70840, 8, 8146, true, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readNetAmtsList(70840, 8, 8146, false, null);
	v2 = __dmi2.readNetAmtsList(70840, 8, 8146, false, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readNetAmtsList(70840, -999, -999, false, null);
	v2 = __dmi2.readNetAmtsList(70840, -999, -999, false, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readNetAmtsList(70840, -999, -999, true, null);
	v2 = __dmi2.readNetAmtsList(70840, -999, -999, true, null);
	assertEquals(v1, v2);	

	v1 = __dmi1.readNetAmtsList(-999, 1, -999, true, "72");
	v2 = __dmi2.readNetAmtsList(-999, 1, -999, true, "72");
	assertEquals(v1, v2);	

	String[] where = new String[2];
	where[0] = "wd = 2";
	where[1] = "wd 2";

	v1 = __dmi1.readNetAmtsList(null, where, null, 
		HydroBase_Report_NetAmounts.STREAM);
	v2 = __dmi2.readNetAmtsList(null, where, null, 
		HydroBase_Report_NetAmounts.STREAM);
	assertEquals(v1, v2);

	where[0] = "wd = 24";
	where[1] = "wd 24";
	v1 = __dmi1.readNetAmtsList(null, where, null, 
		HydroBase_Report_NetAmounts.ADMIN_STREAM);
	v2 = __dmi2.readNetAmtsList(null, where, null, 
		HydroBase_Report_NetAmounts.ADMIN_STREAM);
	assertEquals(v1, v2);
}

public void testReadParcelUse() 
throws Exception {
	header("Parcel Use");
	DateTime start = DateTime.parse("1993-01-01");
	DateTime end = DateTime.parse("1999-01-01");	
	Vector v1 = __dmi1.readParcelUseTSList(1998, -999, -999, "SMALL_GRAINS",
		null, null, null);
	Vector v2 = __dmi2.readParcelUseTSList(1998, -999, -999, "SMALL_GRAINS",
		null, null, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readParcelUseTSList(1998, 3, 10177, null, null, null, null);
	v2 = __dmi2.readParcelUseTSList(1998, 3, 10177, null, null, null, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readParcelUseTSList(1998, 3, -999, "SMALL_GRAINS", null, 
		null, null);
	v2 = __dmi2.readParcelUseTSList(1998, 3, -999, "SMALL_GRAINS", null, 
		null, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readParcelUseTSList(1998, 3, -999, null, "SPRINKLER", 
		null, null);
	v2 = __dmi2.readParcelUseTSList(1998, 3, -999, null, "SPRINKLER", 
		null, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readParcelUseTSList(-999, -999, -999, null, "SPRINKLER", 
		start, end);
	v2 = __dmi2.readParcelUseTSList(-999, -999, -999, null, "SPRINKLER", 
		start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readParcelUseTSStructureToParcelListForStructure_num(
		20546);	
	v2 = __dmi2.readParcelUseTSStructureToParcelListForStructure_num(	
		20546);	
	assertEquals(v1, v2);

	v1 = __dmi1.readParcelUseTSDistinctCalYearsList(-999);
	v2 = __dmi2.readParcelUseTSDistinctCalYearsList(-999);
	assertIntegerVectorEquals(v1, v2);

	v1 = __dmi1.readParcelUseTSDistinctCalYearsList(3);
	v2 = __dmi2.readParcelUseTSDistinctCalYearsList(3);
	assertIntegerVectorEquals(v1, v2);
}

public void testReadPersonDetails()
throws Exception {
	header("Person Details");
	HydroBase_PersonDetails pd1 
		= __dmi1.readPersonDetailsForStructure_num(7009);
	HydroBase_PersonDetails pd2
		= __dmi2.readPersonDetailsForStructure_num(7009);
	Vector v1 = new Vector();
	v1.add(pd1);
	Vector v2 = new Vector();
	v2.add(pd2);
	
	assertEquals(v1, v2);
}

public void testReadPumpTest()
throws Exception {
	// table has no data
	header("Pump Test");
	String[] where = new String[2];
	where[0] = "structure.div = 3";
	where[1] = "div 3";	
	Vector v1 = __dmi1.readGroundWaterWellsPumpingTestList(null, where);
	Vector v2 = __dmi2.readGroundWaterWellsPumpingTestList(null, where);
	assertEquals(v1, v2);
}

public void testReadRefCIU()
throws Exception {
	header("Ref CIU");
	Vector v1 = __dmi1.readRefCIUList();
	Vector v2 = __dmi2.readRefCIUList();
	assertEquals(v1, v2);
}

public void testReadResEOM() 
throws Exception {
	header("Res EOM");
	// table has no data, hence the -1s
	DateTime start = DateTime.parse("1993-01-01");
	DateTime end = DateTime.parse("1999-01-01");	
	Vector v1 = __dmi1.readResEOMList(-1, null, null);
	Vector v2 = __dmi2.readResEOMList(-1, null, null);
	assertEquals(v1, v2);


	v1 = __dmi1.readResEOMList(-1, start, null);
	v2 = __dmi2.readResEOMList(-1, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readResEOMList(-1, null, end);
	v2 = __dmi2.readResEOMList(-1, null, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readResEOMList(-1, start, end);
	v2 = __dmi2.readResEOMList(-1, start, end);
	assertEquals(v1, v2);
}

public void testReadResMeas() 
throws Exception {
	header("Res Meas");
	DateTime start = DateTime.parse("1980-01-01");
	DateTime end = DateTime.parse("1990-01-01");	
	Vector v1 = __dmi1.readResMeasList(67815, null, null);
	Vector v2 = __dmi2.readResMeasList(67815, null, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readResMeasList(67815, start, null);
	v2 = __dmi2.readResMeasList(67815, start, null);
	assertEquals(v1, v2);	

	v1 = __dmi1.readResMeasList(67815, null, end);
	v2 = __dmi2.readResMeasList(67815, null, end);
	assertEquals(v1, v2);	

	v1 = __dmi1.readResMeasList(67815, start, end);
	v2 = __dmi2.readResMeasList(67815, start, end);
	assertEquals(v1, v2);	

	v1 = __dmi1.readResMeasListForStructure_num(1);
	v2 = __dmi2.readResMeasListForStructure_num(1);
	assertEquals(v1, v2);			
}

public void testReadRolodex() 
throws Exception {
	header("Rolodex");
	HydroBase_Rolodex r1 = __dmi1.readRolodexForRolodex_num(3757);
	HydroBase_Rolodex r2 = __dmi2.readRolodexForRolodex_num(3757);
	Vector v1 = new Vector();
	v1.add(r1);
	Vector v2 = new Vector();
	v2.add(r2);
	assertEquals(v1, v2);

	r1 = __dmi1.readRolodexForRolodex_numStructure_num(3757, 7370);
	r2 = __dmi2.readRolodexForRolodex_numStructure_num(3757, 7370);
	v1 = new Vector();
	v1.add(r1);
	v2 = new Vector();
	v2.add(r2);
	assertEquals(v1, v2);	
}

public void testReadRTMeas() 
throws Exception {
	header("RTMeas");

	DateTime start = DateTime.parse("2004-04-01");
	DateTime end = DateTime.parse("2004-04-20");

	Vector v1 = __dmi1.readRTMeasList(7523, start, end, false, false);
	Vector v2 = __dmi2.readRTMeasList(7523, start, end, false, false);
	assertEquals(v1, v2);	

	v1 = __dmi1.readRTMeasList(7523, start, end, false, true);
	v2 = __dmi2.readRTMeasList(7523, start, end, false, true);
	assertEquals(v1, v2);		
}


public void testReadSnowCrse() 
throws Exception {
	header("Snow Crse");
	DateTime start = DateTime.parse("1970-01-01");
	DateTime end = DateTime.parse("1980-01-01");
	Vector v1 = __dmi1.readSnowCrseList(5284, null, null);
	Vector v2 = __dmi2.readSnowCrseList(5284, null, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readSnowCrseList(5284, start, end);
	v2 = __dmi2.readSnowCrseList(5284, start, end);
	assertEquals(v1, v2);	

	v1 = __dmi1.readSnowCrseList(5284, null, end);
	v2 = __dmi2.readSnowCrseList(5284, null, end);
	assertEquals(v1, v2);	

	v1 = __dmi1.readSnowCrseList(5284, start, null);
	v2 = __dmi2.readSnowCrseList(5284, start, null);
	assertEquals(v1, v2);	
}

public void testReadStation()
throws Exception {
	header("Station");
	
	Vector v1 = __dmi1.readStationGeolocCUClimWtsListForCountyAndHydrounit(
		"ARCHULETA", null);
	Vector v2 = __dmi2.readStationGeolocCUClimWtsListForCountyAndHydrounit(
		"ARCHULETA", null);
	assertEquals(v1, v2);

	v1 = __dmi1.readStationGeolocCUClimWtsListForCountyAndHydrounit(
		null, "14080101");
	v2 = __dmi2.readStationGeolocCUClimWtsListForCountyAndHydrounit(
		null, "14080101");
	assertEquals(v1, v2);	

	v1 = __dmi1.readStationGeolocCUClimWtsListForCountyAndHydrounit(
		"ARCHULETA", "14080101");
	v2 = __dmi2.readStationGeolocCUClimWtsListForCountyAndHydrounit(
		"ARCHULETA", "14080101");
	assertEquals(v1, v2);	

	HydroBase_StationView vw1 = __dmi1.readStationViewForStation_id("0092");
	HydroBase_StationView vw2 = __dmi2.readStationViewForStation_id("0092");
	v1 = new Vector();
	v1.add(vw1);
	v2 = new Vector();
	v2.add(vw2);
	assertEquals(v1, v2);	

	vw1 = __dmi1.readStationViewForStation_id("0102");
	vw2 = __dmi2.readStationViewForStation_id("0102");
	v1 = new Vector();
	v1.add(vw1);
	v2 = new Vector();
	v2.add(vw2);
	assertEquals(v1, v2);	

	vw1 = __dmi1.readStationViewForStation_id("0105");
	vw2 = __dmi2.readStationViewForStation_id("0105");
	v1 = new Vector();
	v1.add(vw1);
	v2 = new Vector();
	v2.add(vw2);
	assertEquals(v1, v2);	

	String[] where = new String[2];
	where[0] = "wd = 1";
	where[1] = "wd 1";

	v1 = __dmi1.readStationGeolocMeasTypeList(null, where, null, 
		"Precip", "Daily", null, null, null, false);
	v2 = __dmi2.readStationGeolocMeasTypeList(null, where, null, 
		"Precip", "Daily", null, null, null, false);
	assertEquals(v1, v2);

	v1 = __dmi1.readStationGeolocMeasTypeList(null, where, null, 
		null, null, "DISCHRG", null, null, false);
	v2 = __dmi2.readStationGeolocMeasTypeList(null, where, null, 
		null, null, "DISCHRG", null, null, false);
	assertEquals(v1, v2);	

	v1 = __dmi1.readStationGeolocMeasTypeList(null, where, null, 
		null, null, null, "DWR", "0", false);
	v2 = __dmi2.readStationGeolocMeasTypeList(null, where, null, 
		null, null, null, "DWR", "0", false);
	assertEquals(v1, v2);		

	v1 = __dmi1.readStationGeolocMeasTypeList(null, where, null, 
		"RT_", null, null, null, null, true);
	v2 = __dmi2.readStationGeolocMeasTypeList(null, where, null, 
		"RT_", null, null, null, null, true);
	assertEquals(v1, v2);			

	v1 = __dmi1.readStationGeolocMeasTypeListForWD(1, "Snow", null, null,
		"NOAA", false);
	v2 = __dmi2.readStationGeolocMeasTypeListForWD(1, "Snow", null,null, 
		"NOAA", false);
	assertEquals(v1, v2);			

	v1 = __dmi1.readStationGeolocMeasTypeListForWD(1, "Snow", null,null, 
		"NOAA", true);
	v2 = __dmi2.readStationGeolocMeasTypeListForWD(1, "Snow", null,null, 
		"NOAA", true);
	assertEquals(v1, v2);

	v1 = __dmi1.readStationGeolocMeasTypeListForWD(1, null, 
		"REAL-TIME", "DISCHRG", null, false);
	v2 = __dmi2.readStationGeolocMeasTypeListForWD(1, null,  
		"REAL-TIME", "DISCHRG", null, false);
	assertEquals(v1, v2);
}

public void testReadStream()
throws Exception {
	header("Stream");
	HydroBase_Stream s1 = __dmi1.readStreamForStream_num(1);
	HydroBase_Stream s2 = __dmi2.readStreamForStream_num(1);
	Vector v1 = new Vector();
	v1.add(s1);
	Vector v2 = new Vector();
	v2.add(s2);
	assertEquals(v1, v2);

	v1 = __dmi1.readStreamListForWDStr_trib_to(32, -999);
	v2 = __dmi2.readStreamListForWDStr_trib_to(32, -999);
	assertEquals(v1, v2);

	v1 = __dmi1.readStreamListForWDStr_trib_to(32, 133);
	v2 = __dmi2.readStreamListForWDStr_trib_to(32, 133);
	assertEquals(v1, v2);
}

public void testReadStrType() 
throws Exception {
	header("Str Type");
	Vector v1 = __dmi1.readStrTypeList();
	Vector v2 = __dmi2.readStrTypeList();
	assertEquals(v1, v2);
}

public void testReadStructMeasType() 
throws Exception {
	header("Struct Meas Type");
	Vector v1 = __dmi1.readStructMeasTypeDistinctList();
	Vector v2 = __dmi2.readStructMeasTypeDistinctList();
	assertEquals(v1, v2);
}

public void testReadStructure() 
throws Exception {
	header("Structure");

	Vector v1 = __dmi1.readStructureAKAListForStructure_num(52234);
	Vector v2 = __dmi2.readStructureAKAListForStructure_num(52234);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructureDistinctWDList();
	v2 = __dmi2.readStructureDistinctWDList();
	assertIntegerVectorEquals(v1, v2);

	// no data
	v1 = __dmi1.readStructureMFReachListForStructure_num(444);
	v2 = __dmi2.readStructureMFReachListForStructure_num(444);
	assertEquals(v1, v2);

	HydroBase_StructureReservoir r1 
		= __dmi1.readStructureReservoirForStructure_num(444);
	HydroBase_StructureReservoir r2 
		= __dmi2.readStructureReservoirForStructure_num(444);
	v1 = new Vector();
	v1.add(r1);
	v2 = new Vector();
	v2.add(r2);
	assertEquals(v1, v2);

	r1 = __dmi1.readStructureReservoirForWDID(40, 3830);
	r2 = __dmi2.readStructureReservoirForWDID(40, 3830);
	v1 = new Vector();
	v1.add(r1);
	v2 = new Vector();
	v2.add(r2);	
	assertEquals(v1, v2);

	HydroBase_StructureSmallDam d1 
		= __dmi1.readStructureSmallDamForStructure_num(98880);
	HydroBase_StructureSmallDam d2 
		= __dmi2.readStructureSmallDamForStructure_num(98880);
	v1 = new Vector();
	v1.add(d1);
	v2 = new Vector();
	v2.add(d2);		
	assertEquals(v1, v2);

	int size = -1;
	
	v1 = __dmi1.readStructMeasTypeListForStructure_num(30975, "IDivTotal",
		null, "Annual", null);
	v2 = __dmi2.readStructMeasTypeListForStructure_num(30975, "IDivTotal",
		null, "Annual", null);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructMeasTypeListForStructure_num(128117, null,
		"S:1 F: U:1 T:", null, null);
	v2 = __dmi2.readStructMeasTypeListForStructure_num(128117, null,
		"S:1 F: U:1 T:", null, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructMeasTypeListForStructure_num(30975, true);
	v2 = __dmi2.readStructMeasTypeListForStructure_num(30975, true);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructMeasTypeListForStructure_num(128117, false);
	v2 = __dmi2.readStructMeasTypeListForStructure_num(128117, false);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructMeasTypeListForStructure_numMeas_type(30975,
		"DivComment");
	v2 = __dmi2.readStructMeasTypeListForStructure_numMeas_type(30975, 
		"DivComment");
	assertEquals(v1, v2);

	HydroBase_StructureView sv1 = __dmi1.readStructureViewForWDID(4, 542);
	HydroBase_StructureView sv2 = __dmi2.readStructureViewForWDID(4, 542);
	v1 = new Vector();
	v1.add(sv1);
	v2 = new Vector();
	v2.add(sv2);
	assertEquals(v1, v2);

	String[] where = new String[2];
	where[0] = "geoloc.wd = 37";
	where[1] = "wd 37";

	v1 = __dmi1.readStructureGeolocList(null, where, null);
	v2 = __dmi2.readStructureGeolocList(null, where, null);
	assertEquals(v1, v2);

	Vector wdids = new Vector();
	wdids.add("3700540");
	wdids.add("3700646");
	wdids.add("3701244");
	wdids.add("3703635");
	wdids.add("3703682");
	wdids.add("3703704");
	wdids.add("3703713");
	wdids.add("3703715");
	wdids.add("3703716");
	wdids.add("3707214");

	v1 = __dmi1.readStructureViewListForWDIDList(wdids);
	v2 = __dmi2.readStructureViewListForWDIDList(wdids);
	assertEquals(v1, v2);	

	v1 = __dmi1.readStructureIrrigSummaryListForStructure_num(267903);
	v2 = __dmi2.readStructureIrrigSummaryListForStructure_num(267903);
	assertEquals(v1, v2);

	sv1 = __dmi1.readStructureViewForWDID(37, 3713);
	sv2 = __dmi2.readStructureViewForWDID(37, 3713);		
	v1 = new Vector();
	v1.add(sv1);
	v2 = new Vector();
	v2.add(sv2);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, 37578,
		-999, -999, null, null, null, null, false);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, 37578,
		-999, -999, null, null, null, null, false);
	assertEquals(v1, v2);	

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, -999,
		20, 505, null, null, null, null, false);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, -999,
		20, 505, null, null, null, null, false);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, -999,
		-999, -999, "ALAMOSA D", null, null, null, false);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, -999,
		-999, -999, "ALAMOSA D", null, null, null, false);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, -999,
		20, -999, null, "GRASS_PASTURE", null, null, false);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, -999,
		20, -999, null, "GRASS_PASTURE", null, null, false);
	assertEquals(v1, v2);

	DateTime start = DateTime.parse("1990-01-01");
	DateTime end = DateTime.parse("2000-01-01");

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, -999,
		20, -999, null, null, start, end, false);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, -999,
		20, -999, null, null, start, end, false);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, 37578,
		-999, -999, null, null, null, null, true);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, 37578,
		-999, -999, null, null, null, null, true);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, -999,
		20, 505, null, null, null, null, true);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, -999,
		20, 505, null, null, null, null, true);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, -999,
		-999, -999, "ALAMOSA D", null, null, null, true);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, -999,
		-999, -999, "ALAMOSA D", null, null, null, true);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, -999,
		-999, -999, null, "GRASS_PASTURE", null, null, true);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, -999,
		-999, -999, null, "GRASS_PASTURE", null, null, true);
	assertEquals(v1, v2);

	sv1 = __dmi1.readStructureViewForStructure_num(37578);
	sv2 = __dmi2.readStructureViewForStructure_num(37578);
	v1 = new Vector();
	v1.add(sv1);
	v2 = new Vector();
	v2.add(sv2);
	assertEquals(v1, v2);

	sv1 = __dmi1.readStructureViewForWDID(20, 505);
	sv2 = __dmi2.readStructureViewForWDID(20, 505);
	v1 = new Vector();
	v1.add(sv1);
	v2 = new Vector();
	v2.add(sv2);
	assertEquals(v1, v2);

	sv1 = __dmi1.readStructureViewForStructure_num(37578);
	sv2 = __dmi2.readStructureViewForStructure_num(37578);
	v1 = new Vector();
	v1.add(sv1);
	v2 = new Vector();
	v2.add(sv2);
	assertEquals(v1, v2);	

	where[0] = "geoloc.wd = 24";
	where[1] = "wd 24";

	v1 = __dmi1.readGroundWaterWellsMeasTypeList(null, where);
	v2 = __dmi2.readGroundWaterWellsMeasTypeList(null, where);
	assertEquals(v1, v2);

/*
	v1 = __dmi1.readStructureGeolocStructMeasTypeList(null, "IRelClass",
		null);
	v2 = __dmi2.readStructureGeolocStructMeasTypeList(null, "IRelClass",
		null);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructureGeolocStructMeasTypeList(null, null,
		"Monthly");
	v2 = __dmi2.readStructureGeolocStructMeasTypeList(null, null,
		"Monthly");
	assertEquals(v1, v2);
*/

	v1 = __dmi1.readStructureViewListForWDStream_numStr_type(1, 6351, "R");
	v2 = __dmi2.readStructureViewListForWDStream_numStr_type(1, 6351, "R");
	assertEquals(v1, v2);

	sv1 = __dmi1.readStructureViewForWDID(1, 5163);
	sv2 = __dmi2.readStructureViewForWDID(1, 5163);
	v1 = new Vector();
	v1.add(sv1);
	v2 = new Vector();
	v2.add(sv2);
	assertEquals(v1, v2);
}

public void testReadTransact() 
throws Exception {
	header("Transact");

	String[] where = new String[2];
	where[0] = "wd = 13";
	where[1] = "wd 13";
	
	Vector v1 = __dmi1.readTransactList(null, where, null,
		HydroBase_Report_Transaction.STANDARD_1);
	Vector v2 = __dmi2.readTransactList(null, where, null,
		HydroBase_Report_Transaction.STANDARD_1);
	assertEquals(v1, v2);

	v1 = __dmi1.readTransactList(null, where, null,
		HydroBase_Report_Transaction.STANDARD_2);
	v2 = __dmi2.readTransactList(null, where, null,
		HydroBase_Report_Transaction.STANDARD_2);
	assertEquals(v1, v2);	

	v1 = __dmi1.readTransactList(null, where, null,
		HydroBase_Report_Transaction.STANDARD_3);
	v2 = __dmi2.readTransactList(null, where, null,
		HydroBase_Report_Transaction.STANDARD_3);
	assertEquals(v1, v2);	

	v1 = __dmi1.readTransactList(null, where, null,
		HydroBase_Report_Transaction.STANDARD_4);
	v2 = __dmi2.readTransactList(null, where, null,
		HydroBase_Report_Transaction.STANDARD_4);
	assertEquals(v1, v2);	

	v1 = __dmi1.readTransactList(null, where, null,
		HydroBase_Report_Transaction.STANDARD_5);
	v2 = __dmi2.readTransactList(null, where, null,
		HydroBase_Report_Transaction.STANDARD_5);
	assertEquals(v1, v2);	

	v1 = __dmi1.readTransactList(null, where, null,
		HydroBase_Report_Transaction.STANDARD_6);
	v2 = __dmi2.readTransactList(null, where, null,
		HydroBase_Report_Transaction.STANDARD_6);
	assertEquals(v1, v2);	

	v1 = __dmi1.readTransactListForStructure_num(17807);
	v2 = __dmi2.readTransactListForStructure_num(17807);
	assertEquals(v1, v2);		
}

public void testReadTSProduct() 
throws Exception {
	header("TSProduct");

	// no data

	HydroBase_TSProduct tsp1 = __dmi1.readTSProductForIdentifier("", -999);
	HydroBase_TSProduct tsp2 = __dmi2.readTSProductForIdentifier("", -999);
	Vector v1 = new Vector();
	v1.add(tsp1);
	Vector v2 = new Vector();
	v2.add(tsp2);
	assertEquals(v1, v2);

	v1 = __dmi1.readTSProductListForTSProduct_numProductGroup_numUser_num(
		0, -999, 0);
	v2 = __dmi2.readTSProductListForTSProduct_numProductGroup_numUser_num(
		0, -999, 0);
	assertEquals(v1, v2);
}

public void testReadTSProductProps() 
throws Exception {
	header("TSProduct Props");

	Vector v1 = __dmi1.readTSProductPropsListForTSProduct_num(0);
	Vector v2 = __dmi2.readTSProductPropsListForTSProduct_num(0);
	assertEquals(v1, v2);
}

public void testReadUnpermittedWells() 
throws Exception {
	header("Unpermitted Wells");

	HydroBase_GroundWaterWellsView w1 
		= __dmi1.readUnpermittedWells(98485, null, null);
	HydroBase_GroundWaterWellsView w2
		= __dmi2.readUnpermittedWells(98485, null, null);
	Vector v1 = new Vector();
	v1.add(w1);
	Vector v2 = new Vector();
	v2.add(w2);
	assertEquals(v1, v2);

	w1 = __dmi1.readUnpermittedWells(-999, "373125105450001", null);
	w2 = __dmi2.readUnpermittedWells(-999, "373125105450001", null);
	v1 = new Vector();
	v1.add(w1);
	v2 = new Vector();
	v2.add(w2);	
	assertEquals(v1, v2);

	w1 = __dmi1.readUnpermittedWells(-999, null, "EW28CD");
	w2 = __dmi2.readUnpermittedWells(-999, null, "EW28CD");
	v1 = new Vector();
	v1.add(w1);
	v2 = new Vector();
	v2.add(w2);	
	assertEquals(v1, v2);
}

public void testReadUse() 
throws Exception {
	header("Use");

	Vector v1 = __dmi1.readUseList();
	Vector v2 = __dmi2.readUseList();
	assertEquals(v1, v2);
}

public void testReadUserPreferences() 
throws Exception {
	header("User Preferences");

	Vector v1 = __dmi1.readUserPreferencesListForUser_num(97);
	Vector v2 = __dmi2.readUserPreferencesListForUser_num(97);
	assertEquals(v1, v2);
}

public void testReadUserSecurity() 
throws Exception {
	header("User Security");

	HydroBase_UserSecurity s1 
		= __dmi1.readUserSecurityForLoginPasswordApplication(
		"guest", "guest", null);
	HydroBase_UserSecurity s2 
		= __dmi2.readUserSecurityForLoginPasswordApplication(
		"guest", "guest", null);
	Vector v1 = new Vector();
	v1.add(s1);
	Vector v2 = new Vector();
	v2.add(s2);
	assertEquals(v1, v2);

	s1 = __dmi1.readUserSecurityForLoginPasswordApplication(
		"guest", "guest", "CWRAT");
	s2 = __dmi2.readUserSecurityForLoginPasswordApplication(
		"guest", "guest", "CWRAT");
	v1 = new Vector();
	v1.add(s1);
	v2 = new Vector();
	v2.add(s2);
	assertEquals(v1, v2);	

	s1 = __dmi1.readUserSecurityForUser_num(109);
	s2 = __dmi2.readUserSecurityForUser_num(109);
	v1 = new Vector();
	v1.add(s1);
	v2 = new Vector();
	v2.add(s2);
	assertEquals(v1, v2);		
}

public void testReadWaterDistrict() 
throws Exception {
	header("Water District");

	Vector v1 = __dmi1.readWaterDistrictList(true);
	Vector v2 = __dmi2.readWaterDistrictList(true);
	assertEquals(v1, v2);

	v1 = __dmi1.readWaterDistrictList(false);
	v2 = __dmi2.readWaterDistrictList(false);
	assertEquals(v1, v2);
}

public void testReadWaterDivision() 
throws Exception {
	header("Water Division");

	Vector v1 = __dmi1.readWaterDivisionList();
	Vector v2 = __dmi2.readWaterDivisionList();
	assertEquals(v1, v2);
}

public void testReadWDWater() 
throws Exception {
	header("WD Water");

	Vector v1 = __dmi1.readWDWaterListForWDWater_num(4444);
	Vector v2 = __dmi2.readWDWaterListForWDWater_num(4444);

	HydroBase_WDWater w = null;
	int size = v2.size();
	Vector v3 = new Vector();
	for (int i = 0; i < size; i++) {
		w = (HydroBase_WDWater)v2.elementAt(i);
		v3.add(new HydroBase_WDWater(w, true));
	}
	assertEquals(v1, v3);
}

public void testReadWellApplication() 
throws Exception {
	header("Well Application");
	
	// table has no data

	Vector v1 = __dmi1.readWellApplicationList(null, null, null);
	Vector v2 = __dmi2.readWellApplicationList(null, null, null);
	assertEquals(v1, v2);

	String[] where = new String[2];
	where[0] = "geoloc.wd = 1";
	where[1] = "wd 1";

	v1 = __dmi1.readWellApplicationGeolocList(null, where, null);
	v2 = __dmi2.readWellApplicationGeolocList(null, where, null);
	assertEquals(v1, v2);
}

public void testReadWellMeas() 
throws Exception {
	header("Well Meas");

	DateTime start = DateTime.parse("1970-01-01");
	DateTime end = DateTime.parse("1990-07-31");

	Vector v1 = __dmi1.readWellMeasList(169111, start, end);
	Vector v2 = __dmi2.readWellMeasList(169111, start, end);
	assertEquals(v1, v2);

	v1 = __dmi1.readWellMeasList(169111, start, null);
	v2 = __dmi2.readWellMeasList(169111, start, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readWellMeasList(169111, null, end);
	v2 = __dmi2.readWellMeasList(169111, null, end);
	assertEquals(v1, v2);

}

public void testReadWells() 
throws Exception {
	header("Wells");

	Vector v1 = __dmi1.readWellsList();
	Vector v2 = __dmi2.readWellsList();
	assertEquals(v1, v2);
}

public void testReadWellToLayer() 
throws Exception {
	header("Well To Layer");

	Vector v1 = __dmi1.readWellsWellToLayerList();
	Vector v2 = __dmi2.readWellsWellToLayerList();
	assertEquals(v1, v2);
}

public void testReadWellToParcel() 
throws Exception {
	header("Well To Parcel");

	Vector v1 = __dmi1.readWellsWellToParcelList(16646, -1, -1);
	Vector v2 = __dmi2.readWellsWellToParcelList(16646, -1, -1);
	assertEquals(v1, v2);

	v1 = __dmi1.readWellsWellToParcelList(-1, 1998, 3);
	v2 = __dmi2.readWellsWellToParcelList(-1, 1998, 3);
	assertEquals(v1, v2);
}

public void testReadWellToParcelWellToStructure() 
throws Exception {
	header("Well To Parcel Well To Structure");

	Vector v1 = __dmi1.readWellsWellToParcelWellToStructureList();
	Vector v2 = __dmi2.readWellsWellToParcelWellToStructureList();
	assertEquals(v1, v2);
}

public void testReadWellToStructure() 
throws Exception {
	header("Well To Structure");

	Vector v1 = __dmi1.readWellsWellToStructureList();
	Vector v2 = __dmi2.readWellsWellToStructureList();
	assertEquals(v1, v2);
}

public void testReadWISComments() 
throws Exception {
	header("WIS Comments");

	Vector wisNums = new Vector();
	wisNums.add("1");
	wisNums.add("169");
	wisNums.add("2021");

	Vector v1 = __dmi1.readWISCommentsList(wisNums, null);
	Vector v2 = __dmi2.readWISCommentsList(wisNums, null);
	assertEquals(v1, v2);

	DateTime dt = DateTime.parse("2000-10-02");
	v1 = __dmi1.readWISCommentsList(wisNums, dt);
	v2 = __dmi2.readWISCommentsList(wisNums, dt);
	assertEquals(v1, v2);
}

public void testReadWISDailyWC() 
throws Exception {
	header("WIS Daily WC");
	DateTime start = DateTime.parse("1980-01-01");
	DateTime end = DateTime.parse("1990-01-01");	

	// no data in the table

	Vector v1 = __dmi1.readWISDailyWCList(1, "1", 1, 1, start, "A", 1, 
		"B", "C", 1);
	Vector v2 = __dmi2.readWISDailyWCList(1, "1", 1, 1, start, "A", 1, 
		"B", "C", 1);
 	assertEquals(v1, v2);
}

public void testReadWISData() 
throws Exception {
	header("WIS Comments");

	Vector wisNums = new Vector();
	wisNums.add("1");
	wisNums.add("169");
	wisNums.add("2021");

	Vector wisRows = new Vector();
	wisRows.add("1");
	wisRows.add("2");
	wisRows.add("3");
	wisRows.add("4");
	wisRows.add("5");
	wisRows.add("6");
	wisRows.add("7");
	wisRows.add("8");
	wisRows.add("9");

	Vector v1 = __dmi1.readWISDataListForWis_numWis_rowList(
		wisNums, wisRows, null);
	Vector v2 = __dmi2.readWISDataListForWis_numWis_rowList(
		wisNums, wisRows, null);
	assertEquals(v1, v2);

	DateTime dt = DateTime.parse("2001-10-01");
	v1 = __dmi1.readWISDataList(-999, dt, -999);
	v2 = __dmi2.readWISDataList(-999, dt, -999);
	assertEquals(v1, v2);

	v1 = __dmi1.readWISDataList(-999, null, 14);
	v2 = __dmi2.readWISDataList(-999, null, 14);
	assertEquals(v1, v2);
}

public void testReadWISDiagramData() 
throws Exception {
	header("WIS Diagram Data");

	Vector v1 = __dmi1.readWISDiagramDataListForWis_num(1);
	Vector v2 = __dmi2.readWISDiagramDataListForWis_num(1);
	assertEquals(v1, v2);

	v1 = __dmi1.readWISDiagramDataListForWis_num(2012);
	v2 = __dmi2.readWISDiagramDataListForWis_num(2012);
	assertEquals(v1, v2);	
}

public void testReadWISFormat() 
throws Exception {
	header("WIS Format");

	Vector v1 = __dmi1.readWISFormatList(1, null, null);
	Vector v2 = __dmi2.readWISFormatList(1, null, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readWISFormatList(-999, "Diversion", null);
	v2 = __dmi2.readWISFormatList(-999, "Diversion", null);
	assertEquals(v1, v2);	

	v1 = __dmi1.readWISFormatList(-999, "Diversion", "strm:6351");
	v2 = __dmi2.readWISFormatList(-999, "Diversion", "strm:6351");
	assertEquals(v1, v2);	

	Vector wisNums = new Vector();
	wisNums.add("1");
	wisNums.add("169");
	wisNums.add("2021");
	v1 = __dmi1.readWISFormatListForWis_numList(wisNums);
	v2 = __dmi2.readWISFormatListForWis_numList(wisNums);
	assertEquals(v1, v2);	

	wisNums = new Vector();
	wisNums.add("1");
	wisNums.add("1");
	wisNums.add("1");
	Vector ids = new Vector();
	ids.add("strm:6351");
	ids.add("stat:06754000");
	ids.add("wdid:0100501");
	v1 = __dmi1.readWISFormatListForWis_numIdentifierList(wisNums, ids);
	v2 = __dmi2.readWISFormatListForWis_numIdentifierList(wisNums, ids);
	assertEquals(v1, v2);	
}

public void testReadWISImport() 
throws Exception {
	header("WIS Import");

	Vector v1 = __dmi1.readWISImportListForWis_num(1);
	Vector v2 = __dmi2.readWISImportListForWis_num(1);
	assertEquals(v1, v2);

	v1 = __dmi1.readWISImportListForWis_num(2);
	v2 = __dmi2.readWISImportListForWis_num(2);
	assertEquals(v1, v2);
}

public void testReadWISSheetName() 
throws Exception {
	header("WIS Sheet Name");

	Vector v1 = __dmi1.readWISSheetNameList(1, -999, null, null);
	Vector v2 = __dmi2.readWISSheetNameList(1, -999, null, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readWISSheetNameList(-999, 1, null, null);
	v2 = __dmi2.readWISSheetNameList(-999, 1, null, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readWISSheetNameList(-999, -999, "District 1", null);
	v2 = __dmi2.readWISSheetNameList(-999, -999, "District 1", null);
	assertEquals(v1, v2);

	DateTime dt = DateTime.parse("1998-03-03");
	v1 = __dmi1.readWISSheetNameList(-999, -999, null, dt);
	v2 = __dmi2.readWISSheetNameList(-999, -999, null, dt);
	assertEquals(v1, v2);

	Vector wds = new Vector();
	wds.add("1");
	wds.add("2");
	wds.add("3");
	v1 = __dmi1.readWISSheetNameDistinctList(wds);
	v2 = __dmi2.readWISSheetNameDistinctList(wds);
	assertEquals(v1, v2);	

	v1 = __dmi1.readWISSheetNameList(wds);
	v2 = __dmi2.readWISSheetNameList(wds);
	assertEquals(v1, v2);	
	
	v1 = __dmi1.readWISSheetNameDistinctList(1);
	v2 = __dmi2.readWISSheetNameDistinctList(1);
	assertEquals(v1, v2);			

	v1 = __dmi1.readWISSheetNameDistinctList(5);
	v2 = __dmi2.readWISSheetNameDistinctList(5);
	assertEquals(v1, v2);		

	v1 = __dmi1.readWISSheetNameWISFormatListDistinct(null);
	v2 = __dmi2.readWISSheetNameWISFormatListDistinct(null);
	assertEquals(v1, v2);		
}

public void testWriteTSProduct()
throws Exception {
	header("TS Product");

	HydroBase_TSProduct tsp = new HydroBase_TSProduct();
	tsp.setProductGroup_num(1);
	tsp.setIdentifier("JUnit");
	tsp.setName("JUnit");
	tsp.setComment("JUnit");
	tsp.setUser_num(97);
	__dmi2.writeTSProduct(tsp);

	HydroBase_TSProduct tsp2 = __dmi2.readTSProductForIdentifier(
		"JUnit", 97);
	if (tsp2 == null) {
		fail("The TSProduct was not inserted into the database "
			+ "properly.");
	}
	tsp.setTSProduct_num(tsp2.getTSProduct_num());
	Vector v1 = new Vector();
	v1.add(tsp);
	Vector v2 = new Vector();
	v2.add(tsp2);
	assertEquals(v1, v2);

	tsp.setName("JUnit Test 2");
	__dmi2.writeTSProduct(tsp);
	tsp2 = __dmi2.readTSProductForIdentifier("JUnit", 97);
	if (tsp2 == null) {
		fail("The TSProduct was not updated into the database "
			+ "properly.");
	}
	v1 = new Vector();
	v1.add(tsp);
	v2 = new Vector();
	v2.add(tsp2);
	assertEquals(v1, v2);

	__dmi2.deleteTSProductForIdentifier("JUnit", 97);
	tsp = __dmi2.readTSProductForIdentifier("JUnit", 97);
	if (tsp != null) {
		v1 = new Vector();
		v1.add(tsp);
		assertNoRecords(v1, "TSProduct", "identifier, user_num", 
			"JUnit, 97");
	}
}	

public void testWriteUserPreferences()
throws Exception {
	header("User Preferences");

	__dmi2.writeUserPreference("JUnit Test Preference",
		"JUnit Test Value", "97", "JUnit", true);

	Vector v2 = __dmi2.readUserPreferencesListForUser_num(97);
	HydroBase_UserPreferences up = null;
	int size = v2.size();
	Vector v1 = new Vector();
	for (int i = 0; i < size; i++) {
		up = (HydroBase_UserPreferences)v2.elementAt(i);
		if (up.getPreference().startsWith("JUnit")) {
			v1.add(up);
		}
	}
	
	up = new HydroBase_UserPreferences();
	up.setUser_num(97);
	up.setPreference("JUnit Test Preference");
	up.setPref_value("JUnit Test Value");
	
	v2 = new Vector();
	v2.add(up);
	
	assertEquals(v1, v2);

	__dmi1.dmiDelete("DELETE FROM user_preferences WHERE "
		+ "preference = 'JUnit Test Preference'");
}

public void testWriteWISSheetName() 
throws Exception {
	header("WIS Sheet Name");
	HydroBase_WISSheetName sn = new HydroBase_WISSheetName();
	String sheetName = "JUnit Sheet Name : " 
		+ (new DateTime(DateTime.DATE_CURRENT).toString(
		DateTime.FORMAT_YYYY_MM_DD));
	sn.setSheet_name(sheetName);
	sn.setEffective_date((DateTime.parse("2005-01-01")).getDate());
	sn.setWD(1);
	sn.setGain_method("G");
	sn.setComments("JUnit sample sheet name");

	__dmi2.writeWISSheetName(sn);

	Vector v2 = __dmi2.readWISSheetNameList(
		DMIUtil.MISSING_INT, DMIUtil.MISSING_INT, sheetName, null);

	assertSingleRecord(v2, "WIS Sheet Name", "sheet name", sheetName);

	HydroBase_WISSheetName sn2 = (HydroBase_WISSheetName)v2.elementAt(0);

	int wis_num2 = sn2.getWis_num();
	
	sn.setWis_num(wis_num2);

	Vector v = new Vector();
	v.add(sn);
	assertEquals(v, v2);
	
	DateTime dt = DateTime.parse("2005-02-01");
	
	__dmi2.updateSheet_nameGain_methodEffective_dateForWis_num(
		"H", dt, wis_num2);
	v2 = __dmi2.readWISSheetNameList(
		DMIUtil.MISSING_INT, DMIUtil.MISSING_INT, sheetName, null);
	v = new Vector();
	sn.setGain_method("H");
	sn.setEffective_date((DateTime.parse("2005-02-01")).getDate());
	v.add(sn);
	assertEquals(v, v2);
	
	__dmi2.deleteWISForWis_num(wis_num2);
	v2 = __dmi2.readWISSheetNameList(
		DMIUtil.MISSING_INT, DMIUtil.MISSING_INT, sheetName, null);
	assertNoRecords(v2, "WIS Sheet Name", "wis_num", "" + wis_num2);
}

public void testWriteWISComments()
throws Exception {
	header("WIS Comments");
	HydroBase_WISSheetName sn = new HydroBase_WISSheetName();
	String sheetName = "JUnit Sheet Name : " 
		+ (new DateTime(DateTime.DATE_CURRENT).toString(
		DateTime.FORMAT_YYYY_MM_DD));
	sn.setSheet_name(sheetName);
	sn.setEffective_date((DateTime.parse("2005-01-01")).getDate());
	sn.setWD(1);
	sn.setGain_method("G");
	sn.setComments("JUnit sample sheet name");

	__dmi2.writeWISSheetName(sn);

	Vector v2 = __dmi2.readWISSheetNameList(
		DMIUtil.MISSING_INT, DMIUtil.MISSING_INT, sheetName, null);

	HydroBase_WISSheetName sn2 = (HydroBase_WISSheetName)v2.elementAt(0);
	int wis_num2 = sn2.getWis_num();

	HydroBase_WISComments c = new HydroBase_WISComments();
	c.setWis_num(wis_num2);
	c.setSet_date((DateTime.parse("2005-01-01")).getDate());
	c.setArchive_date((DateTime.parse("2005-01-01")).getDate());
	c.setComment("JUnit Comments");
	
	__dmi2.writeWISComments(c);
	v2 = __dmi2.readWISCommentsList(wis_num2, null);
	Vector v = new Vector();
	v.add(c);
	assertEquals(v, v2);
	
	__dmi2.deleteWISForWis_num(wis_num2);
	v2 = __dmi2.readWISCommentsList(wis_num2, null);
	assertNoRecords(v2, "WIS Comments", "wis_num", "" + wis_num2);
}

public void testWriteWISDailyWC()
throws Exception {
	header("WIS DailyWC");
	HydroBase_WISSheetName sn = new HydroBase_WISSheetName();
	String sheetName = "JUnit Sheet Name : " 
		+ (new DateTime(DateTime.DATE_CURRENT).toString(
		DateTime.FORMAT_YYYY_MM_DD));
	sn.setSheet_name(sheetName);
	sn.setEffective_date((DateTime.parse("2005-01-01")).getDate());
	sn.setWD(1);
	sn.setGain_method("G");
	sn.setComments("JUnit sample sheet name");

	__dmi2.writeWISSheetName(sn);

	Vector v2 = __dmi2.readWISSheetNameList(
		DMIUtil.MISSING_INT, DMIUtil.MISSING_INT, sheetName, null);

	HydroBase_WISSheetName sn2 = (HydroBase_WISSheetName)v2.elementAt(0);
	int wis_num2 = sn2.getWis_num();

	HydroBase_WISDailyWC c = new HydroBase_WISDailyWC();
	c.setWis_dailywc_num(wis_num2);
	c.setMeas_num(0);
	c.setStructure_num(0);
	c.setQuality("0");
	c.setIrr_year(2005);
	c.setIrr_mon(1);
	c.setCal_year(2005);
	c.setCal_mon(2);
	c.setWis_dailywc_num(wis_num2);
	for (int i = 1; i <= 31; i++) {
		c.setAmountForDay(i, i);
		c.setObservationForDay(i, "" + i);
	}
	c.setUnit("JUnit");
	c.setFunc("JUnit");
	c.setWD(1);
	c.setID(1);
	c.setS("JUnit");
	c.setF(11);
	c.setU("JUnit");
	c.setT("JUnit");
	c.setModified((new DateTime(DateTime.DATE_CURRENT).getDate()));
	c.setUser_num(0);
	c.setWis_num(wis_num2);
	c.setWis_column("JU");
	
	__dmi2.writeWISDailyWC(c);
	v2 = __dmi2.readWISDailyWCList(wis_num2, "JU", 1, 1, 
		new DateTime(DateTime.DATE_CURRENT), null, 
		DMIUtil.MISSING_INT, null, null, 0);
	Vector v = new Vector();
	v.add(c);
	assertEquals(v, v2);
	
	__dmi2.deleteWISDailyWC(wis_num2, "JU", 1, 1, 2005, 2);
	v2 = __dmi2.readWISDailyWCList(wis_num2, "JU", 1, 1, null, null, 
		DMIUtil.MISSING_INT, null, null, 0);
	assertNoRecords(v2, "WIS DailyWC", "wis_num", "" + wis_num2);

	__dmi2.deleteWISForWis_num(wis_num2);	
}

public void testWriteWISData()
throws Exception {
	header("WIS Data");
	HydroBase_WISSheetName sn = new HydroBase_WISSheetName();
	String sheetName = "JUnit Sheet Name : " 
		+ (new DateTime(DateTime.DATE_CURRENT).toString(
		DateTime.FORMAT_YYYY_MM_DD));
	sn.setSheet_name(sheetName);
	sn.setEffective_date((DateTime.parse("2005-01-01")).getDate());
	sn.setWD(1);
	sn.setGain_method("G");
	sn.setComments("JUnit sample sheet name");

	__dmi2.writeWISSheetName(sn);

	Vector v2 = __dmi2.readWISSheetNameList(
		DMIUtil.MISSING_INT, DMIUtil.MISSING_INT, sheetName, null);

	HydroBase_WISSheetName sn2 = (HydroBase_WISSheetName)v2.elementAt(0);
	int wis_num2 = sn2.getWis_num();

	HydroBase_WISData d = new HydroBase_WISData();
	d.setWis_num(wis_num2);
	d.setWis_row(0);
	d.setSet_date((DateTime.parse("2005-01-01")).getDate());
	d.setPoint_flow(123);
	d.setNat_flow(234);
	d.setDelivery_flow(345);
	d.setGain(456);
	d.setTrib_natural(567);
	d.setTrib_delivery(678);
	d.setPriority_divr(789);
	d.setDelivery_divr(890);
	d.setRelease(901);
	d.setComment("JUnit");
	d.setDry_river(1);
	
	__dmi2.writeWISData(d);
	v2 = __dmi2.readWISDataList(wis_num2, null, DMIUtil.MISSING_INT);
	Vector v = new Vector();
	v.add(d);
	assertEquals(v, v2);
	
	__dmi2.deleteWISForWis_num(wis_num2);
	v2 = __dmi2.readWISDataList(wis_num2, null, DMIUtil.MISSING_INT);
	assertNoRecords(v2, "WIS Data", "wis_num", "" + wis_num2);
}

public void testWriteWISDiagramData()
throws Exception {
	header("WIS Diagram Data");
	HydroBase_WISSheetName sn = new HydroBase_WISSheetName();
	String sheetName = "JUnit Sheet Name : " 
		+ (new DateTime(DateTime.DATE_CURRENT).toString(
		DateTime.FORMAT_YYYY_MM_DD));
	sn.setSheet_name(sheetName);
	sn.setEffective_date((DateTime.parse("2005-01-01")).getDate());
	sn.setWD(1);
	sn.setGain_method("G");
	sn.setComments("JUnit sample sheet name");

	__dmi2.writeWISSheetName(sn);

	Vector v2 = __dmi2.readWISSheetNameList(
		DMIUtil.MISSING_INT, DMIUtil.MISSING_INT, sheetName, null);

	HydroBase_WISSheetName sn2 = (HydroBase_WISSheetName)v2.elementAt(0);
	int wis_num2 = sn2.getWis_num();

	HydroBase_WISDiagramData d = new HydroBase_WISDiagramData();
	d.setWis_num(wis_num2);
	d.setID("JUnit");
	d.setType("JUnit");
	d.setProps("JUnit");
	
	__dmi2.writeWISDiagramData(d);
	v2 = __dmi2.readWISDiagramDataListForWis_num(wis_num2);
	Vector v = new Vector();
	v.add(d);
	assertEquals(v, v2);
	
	__dmi2.deleteWISDiagramDataForWis_num(wis_num2);
	v2 = __dmi2.readWISDiagramDataListForWis_num(wis_num2);
	assertNoRecords(v2, "WIS DiagramData", "wis_num", "" + wis_num2);

	__dmi2.writeWISDiagramData(d);
	__dmi2.deleteWISDiagramDataForWis_numID(wis_num2, "JUnit");
	v2 = __dmi2.readWISDiagramDataListForWis_num(wis_num2);
	assertNoRecords(v2, "WIS DiagramData", "wis_num", "" + wis_num2);

	__dmi2.writeWISDiagramData(d);
	__dmi2.deleteWISForWis_num(wis_num2);
	v2 = __dmi2.readWISDiagramDataListForWis_num(wis_num2);
	assertNoRecords(v2, "WIS DiagramData", "wis_num", "" + wis_num2);
}

public void testWriteWISFormat()
throws Exception {
	header("WIS Format");
	HydroBase_WISSheetName sn = new HydroBase_WISSheetName();
	String sheetName = "JUnit Sheet Name : " 
		+ (new DateTime(DateTime.DATE_CURRENT).toString(
		DateTime.FORMAT_YYYY_MM_DD));
	sn.setSheet_name(sheetName);
	sn.setEffective_date((DateTime.parse("2005-01-01")).getDate());
	sn.setWD(1);
	sn.setGain_method("G");
	sn.setComments("JUnit sample sheet name");

	__dmi2.writeWISSheetName(sn);

	Vector v2 = __dmi2.readWISSheetNameList(
		DMIUtil.MISSING_INT, DMIUtil.MISSING_INT, sheetName, null);

	HydroBase_WISSheetName sn2 = (HydroBase_WISSheetName)v2.elementAt(0);
	int wis_num2 = sn2.getWis_num();

	HydroBase_WISFormat f = new HydroBase_WISFormat();
	f.setWis_num(wis_num2);
	f.setWis_row(0);
	f.setRow_label("JUnit label");
	f.setRow_type("JUnit");
	f.setKnown_point("J");
	f.setWdwater_num(4045);
	f.setStr_mile(13);
	f.setStructure_num(137175);
	f.setStation_num(1199);
	f.setIdentifier("JUnit");
	f.setGain_factor(123);
	
	__dmi2.writeWISFormat(f);
	v2 = __dmi2.readWISFormatList(wis_num2, null, null);
	Vector v = new Vector();
	v.add(f);
	assertEquals(v, v2);
	
	__dmi2.deleteWISForWis_num(wis_num2);
	v2 = __dmi2.readWISFormatList(wis_num2, null, null);
	assertNoRecords(v2, "WIS Format", "wis_num", "" + wis_num2);
}

public void testWriteWISFormula()
throws Exception {
	header("WIS Formula");
	HydroBase_WISSheetName sn = new HydroBase_WISSheetName();
	String sheetName = "JUnit Sheet Name : " 
		+ (new DateTime(DateTime.DATE_CURRENT).toString(
		DateTime.FORMAT_YYYY_MM_DD));
	sn.setSheet_name(sheetName);
	sn.setEffective_date((DateTime.parse("2005-01-01")).getDate());
	sn.setWD(1);
	sn.setGain_method("G");
	sn.setComments("JUnit sample sheet name");

	__dmi2.writeWISSheetName(sn);

	Vector v2 = __dmi2.readWISSheetNameList(
		DMIUtil.MISSING_INT, DMIUtil.MISSING_INT, sheetName, null);

	HydroBase_WISSheetName sn2 = (HydroBase_WISSheetName)v2.elementAt(0);
	int wis_num2 = sn2.getWis_num();

	HydroBase_WISFormula f = new HydroBase_WISFormula();
	f.setWis_num(wis_num2);
	f.setWis_row(0);
	f.setColumn("JUnit");
	f.setFormula("JUnit");
	f.setFormulastring("JUnit");
	
	__dmi2.writeWISFormula(f);
	v2 = __dmi2.readWISFormulaListForWis_num(wis_num2);
	Vector v = new Vector();
	v.add(f);
	assertEquals(v, v2);
	
	__dmi2.deleteWISForWis_num(wis_num2);
	v2 = __dmi2.readWISFormulaListForWis_num(wis_num2);
	assertNoRecords(v2, "WIS Formula", "wis_num", "" + wis_num2);
}

public void testWriteWISImport()
throws Exception {
	header("WIS Import");
	HydroBase_WISSheetName sn = new HydroBase_WISSheetName();
	String sheetName = "JUnit Sheet Name : " 
		+ (new DateTime(DateTime.DATE_CURRENT).toString(
		DateTime.FORMAT_YYYY_MM_DD));
	sn.setSheet_name(sheetName);
	sn.setEffective_date((DateTime.parse("2005-01-01")).getDate());
	sn.setWD(1);
	sn.setGain_method("G");
	sn.setComments("JUnit sample sheet name");

	__dmi2.writeWISSheetName(sn);

	Vector v2 = __dmi2.readWISSheetNameList(
		DMIUtil.MISSING_INT, DMIUtil.MISSING_INT, sheetName, null);

	HydroBase_WISSheetName sn2 = (HydroBase_WISSheetName)v2.elementAt(0);
	int wis_num2 = sn2.getWis_num();

	HydroBase_WISImport i = new HydroBase_WISImport();
	i.setWis_num(wis_num2);
	i.setWis_row(0);
	i.setColumn("JUnit");
	i.setEnd_time(13);
	i.setTime_offset(24);
	i.setMeas_num(7365);
	i.setImport_method("JUn");
	i.setImport_wis_num(wis_num2);
	i.setImport_identifier("JUnit");
	i.setImport_column("JUnit");
	
	__dmi2.writeWISImport(i);
	v2 = __dmi2.readWISImportListForWis_num(wis_num2);
	Vector v = new Vector();
	v.add(i);
	assertEquals(v, v2);
	
	__dmi2.deleteWISForWis_num(wis_num2);
	v2 = __dmi2.readWISImportListForWis_num(wis_num2);
	assertNoRecords(v2, "WIS Import", "wis_num", "" + wis_num2);
}

private void assertSingleRecord(Vector v, String desc, String field, 
String value) 
throws Exception {
	if (v == null || v.size() == 0) {
		fail("The " + desc + " record that was "
			+ "inserted into the SP database cannot be found by "
			+ "querying for its unique " + field + " value ("
			+ value + ").  The insert must not have been "
			+ "successful.");
	}
	if (v.size() > 1) {
		fail("The " + desc + " record that was "
			+ "inserted will not be deleted -- two records "
			+ "exist with the same " + field + " ("
			+ value + ") in the SP "
			+ "database and so the newly-inserted one cannot "
			+ "be determined.");
	}
}

private void assertNoRecords(Vector v, String desc, String field, 
String value2) 
throws Exception {
	if (v != null && v.size() > 0) {
		fail("The " + desc + " record that was "
			+ "inserted was not deleted from the SP database."
			+ "  Deletion was attempted on field " + field 
			+ " with value: " + value2);
	}
}

/**
Closes the DMI connection once all the tests have run.
*/
public void testCloseDMI() 
throws Exception {
	__dmi1.close();
	__dmi2.close();
}

/**
Opens the DMI connection and reads in the SQL information from the data file.
*/
public void testOpenDMI() 
throws Exception {
	try {
		Message.setStatusLevel(Message.TERM_OUTPUT, 10);
		Message.setStatusLevel(Message.LOG_OUTPUT, 10);
		Message.setWarningLevel(Message.TERM_OUTPUT, 0);
		Message.setWarningLevel(Message.LOG_OUTPUT, 10);
		Message.openLogFile("log.log");
	}
	catch (Exception e) {
		e.printStackTrace();
	}

	PropList p = new PropList("DMI Settings");
	p.setPersistentName("HydroBase_JUnitTestDMI.cfg");
	boolean read = false;
	try {
		p.readPersistent();
		read = true;
	}
	catch (Exception e) {}

	if (!read) {
		IOUtil.testing(false);
		Message.printStatus(1, "", "Could not read from dmi config "
			+ "file. Using default DMIs.");
		// use default values
		__dmi1 = new HydroBaseDMI("SQLServer2000", "hbserver", 
			"hydrobase", 1433, "crdss", "crdss3nt");
		__dmi1.open();
	
		__dmi2 = new HydroBaseDMI("SQLServer2000", "hbserver", 
			"hydrobase", 1433, "cdss", "cdss%tools", true);
		__dmi2.open();
	}
	else {	
		String s = p.getValue("JUnit.Testing");
		if (s != null && s.equalsIgnoreCase("true")) {
			IOUtil.testing(true);
		}
				
		setupDMIs(p);
		__dmi1.open();
		__dmi2.open();
	}
	
	Message.printStatus(1, "", "DMI1: " + __dmi1);
	Message.printStatus(1, "", "Version: "
		+ __dmi1.getLatestVersionString() + "\n" 
		+ __dmi1.getDatabaseVersion());
	Message.printStatus(1, "", "\n\n");
	Message.printStatus(1, "", "DMI2: " + __dmi2);
	Message.printStatus(1, "", "Version: " 
		+ __dmi2.getLatestVersionString() + "\n" 
		+ __dmi2.getDatabaseVersion());
	Message.printStatus(1, "", "\n\n");

/*
	__dmi1 = new HydroBaseDMI("Access", "AccessHydroBase", 
		"crdss", "crdss3nt");
	__dmi1.open();

	__dmi2 = new HydroBaseDMI("Access", "AccessHydroBase", 
		"crdss", "crdss3nt");
	__dmi2.open();
*/
}

public void testReadPumpTestView()
throws Exception {
	// table has no data
	header("Pump Test");
	String[] where = new String[2];
	where[0] = "structure.div = 3";
	where[1] = "div 3";	
	Vector v1 = __dmi1.readGroundWaterWellsPumpingTestList(null, where);
	Vector v2 = __dmi2.readGroundWaterWellsPumpingTestList(null, where);
	assertEquals(v1, v2);
}

public void testReadStationView()
throws Exception {
	header("Station");
	
	HydroBase_StationView vw1 = __dmi1.readStationViewForStation_id("0092");
	HydroBase_StationView vw2 = __dmi2.readStationViewForStation_id("0092");
	Vector v1 = new Vector();
	v1.add(vw1);
	Vector v2 = new Vector();
	v2.add(vw2);
	assertEquals(v1, v2);	

	v1 = __dmi1.readMeasTypeList(669, null, null, null, null);
	v2 = __dmi2.readMeasTypeList(669, null, null, null, null);
	assertEquals(v1, v2);	

	String[] where = new String[2];
	where[0] = "wd = 1";
	where[1] = "wd 1";

	v1 = __dmi1.readStationGeolocMeasTypeList(null, where, null, 
		"Precip", "Daily", null, null, null, false);
	v2 = __dmi2.readStationGeolocMeasTypeList(null, where, null, 
		"Precip", "Daily", null, null, null, false);
	assertEquals(v1, v2);

	v1 = __dmi1.readStationGeolocMeasTypeListForWD(1, "Snow", null, null,
		"NOAA", false);
	v2 = __dmi2.readStationGeolocMeasTypeListForWD(1, "Snow", null,null, 
		"NOAA", false);
	assertEquals(v1, v2);
	
	v1 = __dmi1.readStationGeolocMeasTypeListForWD(1, "Snow", null,null, 
		"NOAA", true);
	v2 = __dmi2.readStationGeolocMeasTypeListForWD(1, "Snow", null,null, 
		"NOAA", true);
	assertEquals(v1, v2);

	v1 = __dmi1.readStationGeolocMeasTypeListForWD(1, null, 
		"REAL-TIME", "DISCHRG", null, false);
	v2 = __dmi2.readStationGeolocMeasTypeListForWD(1, null,  
		"REAL-TIME", "DISCHRG", null, false);	
	assertEquals(v1, v2);
}

public void testReadWellApplicationView() 
throws Exception {
	Vector v1 = __dmi1.readWellApplicationList(null, null, null);
	Vector v2 = __dmi2.readWellApplicationList(null, null, null);
	assertEquals(v1, v2);
	
	String[] where = new String[2];
	where[0] = "geoloc.wd = 1";
	where[1] = "wd 1";

	v1 = __dmi1.readWellApplicationGeolocList(null, where, null);
	v2 = __dmi2.readWellApplicationGeolocList(null, where, null);
	assertEquals(v1, v2);
}

public void testReadStructureView() 
throws Exception {
	String[] where = new String[2];
	where[0] = "geoloc.wd = 37";
	where[1] = "wd 37";

	Vector v1 = __dmi1.readStructureGeolocList(null, where, null);
	Vector v2 = __dmi2.readStructureGeolocList(null, where, null);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructureIrrigSummaryListForStructure_num(267903);
	v2 = __dmi2.readStructureIrrigSummaryListForStructure_num(267903);
	assertEquals(v1, v2);	

	HydroBase_StructureView vw1 = __dmi1.readStructureViewForStructure_num(
		37578);	
	v1 = new Vector();
	v1.add(vw1);
	HydroBase_StructureView vw2 = __dmi2.readStructureViewForStructure_num(
		37578);	
	v2 = new Vector();
	v2.add(vw2);
	assertEquals(v1, v2);	

	vw1 = __dmi1.readStructureViewForWDID(4, 542);	
	v1 = new Vector();
	v1.add(vw1);
	vw2 = __dmi2.readStructureViewForWDID(4, 542);	
	v2 = new Vector();
	v2.add(vw2);
	assertEquals(v1, v2);	

	v1 = __dmi1.readStructureViewListForWDStream_numStr_type(1, 6351,
		"R");
	v2 = __dmi2.readStructureViewListForWDStream_numStr_type(1, 6351,
		"R");	
	assertEquals(v1, v2);	
}

public void testReadStructMeasTypeView() 
throws Exception {
	Vector v1 = __dmi1.readStructMeasTypeListForStructure_num(30975, 
		"IDivTotal", null, "Annual", null);
	Vector v2 = __dmi2.readStructMeasTypeListForStructure_num(30975, 
		"IDivTotal", null, "Annual", null);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructMeasTypeListForStructure_num(30975, false);
	v2 = __dmi2.readStructMeasTypeListForStructure_num(30975, false);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructMeasTypeListForStructure_num(30975, true);
	v2 = __dmi2.readStructMeasTypeListForStructure_num(30975, true);
	assertEquals(v1, v2);

	v1 = __dmi1.readStructMeasTypeListForStructure_numMeas_type(30975,
		"DivComment");
	v2 = __dmi2.readStructMeasTypeListForStructure_numMeas_type(30975, 
		"DivComment");
	assertEquals(v1, v2);

	String[] where = new String[2];
	where[0] = "geoloc.wd = 24";
	where[1] = "wd 24";
	
/*	
	v1 = __dmi1.readStructureGeolocStructMeasTypeList(null, "IRelClass",
		null);
	v2 = __dmi2.readStructureGeolocStructMeasTypeList(null, "IRelClass",
		null);
	assertEquals(v1, v2);
*/
/*
	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, 37578,
		-999, -999, null, null, null, null, false);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, 37578,
		-999, -999, null, null, null, null, false);
	assertEquals(v1, v2);	

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, -999,
		20, 505, null, null, null, null, false);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, -999,
		20, 505, null, null, null, null, false);
	assertEquals(v1, v2);	

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, -999,
		-999, -999, "ALAMOSA D", null, null, null, false);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, -999,
		-999, -999, "ALAMOSA D", null, null, null, false);
	assertEquals(v1, v2);	

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, -999,
		20, -999, null, "GRASS_PASTURE", null, null, false);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, -999,
		20, -999, null, "GRASS_PASTURE", null, null, false);
	assertEquals(v1, v2);	

	DateTime start = DateTime.parse("1990-01-01");
	DateTime end = DateTime.parse("2000-01-01");

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, -999,
		20, -999, null, null, start, end, false);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, -999,
		20, -999, null, null, start, end, false);
	assertEquals(v1, v2);	

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, 37578,
		-999, -999, null, null, null, null, true);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, 37578,
		-999, -999, null, null, null, null, true);
	assertEquals(v1, v2);	

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, -999,
		20, 505, null, null, null, null, true);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, -999,
		20, 505, null, null, null, null, true);
	assertEquals(v1, v2);	

	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, -999,
		-999, -999, "ALAMOSA D", null, null, null, true);
	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, -999,
		-999, -999, "ALAMOSA D", null, null, null, true);
	assertEquals(v1, v2);	
	*/
//	v1 = __dmi1.readStructureIrrigSummaryTSList(null, null, -999,
//		-999, -999, null, "GRASS_PASTURE", null, null, true);
//	v2 = __dmi2.readStructureIrrigSummaryTSList(null, null, -999,
//		-999, -999, null, "GRASS_PASTURE", null, null, true);
//	assertEquals(v1, v2);	

	where[0] = "geoloc.wd = 24";
	where[1] = "wd 24";

	v1 = __dmi1.readGroundWaterWellsMeasTypeList(null, where);
	v2 = __dmi2.readGroundWaterWellsMeasTypeList(null, where);
	assertEquals(v1, v2);
}

public void testReadWISStructures()
throws Exception {
//	__dmi1.newWIS = true;
//	__dmi2.newWIS = false;
//	Vector v1 = __dmi1.readWISStructuresList();
//	Vector v2 = __dmi2.readWISStructuresList();
//	assertEquals(v1, v2);

	IOUtil.setProgramName("CWRAT");
	__dmi1.readUserPreferencesPropList("jth", "jth");
	__dmi2.readUserPreferencesPropList("jth", "jth");
	Vector v1 = __dmi1.readWISStructuresList();
	Vector v2 = __dmi2.readWISStructuresList();
	assertEquals(v1, v2);	
}

public void testTest() 
throws Exception { 
	StopWatch sw1 = new StopWatch();
	int MAX = 250;
	int MOD = 100;

	int pct10 = MAX / 10;
	int pct50 = MAX / 2;

	int count = 0;
	
	StopWatch sw1_10 = new StopWatch();
	StopWatch sw1_50 = new StopWatch();
	sw1.start();
	sw1_10.start();
	sw1_50.start();

	for (int i = 0; i < MAX; i++) {
		if (++count % MOD == 0) {
			Message.printStatus(1, "", "1: " + (i + 1));
			count = 0;
		}
		if (i >= pct10) {
			sw1_10.stop();
		}
		if (i >= pct50) {
			sw1_50.stop();
		}
		__dmi1.readCountyRefList();
		__dmi1.readStreamForStream_num(1);
		__dmi1.readStructureViewForWDID(4, 542);
	}
	sw1.stop();

	StopWatch sw2 = new StopWatch();
	StopWatch sw2_10 = new StopWatch();
	StopWatch sw2_50 = new StopWatch();
	sw2.start();
	sw2_10.start();
	sw2_50.start();	
	for (int i = 0; i < MAX; i++) {
		if (++count % MOD == 0) {
			Message.printStatus(1, "", "2: " + (i + 1));
			count = 0;
		}
		if (i >= pct10) {
			sw2_10.stop();
		}
		if (i >= pct50) {
			sw2_50.stop();
		}		
		__dmi2.readCountyRefList();
		__dmi2.readStreamForStream_num(1);
		__dmi2.readStructureViewForWDID(4, 542);
	}
	sw2.stop();

	Message.printStatus(1, "", "StopWatch 1: " + sw1.getSeconds());
	Message.printStatus(1, "", "        10%: " + sw1_10.getSeconds());
	Message.printStatus(1, "", "        50%: " + sw1_50.getSeconds());
	Message.printStatus(1, "", "StopWatch 2: " + sw2.getSeconds());
	Message.printStatus(1, "", "        10%: " + sw2_10.getSeconds());
	Message.printStatus(1, "", "        50%: " + sw2_50.getSeconds());
}

private void setupDMIs(PropList p) 
throws Exception {
	// set up the first DMI

	__dmi1 = setupDMI(1, p);
	__dmi2 = setupDMI(2, p);
}

private HydroBaseDMI setupDMI(int num, PropList p) 
throws Exception {
	HydroBaseDMI dmi = null;

	String DMI = "DMI" + num + ".";
	
	String s = p.getValue(DMI + "ODBC");
	
	String user = p.getValue(DMI + "User");
	if (user == null) {
		throw new Exception(
			"'" + DMI + "User' property must be defined.");
	}
	
	String password = p.getValue(DMI + "Password");
	if (password == null) {
		throw new Exception(
			"'" + DMI + "Password' property must be defined.");
	}

	user = user.trim();
	password = password.trim();
	
	if (s == null || s.trim().equalsIgnoreCase("false")) {
		// server-based DMI
		String serverType = p.getValue(DMI + "ServerType");
		if (serverType == null) {
			throw new Exception("'" 
				+ DMI + "ServerType' property must be "
				+ "defined.");
		}

		String serverName = p.getValue(DMI + "ServerName");
		if (serverName == null) {
			throw new Exception("'"
				+ DMI + "ServerName' property must be "
				+ "defined.");
		}

		String databaseName = p.getValue(DMI + "DatabaseName");
		if (databaseName == null) {
			throw new Exception("'"
				+ DMI + "DatabaseName' property must be "
				+ "defined.");
		}

		String port = p.getValue(DMI + "Port");
		if (port == null) {
			throw new Exception("'"
				+ DMI + "Port' property must be defined.");
		}

		serverType = serverType.trim();
		serverName = serverName.trim();
		databaseName = databaseName.trim();
		port = port.trim();

		if (!StringUtil.isInteger(port)) {
			throw new Exception(DMI 
				+ "Port' must be an integer.");
		}

		int portNum = StringUtil.atoi(port);

		String storedProcedures = p.getValue(
			DMI + "StoredProcedures");
		boolean useSP = false;
		if (storedProcedures != null 
		    && storedProcedures.equalsIgnoreCase("true")) {
			useSP = true;
		}

		dmi = new HydroBaseDMI(serverType, serverName,
			databaseName, portNum, user, password, useSP);		
	}
	else {
		String databaseType = p.getValue(DMI + "DatabaseType");
		if (databaseType == null) {
			throw new Exception("'" 
				+ DMI + "DatabaseType' property must be "
				+ "defined.");
		}

		String dsnName = p.getValue(DMI + "DSNName");
		if (dsnName == null) {
			throw new Exception("'"
				+ DMI + "DSNName' property must be "
				+ "defined.");
		}
	
		dmi = new HydroBaseDMI(databaseType, dsnName, user, password);	
	}

	return dmi;
}

}
