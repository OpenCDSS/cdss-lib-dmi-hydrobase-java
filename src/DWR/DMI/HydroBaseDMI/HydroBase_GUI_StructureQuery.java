// HydroBase_GUI_StructureQuery - Structure Query GUI

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
 
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.GIS.GeoView.GeoRecord;
import RTi.GIS.GeoView.GeoViewListener;

import RTi.GR.GRLimits;
import RTi.GR.GRPoint;
import RTi.GR.GRShape;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.JScrollWorksheet;
import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.ReportJFrame;
import RTi.Util.GUI.SimpleJButton; 
import RTi.Util.GUI.SimpleJComboBox;

import RTi.Util.IO.PrintJGUI;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.StopWatch;

/**
This GUI displays the main structure query interface.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_StructureQuery 
extends JFrame
implements ActionListener, GeoViewListener, KeyListener, MouseListener, 
WindowListener {

/**
Class name for use in routine variables
*/
private final String __CLASS = "HydroBase_GUI_StructureQuery";

/**
Structure types.
*/
private final String
        __H = 			"Headgate",
        __JD =			"Jurisdictional Dam",
        __MFR = 		"Minimum Flow Reach",
        __NJD = 		"Non-Jurisdictional Dam",
        __R = 			"Reservoir",
        __W = 			"Well or Well field";

/**
Button labels.
*/
private final String
	__BUTTON_CLOSE = 		"Close",
	__BUTTON_DISPLAY_VIEW = 	"Display View",
	__BUTTON_EXPORT = 		"Export",
	__BUTTON_GET_DATA = 		"Get Data",
	__BUTTON_HELP = 		"Help",
	__BUTTON_OK = 			"OK",
	__BUTTON_PRINT = 		"Print",
	__BUTTON_SELECT_ON_MAP =	"Select On Map";
	
/**
Miscellaneous Strings.
*/
public final String	
        SUMMARY = 			"Structure Summary",
        MORE_STRUCTURE = 		"More Structure Information",
        LOCATION = 			"Location",
        OWNER_CONTACT = 		"Owner/Contact Information",
        IRRIGATED_ACRES = 		"Irrigated Acres by Parcel",
        IRRIGATED_ACRES_SUMMARY = 	"Irrigated Acres Summary",
        HEADGATE = 			"Headgate - Detail",
        DAM = 				"Jurisdictional Dam - Detail",
        SMALL_DAM = 			"Non-Jurisdictional Dam - Detail",
        REACH = 			"Minimum Flow Reach - Detail",
        RESERVOIR = 			"Reservoir - Detail",
        WELL = 				"Well - Detail",
        WR_TRANS = 			"Water Rights - Transaction",
        TIME_SERIES = 			"Diversion Coding/Reservoir Releases",
        WR_NET = 			"Water Rights - Net Amounts",
        STORAGE = 			"Historic Storage Summary",
        RES_MEASUREMENTS= 		"Historical Reservoir Measurements",
	// The following is used to size the initial list appropriately...
        EMPTY_VIEW =
	"                                                               ";
/**
Used to determine when this GUI has been called from setting a call (true)
or just normally (false).
*/
private boolean __displayStructsOnly;

/**
Whether this a query that should select from the GeoView display, too.
*/
private boolean __geoViewSelectQuery = false;

/**
The main application JFrame.
*/
private JFrame __parent = null;

/**
Map interface.
*/
private GeoViewUI __geoview_ui = null;

/**
The maps query limits.
*/
private GRLimits __mapQueryLimits = null;

/**
Reference to DMI object.
*/
private HydroBaseDMI __dmi;

/**
Input filters for setting up how to query.
*/
private HydroBase_GUI_Structure_InputFilter_JPanel __filterJPanel = null;

/**
The currently-selected structure view object in the table.
*/
private HydroBase_StructureView __currentStructureView = null;

/**
GUI buttons.
*/
private JButton 
	__closeJButton,
	__exportJButton,
	__getDataJButton,
	__printJButton,
	__selectOnMapJButton;

/**
The JFrame that called this JFrame for getting the structure (probably will
only be HydroBase_GUI_SetCall).
*/
private JFrame __caller;

/**
The label for the table.
*/
private JLabel __tableJLabel;

/**
The JWorksheet in which the data is shown.
*/
private JWorksheet __worksheet;

/**
GUI text fields.
*/
private JTextField
	__statusJTextField;

/**
Button to display a report.
*/
private SimpleJButton __displayViewJButton;

/**
GUI combo boxes.
*/
private SimpleJComboBox 
	__viewJComboBox,
	__waterDistrictJComboBox;

/**
The kind of the last selected view.
*/
private String	__lastSelectedView = SUMMARY;

/**
The results returned from the query.
*/
private List<HydroBase_StructureView> __results = null;

/**
Constructor. 
Create the interface and make visible.
@param dmi open and non-null HydroBaseDMI object
@param parent Parent JFrame that instantiates an instance of this class.
@param geoview_ui GeoViewUI instance to enable map interaction.
@throws Exception if an error occurs
*/
public HydroBase_GUI_StructureQuery(HydroBaseDMI dmi, JFrame parent,
		GeoViewUI geoview_ui) 
throws Exception {
	this(dmi, parent, geoview_ui, true);
}

/**
Constructor. Create the interface.
@param dmi open and non-null HydroBaseDMI object.
@param parent Parent JFrame that instantiates an instance of this class.
@param geoview_ui GeoViewUI instance to enable map interaction.
@param isVisible Indicates whether the interface should be visible at creation.
@throws Exception if an error occurs
*/
public HydroBase_GUI_StructureQuery(HydroBaseDMI dmi, JFrame parent,
GeoViewUI geoview_ui, boolean isVisible) 
throws Exception {
	__parent = parent;
	__geoview_ui = geoview_ui;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());	
	if (dmi == null) {
		throw new Exception ("Null dmi passed to " + __CLASS + " "
			+ "constructor.  DMI object must be open and "
			+ "connected before passing in.");
	}
	if (!dmi.isOpen()) {
		throw new Exception ("Unopened dmi passed to " + __CLASS + " "
			+ "constructor.  DMI object must be open and "
			+ "connected before passing in.");
	}

	__dmi = dmi;
	__displayStructsOnly = false;

	setupGUI(isVisible);
}

