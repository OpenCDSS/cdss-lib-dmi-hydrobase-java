// HydroBase_GUI_WaterRightsQuery - Class to display the water rights form

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
// HydroBase_GUI_WaterRightsQuery - Class to display the water rights form,
//	process user input into database SQL, and display the results of
//	user queries.
//-----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
// 2003-02-27	J. Thomas Sapienza, RTi	Initial version from 
//					HBWaterRightsQueryGUI
// 2003-03-03	JTS, RTi		* Further work in moving over old 
//					  methods and variables.
//					* Started on revising, reviewing, 
//					  javadoc'ing, etc.
// 2003-03-04	JTS, RTi		* Finished the initial iteration.
//					* Added code so that all fields in 
//					  the WHERE or ORDER BY clauses 
//					  also contain the name of the table
// 2003-03-05	JTS, RTi		* Worked on code to get the NetAmts
//					  table to show up in here.
//					* Moved a lot of GUI-related code
//					  out to HydroBaseDMI.
// 2003-03-06	JTS, RTi		* Implemented code for the Transact	
//					  table.
// 2003-03-07	JTS, RTi		* Move location of Report JComboBox
//					* Added "Save As" button.
// 2003-03-10	JTS, RTi		Added code to handle resizing of GUI
//					better.
// 2003-03-11	JTS, RTi		* Added several new file filters to 
//					  the save dialog.
// 2003-03-12	JTS, RTi		Added code so that reports display.
// 2003-03-14	JTS, RTi		Started porting the export code.
// 2003-03-17	JTS, RTi		Finished porting the export code.
// 2003-03-20	JTS, RTi		Revised after SAM's review.
// 2003-03-21	JTS, RTi		Implemented __locationGUI.
// 2003-03-24	JTS, RTi		Fixed status bar update and hourglass
//					cursor issues.
// 2003-03-25	JTS, RTi		* Removed references to
//					  HydroBase_GUI_Util parent object.
// 2003-03-27	JTS, RTi		Did exact comparison with old CWRAT
//					Water Rights Query form and corrected:
//					* Secondary Sort is now initially
//					  Admin Number
// 2003-03-28	JTS, RTi		* Now using a ReportJFrame that can turn
//					  off word-wrapping.
//					* Fixed bug so that no Transaction 
//					  records could be PRINTed or EXPORTed.
//					* Title is now set with the program name
//					  as well as the form name.
// 2003-04-04	JTS, RTi		Changed GUIUtil to JGUIUtil.
// 2003-04-07	JTS, RTi		* Corrected error that would occur when 
//					  view was pressed and no records were 
//					  selected.
//					* Also corrected bug that would cause 
//					  errors if a user tried to adjust the 
//					  column order of results after creating
//					  a view report.
// 2003-05-16	JTS, RTi		* Opened up the GeoViewJPanel code.
//					* Added a CWRATMainJFrame parent (for
//					  isMapVisible()).
// 2003-05-30	JTS, RTi		Added code to copy from the table.
// 2003-06-02	JTS, RTi		Added code so an hourglass displays when
//					sorting the table
// 2003-07-28	JTS, RTi		* Updated JWorksheet code to stop using
//					  deprecated methods.
//					* Removed old JWorksheet method of
//					  enabling copy/paste.
// 2004-01-21	JTS, RTi		Changed to use the new JWorksheet method
//					of displaying a row count column.
// 2004-05-10	JTS, RTi		Activated code that was in place but
//					not used that optimizes when report
//					queries are run.
// 2004-06-22	JTS, RTi		Table strings were moved from the 
//					DMI class to HydroBase_GUI_Util so they
//					were renamed in here.
// 2004-07-12	Steven A. Malers, RTi	Make some minor changes to tool tips
//					to clarify functionality.
// 2004-07-26	JTS, RTi		* Users no longer have to scroll the
//					  "Where" combo box.
//					* Changed the view report button label
//					  slightly.
// 2005-01-11	JTS, RTi		* Converted to use InputFilters.
//					* Changed how legal locations are 
//					  passed to queries.
//					* Added finalize().
// 2005-02-08	JTS, RTi		Removed the build location button
//					and related code.
// 2005-02-09	JTS, RTi		* Removed getOrderByClause().
//					* Where and order by clauses are now 
//					  built within the 
//					  readStructureGeolocList() 
//					  method called from this GUI.  That
//					  method will call an SQL query or
//					  stored procedure query as appropriate.
// 					* Separate table models and cell 
//					  renderers are used if a stored 
//					  procedure query was run.
//					* Removed order by combo boxes.
// 2005-02-11	JTS, RTi		* The non-query by example constructor
//					  and related code has been converted to
//					  use stored procedures.
//					* Removed getWhereClause()
// 2005-02-15	JTS, RTi		Converted all queries to stored 
//					procedures.
// 2005-04-28	JTS, RTI		* Added all data members to finalize().
//					* Added super.finalize() to finalize().
// 2005-05-09	JTS, RTi		All structure queries now return
//					structure view objects.
// 2005-06-22	JTS, RTi		Column widths now come from the 
//					table model, not the cell renderer.
// 2005-06-22	JTS, RTi		* Column widths now come from the 
//					  table model, not the cell renderer.
//					* The table-specific cell renderers 
//					  were removed and replaced with a 
//					  single generic one.
// 2005-11-15	JTS, RTi		Option to query the entire state at once
//					added to the district combo box.
// 2007-02-07	SAM, RTi		Remove the dependence on CWRAT.
//					Pass a JFrame in the constructor.
//					Also pass in a GeoViewAppUI instance for map interaction.
//					Clean up code based on Eclipse feedback.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
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

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.List;

import RTi.DMI.DMIUtil;

import RTi.GIS.GeoView.GeoRecord;
import RTi.GIS.GeoView.GeoViewListener;

import RTi.GR.GRLimits;
import RTi.GR.GRPoint;
import RTi.GR.GRShape;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.JScrollWorksheet;
import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.ReportJFrame;
import RTi.Util.GUI.SimpleJComboBox;

import RTi.Util.IO.PrintJGUI;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.DateTime;
import RTi.Util.Time.StopWatch;

/**
This class displays the Water Rights query form and also formats user input
into workable SQL that the database processes so that the data can be shown 
on the form.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_WaterRightsQuery 
extends JFrame
implements ActionListener, GeoViewListener, ItemListener, KeyListener, 
MouseListener, WindowListener {

/**
Reference to a tabulation rights header.
*/
private final static int __TABULATION_RIGHTS = 0;
/**
Reference to a tabulation augmentation header.
*/
private final static int __TABULATION_AUGMENTATION = 1;

/**
Miscellaneous Strings used in menus, buttons, labels, etc.
*/
private final String 
	__ASSOCIATED_RIGHTS = 		"Associated Rights",	
	__AUG_PLANS = 			"Augmentation Plans",	
	__BUTTON_CLOSE =		"Close",
	__BUTTON_EXPORT = 		"Export",
	__BUTTON_GET_DATA = 		"Get Data",
	__BUTTON_HELP = 		"Help",
	__BUTTON_PRINT = 		"Print",
	__BUTTON_SELECT_ON_MAP = 	"Select On Map",
	__BUTTON_VIEW = 		"View",
	__BUTTON_VIEW_REPORT = 		"View Report:",
	__LEGAL =			"Legal",
	__PRIORITY_STREAM = 		"Priority List by Stream"
					+ " (Stream, Admin #, ID)",	
	__PRIORITY_SUMMARY = 		"Priority List (Admin #, ID)",	
	__TABULATION = 			"Tabulation Report (Admin #, ID)",	
	__NONE = 			"None Available",
	__SUMMARY = 			"Summary",	
	__ADMIN_STREAM = 		"Administrative Summary List by "
					+ "Stream (Stream, Admin #, ID)",
	__ADMIN_SUMMARY = 		"Administrative Summary List"
					+ " (Admin #, ID)",	
	__TRANS_EXTENDED_1 = 		"Extended Water Rights Report"
					+ " (ID, Admin #)",
	__TRANS_EXTENDED_2 = 		"Extended Water Rights Report"
					+ " (Stream #, ID, Admin #)",
	__TRANS_EXTENDED_3 = 		"Extended Water Rights Report"
					+ " (Name, Admin #)",
	__TRANS_EXTENDED_4 = 		"Extended Water Rights Report"
					+ " (Location)",
	__TRANS_EXTENDED_5 = 		"Extended Water Rights Report"
					+ " (Admin #, Name)",
	__TRANS_EXTENDED_6 = 		"Extended Water Rights Report"
					+ " (Stream #, Admin #, ID)",
	__TRANS_STANDARD_1 = 		"Standard Water Rights Report"
					+ " (ID, Admin #)",
	__TRANS_STANDARD_2 = 		"Standard Water Rights Report"
					+ " (Stream #, ID, Admin #)",
	__TRANS_STANDARD_3 = 		"Standard Water Rights Report"
					+ " (Name, Admin #)",
	__TRANS_STANDARD_4 =		"Standard Water Rights Report"
					+ " (Location)",
	__TRANS_STANDARD_5 = 		"Standard Water Rights Report"
					+ " (Admin #, Name)",
	__TRANS_STANDARD_6 = 		"Standard Water Rights Report"
					+ " (Stream #, Admin #, ID)",
	__STREAM = 			"Stream Alphabetical List"
					+ " (Stream, Name, Admin #)",
	__TRANSFER_RIGHTS = 		"Transfer Rights";

