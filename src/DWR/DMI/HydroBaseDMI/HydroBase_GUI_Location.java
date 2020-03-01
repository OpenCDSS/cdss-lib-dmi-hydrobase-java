// HydroBase_GUI_Location - Location Data GUI

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2019 Colorado Department of Natural Resources

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

//-----------------------------------------------------------------------------
// HydroBase_GUI_Location - Location Data GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// Notes: 
//	(1)Can be used to display both structure and station location data.
//-----------------------------------------------------------------------------
// History:
//
// 18 Aug 1997	DLG, RTi 		Created initial version.
// 22 Aug 1997  DLG, RTi 		Added additional GUI components
// 03 Dec 1997	Steven A. Malers, RTi	Implement full export, print.
// 10 Feb 1998	DLG, RTi		Added stream mile display.
//					Updated to 1.1 event model.
// 23 Mar 1998 	DLG, RTi		Added a constructor to accept a 
//					HBStationLocation object
// 04 Apr 1999	SAM, RTi		Change cursors to Cursor class because
//					Frame version is deprecated.
// 12 Apr 1999	SAM, RTi		Minor update because of database
//					changes.  Change the range field to be
//					four characters.
// 2001-11-12	SAM, RTi		Change GUI to JGUIUtil.
//-----------------------------------------------------------------------------
// 2003-09-30	J. Thomas Sapienza, RTi	Initial swing version.
// 2005-02-11	JTS, RTi		Added support for Structure view objects
//					that are returned from stored procedures
// 2005-03-24	JTS, RTi		Completed support for structure view
//					objects.
// 2005-05-09	JTS, RTi		All structure queries now return
//					structure view objects.
// 2005-07-07	JTS, RTi		* Organized the GUI components.
//					* Added UTM values.
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JGUIUtil;

import RTi.Util.IO.PrintJGUI;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

/**
GUI for displaying location information for structures and stations.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_Location 
extends JFrame 
implements ActionListener, WindowListener {

/**
Button labels.
*/
private final String
	__BUTTON_CLOSE = "Close",
	__BUTTON_EXPORT = "Export",
	__BUTTON_HELP = "Help",
	__BUTTON_PRINT = "Print";

/**
Whether the currently-displayed object is a structure or not.
*/
private boolean __isStructure;

/**
The DMI used to communicate with the database.
*/
private HydroBaseDMI __dmi;

/**
The HydroBase_StationGeoloc object to display the data of, if a station 
is to be displayed in the gui.
*/
private	HydroBase_StationGeoloc __station;

/**
The HydroBase_StructureView object to display the data of, if a structure
is to be displayed in the gui.
*/
private	HydroBase_StructureView __structureView;

/**
GUI buttons.
*/
private JButton
	__closeJButton,
	__exportJButton,
	__printJButton;

/**
Textfields for displaying data.
*/
private JTextField
	__countyJTextField,
	__divJTextField,
	__ewCoordJTextField,
	__hydroCodeJTextField,
	__idJTextField,
	__latitudeJTextField,
	__longitudeJTextField,
	__nsCoordJTextField,
	__pmJTextField,
	__q10JTextField,
	__q40JTextField,
	__q160JTextField,
	__rngJTextField,
	__sec1JTextField,
	__sec2JTextField,
	__stateJTextField,
	__statusJTextField,
	__streamMileJTextField,
	__streamNameJTextField,
	__structureTypeJTextField,
	__structureNameJTextField,
	__topoMapJTextField,
	__twnJTextField,
	__utmXJTextField,
	__utmYJTextField,
	__wdJTextField;

/**
Constructor.
@param dmi the dmi with which to communicate to the database.
@param structure the HydroBase_StructureView object to display in the GUI.
*/
public HydroBase_GUI_Location(HydroBaseDMI dmi, 
HydroBase_StructureView structureView) {
        __dmi = dmi;
	__structureView = structureView;
	__isStructure = true;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
        setupGUI();
	
	displayStructureLocation(__structureView);

	initialize();
}

/**
Constructor.
@param dmi the dmi with which to communicate with the database.
@param station the HydroBase_StationGeoloc object to display in the GUI.
*/
public HydroBase_GUI_Location(HydroBaseDMI dmi, 
HydroBase_StationGeoloc station) {
	__dmi = dmi;
	__station = station;
	__isStructure = false;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
        setupGUI();

	displayStationLocation(__station);
}

