// HydroBase_GUI_IrrigatedAcresSummary - Irrigated Acres Summary Data GUI

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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleJButton;

import RTi.Util.IO.PrintJGUI;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

@SuppressWarnings("serial")
public class HydroBase_GUI_IrrigatedAcresSummary 
extends JFrame
implements ActionListener, WindowListener {

/**
Class name.
*/
public final static String CLASS = "HydroBase_GUI_IrrigatedAcresSummary";

/**
Button labels.
*/
private final String
	__BUTTON_CLOSE	= 	"Close",
	__BUTTON_EXPORT = 	"Export",
	__BUTTON_HELP	= 	"Help",
	__BUTTON_PRINT	= 	"Print";

/**
The dmi to use for communicating with the database.
*/
private HydroBaseDMI __dmi;

/**
The structure num.
*/
private int __structureNum;

/**
GUI text fields.
*/
private JTextField
	__divTotDateJTextField,
	__divTotJTextField,
	__gisTotDateJTextField,
	__gisTotJTextField,
	__structTotDateJTextField,
	__structTotJTextField,
	__structureDivJTextField,
	__structureNameJTextField,
	__structureIDJTextField,
	__structureWDJTextField;

/**
@param dmi dmi to use for communicating with the database.
@param structureName Structure name as determined by the Structure Query GUI.
@param structureNum Structure number as determined by the Structure Query GUI.
*/
public HydroBase_GUI_IrrigatedAcresSummary(HydroBaseDMI dmi,
String structureName, int structureNum) {
	__dmi = dmi;
        __structureNum = structureNum;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());	
        // Instantiate GUI components
        setupGUI();

        // Submit total acres query as specified from Structure Query
        submitQuery();

	int wd = Integer.valueOf(__structureWDJTextField.getText().trim()).intValue();
	int id = Integer.valueOf(__structureIDJTextField.getText().trim()).intValue();
	String name = __structureNameJTextField.getText().trim();

	String rest = "Structure Data - Irrigated Acres Summary - "
		+ HydroBase_WaterDistrict.formWDID(wd, id) 
		+ " (" + name + ")";

	if ( (JGUIUtil.getAppNameForWindows() == null) || JGUIUtil.getAppNameForWindows().equals("") ) {
		setTitle ( rest );
	}
	else {	setTitle( JGUIUtil.getAppNameForWindows() + " - " + rest);
	}					
}

/**
Responds to ActionEvents.
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

			int format = Integer.valueOf(eff[1]).intValue();
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
Closes the GUI.
*/
private void closeClicked() {
	setVisible(false);
	dispose();
}

/**
This function displays the requested irrigated acres summary query in the
components on the gui.
@param results list containing the results from the query
*/
private void displayResults(List<HydroBase_StructureView> results) {
	String routine = CLASS + ".displayResults";
	HydroBase_StructureView structure = null;
	try {
		structure = __dmi.readStructureViewForStructure_num(__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine,"Error reading from database.");
		Message.printWarning(2, routine, e);
	}

	String curString = structure.getStr_name();
	if (curString.length() > 0) {
		__structureNameJTextField.setText(curString);
	}
	int curInt = structure.getDiv();
	if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
		__structureDivJTextField.setText("" + curInt);
	}
	curInt = structure.getWD();
	if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
		__structureWDJTextField.setText("" + curInt);
	}
	curInt = structure.getID();
	if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
		__structureIDJTextField.setText("" + curInt);
	}

        // Display irrigated acres results...
        if (results != null && !results.isEmpty()) {
                // data contains the query results. Since only 
                // one record is returned, request the first element
                // in the results Vector.        
                HydroBase_StructureView data = results.get(0);
    
                double curDouble = data.getTia_gis();
                if (!DMIUtil.isMissing(curDouble) && !HydroBase_Util.isMissing(curDouble)) {
                        __gisTotJTextField.setText(StringUtil.formatString(curDouble, "%6.1f").trim());
                }

                curInt = data.getTia_gis_calyear();
                if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                        __gisTotDateJTextField.setText("" + curInt);
                }

                curDouble = data.getTia_div();
                if (!DMIUtil.isMissing(curDouble) && !HydroBase_Util.isMissing(curDouble)) {
                        __divTotJTextField.setText(StringUtil.formatString(curDouble, "%6.1f").trim());
                }

                curInt = data.getTia_div_calyear();
                if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                        __divTotDateJTextField.setText("" + curInt);
                }        

                curDouble = data.getTia_struct();
                if (!DMIUtil.isMissing(curDouble) && !HydroBase_Util.isMissing(curDouble)) {
                        __structTotJTextField.setText(StringUtil.formatString(curDouble, "%6.1f").trim());
                }        

                curInt = data.getTia_struct_calyear();
                if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                        __structTotDateJTextField.setText("" + curInt);
                }        
        }
}

