// HydroBase_Node - a representation of a node on a stream network

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2023 Colorado Department of Natural Resources

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

import java.io.PrintWriter;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

import RTi.GR.GRColor;
import RTi.GR.GRDrawingAreaUtil;
import RTi.GR.GRJComponentDrawingArea;
import RTi.GR.GRLimits;
import RTi.GR.GRSymbol;
import RTi.GR.GRSymbolPosition;
import RTi.GR.GRSymbolShapeType;
import RTi.GR.GRSymbolType;
import RTi.GR.GRText;
import RTi.GR.GRUnits;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import java.util.List;
import java.util.Vector;

/**
This class stores basic node information for use with HydroBase_NodeNetwork.
*/
public class HydroBase_Node
extends DMIDataObject {

/**
Class name.
*/
private final String __CLASS = "HydroBase_Node";

/**
Blank node for spacing nodes on the Makenet plot.
*/
public static final int NODE_TYPE_BLANK	= 0;

/**
Diversion node.
*/
public static final int NODE_TYPE_DIV = 1;

/**
Streamflow node (stream gage).
*/
public static final int NODE_TYPE_FLOW = 2;

/**
Confluence node.
*/
public static final int NODE_TYPE_CONFLUENCE = 3;

/**
Instream flow node.
*/
public static final int NODE_TYPE_ISF = 4;

/**
Reservoir node.
*/
public static final int NODE_TYPE_RES = 5;

/**
Import node.
*/
public static final int NODE_TYPE_IMPORT = 6;

/**
Baseflow node (stream estimate).
*/
public static final int NODE_TYPE_BASEFLOW = 7;

/**
Node at the bottom of a network.
*/
public static final int NODE_TYPE_END = 8;

/**
Other node type (unclassified).
*/
public static final int NODE_TYPE_OTHER = 9;

/**
Unknown node type for initialization and StateMod .rin files that do not have the node type.
*/
public static final int NODE_TYPE_UNKNOWN = 10;

/**
Used for top of stream reaches.
*/
public static final int NODE_TYPE_STREAM = 11;

/**
Used for label points.
*/
public static final int NODE_TYPE_LABEL = 12;

/**
Used for formula cells.
*/
public static final int NODE_TYPE_FORMULA = 13;

/**
Well node (no connection to surface water ditch).
*/
public static final int NODE_TYPE_WELL = 14;

/**
Confluence node for downstream end of off-channel stream.
*/
public static final int NODE_TYPE_XCONFLUENCE = 15;

/**
Well node associated with surface water ditch.
*/
public static final int NODE_TYPE_DIV_AND_WELL = 16;

/**
Node only used in the network drawing code.
This is a node that functions as a user-defined label on the screen.
*/
public static final int NODE_TYPE_LABEL_NODE = 17;

/**
Indicate how upstream nodes are constructed.
The default is to add tributaries first (like makenet) but CWRAT adds the main stem first.
*/
public static final int
	TRIBS_ADDED_FIRST = 1,
	TRIBS_ADDED_LAST = 2;

/**
To determine the kind of names to return.
*/
public final static int
	FULL = 0,
	ABBREVIATION = 1;

/**
Whether to draw the node text or not.
*/
private static boolean __drawText = true;

/**
Whether the node is being drawn in the WIS network display.
*/
private boolean __inWis = true;

/**
Whether the node is a baseflow node or not.  Makenet-specific.
*/
private	boolean __isBaseflow;

/**
Whether the node is an import node or not.
*/
private boolean __isImport;

/**
Whether the river is dry at the node.  WIS-specific.
*/
private boolean __isDryRiver;

/**
Area as float.  Makenet-specific.
*/
private	double __area;

/**
Angle to print label at.  0 == East.  Makenet-specific.
*/
private double __labelAngle;

/**
Precipitation as float.  Makenet-specific.
*/
private double __precip;

/**
The proration factor to distribute the gain.  Makenet-specific.
*/
private double __prorationFactor;

/**
Stream mile for structure (overall from border).  WIS-specific.
*/
private	double __streamMile;

/**
Area * precip as float.  Makenet-specific.
*/
private double __water;

/**
Plotting coordinates for node.  Makenet-specific.
*/
private double
	__x,
	__y;

/**
Pointer to the node directly downstream of this node.
*/
private HydroBase_Node __downstream;

/**
WIS format row associated with this node.  WIS-specific.
*/
private HydroBase_WISFormat __wisFormat;

/**
The size of the icon in pixels on the screen.
*/
public static int ICON_DIAM = 20;

/**
The extra space around the node icon for displaying the extra circle showing that it is a baseflow node.
*/
public static int BASEFLOW_DIAM = 6;

/**
Computational order of nodes, with 1 being most upstream.
This generally has to be set after the entire network has been populated.
*/
private int __computationalOrder = -1;

/**
Label direction (for plotting network).  Makenet-specific.
*/
private int __labelDir;

/**
The node number in the reach (starting at one).
*/
private int __nodeInReachNum;

/**
The reach number counting the total number of streams in the system.
Therefore the first reach in the system is 1, the next reach added is 2, etc.
*/
private int __reachCounter;

/**
The level of the river with 1 being the main stem.
*/
private int __reachLevel;

/**
Serial integer used to keep a running count of the nodes in the network.
*/
private int __serial;

/**
The number of tributaries to the parent stream (starting at one).
In other words, if the downstream node has multiple upstream nodes, this is the counter for those nodes.
That allows a search coming from upstream to know which reach it is coming from.
Mainly important on nodes above a confluence.
*/
private int __tributaryNum;

/**
Node type.
*/
private int __type;

/**
How upstream nodes are constructed.  See TRIBS_*.
*/
private int __upstreamOrder;

/**
Link data for confluences.
*/
private long __link;

/**
Equivalent to wdwater_num or stream_num (when that gets implemented), WIS-specific.
*/
private long __streamNum;

/**
An object of any kind that can be associated with a node.
*/
private Object __associatedObject;

/**
Area as string.  Makenet-specific.
*/
private	String __areaString;

/**
Node description.
*/
private String __desc;

/**
Common ID.
*/
private String __commonID;

/**
ID from the net file.  Makenet-specific.
*/
private String __netID;

/**
Precipitation as string.  Makenet-specific.
*/
private String __precipString;

/**
River node to use in output.  Makenet-specific.
*/
private String __riverNodeID;

/**
User description as set during the processMakenet process.  Makenet-specific.
*/
private	String __userDesc;

/**
Area * precip as String.  Makenet-specific.
*/
private String __waterString;

/**
Vector of nodes directly upstream of this node.
*/
private List<HydroBase_Node> __upstream;

//--------------------------------------------------------------------------
// Data members used solely with the network drawing code.
//--------------------------------------------------------------------------

/**
Whether this nodes drawing bounds have been calculated or not.
*/
private boolean __boundsCalculated = false;

/**
Whether this node was selected with a mouse drag.
*/
private boolean __isSelected = false;

/**
Whether this node was already stored in the database or was generated new for the current network drawer.
*/
private boolean __readFromDB = false;

/**
Whether this node is visible or not.
*/
private boolean __visible = true;

/**
Whether calls should be shown.
*/
private boolean __showCalls = false;

/**
Whether the delivery flow data should be shown.
*/
private boolean __showDeliveryFlow = false;

/**
Whether the natural flow data should be shown.
*/
private boolean __showNaturalFlow = false;

/**
Whether the point flow data should be shown.
*/
private boolean __showPointFlow = false;

/**
Whether the rights should be shown.
*/
private boolean __showRights = false;

/**
The delivery flow value.
*/
private double __deliveryFlow = DMIUtil.MISSING_DOUBLE;

/**
The original UTM X and Y values read for this node's structure from the database.
*/
private double
	__dbX = DMIUtil.MISSING_DOUBLE,
	__dbY = DMIUtil.MISSING_DOUBLE;

/**
The height of the area drawn by this node.
*/
private double __height;

/**
The natural flow value.
*/
private double __naturalFlow = DMIUtil.MISSING_DOUBLE;

/**
The point flow value.
*/
private double __pointFlow = DMIUtil.MISSING_DOUBLE;

/**
The width of the area drawn by this node.
*/
private double __width;

/**
The symbol drawn along with this node.  Depends on the type of node it is.
Can be null, in which case no symbol will be drawn.
*/
private GRSymbol __symbol = null;

/**
A secondary symbol drawn in conjunction with the primary symbol.
If null, then it won't be drawn.
*/
private GRSymbol __secondarySymbol = null;

/**
The WIS num of the wis that this node is associated with.
*/
private int __wisNum = DMIUtil.MISSING_INT;

/**
Call information.
*/
private String __call = DMIUtil.MISSING_STRING;

/**
The unique identifier for this node.  Similar to the common ID.
*/
private String __identifier = null;

/**
The label drawn along with this node.
*/
private String __label = null;

/**
The verbose kind of node this is.
*/
private String __nodeType = null;

/**
Right information.
*/
private String __right = DMIUtil.MISSING_STRING;

/**
The text to be drawn inside the node symbol.
*/
private String __symText = null;

/**
The id of the node immediately downstream of this node.
*/
private String __downstreamNodeID = null;

/**
The ids of all the nodes immediately upstream of this node.
*/
private List<String> __upstreamNodeIDs = null;
//--------------------------------------------------------------------------

/**
Constructor.
Constructs node and initializes to reasonable values(primarily empty strings and zero or -1 values.
*/
public HydroBase_Node() {
	initialize();
}

/**
Add a node downstream from this node.
@param downstream_node Downstream node to add.
@return true if successful, false if not.
*/
public boolean addDownstreamNode(HydroBase_Node downstream_node) {
	String routine = __CLASS + ".addDownstreamNode";
	int dl = 50;

	try {

	if (Message.isDebugOn) {
		Message.printDebug(dl, routine,
			"Adding \"" + downstream_node.getCommonID()
			+ "\" downstream of \"" + getCommonID() + "\"");
	}

	HydroBase_Node oldDownstreamNode = __downstream;

	if (__downstream != null) {
		// There is a downstream node and we need to reconnect it.

		// For the original downstream node, reset its upstream reference to the new node.
		// Use the common identifier to find the element to reset.
		int pos = __downstream.getUpstreamNodePosition(getCommonID());
		if (pos >= 0) {
			List<HydroBase_Node> downstreamUpstream = __downstream.getUpstreamNodes();
			if (downstreamUpstream != null) {
				downstreamUpstream.set(pos,downstream_node);
			}
		}
		// Connect the new downstream node to this node.
		__downstream = downstream_node;

		// Set the upstream node of the new downstream node to point to this node.
		// For now, assume that the node that is being inserted is a new node.
		if (downstream_node.getNumUpstreamNodes() > 0) {
			Message.printWarning(1, routine,
				"Node \"" + downstream_node.getCommonID()
				+ "\" has #upstream > 0");
		}

		// Set the new downstream node data.
		downstream_node.setDownstreamNode(oldDownstreamNode);
		downstream_node.addUpstreamNode(this);
		// Set the new current node data.
		__tributaryNum = downstream_node.getNumUpstreamNodes();
	}
	else {
		// Always need to do this step.
		downstream_node.addUpstreamNode(this);
	}

	String downstreamCommonid = null;
	if (downstream_node.getDownstreamNode() != null) {
		downstreamCommonid = oldDownstreamNode.getCommonID();
	}
	if (Message.isDebugOn) {
		Message.printDebug(dl, routine,
			"\"" + downstream_node.getCommonID()
			+ "\" is downstream of \""
			+ getCommonID() + "\" and upstream of \""
			+ downstreamCommonid + "\"");
	}
	return true;

	}
	catch (Exception e) {
		Message.printWarning(2,routine,"Error adding downstream node.");
		Message.printWarning(2, routine, e);
		return false;
	}
}

/**
Adds a node upstream of this node.
This method is used by the network plotting code and simply appends this node to the end of the __upstream list,
which is why it is used in lieu of the addUpstreamNode() method.
@param node the node to add.
*/
public void addUpstream(HydroBase_Node node) {
	__upstream.add(node);
}

/**
Add a node upstream from this node.
@param upstream_node Node to add upstream.
@return true if successful, false if not.
*/
public boolean addUpstreamNode(HydroBase_Node upstream_node) {
	String routine = __CLASS + ".addUpstreamNode";
	int dl = 50;

	// Add the node to the vector.
	try {

	if (Message.isDebugOn) {
		Message.printDebug(dl, routine,
			"Adding \"" + upstream_node.getCommonID()
			+ "\" upstream of \"" + getCommonID() + "\"");
	}

	if (__upstream == null) {
		// Need to allocate space for it.
		__upstream = new Vector<HydroBase_Node>();
	}

	__upstream.add(upstream_node);

	// Make so the upstream node has this node as its downstream node.
	upstream_node.setDownstreamNode(this);
	if (Message.isDebugOn) {
		Message.printDebug(dl, routine, "\""
			+ upstream_node.getCommonID() + "\" downstream is \""
			+ getCommonID() + "\"");
	}

	return true;

	}
	catch (Exception e) {
		Message.printWarning(2, routine, "Error adding upstream node.");
		Message.printWarning(2, routine, e);
		return false;
	}
}

/**
Adds an id to the list of upstream node ids.  Used by the network drawing code.
@param id the id to add to the upstream node list.  If the list is null it will first be created.
*/
public void addUpstreamNodeID(String id) {
	if (__upstreamNodeIDs == null) {
		__upstreamNodeIDs = new Vector<>();
	}
	__upstreamNodeIDs.add(id);
}

/**
Calculates the area occupied by this node on the drawing area.
@param da the drawing area on which to calculate bounds.
*/
public void calculateBounds(GRJComponentDrawingArea da) {
	if (__inWis) {
		calculateWISBounds(da);
	}
	else {
		calculateNetworkBounds(da);
	}
}

/**
Calculates bounds for nodes in the Network editor display.
@param da the drawing area on which to calculate bounds.
*/
private void calculateNetworkBounds(GRJComponentDrawingArea da) {
	__symText = null;
	__symbol = null;
	__nodeType = null;
	if (__nodeType == null) {
		switch (__type) {
			case NODE_TYPE_BLANK:
				__nodeType = "Blank";		break;
			case NODE_TYPE_DIV:
				__nodeType = "Diversion";	break;
			case NODE_TYPE_FLOW:
				__nodeType = "Streamflow";	break;
			case NODE_TYPE_CONFLUENCE:
				__nodeType = "Confluence";	break;
			case NODE_TYPE_ISF:
				__nodeType = "Instream Flow";	break;
			case NODE_TYPE_RES:
				__nodeType = "Reservoir";	break;
			case NODE_TYPE_IMPORT:
				__nodeType = "Import";		break;
			case NODE_TYPE_BASEFLOW:
				__nodeType = "Baseflow";	break;
			case NODE_TYPE_END:
				__nodeType = "End";		break;
			case NODE_TYPE_OTHER:
				__nodeType = "Other";		break;
			case NODE_TYPE_UNKNOWN:
				__nodeType = "Unknown";		break;
			case NODE_TYPE_STREAM:
				__nodeType = "StreamTop";	break;
			case NODE_TYPE_LABEL:
				__nodeType = "Label";		break;
			case NODE_TYPE_WELL:
				__nodeType = "Well";		break;
			case NODE_TYPE_XCONFLUENCE:
				__nodeType = "XConfluence";	break;
			case NODE_TYPE_DIV_AND_WELL:
				__nodeType = "DiversionAndWell";break;
			default:
				Message.printStatus(1, "", "Unrecognized node "
					+ "type: " + __type);
				__nodeType = null;
				break;
		}
	}

	// REVISIT (JTS - 2004-07-12) don't do string comparison.
	if (__symbol == null) {
		GRSymbolType symbolType = GRSymbolType.POLYGON;
		GRColor black = GRColor.black;

		if (__nodeType.equalsIgnoreCase("Reservoir")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.TRIANGLE_RIGHT_FILLED, black, black, ICON_DIAM, ICON_DIAM);
			__symText = null;
		}
		else if (__nodeType.equalsIgnoreCase("Streamflow")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE_FILLED, black, black, ICON_DIAM, ICON_DIAM);
			__symText = null;
		}
		else if (__nodeType.equalsIgnoreCase("Baseflow")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM, ICON_DIAM);
			__symText = "B";
		}
		else if (__nodeType.equalsIgnoreCase("End")) {
			__symbol = new GRSymbol (symbolType, GRSymbolShapeType.X, black, black, ICON_DIAM, ICON_DIAM);
			__secondarySymbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM, ICON_DIAM);
			__symText = null;
		}
		else if (__nodeType.equalsIgnoreCase("Instream Flow")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM, ICON_DIAM);
			__symText = "M";
		}
		else if (__nodeType.equalsIgnoreCase("Import")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM, ICON_DIAM);
			__symText = "I";
		}
		else if (__nodeType.equalsIgnoreCase("DiversionAndWell")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM, ICON_DIAM);
			__symText = "DW";
		}
		else if (__nodeType.equalsIgnoreCase("Well")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM, ICON_DIAM);
			__symText = "W";
		}
		else if (__nodeType.equalsIgnoreCase("Diversion")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM, ICON_DIAM);
			__symText = "D";
		}
		else if (__nodeType.equalsIgnoreCase("Other")) {
			if (__isBaseflow) {
				__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM, ICON_DIAM);
				__symText = "B";
			}
			else if (__isImport) {
				__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM, ICON_DIAM);
				__symText = "I";
			}
			else {
				__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM, ICON_DIAM);
				__symText = "O";
			}
		}
		else if (__nodeType.equalsIgnoreCase("Label")) {
			__symbol = null;
			__symText = null;
		}
		else if (__nodeType.equalsIgnoreCase("Confluence")
			|| __nodeType.equalsIgnoreCase("XConfluence")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE_FILLED, black, black, ICON_DIAM/2, ICON_DIAM/2);
			__symText = null;
			__width /= 2;
			__height /= 2;
		}
		else {
			Message.printWarning(2, "", "Unknown how to draw " + __nodeType);
			__symbol = null;
			__symText = null;
		}
	}

	String label = __label;

	if (__symbol != null) {
		//double width = ICON_DIAM;
		double height = ICON_DIAM;

		if (__label != null) {
			GRLimits limits = GRDrawingAreaUtil.getTextExtents(da, label, GRUnits.DEVICE);
			if (limits.getHeight() > height) {
				height = limits.getHeight();
			}
			//width += 4 + limits.getWidth();
		}
	}

	__boundsCalculated = true;
}

