// HydroBase_GUI_DailyWC - QInfo Report GUI

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
// HydroBase_GUI_DailyWC - QInfo Report GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 01 Sep 1997	DLG, RTi 		Created initial version.
// 08 Dec 1997	SAM, RTi		Enable print, export.
// 29 Apr 1998  DLG, RTi		Updated to 1.1 event model, added
//					javadoc comments.
// 04 Apr 1999	Steven A. Malers, RTi	Add HBDMI to queries.
// 2001-11-12	SAM, RTi		Change GUI to JGUIUtil.
//					Remove import *.
//					Don't use static strings for internal
//					data.
//-----------------------------------------------------------------------------
// 2003-12-11	J. Thomas Sapienza, RTi	Initial Swing version.
// 2004-01-20	JTS, RTi		Began using the JScrollWorksheet in
//					order to use worksheet row headers.
// 2005-02-14	JTS, RTi		Converted all queries to use
//					stored procedures.
// 2005-02-17	JTS, RTi		Can now handled HydroBase_WISDailyWC
//					objects, which are returned when 
//					stored procedures are used.
// 2005-04-27	JTS, RTi		Added finalize().
// 2005-05-09	JTS, RTi		All structure queries now return
//					structure view objects.
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.Generic_CellRenderer;
import RTi.Util.GUI.Generic_TableModel;
import RTi.Util.GUI.GenericWorksheetData;
import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.JScrollWorksheet;
import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.SimpleJButton;

import RTi.Util.IO.PrintJGUI;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

/**
This class is a GUI for displaying DailyWC information for a structure.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_DailyWC 
extends JFrame 
implements ActionListener, WindowListener {

/**
The class name.
*/
public final static String CLASS = "HydroBase_GUI_DailyWC";

/**
Button labels.
*/
private final String
	__BUTTON_CLOSE		= "Close",
	__BUTTON_EXPORT		= "Export",
	__BUTTON_HELP		= "Help",
	__BUTTON_PRINT		= "Print";

/**
The DMI used to communicate with the database.
*/
private HydroBaseDMI __dmi;

/**
The number of the structure to display data for.
*/
private int __structureNum;

/**
The label that appears next to the worksheet.
*/
private JLabel __worksheetJLabel;

/**
GUI textfields.
*/
private JTextField
	__divJTextField,
	__idJTextField,
	__statusJTextField,
	__structureJTextField,
	__unitsJTextField,
	__wdJTextField;

/**
The worksheet for displaying tabular wc data.
*/
private JWorksheet __worksheet;

/**
String information about the structure.
*/
private String
	__recordType,
	__structureName;

/**
Constructor.
@param dmi the dmi to use for communicating with the database.
@param structureNum structure number
@param structureName structure name
*/
public HydroBase_GUI_DailyWC(HydroBaseDMI dmi, int structureNum, 
String structureName) {
        __dmi = dmi;
	__structureNum = structureNum;
        __structureName = structureName;

        // set up the GUI objects
        setupGUI();

	submitQuery();

	int wd = (new Integer(__wdJTextField.getText().trim())).intValue();
	int id = (new Integer(__idJTextField.getText().trim())).intValue();
	String name = __structureName;
	String rest = "Structure Data - Historical Diversion Daily WC - "
		+ HydroBase_WaterDistrict.formWDID(wd, id)
		+ " (" + name + ")";
	if ((JGUIUtil.getAppNameForWindows() == null) ||
		JGUIUtil.getAppNameForWindows().equals("")) {
		setTitle(rest);
	}
	else {	
		setTitle(JGUIUtil.getAppNameForWindows() + " - " + rest);
	}									
}