private boolean __geoViewSelectQuery = false;

/**
Whether this form is doing a query by example.
*/
private boolean __isQueryByExample = true;

/**
Whether this is a transaction query.
*/
private boolean __isTransactionQuery = false;

// Interface to map
private GeoViewUI __geoview_ui = null;

/**
Map query limits.
*/
private GRLimits __mapQueryLimits = null;

/**
Reference to DMI object.
*/
private HydroBaseDMI __dmi;

/**
The net amounts report.
*/
private HydroBase_Report_NetAmounts __netAmountsReport = null;

/**
The transact report.
*/
private HydroBase_Report_Transaction __transactReport = null;

/**
The input filter panel for querying net amounts data.
*/
private HydroBase_GUI_NetAmts_InputFilter_JPanel __netAmtsFilterJPanel = null;

/**
The input filter panel for querying transact data.
*/
private HydroBase_GUI_Transact_InputFilter_JPanel __transactFilterJPanel = null;

/**
JButton to close everything up.
*/
private JButton __closeJButton = null;

/**
JButton to export to a file.
*/
private JButton __exportJButton = null;

/**
JButton to retrieve data from the database.
*/
private JButton __getDataJButton = null;

/**
JButton to print the results.
*/
private JButton __printJButton = null;

/**
JButton to show a new view.
*/
private JButton __viewJButton = null;

private JLabel __orderJLabel = null;

/**
Table JLabel.
*/
private JLabel __tableJLabel = null;

/**
Table for showing query results.
*/
private JWorksheet __worksheet = null;

/**
GUI textfields.
*/
private JTextField 
	__statusJTextField = null;

/**
SimpleJComboBox to hold the kinds of output templates.
*/
private SimpleJComboBox __outputTemplateJComboBox = null;

/**
SimpleJComboBox to hold the name of the table being queried.
*/
private SimpleJComboBox __tableJComboBox = null;

/**
SimpleJComboBox to hold the list of possible views.
*/
private SimpleJComboBox __viewJComboBox = null;

/**
SimpleJComboBox to hold the list of available water districts.
*/
private SimpleJComboBox __waterDistrictJComboBox = null;

/**
The table which has its data displayed in the table view.
*/
private String __currTable = "";

/**
Contains the results of the latest transact query.
*/
private List<HydroBase_Transact> __resultsTransact = null;

/**
Contains the results of the latest net amounts query.
*/
private List<HydroBase_NetAmts> __resultsNetAmts = null;

/**
Constructor.  This is set up to read the GLOBAL_FAST data from the database
and to also not be a transaction query.
@param dmi open and non-null HydroBaseDMI object
@param parent Parent JFrame that constructs an instance of this class.
@param geoview_ui Instance of GeoViewUI that allows connection to map display.
@throws Exception if an error occurs.
*/
public HydroBase_GUI_WaterRightsQuery(HydroBaseDMI dmi, JFrame parent,
		GeoViewUI geoview_ui ) 
throws Exception {
	__geoview_ui = geoview_ui;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	initialize(dmi, false);
	__isQueryByExample = true;
}

/**
Constructor.  
@param dmi open and non-null HydroBaseDMI object
@param structureNum the structure number of the structure for which to 
open data.
@param parent Parent JFrame that constructs an instance of this class.
@param geoview_ui Instance of GeoViewUI that allows connection to map display.
@param isTransactionQuery whether this is a transaction query or not.
@throws Exception if an error occurs.
*/
public HydroBase_GUI_WaterRightsQuery(HydroBaseDMI dmi, int structureNum, 
JFrame parent, GeoViewUI geoview_ui, boolean isTransactionQuery)
throws Exception {
	__geoview_ui = geoview_ui;
	__isQueryByExample = false;
	
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());	
	initialize(dmi, isTransactionQuery);

	int wd = -1;

	HydroBase_StructureView view 
		= __dmi.readStructureViewForStructure_num(
		structureNum);
	wd = view.getWD();

	// in order to select the proper water district from the 
	// water district combo box ....
	String find = "  " + wd + " -";
	__waterDistrictJComboBox.setSelectedPrefixItem(find);
	
	List<String> whereClause = new ArrayList<String>();
	whereClause.add("structure_num = " + structureNum);

	int numFilters = __transactFilterJPanel.getNumFilterGroups();
	InputFilter filter = null;
	JComponent component = null;
	JTextField textField = null;
	for (int i = 0; i < numFilters; i++) {
		filter = __transactFilterJPanel.getInputFilter(i);	
		component = filter.getInputComponent();	
		if (component instanceof JTextField) {
			textField = (JTextField)component;
			textField.setText("" + structureNum);
		}
	}
	numFilters = __netAmtsFilterJPanel.getNumFilterGroups();
	for (int i = 0; i < numFilters; i++) {
		filter = __netAmtsFilterJPanel.getInputFilter(i);	
		component = filter.getInputComponent();	
		if (component instanceof JTextField) {
			textField = (JTextField)component;
			textField.setText("" + structureNum);
		}
	}		

	__transactFilterJPanel.setVisible(false);
	__netAmtsFilterJPanel.setVisible(false);
	__getDataJButton.setVisible(false);
	__orderJLabel.setVisible(false);
	__outputTemplateJComboBox.setVisible(false);

	submitQuery();
}

/**
Listener method to handle action performed events.
@param event the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent event) {
	String routine = "HydroBase_GUI_WaterRightsQuery.actionPerformed";
        String actionCommand = event.getActionCommand();

	if (actionCommand.equals(__BUTTON_CLOSE)) {
                closeClicked();
        }
        else if (actionCommand.equals(__BUTTON_HELP)) {
        }
        else if (actionCommand.equals(__BUTTON_GET_DATA)) {
		__viewJButton.setEnabled(false);
		try {
			submitQuery();
		}
		catch(Exception ex) {
			Message.printWarning (2, routine, ex);
		}
        } 
        else if (actionCommand.equals(__BUTTON_EXPORT)) {
		try {
			String[] eff = 
				HydroBase_GUI_Util.getExportFilenameAndFormat(
				this, 
				HydroBase_GUI_Util
				.getDelimitedFormatsAndExtensions());

			if (eff == null) {
				return ;
			}

			int format = new Integer(eff[1]).intValue();
	 		// First format the output...
			if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
				List<String> outputStrings = formatOutput(format);
	 			// Now export, letting the user decide the 
				// file...
				HydroBase_GUI_Util.export(this, eff[0], outputStrings);
			}
			else {
                		char delim = HydroBase_GUI_Util
					.getDelimiterForFormat(format);	
				__worksheet.saveToFile(eff[0], "" + delim);
			}
		} 
		catch (Exception ex) {
			Message.printWarning(1, routine, "Error in export.");
			Message.printWarning (2, routine, ex);
		}
        }
	else if (actionCommand.equals(__BUTTON_PRINT)) {
		try {
			SelectFormatTypeJDialog d = 
				new SelectFormatTypeJDialog(this, 
				HydroBase_GUI_Util.getDelimitedFormats());
			int format = d.getSelected();
			if (format == HydroBase_GUI_Util.CANCEL) {
				return;
			}			
			d.dispose();
	 		// First format the output...
			List<String> outputStrings = formatOutput(format);
	 		// Now print...
			PrintJGUI.print(this, outputStrings);
		}
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}		
        }
	/*
	else if (actionCommand.equals(__BUTTON_SAVE_AS)) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save as");
		SimpleFileFilter cff = new SimpleFileFilter("csv", 
			"Comma-delimited files");
		SimpleFileFilter pff = new SimpleFileFilter("txt", 
			"Pipe [ | ]-delimited files");
		SimpleFileFilter sff = new SimpleFileFilter("txt", 
			"Semicolon-delimited files");			
		SimpleFileFilter tff = new SimpleFileFilter("txt",
			"Tab-delimited files");
		SimpleFileFilter scvff = new SimpleFileFilter("txt", 
			"Screen View");			
		SimpleFileFilter sumff = new SimpleFileFilter("txt", 
			"Summary");						
		fc.addChoosableFileFilter(cff);
		fc.addChoosableFileFilter(pff);
		fc.addChoosableFileFilter(sff);
		fc.addChoosableFileFilter(tff);
		fc.addChoosableFileFilter(scvff);
		fc.addChoosableFileFilter(sumff);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(cff);		
		fc.setDialogType(JFileChooser.SAVE_DIALOG);

		int returnVal = fc.showSaveDialog(this);		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String file = fc.getSelectedFile().getName();
			SimpleFileFilter sel = 
				(SimpleFileFilter)fc.getFileFilter();
			String type = sel.toString();
			if (type.equals(cff.toString())) {
				// save as a comma-delimited file	
			}
			else if (type.equals(pff.toString())) {
				// save as a pipe-delimited file
			}
			else if (type.equals(sff.toString())) {
				// save as a semicolon-delimited file
			}
			else if (type.equals(tff.toString())) {
				// save as a tab-delimited file
			}
			else if (type.equals(scvff.toString())) {
				// save as a screen view file
			}
			else if (type.equals(sumff.toString())) {
				// save as a summary file
			}
		}		
	}
	*/
        else if (actionCommand.equals(__BUTTON_SELECT_ON_MAP)) {
		selectOnMap();
        }
	else if (actionCommand.equals(__BUTTON_VIEW)) {
		String selected = __tableJComboBox.getSelected();	
		int row = __worksheet.getSelectedRow();
		if (row < 0) {
			// nothing selected
			return;
		}
	        if (selected.equals(HydroBase_GUI_Util._NET_AMOUNTS)) {
			try {
				new HydroBase_GUI_WaterRight(__dmi, 
					(HydroBase_NetAmts)
					__worksheet.getRowData(row));
			}
			catch (Exception ex) {
				Message.printWarning(1, routine,
					"Unable to build HydroBase_GUI"
					+ "_WaterRight form.");
				Message.printWarning(1, routine, ex);
			}				
	        }
		else if (selected.equals(HydroBase_GUI_Util._TRANS)) {
			try {
				new HydroBase_GUI_WaterRight(__dmi, 
					(HydroBase_Transact)
					__worksheet.getRowData(row));
			}
			catch (Exception ex) {
				Message.printWarning(1, routine,
					"Unable to build HydroBase_GUI"
					+ "_WaterRight form.");
				Message.printWarning(1, routine, ex);
			}
						
		}			
	}
	else if (actionCommand.equals(__BUTTON_VIEW_REPORT)) {
		try {
			getSelectedView();
		}
		catch (Exception ex) {
			Message.printWarning (2, routine, 
				"Error viewing report.");
			Message.printWarning (2, routine, ex);
		}
	}
	JGUIUtil.setWaitCursor(this, false);
}


