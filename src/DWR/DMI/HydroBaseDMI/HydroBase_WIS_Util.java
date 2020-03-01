// HydroBase_WIS_Util - static utility functions to be used with the WIS code

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

//-----------------------------------------------------------------------------
// HydroBase_WIS_Util - static utility functions to be used with the WIS code
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 20 Jul 1998	CGB, RTi		Added queries for Special Data and
//					other WIS sheets to define a value based
//					on the WISImport.
// 01 Aug 1998	CGB, RTi		Added computational methods for
//					gainloss calculations by mile and by
//					weight. The methods are overloaded to
//					handle calls from HydroBase_GUI_WIS and
//					HBWISBUILDERGUI.
// 01 Aug 1998	CGB, RTi		Added format methods to setup a string
//					equation for the WIS gainloss.
// 02 Apr 1999	Steven A. Malers, RTi	Code sweep.
// 08 Apr 1999	SAM, RTi		Add dry river feature.
// 15 Jul 2001	SAM, RTi		Add getWISColumnHeading().
//-----------------------------------------------------------------------------
// Notes:
//
// THERE IS A PROBLEM WITH THE DEFINITION OF THE STATIC COLUMN IDENTIFIERS BEING
// PLACED IN --- HydroBase_GUI_WIS --- !!!!!!!!!!!!!!!!!!
//
// WE EVENTUALLY NEED TO MOVE THEM INTO THIS CLASS OR PERHAPS THE HBDATA CLASS.
// THIS WILL REQUIRE SOME MAJOR CODE REVISION FOR THESE STATIC DEFINITIONS. FOR
// NOW I AM IMPORTING THE ---- HydroBase_GUI_WIS --- CLASS INTO THIS CLASS SO 
// I CAN GET A VALUE FROM ANOTHER WIS TO IMPORT.
//-----------------------------------------------------------------------------
// 2003-10-08	J. Thomas Sapienza, RTi	Began moving to code to HydroBaseDMI.
// 2003-10-09	JTS, RTi		Continued work.
// 2003-10-16	JTS, RTi		Continued work.
// 2003-11-18	JTS, RTi		Uncommented formatWeightedGainLoss().
// 2005-02-16	JTS, RTi		* Removed special data code.
//					* Converted queries to use stored
//					  procedures.
// 2005-05-25	JTS, RTi		Converted queries that pass in a 
//					String date to pass in DateTimes 
//					instead.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.Util.Message.Message;
import RTi.Util.String.StringUtil;
import RTi.Util.Time.TimeUtil;
import RTi.Util.Time.DateTime;

import java.lang.Double;
import java.lang.Integer;
import java.util.List;
import java.util.Vector;

