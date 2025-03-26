// HydroBase_Device_WISDiagram - the device that controls all the drawing of the WIS Diagram

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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.Scrollable;

import RTi.DMI.DMIUtil;

import RTi.GR.GRColor;
import RTi.GR.GRDrawingAreaUtil;
import RTi.GR.GRJComponentDevice;
import RTi.GR.GRLimits;
import RTi.GR.GRSymbolPosition;
import RTi.GR.GRSymbolShapeType;
import RTi.GR.GRText;
import RTi.GR.GRUnits;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.TextResponseJDialog;

import RTi.Util.IO.IOUtil;
import RTi.Util.IO.PrintUtil;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;

/**
This class extends GRJComponentDevice to be the device that manages the WIS diagram drawing area.
*/
@SuppressWarnings("serial")
public class HydroBase_Device_WISDiagram
extends GRJComponentDevice
implements ActionListener, MouseListener, MouseMotionListener, Printable,
Scrollable {

/**
Class name.
*/
public final static String CLASS = "HydroBase_Device_WISDiagram";

/**
Popup menu labels.
*/
private final String
	__MENU_ADD_ANNOTATION = "Add Annotation",
	__MENU_ANNOTATION_BOUNDING_BOX = "Annotation Bounding Box On/Off",
	__MENU_DELETE_ANNOTATION = "Delete Annotation",
	__MENU_DELETE_DB_DATA = "Delete Diagram Data from Database",
	__MENU_INCH_GRID = "Show Half-Inch Grid",
	__MENU_MARGIN = "Show Margins",
	__MENU_PROPERTIES = "Properties",
	__MENU_PIXEL_GRID = "Show 50 Pixel Grid",
	__MENU_PRINT = "Print on Letter in Landscape",
	__MENU_REFRESH = "Refresh",
	__MENU_SHOW_CALLS = "Show Calls",
	__MENU_SHOW_FLOWS = "Show Flow Data",
	__MENU_SHOW_RIGHTS = "Show Rights";

/**
Whether the annotations have been processed into nodes properly yet.  This should only happen once.
*/
private boolean __annotationsProcessed = false;

/**
Whether call information has already been queried from the database or not.
*/
private boolean __callsQueried = false;

/**
Whether the nodes have been changed or not.
*/
private boolean __dirty = false;

/**
Whether to draw a 1-inch grid over the display.
*/
private boolean __drawInchGrid = false;

/**
Whether to display the printing margins on the display.
*/
private boolean __drawMargin = false;

/**
Whether to draw a 50-pixel grid on the display.
*/
private boolean __drawPixelGrid = false;

/**
Whether the nodes can be changed or not.
*/
private boolean __editable = false;

/**
Whether to erase the node (because it is being dragged).
*/
private boolean __eraseNode;

/**
Whether the next time through paint() everything should be refreshed.
*/
private boolean __forceRefresh = true;

/**
Whether a node is currently being dragged.
*/
private boolean __inDrag = false;

/**
Whether this is the first time ever through paint() (not necessarily a one-time
only deal!).
*/
private boolean __initialize = true;

/**
Whether the last node clicked on was an annotation or not.
*/
private boolean __isAnnotation = false;

/**
Whether this is something in paint() that should only ever absolute, positively
happen once.
*/
private boolean __onceEver = true;

/**
Whether the device is ready to start painting.
*/
private boolean __ready = false;

/**
Whether rights information have been queried from the database or not.
*/
private boolean __rightsQueried = false;

/**
Whether to show a red bounding box around the annotation text.  Used for debugging.
*/
private boolean __showAnnotationBoundingBox = false;

/**
Whether call information should be shown in the labels of the nodes.
*/
private boolean __showCalls = false;

/**
Whether flow information should be shown in the labels of the nodes.
*/
private boolean __showFlows = false;

/**
Whether right information should be shown in the labels of the node.
*/
private boolean __showRights = false;

/**
The scale value for printing.
*/
private double __printScale = .66;

/**
The height of the canvas.
*/
private double __height = 0;

/**
The modifier to apply to normal pixel spacing values to transform them into data unit values.
*/
private double __mod = 1;

/**
The width of the canvas.
*/
private double __width = 0;

/**
The last-clicked X location.
*/
private double __x;

/**
The amount to adjust the X value by to find the far lower-left extent of the drag bounding box.
*/
private double __xAdjust;

/**
The last-clicked Y location.
*/
private double __y;

/**
The amount to adjust the Y value by to find the lower-left extent of the drag bounding box.
*/
private double __yAdjust;

/**
The data limits for the whole drawing area.
*/
private GRLimits __dataLimits;

/**
The drawing limits for the whole drawing area.
*/
private GRLimits __drawingLimits;

/**
The drawing limits of the current node.
*/
private GRLimits __nodeLimits = null;

/**
The dmi to use for communicating with the database.
*/
private HydroBaseDMI __dmi;

/**
The drawing area.
*/
private HydroBase_DrawingArea_WISDiagram __drawingArea;

/**
The parent GUI in which this device was instantiated.
*/
private HydroBase_GUI_WISDiagram __parent;

/**
Array of all the nodes being drawn.
*/
private HydroBase_Node[] __nodes = null;

/**
The network of nodes that is drawn.
*/
private HydroBase_NodeNetwork __network;

/**
The initial size of the annotations Vector.
*/
private int __initialSize = 0;

/**
The most-recently left-clicked-on node.
*/
private int __leftClickNode = -1;

/**
The node on which most recently the popup menu was opened.
*/
private int __popUpNode = -1;

/**
The general popup menu that appears when no node is right-clicked on.
*/
private JPopupMenu __popup;

/**
The popup menu that appears when a node is right-clicked on.
*/
private JPopupMenu __nodePopup;

/**
The popup menu that appears when an annotation node is right-clicked on.
*/
private JPopupMenu __labelPopup;

/**
The labels to be drawn in the key.
*/
private String[] __keyLabels = {
	"Reservoir",
	"Stream",
	"Confluence",
	"Station",
	"Diversion",
	"Minimum Flow",
	"Other" };

/**
The letters drawn over the symbols for the the labels in the key.
*/
private String[] __keyText = {
	"",
	"X",
	"C",
	"B",
	"D",
	"M",
	"O" };

/**
The date of the WIS, which will be placed into the legend.
*/
private String __dateString = "";

/**
List of annotations to be drawn on screen.
*/
private List<HydroBase_Node> __annotations = new ArrayList<HydroBase_Node>();

/**
Constructor.
@param dmi the DMI to use for communicating with the database.
@param parent the parent GUI in which this device was instantiated.
*/
public HydroBase_Device_WISDiagram(HydroBaseDMI dmi, HydroBase_GUI_WISDiagram parent, boolean editable) {
	super("HydroBase_Device_WISDiagram");
	__editable = editable;

	addMouseListener(this);
	addMouseMotionListener(this);

	__dmi = dmi;
	__parent = parent;
	//__printScale = 1;

	__popup = new JPopupMenu();
	JMenuItem mi = null;
	JCheckBoxMenuItem jcbmi = null;
	if (editable) {
		mi = new JMenuItem(__MENU_ADD_ANNOTATION);
		mi.addActionListener(this);
		__popup.add(mi);
		
		__popup.addSeparator();
		mi = new JMenuItem(__MENU_DELETE_DB_DATA);
		mi.addActionListener(this);
		__popup.add(mi);
	}
	else {
		jcbmi = new JCheckBoxMenuItem(__MENU_SHOW_FLOWS);
		jcbmi.addActionListener(this);
		__popup.add(jcbmi);
		jcbmi = new JCheckBoxMenuItem(__MENU_SHOW_CALLS);
		jcbmi.addActionListener(this);
		__popup.add(jcbmi);
		jcbmi = new JCheckBoxMenuItem(__MENU_SHOW_RIGHTS);
		jcbmi.addActionListener(this);
		__popup.add(jcbmi);		
	}
	__popup.addSeparator();

	if (IOUtil.testing()) {
		mi = new JMenuItem(__MENU_ANNOTATION_BOUNDING_BOX);
		mi.addActionListener(this);
		__popup.add(mi);
		__popup.addSeparator();
	}		
	
	mi = new JMenuItem(__MENU_PRINT);
	mi.addActionListener(this);
	__popup.add(mi);
	
	if (IOUtil.testing()) {
		__popup.addSeparator();
		jcbmi = new JCheckBoxMenuItem(__MENU_INCH_GRID);
		jcbmi.addActionListener(this);
		__popup.add(jcbmi);
		jcbmi = new JCheckBoxMenuItem(__MENU_PIXEL_GRID);
		jcbmi.addActionListener(this);
		__popup.add(jcbmi);
	}
	//jcbmi = new JCheckBoxMenuItem(__MENU_MARGIN);
	//jcbmi.addActionListener(this);
	//__popup.add(jcbmi);

	__nodePopup = new JPopupMenu();
	mi = new JMenuItem(__MENU_PROPERTIES);
	mi.addActionListener(this);
	__nodePopup.add(mi);

	__labelPopup = new JPopupMenu();
	mi = new JMenuItem(__MENU_PROPERTIES);
	mi.addActionListener(this);
	__labelPopup.add(mi);
	if (editable) {
		mi = new JMenuItem(__MENU_DELETE_ANNOTATION);
		mi.addActionListener(this);
		__labelPopup.add(mi);	
	}
}

/**
Responds to popup menu clicks.
@param event the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent event) {
	String action = event.getActionCommand();

	if (action.equals(__MENU_ADD_ANNOTATION)) {
		addAnnotation(__x, __y);
	}
	else if (action.equals(__MENU_ANNOTATION_BOUNDING_BOX)) {
		if (__showAnnotationBoundingBox) {
			__showAnnotationBoundingBox = false;
		}
		else {
			__showAnnotationBoundingBox = true;
		}
		forceRepaint();
	}
	else if (action.equals(__MENU_DELETE_ANNOTATION)) {
		deleteNode(__popUpNode);
	}
	else if (action.equals(__MENU_DELETE_DB_DATA)) {
		deleteDatabaseData();
	}
	else if (action.equals(__MENU_INCH_GRID)) {	
		if (__drawInchGrid) {	
			__drawInchGrid = false;
		}
		else {
			__drawInchGrid = true;
		}
		forceRepaint();
	}
	else if (action.equals(__MENU_MARGIN)) {
		if (__drawMargin) {
			__drawMargin = false;
		}
		else {
			__drawMargin = true;
		}
		forceRepaint();
	}
	else if (action.equals(__MENU_PROPERTIES)) {
		HydroBase_Node node = null;
		if (__isAnnotation) {
			node = __annotations.get(__popUpNode);
		}
		else {
			node = __nodes[__popUpNode];
		}
		new HydroBase_GUI_WISDiagramNodeProperties(__dmi, this,
			__editable, node, __popUpNode,
			__isAnnotation);
	}
	else if (action.equals(__MENU_PIXEL_GRID)) {
		if (__drawPixelGrid) {
			__drawPixelGrid = false;
		}
		else {
			__drawPixelGrid = true;
		}
		forceRepaint();
	}
	else if (action.equals(__MENU_PRINT)) {
		print();
	}
	else if (action.equals(__MENU_REFRESH)) {
		forceRepaint();
	}
	else if (action.equals(__MENU_SHOW_CALLS)) {
		if (__showCalls) {
			__showCalls = false;
		}
		else {
			__showCalls = true;
		}
		for (int i = 0; i < __nodes.length; i++) {
			__nodes[i].setShowCalls(__showCalls);

			if (!__callsQueried && __showCalls == true) {
				__nodes[i].setCall(queryCall(__nodes[i]));
			}
			__nodes[i].setBoundsCalculated(false);
		}
		__callsQueried = true;
		forceRepaint();
	}		
	else if (action.equals(__MENU_SHOW_FLOWS)) {
		if (__showFlows) {
			__showFlows = false;
		}
		else {
			__showFlows = true;
		}

		for (int i = 0; i < __nodes.length; i++) {
			__nodes[i].setShowDeliveryFlow(__showFlows);
			__nodes[i].setShowNaturalFlow(__showFlows);
			__nodes[i].setShowPointFlow(__showFlows);
			__nodes[i].setBoundsCalculated(false);
		}
		forceRepaint();
	}
	else if (action.equals(__MENU_SHOW_RIGHTS)) {
		if (__showRights) {
			__showRights = false;
		}
		else {
			__showRights = true;
		}
		for (int i = 0; i < __nodes.length; i++) {
			__nodes[i].setShowRights(__showRights);

			if (!__rightsQueried && __showRights == true) {
				__nodes[i].setRight(queryRight(__nodes[i]));
			}
			__nodes[i].setBoundsCalculated(false);
		}
		__rightsQueried = true;
		forceRepaint();
	}	
}

/**
Adds an annotation node to the drawing area at the specified point.
*/
public void addAnnotation(double x, double y) {
	addAnnotation(x, y, false);
}

/**
Adds an annotation node to the drawing area at the specified point.
*/
public void addAnnotation(double x, double y, boolean convert) {
	String text = new TextResponseJDialog(__parent,
		"Enter the annotation text",
		"Enter the annotation text:",
		ResponseJDialog.OK | ResponseJDialog.CANCEL).response();

	if (text == null) {
		return;
	}

	text = StringUtil.replaceString(text, "\"", "'");

	if (convert) {
		y = toSixDigits(convertY(y));
		x = toSixDigits(convertX(x));
	}

	y = toSixDigits(y);
	x = toSixDigits(x);

	HydroBase_Node node = new HydroBase_Node();
	String name = getUniquePropListName(__annotations.size());
	String position = "Center";
	String props = "Number=" + name + ";"
		+ "ShapeType=Text;"
		+ "FontSize=11;"
		+ "FontStyle=Plain;"
		+ "FontName=Arial;"
		+ "Point=" + x + ", " + y + ";"
		+ "Text=\"" + text + "\";"
		+ "TextPosition=" + position;
	PropList p = PropList.parse(props, name, ";");
	node.setAssociatedObject(p);
	GRLimits limits = GRDrawingAreaUtil.getTextExtents(
		__drawingArea, text, GRUnits.DEVICE);	
	double w = convertX(limits.getWidth()) - __dataLimits.getLeftX();
	double h = convertY(limits.getHeight(), false)
		- __dataLimits.getBottomY();

	// Calculate the actual limits for the from the lower-left corner to the upper-right,
	// in order to know when the text has been clicked on (for dragging, or popup menus).
	
	if (position.equalsIgnoreCase("UpperRight")) {
		node.setPosition(x, y, w, h);
	}
	else if (position.equalsIgnoreCase("Right")) {
		node.setPosition(x, y - (h / 2), w, h);
	}
	else if (position.equalsIgnoreCase("LowerRight")) {
		node.setPosition(x, y - h, w, h);
	}
	else if (position.equalsIgnoreCase("Below")
		|| position.equalsIgnoreCase("BelowCenter")) {
		node.setPosition(x - (w / 2), y - h, w, h);
	}
	else if (position.equalsIgnoreCase("LowerLeft")) {
		node.setPosition(x - w, y - h, w, h);
	}
	else if (position.equalsIgnoreCase("Left")) {
		node.setPosition(x - w, y - (h / 2), w, h);
	}
	else if (position.equalsIgnoreCase("UpperLeft")) {
		node.setPosition(x - w, y, w, h);
	}
	else if (position.equalsIgnoreCase("Above")
	        || position.equalsIgnoreCase("AboveCenter")) {
		node.setPosition(x - (w / 2), y, w, h);
	}
	else if (position.equalsIgnoreCase("Center")) {
		node.setPosition(x - (w / 2), y - (h / 2), w, h);
	}

	node.setDirty(true);
	__annotations.add(node);
	forceRepaint();
}

/**
Calculates the width of the widest text to be drawn in the key.
@return the width of the widest text to be drawn in the key.
*/
private double calculateWidestKeyText() {
	double widest = 0;
	GRLimits limits = null;

	for (int i = 0; i < __keyLabels.length; i++) {
		limits = GRDrawingAreaUtil.getTextExtents(
			__drawingArea, __keyLabels[i], GRUnits.DEVICE);
		if (limits.getWidth() > widest) {
			widest = limits.getWidth();
		}
	}

	if (!__dateString.equals("")) {
		limits = GRDrawingAreaUtil.getTextExtents(
			__drawingArea, __dateString, GRUnits.DEVICE);
		if (limits.getWidth() > widest) {
			widest = limits.getWidth();
		}
	}

	return widest;
}

/**
Given a certain annotation, updates the proplist that holds the annotation
info in reaction to the annotation being moved on the screen.
@param annotation the number of the annotation in the __annotations list to have the proplist updated.
*/
private void changeAnnotationLocation(int annotation) {
	HydroBase_Node node = (HydroBase_Node)__annotations.get(annotation);

	double x = node.getX();
	double y = node.getY();
	double w = node.getWidth();
	double h = node.getHeight();
		
	PropList p = (PropList)node.getAssociatedObject();
		
	String position = p.getValue("TextPosition");
		
	if (position.equalsIgnoreCase("UpperRight")) {
		p.setValue("Point", "" + x + "," + y);
	}
	else if (position.equalsIgnoreCase("Right")) {
		p.setValue("Point", "" + x + "," + (y + (h / 2)));
	}
	else if (position.equalsIgnoreCase("LowerRight")) {
		p.setValue("Point", "" + x + "," + (y + h));
	}
	else if (position.equalsIgnoreCase("Below")
		|| position.equalsIgnoreCase("BelowCenter")) {
		p.setValue("Point", "" + (x + (w / 2)) + "," + (y + h));
	}
	else if (position.equalsIgnoreCase("LowerLeft")) {
		p.setValue("Point", "" + (x + w) + "," + (y + h));
	}
	else if (position.equalsIgnoreCase("Left")) {
		p.setValue("Point", "" + (x + w) + "," + (y + (h / 2)));
	}
	else if (position.equalsIgnoreCase("UpperLeft")) {
		p.setValue("Point", "" + (x + w) + "," + y);
	}
	else if (position.equalsIgnoreCase("Above") || position.equalsIgnoreCase("AboveCenter")) {
		p.setValue("Point", "" + (x + (w / 2)) + "," + y);
	}
	else if (position.equalsIgnoreCase("Center")) {
		p.setValue("Point", "" + (x + (w / 2)) + "," + (y + (h / 2)));
	}
}

/**
Clears the display.
*/
public void clear() {
	_graphics.setColor(Color.white);
	_graphics.fillRect( 0, 0, getBounds().width, getBounds().height);
}

/**
Converts an x value from device units to data units.
@param x the x value in device units to convert.
@return the x value in device units.
*/
private double convertX(int x) {
	return convertX((double)x);
}

/**
Converts an x value from device units to data units.
@param x the x value in device units to convert.
@return the x value in device units.
*/
private double convertX(double x) {
	double dx = x * __mod;
	dx += __dataLimits.getLeftX();
	return dx;
}

/**
Converts an y value from device units to data units.
@param y the y value in device units to convert.
@return the y value in device units.
*/
private double convertY(int y) {
	return convertY((double)y);
}

/**
Converts an y value from device units to data units.
@param y the y value in device units to convert.
@return the y value in device units.
*/
private double convertY(double y) {
	return convertY(y, true);
}

/**
Converts an y value from device units to data units.
@param y the y value in device units to convert.
@param invert whether to invert the y value (so that low values are at the bottom of the screen,
opposite of normal java pixel numbering) prior to conversion.
@return the y value in device units.
*/
private double convertY(double y, boolean invert) {
	double dy = y;
	if (invert) {
		dy = invertY(dy);
	}
	dy *= __mod;
	dy += __dataLimits.getBottomY();
	return dy;
}

/**
Deletes the records for the current diagram from wis_diagram_data.
*/
private void deleteDatabaseData() {
	int x = new ResponseJDialog(__parent,
		"Delete Diagram Data?",
		"Do you want to delete the diagram data from the database?\n"
		+ "This will not affect WIS data -- only information directly "
		+ "related to\nthe nodes displayed in the diagram will be "
		+ "deleted.\nIf you select 'Yes', you will have to close the "
		+ "diagram via the 'Cancel'\nbutton and re-open it in order "
		+ "to see the effects.",
		ResponseJDialog.YES | ResponseJDialog.NO).response();
	
	if (x == ResponseJDialog.NO) {
		return;
	}

	try {
		__dmi.deleteWISDiagramDataForWis_num(__parent.getWis_num());
	}
	catch (Exception e) {
		String routine = CLASS + ".deleteDatabaseData";
		Message.printWarning(1, routine, "Error deleting data.");
		Message.printWarning(2, routine, e);
	}

	new ResponseJDialog(__parent,
		"Data Deleted",
		"Diagram data have been deleted.  To see the changes, close "
		+ "the diagram with the\n'Cancel' button and re-open.",
		ResponseJDialog.OK);
}

/**
Deletes the specified node from the diagram.  Should only be used on annotation nodes.
*/
private void deleteNode(int node) {
	if (!__isAnnotation) {
		return;
	}

	__annotations.remove(node);
	forceRepaint();
}

/**
Draws the key.
*/
private void drawLegend() {
	PageFormat pageFormat = getPageFormat();

	// Set the data limits for drawing the legend to not be the normal data units but pixels instead.
	// This way the drawing can be easily done in pixel units.
	// The data units will be re-set at the end of this method.

	useDeviceDataLimits();

	double x = 1;
	double y = (pageFormat.getHeight() / __printScale) - 2;

	double widestText = calculateWidestKeyText();

	int includeDate = 0;
	if (!__dateString.equals("")) {
		includeDate = 1;
	}
	double height = ((__keyLabels.length + includeDate) * 23) + 7;
	
	GRDrawingAreaUtil.setColor(__drawingArea, GRColor.black);
	GRDrawingAreaUtil.drawLine(__drawingArea, x, y,
		x + widestText + 40, y);
	GRDrawingAreaUtil.drawLine(__drawingArea, x, y - height,
		x + widestText + 40, y - height);
	GRDrawingAreaUtil.drawLine(__drawingArea, x, y,
		x, y - height);
	GRDrawingAreaUtil.drawLine(__drawingArea, x + widestText + 40, y,
		x + widestText + 40, y - height);

	int i = 0;
	for (i = 0; i < __keyLabels.length; i++) {
		if (i == 0) {
			GRDrawingAreaUtil.drawSymbol(__drawingArea,
				GRSymbolShapeType.TRIANGLE_RIGHT_HOLLOW, x + 5, y - 13 - (23 * i),
				HydroBase_Node.ICON_DIAM*2/3, GRUnits.DEVICE,
				GRSymbolPosition.CENTER_Y | GRSymbolPosition.LEFT );
		}
		else {
			GRDrawingAreaUtil.drawSymbol(__drawingArea,
				GRSymbolShapeType.CIRCLE_HOLLOW, x + 5, y - 13 - (23 * i),
				HydroBase_Node.ICON_DIAM*2/3, GRUnits.DEVICE,
				GRSymbolPosition.CENTER_Y | GRSymbolPosition.LEFT );
			GRDrawingAreaUtil.drawText(__drawingArea,
				__keyText[i], x + 15, y - 13 - (23 * i), 0,
				GRText.RIGHT| GRText.CENTER_Y);
		}
		GRDrawingAreaUtil.drawText(__drawingArea, __keyLabels[i],
			x + 30, y - 11 - (23 * i), 0, GRText.CENTER_Y);
	}

	if (!__dateString.equals("")) {
		GRDrawingAreaUtil.drawText(__drawingArea, __dateString,
			x + 30, y - 11 - (23 * i), 0, GRText.CENTER_Y);
	}

	useDataLimits();
}

/**
Draws the lines between all the nodes.
*/
private void drawDiagram() {
	HydroBase_Node ds = null;
	double[] p1;
	double[] p2;
	int reachMax = findMaxReachLevel();
	for (int i = 0; i < __nodes.length; i++) {
		ds = __nodes[i].getDownstreamNode();
		while ( ds != null
			&& (  ds.getNodeType().equals("Annotation")
			|| ds.getNodeType().equals("String")
			|| ds.getNodeType().equals("Stream")
			|| ds.getNodeType().equals("Label"))) {
			ds = ds.getDownstreamNode();
		}

		GRDrawingAreaUtil.setColor(__drawingArea, GRColor.blue);
		GRDrawingAreaUtil.setLineWidth(__drawingArea,
			reachMax - __nodes[i].getReachLevel() + 1);
		if (ds != null) {
			p1 = __nodes[i].getDrawToPoint(__drawingArea);	
			p2 = ds.getDrawToPoint(__drawingArea);
			GRDrawingAreaUtil.drawLine(__drawingArea, p1[0], p1[1],
				p2[0], p2[1]);
		}
	}

	// Set back to normal.
	GRDrawingAreaUtil.setLineWidth(__drawingArea, 1);
}

/**
Given the annotations read from the database,
fills in the information necessary to draw the annotation on the screen.
*/
private void fillAnnotationNodeData() {
	int size = __annotations.size();
	HydroBase_Node node = null;
	GRLimits limits = null;
	double x = 0;
	double y = 0;
	double w = 0;
	double h = 0;
	int fontSize = 0;
	String fontName = null;
	String fontStyle = null;
	String position = null;
	String point = null;
	String temp = null;
	PropList p = null;
	String text = null;
	for (int i = 0; i < size; i++) {
		node = (HydroBase_Node)__annotations.get(i);
		p = (PropList)node.getAssociatedObject();

		p.set("Number", "" + i);

		text = p.getValue("Text");
		fontName = p.getValue("FontName");
		fontStyle = p.getValue("FontStyle");
		fontSize = (Integer.valueOf(p.getValue("FontSize"))).intValue();
		limits = GRDrawingAreaUtil.getTextExtents(
			__drawingArea, text, GRUnits.DEVICE,
			fontName, fontStyle, fontSize);	

		w = convertX(limits.getWidth()) -__dataLimits.getLeftX();
		h = convertY(limits.getHeight(), false)
			- __dataLimits.getBottomY();
		
		point = p.getValue("Point");
		temp = StringUtil.getToken(point, ",", 0, 0);
		x = (Double.valueOf(temp)).doubleValue();
		temp = StringUtil.getToken(point, ",", 0, 1);
		y = (Double.valueOf(temp)).doubleValue();

		position = p.getValue("TextPosition");

		if (position.equalsIgnoreCase("UpperRight")) {
			node.setPosition(x, y, w, h);
		}
		else if (position.equalsIgnoreCase("Right")) {
			node.setPosition(x, y - (h / 2), w, h);
		}
		else if (position.equalsIgnoreCase("LowerRight")) {
			node.setPosition(x, y - h, w, h);
		}
		else if (position.equalsIgnoreCase("Below")
			|| position.equalsIgnoreCase("BelowCenter")) {
			node.setPosition(x - (w / 2), y - h, w, h);
		}
		else if (position.equalsIgnoreCase("LowerLeft")) {
			node.setPosition(x - w, y - h, w, h);
		}
		else if (position.equalsIgnoreCase("Left")) {
			node.setPosition(x - w, y - (h / 2), w, h);
		}
		else if (position.equalsIgnoreCase("UpperLeft")) {
			node.setPosition(x - w, y, w, h);
		}
		else if (position.equalsIgnoreCase("Above")
			|| position.equalsIgnoreCase("AboveCenter")) {
			node.setPosition(x - (w / 2), y, w, h);
		}
		else if (position.equalsIgnoreCase("Center")) {
			node.setPosition(x - (w / 2), y - (h / 2), w, h);
		}		
	}
	__annotationsProcessed = true;
	__initialSize = __annotations.size();
}

/**
Finds the highest reach level in the entire network.
@return the highest reach level in the entire network.
*/
private int findMaxReachLevel() {
	int reach = 0;
	for (int i = 0; i < __nodes.length; i++) {
		if (__nodes[i].getReachLevel() > reach) {
			reach = __nodes[i].getReachLevel();
		}
	}
	return reach;
}

/**
Finds the number of the node at the specified click.
@param x the x location of the click
@param y the y location of the click
@return the number of the node at the specified click.
*/
private int findNodeAtXY(double x, double y) {
	for (int i = (__nodes.length - 1); i >= 0; i--) {
		if (__nodes[i].contains(x, y) && __nodes[i].isVisible()) {
			__isAnnotation = false;
			return i;
		}
	}

	// If the node still wasn't found, search to see if an annotation was clicked on.

	int size = __annotations.size();
	HydroBase_Node node = null;
	GRLimits limits = null;
	for (int i = 0; i < size; i++) {
		node = (HydroBase_Node)__annotations.get(i);
		limits = new GRLimits(node.getX(), node.getY(),
			node.getX() + node.getWidth(),
			node.getY() + node.getHeight());
		if (limits.contains(x, y) && node.isVisible()) {
			__isAnnotation = true;
			return i;
		}
	}
	return -1;
}

/**
Forces the display to repaint itself.
*/
public void forceRepaint() {
	__forceRefresh = true;
	repaint();
}

/**
Returns the list of annotations.
@return the list of annotations.
*/
public List<HydroBase_Node> getAnnotations() {
	return __annotations;
}

/**
Returns the current data limits of the drawing area.
@return the current data limits of the drawing area.
*/
public GRLimits getDataLimits() {
	return __drawingArea.getDataLimits();
}

/**
Returns the drawing height.
@return the drawing height.
*/
public double getDrawingHeight() {
	return __height;
}

/**
Returns the drawing width.
@return the drawing width.
*/
public double getDrawingWidth() {	
	return __width;
}

/**
Returns the specified node.
@param nodeNum the number of the node in the node array or the annotation list.
@param isAnnotation whether to return an annotation node (false) or not (true).
@return the specified node.
*/
public HydroBase_Node getNode(int nodeNum, boolean isAnnotation) {
	if (__isAnnotation) {
		return __annotations.get(nodeNum);
	}
	else {
		return __nodes[nodeNum];
	}
}

/**
Returns the array of nodes in the display.
@return the array of nodes in the display.
*/
public HydroBase_Node[] getNodes() {
	return __nodes;
}

/**
Returns the page format for this GUI.
*/
public PageFormat getPageFormat() {
	String routine = CLASS + ".getPageFormat";
	PageFormat pageFormat = PrintUtil.getPageFormat("Letter");
	try {
		PrintUtil.setPageFormatOrientation(pageFormat, PageFormat.LANDSCAPE);
		PrintUtil.setPageFormatMargins(pageFormat, .75, .75, .75, .75);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error creating page format.");
		Message.printWarning(2, routine, e);
	}
	return pageFormat;
}

/**
Returns the preferred scrollable viewport size.
@return the preferred scrollable viewport size.
*/
public Dimension getPreferredScrollableViewportSize() {
	return __parent.getSize();
}

/**
Returns 20.
*/
public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
	return 20;
}