/**
Calculates bounds for nodes in the WIS Network display.
@param da the drawing area on which to calculate bounds.
*/
private void calculateWISBounds(GRJComponentDrawingArea da) {
	if (__symbol == null) {
		GRSymbolType symbolType = GRSymbolType.POLYGON;
		GRColor black = GRColor.black;

		// The secondary symbol is used to blank out the area behind the primary symbol's node icon.

		if (__nodeType.equalsIgnoreCase("Reservoir")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.TRIANGLE_RIGHT, black, black, ICON_DIAM*2/3, ICON_DIAM*2/3);
			__secondarySymbol = new GRSymbol(symbolType, GRSymbolShapeType.TRIANGLE_RIGHT_FILLED, black, black, ICON_DIAM*2/3, ICON_DIAM*2/3);
			__symText = null;
		}
		else if (__nodeType.equalsIgnoreCase("Stream")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM*2/3, ICON_DIAM*2/3);
			__secondarySymbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE_FILLED, black, black, ICON_DIAM*2/3, ICON_DIAM*2/3);
			__symText = "X";
		}
		else if (__nodeType.equalsIgnoreCase("Confluence")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM*2/3, ICON_DIAM*2/3);
			__secondarySymbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE_FILLED, black, black, ICON_DIAM*2/3,
				ICON_DIAM*2/3);
			__symText = "C";
		}
		else if (__nodeType.equalsIgnoreCase("Station")
			|| __nodeType.equalsIgnoreCase("Streamflow")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM*2/3, ICON_DIAM*2/3);
			__secondarySymbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE_FILLED, black, black, ICON_DIAM*2/3,
				ICON_DIAM*2/3);
			__symText = "B";
		}
		else if (__nodeType.equalsIgnoreCase("Diversion")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM*2/3, ICON_DIAM*2/3);
			__secondarySymbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE_FILLED, black, black, ICON_DIAM*2/3,
				ICON_DIAM*2/3);
			__symText = "D";
		}
		else if (__nodeType.equalsIgnoreCase("MinFlow")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM*2/3, ICON_DIAM*2/3);
			__secondarySymbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE_FILLED, black, black, ICON_DIAM*2/3, ICON_DIAM*2/3);
			__symText = "M";
		}
		else if (__nodeType.equalsIgnoreCase("Other")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE, black, black, ICON_DIAM*2/3, ICON_DIAM*2/3);
			__secondarySymbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE_FILLED, black, black, ICON_DIAM*2/3, ICON_DIAM*2/3);
			__symText = "O";
		}
		else if (__nodeType.equalsIgnoreCase("End")) {
			__symbol = new GRSymbol(symbolType, GRSymbolShapeType.CIRCLE_FILLED, black, black, ICON_DIAM*2/3, ICON_DIAM*2/3);
			__symText = null;
		}
		else {
			Message.printStatus(1, "", "Unknown node type: "
				+ __nodeType);
		}
	}

	GRLimits data = da.getDataLimits();
	// REVISIT (JTS - 2004-05-19) The following will need changed if we move from a scrollable area to a jumping area.
	GRLimits draw = da.getDrawingLimits();

	// Calculate the multiplier necessary to convert data units to device units.
	double mod = 1;
	if (draw.getWidth() > draw.getHeight()) {
		mod = data.getWidth() / draw.getWidth();
	}
	else {
		mod = data.getHeight() / draw.getHeight();
	}

	__height = ICON_DIAM*2/3 * mod;
	__width = ICON_DIAM*2/3 * mod;

	__boundsCalculated = true;
}

