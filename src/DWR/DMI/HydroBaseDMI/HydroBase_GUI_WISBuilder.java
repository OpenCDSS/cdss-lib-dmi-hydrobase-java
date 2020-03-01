// HydroBase_GUI_WISBuilder - Water Information Sheet Builder GUI

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
// HydroBase_GUI_WISBuilder - Water Information Sheet Builder GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file. 
//-----------------------------------------------------------------------------
// History:
//
// 25 Aug 1997 DLG, RTi			RTi Created initial version.
// 18 Sep 1997 DLG, RTi			Cleaned up the code and added 
//					archiving routineality
// 20 Feb 1998 DLG, RTi			Added the option to compute gain/loss
//					via weight coefficent or stream mile.
// 27 Feb 1998 DLG, RTi			Added default gain selection.
// 12 Mar 1998 DLG, RTi			Added unique String determination for
//					wis rows.
// 18 Mar 1998 DLG, RTi			Defaulted water district to the one
//					that the wis is most closely associated
//					with. Fixed bug when deleting a row.
// 27 May 1998 CGB, RTi			Added display of gains information.
//					Added radio button for retrieving
//					confluence list by WD or by stream.
//					Fixed same type radio buttons
//					for structures.
// 04 Apr 1999	Steven A. Malers, RTi	Add HBDMI to queries.
// 07 Apr 1999	SAM, RTi		Update for database changes.
//					Change HBStructure and
//					HBStructureQuery to
//					HBStructureLocation and
//					HBStructureLocationQuery so that
//					stream mile are included, etc(HydroBase
//					change).
// 21 May 1999	SAM, RTi		Remove reference to
//					DMIUtil.TIME_ZONE_DATUM.
// 07 Jun 2001	SAM, RTi		Change so network output has a
//					descriptive header and use the normal
//					report GUI with search enabled.
//					Change GUI to JGUIUtil.  Add finalize().
//					Change some data to non-static to save
//					memory.  Add ability to do JPopupMenu
//					on right-click to show properties for a
//					row.  Add an HydroBase_NodeNetwork 
//					object to allow gain properties to be 
//					shown and allow for future displays.
// 15 Jul 2001	SAM, RTi		Add timers to see why the sheet takes
//					so long to load.
// 2002-05-13	SAM, RTi		Follow up on the long load times. Also
//					make general improvements to improve
//					loading, editing, and verification to
//					close out the RGDSS project:
//					* change internal data to not be static
//					* change "Archive" button to "Save".
//					* add the __showExpandedDetails switch
//					  to allow more information to be shown
//					* add more information to row labels to
//					  help understand WIS implementation
//					* add stream number to name choice when
//					  expanded details are shown
// 2002-06-09	SAM, RTi		Update to optimize query and storage of
//					row objects so that the performance is
//					increased.  Remove references to
//					WDWater where not used - stream data is
//					from the Stream table and 
//					HydroBase_Stream.  Apparently wd_water 
//					was never used in the builder.  Add a 
//					Current Stream: field to make it 
//					easier to know the position in the WIS.
//					The setUniqueID()method had the
//					identifier parts reversed for
//					confluences.
// 2002-06-18	SAM, RTi		Fix so when a row is added or deleted
//					the label refreshes to reflect expanded
//					details.
//-----------------------------------------------------------------------------
// 2003-11-17 to 2003-12-02
//		J. Thomas Sapienza, RTi	Getting initial Swing version working
//					like the old AWT version.
// 2003-12-15	JTS, RTi		Added the button for opening the
//					network builder.
// 2005-02-15	JTS, RTi		Converted most queries to use stored
//					procedures.
// 2005-03-09	JTS, RTi		HydroBase_SheetName 	
//					  -> HydroBase_WISSheetName.
// 2005-03-10	JTS, RTi		Converted some delete code to use 
//					new methods added for SP deletes.
// 2005-04-12	JTS, RTi		MutableJList changed to SimpleJList.
// 2005-04-25	JTS, RTi		* Corrected errors resulting from the
//					  transition to stored procedures.
//					* Uses new version of formatDateTime()
//					  suitable for stored procedures.
// 2005-04-28	JTS, RTi		Added all data members to finalize().
// 2005-05-09	JTS, RTi		Only HydroBase_StationView objects are
//					returned from station queries now.
// 2005-05-09	JTS, RTi		All structure queries now return
//					structure view objects.
// 2005-05-23	JTS, RTi		Uses HydroBase_WaterDistrict now for
// 					parseWD().
// 2005-05-25	JTS, RTi		Converted queries that pass in a 
//					String date to pass in DateTimes 
//					instead.
// 2005-06-22	JTS, RTi		* Column widths now come from the 
//					  table model, not the cell renderer.
//					* The table-specific cell renderers 
//					  were removed and replaced with a 
//					  single generic one.
// 2005-08-03	JTS, RTi		Keyboard navigation around the worksheet
//					is now handled properly.
// 2007-02-08	SAM, RTi		Remove dependence on CWRAT.
//					Pass JFrame to constructor.
//					Add GeoViewUI for map interaction.
//					Clean up code based on Eclipse feedback.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import RTi.DMI.DMIUtil;
import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.JWorksheet_CellAttributes;
import RTi.Util.GUI.SimpleJList;
import RTi.Util.GUI.ReportJFrame;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.SimpleJComboBox;
import RTi.Util.GUI.SimpleJMenuItem;
import RTi.Util.IO.PropList;
import RTi.Util.Message.Message;
import RTi.Util.String.StringUtil;
import RTi.Util.Time.DateTime;
import RTi.Util.Time.StopWatch;
import RTi.Util.Time.TimeZoneDefaultType;

// REVISIT (JTS - 2003-12-17)
/*
 need to do something about associated stream numbers.  Imagine this:
 1 - stream - A
 2 - stream - B
 3 - station X

in the above, station X is associated (if built in a liner order from row 1 to
row 3) with stream B.  If stream B is deleted, the station should be associated
now with stream A, but it isn't.  that can cause problems for the network 
builder.  So do something about it.
*/

/**
The WISBuilderGUI is the graphical user interface for building WIS.  The GUI is
laid out as follows:
<pre>
-------------------------------------------------------------------------------
| SheetName                         Date                  Gain Method         |
|-----------------------------------------------------------------------------|
| WIS grid                                                                    |
|                                                                             |
|                                                                             |
|-----------------------------------------------------------------------------|
| Edit buttons                                                                |
|-----------------------------------------------------------------------------|
| Card layout panel for row types  __rowTypeJPanel with _rowTpyeCard          |
|-----------------------------------------------------------------------------|
| Window buttons                                                              |
-------------------------------------------------------------------------------
</pre>
After being displayed, selecting a row calls displayRow(), which displays
information about the row in the bottom part of the interface.  The information
is displayed from objects that are kept in memory in the following objects:
<ol>
<li>	__wisFormatVector - contains WIS row format information</li>
<li>	__confluenceVector - contains confluence stream for current water
	district(and stream tributary) </li>
<li>	__diversionVector - contains structures for current water
	district/stream</li>
<li>	__mfrWeightVector - contains structures for current water 
	district/stream</li>
<li>	__reservoirVector - contains structures for current water
	district/stream</li>
<li>	__stationVector - contains stations for current water district</li>
<li>	__streamVector - contains streams for current water district</li>
</ol>
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_WISBuilder 
extends JFrame 
implements ActionListener, ItemListener, KeyListener, MouseListener, 
WindowListener {

/**
The name of the class.
*/
public final static String CLASS = "HydroBase_GUI_WISBuilder";

/**
The default weight value that will be displayed in textfields.
*/
private final double __DEFAULT_WEIGHT = 1.0;

/**
Used to refer to formula- and import-specific buttons when setting button 
states.
*/
private final int
	__FORMULA = 	0,
	__IMPORT = 	1;

/**
Used to refer to the kinds of structures to display.
*/
private final int
	__ALL = 	0,
	__STREAM = 	1;

/**
The maximum possible formula length.
*/
private final int __MAX_FORMULA_LENGTH = 255;

/**
Constant strings used throughout the GUI.
*/
private final String
	__CONF = 		"conf:",
	__FORMULA_LABEL = 	"Formula Editor: ",
	__HIDE = 		"hide",
	__IN_STREAM = 		"On Stream",
	__IN_WD = 		"In Water District",
	__OTHR = 		"othr:",
	__SELECT_DIVERSION = 	"Select Diversion",
	__SELECT_MFR = 		"Select Min. Flow Reach",
	__SELECT_RESERVOIR = 	"Select Reservoir",
	__SELECT_STATION = 	"Select Station",
	__SELECT_STREAM = 	"Select Stream",
	__SHOW = 		"show",
	__STAT = 		"stat:",
	__STRM = 		"strm:",
	__WDID = 		"wdid:",
	__WEIGHT = 		"Weight:";

/**
Constant labels for buttons and popup menus.
*/
private final String
	__BUTTON_ADD = "+",
	__BUTTON_ADD_FORMULA = "Add Formula",
	__BUTTON_ADD_IMPORT = "Add Import",
	__BUTTON_ADD_ROW = "Add Row",
	__BUTTON_BUILD_DIAGRAM = "Edit WIS Diagram",
	__BUTTON_CLOSE = "Close",
	__BUTTON_DELETE = "Delete",
	__BUTTON_DELETE_FORMULA = "Delete Formula",
	__BUTTON_DELETE_IMPORT = "Delete Import",
	__BUTTON_DELETE_ROW = "Delete Row",
	__BUTTON_DISPLAY_GAINS = "Display Gains",
	__BUTTON_DIVIDE = "/",
	__BUTTON_EDIT_FORMULA = "Edit Formula",
	__BUTTON_EDIT_IMPORT = "Edit Import",
	__BUTTON_HELP = "Help",
	__BUTTON_MULTIPLY = "*",
	__BUTTON_SAVE = "Save",
	__BUTTON_SHOW_NETWORK = "Show as Text",
	__BUTTON_SUBTRACT = "-",
	__BUTTON_SUM = "Sum",
	__POPUP_PROPERTIES = "Properties ...";

/**
Whether anything has changed and should be saved.
*/
private boolean __dirty = false;

/**
Whether to ignore any state changes that occur while this is true.
*/
private boolean __ignoreStateChange = false;

/**
Whether all the GUI objects have been initialized or not.
*/
private boolean __initialized = false;

/**
Whether the current state of the GUI is that a formula is being constructed.
*/
private boolean __isBuildingFormula;

/**
Whether the constant text field is currently shown.
*/
private boolean __isConstantShown;

/**
Whether the sheet is a new one or an old one being edited.
*/
private boolean __isNewSheet;

/**
Whether the code is currently executing the displayRow() method.
*/
private boolean __settingUpRow = false;

/**
Whether a structure has been selected.
*/
private boolean __structureWasSelected;

/**
Whether a water district is being selected.
*/
private boolean __waterDistrictSelection = false;

/**
Card layouts for the gui panels.
*/
private CardLayout
	__rowTypeCard,
	__constantCard;

/**
The parent JFrame running the entire application, for window positioning.
*/
private JFrame __parent;

/**
GeoViewUI for map interactions.
*/
private GeoViewUI __geoview_ui;

/**
Hashtables to store data for populating the combo boxes on the form.
*/
private Hashtable<String,List<HydroBase_Stream>> __confluencesHashtable = new Hashtable<String,List<HydroBase_Stream>>();
private Hashtable<String,List<HydroBase_StructureView>> __diversionHashtable = new Hashtable<String,List<HydroBase_StructureView>>();
private Hashtable<String,List<HydroBase_StructureView>> __mfrWeightHashtable = new Hashtable<String,List<HydroBase_StructureView>>();
private Hashtable<String,List<HydroBase_StructureView>> __reservoirHashtable = new Hashtable<String,List<HydroBase_StructureView>>();
private Hashtable<String,List<HydroBase_StationView>> __stationsHashtable = new Hashtable<String,List<HydroBase_StationView>>();
private Hashtable<String,List<HydroBase_Stream>> __streamsHashtable = new Hashtable<String,List<HydroBase_Stream>>();

/**
The DMI that will be used to read and write to the database.
*/
private HydroBaseDMI __dmi;

/**
The network of nodes in the GUI.
*/
private HydroBase_NodeNetwork __network = null;

/**
The sheet for which data is displayed.
*/
private HydroBase_WISSheetName __wis;

/**
The stream with which this GUI was built (if it was built with a stream).
*/
private HydroBase_Stream __stream = null;

/**
The worksheet's table model.
*/
private HydroBase_TableModel_WISBuilder __tableModel;

/**
The wizard for doing data imports.
*/
private HydroBase_GUI_WISImportWizard __importWizardGUI;

/**
The GUI for displaying the wis network.
*/
private HydroBase_GUI_WISDiagram __diagramGUI = null;

/**
A two-element array that keeps track of the row and column of the cell 
currently in when an arrow key was pressed to navigate around.  Is null if
arrow keys were not used in navigation.
*/
private int[] __keyCell = null;

/**
The widths of the columns in the worksheet.
*/
private int[] __widths = null;

/**
The currently-selected cell row.
*/
private int __curCellCol = 1;

/**
The currently-selected cell column.
*/
private int __curCellRow = 0;

/**
The wis_num for the current sheet.
*/
private int __wisNum;

/**
GUI buttons.
*/
private JButton	
	__addFormulaJButton,
	__addImportJButton,
	__addRowJButton,
	__saveJButton,
	__buildDiagramJButton,
	__closeJButton,
	__deleteFormulaJButton,
	__deleteImportJButton,
	__deleteJButton,
	__deleteRowJButton,
	__displayGainsJButton,
	__divideJButton,
	__minusJButton,
	__multiplyJButton,
	__plusJButton,
	__showNetworkJButton;

/**
GUI check boxes.
*/
private JCheckBox 
	__divKnownPointJCheckBox,
	__mfrWeightKnownPointJCheckBox,
	__otherKnownPointJCheckBox,
	__resKnownPointJCheckBox,
	__staKnownPointJCheckBox;

/**
Label for the formula list.
*/
private JLabel __formulaJLabel;

/**
GUI panels.
*/
private JPanel 
	__confluenceJPanel,
	__constantJPanel,
	__diversionJPanel,
	__formulaJPanel,
	__minFlowReachJPanel,
	__otherJPanel,
	__reservoirJPanel,
	__rowTypeJPanel,
	__stationJPanel,
	__streamJPanel,
	__stringJPanel;

/**
The popup menu for displaying cell properties.
*/
private JPopupMenu __cellJPopupMenu = null;

/**
GUI radio buttons.
*/
private JRadioButton
	__conStreamJRadioButton,
	__conWDJRadioButton,
	__mfrWeightStreamJRadioButton,
	__mfrWeightWDJRadioButton,
	__resStreamJRadioButton,
	__resWDJRadioButton,
	__structStreamJRadioButton,
	__structWDJRadioButton;

/**
GUI text fields.
*/
private JTextField
	__cflWeightJTextField,
	__confluenceRowLabelJTextField,
	__confluenceStreamMileJTextField,
	__constantJTextField,
	__currentStreamJTextField,
	__dateJTextField,
	__diversionRowLabelJTextField,
	__divStreamMileJTextField,
	__divWeightJTextField,
	__mfrWeightRowLabelJTextField,
	__mfrWeightStreamMileJTextField,
	__mfrWeightJTextField,
	__otherRowLabelJTextField,
	__otherStreamMileJTextField,
	__otherWeightJTextField,
	__reservoirRowLabelJTextField,
	__resStreamMileJTextField,
	__resWeightJTextField,
	__sheetNameJTextField,
	__staStreamMileJTextField,
	__stationRowLabelJTextField,
	__statusJTextField,
	__staWeightJTextField,
	__streamRowLabelJTextField,
	__stringRowLabelJTextField,
	__uniqueJTextField;

/**
The worksheet that is displayed while the wis is built.
*/
private JWorksheet __worksheet;

/**
The list in which formulas are built and displayed.
*/
private SimpleJList<String> __formulaList;

/**
GUI combo boxes.
*/
private SimpleJComboBox
	__confluenceNameSimpleJComboBox,
	__diversionNameSimpleJComboBox,
	__gainSimpleJComboBox,
	__mfrWeightNameSimpleJComboBox,
	__reservoirNameSimpleJComboBox,
	__rowTypeSimpleJComboBox,
	__stationNameSimpleJComboBox,
	__streamNameSimpleJComboBox,
	__waterDistrictSimpleJComboBox;

/**
The name of the current wis sheet.
*/
private String __sheetName;

/**
Hashkeys for finding certain types of data for the sheet.
*/
private String
	__curConfluenceHashkey,
	__curDiversionHashkey,
	__curMinflowHashkey,
	__curReservoirHashkey;

/**
Strings to keep track of WDs during certain sheet operations.
*/
private String 
	__curStationWD,
	__curStreamWD;

/**
Lists of data that will go into the combo boxes on the form.
*/
private List<HydroBase_StructureView> __diversionVector;
private List<HydroBase_StructureView> __reservoirVector;
private List<HydroBase_StructureView> __mfrWeightVector;
private List<HydroBase_StationView> __stationVector;
private List<HydroBase_Stream> __streamVector;
private List<HydroBase_Stream> __confluenceVector;

/**
The list that keeps track of formulas as they are built or displayed.
*/
private List<HydroBase_WISMath> __formulaVector;

/**
The list of data that keeps track of all the wis_format objects that will
be written to the database.
*/
private List<HydroBase_WISFormat> __wisFormatList;

/**
Constructor.
@param parent the parent JFrame running the application.
@param geoview_ui GeoViewUI interface for map interaction.
@param dmi open and connected dmi object for reading data from the database.
@param wis HydroBase_WISSheetName object.
*/
public HydroBase_GUI_WISBuilder(JFrame parent, GeoViewUI geoview_ui,
HydroBaseDMI dmi, HydroBase_WISSheetName wis) {
	__parent = parent;
	__geoview_ui = geoview_ui;
	__dmi = dmi;
	__wis = wis;
	__wisNum = wis.getWis_num();
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI();
}

/**
Constructor.
@param parent the parent JFrame running the application.
@param geoview_ui GeoViewUI interface for map interaction.
@param dmi open and connected dmi object for reading data from the database.
@param wis HydroBase_WISSheetName object.
@param stream HydroBase_Stream object.
*/
public HydroBase_GUI_WISBuilder(JFrame parent, GeoViewUI geoview_ui,
HydroBaseDMI dmi, HydroBase_WISSheetName wis, HydroBase_Stream stream) {
	__parent = parent;
	__geoview_ui = geoview_ui;
	__dmi = dmi;
	__stream = stream; 
	__wis = wis;
	__wisNum = wis.getWis_num();
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI();
}

/**
Responds to action events.
@param event ActionEvent object.
*/
public void actionPerformed(ActionEvent event) {
	String command = event.getActionCommand();

	if (command.equals(__BUTTON_ADD)) {
		addOperator("+"); 
	}
	else if (command.equals(__BUTTON_ADD_FORMULA)) {
		addFormulaClicked(); 
	}	
	else if (command.equals(__BUTTON_ADD_IMPORT)) {
		addImportClicked();
	}
	else if (command.equals(__BUTTON_ADD_ROW)) {
		HydroBase_WISFormat rowFormat = __wisFormatList.get( __curCellRow + 1);
		String rowType = rowFormat.getRow_type();
		String label = rowFormat.getRow_label();

		// REVISIT (JTS - 2003-11-26)
		// the confluence, other, station and string sections
		// might need filled out
		if (rowType.equals(HydroBase_GUI_WIS.CONFLUENCE)) {
			// nothing needs checked
		}
		else if (rowType.equals(HydroBase_GUI_WIS.DIVERSION)) {
			if (__diversionNameSimpleJComboBox.getSelected().startsWith(__SELECT_DIVERSION) || label.equals(DMIUtil.MISSING_STRING)) {
				// No diversion was selected
				new ResponseJDialog(this,
					"Select a Diversion",
					"You must select a diversion.",
					ResponseJDialog.OK);
				ready();
				return;
			}				
		}	
		else if (rowType.equals(HydroBase_GUI_WIS.MIN_FLOW_REACH)) {
			if (__mfrWeightNameSimpleJComboBox.getSelected().startsWith(__SELECT_MFR) || label.equals(DMIUtil.MISSING_STRING)) {
				// No min. flow reach was selected...
				new ResponseJDialog(this,
					"Select a Minimum Flow Reach",
					"You must select a minimum flow reach.",
					ResponseJDialog.OK);
				ready();
				return;
			}
		}
		else if (rowType.equals(HydroBase_GUI_WIS.OTHER)) {
			// nothing needs checked
		}
		else if (rowType.equals(HydroBase_GUI_WIS.RESERVOIR)) {
			if (__reservoirNameSimpleJComboBox.getSelected().startsWith(__SELECT_RESERVOIR) || label.equals(DMIUtil.MISSING_STRING)) {
				// No reservoir was selected...
				new ResponseJDialog(this,
					"Select a Reservoir",
					"You must select a reservoir.",
					ResponseJDialog.OK);
				ready();
				return;
			}
		}
		else if (rowType.equals(HydroBase_GUI_WIS.STATION)) {
			// nothing needs checked
		}
		else if (rowType.equals(HydroBase_GUI_WIS.STREAM)) {
			if(__streamNameSimpleJComboBox.getSelected().startsWith( __SELECT_STREAM) || label.equals(DMIUtil.MISSING_STRING)) {
				// No stream was selected so prompt the user...
				new ResponseJDialog(this, 
					"Select a Stream",
					"You must select a stream.",
					ResponseJDialog.OK).response();
				return;
			}
		}		
		else if (rowType.equals(HydroBase_GUI_WIS.STRING)) {
			// nothing needs checked
		}
		else if (rowType.equals("")) {
			// nothing needs checked
		}

		addRowClicked(false);
		// Clear the gains information display in the gain/loss column
		displayGains(false); 
	}	
	else if (command.equals(__BUTTON_BUILD_DIAGRAM)) {
		buildDiagramClicked();
	}
	else if (command.equals(__BUTTON_CLOSE)) {
		close();
	}	
	else if (command.equals(__BUTTON_DELETE)) {
		deleteFormula(); 
	}	
	else if (command.equals(__BUTTON_DELETE_FORMULA)) {
		deleteFormulaClicked(); 
	}	
	else if (command.equals(__BUTTON_DELETE_IMPORT)) {
		deleteImportClicked();
	}
	else if (command.equals(__BUTTON_DELETE_ROW)) {
		deleteRowClicked();
		// Clear the gains information display in the gain/loss column
		displayGains(false);
	}	
	else if (command.equals(__BUTTON_DISPLAY_GAINS)) {
		displayGains(true); 
	}	
	else if (command.equals(__BUTTON_DIVIDE)) {
		addOperator("/"); 
	}	
	else if (command.equals(__BUTTON_EDIT_FORMULA)) {
		addFormulaClicked(); 
	}	
	else if (command.equals(__BUTTON_EDIT_IMPORT)) {
		addImportClicked();
	}
	else if (command.equals(__BUTTON_HELP)) {
		// REVISIT HELP (JTS - 2003-11-17)
	}	
	else if (command.equals(__BUTTON_MULTIPLY)) {
		addOperator("*"); 
	}	
	else if (command.equals(__BUTTON_SAVE)) {
		archiveData();
	}	
	else if (command.equals(__BUTTON_SHOW_NETWORK)) {
		showNetworkClicked(); 
	}	
	else if (command.equals(__BUTTON_SUBTRACT)) {
		addOperator("-"); 
	}
	else if (command.equals(__BUTTON_SUM)) {
		sumClicked(); 
	}	
	else if (command.equals(__POPUP_PROPERTIES)) {
		showCellProperties(__curCellRow, __curCellCol);
	}
}

/**
Called when the enter key is pressed in the __constantJTextField.
Check the number format of the constant and adds it to the __formulaList object.
*/
private void addConstantClicked() {
	double finalValue = DMIUtil.MISSING_DOUBLE;
	Double valueAsDouble = null;
	HydroBase_WISMath wisMath = new HydroBase_WISMath();
	int listIndex = __formulaList.getSelectedIndex();
	String expression = "";
	String valueAsString = __constantJTextField.getText().trim();

	// check the formula length
	if (!checkFormulaLength()) {
		__constantJTextField.setText("");
		return;
	}

	try {	
		valueAsDouble = new Double(valueAsString);
		finalValue = valueAsDouble.doubleValue();
	}
	catch (NumberFormatException e) {
		new ResponseJDialog(this, "Invalid Value",
			valueAsString + " is not a "
			+ "valid constant value.", ResponseJDialog.OK);
		return;
	}
	 
	// don't build an expression if the list box is not selected
	if (listIndex == -1) {
		__isBuildingFormula = false;
		return;
	}

	// get the recently added operator
	String theOperator = ((String)__formulaList.getSelectedItem()).substring(0, 1);
	wisMath.setRange(false);
	wisMath.setOperator(theOperator);
	wisMath.setIsConstant(true);
	wisMath.setConstant(finalValue);
 
	// List object operations: show the built expression in the list object
	// at the appropriate location while deselecting the recently added
	// expression.	
	expression = theOperator + " " + finalValue;
	__formulaList.set(listIndex, expression);
//	__formulaList.deselect(listIndex);
	__constantCard.show(__constantJPanel, __HIDE);
	__isConstantShown = false;
	__constantJTextField.setText("");

	__formulaVector.set(listIndex,wisMath );
	__isBuildingFormula = false;
	updateCellFormula(__curCellRow, __curCellCol);
}