/**
Returns false.
*/
public boolean getScrollableTracksViewportHeight() {
	return false;
}

/**
Returns false.
*/
public boolean getScrollableTracksViewportWidth() {
	return false;
}

/**
Returns 20.
*/
public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
	return 20;
}

/**
Creates a unique name for a proplist.
@param num the number of proplists already in existence.
@return a unique name for a proplist.
*/
private String getUniquePropListName(int num) {
	int size = __annotations.size();
	PropList p = null;
	boolean match = false;
	HydroBase_Node node = null;
	while (true) {
		match = false;
		for (int i = 0; i < size; i++) {
			node = __annotations.get(i);
			p = (PropList)node.getAssociatedObject();
			if (p.getValue("Number").equals("" + num)) {
				match = true;
			}
		}

		if (!match) {
			return "" + num;
		}
		num++;	
	}
}

/**
Inverts Y from the typical Java-style (lower Y values are higher) to lower Y values are lower.
@param y the y value to invert.
@return the inverted Y value.
*/
private double invertY(double y) {
	return _devy2 - y;
}

/**
Sets whether the data has been edited or not.
@param dirty whether the data has been edited (true) or not.
*/
public void setDirty(boolean dirty) {
	__dirty = dirty;

	if (__dirty) {
		return;
	}

	for (int i = 0; i < __nodes.length; i++) {
		__nodes[i].setDirty(false);
	}
	__initialSize = __annotations.size();
	HydroBase_Node node = null;
	for (int i = 0; i < __initialSize; i++) {
		node = __annotations.get(i);
		node.setDirty(false);
	}
}