import RTi.DMI.DMIUtil;
/**
Static utility methods for WIS manipulation.
*/
public class HydroBase_WIS_Util
{
	
/**
DMIUtil.MISSING_DOUBLE used to be -999 but was changed to NaN.
Define the following to handle legacy values in the database.
*/
private static double LEGACY_MISSING_DOUBLE = -999.;

/**
Computes gain/loss at the specified node.  Gains/losses are based on stream mile.
@param network the network the node is in.
@param curNode the node to check.
*/
public static double[] computeGainLoss(HydroBase_NodeNetwork network, HydroBase_Node curNode) {
	return computeGainLoss(network, curNode, null, true);
}

/**
Computes gain/loss at the specified node.  Gains losses are based on stream mile.
@param network the network the node is in.
@param curNode the node to check.
@param wisDataVector list of WIS data from the WIS
@param isComputed whether the values in the WIS have been computed yet or not
*/
public static double[] computeGainLoss(HydroBase_NodeNetwork network, 
HydroBase_Node curNode, List<HydroBase_WISData> wisDataVector, boolean isComputed) { 
	double[] values = { DMIUtil.MISSING_DOUBLE, DMIUtil.MISSING_DOUBLE };
	double D1X = DMIUtil.MISSING_DOUBLE;
	double D12;
	double factor = 1.0;
	double gainLoss = DMIUtil.MISSING_DOUBLE;
	double Q1 = 0.0;
	double Q2 = 0.0;
	double sumD = 0.0;
	double sumR = 0.0;
	HydroBase_WISFormat wisFormat = curNode.getWISFormat();
	int dl = 10;
	int row = wisFormat.getWis_row() - 1;
	String routine = "HydroBase_WIS_Util.computeGainLoss";
	String messageString;
	String rowType = wisFormat.getRow_type();
	String previousRowType;

	HydroBase_WISData wisData = null;
	if (wisDataVector != null) { 
		wisData = (HydroBase_WISData)wisDataVector.get(row);
	}

	Message.printDebug(10, routine, "Computing Gain/Loss (using stream mile) for row: " + row);

	// do not perform calculation for the following row types
	if (rowType.equals(HydroBase_GUI_WIS.STREAM) 
		|| rowType.equals(HydroBase_GUI_WIS.STRING)) {
		return values;
	}

	// get the upstream and downstream base flow node in the current reach.
	HydroBase_Node upFlowNode = network.findUpstreamBaseflowNodeInReach(
		curNode);
	HydroBase_Node downFlowNode = network.findDownstreamBaseflowNodeInReach(
		curNode);
	HydroBase_Node previousNode = null;
	
	// Set the downFlowNode equal to the curNode if the curNode is a known
	// base flow and the downFlow is null.  Treat dry nodes as baseflow...

	if ((curNode.isDryRiver() || curNode.isBaseflow())
		&& downFlowNode == null) {
		downFlowNode = curNode;
	}

	// This flag is so as not to break existing code in the 
	// HydroBase_GUI_WIS.  When no gains are computed it may need 
	// to set data values to 0.0. set gain/gain loss to 0.0 if not 
	// being computed
	if (!isComputed) {
		gainLoss = 0.0;
	}
	// Set gain/loss to 0.0 if the upstream and downstream baseflow node is
	// null. this should never occur as it is enforced in the builder.
	else if (upFlowNode == null || downFlowNode == null) {
		// format the warning String accordingly
		messageString = "";
		if (upFlowNode == null && downFlowNode == null) {
			messageString = "Upstream and downstream base flow"
				+ "nodes do not exist.";	
		}
		else if (upFlowNode == null) {
			messageString = "Upstream base flow node does not"
				+ " exist.";
		}
		else if (downFlowNode == null) {
			messageString = "Downstream base flow node does not "
				+ "exist.";
		}
	
		Message.printWarning(2, routine, "Gain/Loss will not be "
			+ "computed for row " + row + ". " + messageString);

		gainLoss = 0.0;
	}
	else {	
		// Otherwise compute the gain/loss.
		// Get the upstream and downstream baseflow HydroBase_WISFormat
		// information
		HydroBase_WISFormat wisFormatDown = null;
		HydroBase_WISFormat wisFormatPrevious = null;
		HydroBase_WISFormat wisFormatUp = upFlowNode.getWISFormat();
		int upRow = wisFormatUp.getWis_row() - 1;
		int downRow = DMIUtil.MISSING_INT;

		// Recall that the curNode may be the known base flow point
		if (downFlowNode != null) {
			wisFormatDown = downFlowNode.getWISFormat();
			downRow = wisFormatDown.getWis_row() - 1;
		}

		// if this is the most upstream node in the reach,
		// set gain/loss to 0.0.
		if (network.isMostUpstreamNodeInReach(curNode)) {
			gainLoss = 0.0;
		}
		else {	
			// Otherwise, compute gain/loss as scoped.
			// Current node is a base flow node and is not the
			// top most base flow node in the reach. This will be 
			// the row for all computations to refer to as Q2	
			if (curNode.isDryRiver()|| curNode.isBaseflow()) {
			// Otherwise, compute gain/loss as scoped.
				// cgb !!! added this
				wisFormatDown = wisFormat;
				downRow = row;
			}	
			else {	
				// Otherwise, we need the row number of the
				// downstream known baseflow point
				downRow = wisFormatDown.getWis_row() - 1;
			}
			
			if (wisDataVector != null) {
				// Get the known upstream and downstream
				// baseflow values respectively
				Q1 = getWISDataValue(
					(HydroBase_WISData)wisDataVector.get(upRow), HydroBase_GUI_WIS.POINT_FLOW_COL);
				Q2 = getWISDataValue(
					(HydroBase_WISData)wisDataVector.get(downRow), HydroBase_GUI_WIS.POINT_FLOW_COL);
			}
	
			// In the WIS, STRING rowTypes can act as nodes in 
			// a reach, so to correctly determine the difference 
			// we need to pass over the STRING row types
			previousNode = curNode;
			previousRowType = HydroBase_GUI_WIS.STRING;
			while(previousRowType.equals(HydroBase_GUI_WIS.STRING)){
				previousNode = HydroBase_NodeNetwork.getUpstreamNode(
					previousNode,
					HydroBase_NodeNetwork
					.POSITION_REACH_NEXT );
				// break if nothing upstream
				if (previousNode == null) {
					break;
				}
				// otherwise check again.
				else {
					wisFormatPrevious = 
						previousNode.getWISFormat();
					previousRowType = 
						wisFormatPrevious.getRow_type();
					continue;
				}
			}
			
			// a node does not exist upstream of the curNode, 
			// set D1X to 0.0
			if (previousNode == null) {
				D1X = 0.0;
			}
			else {	
				wisFormatPrevious = previousNode.getWISFormat();
				D1X = getWISFormatValue(wisFormatPrevious, 
					HydroBase_GUI_WIS.DISTANCE)
					- getWISFormatValue(wisFormat, 
					HydroBase_GUI_WIS.DISTANCE);
			}		

			// D12 is the distance between the upstream and
			// downstream baseflow nodes.
			D12 = getWISFormatValue(wisFormatUp, 
				HydroBase_GUI_WIS.DISTANCE)
				- getWISFormatValue(wisFormatDown,
				HydroBase_GUI_WIS.DISTANCE);
	
			// ensure that D12 is not 0.0. if so, set gainLoss to
			// 0.0, as division by 0.0 for D12 will produce a NaN.
			if (D12 == 0.0) {
				gainLoss = 0.0;
			}
			// compute sums(i.e., sum of priority and delivery
			// diversions between Q1 and Q2
			// and sum of releases between Q1 and Q2)
			else {
				if (wisDataVector != null) {
					// Loop begins on upRow+1 so that
					// gain/loss will not be double counted.
					HydroBase_WISData data = null;
					for (int curRow = upRow + 1; 
						curRow <= downRow; curRow++) {
						data = (HydroBase_WISData)wisDataVector.get(curRow);
						sumD += getWISDataValue(data, 
							HydroBase_GUI_WIS.PRIORITY_DIV_COL)
							+ getWISDataValue(data, 
							HydroBase_GUI_WIS.DELIVERY_DIV_COL);
						sumR += getWISDataValue(data, 
							HydroBase_GUI_WIS.TRIB_NATURAL_COL)
							+ getWISDataValue(data, 
							HydroBase_GUI_WIS.TRIB_DELIVERY_COL)
							+ getWISDataValue(data, 
							HydroBase_GUI_WIS.RELEASES_COL);
					}

					factor = Q2 - Q1 + sumD - sumR;
				}
		
				// compute gain/loss
				gainLoss = (D1X / D12)* factor;
				values[0] = D1X;
				values[1] = D12;

				if (Message.isDebugOn) {
					Message.printDebug(dl,
						routine, "For row \"" 
						+ curNode.getDescription()
						+ "\": D1X = " + D1X
						+ " D12 = " + D12
						+ " Q2 = " + Q2
						+ " Q1 = " + Q1
						+ " sumD = " + sumD
						+ " sumR = " + sumR);
				}
			}
		}
	}

	if (wisDataVector != null) {
		wisData.setGain(gainLoss);
		wisDataVector.set(row,wisData);
	}

	return values;
}

/**
Computers gain/loss at the specified node.  Gains/losses are based on weighted
coefficients.
@param network the network in which the node is found.
@param curNode the node to evaluated.
@return the gains/losses.
*/
public static double[] computeWeightedGainLoss(HydroBase_NodeNetwork network,
HydroBase_Node curNode) {
	return computeWeightedGainLoss(network, curNode, null, true);
}

/**
Computers gain/loss at the specified node.  Gains/losses are based on weighted coefficients.
@param network the network in which the node is found.
@param curNode the node to evaluated.
@param wisDataVector the vector of data from the wis
@param isComputed whether the data have been computed or not
@return the gains/losses.
*/
public static double[] computeWeightedGainLoss(HydroBase_NodeNetwork network,
HydroBase_Node curNode, List<HydroBase_WISData> wisDataVector, boolean isComputed) {
	double[] values = { DMIUtil.MISSING_DOUBLE, DMIUtil.MISSING_DOUBLE };
	double D1X = DMIUtil.MISSING_DOUBLE;
	double D12 = DMIUtil.MISSING_DOUBLE;
	double factor = 1.0;
	double gainLoss = DMIUtil.MISSING_DOUBLE;
	double Q1 = 0.0;
	double Q2 = 0.0;
	double sumD = 0.0;
	double sumR = 0.0;
	HydroBase_WISData wisData = null;
	HydroBase_WISFormat wisFormat = curNode.getWISFormat();
	HydroBase_WISFormat wisFormatDown = null;
	HydroBase_WISFormat wisFormatUp = null;
	int curRow = DMIUtil.MISSING_INT;
	int dl = 10;
	int downRow = DMIUtil.MISSING_INT;
	int row = wisFormat.getWis_row() - 1;
	int upRow = DMIUtil.MISSING_INT;
	String routine = "HydroBase_WIS_Util.computeWeightedGainLoss";
	String rowType = wisFormat.getRow_type();

	if (wisDataVector != null) { 
		wisData = (HydroBase_WISData)wisDataVector.get(row);
	}

	if (Message.isDebugOn) {
		Message.printDebug(10, routine, "Computing Gain/Loss (using weights) for row: " + row);
	}

	// do not perform calculation for the following row types

	if (rowType.equals(HydroBase_GUI_WIS.STREAM) || rowType.equals(HydroBase_GUI_WIS.STRING)){
		return values;
	}

	// Get the upstream and downstream base flow node in the current reach.
	HydroBase_Node upFlowNode = network.findUpstreamBaseflowNodeInReach(curNode);
	HydroBase_Node downFlowNode = network.findDownstreamBaseflowNodeInReach(curNode);

	// set the downFlowNode equal to the curNode if the curNode
	// is a known base flow and the downFlow is null
	if (	(curNode.isDryRiver()|| curNode.isBaseflow())
		&& downFlowNode == null) {
		downFlowNode = curNode;
	}

	// This flag is so as not to break existing code in the 
	// HydroBase_GUI_WIS.  When no gains are computed it may need 
	// to set data values to 0.0.  set gain/gain loss to 0.0 if 
	// not being computed
	if (!isComputed) {
		gainLoss = 0.0;
	}
	// set gain/loss to 0.0 if the upstream and downstream baseflow node is
	// null. this should never occur as it is enforced in the builder.
	else if (upFlowNode == null || downFlowNode == null) {

		// format the warning String accordingly
		String messageString = "";
		if (upFlowNode == null && downFlowNode == null) {
			messageString = "Upstream and downstream base flow"
				+ " nodes do not exist.";	
		}
		else if (upFlowNode == null) {
			messageString = "Upstream base flow Node does not "
				+ "exist.";
		}
		else if (downFlowNode == null) {
			messageString = "Downstream base flow node does not "
				+ "exist.";
		}
	
		Message.printWarning(2, routine, "Gain/Loss will not be "
			+ "computed for row " + row + ". " 
			+ messageString);

		gainLoss = 0.0;
	}
	// otherwise compute the gain/loss
	else {	
		// get the upstream and downstream baseflow HydroBase_WISFormat
		// information
		wisFormatUp = upFlowNode.getWISFormat();
		upRow = wisFormatUp.getWis_row() - 1;
		wisFormatDown = null;

		// recall that the curNode may be the known base flow point
		if (downFlowNode != null) {
			wisFormatDown = downFlowNode.getWISFormat();
			downRow = wisFormatDown.getWis_row() - 1;
		}

		// if this is the most upstream node in the reach,
		// set gain/loss to 0.0.
		if (network.isMostUpstreamNodeInReach(curNode)) {
			gainLoss = 0.0;
		}
		// otherwise, compute gain/loss as scoped
		else {
			// current node is a base flow node and is not the
			// top most base flow node in the reach. This will be 
			// the row for all computations to refer to as Q2	
			if (curNode.isDryRiver() || curNode.isBaseflow()) {
				wisFormatDown = wisFormat;
				downRow = row;
			}	
			// otherwise, we need the row number of the downstream	
			// known baseflow point
			else {
				downRow = wisFormatDown.getWis_row() - 1;
			}
			
			if (wisDataVector != null) {
				// get the known upstream and downstream baseflow values respectively
				Q1 = getWISDataValue((HydroBase_WISData)wisDataVector.get(upRow),
					HydroBase_GUI_WIS.POINT_FLOW_COL);
				Q2 = getWISDataValue((HydroBase_WISData)wisDataVector.get(downRow),
					HydroBase_GUI_WIS.POINT_FLOW_COL);
			}

			D1X = getWISFormatValue(wisFormat, HydroBase_GUI_WIS.WEIGHT);
			D12 = 0.0;
	
			HydroBase_Node reachNode = upFlowNode;
			HydroBase_WISFormat format = null;
			for (curRow = upRow + 1; curRow <= downRow; curRow++) {
				reachNode = HydroBase_NodeNetwork.getDownstreamNode(reachNode,
				       HydroBase_NodeNetwork.POSITION_RELATIVE);
				format = reachNode.getWISFormat();
				rowType = format.getRow_type();

				// In the WIS, STRING rowTypes can act as nodes in a reach, so to correctly determine 
				// the summation we need to pass over the STRING row types
				if (!rowType.equals(HydroBase_GUI_WIS.STRING)) {
					if (Message.isDebugOn) {
						Message.printDebug(10,
							routine, "Accumulating "
							+ "D12 using row "
							+ (format
							.getWis_row() - 1)
							+ " weight value is "
							+ format
							.getGain_factor());
					}

					D12 += getWISFormatValue(format,  
						HydroBase_GUI_WIS.WEIGHT);

					// compute sums(i.e., sum of priority 
					// and delivery diversions between Q1 
					// and Q2, sum of releases between
					// Q1 and Q2, and sum of weights 
					// between Q1 and Q2)
					if (wisDataVector != null) {
						HydroBase_WISData data = null;
						data = (HydroBase_WISData)wisDataVector.get(curRow);
						sumD += getWISDataValue(data, 
							HydroBase_GUI_WIS.PRIORITY_DIV_COL)
							+ getWISDataValue(data, 
							HydroBase_GUI_WIS.DELIVERY_DIV_COL);
						sumR += getWISDataValue(data, 
							HydroBase_GUI_WIS.TRIB_NATURAL_COL)
							+ getWISDataValue(data, 
							HydroBase_GUI_WIS.TRIB_DELIVERY_COL)
							+ getWISDataValue(data,
							HydroBase_GUI_WIS.RELEASES_COL);
					}
				}
			}

			if (wisDataVector != null) {
				factor = Q2 - Q1 + sumD - sumR;
			}

			// ensure that D12 is not 0.0. if so, set gainLoss to
			// 0.0, as division by 0.0 for D12
			// will produce a NaN.
			if (D12 == 0.0) {
				gainLoss = 0.0;
			}
			else {	
				// compute gain/loss 
				gainLoss = (D1X / D12) * factor;
				values[0] = D1X;
				values[1] = D12;
	
				if (Message.isDebugOn) {
					Message.printDebug(dl, routine, 
						"For row \"" 
						+ curNode.getDescription()
						+ "\" D1X = " + D1X
						+ " D12 = " + D12
						+ " Q2 = " + Q2
						+ " Q1 = " + Q1
						+ " sumD = " + sumD
						+ " sumR = " + sumR);
				}
			}
		}
	}
	
	if (wisDataVector != null) {
		wisData.setGain(gainLoss);
		wisDataVector.set(row, wisData);
	}

	return values;
}

/**
Formats a gain/loss array as a String.
@param values a gain/loss array
@return the gain/loss array formatted as a String.
*/
public static String formatGainLoss(double[] values) {
	String mileString = new String("");

	if (values == null || values.length != 2) {
		return mileString;
	}
	
	double D1X = values[0];
	double D12 = values[1];

	if (D12 != 0.0) {
		double coeff = D1X/D12;
		mileString = StringUtil.formatString(coeff, "%10.2f").trim();	
		mileString = "" + D1X + "/" + D12 + " = " + mileString;
	}
	else {
		mileString = "0.0";
	}

	return mileString;
}

/**
Formats a weighted gain/loss array as a String.
@param values a weighted gain/loss array.
@return the weighted gain/loss array formatted as a String.
*/
public static String formatWeightedGainLoss(double[] values) {
	String weightString = new String("");

	if (values == null || values.length != 2) {
		return weightString;
	}

	double w = values[0];
	double sumW = values[1];

	if (sumW != 0.0) {
		double weight = w / sumW;
		weightString = StringUtil.formatString(weight, "%10.2f").trim();
		weightString = "" + w + "/" + sumW + " = " + weightString;
	}
	else {
		weightString = "0.0";
	}

	return  weightString;
}

/**
Processes a wis import object and returns the imported data value.  Calls 
routines to return a data value based on whether the wis import is 
defined for one of the following cases.  The logic for determining which type of
import is being dealt with is somewhat obscure because a type identification
in the wis_import database table was not originally provided.  So the original
real-time import type remains the default case if the wis_import.import_wis_num
is missing.  If real-time station calls getRealTimeValue(), 
wis_import.import_wis_num = DMIUtil.MISSING_LONG.  Else, Special project calls
getSpecialProjectValue and wis_import.import_identifier starts with
"SPData".  WIS calls getWISWISImportValue and wis_import.import_wis_num
starts with "WIS".
@param dmi the dmi to use for processing.
@param wis_import the wis import object to process.
@return the imported data value list.
*/
public static List<Object> getWISImportValue(HydroBaseDMI dmi, 
HydroBase_WISImport wis_import) {
	String routine = "HydroBase_WIS_Util.getWISImportValue";
	List<Object> v = new Vector<Object>();
	
	// Set the vector's initial values to a flow of zero and zero
	// measurements found...
	v.add(new Double(0.0));
	v.add(new Integer(0));

	if (wis_import == null) {
		Message.printWarning(2, routine, "NULL HydroBase_WISImport");
		return v;
	}

	String id = wis_import.getImport_identifier();
	if (id.startsWith("WIS")) {
		v = getWISValue(dmi, wis_import);
	}
	// Assume real time as the defualt case(i.e. without any
	// prefix on the identifier).
	else {
		v = getRealTimeValue(dmi, wis_import);
	}

	return v;
}		

/**
Sets values for real-time wis_import. At this time, any real-time data with 
a flag are suspect, so they are not processed.
@param dmi the dmi to use for processing
@param wis_import the wis import object to process.
@return a list with a Double in (0) that has the data value to use and an
Integer in (1) that has the number of  data values used to computer (0).  
*/
private static List<Object> getRealTimeValue(HydroBaseDMI dmi, 
HydroBase_WISImport wis_import) {
	String routine = "HydroBase_WIS_Util.getRealTimeValue";
	List<Object> v = new Vector<Object>();
	
	// Set the vector's initial values to a flow of zero and zero
	// measurements found...
	v.add(new Double(0.0));
	v.add(new Integer(0));

	// Start by getting the RT_meas data for the import.  The dates are
	// limited as follows.  In all cases, the end_time and offset are
	// relative to local time.  However, the measurement dates in the
	// database are in MST.  Therefore, we may need to switch to account for
	// the current local time zone.
	//
	// 1)	The is the special case, if the end_time is not specified then
	//	we assume that they want the most current real-time record from
	//	today. Query based on today's date and order by date. This gives
	//      us the most recent record at the end of the results Vector.
	// 2)	If end_time and offset are specified, use them as is.
	// 3)  If the end_time is specified and the offset is not, then
	//	we assume that the offset is one day before the end-time.
	List<HydroBase_RTMeas> measurements = new Vector<HydroBase_RTMeas>();
	HydroBase_RTMeas measurement;
	int size = 0;
	DateTime end = new DateTime(DateTime.DATE_CURRENT 
		| DateTime.PRECISION_MINUTE);	// Local time now.
	DateTime start = null;
	String start_string = null;
	String end_string = null;

	// Change this if we want to query data that has flags...
	boolean useFlaggedData = false;

	List<String> order = new Vector<String>();

	int wis_import_end_time = wis_import.getEnd_time();
	int wis_import_time_offset = wis_import.getTime_offset();

	// no end time specified assume that we need find the most recent record
	if (DMIUtil.isMissing(wis_import_end_time) || HydroBase_Util.isMissing(wis_import_end_time)) {
		start = new DateTime(end);

		// set to start of the day
		start.setHour(0);
		start.setMinute(0);

		try {
			start_string = DMIUtil.formatDateTime(dmi, start);
			end_string = DMIUtil.formatDateTime(dmi, end);
		}
		catch (Exception e) {}

		order.add("rt_meas.date_time");

		List<HydroBase_RTMeas> results = null;
		try {
			results = dmi.readRTMeasList(wis_import.getMeas_num(),
				start, end, useFlaggedData, false);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading "
				+ "from database.");
			Message.printWarning(2, routine, e);
		}

		if (results == null) {
			Message.printWarning(2, routine,
				"No measurements for meas_num "
				+ wis_import.getMeas_num()
				+ " and dates from " + start_string + " to " 
				+ end_string);
			return v;
		}
		size = results.size();
		if (size == 0) {
			Message.printWarning(2, routine,
				"No measurements for meas_num "
				+ wis_import.getMeas_num()
				+ " and dates from " + start_string + " to " 
				+ end_string);
			return v;
		}

		// Get the last record.
		measurement = results.get(size - 1);
		measurements.add(measurement);
	}

