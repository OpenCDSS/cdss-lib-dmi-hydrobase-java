// HydroBase_GUI_WellApplicationQuery - Well permit Query GUI

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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import RTi.GIS.GeoView.GeoRecord;
import RTi.GIS.GeoView.GeoViewListener;

import RTi.GR.GRLimits;
import RTi.GR.GRPoint;
import RTi.GR.GRShape;

import RTi.Util.GUI.JGUIUtil; 

import RTi.Util.GUI.JScrollWorksheet;
import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.SimpleJComboBox;

import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.Time.StopWatch;

/**
This GUI displays the main structure query interface.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_WellApplicationQuery 
extends JFrame
implements ActionListener, GeoViewListener, KeyListener, 
MouseListener, WindowListener {

/**
Button labels.
*/
private final String 
	__BUTTON_CLOSE = "Close",
	__BUTTON_EXPORT = "Export",
	__BUTTON_GET_DATA = "Get Data",
	__BUTTON_HELP = "Help",
	__BUTTON_SELECT_ON_MAP = "Select On Map",
	__BUTTON_VIEW = "View";

/**
Whether the query is a geo view select query or not.
*/
private boolean __geoViewSelectQuery = false;

/**
The parent JFrame on which this JFrame was opened, for window positioning.
*/
private JFrame __parent = null;

/**
The GeoViewUI instance, for map interaction.
*/
private GeoViewUI __geoview_ui = null;

/**
The limits for doing geo view queries.
*/
private GRLimits __mapQueryLimits = null;

/**
The dmi to use for communicating with the database.
*/
private HydroBaseDMI __dmi;

private HydroBase_GUI_Well_InputFilter_JPanel __filterJPanel = null;

/**
GUI buttons.
*/
private JButton		
	__closeJButton,
	__exportJButton,
	__getDataJButton,
	__selectOnMapJButton,
	__viewJButton;

/**
Label by the worksheet telling how many records were returned.
*/
private JLabel __worksheetJLabel;

/**
GUI text fields.
*/
private JTextField 
	__statusJTextField;

/**
Table for showing query results.
*/
private JWorksheet __worksheet = null;

private SimpleJComboBox __waterDistrictJComboBox;

/**
Constructor.  Create the interface and make visible.
@param dmi HydroBaseDMI instance for database interactions.
@param parent the parent that opened this gui.
@param geoview_ui GeoViewUI instance for map interactions.
*/
public HydroBase_GUI_WellApplicationQuery(HydroBaseDMI dmi,
JFrame parent, GeoViewUI geoview_ui) 
throws Exception {
	this(dmi, parent, geoview_ui, true);
}

/**
Constructor.
@param dmi HydroBaseDMI instance for database interactions.
@param parent the parent app that opened this gui.
@param geoview_ui GeoViewUI instance for map interactions.
@param isVisible Indicates whether the interface should be visible at creation.
*/
public HydroBase_GUI_WellApplicationQuery(HydroBaseDMI dmi, 
JFrame parent, GeoViewUI geoview_ui, boolean isVisible)
throws Exception {
	__parent = parent;
	__geoview_ui = geoview_ui;

	if (dmi == null) {
		throw new Exception ("Null dmi passed to "
			+ "HydroBase_GUI_WellApplicationQuery "
			+ "constructor.  DMI object must be open and "
			+ "connected before passing in.");
	}
	if (!dmi.isOpen()) {
		throw new Exception ("Unopened dmi passed to "
			+ "HydroBase_GUI_WellApplicationQuery "
			+ "constructor.  DMI object must be open and "
			+ "connected before passing in.");
	}
	
	__dmi = dmi;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	setupGUI(isVisible);
}

/**
This function is responsible for handling ActionEvents for all 
gui objects which use and ActionEventListener.
@param event ActionEvent object.
*/
public void actionPerformed(ActionEvent event) {
	String routine = "actionPerformed";
	String action = event.getActionCommand();

        if (action.equals(__BUTTON_CLOSE)) {
                closeClicked();
        }
        else if (action.equals(__BUTTON_EXPORT)) {
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
			Message.printWarning(1, routine, "Error in export.");
			Message.printWarning (2, routine, ex);
		}	
        }      
        else if (action.equals(__BUTTON_GET_DATA)) {
		try {
	                submitQuery();
		}
		catch (Exception e) {
			Message.printWarning(1, routine, "Error querying "
				+ "data.");
			Message.printWarning(1, routine, e);
		}	
        }
        else if (action.equals(__BUTTON_HELP)) {
        }
        else if (action.equals(__BUTTON_SELECT_ON_MAP)) {
		selectOnMap();
        }
	else if (action.equals(__BUTTON_VIEW)) {
		viewClicked();
	}
}

