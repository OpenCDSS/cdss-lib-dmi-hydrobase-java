//-----------------------------------------------------------------------------
// HydroBase_GUI_WISDiagram - GUI that allows the user to view or edit a 
//	graphical representation of a wis node diagram.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// 2003-12-15	J. Thomas Sapienza, RTi	Initial version.
// 2004-05-18	JTS, RTi		Major overhaul of the entire design,
//					reflecting lessons learned from the
//					network diagram editor for StateMod
//					and new goals for this tool.
// 2004-05-21	JTS, RTi		Converted to read data from HydroBaseDMI
//					instead of from a flat file.
// 2004-05-27	JTS, RTi		Renamed from HydroBase_GUI_WISNetwork
//					to HydroBase_GUI_WISDiagram.
// 2004-07-20	JTS, RTi		Annotations are now taken into account
//					when determining network extents.
// 2005-02-16	JTS, RTi		Converted queries to use stored
//					procedures.
// 2005-05-09	JTS, RTi		Only HydroBase_StationView objects are
//					returned from station queries now.
// 2005-05-09	JTS, RTi		All structure queries now return
//					structure view objects.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import RTi.GR.GRLimits;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.TextResponseJDialog;

import RTi.Util.IO.Prop;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

/**
This class is a GUI for graphically displaying a node diagram.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_WISDiagram 
extends JFrame 
implements ActionListener, WindowListener {

/**
Class name.
*/
public final static String __CLASS = "HydroBase_GUI_WISDiagram";

/**
Used to refer to the forms in which this GUI can be opened -- one of them 
allows networks to be edited.  The other only allows networks to be viewed.
*/
private final static int 
	__NETWORK_BUILD = 0,
	__NETWORK_VIEW = 1;

/**
Button labels.
*/
private final static String
	__BUTTON_CANCEL =	"Cancel",
	__BUTTON_OK = 		"OK",
	__BUTTON_SAVE = 	"Save";

/**
The bounds of the diagram.
*/
private double 
	__lx,
	__by,
	__rx,
	__ty;

/**
Reference to the dmi used to communicate with the database.
*/
private HydroBaseDMI __dmi = null;

/**
The GRDevice on which drawing will be done.
*/
private HydroBase_Device_WISDiagram __device = null;

/**
The drawing area in which drawing will take place.
*/
private HydroBase_DrawingArea_WISDiagram __drawingArea = null;

/**
The WIS that instantiated this GUI (in display mode).
*/
private HydroBase_GUI_WIS __wis = null;

/**
The WIS Builder that instantiated this GUI (in edit mode).
*/
private HydroBase_GUI_WISBuilder __wisBuilder = null;

/**
The network that will be drawn.
*/
private HydroBase_NodeNetwork __network;

/**
The mode in which this GUI was opened (see NETWORK_*).
*/
private int __mode = -1;

/**
The wis for which a diagram is being displayed.
*/
private int __wisNum = -1;

/**
GUI buttons.
*/
private JButton 
	__saveButton;

/**
Panel in which the drawing area is placed.
*/
private JPanel __panel;

/**
The scroll pane in which the drawing area is placed.
*/
private JScrollPane __jsp;

/**
Status field.
*/
private JTextField __statusField;

/**
List of the records read from the database.
*/
private List<HydroBase_WISDiagramData> __records;

/**
Constructor; private so that it can't be used.
*/
@SuppressWarnings("unused")
private HydroBase_GUI_WISDiagram() {}

/**
Constructor.
@param builder the WISBuilder object that instantiated this GUI.
@param dmi a dmi to use.
*/
public HydroBase_GUI_WISDiagram(HydroBase_GUI_WISBuilder builder,
HydroBaseDMI dmi) {
	__wisBuilder = builder;
	__dmi = dmi;
	__mode = __NETWORK_BUILD;
	__wisNum = __wisBuilder.getWis_num();

	readDatabaseRecords();

	setupGUI();
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());	

	buildDiagram();
}

/**
Constructor.
@param wis the WIS GUI that instantiated this GUI.
@param dmi the dmi to use.
*/
public HydroBase_GUI_WISDiagram(HydroBase_GUI_WIS wis, HydroBaseDMI dmi) {
	__wis = wis;
	__dmi = dmi;
	__mode = __NETWORK_VIEW;	
	__wisNum = __wis.getWISNumber();

	readDatabaseRecords();

	setupGUI();
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());

	buildDiagram();
}