/**
Returns whether the data has been edited or not.
@return whether the data has been edited or not.
*/
public boolean isDirty() {
	if (__dirty) {
		return true;
	}
	for (int i = 0; i < __nodes.length; i++) {
		if (__nodes[i].isDirty()) {
			return true;
		}
	}
	int size = __annotations.size();
	if (size != __initialSize) {
		return true;
	}
	HydroBase_Node node = null;
	for (int i = 0; i < size; i++) {
		node = __annotations.get(i);
		if (node.isDirty()) {
			return true;
		}
	}
	return false;
}

/**
Does nothing.
*/
public void mouseClicked(MouseEvent event) {
}

/**
If a node is currently being dragged, repaints the bounding box at the specified point.
@param event the MouseEvent that happened.
*/
public void mouseDragged(MouseEvent event) {
	double x = convertX(event.getX());
	double y = convertY(event.getY());
	if (__inDrag) {
		__x = x;
		__y = y;
		repaint();
	}
}

/**
Does nothing.
*/
public void mouseEntered(MouseEvent event) {
}

/**
Does nothing.
*/
public void mouseExited(MouseEvent event) {
}

/**
Does nothing.
*/
public void mouseMoved(MouseEvent event) {
}

/**
Determines the node that is being dragged around the screen.
@param event the MouseEvent that happened.
*/
public void mousePressed(MouseEvent event) {
	if (!__editable) {
		return;
	}

	__xAdjust = 0;
	__yAdjust = 0;
	
	// Do not respond to popup events.
	if (event.getButton() == MouseEvent.BUTTON1) {
		double x = convertX(event.getX());
		double y = convertY(event.getY());
		__leftClickNode = findNodeAtXY(x, y);
		// If a node was clicked on.
		if (__leftClickNode > -1) {
			__x = x;
			__y = y;
			if (__isAnnotation) {
				HydroBase_Node node = __annotations.get(__leftClickNode);
				__nodeLimits = new GRLimits(node.getX(),
					node.getY(),
					node.getX() + node.getWidth(),
					node.getY() + node.getHeight());
				node.setVisible(false);
			}
			else {
				__nodeLimits = __nodes[__leftClickNode].getLimits();
				__nodes[__leftClickNode].setVisible(false);
			}

			__xAdjust = x - __nodeLimits.getMinX();
			__yAdjust = y - __nodeLimits.getMinY();
			__inDrag = true;
			__eraseNode = true;
			forceRepaint();
		}
	}
}