/**
This function is responsible for handling ActionEvents for all 
gui objects which use and ActionEventListener.
@param event ActionEvent object.
*/
public void actionPerformed(ActionEvent event) {
	String action = event.getActionCommand();
	String routine = __CLASS + ".actionPerformed()";
	
        if (action.equals(__BUTTON_CLOSE)) {
                closeClicked();
        }
        else if (action.equals(__BUTTON_OK)) {
                closeClicked();
        }
        else if (action.equals(__BUTTON_DISPLAY_VIEW)) {
                // Display the view for the selected view...
		// Treat as if a view choice has been clicked.  There is
		// always something selected!
		viewJComboBoxClicked();
        }
        else if (action.equals(__BUTTON_EXPORT)) {
		try {
			String[] eff = 
				HydroBase_GUI_Util.getExportFilenameAndFormat(
				this, 
				HydroBase_GUI_Util.getFormatsAndExtensions());

			if (eff == null) {
				return;
			}

			int format = Integer.valueOf(eff[1]).intValue();
	 		// First format the output...
			if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
				List<String> outputStrings = formatOutput(format);
	 			// Now export, letting the user decide the file.
				HydroBase_GUI_Util.export(this, eff[0], outputStrings);
			}
			else {
                		char delim = HydroBase_GUI_Util
					.getDelimiterForFormat(format);	
				__worksheet.saveToFile(eff[0], "" + delim);
			}
		} 
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}	
        }
        else if (action.equals(__BUTTON_GET_DATA)) {
		try {
	                submitQuery();
		}
		catch (Exception e) {
			Message.printWarning (2, routine, e);
		}
        }
        else if (action.equals(__BUTTON_HELP)) {
        }
        else if (action.equals(__BUTTON_PRINT)) {
		try {
			SelectFormatTypeJDialog d = 
				new SelectFormatTypeJDialog(this, 
				HydroBase_GUI_Util.getFormats());
			int format = d.getSelected();
			if (format == HydroBase_GUI_Util.CANCEL) {
				return;
			}
			d.dispose();
	 		// First format the output...
			List<String> outputStrings = formatOutput(format);
	 		// Now print...
			PrintJGUI.print(this, outputStrings, 8);
		}
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}		
        }
        else if (action.equals(__BUTTON_SELECT_ON_MAP)) {
		selectOnMap();
        }
}


/**
This function is responsible for closing the GUI.  The GUI never actually goes
away.  It just gets set to invisible.
*/
private void closeClicked() {
	setVisible(false);

	if (__displayStructsOnly) {
		__currentStructureView
			= getCurrentlySelectedStructureView();
		if (__caller instanceof HydroBase_GUI_SetCall) {
			((HydroBase_GUI_SetCall)__caller).structureWindowClosed(
				__currentStructureView);
		}
		else if (__caller instanceof HydroBase_GUI_EditCallsBypass) {
			((HydroBase_GUI_EditCallsBypass)__caller)
				.structureWindowClosed(__currentStructureView);
		}
		
		__displayStructsOnly = false;
		__printJButton.setEnabled(true);
		__exportJButton.setEnabled(true);
		__caller = null;	
	}

	__viewJComboBox.removeAll();
	// Add empty one for sizing...
	__viewJComboBox.add(EMPTY_VIEW);
	__viewJComboBox.setMaximumRowCount(__viewJComboBox.getItemCount());
	__viewJComboBox.setEnabled(false);
	__displayViewJButton.setEnabled(false);
}

/**
Display the results of the query in the spreadsheet.  
@param results the results to display in the spreadsheet.
*/
private void displayResults(List<HydroBase_StructureView> results) 
throws Exception {

	HydroBase_TableModel_StructureGeoloc_SP tm = 
		new HydroBase_TableModel_StructureGeoloc_SP(__results, 
		__dmi);
	HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
	__worksheet.setCellRenderer(cr);
	__worksheet.setModel(tm);	
	__worksheet.setColumnWidths(tm.getColumnWidths());

	// can get some big result sets back from these queries,
	// so encourage the JVM to clean up unused memory
	System.gc();
}

/**
Used when this GUI is opened from a call GUI so that structs are displayed only
and the close button is instead used as an OK button to return the struct
back to the calling Calls GUI.
*/
public void displayStructsOnly(JFrame caller) {
	__caller = caller;
	__displayStructsOnly = true;
	__printJButton.setEnabled(false);
	__exportJButton.setEnabled(false);
        __closeJButton.setText(__BUTTON_OK);
	if (__displayStructsOnly) {
		__closeJButton.setEnabled(false);
	}	
}