/**
Responds to button presses.
@param event the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent event) {
	String command = event.getActionCommand();

	if (command.equals(__BUTTON_CANCEL)) {
		cancelClicked();
	}
	else if (command.equals(__BUTTON_OK)) {
		okClicked();
	}
	else if (command.equals(__BUTTON_SAVE)) {
		saveClicked();
	}
}

/**
Builds the diagram, fills in the locations, creates the drawing areas, and
initially draws the diagram.
*/
private void buildDiagram() {
	String routine = __CLASS + ".buildDiagram";

	double[] loc = null;
	HydroBase_Node[] nodes = recordsToNodes();
	HydroBase_Node node = null;
	int count = 0;
	
	// build the node diagram from the data in the wis_format data.
	buildDiagramFromWISFormat();	

	// loop through every node in the database for the current wis num
	for (int i = 0; i < nodes.length; i++) {
		// try to find a node with the same identifier string
		// in the nodes in the current diagram on the wis.
		node = __network.findNode(nodes[i].getIdentifier());

		if (node == null) {
			// ... it is a node that used to
			// be in the diagram but isn't any longer.
			// Remove it from the database.
			
			try {
				__dmi.deleteWISDiagramDataForWis_numID(
					__wisNum, nodes[i].getIdentifier());
			}
			catch (Exception e) {
				Message.printWarning(1, routine, 
					"Error deleting record.");
				Message.printWarning(2, routine, e);
			}
		}
		else {	
			// the node in the table was found in the node diagram.
			// Set up the information for the node in the diagram
			// with the data from the database.
			loc = lookupLocation(node.getCommonID());
			node.setDBX(loc[0]);
			node.setDBY(loc[1]);
			node.setX(nodes[i].getX());
			node.setY(nodes[i].getY());
			node.setShowDeliveryFlow(nodes[i].showDeliveryFlow());
			node.setShowNaturalFlow(nodes[i].showNaturalFlow());
			node.setShowPointFlow(nodes[i].showPointFlow());
			node.setLabel(nodes[i].getLabel());
			node.setLabelDirection(nodes[i].getLabelDirection());

			// mark the node as being filled with data read
			// from the database
			node.setReadFromDB(true);
			count++;
		}
	}

	int total = 0;
	node = __network.getMostUpstreamNode();	
	String type = null;
	boolean done = false;
	HydroBase_Node holdNode = null;
	while (!done) {
		if (node.getType() == HydroBase_Node.NODE_TYPE_STREAM
		 || node.getType() == HydroBase_Node.NODE_TYPE_LABEL) {}
		else {
			total++;
		}
	
		if (node.getType() == HydroBase_Node.NODE_TYPE_END) {
			total--;
			done = true;
		}		
		else if (node == holdNode) {
			done = true;
			total--;
		}
		holdNode = node;	
		node = HydroBase_NodeNetwork.getDownstreamNode(node, 
			HydroBase_NodeNetwork.POSITION_COMPUTATIONAL);		
	}

	String plural1 = "s";
	if (count == 1) {
		plural1 = "";
	}
	String plural2 = "s";
	if (total == 1) {
		plural2 = "";
	}
	String message = "Diagram coordinates for " + count 
		+ " location" + plural1 + " (out of " + total + " total "
		+ "node" + plural2 + ") read from diagram data.";
	
	if (count != total) {
		message += "The other locations were read from HydroBase UTM "
			+ "or interpolated.";
	}

	setStatus(message);

	// loop through the diagram now and get the far extents of the 
	// UTM coordinate system.  This is done in order to calculate a 
	// spacing value to use when extrapolating/interpolating node
	// positions.  The distance is 6% of the far extent of the diagram
	// in its current shape.  This will only be used if for some reason
	// nothing else can be done to inter/extrapolate the node location.

	// At the same time, set some variables for all nodes, not just those
	// that had data in the database.

	done = false;
	double lx = Double.MAX_VALUE;
	double rx = -1;
	double by = Double.MAX_VALUE;
	double ty = -1;	
	holdNode = null;
	type = null;

	node = __network.getMostUpstreamNode();	

	while (!done) {
		type = HydroBase_Node.getVerboseWISType(node.getType()).trim();
		node.setWis_num(__wisNum);
		node.setIdentifier(node.getCommonID());		
		node.setNodeType(type);
	
		// only do this if no node data was read from 
		// the database
		if (!node.isReadFromDB()) {
			loc = lookupLocation(node.getCommonID());
			node.setDBX(loc[0]);
			node.setDBY(loc[1]);
			node.setX(loc[0]);
			node.setY(loc[1]);
		}
		else {
			// else X and Y were read from the database 
			// and the DBX and DBY will filled in in the 
			// code above.
		}		

		// determine the far extents
			
		if (node.getDBX() >= 0) {
			if (node.getDBX() < lx) {
				lx = node.getDBX();
			}
			if (node.getDBX() > rx) {
				rx = node.getDBX();
			}
		}
		if (node.getDBY() >= 0) {
			if (node.getDBY() < by) {
				by = node.getDBY();
			}
			if (node.getDBY() > ty) {
				ty = node.getDBY();
			}
		}
		
		if (node.getType() == HydroBase_Node.NODE_TYPE_END) {
			done = true;
		}		
		else if (node == holdNode) {
			done = true;
		}

		holdNode = node;	
		node = HydroBase_NodeNetwork.getDownstreamNode(node, 
			HydroBase_NodeNetwork.POSITION_COMPUTATIONAL);		
	}

	count = 0;

	HydroBase_WISDiagramData data = null;
	int wis_num;
	int size = __records.size();
	for (int i = 0; i < size; i++) {
		data = __records.get(i);
		wis_num = data.getWis_num();
		type = data.getType();
		if (wis_num == __wisNum 
		    && type.equalsIgnoreCase("Annotation")) {
			if (data.getX() >= 0) {
				if (data.getX() < lx) {
					lx = data.getX();
				}
				if (data.getX() > rx) {
					rx = data.getX();
				}
			}
			if (data.getY() >= 0) {
				if (data.getY() < by) {
					by = data.getY();
				}
				if (data.getY() > ty) {
					ty = data.getY();
				}
			}
		}
	}

	// check to make sure that the bounds were determined properly.
	// If for any reason none of the nodes have valid values, then 
	// the rest of the code screw up.  If any of the following are 
	// true ...

	if (lx == Double.MAX_VALUE) {
		count++;
	}
	if (rx == Double.MIN_VALUE) {
		count++;
	}
	if (by == Double.MAX_VALUE) {
		count++;
	}
	if (ty == Double.MIN_VALUE) {
		count++;
	}

	// ... then something will need to be done.

	if (count > 0) {
		Double D = null;
		double[] vals = null;
		String[] invalid = null;
		String last = "";
		String plural = "s";
		String s = null;
		String temp = "";
		List<String> v = null;

		// query the user for the bounds of the display.  The bounds
		// will be constrained to be non-negative, and lx < rx and
		// by < ty.
		
		while (s == null) {
			message = "";
			count = 0;
			plural = "s";
			vals = new double[4];
			invalid = new String[4];
			s = new TextResponseJDialog(this, "Enter Coordinates",
				"No coordinates are found in HydroBase.\n"
				+ "Please supply minimum and maximum UTM "
				+ "coordinates in the form:\n"
				+ "   MinX, MinY, MaxX, MaxY\n" 
				+ "Coordinates must be non-negative and the Min"
				+ " values must be smaller than the\n"
				+ "respective Max values.", last,
				ResponseJDialog.OK | ResponseJDialog.CANCEL)
				.response();
			last = s;
			v = StringUtil.breakStringList(s, ",", 0);
			if (v.size() != 4) {
				new ResponseJDialog(this, "Incorrect Number "
					+ "of Coordinates",
					"Enter exactly 4 coordinate values.",
					ResponseJDialog.OK);
				s = null;
				continue;
			}
			
			for (int i = 0; i < 4; i++) {
				temp = (String)v.get(i);
				temp = temp.trim();
				try {
					D = new Double(temp);
					vals[i] = D.doubleValue();
				}
				catch (Exception e) {
					invalid[count] = temp;
					count++;
				}
			}

			if (count > 0) {
				if (count == 1) {
					plural = "";
				}
				message = "";
				for (int i = 0; i < count; i++) {
					if (i > 0) {
						message += ", ";
					}
					message += invalid[i];
				}
				new ResponseJDialog(this, 
					"Invalid Coordinate" + plural, 
					"Invalid coordinate value" + plural
					+ " entered:\n" + message + "\n"
					+ "Please correct.",
					ResponseJDialog.OK);			
				s = null;
				continue;
			}

			if (vals[0] >= vals[2]) {
				count++;
				message += "\n   MinX must be less than MaxX";
			}
			if (vals[1] >= vals[3]) {
				count++;
				message += "\n   MinY must be less than MaxY";
			}
			if (vals[0] < 0) {
				count++;;
				message += "\n   MinX is less than 0";
			}
			if (vals[1] < 0) {
				count++;;
				message += "\n   MinY is less than 0";
			}
			if (vals[2] < 0) {
				count++;;
				message += "\n   MaxX is less than 0";
			}
			if (vals[3] < 0) {
				count++;;
				message += "\n   MaxY is less than 0";
			}
					
			if (count > 0) {
				new ResponseJDialog(this, 
					"Invalid Coordinates",
					"Invalid coordinate values entered.\n"
					+ "Please correct the following:"
					+ message, ResponseJDialog.OK);
				s = null;
				continue;
			}				
		}

		// put the values the user entered into the bounds

		lx = vals[0];
		by = vals[1];
		rx = vals[2];
		ty = vals[3];
	}

	// now go through all the nodes and for any with missing or invalid 
	// location values, interpolate from the nodes around them.

	// default the first node's coordinates to the very middle of all
	// the node locations if they are missing.
	node = __network.getMostUpstreamNode();	
	
	count = 0;

	__lx = lx;
	__by = by;
	__rx = rx;
	__ty = ty;

	__network.fillLocations(__dmi, true, 
		new GRLimits(__lx, __by, __rx, __ty));

	// loop through the diagram now and get the far extents of the 
	// UTM coordinate system, now that all the nodes have had their
	// positions set.  The far extents of the diagram may have changed.

	lx = Double.MAX_VALUE;
	rx = -1;
	by = Double.MAX_VALUE;
	ty = -1;	
	
	done = false;
	node = __network.getMostUpstreamNode();	
	while (!done) {
		type = node.getNodeType();
		if (	type.equals("Annotation")
		     || type.equals("Label")
		     || type.equals("Stream")
		     || type.equals("String")) {}
		else {
			if (node.getX() < lx) {
				lx = node.getX();
			}
			if (node.getX() > rx) {
				rx = node.getX();
			}
			if (node.getY() < by) {
				by = node.getY();
			}
			if (node.getY() > ty) {
				ty = node.getY();
			}
		}

		if (node.getType() == HydroBase_Node.NODE_TYPE_END) {
			done = true;
		}		
		else if (node == holdNode) {
			done = true;
		}

		holdNode = node;	
		node = HydroBase_NodeNetwork.getDownstreamNode(node, 
			HydroBase_NodeNetwork.POSITION_COMPUTATIONAL);		
	}

	int hSize = 1200;
	int vSize = 900;

	// expand the data limits outward so that the extents of the data
	// limits fit within the printable portion of the page.  The extra
	// space will be in the unprintable margin.
	/*
	// the total space of the page
	double totalWidth = pageFormat.getWidth();
	double totalHeight = pageFormat.getHeight();
	// the height of the data.
	double dataWidth = rx - lx;
	double dataHeight = ty - by;

	// solve for the new data size.  Using the above, the following
	// equation was run (where I = ImageableSize, T = total page size,
	// D = Data dimension, and N = the new data dimension being solved 
	// for):
	//    I         D
	//   ---   =   ---
	//    T         N
	// Solve for N.
	
	double newDataWidth = (totalWidth * dataWidth) / imageableWidth;
	double newDataHeight = (totalHeight * dataHeight) / imageableHeight;

	// these are the differences between the new data dimension and the 
	// original.  The diff added to both sides of the original data 
	// dimension will result in the new one.
	double widthDiff = (newDataWidth - dataWidth) / 2;
	double heightDiff = (newDataHeight - dataHeight) / 2;
	// pad the differences by an addition 3 percent.
	double widthDiff5p = widthDiff * 0.03;
	widthDiff += widthDiff5p;
	double heightDiff5p = heightDiff * 0.03;
	heightDiff += heightDiff5p;
	*/

	double w = rx - lx;
	double h = ty - by;

	double widthDiff = w * 0.03;
	double heightDiff = h * 0.03;

	// adjust the far extents of the data to account now for the blank
	// space added in the margin
	lx -= widthDiff;
	rx += widthDiff;
	by -= heightDiff;
	ty += heightDiff;

	w = rx - lx;
	h = ty - by;

	// the next section determines which dimension the data runs farthest
	// in and then modifies the other dimension in order to fit 
	// in the proper ratio of 
	// biggest data dimension : smallest data dimension 
	// ::
	// longest side of the page : smallest side of the page

	if (w > h) {
		double newH = (vSize * w) / hSize;
		double diff = newH - h;
		if (diff < 0) {
			Message.printWarning(1, routine,
				"In order to fit the paper, the diagram"
				+ " has been truncated vertically.");
		}
		diff /= 2;
		ty += diff;
		by -= diff;
	}
	else {
		double newW = (hSize * h) / vSize;
		double diff = newW - w;
		if (diff < 0) {
			Message.printWarning(1, routine,
				"In order to fit the paper, the diagram"
				+ " has been truncated horizontally.");
		}
		diff /= 2;
		lx -= diff;
		rx += diff;
	}

	GRLimits drawingLimits = new GRLimits(0, 0, hSize, vSize);
	GRLimits dataLimits = new GRLimits(lx, by, rx, ty);

	// perform one final check of all the node positions and make sure 
	// they are all within the boundaries specified
	__network.finalCheck(lx, by, rx, ty);

	__device.setPreferredSize(new Dimension(hSize, vSize));
	__drawingArea = new HydroBase_DrawingArea_WISDiagram(__device, 
		drawingLimits);
	__drawingArea.setDataLimits(dataLimits);
	__device.setDrawingArea(__drawingArea);	

	__network.setInWIS(true);
	__device.setNetwork(__network);
	__device.forceRepaint();

	int height = getHeight();
	int width = getWidth();	
	JGUIUtil.forceRepaint(__jsp);
	setInitialViewportPosition();
	JGUIUtil.forceRepaint(__device);
	try {
		Thread.sleep(100);
	}
	catch (Exception e) {}

	// the device that actually paints the diagram was not painting itself
	// at first when the JFrame opened.  No reason could be found for why
	// not, so the following lines were added.  They force the JFrame to 
	// completely repaint, and they work for getting a visible diagram.
	setSize(width, height);
	__device.invalidate();
	__device.validate();
	__jsp.invalidate();
	__jsp.validate();
	invalidate();
	validate();
	repaint();
}

