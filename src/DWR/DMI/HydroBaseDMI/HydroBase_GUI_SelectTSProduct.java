// ----------------------------------------------------------------------------
// HydroBase_GUI_SelectTSProdcut - Class for selecting a TSProduct to work with
//	or for creating a new TSProduct.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
// 2004-03-08	J. Thomas Sapienza, RTi	Initial version.
// 2004-04-30	JTS, RTi		Many changes following SAM's review.
// 2004-05-03	JTS, RTi		Converted to use the dmi connection
//					rather than reading data from a flat
//					table file.
// 2004-05-04	JTS, RTi		Added the Delete button.
// 2004-05-11	JTS, RTi		* Added new text to the display.
//					* Added the TSP Open button.
// 2004-05-13	JTS, RTi		List can now be double-clicked on to
//					open a TSProduct from the database.
// 2004-07-26	JTS, RTi		* Resized the window.
//					* Added a new line of text to the
//					  product explanation paragraph.
//					* Added the Rename button.
// 2005-02-15	JTS, RTi		Converted all queries except for
//					readTimeSeries() to use stored
//					procedures.
// 2005-04-12	JTS, RTi		MutableJList changed to SimpleJList.
// 2005-04-25	JTS, RTi		Updated dmiWrite() to DMI writes().
// 2005-04-28	JTS, RTi		Added finalize().
// 2005-08-04	SAM, RTi		Clean up the notes at the top of the
//					frame.
// ----------------------------------------------------------------------------
// EndHeader

package DWR.DMI.HydroBaseDMI;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.Vector;

import RTi.DMI.DMIUtil;

import RTi.GRTS.TSProduct;
import RTi.GRTS.TSViewJFrame;

import RTi.TS.TS;

import RTi.Util.GUI.JFileChooserFactory;
import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleJList;
import RTi.Util.GUI.ResponseJDialog;
import RTi.Util.GUI.SimpleFileFilter;
import RTi.Util.GUI.TextResponseJDialog;

import RTi.Util.IO.IOUtil;
import RTi.Util.IO.Prop;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

/**
This class allows a TSProduct to be read from the database and worked with, or
to create a new TSProduct to work with.
*/
public class HydroBase_GUI_SelectTSProduct 
extends JFrame 
implements ActionListener, ListSelectionListener, MouseListener, 
WindowListener {

/**
Button labels.
*/
private final String 
	__BUTTON_CANCEL = 	"Cancel",
	__BUTTON_DELETE = 	"Delete",
	__BUTTON_NEW = 		"New",
	__BUTTON_OK = 		"OK",
	__BUTTON_OPEN = 	"Read from File",
	__BUTTON_RENAME = 	"Rename";

/**
Whether users can save to the database or not.
*/
private boolean __saveToDB = false;

/**
The dmi to be used for communicating with the database.
*/
private HydroBaseDMI __dmi = null;

/**
The button for deleting a TSProduct.
*/
private JButton __deleteButton = null;

/**
The button for opening a TSProduct.
*/
private JButton __okButton = null;

/**
The button for renaming a TSProduct.
*/
private JButton __renameButton = null;

/**
The list in which the product descriptions are stored.
*/
private SimpleJList __list = null;

/**
Private to avoid use.
*/
private HydroBase_GUI_SelectTSProduct() {}

/**
Constructor.
@param dmi the dmi to use for communicating with the database.
*/
public HydroBase_GUI_SelectTSProduct(HydroBaseDMI dmi) {
	super();
	__dmi = dmi;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());

	try {
		HydroBase_UserSecurity us = __dmi.readUserSecurityForUser_num(
			__dmi.getUserNum());
		if (us == null) {
			__saveToDB = false;
		}
		else if (us.getLogin().equals("guest")) {
			__saveToDB = false;
		}
		else {
			__saveToDB = true;
		}
	}
	catch (Exception e) {
		String routine = "HydroBase_GUI_SelectTSProduct.constructor";
		Message.printWarning(1, routine,"Error reading from database.");
		Message.printWarning(2, routine, e);
		return;
	}
	
	setupGUI();
}

