// HydroBase_GUI_Well - Well or Well Field Data GUI

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

/**
This class is a GUI for displaying well data.  Right now, there's not much 
to show.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_Well 
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
The dmi used to communicate with the database.
*/
private HydroBaseDMI __dmi;

/**
The number of the well shown.
*/
private int __structureNum;

/**
Data fields.
*/
private JTextField     
	__divJTextField,
	__idJTextField,
	__statusJTextField,
	__structureJTextField,
	__wdJTextField;

/**
The name of the well being shown.
*/
private String __structureName;

/**
Constructor.
@param dmi the dmi used to communicate with the database.
@param structureName the name of the structure to show.
@param structureNum the number of the well to show.
*/
public HydroBase_GUI_Well(HydroBaseDMI dmi, String structureName, 
int structureNum) {
	__dmi = dmi;
        __structureNum = structureNum;
        __structureName = structureName;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI();

	submitAndDisplayStructureQuery();

	int wd = Integer.valueOf(__wdJTextField.getText().trim()).intValue();
	int id = Integer.valueOf(__idJTextField.getText().trim()).intValue();
	String name = __structureName;

	String rest = "Structure Data - Well Data Detail - "
		+ HydroBase_WaterDistrict.formWDID(wd, id)
		+ " (" + name + ")";
	if ( (JGUIUtil.getAppNameForWindows() == null) || JGUIUtil.getAppNameForWindows().equals("") ) {
		setTitle ( rest );
	}
	else {
		setTitle( JGUIUtil.getAppNameForWindows() + " - " + rest);
	}								
}

/**
Responds to action performed events.
@param evt the ActionEvent that occured.
*/
public void actionPerformed(ActionEvent evt) {
	String routine = "HydroBase_GUI_Util.actionPerformed";
	String s = evt.getActionCommand();

        if (s.equals(__BUTTON_CLOSE )) {
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
Closes the gui.
*/
private void closeClicked() {
	setVisible(false);
	dispose();
}

/**
Formats output for printing and exporting.
@param format the format in which to format the output.
*/
private List<String> formatOutput(int format) {
	List<String> v = new Vector<String>();

	if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			HydroBase_GUI_Util.trimText(__structureJTextField),
			HydroBase_GUI_Util.trimText(__divJTextField),
			HydroBase_GUI_Util.trimText(__wdJTextField),
			HydroBase_GUI_Util.trimText(__idJTextField), format));
		v.add(new String());
		v.add(new String("Well data not loaded in HydroBase"));
	}
	else {	
		v.add(HydroBase_GUI_Util.formatStructureHeader(format));
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			HydroBase_GUI_Util.trimText(__structureJTextField),
			HydroBase_GUI_Util.trimText(__divJTextField),
			HydroBase_GUI_Util.trimText(__wdJTextField),
			HydroBase_GUI_Util.trimText(__idJTextField), format));
		v.add(new String());
		v.add(new String("Well data not loaded in HydroBase"));
	}	

	return v;
}

/**
This function instantiates and displays all GUI components onto the Frame.
*/
private void setupGUI() {
	addWindowListener(this);
        // objects used throughout the GUI layout
        Insets insetsNLBR = new Insets(0,7,7,7);
        Insets insetsTLNR = new Insets(7,7,0,7);
        GridBagLayout gbl = new GridBagLayout();

        // Top JPanel
        JPanel topJPanel = new JPanel();
        topJPanel.setLayout(new BorderLayout());
        getContentPane().add("North", topJPanel);

        // Top: West JPanel
        JPanel topWJPanel = new JPanel();
        topWJPanel.setLayout(gbl);
        topJPanel.add("West", topWJPanel);

        JLabel nameJLabel = new JLabel("Structure Name:");
        JGUIUtil.addComponent(topWJPanel, nameJLabel, 
		0, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
 
        JLabel divJLabel = new JLabel("DIV:");
        JGUIUtil.addComponent(topWJPanel, divJLabel, 
		1, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JLabel wdJLabel = new JLabel("WD:");
        JGUIUtil.addComponent(topWJPanel, wdJLabel, 
		2, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JLabel idJLabel = new JLabel("ID:");
        JGUIUtil.addComponent(topWJPanel, idJLabel, 
		3, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __structureJTextField = new JTextField();
        __structureJTextField.setText(__structureName);
        __structureJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __structureJTextField, 
		0, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __divJTextField = new JTextField(5);
        __divJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __divJTextField, 
		1, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __wdJTextField = new JTextField(5);
        __wdJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __wdJTextField, 
		2, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __idJTextField = new JTextField(5);
        __idJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __idJTextField, 
		3, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	JPanel timeJPanel = new JPanel();
	timeJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JGUIUtil.addComponent(topWJPanel, timeJPanel, 
		0, 2, 4, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);

	timeJPanel.add(new JLabel("Currently there is no additional"
		+ " information for Wells."));
        
        // Bottom JPanel
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);

        // Bottom: South JPanel
        JPanel bottomSouthJPanel = new JPanel();
        bottomSouthJPanel.setLayout(new BorderLayout());
        bottomJPanel.add("South", bottomSouthJPanel);

        // Bottom: South: North JPanel
        JPanel bottomSouthNorthJPanel = new JPanel();
        bottomSouthNorthJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomSouthJPanel.add("North", bottomSouthNorthJPanel);

	SimpleJButton print = new SimpleJButton(__BUTTON_PRINT, this);
	print.setToolTipText("Print form data.");
        bottomSouthNorthJPanel.add(print);
	SimpleJButton export = new SimpleJButton(__BUTTON_EXPORT, this);
	export.setToolTipText("Export data to a file.");
        bottomSouthNorthJPanel.add(export);
	SimpleJButton close = new SimpleJButton(__BUTTON_CLOSE, this);
	close.setToolTipText("Close form.");
        bottomSouthNorthJPanel.add(close);
//	SimpleJButton helpJButton = new SimpleJButton(__BUTTON_HELP, this);
//        bottomSouthNorthJPanel.add(helpJButton);
//	helpJButton.setEnabled(false);

        // Bottom: South: South JPanel
        JPanel bottomSSJPanel = new JPanel();
        bottomSSJPanel.setLayout(gbl);
        bottomSouthJPanel.add("South", bottomSSJPanel);

        __statusJTextField = new JTextField(); 
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomSSJPanel, __statusJTextField, 
		0, 1, 10, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
      
        // Frame settings
        setTitle("Well Data");
        pack(); 
        JGUIUtil.center(this);
        setVisible(true);        
}

/**
Submits a query for structure data and displays the data in the gui.
*/
private void submitAndDisplayStructureQuery() {
	String routine = "HydroBase_GUI_Well.submitAndDisplayStructureQuery";
	JGUIUtil.setWaitCursor(this, true);

	Object o = null;
	try {
		o = __dmi.readStructureViewForStructure_num(__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading structure "
			+ "data.");
		Message.printWarning(2, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	if (o == null) {
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	HydroBase_StructureView data = (HydroBase_StructureView)o;
	int curInt = data.getDiv();
	if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
		__divJTextField.setText("" + curInt);
	}

	curInt = data.getWD();
	if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
		__wdJTextField.setText("" + curInt);
	}

	curInt = data.getID();
	if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
		__idJTextField.setText("" + curInt);
	}      
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