/**
Shows the popup menu if the popup mouse button was pressed,
or drops the dragged node at the new position on the screen.
@param event the MouseEvent that happened.
*/
public void mouseReleased(MouseEvent event) {
	double x = convertX(event.getX());
	double y = convertY(event.getY());

	if (event.isPopupTrigger()) {
		int node = findNodeAtXY(x, y);
		__x = x - __xAdjust;
		__y = y - __yAdjust;			
		if (node < 0) {		
			__popup.show(event.getComponent(), event.getX(),
				event.getY());
		}
		else {
			if (__isAnnotation) {
				__labelPopup.show(event.getComponent(),
					event.getX(), event.getY());
			}
			else {
				__nodePopup.show(event.getComponent(),
					event.getX(), event.getY());
			}
			__popUpNode = node;
		}
	}
	else {
		if (!__editable) {
			return;
		}
		__inDrag = false;
		if (__leftClickNode > -1) {
			if (__isAnnotation) {
			
			HydroBase_Node node = __annotations.get(__leftClickNode);
			node.setVisible(true);
			__x = x - __xAdjust;
			__y = y - __yAdjust;

			// Prevent nodes from being dragged off the drawing area completely.
			if (__x < __dataLimits.getLeftX()) {
				__x = __dataLimits.getLeftX();
			}
			if (__y < __dataLimits.getBottomY()) {
				__y = __dataLimits.getBottomY();
			}
			if (__x + node.getWidth() >= __dataLimits.getRightX()) {
				__x = __dataLimits.getRightX()
					- node.getWidth();
			}
			if (__y + node.getHeight() >= __dataLimits.getTopY()) {
				__y = __dataLimits.getTopY()
					- node.getHeight();
			}
			
			node.setX(__x);
			node.setY(__y);

			changeAnnotationLocation(__leftClickNode);
			
			node.setDirty(true);
			__leftClickNode = -1;
			forceRepaint();

			}
			else {
			
			__nodes[__leftClickNode].setVisible(true);
			__x = x;
			__y = y;

			// Prevent nodes from being dragged off the drawing area completely.
			if (__x < __dataLimits.getLeftX()
				+ __nodes[__leftClickNode].getWidth() / 2) {
				__x = __dataLimits.getLeftX()
					+ __nodes[__leftClickNode].getWidth()
					/ 2;
			}
			if (__y < __dataLimits.getBottomY()
				+ __nodes[__leftClickNode].getHeight() / 2) {
				__y = __dataLimits.getBottomY()
					+ __nodes[__leftClickNode].getHeight()
					/ 2;
			}
			if (__x > __dataLimits.getRightX()
				- __nodes[__leftClickNode].getWidth() /2) {
				__x = __dataLimits.getRightX()
					- __nodes[__leftClickNode].getWidth()
					/ 2;
			}
			if (__y > __dataLimits.getTopY()
				- __nodes[__leftClickNode].getHeight() / 2) {
				__y = __dataLimits.getTopY()
					- __nodes[__leftClickNode].getHeight()
					/ 2;
			}
			
			__nodes[__leftClickNode].setX(__x);
			__nodes[__leftClickNode].setY(__y);

			__nodes[__leftClickNode].setDirty(true);
			__leftClickNode = -1;
			forceRepaint();
			}
		}
	}
}