/**
Close the GUI. Overrides the super.closeClicked();
*/
protected void closeClicked() {
	String routine = "HydroBase_GUI_WaterRightsQuery.closeClicked";
	try {
		setVisible(false);
	} 
	catch(Exception e) {
		Message.printWarning (2, routine, e);
	}
}

/**
Displays the requested query results by calling the appropriate members 
functions of the HydroBase_NetAmts class.  In addition, sets the column
headings and widths.
@param results Vector of HydroBase_NetAmts objects.
@param redisplay if true, the columns are being redisplayed according to
another template.  If false, using the same template.
*/
private void displayNetResults(List<HydroBase_NetAmts> results)
throws Exception {
	String temp = __outputTemplateJComboBox.getSelected();
	int type = -1;
	if (temp.equalsIgnoreCase(__LEGAL)) {
		type = HydroBase_TableModel_NetAmts.LEGAL;
	}
	else if (temp.equalsIgnoreCase(__SUMMARY)) {
		type = HydroBase_TableModel_NetAmts.SUMMARY;
	}
	else {
		// this should never happen
		throw new Exception ("Unknown value in "
			+ "Template JComboBox");
	}
	HydroBase_TableModel_NetAmts tm = new HydroBase_TableModel_NetAmts(
		__resultsNetAmts, __dmi, type);
	HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
	__worksheet.setCellRenderer(cr);
	__worksheet.setModel(tm);
	__worksheet.setColumnWidths(tm.getColumnWidths(type));
	__worksheet.setColumnsToolTipText(tm.getColumnToolTips());	
}

/**
Displays the requested query results by calling the appropriate member 
functions of the HydroBase_Transact class.  In addition, sets the column
headings and widths.
@param results list of HydroBase_Transact objects.
*/
private void displayTransactionResults(List<HydroBase_Transact> results)
throws Exception {
	String temp = __outputTemplateJComboBox.getSelected();
	int type = -1;
	if (temp.equalsIgnoreCase(__LEGAL)) {
		type = HydroBase_TableModel_Transact.LEGAL;
	}
	else if (temp.equalsIgnoreCase(__SUMMARY)) {
		type = HydroBase_TableModel_Transact.SUMMARY;
	}
	else if (temp.equalsIgnoreCase(__ASSOCIATED_RIGHTS)) {
		type = HydroBase_TableModel_Transact.ASSOCIATED_RIGHTS;
	}
	else if (temp.equalsIgnoreCase(__AUG_PLANS)) {
		type = HydroBase_TableModel_Transact.AUG_PLANS;
	}
	else if (temp.equalsIgnoreCase(__TRANSFER_RIGHTS)) {
		type = HydroBase_TableModel_Transact.TRANSFER_RIGHTS;
	}
	else {
		// this should never happen
		throw new Exception ("Unknown value in Template JComboBox");
	}
	HydroBase_TableModel_Transact tm = new HydroBase_TableModel_Transact(
		__resultsTransact, __dmi, type);
	HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
	__worksheet.setCellRenderer(cr);
	__worksheet.setModel(tm);	
	__worksheet.setColumnWidths(tm.getColumnWidths(type));
	__worksheet.setColumnsToolTipText(tm.getColumnToolTips());
}

/**
Enables or disables the display sort options on the status parameter.
@param status value to set the enabled value of sortBy1, sortBy2 and 
outputTemplate to.
*/
private void enableSortOptions(boolean status) {
        __outputTemplateJComboBox.setEnabled(status);   
}

/**
Enable layers in the map that are appropriate for the structure view.  If a map
is being used, disable the layers that are not a "WaterRight" or "BaseLayer"
AppLayerType.
*/
private void enableMapLayers() {
	if (!__geoview_ui.isMapVisible()) {
		return;
	}
	JGUIUtil.setWaitCursor(this, true);
	__statusJTextField.setText("Updating map to show only water "
		+ "right layers.");
	Message.printStatus(1, "", "Turning on water right GIS layer types.");

	List<String> enabledAppLayerTypes = new ArrayList<String>(2);
	enabledAppLayerTypes.add("WaterRight");
	enabledAppLayerTypes.add("BaseLayer");
	__geoview_ui.getGeoViewJPanel().enableAppLayerTypes(enabledAppLayerTypes,false);
	enabledAppLayerTypes = null;

	// We want this GUI to listen to the map...
	__geoview_ui.getGeoViewJPanel().getGeoView().addGeoViewListener(this);
	JGUIUtil.setWaitCursor(this, false);
	__statusJTextField.setText("Map shows base layers and water right layers.  Ready.");
}

/**
Cleans up member variables.
*/
protected void finalize()
throws Throwable {
	__mapQueryLimits = null;
	__dmi = null;
	__netAmountsReport = null;
	__transactReport = null;
	__netAmtsFilterJPanel = null;
	__transactFilterJPanel = null;
	__closeJButton = null;
	__exportJButton = null;
	__getDataJButton = null;
	__printJButton = null;
	__viewJButton = null;
	__tableJLabel = null;
	__worksheet = null;
	__statusJTextField = null;
	__outputTemplateJComboBox = null;
	__tableJComboBox = null;
	__viewJComboBox = null;
	__waterDistrictJComboBox = null;
	__currTable = null;
	__resultsNetAmts = null;
	__resultsTransact = null;
	super.finalize();
}

/**
Responsible for formatting output.
@param format the format in which the output should be exported.
*/
public List<String> formatOutput(int format) 
throws Exception {
        if (__currTable.equalsIgnoreCase(HydroBase_GUI_Util._TRANS)) {
		return formatOutputForTransactions(format);
	}
	else if (__currTable.equalsIgnoreCase(HydroBase_GUI_Util._NET_AMOUNTS)){
		return formatOutputForNetAmounts(format);
	}
	else {
		return new ArrayList<String>();
	}
}

