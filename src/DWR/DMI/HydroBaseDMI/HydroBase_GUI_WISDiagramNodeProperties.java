// ----------------------------------------------------------------------------
// HydroBase_GUI_WISDiagramNodeProperties - class to display and edit
// 	properties for a node in the diagram display.
// ----------------------------------------------------------------------------
//  Copyright:	See the COPYRIGHT file.
// ----------------------------------------------------------------------------
// History:
// 2003-12-18	J. Thomas Sapienza, RTi	initial version.
// 2004-05-18	JTS, RTi		Major overhaul of the entire design,
//					reflecting lessons learned from the
//					network diagram editor for StateMod
//					and new goals for this tool.
// 2004-05-24	JTS, RTi		Added apply button.
// 2004-05-27	JTS, RTi		Renamed from 
//					HydroBase_GUI_WISNetworkNodeProperties
//					to 
//					HydroBase_GUI_WISDiagramNodeProperties.
// 2005-04-28	JTS, RTi		Added finalize().
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import RTi.GR.GRLimits;
import RTi.GR.GRText;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleJComboBox;

import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

/**
GUI for displaying, editing, and validating node properties for normal nodes
and annotations.
*/
public class HydroBase_GUI_WISDiagramNodeProperties 
extends JFrame 
implements ActionListener, KeyListener, WindowListener {
	
/**
Button labels.
*/
private final String
	__BUTTON_APPLY = "Apply",
	__BUTTON_CANCEL = "Cancel",
	__BUTTON_OK = "OK";

/**
Whether the node data are editable.
*/
private boolean __editable = false;

/**
Whether the properties currently being displayed are for an annotation.
*/
private boolean __isAnnotation = false;
	
/**
The default background color of editable text fields.
*/
private Color __textFieldBackground = null;

/**
The dmi to use for communicating with the database.
*/
private HydroBaseDMI __dmi;

/**
The GUI that instantiated this GUI.
*/
private HydroBase_Device_WISDiagram __parent;

/**
The node that is being edited.
*/
private HydroBase_Node __node;

/**
The number of the node (for use in the parent GUI).
*/
private int __nodeNum = -1;

/**
GUI buttons
*/
private JButton 
	__applyButton = null,
	__okButton = null;

/**
GUI text fields.
*/
private JTextField 
	__fontSizeTextField,
	__nodeTypeTextField,
	__textTextField,
	__xTextField,
	__yTextField;

/**
GUI combo boxes.
*/
private SimpleJComboBox
	__fontNameComboBox,
	__fontStyleComboBox,
	__textPositionComboBox;

/**
Constructor.
@param dmi the dmi to use for getting data from the database.
@param parent the parent GUI that instantiated this GUI.
@param node the node for which to edit properties.
@param nodeNum the number of the node in the parent GUI.
@param isAnnotation whether the node is an annotation node or not.
*/
public HydroBase_GUI_WISDiagramNodeProperties(HydroBaseDMI dmi, 
HydroBase_Device_WISDiagram parent, boolean editable, 
HydroBase_Node node, int nodeNum, boolean isAnnotation) {
	__dmi = dmi;
	__parent = parent;
	__editable = editable;
	__node = node;
	__nodeNum = nodeNum;
	__isAnnotation = isAnnotation;

	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());	
	setupGUI();
}

