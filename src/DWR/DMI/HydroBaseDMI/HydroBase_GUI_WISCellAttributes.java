// HydroBase_GUI_WISCellAttributes - Shows information pertaining to a particular
// WIS cell such as formula, and import information.

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
 
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleJList;

import RTi.Util.Message.Message;
import RTi.Util.String.StringUtil;

/**
This class is a GUI for displaying information about a cell in a WIS sheet,
such as its formula and format.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_WISCellAttributes 
extends JFrame 
implements ActionListener, WindowListener {

/**
The name of the class.
*/
public final static String CLASS = "HydroBase_GUI_WISCellProperties";

/**
Button labels.
*/
private final String
	__BUTTON_FULL_INFO = 	"Full Info",
	__BUTTON_OK = 		"OK";

/**
Reference to the parent JFrame running the application.
*/
private JFrame __parent = null;

/**
GeoViewUI for map interactions - passed to structure query.
*/
private GeoViewUI __geoview_ui = null;

/**
DMI used to communicate with the database.
*/
private HydroBaseDMI __dmi;

/**
Format for the cell.
*/
private HydroBase_WISFormat __wisFormat;

/**
Formula for the cell.
*/
private HydroBase_WISFormula __wisFormula;

/**
Import data for the cell.
*/
private HydroBase_WISImport __wisImport;

/**
GUI Buttons.
*/
private JButton
	__infoJButton,
	__okJButton;

/**
GUI text fields.
*/
private JTextField
	__gainLossJTextField,
	__weightJTextField;

/**
GUI lists.
*/
private SimpleJList<String>
	__formulaJList,
	__importJList;

/**
Constructor.
@param rowLabel the label for the current row that this cell appears in.
@param colLabel the label for the current column that this cell appears in.
@param contents the data stored in this cell.
@param wisFormat the WISFormat object that accompanies this cell.
@param wisFormula the WISFormula object that accompanies this cell.
@param wisImport the WISImport object that accompanies this cell.
@param dmi the DMI to use for communicating with the database.
*/
public HydroBase_GUI_WISCellAttributes(String rowLabel, String colLabel, 
String contents, HydroBase_WISFormat wisFormat, HydroBase_WISFormula wisFormula,
HydroBase_WISImport wisImport, HydroBaseDMI dmi) {
	this(null, null, rowLabel, colLabel, contents, wisFormat, wisFormula,
		wisImport, dmi, null);
}

/**
Constructor.
@param rowLabel the label for the current row that this cell appears in.
@param colLabel the label for the current column that this cell appears in.
@param contents the data stored in this cell.
@param wisFormat the WISFormat object that accompanies this cell.
@param wisFormula the WISFormula object that accompanies this cell.
@param wisImport the WISImport object that accompanies this cell.
@param dmi the DMI to use for communicating with the database.
@param downstreamNode the node that is downstream from the current cell's.
*/
public HydroBase_GUI_WISCellAttributes(String rowLabel, String colLabel, 
String contents, HydroBase_WISFormat wisFormat, HydroBase_WISFormula wisFormula,
HydroBase_WISImport wisImport, HydroBaseDMI dmi, 
HydroBase_Node downstreamNode) {
	this(null, null, rowLabel, colLabel, contents, wisFormat, wisFormula,
		wisImport, dmi, downstreamNode);
}

/**
Constructor.
@param parent the parent CWRATMainJFrame running the application.
@param geoview_ui GeoViewUI for map interaction.
@param rowLabel the label for the current row that this cell appears in.
@param colLabel the label for the current column that this cell appears in.
@param contents the data stored in this cell.
@param wisFormat the WISFormat object that accompanies this cell.
@param wisFormula the WISFormula object that accompanies this cell.
@param wisImport the WISImport object that accompanies this cell.
@param dmi the DMI to use for communicating with the database.
*/
public HydroBase_GUI_WISCellAttributes(JFrame parent, GeoViewUI geoview_ui,
String rowLabel, 
String colLabel, String contents, HydroBase_WISFormat wisFormat, 
HydroBase_WISFormula wisFormula, HydroBase_WISImport wisImport, 
HydroBaseDMI dmi) {	
	this(parent, geoview_ui, rowLabel, colLabel, contents, wisFormat,
		wisFormula, wisImport, dmi, null);
}