/**
Responds to action events.
@param event the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent event) {
	String routine = "HydroBase_GUI_Location.actionPerformed";
        String actionCommand = event.getActionCommand();

	if (actionCommand.equals(__BUTTON_CLOSE)) {
		closeClicked();
        }
        else if (actionCommand.equals(__BUTTON_EXPORT)) {
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
			List<String> outputStrings = formatOutput(format);
 			// Now export, letting the user decide the file...
			HydroBase_GUI_Util.export(this, eff[0], outputStrings);
		} 
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}		
        }
        else if (actionCommand.equals(__BUTTON_HELP)) {
        }
        else if (actionCommand.equals(__BUTTON_PRINT)) {
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
Closes the GUI.  
*/
private void closeClicked() {
	dispose();
}

/**
Displays station location data.
@param station the station to display the data of.
*/
private void displayStationLocation(HydroBase_StationGeoloc station) {
	JGUIUtil.setWaitCursor(this, true);
	__statusJTextField.setText("Retrieving Data...");

        int curInt = station.getWD();
        if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                __wdJTextField.setText("" + curInt);
        }
  
        __idJTextField.setText(station.getStation_id());            

        curInt = station.getDiv();
        if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                __divJTextField.setText("" + curInt);                        
        }

	// do not have access to this data from HydroBase_StationGeoloc
	//__streamNameJTextField.setText(station.getStreamName());

        double curDouble = station.getStr_mile();
        if (!DMIUtil.isMissing(curDouble) && !HydroBase_Util.isMissing(curDouble)) {
                __streamMileJTextField.setText("" + StringUtil.formatString(curDouble, "%7.2f").trim()); 
        }

	if (__dmi.isDatabaseVersionAtLeast(HydroBaseDMI.VERSION_19990305)) {
		// Need to handle separate fields...
		int ew = station.getCoordsew();
		if (DMIUtil.isMissing(ew) || HydroBase_Util.isMissing(ew)) {
        		__ewCoordJTextField.setText("");
		}
		else {	
			__ewCoordJTextField.setText("" + station.getCoordsew() + station.getCoordsew_dir());
		}
		int ns = station.getCoordsns();
		if (DMIUtil.isMissing(ns) || HydroBase_Util.isMissing(curInt)) {
        		__nsCoordJTextField.setText("");
		}
		else {	
			__nsCoordJTextField.setText("" + station.getCoordsns() + station.getCoordsns_dir());
		}
	}
	else {	
		// Was never any station available...
        	__ewCoordJTextField.setText("");
        	__nsCoordJTextField.setText("");
	}
        __countyJTextField.setText("" + station.getCounty());           
        __hydroCodeJTextField.setText("" + station.getHUC());

        curDouble = station.getLatdecdeg();
        if (!DMIUtil.isMissing(curDouble) && !HydroBase_Util.isMissing(curDouble)) {
                __latitudeJTextField.setText(StringUtil.formatString( curDouble, "%10.6f").trim());
        }
 
        curDouble = station.getLongdecdeg();
        if (!DMIUtil.isMissing(curDouble) && !HydroBase_Util.isMissing(curDouble)) {
                __longitudeJTextField.setText(StringUtil.formatString( curDouble, "%10.6f").trim());
        }

	if (!DMIUtil.isMissing(station.getUtm_x()) && !HydroBase_Util.isMissing(station.getUtm_x())) {
		__utmXJTextField.setText(StringUtil.formatString( station.getUtm_x(), "%10.6f").trim());
	}
	if (!DMIUtil.isMissing(station.getUtm_y()) && !HydroBase_Util.isMissing(station.getUtm_y())) {
		__utmYJTextField.setText(StringUtil.formatString( station.getUtm_y(), "%10.6f").trim());
	}

        __structureTypeJTextField.setText(__dmi.getLocationTypeDescription(
		station.getLoc_type()));
        __pmJTextField.setText("" + station.getPM());
        __q160JTextField.setText("" + station.getQ160());
        __q40JTextField.setText("" + station.getQ40());
        __q10JTextField.setText("" + station.getQ10());

        curInt = station.getRng();
        if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                __rngJTextField.setText("" + curInt);
        }
	__ewCoordJTextField.setText(station.getRdir());

        curInt = station.getSec();
        if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                __sec1JTextField.setText("" + curInt);
        }
        __sec2JTextField.setText("" + station.getSeca());

        __stateJTextField.setText("" + station.getST());
        __topoMapJTextField.setText("" + station.getTopomap());

        curInt = station.getTS();
        if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                __twnJTextField.setText("" + curInt);
        }
	__nsCoordJTextField.setText(station.getTdir());

	JGUIUtil.setWaitCursor(this, false);
	__statusJTextField.setText("Ready");
}