/**
Responds to button presses.
@param event the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent event) {
	String command = event.getActionCommand();
	Object o = event.getSource();

	if (command.equals(__BUTTON_APPLY)) {
		applyChanges();
		__node = __parent.getNode(__nodeNum, __isAnnotation);
		__applyButton.setEnabled(false);
		__okButton.setEnabled(false);
	}
	else if (command.equals(__BUTTON_CANCEL)) {
		closeWindow();
	}
	else if (command.equals(__BUTTON_OK)) {
		if (__editable) {
			applyChanges();
		}
		closeWindow();
	}
	else {
		// a combo box action triggered it.
		validateData();
	}
}

/**
Saves the changes made in the GUI and applies them to the node in the parent 
GUI.
*/
private void applyChanges() {
	if (!validateData()) {
		// if the data are not valid, don't close
		return;
	}

	HydroBase_Node node = new HydroBase_Node();
	if (__isAnnotation) {
		PropList p = new PropList("");
		String temp = null;

		temp = __textTextField.getText().trim();
		temp = StringUtil.replaceString(temp, "\"", "'");
		p.set("Text", temp);

		temp = __xTextField.getText().trim() + ","
			+ __yTextField.getText().trim();
		p.set("Point", temp);

		temp = __textPositionComboBox.getSelected();
		p.set("TextPosition", temp);

		temp = __fontNameComboBox.getSelected().trim();
		p.set("FontName", temp);

		temp = __fontSizeTextField.getText().trim();
		p.set("FontSize", temp);

		temp = __fontStyleComboBox.getSelected().trim();
		p.set("FontStyle", temp);
		node.setAssociatedObject(p);
	}
	else {
		node.setX((new Double(
			__xTextField.getText().trim())).doubleValue());
		node.setY((new Double(
			__yTextField.getText().trim())).doubleValue());
		// offset by 1 for some backwards-compatability issues
		node.setLabelDirection(
			__textPositionComboBox.getSelectedIndex() + 1);
	}

	__parent.updateNode(__nodeNum, node, __isAnnotation);
	__applyButton.setEnabled(false);
	__okButton.setEnabled(false);
}

/**
Closes the GUI.
*/
private void closeWindow() {
	setVisible(false);
	dispose();
}

/**
Displays the annotation values from the annotation proplist in the components
in the GUI.
*/
private void displayPropListValues() {
	PropList p = (PropList)__node.getAssociatedObject();

	String temp = null;
	String val = null;

	val = p.getValue("Text").trim();
	__textTextField.setText(val);

	val = p.getValue("Point").trim();
	temp = StringUtil.getToken(val, ",", 0, 0);
	__xTextField.setText(StringUtil.formatString(temp, "%20.6f").trim());
	temp = StringUtil.getToken(val, ",", 0, 1);
	__yTextField.setText(StringUtil.formatString(temp, "%20.6f").trim());

	val = p.getValue("TextPosition").trim();
	__textPositionComboBox.select(val);

	val = p.getValue("FontName").trim();
	__fontNameComboBox.select(val);

	val = p.getValue("FontSize").trim();
	__fontSizeTextField.setText(val);

	val = p.getValue("FontStyle").trim();
	__fontStyleComboBox.select(val);
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	__textFieldBackground = null;
	__dmi = null;
	__parent = null;
	__node = null;
	__applyButton = null;
	__okButton = null;
	__fontSizeTextField = null;
	__nodeTypeTextField = null;
	__textTextField = null;
	__xTextField = null;
	__yTextField = null;
	__fontNameComboBox = null;
	__fontStyleComboBox = null;
	__textPositionComboBox = null;
	super.finalize();
}

/**
Responds when users press the enter button in an edit field.  Saves the changes
and closes the GUI.
@param event that KeyEvent that happened.
*/
public void keyPressed(KeyEvent event) {
	if (event.getKeyCode() == event.VK_ENTER) {
		if (__editable) {
			applyChanges();
		}
		closeWindow();
	}
}

/**
Responds after users presses a key -- tries to validate the data that has
been entered.
*/
public void keyReleased(KeyEvent event) {
	validateData();
}

/**
Does nothing.
*/
public void keyTyped(KeyEvent event) {}

