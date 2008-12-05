//-----------------------------------------------------------------------------
// HydroBase_GUI_Headgate - Headgate Information GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
// 19 Aug 1997	DLG, RTi 		Created initial version.
// 07 Dec 1997	SAM, RTi		Implement full export/print.
// 29 Apr 1998  DLG, RTi		Updated to 1.1 event model, added
//					javadoc comments.
// 04 Apr 1999	Steven A. Malers, RTi	Add HBDMI to queries.
// 02 Sep 1999	SAM, RTi		Remove import *.
// 2001-11-12	SAM, RTi		Change GUI to GUIUtil.
//					Remove static from internal strings.
//-----------------------------------------------------------------------------
// 2003-12-08	J. Thomas Sapienza, RTi	Initial Swing version.
// 2005-02-14	JTS, RTi		* Checked all dmi calls to make sure 
//					  they use stored procedures.
//					* Changed query to the more specific
//					  version that passes in a structure
//					  number rather than a where clause.
// 2005-04-28	JTS, RTi		Added finalize().
// 2005-05-09	JTS, RTi		All structure queries now return
//					structure view objects.
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

public class HydroBase_GUI_Headgate 
extends JFrame
implements ActionListener, WindowListener {

/**
Class name.
*/
public final static String CLASS = "HydroBase_GUI_Headgate";

/**
Button labels.
*/
private final String
	__CLOSE	= "Close",
	__EXPORT	= "Export",
	__HELP	= "Help",
	__PRINT	= "Print";

/**
HydroBaseDMI object to use for database communication.
*/
private HydroBaseDMI __dmi;

/**
Structure number.
*/
private int __structureNum;

/**
GUI text fields.
*/
private JTextField   	
	__divJTextField,
        __idJTextField,
        __statusJTextField,
    	__structureJTextField,
	__wdJTextField;

/**
Structure name.
*/
private String __strName;

private int 
	__id,
	__wd;

/**
Constructor.
HydroBase_GUI_Headgate constructor for previously determined where Clause.
Use this constructor when NOT using Query By Example.
@param dmi HydroBaseDMI object to use for database communication.
@param structureName Structure name as determined by the Structure Query GUI.
@param structureNum Structure number as determined by the Structure Query GUI.
*/
public HydroBase_GUI_Headgate(HydroBaseDMI dmi, String structureName, 
int structureNum) {
	String routine = CLASS + "()";
	__dmi = dmi;
        __structureNum = structureNum;
	__strName = structureName;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());	
	int curInt;

	Object o = null;

	try {
		o = __dmi.readStructureViewForStructure_num(__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Could not read from "
			+ "database.");
		Message.printWarning(2, routine, e);
	}

	if (o == null) {
		return;
	}

	HydroBase_StructureView data = (HydroBase_StructureView)o;
	if (data != null) {
		__id = data.getDiv();
		__wd = data.getWD();
	}

        // Instantiate GUI components
        setupGUI();

	if (data != null) {
		curInt = data.getDiv();
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
	}
}

/**
Constructor.
Use this constructor when using Query By Example.
@param dmi the dmi to use for database communication.
*/
public HydroBase_GUI_Headgate(HydroBaseDMI dmi) {
        __dmi = dmi;
        
        // instantiate GUI components
        setupGUI();
}

/**
Responds to ActionEvents
@param evt ActionEvent object
*/
public void actionPerformed(ActionEvent evt) {
	String routine = CLASS + ".actionPerformed";

	String s = evt.getActionCommand();

        if (s.equals(__CLOSE)) {
		setVisible(false);
		dispose();
        }
        else if (s.equals(__EXPORT)) {
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
		catch(Exception ex) {
			Message.printWarning(2, routine, ex);
		}	
        }
        else if (s.equals(__HELP)) {
        }
        else if (s.equals(__PRINT)) {
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
		catch(Exception ex) {
			Message.printWarning(2, routine, ex);
		}			
        }
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	__dmi = null;
	__divJTextField = null;
        __idJTextField = null;
        __statusJTextField = null;
    	__structureJTextField = null;
	__wdJTextField = null;
	__strName = null;
	super.finalize();
}

