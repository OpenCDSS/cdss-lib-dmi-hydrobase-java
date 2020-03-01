// HydroBase_GUI_ReservoirMeasurement - Reservoir Measurements GUI

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
// HydroBase_GUI_ReservoirMeasurement - Reservoir Measurements GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 01 Sept 1997	Darrell L. Gillmeister, RTi Created initial version.
// 06 Oct 1997 	DLG, RTi		Added querying functionality
// 06 Dec 1997	Steven A. Malers, RTi	Update to enable export, print.
// 10 Feb 1998 	DLG, RTi		changed column headings to AC-FT for
//					flows. Updated to java 1.1 event model.
// 04 Apr 1999	SAM, RTi		Added HBDMI to queries.
// 2001-11-12	SAM, RTi		Change GUI to JGUIUtil.
//					Remove import *.
// 2002-02-25	SAM, RTi		Increase ID textfield width.
//-----------------------------------------------------------------------------
// 2003-09-26	J. Thomas Sapienza, RTi	Initial Swing version.
// 2004-01-21	JTS, RTi		Changed to use the new JWorksheet method
//					of displaying a row count column.
// 2005-02-15	JTS, RTi		Converted all queries to use stored
//					procedures.
// 2005-03-24	JTS, RTi		Added support for View objects.
// 2005-05-09	JTS, RTi		All structure queries now return
//					structure view objects.
// 2005-06-22	JTS, RTi		* Column widths now come from the 
//					  table model, not the cell renderer.
//					* The table-specific cell renderers 
//					  were removed and replaced with a 
//					  single generic one.
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

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.JScrollWorksheet;
import RTi.Util.GUI.JWorksheet;

import RTi.Util.IO.PrintJGUI;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

/**
This class is a gui for displaying historic reservoir measurements.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_ReservoirMeasurement 
extends JFrame 
implements ActionListener, WindowListener {

/**
Button labels.
*/
private final String 
	__BUTTON_CLOSE = 	"Close",
	__BUTTON_EXPORT = 	"Export",
	__BUTTON_HELP = 	"Help",
	__BUTTON_PRINT = 	"Print";

/**
The dmi used to connect to the database.
*/
private HydroBaseDMI __dmi;

/**
The number of the reservoir structure being displayed.
*/
private int __structureNum;

/**
GUI buttons.
*/
private JButton 
	__closeJButton,
	__exportJButton,
	__printJButton;

/**
The label for the worksheet.
*/
private JLabel __listJLabel;

/**
Data text fields.
*/
private JTextField
	__statusJTextField,
	__structureJTextField,
	__divJTextField,
	__idJTextField,
	__wdJTextField;

/**
The worksheet used to display time series data.
*/
private JWorksheet __worksheet;

/**
The name of the structure being displayed.
*/
private String __structureName;

/**
Constructor.
@param dmi the DMI to use for connecting to the database.
@param structureNum the number of the reservoir for which to show measurements.
@param structureName the name of the reservoir for which to show measurements.
*/
public HydroBase_GUI_ReservoirMeasurement(HydroBaseDMI dmi, int structureNum,
String structureName) {
        __dmi = dmi;
        __structureName = structureName;
	__structureNum = structureNum;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
        // set up the GUI objects
        setupGUI();

        submitStructureQuery();
        submitReservoirMeasurementQuery();

	int wd = (new Integer(__wdJTextField.getText().trim())).intValue();
	int id = (new Integer(__idJTextField.getText().trim())).intValue();
	String name = __structureName;

	String rest = "Structure Data - Historical Reservoir Measurements - "
		+ HydroBase_WaterDistrict.formWDID(wd, id)
		+ " (" + name + ")";
	if (	(JGUIUtil.getAppNameForWindows() == null) ||
		JGUIUtil.getAppNameForWindows().equals("") ) {
		setTitle ( rest );
	}
	else {	setTitle( JGUIUtil.getAppNameForWindows() +
		" - " + rest);
	}								
}

/**
Responds to actionPerformed events.
@param event the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent event) {
	String routine = "HydroBase_GUI_ReservoirMeasurement.actionPerformed";
	String actionCommand = event.getActionCommand();

        if (actionCommand.equals(__BUTTON_CLOSE)) {
		closeClicked();
        }
        else if (actionCommand.equals(__BUTTON_EXPORT)) {
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
        else if (actionCommand.equals(__BUTTON_HELP)) {
        }
        else if (actionCommand.equals(__BUTTON_PRINT)) {
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
Responds when the form is closed.
*/
private void closeClicked() {
	setVisible(false);
	dispose();
}

