//-----------------------------------------------------------------------------
// HydroBase_GUI_CallsComments - GUI for changing the comments and districts
//	affected for a given call.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 2005-04-12	J. Thomas Sapienza, RTi	Initial version.
// 2005-04-26	JTS, RTi		Release comments are only editable now
//					if the call was specified in the 
//					constructor as having been released.
// 2005-04-27	JTS, RTi		Added all data members to finalize().
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.ResponseJDialog;

import RTi.Util.Message.Message;

/**
This class is a modal dialog from which Calls comments can be changed and 
the changes written to a database.
*/
public class HydroBase_GUI_CallsComments 
extends JDialog
implements ActionListener {

/**
Button labels.
*/
private final String
	__BUTTON_CANCEL = 	"Cancel",
	__BUTTON_OK = 		"OK",
	__BUTTON_RESTORE = 	"Restore Original Values";

/**
Whether the call's data were changed.
*/
private boolean __changed = false;

/**
Whether the call has been released or not.
*/
private boolean __released = false;

/**
DMI object.
*/
private HydroBaseDMI __dmi = null;

/**
GUI buttons.
*/
private JButton 
	__cancelJButton = 	null,
	__okJButton = 		null,
	__restoreJButton = 	null;

/**
Textfields to display data.
*/
private JTextField 
	__districtsAffectedJTextField = null,
	__releaseCommentsJTextField = 	null,
	__setCommentsJTextField = 	null;

/**
The original call number.
*/
private int __call_num = -1;

/**
The original values for the comments fields.
*/
private String
	__districtsAffected = 	null,
	__releaseComments = 	null,
	__setComments = 	null;

/**
Constructor.
@param parent the parent JFrame on which this dialog will be opened.
@param dmi the DMI to use for writing changes to the database.
@param call_num the call_num of the call for which data can be changed.
@param set_comments the call's set_comments as displayed in the parent GUI.
@param release_comments the call's release_comments as displayed in the parent
GUI.
@param districts_affected the call's districts_affected as displayed in the 
parent GUI.
@param released if true, the call has been released.  If false, it is still
active.
*/
public HydroBase_GUI_CallsComments(JFrame parent, HydroBaseDMI dmi, 
int call_num, String set_comments, String release_comments, 
String districts_affected, boolean released) {
	super(parent, true);
       
        __dmi = dmi;
	__call_num = call_num;
	__setComments = set_comments;
	__releaseComments = release_comments;
	__districtsAffected = districts_affected;
	__released = released;

        setupGUI();
}

/**
Responds to action events from the GUI buttons.
@param event the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent event) {
        String actionCommand = event.getActionCommand();

	if (actionCommand.equals(__BUTTON_CANCEL)) {
		closeGUI();
	}
	else if (actionCommand.equals(__BUTTON_OK)) {
		okClicked();	
	}
	else if (actionCommand.equals(__BUTTON_RESTORE)) {
		__setCommentsJTextField.setText(__setComments);
		__releaseCommentsJTextField.setText(__releaseComments);
		__districtsAffectedJTextField.setText(__districtsAffected);
	}
}

/**
Called by the GUI that instantiated this class to tell if any data were changed.
@return true if data were changed, false if not.
*/
public boolean changed() {
	return __changed;
}

/**
Responsible for closing the GUI.
*/
private void closeGUI() {
	setVisible(false);	
	dispose();
}

/**
Closes the GUI.
*/
public void finalize()
throws Throwable {
	__dmi = null;
	__cancelJButton = 	null;
	__okJButton = 		null;
	__restoreJButton = 	null;
	__districtsAffectedJTextField = null;
	__releaseCommentsJTextField = 	null;
	__setCommentsJTextField = 	null;
	__districtsAffected = 	null;
	__releaseComments = 	null;
	__setComments = 	null;

	super.finalize();
}

/**
Checks to see if any data have been changed and writes changes to the database.
*/
private void okClicked() {
	String setComments = __setCommentsJTextField.getText().trim();
	String releaseComments = __releaseCommentsJTextField.getText().trim();
	String districtsAffected = __districtsAffectedJTextField.getText()
		.trim();
	if (setComments.equals(__setComments)
	    && releaseComments.equals(__releaseComments)
	    && districtsAffected.equals(__districtsAffected)) {
		// none of the values have changed
		closeGUI();
		return;
	}
	
	__changed = true;

	String message = "Some fields are too long to fit within the database "
		+ "columns.\nThey will be truncated as follows:";
	boolean error = false;

	if (setComments.length() > 60) {
		error = true;
		setComments = setComments.substring(0, 60);
		message += "\n   Set comments: " + setComments;
	}
	
	if (releaseComments.length() > 60) {
		error = true;
		releaseComments = releaseComments.substring(0, 60);
		message += "\n   Release comments: " + releaseComments;
	}
	
	if (districtsAffected.length() > 30) {
		error = true;
		districtsAffected = districtsAffected.substring(0, 30);
		message += "\n   Districts affected: " + districtsAffected;
	}

	if (error) {
		new ResponseJDialog(this, "Data Must Be Truncated",
			message, ResponseJDialog.OK);
	}
	
	try {
		__dmi.updateCallsCommentsForCall_num(__call_num, 
			setComments, releaseComments, 
			districtsAffected);
	}
	catch (Exception ex) {
		String routine = "HydroBase_GUI_CallsComments.okClicked()";
		Message.printWarning(2, routine, ex);
		Message.printWarning(1, routine, "There was an error trying "
			+ "to write to the database.  The data have not been "
			+ "changed.");
	}

	closeGUI();
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	
	JPanel panel = new JPanel();
	panel.setLayout(new GridBagLayout());
	getContentPane().add("Center", panel);

	int y = 0;
	JGUIUtil.addComponent(panel, new JLabel("Districts affected: "),
		0, y, 1, 1, 0, 0,
		GridBagConstraints.NONE, GridBagConstraints.EAST);
	__districtsAffectedJTextField = new JTextField(40);
	__districtsAffectedJTextField.setText(__districtsAffected);
	JGUIUtil.addComponent(panel, __districtsAffectedJTextField,
		1, y, 1, 1, 0, 0,
		GridBagConstraints.NONE, GridBagConstraints.WEST);

	y++;
	JGUIUtil.addComponent(panel, new JLabel("Set comments: "),
		0, y, 1, 1, 0, 0, 
		GridBagConstraints.NONE, GridBagConstraints.EAST);
	__setCommentsJTextField = new JTextField(40);
	__setCommentsJTextField.setText(__setComments);
	JGUIUtil.addComponent(panel, __setCommentsJTextField,
		1, y, 1, 1, 0, 0,
		GridBagConstraints.NONE, GridBagConstraints.WEST);
	
	y++;
	JGUIUtil.addComponent(panel, new JLabel("Release comments: "),
		0, y, 1, 1, 0, 0,
		GridBagConstraints.NONE, GridBagConstraints.EAST);
	__releaseCommentsJTextField = new JTextField(40);
	__releaseCommentsJTextField.setText(__releaseComments);
	__releaseCommentsJTextField.setEditable(__released);
	JGUIUtil.addComponent(panel, __releaseCommentsJTextField,
		1, y, 1, 1, 0, 0,
		GridBagConstraints.NONE, GridBagConstraints.WEST);

	JPanel buttons = new JPanel();
	buttons.setLayout(new GridBagLayout());
	
	__restoreJButton = new JButton(__BUTTON_RESTORE);
	__restoreJButton.addActionListener(this);
	__restoreJButton.setToolTipText("Restores the values currently set in "
		+ "the call, replacing any changes made to the values above.");
	JGUIUtil.addComponent(buttons, __restoreJButton,
		0, 0, 1, 1, 0, 0,
		2, 4, 2, 4,
		GridBagConstraints.NONE, GridBagConstraints.EAST);
	
	__okJButton = new JButton(__BUTTON_OK);
	__okJButton.addActionListener(this);
	__okJButton.setToolTipText("Writes the values to the call in the "
		+ "database and closes this window.");
	JGUIUtil.addComponent(buttons, __okJButton,
		1, 0, 1, 1, 0, 0,
		2, 4, 2, 4,
		GridBagConstraints.NONE, GridBagConstraints.EAST);
	
	__cancelJButton = new JButton(__BUTTON_CANCEL);
	__cancelJButton.addActionListener(this);
	__cancelJButton.setToolTipText("Closes this window without writing "
		+ "any changes to the database.");
	JGUIUtil.addComponent(buttons, __cancelJButton,
		2, 0, 1, 1, 0, 0,
		2, 4, 2, 4,
		GridBagConstraints.NONE, GridBagConstraints.EAST);

	getContentPane().add("South", buttons);

	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}

        setTitle(app + "Edit Call Comments");
        pack();
	setResizable(false);

	JGUIUtil.center(this);

	setVisible(true);
}

}