/**
Clears the upstream node ids stored in the __upstreamNodeIDs Vector.  The list is set to null.
*/
public void clearUpstreamNodeIDs() {
	__upstreamNodeIDs = null;
}

/**
Checks to see if the specified point is contained in the area of the node symbol.
Only checks the symbol of the node along with a tiny area outside the symbol as well.
@return true if the point is contained, false if not.
*/
public boolean contains(double x, double y) {
	if ((x >= __x - 2 - (__width / 2)) && (x <= (__x + (__width / 2 )+ 2))){
		if ((y >= __y - 2 - (__width/2))
		    && (y <= (__y + (__height / 2) + 2))) {
			return true;
		}
	}
	return false;
}

/**
Break the link with an upstream node.
@param upstream_node Upstream node to disconnect from the network.
@return true if successful, false if not.
*/
public boolean deleteUpstreamNode(HydroBase_Node upstream_node) {
	String routine = __CLASS + ".deleteUpstreamNode";

	// Find a matching node.  Just check addresses.
	try {

	for (int i = 0; i < __upstream.size(); i++) {
		if (upstream_node.equals(
			(HydroBase_Node)__upstream.get(i))) {
			// We have found a match.  Delete the element.
			__upstream.remove(i);
			return true;
		}
	}
	return false;

	}
	catch (Exception e) {
		Message.printWarning(2,routine,"Error deleting upstream node.");
		return false;
	}
}

/**
Draws this node on the specified drawing area.
@param da the drawing area on which to draw the node.
*/
public void draw(GRJComponentDrawingArea da) {
	if (!__boundsCalculated) {
		calculateBounds(da);
	}

	if (__inWis) {
		drawWIS(da);
	}
	else {
		drawNetwork(da);
	}
}

/**
Draws this node on the network editor display.
@param da the GRJComponentDrawingArea on which to draw the node.
*/
private void drawNetwork(GRJComponentDrawingArea da) {
	String routine = getClass().getSimpleName() + ".drawNetwork";

	double symbolSize = 0;
	GRSymbolShapeType shapeType = GRSymbolShapeType.NONE;

	// if there is a symbol to be drawn.
	if (__symbol != null) {
		if (__symbol.getShapeType() == GRSymbolShapeType.CIRCLE) {
			// Fill in the background with white so the node can't be seen through.
			GRDrawingAreaUtil.setColor(da, GRColor.white);
			GRDrawingAreaUtil.drawSymbol(da, GRSymbolShapeType.CIRCLE_FILLED, __x, __y, __symbol.getSize(), GRUnits.DEVICE,0);
		}

		if (__isSelected) {
			GRDrawingAreaUtil.setColor(da, GRColor.cyan);
		}
		else {
			GRDrawingAreaUtil.setColor(da, GRColor.black);
		}

		symbolSize = __symbol.getSize();

		if (__type == NODE_TYPE_RES) {
			int labeldir = getLabelDirection()/10;
			if (labeldir == 1) {
				shapeType = GRSymbolShapeType.TRIANGLE_UP_FILLED;
			}
			else if (labeldir == 2) {
				shapeType = GRSymbolShapeType.TRIANGLE_DOWN_FILLED;
			}
			else if (labeldir == 3) {
				shapeType = GRSymbolShapeType.TRIANGLE_LEFT_FILLED;
			}
			else if (labeldir == 4) {
				shapeType = GRSymbolShapeType.TRIANGLE_RIGHT_FILLED;
			}
			else {
				// Default.
				shapeType = GRSymbolShapeType.TRIANGLE_RIGHT_FILLED;
			}
		}
		else {
			shapeType = __symbol.getShapeType();
		}

	}

	if (__drawText) {
		String label = null;
		int labelPos = 0;
		double labelAngle = 0;
		if (__type == NODE_TYPE_BLANK
		    || (__type == NODE_TYPE_CONFLUENCE)
		    || __type == NODE_TYPE_XCONFLUENCE) {
		    	// Do nothing.
		}
		else if (__type == NODE_TYPE_DIV
		    || __type == NODE_TYPE_DIV_AND_WELL
		    || __type == NODE_TYPE_WELL
		    || __type == NODE_TYPE_IMPORT
		    || __type == NODE_TYPE_FLOW
		    || __type == NODE_TYPE_END
		    || __type == NODE_TYPE_BASEFLOW
		    || __type == NODE_TYPE_ISF
		    || __type == NODE_TYPE_OTHER
		    || __type == NODE_TYPE_RES) {
			label = getNodeLabel( HydroBase_NodeNetwork.LABEL_NODES_COMMONID);
		}
		else {
			Message.printWarning(2, routine, "No text specified!");
			label = "";
		}

		int dir = getLabelDirection();
		dir = dir % 10;

		if (dir == 1) {
			labelPos = GRText.BOTTOM | GRText.CENTER_X;
		}
		else if (dir == 7) {
			labelPos = GRText.BOTTOM | GRText.LEFT;
		}
		else if (dir == 4) {
			labelPos = GRText.LEFT | GRText.CENTER_Y;
		}
		else if (dir == 8) {
			labelPos = GRText.LEFT | GRText.TOP;
		}
		else if (dir == 2) {
			labelPos = GRText.TOP | GRText.CENTER_X;
		}
		else if (dir == 5) {
			labelPos = GRText.TOP | GRText.RIGHT;
		}
		else if (dir == 3) {
			labelPos = GRText.RIGHT | GRText.CENTER_Y;
		}
		else if (dir == 6) {
			labelPos = GRText.BOTTOM | GRText.RIGHT;
		}
		else if (dir == 9) {
			labelPos = GRText.CENTER_X | GRText.CENTER_Y;
		}
		else {
			labelPos = GRText.TOP | GRText.CENTER_X;
		}

		if (label == null) {
			label = "";
		}

		if (getIsBaseflow()) {
			GRDrawingAreaUtil.drawSymbolText(da, GRSymbolShapeType.CIRCLE,
				__x, __y, symbolSize + BASEFLOW_DIAM, label,
				labelAngle, labelPos, GRUnits.DEVICE, 0);
		}
		else {
			GRDrawingAreaUtil.drawSymbolText(da, shapeType, __x, __y,
				symbolSize, label, labelAngle, labelPos,
				GRUnits.DEVICE, 0);
		}
	}

	if (__drawText && !getIsBaseflow()) {
		// Do nothing.
	}
	else {
		GRDrawingAreaUtil.drawSymbol(da, shapeType,
			__x, __y, symbolSize, GRUnits.DEVICE,0);
	}

	if (__symText != null){
		GRDrawingAreaUtil.drawText(da, __symText,
			__x, __y, 0,
			GRText.CENTER_Y | GRText.CENTER_X);
	}


	if (!__drawText && getIsBaseflow()) {
		// Draw a circle around baseflow nodes.
		GRDrawingAreaUtil.drawSymbol(da, GRSymbolShapeType.CIRCLE,
			__x, __y, __symbol.getSize() + BASEFLOW_DIAM,
			GRUnits.DEVICE, 0);
	}

	if (__secondarySymbol != null) {
		GRDrawingAreaUtil.drawSymbol(da, __secondarySymbol.getShapeType(),
			__x, __y, __secondarySymbol.getSize(),GRUnits.DEVICE,0);
	}
}

/**
Draws this node for the WIS network display.
@param da the GRJComponentDrawingArea on which to draw the node.
*/
private void drawWIS(GRJComponentDrawingArea da) {
	// Format the label that accompanies the text.
	String label = __label;
	if (__showDeliveryFlow) {
		// label += " DF: " + StringUtil.formatString(__deliveryFlow, "%10.1f").trim();
	}
	if (__showNaturalFlow) {
		// label += " NF: " + StringUtil.formatString(__naturalFlow, "%10.1f").trim();
	}
	if (__showPointFlow) {
		double pf = __pointFlow;
		if (!DMIUtil.isMissing(pf)) {
			label += " PF: "
				+ StringUtil.formatString(pf, "%10.1f").trim();
		}
	}
	if (__showCalls) {
		if (!DMIUtil.isMissing(__call)) {
			label += __call;
		}
	}
	if (__showRights) {
		if (!DMIUtil.isMissing(__right)) {
			label += __right;
		}
	}

	if (__symbol != null) {
		GRDrawingAreaUtil.setColor(da, GRColor.white);
		GRDrawingAreaUtil.drawSymbol(da, __secondarySymbol.getShapeType(),
			__x, __y, __secondarySymbol.getSize(), GRUnits.DEVICE,
			GRSymbolPosition.CENTER_X | GRSymbolPosition.CENTER_Y );

		// Actually stored in the node with a +1 modifier, necessary to ensure some backwards compatibility.
		int dir = getLabelDirection() - 1;
		if (getType() == NODE_TYPE_RES) {
			dir = dir % 10;
		}

		int labelPos = GRText.TOP;

		if (dir == 0) {	// Above.
			labelPos = GRText.BOTTOM | GRText.CENTER_X;
		}
		else if (dir == 1) { // Below.
			labelPos = GRText.TOP | GRText.CENTER_X;
		}
		else if (dir == 2) { // Center.
			labelPos = GRText.CENTER_X | GRText.CENTER_Y;
		}
		else if (dir == 3) { // Left.
			labelPos = GRText.RIGHT | GRText.CENTER_Y;
		}
		else if (dir == 4) { // Lower left.
			labelPos = GRText.RIGHT | GRText.TOP;
		}
		else if (dir == 5) { // lower right.
			labelPos = GRText.LEFT | GRText.TOP;
		}
		else if (dir == 6) { // Right.
			labelPos = GRText.LEFT | GRText.CENTER_Y;
		}
		else if (dir == 7) { // Upper left.
			labelPos = GRText.RIGHT | GRText.BOTTOM;
		}
		else if (dir == 8) { // Upper right
			labelPos = GRText.LEFT | GRText.BOTTOM;
		}

		GRDrawingAreaUtil.setColor(da, GRColor.black);
		GRDrawingAreaUtil.drawSymbolText(da, __symbol.getShapeType(),
			__x, __y, __symbol.getSize(),
			label, GRColor.black, 0, labelPos, GRUnits.DEVICE,
			GRSymbolPosition.CENTER_X | GRSymbolPosition.CENTER_Y );
	}

	if (__symText != null) {
		GRDrawingAreaUtil.drawText(da, __symText,
			__x, __y, 0,
			GRText.CENTER_Y | GRText.CENTER_X);
	}
}

/**
Return a label to use for the specified node.
@param lt Label type(see HydroBase_NodeNetwork.LABEL_NODES_*).
@return a label to use for the specified node.
*/
protected String getNodeLabel(int lt) {
	String label = null;
	if (lt == HydroBase_NodeNetwork.LABEL_NODES_AREA_PRECIP) {
		label = getAreaString() + "*" + getPrecipString();
	}
	else if (lt == HydroBase_NodeNetwork.LABEL_NODES_COMMONID) {
		label = getCommonID();
	}
	else if (lt == HydroBase_NodeNetwork.LABEL_NODES_PF) {
		if (isBaseflow() && (getType() != HydroBase_Node.NODE_TYPE_FLOW)) {
			label = StringUtil.formatString( getProrationFactor(), "%5.3f");
		}
		else {
			label = "";
		}
	}
	else if (lt == HydroBase_NodeNetwork.LABEL_NODES_RIVERNODE) {
		label = getRiverNodeID();
	}
	else if (lt == HydroBase_NodeNetwork.LABEL_NODES_WATER) {
		label = getWaterString();
	}
	else {
		label = getNetID();
	}
	return label;
}

