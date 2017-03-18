//-----------------------------------------------------------------------------
// HydroBase_GUI_Dam - Dam Data GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 19 Aug 1997	DLG, RTi		Created initial version.
// 07 Dec 1997	SAM, RTi		Enable export and print.
// 29 Apr 1998  DLG, RTi		Updated to 1.1 event model, added
//					javadoc comments.
// 04 Apr 1999	Steven A. Malers, RTi	Added HBDMI to queries.
//					Remove import *.
// 2002-02-25	SAM, RTi		Increase width of ID field.
//-----------------------------------------------------------------------------
// 2003-10-03	J. Thomas Sapienza, RTi	Initial Swing version.
// 2003-10-06	JTS, RTi		Completed work on initial Swing version.
// 2004-01-20	JTS, RTi		Began using the JScrollWorksheet in
//					order to use worksheet row headers.
// 2004-07-26	JTS, RTi		Changed "Emergency" tab to 
//					"Emergency Plan."
// 2005-02-14	JTS, RTi		Checked all dmi calls to make sure they
//					use stored procedures.
// 2005-06-22	JTS, RTi		* Column widths now come from the 
//					  table model, not the cell renderer.
//					* The table-specific cell renderers 
//					  were removed and replaced with a 
//					  single generic one.
// 2007-02-26	SAM, RTi		Clean up code baed on Eclipse feedback.
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.JScrollWorksheet;
import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.SimpleJButton;

import RTi.Util.IO.PrintJGUI;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

/**
This class is a GUI for displaying all sorts of Dam information.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_Dam extends JFrame
implements ActionListener, WindowListener {

/**
Button and tab labels.
*/
private final String	
	__BUTTON_CLOSE = 		"Close",
	__BUTTON_EXPORT = 		"Export",
	__BUTTON_PRINT = 		"Print",
	__TAB_EMERGENCY = 		"Emergency Plan",
	__TAB_INSPECTION = 		"Inspection",
	__TAB_OUTLET = 			"Outlet",
	__TAB_SPILLWAY = 		"Spillway";

/**
The DMI used to communicate with the database.
*/
private HydroBaseDMI __dmi;

/**
The number of the dam structure for which data is displayed.
*/
private int __structureNum;

/**
GUI textfields.
*/
private JTextField	
	__damAKAJTextField,
	__damTypeJTextField,
	__divJTextField,
	__distanceJTextField,
	__elevationJTextField,
	__federalLandJTextField,
	__forestIDJTextField,
	__hazardClassJTextField,
	__hydraulicJTextField,
	__idJTextField,
	__jurisdictionalJTextField,
	__lengthJTextField,
	__nabCodeJTextField,
	__nameJTextField,
	__nationalIDJTextField,
	__outletJTextField,
	__purposeJTextField,
	__regulationsJTextField,
	__remarksJTextField,
	__spillwayJTextField,
	__stateDamIDJTextField,
	__statusJTextField,
	__streamCodeJTextField,
	__structuralJTextField,
	__structureJTextField,
	__wdJTextField,
	__widthJTextField,
	__yearJTextField;

/**
Worksheets for displaying additional dam information.
*/
private JWorksheet 
	__emergencyWorksheet,
	__inspectionWorksheet,
	__outletWorksheet,
	__spillwayWorksheet;

/**
The name of the dam structure for which data is displayed.
*/
private String __structureName;