/**
Format output of the worksheet for export.
@param format format flag definied in HydroBase_GUI_Util (SCREEN_VIEW, 
SUMMARY) or a flag that specifies the delimiter to use.
@return a formatted Vector for exporting, printing, etc..
*/
private List<String> formatOutput(int format) {
	int size = __worksheet.getRowCount();
	if (size == 0) {
        	return new Vector<String>();
	}

	List<String> v = new Vector<String>(size, 100);

	if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
        	// Just export in a reasonable format...
		String cols = 
			StringUtil.formatString(__worksheet.getColumnName(0, 
				true), "%-3.3s")	// wd
			+ StringUtil.formatString(__worksheet.getColumnName(1, 
				true), "%-9.9s")	// id
			+ StringUtil.formatString(__worksheet.getColumnName(2, 
				true), "%-41.41s")	// name
			+ StringUtil.formatString("PM", "%-3.3s")	// pm	
			+ StringUtil.formatString("TS", "%-5.5s")	// ts
			+ StringUtil.formatString("RNG", "%-5.5s")	// rng
			+ StringUtil.formatString("SEC", "%-2.2s") + " " // sec
			+ StringUtil.formatString("HSEC", "%-1.1s") + " "	
				// half sec
			+ StringUtil.formatString("Q160", "%-5.5s")	// q160
			+ StringUtil.formatString("Q40", "%-4.4s")	// q40
			+ StringUtil.formatString("Q10", "%-4.4s")	// q10
			+ StringUtil.formatString("WATER SOURCE", "%-41.41s")	
				// water source
			+ StringUtil.formatString("STRUCTURE TYPE", "%-12.12s")	
				+ " "
				// structure type
			+ StringUtil.formatString(__worksheet.getColumnName(8,	
				true), "%-1.1s") + " "
			+ StringUtil.formatString(__worksheet.getColumnName(9,	
				true), "%-1.1s") + " "			
			+ StringUtil.formatString(__worksheet.getColumnName(10,
				true), "%-12.12s");	// dcr cap
		v.add(cols);
		v.add(
			"___________________________________________" +
			"___________________________________________" +
			"___________________________________________" +
			"__________________________________");

		HydroBase_StructureView data;
		String ts;
		String rng;
		String sec;
		String id;
		String wd;
		String dcr;
		String transbsn;

        	for (int i = 0; i < size; i++) {
			data = (HydroBase_StructureView)
				__worksheet.getRowData(i);
                	// Break into parts...
                	id = "";
			wd = "";
			wd = StringUtil.formatString( data.getWD(), "%2d");
            id = StringUtil.formatString( data.getID(), "%8d");
			if (DMIUtil.isMissing(data.getTS()) || HydroBase_Util.isMissing(data.getTS())) {
				ts = "    ";
			}
			else {
				ts = StringUtil.formatString(data.getTS(), "%-4.4s");
			}
			if (DMIUtil.isMissing(data.getRng()) || HydroBase_Util.isMissing(data.getRng())) {
				rng = "    ";
			}
			else {
				rng = StringUtil.formatString(data.getRng(), "%-4.4s");
			}
			if (DMIUtil.isMissing(data.getSec()) || HydroBase_Util.isMissing(data.getSec())) {
				sec = "  ";
			}
			else {
				sec = StringUtil.formatString(data.getSec(), "%-2.2s");
			}
			if (DMIUtil.isMissing(data.getDcr_capacity()) || HydroBase_Util.isMissing(data.getDcr_capacity())) {
				dcr = "            ";
			}
			else {
				dcr = StringUtil.formatString( data.getDcr_capacity(), "%12.12s");
			}
			if (DMIUtil.isMissing(data.getTransbsn()) || HydroBase_Util.isMissing(data.getTransbsn())) {
				transbsn = " ";
			}
			else {
				transbsn = StringUtil.formatString( data.getTransbsn(), "%8d").trim();
			}
			
                	v.add(
                        	  wd + " "
                        	+ id + " "
                        	+ StringUtil.formatString( data.getStr_name(),"%-40.40s") + " "
                        	+ StringUtil.formatString( data.getPM(), "%-2.2s") + " "
				+ ts + " "
				+ rng + " "
				+ sec + " "
				+ StringUtil.formatString( data.getSeca(), "%-1.1s") + " "
				+ StringUtil.formatString( data.getQ160(), "%-2.2s") + "   "
				+ StringUtil.formatString( data.getQ40(), "%-2.2s") + "  "
				+ StringUtil.formatString( data.getQ10(), "%-2.2s") + "  "
               	+ StringUtil.formatString( data.getStrname(), "%-40.40s") + " "
				+ StringUtil.formatString( __dmi.getStructureTypeDescription( data.getStr_type()), "%-12.12s") + " "
				+ StringUtil.formatString( data.getCiu(), "%-1.1s") + " " + transbsn + " " + dcr);
        	}
	}
	else {  
		/*
        	// Just export in a reasonable format...
                char delim = HydroBase_GUI_Util.getDelimiterForFormat(format);	
		int colCount = __worksheet.getColumnCount();
		
		boolean useComma = false;
		if (delim == ',') {
			useComma = true;
		}
		String cols = "";
		for (int i = 0; i < colCount; i++) {
			cols += __worksheet.getColumnName(i, true) + "" + delim;
		}
		v.add(cols);

		int selected = __worksheet.getSelectedRows().length;

		for (int i = 0; i < size; i++) {
			String s = "";			
			String o;
			String d = "";		

			if (__worksheet.rowIsSelected(i) || selected == 0) {
				for (int j = 0; j < colCount; j++) {
					o =__worksheet.getValueAtAsString(i, j);
					if (useComma && o.indexOf(",") > -1) {
						o = "\"" + o + "\"";
					}
					s += o + delim;
				}
				v.addElement(s);			
			}
		}
		*/
	}
	return v;
}

/**
This function performs additional queries as determined by the 
the selected display choices.
@param view Selected view JComboBox.
@param data HBStructureLocation object for the selected row.
*/
private void getDisplayJComboBox(String view, 
HydroBase_StructureView data) {
	String routine = __CLASS + ".getDisplayJComboBox";
        // set the structure name and structure number
        String strucName = data.getStr_name();                         
        int strucNum = data.getStructure_num();

	JGUIUtil.setWaitCursor(this, true);
   
   	try {

        if (view.equals(SUMMARY)) {
		HydroBase_Report_StructureSummary structureSummary = 
			new HydroBase_Report_StructureSummary(__dmi, strucNum);
		List<String> reportVector = structureSummary.getReport();
		PropList props = new PropList("QInfoReport");
		props.set("HelpKey", "true");

		String title = "Structure Data - Structure Summary - ";
		if ((JGUIUtil.getAppNameForWindows() == null) 
			|| JGUIUtil.getAppNameForWindows().equals("")) {}
		else {	
//			title = JGUIUtil.getAppNameForWindows() + title;
		}			
		
		int wd = data.getWD();
		int id = data.getID();
		String name = data.getStr_name();
		
		title += HydroBase_WaterDistrict.formWDID(wd, id)
			+ " (" + name + ")";
		
		props.set("Title", title);
		props.set("DisplayTextComponent", "JTextArea");
		props.set("TotalWidth", "1000");
		props.set("PrintSize", "8");
		new ReportJFrame(reportVector, props);
        }
        else if (view.equals(MORE_STRUCTURE)) {
                new HydroBase_GUI_StructureMoreInfo(__dmi, strucName, strucNum);
        }
        else if (view.equals(LOCATION)) {
                new HydroBase_GUI_Location(__dmi, data);                 
        }
        else if (view.equals(OWNER_CONTACT)) {
                new HydroBase_GUI_OwnerContact(__dmi, strucName, strucNum);
        }
        else if (view.equals(IRRIGATED_ACRES)) {
                new HydroBase_GUI_IrrigatedAcres(__dmi, strucName, strucNum);
        }
        else if (view.equals(IRRIGATED_ACRES_SUMMARY)) {
                new HydroBase_GUI_IrrigatedAcresSummary(__dmi, strucName, 
			strucNum);
        }
        else if (view.equals(HEADGATE)) {
                new HydroBase_GUI_Headgate(__dmi, strucName, strucNum);
        }
        else if (view.equals(DAM)) {
                new HydroBase_GUI_Dam(__dmi, strucName, strucNum);
        }
        else if (view.equals(SMALL_DAM)) {
                new HydroBase_GUI_ExemptDam(__dmi, strucName, strucNum);
        }
        else if (view.equals(REACH)) {
                new HydroBase_GUI_MinimumFlowReach(__dmi, strucName, strucNum);
        }
        else if (view.equals(RESERVOIR)) {
                new HydroBase_GUI_ReservoirData(__dmi, strucName, strucNum);
        }
        else if (view.equals(RES_MEASUREMENTS)) {
                new HydroBase_GUI_ReservoirMeasurement(__dmi, strucNum, 
			strucName); 
        }
        else if (view.equals(WELL)) {
                new HydroBase_GUI_Well(__dmi, strucName, strucNum);
        }
        else if (view.equals(WR_TRANS)) {
                new HydroBase_GUI_WaterRightsQuery(__dmi, strucNum, __parent, 
			__geoview_ui, true);
        }
        else if (view.equals(WR_NET)) {
                new HydroBase_GUI_WaterRightsQuery(__dmi, strucNum, __parent, 
			__geoview_ui, false);
        }
        else if (view.equals(STORAGE)) {
                new HydroBase_GUI_DailyWC(__dmi, strucNum, strucName);
        }
        else if (view.equals(TIME_SERIES)) {
                new HydroBase_GUI_StructureDetail(__dmi, data);
        }	
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error opening GUI.");
		Message.printWarning(2, routine, e);
	}
	JGUIUtil.setWaitCursor(this, false);
}