/**
Constructor.
@param parent the parent JFrame running the application.
@param geoview_ui GeoViewUI for map interaction.
@param rowLabel the label for the current row that this cell appears in.
@param colLabel the label for the current column that this cell appears in.
@param contents the data stored in this cell.
@param wisFormat the WISFormat object that accompanies this cell.  Cannot be
null.
@param wisFormula the WISFormula object that accompanies this cell.
@param wisImport the WISImport object that accompanies this cell.
@param dmi the DMI to use for communicating with the database.
@param downstreamNode the node that is downstream from the current cell's.
*/
public HydroBase_GUI_WISCellAttributes(JFrame parent, GeoViewUI geoview_ui,
String rowLabel, 
String colLabel, String contents, HydroBase_WISFormat wisFormat, 
HydroBase_WISFormula wisFormula, HydroBase_WISImport wisImport, 
HydroBaseDMI dmi, HydroBase_Node downstreamNode) {	
	__parent = parent;
	__geoview_ui = geoview_ui;
	__wisFormat = wisFormat;
	__wisFormula = wisFormula;
	__wisImport = wisImport;
	__dmi = dmi;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI(rowLabel, colLabel, contents, downstreamNode);
}

/**
Responds to actionPerformed events.
@param event ActionEvent object.
*/
public void actionPerformed(ActionEvent event) {
	String command = event.getActionCommand().trim();

        if (command.equals(__BUTTON_OK)) {
		okClicked();
                return;
        }
        else if (command.equals(__BUTTON_FULL_INFO)) {
		// Show information for structure or station...
		showFullInformation();
        }
}

/**
Retrieves the cell formula information and places it into the appropriate 
GUI objects.
*/
private void getCellFormula() {
	String formulaString = "";
	if (__wisFormula == null || __wisFormula.getFormulastring().equals("")){
		__formulaJList.add("CELL DOES NOT CONTAIN A FORMULA");
	}
	else {
		List<HydroBase_WISMath> results = HydroBase_WISMath.parseFormula(
			__wisFormula.getFormulastring(), 
			HydroBase_WISMath.LABEL);
		formulaString = "";
		int terms = results.size();
		HydroBase_WISMath wisMath;
		for (int i = 0; i < terms; i++) {
			wisMath = (HydroBase_WISMath)results.get(i);
	                formulaString = HydroBase_WISMath.getTerm(
				wisMath, HydroBase_WISMath.LABEL);
			__formulaJList.add(formulaString);
		}
	}
}

/**
Retrieves the cell import information and places it into the appropriate GUI
objects.
*/
private void getCellImport() {
	String valueAsString = "";

	if ((__wisImport == null) || __wisImport.getImport_method().equals("")){
		 __importJList.add("CELL DOES NOT CONTAIN AN IMPORT VALUE");
		return;
	}

	List<Object> importValue = HydroBase_WIS_Util.getWISImportValue(
		__dmi, __wisImport);
	if (Integer.parseInt(importValue.get(1).toString()) > 0) {
		Double myDouble = (Double)importValue.get(0);
		valueAsString = StringUtil.formatString(
			myDouble.doubleValue(), "%10.1f");
	}
	else {	
		valueAsString  = "No Import Value available";		
	}

	String id = __wisImport.getImport_identifier();
	String import_type = "";
	String endAsString = "";
	String offsetAsString = "";
	boolean isRealTime = false;
	if (id.startsWith("WIS")) {
		import_type += HydroBase_GUI_WISImportWizard.WIS;
	}

	// Assume real time as the defualt case(i.e. without any
	// prefix on the identifier).
	else {
		import_type += HydroBase_GUI_WISImportWizard.RT;
		isRealTime = true;
		if (__wisImport.getEnd_time()== DMIUtil.MISSING_INT) {
			endAsString = HydroBase_GUI_WISImportWizard.CURRENT;
			offsetAsString = DMIUtil.MISSING_STRING;
		}
		else {
			endAsString += __wisImport.getEnd_time();
			offsetAsString += __wisImport.getTime_offset();
		}
	}

	__importJList.add("IMPORT TYPE: " + import_type);
	if (isRealTime) {
		__importJList.add("IMPORT METHOD: " +
			__wisImport.getImport_method());
		__importJList.add("END TIME: " + endAsString +
			"   TIME OFFSET: " + offsetAsString);
	}
	__importJList.add("ORIGINAL IMPORT VALUE: " + valueAsString.trim());
}

