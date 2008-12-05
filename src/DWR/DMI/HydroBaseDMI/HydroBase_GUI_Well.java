//-----------------------------------------------------------------------------
// HydroBase_GUI_Well - Well or Well Field Data GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 13 Oct 1997	DLG, RTi		Created initial version.
// 07 Dec 1997	SAM, RTi		Enable print and export.
// 30 Apr 1998  DLG, RTi		Updated to 1.1 event model, added
//					javadoc comments.
// 04 Apr 1999	Steven A. Malers, RTi	Add HBDMI to queries.
// 02 Sep 1999	SAM, RTi		Remove import *.  Add a label to
//					indicate taht no additional well data
//					are available.
// 15 May 2001	SAM, RTi		Add a finalize()method.  Don't use
//					static for simple GUI strings.  Change
//					GUI to JGUIUtil.
//-----------------------------------------------------------------------------
// 2003-10-02	J. Thomas Sapienza, RTi	Initial Swing version.
// 2005-02-14	JTS, RTi		Converted all queries to use 
//					stored procedures.
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

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleJButton;

import RTi.Util.IO.PrintJGUI;

import RTi.Util.Message.Message;

/**
This class is a GUI for displaying well data.  Right now, there's not much 
to show.
*/
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

	int wd = (new Integer(__wdJTextField.getText().trim())).intValue();
	int id = (new Integer(__idJTextField.getText().trim())).intValue();
	String name = __structureName;

	String rest = "Structure Data - Well Data Detail - "
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

			int format = new Integer(eff[1]).intValue();
	 		// First format the output...
			List outputStrings = formatOutput(format);
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
			List outputStrings = formatOutput(format);
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
private List formatOutput(int format) {
	List v = new Vector();

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
Clean up before garbage collection.
*/
protected void finalize()
throws Throwable {
	__dmi = null;
	__divJTextField = null;
	__idJTextField = null;
	__statusJTextField = null;
	__structureJTextField = null;
	__wdJTextField = null;
	__structureName = null;
	super.finalize();
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
	if (!DMIUtil.isMissing(curInt)) {
		__divJTextField.setText("" + curInt);
	}

	curInt = data.getWD();
	if (!DMIUtil.isMissing(curInt)) {
		__wdJTextField.setText("" + curInt);
	}

	curInt = data.getID();
	if (!DMIUtil.isMissing(curInt)) {
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