/**
Called when the Add Formula button is pressed.  Shows the formula panel and
begins the building process.
*/
private void addFormulaClicked() {
	// update current row before building an expression
	if (!updateRowFormat(__curCellRow)) {
		return;
	}

	// initialize variables
	__formulaList.removeAll();
	__formulaVector.clear();
	__addFormulaJButton.setText(__BUTTON_ADD_FORMULA);

	// disable import cells since a cell can be either an import
	// or formula cell, but not both.
	setButtonState(__IMPORT, false);

	setFormulaLabel(__curCellRow, __curCellCol);
	setCellColor(__curCellRow, __curCellCol, __curCellCol, Color.red, Color.red);

	__rowTypeCard.show(__rowTypeJPanel, "formula");
	addOperator("=");
	__isBuildingFormula = true;
	setDirty(true);
}

/**
Called when the Add Import button is pressed.  Begins the process of adding import data.
*/
private void addImportClicked() {
	updateRowFormat(__curCellRow);

	HydroBase_WISFormat wisFormat = __wisFormatList.get( __curCellRow + 1);
	HydroBase_WISImport wisImport = wisFormat.getWISImport( __worksheet.getVisibleColumn(__curCellCol));

	int station_num = DMIUtil.MISSING_INT;

	JGUIUtil.setWaitCursor(this, true);

	// Don't allow imports in a STREAM row type.
	if (wisFormat.getRow_type().equals(HydroBase_GUI_WIS.STREAM)) {
		new ResponseJDialog(this, 
			"Invalid Import",
			"You can't import into a Stream row.",
			ResponseJDialog.OK).response();
		ready();
		return;
	}
	
	if (__importWizardGUI == null) {
		__importWizardGUI = new HydroBase_GUI_WISImportWizard(
			__dmi, this, station_num, __curCellRow, __curCellCol, 
			wisImport);
	}
	else {	
		__importWizardGUI.setVisible(station_num, __curCellRow, __curCellCol, wisImport);
	}

	setDirty(true);

	ready();	
}

/**
Called when one of the buttons to add an operator is pressed.  Formula
terms are always appended to the end of the formula editor list.  
@param symbol(e.g., "+", "-", "*", "/", "+").
*/
private void addOperator(String symbol) {
	// update current row before building an expression
	if (!updateRowFormat(__curCellRow)) {
		return;
	}

	if (__isBuildingFormula) {
		return;
	}

	// initialize variables
	HydroBase_WISMath wisMath = new HydroBase_WISMath();
	__constantCard.show(__constantJPanel, __SHOW);
	__isConstantShown = true;
	int listIndex = __formulaList.getItemCount();

	// check the formula length
	if (!checkFormulaLength()) {
		return;
	}

	String tempString = " Select a cell or specify a constant value...";
	__statusJTextField.setText(tempString);
	__formulaList.add(symbol + tempString, listIndex);
	__formulaList.setSelectedIndex(__formulaList.getListSize() - 1);

	wisMath.setOperator(symbol);
	__formulaVector.add(wisMath);

	__formulaList.select(listIndex);
	__isBuildingFormula = true;
	setDirty(true);
}

/**
Called when the Add Row button is pressed.  The user is prompted for append or
insert row if the last row in the sheet is selected.  Rows cannot be inserted
above the first row.
@param initial if true, then this is being called because a new WIS sheet
is being generated and the row should be automatically entered without
asking the using anything.
*/
private void addRowClicked(boolean initial) {
	int row = __curCellRow;
	int numRows = __worksheet.getRowCount();
	boolean isAppending = false;

	// update current row before adding a new one
	// unless the first row has not yet been added
	if (!updateRowFormat(row) && (__worksheet.getRowCount() > 1)) {
		return;
	}

	// prompt for insert or append if last row is selected.
	if ((row == (numRows - 1)) && (numRows != 1) && !initial) {
		int r = new ResponseJDialog(this, 
			"Insert a Row?",
			"Do you want to insert a row?"
			+ "\nNOTE: Selecting No will append a new row to the"
			+ " bottom of the sheet.", 
			ResponseJDialog.YES|ResponseJDialog.NO |
			ResponseJDialog.CANCEL).response();
		if (r == ResponseJDialog.NO) {
			isAppending = true;
		}
		else if (r == ResponseJDialog.CANCEL) {
			return;
		}
	}
	// Don't allow add row if first row is selected and more rows 
	// exist in the sheet
	else if (row == 0 && numRows > 1) {
		new ResponseJDialog(this, 
			"Invalid Insertion Point",
			"You cannot insert a row "
			+ "before the main stem stream row.",
			ResponseJDialog.OK).response();
		return;
	}
	// append for the first row if no more rows in the sheet exist
	else if (row == 0) {
		isAppending = true;
	}

	setDirty(true);

	resetComboBoxes();
	JGUIUtil.setWaitCursor(this, true);
	setButtonsState(false);

	// insert row at the current location if the row header is selected
	int newRow = 0;
	HydroBase_WISFormat format = new HydroBase_WISFormat();
	if (initial) {
		format.setRow_type(HydroBase_GUI_WIS.STREAM);
	}
	if (!isAppending) {
		newRow = row;
		__wisFormatList.add(row + 1,format);
		__worksheet.insertRowAt(new HydroBase_WISData(), row);
		setCellColor(row, 2, 5, Color.white, Color.white);
	}
	// ...otherwise add to the end of the grid
	else {	
		int atRow = __worksheet.getRowCount();
		newRow = atRow;
		__wisFormatList.add(format);
		__worksheet.addRow(new HydroBase_WISData());
		setCellColor(atRow, 2, 5, Color.white, Color.white);
	}

	showJPanel();
	refreshWorksheet();
	selectCell(newRow, HydroBase_GUI_WIS.POINT_FLOW_COL);
	__worksheet.scrollToRow(newRow);

	// apply defaults for the newly added row
	__rowTypeSimpleJComboBox.select(HydroBase_GUI_WIS.STRING);	
	__stringRowLabelJTextField.setText("");
	setUniqueID();
	__deleteRowJButton.setEnabled(true);

	selectCell(newRow, HydroBase_GUI_WIS.POINT_FLOW_COL);
	// clear existing fields.
	clearFields();

	// set the default weight value
	__divWeightJTextField.setText("" + __DEFAULT_WEIGHT);
	__resWeightJTextField.setText("" + __DEFAULT_WEIGHT);
	__mfrWeightJTextField.setText("" + __DEFAULT_WEIGHT);
	__staWeightJTextField.setText("" + __DEFAULT_WEIGHT);
	__cflWeightJTextField.setText("" + __DEFAULT_WEIGHT);
	__otherWeightJTextField.setText("" + __DEFAULT_WEIGHT);

	// Update the row labels for the current and following rows, in case the
	// expanded details are shown...

	updateRowLabels(row);

	ready();
	selectCell(newRow, HydroBase_GUI_WIS.POINT_FLOW_COL);
	refreshNetwork();
	notifyDiagramGUI();
}

/**
Save the WIS to the database.  The WIS contents are taken from the Vectors of
data (e.g., Vector of WISFormat), NOT the display contents.  For example, the
row label can display extra information (like the row type) to help the user
but this information is not saved in the WIS format.
@return true on success, false otherwise.
*/
private boolean archiveData() {
	String routine = CLASS + ".archiveData";

	DateTime effective_date = null;
	try {
		effective_date = new DateTime(DateTime.DATE_CURRENT | DateTime.PRECISION_DAY);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error formatting current "
			+ "date for saving data.  Will continue, but save may "
			+ "not work properly.");
		Message.printWarning(2, routine, e);
	}

	// update the current row before archiving
	if (!updateRowFormat(__curCellRow)) {
		return false;
	}
	refreshWorksheet();

	String tempString = "Archiving Water Information Sheet...";
	Message.printStatus(1, "HydroBase_GUI_WISBuilder.archive",tempString);
	__statusJTextField.setText(tempString);	 
	JGUIUtil.setWaitCursor(this, true);

	__wisNum = getRecentFormatWISNum();

	// remove old records from WIS_Format, WIS_Import,  and WIS_Formula 
	// for __wisNum
	try {
		__dmi.deleteWISFormatForWis_num(__wisNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error deleting records from wis_format table.");
		Message.printWarning(2, routine, e);
	}

	try {
		__dmi.deleteWISImportForWis_num(__wisNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error deleting records from wis_import table.");
		Message.printWarning(2, routine, e);
	}

	try {
		__dmi.deleteWISFormulaForWis_num(__wisNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error deleting records from wis_formula table.");
		Message.printWarning(2, routine, e);
	}

	// update the gain computation choice
	String gain_method = __gainSimpleJComboBox.getSelected().trim();
	if (gain_method.equals(HydroBase_GUI_WIS.NONE)) {
		gain_method = "N";
	}
	else if (gain_method.equals(HydroBase_GUI_WIS.STREAM_MILE)) {
		gain_method = "S";
	}
	else if (gain_method.equals(HydroBase_GUI_WIS.WEIGHTS)) {
		gain_method = "W";
	}

	try {
		__dmi.updateSheet_nameGain_methodEffective_dateForWis_num( gain_method, effective_date, __wisNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error updating sheet_name records.");
		Message.printWarning(2, routine, e);
	}
	
	// loop through all the elements in the __wisFormat object.
	// recall that element 0 contains the row headings
	int size = __wisFormatList.size();
	HydroBase_WISFormat wisFormat;
	HydroBase_WISFormula wisFormula;
	HydroBase_WISImport wisImport;

	for (int curRow = 1; curRow < size; curRow++) {
		wisFormat = (HydroBase_WISFormat)
			__wisFormatList.get(curRow);
		
		// These checks prevent zero length Strings from being saved
		// to the database(SAMX - why is that bad?)...
		String row_label = wisFormat.getRow_label().trim();
		if (row_label.equals("")) {
			row_label = " ";
		}

		String known_point = wisFormat.getKnown_point().trim();
		if (known_point.equals("")) {
			// assume "N" if one is not specified
			known_point = "N";
		}

		wisFormat.setWis_num(__wisNum);
		wisFormat.setWis_row(curRow);
		wisFormat.setRow_label(row_label);
		wisFormat.setKnown_point(known_point);

		try {
			__dmi.writeWISFormat(wisFormat);
		}
		catch (Exception e) {	
			Message.printWarning(1, routine, "Error writing to wis_format table.");
			Message.printWarning(2, routine, e);
		}

		// construct the dmiString for HydroBase_WISFormula
		for (int curCol = 1; curCol < HydroBase_GUI_WIS.NUM_COLUMNS; 
			curCol++) {
			wisFormula = __wisFormatList.get(curRow).getWISFormula(curCol);
			String formula=wisFormula.getFormula().trim();

			if (!formula.equals("")) {
				// archive only if a formula exists
				wisFormula.setWis_num(__wisNum);
				wisFormula.setWis_row(curRow);
				wisFormula.setFormula(formula);
				try {
					__dmi.writeWISFormula(wisFormula);
				}
				catch (Exception e) {
					Message.printWarning(1, routine, "Error writing to wis_formula table.");
					Message.printWarning(2, routine, e);
				}
			}
		}

		// construct the dmiString for HydroBase_WISImport
		for (int curCol = 1; curCol < HydroBase_GUI_WIS.NUM_COLUMNS; 
			curCol++) {
			wisImport = __wisFormatList.get(curRow).getWISImport(__worksheet.getVisibleColumn( curCol));
			String import_method = wisImport.getImport_method().trim();

			// archive only for columns in which an import method exist
			if (!import_method.equals("")) {
				wisImport.setWis_num(__wisNum);
				wisImport.setWis_row(curRow);
				try {	
					__dmi.writeWISImport(wisImport);
				}
				catch (Exception e) {
					Message.printWarning(1, routine, "Error writing to wis_import table.");
					Message.printWarning(2, routine, e);
				}
			}
		}
	}
	ready();
	setDirty(false);
	return true;
}

/**
Called when the baseflow check box is clicked.  The cell colors for the
current cell are set accordingly.
@param radioButton JCheckBox object.
*/
private void baseflowClicked(JCheckBox radioButton) {
	if (radioButton.isSelected()) {
		setCellColor(__curCellRow, 2, 2, Color.white, Color.white);
	}
	else {	
		setCellColor(__curCellRow, 2, 2, Color.lightGray, Color.lightGray);
	}
	updateRowFormat(__curCellRow);
	isValidFormulaCell();
	isValidImportCell();
	refreshWorksheet();
}

/**
Build the syntax for the expression and update the cell formula for the current cell.
*/
private void buildExpression(int row, int col) {
	HydroBase_WISMath wisMath = new HydroBase_WISMath();     
	int listIndex = __formulaList.getSelectedIndex();
	String noValues =  "This column does not contain values.";
 
	// don't build an expression if the list box is not selected
	__isBuildingFormula = false;
	if (listIndex == -1) {
		return;
	}
	else if (!isValidFormulaTerm(row, col, __curCellRow, __curCellCol)) {
		__formulaList.remove(listIndex);
		return;
	}

	// get the recently added operator
	String theOperator = 
		((String)__formulaList.getSelectedItem()).substring(0, 1);

	// check the cell that was selected during building		     
	String colType = getColumnType(col);
	if (colType == null || colType == HydroBase_GUI_WIS.S_COMMENTS) {
		new ResponseJDialog(this, "Error", noValues,ResponseJDialog.OK);
		return;
	}
	HydroBase_WISFormat wisFormat = __wisFormatList.get(row + 1);
	wisMath = new HydroBase_WISMath(); 
	wisMath.setRange(false);
	wisMath.setOperator(theOperator);
	wisMath.setColumnType(colType);
	wisMath.setLabel(wisFormat.getRow_label().trim() + "." + colType);
	wisMath.setUniqueID(wisFormat.getIdentifier().trim());
	String expression = wisMath.getLabel();

	// List object operations: show the built expression in the list object
	// at the appropriate location while deselecting the recently added expression.	
	expression = theOperator + " " + expression;
	__formulaList.set(listIndex, expression);
//	__formulaList.deselect(listIndex);
	__constantCard.show(__constantJPanel, __HIDE);
	__isConstantShown = false;
	__formulaVector.set(listIndex,wisMath );

	updateCellFormula(__curCellRow, __curCellCol);
	setDirty(true);
}

/**
Responds to the Build Diagram button being clicked. If the network GUI has 
already been created and is open, it it popped to the top of the display.
Otherwise it is created and opened.
*/
private void buildDiagramClicked() {
	if (__diagramGUI == null) {
		__diagramGUI = new HydroBase_GUI_WISDiagram(this, __dmi);
	}
	else {
		__diagramGUI.setExtendedState(__diagramGUI.getExtendedState() & ~ICONIFIED);
		__diagramGUI.toFront();
	}
}

/**
Check the length of the formula String and ensure that is does not exceed the
maximum length.
@return true on success, false otherwise.
*/
private boolean checkFormulaLength() {
	String routine = CLASS + ".checkFormulaLength";
	int n = __formulaList.getItemCount();

	// check the length of the formula, return if too long
	if (n > 0) {
		String s = "";
		for (int i = 0; i < (n - 1); i++) {
			s += __formulaList.getItem(i);
		}

		if (s.length() > __MAX_FORMULA_LENGTH) {
			Message.printWarning(1, routine, "Max formula length" + " is " + __MAX_FORMULA_LENGTH + " characters.");
			return false;
		}
	}
	return true;
}

/**
Clear JTextFields for an instance of this class.  These JTextFields are
associated with fields that pertain to row type information.
*/
private void clearFields() {
	__streamRowLabelJTextField.setText("");
	__diversionRowLabelJTextField.setText("");	
	__reservoirRowLabelJTextField.setText("");	
	__mfrWeightRowLabelJTextField.setText("");
	__otherRowLabelJTextField.setText("");
	__confluenceRowLabelJTextField.setText("");
	__divStreamMileJTextField.setText("");
	__divWeightJTextField.setText("");
	__staStreamMileJTextField.setText("");
	__staWeightJTextField.setText("");
	__resStreamMileJTextField.setText("");
	__resWeightJTextField.setText("");
	__mfrWeightStreamMileJTextField.setText("");
	__mfrWeightJTextField.setText("");
	__otherStreamMileJTextField.setText("");
	__otherWeightJTextField.setText("");
	__confluenceStreamMileJTextField.setText("");
	__cflWeightJTextField.setText("");
	__stringRowLabelJTextField.setText("");
	__stationRowLabelJTextField.setText("");

	__ignoreStateChange = true;
	__divKnownPointJCheckBox.setSelected(false);
	__staKnownPointJCheckBox.setSelected(false);
	__resKnownPointJCheckBox.setSelected(false);
	__mfrWeightKnownPointJCheckBox.setSelected(false);
	__otherKnownPointJCheckBox.setSelected(false);
	__ignoreStateChange = false;
}

/**
Close the WIS Builder.
*/
private void close() {
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	if (__dmi.hasPermission(__wis.getWD())) {
		if (__dirty) {
			int r = new ResponseJDialog(this, "Save changes?", 
				"Save changes?", ResponseJDialog.YES 
				| ResponseJDialog.NO | ResponseJDialog.CANCEL)
				.response();
	
			if (r == ResponseJDialog.YES) {
				if (!archiveData()) {
					return;
				}
			}
			else if (r == ResponseJDialog.CANCEL) {
				setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE);
				return;
			}
		}
	}

	setVisible(false);
	dispose();
}

/**
Called when the Delete Formula button is pressed.  Removes the wis_formula
object from the private Vector.
*/
private void deleteFormulaClicked() {
	__formulaList.removeAll();
	__formulaVector.clear();
	__addFormulaJButton.setText(__BUTTON_ADD_FORMULA);
	showJPanel();
	
	// clear out the exiting formula via instantiating
	// a new HydroBase_WISFormula object at the appropriate location
	HydroBase_WISFormat wisFormat = __wisFormatList.get( __curCellRow + 1);
	wisFormat.setWISFormula(new HydroBase_WISFormula(), __curCellCol);
	__wisFormatList.set(__curCellRow + 1,wisFormat );

	// enable import buttons if applicable
	isValidImportCell();

	setCellColor(__curCellRow, __curCellCol, __curCellCol, Color.white, Color.white);
	selectCell(__curCellRow, __curCellCol);
	setDirty(true);
}

/**
Deletes the formula stored at the current cell.
*/
private void deleteFormula() {
	int index = __formulaList.getSelectedIndex();

	if (index == -1) {
		new ResponseJDialog(this,
			"Select Row to Delete",
			"Select a row in the equation editor to delete.",
			ResponseJDialog.OK).response();
		return;
	}
	else {	
		__formulaList.remove(index);
		__formulaVector.remove(index);
	}

	__isBuildingFormula = false;
	
	// entire formula is deleted
	if (__formulaList.getItemCount() == 0) {
		deleteFormulaClicked();
	}
	// Determine if terms still exist in the formula. if so replace the
	// elementAt(0)with an operator.
	else if (index == 0 && !__formulaVector.isEmpty()) {
		// update the __formulaVector and list object
		HydroBase_WISMath wisMath = __formulaVector.get(0);
		wisMath.setOperator("=");
		__formulaVector.set(0,wisMath);
		__formulaList.set(0, HydroBase_WISMath.getTerm( __formulaVector.get(0), HydroBase_WISMath.LABEL));
	}
	updateCellFormula(__curCellRow, __curCellCol);
}

/**
Called when the Delete Import button is pressed.  Removes the wis_import object
from the private Vector.
*/
private void deleteImportClicked() {
	__addImportJButton.setText(__BUTTON_ADD_IMPORT);
	
	// clear out the exiting formula via instantiating
	// a new HydroBase_WISImport object at the appropriate location
	HydroBase_WISFormat wisFormat = __wisFormatList.get( __curCellRow + 1);
	wisFormat.setWISImport(new HydroBase_WISImport(), __curCellCol);
	__wisFormatList.set(__curCellRow + 1,wisFormat);

	// enable formula buttons if applicable
	isValidFormulaCell();

	setCellColor(__curCellRow, __curCellCol, __curCellCol, Color.white, 
		Color.white);
	selectCell(__curCellRow, __curCellCol);
	setDirty(true);
}

/**
Called when the Delete Row button is pressed.  Removes the currently-selected
row and any associated formulae.
*/
private void deleteRowClicked() {
	int row = __curCellRow;
	int numRows = __worksheet.getRowCount();

	HydroBase_WISFormat rowFormat = __wisFormatList.get(row + 1);
	String rowType = rowFormat.getRow_type();

	// return if a valid row is not selected
	if (row < 0) {
		return;
	}
	// can't delete top row
	else if (row == 0) {
		new ResponseJDialog(this, 
			"Invalid Deletion",
			"Cannot remove top row.",
			ResponseJDialog.OK).response();
		return;
	}
	// deleting a stream row type. determine if structures exist below.
	// if so, issue a warning.
	else if (rowType.equals(HydroBase_GUI_WIS.STREAM)) {
		// ensure that rows exist below the selected row before 
		// checking for structures/stations.
		if (numRows > row + 1) {
			HydroBase_WISFormat belowFormat = __wisFormatList.get(row + 2);
			if (belowFormat.getWdwater_num() == rowFormat.getWdwater_num()) {
				new ResponseJDialog(this,
					"Invalid Deletion",
					"Cannot remove stream row."
					+ " You must first delete all other"
					+ " rows that are used"
					+ " for the tributary.", 
					ResponseJDialog.OK).response();
				return;
			}
		}
	}

	setDirty(true);

	JGUIUtil.setWaitCursor(this, true);
	setButtonsState(false);

	// de-allocate space in the __wisFormat object and
	// remove the row from the __worksheet object
	int selRow = row;
	__worksheet.deleteRow(selRow);
	__wisFormatList.remove(row + 1);

	if (selRow == (__worksheet.getRowCount())) {
		selRow--;
	}

	setCurrentCell(selRow, __curCellCol);

	refreshWorksheet();

	// update the numRows as the row has been deleted
	// at this point
	numRows = __worksheet.getRowCount();

	// set the current row accordingly
	if (row != 0 && numRows > 0) {
		selectCell(selRow, HydroBase_GUI_WIS.POINT_FLOW_COL);
		displayRow(selRow);
	}

	if (numRows == 0) {
		__deleteRowJButton.setEnabled(false);
	}
	else {	
		__deleteRowJButton.setEnabled(true);
	}

	// Update the row labels for the current and following rows, in case the
	// expanded details are shown...
	updateRowLabels(row);

	JGUIUtil.setWaitCursor(this, false);
	setButtonsState(true);

	refreshNetwork();
	notifyDiagramGUI();
}

/**
Generate and display a list of confluence streams for the currently-selected
water district or water district and stream.  Selection is determined by the input flag.
@param flag Specify the confluence streams to return. __ALL is used to retrieve
all the confluences for the selected water district, while __STREAM is used to
retrieve confluences according to selected stream and water district.
*/
private void displayConfluenceList(int flag) {
	String routine = CLASS + ".displayConfluenceList";

	// First figure out the key to look up the list in the hashtable.  If
	// flag is __ALL, then the name of the key is the current water district
	// If the flag is __STREAM, then the key is the water district and
	// stream number, separated by _.
	int stream_num = getTribStreamNum(getSelectedRow());
	String current_wd = HydroBase_WaterDistrict.parseWD( __waterDistrictSimpleJComboBox.getSelected().trim());
	String hashkey = "";

	if (flag == __ALL) {
		hashkey = current_wd;
	}
	else {	
		hashkey = current_wd + "_" + stream_num;
	}

	if (hashkey.equals(__curConfluenceHashkey)) {
		// No need to update the list because the combination has not changed.
//		Message.printStatus(1, routine,
//			"Confluence display has not changed.  Not updating.");
		return;
	}

	// Try to look up the confluence list in the hashtable...
	__confluenceVector = __confluencesHashtable.get(hashkey);
	if (__confluenceVector == null) {	
		int tempStream_num = DMIUtil.MISSING_INT;
		if (flag == __STREAM) {
			tempStream_num = (int)stream_num;
			__conStreamJRadioButton.setSelected(true);
		}
		
		JGUIUtil.setWaitCursor(this, true);

		int wd = (new Integer(current_wd)).intValue();
		try {
			__confluenceVector = __dmi.readStreamListForWDStr_trib_to( wd, tempStream_num);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading from stream_list table.");
			Message.printWarning(2, routine, e);
		}

		if (__confluenceVector != null && !(__confluenceVector.isEmpty())) {
			// Add one item at the start to agree with the SimpleJComboBox.
			__confluenceVector.add(0,null);
        	}
		else {	
			// At least have one item...
			__confluenceVector = new Vector<HydroBase_Stream>(1);
			__confluenceVector.add(null);
		}
		// Save the list in the hashtable so it can be retrieved later.
		__confluencesHashtable.put(hashkey, __confluenceVector);
	}

       	Message.printStatus(1, routine, "Found " 
		+ (__confluenceVector.size() - 1) + " confluence streams in WD "
		+ current_wd);

	// Update the choice...
	__ignoreStateChange = true;
	__confluenceNameSimpleJComboBox.removeAll();

	// Add information-only first choice item (matched with null 
	// HydroBase_Stream in the __confluenceVector)...
	__confluenceNameSimpleJComboBox.add(__SELECT_STREAM);

	// Now add all the items in __confluenceVector (ignoring the first null object)...
	int size = 0;
	if (__confluenceVector != null) {
		size = __confluenceVector.size();
	}
	HydroBase_Stream data = null;
	for (int i = 1; i < size; i++) {
		data = __confluenceVector.get(i);
		// Show the name and stream number (which is
		// from the Stream table).  This is OK
		// because all selections from the list use the
		// index, which is cross-referenced to the vector.
		__confluenceNameSimpleJComboBox.add( data.getStream_name().trim() + " (" + data.getStream_num() + ")");
	}

	// Save so that we don't do the same query the next time...
	__curConfluenceHashkey = hashkey;

	__confluenceNameSimpleJComboBox.select(0);
	
	__ignoreStateChange = false;
	ready();
}

/**
Display the gain/loss values for each row in the gain/loss column.  This method
will also clear the column when boolean parameter passed in is false.
*/
private void displayGains(boolean isDisplay) {
	// initialize variables
	String routine = CLASS + ".displayGains";

	String gain = __gainSimpleJComboBox.getSelected().trim();

	String display = "";
	int numRows = __worksheet.getRowCount();

	JGUIUtil.setWaitCursor(this, true);
	__statusJTextField.setText("Please Wait...Calculating Gains");

	// update the current row before determining the network
	// and then the gains.
	if (!updateRowFormat(__curCellRow)) {
		ready();
		return;
	}

	// update the WISFormat object's row number.
	updateRowNumbers();

	// Clear the display if we need.
	if ((isDisplay == false) || (gain.equals(HydroBase_GUI_WIS.NONE))) {
		for (int i = 1; i < numRows; i++) { 
			setCellContents(display, i, HydroBase_GUI_WIS.GAIN_LOSS_COL);
		}

		ready();
		return;
	}

	// Get the gains for each row and display in the gain/loss column
	// Remember we must start at row 1, as row 0 is for the header labels.
	HydroBase_NodeNetwork network = new HydroBase_NodeNetwork();
	network.readWISFormatNetwork(__wisFormatList, 1);
	HydroBase_Node node = network.getMostUpstreamNode();

	int node_row_num = 5000;	
	double[] values;

	HydroBase_WISFormat wisFormat = null;

	if (gain.equals(HydroBase_GUI_WIS.STREAM_MILE)) { 
		while (node != null) {
			// If the downstream node is null then the last node
			// in the network has been encountered.
			if (node.getDownstreamNode() == null) {
				break;
			}

			display = "";
			wisFormat = node.getWISFormat();
			node_row_num = wisFormat.getWis_row();
			if (node_row_num > numRows) {
				Message.printWarning(1, routine, "Problem calculating the the gains/loss information.");
				break;
			}

			if (DMIUtil.isMissing(node_row_num) || HydroBase_Util.isMissing(node_row_num)) {
				Message.printWarning(1, routine, "Problem calculating the the gains/loss information.");
				break;
			}

			values = HydroBase_WIS_Util.computeGainLoss(network, node);

			// make sure we have a valid computation.
			if (DMIUtil.isMissing(values[0]) || HydroBase_Util.isMissing(values[0])) {
				display = DMIUtil.MISSING_STRING;
			}
			else {
				display += HydroBase_WIS_Util.formatGainLoss( values);
			}
			setCellContents(display, node_row_num - 1, HydroBase_GUI_WIS.GAIN_LOSS_COL);
			node = HydroBase_NodeNetwork.getDownstreamNode(node, HydroBase_NodeNetwork.POSITION_COMPUTATIONAL);
		}
	}
	else if (gain.equals(HydroBase_GUI_WIS.WEIGHTS)) {
		while (node != null) {
			// If the downstream node is null then the last node
			// in the network has been encountered.
			if (node.getDownstreamNode() == null) {
				break;
			}

			display = "";
			wisFormat = node.getWISFormat();
			node_row_num = wisFormat.getWis_row();
			if (node_row_num > numRows) {
				Message.printWarning(1, routine, "Problem calculating the the gains/loss information.");
				break;
			}

			if (DMIUtil.isMissing(node_row_num) || HydroBase_Util.isMissing(node_row_num)) {
				Message.printWarning(1, routine, "Problem calculating the the gains/loss information.");
				break;
			}

			values = HydroBase_WIS_Util.computeWeightedGainLoss( network, node);

			// make sure we have a valid computation.
			if (DMIUtil.isMissing(values[0]) || HydroBase_Util.isMissing(values[0])) {
				display = DMIUtil.MISSING_STRING;
			}
			else {	
				display += HydroBase_WIS_Util.formatWeightedGainLoss( values);
			}

			setCellContents(display, node_row_num - 1, HydroBase_GUI_WIS.GAIN_LOSS_COL);
			node = HydroBase_NodeNetwork.getDownstreamNode(node, HydroBase_NodeNetwork.POSITION_COMPUTATIONAL);
		}
	}

	refreshWorksheet();
	ready();
}

/**
Display the data for the currently selected row into the appropriate
objects and display in the panel at the bottom of the WIS builder.  
The row data can then be edited.
@param rowNum row number to update.
*/
private void displayRow(int rowNum) {
	__settingUpRow = true;
	boolean importCell = isImportCell(rowNum, __curCellCol);
	boolean formulaCell = isFormulaCell(rowNum, __curCellCol);
	__structureWasSelected = false;

	// button enabling for formula and import cells. the check for 
	// isValidImportCell and isValidFormulaCell determine which buttons 
	// are to be enabled. The first two clauses below enable formula buttons
	// or import buttons if the cell is of that type.
	if (!formulaCell && !importCell) {
		isValidFormulaCell();
		isValidImportCell();
	}	
	else if (formulaCell) {
		isValidFormulaCell();
		setButtonState(__IMPORT, false);
	}
	else if (importCell) {
		isValidImportCell();
		setButtonState(__FORMULA, false);
	}
	
        __curCellRow = rowNum;

	// clear existing fields.
	clearFields();

	// Load the row formats into the appropriate components
	HydroBase_WISFormat rowFormat = __wisFormatList.get(rowNum + 1);
	String rowType = rowFormat.getRow_type().trim();
	String knownPoint = rowFormat.getKnown_point().trim();
	String rowLabel = rowFormat.getRow_label().trim();
	double streamMile = rowFormat.getStr_mile();
	String weight_String = "";
	double weight = rowFormat.getGain_factor();
	//String streamName = getStream_name(rowNum);
	String identifier = rowFormat.getIdentifier();
        __rowTypeSimpleJComboBox.select(null);
        __rowTypeSimpleJComboBox.select(rowType);
	__uniqueJTextField.setText(identifier);
	setRowLabel(rowNum, formatRowLabel(rowNum, rowLabel));

	// Display the stream for the current row.  Do so by getting the trib...
	HydroBase_WISFormat stream_format = getTribStreamFormat(getSelectedRow());
		
	if (stream_format == null) {
		__currentStreamJTextField.setText("Unknown");
	}
	else {
		__currentStreamJTextField.setText(
			stream_format.getRow_label() + " ("
			+ stream_format.getIdentifier() + ")");
	}

	// stream mile
	String streamMile_String = null;
	if (DMIUtil.isMissing(streamMile) || HydroBase_Util.isMissing(streamMile)) {
		streamMile_String = "";
	}
	else {	
		streamMile_String = "" + streamMile;
	}

	// weight
	if (DMIUtil.isMissing(weight) || HydroBase_Util.isMissing(weight)) {
		weight_String = "";
	}
	else {	
		weight_String = "" + weight;
	}

	// display row formats according to row type...
	int choice_pos = 0;	
		// Position in the *NameSimpleJComboBox, determined from
		// the position in the corresponding *Vector.
	if (rowType.equals(HydroBase_GUI_WIS.CONFLUENCE)) {
		if (__conStreamJRadioButton.isSelected()) {
			displayConfluenceList(__STREAM);
		}
		else {	
			displayConfluenceList(__ALL);
		}
	        __confluenceRowLabelJTextField.setText(rowLabel);
		__confluenceStreamMileJTextField.setText("" +streamMile_String);
		__cflWeightJTextField.setText(weight_String);
		// Search through the confluences for an identifier that matches
		// that of the current format...
		int size = __confluenceVector.size();
		HydroBase_Stream data = null;
		for (int i = 1; i < size; i++) {
			data = __confluenceVector.get(i);
			if (identifier.equalsIgnoreCase(__CONF + data.getStr_trib_to() + data.getStream_num())) {
				choice_pos = i;
				break;
			}
		}
		__confluenceNameSimpleJComboBox.select(choice_pos);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.DIVERSION)) {
		if (__structStreamJRadioButton.isSelected()) {
			displayStructureList(__STREAM);
		}
		else {	
			displayStructureList(__ALL);
		}
	        __diversionRowLabelJTextField.setText(rowLabel);
		__divStreamMileJTextField.setText("" + streamMile_String);
		__divWeightJTextField.setText(weight_String);
		if (knownPoint.equals("Y")) {
			__divKnownPointJCheckBox.setSelected(true);
		}
		else {	
			__divKnownPointJCheckBox.setSelected(false);
		}
		
		// Search through the diversions for and identifier that matches
		// that of the current format...
		int size = __diversionVector.size();
		HydroBase_StructureView view = null;
		for (int i = 1; i < size; i++) {
			view = __diversionVector.get(i);
			if (identifier.equalsIgnoreCase(__WDID + HydroBase_WaterDistrict.formWDID( view.getWD(), view.getID()))) {
				choice_pos = i;
				break;
			}
		}
		__diversionNameSimpleJComboBox.select(choice_pos);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.MIN_FLOW_REACH)) {
		if (__mfrWeightStreamJRadioButton.isSelected()) {
			displayStructureList(__STREAM);
		}
		else {	
			displayStructureList(__ALL);
		}
	        __mfrWeightRowLabelJTextField.setText(rowLabel);
		__mfrWeightStreamMileJTextField.setText("" + streamMile_String);
		__mfrWeightJTextField.setText(weight_String);
		if (knownPoint.equals("Y")) {
			__mfrWeightKnownPointJCheckBox.setSelected(true);
		}
		else {	
			__mfrWeightKnownPointJCheckBox.setSelected(false);
		}
		// Search through the MFR for and identifier that matches
		// that of the current format...
		int size = __mfrWeightVector.size();
		HydroBase_StructureView view = null;
		for (int i = 1; i < size; i++) {
			view = __mfrWeightVector.get(i);
			if (identifier.equalsIgnoreCase(__WDID + HydroBase_WaterDistrict.formWDID( view.getWD(), view.getID()))) {
				choice_pos = i;
				break;
			}			
		}
		__mfrWeightNameSimpleJComboBox.select(choice_pos);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.OTHER)) {
	        __otherRowLabelJTextField.setText(rowLabel);
		__otherStreamMileJTextField.setText("" + streamMile_String);
		__otherWeightJTextField.setText(weight_String);
		if (knownPoint.equals("Y")) {
			__otherKnownPointJCheckBox.setSelected(true);
		}
		else {	
			__otherKnownPointJCheckBox.setSelected(false);
		}
	}
	else if (rowType.equals(HydroBase_GUI_WIS.RESERVOIR)) {
		// Use the option that is selected...
		if (__resStreamJRadioButton.isSelected()) {
			displayStructureList(__STREAM);
		}
		else {	
			displayStructureList(__ALL);
		}
	        __reservoirRowLabelJTextField.setText(rowLabel);
		//__resStreamNameJTextField.setText("" + streamName);
		__resStreamMileJTextField.setText("" + streamMile_String);
		__resWeightJTextField.setText(weight_String);
		if (knownPoint.equals("Y")) {
			__resKnownPointJCheckBox.setSelected(true);
		}
		else {	
			__resKnownPointJCheckBox.setSelected(false);
		}
		// Search through the reservoirs for and identifier that matches
		// that of the current format...
		int size = __reservoirVector.size();
		HydroBase_StructureView view = null;
		for (int i = 1; i < size; i++) {
			view = __reservoirVector.get(i);
			if (identifier.equalsIgnoreCase(__WDID + HydroBase_WaterDistrict.formWDID( view.getWD(), view.getID()))) {
				choice_pos = i;
				break;
			}
		}
		__reservoirNameSimpleJComboBox.select(choice_pos);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.STATION)) {
		displayStationList();
	        __stationRowLabelJTextField.setText(rowLabel);
		__staStreamMileJTextField.setText("" + streamMile_String);
		__staWeightJTextField.setText(weight_String);
		if (knownPoint.equals("Y")) {
			__staKnownPointJCheckBox.setSelected(true);
		}
		else {	
			__staKnownPointJCheckBox.setSelected(false);
		}
		// Search through the stations for and identifier that matches
		// that of the current format...
		int size = __stationVector.size();
		HydroBase_StationView data = null;
		for (int i = 1; i < size; i++) {
			data = __stationVector.get(i);
			if (identifier.equalsIgnoreCase(__STAT + data.getStation_id())) {
				choice_pos = i;
				break;
			}
		}
		__stationNameSimpleJComboBox.select(choice_pos);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.STREAM)) {
		// Stream rows are handled as follows:
		//
		// New stream row - stream list is for the district
		// Existing stream row - stream list is for the district
		//			matching the stream if available or the
		//			district for the sheet otherwise.
		// Update the list of streams, which can be used to select the
		// row label.  If the stream row already exists, it should be
		// possible to select the stream in the 
		// __streamNameSimpleJComboBox.
		displayStreamList();
		// Set the row label...
	        __streamRowLabelJTextField.setText(rowLabel);
		// Search through the confluences for an identifier that matches
		// that of the current format...
		int size = __streamVector.size();
		HydroBase_Stream data = null;
		for (int i = 1; i < size; i++) {
			data = __streamVector.get(i);
			// Some streams are mainstem and don't have the
			// downstream...
			if (DMIUtil.isMissing(data.getStr_trib_to()) || HydroBase_Util.isMissing(data.getStr_trib_to())) {
				if (identifier.equalsIgnoreCase(__STRM + data.getStream_num())) {
					choice_pos = i;
					break;
				}
			}
			// Also try the full id in case -999 is used for the
			// downstream...
			if (identifier.equalsIgnoreCase(__STRM + data.getStream_num() + data.getStr_trib_to())) {
				choice_pos = i;
				break;
			}
		}
		__streamNameSimpleJComboBox.select(null);
		__streamNameSimpleJComboBox.select(choice_pos);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.STRING)) {
	        __stringRowLabelJTextField.setText(rowLabel);
	}
	// rowType is empty, assume a String row type
	else if (rowType.equals("")) {
	        __stringRowLabelJTextField.setText(rowLabel);
		__rowTypeSimpleJComboBox.select(HydroBase_GUI_WIS.STRING);
	}
	
	// load cell formula if one exist and return
	if (isFormulaCell(rowNum, __curCellCol)) {
		loadCellFormula(rowNum, __curCellCol);
		return;
	}
	// otherwise show the appropriate JPanel
	else {	
		showJPanel();
	}
	__settingUpRow = false;
}