/**
Formats output.
@param format format delimiter flag defined in this class
@return returns a formatted Vector for exporting, printing, etc..
*/
public List<String> formatOutput(int format) {
	List<String> v = new Vector<String>();

	if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
		// The output is pretty simple since the GUI is so simple...
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			__structureNameJTextField.getText(),
			__structureDivJTextField.getText(),
			__structureWDJTextField.getText(),
			__structureIDJTextField.getText(), format));
		v.add("");
		v.add("GIS Total: " 
			+ StringUtil.formatString(
			__gisTotJTextField.getText().trim(), "%-10.1f")
			+ "Most Recently Reported: " 
			+ StringUtil.formatString(
			__gisTotDateJTextField.getText().trim(), "%-10.0d")
			+ "Diversion Comments Total: " 
			+ StringUtil.formatString(
			__divTotJTextField.getText().trim(), "%-10.0f")
			+ "Most Recently Reported: " 
			+ StringUtil.formatString(
			__divTotDateJTextField.getText().trim(), "%-10.0d")
			+ "Structure Total: " 
			+ StringUtil.formatString(
			__structTotJTextField.getText().trim(), "%-10.0f")
			+ "Most Recently Reported: " 
			+ StringUtil.formatString(
			__structTotDateJTextField.getText().trim(), "%-10.0d"));
	}
	else {	
		char delim = HydroBase_GUI_Util.getDelimiterForFormat(format);	
		v.add(HydroBase_GUI_Util.formatStructureHeader(format));
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			__structureNameJTextField.getText(),
			__structureDivJTextField.getText(),
			__structureWDJTextField.getText(),
			__structureIDJTextField.getText(), format));
		v.add("");
		v.add("GIS Total" + delim + "Year:" + delim 
			+ "Diversion Comments Total:" + delim +	"Year:" + delim 
			+ "Structure Total" + delim + "Year:" + delim);
		v.add(
			__gisTotJTextField.getText().trim()+ delim
			+ __gisTotDateJTextField.getText().trim()+ delim
			+ __divTotJTextField.getText().trim()+ delim
			+ __divTotDateJTextField.getText().trim()+ delim
			+ __structTotJTextField.getText().trim()+ delim
			+ __structTotDateJTextField.getText().trim()+ delim);
	}	

	return v;
}