	// The end time has been specified so we need to use it to form 
	// the query.
	else {
		int[] hour_min =
			TimeUtil.parseMilitaryTime(wis_import_end_time);
		end.setHour(hour_min[0]);
		end.setMinute(hour_min[1]);
		end.setPrecision(DateTime.PRECISION_MINUTE);

		start = new DateTime(end);
		start.setPrecision(DateTime.PRECISION_MINUTE);
		if (DMIUtil.isMissing(wis_import_time_offset) || HydroBase_Util.isMissing(wis_import_time_offset)) {
			// The offset is not specified, use 24 hours...
			start.addHour(-24);
		}
		else if (wis_import_time_offset == 0) {
			// The offset is specified zero, use 0 hours
			// (spot reading)...
			start.addHour(-0);
		}
		else {	
			// Use the specified hour...
			start.addHour(-hour_min[0]);
			start.addMinute(-hour_min[1]);
		}

		try {
			start_string = DMIUtil.formatDateTime(dmi, start);
			end_string = DMIUtil.formatDateTime(dmi, end);
		}
		catch (Exception e) {
			Message.printWarning(1, routine,
				"Error formatting date.");
			Message.printWarning(2, routine, e);
		}
		// Process the results to get the final numbers...

		List<HydroBase_RTMeas> results = null;
		try {
			results = dmi.readRTMeasList(wis_import.getMeas_num(),
				start, end, useFlaggedData, false);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading "
				+ "from database.");
			Message.printWarning(2, routine, e);
		}		