/**
Builds the node diagram from the wis format vector in the WIS that instantiated
this GUI.
*/
private void buildDiagramFromWISFormat() {
	List<HydroBase_WISFormat> v = null;
	if (__mode == __NETWORK_BUILD) {
		v = __wisBuilder.getWisFormatVector();
	}
	else {
		v = __wis.getWisFormatVector();
	}

	__network = new HydroBase_NodeNetwork();
	__network.setInWIS(true);
	__network.treatDryNodesAsBaseflow(true);	
	__network.readWISFormatNetwork(v, 1);
}

/**
Closes the GUI.
*/
private void cancelClicked() {
	closeWindow();
}

/**
Closes the GUI.
*/
private void closeWindow() {
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	if (__mode == __NETWORK_BUILD) {
		__wisBuilder.networkGUIClosed();
	}
	else {
		__wis.networkGUIClosed();
	}

	if (__device.isDirty()) {
		int r = new ResponseJDialog(this, "Save changes?", 
			"Save changes?", ResponseJDialog.YES 
			| ResponseJDialog.NO | ResponseJDialog.CANCEL)
			.response();
	
		if (r == ResponseJDialog.YES) {
			saveClicked();
		}
		else if (r == ResponseJDialog.CANCEL) {
			setDefaultCloseOperation(
				WindowConstants.DO_NOTHING_ON_CLOSE);
			return;
		}
	}
	
	setVisible(false);
	dispose();	
}