/**
Responds to action performed events.
@param event the event that happened.
*/
public void actionPerformed(ActionEvent event) {
	String command = event.getActionCommand();

	if (command.equals(__BUTTON_CANCEL)) {
		closeClicked();
	}
	else if (command.equals(__BUTTON_DELETE)) {
		deleteClicked();
	}
	else if (command.equals(__BUTTON_NEW)) {
		newClicked();
	}
	else if (command.equals(__BUTTON_OK)) {
		okClicked();
	}
	else if (command.equals(__BUTTON_OPEN)) {
		openClicked();
	}
	else if (command.equals(__BUTTON_RENAME)) {
		renameClicked();
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
Deletes the selected TSProduct from the database.
*/
private void deleteClicked() {
	String s = (String)__list.getSelectedItem();
	int index = s.indexOf("-");
	String id = s.substring(0, index).trim();

	int x = new ResponseJDialog(this, "Confirm Delete",
		"Are you sure that you want to delete the time series product "+
		"with the identifier \"" + id + "\"?",
		ResponseJDialog.YES | ResponseJDialog.NO).response();
	
	if (x == ResponseJDialog.NO) {
		return;
	}

	try {
		__dmi.deleteTSProductForIdentifier(id, __dmi.getUserNum());
	}
	catch (Exception e) {
		String routine = "HydroBase_GUI_SelectTSProduct.deleteClicked";
		Message.printWarning(1, routine, "Error deleting data from "
			+ "database.");
		Message.printWarning(2, routine, e);
	}
	__list.setListData(readTSProducts());
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	__dmi = null;
	__deleteButton = null;
	__okButton = null;
	__renameButton = null;
	__list = null;
	super.finalize();
}

/**
Runs a loop with a dialog to find a valid identifier for the product.
@return a valid identifier or null if the user cancelled the action.
*/
private String getValidIdentifier(String seed) {
	String routine = "HydroBase_GUI_SelectTSProduct.getValidIdentifier";
	String identifier = seed;
	// count is a count of the number of times the following loop has gone
	// through.  If count > 0 then the loop has gone through 1 time and the
	// user entered an invalid value for the TSProduct identifier.  
	boolean inDB = false;
	HydroBase_TSProduct tsp = null;
	int count = 0;
	do {
		inDB = false;
		if (count > 0) {
			new ResponseJDialog(this, "Invalid Identifier",
				"Identifiers cannot contain spaces, apostrophes"
				+ " or dashes, must be at least one "
				+ "character long and cannot already exist "
				+ "in the database.",
				ResponseJDialog.OK);
		}
		
		identifier = new TextResponseJDialog(this, 
			"Enter Time Series Product Identifier",
			"Enter a unique identifier for the time series product."
			+ "\nThe identifier must not exist in the database and "
			+ "\ncannot contain spaces, apostrophes or dashes, and "
			+ "\nmust be at least one character long.", identifier,
			ResponseJDialog.OK | ResponseJDialog.CANCEL).response();

		if (identifier == null) {
			return null;
		}

		identifier = identifier.trim();
		count++;

		try {
			tsp =__dmi.readTSProductForIdentifier(identifier.trim(),
				__dmi.getUserNum());
		}
		catch (Exception e) {
			Message.printWarning(1, routine,
				"Error reading from database.");
			Message.printWarning(2, routine, e);
		}

		if (tsp != null) {
			inDB = true;
		}
	} while ((identifier.equals(""))
		|| (identifier.indexOf(" ") > -1) 
		|| (identifier.indexOf("'") > -1) 
		|| (identifier.indexOf("-") > -1)
		|| inDB);

	return identifier;
}

/**
Reponds when the list is double-clicked, and opens the double-clicked TSProduct.
@param event the MouseEvent that happened.
*/
public void mouseClicked(MouseEvent event) {
	int count = event.getClickCount();
        if (count >= 2) {
		String s = (String)__list.getSelectedItem();
		int index = s.indexOf("-");
		String temp = s.substring(0, index).trim();

		openTSProduct(temp);
	}
}

/**
Does nothing.
*/
public void mouseEntered(MouseEvent event) {}

/**
Does nothing.
*/
public void mouseExited(MouseEvent event) {}

/**
Does nothing.
*/
public void mousePressed(MouseEvent event) {}

/**
Does nothing.
*/
public void mouseReleased(MouseEvent event) {}

/**
Called when New TSProduct is clicked.  Creates a new product and lets the
user edit it and save it.
*/
private void newClicked() {
	String routine = "HydroBase_GUI_SelectTSProduct.newClicked";

	String identifier = "";
	// count is a count of the number of times the following loop has gone
	// through.  If count > 0 then the loop has gone through 1 time and the
	// user entered an invalid value for the TSProduct identifier.  
	int count = 0;
	do {
		if (count > 0) {
			new ResponseJDialog(this, "Invalid Identifier",
				"Identifiers cannot contain spaces, apostrophes"
				+ " or dashes and must be at least one "
				+ "character long.",
				ResponseJDialog.OK);
		}
		
		identifier = new TextResponseJDialog(this, 
			"Enter Time Series Product Identifier",
			"Enter a unique identifier for the time series product."
			+ "\nThe identifier must not exist in the database and "
			+ "\ncannot contain spaces, apostrophes or dashes, and "
			+ "\nmust be at least one character long.", identifier,
			ResponseJDialog.OK | ResponseJDialog.CANCEL).response();

		if (identifier == null) {
			return;
		}

		identifier = identifier.trim();
		count++;
	} while ((identifier.equals(""))
		|| (identifier.indexOf(" ") > -1) 
		|| (identifier.indexOf("'") > -1) 
		|| (identifier.indexOf("-") > -1));

	HydroBase_TSProduct tsp = null;
	try {
		tsp = __dmi.readTSProductForIdentifier(identifier.trim(), 
			__dmi.getUserNum());
	}
	catch (Exception e) {
		Message.printWarning(1, routine,"Error reading from database.");
		Message.printWarning(2, routine, e);
	}

	// if tsp != null then something was returned from the database, ie,
	// the identifier the user chose is already in use.
	if (tsp != null) {
		int x = new ResponseJDialog(this, "Identifier Exists",
			"A TSProduct already exists with that identifier.\n"
			+ "Would you like to edit it?",
			ResponseJDialog.OK | ResponseJDialog.CANCEL).response();
		if (x == ResponseJDialog.CANCEL) {
			return;
		}
		
		openTSProduct(identifier);
		return;
	}

	PropList props = new PropList("TSProduct");
	props.setHowSet(Prop.SET_AT_RUNTIME_BY_USER);
	props.set("Product.ProductID=" + identifier);
	props.set("Product.ProductIDOriginal=" + identifier);
	props.set("Product.ProductType=Graph");
	props.set("Product.ProductTotalHeight=400");
	props.set("Product.ProductTotalWidth=600");
	props.set("Product.Enabled=true");
	props.set("Product.ZoomEnabled=true");
	props.set("SubProduct 1.GraphType=Line");
	props.set("InitialView", "Graph");

	TSProduct product = null;
	try {
		product = new TSProduct(props, null);
		product.setTSList(new Vector());
		TSViewJFrame view = new TSViewJFrame(product);
		if (__saveToDB) {
			view.addTSProductDMI(__dmi);
		}
		view.openPropertiesGUI();
		Point p = view.getViewGraphJFrame().getLocationOnScreen();
		view.getViewGraphJFrame().setLocation(p.x - 50, p.y - 50);
		p = view.getTSProductJFrame().getLocationOnScreen();
		view.getTSProductJFrame().setLocation(p.x + 50, p.y + 50);
	}
	catch (Exception e) {
		Message.printWarning(2, "", "Could not create graph view.");
		Message.printWarning(2, "", e);
	}	
}

/**
Called when the OK button is clicked.  Opens the TSProduct selected in the 
list.
*/
private void okClicked() {
	String s = (String)__list.getSelectedItem();
	int index = s.indexOf("-");
	String temp = s.substring(0, index).trim();

	openTSProduct(temp);
}

/**
Called when the open button is clicked.  Opens a TSP Product file.
*/
private void openClicked() {
	String routine = "HydroBase_GUI_SelectTSProduct.openClicked";

	JGUIUtil.setWaitCursor(this, true);
	String lastDirectorySelected = JGUIUtil.getLastFileDialogDirectory();
	JFileChooser fc = null;
	if (lastDirectorySelected != null) {
		fc = JFileChooserFactory.createJFileChooser(
			lastDirectorySelected);
	}
	else {
		fc = JFileChooserFactory.createJFileChooser();
	}

	fc.setDialogTitle("Select Time Series Product File");
	SimpleFileFilter tsp = new SimpleFileFilter("tsp", 
		"Time Series Product Files");
	fc.addChoosableFileFilter(tsp);
	fc.setAcceptAllFileFilterUsed(false);
	fc.setFileFilter(tsp);
	fc.setDialogType(JFileChooser.OPEN_DIALOG);	
	
	JGUIUtil.setWaitCursor(this, false);
	int retVal = fc.showOpenDialog(this);
	if (retVal != JFileChooser.APPROVE_OPTION) {
		return;
	}

	String currDir = (fc.getCurrentDirectory()).toString();

	if (!currDir.equalsIgnoreCase(lastDirectorySelected)) {
		JGUIUtil.setLastFileDialogDirectory(currDir);
	}
	String filename = currDir + File.separator 
		+ fc.getSelectedFile().getName();

	TSProduct product = null;
	try {
		product = new TSProduct(filename, null);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error creating TSProduct "
			+ "from TSP file.");
		Message.printWarning(2, routine, e);
		return;
	}
	
	Vector v = product.getAllProps();
	int size = v.size();
	Prop p = null;
	Vector tsids = new Vector();
	for (int i = 0; i < size; i++) {
		p = (Prop)v.elementAt(i);
		if (StringUtil.endsWithIgnoreCase(p.getKey(), "TSID")) {
			tsids.add(p.getValue());
		}
	}

	size = tsids.size();
	String s = null;
	TS ts = null;
	Vector tsList = new Vector();
	try {
		for (int i = 0; i < size; i++) {
			s = (String)tsids.elementAt(i);
			ts = __dmi.readTimeSeries(s, null, null, null, 
				true, null);
			tsList.addElement(ts);
		}
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading time series "
			+ "'" + s + "' from database.");
		Message.printWarning(2, routine, e);
		return;
	}
	product.setTSList(tsList);
	
	try {
		TSViewJFrame view = new TSViewJFrame(product);
		if (__saveToDB) {
			view.addTSProductDMI(__dmi);
		}
	}
	catch (Exception e) {
		Message.printWarning(1, routine,"Error opening graph display.");
		Message.printWarning(2, routine, e);
	}
}