/**
This function generates the view list depending on the the type 
of structure selected in the table object.  This list will refresh
each time a row is selected within the MultiList object.
*/
private void getViewList() {
	JGUIUtil.setWaitCursor(this, true);
        String statusString = "Generating view list...";
        Message.printStatus(10, __CLASS + ".getViewList()",
		statusString);
        __statusJTextField.setText(statusString);
	
	int rowCount = __worksheet.getSelectedRowCount();
	if (rowCount > 1) {
		__viewJComboBox.setEnabled(false);
		__displayViewJButton.setEnabled(false);
		__selectOnMapJButton.setEnabled(false);
		if (__displayStructsOnly) {
			__closeJButton.setEnabled(false);
		}

	        statusString = "Cannot generate view list; more than one "
			+ "structure selected";
	        Message.printStatus(10, 
			__CLASS + ".getViewList()",
			statusString);
	        __statusJTextField.setText(statusString);
		JGUIUtil.setWaitCursor(this, false);		
		return;
	}

	int row = __worksheet.getSelectedRow();
	
	if (row == -1) {
		// nothing is selected
		ready();
		return;
	}

	List<String> items = null;

	HydroBase_StructureView data = (HydroBase_StructureView)__worksheet.getRowData(row);

	if (data == null) {
		ready();
		return;
	}

	items = getStructureViews(data);

        // add the items JComboBox object
        __viewJComboBox.removeAll();
        int size = items.size();
	String curItem = "";
        for (int i = 0; i < size; i++) {
        	curItem = items.get(i).toString().trim();
                __viewJComboBox.add(curItem);                            
	}

	// Try to set to the last view that was selected...
	if (__viewJComboBox.contains(__lastSelectedView)) {
		__viewJComboBox.select(__lastSelectedView);
	}
	else {
		__viewJComboBox.select(SUMMARY);
	}
	__viewJComboBox.setMaximumRowCount(__viewJComboBox.getItemCount());
	__viewJComboBox.setEnabled(true);
	__displayViewJButton.setEnabled(true);

	JGUIUtil.setWaitCursor(this, false);
        statusString = "Finished generating view list.";
        Message.printStatus(10, __CLASS + ".getViewList()", statusString);
        __statusJTextField.setText(statusString);
}

/**
This function generates a list containing views relevant to the structure type.
@param type the selected structure type.
@return a list of structure views.
*/
public List<String> getStructureViews(String type) {
	List<String>items = new Vector<String>(10, 5);	// Relevant Structure displays
	boolean	isHeadgate = 	false;
	boolean isJD = 		false;
	boolean	isNJD = 	false;
	boolean	isReservoir = 	false;
	boolean	isMFR = 	false;
	boolean	isWell = 	false;

	// set flags
        if (type.equals(__H)) {
		isHeadgate = true;
        }
        else if (type.equals(__JD)) {
		isJD = true;
        }
        else if (type.equals(__MFR)) {
		isMFR = true;
        }
        else if (type.equals(__NJD)) {
		isNJD = true;
        }
        else if (type.equals(__R)) {
		isReservoir = true;
        }
        else if (type.equals(__W)) {
		isWell = true;
        }

	// arrange alphabetically
	if (isHeadgate || isReservoir || isWell) {
               items.add(TIME_SERIES);
	}
	if (isHeadgate) {
//		items.add(HEADGATE);
	}
	if (isReservoir) {
                items.add(RES_MEASUREMENTS); 
	}
//	items.add(IRRIGATED_ACRES_SUMMARY);
        items.add(IRRIGATED_ACRES);
	if (isJD) {
                items.add(DAM); 
	}
   	items.add(LOCATION);
	if (isMFR) {
                items.add(REACH);
	}
        items.add(MORE_STRUCTURE);
	if (isNJD) {
                items.add(SMALL_DAM);
	}
  	items.add(OWNER_CONTACT);
	if (isReservoir) {
                items.add(RESERVOIR); 
	}
	
	items.add(SUMMARY);

	if (!isNJD && !isJD) {
                items.add(WR_TRANS);
                items.add(WR_NET);
	}
	if (isWell) {
//		items.add(WELL);
	}

        return items;
}

/**
This function generates a list containing views relevant to the structure
type.
@param data selected view type.
@return a list of structure views.
*/
public List<String> getStructureViews(HydroBase_StructureView data) {
	String type = __dmi.getStructureTypeDescription(data.getStr_type());
        return getStructureViews(type);
}

/**
Get the list of AppLayerType that correspond to the GUI in its current state.
@return list of String for AppLayerType that should be considered.
*/
private List<String> getVisibleAppLayerType() {
	int[] rows = __worksheet.getSelectedRows();
	if (rows == null || rows.length == 0) {
		return new Vector<String>();
	}
	
	HydroBase_StructureView sv = null;
	int size = rows.length; 
	Object o = null;
	String type = null;
	List<String> appLayerTypes = new Vector<String>();

	boolean reservoir = false;
	boolean diversion = false;
	boolean dam = false;
	boolean well = false;

	for (int i = 0; i < size; i++) {
		o = __worksheet.getRowData(rows[i]);
		sv = (HydroBase_StructureView)o;
		type = sv.getStr_type();
		
		if (type.equals("R") && !reservoir) {
			appLayerTypes.add("Reservoir");
			reservoir = true;
		}
		else if (type.equals("H") && !diversion) {
			appLayerTypes.add("Diversion");
			diversion = true;
		}
		else if (type.equals("NJD") && !dam) {
			appLayerTypes.add("Dam");
			dam = true;
		}
		else if (type.equals("JD") && !dam) {
			appLayerTypes.add("Dam");
			dam = true;
		}
		else if (type.equals("WU") && !well) {
			appLayerTypes.add("Well");
			well = true;
		}
		else if (type.equals("W") && !well) {
			appLayerTypes.add("Well");
			well = true;
		}
		else if (type.equals("WP") && !well) {
			appLayerTypes.add("Well");
			well = true;
		}		
	}

	return appLayerTypes;
}

/**
Handle the label redraw event from another GeoView (likely a ReferenceGeoView).
Do not do anything here because we assume that application code is setting
the labels.  Only returns null.
@param record Feature being draw.
@return null.
*/
public String geoViewGetLabel(GeoRecord record) {
	return null;
}