/**
Responds to ActionEvents
@param event ActionEvent object
*/
public void actionPerformed(ActionEvent event) {
	String routine = CLASS + ".actionPerformed";
	String s = event.getActionCommand();

        if (s.equals(__BUTTON_CLOSE)) {
		closeClicked();
        }
	else if (s.equals(__BUTTON_EXPORT)) {
		try {
			String[] eff = 
				HydroBase_GUI_Util.getExportFilenameAndFormat(
				this, 
				HydroBase_GUI_Util.getFormatsAndExtensions());

			if (eff == null) {
				return ;
			}

			int format = new Integer(eff[1]).intValue();
	 		// First format the output...
			List<String> outputStrings = formatOutput(format);
 			// Now export, letting the user decide the file...
			HydroBase_GUI_Util.export(this, eff[0], outputStrings);
		} 
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}		
	}
        else if (s.equals(__BUTTON_HELP)) {
        }
	else if (s.equals(__BUTTON_PRINT)) {
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
			PrintJGUI.print(this, outputStrings);
		}
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}			
	}
}

/**
Builds a table model capable of displaying data for the GenericWorksheetData
in the list.
@param v a non-null, non-empty list of GenericWorksheetData objects.
@return a Generic_TableModel for displaying the given data.
*/
private Generic_TableModel buildTableModel(List<GenericWorksheetData> v) {
	try {
		Generic_TableModel model = new Generic_TableModel(v);

		model.setColumnInformation(0, "IRR YEAR", "%4s", 8);
		model.setColumnInformation(1, "IRR MON", "%4s", 7);
		model.setColumnInformation(2, "S", "%2s", 2);
		model.setColumnInformation(3, "F", "%2d", 2);
		model.setColumnInformation(4, "U", "%2s", 2);
		model.setColumnInformation(5, "T", "%2s", 2);
	
		for (int i = 1; i < 32; i++) {
			model.setColumnInformation(5 + i, "" + i, "%8s", 8);
		}
		
		model.setColumnInformation(37, "FUNC", "%20s", 10);
		model.setColumnInformation(38, "QUALITY", "%20s", 10);
	
		return model;
	}
	catch (Exception e) {
		return null;
	}
}

/**
Closes the GUI.
*/
private void closeClicked() {
	setVisible(false);
	dispose();
}

/**
This function displays the query results within the appropriate GUI objects.
@param results list containing the results from the query
*/
private void displayResults(List<HydroBase_DailyWC> results) {
	List<GenericWorksheetData> v = resultsToGenericData(results);

	Generic_TableModel model = buildTableModel(v);

	if (model == null) {
		return;
	}

	if (__dmi.useStoredProcedures()) {
	        HydroBase_WISDailyWC data 
			= (HydroBase_WISDailyWC)results.get(0);
	        __unitsJTextField.setText("" + data.getUnit());
	
		Generic_CellRenderer renderer = new Generic_CellRenderer(model);
		__worksheet.setCellRenderer(renderer);
		__worksheet.setModel(model);
		__worksheet.setColumnWidths(renderer.getColumnWidths());
	}
	else {
	        HydroBase_DailyWC data =(HydroBase_DailyWC)results.get(0);
	        __unitsJTextField.setText("" + data.getUnit());
	
		Generic_CellRenderer renderer = new Generic_CellRenderer(model);
		__worksheet.setCellRenderer(renderer);
		__worksheet.setModel(model);
		__worksheet.setColumnWidths(renderer.getColumnWidths());
	}	
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	__dmi = null;
	__worksheetJLabel = null;
	__divJTextField = null;
	__idJTextField = null;
	__statusJTextField = null;
	__structureJTextField = null;
	__unitsJTextField = null;
	__wdJTextField = null;
	__worksheet = null;
	__recordType = null;
	__structureName = null;
	super.finalize();
}