/**
Opens the TSProduct from the database that has the specified identifier 
string and which belongs to the currently-logged-in user.  Identifier should
already exist.  If identifier does not exist, a warning will be thrown.
@param identifier the identifier string of the TSProduct to read from the
database.
*/
private void openTSProduct(String identifier) {
	String routine = "HydroBase_GUI_SelectTSProduct.openTSProduct";

	Vector dbProps = null;
	HydroBase_TSProduct tsp = null;
	Message.printStatus(1, "", "Open product: " + identifier + " / "
		+ __dmi.getUserNum());
	try {
		// get the requested TSProduct from the database
		tsp = __dmi.readTSProductForIdentifier(identifier, 
			__dmi.getUserNum());
		// read all the properties for that TSProduct from the 
		// database.
		dbProps = __dmi.readTSProductPropsListForTSProduct_num(
			tsp.getTSProduct_num());
	}
	catch (Exception e) {
		Message.printWarning(1, routine,"Error reading from database.");
		Message.printWarning(2, routine, e);
		return;
	}

	PropList props = new PropList("TSProduct");
	props.setHowSet(Prop.SET_FROM_PERSISTENT);
	int size = dbProps.size();
	Vector tsids = new Vector();
	HydroBase_TSProductProps tspp = null;

	// Loops through all the properties and adds them to an actual proplist.
	// Also keeps track of any properties that end with TSID, as these 
	// are time series that will need to be queried from the database.
	for (int i = 0; i < size; i++) {
		tspp = (HydroBase_TSProductProps)dbProps.elementAt(i);
		try {
			props.set(tspp.getProperty() + "="
				+ tspp.getValue());
			if (StringUtil.endsWithIgnoreCase(tspp.getProperty(), 
				"TSID")) {
				tsids.add(tspp.getValue());
			}
		}
		catch (Exception e) {
			Message.printWarning(2, "", "Could not set property.");
			Message.printWarning(2, "", e);
		}
	}

	TSProduct product = null;
	try {
		props.set("InitialView", "Graph");
		props.set("ProductIDOriginal", identifier);
		product = new TSProduct(props, null);
		TS ts = null;
		Vector tsList = new Vector();
		String s = null;
		size = tsids.size();
		for (int i = 0; i < size; i++) {
			s = (String)tsids.elementAt(i);
			ts = __dmi.readTimeSeries(s, null, null, null, 
				true, null);
			tsList.addElement(ts);
		}
		product.setTSList(tsList);

		TSViewJFrame view = new TSViewJFrame(product);
		if (__saveToDB) {
			view.addTSProductDMI(__dmi);
		}
	}
	catch (Exception e) {
		Message.printWarning(2, "", "Error reading time series.");
		Message.printWarning(2, "", e);
	}	
}