/**
Generate and display a list of stations based on the currently selected water
district.  Stations with meas types RT* or Streamflow are retrieved.
*/
private void displayStationList() {
	String routine = CLASS + ".displayStationList";

	// Return if the same __curStationWD is selected
	String current_wd = HydroBase_WaterDistrict.parseWD( __waterDistrictSimpleJComboBox.getSelected()).trim();
	if (__curStationWD.equals(current_wd)) {
		return;
	}

	// Else, see if there is an existing list...
	__stationVector = __stationsHashtable.get(current_wd);
	if (__stationVector == null) {
		// Need to generate a new list of stations...
		JGUIUtil.setWaitCursor(this, true);
	
		try {
			/*
			__stationVector 
				= __dmi.readStationGeolocMeasTypeListForWD(
				StringUtil.atoi(current_wd), null, 
				null, null, null, true);
			*/
			__stationVector = __dmi.readStationViewListForWD( StringUtil.atoi(current_wd));
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading from station_geoloc_meas_type table.");
			Message.printWarning(2, routine, e);
		}

		if (__stationVector != null && !(__stationVector.isEmpty())) {
			// Loop through and remove duplicates - there may be
			// some because we allow some variation on the data types...

			int size = __stationVector.size();
			String prev_id = "";
			String id = "";
			HydroBase_StationView data = null;
			for (int i = 0; i < size; i++) {
               			data = __stationVector.get(i);
				id = data.getStation_id();
				if (id.equals(prev_id)) {
					__stationVector.remove( i);
					--size;
					--i;	
					// Will be incremented in loop
				}
				prev_id = id;
			}
			// Add one item at the start to
			// agree with the SimpleJComboBox.
			__stationVector.add(0,null);
		}
		else {	
			// At least have one item...
			__stationVector = new Vector<HydroBase_StationView>(1);
			__stationVector.add(null);
		}
		// Save the Vector in the hashtable so it can be retrieved later.
		__stationsHashtable.put(current_wd, __stationVector);
	}

       	Message.printStatus(1, routine, "Found " + (__stationVector.size()- 1)+ " stations in WD " + current_wd);

	// Update the SimpleJComboBox...
	__ignoreStateChange = true;
	__stationNameSimpleJComboBox.removeAll();

	// Add information-only first choice item(matched with empty station
	// object in __stationVector)...
        __stationNameSimpleJComboBox.add(__SELECT_STATION);    

	int size = 0;
	if (__stationVector != null) {
		size = __stationVector.size();
	}

	HydroBase_StationView data = null;
       	for (int i = 1; i < size; i++) {
               	data = __stationVector.get(i);
               	__stationNameSimpleJComboBox.add( data.getStation_name().trim() + " (" + data.getStation_id() + ")");
        }	

	// Save so don't do the same query again...
	__curStationWD = current_wd;

	__stationNameSimpleJComboBox.select(0);

	__ignoreStateChange = false;
	ready();
}

/**
Display stream information in the appropriate objects based on the stream name
SimpleJComboBox selection.
@return true if the method finished successfully.  False if not.
*/
private boolean displayStreamInfo() {
	// Determine the stream that is selected...
	int index = __streamNameSimpleJComboBox.getSelectedIndex();

	// Look up the stream data for the selected item...
	HydroBase_Stream streamData = __streamVector.get(index);

	if (__streamNameSimpleJComboBox.getSelected().startsWith( __SELECT_STREAM)) {
		// No stream was selected so prompt the user...
		new ResponseJDialog(this, 
			"Select a Stream",
			"You must select a stream.",
			ResponseJDialog.OK).response();
		return false;
	}

	// Update the stream text field...
	__streamRowLabelJTextField.setText(streamData.getStream_name());
	__structureWasSelected = true;
	return true;
}

/**
Generates a list of HydroBase_Stream based on the currently selected water 
district and displays in the __streamNameSimpleJComboBox.
Typically the water district is initially that for the sheet.
*/
private void displayStreamList() {
	String routine = CLASS + ".displayStreamList";

	// Return if the current water district is the same as the previous
	// water district displayed __curStreamWD...
	String current_wd = HydroBase_WaterDistrict.parseWD(
		__waterDistrictSimpleJComboBox.getSelected()).trim();

	if (__curStreamWD.equals(current_wd)) {
		if (Message.isDebugOn) {
			Message.printDebug(20, routine,
				"Stream number has not changed (" + current_wd 
				+ "). stream list will not be generated.");
		}
		return;
	}

	// Else, see if we have an existing list...
	__streamVector = __streamsHashtable.get(current_wd);
	if (__streamVector == null) {
		// Need to generate a new list of streams...
		JGUIUtil.setWaitCursor(this, true);
        	String tempString = "Retrieving Stream list for water district " + current_wd;
        	__statusJTextField.setText(tempString);
        	Message.printStatus(1, routine, tempString);

		int wd = (new Integer(current_wd)).intValue();
		try {
			__streamVector = __dmi.readStreamListForWDStr_trib_to( wd, DMIUtil.MISSING_INT);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading from " + "stream_list table.");
			Message.printWarning(2, routine, e);
		}
                                
		if (__streamVector != null && !(__streamVector.isEmpty())) {
			// Add one item at the start to agree with the SimpleJComboBox.
			__streamVector.add(0,null);
        	}
		else {	
			// At least have one item...
			__streamVector = new Vector<HydroBase_Stream>(1);
			__streamVector.add(null);
		}
		// Save the Vector in the hashtable so it can be
		// retrieved later.
		__streamsHashtable.put(current_wd, __streamVector);
	}

       	Message.printStatus(1, routine, "Found " + (__streamVector.size()- 1) + " streams in WD " + current_wd);

	// Now update the SimpleJComboBox...
	__ignoreStateChange = true;
	__streamNameSimpleJComboBox.removeAll();

	// Add information-only first choice item (matched with empty 
	// HydroBase_Stream in the __streamVector)...
        __streamNameSimpleJComboBox.add(__SELECT_STREAM);    

	// Now add all the items in __streamVector (ignoring the first null
	// object)...
	int size = 0;
	if (__streamVector != null) {
		size = __streamVector.size();
	}
	HydroBase_Stream data = null;
	for (int i = 1; i < size; i++) {
		data = __streamVector.get(i);
               	__streamNameSimpleJComboBox.add( data.getStream_name().trim() + " (" + data.getStream_num() + ")");
	}

	__streamNameSimpleJComboBox.select(0);

	__ignoreStateChange = false;

	// Save so we don't do the same query the next time...
	__curStreamWD = current_wd;

	ready();
}

/**
Generate and display a list of structures based on the currently-selected water
district.  The reservoir, mfr, and diversion lists are updated as appropriate,
depending on the structure type that is selected.
@param flag Specify the structures to return. __ALL is used to retrieve all the
structures for the selected water district, while __STREAM is used to retrieve
structures according to stream and water district.
*/
private void displayStructureList(int flag) {
	String routine = CLASS + ".displayStructureList";

	// First figure out the key to look up the list in the hashtable.  If
	// flag is __ALL, then the name of the key is the current water 
	// district. If the flag is __STREAM, then the key is the water 
	// district and stream number, separated by _.

	long stream_num = getTribStreamNum(getSelectedRow());
	String current_wd = HydroBase_WaterDistrict.parseWD( __waterDistrictSimpleJComboBox.getSelected()).trim();
	String hashkey = "";
	if (flag == __ALL) {
		hashkey = current_wd;
	}
	else {	
		hashkey = current_wd + "_" + stream_num;
	}

	// The active row type structure will be either 'R', 'D', or 'M'...
	__ignoreStateChange = true;
	char str_type = __rowTypeSimpleJComboBox.getSelected().charAt(0);
	String global_hashkey = "NOT A VALID HASHKEY";
	if (str_type == 'R') {
		global_hashkey = __curReservoirHashkey;
		__resWDJRadioButton.setSelected(true);
	}
	else if (str_type == 'D') {
		global_hashkey = __curDiversionHashkey;
		__structWDJRadioButton.setSelected(true);
	}
	else if (str_type == 'M') {
		global_hashkey = __curMinflowHashkey;
		__mfrWeightWDJRadioButton.setSelected(true);
	}

	if (flag == __STREAM) {
		// Need to do this if called with __STREAM programatically...
		if (str_type == 'R') {
			__resStreamJRadioButton.setSelected(true);
		}
		else if (str_type == 'D') {
			__structStreamJRadioButton.setSelected(true);
		}
		else if (str_type == 'M') {
			__mfrWeightStreamJRadioButton.setSelected(true);
		}
	}

	__ignoreStateChange = false;

	if (hashkey.equals(global_hashkey)) {
		// No need to update the list because the combination has not changed.
		return;
	}

	// Try to look up the confluence list in the hashtable...
	List<HydroBase_StructureView> structureVector = null;
	if (str_type == 'R') {
		structureVector = __reservoirHashtable.get(hashkey);
	}
	else if (str_type == 'D') {
		structureVector = __diversionHashtable.get(hashkey);
	}
	else if (str_type == 'M') {
		structureVector = __mfrWeightHashtable.get(hashkey);
	}
	if (structureVector == null) {
		String type = null;
		if (str_type == 'R') {
			type = "R";
		}
		else if (str_type == 'D') {
			type = "H";
		}
		else if (str_type == 'M') {
			type = "MFR";
		}

//	Message.printStatus(1, "", "Strtype: '" + str_type + "'");
//	Message.printStatus(1, "", "   Type: '" + type + "'");

		long tempStream_num = DMIUtil.MISSING_LONG;	
		if (flag == __STREAM) {
			// Add a where clause for the stream...
			Message.printStatus(1, routine, "Generating structure list for stream.");
			tempStream_num = stream_num;
		}

		JGUIUtil.setWaitCursor(this, true);
        	__statusJTextField.setText("Retrieving Structure list...");

		try {
			if (type == null) {
				structureVector = new Vector<HydroBase_StructureView>();
			}
			else {
				structureVector = __dmi.readStructureViewListForWDStream_numStr_type(
					StringUtil.atoi(current_wd), tempStream_num, type);
			}
		}
		catch (Exception e) {	
			Message.printWarning(1, routine, "Error reading from structure_geoloc table.");
			Message.printWarning(2, routine, e);
		}

		if (structureVector != null && !(structureVector.isEmpty())) {
			// Add one item at the start to
			// agree with the SimpleJComboBox.
			structureVector.add(0,null);
        	}
		else {	
			// At least have one item...
			structureVector = new Vector<HydroBase_StructureView>(1);
			structureVector.add(null);
		}
		// Save the Vector in the hashtable so it can be
		// retrieved later.
		if (str_type == 'R') {
			__reservoirHashtable.put(hashkey, structureVector);
		}
		else if (str_type == 'D') {
			__diversionHashtable.put(hashkey, structureVector);
		}
		else if (str_type == 'M') {
			__mfrWeightHashtable.put(hashkey, structureVector);
		}
	}
	if (str_type == 'R') {
       		Message.printStatus(1, routine, "Found "
			+ (structureVector.size() - 1) + " reservoirs in WD "
			+ current_wd);
		__reservoirVector = structureVector;
	}
	else if (str_type == 'D') {
       		Message.printStatus(1, routine, "Found "
			+ (structureVector.size() - 1) + " diversions in WD "
			+ current_wd);
		__diversionVector = structureVector;
	}
	else if (str_type == 'M') {
       		Message.printStatus(1, routine, "Found "
			+ (structureVector.size() - 1)+ " instream flows in WD "
			+ current_wd);
		__mfrWeightVector = structureVector;
	}

	// Update the choice
	__ignoreStateChange = true;
	if (str_type == 'R') {
		__reservoirNameSimpleJComboBox.removeAll();
        	__reservoirNameSimpleJComboBox.add(__SELECT_RESERVOIR);
		__curReservoirHashkey = hashkey;
	}
	else if (str_type == 'D') {
		__diversionNameSimpleJComboBox.removeAll();
        	__diversionNameSimpleJComboBox.add(__SELECT_DIVERSION); 
		__curDiversionHashkey = hashkey;
	}
	else if (str_type == 'M') {
		__mfrWeightNameSimpleJComboBox.removeAll();
        	__mfrWeightNameSimpleJComboBox.add(__SELECT_MFR);
		__curMinflowHashkey = hashkey;
	}
	__ignoreStateChange = false;
 
	// Now add the items to the choice...
	int size = 0;
	if (structureVector != null) {
		size = structureVector.size();
	}
	HydroBase_StructureView view = null;
	String name;
	__ignoreStateChange = true;
       	for (int i = 1; i < size; i++) {
		view = structureVector.get(i);
		name = view.getStr_name().trim();
		if (str_type == 'D') {
               	__diversionNameSimpleJComboBox.add(name
				+ " (" 
				+ HydroBase_WaterDistrict.formWDID(
				view.getWD(), view.getID()) + ")");
		}
		else if (str_type == 'R') {
               	__reservoirNameSimpleJComboBox.add(name
				+ " (" 
				+ HydroBase_WaterDistrict.formWDID(
				view.getWD(), view.getID()) + ")");
		}
		else if (str_type == 'M') {
                	__mfrWeightNameSimpleJComboBox.add(name
				+ " (" 
				+ HydroBase_WaterDistrict.formWDID(
				view.getWD(), view.getID()) + ")");
		}
        }
	__diversionNameSimpleJComboBox.select(0);
	__ignoreStateChange = false;
	ready();
}