/**
Displays the structure data in the GUI.
*/
private void displayStructureResults(HydroBase_StructureView structure) {
	String curString = null;
	int curInt = 0;

        curInt = structure.getDiv();
        if (DMIUtil.isMissing(curInt) || HydroBase_Util.isMissing(curInt)) {
                curString = "";
        }
        else {
                curString = "" + curInt;
        }
        __divJTextField.setText(curString);

        curInt = structure.getWD();
        if (DMIUtil.isMissing(curInt) || HydroBase_Util.isMissing(curInt)) {
                curString = "";
        }
        else {
                curString = "" + curInt;
        }
        __wdJTextField.setText(curString);

        curInt = structure.getID();
        if (DMIUtil.isMissing(curInt) || HydroBase_Util.isMissing(curInt)) {
                curString = "";
        }
        else {
                curString = StringUtil.formatString(curInt, "%5d");             
        }
        __idJTextField.setText(curString);
}

/**
Cleans up member variables.
*/
public void finalize() 
throws Throwable {
	__dmi = null;
	__closeJButton = null;
	__exportJButton = null;
	__printJButton = null;
	__listJLabel = null;
	__statusJTextField = null;
	__structureJTextField = null;
	__divJTextField = null;
	__idJTextField = null;
	__wdJTextField = null;
	__worksheet = null;
	__structureName = null;
	super.finalize();
}

