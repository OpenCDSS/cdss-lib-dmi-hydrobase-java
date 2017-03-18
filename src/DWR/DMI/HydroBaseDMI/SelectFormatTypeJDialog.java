//-----------------------------------------------------------------------------
// SelectFormatTypeJDialog - Dialog box for users to select the kind of 
//	formatting to do on exports or prints.  A FileChooser dialog would 
//	not have been appropriate.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History: 
// 2003-03-13	J. Thomas Sapienza, RTi	Initial version.
// 2003-04-04	JTS, RTi		Changed GUIUtil to JGUIUtil.
//-----------------------------------------------------------------------------
// EndHeader

package DWR.DMI.HydroBaseDMI;

import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import RTi.Util.GUI.JGUIUtil;

/**
Dialog box for users to select the kind of formatting to do on exports and
prints.  This dialog is modal and not resizable.  
A FileChooser dialog would not have been appropriate.
*/
@SuppressWarnings("serial")
public class SelectFormatTypeJDialog extends JDialog
implements ActionListener {

/**
If a button other than Cancel is pressed, this value is set to the position
in the __formats Vector at which that value can be found.
*/
private int __selected = HydroBase_GUI_Util.CANCEL;

/**
The list of possible formats.  Each element in the vector will be placed onto
a single button.
*/
private List<String> __formats = null;

/**
Constructor.  
@param parent the JFrame on which this dialog should appear.
@param formats a list of formatting strings, each of which will be placed
on a JButton.  If this is null or empty, an exception will be thrown.
@throws exception if an error occurs.
*/
public SelectFormatTypeJDialog(JFrame parent, List<String> formats) 
throws Exception {
	super ( parent, true );
	if (formats == null || formats.size() == 0) {
		throw new Exception ("Do not pass a null or empty format "
			+ "vector to the constructor.");
	}
	initialize (formats);
}

/**
Responds to action performed events.
@param evt the event that happened.
*/
public void actionPerformed(ActionEvent evt) {
	String command = evt.getActionCommand();

	for (int i = 0; i < __formats.size(); i++) {
		if (command.equals(((String)__formats.get(i)))) {
			__selected = i;
			closeDialog();
		}
	}  
	if(command.equals("Cancel")) {
        	closeDialog();
	}
}      

/**
Close the dialog and dispose of graphical resources.
*/
private void closeDialog() {
	super.setVisible(false);
}

/**
Allows an application to return the kind of formatting that was specified.
@return the kind of formatting that was specified.
*/
public int getSelected() {
	return __selected;
}

/**
Initializes and builds the GUI.
@param formats the list of formats, each of which will be placed on a 
JButton.
*/
private void initialize(List<String> formats) {
	setTitle("Select format to print");
	setModal(true);
		
	__formats = formats;
	
	getContentPane().setLayout(new GridLayout(0, 1, 10, 10));

	for (int i = 0; i < formats.size(); i++) {
		String formatName = formats.get(i);
		if (formatName.equals("")) {
			// skip it, but continue counting
		}
		else {
			JButton j = new JButton(formats.get(i));
			j.addActionListener(this);
			getContentPane().add(j);
		}
	}
	JButton j = new JButton("Cancel");
	j.addActionListener(this);
	getContentPane().add(j);
	pack();
	setResizable(false);	
	JGUIUtil.center(this);
	setVisible(true);
}
	
}