/**
Prints out the network.  For debugging.
*/
/* TODO SAM 2007-02-26 Evaluate whether to keep
private void dumpNetwork() {
	boolean done = false;
	HydroBase_Node holdNode;
	HydroBase_Node node = __network.getMostUpstreamNode();	
	Message.printStatus(1, "", "dumpNetwork ---------------------");
	while (!done) {
		holdNode = node;	
		// get the next node
		node = HydroBase_NodeNetwork.getDownstreamNode(node, 
			HydroBase_NodeNetwork.POSITION_COMPUTATIONAL);
		if (node != holdNode) {
			Message.printStatus(1, "","..........................");
			Message.printStatus(1, "", "!] "+ node.toTableString());
			Message.printStatus(1, "","..........................");
			if (node.getType() == HydroBase_Node.NODE_TYPE_END) {
				done = true;
			}		
		}
		else {
			done = true;
		}
	}	
}
*/

/**
Prints out the nodes in the node array.  For debugging.
@param nodes the node array (non-null) for which to print the nodes.
*/
/* TODO SAM 2007-02-26 Decide whether to keep
private void dumpNodes(HydroBase_Node[] nodes) {
	for (int i = 0; i < nodes.length; i++) {
		Message.printStatus(1, "", "" + nodes[i].toTableString() +"\n");
	}
}
*/