/**
Paints the screen.
@param g the Graphics context to use for painting.
*/
public void paint(Graphics g) {
	if (!__ready) {
		return;
	}
	// Sets the graphics in the base class appropriately
	// (double-buffered if doing double-buffered drawing, single-buffered if not).

	Graphics2D g2 = (Graphics2D)g;
	setGraphics(g2);
	setAntiAlias(true);

	// Set up drawing limits based on current window size.
	setLimits(getLimits(true));

	// First time through, do the following.
	if (__initialize) {
		// One time ONLY, do the following.
		if (__onceEver) {
			__height = getBounds().height;
			__width = getBounds().width;	

			__onceEver = false;
		}
		__initialize = false;
		setupDoubleBuffer(0, 0, getBounds().width, getBounds().height);
		
		for (int i = 0; i < __nodes.length; i++) {
			__nodes[i].calculateBounds(__drawingArea);
		}
	
		repaint();
		__forceRefresh = true;
		__dataLimits = __drawingArea.getDataLimits();
		__drawingLimits = __drawingArea.getDrawingLimits();
		if (__drawingLimits.getHeight() > __drawingLimits.getWidth()) {
			__mod = __dataLimits.getHeight()
				/ __drawingLimits.getHeight();
		}
		else {
			__mod = __dataLimits.getWidth()
				/ __drawingLimits.getWidth();
		}

	}
	else {
		// Check to see if the bounds of the device have changed.
		// If they have then the GUI window has been resized and the double buffer size needs changed accordingly.
		if (__height != getBounds().height || __width != getBounds().width) {
			__height = getBounds().height;
			__width = getBounds().width;				
			GRLimits limits = new GRLimits(0, 0, getBounds().width,
				getBounds().height);
			__drawingArea.setDrawingLimits(limits, GRUnits.DEVICE, GRLimits.DEVICE);
			setupDoubleBuffer(0, 0, getBounds().width,
				getBounds().height);
			__forceRefresh = true;
		}
	}

	// Only do the following if explicitly instructed to.
	if (__forceRefresh) {
		GRDrawingAreaUtil.setLineWidth(__drawingArea, 1);
		GRDrawingAreaUtil.setFont(__drawingArea, "Arial", "Plain", 11);
		JGUIUtil.setWaitCursor(__parent, true);

		// If the currently specified node is set to be erased,
		// then a box that is 200 pixels wider and taller
		// (to account for dropshadows and anything else)
		// is created around the node to erase, and clipping bounds are set on that.
		// The call to clear() after this if(){} will then only clear the region inside the clipping box.
		// Drawing (such as of relationship lines) will only occur inside the clipping box.
		__eraseNode = false;
		if (__eraseNode) {
			GRLimits limits =
				new GRLimits((__x - __xAdjust - 100),
				invertY(__y - __yAdjust - 100),
				__x - __xAdjust + __nodeLimits.getWidth() + 100,
				invertY(__y + __nodeLimits.getHeight() - __yAdjust + 100));

			setClip(limits);
		}

		clear();	
		
		setAntiAlias(true);
		drawLegend();
		
		if (__nodes == null) {
			showDoubleBuffer(g2);		
			return;
		}

		setAntiAlias(true);
		drawDiagram();
	
		setAntiAlias(true);
		// Draw the nodes if they are visible.
		for (int i = 0; i < __nodes.length; i++) {
			if (__nodes[i].isVisible()) {
		    	__nodes[i].draw(__drawingArea);
			}
		}
	
		if (__annotations.size() != 0 && !__annotationsProcessed) {
			// Annotations were read from the database.
			fillAnnotationNodeData();
		}		

		setAntiAlias(true);
		int size = __annotations.size();
		HydroBase_Node node = null;
		for (int i = 0; i < size; i++) {
			if (i == __leftClickNode && __isAnnotation) {
				// Skip it.  Outline being drawn for drag.
				continue;
			}
			node = __annotations.get(i);

			GRDrawingAreaUtil.drawAnnotation(__drawingArea, (PropList)node.getAssociatedObject());

			if (__showAnnotationBoundingBox) {
			GRDrawingAreaUtil.setColor(__drawingArea, GRColor.red);
			GRDrawingAreaUtil.drawRectangle(__drawingArea,
				node.getX(), node.getY(), node.getWidth(),
				node.getHeight());
			}
		}
	
		setAntiAlias(true);
		// If the grid should be drawn, do so.
		if (__drawInchGrid) {
			useDeviceDataLimits();
			String s = null;
			GRDrawingAreaUtil.setColor(__drawingArea, GRColor.red);
			for (int i = 0; i < 100000; i+= ((72/__printScale)/2)) {
				GRDrawingAreaUtil.drawLine(__drawingArea, i, 0,
					i, 100000);
				GRDrawingAreaUtil.drawLine(__drawingArea, 0, i,
					100000, i);				
				s = StringUtil.formatString(
					((double)i/(72/__printScale)),"%10.1f");
				GRDrawingAreaUtil.drawText(__drawingArea,
					s,i,
					0, 0, GRText.CENTER_X | GRText.BOTTOM);
				s = StringUtil.formatString(
					((double)i/(72/__printScale)),"%10.1f");
				GRDrawingAreaUtil.drawText(__drawingArea,
					s,0,
					i, 0, GRText.CENTER_Y | GRText.LEFT);
			}
			useDataLimits();
		}
		
		setAntiAlias(true);
		if (__drawPixelGrid) {
			useDeviceDataLimits();
			GRDrawingAreaUtil.setColor(__drawingArea,GRColor.green);
			for (int i = 0; i < 100000; i+= 50) {
				GRDrawingAreaUtil.drawLine(__drawingArea, i, 0,
					i, 100000);
				GRDrawingAreaUtil.drawLine(__drawingArea, 0, i,
					100000, i);
				GRDrawingAreaUtil.drawText(__drawingArea,
					("" + i) ,i, 0, 0,
					GRText.CENTER_X | GRText.BOTTOM);
				GRDrawingAreaUtil.drawText(__drawingArea,
					("" + i),0, i, 0,
					GRText.CENTER_Y | GRText.LEFT);
			}			
			useDataLimits();
		}
		__forceRefresh = false;		

		setAntiAlias(true);
		if (__drawMargin) {
			useDeviceDataLimits();
			GRDrawingAreaUtil.setColor(__drawingArea, GRColor.cyan);
			PageFormat pageFormat = getPageFormat();
			double leftX = pageFormat.getImageableX() /__printScale;
			double topY = (pageFormat.getHeight() - pageFormat.getImageableY()) / __printScale;
			double rightX = (leftX + pageFormat.getImageableWidth()
				/ __printScale) - 1;
			double bottomY = ((pageFormat.getHeight()
				- (pageFormat.getImageableY() + pageFormat.getImageableHeight()))
				/ __printScale) + 1;
			GRDrawingAreaUtil.drawLine(__drawingArea, leftX, topY, leftX, bottomY);
			GRDrawingAreaUtil.drawLine(__drawingArea, rightX, topY, rightX, bottomY);
			GRDrawingAreaUtil.drawLine(__drawingArea, leftX, topY, rightX, topY);
			GRDrawingAreaUtil.drawLine(__drawingArea, leftX, bottomY, rightX, bottomY);
			useDataLimits();
		}

		// If the clipping area was set up above, un-set it with a call to setClip(null).
		if (__eraseNode) {
			setClip(null);
			__eraseNode = false;
		}
		JGUIUtil.setWaitCursor(__parent, false);
	}
	
	// Displays the graphics.
	showDoubleBuffer(g2);
	setAntiAlias(true);
	// If a table is currently being dragged around the screen,
	// draw the outline of the table on top of the double-buffer.
	if (__inDrag) {
		_graphics = (Graphics2D)g2;
		GRDrawingAreaUtil.setColor(__drawingArea, GRColor.black);
		GRDrawingAreaUtil.drawLine(__drawingArea,
			__x - __xAdjust - 2,
			__y - __yAdjust - 2,
			__nodeLimits.getWidth() + __x - __xAdjust + 2,
			__y - __yAdjust - 2);
		GRDrawingAreaUtil.drawLine(__drawingArea,
			__x - __xAdjust - 2,
			__y + __nodeLimits.getHeight() - __yAdjust + 2,
			__nodeLimits.getWidth() + __x - __xAdjust + 2,
			__y + __nodeLimits.getHeight() - __yAdjust + 2);
		GRDrawingAreaUtil.drawLine(__drawingArea,
			__x - __xAdjust - 2,
			__y - __yAdjust - 2,
			__x - __xAdjust - 2,
			__y + __nodeLimits.getHeight() - __yAdjust + 2);
		GRDrawingAreaUtil.drawLine(__drawingArea,
			__x + __nodeLimits.getWidth() - __xAdjust + 2,	
			__y - __yAdjust - 2,
			__x + __nodeLimits.getWidth() - __xAdjust + 2,
			__y + __nodeLimits.getHeight() - __yAdjust + 2);
	}	
}