/**
Does nothing.
@param devPoint Coordinates of mouse in device coordinates (pixels).
@param dataPoint Coordinates of mouse in data coordinates.
*/
public void geoViewMouseMotion(GRPoint devPoint, GRPoint dataPoint) {}

/**
Does nothing.  
@param devLimits Limits of select in device coordinates (pixels).
@param dataLimits Limits of select in data coordinates.
@param selected list of selected GeoRecord.  
*/
public void geoViewInfo(GRShape devLimits, GRShape dataLimits, List<GeoRecord> selected) {}

/**
Does nothing.  
@param devLimits Limits of select in device coordinates (pixels).
@param dataLimits Limits of select in data coordinates.
@param selected Vector of selected GeoRecord.  
*/
public void geoViewInfo(GRPoint devLimits, GRPoint dataLimits, List<GeoRecord> selected) {}

/**
Does nothing.  
@param devLimits Limits of select in device coordinates (pixels).
@param dataLimits Limits of select in data coordinates.
@param selected list of selected GeoRecord.  
*/
public void geoViewInfo(GRLimits devLimits, GRLimits dataLimits, List<GeoRecord> selected) {}

/**
If a selection is made from the map, query the database for region that was
selected.  It is assumed that this GUI is created in the main application
even if it is invisible.  This will ensure that the method is called as a
listener from the GeoView.
@param devLimits Limits of select in device coordinates(pixels).
@param dataLimits Limits of select in data coordinates.
@param selected Vector of selected GeoRecord.  Currently ignored.
*/
public void geoViewSelect(GRShape devLimits, GRShape dataLimits, List<GeoRecord> selected, boolean append) {
	// Figure out which app layer types are selected.  If one that is
	// applicable to this GUI, execute a query...
	List<String> appLayerTypes = __geoview_ui.getGeoViewJPanel().getLegendJTree().getSelectedAppLayerTypes(true);
	int size = appLayerTypes.size();
	String appLayerType;
	boolean viewNeeded = false;
	String routine = __CLASS + ".geoViewSelect()";

	// REVISIT SAM 2006-01-23
	// The geographic query should be in addition to the filters...
	//__filterJPanel.clearInput();
	
	for (int i = 0; i < size; i++) {
		appLayerType = (String)appLayerTypes.get(i);
		if (appLayerType.equalsIgnoreCase("Diversion")) {
			viewNeeded = true;
		}
		else if (appLayerType.equalsIgnoreCase("InstreamFlow")) {
			viewNeeded = true;
		}
		else if (appLayerType.equalsIgnoreCase("Reservoir")) {
			viewNeeded = true;
		}
		else if (appLayerType.equalsIgnoreCase("Well")) {
			viewNeeded = true;
		}
	}
	if (!viewNeeded) {
		// Just return with no action...
		return;
	}

	if (!isVisible()) {
		// If not visible, set to visible if the selected map layers
		// apply to this window...
		setVisible(true);
	}

	if (devLimits.type == GRShape.LIMITS) {
		__mapQueryLimits = (GRLimits)dataLimits;
	}
	else if (devLimits.type == GRShape.POINT) {
		// Do not allow a point to result in a query...
		__mapQueryLimits = null;
		return;
	}

	try {
		__geoViewSelectQuery = true;
		submitQuery();
	}
	catch (Exception e) {
		Message.printWarning (2, routine, e);
	}
	__mapQueryLimits = null;
}

/**
If a selection is made from the map, query the database for region that was
selected.  It is assumed that this GUI is created in the main application
even if it is invisible.  This will ensure that the method is called as a
listener from the GeoView.
@param devLimits Limits of select in device coordinates(pixels).
@param dataLimits Limits of select in data coordinates.
@param selected Vector of selected GeoRecord.  Currently ignored.
*/
public void geoViewSelect(GRPoint devLimits, GRPoint dataLimits, 
		List<GeoRecord> selected, boolean append) {
	geoViewSelect((GRShape)devLimits, (GRShape)dataLimits, selected, append);
}

/**
If a selection is made from the map, query the database for region that was
selected.  It is assumed that this GUI is created in the main application
even if it is invisible.  This will ensure that the method is called as a
listener from the GeoView.
@param devLimits Limits of select in device coordinates(pixels).
@param dataLimits Limits of select in data coordinates.
@param selected Vector of selected GeoRecord.  Currently ignored.
*/
public void geoViewSelect(GRLimits devLimits, GRLimits dataLimits,
		List<GeoRecord> selected, boolean append) {
	geoViewSelect((GRShape)devLimits, (GRShape)dataLimits, selected, 
		append);
}

/**
Does nothing.
@param devLimits Limits of zoom in device coordinates (pixels).
@param dataLimits Limits of zoom in data coordinates.
*/
public void geoViewZoom(GRShape devLimits, GRShape dataLimits) {}

/**
Does nothing.
@param devLimits Limits of zoom in device coordinates (pixels).
@param dataLimits Limits of zoom in data coordinates.
*/
public void geoViewZoom (GRLimits devlim, GRLimits datalim ) {}

/**
Returns the current structure geoloc object.
@return the current structure geoloc object.
*/
public HydroBase_StructureView getCurrentStructureView() {
	return __currentStructureView;
}

/**
Returns the currently-selected structure geoloc object.
@return the currently-selected structure geoloc object.
*/
private HydroBase_StructureView getCurrentlySelectedStructureView() {
	String routine = "getCurrentlySelectedStructureView";
	if (__worksheet.getSelectedRowCount() > 1) {
        	Message.printWarning(10, routine, 
			"Nothing being returned because one and only one " +
			"structure must be selected.");
		ready();
                return null;		
	}

       	int row = __worksheet.getSelectedRow();

        // nothing has been selected. post warning and return.
	if (row == -1) {
        	Message.printWarning(10, routine, 
			"Nothing being returned because one and only one " +
			"structure must be selected.");
		ready();
                return null;
	}

	HydroBase_StructureView data 
		= (HydroBase_StructureView)__worksheet.getRowData(row);
	return data;
}

/**
Responds to key pressed events.
@param event the event that happened.
*/
public void keyPressed(KeyEvent event) {
	int code = event.getKeyCode();
	String routine = __CLASS + ".keyPressed()";
	
	if (code == KeyEvent.VK_ENTER) {
		try {
		        submitQuery();
		}
		catch (Exception e) {
			Message.printWarning (2, routine, e);
		}
	}
}

/**
Does nothing.
@param event the event that happened.
*/
public void keyReleased(KeyEvent event) {}