/**
Cleans up member variables.
*/
public void finalize() 
throws Throwable {
	__dmi = null;
	__device = null;
	__drawingArea = null;
	__wis = null;
	__wisBuilder = null;
	__network = null;
	__saveButton = null;
	__panel = null;
	__jsp = null;
	__statusField = null;
	__records = null;
	super.finalize();
}

/**
Returns the WIS being shown in the wis diagram.
@return the WIS being shown in the wis diagram.
*/
public HydroBase_GUI_WIS getWIS() {
	return __wis;
}

/**
Returns the wis number of the wis for which the diagram is built.  
@return the wis number of the wis for which the diagram is built.
*/
public int getWis_num() {
	return __wisNum;
}

/**
Looks up the label for the node with the specified identifier so that it
can be stored in the node.
@param identifier the identifier of the wis row for which to return the label.
@return the row label.
*/
private String lookupLabel(String identifier) {
	List<HydroBase_WISFormat> v = null;
	if (__mode == __NETWORK_BUILD) {
		v = __wisBuilder.getWisFormatVector();
	}
	else {
		v = __wis.getWisFormatVector();
	}

	int size = v.size();
	HydroBase_WISFormat format = null;
	for (int i = 0; i < size; i++) {
		format = (HydroBase_WISFormat)v.get(i);
		if (format.getIdentifier().equals(identifier)) {
			return format.getRow_label();
		}
	}
	return "Couldn't find: " + identifier;
}