		if (results == null) {
			Message.printWarning(2, routine,
				"No measurements for meas_num "
				+ wis_import.getMeas_num()
				+ " and dates from " + start_string + " to " 
				+ end_string);
			return v;
		}
		size = results.size();
		if (size == 0) {
			Message.printWarning(2, routine,
				"No measurements for meas_num "
				+ wis_import.getMeas_num()
				+ " and dates from " + start_string + " to " 
				+ end_string);
			return v;
		}
		for (int i = 0; i < size; i++) {
			measurement = (HydroBase_RTMeas)results.get(i);
			measurements.add(measurement);
		}
	}

	// Now we have the desired records calculate the max, min, or mean...
	String import_method = wis_import.getImport_method();
	double max = 0.0, min = 0.0, sum = 0.0, value;
	String units;
	size = measurements.size();
	for (int i = 0; i < size; i++) {
		if (i == 0) {
			sum = 0.0;
			min = 10000000.0;
			max = 0.0;
		}
		measurement = (HydroBase_RTMeas)measurements.get(i);
		value = measurement.getAmt();
		units = measurement.getUnit();
		if (!units.equalsIgnoreCase("cfs") && !units.equals("")) {
			Message.printWarning(1, routine,
				"Problem.  Do not know how to convert "
				+ "units from \"" + units 
				+ "\" to CFS for WIS");
			v.set(0,new Double(0.0));
			v.set(1,new Integer(0));
			return v;
		}
		if (import_method.equalsIgnoreCase("ave")
			|| import_method.equalsIgnoreCase("a")
			|| import_method.equalsIgnoreCase("avg")) {
			sum += value;
		}
		else if (import_method.equalsIgnoreCase("min")) {
			if (value < min) {
				min = value;
			}
		}
		else if (import_method.equalsIgnoreCase("max")) {
			if (value > max) {
				max = value;
			}
		}
	}

	// Return the vector of results...

	if (import_method.equalsIgnoreCase("ave")) {
		sum /= (double)size;
		v.set(0,new Double(sum));
	}
	else if (import_method.equalsIgnoreCase("min")) {
		v.set(0,new Double(min));
	}
	else if (import_method.equalsIgnoreCase("max")) {
		v.set(0,new Double(max));
	}
	v.set(1,new Integer(size));
	return v;
}