/**
Display all the structures for a given water district.
@param flag __STREAM if displaying by stream, __ALL if displaying by water district.
*/
private void displayStructuresClicked(int flag) {
	if (flag == __STREAM) {
		__structStreamJRadioButton.setSelected(true);
		__resStreamJRadioButton.setSelected(true);
		__mfrWeightStreamJRadioButton.setSelected(true);
	}
	else if (flag == __ALL) {
		__structWDJRadioButton.setSelected(true);
		__resWDJRadioButton.setSelected(true);
		__mfrWeightWDJRadioButton.setSelected(true);
	}
	displayStructureList(flag);	
	// Display the row to reselect the choice...
	displayRow(__curCellRow);
}

/**
Formats the label for the specified row.
@param row the row for which to format the label.
@param label the label to format for the row.
@return a list that contains the elements of the formatted label.  The first
element will be seen in both the WIS Builder and WIS GUIs.  The second element
will only be seen in the wis builder gui.
*/
private List<String> formatRowLabel(int row, String label) {
	HydroBase_WISFormat formatData = __wisFormatList.get(row + 1);
	String rowType = formatData.getRow_type();
	String rowLabel = label;
	List<String> labelV = new Vector<String>();

	if (rowLabel == null) {
		rowLabel = "";
	}

	labelV.add(rowLabel);
	String rest = "";
	// Append additional row label information so it is easier to
	// edit the sheet.  This information is used only in the display
	// and should not be saved back to the database.  Always want
	// the row type and internal identifier...
	rest = " ("  + (row + 1) + ", " + rowType;
	// Now do additional information...?
	//STRING		= "String",
	//	DIVERSION	= "Diversion",
	//	RESERVOIR	= "Reservoir",
	//	MIN_FLOW_REACH	= "MinFlow",
	//	STATION		= "Station",
	//	CONFLUENCE	= "Confluence",
	//	OTHER		= "Other",
	//	STREAM		= "Stream";
	//if (rowType.equals(HydroBase_GUI_WIS.DIVERSION)) {
	//	...
	//}
	if (!formatData.getIdentifier().trim().equals("")) {
		rest += ", " + formatData.getIdentifier();
	}
	rest += ")";

	labelV.add(rest);

	return labelV;
}

/**
Returns the background color of the currently-selected cell.
@return the background color of the currently-selected cell, or null if no
cell is selected or the background color has not been changed from the default.
*/
private Color getCurrentCellColor() {
	int row = getSelectedRow();
	int col = getSelectedColumn();
	if (row >= 0 && col >= 0) {
		JWorksheet_CellAttributes ca = __worksheet.getCellAttributes( row, col);
		if (ca != null) {
			return ca.backgroundColor;
		}
	}
	return null;
}

/**
Return the HydroBase_WISFormula object corresponding to the specified cell address.
@param row row number.
@param col column number.
@return the HydroBase_WISFormula for the specified cell address if one exists, 
if not null is returned.
*/
protected static HydroBase_WISFormula getCellFormula(List<HydroBase_WISFormula> wisFormulaVector,
int row, int col) {
	HydroBase_WISFormula wisFormula = null;
	if (wisFormulaVector == null) {
		return null;
	}

	// Loop through the wisFormulaVector object to locate the specified 
	// row and column
	int curCol = 0;
	int curRow = 0;
	int size = wisFormulaVector.size();
	for (int i = 0; i < size; i++) {
		wisFormula = wisFormulaVector.get(i);
		curCol = HydroBase_GUI_WIS.getColumnNumber( wisFormula.getColumn());
		curRow = wisFormula.getWis_row();
		if (curCol==col && curRow== (row + 1)) {
			return wisFormula;
		}
	}
	return null;
}

/**
Return a String representation of the column type.
@param col integer representing the desired column.
@return a String representation of the present column, null if not supported.
*/
public static String getColumnType(int col) {
	switch(col) {
                case HydroBase_GUI_WIS.POINT_FLOW_COL:
                        return HydroBase_GUI_WIS.S_POINT_FLOW;
                case HydroBase_GUI_WIS.NATURAL_FLOW_COL:
                        return HydroBase_GUI_WIS.S_NATURAL_FLOW;
                case HydroBase_GUI_WIS.DELIVERY_FLOW_COL:
                        return HydroBase_GUI_WIS.S_DELIVERY_FLOW;
                case HydroBase_GUI_WIS.GAIN_LOSS_COL:
                        return HydroBase_GUI_WIS.S_GAIN_LOSS;
                case HydroBase_GUI_WIS.TRIB_NATURAL_COL:
                        return HydroBase_GUI_WIS.S_TRIB_NATURAL;
                case HydroBase_GUI_WIS.TRIB_DELIVERY_COL:
                        return HydroBase_GUI_WIS.S_TRIB_DELIVERY;
                case HydroBase_GUI_WIS.RELEASES_COL:
                        return HydroBase_GUI_WIS.S_RELEASES;
                case HydroBase_GUI_WIS.PRIORITY_DIV_COL:
                        return HydroBase_GUI_WIS.S_PRIORITY_DIV;
                case HydroBase_GUI_WIS.DELIVERY_DIV_COL:
                        return HydroBase_GUI_WIS.S_DELIVERY_DIV;
		case HydroBase_GUI_WIS.COMMENTS_COL:
			return HydroBase_GUI_WIS.S_COMMENTS;
                default:
                        return null;
        }
}

/**
Compare the current system time to the effective_date for the current wis_num.
If the system time is more recent(to DAY), a new wis_num is added to the
Sheet_Name table for which the working sheet is the newly determined wis_num.
If the current wis_num is equivalent to the current system time, the working
sheet wis_num, remains unchanged.
@return the wis num that was read
*/
private int getRecentFormatWISNum() {
	String routine = CLASS + ".getRecentFormatWISNum";

	// get the current system day
	DateTime TSCurrent = new DateTime( DateTime.DATE_CURRENT | DateTime.PRECISION_DAY);
	TSCurrent.shiftTimeZone(TSCurrent.getTimeZoneAbbreviation());
	String currentDay = TSCurrent.toString(DateTime.FORMAT_Y2K_SHORT);

	// update the __dateJTextField
	__dateJTextField.setText(currentDay);

	// perform query to determine if __wisNum
	// has been archived for the current day
	List<HydroBase_WISSheetName> results = null;
	try {
		results = __dmi.readWISSheetNameList(-999, -999, __sheetName, null, true);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading from sheet_name table.");
		Message.printWarning(2, routine, e);
	}
		
	HydroBase_WISSheetName data = null;
	
	if (results != null && !(results.isEmpty())) {
		// compare effective date for element 0 against the current 
		// day, since effective_date was ordered via DESC		
		data = results.get(0);
		DateTime TSRecent = new DateTime(data.getEffective_date());	
		TSRecent.setPrecision(DateTime.PRECISION_DAY);
		//String mostRecentDay = TSRecent.toString(
			//DateTime.FORMAT_Y2K_SHORT);

		int year1 = TSCurrent.getYear();
		int year2 = TSRecent.getYear();
		int month1 = TSCurrent.getMonth();
		int month2 = TSRecent.getMonth();
		int day1 = TSCurrent.getDay();
		int day2 = TSCurrent.getDay();

		// REVISIT (JTS - 2005-05-10)
		//    if (!TSCurrent.equals(TSRecent)) {
		// was NOT working properly.  Hence, the following:
		// record does not exist for the current day
		if (year1 != year2 || month1 != month2 || day1 != day2) {
			// use the previous saved format info as the new 
			// format info
			int wd = data.getWD();

			HydroBase_WISSheetName sn =new HydroBase_WISSheetName();
			sn.setSheet_name(__sheetName);
			Date date = TSCurrent.getDate(TimeZoneDefaultType.LOCAL);
			sn.setEffective_date(date);
			sn.setWD(wd);

			// records do not exist for the current day. archive 
			// a new wis_num and set this to the working __wisNum.
			// if database is ODBC_ACCESS perform another 
			// sheet_name query in order to determine 
			// the max wis_num.
			if (__dmi.getDatabaseEngine().equals("Access")) {
				int wis_num = DMIUtil.getMaxRecord(__dmi, "sheet_name", "wis_num");
				sn.setWis_num(wis_num);
				try {
					__dmi.writeWISSheetName(sn);
				}
				catch (Exception e) {
					Message.printWarning(1, routine, "Error writing to sheet_name table.");
					Message.printWarning(2, routine, e);
				}
			}
			else {		
				try {
					__dmi.writeWISSheetName(sn);
				}
				catch (Exception e) {
					Message.printWarning(1, routine, "Error writing to sheet_name table.");
					Message.printWarning(2, routine, e);
				}
		       	}

			// get the wis_num of the newly archived sheet using
			// sheet_name and effective date
			try {
				results = __dmi.readWISSheetNameList( -999, -999, __sheetName, TSCurrent);
			}
			catch (Exception e) {
				Message.printWarning(1, routine, "Error reading from sheet_name table.");
				Message.printWarning(2, routine, e);
			}
			data = results.get(0);
			__wisNum = data.getWis_num();
			__wis = data;
		}
	}

	// retrieve all wis_nums with __sheetName...
	List<String> wisNumVector = new Vector<String>();
	try {
		results = __dmi.readWISSheetNameList(-999, -999, __sheetName, 
			null);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading from sheet_name table.");
		Message.printWarning(2, routine, e);
	}

	if (results != null && !results.isEmpty()) {
		int size = results.size();
		for (int i = 0; i < size; i++) {
			data = results.get(i);
			wisNumVector.add("" + data.getWis_num());
		}
	}

	// REVISIT (JTS - 2005-02-15)
	// can't do the big OR clause in SP right now for wis comments

	// impacts of the new format on wis_comments and wis_data		
	// remove from WIS_Data and WIS_Comments where the set Data 
	// is now and wis_num = __wisNum. This allows new formats to be
	// loaded if changed after archiving wis_Data
	List<HydroBase_WISComments> resultsWISComments = null;
	if (!wisNumVector.isEmpty()) {
		try {
			resultsWISComments = __dmi.readWISCommentsList(wisNumVector, TSCurrent);
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading from wis_comments table.");
			Message.printWarning(2, routine, e);
		}
		if (resultsWISComments != null && !resultsWISComments.isEmpty()) {
			// A record was found for today's date and wis_num.
			// Delete this from the the wis_comments and wis_data
			// tables.
			/*
			new ResponseJDialog(this, "Deleting WIS Data"
				+ " that were archived using an older format.",
				ResponseJDialog.OK).response();
			*/
			HydroBase_WISComments wisComments = resultsWISComments.get(0);

			try {
				__dmi.deleteWISForWis_numSet_date( wisComments.getWis_num(), TSCurrent);
			}
			catch (Exception e) {
				Message.printWarning(1, routine, "Error deleting wis data");
				Message.printWarning(2, routine, e);
			}
		}
	}
	return __wisNum;
}

/**
Display confluence information in the appropriate objects depending upon the
currently selected stream.
*/
private void getConfluenceInfo() {
	int index = __confluenceNameSimpleJComboBox.getSelectedIndex();

	// no confluence was selected
	if (__confluenceNameSimpleJComboBox.getSelected().startsWith( __SELECT_STREAM)) {
		new ResponseJDialog(this, 
			"Select a Stream",
			"You must select a stream.",
			ResponseJDialog.OK);
		return;
	}

	HydroBase_Stream streamData = __confluenceVector.get(index);
	__confluenceRowLabelJTextField.setText(streamData.getStream_name());
	double curDouble = streamData.getStr_mile();
	if (!DMIUtil.isMissing(curDouble) && !HydroBase_Util.isMissing(curDouble)) {
		__confluenceStreamMileJTextField.setText("" + curDouble);
	}
	__structureWasSelected = true;
}

/**
Determine the maximum number used in the unique id fields for Other and String row types.
@return the maximum number used in the unique id fields for Other and String row types.
*/
private long getMax() {
	long max = 0;
	int len = __OTHR.length();
	int size = __wisFormatList.size();
	long curNum = 0;
	String id = null;

	for (int count = 1; count < size; count++) {
		id = __wisFormatList.get(count).getIdentifier();

		if (id.startsWith(__OTHR)) {
			curNum = StringUtil.atol(id.substring(len));
			if (curNum > max) {
				max = curNum;
			}
		}
	}

	return max;
}

/**
Returns the currently-selected absolute column.
@return the currently-selected absolute column.
*/
private int getSelectedColumn() {
	return __curCellCol;
}

/**
Returns the currently-selected row.
@return the currently-selected row.
*/
private int getSelectedRow() {
	return __curCellRow;
}

/**
Display station information in the appropriate objects depending upon the
currently selected station.
*/
private void getStationInfo() {
	__structureWasSelected = true;
	int index = __stationNameSimpleJComboBox.getSelectedIndex();

	// no station was selected
	if (__stationNameSimpleJComboBox.getSelected().startsWith(
		__SELECT_STATION)) {
		new ResponseJDialog(this, 
			"Select a Station",
			"You must select a station.",
			ResponseJDialog.OK);
		return;
	}

	HydroBase_StationView stationData = __stationVector.get(index);
	__stationRowLabelJTextField.setText( stationData.getStation_name());	
}

/**
Return the name of the stream to which the current row belongs.
@param row row number.
@return the name of the stream with the same stream_num as the requested row.
If the stream_num of the requested row is DMIUtil.MISSING_INT then the first
occurrence of stream is used.  If the name cannot be located, an empty String is returned.
*/
/* TODO SAM Evaluate whether needed
private String getStream_name(int row) {
	String streamName = "";
	HydroBase_WISFormat wisFormat = 
		(HydroBase_WISFormat)__wisFormatVector.elementAt(row + 1);
	long stream_num = wisFormat.getWdwater_num();
	
	int size = __wisFormatVector.size();
	if (size == 1) {
		return streamName;
	}

	// locate the first occurance of a stream row above the existing row.
	// this is provides a stream name for a row in which a structure has not
	// yet been selected.
	if (DMIUtil.isMissing(stream_num) || HydroBase_Util.isMissing(stream_num)) {
		for (int curRow = row; curRow >= 1; curRow--) {
			wisFormat = (HydroBase_WISFormat)
				__wisFormatVector.elementAt(curRow);
			if (wisFormat.getRow_type().trim().equals(
				HydroBase_GUI_WIS.STREAM)) {
				return wisFormat.getRow_label();
			}
		}
	}
	// locate the stream row with the same stream_num as the requested row
	else {
		for (int curRow = row; curRow >= 1; curRow--) {
			wisFormat = (HydroBase_WISFormat)
				__wisFormatVector.elementAt(curRow);
			if (wisFormat.getRow_type().trim().equals(
				HydroBase_GUI_WIS.STREAM)) {
				if (wisFormat.getWdwater_num() == stream_num) {
					return(wisFormat.getRow_label());
				}
			}
		}
	}
	return streamName;
}
*/

/**
Display diversion, reservoir, and min flow reach information in the appropriate
objects depending upon the selected structure.
*/
private void getStructureInfo() {
	String routine = CLASS + ".getStructureInfo";

	__structureWasSelected = true;

	JGUIUtil.setWaitCursor(this, true);

        String tempString = "Please Wait...Retrieving structure data";
        __statusJTextField.setText(tempString);
        Message.printStatus(2, routine, tempString);

	double mile = -1;
	HydroBase_StructureView structureView = null;
	int index = DMIUtil.MISSING_INT;
	String rowType = __rowTypeSimpleJComboBox.getSelected().trim();
	String str_mile = null;
	String structureName = null;

	// get the structure information from the _structureVector
	if (rowType.equals(HydroBase_GUI_WIS.DIVERSION)) {
		index = __diversionNameSimpleJComboBox.getSelectedIndex();
	        structureView = (HydroBase_StructureView)
			__diversionVector.get(index);
		if (structureView == null) {
			return;
		}
		structureName = structureView.getStr_name();
		structureName = structureName.trim();
		mile = structureView.getStr_mile();
		if (DMIUtil.isMissing(mile) || HydroBase_Util.isMissing(mile)) {
			str_mile = "";
		}
		else {	
			str_mile = "" + mile;
		}
		__diversionRowLabelJTextField.setText(structureName);
       		__divStreamMileJTextField.setText(str_mile);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.RESERVOIR)) {
		index = __reservoirNameSimpleJComboBox.getSelectedIndex();
		structureView = __reservoirVector.get(index);
		if (structureView == null) {
			return;
		}
		structureName = structureView.getStr_name().trim();
		mile = structureView.getStr_mile();
		if (DMIUtil.isMissing(mile) || HydroBase_Util.isMissing(mile)) {
			str_mile = "";
		}
		else {	
			str_mile = "" + mile;
		}
		__reservoirRowLabelJTextField.setText(structureName);
		__resStreamMileJTextField.setText(str_mile);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.MIN_FLOW_REACH)) {
		index = __mfrWeightNameSimpleJComboBox.getSelectedIndex();
	        structureView = __mfrWeightVector.get(index);
		if (structureView == null) {
			return;
		}
		structureName = structureView.getStr_name().trim();
		mile = structureView.getStr_mile();
		if (DMIUtil.isMissing(mile) || HydroBase_Util.isMissing(mile)) {
			str_mile = "";
		}
		else {	
			str_mile = "" + mile;
		}
		__mfrWeightRowLabelJTextField.setText(structureName);
       		__mfrWeightStreamMileJTextField.setText(str_mile);
	}

	ready();
}

/**
Search the __wisFormat for the unique id which matches the unique id for the
wisMath object. the cell row /number is then set.
@param wisMath HydroBase_WISMath object.
@return the row number which the term refers to.
the row number as in the row in the wisformat vector
*/
private int getTermReference(HydroBase_WISMath wisMath) {
	int size = __wisFormatList.size();
	String uniqueID = wisMath.getUniqueID().trim();
	HydroBase_WISFormat wisFormat = null;

	for (int i = 1; i < size; i++) {
		wisFormat = (HydroBase_WISFormat)__wisFormatList.get(i);
		if (wisFormat.getIdentifier().trim().equals(uniqueID)) {
			return i;
		}
	}
	return DMIUtil.MISSING_INT;
}

/**
Return the WISFormat for the stream containing the row.
@param row row number.
@return the WISFormat for the stream containing the row, or null.
*/
private HydroBase_WISFormat getTribStreamFormat(int row) {
	HydroBase_WISFormat wisFormat;
	String rowType = null;
	
	for (int curRow = row; curRow >= 0; curRow--) {
		wisFormat = __wisFormatList.get(curRow + 1);
		rowType = wisFormat.getRow_type().trim();
		if (rowType.equals(HydroBase_GUI_WIS.STREAM)) {
			return wisFormat;
		}
	}
	return null;
}

/**
Return the unique stream number for the stream which this row resides within.
@param row row number.
@return the unique stream number for the row by locating the first occurance of
a stream row type via decrementing row numbers.  Returns DMIUtil.MISSING_INT,
if a stream row type is not located.
*/
private int getTribStreamNum(int row) {
	HydroBase_WISFormat wisFormat = null;
	String rowType = null;
	
	for (int curRow = row; curRow >= 0; curRow--) {
		wisFormat = __wisFormatList.get(curRow + 1);
		rowType = wisFormat.getRow_type().trim();
		if (rowType.equals(HydroBase_GUI_WIS.STREAM)) {
			return wisFormat.getWdwater_num();
		}
	}
	return DMIUtil.MISSING_INT;
}

/**
Returns the WIS format list.
@return the WIS format list.
*/
public List<HydroBase_WISFormat> getWisFormatVector() {
	return __wisFormatList;
}

/**
Returns the WIS number.
@return the WIS number.
*/
public int getWis_num() {
	return __wisNum;
}

/**
Check the __wisFormat object to ensure that a duplicate identifier is not 
present elsewhere in the stream network.
@param row row number.
@param identifier unique row identifier(e.g., wdid:510501).
@return - true if a duplicate row identifier is located or one is not specified, false otherwise.
*/
private boolean isDuplicate(int row, String identifier) {
	// ensure that a unique id exist for the row
	if (identifier.equals("")) {
		new ResponseJDialog(this, 
			"Enter a Unique ID",
			"Must specify a unique id for this row.",
			ResponseJDialog.OK);
		return true;
	}

	int size = __wisFormatList.size(); 
	boolean isDuplicate = false;

	if (size > 1) {
		HydroBase_WISFormat wisFormat = null;
		for (int curRow = 1; curRow < size; curRow++) {
			if (curRow != (row + 1)) {
				wisFormat = __wisFormatList.get(curRow);
				if (wisFormat.getIdentifier().trim().equals(
					identifier)) {
					isDuplicate = true;
					return(true);
				}
			}
			else {
				wisFormat = __wisFormatList.get(curRow);
			}			
		}
	}
        return isDuplicate;
}

/**
Determine whether the current cell is a formula cell.
@param row row number.
@param col column number.
@return true if the current Cell contains a formula, false otherwise.
*/
private boolean isFormulaCell(int row, int col) {
	HydroBase_WISFormat wisFormat = __wisFormatList.get(row + 1);
	HydroBase_WISFormula wisFormula = wisFormat.getWISFormula(col);

	// a formula is present if getFormulastring()does not
	// return an empty String.
	if (!wisFormula.getFormulastring().trim().equals("")) {
		return true;
	}
	return false;
}

/**
Determines whether the current cell is an import cell.
@param row row number.
@param col column number.
@return true if the current cell contains an import, false otherwise.
*/
private boolean isImportCell(int row, int col) {
	HydroBase_WISImport wisImport = __wisFormatList.get(row + 1).getWISImport(col);
	String import_method = wisImport.getImport_method().trim();

	// a non-specified import method indicates that the 
	// cell is not an import cell
	if (import_method.equals("")) {
		return false;
	}
	else {	
		return true;
	}
}

/**
Return a String representation of the JCheckBox state (i.e., "Y" or "N").
@param c JCheckBox object.
@return "Y" if the JCheckBox state is true, "N" otherwise.
*/
private String isKnownPoint(JCheckBox c) {
	if (c.isSelected()) {
		return "Y";
	}
	else {	
		return "N";
	}
}