/**
Returns the makenet area.  Makenet-specific.
@return the makenet area.
*/
public double getArea() {
	return __area;
}

/**
Returns the makenet area as a String.  Makenet-specific.
@return the makenet area as a String.
*/
public String getAreaString() {
	return __areaString;
}

/**
Returns the object associated with a node.
@return the object associated with a node.
*/
public Object getAssociatedObject() {
	return __associatedObject;
}

/**
Returns the node common id.
@return the node common id.
*/
public String getCommonID() {
	return __commonID;
}

/**
Returns the computational order of nodes.
@return the computational order of nodes.
*/
public int getComputationalOrder() {
	return __computationalOrder;
}

/**
Returns the diameter of the icon drawn for this node.  Used by the network plotting tools.
REVISIT (JTS - 2004-05-20) might not be necessary -- many other methods -- would they work?
@return the diameter of the icon drawn for this node.
*/
public double getDataDiameter() {
	return __width;
}

/**
Returns the delivery flow.
@return the delivery flow.
*/
public double getDeliveryFlow() {
	return __deliveryFlow;
}

/**
Returns the node description.
@return the node description.
*/
public String getDescription() {
	return __desc;
}

/**
Returns the UTM X value for the node's structure as stored in the database.
@return the UTM X value for the node's structure as stored in the database.
*/
public double getDBX() {
	return __dbX;
}

/**
Returns the UTM Y value for the node's structure as stored in the database.
@return the UTM Y value for the node's structure as stored in the database.
*/
public double getDBY() {
	return __dbY;
}

/**
Returns the node immediately downstream from this node.
@return the node immediately downstream from this node.
*/
public HydroBase_Node getDownstreamNode() {
	return __downstream;
}

/**
Returns the id of the node immediately downstream from this node.
Used by the network drawing code.
This method only returns the id stored in the __downstreamNodeID data member.
It doesn't check the downstream node to get the ID.
If setDownstreamNodeID() has not been called first, it will return null.
@return the id of the node immediately downstream from this node.
*/
public String getDownstreamNodeID() {
	return __downstreamNodeID;
}

/**
Returns the point at which network connections should be drawn for the node.
Currently only returns the x, y values.
@return the point at which network connections should be drawn for the node.
*/
public double[] getDrawToPoint(GRJComponentDrawingArea da) {
	double[] d = new double[2];
	d[0] = __x;
	d[1] = __y;
	return d;
}

/**
Returns the height of the drawing area occupied by the node.
@return the height of the drawing area occupied by the node.
*/
public double getHeight() {
	return __height;
}

/**
Returns the network node drawer unique node identifier.
@return the network node drawer unique node identifier.
*/
public String getIdentifier() {
	return __identifier;
}

/**
Returns whether this node is a baseflow node or not.  Makenet-specific.
@return whether this node is a baseflow node or not.
*/
public boolean getIsBaseflow() {
	return __isBaseflow;
}

/**
Returns whether this node is an import node or not.  Makenet-specific.
@return whether this node is an import node or not.
*/
public boolean getIsImport() {
	return __isImport;
}

/**
Returns whether this node is a dry river or not.  WIS-specific.
@return whether this node is a dry river or not.
*/
public boolean getIsDryRiver() {
	return __isDryRiver;
}

/**
Returns the label drawn for this node.
@return the label drawn for this node.
*/
public String getLabel() {
	if (__label == null || __label.trim().equals("")) {
		return "";
	}
	return __label;
}

/**
Returns the angle at which to print the label.  Makenet-specific.
@return the angle at which to print the label.
*/
public double getLabelAngle() {
	return __labelAngle;
}

/**
Returns the direction to print the label at.  Makenet-specific.
@return the direction to print the label at.
*/
public int getLabelDirection() {
	return __labelDir;
}

/**
Returns the limits of the area occupied by the drawn node.
The limits only encompass the area occupied by the node icon.
@return the limits of the area occupied by the drawn node.
*/
public GRLimits getLimits() {
	return new GRLimits(__x - (__width / 2), __y - (__height / 2),
		__x + (__width / 2), __y + (__height / 2));
}

/**
Returns the link data for confluences.
@return the link data for confluences.
*/
public long getLink() {
	return __link;
}

/**
Returns the natural flow.
@return the natural flow.
*/
public double getNaturalFlow() {
	return __naturalFlow;
}

/**
Returns the ID from the net file.  Makenet-specific.
@return the ID from the net file.
*/
public String getNetID() {
	return __netID;
}

/**
Returns the node number in the reach.
@return the node number in the reach.
*/
public int getNodeInReachNumber() {
	return __nodeInReachNum;
}

/**
Returns the type of node this is.
@return the type of node this is.
*/
public String getNodeType() {
	return __nodeType;
}

/**
Returns the number of upstream nodes.
@return the number of upstream nodes.
*/
public int getNumUpstreamNodes() {
	if (__upstream == null) {
		return 0;
	}
	else {
		return __upstream.size();
	}
}

/**
Returns the point flow.
@return the point flow.
*/
public double getPointFlow() {
	return __pointFlow;
}

/**
Returns the precip.  Makenet-specific.
@return the precip.
*/
public double getPrecip() {
	return __precip;
}

/**
Returns the precip as a String.  Makenet-specific.
@return the precip as a String.
*/
public String getPrecipString() {
	return __precipString;
}

/**
Returns the gain proration factor.  Makenet-specific.
@return the gain proration factor.
*/
public double getProrationFactor() {
	return __prorationFactor;
}

/**
Returns the reach number.
@return the reach number.
*/
public int getReachCounter() {
	return __reachCounter;
}

/**
Returns the reach level.
@return the reach level.
*/
public int getReachLevel() {
	return __reachLevel;
}

/**
Returns the river node ID.  Makenet-specific.
@return the river node ID.
*/
public String getRiverNodeID() {
	return __riverNodeID;
}

/**
Returns the river node count.
@return the river node count.
*/
public int getSerial() {
	return __serial;
}

/**
Returns the structure stream mile.  WIS-specific.
@return the structure stream mile.
*/
public double getStreamMile() {
	return __streamMile;
}

/**
Returns the stream num.  WIS-specific.
@return the stream num.
*/
public long getStreamNumber() {
	return __streamNum;
}

/**
Returns the label direction in a format that a proplist will know.
One of the string values from GRText.
This assumes the string format is between 1 and 9.  Any other values will result in "AboveCenter" being returned.
@return the label direction in a string format.
*/
public String getTextPosition() {
	// The text position as compared to the values in GRText is actually stored +1 for some backwards compatibility issues.
	String[] positions = GRText.getTextPositions();
	if (__labelDir <= 0 || __labelDir > 9) {
		return positions[0];
	}
	else {
		return positions[(__labelDir - 1)];
	}
}

/**
Returns the tributary number.
@return the tributary number.
*/
public int getTributaryNumber() {
	return __tributaryNum;
}

/**
Returns the node type.
@return the node type.
*/
public int getType() {
	return __type;
}

/**
Returns the elements in the __upstreamNodeIDs Vector as an array of Strings.
The list contains the IDs of all the nodes immediately upstream from this node.
This is used by network drawing code.
@return a String array of all the ids of nodes immediately upstream from this node.
*/
public String[] getUpstreamNodeIDs() {
	int size = 0;
	if (__upstreamNodeIDs != null) {
		size = __upstreamNodeIDs.size();
	}
	String[] ids = new String[size];
	for (int i = 0; i < size; i++) {
		ids[i] = (String)__upstreamNodeIDs.get(i);
	}
	return ids;
}

/**
Returns the IDs of all the nodes immediately upstream of this node.
Used by drawing code.
This goes to each of the upstream nodes in the __upstream list and pulls out their ID to place in the String.
@return a String array of all the nodes' ids in the order the nodes are found in the __upstream list.
*/
public String[] getUpstreamNodesIDs() {
	if (__upstream == null || __upstream.size() == 0) {
		return new String[0];
	}

	String[] ids = new String[__upstream.size()];
	for (int i = 0; i < __upstream.size(); i++) {
		ids[i] =((HydroBase_Node)__upstream.get(i)).getCommonID();
	}
	return ids;
}

/**
Returns the user description.  Makenet-specific.
@return the user description.
*/
public String getUserDescription() {
	return __userDesc;
}

/**
Returns the String node type from the integer type.
@param type the type of the node.
@param flag if FULL, return the full String (used in the network file).
If ABBREVIATION, return the 3 letter abbreviation (used in StateMod station names).
@return the String node type from the integer type.
*/
public static String getTypeString(int type, int flag) {
	String stype = "";

	if (flag == ABBREVIATION) {
		// Abbreviations.
		if (type == NODE_TYPE_BLANK) {
			stype = "BLK";
		}
		else if (type == NODE_TYPE_DIV) {
			stype = "DIV";
		}
		else if (type == NODE_TYPE_DIV_AND_WELL) {
			stype = "D&W";
		}
		else if (type == NODE_TYPE_WELL) {
			stype = "WEL";
		}
		else if (type == NODE_TYPE_FLOW) {
			stype = "FLO";
		}
		else if (type == NODE_TYPE_CONFLUENCE) {
			stype = "CON";
		}
		else if (type == NODE_TYPE_ISF) {
			stype = "ISF";
		}
		else if (type == NODE_TYPE_RES) {
			stype = "RES";
		}
		else if (type == NODE_TYPE_IMPORT) {
			stype = "IMP";
		}
		else if (type == NODE_TYPE_BASEFLOW) {
			stype = "BFL";
		}
		else if (type == NODE_TYPE_END) {
			stype = "END";
		}
		else if (type == NODE_TYPE_OTHER) {
			stype = "OTH";
		}
		else if (type == NODE_TYPE_UNKNOWN) {
			stype = "UNK";
		}
		else if (type == NODE_TYPE_STREAM) {
			stype = "STR";
		}
		else if (type == NODE_TYPE_LABEL) {
			stype = "LAB";
		}
		else if (type == NODE_TYPE_FORMULA) {
			stype = "FOR";
		}
		else if (type == NODE_TYPE_XCONFLUENCE) {
			stype = "XCN";
		}
		else if (type == NODE_TYPE_LABEL_NODE) {
			stype = "LBN";
		}
	}
	else {
		// Full name.
		if (type == NODE_TYPE_BLANK) {
			stype = "BLANK";
		}
		else if (type == NODE_TYPE_DIV) {
			stype = "DIV";
		}
		else if (type == NODE_TYPE_DIV_AND_WELL) {
			stype = "D&W";
		}
		else if (type == NODE_TYPE_WELL) {
			stype = "WELL";
		}
		else if (type == NODE_TYPE_FLOW) {
			stype = "FLOW";
		}
		else if (type == NODE_TYPE_CONFLUENCE) {
			stype = "CONFL";
		}
		else if (type == NODE_TYPE_ISF) {
			stype = "ISF";
		}
		else if (type == NODE_TYPE_RES) {
			stype = "RES";
		}
		else if (type == NODE_TYPE_IMPORT) {
			stype = "IMPORT";
		}
		else if (type == NODE_TYPE_BASEFLOW) {
			stype = "BFL";
		}
		else if (type == NODE_TYPE_END) {
			stype = "END";
		}
		else if (type == NODE_TYPE_OTHER) {
			stype = "OTH";
		}
		else if (type == NODE_TYPE_UNKNOWN) {
			stype = "UNKNOWN";
		}
		else if (type == NODE_TYPE_STREAM) {
			stype = "STREAM";
		}
		else if (type == NODE_TYPE_LABEL) {
			stype = "LABEL";
		}
		else if (type == NODE_TYPE_FORMULA) {
			stype = "FORMULA";
		}
		else if (type == NODE_TYPE_XCONFLUENCE) {
			stype = "XCONFL";
		}
		else if (type == NODE_TYPE_LABEL_NODE) {
			stype = "LABELNODE";
		}
	}
	return stype;
}