/**
Sets values for other wis sheet wis_import.  
@param dmi the dmi to use for processing
@param wis_import the wis import object to process
@return a list with a Double in (0) that has the data value to use and an
Integer in (1) that has the number of data values used to computer (0).  In 
the case of other wis sheets only one value is used, so this is fixed.
*/
private static List<Object> getWISValue(HydroBaseDMI dmi, 
HydroBase_WISImport wis_import) {
	String routine = "HydroBase_WIS_Util.getWISValue";
	List<Object> v = new Vector<Object>();
	
	// Set the vector's initial values to a flow of zero and zero
	// measurements found...
	v.add(new Double(0.0));
	v.add(new Integer(0));

	String id = wis_import.getImport_identifier();

	int begin = -1;
	if (id == null) {
		return v;
	}

	// Remove the start string by looking for '.'
	begin = id.indexOf('.')+ 1;
	if (begin == -1) {
		return v;
	}

	id = id.substring(begin, id.length()).trim();
    
	// Set one date which is assumed to be today. So today's date is used
	// to pull the record.
	DateTime start = new DateTime(DateTime.DATE_CURRENT);
	start.setPrecision(DateTime.PRECISION_DAY);
	String start_string = null;
	try {
		start_string = DMIUtil.formatDateTime(dmi, start);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error formatting date.");
		Message.printWarning(2, routine, e);
	}

	int import_num = wis_import.getImport_wis_num();

	// First process the HydroBase_WISFormat data to get the row 
	// number we are working with.
	List<String> where = new Vector<String>();
	where.add("wis_format.wis_num = " + import_num);
	where.add("wis_format.identifier = '" + id + "'");
	List<HydroBase_WISFormat> results = null;
	try {
		results = dmi.readWISFormatList(import_num, null, id);
	}
	catch (Exception e) {
		Message.printWarning(1, routine,"Error reading from database.");
		Message.printWarning(2, routine, e);
	}
	
	if (results == null) {
		Message.printWarning(2, routine,
			"No WIS row available for WIS: " + import_num
			+ " identifier: " + id);
		return v;
	}
	int size = results.size();
	if (size == 0) {
		Message.printWarning(2, routine,
			"No WIS row available for WIS: " + import_num
			+ " identifier: " + id);
		return v;
	}
	if (size > 1) {
		Message.printWarning(2, routine,
			"Multiple rows defined for WIS: " + import_num
			+ " identifier: " + id);
		return v;
	}

	// get the row number so we can get the correct HydroBase_WISData
	HydroBase_WISFormat format = 
		(HydroBase_WISFormat)results.get(size - 1);
	int row = format.getWis_row() - 1;

	List<HydroBase_WISData> resultsWISData = null;
	try {
		resultsWISData = dmi.readWISDataList(import_num, start, row);
	}
	catch (Exception e) {
		Message.printWarning(1, routine,"Error reading from database.");
		Message.printWarning(2, routine, e);
	}

 	// Process the results to get the final number
	if (resultsWISData == null) {
		Message.printWarning(2, routine,
			"No WIS data available for WIS: " + import_num
			+ " row: " + row + " and date of " + start_string);
		return v;
	}
	size = resultsWISData.size();
	if (size == 0) {
		Message.printWarning(2, routine,
			"No WIS data available for WIS: " + import_num
			+ " row: " + id + " and date of " + start_string);
		return v;
	}

	// If we have values than we will assume the last value is the one 
	// we want.  There should only be one value.
	HydroBase_WISData data = resultsWISData.get(size - 1);

	// Get the correct value based on the defined import column which was
	// requested.
	double value = getWISColumnValue(data, wis_import.getImport_column());
	if ( !DMIUtil.isMissing(value) || HydroBase_Util.isMissing(value) ) {
		// If we get here than set the value
		v.set(0,new Double(value));
		v.set(1,new Integer(1));
	}

	return v;
}

