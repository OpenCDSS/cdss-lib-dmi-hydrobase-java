//-----------------------------------------------------------------------------
// HydroBase_GUI_WIS - WIS Information GUI 
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 25 Aug 1997	DLG, RTi 		Created initial version.
// 16 Feb 1998	DLG, RTi		Updated to java 1.1 event model,
//					added popup menu for cell attributes.
// 20 Feb 1998	DLG, RTi		Added the option to compute gain/loss
//					via weight coefficent or stream mile.
// 23 Feb 1998	DLG, RTi		Reworked GUI layout so that Archive 
//					will stand out more. Satisfies 
//					CRDSS-00394 log.
// 02 Apr 1999	Steven A. Malers, RTi	Add HBDMI to queries.
// 07 Apr 1999	SAM, RTi		Update for database changes.
// 21 May 1999	SAM, RTi		Remove HBData.TIME_ZONE_DATA reference.
// 08 Sep 1999	SAM, RTi		Now that there is a database to test,
//					change the isKnownPoint methods to
//					not consider dry river conditions.  For
//					computations, check for dry river
//					manually.
// 09 Mar 2000	SAM, RTi		Add code to repair WIS that has missing
//					data records.
// 09 Apr 2001	SAM, RTi		Add ability to do PopupMenu on right
//					mouse click for diversion coding
//					editing.  Change GUI to GUIUtil.
//					Add finalize() method and start to
//					work on memory cleanup.  Add graph
//					right-click menu item and remove graph
//					button from bottom.  Use some static
//					lookup methods to help share code
//					between HBWISGUI and HBWISBuilderGUI.
// 14 Jul 2001	SAM, RTi		Change so that if the user has turned
//					diversion coding on, then when a new
//					WIS is loaded, carry forward records
//					for the sheet are automatically added.
//					Then the records are modified as the
//					user enters diversion coding
//					information.  Change so the records are
//					held in memory until the user indicates
//					that the WIS should be saved.
// 2002-02-20	SAM, RTi		Change TSViewGUI to TSViewFrame.
// 2002-03-02	SAM, RTi		Change the Archive button to Save.
// 2002-06-18	SAM, RTi		If an entire row is selected, graph
//					all the data rows.
//-----------------------------------------------------------------------------
// 2003-10-08	J. Thomas Sapienza, RTi	Initial Swing version.
// 2003-10-20	JTS, RTi		* Added initial worksheet code.
//					* Started removing __wisDataVector
//					  code and moving it into the worksheet.
// 2003-11-17	JTS, RTi		* Continued work on WIS.
//					* Opened the Load GUI.
// 2003-11-28	JTS, RTi		Added reference to the parent JFrame.
// 2003-12-03	JTS, RTi		Can now export the text in HTML format.
// 2004-03-10	JTS, RTi		Added code so that a WIS can be opened
//					in read-only form.
// 2004-05-21	JTS, RTi		* Added getColumnTSDataType().
//					* Opened TS reading.
// 2004-05-24	JTS, RTi		graphCell() now allows the graphing
//					of multiple cells at once.
// 2005-02-15	JTS, RTi		Converted most queries except for 
//					readTimeSeries to use stored procedures.
// 2005-03-09	JTS, RTi		HydroBase_SheetName 	
//					  -> HydroBase_WISSheetName.
// 2005-04-28	JTS, RTi		Added all data members to finalize().
// 2005-05-09	JTS, RTi		All structure queries now return
//					structure view objects.
// 2005-05-25	JTS, RTi		Converted queries that pass in a 
//					String date to pass in DateTimes 
//					instead.
// 2005-06-22	JTS, RTi		* Column widths now come from the 
//					  table model, not the cell renderer.
//					* The table-specific cell renderers 
//					  were removed and replaced with a 
//					  single generic one.
// 2005-08-04	JTS, RTi		Code has been modified so that if
//					a user opens a WIS for which the format
//					has changed (because a new format was
//					built in the WIS Builder) the old data
//					is fit into the new format gracefully.
// 2007-02-08	SAM, RTi		Remove dependence on CWRAT.
//					Pass a JFrame to the constructor.
//					Add GeoViewUI for map interaction.
//					Clean up based on Eclipse feedback.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import RTi.DMI.DMIUtil;
import RTi.GRTS.TSViewJFrame;
import RTi.TS.TS;
import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.JWorksheet_CellAttributes;
import RTi.Util.GUI.ReportJFrame;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.SimpleJComboBox;
import RTi.Util.GUI.SimpleJMenuItem;
import RTi.Util.IO.HTMLWriter;
import RTi.Util.IO.IOUtil;
import RTi.Util.IO.PrintJGUI;
import RTi.Util.IO.PropList;
import RTi.Util.Math.MathUtil;
import RTi.Util.Message.Message;
import RTi.Util.String.StringUtil;
import RTi.Util.Time.DateTime;
import RTi.Util.Time.DateTimeBuilderJDialog;
import RTi.Util.Time.TimeUtil;
import RTi.Util.Time.TimeZoneDefaultType;
import RTi.Util.Time.YearType;

/**
This class manipulates a pre-built WIS(water information sheet).  There are
three representations of the sheet:
<p>
<ul>
<li>	__wisFormatVector - Vector of the HydroBase_WISFormat instances 
	(used to store
	description of the sheet including row types, etc.)</li>
<li>	__wisDataVector - Vector of the HydroBase_WISData instances (used to 
	store persistent WIS data for database)</li>
<li>	__network - stream network as an HydroBase_NodeNetwork (used for stream
	traversal)</li>
<li>	__worksheet - Visual component used to store information by cell 
	(used to
	display to the user).</li>
</ul>
<p>

These three storage mechanisms work together to display, analyze, and
input/output the WIS.
*/
public class HydroBase_GUI_WIS 
extends JFrame 
implements ActionListener, ItemListener, KeyListener, MouseListener, 
WindowListener {

/**
Class name.
*/
public final static String CLASS = "HydroBase_GUI_WIS";

/**
WIS column numbers.
*/
public final static int		
	ROW_LABEL_COL = 	1,
	POINT_FLOW_COL = 	2,
	NATURAL_FLOW_COL = 	3,
	DELIVERY_FLOW_COL = 	4,
	GAIN_LOSS_COL = 	5,
	RELEASES_COL = 		6,
	PRIORITY_DIV_COL = 	7,
	DELIVERY_DIV_COL = 	8,
	TRIB_NATURAL_COL = 	9,
	TRIB_DELIVERY_COL = 	10,
	COMMENTS_COL = 		11,
	NUM_COLUMNS = 		11;

/**
WIS column widths.
*/
public final static int
	ROW_LABEL_WIDTH = 	255,
	POINT_FLOW_WIDTH = 	70,
	NATURAL_FLOW_WIDTH = 	70,
	DELIVERY_FLOW_WIDTH = 	70,
	GAIN_LOSS_WIDTH = 	70,
	RELEASES_WIDTH = 	70,
	TRIB_NATURAL_WIDTH = 	70,
	TRIB_DELIVERY_WIDTH = 	70,
	COMMENTS_WIDTH = 	255,
	DEFAULT_ROW_HEIGHT = 	18;

/**
WIS data types.
*/
public final static int		
	DISTANCE = 	2,
	WEIGHT = 	3;

/**
Format types for the export.
*/
public final static int
	TEXT = 	0,
	HTML = 	1;

/**
Popup menu options.
*/
private final static String
	__POPUP_MENU_CELL_GRAPH = 	"Graph",
	__POPUP_MENU_CELL_PROPERTIES = 	"Cell Properties...",
	__POPUP_MENU_DIVERSION_CODING =	"Diversion Coding...";

/**
Column headings (listed in order from column 0 to column 10).
These headings are appropriate for inserting into a table model.  Doing 
string comparisons against column names stored in the database should be
done with the S_ versions (below).
*/
public final static String
	ROW_LABEL = 	"\nRow Label",
	POINT_FLOW = 	"\nPoint Flow",
	NATURAL_FLOW = 	"Natural\nFlow",
	DELIVERY_FLOW =	"Delivery\nFlow",
	GAIN_LOSS = 	"\nGain Loss",
	TRIB_NATURAL =	"Trib.\nNatural",
	TRIB_DELIVERY =	"Trib.\nDelivery",	
	RELEASES = 	"\nReleases",
	PRIORITY_DIV =	"Priority\nDiversion",
	DELIVERY_DIV =	"Delivery\nDiversion",
	COMMENTS =	"\nComments",
	DRY_RIVER =	"Dry River";

/**
Column headings appropriate for doing String comparisons against values in
the database.
*/
public final static String
	S_ROW_LABEL = 		"Row Label",
	S_POINT_FLOW = 		"Point Flow",
	S_NATURAL_FLOW = 	"Natural Flow",
	S_DELIVERY_FLOW =	"Delivery Flow",
	S_GAIN_LOSS = 		"Gain Loss",
	S_TRIB_NATURAL =	"Trib. Natural",
	S_TRIB_DELIVERY =	"Trib. Delivery",	
	S_RELEASES = 		"Releases",
	S_PRIORITY_DIV =	"Priority Diversion",
	S_DELIVERY_DIV =	"Delivery Diversion",
	S_COMMENTS =		"Comments",
	S_DRY_RIVER =		"Dry River";


/**
Array of the column headings.
*/
private final String[] __columnHeadings = {	
	ROW_LABEL,
	POINT_FLOW,
	NATURAL_FLOW,
	DELIVERY_FLOW,
	GAIN_LOSS,
	RELEASES,
	PRIORITY_DIV,
	DELIVERY_DIV,
	TRIB_NATURAL,
	TRIB_DELIVERY,
	COMMENTS 
};

/**
Button labels (extra spacing provided for spacing the button label proper 
when switching between labels).
*/
private final static String	
	__BUTTON_CALCULATE_WIS = 	"Calculate WIS",
	__BUTTON_CANCEL = 		"Cancel",
	__BUTTON_CLEAR = 		"Clear",
	__BUTTON_CLOSE = 		"Close",
	__BUTTON_DISPLAY_DIAGRAM = 	"Display Diagram",	
	__BUTTON_DRY_RIVER = 		"     Dry River     ",
	__BUTTON_EXPORT = 		"Export",
	__BUTTON_HELP = 		"Help",
	__BUTTON_LOAD = 		"Load",
	__BUTTON_PRINT = 		"Print",
	__BUTTON_SAVE = 		"Save",
	__BUTTON_SET_DATE = 		"Set Date",
	__BUTTON_SUMMARY = 		"Summary",
	__BUTTON_UNDRY_RIVER = 		"UN-Dry River";

/**
Row types.
*/
public final static String
	STRING = 		"String",
	DIVERSION = 		"Diversion",
	RESERVOIR = 		"Reservoir",
	MIN_FLOW_REACH = 	"MinFlow",
	STATION = 		"Station",
	CONFLUENCE = 		"Confluence",
	OTHER = 		"Other",
	STREAM = 		"Stream";

/**
Row type option.
*/
private final static String __KNOWN_POINT_FLOW = "Known Point Flow";

/**
Gain methods.
*/
public final static String	
	NONE = 		"Gain Method: None",
	STREAM_MILE = 	"Gain Method: Stream Mile",
	WEIGHTS = 	"Gain Method: Weights";

/**
Whether automatic calculation is turned on or not.
*/
private boolean __autoCalc = false;

/**
Whether gains are being computed or not.
*/
private boolean __computeGain = false;

/**
Whether diversion coding is enabled.
*/
private boolean __diversionCodingEnabled = false;

/**
Whether data on the WIS can be changed or not.
*/
private boolean __editable = true;

/**
Whether the wis is being computed for the first time or not.
*/
private boolean __firstCalc = true;

/**
Whether to force the recomputation of WIS values.
*/
private boolean __forceCompute = false;

/**
Whether to return immediately from itemStateChanged() or not.
*/
private boolean __ignoreItemStateChanged = false;

/**
Whether the GUI is currently calculating the wis values in computeWIS().
*/
//private boolean __inComputeWIS = false;

/**
Whether the gui is fully initialized and data are being shown or not.
*/
private boolean __initialized = false;

/**
Whether a new sheet is being loaded or not.
*/
private boolean __newSheet = false;

/**
IF this sheet is new, if there was any previous data available.
*/
private boolean __previousDataAvailable = false;

/**
Whether the sheet has been modified or not.
*/
private boolean __sheetModified = false;

/**
Whether the grid behavior is to display solving order.
See the public access method.
*/
//private boolean __showGridResponse = false;

/**
The parent JFrame running the application.
*/
private JFrame __parent;

/**
GeoViewUI for map interaction.
*/
GeoViewUI __geoview_ui = null;

/**
The admin date.
*/
private DateTime __adminDateTime;

/**
The DMI used to communicate with the database.
*/
private HydroBaseDMI __dmi;

/**
GUI for loading a water information sheet.
*/
private HydroBase_GUI_LoadWIS __loadWISGUI;

/**
The gui for displaying the network.
*/
private HydroBase_GUI_WISDiagram __diagramGUI = null;

/**
The node network.
*/
private HydroBase_NodeNetwork __network;

/**
The current HydroBase_WISSheetName object.
*/
private HydroBase_WISSheetName __wis;

/**
The table model for the worksheet.
*/
private HydroBase_TableModel_WIS __tableModel;

/**
Mode in which the GUI was instantiated.
*/
private int __mode;

/**
If this WIS is one which was originally saved with an older format, but which
is now being opened with a newer format (HydroBase_GUI_LoadWIS handles how
this is determined), this value is the WIS Num of the format under which the
data were originally saved.  For all other WIS, this value will be missing.
*/
private int __oldFormatWISNum = DMIUtil.MISSING_INT;

/**
GUI buttons.
*/
private JButton	
	__archiveJButton,
	__calculateJButton,
	__clearJButton,
	__closeJButton,
	__dateJButton,
	__dryRiverJButton,
	__exportJButton,
	__loadJButton,
	__printJButton,
	__summaryJButton;

/**
Popup menus.
*/
private JPopupMenu
	__cellJPopupMenu,
	__diversionJPopupMenu;

/**
The tabbed pane holding the worksheets.
*/
private	JTabbedPane __tabJPanel;

/**
GUI text fields.
*/
private JTextField   	
	__commentsJTextField,
	__dateJTextField,  
	__statusJTextField,
	__statusUserJTextField;

/**
The worksheet for displaying wisses.
REVISIT (JTS - 2003-10-08)
This will probably be need to be made into a pointer to the current worksheet --
i.e., the one that is in the currently-displayed tab.  That's for later, 
when we support multiple tabs again.
*/
private JWorksheet __worksheet;

/**
Unique wis_num for the loaded wis.
*/
private int __wisNum;
	
/**
Gain method combo box.
*/
private SimpleJComboBox	__gainSimpleJComboBox;

/**
Popup menu options.
*/
private SimpleJMenuItem	
	__cellGraphJMenuItem1,
	__cellGraphJMenuItem2;

/**
Data Vectors.
*/
private List
	__wisDiversionCodingVector,
	__wisFormatVector,
	__wisFormulaVector,
	__wisImportVector;

/**
PropList for the DateTimeBuildJDialog.
*/
private PropList __dateProps;

/**
The set date.  'null' if loading a sheet for which no WIS Data exists.
Otherwise, will be the date for the wis sheet.
*/
private String __set_date;

/**
WIS sheet name.
*/
private String __sheetName;
/**
Wis status.
*/
private String __wisStatus;

/**
Constructor.
@param parent the parent JFrame running the application.
@param geoview_ui GeoViewUI for map interaction.
@param dmi the dmi to use for communicating with the database.
@param wis the sheet to display data for.
@param mode the mode in which to instantiate the gui.
@param set_date the date for the wis.  null if loading a sheet for which there
is no wis data.
@param sheetName the name of the sheet.
@param isNewSheet true if loading a new sheet, false if not.
@param previousDataAvailable if this sheet is new, whether any previous
data was available
@param editable whether the sheet can be edited or not
*/
public HydroBase_GUI_WIS(JFrame parent, GeoViewUI geoview_ui, HydroBaseDMI dmi, 
HydroBase_WISSheetName wis, int mode, String set_date, String wisStatus, 
String sheetName, boolean isNewSheet, boolean previousDataAvailable, 
boolean editable) {
	this(parent, geoview_ui, dmi, wis, mode, set_date, wisStatus, sheetName,
		isNewSheet, previousDataAvailable, editable, 
		DMIUtil.MISSING_INT);
}

/**
Constructor.
@param parent the parent JFrame running the application.
@param geoview_ui GeoViewUI for map interaction.
@param dmi the dmi to use for communicating with the database.
@param wis the sheet to display data for.
@param mode the mode in which to instantiate the gui.
@param set_date the date for the wis.  null if loading a sheet for which there
is no wis data.
@param sheetName the name of the sheet.
@param isNewSheet true if loading a new sheet, false if not.
@param previousDataAvailable if this sheet is new, whether any previous
data was available
@param editable whether the sheet can be edited or not
*/
public HydroBase_GUI_WIS(JFrame parent, GeoViewUI geoview_ui, HydroBaseDMI dmi, 
HydroBase_WISSheetName wis, int mode, String set_date, String wisStatus, 
String sheetName, boolean isNewSheet, boolean previousDataAvailable, 
boolean editable, int oldFormatWISNum) {		
	__parent = parent;
	__geoview_ui = geoview_ui;
        __dmi = dmi;
	__wis = wis;
	__wisNum = __wis.getWis_num();
	__mode = mode;
	__set_date = set_date;
	__wisStatus = wisStatus;
	__sheetName = sheetName;
	__newSheet = isNewSheet;
	__editable = editable;
	__oldFormatWISNum = oldFormatWISNum;
	__previousDataAvailable = previousDataAvailable;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI();
	__worksheet.refresh();
	__autoCalc = true;
	__initialized = true;
	if (!__newSheet) {
		// so new sheets will be prompted to save if closed
		// immediately.  Others won't.
		__sheetModified = false;
	}

	wisChanged(false);
}

/**
Accounts for the gain method by forcing a recomputation of the WIS values.
This is only called once, when the GUI is first set up, to make sure that the
saved gain value is used for the values that are displayed on the WIS.
*/
private void accountForGainMethod() {
	__forceCompute = true;
	computeWIS();
	__forceCompute = false;
}

/**
This routine handles the events using the ActionListener.
@param event ActionEvent object.
*/
public void actionPerformed(ActionEvent event) {
	String actionCommand = event.getActionCommand();
        if (actionCommand.equals(__BUTTON_DRY_RIVER) ||
	    actionCommand.equals(__BUTTON_UNDRY_RIVER)) {
		dryRiverClicked();
	}
        else if (actionCommand.equals(__BUTTON_CALCULATE_WIS)) {
		// reset __autoCalc to avoid infinite loops ...
		computeWIS();
        } 	
	else if (actionCommand.equals(__BUTTON_CANCEL)) {
		cancelClicked();
	}
        else if (actionCommand.equals(__BUTTON_CLEAR)) {
		clearClicked();
        }	
        else if (actionCommand.equals(__BUTTON_CLOSE)) {
		closeClicked();
        }
	else if (actionCommand.equals(__BUTTON_DISPLAY_DIAGRAM)) {
		displayDiagramClicked();
	}
        else if (actionCommand.equals(__BUTTON_EXPORT)) {
		exportClicked();
	}
        else if (actionCommand.equals(__POPUP_MENU_CELL_GRAPH)) {
		graphCell();
        }
        else if (actionCommand.equals(__BUTTON_HELP)) {
        }
        else if (actionCommand.equals(__BUTTON_LOAD)) {
		loadClicked();
        }
        else if (actionCommand.equals(__BUTTON_PRINT)) {
		printClicked();
        }
        else if (actionCommand.equals(__BUTTON_SAVE)) {
		saveClicked();
        }	
        else if (actionCommand.equals(__BUTTON_SET_DATE)) {
		dateClicked();
        }
        else if (actionCommand.equals(__BUTTON_SUMMARY)) {
		summaryClicked();
        }
        else if (actionCommand.equals(__POPUP_MENU_CELL_PROPERTIES)) {
		JGUIUtil.setWaitCursor(this, true);
		showCellProperties(__worksheet.getSelectedRow(), 
			__worksheet.getSelectedColumn());
		JGUIUtil.setWaitCursor(this, false);
	}
        else if (actionCommand.equals(__POPUP_MENU_DIVERSION_CODING)) {
		// Allow more than one of these to be created.  Currently there
		// is not check to make sure the same diversion is not edited
		// more than once.

		new HydroBase_GUI_WISDiversionCoding(__dmi, this, 
			__wis, __adminDateTime,
			__worksheet.getSelectedRow(), 
			__worksheet.getSelectedColumn(),
			getDiversionCodingForCurrentCell(), __editable);
	}
}

/**
Closes the form without saving anything.
*/
private void cancelClicked() {
	if (__diagramGUI != null) {
		__diagramGUI.setVisible(false);
		__diagramGUI.dispose();
		__diagramGUI = null;
	}
	super.setVisible(false);
	dispose();
}

/**
Returns whether the row type can be considered for drying.
@return true if the row type can be considered for drying, false if not.
*/
private boolean canDryRow(int row) {
	if (row < 0) {
		return false;
	}

	// Only rows with valid flows can be set to dry river...
	boolean isUserEnterCell = isEntryCell(row, POINT_FLOW_COL);
	boolean isFormula = isFormulaCell(row, POINT_FLOW_COL);
	boolean isImport = isImportCell(row, POINT_FLOW_COL);
	HydroBase_WISFormat wisformat = 
		(HydroBase_WISFormat)__wisFormatVector.get(row);
	String rowType = wisformat.getRow_type();
	
	// Don't want complicated rows(formulas, etc.)and only allow
	// MIN_FLOW_REACH, OTHER, DIVERSION, RESERVOIR...
	if (!isUserEnterCell && !isFormula && !isImport
		&& (rowType.equals(DIVERSION)
			|| rowType.equals(MIN_FLOW_REACH)
			|| rowType.equals(OTHER)
			|| rowType.equals(RESERVOIR))
		   ) {
		return true;
	}
	return false;
}

/**
Indicate whether cell has data that can be graphed.  Stream and String rows do
not have data.
@param row Row of interest.
@param col Column of interest.
@return true if the cell has numerical data, false if not.  If the row has
numerical data and the whole row is selected, true is returned.
*/
private boolean cellHasData(int row, int col)
{	if (row < 0) {
		return false;
	}
	HydroBase_WISFormat wisFormat = 
		(HydroBase_WISFormat)__wisFormatVector.get(row);

	String rowType = wisFormat.getRow_type();
	
	if (rowType.equals(STREAM) || rowType.equals(STRING)) {
		return false;
	}
	else if (rowType.equals(STATION)
		&& ((col == ROW_LABEL_COL)
			|| (col == POINT_FLOW_COL) || (col == NATURAL_FLOW_COL) 
			|| (col == DELIVERY_FLOW_COL) 
			||(col == GAIN_LOSS_COL))
		) {
		return true;
	}
	else if (rowType.equals(CONFLUENCE) 
		&& ((col == ROW_LABEL_COL) || (col == POINT_FLOW_COL) 
			|| (col == NATURAL_FLOW_COL) 
			|| (col == DELIVERY_FLOW_COL) 
			|| (col == GAIN_LOSS_COL) || (col == TRIB_NATURAL_COL) 
			|| (col == TRIB_DELIVERY_COL))
		) {
		return true;
	}
	else if ((rowType.equals(DIVERSION) || rowType.equals(RESERVOIR)
			|| rowType.equals(MIN_FLOW_REACH))
		&& ((col == ROW_LABEL_COL) || (col == POINT_FLOW_COL) 
			|| (col == NATURAL_FLOW_COL) 
			|| (col == DELIVERY_FLOW_COL) || (col == GAIN_LOSS_COL) 
			|| (col == RELEASES_COL) || (col == PRIORITY_DIV_COL) 
			|| (col == DELIVERY_DIV_COL))
		) {
		return true;
	}
	else if (rowType.equals(OTHER)) {
		// OTHER - assume all can be graphed
		return true;
	}
	return false;
}

/**
Checks the WIS for neative values, and issue a warning if any are negative.
@return true if there are no negatives, or if there are negatives and the
user pressed 'Yes' on the warning dialog.
*/
private boolean checkForNegatives() {
	double d;
	String s;

	// row loop	
	int numRows = __worksheet.getRowCount();
	for (int r = 0; r < numRows; r++) {	
		for (int c = 1; c < NUM_COLUMNS; c++) {
			s = getArchiveCellContents(r, c);
			if (s != null && c!= COMMENTS_COL && c!= GAIN_LOSS_COL){
				d = StringUtil.atod(s);

				if (!DMIUtil.isMissing(d) && (d < 0.0)) {
					int response = new ResponseJDialog(this,
						"Save with Negative Values?",
						"Attempting to save WIS with"
						+ " negative flow values."
						+ " Continue?", 
						ResponseJDialog.YES |
						ResponseJDialog.NO).response();
					if (response == ResponseJDialog.NO) {
						return false;
					}
					else {	
						return true;
					}
				}
			}
		}
	}
	return true;
}

/**
Sets all of a row's data (except for the row label) to missing.
*/
private void clearRow(int row) {	
	Double d = new Double(DMIUtil.MISSING_DOUBLE);
	__tableModel.setValueAt(d, row, POINT_FLOW_COL);
	__tableModel.setValueAt(d, row, NATURAL_FLOW_COL);
	__tableModel.setValueAt(d, row, DELIVERY_FLOW_COL);
	__tableModel.setValueAt(d, row, GAIN_LOSS_COL);
	__tableModel.setValueAt(d, row, RELEASES_COL);
	__tableModel.setValueAt(d, row, PRIORITY_DIV_COL);
	__tableModel.setValueAt(d, row, DELIVERY_DIV_COL);
	__tableModel.setValueAt(d, row, TRIB_NATURAL_COL);
	__tableModel.setValueAt(d, row, TRIB_DELIVERY_COL);
	__tableModel.setValueAt("", row, COMMENTS_COL); 
}

/**
Responds to a window close command, from a window event or the close button.
*/
protected void closeClicked() {
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	boolean saveSuccess = true;
	
	 if (__dmi.hasPermission(__wis.getWD())) {
		if (__sheetModified && __editable) {
			int r = new ResponseJDialog(this, 
				"Save Changes?",
				"Save changes made to " + __sheetName + "?", 
				ResponseJDialog.YES |ResponseJDialog.NO 
				| ResponseJDialog.CANCEL).response();
			if (r == ResponseJDialog.YES) {
				saveSuccess = saveClicked();
			}
			else if (r == ResponseJDialog.CANCEL) {
				setDefaultCloseOperation(
					WindowConstants.DO_NOTHING_ON_CLOSE);
				return;
			}
			else {
				// no clicked, means don't save but do close
			}
		}
	}

	if (saveSuccess) {
		super.setVisible(false);
		dispose();
	}
	else {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);	
	}
}