/**
This function instantiates and displays all GUI components onto the Frame.
*/
private void setupGUI() {
	addWindowListener(this);

        // objects used throughout the GUI layout
        Insets insetsNLNR = new Insets(0,7,0,7);
        Insets insetsNLBR = new Insets(0,7,7,7);
        Insets insetsTLNR = new Insets(7,7,0,7);
        GridBagLayout gbl = new GridBagLayout();
	int y = 0;

        // Top JPanel
        JPanel topJPanel = new JPanel();
        topJPanel.setLayout(new BorderLayout());
        getContentPane().add("North", topJPanel);

	// Top:  West JPanel
	JPanel topHeaderJPanel = new JPanel();
	topHeaderJPanel.setLayout(gbl);
	topJPanel.add("West", topHeaderJPanel);

        JGUIUtil.addComponent(topHeaderJPanel, new JLabel("Structure Name:"),
		0, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(topHeaderJPanel, new JLabel("DIV:"),
		1, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(topHeaderJPanel, new JLabel("WD:"),
		2, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(topHeaderJPanel, new JLabel("ID:"),
		3, y++, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __structureNameJTextField = new JTextField(15);
        __structureNameJTextField.setEditable(false);
        JGUIUtil.addComponent(topHeaderJPanel, __structureNameJTextField, 
		0, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        __structureDivJTextField = new JTextField(10);
        __structureDivJTextField.setEditable(false);
        JGUIUtil.addComponent(topHeaderJPanel, __structureDivJTextField, 
		1, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        __structureWDJTextField = new JTextField(10);
        __structureWDJTextField.setEditable(false);
        JGUIUtil.addComponent(topHeaderJPanel, __structureWDJTextField, 
		2, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        __structureIDJTextField = new JTextField(10);
        __structureIDJTextField.setEditable(false);
        JGUIUtil.addComponent(topHeaderJPanel, __structureIDJTextField, 
		3, y++, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	y = 0;

        // Center JPanel
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(new BorderLayout());
        getContentPane().add("Center", centerJPanel);

        JPanel topWJPanel = new JPanel();
        topWJPanel.setLayout(gbl);
        centerJPanel.add("West", topWJPanel);

        JGUIUtil.addComponent(topWJPanel, 
		new JLabel("Irrigated Acres Summary:"),
		0, y++, 2, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(topWJPanel, new JLabel("GIS Total (Acres):"),
		0, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        __gisTotJTextField = new JTextField(5);
        __gisTotJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __gisTotJTextField, 
		1, y, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(topWJPanel, 
		new JLabel("Most Recent Report Year:"),
		2, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        __gisTotDateJTextField = new JTextField(5);
        __gisTotDateJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __gisTotDateJTextField, 
		3, y++, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);


        JGUIUtil.addComponent(topWJPanel, 
		new JLabel("Diversion Comments Total (Acres):"),
		0, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        __divTotJTextField = new JTextField(5);
        __divTotJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __divTotJTextField, 
		1, y, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(topWJPanel, 
		new JLabel("Most Recent Report Year:"),
		2, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        __divTotDateJTextField = new JTextField(5);
        __divTotDateJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __divTotDateJTextField, 
		3, y++, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(topWJPanel, 
		new JLabel("Structure Total (Acres):"),
		0, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        __structTotJTextField = new JTextField(5);
        __structTotJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __structTotJTextField, 
		1, y, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(topWJPanel, 
		new JLabel("Most Recent Report Year:"),
		2, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        __structTotDateJTextField = new JTextField(5);
        __structTotDateJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __structTotDateJTextField, 
		3, y++, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        

        // Bottom JPanel
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add("South", bottomJPanel);

	SimpleJButton print = new SimpleJButton(__BUTTON_PRINT, this);
	print.setToolTipText("Print form data.");
        bottomJPanel.add(print);
	SimpleJButton export = new SimpleJButton(__BUTTON_EXPORT, this);
	export.setToolTipText("Export data to a file.");
        bottomJPanel.add(export);
	SimpleJButton close = new SimpleJButton(__BUTTON_CLOSE, this);
	close.setToolTipText("Close form.");
        bottomJPanel.add(close);
//	SimpleJButton help = new SimpleJButton(__BUTTON_HELP, this);
//	help.setEnabled(false);
//      bottomJPanel.add(help);

        // Frame settings
        setTitle("Irrigated Acres Summary");
        pack(); 
        JGUIUtil.center(this);
	setResizable(false);
        setVisible(true);        
}

/**
This function performs a query.
@param whereClause previously constructed where clause to use during the query.
*/
private void submitQuery() {
	String routine = CLASS + ".submitQuery";
	List<HydroBase_StructureView> results = null;
        // perform Irrigated Acres query
        JGUIUtil.setWaitCursor(this, true);
              
	try {
		results = __dmi.readStructureIrrigSummaryListForStructure_num(
			__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine,"Error reading from database.");
		Message.printWarning(2, routine, e);
	}
	
        displayResults(results); 

	JGUIUtil.setWaitCursor(this, false);
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