/**
Displays structure view location data.
@param structure the structure location data to display.
*/
private void displayStructureLocation(HydroBase_StructureView structureView) {
	JGUIUtil.setWaitCursor(this, true);
	__statusJTextField.setText("Retrieving Data...");

        int curInt = structureView.getWD();
        if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                __wdJTextField.setText("" + curInt);
        }
 
        curInt = structureView.getID();
        if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                __idJTextField.setText("" + curInt);            
        }

        curInt = structureView.getDiv();
        if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                __divJTextField.setText("" + curInt);                        
        }

	__streamNameJTextField.setText(structureView.getStrname());

        double curDouble = structureView.getStr_mile();
        if (!DMIUtil.isMissing(curDouble) && !HydroBase_Util.isMissing(curDouble)) {
                __streamMileJTextField.setText("" + StringUtil.formatString(curDouble, "%7.2f").trim()); 
        }

	if (__dmi.isDatabaseVersionAtLeast(HydroBaseDMI.VERSION_19990305)) {
		// Need to handle separate fields...
		int ew = structureView.getCoordsew();
		if (DMIUtil.isMissing(ew) || HydroBase_Util.isMissing(ew)) {
        		__ewCoordJTextField.setText("");
		}
		else {	
			__ewCoordJTextField.setText("" + structureView.getCoordsew() + structureView.getCoordsew_dir());
		}
		int ns = structureView.getCoordsns();
		if (DMIUtil.isMissing(ns) || HydroBase_Util.isMissing(ns)) {
        		__nsCoordJTextField.setText("");
		}
		else {	
			__nsCoordJTextField.setText("" + structureView.getCoordsns() + structureView.getCoordsns_dir());
		}
	}

	else {	// Was never any structureView available...
        	__ewCoordJTextField.setText("");
        	__nsCoordJTextField.setText("");
	}
        __countyJTextField.setText("" + structureView.getCounty());           
        __hydroCodeJTextField.setText("" + structureView.getHUC());

        curDouble = structureView.getLatdecdeg();
        if (!DMIUtil.isMissing(curDouble) && !HydroBase_Util.isMissing(curDouble)) {
                __latitudeJTextField.setText(StringUtil.formatString(curDouble, "%10.6f").trim());
        }
 
        curDouble = structureView.getLongdecdeg();
        if (!DMIUtil.isMissing(curDouble) && !HydroBase_Util.isMissing(curDouble)) {
                __longitudeJTextField.setText(StringUtil.formatString(curDouble, "%10.6f").trim());
        }

	if (!DMIUtil.isMissing(structureView.getUtm_x()) && !HydroBase_Util.isMissing(structureView.getUtm_x())) {
		__utmXJTextField.setText(StringUtil.formatString( structureView.getUtm_x(), "%10.6f").trim());
	}
	if (!DMIUtil.isMissing(structureView.getUtm_y()) && !HydroBase_Util.isMissing(structureView.getUtm_y())) {
		__utmYJTextField.setText(StringUtil.formatString( structureView.getUtm_y(), "%10.6f").trim());
	}

        __structureTypeJTextField.setText(
		__dmi.getLocationTypeDescription(structureView.getLoc_type()));
        __pmJTextField.setText("" + structureView.getPM());
        __q160JTextField.setText("" + structureView.getQ160());
        __q40JTextField.setText("" + structureView.getQ40());
        __q10JTextField.setText("" + structureView.getQ10());

        curInt = structureView.getRng();
        if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                __rngJTextField.setText("" + curInt);
        }
	__ewCoordJTextField.setText(structureView.getRdir());

        curInt = structureView.getSec();
        if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                __sec1JTextField.setText("" + curInt);
        }
        __sec2JTextField.setText("" + structureView.getSeca());

        __stateJTextField.setText("" + structureView.getST());
        __topoMapJTextField.setText("" + structureView.getTopomap());

        curInt = structureView.getTS();
        if (!DMIUtil.isMissing(curInt) && !HydroBase_Util.isMissing(curInt)) {
                __twnJTextField.setText("" + curInt);
        }
	__nsCoordJTextField.setText(structureView.getTdir());

	JGUIUtil.setWaitCursor(this, false);
	__statusJTextField.setText("Ready");
}