/**
Constructor.
@param dmi the dmi to use for communicating with the database.
@param structureName the name of the dam structure to display.
@param structureNum the number of the dam structure to display.
*/
public HydroBase_GUI_Dam(HydroBaseDMI dmi, String structureName, 
int structureNum) {
	__dmi = dmi;
        __structureNum = structureNum;
        __structureName = structureName;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
        setupGUI();
 
	submitAndDisplaySpillwayQuery();
	submitAndDisplayInspectionQuery();
        submitAndDisplayDamQuery();    
	submitAndDisplayOutletQuery();
	submitAndDisplayEmergencyPlanQuery();

	int wd = (new Integer(__wdJTextField.getText().trim())).intValue();
	int id = (new Integer(__idJTextField.getText().trim())).intValue();
	String name = __structureName;

	String rest = "Structure Data - Jurisdictional Dam Detail - "
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
@param evt the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent evt) {
	String routine = "HydroBase_GUI_Dam.actionPerformed";
	String s = evt.getActionCommand();

        if (s.equals("Close")) {
		closeClicked();
        }
	else if (s.equals("Export")) {
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
        else if (s.equals("Help")) {
	}
	else if (s.equals("Print")) {
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
			PrintJGUI.print(this, outputStrings, 8);
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
	setVisible(false);
	dispose();
}

/**
Converts an aband code into a full text description.
*/
private String convertAbandCode(String abandCode) {
	String fullName = null;
        abandCode = abandCode.trim();

        if (abandCode.equalsIgnoreCase("N")) {
                fullName = "Non-Jurisdictional";
        }
        else if (abandCode.equalsIgnoreCase("A")) {
                fullName = "Abandoned";
        }
        else if (abandCode.equalsIgnoreCase("B")) {
                fullName = "Breached";
        }
        else if (abandCode.equalsIgnoreCase("E")) {
                fullName = "Exempt";
        }
        else {
                fullName = abandCode;
        }
 
        return fullName; 
}

/**
Cleans up member variables.
*/
public void finalize() 
throws Throwable {
	__dmi = null;
	__damAKAJTextField = null;
	__damTypeJTextField = null;
	__divJTextField = null;
	__distanceJTextField = null;
	__elevationJTextField = null;
	__federalLandJTextField = null;
	__forestIDJTextField = null;
	__hazardClassJTextField = null;
	__hydraulicJTextField = null;
	__idJTextField = null;
	__jurisdictionalJTextField = null;
	__lengthJTextField = null;
	__nabCodeJTextField = null;
	__nameJTextField = null;
	__nationalIDJTextField = null;
	__outletJTextField = null;
	__purposeJTextField = null;
	__regulationsJTextField = null;
	__remarksJTextField = null;
	__spillwayJTextField = null;
	__stateDamIDJTextField = null;
	__statusJTextField = null;
	__streamCodeJTextField = null;
	__structuralJTextField = null;
	__structureJTextField = null;
	__wdJTextField = null;
	__widthJTextField = null;
	__yearJTextField = null;
	__emergencyWorksheet = null;
	__inspectionWorksheet = null;
	__outletWorksheet = null;
	__spillwayWorksheet = null;
	__structureName = null;
	super.finalize();
}

/**
Formats output for printing or export.
@param format the format in which to format the data for output.
@return a list of strings, each of which is a line in the report to be
exported or printed.
*/
public List<String> formatOutput(int format) {
	List<String> v = new Vector<String>();
	int spillwaySize = __spillwayWorksheet.getRowCount();
	int inspectionSize = __inspectionWorksheet.getRowCount();
	int outletSize = __outletWorksheet.getRowCount();
	int emergencySize = __emergencyWorksheet.getRowCount();

	String s0, s3, s4, s7, s8, s9, s10, s14, s15, s16, s17;
	Integer i1;
	String s1;
	Double d2, d5, d6, d11, d12, d13;
	String s2, s5, s6, s11, s12, s13;

	if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			HydroBase_GUI_Util.trimText(__structureJTextField),
			HydroBase_GUI_Util.trimText(__divJTextField),
			HydroBase_GUI_Util.trimText(__wdJTextField),
			HydroBase_GUI_Util.trimText(__idJTextField), format));
		v.add("");
		v.add("                                          "
			+ "        DAM INFORMATION");
		v.add("DAM AKA:       "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__damAKAJTextField),
			"%-59.59s") + "STATE DAM ID:          "
			+ HydroBase_GUI_Util.trimText(__stateDamIDJTextField));
		v.add("HAZARD CLASS:  "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__hazardClassJTextField),
			"%-23.23s") + "NAB CODE:        "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__nabCodeJTextField), 
			"%-19.19s") + "NATIONAL INVENTORY ID: "
			+ HydroBase_GUI_Util.trimText(__nationalIDJTextField));
		v.add("NEAREST DOWNSTREAM                         "
			+ "                               FOREST SERVICE "
			+ "ID:     "
			+ HydroBase_GUI_Util.trimText(__forestIDJTextField));
		v.add("TOWN NAME:     " + StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__nameJTextField),
			"%-23.23s") + "DISTANCE:        "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__distanceJTextField), 
			"%-19.19s") + "FEDERAL LAND:          "
			+ HydroBase_GUI_Util.trimText(__federalLandJTextField));
		v.add("                                      "
			+ "STREAM CODE:     " + StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__streamCodeJTextField), 
			"%-19.19s") + "FEDERAL REGULATIONS:   "
			+ HydroBase_GUI_Util.trimText(__regulationsJTextField));
		v.add("");
		v.add("SPILLWAY CAPACITY: " + StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__spillwayJTextField),
			"%-19.19s") + "CREST ELEVATION: "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__elevationJTextField), 
			"%-19.19s") + "JURISDICTIONAL HEIGHT: "
			+ HydroBase_GUI_Util.trimText(
			__jurisdictionalJTextField));
		v.add("OUTLET CAPACITY:   " + StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__outletJTextField),
			"%-19.19s") + "CREST LENGTH:    "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__lengthJTextField), 
			"%-19.19s") + "HYDRAULIC HEIGHT:      "
			+ HydroBase_GUI_Util.trimText(__hydraulicJTextField));
		v.add("                                      "
			+ "CREST WIDTH:     "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__widthJTextField), 
			"%-19.19s") + "STRUCTURAL HEIGHT:     "
			+ HydroBase_GUI_Util.trimText(__structuralJTextField));
		v.add("DAM TYPE:          " + StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__damTypeJTextField),
			"%-19.19s") + "PURPOSE:         "
			+ StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__purposeJTextField), 
			"%-19.19s") + "YEAR COMPLETE:         "
			+ HydroBase_GUI_Util.trimText(__yearJTextField));
		v.add("REMARKS:           "
			+ HydroBase_GUI_Util.trimText(__remarksJTextField));

		if (spillwaySize > 0) {
			v.add("");
			v.add("                                     "
				+ "           SPILLWAY INFORMATION");
			v.add("                                     "
				+ "                                        "
				+ "    WALL SIDE");
			v.add("NAME                CAPACITY  "
				+ "       FREEBOARD              FLOW"
				+ " CODE         TYPE SLOPE   WIDTH");
			v.add("______________________________________"
				+ "_________________________________________"
				+ "_________________________________________");
			// Now do the list...
			for (int i = 0; i < spillwaySize; i++) {
				s0 = (String)__spillwayWorksheet.getValueAt(
					i, 0);
				if (DMIUtil.isMissing(s0)) {
					s0 = "";
				}
				i1 = (Integer)__spillwayWorksheet.getValueAt(
					i, 1);
				if (DMIUtil.isMissing(i1)) {
					s1 = StringUtil.formatString("", 
						"%17.17s");
				}
				else {
					s1 = StringUtil.formatString(
						i1.toString(), "%17d");
				}
				d2 = (Double)__spillwayWorksheet.getValueAt(
					i, 2);
				if (DMIUtil.isMissing(d2)) {
					s2 = StringUtil.formatString("", 
						"%12.12s");
				}
				else {
					s2 = StringUtil.formatString(
						d2.toString(),"%12.1f");
				}
				s3 = (String)__spillwayWorksheet.getValueAt(
					i, 3);
				if (DMIUtil.isMissing(s3)) {
					s3 = "";
				}
				s4 = (String)__spillwayWorksheet.getValueAt(
					i, 4);
				if (DMIUtil.isMissing(s4)) {
					s4 = "";
				}
				d5 = (Double)__spillwayWorksheet.getValueAt(
					i, 5);
				if (DMIUtil.isMissing(d5)) {
					s5 = StringUtil.formatString("",
						"%8.8s");
				}
				else {
					s5 = StringUtil.formatString(
						d5.toString(), "%8.1f");
				}				
				d6 = (Double)__spillwayWorksheet.getValueAt(
					i, 6);
				if (DMIUtil.isMissing(d6)) {
					s6 = StringUtil.formatString(
						"", "%16.16s");
				}
				else {
					s6 = StringUtil.formatString(
						d6.toString(), "%16.1f");
				}
				v.add(
					StringUtil.formatString(
					s0.trim(),
					"%-20.20s")
					+ StringUtil.formatString(
					s1.trim(),
					"%-17.17s")
					+ StringUtil.formatString(
					s2.trim(),
					"%-12.12s")
					+ "           "
					+ StringUtil.formatString(
					s3.trim(),
					"%-5.5s") + "             "
					+ StringUtil.formatString(
					s4.trim(),
					"%-5.5s")
					+ StringUtil.formatString(
					s5.trim(),
					"%-8.8s")
					+ StringUtil.formatString(
					s6.trim(),
					"%-16.16s"));
			}
		}

		if (outletSize > 0) {
			v.add("");
			v.add("                                    "
				+ "               OUTLET INFORMATION");
			v.add("NAME                          "
				+ "CAPACITY      DIAMETER        LENGTH    "
				+ "     TYPE   OUTLET DESCRIPTION");
			v.add("______________________________________"
				+ "_________________________________________"
				+ "_________________________________________");

			for (int i = 0; i < outletSize; i++) {
				s10 = (String)__outletWorksheet.getValueAt(
					i, 0);
				if (DMIUtil.isMissing(s10)) {
					s10 = "";
				}
				d11 = (Double)__outletWorksheet.getValueAt(
					i, 1);
				if (DMIUtil.isMissing(d11)) {
					s11 = StringUtil.formatString("",
						"%8.8s");
				}
				else {
					s11 = StringUtil.formatString(
						d11.toString(), "%8.3f");
				}
				d12 = (Double)__outletWorksheet.getValueAt(
					i, 2);
				if (DMIUtil.isMissing(d12)) {
					s12 = StringUtil.formatString("",
						"%14.14s");
				}
				else {
					s12 = StringUtil.formatString(
						d12.toString(), "%14.3f");
				}
				d13 = (Double)__outletWorksheet.getValueAt(
					i, 3);
				if (DMIUtil.isMissing(d13)) {
					s13 = StringUtil.formatString("",
						"%15.15s");
				}
				else {
					s13 = StringUtil.formatString(
						d13.toString(), "%15.3f");
				}		
				s14 = (String)__outletWorksheet.getValueAt(
					i, 4);
				if (DMIUtil.isMissing(s14)) {
					s14 = "";
				}
				s15 = (String)__outletWorksheet.getValueAt(
					i, 5);
				if (DMIUtil.isMissing(s15)) {
					s15 = "";
				}
				v.add(
					StringUtil.formatString(s10.trim(),
					"%-30.30s")
					+ StringUtil.formatString(
					s11.trim(),
					"%-8.8s") + "      "
					+ StringUtil.formatString(
					s12.trim(),
					"%-14.14s")
					+ "  "
					+ StringUtil.formatString(
					s13.trim(),
					"%-15.15s")
					+ StringUtil.formatString(
					s14.trim(),"%-2.2s") + "     "
					+ s15.trim());
			}
		}

		if (inspectionSize > 0) {
			v.add("");
			v.add("                                     "
				+ "                INSPECTION LOG");
			v.add("DATE           INSPECTOR      TYPE    ");
			v.add("______________________________________"
				+ "_________________________________________"
				+ "_________________________________________");

			for (int i = 0; i < inspectionSize; i++) {
				s7 = (String)__inspectionWorksheet.getValueAt(
					i, 0);
				if (DMIUtil.isMissing(s7)) {
					s7 = "";
				}
				else {
				}
				s8 = (String)__inspectionWorksheet.getValueAt(
					i, 1);
				if (DMIUtil.isMissing(s8)) {
					s8 = "";
				}
				s9 = (String)__inspectionWorksheet.getValueAt(
					i, 2);
				if (DMIUtil.isMissing(s9)) {
					s9 = "";
				}
				v.add(
					StringUtil.formatString(s7.trim(),
					"%-10.10s") + "     "
					+ StringUtil.formatString(s8.trim(),
					"%-3.3s") + "            "
					+ StringUtil.formatString(s9.trim(),
					"%-1.1s") + "       ");
			}
		}
		
		if (emergencySize > 0) {
			v.add("");
			v.add("                                     "
				+ "                EMERGENCY PLAN");
			v.add("DATE           REQUIRED");
			v.add("______________________________________"
				+ "_________________________________________"
				+ "_________________________________________");

			for (int i = 0; i < emergencySize; i++) {
				s16 = (String)__emergencyWorksheet.getValueAt(
					i, 0);
				if (DMIUtil.isMissing(s16)) {
					s16 = "";
				}
				s17 = (String)__emergencyWorksheet.getValueAt(
					i, 1);
				if (DMIUtil.isMissing(s17)) {
					s17 = "";
				}
				v.add(
					StringUtil.formatString(s16.trim(),
					"%-10.10s") + "     "
					+ s17.trim());
			}
		}
	}

	else {	
		char delim = HydroBase_GUI_Util.getDelimiterForFormat(format);	
		v.add(HydroBase_GUI_Util.formatStructureHeader(format));
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			HydroBase_GUI_Util.trimText(__structureJTextField),
			HydroBase_GUI_Util.trimText(__divJTextField),
			HydroBase_GUI_Util.trimText(__wdJTextField),
			HydroBase_GUI_Util.trimText(__idJTextField), format));
		v.add("");
		v.add("DAM AKA" + delim + "HAZARD CLASS" + delim 
			+ "NAB CODE" + delim + "STATE DAME ID" + delim 
			+ "NAT INVENT ID" + delim + "FOREST SERV ID" + delim 
			+ "FED LAND" + delim + "FED REGS" + delim 
			+ "DOWN TOWN" + delim + "DIST" + delim + "STREAM CODE" 
			+ delim + "SPILLWAY CAP" + delim + "OUTLET CAP" 
			+ delim + "CREST ELEV" + delim + "CREST LEN" + delim 
			+ "CREST WID" + delim + "JURIS HT" + delim + "HYD HT" 
			+ delim + "STR HT" + delim + "TYPE" + delim 
			+ "PURPOSE" + delim + "YEAR COMPLETE" + delim 
			+ "REMARKS" + delim);

		v.add(
			HydroBase_GUI_Util.trimText(__damAKAJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__hazardClassJTextField) 
			+ delim 
			+ HydroBase_GUI_Util.trimText(__nabCodeJTextField) 
			+ delim 
			+ HydroBase_GUI_Util.trimText(__stateDamIDJTextField) 
			+ delim 
			+ HydroBase_GUI_Util.trimText(__nationalIDJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__forestIDJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__federalLandJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__regulationsJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__nameJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__distanceJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__streamCodeJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__spillwayJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__outletJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__elevationJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__lengthJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__widthJTextField) 
			+ delim
			+HydroBase_GUI_Util.trimText(__jurisdictionalJTextField)
			+ delim
			+ HydroBase_GUI_Util.trimText(__hydraulicJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__structuralJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__damTypeJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__purposeJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__yearJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__remarksJTextField) 
			+ delim);

		if (spillwaySize > 0) {
			v.add("");
			v.add("NAME" + delim + "CAPACITY" + delim 
				+ "FREEBOARD" + delim + "FLOW CODE" + delim 
				+ "TYPE" + delim + "WALL SIDE SLOPE" + delim 
				+ "WIDTH" + delim);

			for (int i = 0; i < spillwaySize; i++) {
				s0 = (String)__spillwayWorksheet.getValueAt(
					i, 0);
				if (DMIUtil.isMissing(s0)) {
					s0 = "";
				}
				i1 = (Integer)__spillwayWorksheet.getValueAt(
					i, 1);
				if (DMIUtil.isMissing(i1)) {
					s1 = StringUtil.formatString("", 
						"%17.17s");
				}
				else {
					s1 = StringUtil.formatString(
						i1.toString(), "%17d");
				}
				d2 = (Double)__spillwayWorksheet.getValueAt(
					i, 2);
				if (DMIUtil.isMissing(d2)) {
					s2 = StringUtil.formatString("", 
						"%12.12s");
				}
				else {
					s2 = StringUtil.formatString(
						d2.toString(),"%12.1f");
				}
				s3 = (String)__spillwayWorksheet.getValueAt(
					i, 3);
				if (DMIUtil.isMissing(s3)) {
					s3 = "";
				}
				s4 = (String)__spillwayWorksheet.getValueAt(
					i, 4);
				if (DMIUtil.isMissing(s4)) {
					s4 = "";
				}
				d5 = (Double)__spillwayWorksheet.getValueAt(
					i, 5);
				if (DMIUtil.isMissing(d5)) {
					s5 = StringUtil.formatString("",
						"%8.8s");
				}
				else {
					s5 = StringUtil.formatString(
						d5.toString(), "%8.1f");
				}				
				d6 = (Double)__spillwayWorksheet.getValueAt(
					i, 6);
				if (DMIUtil.isMissing(d6)) {
					s6 = StringUtil.formatString(
						"", "%16.16s");
				}
				else {
					s6 = StringUtil.formatString(
						d6.toString(), "%16.1f");
				}
				v.add(s0.trim() + delim + s1.trim()
					+ delim + s2.trim() + delim 
					+ s3.trim() + delim + s4.trim()
					+ delim + s5.trim() + delim
					+ s6.trim() + delim);
			}
		}

		if (outletSize > 0) {
			v.add("");
			v.add("NAME" + delim + "CAPACITY" + delim 
				+ "DIAMETER" + delim + "LENGTH" + delim 
				+ "TYPE" + delim + "OUTLET DESCRIPTION" +delim);
			for (int i = 0; i < outletSize; i++) {
				s10 = (String)__outletWorksheet.getValueAt(
					i, 0);
				if (DMIUtil.isMissing(s10)) {
					s10 = "";
				}
				d11 = (Double)__outletWorksheet.getValueAt(
					i, 1);
				if (DMIUtil.isMissing(d11)) {
					s11 = StringUtil.formatString("",
						"%8.8s");
				}
				else {
					s11 = StringUtil.formatString(
						d11.toString(), "%8.3f");
				}
				d12 = (Double)__outletWorksheet.getValueAt(
					i, 2);
				if (DMIUtil.isMissing(d12)) {
					s12 = StringUtil.formatString("",
						"%14.14s");
				}
				else {
					s12 = StringUtil.formatString(
						d12.toString(), "%14.3f");
				}
				d13 = (Double)__outletWorksheet.getValueAt(
					i, 3);
				if (DMIUtil.isMissing(d13)) {
					s13 = StringUtil.formatString("",
						"%15.15s");
				}
				else {
					s13 = StringUtil.formatString(
						d13.toString(), "%15.3f");
				}		
				s14 = (String)__outletWorksheet.getValueAt(
					i, 4);
				if (DMIUtil.isMissing(s14)) {
					s14 = "";
				}
				s15 = (String)__outletWorksheet.getValueAt(
					i, 5);
				if (DMIUtil.isMissing(s15)) {
					s15 = "";
				}
				v.add(s10.trim() + delim + s11.trim()
					+ delim + s12.trim() + delim
					+ s13.trim() + delim + s14.trim()
					+ delim + s15.trim() + delim);
			}
		}

		if (inspectionSize > 0) {
			v.add("");
			v.add("DATE" + delim + "INSPECTOR" + delim 
				+ "TYPE" + delim + "DAM/OUTLET NAME" + delim);
			for (int i = 0; i < inspectionSize; i++) {
				s7 = (String)__inspectionWorksheet.getValueAt(
					i, 0);
				if (DMIUtil.isMissing(s7)) {
					s7 = "";
				}
				else {
				}
				s8 = (String)__inspectionWorksheet.getValueAt(
					i, 1);
				if (DMIUtil.isMissing(s8)) {
					s8 = "";
				}
				s9 = (String)__inspectionWorksheet.getValueAt(
					i, 2);
				if (DMIUtil.isMissing(s9)) {
					s9 = "";
				}
				v.add(s7.trim() + delim + s8.trim()
					+ delim + s9.trim() + delim);
			}
		}

		if (emergencySize > 0) {
			v.add("");
			v.add("DATE" + delim + "REQUIRED" + delim);
			for (int i = 0; i < emergencySize; i++) {
				s16 = (String)__emergencyWorksheet.getValueAt(
					i, 0);
				if (DMIUtil.isMissing(s16)) {
					s16 = "";
				}
				s17 = (String)__emergencyWorksheet.getValueAt(
					i, 1);
				if (DMIUtil.isMissing(s17)) {
					s17 = "";
				}
					
				v.add(s16.trim() + delim + s17.trim()
					+ delim);
			}
		}
	}	

	return v;
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	String routine = "HydroBase_GUI_Dam.setupGUI";
	addWindowListener(this);
	
        // objects used throughout the GUI layout
        Insets insetsTLBR = new Insets(7,7,7,7);
        Insets insetsTLBN = new Insets(7,7,7,0);
        Insets insetsTNBR = new Insets(7,0,7,7);
        Insets insetsNLBR = new Insets(0,7,7,7);
        Insets insetsTLNR = new Insets(7,7,0,7);
        Insets insetsNNNR = new Insets(0,0,0,7);
        Insets insetsNLNN = new Insets(0,7,0,0);
        Insets insetsNNBR = new Insets(0,0,7,7);
        Insets insetsNLBN = new Insets(0,7,7,0);
        Insets insetsNNBN = new Insets(0,0,7,0);
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

        __idJTextField = new JTextField(10);
        __idJTextField.setEditable(false);
        JGUIUtil.addComponent(topWJPanel, __idJTextField, 
		3, 1, 1, 1, 0, 0, insetsNLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);            

        // Center JPanel
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(new BorderLayout());
        getContentPane().add("Center", centerJPanel);

        // Center: West JPanel
        JPanel centerWJPanel = new JPanel();
        centerWJPanel.setLayout(gbl);
        centerJPanel.add("West", centerWJPanel);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Dam AKA:"), 
		0, 2, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __damAKAJTextField = new JTextField(20);
        __damAKAJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __damAKAJTextField, 
		1, 2, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
                      
        JGUIUtil.addComponent(centerWJPanel, new JLabel("State Dam ID:"), 
		4, 2, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __stateDamIDJTextField = new JTextField(5);
        __stateDamIDJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __stateDamIDJTextField, 
		5, 2, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Hazard Class:"), 
		0, 3, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __hazardClassJTextField = new JTextField(5);
        __hazardClassJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __hazardClassJTextField, 
		1, 3, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("NAB Code:"), 
		2, 3, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __nabCodeJTextField = new JTextField(10);
        __nabCodeJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __nabCodeJTextField, 
		3, 3, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, 
		new JLabel("National Inventory ID:"), 
		4, 3, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __nationalIDJTextField = new JTextField(10);
        __nationalIDJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __nationalIDJTextField, 
		5, 3, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Nearest Downstream"), 
		0, 4, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Forest Service ID:"), 
		4, 4, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __forestIDJTextField = new JTextField(10);
        __forestIDJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __forestIDJTextField, 
		5, 4, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
                                                                
        JGUIUtil.addComponent(centerWJPanel, new JLabel("Town Name:"), 
		0, 5, 1, 1, 0, 0, insetsNLBN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __nameJTextField = new JTextField(10);
        __nameJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __nameJTextField, 
		1, 5, 1, 1, 0, 0, insetsNNBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Distance:"), 
		2, 5, 1, 1, 0, 0, insetsNNBN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __distanceJTextField = new JTextField(10);
        __distanceJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __distanceJTextField, 
		3, 5, 1, 1, 0, 0, insetsNNBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Federal Land:"), 
		4, 5, 1, 1, 0, 0, insetsNLBN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __federalLandJTextField = new JTextField(10);
        __federalLandJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __federalLandJTextField, 
		5, 5, 1, 1, 0, 0, insetsNNBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Stream Code:"), 
		2, 6, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __streamCodeJTextField = new JTextField(10);
        __streamCodeJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __streamCodeJTextField, 
		3, 6, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, 
		new JLabel("Federal Regulations:"), 
		4, 6, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __regulationsJTextField = new JTextField(10);
        __regulationsJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __regulationsJTextField, 
		5, 6, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Crest:"), 
		2, 7, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Dam Heights:"), 
		4, 7, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Spillway Capacity:"), 
		0, 8, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __spillwayJTextField = new JTextField(10);
        __spillwayJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __spillwayJTextField, 
		1, 8, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Elevation:"), 
		2, 8, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __elevationJTextField = new JTextField(10);
        __elevationJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __elevationJTextField, 
		3, 8, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Jurisdictional:"), 
		4, 8, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __jurisdictionalJTextField = new JTextField(10);
        __jurisdictionalJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __jurisdictionalJTextField, 
		5, 8, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Outlet Capacity:"), 
		0, 9, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __outletJTextField = new JTextField(10);
        __outletJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __outletJTextField, 
		1, 9, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Length:"), 
		2, 9, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __lengthJTextField = new JTextField(10);
        __lengthJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __lengthJTextField, 
		3, 9, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Hydraulic:"), 
		4, 9, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

         __hydraulicJTextField = new JTextField(10);
        __hydraulicJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __hydraulicJTextField, 
		5, 9, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Width:"), 
		2, 10, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __widthJTextField = new JTextField(10);
        __widthJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __widthJTextField, 
		3, 10, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Structural:"), 
		4, 10, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __structuralJTextField = new JTextField(10);
        __structuralJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __structuralJTextField, 
		5, 10, 1, 1, 0, 0, insetsNNNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Dam Type:"), 
		0, 11, 1, 1, 0, 0, insetsTLBN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __damTypeJTextField = new JTextField(10);
        __damTypeJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __damTypeJTextField, 
		1, 11, 1, 1, 0, 0, insetsTNBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Purpose:"), 
		2, 11, 1, 1, 0, 0, insetsTLBN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __purposeJTextField = new JTextField(10);
        __purposeJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __purposeJTextField, 
		3, 11, 1, 1, 0, 0, insetsTNBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Year Completed:"), 
		4, 11, 1, 1, 0, 0, insetsNLNN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __yearJTextField = new JTextField(10);
        __yearJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __yearJTextField, 
		5, 11, 1, 1, 0, 0, insetsTNBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Remarks:"), 
		0, 12, 1, 1, 0, 0, insetsTLBN, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __remarksJTextField = new JTextField(10);
        __remarksJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __remarksJTextField, 
		1, 12, 6, 1, 0, 0, insetsTNBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	JTabbedPane tabPanel = new JTabbedPane();
	JGUIUtil.addComponent(centerWJPanel, tabPanel,
		0, 13, 6, 5, 0, 1, 
		insetsTLBR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

	PropList p = new PropList("HydroBase_GUI_Dam.JWorksheet");
	/*
	p.add("JWorksheet.CellFont=Courier");
	p.add("JWorksheet.CellStyle=Plain");
	p.add("JWorksheet.CellSize=11");
	p.add("JWorksheet.HeaderFont=Arial");
	p.add("JWorksheet.HeaderStyle=Plain");
	p.add("JWorksheet.HeaderSize=11");
	p.add("JWorksheet.HeaderBackground=LightGray");
	p.add("JWorksheet.RowColumnPresent=false");
	*/
	p.add("JWorksheet.ShowRowHeader=true");
	p.add("JWorksheet.ShowPopupMenu=true");
	p.add("JWorksheet.AllowCopy=true");	
	p.add("JWorksheet.SelectionMode=SingleRowSelection");

	// spillway worksheet
	int[] spillwayWidths = null;
	JScrollWorksheet spillwayJSW = null;
	try {
		HydroBase_TableModel_Dam tm = new HydroBase_TableModel_Dam(new Vector<HydroBase_DamSpillway>());
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
	
		spillwayJSW = new JScrollWorksheet(cr, tm, p);
		__spillwayWorksheet = spillwayJSW.getJWorksheet();

		for (int i = 7; i < 18; i++) {
			__spillwayWorksheet.removeColumn(i);
		}

		spillwayWidths = tm.getColumnWidths();
	}
	catch (Exception e) {
		Message.printWarning(2, routine, e);
		spillwayJSW = new JScrollWorksheet(0, 0, p);
		__spillwayWorksheet = spillwayJSW.getJWorksheet();
	}
	__spillwayWorksheet.setPreferredScrollableViewportSize(null);
	__spillwayWorksheet.setHourglassJFrame(this);

	// inspection worksheet
	int[] inspectionWidths = null;
	JScrollWorksheet inspectionJSW = null;
	try {
		HydroBase_TableModel_Dam tm = new HydroBase_TableModel_Dam(new Vector<HydroBase_DamInspection>());
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
	
		inspectionJSW = new JScrollWorksheet(cr, tm, p);
		__inspectionWorksheet = inspectionJSW.getJWorksheet();

		for (int i = 0; i < 7; i++) {
			__inspectionWorksheet.removeColumn(i);
		}
		for (int i = 10; i < 18; i++) {
			__inspectionWorksheet.removeColumn(i);
		}

		inspectionWidths = tm.getColumnWidths();
	}
	catch (Exception e) {
		Message.printWarning(2, routine, e);
		inspectionJSW = new JScrollWorksheet(0, 0, p);
		__inspectionWorksheet = inspectionJSW.getJWorksheet();
	}
	__inspectionWorksheet.setPreferredScrollableViewportSize(null);
	__inspectionWorksheet.setHourglassJFrame(this);

	// outlet worksheet
	int[] outletWidths = null;
	JScrollWorksheet outletJSW = null;
	try {
		HydroBase_TableModel_Dam tm = new
			HydroBase_TableModel_Dam(new Vector<HydroBase_DamOutlet>());
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
	
		outletJSW = new JScrollWorksheet(cr, tm, p);
		__outletWorksheet = outletJSW.getJWorksheet();

		for (int i = 0; i < 10; i++) {
			__outletWorksheet.removeColumn(i);
		}
		for (int i = 16; i < 18; i++) {
			__outletWorksheet.removeColumn(i);
		}

		outletWidths = tm.getColumnWidths();
	}
	catch (Exception e) {
		Message.printWarning(2, routine, e);
		outletJSW = new JScrollWorksheet(0, 0, p);
		__outletWorksheet = outletJSW.getJWorksheet();
	}
	__outletWorksheet.setPreferredScrollableViewportSize(null);
	__outletWorksheet.setHourglassJFrame(this);

	// emergency plan worksheet
	int[] emergencyWidths = null;
	JScrollWorksheet emergencyJSW = null;
	try {
		HydroBase_TableModel_Dam tm = new
			HydroBase_TableModel_Dam(new Vector<HydroBase_EmergencyPlan>());
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
	
		emergencyJSW = new JScrollWorksheet(cr, tm, p);
		__emergencyWorksheet = emergencyJSW.getJWorksheet();

		for (int i = 0; i < 16; i++) {
			__emergencyWorksheet.removeColumn(i);
		}

		emergencyWidths = tm.getColumnWidths();
	}
	catch (Exception e) {
		Message.printWarning(2, routine, e);
		emergencyJSW = new JScrollWorksheet(0, 0, p);
		__emergencyWorksheet = emergencyJSW.getJWorksheet();
	}
	__emergencyWorksheet.setPreferredScrollableViewportSize(null);
	__emergencyWorksheet.setHourglassJFrame(this);

	tabPanel.add(__TAB_SPILLWAY, spillwayJSW);
	tabPanel.add(__TAB_INSPECTION, inspectionJSW);
	tabPanel.add(__TAB_OUTLET, outletJSW);
	tabPanel.add(__TAB_EMERGENCY, emergencyJSW);

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
	print.setToolTipText("Print the data.");
        bottomSouthNorthJPanel.add(print);
	SimpleJButton export = new SimpleJButton(__BUTTON_EXPORT, this);
	export.setToolTipText("Export data to a file.");
        bottomSouthNorthJPanel.add(export);
	SimpleJButton close = new SimpleJButton(__BUTTON_CLOSE, this);
	close.setToolTipText("Close the form.");
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
		0, 1, 10, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.WEST);
            
        // Frame settings
        setTitle("Dam Information");
        pack();
	setSize(getWidth(), getHeight() + 150);
        setResizable(false);
        JGUIUtil.center(this);
        setVisible(true);

	if (spillwayWidths != null) {
		__spillwayWorksheet.setColumnWidths(spillwayWidths);
	}
	if (inspectionWidths != null) {
		__inspectionWorksheet.setColumnWidths(inspectionWidths);
	}
	if (outletWidths != null) {
		__outletWorksheet.setColumnWidths(outletWidths);
	}
	if (emergencyWidths != null) {
		__emergencyWorksheet.setColumnWidths(emergencyWidths);
	}
}

/**
Submits a query for dam information and displays the data on the form.
*/
private void submitAndDisplayDamQuery() {
	String routine = "HydroBase_GUI_Dam.submitAndDisplayDamQuery";
	JGUIUtil.setWaitCursor(this, true);

	HydroBase_StructureDam data = null;

	try {
		data = __dmi.readDamForStructure_num(__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading dam data.");
		Message.printWarning(2, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

        int curInt = data.getWD();
        if (!DMIUtil.isMissing(curInt)) {
                __wdJTextField.setText("" + curInt);
        }

        curInt = data.getID();
        if (!DMIUtil.isMissing(curInt)) {
                __idJTextField.setText("" + curInt);
        }
 
        curInt = data.getDiv();
        if (!DMIUtil.isMissing(curInt)) {
                __divJTextField.setText("" + curInt);
        }

        curInt = data.getCompl_year();
        if (!DMIUtil.isMissing(curInt)) {
                __yearJTextField.setText("" + curInt);
        }

        double curDouble = data.getCrest_elev();
        if (!DMIUtil.isMissing(curDouble)) {
                __elevationJTextField.setText("" + curDouble);
        }

        curDouble = data.getCrest_width();
        if (!DMIUtil.isMissing(curDouble)) {
                __widthJTextField.setText("" + curDouble);
        }

        curInt = data.getDamid();
        if (!DMIUtil.isMissing(curInt)) {
                __stateDamIDJTextField.setText("" + curInt); 
        }

        __damTypeJTextField.setText("" + data.getDam_type());
        __nameJTextField.setText("" + data.getDown_town());

        curDouble = data.getDown_town_dist();
        if (!DMIUtil.isMissing(curDouble)) {
                __distanceJTextField.setText("" + curDouble);
        }

        __federalLandJTextField.setText("" + data.getFed_land());
        __regulationsJTextField.setText("" + data.getFed_regs());
        __forestIDJTextField.setText("" + data.getForestid());
        __hazardClassJTextField.setText("" + data.getHazard_class());

        curDouble = data.getHeight();
        if (!DMIUtil.isMissing(curDouble)) {
                __jurisdictionalJTextField.setText("" + curDouble);
        } 

        curDouble = data.getHyd_height();
        if (!DMIUtil.isMissing(curDouble)) {
                __hydraulicJTextField.setText("" + curDouble);
        }

        curDouble = data.getLength();
        if (!DMIUtil.isMissing(curDouble)) {
                __lengthJTextField.setText("" + curDouble);
        }

        __nationalIDJTextField.setText("" + data.getNatid());

        curDouble = data.getOutlet_capacity();
        if (!DMIUtil.isMissing(curDouble)) {
                __outletJTextField.setText("" + curDouble);
        }

        __nabCodeJTextField.setText(convertAbandCode(data.getAband_code()));
        __damAKAJTextField.setText("");
        __purposeJTextField.setText(data.getPurposes());
        __remarksJTextField.setText(data.getRemarks());

        curDouble = data.getSpillway_capacity();
        if (!DMIUtil.isMissing(curDouble)) {
                __spillwayJTextField.setText("" + curDouble);
        }

         __streamCodeJTextField.setText("" + data.getStrm_code());

        curDouble = data.getStr_height();
        if (!DMIUtil.isMissing(curDouble)) {
                __structuralJTextField.setText("" + curDouble);
        }

	JGUIUtil.setWaitCursor(this, false);
}

/**
Submits a query for emergency plan data and puts the data into the 
emergency plan worksheet.
*/
private void submitAndDisplayEmergencyPlanQuery() {
	String routine = "HydroBase_GUI_Dam.submitAndDisplayEmergencyPlanQuery";
	JGUIUtil.setWaitCursor(this, true);
	
	List<HydroBase_EmergencyPlan> v = null;
	try {
		v = __dmi.readEmergencyPlanListForStructure_num(__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading emergency plan "
			+ "data.");
		Message.printWarning(2, routine, e);
	}

	if (v == null) {
		v = new Vector<HydroBase_EmergencyPlan>();
	}
	
	__emergencyWorksheet.setData(v);
	JGUIUtil.setWaitCursor(this, false);
}

/**
Submits a query for inspection data and puts the data into the inspection 
worksheet.
*/
private void submitAndDisplayInspectionQuery() {
	String routine = "HydroBase_GUI_Dam.submitAndDisplayInspectionQuery";
	JGUIUtil.setWaitCursor(this, true);

	List<HydroBase_DamInspection> v = null;
	try {
		v = __dmi.readDamInspectionListForStructure_num(__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading dam inspection "
			+ "data.");
		Message.printWarning(2, routine, e);
	}
	
	if (v == null) {
		v = new Vector<HydroBase_DamInspection>();
	}

	__inspectionWorksheet.setData(v);
	JGUIUtil.setWaitCursor(this, false);
}

/**
Submits a query for outlet data and puts the data into the outlet worksheet.
*/
private void submitAndDisplayOutletQuery() {
	String routine = "HydroBase_GUI_Dam.submitAndDisplayOutletQuery";
	JGUIUtil.setWaitCursor(this, true);

	List<HydroBase_DamOutlet> v = null;
	try {
		v = __dmi.readDamOutletListForStructure_num(__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading dam outlet "
			+ "data.");
		Message.printWarning(2, routine, e);
		
	}

	if (v == null) {
		v = new Vector<HydroBase_DamOutlet>();
	}

	__outletWorksheet.setData(v);
	JGUIUtil.setWaitCursor(this, false);
}

/**
Submits a query for spillway data and puts the data into the spillway worksheet.
*/
private void submitAndDisplaySpillwayQuery() {
	String routine = "HydroBase_GUI_Dam.submitAndDisplaySpillwayQuery";
	JGUIUtil.setWaitCursor(this, true);

	List<HydroBase_DamSpillway> v = null;
	try {
		v = __dmi.readDamSpillwayListForStructure_num(__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading dam spillway "
			+ " data.");
		Message.printWarning(2, routine, e);
	}

	if (v == null) {
		v = new Vector<HydroBase_DamSpillway>();
	}

	__spillwayWorksheet.setData(v);
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