/**
Checks to see if multiple rows are selected after a key press on the worksheet.
In particular, checks for Control-A events.
@param event the event that happened.
*/
public void keyTyped(KeyEvent event) {
	if (event.getSource() != __worksheet) {
		return;
	}

	// If more than row is selected (aka, from Control-A), deselect the
	// display view components.
	int row = __worksheet.getSelectedRowCount();
	if (row == 1) {
		if (__displayStructsOnly) {
			__closeJButton.setEnabled(true);
			__displayViewJButton.setEnabled(false);
			__viewJComboBox.setEnabled(false);			
		}	
		else {
			__displayViewJButton.setEnabled(true);
		}
	}
	else {
		if (__displayStructsOnly) {
			__closeJButton.setEnabled(false);
		}
		__displayViewJButton.setEnabled(false);
	}	
}

/**
Responds to mouse clicked events.
@param event the event that happened.  
*/
public void mouseClicked(MouseEvent event) {
 	// Show the location builer if the double click occurs
	// and a locatation query is selected.
	int row = __worksheet.getSelectedRowCount();
	if (row == 1) {
		if (__displayStructsOnly) {
			__closeJButton.setEnabled(true);
			__displayViewJButton.setEnabled(false);
			__viewJComboBox.setEnabled(false);			
		}	
		else {
			__displayViewJButton.setEnabled(true);
		}
	}
	else {
		if (__displayStructsOnly) {
			__closeJButton.setEnabled(false);
		}
		__displayViewJButton.setEnabled(false);
	}
}

/**
Does nothing.
@param event the event that happened.
*/
public void mouseEntered(MouseEvent event) {}

/**
Does nothing.
@param event the event that happened.
*/
public void mouseExited(MouseEvent event) {}

/**
Responds to mouse pressed events.
@param event the event that happened.
*/
public void mousePressed(MouseEvent event) {
	Object ob = event.getComponent();
	if (ob == __worksheet) {
		int row = __worksheet.getSelectedRow();
		if (row > -1) {
			if (__displayStructsOnly) { 
				__displayViewJButton.setEnabled(false);
				__viewJComboBox.setEnabled(false);
			}
			else {
	                	getViewList();
			}
		}
	}
	else {	
		int numFilters = __filterJPanel.getNumFilterGroups();
		for (int i = 0; i < numFilters; i++) {
			if (event.getSource() == __filterJPanel.getInputFilter(
				i).getInputComponent()) {
				HydroBase_GUI_Util.buildLocation(this,
					(JTextField)event.getSource());
			}
		}
	}
}
/**
Responds to mouse released events. 
@param event the event that happened.
*/
public void mouseReleased(MouseEvent event) {
	int rowCount = __worksheet.getSelectedRowCount();
	if (rowCount > 1) {
		__viewJComboBox.setEnabled(false);
		__displayViewJButton.setEnabled(false);
		if (__displayStructsOnly) {
			__closeJButton.setEnabled(false);
		}		
		if (__geoview_ui.isMapVisible()) {
			__selectOnMapJButton.setEnabled(true);
		}
		else {
			__selectOnMapJButton.setEnabled(false);
		}

	        String statusString ="Cannot generate view list; more than one "
			+ "structure selected";
	        Message.printStatus(10, __CLASS + ".getViewList()",
			statusString);
	        __statusJTextField.setText(statusString);
		JGUIUtil.setWaitCursor(this, false);		
	}
	else if (rowCount == 1) {
		if (__displayStructsOnly) { 
			__displayViewJButton.setEnabled(false);
			__viewJComboBox.setEnabled(false);
		}	
		else {
			__viewJComboBox.setEnabled(true);
			__displayViewJButton.setEnabled(true);
		}
		if (__geoview_ui.isMapVisible()) {
			__selectOnMapJButton.setEnabled(true);
		}
		else {
			__selectOnMapJButton.setEnabled(false);
		}
	}
}

/**
Reset the GUI for user interaction.
*/
private	void ready() {
	__statusJTextField.setText("Ready");
	JGUIUtil.setWaitCursor(this, false);
}

/**
Select the items in the list on the map.  This is done by putting together a
list of String, each of which has "wd,id".  These field names are set in the
GeoView Project as AppJoinField="wd,id".
*/
private void selectOnMap() {
	int size = __worksheet.getRowCount();
	List<String> idlist = new Vector<String>(size);
        __statusJTextField.setText("Selecting and zooming to structures on "
		+ "map.  Please wait...");
	JGUIUtil.setWaitCursor(this, true);
	
       	int[] rows = __worksheet.getSelectedRows();	
	if (rows == null || rows.length == 0) {
		ready();
		return;
	}
	else {	
		HydroBase_StructureView sv = null;
		for (int i = 0; i < rows.length; i++) {
			sv = (HydroBase_StructureView)
				__worksheet.getRowData(rows[i]);
			idlist.add(sv.getWD() + "," 
				+ sv.getID());
		}
	}

	// Base layers are always visible...
	List<String> enabledAppLayerTypes = getVisibleAppLayerType();
	enabledAppLayerTypes.add("BaseLayer");
	__geoview_ui.getGeoViewJPanel().enableAppLayerTypes(enabledAppLayerTypes, 
		false);
	__statusJTextField.setText(
		"Map shows base layers and structure layers.  Ready.");
	
	// Select the features, searching only selected structure types, and
	// zoom to the selected shapes...
	List<String> appLayerTypes = getVisibleAppLayerType();
	List<GeoRecord> matchingFeatures =__geoview_ui.getGeoViewJPanel().selectAppFeatures(
		appLayerTypes, idlist, true, .05, .05);
	int matches = 0;
	if (matchingFeatures != null) {
		matches = matchingFeatures.size();
	}
	if (matches == 0) {
		Message.printWarning(1, 
			__CLASS + ".selectOnMap",
			"No matching features were found in map data.\n" +
			"This may be because the location data are incomplete "
			+ "or\nbecause no suitable GIS data layers are being "
			+ "used.");
	}
	else if (matches != idlist.size()) {
		Message.printWarning(1, 
			__CLASS + ".selectOnMap",
			"" + matches + " matching features found in map "
			+ "(looking for " + idlist.size() + ").\nThis may "
			+ "be because of incomplete location data.");
	}
        __statusJTextField.setText("Map is zoomed to selected structures.  "
		+ "Ready.");
	JGUIUtil.setWaitCursor(this, false);
}