/**
Called when the OK button is pressed.  Closes the GUI.
*/
private void okClicked() {
	super.setVisible(false);                                            
        dispose();                        
}

/**
Sets the value displayed in the gains field.
@param s the value to display in the gains field.
*/
public void setGainsField(String s) {
	__gainLossJTextField.setText(s);
}


/**
Sets up the GUI.
@param rowLabel the label for the current cell's row.
@param colLabel the label for the current cell's column.
@param contents that value stored in the current cell.
@param downstreamNode the node downstream from the current cell's node.
*/
private void setupGUI(String rowLabel, String colLabel, String contents,
HydroBase_Node downstreamNode) {
	String routine = CLASS + ".setupGUI";
	addWindowListener(this);

	// definitions to be used in the GridBagLayouts
	Insets insetsTLNN = new Insets(7,7,0,0);
	Insets insetsTNNR = new Insets(7,0,0,7);
	Insets insetsNNNR = new Insets(0,0,0,7);
	Insets insetsNLNN = new Insets(0,7,0,0);
	GridBagLayout gbl = new GridBagLayout();

        // North West panel
        JPanel northWJPanel = new JPanel();
	northWJPanel.setLayout(gbl);
        //northJPanel.add("West", northWJPanel);
        getContentPane().add("Center", northWJPanel);

	int y = 0;
       	JGUIUtil.addComponent(northWJPanel, new JLabel("Row JLabel:"), 
		0, y, 1, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
	JTextField rowLabelJTextField = null;
	if (rowLabel != null) {
		rowLabelJTextField = new JTextField(rowLabel);
	}
	else {	
		rowLabelJTextField = new JTextField(" ");
	}
	rowLabelJTextField.setEditable(false);
	JGUIUtil.addComponent(northWJPanel, rowLabelJTextField, 
		1, y, 3, 1, 1, 0, insetsTNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northWJPanel, new JLabel("Internal Identifier:"), 
		0, ++y, 1, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
	JTextField __identifierJTextField = new JTextField();
	String id = "";
	id = __wisFormat.getIdentifier();
	__identifierJTextField.setText(id);
	__identifierJTextField.setEditable(false);
	JGUIUtil.addComponent(northWJPanel, __identifierJTextField, 
		1, y, 2, 1, 1, 0, insetsTNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	// Only add if a WDID - don't want to mess with station yet...
	if (id.startsWith("wdid:")) {
		__infoJButton = new JButton(__BUTTON_FULL_INFO);
		__infoJButton.setToolTipText("Show full information for "
			+ "structure or station.");
		__infoJButton.addActionListener(this);
		JGUIUtil.addComponent(northWJPanel, __infoJButton, 
			3, y, 1, 1, 0, 0, insetsTNNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	}

	if (colLabel != null) {
        	JGUIUtil.addComponent(northWJPanel, new JLabel("Column:"), 
		0, ++y, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
		JTextField colLabelJTextField = new JTextField(" " + colLabel 
			+ "     ");
		colLabelJTextField.setEditable(false);
        	JGUIUtil.addComponent(northWJPanel, colLabelJTextField, 
			1, y, 3, 1, 0, 0, insetsNNNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	}

        JGUIUtil.addComponent(northWJPanel, new JLabel("Row Type:"), 
		0, ++y, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
	JTextField rowTypeJTextField = new JTextField();
	if (__wisFormat.getRow_type() != null) {
		rowTypeJTextField.setText(__wisFormat.getRow_type());
	}
	rowTypeJTextField.setEditable(false);
	JGUIUtil.addComponent(northWJPanel, rowTypeJTextField, 
		1, y, 1, 1, 1, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northWJPanel, new JLabel("Stream for Row:"), 
		0, ++y, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
	JTextField streamJTextField = new JTextField();
	HydroBase_Stream stream = null;
	try {
		stream = __dmi.readStreamForStream_num(
			__wisFormat.getWdwater_num());
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading stream data.");
		Message.printWarning(2, routine, e);
	}
	if (stream != null) {
		streamJTextField.setText(stream.getStream_name() + " ("
			+ "wis_format.wdwater_num = " 
			+ __wisFormat.getWdwater_num() + ")");
	}
	else {	
		streamJTextField.setText(
			" Unknown (wis_format.wdwater_num = " 
			+ __wisFormat.getWdwater_num() + ")");
	}
	streamJTextField.setEditable(false);
	JGUIUtil.addComponent(northWJPanel, streamJTextField, 
		1, y, 3, 1, 1, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northWJPanel, new JLabel("Stream Mile:"), 
		0, ++y, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
	JTextField mileJTextField = new JTextField();
	if ( !DMIUtil.isMissing(__wisFormat.getStr_mile()) && !HydroBase_Util.isMissing(__wisFormat.getStr_mile())) {
		mileJTextField.setText("" + __wisFormat.getStr_mile());
	}
	mileJTextField.setEditable(false);
	JGUIUtil.addComponent(northWJPanel, mileJTextField, 
		1, y, 1, 1, 1, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	if (__wisFormat.getRow_type().equalsIgnoreCase( HydroBase_GUI_WIS.CONFLUENCE)) {
		// The wdwater is the current stream number and the wdwater_link
		// is the stream number for the stream that is coming in at the
		// confluence...
        	JGUIUtil.addComponent(northWJPanel, new JLabel(
			"Tributary Stream:"), 
			0, ++y, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
		JTextField trib_streamJTextField = new JTextField();
		try {
			stream = __dmi.readStreamForStream_num(
				__wisFormat.getWdwater_link());
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading stream data.");
			Message.printWarning(2, routine, e);
		}
		if (stream != null) {
			trib_streamJTextField.setText(stream.getStream_name() + " (" + __wisFormat.getWdwater_link() + ")");
		}
		else {	
			trib_streamJTextField.setText( " Unknown (" + __wisFormat.getWdwater_link() + ")");
		}
		trib_streamJTextField.setEditable(false);
		JGUIUtil.addComponent(northWJPanel, trib_streamJTextField, 
			1, y, 3, 1, 1, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	}
	else if (__wisFormat.getRow_type().equalsIgnoreCase(
		HydroBase_GUI_WIS.STREAM)) {
		// The wdwater is the current stream number and the wdwater_link
		// is the stream number for the downstream stream...
        	JGUIUtil.addComponent(northWJPanel, new JLabel(
			"Downstream Stream:"), 
			0, ++y, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
		JTextField trib_streamJTextField = new JTextField();
		try {
			stream = __dmi.readStreamForStream_num(
				__wisFormat.getWdwater_link());
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading stream data.");
			Message.printWarning(2, routine, e);
		}
		if (stream != null) {
			trib_streamJTextField.setText(stream.getStream_name() + " (" + __wisFormat.getWdwater_link() + ")");
		}
		else {	
			trib_streamJTextField.setText( " Unknown (" + __wisFormat.getWdwater_link() + ")");
		}
		trib_streamJTextField.setEditable(false);
		JGUIUtil.addComponent(northWJPanel, trib_streamJTextField, 
			1, y, 3, 1, 1, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	}

        JGUIUtil.addComponent(northWJPanel, new JLabel("Is Baseflow:"), 
		0, ++y, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
	JTextField baseflowJTextField = new JTextField(7);
	baseflowJTextField.setText(__wisFormat.getKnown_point());
	baseflowJTextField.setEditable(false);
	JGUIUtil.addComponent(northWJPanel, baseflowJTextField, 
		1, y, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northWJPanel,new JLabel("Current Cell Contents:"),
		0, ++y, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
	JTextField contentsJTextField = new JTextField();
	if (contents != null) {
		contentsJTextField.setText(contents);
	}
	contentsJTextField.setEditable(false);
	JGUIUtil.addComponent(northWJPanel, contentsJTextField, 
		1, y, 3, 1, 1, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northWJPanel,new JLabel("Downstream Row Label:"),
		0, ++y, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
	JTextField down_row_labelJTextField = new JTextField();
	down_row_labelJTextField.setEditable(false);
	if (downstreamNode != null) {
		down_row_labelJTextField.setText(
			downstreamNode.getDescription());
	}
	contentsJTextField.setEditable(false);
	JGUIUtil.addComponent(northWJPanel, down_row_labelJTextField, 
		1, y, 3, 1, 1, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northWJPanel,
		new JLabel("Downstream Internal Identifier:"), 
		0, ++y, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
	JTextField down_internal_idJTextField = new JTextField();
	down_internal_idJTextField.setEditable(false);
	if (downstreamNode != null) {
		down_internal_idJTextField.setText(
			downstreamNode.getCommonID());
	}
	contentsJTextField.setEditable(false);
	JGUIUtil.addComponent(northWJPanel, down_internal_idJTextField, 
		1, y, 3, 1, 1, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northWJPanel, new JLabel(
		"Gain/Loss Coefficient. (using Stream Mile):"), 
		0, ++y, 2, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
	__gainLossJTextField = new JTextField();
	__gainLossJTextField.setEditable(false);
	JGUIUtil.addComponent(northWJPanel, __gainLossJTextField, 
		2, y, 2, 1, 1, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northWJPanel, new JLabel(
		"Gain/Loss Coefficient. (using Weight):"), 
		0, ++y, 2, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);
	__weightJTextField = new JTextField();
	__weightJTextField.setEditable(false);
	JGUIUtil.addComponent(northWJPanel, __weightJTextField, 
		2, y, 2, 1, 1, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northWJPanel, new JLabel("Import:"), 
		0, ++y, 1, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.NORTHEAST);
	__importJList = new SimpleJList<String>();
        JGUIUtil.addComponent(northWJPanel, new JScrollPane(__importJList), 
		1, y, 4, 1, 1, 1, insetsTNNR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

        JGUIUtil.addComponent(northWJPanel, new JLabel("Formula:"), 
		0, ++y, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.NORTHEAST);
	__formulaJList = new SimpleJList<String>();
        JGUIUtil.addComponent(northWJPanel, new JScrollPane(__formulaJList), 
		1, y, 4, 1, 1, 1, insetsNNNR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

	JPanel okJPanel = new JPanel();
	getContentPane().add("South", okJPanel);

        __okJButton = new JButton(__BUTTON_OK);
	__okJButton.setToolTipText("Accept values and return.");
	__okJButton.addActionListener(this);
	okJPanel.add(__okJButton);

	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}

        setTitle(app + "WIS Cell Properties");
        pack();

	getCellImport();
	getCellFormula();
        pack();
	setSize(getWidth() + 20, getHeight());
	JGUIUtil.center(this);
        setVisible(true);
}

/**
Sets the value to display in the weighted gains field.
@param s the value to display in the weighted gains field.
*/
public void setWeightedGainsField(String s) {
	__weightJTextField.setText("" + s);
}

/**
Show the full information for a structure or station by using the main View
Data GUIs.
*/
private void showFullInformation() {
	String routine = CLASS + ".showFullInformation";
	String id = __wisFormat.getIdentifier();
	if (id.startsWith("wdid:") && (id.length() > 5) && (__parent != null)) {
		try {
			HydroBase_GUI_StructureQuery gui = 
				new HydroBase_GUI_StructureQuery(__dmi, 
				__parent, __geoview_ui );
			gui.setQueryWDID(id.substring(5));
			gui.submitQuery();
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error reading reading"
				+ " structure data.");
			Message.printWarning(2, routine, e);
		}
	}
	else if (id.startsWith("stat:")&&(__parent != null)) {
		Message.printWarning(1, "", "Need station display.");
	}
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
*/
public void windowClosing(WindowEvent event) {
	okClicked();
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