/**
Looks up the location of a structure in the database.
@param identifier the identifier of the structure.
@return a two-element array, the first element of which is the X location
and the second of which is the Y location.  If none can be found, both values
will be -1.
*/
private double[] lookupLocation(String identifier) {
	String routine = __CLASS + ".lookupLocation";
	double[] loc = new double[2];
	loc[0] = -999.00;
	loc[1] = -999.00;

	if (identifier.startsWith("wdid:")) {
		String swdid = identifier.substring(5);
		try {
			int[] wdid = HydroBase_WaterDistrict.parseWDID(swdid);
			HydroBase_StructureView view = 
				__dmi.readStructureViewForWDID(wdid[0],wdid[1]);
			if (view == null) {
				Message.printWarning(2, routine,
					"Couldn't find a view in HydroBase"
					+ " with wd: " + wdid[0] + " and id: "
					+ wdid[1]);
				return loc;
			}
			HydroBase_Geoloc geoloc = __dmi.readGeolocForGeoloc_num(
				view.getGeoloc_num());
			loc[0] = geoloc.getUtm_x();
			loc[1] = geoloc.getUtm_y();
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading data.");
			Message.printWarning(2, routine, e);
		}
	}
	else if (identifier.startsWith("stat:")) {
		String id = identifier.substring(5);
		try {
			HydroBase_StationView station = 
				__dmi.readStationViewForStation_id(id);
			if (station == null) {
				Message.printWarning(2, routine,
					"Couldn't find a station in HydroBase "
					+ "with id: '" + id + "'");
				return loc;
			}
			HydroBase_Geoloc geoloc = __dmi.readGeolocForGeoloc_num(
				station.getGeoloc_num());
			loc[0] = geoloc.getUtm_x();
			loc[1] = geoloc.getUtm_y();
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading data.");
			Message.printWarning(2, routine, e);
		}		
	}
	return loc;
}

/**
Called when the OK button is pressed.  Saves the diagram and closes the GUI.
*/
private void okClicked() {
	if (__mode == __NETWORK_BUILD) {
		saveClicked();
	}
	closeWindow();
}

/**
Reads wis_network_data records for the current wis from the database and 
stores them in __records.
*/
private void readDatabaseRecords() {
	try {
		__records = __dmi.readWISDiagramDataListForWis_num(__wisNum);
	}
	catch (Exception e) {
		Message.printWarning(2, "", "Could not read wis diagram data "
			+ "for wis_num: " + __wisNum);
		Message.printWarning(2, "", e);
	}

	if (__records == null) {
		__records = new ArrayList<HydroBase_WISDiagramData>();
	}
}

/**
Turns database records in HydroBase_Nodes.
@return an array of HydroBase_Nodes of the nodes in the database.
*/
private HydroBase_Node[] recordsToNodes() {
	HydroBase_Node node = null;
	HydroBase_WISDiagramData data = null;
	int rows = __records.size();
	int wis_num = 0;
	String type = null;
	List<HydroBase_Node> v = new ArrayList<HydroBase_Node>();

	for (int i = 0; i < rows; i++) {
		data = (HydroBase_WISDiagramData)__records.get(i);
		wis_num = data.getWis_num();
		type = data.getType().trim();
		if (wis_num == __wisNum 
		    && !type.equalsIgnoreCase("Annotation")
		    && !type.equalsIgnoreCase("String")
		    && !type.equalsIgnoreCase("Stream")
		    && !type.equalsIgnoreCase("Label")) {
			node = new HydroBase_Node();
			node.setWis_num(i);
			node.setIdentifier(data.getID());
			node.setType(HydroBase_Node.lookupType(type));
			node.setNodeType(type);
			node.setX(data.getX());
			node.setY(data.getY());
			node.setLabelDirection(data.getTextPosition());
			node.setLabel(lookupLabel(data.getID()));
			v.add(node);			
		}
	}			

	// annotations
	List<HydroBase_Node> annotations = new ArrayList<HydroBase_Node>();
	PropList p = null;
	String props = null;
	for (int i = 0; i < rows; i++) {
		data = __records.get(i);
		wis_num = data.getWis_num();
		type = data.getType();
		p = null;
		if (wis_num == __wisNum 
		    && type.equalsIgnoreCase("Annotation")) {
			node = new HydroBase_Node();
			node.setNodeType(type);
			props = data.getProps();
			p = PropList.parse(props, "", ";");
			/*
			Message.printStatus(1, "", "-----------------------");
			for (int j = 0; j < p.size(); j++) {
				Message.printStatus(1, "", "" + p.propAt(j));
			}
			*/
			node.setAssociatedObject(p);	
			annotations.add(node);
		}
	}			

	__device.setAnnotations(annotations);

	int size = v.size();
	if (size == 0) {
		HydroBase_Node[] nodes = new HydroBase_Node[0];
		return nodes;
	}

	HydroBase_Node[] nodes = new HydroBase_Node[size];
	for (int i = 0; i < size; i++) {
		nodes[i] = (HydroBase_Node)v.get(i);
	}
	return nodes;
}