/**
Responds to the clear button being pressed.
*/
private void clearClicked() {
	int r = new ResponseJDialog(this,
		"Clear All Values?",
		"All values will be cleared from the WIS. Continue?",
		ResponseJDialog.YES | ResponseJDialog.NO).response();

	// return if no is selected
	if (r != ResponseJDialog.YES) {
		return;
	}

	JGUIUtil.setWaitCursor(this, true);
//	__statusJTextField.setText("Please Wait...Clearing Values...");
	__sheetModified = true;

	int size = __worksheet.getRowCount();
	for (int i = 0; i < size; i++) {
		clearRow(i);
	}
//	__statusJTextField.setText("Finished clearing values.");
	JGUIUtil.setWaitCursor(this, false);
}

/**
This routine is called by the "calculate wis" button being pressed.
It calls the appropriate functions so that the values may be computed for the WIS.	
*/
public void computeWIS() {
	if (!__editable && !__forceCompute) {
		return;
	}

	// set autocalc to false internally (it is reset to true [if it 
	// was true] when the method exits) to avoid infinite loops
	boolean holdAutoCalc = __autoCalc;
	__autoCalc = false;

	String routine = CLASS + ".computeWIS()";

	if (!__worksheet.stopEditing()) {
		Message.printWarning(1, routine, "Correct the data errors in "
			+ "the worksheet before computing the WIS.");
		__autoCalc = holdAutoCalc;
		return;
	}

	//__inComputeWIS = true;

	int row = __worksheet.getSelectedRow();

	HydroBase_WISFormat wisFormat = null;
	//int numRows = __worksheet.getRowCount();
    setIsModified(true);

	// determine the gain/loss computation method
	boolean	weightedGain;
	if (__gainSimpleJComboBox.getSelected().trim().equals(WEIGHTS)) {
		weightedGain = true;
	}
	else {	
		weightedGain = false;
	}

	// set the __computeGain flag
	if (__gainSimpleJComboBox.getSelected().trim().equals(NONE)) {
		__computeGain = false;
	}
	else {	
		__computeGain = true;
	}	

	// add an empty String to the first element so that the row numbers
	// in the __worksheet object will match the elements in the__wisFormatVectorS
	List worksheetData = __worksheet.getAllData();

	int rows = __worksheet.getRowCount();
	for (int i = 0; i < rows; i++) {
		setIsEvaluated(i, false);
	}
	
	// begin perform calculations
	JGUIUtil.setWaitCursor(this, true);
	String tempString = "Please Wait...Performing Calculations";
//	__statusJTextField.setText(tempString);
	Message.printStatus(1, routine, tempString);    

	// reset formula status
	setFormulaStatus(false);

	//---------------------------------------------------------------------
	// DELIVERY FLOW TRANSFER
	//
	// The following loop is responsible for propagating delivery flow
	// throughout the river network. if a confluence is encountered and uses
	// a formula to transfer water from a trib, the formula is evaluated
	// first before delivery flow is computed.
	//---------------------------------------------------------------------

	if (__network == null) {
		__autoCalc = holdAutoCalc;
		//__inComputeWIS = false;
		return;
	}
	
	HydroBase_Node node = __network.getMostUpstreamNode();
	if (node == null) {
		Message.printWarning(1, routine, "An error has occurred in "
			+ "the river network.  Upstream node is null.");
		__autoCalc = holdAutoCalc;
		//__inComputeWIS = false;
		return;
	}
	int curRow = 0;
	boolean breakNow = false;
	for (; node != null;
		node = HydroBase_NodeNetwork.getDownstreamNode(node,
		HydroBase_NodeNetwork.POSITION_COMPUTATIONAL)) {

		// If the downstream node is null then the last node in the
		// network has been encountered.
		if (node.getDownstreamNode() == null) {
			breakNow = true;
		}
		
		wisFormat = node.getWISFormat();
		curRow = wisFormat.getWis_row() - 1;
		
		if (isFormulaCell(curRow, TRIB_DELIVERY_COL)) {
			processFormula(curRow, TRIB_DELIVERY_COL);
		}
		computeDeliveryFlow(node);
		if (breakNow) {
			break;
		}
	}

	//---------------------------------------------------------------------
	// COMPUTE ALL BASE FLOW NODES (CONTAINS USER SUPPLIED VALUES)
	//
	// The following loop evaluates all known baseflow nodes which ARE user
	// supplied.  Natural flow is computed as point flow - delivery flow
	//---------------------------------------------------------------------
	node = __network.getMostUpstreamNode();
	breakNow = false;
	for (; node != null;
		node = HydroBase_NodeNetwork.getDownstreamNode(node,
		HydroBase_NodeNetwork.POSITION_COMPUTATIONAL)) {

		// If the downstream node is null then the last node in the
		// network has been encountered.
		if (node.getDownstreamNode() == null) {
			breakNow = true;
		}
	
		wisFormat = node.getWISFormat();
		curRow = wisFormat.getWis_row() - 1;
		
		if (isKnownPoint(wisFormat) || isDryRiver(wisFormat)) {
			if (!isFormulaCell(curRow, NATURAL_FLOW_COL)
				&& !isFormulaCell(curRow, POINT_FLOW_COL)) {
				computeNaturalFlow(node);
				setIsEvaluated(curRow, true);
			}
		}
		if (breakNow) {
			break;
		}		
	}

	//---------------------------------------------------------------------
	// COMPUTE ALL BASE FLOW NODES (NOT USER SUPPLIED VALUES)
	//
	// The following loop evaluates all known baseflow nodes which are not
	// user supplied.  A formula may exist in either the natural flow or
	// point flow cell. if the natural flow is a user entered formula, it
	// is evaluated first with point flow computed second. If the point flow
	// is a user entered formula it is evaluated first before computing
	// natural flow.
	//---------------------------------------------------------------------
	node = __network.getMostUpstreamNode();
	breakNow = false;
	for (; node != null;
		node = HydroBase_NodeNetwork.getDownstreamNode(node,
		HydroBase_NodeNetwork.POSITION_COMPUTATIONAL)) {

		// If the downstream node is null then the last node in the
		// network has been encountered.
		if (node.getDownstreamNode() == null) {
			breakNow = true;
		}
	
		wisFormat = node.getWISFormat();
		curRow = wisFormat.getWis_row() - 1;

		if (isFormulaCell(curRow, NATURAL_FLOW_COL)) {
			processFormula(curRow, NATURAL_FLOW_COL);
			computePointFlowGivenNaturalFlow(node);
			setIsEvaluated(curRow, true);
		}
		else if (isFormulaCell(curRow, POINT_FLOW_COL)) {
			processFormula(curRow, POINT_FLOW_COL);
			computeNaturalFlow(node);
			setIsEvaluated(curRow, true);
		}
		if (breakNow) {
			break;
		}		
	}		

	//---------------------------------------------------------------------
	// NODAL MASS BALANCE COMPUTATIONS
	//
	// The following loop is responsible for computing gain/loss, point
	// flow, and natural flow for all nodes in river network which are not
	// baseflow nodes or String nodes.
	//---------------------------------------------------------------------
	node = __network.getMostUpstreamNode();
	breakNow = false;
	for (; node != null;
		node = HydroBase_NodeNetwork.getDownstreamNode(node,
		HydroBase_NodeNetwork.POSITION_COMPUTATIONAL)) {

		// If the downstream node is null then the last node in the
		// network has been encountered.
		if (node.getDownstreamNode() == null) {
			breakNow = true;
		}
		
		wisFormat = node.getWISFormat();
		curRow = wisFormat.getWis_row() - 1;

		// The ordering of row calculations is important and must
		// adhere to the following: formulae for confluence row types,
		// gain loss, point flow, and natural flow.	
		if (!isEvaluated(curRow)) {
			computeFormulas(CONFLUENCE, node);

			if (weightedGain) {
				HydroBase_WIS_Util.computeWeightedGainLoss(
					__network, node, worksheetData, 
					__computeGain);
			}
			else {	
				HydroBase_WIS_Util.computeGainLoss(__network, 
					node, worksheetData, __computeGain);
			}
			
			computePointFlow(node);
			computeNaturalFlow(node);
			setIsEvaluated(curRow, true);
		}
		if (breakNow) {
			break;
		}		
	}

	//---------------------------------------------------------------------
	// COMPUTE GAIN LOSS FOR ALL BASE FLOW NODES
	//
	// The following loop evaluates gain loss for ALL known baseflow nodes.
	//---------------------------------------------------------------------
	node = __network.getMostUpstreamNode();
	breakNow = false;
	for (; node != null;
		node = HydroBase_NodeNetwork.getDownstreamNode(node,
		HydroBase_NodeNetwork.POSITION_COMPUTATIONAL)) {

		// If the downstream node is null then the last node in the
		// network has been encountered.
		if (node.getDownstreamNode() == null) {
			breakNow = true;
		}
	
		wisFormat = node.getWISFormat();
		curRow = wisFormat.getWis_row() - 1;
		if (isKnownPoint(wisFormat) || isDryRiver(wisFormat)) {
			if (weightedGain) {
				HydroBase_WIS_Util.computeWeightedGainLoss( __network, node, worksheetData, __computeGain);
			}
			else {	
				HydroBase_WIS_Util.computeGainLoss(__network, node, worksheetData, __computeGain);
			}
		}
		if (breakNow) {
			break;
		}
	}		

	// compute formulae for String row types
	computeFormulas(STRING, null);

	// update sheet display as a result of the calculations
	for (int i = 0; i < worksheetData.size(); i++) {
		setRowValues((HydroBase_WISData)worksheetData.get(i), i);
	}

	// end calculations
	JGUIUtil.setWaitCursor(this, false);
//        setStatus("Sheet has been modified");
	tempString = "Finished performing calculations.";
//	__statusJTextField.setText(tempString);
	Message.printStatus(1, routine, tempString);
//        setStatus("Sheet has been modified");
        setIsModified(true);
	if (row > 0) {
		__worksheet.selectRow(row);
	}
	__autoCalc = holdAutoCalc;

	//__inComputeWIS = false;

	if (__diagramGUI == null) {
		return;
	}
	String id = null;
	HydroBase_WISFormat format = null;
	double pf = 0;
	for (int i = 1; i < rows; i++) {
		format = (HydroBase_WISFormat)__wisFormatVector.get(i);
		id = format.getIdentifier();
		pf = ((Double)__worksheet.getValueAt(i, 1)).doubleValue();
		
		__diagramGUI.updatePointFlow(id, pf);
	}

	__diagramGUI.refresh();	
}

/**
Computes point flow at the specified node.
@param curNode the node at which to compute point flow.
*/
private void computePointFlow(HydroBase_Node curNode) {
	String routine = CLASS + ".computePointFlow()";
	HydroBase_WISFormat wisFormat = curNode.getWISFormat();
	int row = wisFormat.getWis_row() - 1;
	HydroBase_WISData wisData = 
		(HydroBase_WISData)__worksheet.getRowData(row);
	boolean knownPoint = isKnownPoint(wisFormat);
	if (!knownPoint) {
		knownPoint = isDryRiver(wisFormat);
	}
	boolean isFormula = isFormulaCell(row, POINT_FLOW_COL);
	String rowType = wisFormat.getRow_type();
	if (Message.isDebugOn) {
		Message.printDebug(10, routine,
			"Computing Point Flow for row: " + row);
	}

	// do not perform calculation for the following row types
	if (rowType.equals(STREAM) || rowType.equals(STRING)) {
		return;
	}

	// compute point flow for cells which are not a known point
	// and are not a formula.
	if (!isEntryCell(row, POINT_FLOW_COL) && !isFormula) {
		// check so that we are not using a string row 
		// type for calculations
		int upRow = (row - 1);
		HydroBase_WISFormat upWISFormat = 
			(HydroBase_WISFormat)__wisFormatVector.get(upRow);
		String upRowType = upWISFormat.getRow_type();
		while (upRowType.equals(HydroBase_GUI_WIS.STRING) 
			&& upRow != 1){
			upRow--;
			upWISFormat = (HydroBase_WISFormat)
				__wisFormatVector.get(upRow);
			upRowType = upWISFormat.getRow_type();
		}

		double gain = wisData.getGain();
		double trib_nat = wisData.getTrib_natural();
		double trib_del = wisData.getTrib_delivery();
		double release = wisData.getRelease();
		double priority_div = wisData.getPriority_divr();
		double delivery_div = wisData.getDelivery_divr();
		if (DMIUtil.isMissing(gain)) {
			gain = 0;
		}
		if (DMIUtil.isMissing(trib_nat)) {
			trib_nat = 0;
		}
		if (DMIUtil.isMissing(trib_del)) {
			trib_del = 0;
		}
		if (DMIUtil.isMissing(release)) {
			release = 0;
		}
		if (DMIUtil.isMissing(priority_div)) {
			priority_div = 0;
		}
		if (DMIUtil.isMissing(delivery_div)) {
			delivery_div = 0;
		}

		double pointFlow = + getWISDataValue(upRow, POINT_FLOW_COL)
			+ gain + trib_nat + trib_del + release - priority_div
			- delivery_div;
/*
		double pointFlow = + getWISDataValue(upRow, POINT_FLOW_COL)
			+ wisData.getGain()
			+ wisData.getTrib_natural()
			+ wisData.getTrib_delivery()
			+ wisData.getRelease()
			- wisData.getPriority_divr()
			- wisData.getDelivery_divr();
*/
		wisData.setPoint_flow(pointFlow);
		__worksheet.setRowData(wisData, row);
	}
}

/**
Computes point flow given natural flow as point flow = natural flow + 
delivery flow.
@param curNode the node to evaluate in the network.
*/
private void computePointFlowGivenNaturalFlow(HydroBase_Node curNode) {
	String routine = CLASS + ".computeNaturalFlow()";
	HydroBase_WISFormat wisFormat = curNode.getWISFormat();
	int row = wisFormat.getWis_row() - 1;
	HydroBase_WISData wisData = 
		(HydroBase_WISData)__worksheet.getRowData(row);
	String rowType = wisFormat.getRow_type();
	if (Message.isDebugOn) {
		Message.printDebug(10, routine, "Computing Point Flow given "
			+ "Natural Flow for row: " + row);
	}

	// do not perform calculation for the following row types
	if (rowType.equals(STREAM) || rowType.equals(STRING)) {
		return;
	}
		
	double nat_flow = wisData.getNat_flow();
	double del_flow = wisData.getDelivery_flow();
	if (DMIUtil.isMissing(nat_flow)) {
		nat_flow = 0;
	}
	if (DMIUtil.isMissing(del_flow)) {
		del_flow = 0;
	}
	double pointFlow = nat_flow + del_flow;
//	double pointFlow = wisData.getNat_flow() + wisData.getDelivery_flow();
	wisData.setPoint_flow(pointFlow);
	__worksheet.setRowData(wisData, row);
}

/**
Computes natural flow at the specified node.
@param curNode the node to evaluate.
*/
private void computeNaturalFlow(HydroBase_Node curNode) {
	// initialize variables	
	String routine = CLASS + ".computeNaturalFlow()";
	HydroBase_WISFormat wisFormat = curNode.getWISFormat();
	int row = wisFormat.getWis_row() - 1;
	HydroBase_WISData wisData = 
		(HydroBase_WISData)__worksheet.getRowData(row);
	String rowType = wisFormat.getRow_type();
	if (Message.isDebugOn) {
		Message.printDebug(10, routine, "Computing Natural Flow "
			+ "for row: " + row);
	}

	// do not perform calculation for the following row types
	if (rowType.equals(STREAM) || rowType.equals(STRING)) {
		return;
	}
	
	double point_flow = wisData.getPoint_flow();
	double del_flow = wisData.getDelivery_flow();
	if (DMIUtil.isMissing(point_flow)) {
		point_flow = 0;
	}
	if (DMIUtil.isMissing(del_flow)) {
		del_flow = 0;
	}

	double naturalFlow = point_flow - del_flow;
	//	wisData.getPoint_flow()-wisData.getDelivery_flow();
	wisData.setNat_flow(naturalFlow);
	__worksheet.setRowData(wisData, row);
}

/**
Computes natural flow for all nodes where the point flow is known and is not
a formula.
*/
/*
SAM REVISIT 2007-02-08
Evaluate whether this can be deleted.
private void computeNaturalForUserEnteredBaseflows() {
	HydroBase_Node node = __network.getMostUpstreamNode();

	HydroBase_WISFormat wisFormat;	
	int curRow;
	// move downstream in computational order...
	for (; node != null;
		node = HydroBase_NodeNetwork.getDownstreamNode(node,
			HydroBase_NodeNetwork.POSITION_COMPUTATIONAL)) {

		// If the downstream node is null, break(redundant?)
		if (node.getDownstreamNode() == null) {
			break;
		}

		wisFormat = node.getWISFormat();
		curRow = wisFormat.getWis_row() - 1;

		if (isKnownPoint(wisFormat) || isDryRiver(wisFormat)) {
			// the known base flow point cannot have a formula
			// in the point flow or natural flow columns for 
			// it to be computed.
			if (!isFormulaCell(curRow, POINT_FLOW_COL)
				&&! isFormulaCell(curRow, NATURAL_FLOW_COL)) {
				computeDeliveryFlow(node);
				computeNaturalFlow(node);
			}
		}		
	}
}
*/

/**
Computes delivery flow at the specified node.
@param curNode the node the evaluate in the network.
*/
private void computeDeliveryFlow(HydroBase_Node curNode) {
	String routine = CLASS + ".computeDeliveryFlow()";
	HydroBase_WISFormat wisFormat = curNode.getWISFormat();
	int row = wisFormat.getWis_row() - 1;
	HydroBase_WISData wisData = 
		(HydroBase_WISData)__worksheet.getRowData(row);
	String rowType = wisFormat.getRow_type();
	double deliveryFlow = DMIUtil.MISSING_DOUBLE;
	if (Message.isDebugOn) {
		Message.printDebug(10, routine,
			"Computing Delivery Flow for row: " + row);
	}

	// do not perform calculation for the following row types
	if (rowType.equals(STREAM) || rowType.equals(STRING)) {
		return;
	}

	// top most node in current tributary
	// gets delivery flow from the Releases column
	if (__network.isMostUpstreamNodeInReach(curNode)) {
		deliveryFlow = getWISDataValue(row, RELEASES_COL);
	}
	// all other nodes in current tributary compute delivery flow
	else {	
		// Check so that we are not using a string row type for
		// calculations
		int upRow = (row - 1);
		HydroBase_WISFormat upWISFormat = 
			(HydroBase_WISFormat)__wisFormatVector.get(upRow);
		String upRowType = upWISFormat.getRow_type();
		while (upRowType.equals(HydroBase_GUI_WIS.STRING) 
			&& upRow != 1){
			upRow--;
			upWISFormat = (HydroBase_WISFormat)
				__wisFormatVector.get(upRow);
			upRowType = upWISFormat.getRow_type();
		}

		double trib_del = wisData.getTrib_delivery();
		double release = wisData.getRelease();
		double del_div = wisData.getDelivery_divr();
		if (DMIUtil.isMissing(trib_del)) {
			trib_del = 0;
		}
		if (DMIUtil.isMissing(release)) {
			release = 0;
		}
		if (DMIUtil.isMissing(del_div)) {
			del_div = 0;
		}
		deliveryFlow = getWISDataValue(upRow, DELIVERY_FLOW_COL)
			+ trib_del + release - del_div;

		/*
		deliveryFlow = getWISDataValue(upRow, DELIVERY_FLOW_COL)
			+ wisData.getTrib_delivery()
			+ wisData.getRelease()
			- wisData.getDelivery_divr();
		*/
	}

	wisData.setDelivery_flow(deliveryFlow);
	__worksheet.setRowData(wisData, row);
}