/**
Reads TSProducts from the database and updates the list in the GUI.
*/
private Vector readTSProducts() {
	String routine = getClass() + ".readTSProducts";
	Vector v = null;
	
	try {
		v = 
		__dmi.readTSProductListForTSProduct_numProductGroup_numUser_num(
			DMIUtil.MISSING_INT, DMIUtil.MISSING_INT, 
			__dmi.getUserNum());
	}
	catch (Exception e) {
		Message.printWarning(2, routine,"Couldn't read from database.");
		Message.printWarning(2, routine, e);
		v = null;
	}

	if (v == null) {
		v = new Vector();
	}

	int size = v.size();
	Vector strings = new Vector();
	HydroBase_TSProduct tsp = null;
	String s = null;
	for (int i = 0; i < size; i++) {
		tsp = (HydroBase_TSProduct)v.elementAt(i);
		s = "" + tsp.getIdentifier() + " - " + tsp.getName();
		strings.add(s);
	}
	return strings;
}

/**
Renames a TSProduct.
*/
private void renameClicked() {
	String routine = "HydroBase_GUI_SelectTSProduct.renameClicked";
	String s = (String)__list.getSelectedItem();
	int index = s.indexOf("-");
	String currentID = s.substring(0, index).trim();

	String id = getValidIdentifier(currentID);

	if (id == null) {	
		return;
	}

	try {
		__dmi.updateTSProductIdentifierForUser_numIdentifier(id,
			__dmi.getUserNum(), currentID);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error writing to database.");
		Message.printWarning(2, routine, e);
	}
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	addWindowListener(this);

	String appName = JGUIUtil.getAppNameForWindows();
	if (appName == null) {
		appName = "";
	}

        JPanel northJPanel = new JPanel();
        northJPanel.setLayout(new GridBagLayout());
        getContentPane().add("North", northJPanel);

	int y = 0;
	JGUIUtil.addComponent(northJPanel, 
		new JLabel(
		"Time series plots are managed as \"time series products\"," +
		" which have an identifier and name."),
		0, y++, 1, 1, 1, 0, 
		GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);		
	JGUIUtil.addComponent(northJPanel, 
		new JLabel(
		"Time series products are created by dragging time series " +
		"from main display windows onto a graph layout."),
		0, y++, 1, 1, 1, 0, 
		GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);		
	JGUIUtil.addComponent(northJPanel,
		new JLabel(
		"To process an existing product, select it and press OK."),
		0, y++, 1, 1, 1, 0, 
		GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);		
	JGUIUtil.addComponent(northJPanel, 
		new JLabel(
		"To modify an existing product, select it and press OK." +
		"  Then right-click on the graph and edit its properties."),
		0, y++, 1, 1, 1, 0, 
		GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);		
	JGUIUtil.addComponent(northJPanel,
		new JLabel( "To create a new product, press New."),
		0, y++, 1, 1, 1, 0, 
		GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);		
	JGUIUtil.addComponent(northJPanel, 
		new JLabel(
		"Time series products are saved using the current login."),
		0, y++, 1, 1, 1, 0, 
		GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);		

	if ((__dmi.getDatabaseEngine().equals("SQL_Server")
		|| __dmi.getDatabaseEngine().equals("SQLServer7")
		|| __dmi.getDatabaseEngine().equals("SQLServer2000"))
		&& __dmi.isGuestLoggedIn()) {
		JGUIUtil.addComponent(northJPanel, 
			new JLabel(" "),
			0, y++, 1, 1, 1, 0, 
			GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);
		JGUIUtil.addComponent(northJPanel, 
			new JLabel(
			"You are currently logged in as \"guest\".  Therefore "
			+ "you can create time series products"
			),
			0, y++, 1, 1, 1, 0, 
			GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);
		JGUIUtil.addComponent(northJPanel, 
			new JLabel(
			"but cannot save to the database.  You can save as "
			+ "time series product files and open later."),
			0, y++, 1, 1, 1, 0, 
			GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);
	}

	JGUIUtil.addComponent(northJPanel, 
		new JLabel(" "),
		0, y++, 1, 1, 1, 0, 
		GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);		
	JGUIUtil.addComponent(northJPanel, 
		new JLabel("Select the time series product to open:"),
		0, y, 1, 1, 1, 0, 
		GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);

	JPanel centerJPanel = new JPanel();
	centerJPanel.setLayout(new GridBagLayout());
	getContentPane().add("Center", centerJPanel);

	__list = new SimpleJList(readTSProducts());
	__list.addMouseListener(this);
	JGUIUtil.addComponent(centerJPanel, new JScrollPane(__list),
		0, 0, 1, 1, 1, 1,
		GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);

        JPanel southJPanel = new JPanel();
        southJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add("South", southJPanel);

	JButton newButton = new JButton(__BUTTON_NEW);
	newButton.addActionListener(this);
	southJPanel.add(newButton);

	JButton openButton = new JButton(__BUTTON_OPEN);
	openButton.addActionListener(this);
	southJPanel.add(openButton);

	__deleteButton = new JButton(__BUTTON_DELETE);
	__deleteButton.addActionListener(this);
	southJPanel.add(__deleteButton);
	__deleteButton.setEnabled(false);

	__renameButton = new JButton(__BUTTON_RENAME);
	__renameButton.addActionListener(this);
	southJPanel.add(__renameButton);
	__renameButton.setEnabled(false);

	__okButton = new JButton(__BUTTON_OK);
	__okButton.addActionListener(this);
	southJPanel.add(__okButton);
	__okButton.setEnabled(false);

	JButton cancelButton = new JButton(__BUTTON_CANCEL);
	cancelButton.addActionListener(this);
	southJPanel.add(cancelButton);

	if (appName == null || appName.equals("")) {
		setTitle("Select Time Series Product");
	}
	else {	
		setTitle(appName + " - Select Time Series Product");
	}		
	
	__list.addListSelectionListener(this);
	
	pack();
	setSize(getWidth() + 100, 400);
	setVisible(true);
        JGUIUtil.center(this);
}