/**
Returns the first upstream node.
@return the reference to the first upstream node or null if not found.
*/
public HydroBase_Node getUpstreamNode() {
	if (__upstream == null) {
		return null;
	}
	// Return the first one.
	return getUpstreamNode(0);
}

/**
Return the upstream node at the specific position.
@param position 0-index position of upstream node.
@return the reference to the upstream node for the specified position, or null if not found.
*/
public HydroBase_Node getUpstreamNode(int position) {
	String routine = __CLASS + ".getUpstreamNode";

	if (__upstream == null) {
		return null;
	}
	if (__upstream.size() < (position + 1)) {
		Message.printWarning(1, routine,
			"Upstream position [" + position + "] is not found(max " + __upstream.size() + ")");
		return null;
	}
	// Return the requested one.
	return(HydroBase_Node)__upstream.get(position);
}

/**
Returns an upstream node given its common id.
@param commonID the commonID for which to find an upstream node.
@return an upstream node position given its common ID, or -1 if not found.
*/
public int getUpstreamNodePosition(String commonID) {
	if (__upstream == null) {
		return -1;
	}
	int size = __upstream.size();
	HydroBase_Node upstream;
	for (int i = 0; i < size; i++) {
		// Return the first one that matches.
		upstream = (HydroBase_Node)__upstream.get(i);
		if (commonID.equalsIgnoreCase(upstream.getCommonID())) {
			return i;
		}
	}
	return -1;
}

/**
Returns the list of upstream nodes.
@return the list of upstream nodes.
*/
public List<HydroBase_Node> getUpstreamNodes() {
	return __upstream;
}

/**
Returns how upstream nodes are constructed.  See TRIBS_*.
@return how upstream nodes are constructed.
*/
public int getUpstreamOrder() {
	return __upstreamOrder;
}

/**
Returns the verbose node type description string.
@param type the type of node for which to return the verbose type.
@return the verbose node type description string.
*/
public static String getVerboseType(int type) {
	String stype = "";
	// Abbreviations.
	if (type == NODE_TYPE_BLANK) {
		stype = "Blank";
	}
	else if (type == NODE_TYPE_DIV) {
		stype = "Diversion";
	}
	else if (type == NODE_TYPE_DIV_AND_WELL) {
		stype = "Diversion and Well";
	}
	else if (type == NODE_TYPE_WELL) {
		stype = "Well";
	}
	else if (type == NODE_TYPE_FLOW) {
		stype = "Streamflow";
	}
	else if (type == NODE_TYPE_CONFLUENCE) {
		stype = "Confluence";
	}
	else if (type == NODE_TYPE_ISF) {
		stype = "Instream Flow";
	}
	else if (type == NODE_TYPE_RES) {
		stype = "Reservoir";
	}
	else if (type == NODE_TYPE_IMPORT) {
		stype = "Import";
	}
	else if (type == NODE_TYPE_BASEFLOW) {
		stype = "Baseflow";
	}
	else if (type == NODE_TYPE_END) {
		stype = "End";
	}
	else if (type == NODE_TYPE_OTHER) {
		stype = "Other";
	}
	else if (type == NODE_TYPE_UNKNOWN) {
		stype = "Uknown";
	}
	else if (type == NODE_TYPE_STREAM) {
		stype = "Stream";
	}
	else if (type == NODE_TYPE_LABEL) {
		stype = "Label";
	}
	else if (type == NODE_TYPE_FORMULA) {
		stype = "Formula";
	}
	else if (type == NODE_TYPE_XCONFLUENCE) {
		stype = "XConfluence";
	}
	else if (type == NODE_TYPE_LABEL_NODE) {
		stype = "LabelNode";
	}
	return stype;
}

/**
Returns the verbose node type description string.
@param type the type of node for which to return the verbose type.
@return the verbose node type description string.
*/
public static String getVerboseWISType(int type) {
	String stype = "";
	// Abbreviations.
	if (type == NODE_TYPE_BLANK) {
		stype = "Blank";
	}
	else if (type == NODE_TYPE_DIV) {
		stype = "Diversion";
	}
	else if (type == NODE_TYPE_DIV_AND_WELL) {
		stype = "Diversion and Well";
	}
	else if (type == NODE_TYPE_WELL) {
		stype = "Well";
	}
	else if (type == NODE_TYPE_FLOW) {
		stype = "Station";
	}
	else if (type == NODE_TYPE_CONFLUENCE) {
		stype = "Confluence";
	}
	else if (type == NODE_TYPE_ISF) {
		stype = "Instream Flow";
	}
	else if (type == NODE_TYPE_RES) {
		stype = "Reservoir";
	}
	else if (type == NODE_TYPE_IMPORT) {
		stype = "Import";
	}
	else if (type == NODE_TYPE_BASEFLOW) {
		stype = "Baseflow";
	}
	else if (type == NODE_TYPE_END) {
		stype = "End";
	}
	else if (type == NODE_TYPE_OTHER) {
		stype = "Other";
	}
	else if (type == NODE_TYPE_UNKNOWN) {
		stype = "Uknown";
	}
	else if (type == NODE_TYPE_STREAM) {
		stype = "Stream";
	}
	else if (type == NODE_TYPE_LABEL) {
		stype = "Label";
	}
	else if (type == NODE_TYPE_FORMULA) {
		stype = "Formula";
	}
	else if (type == NODE_TYPE_XCONFLUENCE) {
		stype = "XConfluence";
	}
	else if (type == NODE_TYPE_LABEL_NODE) {
		stype = "LabelNode";
	}
	return stype;
}

/**
Returns area * precip as a float.  Makenet-specific.
@return area * precip.
*/
public double getWater() {
	return __water;
}

/**
Returns area * precip as a String.  Makenet-specific.
@return area * precip as a String.
*/
public String getWaterString() {
	return __waterString;
}

/**
Returns the width of the drawing area occupied by this node.
@return the width of the drawing area occupied by this node.
*/
public double getWidth() {
	return __width;
}

/**
Returns the wis format associated with this node.
@return the wis format associated with this node.
*/
public HydroBase_WISFormat getWISFormat() {
	return __wisFormat;
}

/**
Returns the wis num associated with this node.
@return the wis num associated with this node.
*/
public int getWis_num() {
	return __wisNum;
}

/**
Returns the node X coordinate.  Makenet-specific.
@return the node X coordinate.
*/
public double getX() {
	return __x;
}

/**
Returns the node Y coordinate.  Makenet-specific.
@return the node Y coordinate.
*/
public double getY() {
	return __y;
}

/**
Inserts an upstream node into the Vector of upstream nodes.
Used by the network diagramming tools.
@param node the node to insert.
@param pos the position at which to insert the node.
*/
public void insertUpstreamNode(HydroBase_Node node, int pos) {
	__upstream.add(pos,node);
}

/**
Inserts multiple nodes into the list of upstream nodes.
Used by network diagramming tools.
@param nodes a non-null list of nodes to be inserted upstream of this node.
@param pos the position at which to insert the nodes.
*/
public void insertUpstreamNodes(List<HydroBase_Node> nodes, int pos) {
	for (int i = nodes.size() - 1; i >= 0; i--) {
		__upstream.add(pos,nodes.get(i));
	}
}

/**
Initialize data members.
*/
private void initialize() {
	__areaString = 		"";
	__area = 		0.0;
	__precipString = 	"";
	__precip = 		0.0;
	__waterString = 	"";
	__water = 		0.0;
	__prorationFactor = 	0.0;
	__x = 			0.0;
	__y = 			0.0;
	__labelAngle = 		45;

	__desc = 		"";
	__userDesc = 		"";

	__commonID = 		"";
	__netID = 		"";
	__riverNodeID = 	"";
	__isBaseflow = 	false;
	__labelDir = 		1;
	__serial = 		0;
	__computationalOrder = 	-1;
	__type = 		NODE_TYPE_BLANK;
	__tributaryNum = 	1;
	__reachCounter =	 0;
	__reachLevel = 		0;
	__nodeInReachNum =	 1;
	__downstream = 		null;
	__upstream = 		null;
	__upstreamOrder = 	TRIBS_ADDED_FIRST;

	// WIS data.
	__streamMile = 		DMIUtil.MISSING_DOUBLE;
	__streamNum = 		DMIUtil.MISSING_LONG;
	__wisFormat = 		null;
	__link = 		DMIUtil.MISSING_LONG;
	__isDryRiver = 		false;
}

/**
Equivalent to getIsBaseflow().
*/
public boolean isBaseflow() {
	return __isBaseflow;
}

/**
Equivalent to getIsImport().
*/
public boolean isImport() {
	return __isImport;
}

/**
Equivalent to getIsDryRiver().
*/
public boolean isDryRiver() {
	return __isDryRiver;
}

/**
Returns whether this node was read from the database or generated from a
network during the latest invocation of the network drawing code.
@return whether this node was read from the database or not.
*/
public boolean isReadFromDB() {
	return __readFromDB;
}

/**
Returns whether this node was selected on the network diagram.
@return whether this node was selected on the network diagram.
*/
public boolean isSelected() {
	return __isSelected;
}

/**
Returns whether this node is visible or not.
@return whether this node is visible or not.
*/
public boolean isVisible() {
	return __visible;
}

/**
Returns whether this node is within the specified limits or not.
@param limits the limits to check
@param scale the scale value of the drawing area
@return true if the node is within the limits, false if not.
*/
public boolean isWithinLimits(GRLimits limits, double scale) {
	return limits.contains(__x * scale, __y * scale,
		(__x + __width) * scale, (__y + __height) * scale, false);
}