/**
Evaluates formulas for the WIS.  Cannot currently evaluate circular cell
references.
@param flag computational flag (one of CONFLUENCE, __KNOWN_POINT_FLOW,
NATURAL_FLOW, or STRING).
@param curNode the node in which to evaluate the formula.
*/
private void computeFormulas(String flag, HydroBase_Node curNode) {
	HydroBase_WISFormat wisFormat;
	HydroBase_WISFormula wisFormula;

	int row = -1;
	int col = -1;
	int numFormulas = -1;
	int curFormula = -1;	
//Message.printStatus(1, "", "computeFormulas(" + flag + "): \n" + curNode);
	// if the flag is CONFLUENCE then check the TRIB_NATURAL_COL
	// and TRIB_DELIVERY_COL for a formula for the curNode.
	if (flag.equals(CONFLUENCE) && curNode != null) {
		wisFormat = curNode.getWISFormat();
		row = wisFormat.getWis_row() - 1;

		// return if the row type is not a confluence
		if (!wisFormat.getRow_type().equals(CONFLUENCE)) {
			return;
		}

		// check just the trib. natural as trib. delivery has 
		// already been processed.
		if (isFormulaCell(row, TRIB_NATURAL_COL)) {
			processFormula(row, TRIB_NATURAL_COL);
		}
	}
	// if flag is __KNOWN_POINT_FLOW, evaluate all formulae in the 
	// POINT_FLOW_COL which may reference other known point flows
	// which contain a value but not a formula.
	else if (flag.equals(__KNOWN_POINT_FLOW)) {
		numFormulas = __wisFormulaVector.size();
		for (curFormula=0; curFormula < numFormulas; curFormula++) {
			wisFormula = (HydroBase_WISFormula)
				__wisFormulaVector.get(curFormula);
			row = wisFormula.getWis_row() - 1;
			wisFormat = (HydroBase_WISFormat)
				__wisFormatVector.get(row);

			if ((isKnownPoint(wisFormat) || isDryRiver(wisFormat)) 
				&& isFormulaCell(row, POINT_FLOW_COL)) {
				processFormula(row, POINT_FLOW_COL);
			}
		}		
	}
	// if flag is NATURAL_FLOW, evaluate NATURAL_FLOW user enter formulae
	// for curNode
	else if (flag.equals(NATURAL_FLOW) && curNode != null) {
		wisFormat = curNode.getWISFormat();

		// don't process String row types
		if (wisFormat.getRow_type().equals(STRING)) {
			return;
		}

		row = wisFormat.getWis_row() - 1;
		if (isFormulaCell(row, NATURAL_FLOW_COL)) {
			processFormula(row, NATURAL_FLOW_COL);
		}
	}
	// if flag is STRING, evaluate all formulae which exist in 
	// in String row types. this should be called once the entire river
	// network has been computed.
	else if (flag.equals(STRING)) {
		numFormulas = __wisFormulaVector.size();
		for (curFormula = 0; curFormula < numFormulas; curFormula++) {
			wisFormula = (HydroBase_WISFormula)
				__wisFormulaVector.get(curFormula);
			row = wisFormula.getWis_row() - 1;
			col = getColumnNumber(wisFormula.getColumn());
			wisFormat = (HydroBase_WISFormat)
				__wisFormatVector.get(row);

			// process formula if specified in a String row type.
			if (wisFormat.getRow_type().trim().equals(STRING)) {
				processFormula(row, col);
			}
		}		
	}
}

/**
Reponds when the set date button is pressed.
*/
private void dateClicked() {
	String routine = CLASS + ".dateClicked()";
	
	DateTime from = null;

	try {	
		from = DateTime.parse(__dateJTextField.getText(),
			DateTime.FORMAT_YYYY_MM_DD_HH_mm );
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Exception parsing date.");
		Message.printWarning(2, routine, e);
		from = new DateTime(DateTime.PRECISION_MINUTE);
	}
	new DateTimeBuilderJDialog(this, __dateJTextField, from, __dateProps);
	try {	
		__adminDateTime = DateTime.parse(__dateJTextField.getText());
	}
	catch (Exception e) {;}
	wisChanged(true);
}

/**
Called when the Display Network button is pressed.
*/
private void displayDiagramClicked() {
	if (__diagramGUI == null) {
		__diagramGUI = new HydroBase_GUI_WISDiagram(this, __dmi);
		computeWIS();
	}
	else {
		__diagramGUI.setExtendedState(__diagramGUI.getExtendedState() 
			& ~ICONIFIED);
		__diagramGUI.toFront();
	}
	int rows = __worksheet.getRowCount();
	HydroBase_WISFormat format = null;
	String id = null;
	double pf = 0;
	for (int i = 1; i < rows; i++) {
		format = (HydroBase_WISFormat)__wisFormatVector.get(i);
		id = format.getIdentifier();
		pf = ((Double)__worksheet.getValueAt(i, 1)).doubleValue();
		
		__diagramGUI.updatePointFlow(id, pf);
	}	
}

/**
Called when WIS data is loaded to set a row as dry.
@param row the row to set dry.
*/
private void dryRiver(int row) {
	String routine = "dryRiver";

	HydroBase_WISData wisdata = 
		(HydroBase_WISData)__worksheet.getRowData(row);
	HydroBase_WISFormat wisformat = 
		(HydroBase_WISFormat)__wisFormatVector.get(row);

	int dryRiver = wisdata.getDry_river();

	// Set the dry river information in the node...
	HydroBase_Node node = __network.findNode(wisformat.getIdentifier());
	if (node == null) {
		Message.printWarning(1, routine,
			"Unable to find row in network.  Possible data error.");
		return;
	}
	else {	
		// Set the dry river flag for the node...
		if (dryRiver == 1) {
			node.setIsDryRiver(false);
		}
		else {	
			node.setIsDryRiver(true);
		}
	}

	// Set the HydroBase_WISData...

	if (dryRiver == 1) {
		Message.printStatus(1, routine,
			"Setting row " + row + " to NOT DRY");
		wisdata.setDry_river(0);
	}
	else {	
		Message.printStatus(1, routine,
			"Setting row " + row + " to DRY");
		wisdata.setDry_river(1);
	}
	__worksheet.refresh();
}

/**
For the selected row, set the dry river flag.  Set in both the 
HydroBase_WISData and the node.  This causes the display to be updated so 
that "DRY" is shown in the point flow and "0.0" is used for the point flow.  
If autmatically updating the WIS, it will be recomputed.
*/
private void dryRiverClicked() {
	String routine = CLASS + ".dryRiverClicked";

	int row = __worksheet.getSelectedRow();
	int col = __worksheet.getVisibleColumn(__worksheet.getSelectedColumn());
	if (row < 0) {
		return;
	}

	HydroBase_WISData wisdata = 
		(HydroBase_WISData)__worksheet.getRowData(row);
	HydroBase_WISFormat wisformat = 
		(HydroBase_WISFormat)__wisFormatVector.get(row);

	int dryRiver = wisdata.getDry_river();

	// Set the dry river information in the node...
	HydroBase_Node node = __network.findNode(wisformat.getIdentifier());
	if (node == null) {
		Message.printWarning(1, routine,
			"Unable to find row in network.  Possible data error.");
		return;
	}
	else {	
		// Set the dry river flag for the node...
		if (dryRiver == 1) {
			node.setIsDryRiver(false);
		}
		else {	
			node.setIsDryRiver(true);
		}
	}

	// Set the HydroBase_WISData...

	if (dryRiver == 1) {
		Message.printStatus(1, routine,
			"Setting row " + row + " to NOT DRY");
		wisdata.setDry_river(0);
		// Now reset the button appropriately(the row is already
		// selected so set to the opposite of its current value).
		__dryRiverJButton.setText(__BUTTON_DRY_RIVER);
	}
	else {	
		Message.printStatus(1, routine,
			"Setting row " + row + " to DRY");
		wisdata.setDry_river(1);
		// Now reset the button appropriately(the row is already
		// selected so set to the opposite of its current value).
		__dryRiverJButton.setText(__BUTTON_UNDRY_RIVER);
	}

	// Now that the data are set, update the display using whatever the
	// latest computation was...

	// avoid long loops caused by all the sets in the setRowValues() 
	// method.
	boolean holdAutoCalc = __autoCalc;
	__autoCalc = false;
	setRowValues(wisdata, row);
	__autoCalc = holdAutoCalc;
	__worksheet.selectRow(row);
	if (__autoCalc) {
		computeWIS();
	}
	else {	
		__worksheet.refresh();
	}
	__worksheet.setRequestFocusEnabled(true);
	__worksheet.requestFocus();
	__worksheet.selectCell(row, col);
	__worksheet.refresh();
}

/**
Responds to the Export JButton.  Export the WIS as text.
*/
private void exportClicked() {
	String routine = CLASS + ".exportClicked";
	try {
		List v = new Vector();
		String[] formats = new String[2];
		formats[0] = "Screen View";
		formats[1] = "txt";
		v.add(formats);
		formats = new String[2];
		formats[0] = "HTML file";
		formats[1] = "html";
		v.add(formats);
		String[] eff = 
			HydroBase_GUI_Util.getExportFilenameAndFormat(this, v);
		if (eff == null) {
			return ;
		}

		int format = new Integer(eff[1]).intValue();
	 	// First format the output...
		if (format == TEXT) {
			List outputStrings = formatTextOutput();
 			// Now export, letting the user decide the file...
			HydroBase_GUI_Util.export(this, eff[0], outputStrings);
		}
		else if (format == HTML) {
			formatHTMLOutput(eff[0]);
		}
	} 
	catch (Exception ex) {
		Message.printWarning(1, routine, "Error in Export.");
		Message.printWarning (2, routine, ex);
	}	
}

/**
Clean up before garbage collection.
*/
protected void finalize()
throws Throwable {
	__parent = null;
	__adminDateTime = null;
	__dmi = null;
	__loadWISGUI = null;
	__diagramGUI = null;
	__network = null;
	__wis = null;
	__tableModel = null;
	__archiveJButton = null;
	__calculateJButton = null;
	__clearJButton = null;
	__closeJButton = null;
	__dateJButton = null;
	__dryRiverJButton = null;
	__exportJButton = null;
	__loadJButton = null;
	__printJButton = null;
	__summaryJButton = null;
	__cellJPopupMenu = null;
	__diversionJPopupMenu = null;
	__tabJPanel = null;
	__commentsJTextField = null;
	__dateJTextField = null;
	__statusJTextField = null;
	__statusUserJTextField = null;
	__worksheet = null;
	__gainSimpleJComboBox = null;
	__cellGraphJMenuItem1 = null;
	__cellGraphJMenuItem2 = null;
	__wisDiversionCodingVector = null;
	__wisFormatVector = null;
	__wisFormulaVector = null;
	__wisImportVector = null;
	__dateProps = null;
	__set_date = null;
	__sheetName = null;
	__wisStatus = null;

	super.finalize();
}

/**
Find a row given a WD and ID.
@param wd Water district.
@param id Water district identifier.
@return the row matching the WD ID or -1 if not found.
*/
private int findRowForWDID(int wd, int id) {
	String routine = CLASS + ".findRowForWDID";
	
	int size = 0;
	if (__wisFormatVector != null) {
		size = __wisFormatVector.size();
	}

	String identifier;
	int[] wdidParts;
	for (int row = 0; row < size; row++) {
		identifier = getIdentifier(row);
		// Only interested in structures that have a WDID...
		if (!identifier.regionMatches(true, 0, "wdid:", 0, 5)) {
			continue;
		}
		try {
			wdidParts = HydroBase_WaterDistrict.parseWDID(
				identifier);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error parsing WDID.");
			Message.printWarning(2, routine, e);
			return -1;
		}
		if (wdidParts == null) {
			continue;
		}
		if (wdidParts[0] == wd && wdidParts[1] == id) {
			return row;
		}
	}
	return -1;
}

/**
Searches through the wis format vector to find the wis format row that has the
given identifier string.
@param identifier the identifier to match against.
@return the row number of the wis format row with the given identifier.  If 
none match, -1 is returned.
*/
private int findWISFormatRowWithIdentifier(String identifier) {
	HydroBase_WISFormat format = null;
	int size = __wisFormatVector.size();
	for (int i = 0; i < size; i++) {
		format = (HydroBase_WISFormat)__wisFormatVector.get(i);
		if (format.getIdentifier().equals(identifier)) {
			return i;
		}
	}
	return -1;
}

/**
Looks up the wis identifier that corresponds to the given wis data row number.
The identifier is looked up by querying the database for the wis_format record
that matches the given row number and the __oldFormatWISNum with which the
WIS was opened.
@param wis_row the wis_row of a wis_data object that should be matched.
@return the identifier string of the corresponding old wis_format record in 
the database, or null if no matches could be found.
*/
private String findWISIdentifierForRow(int wis_row) {
	HydroBase_WISFormat format = null;
	
	try {
		format = __dmi.readWISFormatForWis_numWis_row(
			__oldFormatWISNum, wis_row);	
	}
	catch (Exception e) {
		Message.printWarning(2, "", "Error reading wis_format for "
			+ "wis_num = " + __oldFormatWISNum + " and row = "
			+ wis_row);
		Message.printWarning(2, "", e);
	}

	if (format == null) {
		return null;
	}
	else {
		return format.getIdentifier();
	}
}

/**
Formats output to an HTML file.
@param filename the name of the file to write.
*/
private void formatHTMLOutput(String filename) {
	String routine = CLASS + ".formatHTMLOutput";
	try {
		int numRows = __worksheet.getRowCount();

		// add the header information which includes sheet name 
		// and set date.
		String headerString = "Water Information Sheet: ";
		headerString +=  __sheetName;

		HTMLWriter html = new HTMLWriter(filename, headerString, true);

		DateTime dt = new DateTime(DateTime.DATE_CURRENT | 
			DateTime.PRECISION_MINUTE);

		html.comment("WIS Exported from CWRAT at:");
		html.comment("  " + dt.toString());
		
		html.heading(3, headerString);				

		headerString = "Administrative date: ";
		headerString += __dateJTextField.getText().trim();
		html.heading(4, headerString);

		headerString = "Created: ";
		headerString += "" + (new DateTime(DateTime.DATE_CURRENT));
		html.heading(4, headerString);

		html.paragraph();

		html.tableStartFloatLeft("border=2");

		html.tableRowStart("bgcolor=gray");		
		String s;
		for (int curCol = 0; curCol < NUM_COLUMNS; curCol++) {
			s = __worksheet.getColumnName(curCol, true);
			html.tableHeader(
				StringUtil.formatString(s,
				"%-30.30s"));
		}
		html.tableRowEnd();

		// row loop
		Double cellD;
		Color bg = null;
		Color fg = null;
		for (int curRow = 0; curRow < numRows; curRow++) {
			html.tableRowStart();
	
			// column heading
			s = (String)(__worksheet.getValueAt(curRow, 0));
			html.tableCell(StringUtil.formatString(s, "%-30.30s"));
	
			// values
	
			// Note:
			// there is some confusing code here which is related to
			// the visible vs. the absolute column numbers of the 
			// worksheet.  See the javadocs for JWorksheet for more
			// explanation.  
			
			for (int curCol = 1; curCol < (COMMENTS_COL - 1); 
				curCol++) {
				bg = getCellBackgroundColor(curRow, 
					__worksheet.getAbsoluteColumn(curCol));
				fg = getCellTextColor(curRow, 
					__worksheet.getAbsoluteColumn(curCol));
				cellD = (Double)(__worksheet.getValueAt(curRow, 
					curCol));
				if (curCol == 1) {
					s = __worksheet.getCellAlternateText(
						curRow, POINT_FLOW_COL);
					if (s == null) {
						if (DMIUtil.isMissing(cellD)) {
							s = StringUtil
								.formatString(
								" ", "%10.10s");
						}
						else {
							s = StringUtil
								.formatString(
								cellD,"%10.1f");
						}
					}				
					else {
						s = StringUtil.formatString(
							s, "%10.10s");
					}
				}
				else {
					if (DMIUtil.isMissing(cellD)) {
						s = StringUtil.formatString(
							" ", "%10.10s");
					}
					else {
						s = StringUtil.formatString(
							cellD, "%10.1f");
					}
				}
				if (bg != null) {
					html.tableCellStart("bgcolor=#"
						+ MathUtil.decimalToHex(
							bg.getRed()) + ""
						+ MathUtil.decimalToHex(
							bg.getGreen()) + ""
						+ MathUtil.decimalToHex(
							bg.getBlue()));
				}
				else {
					html.tableCellStart();
				}
				if (fg != null) {
					html.fontStart("color=#"
						+ MathUtil.decimalToHex(
							fg.getRed()) + ""
						+ MathUtil.decimalToHex(
							fg.getGreen()) + ""
						+ MathUtil.decimalToHex(
							fg.getBlue()));
				}
				html.addText(s);
				if (fg != null) {
					html.fontEnd();
				}
				html.tableCellEnd();
			}
	 
			// comments
			s = (String)(__worksheet.getValueAt(curRow, 
				(COMMENTS_COL - 1)));
			s = StringUtil.formatString(s.trim(), "%s");
			html.tableCell(s);
			html.tableRowEnd();
		}
		html.tableEnd();
	
		html.closeFile();	
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error writing HTML.  See "
			+ "log file for details.");
		Message.printWarning(2, routine, e);
	}
}

/**
Formats WIS data for text-based export.
@return a formatted Vector for exporting, printing, etc.
*/
private List formatTextOutput() {
	List v = new Vector();
	int numRows = __worksheet.getRowCount();
	String DELIM = " ";

	// Add some blank lines for punching...
	v.add("");
	v.add("");

	// add the header information which includes sheet name and set date.
	String headerString = "Water Information Sheet: ";
	headerString +=  __sheetName;
	v.add(headerString);

	headerString = "Administrative date: ";
	headerString += __dateJTextField.getText().trim();
	v.add(headerString);

	headerString = "";
	v.add(headerString);
	
	// column headers
	String cellString;
	String rowString = "";
	/*
	for (int curCol = 0; curCol < NUM_COLUMNS; curCol++) {
		cellString = __worksheet.getColumnName(curCol, true);
		if (curCol == 0) {
			// Use 30 characters for names...
			rowString += StringUtil.formatString(cellString,
				"%-30.30s") + DELIM;
		}
		else {	
			// Use 10 characters for others...
			rowString += StringUtil.formatString(cellString,
				"%10.10s") + DELIM;
		}
	}
	v.add(rowString);
	*/

	v.add("                               Point Flow    Natural   "
		+ "Delivery                        Priority   Delivery   "
		+ "  Trib.    Trib.");
	v.add("Row Label                      Point Flow    Flow      "
		+ "Flow      Gain Loss   Releases  Diversion  Diversion  "
		+ "  Natural  Delivery  Comments");	
	v.add("--------------------------------------------------------"
		+ "----------------------------------------------------"
		+ "-----------------------------------------");

	// row loop
	Double cellD;
	String s;
	for (int curRow = 0; curRow < numRows; curRow++) {
		rowString = "";

		// column heading
		cellString = (String)(__worksheet.getValueAt(curRow, 0));
		rowString += StringUtil.formatString(cellString, "%-30.30s")
			+ DELIM;

		// values

		// Note:
		// there is some confusing code here which is related to 
		// the visible vs. the absolute column numbers of the worksheet.
		// See the javadocs for JWorksheet for more explanation.  
		
		for (int curCol = 1; curCol < (COMMENTS_COL - 1); curCol++) {
			cellD = (Double)(__worksheet.getValueAt(curRow, 
				curCol));
			if (curCol == 1) {
				s = __worksheet.getCellAlternateText(curRow,
					POINT_FLOW_COL);
				if (s == null) {
					if (DMIUtil.isMissing(cellD)) {
						rowString += 
							StringUtil.formatString(
							" ", "%10.10s") +DELIM;
					}
					else {
						rowString += 
							StringUtil.formatString(
							cellD, "%10.1f") +DELIM;
					}
				}				
				else {
					rowString += StringUtil.formatString(
						s, "%10.10s") + DELIM;
				}
			}
			else {
				if (DMIUtil.isMissing(cellD)) {
					rowString += StringUtil.formatString(
						" ", "%10.10s") + DELIM;
				}
				else {
					rowString += StringUtil.formatString(
						cellD, "%10.1f") + DELIM;
				}
			}
		}
 
		// comments
		cellString = (String)(__worksheet.getValueAt(curRow, 
			(COMMENTS_COL - 1)));
		rowString += StringUtil.formatString(cellString.trim(), "%s") 
			+ DELIM;

		v.add(rowString);
	}
	
	return v;
}

/**
Returns the admin date time as a DateTime object.
@return the admin date time.
*/
protected DateTime getAdminDateTime() {
	return __adminDateTime;
}

/**
Returns the admin date time as a string (formatted to YYYY MM DD HH mm).
@return the admin date time as a string (formatted to YYYY MM DD HH mm).
*/
protected String getAdminDateTimeString() {
	return __adminDateTime.toString(
		DateTime.FORMAT_YYYY_MM_DD_HH_mm);
}

/**
Returns the admin date time as a string in the given format.
@format the DateTime.FORMAT_* in which to return the date time.
@return the admin date time as a string.
*/
protected String getAdminDateTimeString(int format) {
	return __adminDateTime.toString(format);
}

/**
This routine formats cell contents for archiving since empty Strings
cannot be archived into fields where a value is expected.
@param row Row number.
@param col the <b>absolute</b> Column number.
@return Returns the contents of the specified cell. If cell is empty,
0.0 is returned.
*/
private String getArchiveCellContents(int row, int col) {
	if ((col == (ROW_LABEL_COL)) 
		|| (col == (COMMENTS_COL))) {
		return (String)(__worksheet.getValueAt(row, col - 1));
	}
	Double D = (Double)(__worksheet.getValueAt(row, col - 1));
	double d = D.doubleValue();

	return StringUtil.formatString(d, "%10.1f");
}

/**
Returns the background color for the cell at the specified position.
@param row the row of the cell
@param absoluteColumn the absolute column of the cell.
@return the background color of the cell, or null if the cell's background color
is the default.
*/
private Color getCellBackgroundColor(int row, int absoluteColumn) {
	if (row >= 0 && absoluteColumn >= 0) {
		JWorksheet_CellAttributes ca = __worksheet.getCellAttributes(
			row, absoluteColumn);
		if (ca != null) {
			return ca.backgroundColor;
		}
	}
	return null;
}

/**
Return the WIS cell contents for a given row and column.
@param row Row number.
@param col <b>visible</b> Column number.
@return Contents of cell.
*/
public String getCellContents(int row, int col) {
	return "" + (__worksheet.getValueAt(row, col));
}

/**
Return the HydroBase_WISFormula object corresponding to the specified cell 
address.
@param row row number.
@param column column number.
*/
private HydroBase_WISFormula getCellFormula(int row, int col) {
	return HydroBase_GUI_WISBuilder.getCellFormula(__wisFormulaVector, 
		row, col);
}

/**
Returns the HydroBase_WISImport object corresponding to the specified cell
address.
@param row the row of the cell
@param col the column of the cell.
@return the HydroBase_WISImport object for the specified cell, or null if 
none exist.
*/
private HydroBase_WISImport getCellImport(int row, int col) {
	HydroBase_WISImport wisImport = null;
	int curCol;
	int curRow;

	// loop through the __wisImportVector object to locate the specified 
	// row and column
	int size = __wisImportVector.size();
	for (int i = 0; i < size; i++) {
		wisImport = (HydroBase_WISImport)__wisImportVector.get(i);
		curCol = getColumnNumber(wisImport.getColumn());
		curRow = wisImport.getWis_row() - 1;
		if (curCol == col && curRow == row) {
			return wisImport;
		}
	}
	return null;
}

/**
Returns the text color for the cell at the specified position.
@param row the row of the cell
@param absoluteColumn the absolute column of the cell.
@return the text color of the cell, or null if the cell's text color
is the default.
*/
private Color getCellTextColor(int row, int absoluteColumn) {
	if (row >= 0 && absoluteColumn >= 0) {
		JWorksheet_CellAttributes ca = __worksheet.getCellAttributes(
			row, absoluteColumn);
		if (ca != null) {
			return ca.foregroundColor;
		}
	}
	return null ;
}

/**
Get the column label for the column.
@param col <b>visible</b> column of interest.
*/
public String getColumnLabel(int col) {
	return __columnHeadings[col];	
}

/**
Returns the requested column as an int.
@param colHeading the String representing the desired column.
@return the column number.
*/
public static int getColumnNumber(String colHeading) {
	String routine = CLASS + ".getColumnNumber";
	if (Message.isDebugOn) {
		Message.printDebug(40, routine, "getting column number for \""
			+ colHeading + "\"");
	}

        if (colHeading.equals(S_POINT_FLOW)) {
        	return POINT_FLOW_COL;
	}
        else if (colHeading.equals(S_NATURAL_FLOW)) {
                return NATURAL_FLOW_COL;
	}
        else if (colHeading.equals(S_DELIVERY_FLOW)) {
                return DELIVERY_FLOW_COL;
	}
        else if (colHeading.equals(S_GAIN_LOSS)) {
                return GAIN_LOSS_COL;
	} 
        else if (colHeading.equals(S_TRIB_NATURAL)) {
                return TRIB_NATURAL_COL;
	}
        else if (colHeading.equals(S_TRIB_DELIVERY)) {
                return TRIB_DELIVERY_COL;
	}
        else if (colHeading.equals(S_RELEASES)) {
                return RELEASES_COL;
	}
        else if (colHeading.equals(S_PRIORITY_DIV)) {
                return PRIORITY_DIV_COL;
	}
        else if (colHeading.equals(S_DELIVERY_DIV)) {
                return DELIVERY_DIV_COL;
	}
	else {	
		if (Message.isDebugOn) {
			Message.printDebug(10, routine, 
				"Unable to get column number for \"" 
				+ colHeading + "\"");
		}
		return DMIUtil.MISSING_INT;
	}
}