/**
Return the string heading for a WIS column
@return the heading for the specified column or an empty string if not a valid
heading.
@param column to look up(0 is row label).  Only data columns should be
requested.
*/
/*
UNDER CONSTRUCTION
public static String getWISColumnHeading(int col) {
{	if (col == HydroBase_GUI_WIS.POINT_FLOW_COL) {
		return HydroBase_GUI_WIS.POINT_FLOW;
	}
	else if (col == HydroBase_GUI_WIS.NATURAL_FLOW_COL) {
		return HydroBase_GUI_WIS.NATURAL_FLOW;
	}
	else if (col == HydroBase_GUI_WIS.DELIVERY_FLOW_COL) {
		return HydroBase_GUI_WIS.DELIVERY_FLOW;
	}
	else if (col == HydroBase_GUI_WIS.GAIN_LOSS_COL) {
		return HydroBase_GUI_WIS.GAIN_LOSS;
	}
	else if (col == HydroBase_GUI_WIS.TRIB_NATURAL_COL) {
		return HydroBase_GUI_WIS.TRIB_NATURAL;
	}
	else if (col == HydroBase_GUI_WIS.TRIB_DELIVERY_COL) {
		return HydroBase_GUI_WIS.TRIB_DELIVERY;
	}
	else if (col == HydroBase_GUI_WIS.RELEASES_COL) {
		return HydroBase_GUI_WIS.RELEASES;
	}
	else if (col == HydroBase_GUI_WIS.PRIORITY_DIV_COL) {
		return HydroBase_GUI_WIS.PRIORITY_DIV;
	}
	else if (col == HydroBase_GUI_WIS.DELIVERY_DIV_COL) {
		return HydroBase_GUI_WIS.DELIVERY_DIV;
	}
	else {	return "";
	}
	return null;
}
*/	