/**
Repaints the diagram.
*/
public void refresh() {
	__device.forceRepaint();
}

/**
Rereads the diagram from the WIS and starts over drawing the diagram.  If any
changes have been made to the diagram diagram, the user is prompted to save
or discard them.
*/
public void rereadDiagram() {
	if (__device.isDirty()) {
		/*
		setExtendedState(getExtendedState() & ~ICONIFIED);	
		this.toFront();
		int response = new ResponseJDialog(this, "Save changes?",
			"Changes have been made to the diagram.  Save "
			+ "changes to database before continuing?",
			ResponseJDialog.YES | ResponseJDialog.NO).response();
		if (response == ResponseJDialog.YES) {
		*/
			saveClicked();
		//}
	}
	buildDiagram();
}

/**
Saves the diagram to the database.
*/
private void saveClicked() {
	String routine = __CLASS + ".saveClicked";

	// first remove all the WIS records from the table (clean)
	try {
		__dmi.deleteWISDiagramDataForWis_num(__wisNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error deleting "
			+ "wis_network_data records from database.");
		Message.printWarning(2, routine, e);
	}

	double xtemp = 0;
	double ytemp = 0;
	HydroBase_Node[] nodes = __device.getNodes();
	HydroBase_Node node = null;
	int size = 0;
	List<HydroBase_Node> annotations = __device.getAnnotations();
	HydroBase_WISDiagramData wnd = null;
	String props = null;
	String stemp = null;

	if (nodes != null) {
		size = nodes.length;
		for (int i = 0; i < size; i++) {		
			try {
				props = "";
				wnd = new HydroBase_WISDiagramData();
				wnd.setWis_num(__wisNum);
				wnd.setID(nodes[i].getIdentifier());
				wnd.setType(HydroBase_Node.getVerboseWISType(
					nodes[i].getType()));
				xtemp = nodes[i].getX();
				stemp = StringUtil.formatString(xtemp,"%18.6f");
				xtemp = (new Double(stemp)).doubleValue();
				ytemp = nodes[i].getY();
				stemp = StringUtil.formatString(ytemp,"%18.6f");
				ytemp = (new Double(stemp)).doubleValue();
				props = "Point=" + xtemp + ", " + ytemp + ";";
				props += "TextPosition=" 
					+ nodes[i].getTextPosition();
				wnd.setProps(props);
				__dmi.writeWISDiagramData(wnd);
			}
			catch (Exception e) {
				Message.printWarning(1, routine, "Error "
					+ "writing table record.");
				Message.printWarning(2, routine, e);
			}
		}
	}

	if (annotations != null) {
		size = annotations.size();
		PropList propList = null;
		int propCount = 0;
		Prop prop = null;
		int count = 0;
		String key = null;
		String value = null;

		for (int i = 0; i < size; i++) {
			try {
				wnd = new HydroBase_WISDiagramData();
				wnd.setWis_num(__wisNum);
				wnd.setID("Annotation " + (i + 1));
				wnd.setType("Annotation");

				node = (HydroBase_Node)annotations.get(i);
				propList = (PropList)node.getAssociatedObject();
				propCount = propList.size();
				props = "";
				count = 0;
				for (int j = 0; j < propCount; j++) {
					prop = propList.elementAt(j);
					if (count > 0) {
						props += ";";
					}
					key = prop.getKey().trim();
					value = prop.getValue().trim();
					if (!key.equals("Number")) {
						props += key + "=" + value;
						count++;
					}
				}
				if (props.equals("")) {
					props = "ShapeType=Text";
				}
				wnd.setProps(props);
				__dmi.writeWISDiagramData(wnd);
			}
			catch (Exception e) {
				Message.printWarning(1, routine, "Error "
					+ "writing table record.");
				Message.printWarning(2, routine, e);
			}
		}
	}

	size = nodes.length;
	for (int i = 0; i < size; i++) {		
		nodes[i].setDirty(false);
	}
	size = annotations.size();
	for (int i = 0; i < size; i++) {	
		node = (HydroBase_Node)annotations.get(i);
		node.setDirty(false);
	}
	__device.setDirty(false);
}