/**
Formats output for printing or export.
@param format format delimiter flag defined in this class
@return returns a formatted Vector for exporting, printing, etc..
*/
public List<String> formatOutput(int format) {
	String routine = CLASS + ".formatOutput";
	List<String> v = new Vector<String>();

	// First get the multilist back as strings...
	int size = __worksheet.getRowCount();

	if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
		Message.printWarning(1, routine, "This type of export/print "
			+ "is not enabled yet.  Will not work.");
		return null;
		/*
		This code wasn't doing anything in the original version 
		so it's commented out here.  Probably not a pressing issue
		to get it done.
		// The output is pretty simple since the GUI is so simple...
		v.addElement(HBGUI.formatStructureHeader(
			__structureJTextField.getText(),
			__divJTextField.getText(),
			__wdJTextField.getText(),
			__idJTextField.getText(), format));
		Vector strings;
		Vector records = new Vector(50, 50);
		//HBStructureDailyWaterColorRecord record;
		HydroBase_DailyWC record;
		String string = "";
		for (int i = 0; i < size; i++) {
			strings = HBGUI.breakMultiListString(
				(String)list_strings.elementAt(i));
			if (strings.size()< 24) {
				// Problem??
				continue;
			}
			//record = new HBStructureDailyWaterColorRecord();
			record = new HydroBase_DailyWC();
			// Set the record type so that we get the correct
			// headings...
			// record.setRecordType(__recordType);
			record.setIrrigationYear(
			Integer.parseInt(
			(String)strings.elementAt(0)));
			string =(String)strings.elementAt(1);
			record.setCalendarMonth(string);
			record.setIrrigationMonth(string);

			string =(String)strings.elementAt(2);
			if (string.length()> 0) {
				record.setSource(string.trim());
			}
			string =(String)strings.elementAt(3);
			if (string.length()> 0) {
				record.setFrom(string.trim());
			}
			string =(String)strings.elementAt(4);
			if (string.length()> 0) {
				record.setUse(string.trim());
			}
			string =(String)strings.elementAt(5);
			if (string.length()> 0) {
				record.setType(string.trim());
			}

			for (int j = 0; j < 31; j++) {
				string =(String)strings.elementAt(6 + j);
				if (string.length()> 0) {
					// Need to strip off observation
					// flags...
					String amount =
					HBStructureDailyAmountRecord
					.getDataAmount(string);
					String obs =
					HBStructureDailyAmountRecord
					.getDataObservationCode(string);
					record.setAmountForDay(j,
					(new Double(amount)).doubleValue());
					record.setObservationCodeForDay(j,
						obs);
				}
			}
			string =(String)strings.elementAt(37);
			if (string.length()> 0) {
				record.setFunctionCode(string);
			}
			string =(String)strings.elementAt(38);
			if (string.length()> 0) {
				record.setQuality(string);
			}
			// Add the record to the list...
			records.addElement(record);
		}
		// Now we have the vector, so get the formatted
		// output...
		//Vector formatted =
		//HBDMIUtil.formatDailyDiversionForQINFO(
		//_parent.getHBDMI(), records, true);
		//v = StringUtil.addListToStringList(v, formatted);
		*/
	}
	else {	
		char delim = HydroBase_GUI_Util.getDelimiterForFormat(format);	
		v.add(HydroBase_GUI_Util.formatStructureHeader(format));
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			__structureJTextField.getText(),
			__divJTextField.getText(),
			__wdJTextField.getText(),
			__idJTextField.getText(), format));
		v.add("");
		// Now format the list by just spitting back what is in
		// the multilist...
		String s;		
		for (int i = 0; i < size; i++) {
			s = "";
			for (int j = 0; j < 38; j++) {
				s += (__worksheet.getValueAtAsString(i, j))
					.toString() + delim;
			}
			s += __worksheet.getValueAtAsString(i, 38);
			v.add(s);
		}
	}

	return v;
}