/**
Returns the time series data type for data stored in the specified column.
@param column the <b>absolute</b> number of the column for which to return
the data type.  ROW_LABEL_COL is 1, POINT_FLOW_COL is 2, etc.
@return the time series data type for the specified column.
*/
private String getColumnTSDataType(int column) {
	String columnName = __columnHeadings[column - 1];

	if (columnName.equals(ROW_LABEL)) {
		return "";
	}
	else if (columnName.equals(POINT_FLOW)) {
		return "WISPointFlow";
	}
	else if (columnName.equals(NATURAL_FLOW)) {
		return "WISNaturalFlow";
	}
	else if (columnName.equals(DELIVERY_FLOW)) {
		return "WISDeliveryFlow";
	}
	else if (columnName.equals(GAIN_LOSS)) {
		return "WISGainLoss";
	}
	else if (columnName.equals(TRIB_NATURAL)) {
		return "WISTribNaturalFlow";
	}
	else if (columnName.equals(TRIB_DELIVERY)) {
		return "WISTribDeliveryFlow";
	}
	else if (columnName.equals(RELEASES)) {
		return "WISRelease";
	}
	else if (columnName.equals(PRIORITY_DIV)) {
		return "WISPriorityDiversion";
	}
	else if (columnName.equals(DELIVERY_DIV)) {
		return "WISDeliveryDiversion";
	}
	else if (columnName.equals(COMMENTS)) {
		return "";
	}
	else if (columnName.equals(DRY_RIVER)) {
		return "";
	}
	return "";
}
	
/**
Get the records from __wisDiversionCodingVector for the currently selected
cell.  The records should be sorted by row and then column.  This should only
be called when editing diversion coding.  The WD, ID, and WIS column are
checked.
@return the records for the currently-selected cell.
*/
public List getDiversionCodingForCurrentCell() {
	String routine = CLASS + ".getDiversionCodingForCurrentCell";
	List foundRecords = new Vector();
	int size = __wisDiversionCodingVector.size();
	HydroBase_WISDailyWC record = null;
	String identifier = getIdentifier(getSelectedRow());
	if (!identifier.regionMatches(true, 0, "wdid:", 0, 5)) {
		return null;
	}
	int[] wdidParts = null;
	try {
		wdidParts = HydroBase_WaterDistrict.parseWDID(identifier);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error parsing WDID.");
		Message.printWarning(2, routine, e);
		return null;
	}
	if (wdidParts == null) {
		return null;
	}
	int wd = wdidParts[0];
	int id = wdidParts[1];

	String wis_column = "";
	int col = __worksheet.getSelectedColumn();
	if (col == RELEASES_COL) {
		wis_column = "RR";
	}
	else if (col == PRIORITY_DIV_COL) {
		wis_column = "PD";
	}
	else {	
		wis_column = "DD";
	}
	for (int i = 0; i < size; i++) {
		record = (HydroBase_WISDailyWC)
			__wisDiversionCodingVector.get(i);
		if ((wd == record.getWD()) &&(id == record.getID()) 
			&& wis_column.equals(record.getWis_column())) {
			// OK not to make a copy since the values are just
			// being displayed...
			foundRecords.add(record);
		}
	}

	return foundRecords;
}

/**
Get the internal identifier for a row.
@param row Row of interest.
@return the internal identifier for the row(e.g., wdid:...).
*/
public String getIdentifier(int row) {
	HydroBase_WISFormat wisFormat = 
		(HydroBase_WISFormat)__wisFormatVector.get(row);
	if (wisFormat == null) {
		return "";
	}
	return wisFormat.getIdentifier();
}

/**
Get the row label for the row.
@param row the row for which to return the row label.
@return the row label for the specified row.
*/
public String getRow_label(int row) { 
	HydroBase_WISFormat wisFormat = 
		(HydroBase_WISFormat)__wisFormatVector.get(row);
	if (wisFormat == null) {
		return "";
	}
	return wisFormat.getRow_label();
}

/**
Get the row type for the row.
@param row the row for which to return the row label.
@return the row type for the specified row.
*/
public String getRow_type(int row) { 
	HydroBase_WISFormat wisFormat = 
		(HydroBase_WISFormat)__wisFormatVector.get(row);
	if (wisFormat == null) {
		return "";
	}
	return wisFormat.getRow_type();
}

/**
Returns the specified row values as a HydroBase_WISData object.
@param row the row number to evaluate.
@return the HydroBase_WISData object for the specified row.
*/
/*
SAM REVISIT 2007-02-08
Evaluate whether this can be removed.
private HydroBase_WISData getRowValues(int row) {
	double curDouble;
	HydroBase_WISData rowData = new HydroBase_WISData();	
	String curString =(String)(__worksheet.getValueAt(row, 
		POINT_FLOW_COL - 1));
	if (curString.equals("DRY")) {
		rowData.setDry_river(1);
		curDouble = 0.0;
	}
	else {	
		curDouble = ((Double)__worksheet.getValueAt(row, 
			POINT_FLOW_COL - 1)).doubleValue();
		rowData.setDry_river(0);
		if (DMIUtil.isMissing(curDouble) ) {
			curDouble = 0.0;
		}
	}
	rowData.setPoint_flow(curDouble);			

	curDouble = ((Double)__worksheet.getValueAt(row, 
		NATURAL_FLOW_COL - 1)).doubleValue();
	if (DMIUtil.isMissing(curDouble) ) {
		curDouble = 0.0;
	}
	rowData.setNat_flow(curDouble);

	curDouble = ((Double)__worksheet.getValueAt(row, 
		DELIVERY_FLOW_COL - 1)).doubleValue();
	if (DMIUtil.isMissing(curDouble) ) {
		curDouble = 0.0;
	}
	rowData.setDelivery_flow(curDouble);

	curDouble = ((Double)__worksheet.getValueAt(row, 
		GAIN_LOSS_COL - 1)).doubleValue();
	if (DMIUtil.isMissing(curDouble) ) {
		curDouble = 0.0;
	}
	rowData.setGain(curDouble);

	curDouble = ((Double)__worksheet.getValueAt(row, 
		TRIB_NATURAL_COL - 1)).doubleValue();
	if (DMIUtil.isMissing(curDouble) ) {
		curDouble = 0.0;
	}
	rowData.setTrib_natural(curDouble);

	curDouble = ((Double)__worksheet.getValueAt(row, 
		TRIB_DELIVERY_COL - 1)).doubleValue();
	if (DMIUtil.isMissing(curDouble) ) {
		curDouble = 0.0;
	}
	rowData.setTrib_delivery(curDouble);

	curDouble = ((Double)__worksheet.getValueAt(row, 
		PRIORITY_DIV_COL - 1)).doubleValue();
	if (DMIUtil.isMissing(curDouble) ) {
		curDouble = 0.0;
	}
	rowData.setPriority_divr(curDouble);

	curDouble = ((Double)__worksheet.getValueAt(row, 
		DELIVERY_DIV_COL - 1)).doubleValue();
	if (DMIUtil.isMissing(curDouble) ) {
		curDouble = 0.0;
	}
	rowData.setDelivery_divr(curDouble);

	curDouble = ((Double)__worksheet.getValueAt(row, 
		RELEASES_COL - 1)).doubleValue();
	if (DMIUtil.isMissing(curDouble) ) {
		curDouble = 0.0;
	}
	rowData.setRelease(curDouble);
	
	rowData.setComment((String)__worksheet.getValueAt(row, 
		COMMENTS_COL - 1));

	return rowData;
}
*/

/**
Get the selected column.
@return the selected column.
*/
public int getSelectedColumn() {
	return __worksheet.getSelectedColumn();
}

/**
Get the selected row.
@return the selected row.
*/
public int getSelectedRow() {
	return __worksheet.getSelectedRow();
}

/**
This routine searches the __wisFormat for the unique id which matches the 
unique id for the wisMath object.  The cell row and column values are then
set.
@param wisMath the wisMath object to match.
@param wisFormula the formula that contains the wisMath term.
@return the wisMath object with its row and col values set.
*/
private HydroBase_WISMath getTermReference(HydroBase_WISMath wisMath, 
HydroBase_WISFormula wisFormula) {
	String routine = CLASS + ".getTermReference()";

	int size = __wisFormatVector.size();
	String uniqueID = wisMath.getUniqueID().trim();
	String rowJLabel = wisMath.getLabel().trim();
	String columnType = wisMath.getColumnType();
	int row = wisFormula.getWis_row() - 1;
	int col = getColumnNumber(wisFormula.getColumn());
	
	HydroBase_WISFormat wisFormat;
	for (int i = 0; i < size; i++) {
		wisFormat = (HydroBase_WISFormat)__wisFormatVector.get(i);

		if (wisFormat.getIdentifier().trim().equals(uniqueID)) {
			wisMath.setRowNumber(wisFormat.getWis_row() - 1);
			wisMath.setColumnNumber(getColumnNumber(columnType));
			return wisMath;
		}
	}
	Message.printWarning(1, routine,
		"WIS cannot perform computations since the"
		+ "\nFormula in row: " + row + " column: " + col
		+ "\nrefers to the following term which does not exist:"
		+ "\n" + rowJLabel + "." + columnType );
	return wisMath;
}

/**
Get the WIS number.
@return the wis number.
*/
public int getWISNumber() {
	return __wisNum;
}

/**
Returns the specified variable from the worksheet for the requested row.  
Values are not returned directly from the worksheet in order to handle 
MISSING data values nicely.
@param row the row number to evaluate.
@param col the data flag
@return the value for the specified row variable.
*/
private double getWISDataValue(int row, int col) {
	double value = DMIUtil.MISSING_DOUBLE;

	HydroBase_WISData data = 
		(HydroBase_WISData)__worksheet.getRowData(row);

	switch (col) {
		case POINT_FLOW_COL:
			value = data.getPoint_flow();
			break;
		case NATURAL_FLOW_COL:
			value = data.getNat_flow();
			break;
		case DELIVERY_FLOW_COL:
			value = data.getDelivery_flow();
			break;
		case GAIN_LOSS_COL:
			value = data.getGain();
			break;
		case TRIB_NATURAL_COL:
			value = data.getTrib_natural();
			break;
		case TRIB_DELIVERY_COL:
			value = data.getTrib_delivery();
			break;
		case RELEASES_COL:
			value = data.getRelease();
			break;
		case PRIORITY_DIV_COL:
			value = data.getPriority_divr();
			break;
		case DELIVERY_DIV_COL:
			value = data.getDelivery_divr();
			break;		
	}
	if (DMIUtil.isMissing(value)) {
		return 0;
	}
	return value;
}

/**
Returns the specified variable from the __wisFormatVector for the requested 
row.
@param row the row number to evaluate.
@param flag the data flag
@return the value for the specified row variable.
*/
/* SAM REVISIT 2007-02-08
Evaluate whether this can be removed.
private double getWISFormatValue(int row, int flag) {
	double value = DMIUtil.MISSING_DOUBLE;

	if (row < 1) {
		return 0.0;
	}
	HydroBase_WISFormat data = 
		(HydroBase_WISFormat)__wisFormatVector.elementAt(row);

	switch (flag) {
		case DISTANCE:
			value = data.getStr_mile();
			break;
		case WEIGHT:
			value = data.getGain_factor();
			break;
	}
	if (DMIUtil.isMissing(value)) {
		return 0;
	}
	return value;
}
*/

/**
Returns the vector of wis formats.
@return the vector of wis formats.
*/
public List getWisFormatVector() {
	return __wisFormatVector;
}

/**
Returns the location of the specified formula cell.
@param row the row number
@param col the column number
@return the index within the __wisFormulaVector object for the specified row
and column if one is located.  Returns DMIUtil.MISSING_INT if one was not 
located.
*/
private int getWISFormulaIndex(int row, int col) { 
	HydroBase_WISFormula wisFormula;
	int size = __wisFormulaVector.size();
	int wisCol;
	int wisRow;
	for (int i = 0; i < size; i++) {
		wisFormula = 
			(HydroBase_WISFormula)__wisFormulaVector.get(i);
		wisCol = getColumnNumber(wisFormula.getColumn());
		wisRow = wisFormula.getWis_row() - 1;
		if (wisCol==col && wisRow==row) {
			return i;
		}
	}
	return DMIUtil.MISSING_INT;
}

/**
Get the total value from the main sheet for a row that has a structure matching
the WDID and the given column.
@param wd Water district for structure of interest.
@param id Identifier for structure of interest.
@param wis_col RELEASE_COL, PRIORITY_DIV_COL, OR DELIVERY_DIV_COL.
*/
private double getWDIDCellContents(int wd, int id, int wis_col) { 
	int pos = findRowForWDID(wd, id);
	if (pos >= 0) {
		// Found the structure...
		HydroBase_WISData wisdata = 
			(HydroBase_WISData)__worksheet.getRowData(pos);
		if (wis_col == RELEASES_COL) {
			return wisdata.getRelease();
		}
		else if (wis_col == PRIORITY_DIV_COL) {
			return wisdata.getPriority_divr();
		}
		else if (wis_col == DELIVERY_DIV_COL) {
			return wisdata.getDelivery_divr();
		}
	}

	return 0.0;
}

/**
Graph the current selected cell's data.  Currently the entire period is
returned.  This method will only be called if a row has data.
*/
private void graphCell() {
	int[] graphCols = null;
	String dataType = null;
	String tsidentString = null;
	TS ts = null;
	List tslist = new Vector();
	List v = __worksheet.getSelectedCells();

/*
	int col = __worksheet.getSelectedColumn();
	int row = __worksheet.getSelectedRow();
*/
	
	int[] rows = (int[])v.get(0);
	int[] cols = (int[])v.get(1);

	int row = -1;
	int col = -1;

	int size = rows.length;

	for (int cell = 0; cell < size; cell++) {

	row = rows[cell];
	col = cols[cell];

	HydroBase_WISFormat wisformat = (HydroBase_WISFormat)__wisFormatVector.get(row);
	String rowType = wisformat.getRow_type();
	if (col == 0) {
		// do the following so all cells are graphed, but only once
		size = 1;

		// Entire row is selected so graph the columns that have numerical data...
		if (rowType.equals(STATION)) {
			// Only point flow and resulting computed columns
			graphCols = new int[4];
			graphCols[0] = POINT_FLOW_COL;
			graphCols[1]	= NATURAL_FLOW_COL;
			graphCols[2]	= DELIVERY_FLOW_COL;
			graphCols[3]	= GAIN_LOSS_COL;
		}
		else if (rowType.equals(CONFLUENCE)) {
			// Only first four and the confluence columns...
			graphCols = new int[6];
			graphCols[0] = POINT_FLOW_COL;
			graphCols[1]	= NATURAL_FLOW_COL;
			graphCols[2]	= DELIVERY_FLOW_COL;
			graphCols[3]	= GAIN_LOSS_COL;
			graphCols[4]	= TRIB_NATURAL_COL;
			graphCols[5]	= TRIB_DELIVERY_COL;
		}
		else if (rowType.equals(DIVERSION) || rowType.equals(RESERVOIR) 
			|| rowType.equals(MIN_FLOW_REACH)) {
			// Only first four and the regulation columns(SAMX:
			// maybe MFR needs to be treated differently)...
			graphCols = new int[7];
			graphCols[0] = POINT_FLOW_COL;
			graphCols[1]	= NATURAL_FLOW_COL;
			graphCols[2]	= DELIVERY_FLOW_COL;
			graphCols[3]	= GAIN_LOSS_COL;
			graphCols[4]	= RELEASES_COL;
			graphCols[5]	= PRIORITY_DIV_COL;
			graphCols[6]	= DELIVERY_DIV_COL;
		}
		else {	
			// OTHER - assume all may be graphed...
			graphCols = new int[9];
			graphCols[0] = POINT_FLOW_COL;
			graphCols[1]	= NATURAL_FLOW_COL;
			graphCols[2]	= DELIVERY_FLOW_COL;
			graphCols[3]	= GAIN_LOSS_COL;
			graphCols[4]	= RELEASES_COL;
			graphCols[5]	= PRIORITY_DIV_COL;
			graphCols[6]	= DELIVERY_DIV_COL;
			graphCols[7]	= TRIB_NATURAL_COL;
			graphCols[8]	= TRIB_DELIVERY_COL;
		}
	}
	else {	
		// Graph single column...
		graphCols = new int[1];
		graphCols[0] = __worksheet.getAbsoluteColumn(col);
	}

	for (int i = 0; i < graphCols.length; i++) {
		// Put together a time series identifier string.  The WIS number
		// is the location and the column heading is the scenario...
		dataType = getColumnTSDataType(graphCols[i]);

		if (dataType.equals("")) {
			// shouldn't happen, because columns are checked
			// above, but just in case ...
			continue;
		}
		
		tsidentString = wisformat.getIdentifier() + ".DWR."
			+ dataType + ".Day." + __sheetName;
			
		Message.printStatus(1, CLASS + ".graphCell",
			"Getting \"" + tsidentString + "\"");

		try {	
			ts = __dmi.readTimeSeries(tsidentString, 
				null,	// date 1
				null,	// date 2
				null, 	// units
				true,	// read data?
				null);	// props
		}
		catch (Exception e) {
			Message.printWarning(2, CLASS + ".graphCell", e);
			continue;
		}
		
		if (ts == null) {
			Message.printWarning(1, CLASS + ".graphCell",
				"Error getting data for time series.  "
				+ "Unable to graph.");
			continue;
		}
		tslist.add(ts);
	}

	}

	if (tslist.size() == 0) {
		Message.printWarning(1, CLASS + ".graphCell",
			"Unable to get data for cell time series.");
		return;
	}

	PropList props = new PropList("LineGraph");
	props.set("InitialView", "Graph");
	props.set("CalendarType", "" + YearType.WATER);
	props.set("TotalWidth", "750");
	props.set("TotalHeight", "550");
	props.set("DisplayFont", "Courier");
	props.set("DisplaySize", "11");
	props.set("PrintFont", "Courier");
	props.set("PrintSize", "7");
	props.set("PageLength", "100");
	props.set("InitialView", "Graph");
	props.set("GraphType", "Line");

	props.set("TitleString", "" + __sheetName + " "
		+ __adminDateTime.toString(DateTime.FORMAT_YYYY_MM_DD)
		+ " WIS");
	
	try {	
		new TSViewJFrame(tslist, props);
	}
	catch (Exception e) {
		Message.printWarning(1, CLASS + ".graphCell",
			"Error graphing data.  See log file for details.");
		Message.printWarning(2, CLASS + ".graphCell", e);
	}
}

/**
Initialize the __wisDiversionCoding Vector.  Because each structure may have
different dates from which the data are carried forward, need to loop 
through the records in the sheet, updating the diverison coding as necessary.
After this method is called, the in-memory diversion coding records should match
the values shown as the total.
*/
private void initializeDiversionCoding() {
	String routine = CLASS + ".initializeDiversionCoding";

	// Check the user preference...
	if (!__diversionCodingEnabled) {
		return;
	}

	DateTime detailDate = null;
	try {	
		detailDate = DateTime.parse(__dateJTextField.getText().trim(),
			DateTime.FORMAT_YYYY_MM_DD_HH_mm);
	}
	catch (Exception e) {
		Message.printWarning(1, routine,
			"Unable to determine date from WIS.");
		Message.printWarning(2, routine, e);
		return;
	}
	HydroBase_WISDailyWC record = null;	
	int[] wdidParts = null;
	int maxDataCount = 0;
	int nearestDay = 0;
	int nodatacount = 0;
	int rsize = 0;
	int size = __worksheet.getRowCount();
	String identifier = null;
	String wis_column = null;	
	List records = null;
	for (int col = 0; col < 3; col++) {
		// Only interested in reservoir releases (actually, not yet
		// supported), priority diversions, and delivery diversions...
		if (col == 0) {
			wis_column = "RR";
		}
		else if (col == 1) {
			wis_column = "PD";
		}
		else {	
			wis_column = "DD";
		}
		for (int i = 0; i < size; i++) {
			identifier = getIdentifier(i);
			// Only interested in structures that have a WDID...
			if (!identifier.regionMatches(true, 0, "wdid:", 0, 5)) {
				continue;
			}
			++maxDataCount;
			try {
				wdidParts = HydroBase_WaterDistrict.parseWDID(
					identifier);
			}
			catch (Exception e) {
				Message.printWarning(1, routine, "Error "
					+ "parsing WDID.");
				Message.printWarning(2, routine, e);
			}
			if (wdidParts == null) {
				continue;
			}
			// The results of the following are a group of records
			// that have the same day, and the day is as near to
			// or equal the sheet date as possible(within the same
			// irrigation year).
			try {
				records = __dmi.readWISDailyWCList(__wisNum, 
					wis_column, wdidParts[0], wdidParts[1], 
					detailDate, null, DMIUtil.MISSING_INT, 
					null, null, 0);
			}
			catch (Exception e) {
				Message.printWarning(1, routine, "Error "
					+ "parsing WDID.");
				Message.printWarning(2, routine, e);
			}

			if (records == null) {
				++nodatacount;
				continue;
			}
			// Add to the main list.  Find the nearest value to the
			// current date and use it, setting the observation flag
			// to "C" to indicate carry forward if the date is not
			// today.  The only slots of interest on the record is
			// the daily values.
			rsize = records.size();
			for (int j = 0; j < rsize; j++) {
				record = (HydroBase_WISDailyWC)
					records.get(j);
				nearestDay = record.findNearestDataDay(
					__adminDateTime.getYear(),
					__adminDateTime.getMonth(),
					__adminDateTime.getDay());
				if (nearestDay != __adminDateTime.getDay()) {
					// Copy the values of the carry-forward
					// date to the current date...
					record.setAmountForDay(
						__adminDateTime.getDay(),
						record.getAmountForDay(
						nearestDay));
					record.setObservationForDay(
						__adminDateTime.getDay(), "C");
				}
				// Else values are on the same day already.
				__wisDiversionCodingVector.add(record);
			}
		}
	}

	if (__editable) {
		Message.printWarning(1, routine, 
			"There are " + nodatacount + " row/column "
			+ "combinations without\ndiversion coding to "
			+ "carry forward out of a possible " 
			+ maxDataCount + "."
			+ "\nEdit the diversion coding for each diversion and "
			+ "reservoir to initialize values.\n"
			+ "Use the Summary button to review rows without "
			+ "diversion coding.");
	}
}

/**
Determines if auto calculation is selected.
@return true if auto calculation is selected, false otherwise.
*/
public boolean isAutoRecalculation() {
	return __autoCalc;
}

/**
This routine determines if the given row is a dry river row.
@param row Row number.
@return true if a dry river row, false if not.
*/
protected boolean isDryRiverRow(int row) {
	HydroBase_WISData wisdata = 
		(HydroBase_WISData)__worksheet.getRowData(row);
	int dryRiver = wisdata.getDry_river();
	if (dryRiver == 1) {
		return true;
	}
	return false;
}

/**
Returns true if the HydroBase_WISFormat instance is a dry river, false 
otherwise.
@param wisformat WIS format row.
@return true if the HydroBase_WISFormat instance is a dry river, false
otherwise.
*/
protected boolean isDryRiver(HydroBase_WISFormat wisformat) {
	int row = wisformat.getWis_row() - 1;
	if (row < 0) {
		return false;
	}
	if (isDryRiverRow(row)) {
		return true;
	}
	return false;
}