/**
Sets up the GUI.
*/
private void setupGUI() {
	addWindowListener(this);

	JPanel panel = new JPanel();
	panel.setLayout(new GridBagLayout());

	int y = 0;

	if (__isAnnotation) {
		JGUIUtil.addComponent(panel, new JLabel("Text:"),
			0, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		JGUIUtil.addComponent(panel, new JLabel("X:"),
			0, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		JGUIUtil.addComponent(panel, new JLabel("Y:"),
			0, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		JGUIUtil.addComponent(panel, new JLabel("Text Position:"),
			0, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		JGUIUtil.addComponent(panel, new JLabel("Font Name:"),
			0, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		JGUIUtil.addComponent(panel, new JLabel("Font Size:"),
			0, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		JGUIUtil.addComponent(panel, new JLabel("Font Style:"),
			0, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		
		__textTextField = new JTextField(30);
		__textFieldBackground = __textTextField.getBackground();
		__xTextField = new JTextField(15);
		__yTextField = new JTextField(15);
	
		__textPositionComboBox = new SimpleJComboBox(false);
		String[] positions = GRText.getTextPositions();
		__textPositionComboBox.setMaximumRowCount(positions.length);
		for (int i = 0; i < positions.length; i++) {
			__textPositionComboBox.addItem(positions[i]);
		}
		
		__fontNameComboBox = JGUIUtil.newFontNameJComboBox();
		__fontSizeTextField = new JTextField(7);
		__fontStyleComboBox = JGUIUtil.newFontStyleJComboBox();
		
		displayPropListValues();
	
		__textTextField.addKeyListener(this);
		__xTextField.addKeyListener(this);
		__yTextField.addKeyListener(this);
		__fontSizeTextField.addKeyListener(this);
		__textPositionComboBox.addActionListener(this);
		__fontNameComboBox.addActionListener(this);
		__fontStyleComboBox.addActionListener(this);
	
	// REVISIT (JTS - 2004-05-19)
	// the above should be auto-generated somehow (style, names)
	
		y = 0;
		JGUIUtil.addComponent(panel, __textTextField,
			1, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, __xTextField,
			1, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, __yTextField,
			1, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, __textPositionComboBox,
			1, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, __fontNameComboBox,
			1, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, __fontSizeTextField,
			1, y++, 1, 1, 1, 1,
		GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, __fontStyleComboBox,
			1, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
	
		if (!__editable) {
			__textTextField.setEditable(false);
			__xTextField.setEditable(false);
			__yTextField.setEditable(false);
			__textPositionComboBox.setEnabled(false);
			__fontNameComboBox.setEnabled(false);
			__fontSizeTextField.setEditable(false);
			__fontStyleComboBox.setEnabled(false);
		}	
	}
	else {
		JGUIUtil.addComponent(panel, new JLabel("Label:"),
			0, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		JGUIUtil.addComponent(panel, new JLabel("Same as WIS row."),
			2, y-1, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, new JLabel("Row/Node Type:"),
			0, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		JGUIUtil.addComponent(panel, new JLabel("Label Position:"),
			0, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		JGUIUtil.addComponent(panel, new JLabel("X Position:"),
			0, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		JGUIUtil.addComponent(panel, new JLabel("UTM coordinate (as "
			+ "displayed)"), 
			2, y-1, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, new JLabel("Y Position:"),
			0, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		JGUIUtil.addComponent(panel, new JLabel("UTM coordinate (as "
			+ "displayed)"), 
			2, y-1, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, new JLabel("Alternate X:"),
			0, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		JGUIUtil.addComponent(panel, new JLabel("UTM coordinate "
			+ " (physical)"),
			2, y-1, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, new JLabel("Alternate Y:"),
			0, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
		JGUIUtil.addComponent(panel, new JLabel("UTM coordinate "
			+ "(physical)"),
			2, y-1, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);

	
		__textTextField = new JTextField(30);
		__textFieldBackground = __textTextField.getBackground();
		__textTextField.addKeyListener(this);
		__textTextField.setEditable(false);
		__nodeTypeTextField = new JTextField(15);
		__nodeTypeTextField.setEditable(false);
		__textPositionComboBox = new SimpleJComboBox(false);
		String[] positions = GRText.getTextPositions();
		__textPositionComboBox.setMaximumRowCount(positions.length);
		for (int i = 0; i < positions.length; i++) {
			__textPositionComboBox.addItem(positions[i]);
		}		
		// offset by 1 for some backwards-compatability issues
		int textDir = __node.getLabelDirection() - 1;
		__textPositionComboBox.select(textDir);
		__textPositionComboBox.addActionListener(this);
		__xTextField = new JTextField(15);
		__xTextField.addKeyListener(this);
		__yTextField = new JTextField(15);
		__yTextField.addKeyListener(this);
		JTextField utmXTextField = new JTextField(15);
		JTextField utmYTextField = new JTextField(15);
	
		utmXTextField.setEditable(false);
		utmYTextField.setEditable(false);
	
		if (!__editable) {
			__textPositionComboBox.setEnabled(false);
			__xTextField.setEditable(false);
			__yTextField.setEditable(false);
		}	
	
		y = 0;
		JGUIUtil.addComponent(panel, __textTextField,
			1, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, __nodeTypeTextField,
			1, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, __textPositionComboBox,
			1, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, __xTextField,
			1, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, __yTextField, 
			1, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, utmXTextField, 
			1, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
		JGUIUtil.addComponent(panel, utmYTextField, 
			1, y++, 1, 1, 1, 1,
			GridBagConstraints.NONE, GridBagConstraints.WEST);
	
		__textTextField.setText(__node.getLabel());
		__nodeTypeTextField.setText(__node.getNodeType());
		__xTextField.setText("" 
			+ StringUtil.formatString(__node.getX(), "%20.6f")
			.trim());
		__yTextField.setText("" 
			+ StringUtil.formatString(__node.getY(), "%20.6f")
			.trim());
		utmXTextField.setText("" 
			+ StringUtil.formatString(__node.getDBX(), "%20.6f")
			.trim());
		utmYTextField.setText("" 
			+ StringUtil.formatString(__node.getDBY(), "%20.6f")
			.trim());
	}

	__applyButton = new JButton(__BUTTON_APPLY);
	if (__editable) {
		__applyButton.setToolTipText("Apply changes.");
		__applyButton.addActionListener(this);
	}
	__applyButton.setEnabled(false);

	JButton cancelButton = new JButton(__BUTTON_CANCEL);
	cancelButton.setToolTipText("Discard changes and return.");
	cancelButton.addActionListener(this);

	__okButton = new JButton(__BUTTON_OK);
	__okButton.setToolTipText("Accept changes and return.");
	__okButton.setEnabled(false);
	__okButton.addActionListener(this);

	JPanel southPanel = new JPanel();
	southPanel.setLayout(new GridBagLayout());

	if (__editable) {
		JGUIUtil.addComponent(southPanel, __applyButton,
			0, 0, 1, 1, 1, 1,
			0, 10, 0, 10,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
	}

	int space = 0;
	if (!__editable) {
		space = 1;
	}

	JGUIUtil.addComponent(southPanel, __okButton,
		1, 0, 1, 1, space, space,
		0, 10, 0, 10,
		GridBagConstraints.NONE, GridBagConstraints.EAST);

	if (__editable) {
		JGUIUtil.addComponent(southPanel, cancelButton,
			2, 0, 1, 1, 0, 0,
			0, 10, 0, 10,
			GridBagConstraints.NONE, GridBagConstraints.EAST);
	}
	else {
		__okButton.setEnabled(true);
	}

	getContentPane().add("Center", panel);
	getContentPane().add("South", southPanel);

	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}	

	if (__isAnnotation) {
		setTitle(app + "Annotation Properties");
		pack();
		setSize(getWidth() + 155, getHeight());
	}
	else {
		setTitle(app + "Node Properties");
		pack();
		setSize(getWidth() + 30, getHeight());
	}

	JGUIUtil.center(this);
	show();
}

/**
Validates data entered in the GUI.  If any values are invalid (non-numbers in
the X and Y fields, blank label field), the OK button is disabled and the
field is highlighted in red.
@return true if all the text is valid.  False if not.
*/
private boolean validateData() {
	String text = __textTextField.getText().trim();
	double x = -1;
	boolean badX = false;
	double y = -1;
	boolean badY = false;

	GRLimits limits = __parent.getDataLimits();

	// make sure the X value is a double and that it is within the range
	// of the X values in the data limits
	try {
		x = (new Double(__xTextField.getText().trim())).doubleValue();
		if (x < limits.getLeftX() || x > limits.getRightX()) {
			badX = true;
		}
	}
	catch (Exception e) {
		badX = true;
	}
	
	// if the X value is not valid, set the textfield red to show this. 
	// Otherwise, make sure the text has the proper background color.
	if (badX) {
		__xTextField.setBackground(Color.red);
	}
	else {
		__xTextField.setBackground(__textFieldBackground);
	}

	// make sure the Y value is a double and that it is within the range
	// of the Y values in the data limits
	try {
		y = (new Double(__yTextField.getText().trim())).doubleValue();
		if (y < limits.getBottomY() || x > limits.getTopY()) {
			badY = true;
		}
	}
	catch (Exception e) {
		badY = true;
	}
	
	// if the Y value is not valid, set the textfield red to show this. 
	// Otherwise, make sure the text has the proper background color.
	if (badY) {
		__yTextField.setBackground(Color.red);
	}
	else {
		__yTextField.setBackground(__textFieldBackground);
	}
	
	// make sure that the text is not an empty string.  If it is, make
	// its textfield red.  Otherwise, the textfield will have the normal
	// textfield color.
	boolean badText = false;
	if (__isAnnotation) {
		if (text.trim().equals("")) {
			badText = true;
			__textTextField.setBackground(Color.red);		
		}
		else {
			__textTextField.setBackground(__textFieldBackground);
		}
	}

	// make sure that the font size is an integer greater than 0.  If not,
	// set its textfield to red.  Otherwise the textfield will have a
	// normal textfield color.
	boolean badFontSize = false;
	if (__isAnnotation) {
		int size = 0;
		try {
			size = (new Integer(
				__fontSizeTextField.getText().trim()))
				.intValue();
		}
		catch (Exception e) {
			badFontSize = true;
		}

		if (size <= 0) {	
			badFontSize = true;
		}

		if (badFontSize) {
			__fontSizeTextField.setBackground(Color.red);
		}
		else {
			__fontSizeTextField.setBackground(
				__textFieldBackground);
		}
	}

	if (!badText && !badX && !badY && !badFontSize) {
		// if all the data validated properly then mark whether
		// the data are dirty or not.   OK is only active if 
		// the data are valid and something is dirty.
		boolean dirty = false;
		if (__isAnnotation) {
			PropList p = (PropList)__node.getAssociatedObject();
			
			String temp = p.getValue("Text").trim();
			if (!temp.equals(__textTextField.getText().trim())) {
				dirty = true;
			}

			String val = p.getValue("Point").trim();
			temp = StringUtil.getToken(val, ",", 0, 0);

			if (!temp.equals(__xTextField.getText().trim())) {
				dirty = true;
			}
			
			temp = StringUtil.getToken(val, ",", 0, 1);
			if (!temp.equals(__yTextField.getText().trim())) {
				dirty = true;
			}

			temp = p.getValue("FontSize").trim();
			if (!temp.equals(
				__fontSizeTextField.getText().trim())) {
				dirty = true;
			}

			temp = p.getValue("FontName").trim();
			if (!temp.equals(
				__fontNameComboBox.getSelected().trim())) {
				dirty = true;
			}

			temp = p.getValue("FontStyle").trim();
			if (!temp.equals(
				__fontStyleComboBox.getSelected().trim())) {
				dirty = true;
			}

			temp = p.getValue("TextPosition").trim();
			if (!temp.equals(
				__textPositionComboBox.getSelected().trim())) {
				dirty = true;
			}
		}
		else {
			if (!text.equals(__node.getLabel())) {
				dirty = true;
			}
			if (x != __node.getX()) {
				dirty = true;
			}
			if (y != __node.getY()) {
				dirty = true;
			}
			
			// offset by 1 for some backwards-compatability issues
			int textDir = __node.getLabelDirection() - 1;
			int dir = __textPositionComboBox.getSelectedIndex();
			
			if (textDir != dir) {
				dirty = true;
			}
		}

		__applyButton.setEnabled(dirty);
		__okButton.setEnabled(dirty);
		return true;
	}
	else {
		// if the data aren't valid, the ok button is not enabled
		__applyButton.setEnabled(false);
		__okButton.setEnabled(false);
		return false;
	}
}

/**
Does nothing.
*/
public void windowActivated(WindowEvent event) {}

/**
Closes the GUI.
*/
public void windowClosing(WindowEvent event) {
	closeWindow();
}
/**
Does nothing.
*/
public void windowClosed(WindowEvent event) {}

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