/**
Sets up a print job and submits it.
*/
public void print() {
	String routine = CLASS + ".print";
	PrinterJob printJob = PrinterJob.getPrinterJob();

	PageFormat pageFormat = getPageFormat();

	printJob.setPrintable(this, pageFormat);

	try {
	PrintUtil.print(this, pageFormat);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error printing");
		Message.printWarning(2, routine, e);
	}
}

/**
Prints a page.
@param g the Graphics context to which to print.
@param pageFormat the pageFormat to use for printing.
@param pageIndex the index of the page to print.
@return Printable.NO_SUCH_PAGE if no page should be printed,
or Printable.PAGE_EXISTS if a page should be printed.
*/
public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
	if (pageIndex > 0) {
		return NO_SUCH_PAGE;
	}

	Graphics2D g2d = (Graphics2D)g;
	// Set for the GRDevice because we will temporarily use  that to do the drawing.

	double transX = 0;
	double transY = 0;
	
	if (!StringUtil.startsWithIgnoreCase(
		PrintUtil.pageFormatToString(pageFormat),
		"Plotter")) {
	if (pageFormat.getOrientation() == PageFormat.LANDSCAPE) {
		transX = pageFormat.getImageableX();
//		transY = pageFormat.getHeight()
//			- (pageFormat.getImageableHeight()
//			+ pageFormat.getImageableY());
		transY = pageFormat.getImageableY();
	}
	else {
		transX = pageFormat.getImageableX();
		transY = pageFormat.getImageableY();
	}
	}