/**
Determines if the given cell is an entry cell.
@param row row number of the cell.
@param col column number of the cell.
@return true if the cell is an entry cell, false otherwise.
*/
public boolean isEntryCell(int row, int col) {
	return ((HydroBase_WISFormat)__wisFormatVector.get(row))
		.isEntryCell(col);
}

/**
Determines if the given cell is a formula cell.
@param row row number of the cell
@param col column number of the cell
@return true if the cell is a formula cell, false otherwise.
*/
private boolean isFormulaCell(int row, int col) {
	HydroBase_WISFormula wisFormula;
	int size = __wisFormulaVector.size();
	int wisCol;
	int wisRow;
	for (int i = 0; i < size; i++) {
		wisFormula = 
			(HydroBase_WISFormula)__wisFormulaVector.get(i);
		wisCol = getColumnNumber(wisFormula.getColumn());
		wisRow = wisFormula.getWis_row() - 1;
		if (wisCol == col && wisRow == row) {
			return true;
		}
	}
	// a match was not found, return false
	return false;
}

/**
Determines if the given cell is an import cell.
@param row row number of the cell
@param col column number of the cell
@return true if the cell is an import cell, false otherwise.
*/
private boolean isImportCell(int row, int col) {
	HydroBase_WISImport wisImport;
	int size = __wisImportVector.size();
	int wisCol;
	int wisRow;
	for (int i = 0; i < size; i++) {
		wisImport = (HydroBase_WISImport)__wisImportVector.get(i);
		wisCol = getColumnNumber(wisImport.getColumn());
		wisRow = wisImport.getWis_row() - 1;
		if (wisCol == col && wisRow == row) {
			return true;
		}
	}
	// a match was not found, return false
	return false;
}

/**
Determines whether the specified row is a known point flow row.
@param row Sheet row number.  If the row is -999 (header), return false.
@return true if the row is a known point flow row, false otherwise.
*/
protected boolean isKnownPoint(int row) {
	String 		knownPoint;	// known point String

	if (row < 0) {
		return false;
	}

	// Else, also check for static known points...

	HydroBase_WISFormat wisformat = 
		(HydroBase_WISFormat)__wisFormatVector.get(row);
	knownPoint = wisformat.getKnown_point().trim();
	if (knownPoint.equals("Y")) {
		return true;
	}
	else {	
		return false;
	}
}

/**
Determines if the HydroBase_WISFormat object is a known point flow row.
@param wisformat the wisformat to check
@return true if the HydroBase_WISFormat instance is a known point flow row, 
otherwise false
*/
protected boolean isKnownPoint(HydroBase_WISFormat wisformat) { 
	int row = wisformat.getWis_row() - 1;
	if (row < 0) {
		return false;
	}

	// Check for static known points...
	String knownPoint = wisformat.getKnown_point().trim();

	if (knownPoint.equals("Y")) {
		return true;
	}
	else {	
		return false;
	}
}

/**
Returns the evaluation status of the wis data row.
@param row the row for which to return the statuc
@return true if the row has been evaluated, false otherwise.
*/
private boolean isEvaluated(int row) {
	HydroBase_WISData wisData = 
		(HydroBase_WISData)__worksheet.getRowData(row);
	return wisData.isEvaluated();
}

/**
Responds to item events.
@param event the ItemEvent that happened.
*/
public void itemStateChanged(ItemEvent event) {
	if (__ignoreItemStateChanged) {
		return;
	}

	Object o = event.getItemSelectable();

	if (o == __gainSimpleJComboBox 
	    && event.getStateChange() == ItemEvent.SELECTED) {
		wisChanged(true);		
		computeWIS();
	}
}

/**
Sets the wis as having been modified.
*/
public void keyPressed(KeyEvent event) {
	wisChanged(true);
}

/**
Does nothing.
*/
public void keyReleased(KeyEvent event) {}

/**
Does nothing.
*/
public void keyTyped(KeyEvent event) {}


/**
Load the selected water information sheet into the __worksheet object and 
populate the __wisFormatVector object so that each element represents a row 
in the __worksheet object.
*/
private void loadClicked() {
	if (__loadWISGUI == null) {
        	__loadWISGUI = new HydroBase_GUI_LoadWIS(__parent, __geoview_ui, __dmi, 
			__mode, !__editable);
	}
	else {	
		__loadWISGUI.setVisible(true);                 
	}
}

/**
Load the selected water information sheet into the __worksheet object and 
populate the __wisFormatVector object so that each element represents a row 
in the __worksheet object.  If diversion coding is used, the data are carried 
forward, if necessary.
*/
private void loadSheet() {
	// initialize variables
	String routine = CLASS + ".loadSheet()";

	// Add an empty String to the first element so that the row numbers
	// in the __worksheet object will match the elements in the 
	// __wisFormatVector

	__wisFormatVector.clear();
	__worksheet.clear();
		
        JGUIUtil.setWaitCursor(this, true);
	String tempString = "Please Wait...Retrieving WIS";
	Message.printStatus(1, routine, tempString);
//	__statusJTextField.setText(tempString);

	// submit query sheet_name query
	List results = null;
	try {
		results = __dmi.readWISSheetNameList(-999, __wisNum, null,null,
			true);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading Sheet Name "
			+ "data.");
		Message.printWarning(2, routine, e);
	}
	// display results from sheet_name query
	String gain;
	if (results != null && results.size() > 0) {
		HydroBase_WISSheetName sheetData = 
			(HydroBase_WISSheetName)results.get(0);
		gain = sheetData.getGain_method();
		if (gain.equals("N")) {
			__gainSimpleJComboBox.select(NONE);
		}
		else if (gain.equals("S")) {
			__gainSimpleJComboBox.select(STREAM_MILE);
		}
		else if (gain.equals("W")) {
			__gainSimpleJComboBox.select(WEIGHTS);
		}		
	}

	try {
		results = __dmi.readWISFormatList(__wisNum, null, null);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading wis_format "
			+ "data.");
		Message.printWarning(2, routine, e);
	}
	int size = 0;
	if (results != null) {
		size = results.size();
		if (Message.isDebugOn) {
			Message.printDebug(1, routine,
				"Number of rows describing WIS format is " 
				+ size);
		}
	}

	// display results from wis_format query
	HydroBase_WISFormat data;
	if (results != null && results.size() > 0) {
		for (int i = 0; i < size; i++) {
			data = (HydroBase_WISFormat)results.get(i);
			// Add the element here in the event that data members
			// needed to be modified.
			__wisFormatVector.add(data);
			if (__newSheet) {
				__worksheet.addRow(new HydroBase_WISData());
			}			
		} 
		// REVISIT (JTS - 2003-10-16)
		// we don't have multiple tab panels right now
//		__tabJPanel.setTabs(__wisFormatVector);
	}

	// instantiate HydroBase_NodeNetwork object
	if (__wisFormatVector.size() > 0) {
		__network = new HydroBase_NodeNetwork();
		__network.setInWIS(true);
		__network.treatDryNodesAsBaseflow(true);
		__network.readWISFormatNetwork(__wisFormatVector, 0);
	}

       	JGUIUtil.setWaitCursor(this, false);
	tempString = "Finished retrieving WIS format.";
	Message.printStatus(1, routine, tempString);
//	__statusJTextField.setText(tempString);

	// load wis formulae
	tempString = "Loading WIS formulae...";
	Message.printStatus(1, routine, tempString);
//	__statusJTextField.setText(tempString);
	loadWISFormula();
	tempString = "Finished loading WIS formulae";
	Message.printStatus(1, routine, tempString);
//	__statusJTextField.setText(tempString);
	// need to load WISData into sheet 
	if (__set_date != null) {
		tempString = "Loading WIS data...";
		Message.printStatus(1, routine, tempString);
//		__statusJTextField.setText(tempString);
		loadWISData();
		for (int i = 0; i < __wisFormatVector.size(); i++) {
			data = (HydroBase_WISFormat)
				__wisFormatVector.get(i);
			__tableModel.setCellContents(data.getRow_label(),
				i, ROW_LABEL_COL, "");
		}
					
		tempString = "Finished loading WIS data";
		Message.printStatus(1, routine, tempString);
//		__statusJTextField.setText(tempString);
	}

	// these formats are known once wis_format and wis_formula have
	// been queried.
	setKnownBaseflowFormats();

	// set the cell formats and data.
	size = __wisFormatVector.size();

	loadWISImports();
	
	if (__newSheet) {
		__adminDateTime = new DateTime(DateTime.DATE_CURRENT);
		String currentDate = __adminDateTime.toString(
			DateTime.FORMAT_YYYY_MM_DD_HH_mm);
		__dateJTextField.setText(currentDate);
		
		if (!__previousDataAvailable) {
			HydroBase_WISData wisData = new HydroBase_WISData();
			for (int row = 0; row < size; row++) {
				setRowValues(wisData, row);
				data = (HydroBase_WISFormat)
					__wisFormatVector.get(row);
				__tableModel.setCellContents(
					data.getRow_label(), row, 
					ROW_LABEL_COL, "");
			}
		}
	}

	initializeDiversionCoding();
	__worksheet.refresh();

	if (__newSheet && !__previousDataAvailable) {
		__ignoreItemStateChanged = false;
		__gainSimpleJComboBox.select(STREAM_MILE);
		__gainSimpleJComboBox.select(NONE);
		__ignoreItemStateChanged = true;
	}
}

/**
Loads wis data saved with a previous format and converts the data to fit 
into the new wis format.
*/
private void loadAndConvertOldFormatWISData() {
	String routine = CLASS + ".loadWISData()";
	DateTime dateDate = null;
	try {	
		dateDate = DateTime.parse(__set_date, 
			DateTime.FORMAT_YYYY_MM_DD_HH_mm);
		dateDate.setPrecision(DateTime.PRECISION_DAY);
	}
	catch (Exception e) {}

	// begin wis_Data query
	String tempString = "Please Wait...Retrieving WIS Data";
	JGUIUtil.setWaitCursor(this, true);
	Message.printStatus(1, routine, tempString);

	List results = null;
	try {
		results = __dmi.readWISDataList(__oldFormatWISNum, dateDate,
			DMIUtil.MISSING_INT);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error formatting date/time.");
		Message.printWarning(2, routine, e);
	}

	int size = 0;
	if (results != null) {
		size = results.size();
	}

	HydroBase_WISData[] rows = new HydroBase_WISData[
		__wisFormatVector.size()];
	for (int i = 0; i < rows.length; i++) {
		rows[i] = null;
	}

	HydroBase_WISData data = null;
	int formatNum = 0;
	int rowNum = 0;
	String identifier = null;
	for (int i = 0; i < size; i++) {
		data = (HydroBase_WISData)results.get(i);
		rowNum = data.getWis_row();

		identifier = findWISIdentifierForRow(rowNum);
		if (identifier == null) {
			// a valid identifier could not be read for this data.
			// This shouldn't happen, but if it does, handle
			// gracefully.
			continue;
		}
		
		formatNum = findWISFormatRowWithIdentifier(identifier);
		if (formatNum < 0) {
			// the wis_format identifier this data used to match
			// up with in the old format no longer exists in the
			// current format.  The data will be discarded. 
			continue;
		}

		rows[formatNum] = data;
	}

	for (int i = 0; i < rows.length; i++) {
		if (rows[i] == null) {
			rows[i] = new HydroBase_WISData();
			rows[i].setWis_row(i + 1);
			rows[i].setWis_num(__wisNum);
		}
	}

	HydroBase_Node node = null;
	HydroBase_WISFormat wisformat = null;
		__worksheet.clear();
	// load the results of the Query into the sheet
	for (int i = 0; i < rows.length; i++) {
		__worksheet.addRow(new HydroBase_WISData());
		setRowValues(rows[i], i);
		// If a dry river node, search for it in the node
		// network and set the flag...
		if (rows[i].getDry_river() == 1) {
			// First need to determine the identifier for
			// the row...
			wisformat = (HydroBase_WISFormat)
				__wisFormatVector.get(i);
			node = __network.findNode(wisformat.getIdentifier());
			if (node != null) {
				Message.printStatus(1, routine,
					"Setting " + wisformat.getIdentifier()
					+ " (common: " + node.getCommonID()
					+ ") to dry river.");
				dryRiver(i);
			}
			else {	
				Message.printStatus(1, routine,
					"Not able to set "
					+ wisformat.getIdentifier()
					+ " to dry river.");
			}
		}
	}

	try {
		results = __dmi.readWISCommentsList(__oldFormatWISNum, 
			dateDate);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading wis_comments "
			+ "data.");
		Message.printWarning(2, routine, e);
	}

	HydroBase_WISComments commentData = 
		(HydroBase_WISComments)results.get(0);
	__commentsJTextField.setText(commentData.getComment().trim());
	if (!__newSheet) {
		__adminDateTime = new DateTime(commentData.getSet_date());
		__dateJTextField.setText(__adminDateTime.toString(
			DateTime.FORMAT_YYYY_MM_DD_HH_mm));
	}

	tempString = "Finished retrieving WIS Data.";
	JGUIUtil.setWaitCursor(this, false);
	Message.printStatus(1, routine, tempString);
}

/**
Loads WISData into the WIS given the __wisNum and __set_date.
*/
private void loadWISData() {
	// if the old format wis num is not missing, then these wis data were
	// originally saved with an older wis format and must be converted to
	// use the new format.
	if (!DMIUtil.isMissing(__oldFormatWISNum)) {
		loadAndConvertOldFormatWISData();
		return;
	}

	String routine = CLASS + ".loadWISData()";
	DateTime dateDate = null;
	try {	
		dateDate = DateTime.parse(__set_date, 
			DateTime.FORMAT_YYYY_MM_DD_HH_mm);
		dateDate.setPrecision(DateTime.PRECISION_DAY);
	}
	catch (Exception e) {}

	// begin wis_Data query
	String tempString = "Please Wait...Retrieving WIS Data";
	JGUIUtil.setWaitCursor(this, true);
	Message.printStatus(1, routine, tempString);
//	__statusJTextField.setText(tempString);

	List results = null;
	try {
		results = __dmi.readWISDataList(__wisNum, dateDate,
			DMIUtil.MISSING_INT);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error formatting date/time.");
		Message.printWarning(2, routine, e);
	}

	int size = 0;
	if (results != null) {
		size = results.size();
	}
	int size_original = size;

	HydroBase_WISData wisData;
	if (results != null) {
		// Check to see if size agrees with the WIS format size (it
		// should).  Remember one row was added to format for header so
		// do all comparisons without the header...
		int wisFormat_size_nh = __wisFormatVector.size();
		HydroBase_WISFormat wis_format = null;
		StringBuffer message = new StringBuffer();
		if (size > wisFormat_size_nh) {
			// Loop through and add to the message the rows that
			// are being deleted.
			int remove_count = 0;
			for (int irow = (wisFormat_size_nh - 1);
				irow < size; irow++) {
				wis_format = (HydroBase_WISFormat)
					__wisFormatVector.get(irow);
				// Just remove the last one repeatedly...
				if (remove_count == 0) {
					message.append("Removing data for row " 
						+ (irow + 1) + "(" 
						+ wis_format.getRow_label() 
						+ ")");
				}
				else {	
					message.append(", row " +(irow + 1) 
						+ "(" 
						+ wis_format.getRow_label() 
						+ ")");
				}
				results.remove(irow);
				++remove_count;
			}
			// Now print the message.
			Message.printWarning(1, routine,
				"WIS format size is " + wisFormat_size_nh
				+ " rows but WIS data size is " + size_original
				+ " rows.\n"
				+ "Format and data are incompatible.\n"
				+ "Removing extra data records for display.\n"
				+ "Saving the WIS will lose those records.\n"
				+ "Data rows being removed are:\n"
				+ StringUtil.lineWrap(message.toString(), 
				80, "\n")
				+ "Contact support to correct the database.\n");
		}
		else if (size < wisFormat_size_nh) {
			// Figure out which rows were lost and print a message.
			// Also add empty data to repair the sheet.
			int add_count = 0;
			int row_number = 0;	// From WIS data.
			for (int irow = 0; irow < wisFormat_size_nh; irow++) {
				wis_format = (HydroBase_WISFormat)
					__wisFormatVector.get(irow);
				if (irow > (size - 1)) {
					// Ran out of data rows...
					if (add_count == 0) {
						message.append("Missing:  row "
							+ (irow + 1) + "("
							+ wis_format
							.getRow_label()
							+ ")");
					}
					else {	
						message.append(", row "
							+ (irow + 1) + "("
							+ wis_format
							.getRow_label()
							+ ")");
					}
					wisData = new HydroBase_WISData();
					wisData.setWis_row(irow + 1);
					wisData.setWis_num(
						wis_format.getWis_num());
					results.add(irow,wisData);
					size = results.size();
					++add_count;
					continue;
				}
				// Have wisData, so check its position(the
				// row number is relative to 1, but the results
				// array is relative to zero)...
				wisData = (HydroBase_WISData)results.get(
					irow);
				row_number = wisData.getWis_row() - 1;
				if (row_number != (irow)) {
					// We may be missing more than one
					// row but rather than loop here, add
					// a row and reset the outside loop to
					// check again...
					if (add_count == 0) {
						message.append(
							"Missing row " 
							+ (irow + 1)
							+ "(" 
							+ wis_format
							.getRow_label()
							+ ")");
					}
					else {	
						message.append(", row " 
							+ (irow)
							+ "(" 
							+ wis_format
							.getRow_label()
							+ ")");
					}
					wisData = new HydroBase_WISData();
					wisData.setWis_row(irow + 1);
					wisData.setWis_num(
						wis_format.getWis_num());
					results.add(irow,wisData);
					++add_count;
					size = results.size();
					--irow;
					continue; // This will increment irow
						// and keep it on the same row
						// again.
				}
			}
			Message.printWarning(1, routine,
				"WIS format size is " + wisFormat_size_nh
				+ " rows but WIS data size is " + size_original
				+ " rows.\nData were lost during last save.\n"
				+ "Setting to missing values for display, as "
				+ "follows:\n"
				+ StringUtil.lineWrap(message.toString(), 
				80, "\n")
				+ "Sheet can be saved - or start from other "
				+ "sheet with complete data.\n"
				+ "Please report to support.");
		}
	}

	// load the results of the Query into the sheet
	if (results != null && results.size() > 0) {
		HydroBase_Node node = null;
		HydroBase_WISFormat wisformat = null;
		__worksheet.clear();
		for (int curRow = 0; curRow < size; curRow++) {
			wisData = (HydroBase_WISData)results.get(curRow);
			__worksheet.addRow(new HydroBase_WISData());
			setRowValues(wisData, curRow);
			// If a dry river node, search for it in the node
			// network and set the flag...
			if (wisData.getDry_river() == 1) {
				// First need to determine the identifier for
				// the row...
				wisformat = (HydroBase_WISFormat)
					__wisFormatVector.get(curRow);
				node = __network.findNode(
					wisformat.getIdentifier());
				if (node != null) {
					Message.printStatus(1, routine,
						"Setting " 
						+ wisformat.getIdentifier()
						+ " (common: " 
						+ node.getCommonID()
						+ ") to dry river.");
					dryRiver(curRow);
				}
				else {	
					Message.printStatus(1, routine,
						"Not able to set "
						+ wisformat.getIdentifier()
						+ " to dry river.");
				}
			}
		}
	}

	try {
		results = __dmi.readWISCommentsList(__wisNum, dateDate);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading wis_comments "
			+ "data.");
		Message.printWarning(2, routine, e);
	}

	HydroBase_WISComments commentData = 
		(HydroBase_WISComments)results.get(0);
	__commentsJTextField.setText(commentData.getComment().trim());
	if (!__newSheet) {
		__adminDateTime = new DateTime(commentData.getSet_date());
		__dateJTextField.setText(__adminDateTime.toString(
			DateTime.FORMAT_YYYY_MM_DD_HH_mm));
	}

	// end wis_Data query
	tempString = "Finished retrieving WIS Data.";
	JGUIUtil.setWaitCursor(this, false);
	Message.printStatus(1, routine, tempString);
//	__statusJTextField.setText(tempString);
}

/**
Load the WIS_formula for the WIS given the __wisNum.
*/
private void loadWISFormula() {
	String routine = CLASS + ".loadWISFormula()";

        JGUIUtil.setWaitCursor(this, true);
        String tempString = "Please Wait...Retrieving WIS Formulae"; 
//        __statusJTextField.setText(tempString);
        Message.printStatus(1, routine, tempString);
        List results = null;

	try {
		results = __dmi.readWISFormulaListForWis_num(__wisNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading wis_formula "
			+ "list.");
		Message.printWarning(2, routine, e);
	}
	
	HydroBase_WISFormula formulaData;
	HydroBase_WISFormat wisFormat;
	int row;
	int col;
	if (results != null && results.size() > 0) {
	        int size = results.size();
        	for (int i = 0; i < size; i++) { 
                	formulaData = 
				(HydroBase_WISFormula)results.get(i);
			__wisFormulaVector.add(formulaData);

			row = formulaData.getWis_row() - 1;
			col = getColumnNumber(formulaData.getColumn());
			__tableModel.setCellAsFormulaStyle(row, col);

			// set the entry cell type on the 
			// __wisFormatVector object
			wisFormat = (HydroBase_WISFormat)
				__wisFormatVector.get(row);
			wisFormat.setIsEntryCell(col, false);
			__wisFormatVector.set(row,wisFormat);
		}
		setFormulaReferences();
	}
	__worksheet.refresh();
        JGUIUtil.setWaitCursor(this, false);
        tempString = "Finished Retrieving WIS Formulae";
//        __statusJTextField.setText(tempString);
        Message.printStatus(1, routine, tempString);                         
}

/**
Load WIS Import into the WIS given the __wisNum and __set_date.
*/
private void loadWISImports() {
	String routine = CLASS + ".loadWISImports()";

	String tempString = "Please Wait...Retrieving WIS Imports.";
	JGUIUtil.setWaitCursor(this, true);
	Message.printStatus(1, routine, tempString);
//	__statusJTextField.setText(tempString);

	List results = null;

// REVISIT (JTS - 2004-05-24)
// imports being done properly??

	try {
		results = __dmi.readWISImportListForWis_num(__wisNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading wis_import "
			+ "data.");
		Message.printWarning(2, routine, e);
	}
 
	double value = DMIUtil.MISSING_DOUBLE;
	Double tempDouble;
 	HydroBase_WISImport wisImport;
	int row;
	int col;
	List importValue = null;
	if (results != null && results.size() > 0) {
		int size = results.size();
		for (int curElement = 0; curElement < size; curElement++) {
			wisImport = (HydroBase_WISImport)results.get(
				curElement);
			__wisImportVector.add(wisImport);
			
			// get the data to be imported into the sheet, if none 
			// exist, leave the values as they are, otherwise load 
			// the new data into the appropriate cell and change 
			// the font color to blue. Element 1 in the importValue
			// Vector contains the number of data values used to
			// compute the imported value. if this amount is zero
			// no data was found.
			importValue = HydroBase_WIS_Util.getWISImportValue(
				__dmi, wisImport); 
			if (StringUtil.atoi(importValue.get(1).toString())
				> 0) {
				tempDouble = (Double)importValue.get(0);
				value = tempDouble.doubleValue();
				row = wisImport.getWis_row() - 1;
				col = getColumnNumber(wisImport.getColumn());

				// Set the value for the import cell. The call
				// takes some dummies as it is overloaded to
				// handle a number of options. The key is
				// passing a boolean true value to denote it is
				// an import cell.

				__tableModel.setCellContents(value, "%10.1f",
					row, col, "", false, false, true, 
					__firstCalc);
			}
		}
	}

	// end wis_import query
	tempString = "Finished retrieving WIS Imports.";
	JGUIUtil.setWaitCursor(this, false);
	Message.printStatus(1, routine, tempString);
//	__statusJTextField.setText(tempString);
}

/**
Responds to the mouse clicked event; doesn't do anything.
@param event MouseEvent object.
*/
public void mouseClicked(MouseEvent event) {}