/**
Lookup the type from a string.
The string can be any recognized full or abbreviated string used for the network or CWRAT.
It can also be the abbreviated string used in StateMod station names and node diagram.
@return the integer node type or -1 if not recognized.
*/
public static int lookupType(String type){
	if (type.equalsIgnoreCase("BFL")) {
		return NODE_TYPE_BASEFLOW;
	}
	else if (type.equalsIgnoreCase("BLANK")
		|| type.equalsIgnoreCase("BLK")) {
		return NODE_TYPE_BLANK;
	}
	else if (type.equalsIgnoreCase("confluence")
		|| type.equalsIgnoreCase("CON")) {
		return NODE_TYPE_CONFLUENCE;
	}
	else if (type.equalsIgnoreCase("diversion")
		|| type.equalsIgnoreCase("DIV")) {
		return NODE_TYPE_DIV;
	}
	else if (type.equalsIgnoreCase("D&W")
		|| type.equalsIgnoreCase("DW")) {
		return NODE_TYPE_DIV_AND_WELL;
	}
	else if (type.equalsIgnoreCase("END")) {
		return NODE_TYPE_END;
	}
	else if (type.equalsIgnoreCase("FLOW")
		|| type.equalsIgnoreCase("FLO")) {
		return NODE_TYPE_FLOW;
	}
	else if (type.equalsIgnoreCase("formula")) {
		return NODE_TYPE_FORMULA;
	}
	else if (type.equalsIgnoreCase("IMP")) {
		return NODE_TYPE_IMPORT;
	}
	else if (type.equalsIgnoreCase("ISF")) {
		return NODE_TYPE_ISF;
	}
	else if (type.equalsIgnoreCase("minflow")
		|| type.equalsIgnoreCase("ISF")) {
		return NODE_TYPE_ISF;
	}
	else if (type.equalsIgnoreCase("other")
		|| type.equalsIgnoreCase("OTH")) {
		return NODE_TYPE_OTHER;
	}
	else if (type.equalsIgnoreCase("reservoir")
		|| type.equalsIgnoreCase("RES")) {
		return NODE_TYPE_RES;
	}
	else if (type.equalsIgnoreCase("station")
		|| type.equalsIgnoreCase("FLO")) {
		return NODE_TYPE_FLOW;
	}
	else if (type.equalsIgnoreCase("stream")
		|| type.equalsIgnoreCase("STR")) {
		return NODE_TYPE_STREAM;
	}
	else if (type.equalsIgnoreCase("string")) {
		return NODE_TYPE_LABEL;
	}
	else if (type.equalsIgnoreCase("UNKNOWN")) {
		return NODE_TYPE_UNKNOWN;
	}
	else if (type.equalsIgnoreCase("well")
		|| type.equalsIgnoreCase("WEL")) {
		return NODE_TYPE_WELL;
	}
	else if (type.equalsIgnoreCase("XCONFL")
		|| type.equalsIgnoreCase("XCN")) {
		return NODE_TYPE_XCONFLUENCE;
	}
	else if (type.equalsIgnoreCase("LabelNode")) {
		return NODE_TYPE_LABEL_NODE;
	}
	else {
		return -1;
	}
}

/**
Reset some internal node things when reusing a node (for use in the network editor layout).
@param type the new type of the node.
*/
public void newNode(int type) {
	__type = type;
	__nodeType = null;
	__symbol = null;
	__boundsCalculated = false;
	__isBaseflow = false;
	__isImport = false;
}

/**
Breaks apart the proration information and saves in the node.
@param string0 the input string.  Assumed to be either xxx*yyy or xxxx.
@return true if successful, false if not.
*/
public boolean parseAreaPrecip(String string0) {
	String routine = __CLASS + ".getNodeAreaPrecip";

	// If the proration information is empty, use empty strings for other parts.
	try {
	setAreaString("");
	setPrecipString("");
	setWaterString("");

	if (string0 == null) {
		return true;
	}
	if (string0.length() == 0) {
		return true;
	}

	String string = string0;
	char theOperator = '\0';
	int length = string.length();
	int nfields = 1;
	for (int i = 0; i < length; i++) {
		if (	(string.charAt(i) == '-')
			|| (string.charAt(i) == '*')
			|| (string.charAt(i) == '+')
			|| (string.charAt(i) == '/')) {
			theOperator = string.charAt(i);
			nfields = 2;
			// Set to a space so that we can parse later.
			string = string.substring(0,i)
				+ " " + string.substring((i + 1));
			if (Message.isDebugOn) {
				Message.printDebug(10, routine, "String=\"" + string + "\"");
			}
		}
	}

	String area = "";
	String precip = "";
	if (nfields == 1) {
		// Just use the original string but set the precipitation to one so that can print it if we want.
		setAreaString(string0);
		setPrecipString("1");
		setWaterString(string0);
		return true;
	}
	else if (nfields == 2) {
		// Assume that we have a valid theOperator and do the math.
		List<String> v = StringUtil.breakStringList(string, " \t", 0);
		area = v.get(0);
		precip = v.get(1);
		double a = (new Double(area)).doubleValue();
		double p = (new Double(precip)).doubleValue();
		double water = 0;
		if (theOperator == '*') {
			water = a*p;
		}
		else if ((theOperator == '/')
			|| (theOperator == '+')
			|| (theOperator == '-')) {
			Message.printWarning(1, routine,
				"Operator " + theOperator
				+ " not allowed - use * for proration");
			return false;
			//water = a/p;
			//water = a + p;
			//water = a - p;
		}
		else {
			Message.printWarning(1, routine,
				"String \"" + string0
				+ "\" cannot be converted to proration");
			return false;
		}
		// Now save values in the node in both string and floating point form.
		setAreaString(area);
		setPrecipString(precip);
		setWaterString(StringUtil.formatString(water, "%12.2f"));
		setArea((new Double(area)).doubleValue());
		setPrecip((new Double(precip)).doubleValue());
		setWater(water);
		if (getWater() > 0.0) {
			// This is a baseflow node.
			setIsBaseflow(true);
		}
		return true;
	}

	Message.printWarning(1, routine, "String \"" + string0 + "\" cannot be converted to proration");
	return false;

	}
	catch (Exception e) {
		Message.printWarning(1, routine, "String \"" + string0 + "\" cannot be converted to proration");
		Message.printWarning(2, routine, e);
		return false;
	}
}

/**
Removes a node from the upstream of this node.  Used by the network drawing code.
@param pos the position in the __upstream Vector of the node to be removed.
*/
public void removeUpstreamNode(int pos) {
	__upstream.remove(pos);
}

/**
Replaces one of this node's upstream nodes with another node.  Used by the network drawing code.
@param node the node to replace the upstream node with.
@param pos the position in the __upstream Vector of the node to be replaced.
*/
public void replaceUpstreamNode(HydroBase_Node node, int pos) {
	__upstream.set(pos,node);
}

/**
Sets the makenet area.
@param area value to put in the makenet area.
*/
public void setArea(double area) {
	__area = area;
}

/**
Sets the makenet area as a String.
@param areaString value to put into the makenet area String.
*/
public void setAreaString(String areaString) {
	if (areaString != null) {
		__areaString = areaString;
	}
}

/**
Sets the object associated with this node.
@param o the object associated with the node.
*/
public void setAssociatedObject(Object o) {
	__associatedObject = o;
}

/**
Sets whether the bounds have been calculated or not.
If set to false, then the next time the node is drawn it will recalculate all its bounds.
@param calculated whether the drawing bounds have been calculated or not.
*/
public void setBoundsCalculated(boolean calculated) {
	__boundsCalculated = calculated;
}

/**
Sets the call information to be displayed by the node label.
@param call the information to display.
*/
public void setCall(String call) {
	__call = call;
}

/**
Sets the node's common id.
@param commonid the value to put into the node's common id.
*/
public void setCommonID(String commonid) {
	if (commonid != null) {
		__commonID = commonid;
	}
}

/**
Sets the computational order of nodes.
@param computationalOrder the value to set the computation order to.
*/
public void setComputationalOrder(int computationalOrder) {
	__computationalOrder = computationalOrder;
}

/**
Sets the diameter of the node in data units (for use in determining what points are contained).
@param diam the diameter of the node in data units.
*/
public void setDataDiameter(double diam) {
	__width = diam;
	__height = diam;
}

/**
Sets the UTM X value stored in the database.
@param x the x value to set.
*/
public void setDBX(double x) {
	__dbX = x;
}

/**
Sets the UTM Y value stored in the database.
@param y the y value to set.
*/
public void setDBY(double y) {
	__dbY = y;
}

/**
Sets the delivery flow.
@param deliveryFlow value to put in the delivery flow.
*/
public void setDeliveryFlow(double deliveryFlow) {
	__deliveryFlow = deliveryFlow;
}

/**
Sets the description based on the node type.
*/
public void setDescription() {
	setDescription(getTypeString(__type,1));
}

/**
Sets the node description.
@param desc the node description to store in the node.
*/
public void setDescription(String desc) {
	if (desc != null) {
		__desc = desc;
	}
}

/**
Sets the node immediately downstream from this node.
@param downstream the node immediately downstream from this node.
*/
public void setDownstreamNode(HydroBase_Node downstream) {
	__downstream = downstream;
}

/**
Sets the id of the node downstream from this node.  Used in the network drawing code.
@param id the id of the node downstream from this node.
*/
public void setDownstreamNodeID(String id) {
	__downstreamNodeID = id;
}

/**
Sets whether text labels should be drawn along with the node in the network editor.
@param drawText whether to draw text labels.
*/
public static void setDrawText(boolean drawText) {
	__drawText = drawText;
}

/**
Sets the icon diameter.  The baseflow icon diameter will be computed accordingly.
@param size size of the icon diameter in pixels.
*/
public static void setIconDiam(int size) {
	ICON_DIAM = size;
	int third = ICON_DIAM / 3;
	if ((third % 2) == 1) {
		third++;
	}
	BASEFLOW_DIAM = third;
}

/**
Sets the node drawing code identifier for the node.
@param identifier the identifier to set.
*/
public void setIdentifier(String identifier) {
	__identifier = identifier;
}

/**
Set whether the node is being drawn in the WIS network display.
@param inWis whether the node is being drawn in the WIS network display.
*/
public void setInWis(boolean inWis) {
	__inWis = inWis;
}

public void setInWIS(boolean inWis) {
	__inWis = inWis;
}

/**
Sets whether this node is a baseflow node or not.  Makenet-specific.
@param isBaseflow whether this node is a baseflow node or not.
*/
public void setIsBaseflow(boolean isBaseflow) {
	__isBaseflow = isBaseflow;
}

/**
Sets whether this node is an import node or not.
@param isImport whether this node is an import node or not.
*/
public void setIsImport(boolean isImport) {
	__isImport = isImport;
}

/**
Sets whether this node is a dry river or not.  WIS-specific.
@param isDryRiver whether this node is a dry river or not.
*/
public void setIsDryRiver(boolean isDryRiver) {
	__isDryRiver = isDryRiver;
}

/**
Sets the node label.
@param label the label to set.
*/
public void setLabel(String label) {
	__label = label;
}

/**
Sets the angle to print the label at.  Makenet-specific.
@param labelAngle the angle to print the label at.
*/
public void setLabelAngle(double labelAngle) {
	__labelAngle = labelAngle;
}