/**
Determine if the current cell can accept a formula and enable
__addFormulaJButton and __deleteFormulaJButton if a formula can be entered into
the selected cell.
@return true if the current Cell can accept a formula, false otherwise.
*/
private boolean isValidFormulaCell() {
	Color c = getCurrentCellColor();
	if (c == null) {
		__addFormulaJButton.setText(__BUTTON_ADD_FORMULA);
		setButtonState(__FORMULA, true);
		return true;
	}
	else if (c == Color.white) {
		__addFormulaJButton.setText(__BUTTON_ADD_FORMULA);
		setButtonState(__FORMULA, true);
		return true;
	}
	else if (c == Color.blue) {
		__addFormulaJButton.setText(__BUTTON_ADD_FORMULA);
		setButtonState(__FORMULA, true);
		return true;
	}
	else if (c == Color.red) {
		__addFormulaJButton.setText(__BUTTON_EDIT_FORMULA);
		setButtonState(__FORMULA, true);
		setButtonState(__IMPORT, false);
		return true;
	}
	else {	
		__addFormulaJButton.setText(__BUTTON_ADD_FORMULA);
		setButtonState(__FORMULA, false);
		return true;
	}
}

/**
Determines if the formula term is valid.  Invalid terms are as follows:
</ol>
<li>	refer to other formula cells</li>
<li>	known point flow terms must refer to other known point flow cells which
	are formulas.</li>
<li>	confluence formulas for trib natural and trib delivery must refer to
	natural and delivery flow respectively.</li>
@param termRow	term row number.
@param termCol	term column number.
@param editRow	row number where expression is being added.
@param editCol	column number where expression is being added.
@return true if valid, false otherwise.
*/
private boolean isValidFormulaTerm(int termRow, int termCol, int editRow, 
int editCol) {
	HydroBase_WISFormat termFormat = __wisFormatList.get(termRow + 1);
	HydroBase_WISFormat wisFormat = __wisFormatList.get(editRow + 1);
	boolean isKnownPoint = HydroBase_WISFormat.isKnownPoint( wisFormat.getKnown_point().trim());
	String editRowType = wisFormat.getRow_type().trim();

	// cannot reference another formula
	if (isFormulaCell(termRow, termCol)) {
		new ResponseJDialog(this, 
			"Invalid Formula",
			"Formulas cannot contain terms which"
			+ " reference other formula cells.",ResponseJDialog.OK);
		return false;
	}
	// known point expressions may only reference other known point cells 
	// which do not contain formulas
	else if (isKnownPoint && editCol == HydroBase_GUI_WIS.POINT_FLOW_COL) {
		boolean termKnownPoint = HydroBase_WISFormat.isKnownPoint(
			termFormat.getKnown_point().trim());
	
		// known point formulas must reference other known point cells
		if (termCol != HydroBase_GUI_WIS.POINT_FLOW_COL || !termKnownPoint) {
			new ResponseJDialog(this, 
				"Invalid Formula",
				"Point flow terms must "
				+ "reference a known point flow cell.", 
				ResponseJDialog.OK);
			return false;
		}		
		
	}
	// confluence formulas for trib natural and trib delivery must refer 
	// to natural and delivery flow respectively
	else if (editRowType.equals(HydroBase_GUI_WIS.CONFLUENCE)) {
		if (editCol == HydroBase_GUI_WIS.TRIB_NATURAL_COL) {
			if (termCol != HydroBase_GUI_WIS.NATURAL_FLOW_COL) {
				new ResponseJDialog(this, 
					"Invalid Formula",
					"Trib. Natural must"
					+ " reference a natural flow column.",
					ResponseJDialog.OK);
				return false;	
			}
		}
		else if (editCol == HydroBase_GUI_WIS.TRIB_DELIVERY_COL) {
			if (termCol != HydroBase_GUI_WIS.DELIVERY_FLOW_COL) {
				new ResponseJDialog(this, 
					"Invalid Formula",
					"Trib. Delivery must"
					+ " reference a delivery flow column.",
					ResponseJDialog.OK);
				return false;
			}
		}
	
	}

	// ...otherwise the term is valid
	return true;
}

/**
Determine if the current cell can accept an import and enable __addImportJButton
and __deleteImportJButton if an import can be entered into the selected cell.
@return true if the current Cell can accept an import, false otherwise.
*/
private boolean isValidImportCell() {
	Color c = getCurrentCellColor();
	if (c == null) {
		__addImportJButton.setText(__BUTTON_ADD_IMPORT);
		setButtonState(__IMPORT, true);
		return true;
	}
	else if (c == Color.white) {
		__addImportJButton.setText(__BUTTON_ADD_IMPORT);
		setButtonState(__IMPORT, true);
		return true;
	}
	else if (c == Color.blue) {
		__addImportJButton.setText(__BUTTON_EDIT_IMPORT);
		setButtonState(__IMPORT, true);
		setButtonState(__FORMULA, false);
		return true;
	}
	else if (c == Color.red) {
		__addImportJButton.setText(__BUTTON_ADD_IMPORT);
		setButtonState(__IMPORT, true);
		return true;
	}
	else {	
		__addImportJButton.setText(__BUTTON_ADD_IMPORT);
		setButtonState(__IMPORT, false);
		return true;
	}
}

/**
Responds to itemStateChanged events.
@param event the ItemEvent that happened.
*/
public void itemStateChanged(ItemEvent event) {
	// if the GUI is being initialized or if a row is being built for
	// display or if explicitly told not to, then don't worry about the
	// event that happened.
	if (!__initialized || __settingUpRow || __ignoreStateChange) {	
		return;
	}

	Object o = event.getItemSelectable();

	// ignore deselection events -- except for JCheckBoxes.
	if (event.getStateChange() != ItemEvent.SELECTED
		&& o != __divKnownPointJCheckBox
		&& o != __staKnownPointJCheckBox
		&& o != __resKnownPointJCheckBox
		&& o != __mfrWeightKnownPointJCheckBox
		&& o != __otherKnownPointJCheckBox) {
		return;
	}
		
	String routine = CLASS + ".itemStateChanged";
		
        if (__rowTypeSimpleJComboBox == o) {
		rowTypeSimpleJComboBoxClicked();
        }
	else if ((o == __structStreamJRadioButton)
		|| (o == __resStreamJRadioButton)
		|| (o == __mfrWeightStreamJRadioButton)) {
		displayStructuresClicked(__STREAM);
	}
	else if ((o == __structWDJRadioButton)
		|| (o == __resWDJRadioButton)
		|| (o == __mfrWeightWDJRadioButton)) {
		displayStructuresClicked(__ALL);
	}

	// Add event handling for the confluence checkbox items
	else if (o == __conStreamJRadioButton) {
		displayConfluenceList(__STREAM);
	}
	else if (o == __conWDJRadioButton) {
		displayConfluenceList(__ALL);
	}
        else if (__waterDistrictSimpleJComboBox == o) {
		__waterDistrictSelection = true;
                displayStructureList(__STREAM);
		displayConfluenceList(__STREAM);		
		displayStationList();
		displayStreamList();
		//displayRow();	// This forces a reselect of the choice...
		__waterDistrictSelection = false;
        }
        else if (__divKnownPointJCheckBox == o) {
		setDirty(true);
		baseflowClicked(__divKnownPointJCheckBox);
        }
        else if (__staKnownPointJCheckBox == o) {
		setDirty(true);
		baseflowClicked(__staKnownPointJCheckBox);
        }
        else if (__resKnownPointJCheckBox == o) {
		setDirty(true);
		baseflowClicked(__resKnownPointJCheckBox);
        }
        else if (__mfrWeightKnownPointJCheckBox == o) {
		setDirty(true);
		baseflowClicked(__mfrWeightKnownPointJCheckBox);
        }
        else if (__otherKnownPointJCheckBox == o) {
		setDirty(true);
		baseflowClicked(__otherKnownPointJCheckBox);
        }
        else if (__diversionNameSimpleJComboBox == o) {
                getStructureInfo();
		setUniqueID();
		setRowLabel(__curCellRow, 
			formatRowLabel(__curCellRow,((SimpleJComboBox)o)
			.getSelected()));
		refreshNetwork();
		notifyDiagramGUI();
        }
        else if (__reservoirNameSimpleJComboBox == o) {
                getStructureInfo();
		setUniqueID();
		setRowLabel(__curCellRow, 
			formatRowLabel(__curCellRow,((SimpleJComboBox)o)
			.getSelected()));
		refreshNetwork();
		notifyDiagramGUI();
        }
        else if (__mfrWeightNameSimpleJComboBox == o) {
                getStructureInfo();
		setUniqueID();
		setRowLabel(__curCellRow, 
			formatRowLabel(__curCellRow,((SimpleJComboBox)o)
			.getSelected()));
		refreshNetwork();
		notifyDiagramGUI();
        }
        else if (__stationNameSimpleJComboBox == o) {
		if (__waterDistrictSelection) {
			// the above can cause item state changes for the stream
			// name combo box -- and they should be ignored.  Just
			// the combo box values and make the user choose 
			// something ...
			return;
		}
	
		getStationInfo();
		setUniqueID();
		setRowLabel(__curCellRow, 
			formatRowLabel(__curCellRow,((SimpleJComboBox)o)
			.getSelected()));
		refreshNetwork();
		notifyDiagramGUI();
        }
        else if (__streamNameSimpleJComboBox == o) { 
		if (__waterDistrictSelection) {
			// the above can cause item state changes for the stream
			// name combo box -- and they should be ignored.  Just
			// the combo box values and make the user choose 
			// something ...
			return;
		}
		if (displayStreamInfo()) {
			setUniqueID();
			setRowLabel(__curCellRow, 
				formatRowLabel(__curCellRow,((SimpleJComboBox)o)
				.getSelected()));
			refreshNetwork();
		notifyDiagramGUI();
		}
        }
	else if (__confluenceNameSimpleJComboBox == o) {
		if (__waterDistrictSelection) {
			// the above can cause item state changes for the stream
			// name combo box -- and they should be ignored.  Just
			// the combo box values and make the user choose 
			// something ...
			return;
		}
		
		getConfluenceInfo();
		setUniqueID();
		setRowLabel(__curCellRow, formatRowLabel(__curCellRow,((SimpleJComboBox)o).getSelected()));
		refreshNetwork();
		notifyDiagramGUI();
	}

	else if (__gainSimpleJComboBox == o) {
		String gain = ((SimpleJComboBox)o).getSelected().trim();
		if (gain.equals(HydroBase_GUI_WIS.STREAM_MILE) || gain.equals(HydroBase_GUI_WIS.WEIGHTS)) {
			displayGains(true);
			__displayGainsJButton.setEnabled(true);
		}
		else if (gain.equals(HydroBase_GUI_WIS.NONE)) {
			displayGains(false);
			__displayGainsJButton.setEnabled(false);
		}
	}
	else {
		Message.printWarning(1, routine, "Unrecognized command.");
	}

	refreshWorksheet();
}