/**
Responds to the mouse entered event; doesn't do anything.
@param event MouseEvent object.
*/
public void mouseEntered(MouseEvent event) {}

/**
Responds to the mouse exited event; doesn't do anything.
*/
public void mouseExited(MouseEvent event) {}

/**
Responds to the mouse Pressed event, as follows:
if the right-mouse button was clicked over the worksheet, then figure out
the selected cell and show the popup menu.  If the row has data, set it
so that the data can be graphed from the popup menu.
@param event MouseEvent object.
*/
public void mousePressed(MouseEvent event) {
	int mods = event.getModifiers();
	Component c = event.getComponent();
	if ((c == __worksheet) && ((mods & MouseEvent.BUTTON3_MASK) != 0)) {
		c = null;
		// If a diversion row, display the diversion JPopupMenu.
		// Otherwise show the general popup.  Also, if in a column
		// and row that has data, enable the graph button...
		int col = __worksheet.getSelectedColumn();
		int row = __worksheet.getSelectedRow();
		if (((col == PRIORITY_DIV_COL) || (col == RELEASES_COL) 
			|| (col == DELIVERY_DIV_COL)) 
			&&getIdentifier(row).regionMatches(true,0,"wdid:",0,5)){
			// Only show menu if the row has data...
			if (cellHasData(row, col)) {
				__cellGraphJMenuItem2.setEnabled(true);
			}
			else {	
				__cellGraphJMenuItem2.setEnabled(false);
			}
			__diversionJPopupMenu.show(event.getComponent(), 
				event.getX(), event.getY());
		}
		else {	
			if (cellHasData(row, col)) {
				__cellGraphJMenuItem1.setEnabled(true);
			}
			else {	
				__cellGraphJMenuItem1.setEnabled(false);
			}
			__cellJPopupMenu.show(event.getComponent(), 
				event.getX(), event.getY());
		}
	}

	// Check to see whether we need to toggle the dry river button.
	if (Message.isDebugOn) {
		Message.printDebug(1, CLASS + ".mouseClicked",
			"Detected mouse click.");
	}
	if ((__wisFormatVector != null) && !__worksheet.isEmpty()) {
		// Have format data so OK to process...
		// Allow dry river operations on OTHER, MIN_FLOW_REACH,
		// DIVERSION, and RESERVOIR rows...
		int row = __worksheet.getSelectedRow();
		if (Message.isDebugOn) {
			Message.printDebug(1,
				CLASS + ".mouseClicked",
				"Checking row for Dry River button changes.");
		}
		if (canDryRow(row)) {
			// Change the button for the row type...
			if (isDryRiverRow(row)) {
				// Row is already dry so turn the
				// button to un-dry...
				__dryRiverJButton.setText(
					__BUTTON_UNDRY_RIVER);
			}
			else {	
				// Row is not dry so change button to dry...
				__dryRiverJButton.setText(
					__BUTTON_DRY_RIVER);
			}
			if (__editable) {
				__dryRiverJButton.setEnabled(true);
			}
		}
		else {	
			// Cannot dry row...
			__dryRiverJButton.setEnabled(false);
		}
	}
}

/**
Responds to mouse released events; does nothing.
@param event the MouseEvent that happened.
*/
public void mouseReleased(MouseEvent event) {}

/**
Called by the network GUI window when it closes.
*/
protected void networkGUIClosed() {
	__diagramGUI = null;
}

/**
Notifies the network GUI (if it is open) that the network has been changed.
*/
/*
SAM REVISIT 2007-02-08
Evaluate whether this can be removed.
private void notifyDiagramGUI() {
	if (__diagramGUI != null) {
		__diagramGUI.rereadDiagram();
	}
}
*/

/**
Responds when the print button is clicked.
*/
private void printClicked() {
	String routine = CLASS + ".printClicked";
	try {
		/*
		SelectFormatTypeJDialog d = 
			new SelectFormatTypeJDialog(this, 
			HydroBase_GUI_Util.getFormats());
		int format = d.getSelected();
		if (format == HydroBase_GUI_Util.CANCEL) {
			return;
		}
		d.dispose();
		*/
 		// First format the output...
		List outputStrings = formatTextOutput();
 		// Now print...
		PrintJGUI.print(this, outputStrings, 8);
	}
	catch (Exception ex) {
		Message.printWarning(1, routine, "Error while printing.");
		Message.printWarning (2, routine, ex);
	}		
}

/**
Processes the formula for the specified row and column.
@param row the row for which to process the formula
@param col the column for which to process the formula.
*/
private void processFormula(int row, int col) { 
	String routine = CLASS + ".processFormula()";
	// get the formula index for the specified row and column
	int index = getWISFormulaIndex(row, col);
	if (index == DMIUtil.MISSING_INT) {
		return;
	}

	HydroBase_WISFormula wisFormula = 
		(HydroBase_WISFormula)__wisFormulaVector.get(index);

//Message.printStatus(1, "", "\n\nprocessFormula(" + row + ", " + col + ") \n"
//	+ wisFormula);

	double total = 0.0;
	double value = 0.0;
	HydroBase_WISMath wisMath;
	int numTerms = wisFormula.getNumberOfTerms();
	int refRow;
	int refCol;
	String oper;
	String syntax = "";

	for (int curTerm = 0; curTerm < numTerms; curTerm++) {
		wisMath = wisFormula.getTerm(curTerm);
		oper = wisMath.getOperator();
		
		if (wisMath.isConstantTerm()) {
			value = wisMath.getConstant();
		}
		else {
			refRow = wisMath.getRowNumber();
			refCol = wisMath.getColumnNumber();
			value = getWISDataValue(refRow, refCol);	
		}
		syntax += oper + "(" + value + ")";
		if (oper.equals("+")) {
			total = total + value;
		}
		else if (oper.equals("-")) {
			total = total - value;
		}
		else if (oper.equals("/")) {
			total = total / value;
		}
		else if (oper.equals("*")) {
			total = total * value;
		}
		else if (oper.equals("=")) {
			total = value;
		}
	}
	// set the computed total value at the appropriate location
	Message.printStatus(2, routine, "Evaluating:" 
		+ wisFormula.getFormulastring()
		+ "\nAs: " + syntax + " = " + total);

	// set the computed value on the __wisData object and change 
	// the formula evaluation status to true
	setWISDataValueByCell(row, col, total);
	wisFormula.setFormulaEvaluated(true);
	__wisFormulaVector.set(index,wisFormula);
}

/**
Sets the formula references for each term in each formula.  Needs only to be 
called once when the instance of this class occurs as the references are 
constant for the entire session.
*/
private void setFormulaReferences() {
	int numFormulas = __wisFormulaVector.size();

	HydroBase_WISFormula wisFormula;
	HydroBase_WISMath wisMath;
	int numTerms;
	String formula;
	String formulaAsJLabel;
	List terms;
	List termsAsJLabels;
	for (int i = 0; i < numFormulas; i++) {
		wisFormula = 
			(HydroBase_WISFormula)__wisFormulaVector.get(i);

		formula = wisFormula.getFormula();
		terms = HydroBase_WISMath.parseFormula(formula, 
			HydroBase_WISMath.UNIQUE_ID);
		formulaAsJLabel = wisFormula.getFormulastring();
		termsAsJLabels = HydroBase_WISMath.parseFormula(formulaAsJLabel,
			HydroBase_WISMath.LABEL);

		numTerms = terms.size();
		// add terms to the wisFormula object
		for (int curTerm = 0; curTerm < numTerms; curTerm++) {
			wisMath = (HydroBase_WISMath)terms.get(curTerm);
			wisMath.setLabel(((HydroBase_WISMath)
			termsAsJLabels.get(curTerm)).getLabel());
			
			// for the current wisMath object, determine the row 
			// and column that the term refers to. Constant values 
			// have no term reference.
			if (!wisMath.isConstantTerm()) {
				wisMath = getTermReference(wisMath, wisFormula);
			}
			wisFormula.addTerm(wisMath);
			__wisFormulaVector.set(i,wisFormula);
		}
	}
}

/**
Sets the status of the wisData row.
@param row the row for which to set the status
@param status true if the row has been evaluated, false otherwise.
*/
private void setIsEvaluated(int row, boolean status) {
	HydroBase_WISData wisData = 
		(HydroBase_WISData)__worksheet.getRowData(row);
	wisData.setIsEvaluated(status);
	__worksheet.setRowData(wisData, row);
}

/**
Set the __sheetModified variable.
@param state - true if sheet has been modified, false otherwise.
*/
public void setIsModified(boolean state) {
	__sheetModified = state;
}

/**
Sets the formats and flags for the known baseflow rows.
*/
private void setKnownBaseflowFormats() {
	HydroBase_WISFormat wisFormat;
	int numRows = __wisFormatVector.size();
	for (int curRow = 0; curRow < numRows; curRow++) {
		wisFormat = (HydroBase_WISFormat)
			__wisFormatVector.get(curRow);
		
		// unset the appropriate entry cell for known
		// baseflow points.
		if (isKnownPoint(wisFormat)) {
			// determine which cell contains the formula and
			// set the cell attributes accordingly
			if (isFormulaCell(curRow, POINT_FLOW_COL)) {
				__tableModel.setCellAsComputedStyle(curRow,
					NATURAL_FLOW_COL);
				wisFormat.setIsEntryCell(NATURAL_FLOW_COL,
					false);
			}
			else if (isFormulaCell(curRow, NATURAL_FLOW_COL)) {
				__tableModel.setCellAsComputedStyle(curRow,
					POINT_FLOW_COL);
				wisFormat.setIsEntryCell(POINT_FLOW_COL,false);
			}
			else {	
				__tableModel.setCellAsBaseflowStyle(curRow,
					POINT_FLOW_COL);
				wisFormat.setIsEntryCell(POINT_FLOW_COL, true);
			}
			__wisFormatVector.set(curRow,wisFormat);
		}
	}
	__worksheet.refresh();
}

/**
This routine sets the contents of the grid cells for the specified row.
@param row is row position in grid
*/
private void setRowValues(HydroBase_WISData wisData, int row) {
	String routine = CLASS + ".setRowValues";

	if (row >= __worksheet.getRowCount()) {
		if (Message.isDebugOn) {
			Message.printDebug(1, routine,
				"Requesting WIS data " + row 
				+ " but WIS data only has "
				+ __worksheet.getRowCount()
				+ " rows.  Database error?");
		}
		return;
	}

	// initialize variables 
	HydroBase_WISFormat wisFormat = 
		(HydroBase_WISFormat)__wisFormatVector.get(row);
	String rowType = wisFormat.getRow_type();
	if (rowType.equals("")) {
		rowType = STRING;
	}

	// Point flow...
	boolean isUserEnterCell = isEntryCell(row, POINT_FLOW_COL);
	boolean isFormula = isFormulaCell(row, POINT_FLOW_COL);
	boolean isImport = isImportCell(row, POINT_FLOW_COL);
	boolean isDryRiver = isDryRiverRow(row);
	
	if (isDryRiver) {
		// Display "DRY" in the cell and treat as zero flow...
		// This is only allowed on point flow cells that are not
		// formulae.  For data entry, import, etc., the values can
		// be set manually to zero through the GUI.
//		__tableModel.setCellContents(0.0, "DRY", row, 
		__tableModel.setCellContents(0.0, "%10.1f", row, 
			POINT_FLOW_COL, rowType, isUserEnterCell, 
			isFormula, isImport, __firstCalc);
		__worksheet.setCellAlternateText(row, POINT_FLOW_COL, "DRY");
	}
	else {	
		// Handle as usual...
		__tableModel.setCellContents(wisData.getPoint_flow(), 
			"%10.1f", row, POINT_FLOW_COL, rowType, 
			isUserEnterCell, isFormula, isImport, __firstCalc);
		__worksheet.setCellAlternateText(row, POINT_FLOW_COL, null);
	}

	// Natural flow...
	isUserEnterCell = isEntryCell(row, NATURAL_FLOW_COL);
	isFormula = isFormulaCell(row, NATURAL_FLOW_COL);
	isImport = isImportCell(row, NATURAL_FLOW_COL);
	__tableModel.setCellContents(wisData.getNat_flow(), 
		"%10.1f", row, 
		NATURAL_FLOW_COL, rowType, isUserEnterCell, 
		isFormula, isImport, __firstCalc);

	// Delivery flow...
	isUserEnterCell = isEntryCell(row, DELIVERY_FLOW_COL);
	isFormula = isFormulaCell(row, DELIVERY_FLOW_COL);
	isImport = isImportCell(row, DELIVERY_FLOW_COL);
	__tableModel.setCellContents(wisData.getDelivery_flow(), 
			"%10.1f", row, 
			DELIVERY_FLOW_COL, rowType, isUserEnterCell, 
			isFormula, isImport, __firstCalc);

	// Gain loss...
	// provision to check that if gain loss is not computed,
	// don't display values.
	isUserEnterCell = isEntryCell(row, GAIN_LOSS_COL);
	isFormula = isFormulaCell(row, GAIN_LOSS_COL);
	isImport = isImportCell(row, GAIN_LOSS_COL);
	if (!__computeGain) {
		__tableModel.setCellContents(DMIUtil.MISSING_DOUBLE, 
			"%10.1f", row, GAIN_LOSS_COL, rowType,
			isFormula, false, isImport, __firstCalc);		
	}
	else {	
		__tableModel.setCellContents(wisData.getGain(), 
			"%10.1f", row, GAIN_LOSS_COL, rowType, isUserEnterCell, 
			isFormula, isImport, __firstCalc);
	}

	// Tributary natural flow...
	isUserEnterCell = isEntryCell(row, TRIB_NATURAL_COL);
	isFormula = isFormulaCell(row, TRIB_NATURAL_COL);
	isImport = isImportCell(row, TRIB_NATURAL_COL);
	if (rowType.equals(CONFLUENCE) || rowType.equals(OTHER)) {
		__tableModel.setCellContents(wisData.getTrib_natural(), 
			"%10.1f", row, TRIB_NATURAL_COL, rowType, true, 
			isFormula, isImport, __firstCalc);
	}
	else {	
		__tableModel.setCellContents(wisData.getTrib_natural(), 
			"%10.1f", row, TRIB_NATURAL_COL, rowType, false, 
			isFormula, isImport, __firstCalc);
	}

	// Tributary delivery flow...
	isUserEnterCell = isEntryCell(row, TRIB_DELIVERY_COL);
	isFormula = isFormulaCell(row, TRIB_DELIVERY_COL);
	isImport = isImportCell(row, TRIB_DELIVERY_COL);
	if (rowType.equals(CONFLUENCE) || rowType.equals(OTHER)) {
		__tableModel.setCellContents(wisData.getTrib_delivery(), 
			"%10.1f", row, TRIB_DELIVERY_COL, rowType, true, 
			isFormula, isImport, __firstCalc);
	}
	else {	
		__tableModel.setCellContents(wisData.getTrib_delivery(), 
			"%10.1f", row, TRIB_DELIVERY_COL, rowType, false, 
			isFormula, isImport, __firstCalc);
	}

	// Release flow...
	isUserEnterCell = isEntryCell(row, RELEASES_COL);
	isFormula = isFormulaCell(row, RELEASES_COL);
	isImport = isImportCell(row, RELEASES_COL);
	if (rowType.equals(STRING) || rowType.equals(STREAM)) {
		__tableModel.setCellContents(wisData.getRelease(), 
			"%10.1f", row, RELEASES_COL, rowType, false, 
			isFormula, isImport, __firstCalc);
	}
	else if (rowType.equals(RESERVOIR) || rowType.equals(OTHER)
		|| rowType.equals(DIVERSION)) {
		__tableModel.setCellContents(wisData.getRelease(), 
			"%10.1f", row, RELEASES_COL, rowType, true, 
			isFormula, isImport, __firstCalc);
	}
	else {	
		__tableModel.setCellContents(wisData.getRelease(), 
			"%10.1f", row, RELEASES_COL, rowType, false, 
			isFormula, isImport, __firstCalc);
	}

	// Priority diversion...
	isUserEnterCell = isEntryCell(row, PRIORITY_DIV_COL);
	isFormula = isFormulaCell(row, PRIORITY_DIV_COL);
	isImport = isImportCell(row, PRIORITY_DIV_COL);
	if (rowType.equals(STRING) && rowType.equals(STREAM)) {
		__tableModel.setCellContents(wisData.getPriority_divr(), 
			"%10.1f", row, PRIORITY_DIV_COL, rowType, false, 
			isFormula, isImport, __firstCalc);
	}
	else if (rowType.equals(OTHER) || rowType.equals(DIVERSION)
		|| rowType.equals(RESERVOIR)) {
		__tableModel.setCellContents(wisData.getPriority_divr(), 
			"%10.1f", row, PRIORITY_DIV_COL, rowType, true, 
			isFormula, isImport, __firstCalc);
	}
	else {	
		__tableModel.setCellContents(wisData.getPriority_divr(), 
			"%10.1f", row, PRIORITY_DIV_COL, rowType, false, 
			isFormula, isImport, __firstCalc);
	}

	// Delivery diversion...
	isUserEnterCell = isEntryCell(row, DELIVERY_DIV_COL);
	isFormula = isFormulaCell(row, DELIVERY_DIV_COL);
	isImport = isImportCell(row, DELIVERY_DIV_COL);
	if (rowType.equals(STRING) || rowType.equals(STREAM)) {
		__tableModel.setCellContents(wisData.getDelivery_divr(), 
			"%10.1f", row, DELIVERY_DIV_COL, rowType, false, 
			isFormula, isImport, __firstCalc);
	}
	else if (rowType.equals(OTHER) || rowType.equals(DIVERSION)
		|| rowType.equals(RESERVOIR)) {
		__tableModel.setCellContents(wisData.getDelivery_divr(), 
			"%10.1f", row, DELIVERY_DIV_COL, rowType, true, 
			isFormula, isImport, __firstCalc);
	}
	else {	
		__tableModel.setCellContents(wisData.getDelivery_divr(), 
			"%10.1f", row, DELIVERY_DIV_COL, rowType, false, 
			isFormula, isImport, __firstCalc);
	}

	__tableModel.setCellContents(wisData.getComment(), row, COMMENTS_COL,
		rowType);
}

/**
Set the status of all the HydroBase_WISFormulae in the __wisFormulaVector 
object.
@param state Boolean state to set all the formulae to. true signifies
that formulae have been evaluated while false means that they have not.
*/
private void setFormulaStatus(boolean state) {
	int total = __wisFormulaVector.size();
	
	HydroBase_WISFormula wisFormula;
	for (int curFormula = 0; curFormula < total; curFormula++) {
		wisFormula = (HydroBase_WISFormula)
			__wisFormulaVector.get(curFormula);
		wisFormula.setFormulaEvaluated(false);
		__wisFormulaVector.set(curFormula,wisFormula );
	}
}

/**
Update the __statusUserJTextField object.
@param status String to display in the __statusUserJTextField object.
*/
public void setStatus(String status) {
	__statusUserJTextField.setText(status.trim());
}

/**
Set the value at the specified row and column.
worksheet values are set as they represent the actual cell
values during calculation of the WIS. 
Row and column are interpreted as an element location and member 
routine address.
@param row Row number.
@param col Column number.
@param value Value to set at the respective row and column.
*/
private void setWISDataValueByCell(int row, int col, double value) {
	HydroBase_WISData wisData = 
		(HydroBase_WISData)__worksheet.getRowData(row);
	
	// determine the appropriate column
	if (col == POINT_FLOW_COL) {
		wisData.setPoint_flow(value);			
	}
	else if (col == NATURAL_FLOW_COL) {
		wisData.setNat_flow(value);
	}
	else if (col == DELIVERY_FLOW_COL) {
		wisData.setDelivery_flow(value);
	}
	else if (col == GAIN_LOSS_COL) {
		wisData.setGain(value);
	}
	else if (col == TRIB_NATURAL_COL) {
		wisData.setTrib_natural(value);
	}
	else if (col == TRIB_DELIVERY_COL) {
		wisData.setTrib_delivery(value);
	}
	else if (col == PRIORITY_DIV_COL) {
		wisData.setPriority_divr(value);
	}
	else if (col == DELIVERY_DIV_COL) {
		wisData.setDelivery_divr(value);
	}
	else if (col == RELEASES_COL) {
		wisData.setRelease(value);
	}
	__worksheet.setRowData(wisData, row);
}

/**
Shows/hides the JFrame.  If true, the sheet is loaded from the database.
@param state true if showing the frame, false if hiding it.
*/
public void setVisible(boolean state) {
	if (state) {
		// reset member variables
		__wisFormatVector = new Vector();
		__worksheet.clear();
		__wisFormulaVector = new Vector();
		__wisImportVector = new Vector();
		__wisDiversionCodingVector = new Vector();
		__firstCalc = true;

		// set date properties
		__dateProps = 
			new PropList("Calls DateTimeBuilderJDialog properties");
		__dateProps.set("DatePrecision", "Minute" );
		__dateProps.set("DateFormat", "Y2K");

		// load the selected WIS
		loadSheet();
		__statusUserJTextField.setText(__wisStatus);
		setIsModified(false);
		setSize(880, 600);
		JGUIUtil.center(this);

		// re-compute sheet if copied from previous days data
		if (__wisStatus.startsWith("Copied")) {
			computeWIS();
		}

		// set auto calculation flag
		String calcAuto = __dmi.getPreferenceValue("WIS.CalculateAuto");
		if (calcAuto.equals("1")) {
			__autoCalc = true;
			__calculateJButton.setEnabled(false);
		}
		else {	
			__autoCalc = false;
		}
       	}
	super.setVisible(state);
}