/**
Clear the list and disable buttons.
*/
private void clearList() {
	__worksheet.clear();  
	// Disable displays until the user picks something...
	__selectOnMapJButton.setEnabled(false);
	__viewJButton.setEnabled(false);
	__exportJButton.setEnabled(false);
	__viewJButton.setEnabled(false);
}

/**
This function is responsible for closing the GUI.
*/
protected void closeClicked() {
	setVisible(false); 
}


/**
Display the results of the query in the spreadsheet.  
@param results the results to display in the spreadsheet.
*/
private void displayResults(List<HydroBase_WellApplicationView> results) 
throws Exception {
	HydroBase_TableModel_WellApplicationView tm = 
		new HydroBase_TableModel_WellApplicationView(results);
	HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
	__worksheet.setCellRenderer(cr);
	__worksheet.setModel(tm);	
	__worksheet.setColumnWidths(tm.getColumnWidths());
}

/**
Enable layers in the map that are appropriate for the structure view.  If a map
is being used, disable the layers that are not a "WellPermit" or "BaseLayer"
AppLayerType.
*/
private void enableMapLayers() {
	if (__geoview_ui.isMapVisible()) {
		JGUIUtil.setWaitCursor(this, true);
		__statusJTextField.setText(
			"Updating map to show only structure layers.");
		Message.printStatus(1, "",
			"Turning on structure GIS layer types.");
			
		List<String> enabled_appLayerTypes = new Vector<String>();
		enabled_appLayerTypes.add("WellPermit");
		
		// Base layers are always visible...
		enabled_appLayerTypes.add("BaseLayer");
		__geoview_ui.getGeoViewJPanel().enableAppLayerTypes(
			enabled_appLayerTypes, false);
		enabled_appLayerTypes = null;
		
		JGUIUtil.setWaitCursor(this, false);
		__statusJTextField.setText(
			"Map shows base layers and well permit layers.  "
			+ "Ready.");
	}
}

/**
Responsible for formatting output.
@param format format delimiter flag defined in this class
@return formatted list for exporting, printing, etc..
*/
public List<String> formatOutput(int format) {	
	List<String> v = new Vector<String>();
        int numCols = __worksheet.getColumnCount();
        int numRows = __worksheet.getRowCount();
        String rowString;

        char delim = HydroBase_GUI_Util.getDelimiterForFormat(format);
       
       	rowString = "";
       	for (int j = 1; j < numCols; j++) {
		rowString += __worksheet.getColumnName(j, true) + "" + delim;
	}
	v.add(rowString);
		
	Object o = null;
       
        for (int i = 0; i < numRows; i++) {
                rowString = "";
                for (int j = 1; j < numCols; j++) {
			o = __worksheet.getValueAtAsString(i, j);
			if (o == null) {
				rowString += "" + " " + delim;
			} 
			else {
                        	rowString += "" + o.toString() + delim;
			}
                }
                v.add(rowString);
        }
        return v;
}

/**
Does nothing.
@return null.
*/
public String geoViewGetLabel(GeoRecord record) {
	return null;
}

/**
Does nothing.
*/
public void geoViewInfo(GRShape devlimits, GRShape datalimits, List<GeoRecord> selected)
{}

/**
Does nothing.
*/
public void geoViewInfo(GRPoint devlimits, GRPoint datalimits, List<GeoRecord> selected)
{}

/**
Does nothing.
*/
public void geoViewInfo(GRLimits devlimits, GRLimits datalimits,List<GeoRecord> selected)
{}

/**
Does nothing.
*/
public void geoViewMouseMotion(GRPoint devpt, GRPoint datapt) {}