/**
Handles key pressed events -- if enter is pressed in certain text fields then
the text data is saved.
@param event KeyEvent object.
*/
public void keyPressed(KeyEvent event) {
	setDirty(true);
	int code = event.getKeyCode();

	if (event.getSource() == __worksheet) {
		if (code == KeyEvent.VK_KP_DOWN || code == KeyEvent.VK_KP_UP
		    || code == KeyEvent.VK_KP_LEFT || code == KeyEvent.VK_KP_RIGHT
		    || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_UP
		    || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT) {
			__keyCell = new int[2];

			if (code == KeyEvent.VK_KP_DOWN || code == KeyEvent.VK_DOWN) {
				if (__curCellRow == __worksheet.getRowCount()) {
					__keyCell = null;
					return;
				}
				__keyCell[0] = __curCellRow + 1;
				__keyCell[1] = __curCellCol - 1;
			}
			else if (code == KeyEvent.VK_KP_UP || code == KeyEvent.VK_UP){
				if (__curCellRow == 0) {
					__keyCell = null;
					return;
				}
				__keyCell[0] = __curCellRow - 1;
				__keyCell[1] = __curCellCol - 1;
			}
			else if (code == KeyEvent.VK_KP_LEFT || code == KeyEvent.VK_LEFT) {
			    	if (__curCellCol == 1) {
					__keyCell = null;
					return;
				}
				__keyCell[0] = __curCellRow;
				__keyCell[1] = __curCellCol - 2;
			}
			else if (code == KeyEvent.VK_KP_RIGHT || code == KeyEvent.VK_RIGHT) {
			    	if (__curCellCol == __worksheet.getColumnCount() + 1) {
				    	__keyCell = null;
					return;
				}
				__keyCell[0] = __curCellRow;
				__keyCell[1] = __curCellCol;
			}

		    	worksheetCellChanged(false, null);
		}
		return;
	}
	
	if (code == KeyEvent.VK_ENTER) {
		if (__isConstantShown) {
			addConstantClicked();
		}
		else {	
			updateRowFormat(__curCellRow);
			refreshWorksheet();
		}
	}
	else if (code == KeyEvent.VK_F1) {
	}
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
Load the formula into the list box for the specified row and column.
@param row row number.
@param col column number.
*/
private void loadCellFormula(int row, int col) {
	String routine = CLASS + ".loadCellFormula";
        __formulaList.removeAll();
	__formulaVector.clear();

	isValidFormulaCell();
	__rowTypeCard.show(__rowTypeJPanel, "formula");       
	setFormulaLabel(row, col);
	__addFormulaJButton.setText(__BUTTON_EDIT_FORMULA);
	
	Message.printStatus(2, routine, "Loading formula for row " + row + " col " + col);
	
	// get the formula for the selected row and column and populate
	// Vectors with both types of formula formats.
	HydroBase_WISFormat wisFormat = __wisFormatList.get(row + 1);
	HydroBase_WISFormula wisFormula = wisFormat.getWISFormula(col);
	
        String formula = wisFormula.getFormulastring().trim();
        List<HydroBase_WISMath> formulaAsJLabel = HydroBase_WISMath.parseFormula(formula, 
		HydroBase_WISMath.LABEL);
		
        formula = wisFormula.getFormula().trim();
        List<HydroBase_WISMath> formulaAsUnique = HydroBase_WISMath.parseFormula(formula, 
		HydroBase_WISMath.UNIQUE_ID);

        // add the items to the __formulaList and __formulaVector objects
	boolean isConstant = false;
	HydroBase_WISMath wisMathAsJLabel = null;
	HydroBase_WISMath wisMathAsUnique = null;
        int size = formulaAsJLabel.size();
	
        for (int i = 0; i < size; i++) {
		wisMathAsJLabel = formulaAsJLabel.get(i);
		wisMathAsUnique = formulaAsUnique.get(i);
		wisMathAsJLabel.setUniqueID(wisMathAsUnique.getUniqueID());
		wisMathAsJLabel.setIsConstant(wisMathAsUnique.isConstantTerm());
		wisMathAsJLabel.setColumnType(wisMathAsUnique.getColumnType());

		isConstant = wisMathAsUnique.isConstantTerm();
		wisMathAsJLabel.setIsConstant(isConstant);
		if (isConstant) {
			wisMathAsJLabel.setConstant( wisMathAsUnique.getConstant());
                	__formulaList.add(HydroBase_WISMath.getTerm( wisMathAsJLabel,
			HydroBase_WISMath.CONSTANT));
		}
		else {	
			wisMathAsJLabel.setLabel(wisMathAsJLabel.getLabel());
                	__formulaList.add(HydroBase_WISMath.getTerm( wisMathAsJLabel, HydroBase_WISMath.LABEL));
		}

		// update the __formulaVector
		__formulaVector.add(wisMathAsJLabel);
        }
}

/**
Does nothing.
*/
public void mouseClicked(MouseEvent event) {}

/**
Does nothing.
*/
public void mouseEntered(MouseEvent event) {}

/**
Updates the row format when the mouse is moved outside of most text fields.
*/
public void mouseExited(MouseEvent event) {
	Object o = event.getSource();

	if (	o == __cflWeightJTextField
		|| o == __confluenceRowLabelJTextField
		|| o == __confluenceStreamMileJTextField
		|| o == __diversionRowLabelJTextField
		|| o == __divStreamMileJTextField
		|| o == __divWeightJTextField
		|| o == __mfrWeightRowLabelJTextField
		|| o == __mfrWeightStreamMileJTextField
		|| o == __mfrWeightJTextField		
		|| o == __otherRowLabelJTextField
		|| o == __otherStreamMileJTextField
		|| o == __otherWeightJTextField
		|| o == __reservoirRowLabelJTextField
		|| o == __resStreamMileJTextField
		|| o == __resWeightJTextField
		|| o == __staStreamMileJTextField
		|| o == __stationRowLabelJTextField
		|| o == __staWeightJTextField
		|| o == __stringRowLabelJTextField
		|| o == __streamRowLabelJTextField) {
		updateRowFormat(__curCellRow);
	}

	if (	o == __cflWeightJTextField
		|| o == __divWeightJTextField
		|| o == __mfrWeightRowLabelJTextField
		|| o == __mfrWeightStreamMileJTextField
		|| o == __mfrWeightJTextField		
		|| o == __otherWeightJTextField
		|| o == __resWeightJTextField
		|| o == __staWeightJTextField) {
			String gain = __gainSimpleJComboBox.getSelected();
			if (gain.equals(HydroBase_GUI_WIS.STREAM_MILE) || gain.equals(HydroBase_GUI_WIS.WEIGHTS)) {
				displayGains(true);
			}
			else if (gain.equals(HydroBase_GUI_WIS.NONE)) {
				displayGains(false);
			}		
	}
}

/**
Respond to the mouse Pressed event.  Displays a popup menu for cell properties,
selects cells when not building formulas, and chooses the cells to use in 
formulas if in formula-building mode.
@param event MouseEvent object.
*/
public void mousePressed(MouseEvent event) {
	int mods = event.getModifiers(); 
	Component c = event.getComponent();
	
	// show the cell popup menu if right-clicking on the worksheet
	if (c.equals(__worksheet) && ((mods & MouseEvent.BUTTON3_MASK)!= 0)) {
		__cellJPopupMenu.show(c, event.getX(), event.getY());
	}
	else if (c.equals(__worksheet)) {
		if (__isBuildingFormula) {
			int[] cell = __worksheet.getCellAtClick(event);
			buildExpression(cell[0], __worksheet.getAbsoluteColumn(cell[1]));
			selectCell(__curCellRow, __curCellCol);
		}
		else {
			worksheetCellChanged(true, event);
		}
	}
}

/**
Does nothing.
*/
public void mouseReleased(MouseEvent event) {}

/**
Called when the network GUI closes.  Sets the gui object to null.
*/
protected void networkGUIClosed() {
	__diagramGUI = null;
}

/**
Notifies the network GUI (if it is open) of changes made to the WIS.
*/
private void notifyDiagramGUI() {
	if (__diagramGUI != null) {
		if (!updateRowFormat(__curCellRow)) {
			return;
		}	
		__network = null;
		System.gc();
		if (__wisFormatList.size() > 2) {
			__network = new HydroBase_NodeNetwork();
			__network.setInWIS(true);
			__network.treatDryNodesAsBaseflow(true);
			__network.readWISFormatNetwork(__wisFormatList, 1);
		}
		else {
			// create an empty network to avoid null pointers
			__network = new HydroBase_NodeNetwork();
			__network.setInWIS(true);
		}	
		__diagramGUI.rereadDiagram();
	}
}

/**
Load the sheet_name for the __wisNum.
*/
private void querySheetName() {
	String routine = CLASS + ".querySheetName";

	__sheetName = "";

	Date date = null;

	JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Please Wait...Retrieving Sheet Name Data");

        List<HydroBase_WISSheetName> results = null;
	try {
		results = __dmi.readWISSheetNameList(-999, __wisNum, null,null, true);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading from sheet_name table.");
		Message.printWarning(2, routine, e);
	}
	
	if (results != null && !results.isEmpty()) {
               	HydroBase_WISSheetName wisData = results.get(0);
		__sheetName = wisData.getSheet_name();
		date = wisData.getEffective_date();

		String gain = wisData.getGain_method();		
		if (gain.equals("N")) {
			__gainSimpleJComboBox.select(HydroBase_GUI_WIS.NONE);
			__displayGainsJButton.setEnabled(false);
		}
		else if (gain.equals("S")) {
			__gainSimpleJComboBox.select( HydroBase_GUI_WIS.STREAM_MILE);
			__displayGainsJButton.setEnabled(true);
		}
		else if (gain.equals("W")) {
			__gainSimpleJComboBox.select(HydroBase_GUI_WIS.WEIGHTS);
			__displayGainsJButton.setEnabled(true);
		}		
        }	

	__sheetNameJTextField.setText(__sheetName);
	DateTime tsDate = new DateTime(date);
	__dateJTextField.setText(tsDate.toString(DateTime.FORMAT_Y2K_SHORT));

  	ready();
}

/**
Load the WIS_format for the entire grid using __wisNum.
*/
private void queryWISFormat() {
	String routine = CLASS + ".loadWISFormat";

	__wisFormatList.clear();
        __wisFormatList.add(new HydroBase_WISFormat()); 
	__isNewSheet = true;

	// begin query
	JGUIUtil.setWaitCursor(this, true);
        String tempString = "Please Wait...Retrieving WIS Format"; 
        __statusJTextField.setText(tempString);
        Message.printStatus(1, routine, tempString);

        List<HydroBase_WISFormat> results = null;
	try {
		results = __dmi.readWISFormatList(__wisNum, null, null);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading from wis_format table.");
		Message.printWarning(2, routine, e);
	}
	
	HydroBase_WISFormat formatData = null;
	int row = 0;
	String rowLabel = null;
	String rowType;
	HydroBase_WISData data = null;
	if (results != null && !(results.isEmpty())) {
	        int size = results.size();
        	for (int i = 0; i < size; i++) { 
                	formatData = results.get(i);
			__wisFormatList.add(formatData);

			// Pad all columns for the WIS with spaces...
			data = new HydroBase_WISData();
			// Now set the row label field...
			rowType = formatData.getRow_type();
			rowLabel = formatData.getRow_label();
			if (rowLabel == null) {
				rowLabel = "";
			}
			// Append additional row label information so it is
			// easier to edit the sheet.  This information is used
			// only in the display an will not be saved back to the
			// database.  Always want the row type and internal identifier...
			rowLabel += " ("  + (i + 1) + ", " + rowType;
			// Now do additional information...?
			//STRING		= "String",
			//	DIVERSION	= "Diversion",
			//	RESERVOIR	= "Reservoir",
			//	MIN_FLOW_REACH	= "MinFlow",
			//	STATION		= "Station",
			//	CONFLUENCE	= "Confluence",
			//	OTHER		= "Other",
			//	STREAM		= "Stream";
			//if (rowType.equals(HydroBase_GUI_WIS.DIVERSION)) {
			//	...
			//}
			if (!formatData.getIdentifier().trim().equals("")) {
				rowLabel += ", " + formatData.getIdentifier();
			}
			rowLabel += ")";
			data.setRow_label(rowLabel);
			row = __worksheet.getRowCount();
			__worksheet.addRow(data);
			if (formatData.getRow_type().equals( HydroBase_GUI_WIS.STRING)) {
				setCellColor(row, 2, HydroBase_GUI_WIS.NUM_COLUMNS, Color.white, Color.white);
			}
			else {	
				setCellColor(row, 2, 5, Color.lightGray, Color.black);
			}
			if (formatData.getKnown_point().equals("Y")) {
				setCellColor(row, 2, 2, Color.white, Color.white);
			}
			__isNewSheet = false;
		}
        }	
	
	refreshNetwork();
	notifyDiagramGUI();

  	ready();                       
}

/**
Load the WIS_formula for the entire grid using __wisNum.
*/
private void queryWISFormula() {
	String routine = CLASS + ".queryWISFormula";

	JGUIUtil.setWaitCursor(this, true);
	
	String tempString = "Please Wait...Retrieving WIS Formulas"; 
        __statusJTextField.setText(tempString);
        Message.printStatus(1, routine, tempString);
	
        List<HydroBase_WISFormula> results = null;
	try {
		results = __dmi.readWISFormulaListForWis_num(__wisNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading from wis_formula table.");
		Message.printWarning(2, routine, e);
	}

	HydroBase_WISFormat wisFormat = null;
	HydroBase_WISFormula formulaData = null;
	int columnNum = -1;
	int row = -1;
	String columnType = null;
	if (results != null && !(results.isEmpty())) {
	        int size = results.size();
        	for (int i = 0; i < size; i++) { 
                	formulaData = results.get(i);
			row = formulaData.getWis_row();
			columnType = formulaData.getColumn().trim();
			columnNum = HydroBase_GUI_WIS.getColumnNumber( columnType);
			setCellColor(row - 1, columnNum, columnNum, Color.red, Color.red);

			// set the formula on the __wisFormat object
			wisFormat= __wisFormatList.get(row);
			wisFormat.setWISFormula(formulaData, columnNum);
			__wisFormatList.set(row,wisFormat);
		}
        }	

	ready();                        
}

/**
Load the WIS_import for the entire grid using __wisNum.
*/
private void queryWISImport() {
	String routine = CLASS + ".queryWISImport";

	JGUIUtil.setWaitCursor(this, true);
     	__statusJTextField.setText("Please Wait...Retrieving WIS Imports");
	
     	List<HydroBase_WISImport> results = null;
	try {
		results = __dmi.readWISImportListForWis_num(__wisNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading from wis_import table.");
		Message.printWarning(2, routine, e);
	}

	HydroBase_WISFormat wisFormat = null;
	HydroBase_WISImport wisImport = null;
	int columnNum = -1;
	int row = -1;
	String columnType = null;
	if (results != null && !(results.isEmpty())) {
	        int size = results.size();
        	for (int i = 0; i < size; i++) { 
                	wisImport = results.get(i);
			row = wisImport.getWis_row();
			columnType = wisImport.getColumn().trim();
			columnNum = HydroBase_GUI_WIS.getColumnNumber( columnType);
			setCellColor(row - 1, columnNum, columnNum, Color.blue, Color.blue);

			// set the wis import on the __wisFormat object
			wisFormat = __wisFormatList.get(row);
			wisFormat.setWISImport(wisImport, columnNum);
			__wisFormatList.set(row,wisFormat);
		}
        }	

	ready();
}

/**
Set the GUI ready for user interaction.
*/
private void ready() {
	JGUIUtil.setWaitCursor(this, false);
	setButtonsState(true);
	__statusJTextField.setText("Ready");
}

/**
Refresh the __network by regenerating the HydroBase_NodeNetwork.  This should 
be done at initial load and when rows are added or deleted.  There is 
a performance penalty but at least the network will be current.
*/
private void refreshNetwork() {
	__network = null;
	System.gc();
	if (__wisFormatList.size() > 2) {
		__network = new HydroBase_NodeNetwork();
		__network.setInWIS(true);
		__network.treatDryNodesAsBaseflow(true);
		__network.readWISFormatNetwork(__wisFormatList, 1);
	}
	else {
		// create an empty network to avoid null pointers
		__network = new HydroBase_NodeNetwork();
		__network.setInWIS(true);
	}
}

/**
Refreshes the worksheet and makes sure the cell that was selected prior to
refresh is selected again.
*/
private void refreshWorksheet() {
	__worksheet.refresh();
	if ((__curCellRow > (__worksheet.getRowCount() - 1)) || __curCellCol < 0) {
		return;
	}

	if (__keyCell != null) {
		// This occurs when the user has used an arrow key to navigate
		// around the worksheet.  For some reason, the selectCell()
		// call was resulting in odd behavior in THIS instance.
		// __keyCell is ONLY not null when an arrow key has been
		// pressed, and thus if it is not null then the program is 
		// currently in the state that would result in a bad refresh
		// and so the selectCell() is ignored.
	}
	else {
		__worksheet.selectCell(__curCellRow, __worksheet.getVisibleColumn(__curCellCol));
	}
}

/**
Resets all the GUI's JComboBoxes so that the very first item in them is selected.
*/
private void resetComboBoxes() {
	__diversionNameSimpleJComboBox.select(null);
	__reservoirNameSimpleJComboBox.select(null);
	__mfrWeightNameSimpleJComboBox.select(null);
	__stationNameSimpleJComboBox.select(null);
	__confluenceNameSimpleJComboBox.select(null);
	__streamNameSimpleJComboBox.select(null);
	__ignoreStateChange = true;
	__diversionNameSimpleJComboBox.select(0);
	__reservoirNameSimpleJComboBox.select(0);
	__mfrWeightNameSimpleJComboBox.select(0);
	__stationNameSimpleJComboBox.select(0);
	__confluenceNameSimpleJComboBox.select(0);
	__streamNameSimpleJComboBox.select(0);
	__ignoreStateChange = false;
}

/**
Respond when the __rowTypeSimpleJComboBox is selected.
*/
private void rowTypeSimpleJComboBoxClicked() {
	int row = getSelectedRow();
	String type = __rowTypeSimpleJComboBox.getSelected();
	if (type != null) {
		type = type.trim();
	}
	else {
		return;
	}
	
        __formulaList.removeAll();
	__formulaVector.clear();

	if (row == 0) {
		if (!type.equals(HydroBase_GUI_WIS.STREAM)) {
			new ResponseJDialog(this,
				"First Row Must Be Stream",
				"The first row must be a stream"
				+ " row type.", ResponseJDialog.OK).response();
			__rowTypeSimpleJComboBox.select( HydroBase_GUI_WIS.STREAM);
			return;
		}
	}

	// determine if a formula exist in the current row. 
	// if so, issue a warning before deleting.
	for (int col = 1; col < HydroBase_GUI_WIS.NUM_COLUMNS; col++) {
		if (isFormulaCell(row, col)) {
			int r = new ResponseJDialog(this, 
				"Warning!",
				"Changing the"
				+ " row type will delete formulas. Continue?", 
				ResponseJDialog.YES | ResponseJDialog.NO).response();
			if (r == ResponseJDialog.YES) {
				break;
			}
			else {
				return;
			}
		}
	}

	setDirty(true);
	
	// clear out the existing formula via instantiating
	// a new HydroBase_WISFormula object at the appropriate location
	HydroBase_WISFormat wisFormat = __wisFormatList.get(row + 1);	
	for (int col = 1; col < HydroBase_GUI_WIS.NUM_COLUMNS; col++) {
		wisFormat.setWISFormula(new HydroBase_WISFormula(), col);
	}
	wisFormat.setRow_type(type);
	__wisFormatList.set(row + 1,wisFormat );

	// set the cell colors accordingly and show the requested panel
	setCellColor(row, 2, 5, Color.lightGray, Color.black);
	if (type.equals(HydroBase_GUI_WIS.STRING)) {
		setCellColor(row, 2, HydroBase_GUI_WIS.NUM_COLUMNS, Color.white, Color.white);
	}
	else {	
		setCellColor(row, 2, 5, Color.lightGray, Color.black);
		setCellColor(row, 6, HydroBase_GUI_WIS.NUM_COLUMNS, Color.white, Color.white);
	}

	// generate the unique id for the following row types.
	if (type.equals(HydroBase_GUI_WIS.STRING) || type.equals(HydroBase_GUI_WIS.OTHER)) {
		setUniqueID();
	}

	// active the formula and edit buttons according to the selected cell
	isValidFormulaCell();
	isValidImportCell();

	// set the default weight value
	__divWeightJTextField.setText("" + __DEFAULT_WEIGHT);
	__resWeightJTextField.setText("" + __DEFAULT_WEIGHT);
	__mfrWeightJTextField.setText("" + __DEFAULT_WEIGHT);
	__staWeightJTextField.setText("" + __DEFAULT_WEIGHT);
	__cflWeightJTextField.setText("" + __DEFAULT_WEIGHT);
	__otherWeightJTextField.setText("" + __DEFAULT_WEIGHT);

	// default the baseflow to checked for station
	__ignoreStateChange = true;
	__staKnownPointJCheckBox.setSelected(true);
	__ignoreStateChange = false;

	showJPanel();

	refreshNetwork();
	notifyDiagramGUI();
}

/**
Sets the currently-selected cell, selects it on the worksheet,  and 
refreshes the worksheet.
@param row the row to select.
@param absoluteColumn the absoluteColumn to select.
*/
private void selectCell(int row, int absoluteCol) {
	__curCellRow = row;
	__curCellCol = absoluteCol;
	__worksheet.selectCell(row, __worksheet.getVisibleColumn(absoluteCol));
}

/**
Select the main stem according to the stream selected when specifying a new WIS.
@param stream HydroBase_Stream object.
*/
private void selectMainStem(HydroBase_Stream stream) {
	if (stream == null) {
		return;
	}

	long stream_num = stream.getStream_num();

	HydroBase_Stream data = null;
	if (__streamVector != null && !(__streamVector.isEmpty())) {
		int size = __streamVector.size();
		for (int i = 1; i < size; i++) {
			data = __streamVector.get(i);
			if (data.getStream_num() == stream_num) {
				String name = data.getStream_name();
				__streamNameSimpleJComboBox.select(null);
				__streamNameSimpleJComboBox.select(i);
  				__streamRowLabelJTextField.setText(name);
				setRowLabel(0, formatRowLabel(0, name));
				refreshWorksheet();
				setUniqueID();
				updateRowFormat(0);
				displayStructureList(__STREAM);
				displayConfluenceList(__STREAM);
				displayStationList();
				return;
			}
		}
	}
}

/**
Enables/disables the requested buttons.
@param flag __FORMULA if disabling formula related, __IMPORT if disabling import buttons.
@param state true if enabling the button, false otherwise.
*/
private void setButtonState(int flag, boolean state) {
	if (flag == __FORMULA) {
		__addFormulaJButton.setEnabled(state);
		__deleteFormulaJButton.setEnabled(state);
	}
	else if (flag == __IMPORT) {
		__addImportJButton.setEnabled(state);
		__deleteImportJButton.setEnabled(state);
	}
}

/**
Sets the states of all the buttons on the form to enabled or disabled.
@param state if true, set the buttons to enabled.  If false, disable them.
*/
private void setButtonsState(boolean state) {
	__addRowJButton.setEnabled(state);
       	__deleteRowJButton.setEnabled(state);
	__buildDiagramJButton.setEnabled(state);
	__showNetworkJButton.setEnabled(state);
	__addImportJButton.setEnabled(state);
	__deleteImportJButton.setEnabled(state);
	__addFormulaJButton.setEnabled(state);
	__deleteFormulaJButton.setEnabled(state);
}

/**
Sets the cell background and foreground colors for a range of columns in a row.
@param row the row for which to set cell colors.
@param fromAbsCol the absolute column at which to begin setting cell colors.
@param toAbsCol the last absolute column for which to set cell colors.
@param bgColor the Color to set the cell's background to.
@param fgColor the Color to set the cell's foreground to.
*/
private void setCellColor(int row, int fromAbsCol, int toAbsCol, Color bgColor,
Color fgColor) {
	if ((fromAbsCol < 0) || (toAbsCol < 0)) {
		return;
	}
	if (toAbsCol < fromAbsCol) {
		return;
	}

	JWorksheet_CellAttributes ca = new JWorksheet_CellAttributes();
	ca.backgroundColor = bgColor;
	ca.foregroundColor = fgColor;

	for (int i = fromAbsCol; i <= toAbsCol; i++) {
		__worksheet.setCellAttributes(row, i, ca);
	}
}

/**
Sets the contents of the cell at the specified row and column.
@param value the contents to set in the cell.
@param row the row for which to set the cell contents.
@param absoluteColumn the absolute column at which to set the cell contents.
*/
private void setCellContents(String value, int row, int absoluteColumn) {
	__worksheet.setValueAt(value, row, __worksheet.getVisibleColumn(absoluteColumn));
}

/**
Sets the currently-selected cell, but does not select it yet in the worksheet.
@param row the row to select.
@param absoluteColumn the absolute column number to select.
*/
private void setCurrentCell(int row, int absoluteColumn) {
	__curCellRow = row;
	__curCellCol = absoluteColumn;
}

/**
Sets the GUI dirty when changes are made, enabling the save button.
@param dirty whether the data is dirty (true) or not (false).
*/
public void setDirty(boolean dirty) {
	__dirty = dirty;
	if (__dirty) {
		if (__dmi.hasPermission(__wis.getWD())) {
			__saveJButton.setEnabled(true);
		}
		else {
			__saveJButton.setEnabled(false);
		}
	}
	else {
		__saveJButton.setEnabled(false);
	}
}

/**
Set the text for the __formulaJLabel object given the cell address.
@param row row number.
@param col column number.
*/
private void setFormulaLabel(int row, int col) {
	HydroBase_WISFormat wisFormat = __wisFormatList.get(row + 1);
	String rowLabel = wisFormat.getRow_label();
	String columnJLabel = getColumnType(col);
	__formulaJLabel.setText(__FORMULA_LABEL + "Formula for " + rowLabel + "." + columnJLabel);
}

/**
Set up the GUI defaults. These defaults may differ depending upon whether an
existing format or a new sheet is loaded.
*/
private void setGUIDefaults() {
	// Generate the water districts (WD.ActiveDivision.DistrictSelect
	// property) and initialize GUI settings...
	List<String> v = HydroBase_GUI_Util.generateWaterDistricts(__dmi);
	int size = v.size();
	int wd = 0;
	String name = null;
	String swd = null;
	List<String> v2 = new Vector<String>();
	int pos = -1;
	int last = -1;
	int iWisWD = __wis.getWD();
	boolean found = false;
	for (int i = 0; i < size; i++) {
		swd = v.get(i);
		wd = (Integer.decode(swd)).intValue();
		if (wd == iWisWD) {
			found = true;
		}
		if (iWisWD > last && iWisWD < wd) {
			pos = i;
		}
		last = wd;
	}
	
	if (pos > -1) {
		v.add(pos,"" + iWisWD);
	}
	else if (found == false) {
		v.add("" + iWisWD);
	}
	
	size = v.size();	
	for (int i = 0; i < size; i++) {
		swd = v.get(i);
		wd = (Integer.decode(swd)).intValue();
		try {
			name = __dmi.getWaterDistrictName(wd);
			v2.add("" + wd + " - " + name);
		}
		catch (Exception e) {
			v2.add("" + wd);
		}
	}
	__waterDistrictSimpleJComboBox.setData(v2);

	// default to the first item being selected (avoid null-pointer errors
	// later on ...)
	__ignoreStateChange = true;
	__waterDistrictSimpleJComboBox.select(0);
	__ignoreStateChange = false;

	// Select the water district for which this sheet is most closely
	// associated with. if not found, then default to the first item.
	String curWD = null;
	String wisWD = "" + __wis.getWD();
	size = __waterDistrictSimpleJComboBox.getItemCount();
	for (int counter = 0; counter < size; counter++) {
		curWD = HydroBase_WaterDistrict.parseWD( __waterDistrictSimpleJComboBox.getItem(counter));
		if (curWD.equals(wisWD)) {
			__ignoreStateChange = true;
			__waterDistrictSimpleJComboBox.select(counter);
			__ignoreStateChange = false;
		}
	}
	
	// No rows have been added, default to a String row type...
	if (__wisFormatList.size() < 2) {
		__ignoreStateChange = true;
		__rowTypeSimpleJComboBox.select(HydroBase_GUI_WIS.STRING);
		__ignoreStateChange = true;
	}
	// Select the topmost row and set the defaults according to this row...
	else {	
		HydroBase_WISFormat wisFormat = __wisFormatList.get(__curCellRow + 1);
		__ignoreStateChange = true;
		__rowTypeSimpleJComboBox.select(wisFormat.getRow_type());
		__ignoreStateChange = false;
		displayRow(__curCellRow);
		selectCell(__curCellRow, HydroBase_GUI_WIS.POINT_FLOW_COL);
	}
        showJPanel();
	setSize(750, 650);
}

/**
Sets the ID for the current row.
@param id the ID to set for the current row.
*/
private void setRowID(String id) {
	HydroBase_WISFormat wisFormat = __wisFormatList.get(__curCellRow + 1);
	wisFormat.setIdentifier(id);
	__wisFormatList.set(__curCellRow + 1,wisFormat );
}

/**
Sets the label of the specified row.
@param row the row for which to set the label.
@param label the two parts to use for setting the label.  The first element
is a String that will be the information actually stored in the data object
and displayed on the WIS screen, too.  The second element is the additional
information that is only shown on the WISBuilder screen.
*/
private void setRowLabel(int row, List<String> label) {
	String first = label.get(0);
	String rest = label.get(1);
	HydroBase_WISFormat wisFormat = __wisFormatList.get(row + 1);
	wisFormat.setRow_label(first);
	__wisFormatList.set(row+1,wisFormat);	
	__worksheet.setValueAt(first, row, 0);

	// bypass the worksheet and set the value directly into a non-existent
	// column in the table model
	__tableModel.setValueAt(rest, row, 99);

	refreshWorksheet();
}

/**
Set the unique identifier JTextField value, which is determined based on the 
row type as follows:
<pre>
	row type			id
	-----------		----------------
	Diversion		"wdid:" + WDID
	Minimum Flow Reach	"wdid:" + WDID
	Reservoir		"wdid:" + WDID
	Station			"stat:" + StationID
	Stream			"strm:" + wdwater_num + wdwater_link
				where wdwater_link is downstream stream
				If downstream stream is null, don't include
				(some old versions may have -999).
	Confluence		"conf:" + wdwater_num + wdwater_link
				where wdwater_num is larger stream and
				wdwater_link is tributary
	Other			"othr:" +(max + 1)
				where max = a counter for the sheet
	String			"othr:" +(max + 1)
				where max = a counter for the sheet
</pre>
wdwater_num is currently taken from stream.stream_num and
stream.str_trib_to is used for wdwater_link<p>
The WDWater table is NOT currently used!
*/
private void setUniqueID() {
	String routine = "HydroBase_GUI_WISBuilder.setUniqueID()";

	JGUIUtil.setWaitCursor(this, true);
	__statusJTextField.setText("Determining unique id...");

	HydroBase_Stream streamData = null;
	HydroBase_StructureView structView = null;
	int index = DMIUtil.MISSING_INT;
	String id = null;
	String type = __rowTypeSimpleJComboBox.getSelected().trim();

	if (type.equals(HydroBase_GUI_WIS.CONFLUENCE)) {
		index = __confluenceNameSimpleJComboBox.getSelectedIndex();
		streamData = __confluenceVector.get(index);
		if (streamData == null) {
			ready();
			// user needs to save a confluence type
			return;
		}
		id = __CONF + streamData.getStr_trib_to() + streamData.getStream_num();
	}
	else if (type.equals(HydroBase_GUI_WIS.DIVERSION)) {
		index = __diversionNameSimpleJComboBox.getSelectedIndex();
		structView = __diversionVector.get(index);
		if (structView == null) {
			ready();
			// user needs to save a diversion type
			return;
		}
		id = __WDID + HydroBase_WaterDistrict.formWDID( structView.getWD(), structView.getID());
	}
	else if (type.equals(HydroBase_GUI_WIS.MIN_FLOW_REACH)) {
		index = __mfrWeightNameSimpleJComboBox.getSelectedIndex();
		structView = __mfrWeightVector.get(index);
		if (structView == null) { 
			ready();
			// user needs to save a min flow type
			return;
		}
		id = __WDID + HydroBase_WaterDistrict.formWDID( structView.getWD(), structView.getID());
	}
	else if (type.equals(HydroBase_GUI_WIS.OTHER)) {
		id = __OTHR + (getMax() + 1);
	}
	else if (type.equals(HydroBase_GUI_WIS.RESERVOIR)) {
		index = __reservoirNameSimpleJComboBox.getSelectedIndex();
		structView = __reservoirVector.get(index);
		if (structView == null) {
			ready();
			// user needs to save a reservoir type
			return;
		}
		id = __WDID + HydroBase_WaterDistrict.formWDID( structView.getWD(), structView.getID());
	}
	else if (type.equals(HydroBase_GUI_WIS.STATION)) {
		index = __stationNameSimpleJComboBox.getSelectedIndex();
		HydroBase_StationView stationData = __stationVector.get(index);
		if (stationData == null) {
			ready();
			// user needs to save a station type
			return;
		}
		id = __STAT + stationData.getStation_id();		
	}
	else if (type.equals(HydroBase_GUI_WIS.STREAM)) {
		index = __streamNameSimpleJComboBox.getSelectedIndex();
		streamData = __streamVector.get(index);
		if (streamData == null) {
			ready();
			// user needs to save a stream type
			return;
		}
		if (DMIUtil.isMissing(streamData.getStr_trib_to()) || HydroBase_Util.isMissing(streamData.getStr_trib_to())) {
			// Only use one...
			id = __STRM + streamData.getStream_num();
		}
		else if (streamData.getStream_num() == streamData.getStr_trib_to()) {
			// Only use one...
			id = __STRM + streamData.getStream_num();
		}
		else {	
			id = __STRM + streamData.getStream_num() + streamData.getStr_trib_to();
		}
	}
	else if (type.equals(HydroBase_GUI_WIS.STRING)) {
		id = __OTHR + (getMax() + 1);
	}

	ready();

	int row = getSelectedRow();
	// check for row uniqueness
	if (isDuplicate(row, id)) {
		Message.setTopLevel(this);
		ready();
		Message.printWarning(1, routine, 
			id + " is not a unique row identifier."
			+ "\nCheck that this " + type + " has not been"
			+ " previously added.");
		return;
	}

	ready();
	__uniqueJTextField.setText(id);
	setRowID(id);
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	String routine = CLASS + ".setupGUI";

	addWindowListener(this);

	// definitions to be used in the GridBagLayouts
	Insets insetsNLNR = new Insets(0, 7, 0, 7);
	Insets insetsTNNN = new Insets(7, 0, 0, 0);
	Insets insetsTLNR = new Insets(7, 7, 0, 7);
	Insets insetsTLBN = new Insets(7, 7, 7, 0);
	Insets insetsTNBR = new Insets(7, 0, 7, 7);
	Insets insetsNLBR = new Insets(0, 7, 7, 7);
	GridBagLayout gbl = new GridBagLayout();

	// Center JPanel
	JPanel centerJPanel = new JPanel();
	centerJPanel.setLayout(new BorderLayout());
	getContentPane().add("Center", centerJPanel);

	// Center JPanel: North
	JPanel centerNorthJPanel = new JPanel();
	centerNorthJPanel.setLayout(new BorderLayout());
	centerJPanel.add("North", centerNorthJPanel);

	// Center JPanel: North: West
	JPanel centerNorthWestJPanel = new JPanel();
	centerNorthWestJPanel.setLayout(gbl);
	centerNorthJPanel.add("West", centerNorthWestJPanel);

	JGUIUtil.addComponent(centerNorthWestJPanel, new JLabel("Sheet Name:"),
		0, 0, 1, 1, 0, 0, insetsTLBN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	__sheetNameJTextField = new JTextField(30);
	__sheetNameJTextField.setEditable(false);
	JGUIUtil.addComponent(centerNorthWestJPanel, __sheetNameJTextField, 
		1, 0, 1, 1, 0, 0, insetsTNBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	JGUIUtil.addComponent(centerNorthWestJPanel, new JLabel("Date:"), 
		2, 0, 1, 1, 0, 0, insetsTLBN, GridBagConstraints.NONE, GridBagConstraints.EAST);
	      
	__dateJTextField = new JTextField(15);
	__dateJTextField.setEditable(false);
	JGUIUtil.addComponent(centerNorthWestJPanel, __dateJTextField, 
		3, 0, 1, 1, 0, 0, insetsTNBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__gainSimpleJComboBox = new SimpleJComboBox();
	__gainSimpleJComboBox.addItemListener(this);
	__gainSimpleJComboBox.add(HydroBase_GUI_WIS.NONE);
	__gainSimpleJComboBox.add(HydroBase_GUI_WIS.WEIGHTS);
	__gainSimpleJComboBox.add(HydroBase_GUI_WIS.STREAM_MILE);
	JGUIUtil.addComponent(centerNorthWestJPanel, __gainSimpleJComboBox, 
		4, 0, 1, 1, 0, 0, insetsTNBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// Center JPanel: Center
	JPanel centerCenterJPanel = new JPanel();
	centerCenterJPanel.setLayout(new BorderLayout());
	centerCenterJPanel.setLayout(gbl);
	centerJPanel.add("Center", centerCenterJPanel);

	PropList p = new PropList("HydroBase_GUI_WIS.JWorksheet");
	p.add("JWorksheet.CellFont=Courier");
	p.add("JWorksheet.CellStyle=Plain");
	p.add("JWorksheet.CellSize=11");
	p.add("JWorksheet.HeaderFont=Arial");
	p.add("JWorksheet.HeaderStyle=Plain");
	p.add("JWorksheet.HeaderSize=11");
	p.add("JWorksheet.HeaderBackground=LightGray");
	p.add("JWorksheet.RowColumnPresent=false");
	p.add("JWorksheet.ShowPopupMenu=true");
	p.add("JWorksheet.AllowCopy=true");	
	p.add("JWorksheet.SelectionMode=SingleCellSelection");

	try {
		__tableModel = new HydroBase_TableModel_WISBuilder(new Vector<HydroBase_WISData>(), this);
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer( __tableModel);
	
		__worksheet = new JWorksheet(cr, __tableModel, p);
		__widths = __tableModel.getColumnWidths();
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error creating worksheet.");
		Message.printWarning(2, routine, e);
		__worksheet = new JWorksheet(0, 0, p);
	}
	__worksheet.setPreferredScrollableViewportSize(null);
	__worksheet.setHourglassJFrame(this);
	
	JGUIUtil.addComponent(centerCenterJPanel, new JScrollPane(__worksheet), 
		0, 1, 3, 1, 1, 1, insetsNLNR, GridBagConstraints.BOTH, GridBagConstraints.WEST);
	__worksheet.addMouseListener(this);
	__worksheet.addKeyListener(this);

	// Center JPanel: South
	JPanel centerSouthJPanel = new JPanel();
	centerSouthJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	centerJPanel.add("South", centerSouthJPanel);

	__addRowJButton = new JButton(__BUTTON_ADD_ROW);
	__addRowJButton.setToolTipText("Add a row above the selected row."
		+ "  If the last row is selected, add before or after.");
	__addRowJButton.addActionListener(this);
	centerSouthJPanel.add(__addRowJButton);

	__deleteRowJButton = new JButton(__BUTTON_DELETE_ROW);
	__deleteRowJButton.setToolTipText("Delete the selected row.");
	__deleteRowJButton.addActionListener(this);
	centerSouthJPanel.add(__deleteRowJButton);

	__addFormulaJButton = new JButton(__BUTTON_ADD_FORMULA);
	__addFormulaJButton.setToolTipText("Add a formula to the current cell.");
	__addFormulaJButton.addActionListener(this);
	centerSouthJPanel.add(__addFormulaJButton);

	__deleteFormulaJButton = new JButton(__BUTTON_DELETE_FORMULA);
	// REVISIT (JTS - 2004-06-04)
	// should not be enabled all the time
	__deleteFormulaJButton.setToolTipText("Delete the formula from the current cell.");
	__deleteFormulaJButton.addActionListener(this);
	centerSouthJPanel.add(__deleteFormulaJButton);

	__addImportJButton = new JButton(__BUTTON_ADD_IMPORT);
	__addImportJButton.setToolTipText("Add an import to the current cell.");
	__addImportJButton.addActionListener(this);
	centerSouthJPanel.add(__addImportJButton);

	__deleteImportJButton = new JButton(__BUTTON_DELETE_IMPORT);
	__deleteImportJButton.setToolTipText("Delete the import from the current cell.");
	__deleteImportJButton.addActionListener(this);
	centerSouthJPanel.add(__deleteImportJButton);

	__displayGainsJButton = new JButton(__BUTTON_DISPLAY_GAINS);
	__displayGainsJButton.setToolTipText("Display WIS gains.");
	__displayGainsJButton.setEnabled(false);
	__displayGainsJButton.addActionListener(this);
	centerSouthJPanel.add(__displayGainsJButton);

	// Bottom JPanel
	JPanel bottomJPanel = new JPanel();
	bottomJPanel.setLayout(new BorderLayout());
	getContentPane().add("South", bottomJPanel);

	// Bottom JPanel: West
	JPanel bottomWestJPanel = new JPanel();
	bottomWestJPanel.setLayout(new BorderLayout());
	bottomJPanel.add("West", bottomWestJPanel);

	// Bottom: West: North JPanel
	JPanel bottomNorthJPanel = new JPanel();
	bottomNorthJPanel.setLayout(new BorderLayout());
	bottomWestJPanel.add("North", bottomNorthJPanel);

	// Bottom West: North West JPanel
	JPanel bottomNWJPanel = new JPanel();
	bottomNWJPanel.setLayout(gbl);
	bottomNorthJPanel.add("West", bottomNWJPanel);

	JGUIUtil.addComponent(bottomNWJPanel, new JLabel("Water Districts:"), 
		0, 0, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(bottomNWJPanel, new JLabel("Row Type:"), 
		1, 0, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(bottomNWJPanel, new JLabel("Unique Identifier:"), 
		2, 0, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	JGUIUtil.addComponent(bottomNWJPanel,
		new JLabel("Stream for Current Row:"), 
		3, 0, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__waterDistrictSimpleJComboBox = new SimpleJComboBox();
	__waterDistrictSimpleJComboBox.addItemListener(this);
	JGUIUtil.addComponent(bottomNWJPanel, __waterDistrictSimpleJComboBox, 
		0, 1, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__rowTypeSimpleJComboBox = new SimpleJComboBox();
	__rowTypeSimpleJComboBox.add(HydroBase_GUI_WIS.CONFLUENCE);
	__rowTypeSimpleJComboBox.add(HydroBase_GUI_WIS.DIVERSION);
	__rowTypeSimpleJComboBox.add(HydroBase_GUI_WIS.MIN_FLOW_REACH);
	__rowTypeSimpleJComboBox.add(HydroBase_GUI_WIS.OTHER);
	__rowTypeSimpleJComboBox.add(HydroBase_GUI_WIS.RESERVOIR);
	__rowTypeSimpleJComboBox.add(HydroBase_GUI_WIS.STATION);
	__rowTypeSimpleJComboBox.add(HydroBase_GUI_WIS.STREAM);
	__rowTypeSimpleJComboBox.add(HydroBase_GUI_WIS.STRING);
	__rowTypeSimpleJComboBox.addItemListener(this);
	JGUIUtil.addComponent(bottomNWJPanel, __rowTypeSimpleJComboBox, 
		1, 1, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__uniqueJTextField = new JTextField();
	__uniqueJTextField.addKeyListener(this);
	__uniqueJTextField.setEditable(false);
	__uniqueJTextField.setBackground(Color.lightGray);
	JGUIUtil.addComponent(bottomNWJPanel, __uniqueJTextField, 
		2, 1, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__currentStreamJTextField = new JTextField(25);
	__currentStreamJTextField.setEditable(false);
	__currentStreamJTextField.setBackground(Color.lightGray);
	JGUIUtil.addComponent(bottomNWJPanel, __currentStreamJTextField, 
		3, 1, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//--------------------------------------------------------------
	// CardLayout JPanel
	// NOTE: 
	//  _bottomWCenterJPanel contains: __formulaJPanel, __confluenceJPanel
	//  __streamJPanel, __diversionJPanel, __stationJPanel, __stringJPanel
	//--------------------------------------------------------------
	__rowTypeCard = new CardLayout(0,0);

	// Bottom West: Center JPanel
	__rowTypeJPanel = new JPanel();
	__rowTypeJPanel.setLayout(__rowTypeCard);
	bottomWestJPanel.add("West", __rowTypeJPanel);

	//--------------------------------------------------------------
	// Formula JPanel and JComponents
	//--------------------------------------------------------------
	__formulaJPanel = new JPanel();
	__formulaJPanel.setLayout(gbl);
	__rowTypeJPanel.add("formula", __formulaJPanel);

	__formulaJLabel = new JLabel(__FORMULA_LABEL);
	JGUIUtil.addComponent(__formulaJPanel, __formulaJLabel, 
		0, 0, 10, 1, 1, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__formulaList = new SimpleJList<String>();
	JGUIUtil.addComponent(__formulaJPanel, new JScrollPane(__formulaList), 
		0, 1, 10, 10, 1, 1, insetsNLNR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

	JPanel operatorsJPanel = new JPanel();
	operatorsJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	JGUIUtil.addComponent(__formulaJPanel, operatorsJPanel, 
		0, 12, 1, 1, 1, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__deleteJButton = new JButton(__BUTTON_DELETE);
	__deleteJButton.setToolTipText("Delete selected formula operation.");
	__deleteJButton.addActionListener(this);
	operatorsJPanel.add(__deleteJButton);

	__plusJButton = new JButton(__BUTTON_ADD);
	__plusJButton.setToolTipText("Adddition operation.");
	__plusJButton.addActionListener(this);
	operatorsJPanel.add(__plusJButton);

	__minusJButton = new JButton(__BUTTON_SUBTRACT);
	__minusJButton.setToolTipText("Subtraction operation.");
	__minusJButton.addActionListener(this);
	operatorsJPanel.add(__minusJButton);
 
	__divideJButton = new JButton(__BUTTON_DIVIDE);
	__divideJButton.setToolTipText("Division operation.");
	__divideJButton.addActionListener(this);
	operatorsJPanel.add(__divideJButton);

	__multiplyJButton = new JButton(__BUTTON_MULTIPLY);
	__multiplyJButton.setToolTipText("Multiplication operation.");
	__multiplyJButton.addActionListener(this);
	operatorsJPanel.add(__multiplyJButton);

/*
	JTS - 2003-12-01
	the sum button is not enabled yet, and it may never be.
	__sumJButton = new JButton(__BUTTON_SUM);
	__sumJButton.setEnabled(false);
	__sumJButton.addActionListener(this);
	operatorsJPanel.add(__sumJButton);
*/

	__constantCard = new CardLayout(0,0);

	__constantJPanel = new JPanel();
	__constantJPanel.setLayout(__constantCard);
	operatorsJPanel.add(__constantJPanel);

	JPanel constantShowJPanel = new JPanel();
	constantShowJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	__constantJPanel.add(__SHOW, constantShowJPanel);

	constantShowJPanel.add(new JLabel("Constant value:"));
	
	__constantJTextField = new JTextField(10);
	__constantJTextField.addKeyListener(this);
	constantShowJPanel.add(__constantJTextField);
	
	JPanel constantHideJPanel = new JPanel();
	constantHideJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	__constantJPanel.add(__HIDE, constantHideJPanel);

	__constantCard.show(__constantJPanel, __HIDE);
	__isConstantShown = false;

	//--------------------------------------------------------------
	// String JPanel and JComponents
	//--------------------------------------------------------------
	__stringJPanel = new JPanel();
	__stringJPanel.setLayout(gbl);
	__rowTypeJPanel.add(HydroBase_GUI_WIS.STRING, __stringJPanel);

	JGUIUtil.addComponent(__stringJPanel, new JLabel("Row Label:"), 
		0, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);

	__stringRowLabelJTextField = new JTextField(30);
	__stringRowLabelJTextField.addMouseListener(this);
	__stringRowLabelJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__stringJPanel, __stringRowLabelJTextField, 
		0, 1, 1, 1, 1, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);

	//--------------------------------------------------------------
	// Stream JPanel and JComponents
	//--------------------------------------------------------------
	__streamJPanel = new JPanel();
	__streamJPanel.setLayout(gbl);
	__rowTypeJPanel.add(HydroBase_GUI_WIS.STREAM, __streamJPanel);

	JGUIUtil.addComponent(__streamJPanel, new JLabel(
		"Stream (Stream Number):"), 
		0, 0, 1, 1, 1, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(__streamJPanel, new JLabel(
		"Row Label (Select Stream at left if possible):"), 
		1, 0, 1, 1, 1, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__streamNameSimpleJComboBox = new SimpleJComboBox();
	__streamNameSimpleJComboBox.add(__SELECT_STREAM);    
	__streamNameSimpleJComboBox.addItemListener(this);
	JGUIUtil.addComponent(__streamJPanel, __streamNameSimpleJComboBox, 
		0, 1, 1, 1, 1, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__streamRowLabelJTextField = new JTextField(30);
	__streamRowLabelJTextField.addKeyListener(this);
	__streamRowLabelJTextField.addMouseListener(this);
	JGUIUtil.addComponent(__streamJPanel, __streamRowLabelJTextField, 
		1, 1, 1, 1, 1, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	//--------------------------------------------------------------
	// Confluence JPanel and JComponents
	//--------------------------------------------------------------
	__confluenceJPanel = new JPanel();
	__confluenceJPanel.setLayout(gbl);
	__rowTypeJPanel.add(HydroBase_GUI_WIS.CONFLUENCE, __confluenceJPanel);

	JGUIUtil.addComponent(__confluenceJPanel, new JLabel(
		"Confluence (Stream Number):"), 
		0, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	ButtonGroup concbg = new ButtonGroup();
	
	__conStreamJRadioButton = new JRadioButton(__IN_STREAM, true);
	concbg.add(__conStreamJRadioButton);
	__conStreamJRadioButton.addItemListener(this);
	JGUIUtil.addComponent(__confluenceJPanel, 
		__conStreamJRadioButton, 
		1, 0, 1, 1, 0, 0, insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	__conWDJRadioButton = new JRadioButton(__IN_WD + " " + __wis.getWD(), 
		false);
	concbg.add(__conWDJRadioButton);
	__conWDJRadioButton.addItemListener(this);
	JGUIUtil.addComponent(__confluenceJPanel, 
		__conWDJRadioButton, 
		2, 0, 1, 1, 0, 0, insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	JGUIUtil.addComponent(__confluenceJPanel, new JLabel(
		"Row Label (Select Confluence at left if possible):"), 
		3, 0, 2, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__confluenceNameSimpleJComboBox = new SimpleJComboBox();
	__confluenceNameSimpleJComboBox.addItemListener(this);
	__confluenceNameSimpleJComboBox.add(__SELECT_STREAM);
	JGUIUtil.addComponent(__confluenceJPanel, 
		__confluenceNameSimpleJComboBox, 
		0, 1, 3, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__confluenceRowLabelJTextField = new JTextField(30);
	__confluenceRowLabelJTextField.addMouseListener(this);
	__confluenceRowLabelJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__confluenceJPanel, 
		__confluenceRowLabelJTextField, 
		3, 1, 2, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	JGUIUtil.addComponent(__confluenceJPanel, 
		new JLabel("Stream Mile:"), 
		3, 2, 1, 1, 1, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	JGUIUtil.addComponent(__confluenceJPanel, new JLabel(__WEIGHT), 
		4, 2, 1, 1, 1, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__confluenceStreamMileJTextField = new JTextField(10);
	__confluenceStreamMileJTextField.addMouseListener(this);
	__confluenceStreamMileJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__confluenceJPanel,
		__confluenceStreamMileJTextField,
		3, 3, 1, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__cflWeightJTextField = new JTextField(10);
	__cflWeightJTextField.addMouseListener(this);
	__cflWeightJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__confluenceJPanel, __cflWeightJTextField, 
		4, 3, 1, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//--------------------------------------------------------------
	// Diversion JPanel and JComponents
	//--------------------------------------------------------------
	__diversionJPanel = new JPanel();
	__diversionJPanel.setLayout(gbl);
	__rowTypeJPanel.add(HydroBase_GUI_WIS.DIVERSION, __diversionJPanel);

	JGUIUtil.addComponent(__diversionJPanel,
		new JLabel("Diversion (WDID):"), 
		0, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	ButtonGroup strcbg = new ButtonGroup();
	
	__structStreamJRadioButton = new JRadioButton(__IN_STREAM, true);
	strcbg.add(__structStreamJRadioButton);
	__structStreamJRadioButton.addItemListener(this);
	JGUIUtil.addComponent(__diversionJPanel, 
		__structStreamJRadioButton, 
		1, 0, 1, 1, 0, 0, insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	__structWDJRadioButton = new JRadioButton(__IN_WD + " " + __wis.getWD(),
		false);
	strcbg.add(__structWDJRadioButton);
	__structWDJRadioButton.addItemListener(this);
	JGUIUtil.addComponent(__diversionJPanel, 
		__structWDJRadioButton, 
		2, 0, 1, 1, 0, 0, insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	JGUIUtil.addComponent(__diversionJPanel, new JLabel(
		"Row Label (Select Diversion at left if possible):"), 
		3, 0, 2, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__diversionNameSimpleJComboBox = new SimpleJComboBox();
	__diversionNameSimpleJComboBox.addItemListener(this);
	__diversionNameSimpleJComboBox.add(__SELECT_DIVERSION);
	JGUIUtil.addComponent(__diversionJPanel, __diversionNameSimpleJComboBox,
		0, 1, 3, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__diversionRowLabelJTextField = new JTextField(30);
	__diversionRowLabelJTextField.addMouseListener(this);
	__diversionRowLabelJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__diversionJPanel, __diversionRowLabelJTextField,
		3, 1, 2, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__divKnownPointJCheckBox = new JCheckBox("Baseflow");
	__divKnownPointJCheckBox.setEnabled(false);
	__divKnownPointJCheckBox.addItemListener(this);
	JGUIUtil.addComponent(__diversionJPanel, __divKnownPointJCheckBox, 
		5, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	
	//__divStreamNameJLabel = new JLabel("Stream Name:");
	//JGUIUtil.addComponent(__diversionJPanel, __divStreamNameJLabel, 
		//0, 2, 3, 1, 1, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	JGUIUtil.addComponent(__diversionJPanel, 
		new JLabel("Stream Mile:"), 
		3, 2, 1, 1, 1, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	JGUIUtil.addComponent(__diversionJPanel, new JLabel(__WEIGHT), 
		4, 2, 1, 1, 1, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//__divStreamNameJTextField = new JTextField(30);
	//__divStreamNameJTextField.setEditable(false);
	//__divStreamNameJTextField.setBackground(Color.lightGray);
	//JGUIUtil.addComponent(__diversionJPanel, __divStreamNameJTextField, 
		//0, 3, 3, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__divStreamMileJTextField = new JTextField(10);
	__divStreamMileJTextField.addMouseListener(this);
	__divStreamMileJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__diversionJPanel, __divStreamMileJTextField, 
		3, 3, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__divWeightJTextField = new JTextField(10);
	__divWeightJTextField.addMouseListener(this);
	__divWeightJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__diversionJPanel, __divWeightJTextField, 
		4, 3, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//--------------------------------------------------------------
	// Reservoir JPanel and JComponents
	//--------------------------------------------------------------
	__reservoirJPanel = new JPanel();
	__reservoirJPanel.setLayout(gbl);
	__rowTypeJPanel.add(HydroBase_GUI_WIS.RESERVOIR, __reservoirJPanel);

	JGUIUtil.addComponent(__reservoirJPanel, new JLabel(
		"Reservoir (WDID):"), 
		0, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	ButtonGroup rescbg = new ButtonGroup();
	
	__resStreamJRadioButton = new JRadioButton(__IN_STREAM, true);
	rescbg.add(__resStreamJRadioButton);
	__resStreamJRadioButton.addItemListener(this);
	JGUIUtil.addComponent(__reservoirJPanel, 
		__resStreamJRadioButton, 
	1, 0, 1, 1, 0, 0, insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	__resWDJRadioButton = new JRadioButton(__IN_WD + " " + __wis.getWD(), 
		false);
	rescbg.add(__resWDJRadioButton);
	__resWDJRadioButton.addItemListener(this);
	JGUIUtil.addComponent(__reservoirJPanel, 
		__resWDJRadioButton, 
		2, 0, 1, 1, 0, 0, insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	JGUIUtil.addComponent(__reservoirJPanel, new JLabel(
		"Row Label (Select Reservoir at left if possible):"), 
		3, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__reservoirNameSimpleJComboBox = new SimpleJComboBox();
	__reservoirNameSimpleJComboBox.addItemListener(this);
	__reservoirNameSimpleJComboBox.add(__SELECT_RESERVOIR);
	JGUIUtil.addComponent(__reservoirJPanel, __reservoirNameSimpleJComboBox,
		0, 1, 3, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__reservoirRowLabelJTextField = new JTextField(30);
	__reservoirRowLabelJTextField.addMouseListener(this);
	__reservoirRowLabelJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__reservoirJPanel, __reservoirRowLabelJTextField,
		3, 1, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__resKnownPointJCheckBox = new JCheckBox("Baseflow");
	__resKnownPointJCheckBox.setEnabled(false);
	__resKnownPointJCheckBox.addItemListener(this);
	JGUIUtil.addComponent(__reservoirJPanel, __resKnownPointJCheckBox, 
		4, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	
	JGUIUtil.addComponent(__reservoirJPanel, 
		new JLabel("Stream Mile:"), 
		3, 2, 1, 1, 1, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	JGUIUtil.addComponent(__reservoirJPanel, new JLabel(__WEIGHT), 
		4, 2, 1, 1, 1, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__resStreamMileJTextField = new JTextField(10);
	__resStreamMileJTextField.addMouseListener(this);
	__resStreamMileJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__reservoirJPanel, __resStreamMileJTextField, 
		3, 3, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__resWeightJTextField = new JTextField(10);
	__resWeightJTextField.addMouseListener(this);
	__resWeightJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__reservoirJPanel, __resWeightJTextField, 
		4, 3, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//--------------------------------------------------------------
	// Minimum Flow Reach and JComponents
	//--------------------------------------------------------------
	__minFlowReachJPanel = new JPanel();
	__minFlowReachJPanel.setLayout(gbl);
	__rowTypeJPanel.add(HydroBase_GUI_WIS.MIN_FLOW_REACH, 
		__minFlowReachJPanel);

	JGUIUtil.addComponent(__minFlowReachJPanel,new JLabel(
		"Min Flow Reach (WDID):"),
		0, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	ButtonGroup mfrcbg = new ButtonGroup();
	
	__mfrWeightStreamJRadioButton = new JRadioButton(__IN_STREAM, true);
	mfrcbg.add(__mfrWeightStreamJRadioButton);
	__mfrWeightStreamJRadioButton.addItemListener(this);
	JGUIUtil.addComponent(__minFlowReachJPanel, 
		__mfrWeightStreamJRadioButton, 
		1, 0, 1, 1, 0, 0, insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	__mfrWeightWDJRadioButton = new JRadioButton(
		__IN_WD + " " + __wis.getWD(), false);
	mfrcbg.add(__mfrWeightWDJRadioButton);
	__mfrWeightWDJRadioButton.addItemListener(this);
	JGUIUtil.addComponent(__minFlowReachJPanel, 
		__mfrWeightWDJRadioButton, 
		2, 0, 1, 1, 0, 0, insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	JGUIUtil.addComponent(__minFlowReachJPanel, new JLabel(
		"Row Label (Select Min Flow Reach at left if possible):"), 
		3, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__mfrWeightNameSimpleJComboBox = new SimpleJComboBox();
	__mfrWeightNameSimpleJComboBox.addItemListener(this);
	__mfrWeightNameSimpleJComboBox.add(__SELECT_MFR);
	JGUIUtil.addComponent(__minFlowReachJPanel, 
		__mfrWeightNameSimpleJComboBox, 
		0, 1, 3, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__mfrWeightRowLabelJTextField =  new JTextField(30);
	__mfrWeightRowLabelJTextField.addMouseListener(this);
	__mfrWeightRowLabelJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__minFlowReachJPanel, 
		__mfrWeightRowLabelJTextField, 
		3, 1, 2, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__mfrWeightKnownPointJCheckBox = new JCheckBox("Baseflow");
	__mfrWeightKnownPointJCheckBox.setEnabled(false);
	__mfrWeightKnownPointJCheckBox.addItemListener(this);
	JGUIUtil.addComponent(__minFlowReachJPanel, 
		__mfrWeightKnownPointJCheckBox, 
		5, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	
	JGUIUtil.addComponent(__minFlowReachJPanel, 
		new JLabel("Stream Mile:"), 
		3, 2, 1, 1, 1, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	JGUIUtil.addComponent(__minFlowReachJPanel, new JLabel(__WEIGHT), 
		4, 2, 1, 1, 1, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__mfrWeightStreamMileJTextField = new JTextField(10);
	__mfrWeightStreamMileJTextField.addMouseListener(this);
	__mfrWeightStreamMileJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__minFlowReachJPanel, 
		__mfrWeightStreamMileJTextField, 
		3, 3, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__mfrWeightJTextField = new JTextField(10);
	__mfrWeightJTextField.addMouseListener(this);
	__mfrWeightJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__minFlowReachJPanel, __mfrWeightJTextField,
		4, 3, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//--------------------------------------------------------------
	// Other JPanel and JComponents
	//--------------------------------------------------------------
	__otherJPanel = new JPanel();
	__otherJPanel.setLayout(gbl);
	__rowTypeJPanel.add(HydroBase_GUI_WIS.OTHER, __otherJPanel);

	JGUIUtil.addComponent(__otherJPanel, new JLabel("Row Label:"), 
		1, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__otherRowLabelJTextField =  new JTextField(30);
	__otherRowLabelJTextField.addMouseListener(this);
	__otherRowLabelJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__otherJPanel, __otherRowLabelJTextField, 
		1, 1, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__otherKnownPointJCheckBox = new JCheckBox("Baseflow");
	__otherKnownPointJCheckBox.addItemListener(this);
	JGUIUtil.addComponent(__otherJPanel, __otherKnownPointJCheckBox, 
		2, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(__otherJPanel, 
		new JLabel("Stream Mile:"),
		1, 4, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(__otherJPanel, new JLabel(__WEIGHT), 
		2, 4, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	__otherStreamMileJTextField = new JTextField(30);
	__otherStreamMileJTextField.addMouseListener(this);
	__otherStreamMileJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__otherJPanel, __otherStreamMileJTextField, 
		1, 5, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__otherWeightJTextField = new JTextField(10);
	__otherWeightJTextField.addMouseListener(this);
	__otherWeightJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__otherJPanel, __otherWeightJTextField, 
		2, 5, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//--------------------------------------------------------------
	// Station JPanel and JComponents
	//--------------------------------------------------------------
	__stationJPanel = new JPanel();
	__stationJPanel.setLayout(gbl);
	__rowTypeJPanel.add(HydroBase_GUI_WIS.STATION, __stationJPanel);

	JGUIUtil.addComponent(__stationJPanel, new JLabel(
		"Station (Station Identifier):"),
		0, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	JGUIUtil.addComponent(__stationJPanel, new JLabel(
		"Row Label (Select Station at left if possible):"), 
		1, 0, 2, 1, 1, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__stationNameSimpleJComboBox = new SimpleJComboBox();
	__stationNameSimpleJComboBox.addItemListener(this);
	__stationNameSimpleJComboBox.add(__SELECT_STATION);
	JGUIUtil.addComponent(__stationJPanel, __stationNameSimpleJComboBox, 
		0, 1, 1, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
	__stationRowLabelJTextField =  new JTextField(30);
	__stationRowLabelJTextField.addMouseListener(this);
	__stationRowLabelJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__stationJPanel, __stationRowLabelJTextField, 
		1, 1, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__staKnownPointJCheckBox = new JCheckBox("Baseflow");
	__staKnownPointJCheckBox.addItemListener(this);
	JGUIUtil.addComponent(__stationJPanel, __staKnownPointJCheckBox, 
		2, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(__stationJPanel, 
		new JLabel("Stream Mile:"),
		1, 2, 1, 1, 1, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	JGUIUtil.addComponent(__stationJPanel, new JLabel(__WEIGHT), 
		2, 2, 1, 1, 1, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__staStreamMileJTextField = new JTextField(30);
	__staStreamMileJTextField.addMouseListener(this);
	__staStreamMileJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__stationJPanel, __staStreamMileJTextField, 
		1, 3, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__staWeightJTextField = new JTextField(10);
	__staWeightJTextField.addMouseListener(this);
	__staWeightJTextField.addKeyListener(this);
	JGUIUtil.addComponent(__stationJPanel, __staWeightJTextField, 
		2, 3, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// Bottom: South JPanel
	JPanel bottomSJPanel = new JPanel();
	bottomSJPanel.setLayout(new BorderLayout());
	bottomJPanel.add("South", bottomSJPanel);

	// Bottom: South: North JPanel
	JPanel bottomSNJPanel = new JPanel();
	bottomSNJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	bottomSJPanel.add("North", bottomSNJPanel);

	__showNetworkJButton = new JButton(__BUTTON_SHOW_NETWORK);
	__showNetworkJButton.setToolTipText("Show a text representation of "
		+ "the WIS (as a stream network).");
	__showNetworkJButton.addActionListener(this);
	bottomSNJPanel.add(__showNetworkJButton); 

	__buildDiagramJButton = new JButton(__BUTTON_BUILD_DIAGRAM);
	__buildDiagramJButton.setToolTipText("Edit the WIS diagram.");
	__buildDiagramJButton.addActionListener(this);
	bottomSNJPanel.add(__buildDiagramJButton);

	__saveJButton = new JButton(__BUTTON_SAVE);
	__saveJButton.setToolTipText("Save WIS to database.");
	__saveJButton.setEnabled(false);
	__saveJButton.addActionListener(this);
	bottomSNJPanel.add(__saveJButton);

	__closeJButton = new JButton(__BUTTON_CLOSE);
	__closeJButton.setToolTipText("Close builder.");
	__closeJButton.addActionListener(this);
	bottomSNJPanel.add(__closeJButton);

//	__helpJButton = new JButton(__BUTTON_HELP);
//	__helpJButton.addActionListener(this);
//	bottomSNJPanel.add(__helpJButton);

	// Bottom: South: South JPanel
	JPanel bottomSSJPanel = new JPanel();
	bottomSSJPanel.setLayout(gbl);
	bottomSJPanel.add("South", bottomSSJPanel);

	__statusJTextField = new JTextField();
	__statusJTextField.setEditable(false);
	JGUIUtil.addComponent(bottomSSJPanel, __statusJTextField, 
		0, 1, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// Popup Menu...

	__cellJPopupMenu = new JPopupMenu();
	__cellJPopupMenu.add(new SimpleJMenuItem(__POPUP_PROPERTIES, this));
	__worksheet.setPopupMenu(__cellJPopupMenu, false);

	// JFrame settings

	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}
	
	setTitle(app + "Water Information Sheet Builder");

	
	__initialized = true;

	if (__isNewSheet) {
		__rowTypeSimpleJComboBox.select(null);
		__rowTypeSimpleJComboBox.select(HydroBase_GUI_WIS.STREAM);

		String wd = __waterDistrictSimpleJComboBox.getSelected().trim();
		__waterDistrictSimpleJComboBox.select(null);
		__waterDistrictSimpleJComboBox.select(wd);
	}

	setVisible(true, __stream);
	__dirty = false;
}

/**
Shows the HydroBase_GUI_WISBuilder object.
@param state true if showing this GUI, false otherwise.
@param stream HydroBase_Stream object.  If a new WIS, a stream row will 
automatically be added for the top level stream.
*/
public void setVisible(boolean state, HydroBase_Stream stream) {
	if (state) {
		String routine = "HydroBase_GUI_WISBuilder.setVisible";
		StopWatch timerAll = new StopWatch();
		timerAll.start();
		StopWatch timerPart = new StopWatch();
                Message.setTopLevel(this);

   		// initialize variables
		__isBuildingFormula = false;  
		__structureWasSelected = false;
	        __wisFormatList = new Vector<HydroBase_WISFormat>();
		__wisFormatList.add(new HydroBase_WISFormat());
		__formulaVector = new Vector<HydroBase_WISMath>();
		__diversionVector = new Vector<HydroBase_StructureView>();
		__reservoirVector = new Vector<HydroBase_StructureView>();
		__mfrWeightVector = new Vector<HydroBase_StructureView>();
		__stationVector = new Vector<HydroBase_StationView>();
		__streamVector = new Vector<HydroBase_Stream>();
		__confluenceVector = new Vector<HydroBase_Stream>();

		__curConfluenceHashkey = "";
		__curStationWD = "";
		__curStreamWD = "";

		// load the sheet_name, wis_format, wis_import, 
		// and wis_formula data
		timerPart.start();
		querySheetName();
		timerPart.stop();
		Message.printStatus(1, routine,
			"Querying sheet name from wis_num took "
			+ StringUtil.formatString(timerPart.getSeconds(),"%.2f")
			+ " seconds.");
		timerPart.clear();
		timerPart.start();
		queryWISFormat();
		timerPart.stop();
		Message.printStatus(1, routine,
			"Querying WIS format for wis_num took "
			+ StringUtil.formatString(timerPart.getSeconds(),"%.2f")
			+ " seconds.");
		timerPart.clear();
		timerPart.start();
		queryWISImport();
		timerPart.stop();
		Message.printStatus(1, routine,
			"Querying WIS imports for wis_num took "
			+ StringUtil.formatString(timerPart.getSeconds(),"%.2f")
			+ " seconds.");
		timerPart.clear();
		timerPart.start();
		queryWISFormula();
		timerPart.stop();
		Message.printStatus(1, routine,
			"Querying WIS formulae for wis_num took "
			+ StringUtil.formatString(timerPart.getSeconds(),"%.2f")
			+ " seconds.");
		timerPart.clear();
		timerPart.start();

		// set GUI defaults
		setGUIDefaults();	// This sets the water district list
		timerPart.stop();
		Message.printStatus(1, routine,
			"Setting WIS cell defaults took "
			+ StringUtil.formatString(timerPart.getSeconds(),"%.2f")
			+ " seconds.");
		timerPart.clear();
		timerPart.start();
		displayStationList();
		timerPart.stop();
		Message.printStatus(1, routine,
			"Generating initial streamflow station list for "
			+ "selected WDs took "
			+ StringUtil.formatString(timerPart.getSeconds(),"%.2f")
			+ " seconds.");
		timerPart.clear();
		timerPart.start();
		displayStreamList();
		timerPart.stop();
		Message.printStatus(1, routine,
			"Generating stream list for selected WDs took "
			+ StringUtil.formatString(timerPart.getSeconds(),"%.2f")
			+ " seconds.");

		pack();
		setSize(930, 650);

		// Automatically add a new row for new wis and default to 
		// a stream row type.
		if (__isNewSheet) {
			addRowClicked(true);
			__rowTypeSimpleJComboBox.select(
				HydroBase_GUI_WIS.STREAM);
			setCellColor(0, 2, 5, Color.lightGray, Color.black);
			showJPanel();
			refreshWorksheet();
			selectMainStem(stream);
		}

		// Structure list generation should appear here so that new
		// sheets will have the first row automatically set.
		timerPart.clear();
		timerPart.start();
		displayStructureList(__STREAM);
		timerPart.stop();
		Message.printStatus(1, routine,
			"Generating structure list for selected WDs took "
			+ StringUtil.formatString(timerPart.getSeconds(),"%.2f")
			+ " seconds.");
		timerPart.clear();
		timerPart.start();
		// also call confluence generation
		displayConfluenceList(__STREAM);
		timerPart.stop();
		Message.printStatus(1, routine,
			"Generating confluence list for selected WDs took "
			+ StringUtil.formatString(timerPart.getSeconds(),"%.2f")
			+ " seconds.");
		JGUIUtil.center(this);
		timerAll.stop();
		Message.printStatus(1, routine,
			"WIS load took a total of "
			+ StringUtil.formatString(timerAll.getSeconds(),"%.2f")
		 	+ " seconds.");
		timerAll = null;
		timerPart = null;
		routine = null;
	}
        super.setVisible(state);
	if (__widths != null) {
		__worksheet.setColumnWidths(__widths);
	}
}

/**
Similar to the HydroBase_GUI_WIS.loadCellAttributes - show the properies for a 
cell.
@param row Row number.
@param col Column number.
*/
private void showCellProperties(int row, int col) {
	__statusJTextField.setText("Displaying cell properties.");

	// initialize variables
	HydroBase_WISFormat wisFormat = __wisFormatList.get(row + 1);
	String rowType = wisFormat.getRow_type();

	// locate the formula and import for the current cell
	HydroBase_WISFormula wisFormula = wisFormat.getWISFormula(col);
	HydroBase_WISImport wisImport = wisFormat.getWISImport( __worksheet.getVisibleColumn(col + 1));
	String contents = "" + __worksheet.getValueAt(row, col - 1);
	String mileString = "";
	String weightString = "";
	String NA = "Does not apply.";
	HydroBase_Node downstreamNode = null;

	// get the gain Loss information
	if (rowType.equals(HydroBase_GUI_WIS.STRING)
		|| rowType.equals(HydroBase_GUI_WIS.STREAM)) {
		mileString = NA;
		weightString = NA;
	}
	else {	
		HydroBase_Node node = __network.getMostUpstreamNode();
		while (node != null) {
			node = HydroBase_NodeNetwork.getDownstreamNode(node, HydroBase_NodeNetwork.POSITION_COMPUTATIONAL);
			// find the node matching the wisFormat key...
			if (node.getWISFormat() == wisFormat) {
				// first get the gains using stream mile.
				double[] mile_values = HydroBase_WIS_Util.computeGainLoss( __network, node);
				mileString = HydroBase_WIS_Util.formatGainLoss( mile_values);

				// now get the gains using weights
				double[] weight_values = HydroBase_WIS_Util.computeWeightedGainLoss( __network, node);
				weightString = HydroBase_WIS_Util.formatGainLoss(weight_values);

				// make sure we have a valid computation.
				if (DMIUtil.isMissing(mile_values[0]) || HydroBase_Util.isMissing(mile_values[0])) {
					mileString = NA;
				}

				// make sure we have a valid computation.
				if (DMIUtil.isMissing(weight_values[0]) || HydroBase_Util.isMissing(weight_values[0])) {
					weightString = NA;
				}

				// Get the next downstream node...
				downstreamNode = node.getDownstreamNode();
				break;
			}
		}
	}

	HydroBase_GUI_WISCellAttributes gui = 
		new HydroBase_GUI_WISCellAttributes(__parent, __geoview_ui,
		wisFormat.getRow_label(), getColumnType(col), contents, 
		wisFormat, wisFormula, wisImport, __dmi, downstreamNode);
	gui.setGainsField(mileString);
	gui.setWeightedGainsField(weightString);
}

/**
This method responds to the __showNetworkJButton ACTION_EVENT and displays a
text representation of the network.
*/
private void showNetworkClicked() {
	// update the current row before showing the network
	if (!updateRowFormat(__curCellRow)) {
		return;
	}

	JGUIUtil.setWaitCursor(this, true);
	setButtonsState(false);
	__statusJTextField.setText("Please Wait...Displaying River Network");

	refreshNetwork();
	notifyDiagramGUI();

	List<String> networkLayout = __network.createIndentedRiverNetworkStrings();
	PropList reportProp = new PropList("ReportJFrame.props");
	reportProp.set("TotalWidth", "750");
	reportProp.set("TotalHeight", "550");
	reportProp.set("DisplayFont", "Courier");
	reportProp.set("DisplaySize", "11");
	reportProp.set("PrintFont", "Courier");
	reportProp.set("PrintSize", "7");
	reportProp.set("Title", "Water Information Sheet Network");
	new ReportJFrame(networkLayout, reportProp);
	networkLayout = null;
	reportProp = null;
	ready();
}

/**
Show the appropriate panel and components depending upon the selected row type.
*/
private void showJPanel() {
	String rowType = __rowTypeSimpleJComboBox.getSelected().trim();
	
	if (rowType.equals(HydroBase_GUI_WIS.CONFLUENCE)) {
		if (__conStreamJRadioButton.isSelected()) {
			displayConfluenceList(__STREAM);
		}
		else {	
			displayConfluenceList(__ALL);
		}
		__rowTypeCard.show(__rowTypeJPanel, HydroBase_GUI_WIS.CONFLUENCE);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.DIVERSION)) {
		if (__structStreamJRadioButton.isSelected()) {		
			displayStructureList(__STREAM);
		}
		else {	
			displayStructureList(__ALL);
		}
        	__rowTypeCard.show(__rowTypeJPanel, HydroBase_GUI_WIS.DIVERSION);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.MIN_FLOW_REACH)) {
		if (__mfrWeightStreamJRadioButton.isSelected()) {
			displayStructureList(__STREAM);
		}
		else {	
			displayStructureList(__ALL);
		}
        	__rowTypeCard.show(__rowTypeJPanel, HydroBase_GUI_WIS.MIN_FLOW_REACH);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.OTHER)) {
        	__rowTypeCard.show(__rowTypeJPanel, HydroBase_GUI_WIS.OTHER);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.RESERVOIR)) {
		if (__resStreamJRadioButton.isSelected()) {
			displayStructureList(__STREAM);
		}
		else {	
			displayStructureList(__ALL);
		}
		__rowTypeCard.show(__rowTypeJPanel, HydroBase_GUI_WIS.RESERVOIR);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.STATION)) {
       		__rowTypeCard.show(__rowTypeJPanel, HydroBase_GUI_WIS.STATION);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.STREAM)) {
		__rowTypeCard.show(__rowTypeJPanel, HydroBase_GUI_WIS.STREAM);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.STRING)) {
		__rowTypeCard.show(__rowTypeJPanel, HydroBase_GUI_WIS.STRING);
	}
	else {
		Message.printStatus(1, "", "  Unknown rowtype: '" + rowType + "'");
	}
	refreshWorksheet();
}

/**
Converts a String to a number.  If the String is not a valid double number,
will return DMIUtil.MISSING_DOUBLE.
@param s the String to convert to a double.
@return the double version of the String value.  Returns DMIUtil.MISSING_DOUBLE
if the String is not a valid double number.
*/
private double stringToDouble(String s) {
	String routine = CLASS + ".stringToDouble";
	
	if (s == null || s.trim().equals("")) {
		return DMIUtil.MISSING_DOUBLE;
	}
	try {
		Double D = new Double(s);
		return D.doubleValue();
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error converting string ('" + s + "') to a number.");
		Message.printWarning(2, routine, e);
	}
	return DMIUtil.MISSING_DOUBLE;	
}

/**
Does nothing.
*/
private void sumClicked() {}

/**
Update the equation in the presently selected cell.
@param row row number for the formula.
@param col column number for the formula.
*/
private void updateCellFormula(int row, int col) {
	HydroBase_WISFormat wisFormat = null;
	HydroBase_WISMath wisMath = null;
	String colType = getColumnType(col);
	String labelFormula = "";
	String uniqueFormula = "";

	// rebuild the expressions	
	int size = __formulaVector.size();
	for (int i = 0; i < size; i++) {
		wisMath = __formulaVector.get(i);

		// range type expressions
		if (wisMath.getRange()) {}
		else if (wisMath.isConstantTerm()) {
			labelFormula += wisMath.getOperator() + " " + wisMath.getConstant() + " ";
			uniqueFormula +=  wisMath.getOperator() + " " + wisMath.getConstant() + " ";
		}
		// all other expressions
		else  { 
			int refRow = getTermReference(wisMath);
			if (!DMIUtil.isMissing(refRow) && !HydroBase_Util.isMissing(refRow)) {
				wisFormat = __wisFormatList.get(refRow);
				labelFormula += wisMath.getOperator()
					+ " " + wisFormat.getRow_label()
					+ "." + wisMath.getColumnType() + " ";
				uniqueFormula += wisMath.getOperator()
					+ " " + wisFormat.getIdentifier()
					+ "." + wisMath.getColumnType() + " ";
			}
		}
	}

	// add expressions to the wisFormula object
	HydroBase_WISFormula wisFormula = new HydroBase_WISFormula();
	wisFormula.setColumn(colType);
	wisFormula.setFormulastring(labelFormula);
	wisFormula.setFormula(uniqueFormula);

	// update the __wisFormat object for the current row
	wisFormat = __wisFormatList.get(row + 1);
	wisFormat.setWISFormula(wisFormula, col);
	__wisFormatList.set(row+1,wisFormat);

	// set focus back to original cell
	selectCell(row, col);
}

/**
Update the import information for the specified row.
@param row row number.
@param col column number.
@param wisImport HydroBase_WISImport object.
*/
public void updateCellImport(int row, int col, HydroBase_WISImport wisImport) {
	HydroBase_WISFormat wisFormat = __wisFormatList.get(row + 1);
	wisFormat.setWISImport(wisImport, col);
	__wisFormatList.set(row+1,wisFormat);

	// gui updates
	__addImportJButton.setText(__BUTTON_EDIT_IMPORT);

	setCellColor(row, col, col, Color.blue, Color.blue);
	refreshWorksheet();

	// disable formula cells since a cell can be either an import
	// or formula cell, but not both.
	setButtonState(__FORMULA, false);
}

/**
Update the wis row numbers for the Vector WISFormat objects.
*/
private void updateRowNumbers() {
	HydroBase_WISFormat wisFormat = null;
	if (__wisFormatList != null && !(__wisFormatList.isEmpty())) {
		int size = __wisFormatList.size();
		for (int curRow = 1; curRow < size; curRow++) {
			wisFormat = __wisFormatList.get(curRow);
			wisFormat.setWis_row(curRow);
		}
	}
}

/**
Update the row characteristics for the currently selected row.  The text fields
and other components in the detail are used to retrieve information (not the grid contents).
@param rowNum row number to update.
@return true if row is successfully updated, false otherwise.
*/
private boolean updateRowFormat(int rowNum) {
	// The following checks must be performed first.  Can't update row if a
	// valid row is not selected or row is not added to the __wisFormat object
	if ((rowNum < 0) || (rowNum >= __wisFormatList.size() - 1)) {
		Message.printStatus(1, "", "updateRowFormat: rowNum < 0 || rowNum >= __wisFormatVector.size() - 1");
		return false;
	}
	// initialize variables
        HydroBase_WISFormat row = __wisFormatList.get(rowNum + 1);

	double streamMile = row.getStr_mile();
	double weight = row.getGain_factor();
	HydroBase_StationView stationView = null;
	HydroBase_Stream streamData = null;
	HydroBase_StructureView structureView = null;
	int index = -1;
	long stationNum = row.getStation_num();	
	long stream_num = row.getWdwater_num();   
	long structureNum = row.getStructure_num();
	long wdWaterLink = row.getWdwater_link();	
	String knownPoint = "N";
	String rowLabel = null;
	String rowType = __rowTypeSimpleJComboBox.getSelected().trim();	
	String uniqueID = __uniqueJTextField.getText().trim();

	// get the field wis format field values according to row type
	if (rowType.equals(HydroBase_GUI_WIS.DIVERSION)) {
		// update structure info only if a new structure was selected
		if (__structureWasSelected) {
			index=__diversionNameSimpleJComboBox.getSelectedIndex();
			structureView = __diversionVector.get(index);
			structureNum = structureView.getStructure_num();
		}
		stream_num = getTribStreamNum(rowNum);
		streamMile = stringToDouble( __divStreamMileJTextField.getText());
		weight = stringToDouble(__divWeightJTextField.getText());
		rowLabel = __diversionRowLabelJTextField.getText().trim();
		knownPoint = isKnownPoint(__divKnownPointJCheckBox);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.RESERVOIR)) {
		// update structure info only if a new structure was selected
		if (__structureWasSelected) {
			index=__reservoirNameSimpleJComboBox.getSelectedIndex();
			structureView = __reservoirVector.get(index);
			structureNum = structureView.getStructure_num();
		}
		stream_num = getTribStreamNum(rowNum);
		streamMile = stringToDouble( __resStreamMileJTextField.getText());
		weight = stringToDouble(__resWeightJTextField.getText());
		rowLabel = __reservoirRowLabelJTextField.getText().trim();
		knownPoint = isKnownPoint(__resKnownPointJCheckBox);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.MIN_FLOW_REACH)) {
		if (__structureWasSelected) {
			index=__mfrWeightNameSimpleJComboBox.getSelectedIndex();
			structureView = __mfrWeightVector.get(index);
			structureNum = structureView.getStructure_num();
		}
		stream_num = getTribStreamNum(rowNum);
		streamMile = stringToDouble( __mfrWeightStreamMileJTextField.getText());
		weight = stringToDouble( __mfrWeightJTextField.getText());
		rowLabel = __mfrWeightRowLabelJTextField.getText().trim();
		knownPoint = isKnownPoint(__mfrWeightKnownPointJCheckBox);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.OTHER)) {
		streamMile = stringToDouble( __otherStreamMileJTextField.getText());
		weight = stringToDouble(__otherWeightJTextField.getText());
		rowLabel = __otherRowLabelJTextField.getText().trim();
		knownPoint = isKnownPoint(__otherKnownPointJCheckBox);
		stream_num = getTribStreamNum(rowNum);
	}
	else if (rowType.equals(HydroBase_GUI_WIS.CONFLUENCE)) {
		rowLabel = __confluenceRowLabelJTextField.getText().trim();
		stream_num = getTribStreamNum(rowNum);
		streamMile = stringToDouble( __confluenceStreamMileJTextField.getText());
		weight = stringToDouble(__cflWeightJTextField.getText());

		if (__structureWasSelected) {
			index = __confluenceNameSimpleJComboBox.getSelectedIndex();
			streamData = __confluenceVector.get(index);
			// Link is the WDWater number of the tributary stream...
			wdWaterLink = streamData.getStream_num();
		}
	}
	else if (rowType.equals(HydroBase_GUI_WIS.STREAM)) {
		rowLabel = __streamRowLabelJTextField.getText().trim();
		streamMile = stringToDouble(__staStreamMileJTextField.getText());
		if (__structureWasSelected) {
			index = __streamNameSimpleJComboBox.getSelectedIndex();
			streamData = __streamVector.get( index);
			stream_num = streamData.getStream_num();
			// Link is the WDWater number of the downstream stream...
			wdWaterLink = streamData.getStr_trib_to();
		}
	}
	else if (rowType.equals(HydroBase_GUI_WIS.STATION)) {
		rowLabel = __stationRowLabelJTextField.getText().trim();
		knownPoint = isKnownPoint(__staKnownPointJCheckBox);
		stream_num = getTribStreamNum(rowNum);
		streamMile =stringToDouble(__staStreamMileJTextField.getText());
		weight = stringToDouble(__staWeightJTextField.getText());

		if (__structureWasSelected) {
			index = __stationNameSimpleJComboBox.getSelectedIndex();
			stationView = __stationVector.get(index);
			if (stationView == null) {
				return false;
			}
			stationNum = stationView.getStation_num();
		}
	}
	else if (rowType.equals(HydroBase_GUI_WIS.STRING)) {
		rowLabel = __stringRowLabelJTextField.getText().trim();
		stream_num = getTribStreamNum(rowNum);
	}
 
	// Set to false so in the event that a structure is not selected 
	__structureWasSelected = false;

	// Set values on the HydroBase_WISFormat object and update 
	// the grid label

        row.setWis_num(__wisNum);
	row.setIdentifier(uniqueID);
        row.setRow_label(rowLabel);
        row.setRow_type(rowType);
        row.setKnown_point(knownPoint);
	row.setWdwater_num((int)stream_num);
	row.setWdwater_link((int)wdWaterLink);
        row.setStr_mile(streamMile);
	row.setStation_num((int)stationNum);	
	row.setStructure_num((int)structureNum);	
	row.setGain_factor(weight);
	row.setWis_row(rowNum + 1);
	__wisFormatList.set(rowNum+1,row);
	// Set the visual display information...
	setRowLabel(rowNum, formatRowLabel(rowNum, rowLabel));
	
        return true; 
}

/**
Update the row labels due to a row insert or delete.  Updates will only be
applied if expanded details are shown.
@param starting_row starting row to be updated.
*/
private void updateRowLabels(int starting_row) {
	int size = __worksheet.getRowCount();
	HydroBase_WISFormat wisformat = null;
	for (int i = starting_row; i < size; i++) {
        	wisformat = __wisFormatList.get( i + 1);
		setRowLabel(i, formatRowLabel(i, wisformat.getRow_label()));
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
Closes the GUI nicely.
@param event the WindowEvent that happened.
*/
public void windowClosing(WindowEvent event) {
	close();
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

private void worksheetCellChanged(boolean wasMouse, MouseEvent event) {
	int[] cell = null;

	if (wasMouse) {
		cell = __worksheet.getCellAtClick(event);
	}
	else {
		cell = __keyCell;
	}

	if (!updateRowFormat(__curCellRow)) {
		return;
	}					

	if (wasMouse) {
		selectCell(cell[0], __worksheet.getAbsoluteColumn(cell[1]));
	}
	else {
		__curCellRow = cell[0];
		__curCellCol = __worksheet.getAbsoluteColumn(cell[1]);
	}
	resetComboBoxes();
	displayRow(cell[0]);
	__keyCell = null;
}

// REVISIT (JTS - 2005-08-04)
/*
- if you are making a formula and instantly click in a non-numeric field, the 
  formula cannot be built because of an error, but the red paint job on the
  cell sticks around.  MINOR issue.

- various things with enabling/disabling buttons 
*/

}