/**
Responds to the Save JButton being pressed.
*/
private boolean saveClicked() {
	String routine = CLASS + ".saveClicked()";
	String orig_date = __dateJTextField.getText().trim();

	// get the current system time
	DateTime tsDate = new DateTime();
	tsDate.shiftTimeZone(TimeUtil.getLocalTimeZoneAbbr());

	// check for the presence of single quotes
	if (DMIUtil.checkSingleQuotes(__commentsJTextField.getText().trim())) {
		Message.printWarning(1, routine, "Cannot have single quotes "
			+ " in the comments. ");
		return false;
	}

	// The date shown to the user is local time, which might be "MDT" or
	// "MST", etc.  We need to convert to local time(e.g., "MST")for
	// storage in the database.
	// Assume that we have a DateTime containing the displayed date and TZ
	// Convert to the datum
	try {	
		tsDate = DateTime.parse(orig_date, 
			DateTime.FORMAT_YYYY_MM_DD_HH_mm);
	}
	catch (Exception e) {}

	orig_date = tsDate.toString(
		DateTime.FORMAT_Y2K_SHORT);
//		DateTime.FORMAT_MM_SLASH_DD_SLASH_YYYY_HH_mm);

	// determine if tsDate refers to a past or future date
	DateTime curDateTime = new DateTime(DateTime.DATE_CURRENT);
	DateTime archiveDateTime = new DateTime(tsDate);
	curDateTime.setHour(0);
	curDateTime.setMinute(0);
	curDateTime.setSecond(0);
	archiveDateTime.setHour(0);
	archiveDateTime.setMinute(0);
	archiveDateTime.setSecond(0);

	int response;
	if (archiveDateTime.greaterThan(curDateTime)) {
		response = new ResponseJDialog(this,
			"Save for a future date?",
			"Attempting to save WIS for a future date. Continue?", 
			ResponseJDialog.YES|ResponseJDialog.NO).response();
		if (response == ResponseJDialog.NO) {
			return false;
		}
	}
	else if (archiveDateTime.lessThan(curDateTime)) {
		response = new ResponseJDialog(this,
			"Save for a previous date?",
			"Attempting to save WIS for a "
			+ "previous date. Continue?", 
			ResponseJDialog.YES|ResponseJDialog.NO).response();
		if (response == ResponseJDialog.NO) {
			return false;
		}
	}

	// compute sheet before archiving
	computeWIS();

	// check for negative values
	if (!checkForNegatives()) {
		return false;
	}

	// dates are now in the correct Time Zone for archiving
	try {
		tsDate.setPrecision(DateTime.PRECISION_DAY);
		archiveDateTime.setPrecision(DateTime.PRECISION_MINUTE);
	}
	catch (Exception e) {}
	DateTime archive_DateTime = new DateTime();
	String sheetComments = __commentsJTextField.getText().trim();

	// begin archive process
	JGUIUtil.setWaitCursor(this, true);
	String tempString = "Please Wait...Saving WIS";
//	__statusJTextField.setText(tempString);
	Message.printStatus(1, routine, tempString);

	// delete WIS_Comments and WIS_Data records
	// for wis_num where the set_date is equivalent to 
	// the current set_date. This prohibits having multilple
	// WIS for the same day.
	Message.printStatus(1, routine, "Deleting WIS records..");
	try {
		__dmi.deleteWISForWis_numSet_date(__wisNum, tsDate);
		// pre 2005-02-23 this did two delets to first delete
		// "comments" and "data" data for the given date and 
		// wis num.  Speaking with D Stenzel resulted in the
		// above change.
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error deleting wis data");
		Message.printWarning(2, routine, e);
	}

	try {
		HydroBase_WISComments wc = new HydroBase_WISComments();
		wc.setWis_num(__wisNum);
		wc.setSet_date(tsDate.getDate(TimeZoneDefaultType.LOCAL));
		wc.setArchive_date(archiveDateTime.getDate(TimeZoneDefaultType.LOCAL));
		wc.setComment(sheetComments);
		__dmi.writeWISComments(wc);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error writing wis_comments data.");
		Message.printWarning(2, routine, e);
	}

	int dry_river;
	int numRows = __worksheet.getRowCount();
	String comment;
	String delivery_flow;
	String delivery_divr;
	String gain;
	String nat_flow;
	String point_flow;
	String priority_divr;
	String release;
	String trib_delivery;
	String trib_natural;
	for (int curRow = 0; curRow < numRows; curRow++) {
		// get the cell contents for the curRow
		point_flow = getArchiveCellContents(curRow, POINT_FLOW_COL);
		nat_flow = getArchiveCellContents(curRow, NATURAL_FLOW_COL);
		delivery_flow = getArchiveCellContents(curRow,
			DELIVERY_FLOW_COL);
		gain = getArchiveCellContents(curRow, GAIN_LOSS_COL);
		trib_natural = getArchiveCellContents(curRow,
			TRIB_NATURAL_COL);
		trib_delivery = getArchiveCellContents(curRow,
			TRIB_DELIVERY_COL);
		priority_divr = getArchiveCellContents(curRow,
			PRIORITY_DIV_COL);
		delivery_divr = getArchiveCellContents(curRow,
			DELIVERY_DIV_COL);
		release = getArchiveCellContents(curRow, RELEASES_COL);
		comment=(String)(__worksheet.getValueAt(curRow,COMMENTS_COL-1));
		// The dry river indicator is not stored as a column...
		dry_river= ((HydroBase_WISData)
			__worksheet.getRowData(curRow)).getDry_river();

		try {
			HydroBase_WISData wd = new HydroBase_WISData();
			wd.setWis_num(__wisNum);
			wd.setWis_row(curRow + 1);
			wd.setSet_date(tsDate.getDate(TimeZoneDefaultType.LOCAL));
			wd.setPoint_flow((new Double(point_flow)).doubleValue());
			wd.setNat_flow((new Double(nat_flow)).doubleValue());
			wd.setDelivery_flow((new Double(delivery_flow)).doubleValue());
			wd.setGain((new Double(gain)).doubleValue());
			wd.setTrib_natural((new Double(trib_natural)).doubleValue());
			wd.setTrib_delivery((new Double(trib_delivery)).doubleValue());
			wd.setPriority_divr((new Double(priority_divr)).doubleValue());
			wd.setDelivery_divr((new Double(delivery_divr)).doubleValue());
			wd.setRelease((new Double(release)).doubleValue());
			wd.setComment(comment);
			wd.setDry_river(dry_river);

			__dmi.writeWISData(wd);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error writing "
				+ "wis_data data.");
			Message.printWarning(2, routine, e);
		}
	}

	// Save the diversion coding...

	boolean diversionCodingComplete = true;
	if (__diversionCodingEnabled) {
		diversionCodingComplete = saveDiversionCoding();
	}

	// update sheet status
	__statusUserJTextField.setText("Saved on: " 
		+ archive_DateTime.toString(DateTime.FORMAT_YYYY_MM_DD_HH_mm));
	setIsModified(false);
	
	// finish archive process
	JGUIUtil.setWaitCursor(this, false);
	tempString = "WIS has been saved.";
	__statusJTextField.setText(tempString);
	Message.printStatus(1, routine, tempString);

	if (!diversionCodingComplete) {
		// Indicate that the sheet is still "dirty" - user can cancel.
		setIsModified(true);
		tempString = "Diversion coding was not completed.  "
			+ "Continue Editing.";
		__statusJTextField.setText(tempString);
		Message.printStatus(1, routine, tempString);
	}
	return diversionCodingComplete;
}

/**
Save the diversion coding associated with the WIS.  All records for the
current day are saved, even if they have a "C" observation flag.  Only the
values for the current day are updated.
*/
private boolean saveDiversionCoding() {
	String routine = CLASS + ".saveDiversionCoding";
	// First check to see that data are error-free...
	int size = __wisDiversionCodingVector.size();
	if (size == 0) {
		return true;
	}
	// Now save to the database...
	// The returned data will have values set only for one day.
	// For each detail record check for an existing record in the database
	// for the current month.
	// If an existing record is found, do an update.  Otherwise, do an
	// insert...
	List oldrecords = null;
	HydroBase_WISDailyWC record = null;
	HydroBase_WISDailyWC record2 = null;
	int meas_num = 0;
	int structure_num = 0;

	__adminDateTime.setPrecision(DateTime.PRECISION_MINUTE);

	int wd_prev = -1;	// Previous WD for a diversion coding record
	int id_prev = -1;	// Previous ID for a diversion coding record
	String wis__col_String_prev = "";
	String wis__col_String = "";
				// Diversion coding wis column(e.g., "DD")
	int wd = -1, id = -1;	// Current wd and id for a diversion coding
				// record
	double total;	// Total diversion coding of a group of records(for
			// structure WDID and WIS column).
	double wis_total;// Total for a WIS cell.
	int wis_col = 0;// Numeric column that is being checked
	int div_coding_record_count = 0;
			// Number of records in a diversion coding group(for
			// a WDID and WIS column)
	String wis_total_String, total_String;
			// Strings used to compare the totals.
	// Loop through twice - the first time to check values and the second
	// time to do the update if the user did not break out.
	for (int iloop = 0; iloop < 2; iloop ++) {
	for (int i = 0; i < size; i++) {
		record = (HydroBase_WISDailyWC)
			__wisDiversionCodingVector.get(i);
		// Check the total of the main grid cell and the total of the
		// in-memory diversion records.  If the totals are not the same,
		// then adjust the in-memory records accordingly.  Only need to
		// do this once per structure(but may be in a group of records
		// here).
		// Save the wd and id so we don't reprocess the
		// same records again...
		wd_prev = wd;
		id_prev = id;
		wis__col_String_prev = wis__col_String;
		wd = record.getWD();
		id = record.getID();
		wis__col_String = record.getWis_column();
		if (wis__col_String.equalsIgnoreCase("RR")) {
			wis_col = RELEASES_COL;
		}
		else if (wis__col_String.equalsIgnoreCase("PD")) {
			wis_col = PRIORITY_DIV_COL;
		}
		else if (wis__col_String.equalsIgnoreCase("DD")) {
			wis_col = DELIVERY_DIV_COL;
		}
		else {	
			continue;
		}
		if (!((wd == wd_prev) && (id == id_prev) 
			&& wis__col_String.equalsIgnoreCase(
			wis__col_String_prev))) {
			// Have not yet checked this group of records for total
			// values.  The diversion coding records are assured of
			// being grouped together.  Get the total for the
			// associated records(structure WDID and wis column)...
			Message.printStatus(1, routine,
				"Checking total diversion coding for " 
				+ wd + " " + id + " " + wis__col_String);
			total = 0.0;
			div_coding_record_count = 0;
			for (int j = i; j < size; j++) {
				record2 = (HydroBase_WISDailyWC)
					__wisDiversionCodingVector.get(j);
				if (Message.isDebugOn) {
					Message.printDebug(1, routine,
						"Checking record: "
						+ record2.toString());
				}
				if ((wd == record2.getWD()) 
					&& (id == record2.getID()) 
					&& wis__col_String.equalsIgnoreCase(
					record2.getWis_column())) {
					// Same structure and column so total...
					if (!DMIUtil.isMissing(
						record2.getAmountForDay(
						__adminDateTime.getDay()))) {
						total +=record2.getAmountForDay(
							__adminDateTime
							.getDay());
					}
					++div_coding_record_count;
				}
				else {	
					// Different structure and/or
					// column...
					break;
				}
			}
			// Get the total from the sheet...
			wis_total = getWDIDCellContents(wd, id, wis_col);
			// Compare the totals but do so as strings to make sure
			// there is not a precision problem...
			total_String = StringUtil.formatString(total,"%10.2f");
			wis_total_String = StringUtil.formatString(
				wis_total,"%10.2f");
			if (!total_String.equals(wis_total_String)) {
				// The diversion coding record totals don't
				// match.  If only one diversion coding record
				// was present, leave the coding the same and
				// set the obs flag to 'W' to indicate the value
				// was set in the WIS.
				if (div_coding_record_count == 1) {
					if (Message.isDebugOn) {
						Message.printDebug(1, routine,
							"WIS total " 
							+ wis_total_String
							+ " does not match "
							+ "diversion coding "
							+ "total " 
							+ total_String 
							+ " setting flag to W");
					}
					record.setAmountForDay(
						__adminDateTime.getDay(), 
						wis_total); 
					record.setObservationForDay(
						__adminDateTime.getDay(), "W"); 
				}
				else {	
					// Have more than one record and don't
					// know how to automatically adjust the
					// diversion coding.  Warn user and then
					// Set the diversion coding to a single
					// record with null SFUT, a total
					// matching the WIS value and an obs
					// code of 'W'...
					int pos = findRowForWDID(wd,id);
					String label;
					if (pos < 0) {
						label = "Unknown row, wd = " +
						wd + " id = " + id;
					}
					else {	
						label =
							((HydroBase_WISFormat)
							__wisFormatVector
							.get(pos)
							).getRow_label();
					}
					if (wis_col == RELEASES_COL) {
						label += ", Releases";
					}
					else if (wis_col == PRIORITY_DIV_COL) {
						label += ", Priority Diversion";
					}
					else if (wis_col == DELIVERY_DIV_COL) {
						label += ", Delivery Diversion";
					}
					if (iloop == 0) {
					int response = new ResponseJDialog(this,
						"Warning!",
						"The main WIS has a total of "
						+ wis_total_String + " for "
						+ label
						+ "\nThis total does not agree"
						+ " with the diversion coding"
						+ " total of "
						+ total_String + ".\n\n"
						+ "Select Yes to automatically"
						+ " change diversion coding to "
						+ "one record with the same "
						+ "total,\nempty SFUT and an "
						+ "obs flag of W.\n\n"
						+ "Select No resume editing the"
						+ " WIS.\n\n"
						+ " Continue saving WIS?",
						ResponseJDialog.YES|
						ResponseJDialog.NO
						).response();
					if (response == ResponseJDialog.NO) {
						// Break out of save...
						return false;
					}
		// YES - Change the in-memory records
		// adjust tab spacing back because it was getting ridiculous
		// at the end of the line with only 8 characters to work with.
		for (int j = i; j < size; j++) {
			if ((wd == record.getWD()) 
				&&(id == record.getID())
				&& wis__col_String.equalsIgnoreCase(
				record.getWis_column())) {
				// Same structure so reset first matching
				// record...
				if (j == i) {
					record.setAmountForDay(
						__adminDateTime.getDay(),
						wis_total);
					record.setS("");
					record.setF(DMIUtil.MISSING_INT);
					record.setU("");
					record.setT("");
					record.setObservationForDay(
						__adminDateTime.getDay(), "W");
				}
				else {	
					// Delete the remaining matching
					// records...
					__wisDiversionCodingVector
						.remove(j);
						--size;
				}
			}
			else {	
			// Different structure..
				break;
			}
		}
					} // End iloop == 0
				}
			}
		}
		if (iloop == 0) {
			// Don't update until we make sure everything is OK...
			continue;
		}
		// First delete from existing database records any values that
		// match the wis_num, wd, id, and column, and date.  This
		// guarantees that SFUT combinations that were used before but
		// are not used now are not propagated.  Only do if the record's
		// unique information is not the same as the previous row
		//(otherwise will only end up with the last record in a
		// WD ID, column combination)...
		if (!((wd == wd_prev) && (id == id_prev) 
			&& wis__col_String.equalsIgnoreCase(
			wis__col_String_prev))) {
			try {	
				__dmi.deleteWISDailyWC(record.getWis_num(), 
					record.getWis_column(), record.getWD(),
					record.getID(), 
					__adminDateTime.getYear(),
					__adminDateTime.getMonth());
			}
			catch (Exception e) {
				Message.printWarning(1, routine, "Error "
					+ "deleting wis_daily_wc data.");
				Message.printWarning(2, routine, e);
			}
		}
		// Now get the existing database records that match the
		// in-memory records to wis_num, wd, id, column, date, and
		// SFUT.  If records exist, they will be updated.  Otherwise
		// a new record will be entered...
		try {
			oldrecords = __dmi.readWISDailyWCList(
				record.getWis_num(), record.getWis_column(), 
				record.getWD(), record.getID(),
				__adminDateTime, record.getS(), record.getF(), 
				record.getU(), record.getT(), 1);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading "
				+ "wis_daily_wc data.");
			Message.printWarning(2, routine, e);
		}

		if (oldrecords == null) {
			// Insert a new record that is mostly blank except for
			// the one day that we have data.
			Object o = null;
			try {
				o = __dmi.readStructureViewForWDID(
					record.getWD(), record.getID());
			}
			catch (Exception e) {
				Message.printWarning(1, routine, "Error reading"
					+ " structure data.");
				Message.printWarning(2, routine, e);
			}

			if (o == null) {
				Message.printWarning(1, routine,
					"Unable to determine structure "
					+ "information for SFUT record, WDID = "
					+ HydroBase_WaterDistrict
					.formWDID(record.getWD(),
					record.getID())
					+ ".  Can't save.");
				continue;
			}
			else {
				HydroBase_StructureView struc 
					= (HydroBase_StructureView)o;
				structure_num = struc.getStructure_num();
			}
			
			// Always set to zero since don't have a link to a
			// struct_meas_type value(but may need one in the
			// future for robust data entry)...
			meas_num = 1;	// Set to 0 when key constraint is
					// removed to struct_meas_type

			// Figure out the next wis__dailwc_num value by
			// querying the database and then using it.  Should
			// really use transactions somehow to guarantee this
			// value is safe but for now just count on the timing
			// to keep things in order...
			int wis_dailywc_num = DMIUtil.getMaxRecord(__dmi,
				"wis_daily_wc", "wis_dailywc_num");
			if (DMIUtil.isMissing(wis_dailywc_num)) {
				wis_dailywc_num = 0;
			}
			wis_dailywc_num++;
			HydroBase_WISDailyWC wc = new HydroBase_WISDailyWC();
			wc.setWis_dailywc_num(wis_dailywc_num);
			wc.setMeas_num(meas_num);
			wc.setWis_num(record.getWis_num());
			wc.setWis_column(record.getWis_column());
			wc.setIrr_year(TimeUtil.irrigationYearFromCalendar(
				__adminDateTime.getMonth(),
				__adminDateTime.getYear()));
			wc.setIrr_mon(TimeUtil.irrigationMonthFromCalendar(
				__adminDateTime.getMonth()));
			wc.setCal_year(__adminDateTime.getYear());
			wc.setCal_mon(__adminDateTime.getMonth());
			wc.setAmountForDay(__adminDateTime.getDay(), 
		              record.getAmountForDay(__adminDateTime.getDay()));
			wc.setObservationForDay(__adminDateTime.getDay(),
				record.getObservationForDay(
				__adminDateTime.getDay()));
			wc.setStructure_num(structure_num);
			wc.setUnit("CFS");
			wc.setWD(record.getWD());
			wc.setID(record.getID());
			try {
				wc.setDiv(__dmi.lookupWaterDivisionForDistrict(
					record.getWD()));
			}
			catch (Exception e) {
				Message.printWarning(1, routine, "Error finding"
					+ " water division for district.");
				Message.printWarning(2, routine, e);
			}
			wc.setS(record.getS());
			wc.setF(record.getF());
			wc.setU(record.getU());
			wc.setT(record.getT());
			wc.setModified(__adminDateTime.getDate(TimeZoneDefaultType.LOCAL));
			wc.setUser_num(__dmi.getUserNum());

			try {
				__dmi.writeWISDailyWC(wc);
			}
			catch (Exception e) {
				Message.printWarning(1, routine, "Error writing wis_daily_wc data.");
				Message.printWarning(2, routine, e);
			}
		}
		else {	
			// Update the old record with the new record's data.
			// The only things new are the value and observation
			// code, the date, and the user...
			if (oldrecords.size() != 1) {
				// There should only be one record matching the
				// given criteria...
				Message.printWarning(1,
					CLASS + ".saveData",
					"Multiple existing records for "
					+ HydroBase_WaterDistrict.formWDID(
					record.getWD(),record.getID())
					+ ".  Talk to support.");
				continue;
			}
			else {	
				HydroBase_WISDailyWC wc = (HydroBase_WISDailyWC)
					oldrecords.get(0);
				wc.setAmountForDay(__adminDateTime.getDay(),
					record.getAmountForDay(
					__adminDateTime.getDay()));
				wc.setObservationForDay(
					__adminDateTime.getDay(),
					record.getObservationForDay(
					__adminDateTime.getDay()));
				wc.setUnit("CFS");
				wc.setModified(__adminDateTime.getDate(TimeZoneDefaultType.LOCAL));
				wc.setUser_num(__dmi.getUserNum());
				try {
					__dmi.writeWISDailyWC(wc);
				}
				catch (Exception e) {
					Message.printWarning(1, routine, "Error writing wis_daily_wc data.");
					Message.printWarning(2, routine, e);
				}
			}
		}
	}
	}


	///////////////////////////////////////////////////////////////////
	// The original query code.
	// These queries were pretty complicated, so if errors ever need
	// checked, here is the original code that built the queries ...

	/*
			// Blanks in the following fields are to be NULLs in the
			// database...
			if (record.getS().equals("")) {
				source_String0 = "";
				source_String1 = "";
				source_String2 = " AND S is null";
			}
			else {	
				source_String0 = "S, ";
				source_String1 = "'" + record.getS()+"', ";
				source_String2 = " AND S='" + record.getS()
					+ "', ";
			}
			// originally record.getFrom().equals("")
			if (DMIUtil.isMissing(record.getF())) {
				from_String0 = "";
				from_String1 = "";
				from_String2 = " AND F is null";
			}
			else {	
				from_String0 = "F, ";
				from_String1 = "" + record.getF() + ", ";
				from_String2 = " AND F=" + record.getF() + ", ";
			}
			if (record.getU().equals("")) {
				use_String0 = "";
				use_String1 = "";
				use_String2 = " AND U is null";
			}
			else {	
				use_String0 = "U, ";
				use_String1 = "'" + record.getU() + "', ";
				use_String2 =" AND U='" + record.getU() + "', ";
			}
			if (record.getT().equals("")) {
				type_String0 = "";
				type_String1 = "";
				type_String2 = " AND T is null";
			}
			else {	
				type_String0 = "T, ";
				type_String1 = "'" + record.getT() + "', ";
				type_String2 =" AND T='" + record.getT()+"'";
			}
			/*
			if (record.getObservationCodeForDay(
				__adminDateTime.getDay()).equals("")) {
				obs_String0 = "";
				obs_String1 = "";
				obs_String2 = "AND obs" 
					+ __adminDateTime.getDay() + " is null";
			}
			else {	
				obs_String0 = "obs"+__adminDateTime.getDay()
					+ ", ";
				obs_String1= "'"
					+ record.getObservationCodeForDay(
					__adminDateTime.getDay()) + "', ";
				obs_String2= " obs" + __adminDateTime.getDay()
					+ "='" 
					+ record.getObservationCodeForDay(
					__adminDateTime.getDay()) + "', ";
			}
			// Figure out the next wis__dailwc_num value by
			// querying the database and then using it.  Should
			// really use transactions somehow to guarantee this
			// value is safe but for now just count on the timing
			// to keep things in order...
			long wis_dailywc_num = _parent.getMaxRecord(dmi,
				"wis__daily_wc", "wis_dailywc_num") + 1;
                	dmi_String =
				"INSERT INTO wis__daily_wc("
				+ "wis_dailywc_num, meas_num,"
				+ "wis_num, wis_column, "
				+ "irr_year, irr_mon,"
				+ "cal_year, cal_mon, "
				+ "amt" + __adminDateTime.getDay()
				+ ", " + obs_String0
				+ " structure_num,"
				+ "unit, wd, " + ld + "id" + rd
				+ ", div, " + source_String0
				+ from_String0 + use_String0 + type_String0
				+ "modified, user_num)VALUES("
				+ wis_dailywc_num + ", " + meas_num + ", "
				+ record.getWis_num() + ", "
				+ "'" + record.getWis_column() + "', "
				+ TimeUtil.irrigationYearFromCalendar(
				__adminDateTime.getMonth(),
				__adminDateTime.getYear()) + ", "
				+ TimeUtil.irrigationMonthFromCalendar(
				__adminDateTime.getMonth()) + ", "
				+ __adminDateTime.getYear() + ", "
				+ __adminDateTime.getMonth() + ", "
				+ record.getAmountForDay(
				__adminDateTime.getDay())
				+ ", " + obs_String1
				+ structure_num +", 'CFS', "
				+ record.getWD() + ", "	+ record.getID() + ", "
				+ HBData.getWaterDivision(record.getWD()) + ", "
				+ source_String1 + from_String1
				+ use_String1 + type_String1 + now_sqldate 
				+ ", " + _parent.getUserNumber() + ")";
				+ "";
			Message.printStatus(1, routine,
				"Insert statement: \"" + dmi_String + "\"");
                	dmi.processUpdate(dmi_String);
		}
		else {	
			// Update the old record with the new record's data.
			// The only things new are the value and observation
			// code, the date, and the user...
			if (oldrecords.size()!= 1) {
				// There should only be one record matching the
				// given criteria...
				Message.printWarning(1,
					CLASS + ".saveData",
					"Multiple existing records for "
					+ HydroBase_WaterDistrict.formWDID(
					record.getWD(),record.getID())
					+ ".  Talk to support.");
				continue;
			}
			else {	
				dmi_String =
					"UPDATE wis__daily_wc SET "
					+ "amt" + __adminDateTime.getDay()
					+ " = " + record.getAmountForDay(
					__adminDateTime.getDay()) + ", "
					+ obs_String2 
					+ "unit = 'CFS', modified = "
					+ now_sqldate + ", "
					+ "user_num = "+ _parent.getUserNumber()
					+ " WHERE wis_num = "
					+ record.getWis_num()
					+ " AND wis_column = "
					+ "'" + record.getWis_column() 
					+ "' AND cal_year = "
					+ __adminDateTime.getYear() + " AND "
					+ "cal_mon = "
					+ __adminDateTime.getMonth() + " AND "
					+ "wd = " + record.getWD() + " AND "
					+ ld + "id" + rd + " = "
					+ record.getID() + " "
					+ source_String2 + from_String2
					+ use_String2 + type_String2;
			}
			Message.printStatus(1, routine,
				"Update statement: \"" + dmi_String + "\"");
                	dmi.processUpdate(dmi_String);
	*/	
	
	oldrecords = null;
	record = null;
	return true;
}