/**
Responds to changes in the list selection.
@param event the ListSelectionEvent that happened.
*/
public void valueChanged(ListSelectionEvent event) {
	if (__list.getSelectedIndex() == -1) {
		__okButton.setEnabled(false);
		__renameButton.setEnabled(false);
		__deleteButton.setEnabled(false);
	}
	else {
		__okButton.setEnabled(true);
		__renameButton.setEnabled(true);
		__deleteButton.setEnabled(true);
	}
}

/**
Responds to the window closing event.
@param event the window event that happened.
*/
public void windowClosing(WindowEvent event) {
        closeClicked();
}

/**
Responds to the window activated event. Updates the list of products in
the database.
@param event the window event that happened.
*/
public void windowActivated(WindowEvent event) {
	if (__list != null) {
		__list.setListData(readTSProducts());
	}
}

/**
Responds to the window closed event.  Does nothing.
@param event the window event that happened.
*/
public void windowClosed(WindowEvent event) {}

/**
Responds to the window deactivated event.  Does nothing.
@param event the window event that happened.
*/
public void windowDeactivated(WindowEvent event) {}

/**
Responds to window deiconified events.  Does nothing.
@param event the window event that happened.
*/
public void windowDeiconified(WindowEvent event) {}

/**
Responds to window opened events.  Does nothing.
@param event the window event that happened.
*/
public void windowOpened(WindowEvent event) {}

/**
Responds to window iconified events.  Does nothing.
@param event the window event that happened.
*/
public void windowIconified(WindowEvent event) {}

}