/**
If a selection is made from the map, query the database for region that was
selected.  It is assumed that this GUI is created in the main application
even if it is invisible.  This will ensure that the method is called as a
@param devlimits Limits of select in device coordinates(pixels).
@param datalimits Limits of select in data coordinates.
@param selected list of selected GeoRecord.  Currently ignored.
*/
public void geoViewSelect(GRShape devlimits, GRShape datalimits,
		List<GeoRecord> selected, boolean append) {
	String routine = "geoViewSelect";
	// Figure out which app layer types are selected.  If one that is
	// applicable to this GUI, execute a query...

	List<String> appLayerTypes = __geoview_ui.getGeoViewJPanel().getLegendJTree()
		.getSelectedAppLayerTypes(true);
	int size = appLayerTypes.size();
	String appLayerType = null;
	boolean view_needed = false;

	for (int i = 0; i < size; i++) {
		appLayerType =(String)appLayerTypes.get(i);
		if (appLayerType.equalsIgnoreCase("WellPermit")) {
			// Make sure the headgate checkbox is selected...
			view_needed = true;
			break;
		}
	}

	if (!view_needed) {
		return;
	}

	if (!isVisible()) {
		// If not visible, set to visible if the selected map layers
		// apply to this window...
		setVisible(true);
	}

	// Now execute the query...

	if (devlimits.type == GRShape.LIMITS) {
		__mapQueryLimits =(GRLimits)datalimits;
	}
	else if (devlimits.type == GRShape.POINT) {
		// Do not allow point to initiate a query...
		__mapQueryLimits = null;
		return;
	}

	try {
		__geoViewSelectQuery = true;
                submitQuery();
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error querying data.");
		Message.printWarning(2, routine, e);
	}	
	__mapQueryLimits = null;
}

/**
If a selection is made from the map, query the database for region that was
selected.  It is assumed that this GUI is created in the main application
even if it is invisible.  This will ensure that the method is called as a
@param devlimits Limits of select in device coordinates(pixels).
@param datalimits Limits of select in data coordinates.
@param selected Vector of selected GeoRecord.  Currently ignored.
*/
public void geoViewSelect(GRPoint devlimits, GRPoint datalimits,
		List<GeoRecord> selected, boolean append) {
	geoViewSelect(devlimits, datalimits, selected, append);
}

/**
If a selection is made from the map, query the database for region that was
selected.  It is assumed that this GUI is created in the main application
even if it is invisible.  This will ensure that the method is called as a
@param devlimits Limits of select in device coordinates(pixels).
@param datalimits Limits of select in data coordinates.
@param selected list of selected GeoRecord.  Currently ignored.
*/
public void geoViewSelect(GRLimits devlimits, GRLimits datalimits,
		List<GeoRecord> selected, boolean append) {
	geoViewSelect(devlimits, datalimits, selected, append);
}

/**
Does nothing.
*/
public void geoViewZoom(GRShape devlimits, GRShape datalimits) {}

/**
Does nothing.
*/
public void geoViewZoom(GRLimits devlimits, GRLimits datalimits) {}

/**
Return a list with the visible AppLayerType.
*/
private List<String> getVisibleAppLayerType() {
	List<String> appLayerTypes = new Vector<String>(1);
	appLayerTypes.add("WellPermit");
	return appLayerTypes;
}

/**
Responds to key pressed events.
@param event the KeyEvent that happened.
*/
public void keyPressed(KeyEvent event) {
	String routine = "HydroBase_GUI_WellApplicationQuery.keyPressed";
        int code = event.getKeyCode();
        
        // enter key runs the query.
        if (code == KeyEvent.VK_ENTER) {
		try {
	                submitQuery();
		}
		catch (Exception e) {
			Message.printWarning(1, routine,"Error querying data.");
			Message.printWarning(2, routine, e);
		}
        }
}

/**
Responds to the key released events.  Does nothing.
@param event the KeyEvent that happened.
*/
public void keyReleased(KeyEvent event) {}
/**
Responds to key typed events.  Does nothing.
@param event the KeyEvent that happened.
*/
public void keyTyped(KeyEvent event) {}

/**
Responds to mouse clicks.
@param event the MouseEvent that happened.
*/
public void mouseClicked(MouseEvent event) {
}

