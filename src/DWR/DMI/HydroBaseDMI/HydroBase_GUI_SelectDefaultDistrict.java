//-----------------------------------------------------------------------------
// HydroBase_GUI_SelectDefaultDistrict - GUI to select a district
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History: 
//
// 14 Apr 1999	Steven A. Malers, RTi	First version of GUI to select the
//					default district.
// 2001-11-12	SAM, RTi		Change GUI to GUIUtil.
//					Change IO to IOUtil.
//					Don't use static data for private
//					strings.
//-----------------------------------------------------------------------------
// 2003-03-26	J. Thomas Sapienza, RTi	Convert to Swing.
// 2003-04-04	JTS, RTi		Changed GUIUtil to JGUIUtil.
// 2005-04-12	JTS, RTi		MutableJList changed to SimpleJList.
// 2005-04-28	JTS, RTi		Added finalize().
// 2005-11-16	JTS, RTi		Added support for the "Entire State"
//					option.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import RTi.Util.Message.Message;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleJList;


/**
GUI to select a default district for menus, etc.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_SelectDefaultDistrict 
extends JDialog
implements ActionListener, KeyListener, ItemListener, WindowListener,
ListSelectionListener {

/**
Reference to parent's dmi object.
*/
private HydroBaseDMI __dmi = null;

/**
Parent.
*/
private HydroBase_GUI_Options __parent = null;

/**
District list.
*/
private	SimpleJList __districtJList = null;

/**
OK JButton
*/
private JButton __okJButton = null;

/**
Current default district JTextField.
*/
private JTextField __currentDefaultJTextField = null;
/**
Status bar.
*/
private JTextField __statusJTextField = null;

/**
Default district that has been selected.
*/
private static String __defaultDistrict = "NONE";

/**
Miscellaneous Strings.
*/
private final String 
	__BUTTON_CANCEL = 	"Cancel",
	__BUTTON_HELP = 	"Help",
	__BUTTON_OK = 		"OK";

/**
String for No Preference.
*/
private final static String __NONE = "NONE";

/**
Construct GUI.
@param parent HydroBase_GUI_Options gui that instantiated this object.
@param dmi reference to the parent's dmi object.
*/
public HydroBase_GUI_SelectDefaultDistrict(HydroBase_GUI_Options parent,
HydroBaseDMI dmi) {
	super(parent, true);
	__parent = parent;
	__dmi = dmi;

	setupGUI();
}

/**
This function responds to action performed events.
@param evt the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent evt) {
	String command = evt.getActionCommand();
	
	if (command.equals(__BUTTON_OK)) {
		okClicked();
	}  
	else if (command.equals(__BUTTON_CANCEL)) {
        	cancelClicked();
	}
	else if (command.equals(__BUTTON_HELP)) {
	}
}      

/**
If Cancel is clicked, this method is called.  It disposes the dialog.
*/
private void cancelClicked() {
	super.setVisible(false);
	dispose();
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	__dmi = null;
	__parent = null;
	__districtJList = null;
	__okJButton = null;
	__currentDefaultJTextField = null;
	__statusJTextField = null;
	super.finalize();
}

/**
Returns the selected default district.
@return the selected default district.
*/
public static String getDefaultDistrict() {
	return __defaultDistrict;
}

/**
Responds to key pressed events.
@param evt the KeyEvent that happened.
*/
public void keyPressed(KeyEvent evt) {
	int code = evt.getKeyCode();

	// enter key is the same as ok
	if (code == KeyEvent.VK_ENTER) {
       		okClicked();
	}
	// F1 envokes help
	else if (code == KeyEvent.VK_F1) {
	}
}

/**
Responds to key released events; does nothing.
@param event the KeyEvent that happened.
*/
public void keyReleased(KeyEvent event) {}

/**
Responds to key typed events; does nothing.
@param event the KeyEvent that happened.
*/
public void keyTyped(KeyEvent event) {}

/**
Handle itemStateChanged events; does nothing.
@param event Event to handle.
*/
public void itemStateChanged(ItemEvent event) {}