/**
Formats the net amounts data for output.
@param format the format in which the data should be output.
@return a list, each element of which contains a line of data for the report.
*/
public List<String> formatOutputForNetAmounts(int format) 
throws Exception {

        int size = __worksheet.getRowCount();

        List<String> v = new ArrayList<String>(size);

        // Get the list contents...

        // First format the header...
        char delim = '|';
        if (!__isQueryByExample && format == HydroBase_GUI_Util.SCREEN_VIEW) {
                // We need to make sure that regardless of how the screen is
                // organized, we output as follows...
                // Get the information given the known structure number...
// REVISIT (JTS - 2005-01-10)
// need another way of getting the structure num
		int structure_num = 0;
//              int structure_num = (new Integer(
//			__searchForJTextField.getText())).intValue();

		HydroBase_StructureGeoloc hbsg = __dmi.readStructureGeolocForStructure_num(structure_num);
                v.add(HydroBase_GUI_Util.formatStructureHeader(
                        hbsg.getStr_name(),
                        StringUtil.formatString(hbsg.getDiv(),"%d"),
                        StringUtil.formatString(hbsg.getWD(),"%d"),
                        StringUtil.formatString(hbsg.getID(),"%d"),
                        format));			
		
                v.add("");
                v.add(
			"                                               "
			+ "WATER RIGHTS NET AMOUNTS INFORMATION");
		v.add(
			"ADMIN. NO   ADJ DATE   APPRO DATE  DECREED   DECREED"
			+ "   DECREED   DECREED   DECREED   DECREED   USES");
		v.add(
			"                                  RATE ABS.  VOL ABS."
			+ " RATE COND  VOL COND RATE APEX  VOL APEX");
		v.add(
			"____________________________________________________"
			+ "__________________________________________________"
			+ "__________________");
        }
	
        else if (__isQueryByExample &&
	       format == HydroBase_GUI_Util.SCREEN_VIEW) {
                // The legacy tabulation report...
                List<String> header = formatWRTabulationHeader(__TABULATION_RIGHTS);
                for (int i = 0; i < header.size(); i++) {
                        v.add(header.get(i));
                }
        }
        else {  
                delim = HydroBase_GUI_Util.getDelimiterForFormat(format);
        }

        // Now do the body of the output...

	int[] selected = __worksheet.getSelectedRows();
	int length = selected.length;
	boolean isSelected = false;
	boolean printedHeader = false;
        for (int i = 0; i < size; i++) {
		HydroBase_NetAmts data = (HydroBase_NetAmts)__worksheet.getRowData(i);	

                if (   !__isQueryByExample &&
                       format == HydroBase_GUI_Util.SCREEN_VIEW) {
                        v.add(
                                StringUtil.formatString(
					data.getAdmin_no(),"%11.5f") + " "
                                + StringUtil.formatString(
					data.getAdj_date(),"%-10.10s") + " "
                                + StringUtil.formatString(
					data.getApro_date(),"%-10.10s") + " "
                                + StringUtil.formatString(
					data.getNet_rate_abs(), "%9.2f") + " "
                                + StringUtil.formatString(
					data.getNet_vol_abs(), "%9.1f") + " "
                                + StringUtil.formatString(
					data.getNet_rate_cond(), "%9.2f") + " "
                                + StringUtil.formatString(
					data.getNet_vol_cond(), "%9.1f") + " "
                                + StringUtil.formatString(
					data.getNet_rate_apex(), "%9.2f") + " "
                                + StringUtil.formatString(
					data.getNet_vol_apex(), "%9.1f") + " "
				+ data.getUse()
			);
                }
                else if (__isQueryByExample &&
                       format == HydroBase_GUI_Util.SCREEN_VIEW) {
                        // Legacy water rights tabulation, although it has been
                        // modified slightly based on the new database fields
                        // and what we have available in the multilist.
                        // 
                        // Need to do some checks here...
                        double abs = DMIUtil.MISSING_DOUBLE;
			double apex = DMIUtil.MISSING_DOUBLE;
			double con = DMIUtil.MISSING_DOUBLE;
			String units = "";
                        // Check rates first...
                        double rateabs = data.getNet_rate_abs();
                        double ratecond = data.getNet_rate_cond();
			
                        if ( !DMIUtil.isMissing(rateabs) ||!DMIUtil.isMissing(ratecond) ) {
                                // Do flows...
                                abs = rateabs;
                                con = ratecond;
                                units = "C";
                        }
                        else {  
				// Check volumes...
				double volabs = data.getNet_vol_abs();
				double volcond = data.getNet_vol_cond();
                if ( !DMIUtil.isMissing(volabs) || !DMIUtil.isMissing(volcond) ) {
                                        // Do volumes...
                                        abs = volabs;
                                        con = volcond;
                                        units = "A";
                                }
                        }
			
                        // Check the alternate point/exchange(can be in
                        // addition to the flow or volume)...
                        double rateApex = data.getNet_rate_apex();
                        double volApex = data.getNet_vol_apex();

                        if ( !DMIUtil.isMissing(rateApex) ) {
                                // Do flow...
                                apex = rateApex;
                                if (units.length() == 0) {
                                        units = "C";
                                }
                        }
                        else if ( !DMIUtil.isMissing(volApex) ) {
                                // Do volume...
                                apex = volApex;
                                if (units.length()== 0) {
                                        units = "A";
                                }
                        }
                        v.add(
                                StringUtil.formatString(
					data.getWr_name(), "%-24.24s")
                               	+ " ?   " +
                                StringUtil.formatString("??", "%-20.20s")
                                + " " +
                                StringUtil.formatString(
					data.getWD(), "%2d")
                                + " " +
                                StringUtil.formatString(
					data.getTS(), "%4.4s")
                                + " " +
                                StringUtil.formatString(
					data.getRng(), "%4.4s")
                                + " " +
                                StringUtil.formatString(
					data.getSec(), "%4.4s")
                                + " " +
                                StringUtil.formatString(
					data.getSeca(), "%2.2s")
                                + " " +
                                StringUtil.formatString(
					data.getQ160(), "%4.4s")
                                + " " +
                                StringUtil.formatString(
					data.getQ40(), "%3.3s")
                                + " " +
                                StringUtil.formatString(
					data.getQ10(), "%3.3s")
                                + " " +
                                StringUtil.formatString(
					data.getPM(), "%2.2s")
                                + " " +
                                StringUtil.formatString(
					data.getUse(), "%-9.9s")
                                + " " +
                                StringUtil.formatString(abs,"%9.3f")
                                + " " +
                                StringUtil.formatString(con,"%9.3f")
                                + " " +
                                StringUtil.formatString(apex,"%9.3f")
                                + " " +
                                StringUtil.formatString(units,"%1.1s")
                                + " " +
                                StringUtil.formatString(
					data.getAdj_date(), "%10.10s")
                                + " " +
                                StringUtil.formatString(
					data.getPadj_date(), "%10.10s")
                                + " " +
                                StringUtil.formatString(
					data.getApro_date(), "%10.10s")
                                + " " +
                                StringUtil.formatString(
					data.getOrder_no(), "%1.1s")
                                + " " +
                                StringUtil.formatString(
					data.getAdmin_no(), "%11.5f")
                                + " " +
                                StringUtil.formatString(
					data.getID(), "%5d")
                                + " " +
                                StringUtil.formatString(
					data.getPri_case_no(), "%5d")
                                + " ");
                }
                else {
			boolean useComma = false;
			if (delim == ',') {
				useComma = true;
			}
			isSelected = false;
			if (length == 0) {
				isSelected = true;
			}
			for (int k = 0; k < length; k++) {
				if (i == selected[k]) {
					isSelected = true;
				}
			}
			if (isSelected) {
				// Delimited.  Output in the order shown on the
	                        // screen...					
				int colCount = __worksheet.getColumnCount();
				String s = "";			
				String o;		
				if (!printedHeader) {				
					for (int j = 0; j < colCount; j++) {
						s += __worksheet
							.getColumnName(j, true) 
							+ delim;
					}
					v.add(s);
					printedHeader = true;
				}
				
				s = "";
				for (int j = 0; j < colCount; j++) {
					o = __worksheet
						.getValueAtAsString(i, j);
					if (useComma && o.indexOf(",") > -1) {
						o = "\"" + o + "\"";
					}
					s += o + delim;
				}

				v.add(s);			
			}
		}
        }

        return v;
}

/**
Formats the net amounts data for output.
@param format the format in which the data should be output.
@return a list, each element of which contains a line of data for the report.
*/
public List<String> formatOutputForTransactions(int format) {
	int size = __worksheet.getRowCount();

        List<String> v = new ArrayList<String>(size);

        if (!__outputTemplateJComboBox.getSelectedItem().equals("Summary") &&
	    !__outputTemplateJComboBox.getSelectedItem().equals("Legal") &&
	    !__outputTemplateJComboBox.getSelectedItem().equals(
	    	"Associated Rights") &&
	    !__outputTemplateJComboBox.getSelectedItem().equals(
	    	"Augmentation Plans") &&
	    !__outputTemplateJComboBox.getSelectedItem().equals(
	    	"Transfer Rights")) {
		// this really should never happen
		return new ArrayList<String>();
        }
	
        // First format the header...
        char delim = '|';
        if ( !__isQueryByExample && format == HydroBase_GUI_Util.SCREEN_VIEW) {
                // We need to make sure that regardless of how the screen is
                // organized, we output as follows...
                v.add(
			  "                                          "
			+ "WATER RIGHTS TRANSACTION INFORMATION");
                v.add(
			  "ADMIN. NO   ADJ DATE   APPRO DATE COURT NO   "
			+ "DECREED   DECREED   STATUS USES      COMMENTS");
                v.add(
			  "                                           "
			+ "RATE (CFS) VOL (AF)");
                v.add(
			  "_____________________________________________"
			+ "_____________________________________________"
			+ "______________________________");
        }
        else if (__isQueryByExample &&
	        format == HydroBase_GUI_Util.SCREEN_VIEW) {
                // Legacy stream alpha report...
                v.add("need to add stream alpha header");
        }
        else {  
		// Delimited is done in the main loop...
		delim = HydroBase_GUI_Util.getDelimiterForFormat(format);
        }

	int[] selected = __worksheet.getSelectedRows();
	int length = selected.length;
	boolean isSelected = false;
	boolean printedHeader = false;	
        for (int i = 0; i < size; i++) {
		HydroBase_Transact data = (HydroBase_Transact)__worksheet.getRowData(i);	
	
                if (!__isQueryByExample 
		    && format == HydroBase_GUI_Util.SCREEN_VIEW) {
                        v.add(
				StringUtil.formatString(
					data.getAdmin_no(), "%11.5f")	
                                + " ");
                }
                else if (__isQueryByExample &&
                          format == HydroBase_GUI_Util.SCREEN_VIEW) {
                        // Legacy stream alpha report...
                        v.add("need to add stream alpha body");
                }
                else {  
			isSelected = false;
			for (int k = 0; k < length; k++) {
				if (i == selected[k]) {
					isSelected = true;
				}
			}
			if (isSelected) {		
			// Delimited.  Output in the order shown on the
                        // screen...					
			int colCount = __worksheet.getColumnCount();
			String s = "";			
			String o;
			String d = "";			
			if (!printedHeader) {
				for (int j = 0; j < colCount; j++) {
					s += __worksheet.getColumnName(j, true) 
						+ delim;
				}
				v.add(s);
				printedHeader = true;
			}
			
			s = "";
			
			for (int j = 0; j < colCount; j++) {
				o = __worksheet.getValueAtAsString(i, j);
				if (o == null) {
					d = "";
				}
				else {
					d = o.toString();
				}
				s += d + delim;
			}

			v.add(s);
			}
		}		
        }

        return v;
}