/**
Sets the initial position of the scrollpane's viewport to be the lower-left
hand corner.
*/
protected void setInitialViewportPosition() {
/*
REVISIT -- need to calculate pixel height of drawing area
	Point pt = new Point();
	pt.x = 0;
	pt.y = (int)(__vPixels - 
		(__jsp.getViewport().getExtentSize().getHeight()));
	__jsp.getViewport().setViewPosition(pt);
*/
}

/**
Sets the status message in the status bar of the parent JFrame.  
@param status the status to set in the status text field.
*/
public void setStatus(String status) {
	if (__statusField != null) {
		if (status != null) {
			__statusField.setText(status);
		} 
		else {
			__statusField.setText("");
		}
		JGUIUtil.forceRepaint(__statusField);		
	}
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	addWindowListener(this);

	__panel = new JPanel();
	__panel.setLayout(new GridBagLayout());

	boolean editable = false;
	if (__mode == __NETWORK_BUILD) {
		editable = true;
	}
	__device = new HydroBase_Device_WISDiagram(__dmi, this, editable);
	if (__mode == __NETWORK_BUILD) {
		__device.setWISDate("");
	}
	else {
		__device.setWISDate(__wis.getAdminDateTimeString());
	}	
	__device.setPreferredSize(new Dimension(0, 0));
	GRLimits drawingLimits = new GRLimits(0.0, 0.0, 0, 0);
	__drawingArea = new HydroBase_DrawingArea_WISDiagram(__device, 
		drawingLimits);
	__device.setDrawingArea(__drawingArea);

	__jsp = new JScrollPane(__device);
	JGUIUtil.addComponent(__panel, __jsp,
		0, 0, 1, 1, 1, 1,
		GridBagConstraints.BOTH, GridBagConstraints.CENTER);

	getContentPane().add("Center", __panel);

	JPanel bottom = new JPanel();
	bottom.setLayout(new GridBagLayout());
	__statusField = new JTextField(10);
	__statusField.setEditable(false);

	JPanel panel = new JPanel();
	panel.setLayout(new GridBagLayout());
	
	__saveButton = new JButton(__BUTTON_SAVE);
	__saveButton.setToolTipText("Save diagram to database.");
	__saveButton.addActionListener(this);
	JButton okButton = new JButton(__BUTTON_OK);
	okButton.setToolTipText("Save diagram to database and close.");
	okButton.addActionListener(this);
	JButton cancelButton = new JButton(__BUTTON_CANCEL);
	cancelButton.setToolTipText("Discard changes and close.");
	cancelButton.addActionListener(this);

	int x = 0;
	if (__mode == __NETWORK_BUILD) {	
		JGUIUtil.addComponent(panel, __saveButton,
			x++, 0, 1, 1, 1, 0,
			0, 10, 0, 10,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		JGUIUtil.addComponent(panel, okButton,
			x++, 0, 1, 1, 0, 0,
			0, 10, 0, 10,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
	}
	else {
		JGUIUtil.addComponent(panel, okButton,
			x++, 0, 1, 1, 1, 0,
			0, 10, 0, 10,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
	}
	if (__mode == __NETWORK_BUILD) {
		JGUIUtil.addComponent(panel, cancelButton,
			x++, 0, 1, 1, 0, 0,
			0, 10, 0, 10,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
	}

	JGUIUtil.addComponent(bottom, __statusField,
		0, 0, 1, 1, 1, 1,
		GridBagConstraints.BOTH, GridBagConstraints.WEST);		

	JGUIUtil.addComponent(panel, bottom,
		0, 1, 4, 1, 1, 1,
		GridBagConstraints.BOTH, GridBagConstraints.CENTER);
		
	getContentPane().add("South", panel);	

	__statusField.setText("");

	setSize(800, 600);
	setVisible(true);
	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}

	if (editable) {
		setTitle(app + "Build Diagram");
	}
	else {
		setTitle(app + "WIS Diagram");
	}
	JGUIUtil.center(this);
}

/**
Updates the point flow for the node with the specified ID.
@param id the id of the node to update the point flow for.
@param pf the point flow to update the node with.
*/
protected void updatePointFlow(String id, double pf) {
	__device.updatePointFlow(id, pf);
}

/**
Does nothing.
*/
public void windowActivated(WindowEvent event) {}

/**
Does nothing.
*/
public void windowClosed(WindowEvent event) {}

/**
Closes the GUI.
*/
public void windowClosing(WindowEvent event) {
	closeWindow();
}

/**
Does nothing.
*/
public void windowDeactivated(WindowEvent event) {}

/**
Does nothing.
*/
public void windowDeiconified(WindowEvent event) {}

/**
Does nothing.
*/
public void windowIconified(WindowEvent event) {}

/**
Does nothing.
*/
public void windowOpened(WindowEvent event) {}

}