/**
Process event if OK is selected.
*/
private void okClicked() { 
	// Get the selected district from the list...
	__defaultDistrict = (String)__districtJList.getSelectedValue();
	String prefDistrict = __defaultDistrict;
	if (prefDistrict.equals(HydroBase_GUI_Util._ALL_DIVISIONS)) {
		// keep as is
	}
	else if (!prefDistrict.equals(__NONE)) {
		// We want the district number and " - name..."
		prefDistrict = prefDistrict.substring(5).trim();
	}
	setVisible(false);
	dispose();
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	String routine = "HydroBase_GUI_SelectDefaultDistrict.setupGUI";

	addWindowListener(this);
                
        // used in the GridBagLayouts
        Insets LTB_insets = new Insets(7,7,0,0);
        GridBagLayout gbl = new GridBagLayout();

        // North JPanel
        JPanel northJPanel = new JPanel();
        northJPanel.setLayout(new BorderLayout());
        getContentPane().add("North", northJPanel);
        
        // North West JPanel
        JPanel northWJPanel = new JPanel();
        northWJPanel.setLayout(gbl);
        northJPanel.add("West", northWJPanel);

	int y = 0;	// Vertical position of components.

        JGUIUtil.addComponent(northWJPanel, new JLabel(
		"* indicates that the district is in the current database"), 
		0, y, 2, 1, 0, 0, LTB_insets, GridBagConstraints.NONE, GridBagConstraints.CENTER);
	++y;
        JGUIUtil.addComponent(northWJPanel, new JLabel(
		"The districts are grouped by division."), 
		0, y, 2, 1, 0, 0, LTB_insets, GridBagConstraints.NONE, GridBagConstraints.CENTER);

	// Show the current default...

	++y;
        JGUIUtil.addComponent(northWJPanel, new JLabel(
		"Current default:"), 
		0, y, 1, 1, 0, 0, LTB_insets, GridBagConstraints.NONE, GridBagConstraints.EAST);

	__currentDefaultJTextField = new JTextField(40);
	__currentDefaultJTextField.setText(
		__dmi.getPreferenceValue("WD.DistrictDefault"));
	__currentDefaultJTextField.setEnabled(false);
        JGUIUtil.addComponent(northWJPanel, __currentDefaultJTextField,
		1, y, 1, 1, 0, 0, LTB_insets, GridBagConstraints.NONE, GridBagConstraints.WEST);

	// Add list and show 20 districts...
	++y;
	String [] selectedDistricts = __parent.getSelectedDistricts();
	int nselectedDistricts = 0;
	if (selectedDistricts != null) {
		nselectedDistricts = selectedDistricts.length;
	}

	// This button is constructed here to avoid any null-pointer errors
	// in the valueChanged() method after the __districtJList is 
	// constructed and values are added to it.
	__okJButton = new JButton(__BUTTON_OK);
	__okJButton.setToolTipText("Accept selection.");

	__districtJList = new SimpleJList();
	__districtJList.setSelectionMode(
		ListSelectionModel.SINGLE_SELECTION);
	__districtJList.addListSelectionListener(this);
        JGUIUtil.addComponent(northWJPanel, new JScrollPane(__districtJList),
		0, y, 2, 1, 0, 0, LTB_insets, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
	
	// Always add none...
	__districtJList.add(__NONE);

	// Always add Entire State.
	if (__dmi.getDatabaseVersion() >= HydroBaseDMI.VERSION_20050701) {
		__districtJList.add(HydroBase_GUI_Util._ALL_DIVISIONS);
	}
	
	// Now add all the districts that have been selected in the parent
	// HBOptionsGUI.  If the original is encountered, select it.
	// Always select the one from the preferences.  If it is not in the
	// list, select NONE...
	String userDefault = 
		__dmi.getPreferenceValue("WD.DistrictDefault").trim();
	Message.printStatus(1, routine, "Current default is \"" +
		userDefault + "\"");
	int matchIndex = 0;
	if (selectedDistricts != null) {
		// Populate the list...
		String district, substring;
		for (int i = 0; i < nselectedDistricts; i++) {
			district = selectedDistricts[i];
			__districtJList.add(district);
			// Check to see if the current default is in the list.
			// If it is, add it...
			substring = district.substring(5).trim();
			if (	!userDefault.equals("NONE")&&
				substring.equalsIgnoreCase(userDefault)) {
				// NONE has already been added so add one to
				// the index...
				matchIndex = i + 1;
			}
		}
	}
	__districtJList.select(matchIndex);
                        
        // South JPanel
        JPanel southJPanel = new JPanel();
        southJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", southJPanel);
                
        // South North JPanel
        JPanel southNJPanel = new JPanel();
        southNJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        southJPanel.add("North", southNJPanel);
        
	__okJButton.addActionListener(this);
        southNJPanel.add(__okJButton);
        
        JButton cancelJButton = new JButton(__BUTTON_CANCEL);
	cancelJButton.setToolTipText("Cancel selection and return.");
	cancelJButton.addActionListener(this);
        southNJPanel.add(cancelJButton);

//        JButton helpJButton = new JButton(__BUTTON_HELP);
//	helpJButton.addActionListener(this);
//        southNJPanel.add(helpJButton);
        
        // South South JPanel
        JPanel southSJPanel = new JPanel();
        southSJPanel.setLayout(gbl);
        southJPanel.add("South", southSJPanel);
        
        __statusJTextField = new JTextField();
        __statusJTextField.setBackground(Color.lightGray);
	__statusJTextField.setEditable(false);
        JGUIUtil.addComponent(southSJPanel, __statusJTextField, 
		0, 0, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	__statusJTextField.setText(
	"Select a default district(for menus, queries, etc.)");
        
        // frame settings        
        setBackground(Color.lightGray);        
	
	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}
	
        setTitle(app + "Select Default District");
        pack();
	JGUIUtil.center(this);
        setResizable(false);    	
	setVisible(true);
}

/**
Responds to value changed events.
@param evt the ListSelectionEvent that happened.
*/
public void valueChanged(ListSelectionEvent evt) {
	if (__districtJList.getSelectedIndex() == -1) {
		// nothing selected, disable OK
		__okJButton.setEnabled(false);
	}
	else {
		__okJButton.setEnabled(true);
	}
}

/**
Responds to window activated event; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowActivated(WindowEvent evt) {}

/**
Responds to window closed event; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowClosed(WindowEvent evt) {}

/**
Responds to window closing event.
@param evt the WindowEvent that happened.
*/
public void windowClosing(WindowEvent evt) {
	cancelClicked();
}

/**
Responds to window deactivated event; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowDeactivated(WindowEvent evt) {}

/**
Responds to window deiconified event; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowDeiconified(WindowEvent evt) {}

/**
Responds to window iconified event; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowIconified(WindowEvent evt) {}

/**
Responds to window opened event; does nothing.
@param evt the WindowEvent that happened.
*/
public void windowOpened(WindowEvent evt) {}

}