//	g2d.scale(__printScale, __printScale);
	// .57 because:
	// 9.5 inches (.75 inch margin on L & R) * 72 = 684.
	// 684 / 1200 (size of drawing area in pixels) = .57
	g2d.translate(transX, transY);
	g2d.scale(0.56, 0.56);

	paint(g2d);

	return PAGE_EXISTS;
}

/**
Queries out call information for a node.
@param node the node for which to query call information.
@return the call information that was read from the database,
or DMIUtil.MISSING_STRING if the node has no call information.
*/
private String queryCall(HydroBase_Node node) {
	String id = node.getCommonID();
	if (!StringUtil.startsWithIgnoreCase(id, "wdid:")) {
		return DMIUtil.MISSING_STRING;
	}
	
	int index = id.indexOf(":");
	id = id.substring(index + 1);
	
	try {
		int[] wdid = HydroBase_WaterDistrict.parseWDID(id);
		DateTime date = __parent.getWIS().getAdminDateTime();

		List<HydroBase_Calls> v = __dmi.readCallsListForWISDiagram(wdid[0], wdid[1],date);
	
		if (v == null || v.size() == 0) {
			return DMIUtil.MISSING_STRING;
		}
		else {
			HydroBase_Calls c = (HydroBase_Calls)v.get(0);
			DateTime set = new DateTime(c.getDate_time_set());
			DateTime apro = new DateTime(c.getApro_date());
			String dcr = c.getDcr_amt();
			index = dcr.indexOf(" ");
			if (index > -1) {
				dcr = dcr.substring(0, index);
			}
			return "\nCall set "
				+ set.toString(DateTime.FORMAT_YYYY_MM_DD) + " for "
				+ apro.toString(DateTime.FORMAT_YYYY_MM_DD) + " " + dcr + " CFS";
		}
	}
	catch (Exception e) {
		Message.printWarning(2, "queryCall", "Error reading from database.");
		Message.printWarning(2, "queryCall", e);
		return DMIUtil.MISSING_STRING;
	}
}

/**
Queries right information for the specified node.
@param node the node for which to query right information.
@return the right information or DMIUtil.MISSING_STRING if the node has no right information.
*/
private String queryRight(HydroBase_Node node) {
	String id = node.getCommonID();
	if (!StringUtil.startsWithIgnoreCase(id, "wdid:")) {
		return DMIUtil.MISSING_STRING;
	}
	
	int index = id.indexOf(":");
	id = id.substring(index + 1);
	
	try {
		int[] wdid = HydroBase_WaterDistrict.parseWDID(id);
		List<String> where = new ArrayList<String>();

		where.add("net_rate_abs > 0");
		where.add("wd = " + wdid[0]);
		where.add("id = " + wdid[1]);

		List<HydroBase_NetAmts> v = __dmi.readNetAmtsList(DMIUtil.MISSING_INT, wdid[0], wdid[1], true, "72");
	
		int size = v.size();
		if (size == 0) {
			return DMIUtil.MISSING_STRING;
		}
		else {	
			DateTime d = null;
			HydroBase_NetAmts n = null;
			String ret = "";
			for (int i = 0; i < size; i++) {	
				n = (HydroBase_NetAmts)v.get(i);
				d = new DateTime(n.getApro_date());
				ret += "\n"
					+ d.toString(DateTime.FORMAT_YYYY_MM_DD)
					+ " = "
					+ StringUtil.formatString(
					n.getNet_rate_abs(), "%12.2f").trim()
					+ " CFS";
			}
			return ret;
		}
	}
	catch (Exception e) {
		Message.printWarning(2, "queryRight", "Error reading from database.");
		Message.printWarning(2, "queryRight", e);	
		return DMIUtil.MISSING_STRING;
	}
}

/**
Sets the list of annotations.
@param annotations the annotations list to use.
*/
public void setAnnotations(List<HydroBase_Node> annotations) {
	__annotations = annotations;
}

/**
Sets the drawing area to be used with this device.
@param drawingArea the drawingArea to use with this device.
*/
public void setDrawingArea(HydroBase_DrawingArea_WISDiagram drawingArea) {
	__drawingArea = drawingArea;
}

/**
Sets whether the nodes can be changed or not.
*/
public void setEditable(boolean editable) {
	__editable = editable;
}