/**
Set a WDID for a query.  This overrides what normally would come up in the
GUI in interactive mode.  Call submitQuery()to execute the query for the
WDID.
*/
public void setQueryWDID(String wdid) {
	String routine = __CLASS + ".setQueryWDID";
	
	// Split the WDID into its parts...
	try {
	int[] parts = HydroBase_WaterDistrict.parseWDID(wdid);
	if (parts == null) {
		Message.printWarning(1, 
			__CLASS + ".setQueryWDID",
			"WDID \"" + wdid +
			"\" is not valid.  Cannot change query settings.");
		return;
	}

	// Set the water district filter to the selected district...
	String s = HydroBase_GUI_Util.setWaterDistrictJComboBox(__dmi,
		__waterDistrictJComboBox, ("" + parts[0]), true, false, true);
	__waterDistrictJComboBox.select(s);

	// Set the Where to Structure ID...

	// REVISIT (JTS - 2005-01-10)
	// select the STRUCTURE ID from the filter

	// Set the Is to the ID part of what was passed in...

	__filterJPanel.setInputFilter(0, "Structure ID;Equals;" + parts[1], 
		";");

	}
	catch (Exception e) {
		Message.printWarning (2, routine, e);
	}	
}

/**
Sets up the GUI.
*/
private void setupGUI(boolean isVisible) {
	Message.setTopLevel(this);

	addWindowListener(this);
	addKeyListener(this);
        
        // objects used throughout the GUI layout
	int buffer = 4;		// New inset.
        Insets insetsNNNR = new Insets(0,0,0,buffer);
        Insets insetsNLNN = new Insets(0,buffer,0,0);
        Insets insetsNLBR = new Insets(0,buffer,buffer,buffer);
        Insets insetsTLNR = new Insets(buffer,buffer,0,buffer);
        GridBagLayout gbl = new GridBagLayout();
        
        // North JPanel
        JPanel topJPanel = new JPanel();
        topJPanel.setLayout(new BorderLayout());
        getContentPane().add("North", topJPanel);
        
        // North: West JPanel
        JPanel topLeftJPanel = new JPanel();
        topLeftJPanel.setLayout(gbl);
        topJPanel.add("West", topLeftJPanel);
        
        JGUIUtil.addComponent(topLeftJPanel, new JLabel("Query Options:"), 
		0, 0, 2, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        
        JGUIUtil.addComponent(topLeftJPanel, new JLabel("Div/Dist:"), 
		0, 1, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
        
        __waterDistrictJComboBox = new SimpleJComboBox();
        JGUIUtil.addComponent(topLeftJPanel, __waterDistrictJComboBox, 
		1, 1, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        
 	__filterJPanel = new HydroBase_GUI_Structure_InputFilter_JPanel(__dmi, 
		this);
	__filterJPanel.addEventListeners(this);
        JGUIUtil.addComponent(topLeftJPanel, __filterJPanel, 
		0, 2, 2, 3, 0, 0, insetsNLNN, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);
        
        __getDataJButton = new JButton(__BUTTON_GET_DATA);
	__getDataJButton.addActionListener(this);
	__getDataJButton.setToolTipText("Read data from database.");
        JGUIUtil.addComponent(topLeftJPanel, __getDataJButton, 5, 3, 
		1, 3, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH);

        // Center JPanel
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(gbl);
        getContentPane().add("Center", centerJPanel);        
        
        __tableJLabel = new JLabel(HydroBase_GUI_Util.LIST_LABEL);
        JGUIUtil.addComponent(centerJPanel, __tableJLabel, 1, 1, 
		7, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        
	PropList p = new PropList("HydroBase_GUI_StructureQuery.JWorksheet");
	p.add("JWorksheet.ShowRowHeader=true");
	p.add("JWorksheet.AllowCopy=true");
	p.add("JWorksheet.ShowPopupMenu=true");
	p.add("JWorksheet.SelectionMode=ExcelSelection");
	p.add("JWorksheet.OneClickRowSelection=true");
	JScrollWorksheet jsw = new JScrollWorksheet(0, 0, p);
        __worksheet = jsw.getJWorksheet();
	__worksheet.setHourglassJFrame(this);
	__worksheet.addMouseListener(this);
	__worksheet.addKeyListener(this);
		
        JGUIUtil.addComponent(centerJPanel, jsw,
		1, 2, 7, 3, 1, 1, insetsNLBR, GridBagConstraints.BOTH, GridBagConstraints.WEST);
        
        //Bottom JPanel(Consist of three more JPanels)
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);
        
        // Bottom: North JPanel
        JPanel bottomNorthJPanel = new JPanel();
        bottomNorthJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add("North", bottomNorthJPanel);

	JPanel displayJPanel = new JPanel();
	displayJPanel.setLayout(gbl);
	bottomNorthJPanel.add(displayJPanel);

	__displayViewJButton = new SimpleJButton("Display View:",
		__BUTTON_DISPLAY_VIEW, this);
	__displayViewJButton.setToolTipText("Display the selected view for "
		+ "the selected structure.");
        JGUIUtil.addComponent(displayJPanel, __displayViewJButton, 
		0, 0, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
        
        __viewJComboBox = new SimpleJComboBox();
	__viewJComboBox.setPrototypeDisplayValue(EMPTY_VIEW);
	// Add to get size right...
        JGUIUtil.addComponent(displayJPanel, __viewJComboBox, 
		1, 0, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
      		        
        // Bottom: Center JPanel
        JPanel bottomCenterJPanel = new JPanel();
        bottomCenterJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add("Center", bottomCenterJPanel);
                
        __selectOnMapJButton = new JButton(__BUTTON_SELECT_ON_MAP);
	__selectOnMapJButton.setToolTipText("Select the selected structure on "
		+ "the GIS map.");
        __selectOnMapJButton.setEnabled(false);	// Until data selected
	__selectOnMapJButton.addActionListener(this);
        bottomCenterJPanel.add(__selectOnMapJButton);

        __printJButton = new JButton(__BUTTON_PRINT);
	__printJButton.setToolTipText("Print form data.");
        __printJButton.setEnabled(false);
	__printJButton.addActionListener(this);
        bottomCenterJPanel.add(__printJButton);
        
        __exportJButton = new JButton(__BUTTON_EXPORT);
	__exportJButton.setToolTipText("Export data to a file.");
	__exportJButton.addActionListener(this);
	__exportJButton.setEnabled(false);
        bottomCenterJPanel.add(__exportJButton);
        
        __closeJButton = new JButton(__BUTTON_CLOSE);
	__closeJButton.setToolTipText("Close form.");
	__closeJButton.addActionListener(this);
        bottomCenterJPanel.add(__closeJButton);
        
        // Bottom: South JPanel
        JPanel bottomSouthJPanel = new JPanel();
        bottomSouthJPanel.setLayout(gbl);
        bottomJPanel.add("South", bottomSouthJPanel);
        
        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomSouthJPanel, __statusJTextField, 0, 1, 
		10, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        
	__results = new Vector<HydroBase_StructureView>();
	
        // JFrame settings
	if (	(JGUIUtil.getAppNameForWindows() == null) ||
		JGUIUtil.getAppNameForWindows().equals("") ) {
		setTitle ( "Structure Data - Query" );
	}
	else {	setTitle( JGUIUtil.getAppNameForWindows() +
		" - Structure Data - Query" );
	}			
        pack();
	setSize(770, 500);
	JGUIUtil.center(this);

	// SAM FIGURED OUT HOW TO MAKE THE FOLLOWING WORK.  LET'S SEE HOW IT
	// GOES AND THEN REMOVE THE FOLLOWING.
	// place the following so that pack will not affect the original
	// sizing above.
	//__viewJComboBox.removeAll();
	// Disable until we select something...
	__viewJComboBox.setEnabled(false);
	__displayViewJButton.setEnabled(false);

	// We want this GUI to listen to the map.  If no map is used no
	// listener calls will be generated...

	__geoview_ui.getGeoViewJPanel().getGeoView().addGeoViewListener(this);

	// SAM reworked the GUI so that even at expanded size it works OK.
        setVisible(isVisible);
}

/**
Show/hide the frame.  If district preferences have changed, they are reset here.
@param state true if showing the frame, false if hiding it.
*/
public void setVisible(boolean state) {
	String routine = __CLASS + ".setVisible()";
	if (state) {
		JGUIUtil.setWaitCursor(this, true);
		// SAMX only reset the choices if they have changed - the user
		// may want to jump back and forth with existing settings!
               	//__worksheet.clear();
		ready();
               	__tableJLabel.setText(HydroBase_GUI_Util.LIST_LABEL);
		// REVISIT (JTS - 2005-01-10)
		// select STRUCTURE NAME from the filter panel

		try {
			HydroBase_GUI_Util.setWaterDistrictJComboBox(__dmi, 
				__waterDistrictJComboBox, null, true, false,
				true);
		}
		catch (Exception e) {
			Message.printWarning (2, routine, e);
		}
		if (__worksheet.getRowCount() > 0) {
			__printJButton.setEnabled(true);
			__exportJButton.setEnabled(true);
		}
		else {	
			__printJButton.setEnabled(false);
			__exportJButton.setEnabled(false);
		}
		if (__displayStructsOnly) {
			__closeJButton.setEnabled(false);
		}
		if (__geoview_ui.isMapVisible() && (__worksheet.getRowCount()> 0) &&
		    __geoview_ui.getGeoViewJPanel().hasAppLayerType(
		    	getVisibleAppLayerType())) {
       			__selectOnMapJButton.setEnabled(true);
		}
		else {	
			__selectOnMapJButton.setEnabled(false);
		}
               	Message.setTopLevel(this);
		JGUIUtil.setWaitCursor(this, false);
		__worksheet.clear();
	}
	// Make sure the window is visible...
	// This is Java 2...
	//setSelected(JFrame.NORMAL);
        super.setVisible(state);
}

/**
Constructs and submits a threaded query.
@return true if Query is submitted successfully, false otherwise.
*/
public void submitQuery() 
throws Exception {
        JGUIUtil.setWaitCursor(this, true);
       
	String routine = __CLASS + ".submitQuery";
        __tableJLabel.setText(HydroBase_GUI_Util.LIST_LABEL);
	String function = __CLASS + ".submitQuery()";
	__viewJComboBox.removeAll();
        __worksheet.clear();       

        //change cursor to hour glass
        String tempString = "Please Wait... Retrieving Data";
        __statusJTextField.setText(tempString);
        Message.printStatus(10, function, tempString);       
	tempString = null;

	if (!__filterJPanel.checkInput(true)) {
		ready();
		return;
	}

        String status = "Please Wait... Retrieving Data";
        Message.printStatus(10, function, status);
        __statusJTextField.setText(status);

	HydroBase_TableModel_StructureGeoloc_SP tm = 
		new HydroBase_TableModel_StructureGeoloc_SP(new Vector<HydroBase_StructureView>(), 
		__dmi);
	HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
	__worksheet.setCellRenderer(cr);
	__worksheet.setModel(tm);	

	// can get some big result sets back from these queries,
	// so encourage the JVM to clean up unused memory
	System.gc();

	StopWatch sw = new StopWatch();
	try {
		sw.start();
		__results = __dmi.readStructureGeolocList(__filterJPanel, 
			__dmi.getWaterDistrictWhereClause(
			__waterDistrictJComboBox, 
			HydroBase_GUI_Util._STRUCTURE_TABLE_NAME,
			true, true), __mapQueryLimits);
		displayResults(__results);
		sw.stop();			
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error in query.");
		Message.printWarning(2, routine, e);
	}

        int displayed = __worksheet.getRowCount();
	String plural = "";
	if (displayed != 1) {
		plural = "s";
	}

        status = "" + __results.size() + " record" + plural + " returned in " 
		+ sw.getSeconds() + " seconds";
	__tableJLabel.setText("Structure Records: " + status);
	if (__geoViewSelectQuery) {
		status += ", queried using map coordinates.";
		__geoViewSelectQuery = false;
	}
	else {
		status += ".";
	}

        __statusJTextField.setText(status);

	// set status information
	
	if (displayed == 0) {
		// Nothing to display...
		__viewJComboBox.setEnabled(false);
		__displayViewJButton.setEnabled(false);
		__selectOnMapJButton.setEnabled(false);
		__printJButton.setEnabled(false);
		__exportJButton.setEnabled(false);
	}
	else {
		__viewJComboBox.setEnabled(false);
		__displayViewJButton.setEnabled(false);
		__selectOnMapJButton.setEnabled(false);
		__printJButton.setEnabled(true);
		__exportJButton.setEnabled(true);
	}
		
		
	JGUIUtil.setWaitCursor(this, false);
}

/**
This function displays the desired view for the selected structure if
data is present in the MuiltiList object.
*/
private void viewJComboBoxClicked() {
        // initialize variables
        String function = "HBStationGUI.displayInfo()";
	// Save this so if the list is refreshed it is automatically selected.
	__lastSelectedView = __viewJComboBox.getSelected();
       	int row = __worksheet.getSelectedRow();
 
 	JGUIUtil.setWaitCursor(this, true);
	__statusJTextField.setText("Please Wait... Displaying View");

        // nothing has been selected. post warning and return.
	if (row == -1) {
        	Message.printWarning(1, function, "Select a structure from the"
			+ " list before displaying additional information.");
		ready();
		function = null;
                return;
	}
	
	HydroBase_StructureView data 
		= getCurrentlySelectedStructureView();
        getDisplayJComboBox(__viewJComboBox.getSelected().trim(), data);

	ready();
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
Responds to the window closing event and passes control over to closeClicked().
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
public void windowOpened(WindowEvent event) {}

/**
Does nothing.
*/
public void windowIconified(WindowEvent event) {}

}