/**
Responsible for formatting output.
@param format the format in which to format the output.
*/
public List<String> formatOutput(int format) {
	List<String> v = new Vector<String>();

	if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
                String ns = "";
                String ew = "";
                if (__nsCoordJTextField.getText().length() > 0) {
                        ns = __nsCoordJTextField.getText() + " NS ";
                }
                if (__ewCoordJTextField.getText().length() > 0) {
                        ew = __ewCoordJTextField.getText().length() + " EW";
                }
                v.add(HydroBase_GUI_Util.formatStructureHeader(
                        __structureNameJTextField.getText(),
                        __divJTextField.getText(),
                        __wdJTextField.getText(),
                        __idJTextField.getText(), format));
                v.add("");
                v.add("   STATE: " 
			+ StringUtil.formatString(__stateJTextField.getText(), 
			"%-7.7s") + "            COUNTY: " 
			+ __countyJTextField.getText());
                v.add("  STREAM: " +__streamNameJTextField.getText());
                v.add("  STREAM MILE: " 
			+ __streamMileJTextField.getText());
                v.add("LOCATION: TOWNSHIP " + __twnJTextField.getText() 
			+ __nsCoordJTextField.getText()
			+ " RANGE " + __rngJTextField.getText() 
			+ __ewCoordJTextField.getText()
			+ " SECTION " + __sec1JTextField.getText() 
			+ __sec2JTextField.getText() + " " 
			+ __q160JTextField.getText() + " " 
			+ __q40JTextField.getText() + " " 
			+ __q10JTextField.getText() + " " + ns + ew);
                v.add("LATITUDE: " 
			+StringUtil.formatString(__latitudeJTextField.getText(),
                	"%-16.16s") + " LONGITUDE: " 
			+ __longitudeJTextField.getText());
		v.add("UTM X: "
			+ StringUtil.formatString(__utmXJTextField.getText(),
			"%-16.16s") + " UTM Y: "
			+ __utmYJTextField.getText());
        }
        else {  
		char delim = HydroBase_GUI_Util.getDelimiterForFormat(format); 
                v.add(HydroBase_GUI_Util.formatStructureHeader(format));
                v.add(HydroBase_GUI_Util.formatStructureHeader(
                        __structureNameJTextField.getText(),
                        __divJTextField.getText(),
                        __wdJTextField.getText(),
                        __idJTextField.getText(), format));
                v.add("");
                v.add("STATE" + delim + "COUNTY" + delim + "STREAM" 
			+ delim + "STREAM MILE" + delim + "PM" + delim + "TS" 
			+ delim + "RNG" + delim + "SEC" + delim + "HALFSEC" 
			+ delim + "1/4" + delim + "1/4 1/4" + delim 
			+ "1/4 1/4 1/4"
			+ delim + "NS" + delim + "EW" + delim + "LAT" + delim 
			+ "LON" + delim + "UTMX" + delim + "UTMY" + delim);
                v.add(__stateJTextField.getText() + delim 
			+ __countyJTextField.getText() + delim 
			+ __streamNameJTextField.getText() + delim 
			+ __streamMileJTextField.getText() + delim 
			+ __pmJTextField.getText() + delim 
			+ __twnJTextField.getText() 
			+ __nsCoordJTextField.getText() + delim 
			+ __rngJTextField.getText() 
			+ __ewCoordJTextField.getText() + delim 
			+ __sec1JTextField.getText() + delim 
			+ __sec2JTextField.getText() + delim 
			+ __q160JTextField.getText() + delim 
			+ __q40JTextField.getText() + delim 
			+ __q10JTextField.getText() + delim 
			+ __nsCoordJTextField.getText() + delim 
			+ __ewCoordJTextField.getText() + delim 
			+ __latitudeJTextField.getText() + delim 
			+ __longitudeJTextField.getText() + delim
			+ __utmXJTextField.getText() + delim
			+ __utmYJTextField.getText() + delim);
        }       
        return v;
}