/**
Sets the direction to print the label in.  Makenet-specific.
@param labelDir the direction to print the label in.
*/
public void setLabelDirection(int labelDir) {
	__labelDir = labelDir;
}

/**
Sets the link data for confluences.
@param link the value to set the link data to.
*/
public void setLink(long link) {
	__link = link;
}

/**
Sets the natural flow.
@param naturalFlow value to put into the natural flow.
*/
public void setNaturalFlow(double naturalFlow) {
	__naturalFlow = naturalFlow;
}

/**
Sets the point flow.
@param pointFlow value to put in point flow.
*/
public void setPointFlow(double pointFlow) {
	__pointFlow = pointFlow;
}

/**
Sets the ID from the net file.  Makenet-specific.
@param netid the value to set the net file ID to.
*/
public void setNetID(String netid) {
	if (netid != null) {
		__netID = netid;
	}
}

/**
Sets the node number in the reach.
@param nodeInReachNum the node number in the reach.
*/
public void setNodeInReachNumber(int nodeInReachNum) {
	__nodeInReachNum = nodeInReachNum;
}

/**
Sets the type of node this is.
@param nodeType the type of node this is.
*/
public void setNodeType(String nodeType) {
	__nodeType = nodeType;
}

/**
Sets this node's position on the network diagram.
@param x the x location.
@param y the y location.
@param width the width of the node.
@param height the height of the node.
*/
public void setPosition(double x, double y, double width, double height) {
	__x = x;
	__y = y;
	__width = width;
	__height = height;
}

/**
Sets the precip.  Makenet-specific.
@param precip the precip to set.
*/
public void setPrecip(double precip) {
	__precip = precip;
}

/**
Sets the precip as a String.  Makenet-specific.
@param precipString value to set the precip string to.
*/
public void setPrecipString(String precipString) {
	if (precipString != null) {
		__precipString = precipString;
	}
}

/**
Sets the gain proration factor.  Makenet-specific.
@param prorationFactor value to set the proration factor to.
*/
public void setProrationFactor(double prorationFactor) {
	__prorationFactor = prorationFactor;
}

/**
Sets the reach number.
@param reachCounter value to set the reach number to.
*/
public void setReachCounter(int reachCounter) {
	__reachCounter = reachCounter;
}

/**
Sets the reach level.
@param reachLevel value to set the reach level to.
*/
public void setReachLevel(int reachLevel) {
	__reachLevel = reachLevel;
}

/**
Sets whether this node's data was read from the database or not.
@param read whether this node's data was read from the database.
*/
public void setReadFromDB(boolean read) {
	__readFromDB = read;
}

/**
Sets the right to be show in the node label.
@param right the right information to show in the node label.
*/
public void setRight(String right) {
	__right = right;
}

/**
Sets the river node ID.
@param riverNodeID the value to set the river node ID to.
*/
public void setRiverNodeID(String riverNodeID) {
	if (riverNodeID != null) {
		__riverNodeID = riverNodeID;
	}
}

/**
Sets whether this node was selected on the network diagram.
@param selected whether this node was selected on the network diagram.
*/
public void setSelected(boolean selected) {
	__isSelected = selected;
}

/**
Sets the node count.
@param serial the value to set the node count to.
*/
public void setSerial(int serial) {
	__serial = serial;
}

/**
Sets whether to show call information.
@param showCalls whether to show call information.
*/
public void setShowCalls(boolean showCalls) {
	__showCalls = showCalls;
}

/**
Sets whether to show the delivery flow.
@param showDeliveryFlow whether to show the delivery flow.
*/
public void setShowDeliveryFlow(boolean showDeliveryFlow) {
	__showDeliveryFlow = showDeliveryFlow;
}

/**
Sets whether to show the natural flow.
@param showNaturalFlow whether to show the natural flow.
*/
public void setShowNaturalFlow(boolean showNaturalFlow) {
	__showNaturalFlow = showNaturalFlow;
}

/**
Sets whether to show the point flow.
@param showPointFlow whether to show the point flow.
*/
public void setShowPointFlow(boolean showPointFlow) {
	__showPointFlow = showPointFlow;
}

/**
Sets whether to show right information.
@param showRights whether to show right information.
*/
public void setShowRights(boolean showRights) {
	__showRights = showRights;
}

/**
Sets the structure stream mile.  WIS-specific.
@param streamMile value to set the stream mile to.
*/
public void setStreamMile(double streamMile) {
	__streamMile = streamMile;
}

/**
Sets the stream number.  WIS-specific.
@param streamNumber value to put into the stream number.
*/
public void setStreamNumber(long streamNumber) {
	__streamNum = streamNumber;
}

/**
Sets the symbol to use for the node.
@param symbol the GRSymbol to use.
*/
public void setSymbol(GRSymbol symbol) {
	__symbol = symbol;
}

/**
Sets the tributary number.
@param tributaryNumber value to put into tributary number.
*/
public void setTributaryNumber(int tributaryNumber) {
	__tributaryNum = tributaryNumber;
}

/**
Sets the node type.
@param type value to set the node type to.
*/
public void setType(int type) {
	__type = type;
}

/**
Sets the node type based on the abbreviated String type.
The abbreviated string is used in the StateMod station names and the network diagram.
@param type the abbreviated string specifying what type of node this node is.
*/
public void setTypeAbbreviation(String type) {
	if (type.equalsIgnoreCase("BLK")) {
		setType(NODE_TYPE_BLANK);
	}
	else if (type.equalsIgnoreCase("DIV")) {
		setType(NODE_TYPE_DIV);
	}
	else if (type.equalsIgnoreCase("D&W")
		|| type.equalsIgnoreCase("DW")) {
		setType(NODE_TYPE_DIV_AND_WELL);
	}
	else if (type.equalsIgnoreCase("WEL")) {
		setType(NODE_TYPE_WELL);
	}
	else if (type.equalsIgnoreCase("FLO")) {
		setType(NODE_TYPE_FLOW);
	}
	else if (type.equalsIgnoreCase("CON")) {
		setType(NODE_TYPE_CONFLUENCE);
	}
	else if (type.equalsIgnoreCase("ISF")) {
		setType(NODE_TYPE_ISF);
	}
	else if (type.equalsIgnoreCase("RES")) {
		setType(NODE_TYPE_RES);
	}
	else if (type.equalsIgnoreCase("IMP")) {
		setType(NODE_TYPE_IMPORT);
	}
	else if (type.equalsIgnoreCase("BFL")) {
		setType(NODE_TYPE_BASEFLOW);
	}
	else if (type.equalsIgnoreCase("END")) {
		setType(NODE_TYPE_END);
	}
	else if (type.equalsIgnoreCase("OTH")) {
		setType(NODE_TYPE_OTHER);
	}
	else if (type.equalsIgnoreCase("UNK")) {
		setType(NODE_TYPE_UNKNOWN);
	}
	else if (type.equalsIgnoreCase("STR")) {
		setType(NODE_TYPE_STREAM);
	}
	else if (type.equalsIgnoreCase("LAB")) {
		setType(NODE_TYPE_LABEL);
	}
	else if (type.equalsIgnoreCase("FOR")) {
		setType(NODE_TYPE_FORMULA);
	}
	else if (type.equalsIgnoreCase("XCN")) {
		setType(NODE_TYPE_XCONFLUENCE);
	}
	else if (type.equalsIgnoreCase("LBN")) {
		setType(NODE_TYPE_LABEL_NODE);
	}
}

/**
Sets the list nodes upstream of this node.  Used by the network diagramming tools.
@param v the list of upstream nodes.  If null, then there are no nodes upstream of this node.
*/
public void setUpstreamNodes(List<HydroBase_Node> v) {
	__upstream = v;
}

/**
Sets the user description.  Makenet-specific.
@param desc value to put in the user description.
*/
public void setUserDescription(String desc) {
	if (desc == null) {
		__userDesc = "";
	}
	else {
		__userDesc = desc;
	}
}

/**
Set the type from a string.  This is used in CWRAT WIS.
@param type the type to set.
*/
public void setType(String type) {
	if (type.equalsIgnoreCase("diversion")) {
		setType(NODE_TYPE_DIV);
	}
	else if (type.equalsIgnoreCase("well")) {
		setType(NODE_TYPE_WELL);
	}
	else if (type.equalsIgnoreCase("confluence")) {
		setType(NODE_TYPE_CONFLUENCE);
	}
	else if (type.equalsIgnoreCase("formula")) {
		setType(NODE_TYPE_FORMULA);
	}
	else if (type.equalsIgnoreCase("minflow")) {
		setType(NODE_TYPE_ISF);
	}
	else if (type.equalsIgnoreCase("other")) {
		setType(NODE_TYPE_OTHER);
	}
	else if (type.equalsIgnoreCase("reservoir")) {
		setType(NODE_TYPE_RES);
	}
	else if (type.equalsIgnoreCase("station")) {
		setType(NODE_TYPE_FLOW);
	}
	else if (type.equalsIgnoreCase("stream")) {
		setType(NODE_TYPE_STREAM);
	}
	else if (type.equalsIgnoreCase("string")) {
		setType(NODE_TYPE_LABEL);
	}
	else if (type.equalsIgnoreCase("labelNode")) {
		setType(NODE_TYPE_LABEL_NODE);
	}
}

/**
Sets how upstream nodes are constructed.  See TRIBS_*.
@param upstreamOrder how upstream nodes are constructed.
*/
public void setUpstreamOrder(int upstreamOrder) {
	__upstreamOrder = upstreamOrder;
}

/**
Returns the verbose node type description string.
@param type the type of node for which to return the verbose type.
@return the verbose node type description string.
*/
public void setVerboseType(String stype) {
	// Abbreviations.
	if (stype.equalsIgnoreCase("Blank")) {
		__type = NODE_TYPE_BLANK;
	}
	else if (stype.equalsIgnoreCase("Diversion")) {
		__type = NODE_TYPE_DIV;
	}
	else if (stype.equalsIgnoreCase("Diversion and Well")) {
		__type = NODE_TYPE_DIV_AND_WELL;
	}
	else if (stype.equalsIgnoreCase("Well")) {
		__type = NODE_TYPE_WELL;
	}

// The following is done because flow types have different names in WIS and non-WIS screens.
// Unfortunately, this causes a lot of problems.
// A far far better solution would be to rename one of the flow types,
// (e.g., for WIS make a NODE_TYPE_STATION node that uses "Station" and leave NODE_TYPE_FLOW for "Streamflow").
// There's lots of work to be done in revising these classes, though.  It'll have to wait.

	else if (!__inWis && stype.equalsIgnoreCase("Streamflow")) {
		__type = NODE_TYPE_FLOW;
	}
	else if (__inWis && stype.equalsIgnoreCase("Station")) {
		__type = NODE_TYPE_FLOW;
	}
	else if (stype.equalsIgnoreCase("Confluence")) {
		__type = NODE_TYPE_CONFLUENCE;
	}
	else if (stype.equalsIgnoreCase("Instream Flow")) {
		__type = NODE_TYPE_ISF;
	}
	else if (stype.equalsIgnoreCase("Reservoir")) {
		__type = NODE_TYPE_RES;
	}
	else if (stype.equalsIgnoreCase("Import")) {
		__type = NODE_TYPE_IMPORT;
	}
	else if (stype.equalsIgnoreCase("Baseflow")) {
		__type = NODE_TYPE_BASEFLOW;
	}
	else if (stype.equalsIgnoreCase("End")) {
		__type = NODE_TYPE_END;
	}
	else if (stype.equalsIgnoreCase("Other")) {
		__type = NODE_TYPE_OTHER;
	}
	else if (stype.equalsIgnoreCase("Uknown")) {
		__type = NODE_TYPE_UNKNOWN;
	}
	else if (stype.equalsIgnoreCase("Stream")) {
		__type = NODE_TYPE_STREAM;
	}
	else if (stype.equalsIgnoreCase("Label")) {
		__type = NODE_TYPE_LABEL;
	}
	else if (stype.equalsIgnoreCase("Formula")) {
		__type = NODE_TYPE_FORMULA;
	}
	else if (stype.equalsIgnoreCase("XConfluence")) {
		__type = NODE_TYPE_XCONFLUENCE;
	}
	else if (stype.equalsIgnoreCase("LabelNode")) {
		__type = NODE_TYPE_LABEL_NODE;
	}
	else {
		Message.printWarning(2, "setVerboseType",
			"Unknown type: '" + stype + "'");
		__type = NODE_TYPE_BLANK;
	}
}