/**
Set the diversion coding, given an HydroBase_GUI_WISDiversionCoding instance 
that is closing down.
*/
public void setDiversionCoding(HydroBase_GUI_WISDiversionCoding dcGUI) {
	if (!dcGUI.isModified()) {
		// The data were not changed so don't update the structure's
		// diversion coding...
		return;
	}
	// Else get the diversion coding records from the GUI...
	List records = dcGUI.getDiversionCoding();
	// Need to update the diversion coding records for the structure for the
	// proper wis_column...
	int size = records.size();
	// First delete the records that are currently saved for the cell, if
	// there are any.  Save the position if deleting so that the new ones
	// can be inserted in the same place.  It is OK to delete the entire
	// record(even though it is for a month)because the code that does
	// the insert at the end will properly update the database for the
	// WIS date.
	int insert_pos = -1;
	int size_all = __wisDiversionCodingVector.size();
	HydroBase_WISDailyWC record = null;
	HydroBase_WISDailyWC record_all = null;
	int row = 0;
	int col = 0;
	for (int i = 0; i < size; i++) {
		record = (HydroBase_WISDailyWC)records.get(i);
		// Search for and delete old records.  Only need to do this once
		// because the diversion coding editor only allows editing
		// one WIS column at a time and the WDID is always for the
		// row...
		if (i == 0) {
		for (int j = 0; j < size_all; j++) {
			record_all = (HydroBase_WISDailyWC)
			__wisDiversionCodingVector.get(j);
			// Want to remove all records that match because even
			// if the SFUT is different the whole list of SFUT need
			// to be replaced with the new ones...
			if (	(record.getWD() == record_all.getWD()) 
				&& (record.getID() == record_all.getID()) 
				&& record.getWis_column().equals(
				record_all.getWis_column())) {
				// Save the position so that new records can be
				// inserted at the same point...
				if (insert_pos < 0) {
					insert_pos = j;
				}
				// Remove the record in the main list...
				__wisDiversionCodingVector.remove(j);
				--size_all;
				--j;
			}
		}
		} // End if i == 0
		// Now, add a new record for the specified date...
		if (insert_pos >= 0) {
			// Have a place to insert...
			__wisDiversionCodingVector.add((insert_pos + i),record);
		}
		else {	
			// Just add at the end...
			__wisDiversionCodingVector.add(record);
		}
		// Set the total value in the main sheet to match the
		// total value from the editor.
		row = dcGUI.getWis_row();
		col = 0;
		if (record.getWis_column().equals("RR")) {
			col = RELEASES_COL;
		}
		else if (record.getWis_column().equals("DD")) {
			col = DELIVERY_DIV_COL;
		}
		else if (record.getWis_column().equals("PD")) {
			col = PRIORITY_DIV_COL;
		}
	}

	__tableModel.setCellContents(dcGUI.getTotalAmount(),
		"%10.1f", row, col, getRow_type(row),
		isEntryCell(row, col), isFormulaCell(row, col),
		isImportCell(row, col), false);
	__sheetModified = true;	// Main sheet has been modified
	if (__autoCalc) {
		computeWIS();
	}
	else {	
		// Manually update the display...
		__worksheet.refresh();
	}
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	__ignoreItemStateChanged = true;
	String routine = CLASS + ".setupGUI";

	addWindowListener(this);

	// objects used throughout the GUI layout
	Insets insetsNLBN = new Insets(0,7,7,0);
	Insets insetsNNBR = new Insets(0,0,7,7);
	Insets insetsTLNR = new Insets(7,7,0,7);
	Insets insetsTLBN = new Insets(7,7,7,0);
	Insets insetsTNBR = new Insets(7,0,7,7);
       	GridBagLayout gbl = new GridBagLayout();

        // North JPanel
        JPanel northJPanel = new JPanel();
        northJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        getContentPane().add("North", northJPanel);

	northJPanel.add(new JLabel("Administrative data for:"));
        
        __dateJTextField = new JTextField(15);
	__dateJTextField.setEditable(false);
	northJPanel.add(__dateJTextField);

	__dateJButton = new JButton(__BUTTON_SET_DATE);
	__dateJButton.setToolTipText("Set WIS date.");
	__dateJButton.addActionListener(this);
	northJPanel.add(__dateJButton);

	if (!__editable) {
		northJPanel.add(new JLabel("Sheet is read-only, showing "
			+ "values that were saved."));
	}
	
	if (!__editable) {
		__dateJButton.setEnabled(false);
	}

        // Center JPanel
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(gbl);
       	getContentPane().add("Center", centerJPanel);

	PropList p = new PropList(CLASS + ".JWorksheet");
	p.add("JWorksheet.ShowPopupMenu=false");
	p.add("JWorksheet.AllowCopy=true");	
	p.add("JWorksheet.AllowPaste=true");	
	p.add("JWorksheet.SelectionMode=ExcelSelection");

	int widths[] = null;
	try {
		__tableModel = new HydroBase_TableModel_WIS(new Vector(), this,
			__editable);
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(
			__tableModel);
		__worksheet = new JWorksheet(cr, __tableModel, p);
		__worksheet.removeColumn(0);
		__tableModel.setWorksheet(__worksheet);
		widths = __tableModel.getColumnWidths();
	}
	catch (Exception e) {
		Message.printWarning(2, routine, e);
		__worksheet = new JWorksheet(0, 0, p);
	}
	__worksheet.setPreferredScrollableViewportSize(null);
	__worksheet.setHourglassJFrame(this);
	__worksheet.addMouseListener(this);	

	__tabJPanel = new JTabbedPane();
	// REVISIT (JTS - 2003-10-21)
	// the tab panel needs the name of the worksheet there
	__tabJPanel.add(new JScrollPane(__worksheet));
        JGUIUtil.addComponent(centerJPanel, __tabJPanel, 
		0, 1, 6, 1, 1, 1, insetsTLNR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

	JGUIUtil.addComponent(centerJPanel, new JLabel("Comments:"), 
		0, 2, 1, 1, 0, 0, insetsTLBN, GridBagConstraints.NONE, GridBagConstraints.EAST);
         
        __commentsJTextField = new JTextField(40);        
	JGUIUtil.addComponent(centerJPanel, __commentsJTextField, 
		1, 2, 4, 1, 1, 0, insetsTNBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	if (!__editable) {
		__commentsJTextField.setEditable(false);
	}
	__commentsJTextField.addKeyListener(this);

	__calculateJButton = new JButton(__BUTTON_CALCULATE_WIS);
	__calculateJButton.setToolTipText("Calculate WIS values.");
	__calculateJButton.addActionListener(this);
	/*
	removed because the wis is automatically calculated after every entry 
	now
	JGUIUtil.addComponent(centerJPanel, __calculateJButton, 
		5, 2, 1, 1, 0, 0, insetsTNBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	*/

//	JGUIUtil.addComponent(centerJPanel, new JLabel("Status:"), 
//		0, 3, 1, 1, 0, 0, insetsNLBN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __statusUserJTextField = new JTextField(40);
//	__statusUserJTextField.setEditable(false);
//	JGUIUtil.addComponent(centerJPanel, __statusUserJTextField, 
//		1, 3, 4, 1, 1, 0, insetsNNBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// REVISIT (JTS - 2004-06-09)
	// gain method needs to be saved with wis_comments
	__gainSimpleJComboBox = new SimpleJComboBox();
	__gainSimpleJComboBox.addItemListener(this);
	__gainSimpleJComboBox.add(NONE);
	__gainSimpleJComboBox.add(STREAM_MILE);
	__gainSimpleJComboBox.add(WEIGHTS);
	if (__editable) {
		JGUIUtil.addComponent(centerJPanel, __gainSimpleJComboBox, 
			5, 2, 1, 1, 0, 0, insetsNNBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	}
		
        // Bottom JPanel
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);

        // Bottom: North JPanel
        JPanel bottomNJPanel = new JPanel();
        bottomNJPanel.setLayout(gbl);
        bottomJPanel.add("North", bottomNJPanel);

	JPanel operationsJPanel = new JPanel();
	operationsJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	JGUIUtil.addComponent(bottomNJPanel, operationsJPanel, 
		0, 0, 1, 1, 1, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER);

        __archiveJButton = new JButton(__BUTTON_SAVE);
	__archiveJButton.setToolTipText("Save WIS to database.");
	if (!IOUtil.testing() && !__dmi.hasPermission(__wis.getWD())) {
		__archiveJButton.setEnabled(false);
	}
	__archiveJButton.addActionListener(this);
	operationsJPanel.add(__archiveJButton);
	if (!__editable) {	
		__archiveJButton.setEnabled(false);
	}
        
        __clearJButton = new JButton(__BUTTON_CLEAR);
	__clearJButton.setToolTipText("Clear WIS values.");
	__clearJButton.addActionListener(this);
	operationsJPanel.add(__clearJButton);
	if (!__editable) {
		__clearJButton.setEnabled(false);
	}
        
        __loadJButton = new JButton(__BUTTON_LOAD);
	__loadJButton.setToolTipText("Load another WIS in a new window.");
	__loadJButton.addActionListener(this);
	if (!__editable) {
		operationsJPanel.add(__loadJButton);
	}
        
        __dryRiverJButton = new JButton(__BUTTON_DRY_RIVER);
	__dryRiverJButton.setToolTipText("Dry River sets the point flow to DRY "
		+ "(0.0) for selected row.  Un-DRY river allows the point flow "
		+ "to be computed.");
	__dryRiverJButton.addActionListener(this);
	__dryRiverJButton.setEnabled(false);
	operationsJPanel.add(__dryRiverJButton);
	if (!__editable) {
		__dryRiverJButton.setEnabled(false);
	}

	JPanel frameJPanel = new JPanel();
	frameJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	JGUIUtil.addComponent(bottomNJPanel, frameJPanel, 
		0, 1, 1, 1, 1, 0, insetsNLBN, GridBagConstraints.NONE, GridBagConstraints.CENTER);
                         
        __printJButton = new JButton(__BUTTON_PRINT);
	__printJButton.setToolTipText("Print WIS.");
	__printJButton.addActionListener(this);
        frameJPanel.add(__printJButton);                        
        
        __exportJButton = new JButton(__BUTTON_EXPORT);
	__exportJButton.setToolTipText("Export WIS to file.");
	__exportJButton.addActionListener(this);
        frameJPanel.add(__exportJButton);
        
        __summaryJButton = new JButton(__BUTTON_SUMMARY);
	__summaryJButton.setToolTipText("Display WIS summary.");
	__summaryJButton.addActionListener(this);
        frameJPanel.add(__summaryJButton);

	JButton displayDiagram = new JButton(__BUTTON_DISPLAY_DIAGRAM);
	displayDiagram.setToolTipText("Display WIS diagram.");
	displayDiagram.addActionListener(this);
	frameJPanel.add(displayDiagram);
        
        __closeJButton = new JButton(__BUTTON_CLOSE);
	__closeJButton.setToolTipText("Save changes and close the window.");
	__closeJButton.addActionListener(this);
        frameJPanel.add(__closeJButton);
        
        JButton cancelJButton = new JButton(__BUTTON_CANCEL);
	cancelJButton.setToolTipText("Close the window without saving "
		+ "changes.");
	cancelJButton.addActionListener(this);
	if (__editable) {
	        frameJPanel.add(cancelJButton);
	}

//        __helpJButton = new JButton(__BUTTON_HELP);
//	__helpJButton.addActionListener(this);
//        frameJPanel.add(__helpJButton);
//	__helpJButton.setEnabled(false);

        // Bottom: South JPanel
        JPanel bottomSJPanel = new JPanel();
        bottomSJPanel.setLayout(gbl);
        bottomJPanel.add("South", bottomSJPanel);

	// __cellJPopupMenu for general cell...

	__cellJPopupMenu = new JPopupMenu();
	__cellJPopupMenu.add(
		new SimpleJMenuItem(__POPUP_MENU_CELL_PROPERTIES,
		__POPUP_MENU_CELL_PROPERTIES, this));
	__cellJPopupMenu.addSeparator();
	__cellGraphJMenuItem1 =
		new SimpleJMenuItem(__POPUP_MENU_CELL_GRAPH, 
		__POPUP_MENU_CELL_GRAPH, this);
	__cellJPopupMenu.add(__cellGraphJMenuItem1);
	__cellJPopupMenu.addSeparator();
//	__worksheet.add(__cellJPopupMenu);
	__worksheet.setPopupMenu(__cellJPopupMenu, false);

	// __diversionJPopupMenu for priority diversion(with diversion
	// coding)...

	__diversionJPopupMenu = new JPopupMenu();
	__diversionJPopupMenu.add(
		new SimpleJMenuItem(__POPUP_MENU_CELL_PROPERTIES,
		__POPUP_MENU_CELL_PROPERTIES, this));
	__diversionJPopupMenu.addSeparator();
	SimpleJMenuItem m = new SimpleJMenuItem(__POPUP_MENU_DIVERSION_CODING,
		__POPUP_MENU_DIVERSION_CODING, this);
	// Only enable if user wants to do diversion coding...	
	String prop_val = __dmi.getPreferenceValue("WIS.EnableDiversionCoding");
	if ((prop_val != null) && prop_val.equals("1")) {
		__diversionCodingEnabled = true;
		m.setEnabled(true);
	}
	else {	
		m.setEnabled(false);
	}
	__diversionJPopupMenu.add(m);
	__diversionJPopupMenu.addSeparator();
	__cellGraphJMenuItem2 = new SimpleJMenuItem(__POPUP_MENU_CELL_GRAPH,
		__POPUP_MENU_CELL_GRAPH, this);
	__diversionJPopupMenu.add(__cellGraphJMenuItem2);
	__diversionJPopupMenu.addSeparator();
//	__worksheet.add(__diversionJPopupMenu);
	__worksheet.setPopupMenu(__diversionJPopupMenu, false);

        __statusJTextField = new JTextField();
	__statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomSJPanel, __statusJTextField, 
		0, 1, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // JFrame settings
	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}
	
        setTitle(app + "Water Information Sheet: " + __sheetName);
	pack(); 
        setVisible(true);
	
	if (widths != null) {
		__worksheet.setColumnWidths(widths);
	}
	__ignoreItemStateChanged = false;

	accountForGainMethod();

	__firstCalc = false;
}

/**
Show cell properties.
@param row Row number.
@param col Column number.
*/
public void showCellProperties(int row, int col) {
//	__statusJTextField.setText("Displaying cell properties.");

	HydroBase_WISFormat wisFormat = 
		(HydroBase_WISFormat)__wisFormatVector.get(row);
	String rowType = wisFormat.getRow_type();

	// locate the formula and import for the current cell
	HydroBase_WISFormula wisFormula = getCellFormula(row, col);
	HydroBase_WISImport wisImport = getCellImport(row, col);
	String contents = "";
	Class c = __worksheet.getColumnClass(col -1);
	if (c == Double.class) {
		Double D = (Double)(__worksheet.getValueAt(row, col - 1));
		if (!DMIUtil.isMissing(D)) {
			contents = D.toString();
		}
	}
	else {
		contents = (__worksheet.getValueAt(row, col - 1)).toString();
	}
	String mileString = "";
	String weightString = "";
	String NA = "Does not apply.";
	HydroBase_Node downstream_node = null;
	// get the gain Loss information
	if (rowType.equals(HydroBase_GUI_WIS.STRING) 
		|| rowType.equals(HydroBase_GUI_WIS.STREAM)) {
		mileString = NA;
		weightString = NA;
	}
	else {	
		HydroBase_Node node = __network.getMostUpstreamNode();
		while (node != null) {
			node = HydroBase_NodeNetwork.getDownstreamNode(node,
				HydroBase_NodeNetwork.POSITION_COMPUTATIONAL);
			// find the node
			if (node.getWISFormat() == wisFormat) {
				// first get the gains using stream mile.
				double[] mile_values =
				HydroBase_WIS_Util.computeGainLoss(__network, 
					node);
				mileString = HydroBase_WIS_Util.formatGainLoss(
					mile_values);

				// now get the gains using weights
				double[] weight_values = HydroBase_WIS_Util
					.computeWeightedGainLoss(
					__network, node);
				weightString = 
					HydroBase_WIS_Util.formatGainLoss(
					weight_values);

				// make sure we have a valid computation.
				if (DMIUtil.isMissing(mile_values[0])) {
					mileString = NA;
				}

				// make sure we have a valid computation.
				if (DMIUtil.isMissing(weight_values[0])){
					weightString = NA;
				}

				// Get downstream node...
				downstream_node = node.getDownstreamNode();
				break;
			}
		}
	}
	HydroBase_GUI_WISCellAttributes gui = 
		new HydroBase_GUI_WISCellAttributes(__parent, __geoview_ui,
		wisFormat.getRow_label(), 
		HydroBase_GUI_WISBuilder.getColumnType(col), contents, 
		wisFormat, wisFormula, wisImport, __dmi, downstream_node);

	gui.setGainsField(mileString);
	gui.setWeightedGainsField(weightString);
	
	downstream_node = null;
}

/**
Display a summary report.  This is the same as the export but includes a
diversion summary on the bottom.
*/
private void summaryClicked() {
	String routine = CLASS + ".summaryClicked";

	List outputStrings = formatTextOutput();
	// Add the diversion summary...
	if (__diversionCodingEnabled) {
		outputStrings.add("");
		outputStrings.add("Diversion Coding Summary:");
		outputStrings.add("");
		int size = __worksheet.getRowCount();
		int dsize = __wisDiversionCodingVector.size();
		// Loop through the WIS rows and print related diversion coding
		// records...
		String identifier = null;
		int[] wdidParts = null;
		HydroBase_WISDailyWC record = null;
		int wd = 0;
		int id = 0;
		int j = 0;
		int foundCount = 0;
		for (int i = 0; i < size; i++) {
			identifier = getIdentifier(i);
			if (!identifier.regionMatches(true, 0, "wdid:", 0, 5)) {
				continue;
			}
			outputStrings.add(getRow_label(i));
			try {
				wdidParts=HydroBase_WaterDistrict.parseWDID(
					identifier);
			}
			catch (Exception e) {
				Message.printWarning(1, routine, "Error parsing"
					+ " WDID.");
				Message.printWarning(2, routine, e);
			}
			identifier = null;
			if (wdidParts == null) {
				continue;
			}
			wd = wdidParts[0];
			id = wdidParts[1];
			wdidParts = null;
			// Just do a brute-force search...
			foundCount = 0;
			for (j = 0; j < dsize; j++) {
				record = (HydroBase_WISDailyWC)
					__wisDiversionCodingVector.get(j);
				if ((wd == record.getWD()) 
					&& (id == record.getID())) {
					++foundCount;
					outputStrings.add("      "
						+ record.getWis_column() 
						+ "   S:"
						+ StringUtil.formatString(
						record.getS(), "%-1.1s") 
						+ "  F:"
						+ StringUtil.formatString(
						record.getF(), "%-7.7s") 
						+ "  U:"
						+ StringUtil.formatString(
						record.getU(), "%1.1s") 
						+ "  T:"
						+ StringUtil.formatString(
						record.getT(), "%1.1s") 
						+ "  "
						+ StringUtil.formatString(
						record.getAmountForDay(
						__adminDateTime.getDay()), 
						"%10.2f") + " CFS "
						+ StringUtil.formatString(
						record.getObservationForDay(
						__adminDateTime.getDay()),
						"%1.1s"));
				}
			}
			if (foundCount == 0) {
				outputStrings.add(
					"      No Diversion Coding");
			}
		}
		}
	else {	
		outputStrings.add("");
		outputStrings.add("Diversion coding is not enabled.");
	}
	PropList reportProp = new PropList("ReportJFrame.props");
	reportProp.set("TotalWidth", "750");
	reportProp.set("TotalHeight", "550");
	reportProp.set("DisplayFont", "Courier");
	reportProp.set("DisplaySize", "11");
	reportProp.set("PrintFont", "Courier");
	reportProp.set("PrintSize", "7");
	if (__diversionCodingEnabled) {
		reportProp.set("Title",
			"WIS Summary (Diversion Coding at Bottom) - "
			+ __adminDateTime.toString(DateTime.FORMAT_YYYY_MM_DD));
	}
	else {	
		reportProp.set("Title", "WIS Summary - "
			+ __adminDateTime.toString(DateTime.FORMAT_YYYY_MM_DD));
	}
	new ReportJFrame(outputStrings, reportProp);
	reportProp = null;
	outputStrings = null;
}

/**
Updates a point flow value in the diagram when the point flow at a certain row
changes.
@param row the row for which point flow values changed.
@param pf the new point flow value.
*/
protected void updateDiagramPointFlow(int row, double pf) {
	if (__diagramGUI == null) {
		return;
	}

	HydroBase_WISFormat format = 
		(HydroBase_WISFormat)__wisFormatVector.get(row);
	String id = format.getIdentifier();
	__diagramGUI.updatePointFlow(id, pf);
}

/**
Called by the table model whenever a value is edited -- recompute the wis if
automatic computation is activated.
*/
protected void valueSet(int row, int col) {
	if (__initialized) {
		if (col == 7 || col == 6) {
			// diversion coding record possibly changed.  Check
			// to see if there is a diversion coding record for
			// this cell

			String identifier = getIdentifier(row);
			if (!identifier.regionMatches(true, 0, "wdid:", 0, 5)) {
				if (__autoCalc) {
					computeWIS();
				}
				return;
			}
			int[] wdidParts = null;
			try {
				wdidParts = HydroBase_WaterDistrict.parseWDID(
					identifier);
			}
			catch (Exception e) {
				String routine = "HydroBase_GUI_WIS."
					+ "valueSet";
				Message.printWarning(1, routine, 
					"Error parsing WDID.");
				Message.printWarning(2, routine, e);
				return;
			}
			
			if (wdidParts == null) {
				if (__autoCalc) {
					computeWIS();
				}
				return;
			}
			int wd = wdidParts[0];
			int id = wdidParts[1];

			String wis_column = "";
			if (col == RELEASES_COL) {
				wis_column = "RR";
			}
			else if (col == PRIORITY_DIV_COL) {
				wis_column = "PD";
			}
			else {	
				wis_column = "DD";
			}
			
			HydroBase_WISDailyWC record = null;
			int count = 0;
			int size = __wisDiversionCodingVector.size();
			for (int i = 0; i < size; i++) {
				record = (HydroBase_WISDailyWC)
					__wisDiversionCodingVector.get(i);
				if ((wd == record.getWD()) 
					&& (id == record.getID()) 
					&& wis_column.equals(
					record.getWis_column())) {
					count++;
				}
			}

			if (count != 1) {
				if (__autoCalc) {
					computeWIS();
				}
				return;
			}

			Double D = (Double)__worksheet.getValueAt(row, col-1);
			double d = D.doubleValue();

			for (int i = 0; i < size; i++) {
				record = (HydroBase_WISDailyWC)
					__wisDiversionCodingVector.get(i);
				if ((wd == record.getWD()) 
					&& (id == record.getID()) 
					&& wis_column.equals(
					record.getWis_column())) {
					record.setObservationForDay(
						__adminDateTime.getDay(),
						"*");
					record.setAmountForDay(
						__adminDateTime.getDay(), d);
					if (__autoCalc) {
						computeWIS();
					}
					return;
				}
			}			
		}
	}
	if (__autoCalc) {
		// reset __autoCalc within this method to avoid infinite 
		// loops ...
		computeWIS();
	}
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
Called when the window is closing.
@param event the WindowEvent that happened.
*/
public void windowClosing(WindowEvent event) {
	closeClicked();
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

/**
Called when the wis is changed (or set clean), this sets status field text 
and stores whether the wis is changed or not.
@param changed whether the wis was changed or not.  Calling with false will
result in the WIS considered fresh and new.
*/
protected void wisChanged(boolean changed) {
	if (changed) {
		__statusJTextField.setText("WIS has been modified.");
	}
	else {
		__statusJTextField.setText("Ready.");
	}
	__sheetModified = changed;
}

}