/**
Sets the network of nodes and the annotations that will be drawn.
@param network non-null Node Network
@param labels Vector of nodes that are just labels on the screen.
*/
public void setNetwork(HydroBase_NodeNetwork network) {
	__network = network;
	__network.setInWIS(true);
	__ready = true;
	boolean done = false;
	List<HydroBase_Node> nodes = new ArrayList<HydroBase_Node>();

	HydroBase_Node node = null;
	HydroBase_Node holdNode = null;
	node = __network.getMostUpstreamNode();
	done = false;
	String type = null;

	while (!done) {
		if (node.getType() == HydroBase_Node.NODE_TYPE_END) {
			done = true;
		}		

		type = HydroBase_Node.getVerboseWISType(node.getType()).trim();

		if (!type.equalsIgnoreCase("Annotation")
		    && !type.equalsIgnoreCase("Stream")
		    && !type.equalsIgnoreCase("String")
		    && !type.equalsIgnoreCase("Label")) {
			nodes.add(node);
		}

		holdNode = node;	
		node = HydroBase_NodeNetwork.getDownstreamNode(node,
			HydroBase_NodeNetwork.POSITION_COMPUTATIONAL);		
		if (node == holdNode) {
			done = true;
		}			
	}		

	int size = nodes.size();

	__nodes = new HydroBase_Node[size];

	for (int i = 0; i < size; i++) {
		__nodes[i] = (HydroBase_Node)nodes.get(i);
		__nodes[i].setBoundsCalculated(false);
	}
}

/**
Sets the date to display in the legend as the WIS date.
@param s the date to display in the legend.
*/
protected void setWISDate(String s) {
	__dateString = s;
}

/**
Takes a double and trims its decimal values so that it only has 6 places of precision.
@param d the double to trim.
@return the same double with only 6 places of precision.
*/
private double toSixDigits(double d) {
	String s = StringUtil.formatString(d, "%20.6f");
	Double D = Double.valueOf(s);
	return D.doubleValue();
}

/**
Updates one of the nodes with location and text information stored in the passed-in node.
@param nodeNum the number of the node (in the node array) to update.
@param node the node holding information with which the other node should
be updated.
*/
public void updateNode(int nodeNum, HydroBase_Node node, boolean isAnnotation) {
	if (__isAnnotation) {
		PropList p = (PropList)node.getAssociatedObject();

		HydroBase_Node vNode = (HydroBase_Node)__annotations.get( nodeNum);
		PropList vp = (PropList)vNode.getAssociatedObject();
		vNode.setAssociatedObject(p);

		String text = p.getValue("Text");
		boolean labelChanged = false;
		if (!text.equals(vp.getValue("Text"))) {
			vp.setValue("Text", text);
			labelChanged = true;
		}
	
		String val = p.getValue("Point").trim();
		String position = p.getValue("TextPosition");
		String fontSize = p.getValue("FontSize");

		if (!val.equals(vp.getValue("Point"))
			|| labelChanged
			|| !position.equals(vp.getValue("TextPosition"))
			|| !fontSize.equals(vp.getValue("FontSize"))) {

			int size = Integer.valueOf(p.getValue("FontSize")).intValue();

			GRLimits limits = GRDrawingAreaUtil.getTextExtents(
				__drawingArea, text, GRUnits.DEVICE,
				p.getValue("FontName"), p.getValue("FontStyle"),
				size);	

			double w = convertX(limits.getWidth()) - __dataLimits.getLeftX();
			double h = convertY(limits.getHeight(), false) - __dataLimits.getBottomY();

			if (!val.equals(vp.getValue("Point"))) {
				vp.setValue("Point", val);
				vNode.setDirty(true);
			}
			if (!position.equals(vp.getValue("TextPosition"))) {
				vp.setValue("TextPosition", position);
				vNode.setDirty(true);
			}
		
			String temp = StringUtil.getToken(val, ",", 0, 0);
			double x = (Double.valueOf(temp)).doubleValue();
			temp = StringUtil.getToken(val, ",", 0, 1);
			double y = (Double.valueOf(temp)).doubleValue();
	
			if (position.equalsIgnoreCase("UpperRight")) {
				vNode.setPosition(x, y, w, h);
				vNode.setDirty(true);
			}
			else if (position.equalsIgnoreCase("Right")) {
				vNode.setPosition(x, y - (h / 2), w, h);
			}
			else if (position.equalsIgnoreCase("LowerRight")) {
				vNode.setPosition(x, y - h, w, h);
			}
			else if (position.equalsIgnoreCase("Below")
				|| position.equalsIgnoreCase("BelowCenter")) {
				vNode.setPosition(x - (w / 2), y - h, w, h);
			}
			else if (position.equalsIgnoreCase("LowerLeft")) {
				vNode.setPosition(x - w, y - h, w, h);
			}
			else if (position.equalsIgnoreCase("Left")) {
				vNode.setPosition(x - w, y - (h / 2), w, h);
			}
			else if (position.equalsIgnoreCase("UpperLeft")) {
				vNode.setPosition(x - w, y, w, h);
			}
			else if (position.equalsIgnoreCase("Above")
				|| position.equalsIgnoreCase("AboveCenter")) {
				vNode.setPosition(x - (w / 2), y, w, h);
			}
			else if (position.equalsIgnoreCase("Center")) {
				vNode.setPosition(x - (w / 2), y - (h / 2),
					w, h);
			}		
		}

		val = p.getValue("FontName");
		if (!val.equals(vp.getValue("FontName"))) {
			vNode.setDirty(true);
			vp.setValue("FontName", val);
		}

		val = p.getValue("FontSize");
		if (!val.equals(vp.getValue("FontSize"))) {
			vNode.setDirty(true);
			vp.setValue("FontSize", val);
		}

		val = p.getValue("FontStyle");
		if (!val.equals(vp.getValue("FontStyle"))) {
			vNode.setDirty(true);
			vp.setValue("FontStyle", val);
		}
		vNode.setAssociatedObject(vp);
	}
	else {
		if (__nodes[nodeNum].getX() != node.getX()) {
			__nodes[nodeNum].setDirty(true);
			__nodes[nodeNum].setX(node.getX());
		}
		if (__nodes[nodeNum].getY() != node.getY()) {
			__nodes[nodeNum].setDirty(true);
			__nodes[nodeNum].setY(node.getY());
		}
		if (__nodes[nodeNum].getLabelDirection()
		    != node.getLabelDirection()) {
			__nodes[nodeNum].setDirty(true);
			__nodes[nodeNum].setLabelDirection(
				node.getLabelDirection());
		}
		__nodes[nodeNum].calculateBounds(__drawingArea);
	}
	forceRepaint();
}

/**
Updates the point flow value for the node with the specified unique ID string.
@param id the id of the node for which to update the point flow value.
@param pf the new point flow value.
*/
protected void updatePointFlow(String id, double pf) {
	id = id.trim();
	for (int i = 0; i < __nodes.length; i++) {
		if (__nodes[i].getCommonID().trim().equalsIgnoreCase(id)) {
			__nodes[i].setPointFlow(pf);
			return;
		}
	}
}

/**
If called, sets the drawing area to use its data limits as the data limits.
See useDeviceDataLimits().
*/
private void useDataLimits() {
	__drawingArea.setDataLimits(__dataLimits);
}

/**
If called, sets the drawing area to use device units as the data limits.
See useDataLimits().
*/
private void useDeviceDataLimits() {
	PageFormat pageFormat = getPageFormat();

	GRLimits deviceLimits = new GRLimits(0, 0,
		pageFormat.getWidth() / __printScale,
		pageFormat.getHeight() / __printScale);
	__drawingArea.setDataLimits(deviceLimits);
}

}