/**
Formats the header for the water right tabulation.
@param flag flag that says what kind of header to build.
@return a Vector of Strings, each of which is a line of text in the header.
*/
public static List<String> formatWRTabulationHeader(int flag) {
	List<String> v = new ArrayList<String>(2);

	DateTime t = new DateTime(DateTime.DATE_CURRENT);
	String now = t.toString(DateTime.FORMAT_YYYY_MM_DD_HH_mm_SS);

        if (flag == __TABULATION_RIGHTS) {
                v.add("");
                v.add(
			  "                                                   "
			+ "                              Water Rights "
			+ "Tabulation                     " + now);
                v.add("");
                v.add(
			  "Name of Structure        Typ Name of Source       "
			+ "WD TS   RNG  SEC     Q160 Q40 Q10 PM Use        "
			+ " Net Abs  Net Cond AltP/Exch U   Adj Date P Adj "
			+ "Date Appro Date O AdminNumber   ID# Pr#/C#");
                v.add("");
        }
        else if (flag == __TABULATION_AUGMENTATION) {
                v.add("");
                v.add(
		         "                                       Tabulation of "
			+ "Augmentation Plans        " + now);
                v.add(
			  "Name of Plan              Stream Name              "
			+ "W C Case   WD   L O C A T I O N      Adj Date    "
			+ " ID# U s e s   Line");
                v.add("");
        }

        return v;
}

/**
Populates the __viewJComboBox object with the appropriate report views.
@throws Exception if an error occurs.
*/
private void generateReportViews()
throws Exception {
	JGUIUtil.setWaitCursor(this, true);
        __viewJComboBox.removeAll();

        String table = __tableJComboBox.getSelected();

	__viewJComboBox.setPrototypeDisplayValue(__ADMIN_STREAM);
        if (table.equalsIgnoreCase(HydroBase_GUI_Util._NET_AMOUNTS)) {   
                __viewJComboBox.add(__ADMIN_SUMMARY);
                __viewJComboBox.add(__ADMIN_STREAM);
                __viewJComboBox.add(__PRIORITY_SUMMARY);
                __viewJComboBox.add(__PRIORITY_STREAM);
                __viewJComboBox.add(__STREAM);
                __viewJComboBox.add(__TABULATION);
                __viewJComboBox.select(__ADMIN_SUMMARY); 
        }
        else if (table.equalsIgnoreCase(HydroBase_GUI_Util._TRANS)) {
                __viewJComboBox.add(__TRANS_STANDARD_1);
                __viewJComboBox.add(__TRANS_STANDARD_2);
                __viewJComboBox.add(__TRANS_STANDARD_3);
                __viewJComboBox.add(__TRANS_STANDARD_4);
                __viewJComboBox.add(__TRANS_STANDARD_5);
                __viewJComboBox.add(__TRANS_STANDARD_6);
                __viewJComboBox.add(__TRANS_EXTENDED_1);
                __viewJComboBox.add(__TRANS_EXTENDED_2);
                __viewJComboBox.add(__TRANS_EXTENDED_3);
                __viewJComboBox.add(__TRANS_EXTENDED_4);
                __viewJComboBox.add(__TRANS_EXTENDED_5);
                __viewJComboBox.add(__TRANS_EXTENDED_6);
                __viewJComboBox.select(__TRANS_STANDARD_1);
        }
        else {
                __viewJComboBox.add(__NONE);
        }

	JGUIUtil.setWaitCursor(this, false);
}

/**
Do nothing.  Should this do the same as a select?
Currently this does nothing.
@param devlimits Limits of select in device coordinates(pixels).
@param datalimits Limits of select in data coordinates.
@param selected list of selected GeoRecord.  Currently ignored.
*/
public void geoViewInfo(GRShape devlimits, GRShape datalimits, List<GeoRecord> selected) {}

/**
Do nothing.  Should this do the same as a select?
@param grp first point
@param grp2 second point
@param selected selected list.
*/
public void geoViewInfo(GRPoint grp, GRPoint grp2, List<GeoRecord> selected) {}

/**
Do nothing.  Should this do the same as a select?
@param grl first limits
@param grl2 second limits
@param selected selected list.
*/
public void geoViewInfo(GRLimits grl, GRLimits grl2, List<GeoRecord> selected) {}

/**
Handle the label redraw event from another GeoView(likely a ReferenceGeoView).
Do not do anything here because we assume that application code is setting
the labels.
@param record Feature being draw.
@return null
*/
public String geoViewGetLabel(GeoRecord record) {
	return null;
}

/**
Handle the mouse motion event from another GeoView(likely a ReferenceGeoView).
Currently this does nothing.
@param devpt Coordinates of mouse in device coordinates(pixels).
@param datapt Coordinates of mouse in data coordinates.
*/
public void geoViewMouseMotion(GRPoint devpt, GRPoint datapt) {}

/**
If a selection is made from the map and this window is visible, query the
database for region that was selected.
Currently this does nothing.
@param devlimits Limits of select in device coordinates(pixels).
@param datalimits Limits of select in data coordinates.
@param selected list of selected GeoRecord.  Currently ignored.
*/
public void geoViewSelect(GRShape devlimits, GRShape datalimits,
		List<GeoRecord> selected, boolean append) {
	String routine = "geoViewSelect";
	if (isVisible()) {
		Message.printWarning(1, routine,
			"Water rights cannot currently be queried "
			+ "based on UTM.");
		if (devlimits.type == GRShape.LIMITS) {
			__mapQueryLimits = (GRLimits)datalimits;
		}
		else if (devlimits.type == GRShape.POINT) {
			__mapQueryLimits = new GRLimits(
			((GRPoint)datalimits).x,
			((GRPoint)datalimits).y,
			((GRPoint)datalimits).x,
			((GRPoint)datalimits).y);
		}
		try {
			__geoViewSelectQuery = true;
			submitQuery();
		}
		catch (Exception e) {
			Message.printWarning(2, routine, "Error submitting "
				+ "database query.");
			Message.printWarning(2, routine, e);
		}
		__mapQueryLimits = null;
	}
}

/**
If a selection is made from the map and this window is visible, query the
database for region that was selected.
Currently does nothing.
@param devlimits first point
@param datalimits second point
@param selected list of selected areas
@param append whether to append.
*/
public void geoViewSelect(GRPoint devlimits, GRPoint datalimits, 
		List<GeoRecord> selected, boolean append) {
	geoViewSelect((GRShape)devlimits, (GRShape)datalimits, selected,append);
}

/**
If a selection is made from the map and this window is visible, query the
database for region that was selected.
Currently does nothing.
@param devlimits first limits
@param datalimits second limits
@param selected list of selected areas
@param append whether to append.
*/
public void geoViewSelect(GRLimits devlimits, GRLimits datalimits,
		List<GeoRecord> selected, boolean append) {
	geoViewSelect((GRShape)devlimits, (GRShape)datalimits, selected, 
		append);
}

/**
Handle the zoom event from another GeoView(likely a ReferenceGeoView).
This resets the data limits for this GeoView to those specified(if not
null)and redraws the GeoView.
@param devlimits Limits of zoom in device coordinates(pixels).
@param datalimits Limits of zoom in data coordinates.
*/
public void geoViewZoom(GRShape devlimits, GRShape datalimits) {}

/**
Handle the zoom event from another GeoView (likely a ReferenceGeoView).  This
resets the data limits for this GeoView to those specified (if not null) and
redraws the GeoView.
@param devlim first set of limits
@param datalim second set of limits
*/
public void geoViewZoom (GRLimits devlim, GRLimits datalim ) {}