/**
Turns a Vector of HydroBase_DailyWC objects from a query into a Vector of
GenericWorksheetData objects.
@param results the non-null, non-empty Vector of HydroBase_DailyWC objects.
@return a Vector of GenericWorksheetData objects.
*/
private List<GenericWorksheetData> resultsToGenericData(List<HydroBase_DailyWC> results) {
	if (__dmi.useStoredProcedures()) {
		List<GenericWorksheetData> v = new Vector<GenericWorksheetData>();
		int size = results.size();
		HydroBase_DailyWC result = null;
		GenericWorksheetData data = null;
		double d;
		String s;
	        for (int i = 0; i < size; i++) {
	                result = results.get(i);
			data = new GenericWorksheetData(39);
			data.setValueAt(0, "" + result.getIrr_year());
			data.setValueAt(1, "" + result.getIrr_mon());
			data.setValueAt(2, result.getS());
			data.setValueAt(3, new Integer(result.getF()));
			data.setValueAt(4, result.getU());
			data.setValueAt(5, result.getT());
	
			for (int j = 1; j < 32; j++) {
				d = result.getAmountForDay(j);
				if (DMIUtil.isMissing(d) || HydroBase_Util.isMissing(d)) {
					s = "";
				}
				else {
					s = StringUtil.formatString(d, "%7.2f");
				}
				s += result.getObservationForDay(j);
				data.setValueAt(5 + j, s);
			}
				
			data.setValueAt(37, result.getFunc());
			data.setValueAt(38, result.getQuality());
		
			v.add(data);
		}
	
		return v;	
	}
	else {
		List<GenericWorksheetData> v = new Vector<GenericWorksheetData>();
		int size = results.size();
		HydroBase_DailyWC result = null;
		GenericWorksheetData data = null;
		double d;
		String s;
	        for (int i = 0; i < size; i++) {
	                result = (HydroBase_DailyWC)results.get(i);
			data = new GenericWorksheetData(39);
			data.setValueAt(0, "" + result.getIrr_year());
			data.setValueAt(1, "" + result.getIrr_mon());
			data.setValueAt(2, result.getS());
			data.setValueAt(3, new Integer(result.getF()));
			data.setValueAt(4, result.getU());
			data.setValueAt(5, result.getT());
	
			for (int j = 1; j < 32; j++) {
				d = result.getAmountForDay(j);
				if (DMIUtil.isMissing(d) || HydroBase_Util.isMissing(d)) {
					s = "";
				}
				else {
					s = StringUtil.formatString(d, "%7.2f");
				}
				s += result.getObservationForDay(j);
				data.setValueAt(5 + j, s);
			}
				
			data.setValueAt(37, result.getFunc());
			data.setValueAt(38, result.getQuality());
		
			v.add(data);
		}
	
		return v;
	}
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	addWindowListener(this);

        // objects used throughout the GUI layout
        Insets insetsTLBR = new Insets(7,7,7,7);
        Insets insetsNLBR = new Insets(0,7,7,7);
        Insets insetsTLNR = new Insets(7,7,0,7);
        GridBagLayout gbl = new GridBagLayout();
               
        // North JPanel
        JPanel NorthJPanel = new JPanel();
        NorthJPanel.setLayout(new BorderLayout());
        getContentPane().add("North", NorthJPanel);

        // North: West JPanel
        JPanel NorthWJPanel = new JPanel();
        NorthWJPanel.setLayout(gbl);
        NorthJPanel.add("West", NorthWJPanel);

        JGUIUtil.addComponent(NorthWJPanel, new JLabel("Structure Name"), 
		0, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST); 

        JGUIUtil.addComponent(NorthWJPanel, new JLabel("DIV"), 
		1, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
     
        JGUIUtil.addComponent(NorthWJPanel, new JLabel("WD"), 
		2, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(NorthWJPanel, new JLabel("ID"), 
		3, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __structureJTextField = new JTextField(__structureName);
        __structureJTextField.setEditable(false);
        JGUIUtil.addComponent(NorthWJPanel, __structureJTextField, 
		0, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
       
        __divJTextField = new JTextField(5);
        __divJTextField.setEditable(false);
        JGUIUtil.addComponent(NorthWJPanel, __divJTextField, 
		1, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __wdJTextField = new JTextField(5);
        __wdJTextField.setEditable(false);
        JGUIUtil.addComponent(NorthWJPanel, __wdJTextField, 
		2, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __idJTextField = new JTextField(5);
        __idJTextField.setEditable(false);
        JGUIUtil.addComponent(NorthWJPanel, __idJTextField, 
		3, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        // Center JPanel
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(gbl);
        add("Center", centerJPanel);

        JGUIUtil.addComponent(centerJPanel, new JLabel("UNITS:"), 
		0, 0, 1, 1, 0, 0, insetsTLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);        

        __unitsJTextField = new JTextField(5);
        __unitsJTextField.setEditable(false);
        JGUIUtil.addComponent(centerJPanel, __unitsJTextField, 
		1, 0, 1, 1, 0, 0, insetsTLBR, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __worksheetJLabel = new JLabel(HydroBase_GUI_Util.LIST_LABEL);
        JGUIUtil.addComponent(centerJPanel, __worksheetJLabel, 
		0, 1, 3, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	PropList p = new PropList("HydroBase_GUI_DailyWC.JWorksheet");
	p.add("JWorksheet.ShowRowHeader=true");
	p.add("JWorksheet.AllowCopy=true");
	p.add("JWorksheet.ShowPopupMenu=true");	

	JScrollWorksheet jsw = new JScrollWorksheet(0, 0, p);
        __worksheet = jsw.getJWorksheet();
	__worksheet.setHourglassJFrame(this);

        JGUIUtil.addComponent(centerJPanel, jsw,
		0, 2, 3, 3, 1, 1, insetsNLBR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

        // Bottom JPanel
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);

        // Bottom: North JPanel
        JPanel bottomNJPanel = new JPanel();
        bottomNJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add("North", bottomNJPanel);

	SimpleJButton print = new SimpleJButton(__BUTTON_PRINT, this);
	print.setToolTipText("Print the data from the form.");
	bottomNJPanel.add(print);
	SimpleJButton export = new SimpleJButton(__BUTTON_EXPORT, this);
	export.setToolTipText("Export the data to a file.");
	bottomNJPanel.add(export);
	SimpleJButton close = new SimpleJButton(__BUTTON_CLOSE, this);
	close.setToolTipText("Close the form.");
	bottomNJPanel.add(close);
//	bottomNJPanel.add(new SimpleJButton(__BUTTON_HELP, this));

        // Bottom: South JPanel
        JPanel bottomSJPanel = new JPanel();
        bottomSJPanel.setLayout(gbl);
        bottomJPanel.add("South", bottomSJPanel);

        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomSJPanel, __statusJTextField, 
		0, 1, 10, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // Frame settings
        setTitle("Historic Diversion - Daily WC");
        setSize(800,400);
        JGUIUtil.center(this);
        setVisible(true);
}

/**
This function shows or hides the GUI.
@param state if true, the GUI is shown.  If false, it is hidden.
*/
public void setVisible(boolean state) {
        super.setVisible(state);
        if (state) {
                __worksheetJLabel.setText(HydroBase_GUI_Util.LIST_LABEL);
        }
}

/**
This function constructs and submits the query.
*/
private void submitQuery() {
	String routine = CLASS + ".submitQuery";
	
	HydroBase_StructureView structure = null;
	try {
		structure = (HydroBase_StructureView)
			__dmi.readStructureViewForStructure_num(
			__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine,
			"Error reading from database.");
		Message.printWarning(2, routine, e);
		return;
	}
	
	__recordType = structure.getStr_type().trim();
	// if the __recordType ls 'H'(headgate), set to
	// 'D'(diversion)
	if (__recordType.equals("H")) {
		__recordType = "D";
	}

	int curInt = structure.getDiv();
	if (DMIUtil.isMissing(curInt) || HydroBase_Util.isMissing(curInt)) {
		__divJTextField.setText("" + curInt);
	}

	curInt = structure.getWD();
	if (DMIUtil.isMissing(curInt) || HydroBase_Util.isMissing(curInt)) {
		__wdJTextField.setText("" + curInt);
	}

	curInt = structure.getID();
	if (DMIUtil.isMissing(curInt) || HydroBase_Util.isMissing(curInt)) {
		__idJTextField.setText("" + curInt);
	}        

	List<HydroBase_DailyWC> results = null;
	try {
		results = 
		 	__dmi.readDailyWCListForStructure_numRecordType(
			__structureNum, __recordType);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading from database.");
		Message.printWarning(2, routine, e);
		return;
	}

        __worksheetJLabel.setText(HydroBase_GUI_Util.LIST_LABEL);

	int numRecords = 0;
        if (results != null && results.size() > 0) {
		displayResults(results);            
		numRecords = results.size();		
        }

	String plural = "s";
	if (numRecords == 1) {
		plural = "";
	}
	__worksheetJLabel.setText("" + numRecords + " record" + plural + " returned.");
        __statusJTextField.setText("Ready");
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
@param event WindowEvent object
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

}