/**
Returns the appropriate value from a HydroBase_WISData object based on the 
String parameter passed in.  This String should be one of the static wis
column headings (point flow, release, ...).
@param data the object from which to return the value
@param s the String that tells which value to return
@return the specified value
*/
public static double getWISColumnValue(HydroBase_WISData data, String s) {
	double value = DMIUtil.MISSING_DOUBLE;

	if (s.equals(HydroBase_GUI_WIS.POINT_FLOW)) {
		value = data.getPoint_flow();
	}
	else if (s.equals(HydroBase_GUI_WIS.NATURAL_FLOW)) {
		value = data.getNat_flow();
	}
	else if (s.equals(HydroBase_GUI_WIS.DELIVERY_FLOW)) {
		value = data.getDelivery_flow();
	}
	else if (s.equals(HydroBase_GUI_WIS.GAIN_LOSS)) {
		value = data.getGain();
	}
	else if (s.equals(HydroBase_GUI_WIS.TRIB_NATURAL)) {
		value = data.getTrib_natural();
	}
	else if (s.equals(HydroBase_GUI_WIS.TRIB_DELIVERY)) {
		value = data.getTrib_delivery();
	}
	else if (s.equals(HydroBase_GUI_WIS.RELEASES)) {
		value = data.getRelease();
	}
	else if (s.equals(HydroBase_GUI_WIS.PRIORITY_DIV)) {
		value = data.getPriority_divr();
	}
	else if (s.equals(HydroBase_GUI_WIS.DELIVERY_DIV)) {
		value = data.getDelivery_divr();
	}
	// New missing is NaN but -999 was used before
	if (DMIUtil.isMissing(value) || (value == LEGACY_MISSING_DOUBLE) || HydroBase_Util.isMissing(value) ) {
		return 0;
	}

	return value;
}