/**
Retrieves the selected view based on the _viewJComboBox item and starts a 
query for a report.  Results are displayed only when all the records have
been returned.
@throws Exception if an error occurs
*/
private void getSelectedView()
throws Exception {
        String view = __viewJComboBox.getSelected().trim();

	JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Retrieving Data...");

        // get the where and return if an error occurred
	int reportType = DMIUtil.MISSING_INT;
        String table = __tableJComboBox.getSelected();
        List<String> reportList = null;
        if (table.equalsIgnoreCase(HydroBase_GUI_Util._NET_AMOUNTS)) {
                if (view.equals(__PRIORITY_SUMMARY)) {
			reportType = 
				HydroBase_Report_NetAmounts.PRIORITY_SUMMARY;
                }
                else if (view.equals(__PRIORITY_STREAM)) {
			reportType = 
				HydroBase_Report_NetAmounts.PRIORITY_STREAM;
                }
                else if (view.equals(__TABULATION)) {
			reportType = 
				HydroBase_Report_NetAmounts.TABULATION;
                }
                else if (view.equals(__ADMIN_SUMMARY)) {
			reportType = 
				HydroBase_Report_NetAmounts.ADMIN_SUMMARY;
                }
                else if (view.equals(__ADMIN_STREAM)) {
			reportType = 
				HydroBase_Report_NetAmounts.ADMIN_STREAM;
                }
                else if (view.equals(__STREAM)) {
			reportType = 
				HydroBase_Report_NetAmounts.STREAM;
                }

		if (__netAmountsReport == null) {
			String[] districtWhere 
				= __dmi.getWaterDistrictWhereClause(
				__waterDistrictJComboBox, 
				HydroBase_GUI_Util._NET_AMOUNTS_TABLE_NAME, 
				true, true);
		
			__netAmountsReport = 
				new HydroBase_Report_NetAmounts(__dmi, 
				reportType, __netAmtsFilterJPanel, 
				districtWhere);
		}
		else {
			__netAmountsReport.createReport(reportType);
		}

		reportList = __netAmountsReport.getReport();
        }

        else if (table.equalsIgnoreCase(HydroBase_GUI_Util._TRANS)) {
                if (view.equals(__TRANS_STANDARD_1)) {
			reportType = 
				HydroBase_Report_Transaction.STANDARD_1;
                }
                else if (view.equals(__TRANS_STANDARD_2)) {
			reportType = 
				HydroBase_Report_Transaction.STANDARD_2;
                }
                else if (view.equals(__TRANS_STANDARD_3)) {
			reportType = 
				HydroBase_Report_Transaction.STANDARD_3;
                }
                else if (view.equals(__TRANS_STANDARD_4)) {
			reportType = 
				HydroBase_Report_Transaction.STANDARD_4;
                }
                else if (view.equals(__TRANS_STANDARD_5)) {
			reportType = 
				HydroBase_Report_Transaction.STANDARD_5;
                }
                else if (view.equals(__TRANS_STANDARD_6)) {
			reportType = 
				HydroBase_Report_Transaction.STANDARD_6;
                }
                else if (view.equals(__TRANS_EXTENDED_1)) {
			reportType = 
				HydroBase_Report_Transaction.EXTENDED_1;
                }
                else if (view.equals(__TRANS_EXTENDED_2)) {
			reportType = 
				HydroBase_Report_Transaction.EXTENDED_2;
                }
                else if (view.equals(__TRANS_EXTENDED_3)) {
			reportType = 
				HydroBase_Report_Transaction.EXTENDED_3;
                }
                else if (view.equals(__TRANS_EXTENDED_4)) {
			reportType = 
				HydroBase_Report_Transaction.EXTENDED_4;
                }
                else if (view.equals(__TRANS_EXTENDED_5)) {
			reportType = 
				HydroBase_Report_Transaction.EXTENDED_5;
                }
                else if (view.equals(__TRANS_EXTENDED_6)) {
			reportType = 
				HydroBase_Report_Transaction.EXTENDED_6;
                }

		if (__transactReport == null) {
			String[] districtWhere 
				= __dmi.getWaterDistrictWhereClause(
				__waterDistrictJComboBox, 
				HydroBase_GUI_Util._TRANS_TABLE_NAME, true,
				true);
				
			__transactReport = 
				new HydroBase_Report_Transaction(__dmi, 
				reportType, __transactFilterJPanel,
				districtWhere);
		}
		else {
			__transactReport.createReport(reportType);
		}

		reportList = __transactReport.getReport();
        }

        __statusJTextField.setText("Ready.");
	
	PropList pl = new PropList("Report Props");
	pl.set("Title", view);
	pl.set("DisplayTextComponent", "JTextArea");
	new ReportJFrame(reportList, pl);
	JGUIUtil.setWaitCursor(this, false);
}

/**
Get the list of AppLayerType that correspond to the GUI in its current state.
Currently this only adds "WaterRight".  It may be necessary to distinguish
between net and transactional rights.
@return list of String for AppLayerType that should be considered.
*/
private List<String> getVisibleAppLayerType() {
	List<String> appLayerTypes = new ArrayList<String>();
	appLayerTypes.add("WaterRight");
	return appLayerTypes;
}

/**
Initialization routine called by the various constructors.
@param dmi open and non-null HydroBaseDMI object
@param isTransactionQuery whether this is a transaction query or not.
@throws Exception if an error occurs.
*/
private void initialize(HydroBaseDMI dmi, boolean isTransactionQuery)
throws Exception {
	String obj = "HydroBase_GUI_WaterRightsQuery";
	if (dmi == null) {
		throw new Exception ("Null dmi passed to "
			+ obj + " "
			+ "constructor.  DMI object must be open and "
			+ "connected before passing in.");
	}
	if (!dmi.isOpen()) {
		throw new Exception ("Unopened dmi passed to "
			+ obj + " "
			+ "constructor.  DMI object must be open and "
			+ "connected before passing in.");
	}

	__dmi = dmi;
	__isTransactionQuery = isTransactionQuery;
	setupGUI();
}

/**
Listener for item state changed events.
@param event the ItemEvent that happened.
*/
public void itemStateChanged(ItemEvent event) {
        Object objectID = event.getItemSelectable();
	String routine = "HydroBase_GUI_WaterRightsQuery.itemStateChanged";

        if (__tableJComboBox.equals(objectID)) {
		try {
	                setFilterJPanelVisible();
	                generateReportViews();
			__worksheet.clear();
			__viewJButton.setEnabled(false);
		} 
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}		
        }
        else if (__outputTemplateJComboBox.equals(objectID)) {
		try {	
	                templateClicked();
		} 
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}		
        }
}