/**
Formats output for printing or writing to a file.
@param format the format in which to format the output.
*/
public List<String> formatOutput(int format) {
	List<String> v = new Vector<String>();
	// First get the multilist back as strings...

	int size = __worksheet.getRowCount();

	String s0, s1, s2, s3, s4, s5;
	if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
		// The output is pretty simple since the GUI is so simple...
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			HydroBase_GUI_Util.trimText(__structureJTextField),
			HydroBase_GUI_Util.trimText(__divJTextField),
			HydroBase_GUI_Util.trimText(__wdJTextField),
			HydroBase_GUI_Util.trimText(__idJTextField),
			format));
		v.add("");
		v.add("                                              "
			+ "RESERVOIR MEASUREMENT DATA");
		v.add("DATE           GAGE HT.      STORAGE        "
			+ "INFLOW         RELEASE       EVAP. LOSS");
		v.add("              (FEET)        (AF)       (AF) "
			+ "        (AF)            (AF)");
		v.add("____________________________________________"
			+ "____________________________________");
		// Now do the curve(skip the header)...
		for (int i = 0; i < size; i++) {
			s0 = ((Date)(__worksheet.getValueAt(i, 0))).toString();
			s1 = __worksheet.getValueAtAsString(i, 1, "%11.1f");
			s2 = __worksheet.getValueAtAsString(i, 2, "%14.0f");
			s3 = __worksheet.getValueAtAsString(i, 3, "%16.0f");
			s4 = __worksheet.getValueAtAsString(i, 4, "%16.0f");
			s5 = __worksheet.getValueAtAsString(i, 5, "%16.0f");

			v.add(
				StringUtil.formatString(s0.trim(), "%10.10s")
				+ StringUtil.formatString(s1, "%11.11s")
				+ StringUtil.formatString(s2, "%14.14s")
				+ StringUtil.formatString(s3 ,"%16.16s")
				+ StringUtil.formatString(s4, "%16.16s")
				+ StringUtil.formatString(s5,"%16.16s"));
		}
	}
	else {	
                char delim = HydroBase_GUI_Util.getDelimiterForFormat(format);	
		v.add(HydroBase_GUI_Util.formatStructureHeader(format));
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			HydroBase_GUI_Util.trimText( __structureJTextField),
			HydroBase_GUI_Util.trimText(__divJTextField),
			HydroBase_GUI_Util.trimText(__wdJTextField),
			HydroBase_GUI_Util.trimText(__idJTextField),
			format));
		v.add("");
		v.add("DATE" + delim + "GAGE HT.(FEET)" + delim 
			+ "STORAGE(AF)" + delim + "INFLOW(AF)" + delim
			+ "RELEASE(AF)" + delim + "EVAP LOSS(AF)" + delim);
		// Now do the curve(skip the header)...
		for (int i = 0; i < size; i++) {
			s0 = ((Date)(__worksheet.getValueAt(i, 0))).toString();
			s1 = __worksheet.getValueAtAsString(i, 1, "%11.1f");
			s2 = __worksheet.getValueAtAsString(i, 2, "%14.0f");
			s3 = __worksheet.getValueAtAsString(i, 3, "%16.0f");
			s4 = __worksheet.getValueAtAsString(i, 4, "%16.0f");
			s5 = __worksheet.getValueAtAsString(i, 5, "%16.0f");
			v.add(
				StringUtil.formatString(s0.trim(), "%10.10s")
				.trim()
				+ delim + s1.trim()
				+ delim + s2.trim()
				+ delim + s3.trim()
				+ delim + s4.trim()
				+ delim + s5.trim());
			
		}
	}	

	return v;
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	String routine = "HydroBase_GUI_ReservoirMeasurement.setupGUI";

        // objects used throughout the GUI layout
        Insets insetsNLNR = new Insets(0,7,0,7);
        Insets insetsNLBR = new Insets(0,7,7,7);
        Insets insetsTLNR = new Insets(7,7,0,7);
        Insets insetsTLBR = new Insets(7,7,7,7);
        GridBagLayout gbl = new GridBagLayout();

        addWindowListener(this);
               
        // North JPanel
        JPanel North_JPanel = new JPanel();
        North_JPanel.setLayout(new BorderLayout());
        getContentPane().add("North", North_JPanel);

        // North: West JPanel
        JPanel NorthW_JPanel = new JPanel();
        NorthW_JPanel.setLayout(gbl);
        North_JPanel.add("West", NorthW_JPanel);

        JGUIUtil.addComponent(NorthW_JPanel, new JLabel("Structure Name"),
		0, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST); 

        JGUIUtil.addComponent(NorthW_JPanel, new JLabel("DIV"),
		1, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
     
        JGUIUtil.addComponent(NorthW_JPanel, new JLabel("WD"),
		2, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(NorthW_JPanel, new JLabel("ID"),
		3, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __structureJTextField = new JTextField(__structureName);
        __structureJTextField.setEditable(false);
        JGUIUtil.addComponent(NorthW_JPanel, __structureJTextField,
		0, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
       
        __divJTextField = new JTextField(5);
        __divJTextField.setEditable(false);
        JGUIUtil.addComponent(NorthW_JPanel, __divJTextField,
		1, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __wdJTextField = new JTextField(5);
        __wdJTextField.setEditable(false);
        JGUIUtil.addComponent(NorthW_JPanel, __wdJTextField,
		2, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __idJTextField = new JTextField(10);
        __idJTextField.setEditable(false);
        JGUIUtil.addComponent(NorthW_JPanel, __idJTextField,
		3, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(NorthW_JPanel, 
		new JLabel("See also Diversion Coding and Reservoir Release "
		+ " data."),
		0, 2, 6, 1, 0, 0, insetsTLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        // Center JPanel
        JPanel center_JPanel = new JPanel();
        center_JPanel.setLayout(gbl);
        getContentPane().add("Center", center_JPanel);

        __listJLabel = new JLabel(HydroBase_GUI_Util.LIST_LABEL);
        JGUIUtil.addComponent(center_JPanel, __listJLabel, 0, 0, 3, 1, 0, 0, 
		insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	PropList p = new PropList("HydroBase_GUI_ReservoirMeasurement"
		+ ".JWorksheet");
	/*
	p.add("JWorksheet.CellFont=Courier");
	p.add("JWorksheet.CellStyle=Plain");
	p.add("JWorksheet.CellSize=11");
	p.add("JWorksheet.HeaderFont=Arial");
	p.add("JWorksheet.HeaderStyle=Plain");
	p.add("JWorksheet.HeaderSize=11");
	p.add("JWorksheet.HeaderBackground=LightGray");
	p.add("JWorksheet.RowColumnPresent=false");
	*/
	p.add("JWorksheet.ShowRowHeader=true");
	p.add("JWorksheet.ShowPopupMenu=true");
	p.add("JWorksheet.AllowCopy=true");	
	p.add("JWorksheet.SelectionMode=ExcelSelection");

	int[] widths = null;
	JScrollWorksheet jsw = null;
	try {
		HydroBase_TableModel_ReservoirMeasurement tm = new
			HydroBase_TableModel_ReservoirMeasurement(new Vector<HydroBase_ResMeas>());
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
	
		jsw = new JScrollWorksheet(cr, tm, p);
		__worksheet = jsw.getJWorksheet();

		widths = tm.getColumnWidths();
	}
	catch (Exception e) {
		Message.printWarning(2, routine, e);
		jsw = new JScrollWorksheet(0, 0, p);
		__worksheet = jsw.getJWorksheet();
	}
	__worksheet.setPreferredScrollableViewportSize(null);
	__worksheet.setHourglassJFrame(this);

	JGUIUtil.addComponent(center_JPanel, new JScrollPane(__worksheet), 
		0, 1, 3, 3, 1, 1, 
		insetsNLBR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

        // Bottom JPanel
        JPanel bottom_JPanel = new JPanel();
        bottom_JPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottom_JPanel);

        // Bottom: North JPanel
        JPanel bottomN_JPanel = new JPanel();
        bottomN_JPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottom_JPanel.add("North", bottomN_JPanel);

        __printJButton = new JButton(__BUTTON_PRINT);
	__printJButton.addActionListener(this);
	__printJButton.setToolTipText("Print form data.");
        bottomN_JPanel.add(__printJButton);

        __exportJButton = new JButton(__BUTTON_EXPORT);
	__exportJButton.addActionListener(this);
	__exportJButton.setToolTipText("Export data to a file.");
        bottomN_JPanel.add(__exportJButton);

        __closeJButton = new JButton(__BUTTON_CLOSE);
	__closeJButton.addActionListener(this);
	__closeJButton.setToolTipText("Close form.");
        bottomN_JPanel.add(__closeJButton);

//      __helpJButton = new JButton(__BUTTON_HELP);
//	__helpJButton.addActionListener(this);
//	__helpJButton.setEnabled(false);
//      bottomN_JPanel.add(__helpJButton);

        // Bottom: South JPanel
        JPanel bottomS_JPanel = new JPanel();
        bottomS_JPanel.setLayout(gbl);
        bottom_JPanel.add("South", bottomS_JPanel);

        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomS_JPanel, __statusJTextField, 
		0, 1, 10, 1, 1, 0, 
		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);


	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}

        // Frame settings
        setTitle(app + "Reservoir Measurements");
        setSize(700,400);
        JGUIUtil.center(this);
        setVisible(true);

	if (widths != null) {
		__worksheet.setColumnWidths(widths);
	}
}

/**
Submits the reservoir measurement query.
*/
private void submitReservoirMeasurementQuery() {
        String routine = "HydroBase_GUI_ReservoirMeasurement"
		+ ".submitReservoirMeasurementQuery()";
        String dmiQuery = "Reservoir Measurement Query";

        __listJLabel.setText(HydroBase_GUI_Util.LIST_LABEL);

	JGUIUtil.setWaitCursor(this, true);
        String tempString = "Please wait... Performing " + dmiQuery;
        __statusJTextField.setText(tempString);
              
	
        int numRecords = 0;
	try {
		List<HydroBase_ResMeas> results = 
			__dmi.readResMeasListForStructure_num(__structureNum);
	        if (results != null && results.size() > 0) {
			__worksheet.setData(results);
			numRecords = results.size();
	        }
	}
	catch (Exception e) {
		Message.printWarning(1, routine, e);
	}

        tempString = dmiQuery + " completed.";
        __statusJTextField.setText(tempString);
	String plural = "s";
	if (numRecords == 1) {
		plural = "";
	}
	__listJLabel.setText(HydroBase_GUI_Util.LIST_LABEL + " ("
		+ numRecords + " record" + plural + " returned)");
        JGUIUtil.setWaitCursor(this, false);
}    

/**
Constructs and submits the structure query.
*/
private void submitStructureQuery() {  
	String routine = "HydroBase_GUI_ReservoirMeasurement"
		+ ".submitStructureQuery";
        String dmiQuery = "Structure Query";

	JGUIUtil.setWaitCursor(this, true);
        String message = "Please wait... Performing " + dmiQuery;
        __statusJTextField.setText(message);
              
        // results Vector contains the reservoir query results
	try {
		HydroBase_StructureView view 
			= __dmi.readStructureViewForStructure_num(
			__structureNum);
		displayStructureResults(view);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, e);
	}

        message = dmiQuery + " completed.";
        __statusJTextField.setText(message);
	JGUIUtil.setWaitCursor(this, false);
}    


public void windowActivated(WindowEvent evt) {;}
public void windowClosed(WindowEvent evt) {;}
public void windowClosing(WindowEvent evt) {
	closeClicked();
}
public void windowDeactivated(WindowEvent evt) {;}
public void windowDeiconified(WindowEvent evt) {;}
public void windowIconified(WindowEvent evt) {;}
public void windowOpened(WindowEvent evt) {;}

}