/**
Initializes some settings for the GUI.
*/
private void initialize() {
	int wd = (new Integer(__wdJTextField.getText().trim())).intValue();
	int id = (new Integer(__idJTextField.getText().trim())).intValue();
	String name = __structureNameJTextField.getText().trim();

	String rest = "Structure Data - Location Data - "
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
Sets up the GUI.
*/
private void setupGUI() {
	addWindowListener(this);
	
        Insets NLNR = new Insets(0,7,0,7);
        Insets TLNR = new Insets(7,7,0,7);
        Insets NLBR = new Insets(0,7,7,7);
        Insets NLBN = new Insets(0,7,7,0);
        Insets NLNN = new Insets(0,7,0,0);       

	GridBagLayout gbl = new GridBagLayout();
        
	JPanel bigPanel = new JPanel();
	bigPanel.setLayout(new GridBagLayout());

        // Top: West JPanel
        JPanel topLeftJPanel = new JPanel();
        topLeftJPanel.setLayout(gbl);

	JGUIUtil.addComponent(bigPanel, topLeftJPanel,
		0, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);

	if (__isStructure) {
	        JGUIUtil.addComponent(topLeftJPanel, 
			new JLabel("Structure Name:"), 
			0, 0, 1, 1, 0, 0, TLNR, 
			GridBagConstraints.NONE, GridBagConstraints.WEST);
	}
	else {
	        JGUIUtil.addComponent(topLeftJPanel, 
			new JLabel("Station Name:"), 
			0, 0, 1, 1, 0, 0, TLNR, 
			GridBagConstraints.NONE, GridBagConstraints.WEST);
	}

        JGUIUtil.addComponent(topLeftJPanel, new JLabel("DIV:"), 
		1, 0, 1, 1, 0, 0, TLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(topLeftJPanel, new JLabel("WD:"), 
		2, 0, 1, 1, 0, 0, TLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

	JLabel id = null;
	if (__isStructure) {
		id = new JLabel("ID:");
	}
	else {
		id = new JLabel("Station ID:");
	}

        JGUIUtil.addComponent(topLeftJPanel, id, 
		3, 0, 1, 1, 0, 0, TLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        __structureNameJTextField = new JTextField();
	if (__isStructure) {
		__structureNameJTextField.setText(" " 
			+ __structureView.getStr_name() + "     ");
	}
	else {
		__structureNameJTextField.setText(" " 
			+ __station.getStation_name() + "     ");
	}
        __structureNameJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel, __structureNameJTextField, 
		0, 1, 1, 1, 1, 0, NLBR, 
		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __divJTextField = new JTextField(3);
        __divJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel,__divJTextField, 
		1, 1, 1, 1, 0, 0, NLBR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        __wdJTextField = new JTextField(3);
        __wdJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel, __wdJTextField, 
		2, 1, 1, 1, 0, 0, NLBR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        __idJTextField = new JTextField(10);
        __idJTextField.setEditable(false);
        JGUIUtil.addComponent(topLeftJPanel, __idJTextField, 
		3, 1, 1, 1, 0, 0, NLBR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);
		
        // Top: South: West JPanel
        JPanel topSWJPanel = new JPanel();
        topSWJPanel.setLayout(gbl);

	JGUIUtil.addComponent(bigPanel, topSWJPanel,
		0, 1, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(topSWJPanel, new JLabel("State:"), 
		0, 2, 1, 1, 0, 0, NLNN, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(topSWJPanel, new JLabel("County:"), 
		1, 2, 1, 1, 0, 0, NLNN, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);
		
        JGUIUtil.addComponent(topSWJPanel, new JLabel("Location Type:"), 
		2, 2, 1, 1, 0, 0, NLNN, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

	JGUIUtil.addComponent(topSWJPanel, new JLabel("Stream Name:"),
		3, 2, 1, 1, 0, 0, NLNN, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(topSWJPanel, new JLabel("Stream Mile:"), 
		4, 2, 1, 1, 0, 0, NLNN, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        __stateJTextField = new JTextField(10);
        __stateJTextField.setEditable(false);
        JGUIUtil.addComponent(topSWJPanel, __stateJTextField, 
		0, 3, 1, 1, 0, 0, NLBN, 
		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __countyJTextField = new JTextField(10);
        __countyJTextField.setEditable(false);
        JGUIUtil.addComponent(topSWJPanel, __countyJTextField, 
		1, 3, 1, 1, 0, 0, NLBN, 
		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);  

        __structureTypeJTextField = new JTextField(20);
        __structureTypeJTextField.setEditable(false);
        JGUIUtil.addComponent(topSWJPanel, __structureTypeJTextField, 
		2, 3, 1, 1, 0, 0, NLBN, 
		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__streamNameJTextField = new JTextField(20);
	__streamNameJTextField.setEditable(false);
	JGUIUtil.addComponent(topSWJPanel, __streamNameJTextField, 
		3, 3, 1, 1, 0, 0, NLBN, 
		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __streamMileJTextField = new JTextField(20);
        __streamMileJTextField.setEditable(false);
        JGUIUtil.addComponent(topSWJPanel, __streamMileJTextField, 
		4, 3, 1, 1, 0, 0, NLBR, 
		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // CenterTop: West JPanel
        JPanel centerTopWestJPanel = new JPanel();
        centerTopWestJPanel.setLayout(gbl);

	JGUIUtil.addComponent(bigPanel, centerTopWestJPanel,
		0, 2, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerTopWestJPanel, new JLabel("COORDINATES:"),
		0, 0, 2, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.CENTER);

        JGUIUtil.addComponent(centerTopWestJPanel, new JLabel("PM:"), 
		0, 1, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerTopWestJPanel, new JLabel("TWN:"), 
		1, 1, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerTopWestJPanel, new JLabel("RNG:"), 
		3, 1, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerTopWestJPanel, new JLabel("SEC:"), 
		5, 1, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerTopWestJPanel, new JLabel("1/4:"), 
		7, 1, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerTopWestJPanel, new JLabel("1/4 1/4:"), 
		8, 1, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerTopWestJPanel, new JLabel("1/4 1/4 1/4:"), 
		9, 1, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);
        
        __pmJTextField = new JTextField(3);
        __pmJTextField.setEditable(false);
        JGUIUtil.addComponent(centerTopWestJPanel, __pmJTextField, 
		0, 2, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);
	
        __twnJTextField = new JTextField(3);
        __twnJTextField.setEditable(false);
        JGUIUtil.addComponent(centerTopWestJPanel, __twnJTextField, 
		1, 2, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);                        

        __nsCoordJTextField = new JTextField(2);
        __nsCoordJTextField.setEditable(false);
        JGUIUtil.addComponent(centerTopWestJPanel, __nsCoordJTextField, 
		2, 2, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);
		
        __rngJTextField = new JTextField(3);
        __rngJTextField.setEditable(false);
        JGUIUtil.addComponent(centerTopWestJPanel, __rngJTextField, 
		3, 2, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);                        

	// 4 digits plus direction...
        __ewCoordJTextField = new JTextField(2);
        __ewCoordJTextField.setEditable(false);
        JGUIUtil.addComponent(centerTopWestJPanel, __ewCoordJTextField, 
		4, 2, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);                                 

        __sec1JTextField = new JTextField(3);
        __sec1JTextField.setEditable(false);
        JGUIUtil.addComponent(centerTopWestJPanel, __sec1JTextField, 
		5, 2, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);                        
                      
        __sec2JTextField = new JTextField(3);
        __sec2JTextField.setEditable(false);
        JGUIUtil.addComponent(centerTopWestJPanel, __sec2JTextField, 
		6, 2, 1, 1, 0, 0, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        __q160JTextField = new JTextField(3);
        __q160JTextField.setEditable(false);
        JGUIUtil.addComponent(centerTopWestJPanel, __q160JTextField, 
		7, 2, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);                        

        __q40JTextField = new JTextField(3);
        __q40JTextField.setEditable(false);
        JGUIUtil.addComponent(centerTopWestJPanel, __q40JTextField, 
		8, 2, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        __q10JTextField = new JTextField(3);
        __q10JTextField.setEditable(false);
        JGUIUtil.addComponent(centerTopWestJPanel, __q10JTextField, 
		9, 2, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

	JPanel fourthPanel = new JPanel();
	fourthPanel.setLayout(new GridBagLayout());

	JGUIUtil.addComponent(bigPanel, fourthPanel,
		0, 3, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(fourthPanel, new JLabel("Latitude:"), 
		0, 3, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);		
        JGUIUtil.addComponent(fourthPanel, new JLabel("Longitude:"), 
		1, 3, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(fourthPanel, new JLabel("UTM X:"), 
		2, 3, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(fourthPanel, new JLabel("UTM Y:"), 
		3, 3, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);		
        JGUIUtil.addComponent(fourthPanel, new JLabel("Topographic Map:"),
		4, 3, 1, 1, 0, 0, NLNR, 
		 GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(fourthPanel, new JLabel("Hydrologic Unit Code:"), 
		5, 3, 1, 1, 0, 0, NLNR, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);
		
        __latitudeJTextField = new JTextField(10);
        __latitudeJTextField.setEditable(false);
        JGUIUtil.addComponent(fourthPanel, __latitudeJTextField, 
		0, 10, 1, 1, 0, 0, NLBR, 
		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __longitudeJTextField = new JTextField(10);
        __longitudeJTextField.setEditable(false);
        JGUIUtil.addComponent(fourthPanel, __longitudeJTextField, 
		1, 10, 1, 1, 0, 0, NLBR, 
		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __utmXJTextField = new JTextField(11);
        __utmXJTextField.setEditable(false);
        JGUIUtil.addComponent(fourthPanel, __utmXJTextField, 
		2, 10, 1, 1, 0, 0, NLBR, 
		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __utmYJTextField = new JTextField(11);
        __utmYJTextField.setEditable(false);
        JGUIUtil.addComponent(fourthPanel, __utmYJTextField, 
		3, 10, 1, 1, 0, 0, NLBR, 
		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __topoMapJTextField = new JTextField();
        __topoMapJTextField.setEditable(false);
        JGUIUtil.addComponent(fourthPanel, __topoMapJTextField, 
		4, 10, 1, 1, 0, 0, NLBR, 
		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __hydroCodeJTextField = new JTextField();
        __hydroCodeJTextField.setEditable(false);
        JGUIUtil.addComponent(fourthPanel, __hydroCodeJTextField, 
		5, 10, 1, 1, 0, 0, NLBR, 
		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	getContentPane().add("Center", bigPanel);

        // Bottom JPanel(consist of two more panels)
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);

        // Bottom: North JPanel
        JPanel bottomNorthJPanel = new JPanel();
        bottomNorthJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add("North", bottomNorthJPanel);

        __printJButton = new JButton(__BUTTON_PRINT);
        __printJButton.setEnabled(true);
	__printJButton.addActionListener(this);
	__printJButton.setToolTipText("Print form data.");
        bottomNorthJPanel.add(__printJButton);

        __exportJButton = new JButton(__BUTTON_EXPORT);
        __exportJButton.setEnabled(true);
	__exportJButton.addActionListener(this);
	__exportJButton.setToolTipText("Export data to a file.");
        bottomNorthJPanel.add(__exportJButton);

        __closeJButton = new JButton(__BUTTON_CLOSE);
	__closeJButton.addActionListener(this);
	__closeJButton.setToolTipText("Close the form.");
        bottomNorthJPanel.add(__closeJButton);

//        __helpJButton = new JButton(__BUTTON_HELP);
//	__helpJButton.setEnabled(false);
//	__helpJButton.addActionListener(this);
//        bottomNorthJPanel.add(__helpJButton);                

        // Bottom: South JPanel
        JPanel bottomSouthJPanel = new JPanel();
        bottomSouthJPanel.setLayout(gbl);
        bottomJPanel.add("South", bottomSouthJPanel);

        JPanel bottomSouthSouth_fillerJPanel = new JPanel();
        JGUIUtil.addComponent(bottomSouthJPanel, bottomSouthSouth_fillerJPanel, 
		0, 0, 1, 1, 0, 0, 
		GridBagConstraints.NONE, GridBagConstraints.WEST);

        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomSouthJPanel, __statusJTextField, 
		0, 1, 10, 1, 1, 0, 
		GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        setTitle("Location Data");
        pack();
        JGUIUtil.center(this);
        setVisible(true);     
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