/**
Listener for key pressed events.  If an enter is pressed, the query will
be run.
@param event the KeyEvent that happened.
*/
public void keyPressed(KeyEvent event) {
	String routine = "HydroBase_GUI_WaterRightsQuery.keyPressed";
        int code = event.getKeyCode();

        // enter key runs the query.
        if (code == KeyEvent.VK_ENTER) {        
		try {
       			submitQuery();
		}
		catch (Exception ex) {
			Message.printWarning(2, routine, "Error running "
				+ "database query");
			Message.printWarning(2, routine, ex);
			return;
		}		
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
Does nothing.
*/
public void mouseClicked(MouseEvent event) {}

/**
Does nothing.
*/
public void mouseEntered(MouseEvent event) {}

/**
Does nothing.
*/
public void mouseExited(MouseEvent event) {}

/**
Responds to mouse presses on the worksheets and input filters.
@param event the MouseEvent that happened.
*/
public void mousePressed(MouseEvent event) {
	Object ob = event.getComponent();
	if (ob == __worksheet) {
		int row = __worksheet.getSelectedRow();
		if (row > -1) {
			if (__worksheet.getSelectedRowCount() > 1) {
				__viewJButton.setEnabled(false);
			}
			else {
				__viewJButton.setEnabled(true);
			}
			if (__geoview_ui.isMapVisible()) {
//				__selectOnMapJButton.setEnabled(true);
			}
			else {
//				__selectOnMapJButton.setEnabled(false);
			}
		}
	}
	else {	
		int numFilters = __transactFilterJPanel.getNumFilterGroups();
		for (int i = 0; i < numFilters; i++) {
			if (event.getSource() 
			 	== __transactFilterJPanel.getInputFilter(
				i).getInputComponent()) {
				HydroBase_GUI_Util.buildLocation(this,
					(JTextField)event.getSource());
			}
		}
		numFilters = __netAmtsFilterJPanel.getNumFilterGroups();
		for (int i = 0; i < numFilters; i++) {
			if (event.getSource() 
			 	== __netAmtsFilterJPanel.getInputFilter(
				i).getInputComponent()) {
				HydroBase_GUI_Util.buildLocation(this,
					(JTextField)event.getSource());
			}
		}		
	}	
}

/**
Does nothing.
*/
public void mouseReleased(MouseEvent event) {}

/**
Resets the GUI for user interaction.
*/
private void ready() {
	JGUIUtil.setWaitCursor(this, false);
        __statusJTextField.setText("Ready");
}

/**
Select the items in the list on the map.  This is done by putting together a
Vector of String, each of which has "wd,id".  These field names are set in the
GeoView Project as AppJoinField="wd,id".
*/
private void selectOnMap() {
	String table = __tableJComboBox.getSelected();
	List<String> idlist = new ArrayList<String>();
        __statusJTextField.setText(
		"Selecting and zooming to water rights on map.  "
		+ "Please wait...");
	JGUIUtil.setWaitCursor(this, true);

       	int [] rows = __worksheet.getSelectedRows();
	int wd = DMIUtil.MISSING_INT;
	int wd_prev = DMIUtil.MISSING_INT;
	int id = DMIUtil.MISSING_INT;
	int id_prev = DMIUtil.MISSING_INT;
	boolean all = false;
	int size = 0;
	if ((rows == null) || (rows.length == 0)) {
		size = __worksheet.getRowCount();
		all = true;
	}
	
	HydroBase_NetAmts n = null;
	HydroBase_Transact t = null;
	// Nothing is selected so locate all the water rights...
	for (int i = 0; i < size; i++) {		
		if (table.equalsIgnoreCase(HydroBase_GUI_Util._NET_AMOUNTS)) {
			if (all) {
				n = (HydroBase_NetAmts)
					__worksheet.getRowData(i);
			}
			else {
				n = (HydroBase_NetAmts)
					__worksheet.getRowData(rows[i]);
			}
			wd = n.getWD();
			id = n.getID();
		}
	        else if (table.equalsIgnoreCase(HydroBase_GUI_Util._TRANS)) {
			if (all) {
				t = (HydroBase_Transact)
					__worksheet.getRowData(i);
			}
			else {
				t = (HydroBase_Transact) 
					__worksheet.getRowData(rows[i]);
			}
			wd = t.getWD();
			id = t.getID();
		}
		else {
			continue;
		}

		if (wd != wd_prev || id != id_prev) {
			idlist.add(wd +","+ id);
			Message.printStatus(1, "", "Feature is \""
				+ (String)idlist.get(i) + "\"");
			wd_prev = wd;
			id_prev = id;
		}
	}

	// Select the features, searching only selected structure types, and
	// zoom to the selected shapes...
	List<GeoRecord> matching_features =__geoview_ui.getGeoViewJPanel().selectAppFeatures(
		getVisibleAppLayerType(), idlist, true, .05, .05);
	int matches = 0;
	if (matching_features != null) {
		matches = matching_features.size();
	}
	if (matches == 0) {
		Message.printWarning(1, "HBWaterRightsQueryGUI.selectOnMap",
		"No matching features were found in map data.\n" +
		"This may be because the location data are incomplete or\n" +
		"because no suitable GIS data layers are being used.");
	}
	else if (matches != idlist.size()) {
		Message.printWarning(1, "HBWaterRightsQueryGUI.selectOnMap",
		"" + matches + " matching features out of " + size +
		" records were found in map data.\n" +
		"This may be because of incomplete location data.");
	}

        __statusJTextField.setText(
		"Map is zoomed to selected water rights.  Ready.");
	JGUIUtil.setWaitCursor(this, false);
}

/**
Sets the filter panels visible or not depending on which table is selected.
*/
private void setFilterJPanelVisible()
throws Exception {
	String selected = __tableJComboBox.getSelected();

	__outputTemplateJComboBox.removeAllItems();
        if (selected.equals(HydroBase_GUI_Util._NET_AMOUNTS)) {
		__netAmtsFilterJPanel.setVisible(true);
		__transactFilterJPanel.setVisible(false);
                __outputTemplateJComboBox.add(__LEGAL);
                __outputTemplateJComboBox.add(__SUMMARY);		
        }
	else if (selected.equals(HydroBase_GUI_Util._TRANS)) {
		__netAmtsFilterJPanel.setVisible(false);
		__transactFilterJPanel.setVisible(true);
                __outputTemplateJComboBox.add(__ASSOCIATED_RIGHTS);
                __outputTemplateJComboBox.add(__AUG_PLANS);
                __outputTemplateJComboBox.add(__LEGAL);
                __outputTemplateJComboBox.add(__SUMMARY);
                __outputTemplateJComboBox.add(__TRANSFER_RIGHTS);		
        }
	__outputTemplateJComboBox.setPrototypeDisplayValue(__AUG_PLANS);
        ready();
}

/**
Show the Frame.
@param state true if showing the GUI, false otherwise.
@throws Exception if an error occurs.
*/
public void setVisible(boolean state) {
	String routine = "HydroBase_GUI_WaterRightsQuery.setVisible";
	try {
        if (state) {
		// the following code represents the defaults 
		// which are envoked when this gui is shown
                __tableJLabel.setText(HydroBase_GUI_Util.LIST_LABEL);

		HydroBase_GUI_Util.setWaterDistrictJComboBox(__dmi, 
			__waterDistrictJComboBox, null, true, false, true);

                if (__isQueryByExample) {
	                __outputTemplateJComboBox.setEnabled(true);
                }

		if (__worksheet.getRowCount() > 0) {
			__printJButton.setEnabled(true);
			__exportJButton.setEnabled(true);
		}
		else {	
			__printJButton.setEnabled(false);
			__exportJButton.setEnabled(false);
		}

		if (	__geoview_ui.isMapVisible() &&
			(__worksheet.getRowCount() > 0) &&
			__geoview_ui.getGeoViewJPanel().hasAppLayerType(
				getVisibleAppLayerType())) {
//       			__selectOnMapJButton.setEnabled(true);
		}
		else {	
//			__selectOnMapJButton.setEnabled(false);
		}
		enableMapLayers();
                __statusJTextField.setText("");
                __resultsTransact = new ArrayList<HydroBase_Transact>();
                __resultsNetAmts = new ArrayList<HydroBase_NetAmts>();
                __currTable = __tableJComboBox.getSelected();
                generateReportViews();		
		__worksheet.clear();
        }
        super.setVisible(state);
	}
	catch (Exception e) {
		Message.printWarning (2, routine, e);
	}
}

/**
Set up the GUI and the layout with which the user will interface.
@throws Exception if an error occurs.
*/
private void setupGUI()
throws Exception {
	Message.setTopLevel(this);

        Insets insetsNLNR = new Insets(0,7,0,7);
        Insets insetsTLNR = new Insets(7,7,0,7);
        Insets insetsNNNX = new Insets(0,0,0,28);
        Insets insetsNLNN = new Insets(0,7,0,0);
        Insets insetsNNNR = new Insets(0,0,0,7);
        GridBagLayout gbl = new GridBagLayout();
 
        addWindowListener(this);
        addKeyListener(this);

        // Top JPanel
        JPanel topJPanel = new JPanel();
        topJPanel.setLayout(new BorderLayout());
        getContentPane().add("North", topJPanel);

        // Left: Top JPanel
        JPanel topLeftJPanel = new JPanel();
        topLeftJPanel.setLayout(gbl);
        topJPanel.add("West", topLeftJPanel);

        JGUIUtil.addComponent(topLeftJPanel, new JLabel("Query Options:"), 
                0, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(topLeftJPanel, 
		new JLabel("Water Division/District:"), 
                0, 1, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __waterDistrictJComboBox = new SimpleJComboBox();
        if (!__isQueryByExample) {
                __waterDistrictJComboBox.setEnabled(false);
        }
        JGUIUtil.addComponent(topLeftJPanel, __waterDistrictJComboBox, 
                1, 1, 1, 1, 0, 0, insetsNNNX, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(topLeftJPanel, new JLabel("Water Rights Type:"), 
                0, 2, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __tableJComboBox = new SimpleJComboBox();
        List<String> tables = HydroBase_GUI_Util.getTableStrings(this);
	for (int i = 0; i < tables.size(); i++) {
	        __tableJComboBox.add(tables.get(i));
	}
        if (!__isQueryByExample) {
                __tableJComboBox.setEnabled(false);
        }
        __tableJComboBox.addItemListener(this);
        JGUIUtil.addComponent(topLeftJPanel, __tableJComboBox, 
                1, 2, 1, 1, 0, 0, insetsNNNX, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__netAmtsFilterJPanel = new HydroBase_GUI_NetAmts_InputFilter_JPanel(
		__dmi, this, __isQueryByExample);
	__transactFilterJPanel = new HydroBase_GUI_Transact_InputFilter_JPanel(
		__dmi, this, __isQueryByExample);

	__transactFilterJPanel.setVisible(false);

	__netAmtsFilterJPanel.addEventListeners(this);
	__transactFilterJPanel.addEventListeners(this);

        JGUIUtil.addComponent(topLeftJPanel, __netAmtsFilterJPanel,
                0, 3, 3, 2, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
        JGUIUtil.addComponent(topLeftJPanel, __transactFilterJPanel,
                0, 3, 3, 2, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __getDataJButton = new JButton(__BUTTON_GET_DATA);
	__getDataJButton.setToolTipText("Read data from database.");
        if (!__isQueryByExample) {
                __getDataJButton.setEnabled(false);
        }
        __getDataJButton.addActionListener(this);
        JGUIUtil.addComponent(topLeftJPanel, __getDataJButton, 
                4, 3, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);

	__orderJLabel = new JLabel("Column Order:");

        JGUIUtil.addComponent(topLeftJPanel, __orderJLabel,
                3, 4, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __outputTemplateJComboBox = new SimpleJComboBox();
        __outputTemplateJComboBox.addItemListener(this);
        JGUIUtil.addComponent(topLeftJPanel, __outputTemplateJComboBox, 
                4, 4, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // Center JPanel
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(gbl);
        getContentPane().add("Center", centerJPanel);

        __tableJLabel = new JLabel(HydroBase_GUI_Util.LIST_LABEL);
        JGUIUtil.addComponent(centerJPanel, __tableJLabel, 
                1, 1, 7, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	PropList p = 
		new PropList("HydroBase_GUI_WaterRightsQuery.JWorksheet");

	p.add("JWorksheet.ShowPopupMenu=true");
	p.add("JWorksheet.AllowCopy=true");	
	p.add("JWorksheet.ShowRowHeader=true");

	JScrollWorksheet jsw = new JScrollWorksheet(0, 0, p);
	__worksheet = jsw.getJWorksheet();

	__worksheet.setHourglassJFrame(this);
	__worksheet.addMouseListener(this);
	JGUIUtil.addComponent(centerJPanel, jsw,
                1, 2, 7, 3, 1, 1, insetsNLNR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

        // Bottom JPanel
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);

        // Bottom: North JPanel
        JPanel bottomNJPanel = new JPanel();
        bottomNJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add("North", bottomNJPanel);

//	__selectOnMapJButton = new JButton(__BUTTON_SELECT_ON_MAP);
//	__selectOnMapJButton.setEnabled(false);
//	__selectOnMapJButton.addActionListener(this);
//	bottomNJPanel.add(__selectOnMapJButton);

        __viewJButton = new JButton(__BUTTON_VIEW);
	__viewJButton.setToolTipText(
		"View details about selected water right.");
	__viewJButton.setEnabled(false);
        __viewJButton.addActionListener(this);
        bottomNJPanel.add(__viewJButton);

        __printJButton = new JButton(__BUTTON_PRINT);
	__printJButton.setToolTipText("Print table of data.");
	__printJButton.setEnabled(false);
        __printJButton.addActionListener(this);
        bottomNJPanel.add(__printJButton);

        __exportJButton = new JButton(__BUTTON_EXPORT);
	__exportJButton.setToolTipText("Export data to a file.");
	__exportJButton.setEnabled(false);
        __exportJButton.addActionListener(this);
        bottomNJPanel.add(__exportJButton);

        __closeJButton = new JButton(__BUTTON_CLOSE);
	__closeJButton.setToolTipText("Close this window.");
        __closeJButton.addActionListener(this);
        bottomNJPanel.add(__closeJButton);

        // Bottom: Center JPanel
        JPanel bottomCJPanel = new JPanel();
        bottomCJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add("Center", bottomCJPanel);

	JButton viewR = new JButton(__BUTTON_VIEW_REPORT);
	viewR.setToolTipText(
		"<HTML>View selected report for above data in a separate " +
		"window.<br>" +
		"In some cases the data will be requeried to enforce<br>" +
		"a specific sort order.</HTML>");
	viewR.addActionListener(this);
	bottomCJPanel.add(viewR);

        __viewJComboBox = new SimpleJComboBox();
	__viewJComboBox.setToolTipText(
		"Select a standard report format." );
        __viewJComboBox.add(__ADMIN_STREAM);
	__viewJComboBox.setPrototypeDisplayValue(__ADMIN_STREAM);
        __viewJComboBox.addItemListener(this);
	bottomCJPanel.add(__viewJComboBox);

/*
	JButton saveAs = new JButton(__BUTTON_SAVE_AS);
	saveAs.addActionListener(this);
	bottomCJPanel.add(saveAs);
*/
        // Bottom: South JPanel
        JPanel bottomSJPanel = new JPanel();
        bottomSJPanel.setLayout(gbl);
        bottomJPanel.add("South", bottomSJPanel);

        __statusJTextField = new JTextField();
        //__statusJTextField.setBackground(Color.lightGray);
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomSJPanel, __statusJTextField, 
                0, 1, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // set table query 
        if (__isTransactionQuery == true) {     
                __tableJComboBox.select(HydroBase_GUI_Util._TRANS);
        } 
        else {
              __tableJComboBox.select(HydroBase_GUI_Util._NET_AMOUNTS);
        }

        // Frame settings
        //setBackground(Color.lightGray);
	if (	(JGUIUtil.getAppNameForWindows() == null) ||
		JGUIUtil.getAppNameForWindows().equals("") ) {
		setTitle ( "Water Rights Data - Query" );
	}
	else {	setTitle( JGUIUtil.getAppNameForWindows() +
		" - Water Rights - Query" );
	}				
        setSize(700,450);
        JGUIUtil.center(this);

        // place the following so that pack will not affect the original
        // sizing above.
        generateReportViews();
        enableSortOptions(false);
	setFilterJPanelVisible(); 
        setVisible(true);
}

/**
This method constructs and submits the requested query as specified through
the GUI.  
@param whereClause Vector of where clauses to add to the query.
@throws Exception if the query was not successful.
*/
private void submitQuery() 
throws Exception {  
	String routine = "HydroBase_GUI_WaterRightsQuery.submitQuery";
        String table = __tableJComboBox.getSelected();
        __tableJLabel.setText(HydroBase_GUI_Util.LIST_LABEL); 
        __currTable = table;

        // perform query
	JGUIUtil.setWaitCursor(this, true);
        String status = "Please Wait... Retrieving Data";

        Message.printStatus(10, routine, status);
        __statusJTextField.setText(status);
        // Instantiate new Query Object depending upon selected table
        // Transaction table selected
	StopWatch sw = new StopWatch();
        if (table.equalsIgnoreCase(HydroBase_GUI_Util._TRANS)) {
		if (!__transactFilterJPanel.checkInput(true)) {
			ready();
			return;
		}	
                Message.printStatus(10, routine, "Transaction Table Selected");
		try {
			sw.start();
			List<HydroBase_Transact> v = __dmi.readTransactList(
				__transactFilterJPanel, 
				__dmi.getWaterDistrictWhereClause(
					__waterDistrictJComboBox, 
					HydroBase_GUI_Util._TRANS_TABLE_NAME, 
					true, true),
				__mapQueryLimits);
			__resultsTransact = v;
			displayTransactionResults(__resultsTransact);
			sw.stop();	
	        status = "" + __resultsTransact.size() + " records returned in " 
	        		+ sw.getSeconds() + " seconds";
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading transact data.");
			Message.printWarning (2, routine, e);
		}
        }                
        // Net Amounts table selected
        else if (table.equalsIgnoreCase(HydroBase_GUI_Util._NET_AMOUNTS)) {
		if (!__netAmtsFilterJPanel.checkInput(true)) {
			ready();
			return;
		}		
                Message.printStatus(10, routine, "Net Amounts Table Selected");
		try {
			sw.start();
			List<HydroBase_NetAmts> v = __dmi.readNetAmtsList(
				__netAmtsFilterJPanel, 
				__dmi.getWaterDistrictWhereClause(
					__waterDistrictJComboBox, 
					HydroBase_GUI_Util._NET_AMOUNTS_TABLE_NAME, true,
					true),
				__mapQueryLimits, -1);
			
			__resultsNetAmts = v;
			displayNetResults(__resultsNetAmts);
			sw.stop();
	        status = "" + __resultsNetAmts.size() + " records returned in " 
	        		+ sw.getSeconds() + " seconds";
		}
		catch (Exception e) {	
			Message.printWarning(1, routine, "Error reading "
				+ "net amts data.");
			Message.printWarning (2, routine, e);
		}		
        }
       // Augmentation table selected
        else if (table.equalsIgnoreCase(HydroBase_GUI_Util._AUG)) {
                Message.printWarning(1, routine, "Augmentation Plans"
                        + " is a future feature.\nPlease select another"
                        + " table to query.");
                throw new Exception ("Augmentation plans is a future feature.  "
			+ "Another table should be selected for the query.");
        }

	if (__worksheet.getRowCount() > 0) {
		__printJButton.setEnabled(true);
		__exportJButton.setEnabled(true);
	}
	else {	
		__printJButton.setEnabled(false);
		__exportJButton.setEnabled(false);
	}
	
	__tableJLabel.setText("Water Rights Records: " + status);
	if (__geoViewSelectQuery) {
		status += ", queried using map coordinates.";
		__geoViewSelectQuery = false;
	}
	else {
		status += ".";
	}

        __statusJTextField.setText(status);
	
	JGUIUtil.setWaitCursor(this, false);
	__transactReport = null;
	__netAmountsReport = null;	
}    

/**
Responds to the __outputTemplateJComboBox item state changed event.
@throws Exception if an error occurs.
*/
private void templateClicked()
throws Exception {
        String selected  = __tableJComboBox.getSelected();

        if (!__currTable.equalsIgnoreCase(selected)) {
                // the present table does not match the table used in
                // the latest query. cannot re-arrange data in this case
                // to reflect the requested template. Perhaps the user
                // is changing templates for a new query so just return.
                return;
        }

	JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Displaying new query template...");

        // display for transaction table
        if (__currTable.equalsIgnoreCase(HydroBase_GUI_Util._TRANS)) {
                displayTransactionResults(__resultsTransact);
        }                
        // display for net amounts table
        else if (__currTable.equalsIgnoreCase(HydroBase_GUI_Util._NET_AMOUNTS)){
                displayNetResults(__resultsNetAmts);
        }
        ready();
}

/**
Listener for window activated events.  Does nothing.
@param event the WindowEvent that happened.
*/
public void windowActivated(WindowEvent event) {}
/**
Listener for window closed events.  Does nothing.
@param event the WindowEvent that happened.
*/
public void windowClosed(WindowEvent event) {}

/**
Listener for window closing events.  Calls closeClicked().
@param event the WindowEvent that happened.
*/
public void windowClosing(WindowEvent event) {
        closeClicked();
}

/**
Listener for window deactivated events.  Does nothing.
@param event the WindowEvent that happened.
*/
public void windowDeactivated(WindowEvent event) {}
/**
Listener for window deiconified events.  Does nothing.
@param event the WindowEvent that happened.
*/
public void windowDeiconified(WindowEvent event) {}
/**
Listener for window iconified events.  Does nothing.
@param event the WindowEvent that happened.
*/
public void windowIconified(WindowEvent event) {}
/**
Listener for window opened events.  Does nothing.
@param event the WindowEvent that happened.
*/
public void windowOpened(WindowEvent event) {}

}