/**
Responds to clicks in the filter jpanel.
@param event the MouseEvent that happened.
*/
public void mousePressed(MouseEvent event) {
	int numFilters = __filterJPanel.getNumFilterGroups();
	for (int i = 0; i < numFilters; i++) {
		if (event.getSource() == __filterJPanel.getInputFilter(
			i).getInputComponent()) {
			HydroBase_GUI_Util.buildLocation(this,
				(JTextField)event.getSource());
		}
	}
}

/**
Responds when the mouse button is released.
@param event the MouseEvent that happened.
*/
public void mouseReleased(MouseEvent event) {
	Object ob = event.getComponent();
	if (ob == __worksheet) {
		int row = __worksheet.getSelectedRowCount();
		if (row == 1) {
			__viewJButton.setEnabled(true);
			if (__geoview_ui.isMapVisible()) {
				List<String> v = __geoview_ui.getGeoViewJPanel()
					.getEnabledAppLayerTypes();
				if (v != null) {
					int size = v.size();
					for (int i = 0; i < size; i++) {
						if (v.get(i).equalsIgnoreCase("Well")) {
							__selectOnMapJButton
								.setEnabled(
								true);
							return;
						}
					}
				}
				__selectOnMapJButton.setEnabled(false);
			}
			else {
				__selectOnMapJButton.setEnabled(false);
			}
		}
		else {
			__viewJButton.setEnabled(false);
			__selectOnMapJButton.setEnabled(false);
		}
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
Resets the GUI for user interaction.
*/
private	void ready() {
	JGUIUtil.setWaitCursor(this, false);
	__statusJTextField.setText("Ready");
}

/**
Select the items in the list on the map.  This is done by putting together a
list of String, each of which has "receipt".  This field name is set in the
GeoView Project as AppJoinField="receipt".
*/
private void selectOnMap() {
	int size = __worksheet.getRowCount();
	List<String> idlist = new Vector<String>(size);
        __statusJTextField.setText("Selecting and zooming to well permits "
		+ "map.  Please wait...");
	JGUIUtil.setWaitCursor(this, true);

       	int[] rows = __worksheet.getSelectedRows();
	boolean all = false;
	if ((rows == null)||(rows.length == 0)) {
		all = true;
	}
	else {	
		size = rows.length;
	}

	int row = 0;
	HydroBase_WellApplicationView wav = null;
	for (int i = 0; i < size; i++) {
		if (all) {
			row = i;
		}
		else {
			row = rows[i];
		}
		wav = (HydroBase_WellApplicationView)
			__worksheet.getRowData(row);
		idlist.add(wav.getReceipt());
	}
	
	List<GeoRecord> matching_features =__geoview_ui.getGeoViewJPanel().selectAppFeatures(
				getVisibleAppLayerType(), idlist, true,
				0.5, 0.5);
	int matches = 0;
	if (matching_features != null) {
		matches = matching_features.size();
	}
	if (matches == 0) {
		Message.printWarning(1, 
			"HydroBase_GUI_WellApplicationQuery.selectOnMap",
			"No matching features were found in map data.\n"
			+ "This may be because the location data are "
			+ "incomplete or\nbecause no suitable GIS data "
			+ "layers are being used.");
	}
	else if (matches != size) {
		Message.printWarning(1, 
			"HydroBase_GUI_WellApplicationQuery.selectOnMap",
			"" + matches + " matching features out of " + size
			+ " records were found in map data.\n"
			+ "This may be because of incomplete location data.");
	}
        __statusJTextField.setText(
		"Map is zoomed to selected well permits.  Ready.");
	JGUIUtil.setWaitCursor(this, false);
}

/**
Show/hide the frame.  If district preferences have changed, they are reset here.
@param state true if showing the frame, false if hiding it.
*/
public synchronized void setVisible(boolean state) {
	String routine = "setVisible";
        if (state) {
                clearList();
		ready();

		try {
		HydroBase_GUI_Util.setWaterDistrictJComboBox(
			__dmi, __waterDistrictJComboBox, null, true, true,true);
		}
		catch (Exception e) {
			Message.printWarning (2, routine, e);
		}		
		
                __worksheetJLabel.setText(HydroBase_GUI_Util.LIST_LABEL);
//               __queryTypeJComboBox.select(HydroBase_GUI_Util._PERMIT_NUMBER);
// REVISIT -- select the 0th filter from the panel
                Message.setTopLevel(this);
		enableMapLayers();
		__worksheet.clear();
        }
        super.setVisible(state);
}

/**
Submits a query and displays the results.
*/
private void submitQuery() 
throws Exception {
	String routine= "HydroBase_GUI_WellApplicationQuery.submitQuery()";
        __worksheetJLabel.setText(HydroBase_GUI_Util.LIST_LABEL);

	if (!__filterJPanel.checkInput(true)) {
		ready();
		return;
	}		

	// Make the structure window go to the top so that it is obvious what is going on.
	setVisible(true);
	toFront();

	JGUIUtil.setWaitCursor(this, true);
        String status = "Please Wait... Retrieving Data";
        Message.printStatus(10, routine, status);
        __statusJTextField.setText(status);	

	StopWatch sw = new StopWatch();
	List<HydroBase_WellApplicationView> v = null;	
	try {
		sw.start();
		v = __dmi.readWellApplicationGeolocList(
			__filterJPanel, 
			__dmi.getWaterDistrictWhereClause(
				__waterDistrictJComboBox, 
				HydroBase_GUI_Util._WELL_APPLICATION_TABLE_NAME,
				true, true),
			__mapQueryLimits);
		displayResults(v);
		sw.stop();			
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading well "
			+ "application data.");
		v = new Vector<HydroBase_WellApplicationView>();
		Message.printWarning (2, routine, e);
	}

        int displayed = __worksheet.getRowCount();
	String plural = "";
	if (displayed != 1) {
		plural = "s";
	}

        status = "" + v.size() + " record" + plural + " returned in " 
		+ sw.getSeconds() + " seconds";
	if (__geoViewSelectQuery) {
		status += ", queried using map coordinates.";
		__geoViewSelectQuery = false;
	}
	else {
		status += ".";
	}

        __statusJTextField.setText(status);

        // set the record display       
        __worksheetJLabel.setText("" + displayed + " record" + plural + 
		" returned in " + sw.getSeconds() + " seconds.");

	__selectOnMapJButton.setEnabled(false);
	__viewJButton.setEnabled(false);	
	if (displayed == 0) {
		__exportJButton.setEnabled(false);
	}
	else {
		__exportJButton.setEnabled(true);
	}							       
                       
        ready();
}

/**
Sets up the GUI.
*/
private void setupGUI(boolean isVisible) {
	addWindowListener(this);

	addKeyListener(this);
        
        // objects used throughout the GUI layout
	// int buffer = 7;	// Old inset.
	int buffer = 4;		// New inset.
        Insets insetsNNNR = new Insets(0,0,0,buffer);
        Insets insetsNLNN = new Insets(0,buffer,0,0);
        Insets insetsNLBR = new Insets(0,buffer,buffer,buffer);
        Insets insetsTLNR = new Insets(buffer,buffer,0,buffer);
        GridBagLayout gbl = new GridBagLayout();
        
        // North JPanel
        JPanel top_JPanel = new JPanel();
        top_JPanel.setLayout(new BorderLayout());
        getContentPane().add("North", top_JPanel);
        
        // North: West JPanel
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(gbl);
        top_JPanel.add("West", topLeftPanel);
        
        JGUIUtil.addComponent(topLeftPanel, new JLabel("Query Options:"), 
		0, 0, 2, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        
        JGUIUtil.addComponent(topLeftPanel, new JLabel("Div/Dist:"), 
		0, 1, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

	__waterDistrictJComboBox = new SimpleJComboBox();
        JGUIUtil.addComponent(topLeftPanel, __waterDistrictJComboBox,
		1, 1, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__filterJPanel = new HydroBase_GUI_Well_InputFilter_JPanel(__dmi, this);
	__filterJPanel.addEventListeners(this);
        JGUIUtil.addComponent(topLeftPanel, __filterJPanel, 
		0, 2, 3, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        
        __getDataJButton = new JButton(__BUTTON_GET_DATA);
	__getDataJButton.setToolTipText("Read data from database.");
	__getDataJButton.addActionListener(this);
        JGUIUtil.addComponent(topLeftPanel, __getDataJButton, 
		6, 2, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
        
        // Center JPanel
        JPanel center_JPanel = new JPanel();
        center_JPanel.setLayout(gbl);
        getContentPane().add("Center", center_JPanel);        
        
        __worksheetJLabel = new JLabel(HydroBase_GUI_Util.LIST_LABEL);
        JGUIUtil.addComponent(center_JPanel, __worksheetJLabel, 1, 1, 
		7, 1, 0, 0, insetsTLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        
	PropList p = new PropList("HydroBase_GUI_WellApplicationQuery"
		+ ".JWorksheet");
	p.add("JWorksheet.ShowRowHeader=true");
	p.add("JWorksheet.AllowCopy=true");	
	p.add("JWorksheet.ShowPopupMenu=true");

	JScrollWorksheet jsw = new JScrollWorksheet(0, 0, p);
	__worksheet = jsw.getJWorksheet();
	__worksheet.setHourglassJFrame(this);
	__worksheet.addMouseListener(this);
        JGUIUtil.addComponent(center_JPanel, jsw,
		1, 2, 7, 3, 1, 1, insetsNLBR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

        //Bottom JPanel(Consist of three more JPanels)
        JPanel bottom_JPanel = new JPanel();
        bottom_JPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottom_JPanel);
        
        // Bottom: North JPanel
        JPanel bottomNorth_JPanel = new JPanel();
        bottomNorth_JPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottom_JPanel.add("North", bottomNorth_JPanel);

	JPanel display_JPanel = new JPanel();
	display_JPanel.setLayout(gbl);
	bottomNorth_JPanel.add(display_JPanel);

        // Bottom: Center JPanel
        JPanel bottomCenter_JPanel = new JPanel();
        bottomCenter_JPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottom_JPanel.add("Center", bottomCenter_JPanel);
                
        __selectOnMapJButton = new JButton(__BUTTON_SELECT_ON_MAP);
	__selectOnMapJButton.setToolTipText("Select selected well application "	
		+ "on GIS map.");
        __selectOnMapJButton.setEnabled(false);	// Until data selected
	__selectOnMapJButton.addActionListener(this);
        bottomCenter_JPanel.add(__selectOnMapJButton);

        __viewJButton = new JButton(__BUTTON_VIEW);
	__viewJButton.setToolTipText("View selected well application.");
	__viewJButton.addActionListener(this);
	__viewJButton.setEnabled(false);
        bottomCenter_JPanel.add(__viewJButton);
        
        __exportJButton = new JButton(__BUTTON_EXPORT);
	__exportJButton.setToolTipText("Export form data to a file.");
        __exportJButton.setEnabled(false);		
	__exportJButton.addActionListener(this);
        bottomCenter_JPanel.add(__exportJButton);

        __closeJButton = new JButton(__BUTTON_CLOSE);
	__closeJButton.setToolTipText("Close form.");
	__closeJButton.addActionListener(this);
        bottomCenter_JPanel.add(__closeJButton);
        
//        __helpJButton = new JButton(__BUTTON_HELP);
//	__helpJButton.addActionListener(this);
//        bottomCenter_JPanel.add(__helpJButton);
        
        // Bottom: South JPanel
        JPanel bottomSouth_JPanel = new JPanel();
        bottomSouth_JPanel.setLayout(gbl);
        bottom_JPanel.add("South", bottomSouth_JPanel);
        
        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomSouth_JPanel, __statusJTextField, 0, 1, 
		10, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        
        // Frame settings
	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}
	setTitle(app + "Well Permit Query");
        pack();
	setSize(750, 500);
	JGUIUtil.center(this);

	// We want this GUI to listen to the map.  If no map is used no
	// listener calls will be generated...
	__geoview_ui.getGeoViewJPanel().getGeoView().addGeoViewListener(this);

	setVisible(isVisible);
}

/**
Responds when the view button is pressed.
*/
private void viewClicked() {
	int row = __worksheet.getSelectedRow();
	HydroBase_WellApplicationView data 
		= (HydroBase_WellApplicationView)
	__worksheet.getRowData(row);

	new HydroBase_GUI_WellApplication(__parent, data);
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
Responds to window closing events, calls closeClicked().
@param event the window event that happened.
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