/**
Sets whether this node is visible or not.
@param visible whether this node is visible or not.
*/
public void setVisible(boolean visible) {
	__visible = visible;
}

/**
Sets area * precip.  Makenet-specific.
@param water the value to put in area * precip.
*/
public void setWater(double water) {
	__water = water;
}

/**
Sets area * precip as a String.  Makenet-specific.
@param waterString value of area * precip as a String.
*/
public void setWaterString(String waterString) {
	if (waterString != null) {
		__waterString = waterString;
	}
}

/**
Sets the wis format associated with this node.
@param wisFormat the wis format associated with this node.
*/
public void setWISFormat(HydroBase_WISFormat wisFormat) {
	__wisFormat = wisFormat;
}

/**
Sets the number of the wis sheet this node is associated with.
@param wis_num the number of the wis sheet this node is associated with.
*/
public void setWis_num(int wis_num) {
	__wisNum = wis_num;
}

/**
Sets the node X coordinate.
@param x value to put into X
*/
public void setX(double x) {
	__x = x;
}

/**
Sets the node Y coordinate.
@param y value to put into Y.
*/
public void setY(double y) {
	__y = y;
}

/**
Returns whether calls should be shown.
@return whether calls should be shown.
*/
public boolean showCalls() {
	return __showCalls;
}

/**
Returns whether the delivery flow should be shown.
@return whether the delivery flow should be shown.
*/
public boolean showDeliveryFlow() {
	return __showDeliveryFlow;
}

/**
Returns whether the natural flow should be shown.
@return whether the natural flow should be shown.
*/
public boolean showNaturalFlow() {
	return __showNaturalFlow;
}

/**
Returns whether the point flow should be shown.
@return whether the point flow should be shown.
*/
public boolean showPointFlow() {
	return __showPointFlow;
}

/**
Returns whether to show the rights.
@return whether the rights should be shown.
*/
public boolean showRights() {
	return __showRights;
}

/**
Returns a String representation of the object suitable for debugging a network.
@return a String representation of the object suitable for debugging a network.
*/
public String toNetworkDebugString() {
	String down = "";
	String up = "";

	if (__downstream != null) {
		down = __downstream.getCommonID();
	}
	else {
		down = "null";
	}

	if (__upstream != null) {
		for (int i = 0; i < getNumUpstreamNodes(); i++) {
			up = up + " [" + i + "]:\"" + getUpstreamNode(i).getCommonID() + "\"";
		}
	}
	else {
		up = "null";
	}

	return "[" + __label + "]  US: '" + up + "'   DS: '" + down + "'" + "  RL: " + __reachLevel;
}

/**
Returns information about the node in a format useful for debugging node layouts in the network editor.
@return information about the node in a format useful for debugging node layouts in the network editor.
*/
public String toNetworkString() {
	return
		"Identifier: '" + __identifier + "'\n" +
		"Node type:  '" + __nodeType   + "'\n" +
		"X:           " + __x 		+ "\n" +
		"DBX:         " + __dbX		+ "\n" +
		"Y:           " + __y		+ "\n" +
		"DBY:         " + __dbY		+ "\n" +
		"Description:'" + __desc        + "'\n" +
		"User desc:  '" + __userDesc    + "'\n" +
		"CommonID:   '" + __commonID    + "'\n" +
		"IsVisible:   " + __visible 	+ "\n";
}

/**
Returns a verbose String representation of the object.
@return a verbose String representation of the object.
*/
public String toString() {
	String down = "";
	String up = "";

	if (__downstream != null) {
		down = __downstream.getCommonID();
	}
	else {
		down = "null";
	}

	if (__upstream != null) {
		for (int i = 0; i < getNumUpstreamNodes(); i++) {
			up = up + " [" + i + "]:\""
				+ getUpstreamNode(i).getCommonID() + "\"";
		}
	}
	else {
		up = "null";
	}

	return "\"" + getCommonID() + "\" T=" + getTypeString(__type,1)
		+ " T#=" + __tributaryNum + " RC=" + __reachCounter + " RL="
		+ __reachLevel + " #=" + __serial + " #inR="
		+ __nodeInReachNum + " CO=" + __computationalOrder
		+ " DWN=\"" + down + "\" #up=" + getNumUpstreamNodes() + " UP="
		+ up;
}

/**
Returns a String representation of the DB table data from the node.
@return a String representation of the DB table data from the node.
*/
public String toTableString() {
	return
		"Wis_num:     " + __wisNum 	+ "\n" +
		"Identifier: '" + __identifier 	+ "'\n" +
		"Node type:  '" + __nodeType 	+ "'\n" +
		"X:           " + __x 		+ "\n" +
		"DBX:         " + __dbX		+ "\n" +
		"Y:           " + __y		+ "\n" +
		"DBY:         " + __dbY		+ "\n" +
		"Label:      '" + __label;
}

/**
Writes the node out to the given PrintWriter as XML.
@param out the PrintWrite to write the node out to.
@param verbose if true, then all the information about the node will be written out.
*/
public String writeNodeXML(PrintWriter out, boolean verbose) {
	String xml = "    <Node ";
	String n = System.getProperty("line.separator");

	String id = __commonID;
	id = StringUtil.replaceString(id, "&", "&amp;");
	id = StringUtil.replaceString(id, "<", "&lt;");
	id = StringUtil.replaceString(id, ">", "&gt;");

	xml += "ID = \""  + id + "\"" + n;
	xml += "         AlternateX = \"" + __dbX + "\"" + n;
	xml += "         AlternateY = \"" + __dbY + "\"" + n;
	String desc = __desc;
	desc = StringUtil.replaceString(desc, "&", "&amp;");
	desc = StringUtil.replaceString(desc, "<", "&lt;");
	desc = StringUtil.replaceString(desc, ">", "&gt;");
	xml += "         Description = \"" + desc + "\"" + n;
//	xml += "         Identifier = \"" + __identifier + "\"" + n;
	xml += "         IsBaseflow = \"" + __isBaseflow + "\"" + n;
	xml += "         IsImport = \"" + __isImport + "\"" + n;
//	xml += "         LabelAngle = \"" + __labelAngle + "\"" + n;

	if (__isBaseflow) {
		xml += "         Area = \"" + __area + "\"" + n;
		xml += "         Precipitation = \"" + __precip + "\"" + n;
	}

	String sdir = null;
	int dir = getLabelDirection();
	dir = dir % 10;

	if (dir == 1) {
		sdir = "AboveCenter";
	}
	else if (dir == 7) {
		sdir = "UpperRight";
	}
	else if (dir == 4) {
		sdir = "Right";
	}
	else if (dir == 8) {
		sdir = "LowerRight";
	}
	else if (dir == 2) {
		sdir = "BelowCenter";
	}
	else if (dir == 5) {
		sdir = "LowerLeft";
	}
	else if (dir == 3) {
		sdir = "Left";
	}
	else if (dir == 6) {
		sdir = "UpperLeft";
	}
	else if (dir == 9) {
		sdir = "Center";
	}
	else {
		sdir = "BelowCenter";
	}

	if (sdir != null) {
		xml += "         LabelPosition = \"" + sdir + "\"" + n;
	}

	if (getType() == NODE_TYPE_RES) {
		sdir = null;
		dir = getLabelDirection();
		dir = dir / 10;
		if (dir == 2) {
			sdir = "Up";
		}
		else if (dir == 1) {
			sdir = "Down";
		}
		else if (dir == 4) {
			sdir = "Left";
		}
		else if (dir == 3) {
			sdir = "Right";
		}
		else {
			sdir = "Left";
		}
		if (sdir != null) {
			xml += "         ReservoirDir = \"" + sdir + "\"" + n;
		}
	}

//	String type = getTypeString(__type, 1);
	String type = getVerboseType(__type);
//	if (type.equalsIgnoreCase("D&W")) {
//		type = "DW";
//	}
	xml += "         Type = \"" + type + "\"" + n;

	if (verbose) {
		xml += "         ComputationalOrder = \"" + __computationalOrder
			+"\"" + n;
		xml += "         NodeInReachNum = \"" + __nodeInReachNum + "\""
			+ "" + n;
		xml += "        ReachCounter = \"" + __reachCounter + "\"" + n;
		xml += "         Serial = \"" + __serial + "\"" + n;
		xml += "         TributaryNum = \"" + __tributaryNum + "\"" + n;
		xml += "         UpstreamOrder = \"" + __upstreamOrder + "\""
			+ n;
	}

	xml += "         X = \"" + StringUtil.formatString(__x, "%13.6f").trim()
		+ "\"" + n;
	xml += "         Y = \"" + StringUtil.formatString(__y, "%13.6f").trim()
		+ "\">" + n;

	if (__downstream != null) {
		String down = __downstream.getCommonID();
		down = StringUtil.replaceString(down, "&", "&amp;");
		down = StringUtil.replaceString(down, "<", "&lt;");
		down = StringUtil.replaceString(down, ">", "&gt;");
		xml += "        <DownstreamNode ID = \"" + down + "\"/>" + n;
	}

	String up = "";
	int num = getNumUpstreamNodes();
	if (__upstream != null) {
		for (int i = 0; i < num; i++) {
			up = getUpstreamNode(i).getCommonID();
			up = StringUtil.replaceString(up, "&", "&amp;");
			up = StringUtil.replaceString(up, "<", "&lt;");
			up = StringUtil.replaceString(up, ">", "&gt;");
			xml += "        <UpstreamNode ID = \"" + up + "\"/>" + n;
		}
	}
	xml += "    </Node>" + n;

	if (out != null) {
		out.print(xml + "" + n);
		return null;
	}
	else {
		return xml;
	}
}

}