/**
Return the specified variable from the wisDataVector object for the requested
row.
@return the value for the specified row variable.
@param data HydroBase_WISData to evalutate.
@param col Data flag to retrieve value.
*/
public static double getWISDataValue(HydroBase_WISData data, int col) {
	double value = DMIUtil.MISSING_DOUBLE;

	switch(col) {
		case HydroBase_GUI_WIS.POINT_FLOW_COL:
			value = data.getPoint_flow();
			break;
		case HydroBase_GUI_WIS.NATURAL_FLOW_COL:
			value = data.getNat_flow();
			break;
		case HydroBase_GUI_WIS.DELIVERY_FLOW_COL:
			value = data.getDelivery_flow();
			break;
		case HydroBase_GUI_WIS.GAIN_LOSS_COL:
			value = data.getGain();
			break;
		case HydroBase_GUI_WIS.TRIB_NATURAL_COL:
			value = data.getTrib_natural();
			break;
		case HydroBase_GUI_WIS.TRIB_DELIVERY_COL:
			value = data.getTrib_delivery();
			break;
		case HydroBase_GUI_WIS.RELEASES_COL:
			value = data.getRelease();
			break;
		case HydroBase_GUI_WIS.PRIORITY_DIV_COL:
			value = data.getPriority_divr();
			break;
		case HydroBase_GUI_WIS.DELIVERY_DIV_COL:
			value = data.getDelivery_divr();
			break;		
	}

	if (DMIUtil.isMissing(value) || (value == LEGACY_MISSING_DOUBLE) || HydroBase_Util.isMissing(value) ) {
		return 0;
	}
	
	return value;
}

/**
Returns the specified variable from the wis format object.
@param flag the value to return.
@return the specified variable.
*/
public static double getWISFormatValue(HydroBase_WISFormat format, int flag) {
	double value = DMIUtil.MISSING_DOUBLE;
	if (format == null || (format.getWis_row() - 1) < 1) {
		return(0.0);
	}

	switch (flag) {
		case HydroBase_GUI_WIS.DISTANCE:
			value = format.getStr_mile();
			break;
		case HydroBase_GUI_WIS.WEIGHT:
			value = format.getGain_factor();
			break;
	}
	return value;
}

}