/**
Responsible for formatting output.
@param FORMAT format delimiter flag defined in this class
@return returns a formatted Vector for exporting, printing, etc..
*/
public List formatOutput(int FORMAT) {
	List v = new Vector();

	if (FORMAT == HydroBase_GUI_Util.SCREEN_VIEW) {
		// The output is pretty simple since the GUI is so simple...
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			__structureJTextField.getText(),
			__divJTextField.getText(),
			__wdJTextField.getText(),
			__idJTextField.getText(), FORMAT));
	}
	else {	
		v.add(HydroBase_GUI_Util.formatStructureHeader(FORMAT));
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			__structureJTextField.getText(),
			__divJTextField.getText(),
			__wdJTextField.getText(),
			__idJTextField.getText(), FORMAT));
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
        JPanel topLeftJPanel = new JPanel();
        topLeftJPanel.setLayout(gbl);
        topJPanel.add("West", topLeftJPanel);
 
        JGUIUtil.addComponent(topLeftJPanel, new JLabel("Structure Name:"), 
		0, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
           
        JGUIUtil.addComponent(topLeftJPanel, new JLabel("DIV:"), 
		1, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(topLeftJPanel, new JLabel("WD:"), 
		2, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
  
        JGUIUtil.addComponent(topLeftJPanel, new JLabel("ID:"), 
		3, 0, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __structureJTextField = new JTextField();
	__structureJTextField.setText(__strName);
	__structureJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel, __structureJTextField, 
		0, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __divJTextField = new JTextField(5);
	__divJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel, __divJTextField, 
		1, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __wdJTextField = new JTextField(5);
	__wdJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel, __wdJTextField, 
		2, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __idJTextField = new JTextField(5);
	__idJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel, __idJTextField, 
		3, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
 
	JPanel timeJPanel = new JPanel();
	timeJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JGUIUtil.addComponent(topLeftJPanel, timeJPanel, 
		0, 2, 4, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);

	timeJPanel.add(new JLabel("Currently there is no additional"
		+ " information for Headgates."));
                                
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

	SimpleJButton print = new SimpleJButton(__PRINT, this);
	print.setToolTipText("Print form data.");
        bottomSouthNorthJPanel.add(print);
	SimpleJButton export = new SimpleJButton(__EXPORT, this);
	export.setToolTipText("Export data to a file.");
        bottomSouthNorthJPanel.add(export);
	SimpleJButton close = new SimpleJButton(__CLOSE, this);
	close.setToolTipText("Close the form.");
        bottomSouthNorthJPanel.add(close);
//        SimpleJButton help = new SimpleJButton(__HELP, this);
//	help.setEnabled(false);
//	bottomSouthNorthJPanel.add(help);

        // Bottom: South: South JPanel
        JPanel bottomSouthSouthJPanel = new JPanel();
        bottomSouthSouthJPanel.setLayout(gbl);
        bottomSouthJPanel.add("South", bottomSouthSouthJPanel);

        __statusJTextField = new JTextField();
	__statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomSouthSouthJPanel, __statusJTextField, 
		0, 1, 10, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // Frame settings
        String rest = "Structure Data - Headgate Detail - "
		+ HydroBase_WaterDistrict.formWDID(__wd, __id)
		+ " (" + __strName + ")";
	
	if (	(JGUIUtil.getAppNameForWindows() == null) ||
		JGUIUtil.getAppNameForWindows().equals("") ) {
		setTitle ( rest );
	}
	else {	setTitle( JGUIUtil.getAppNameForWindows() +
		" - " + rest);
	}			
	
        pack();
	JGUIUtil.center(this);
        setVisible(true);        
}

/**
Does nothing.
*/
public void windowActivated(WindowEvent evt) {}

/**
Does nothing.
*/
public void windowClosed(WindowEvent evt) {}

/**
Closes the GUI
@param evt WindowEvent object
*/
public void windowClosing(WindowEvent evt) {
	setVisible(false);
	dispose();
}

/**
Does nothing.
*/
public void windowDeactivated(WindowEvent evt) {}

/**
Does nothing.
*/
public void windowDeiconified(WindowEvent evt) {}

/**
Does nothing.
*/
public void windowIconified(WindowEvent evt) {}

/**
Does nothing.
*/
public void windowOpened(WindowEvent evt) {}

}
