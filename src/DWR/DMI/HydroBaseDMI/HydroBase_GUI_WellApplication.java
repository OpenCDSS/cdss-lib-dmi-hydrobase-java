//-----------------------------------------------------------------------------
// HydroBase_GUI_WellApplication - Well Application Detail GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 07 Jun 2000	Catherine E. Nutting-Lane, RTi
//					Created initial version.
// 19 Jun 2000	CEN, RTi		Enabled print/export buttons
// 2001-11-12	SAM, RTi		Change GUI to JGUIUtil.
// 2003-02-12	SAM, RTi		Fix the comment text field size to
//					reasonable limit.  Long comments were
//					making it too long.
//-----------------------------------------------------------------------------
// 2003-05-19	J. Thomas Sapienza, RTi	Initial swing version.
// 2005-02-11	JTS, RTi		Changed code to support when well
//					views are returned from stored procedure
//					queries.
// 2005-04-28	JTS, RTi		Added finalize().
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import RTi.Util.IO.ExportJGUI;
import RTi.Util.IO.PrintJGUI;

import RTi.Util.GUI.JGUIUtil;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

/**
This GUI displays detailed well application information.
*/
public class HydroBase_GUI_WellApplication 
extends JFrame
implements ActionListener {

private final String __BUTTON_CLOSE = "Close";
private final String __BUTTON_EXPORT = "Export";
private final String __BUTTON_HELP = "Help";
private final String __BUTTON_PRINT = "Print";

private JButton __closeJButton;
private JButton __exportJButton;
private JButton __printJButton;

private JTextField __abcoDateJTextField;
private JTextField __abrDateJTextField;
private JTextField __acreFtJTextField;
private JTextField __actCodeJTextField;
private JTextField __actDateJTextField;
private JTextField __aquifer1JTextField;
private JTextField __aquifer2JTextField;
private JTextField __areaIrrJTextField;
private JTextField __basinJTextField;
private JTextField __blockJTextField;
private JTextField __bperfJTextField;
private JTextField __caseNoJTextField;
private JTextField __commentJTextField;
private JTextField __depthJTextField;
private JTextField __divJTextField;
private JTextField __drillerLicJTextField;
private JTextField __elevJTextField;
private JTextField __engineerJTextField;
private JTextField __exDateJTextField;
private JTextField __filingJTextField;
private JTextField __irrMeasJTextField;
private JTextField __levelJTextField;
private JTextField __lotJTextField;
private JTextField __mdJTextField;
private JTextField __nbuDateJTextField;
private JTextField __noticeDateJTextField;
private JTextField __npDateJTextField;
private JTextField __nwcDateJTextField;
private JTextField __pyieldJTextField;
private JTextField __pacreftJTextField;
private JTextField __parcelNoJTextField;
private JTextField __parcelSizeJTextField;
private JTextField __pcDateJTextField;
private JTextField __pdepthJTextField;
private JTextField __permitNoJTextField;
private JTextField __permitRplJTextField;
private JTextField __permitSufJTextField;
private JTextField __piDateJTextField;
private JTextField __pumpLicJTextField;
private JTextField __receiptJTextField;
private JTextField __saDateJTextField;
private JTextField __sbuDateJTextField;
private JTextField __statCodeJTextField;
private JTextField __statDateJTextField;
private JTextField __statusJTextField;
private JTextField __statuteJTextField;
private JTextField __subdivNameJTextField;
private JTextField __tperfJTextField;
private JTextField __tranCodeJTextField;
private JTextField __tranDateJTextField;
private JTextField __use1JTextField;
private JTextField __use2JTextField;
private JTextField __use3JTextField;
private JTextField __userJTextField;
private JTextField __waDateJTextField;
private JTextField __wcDateJTextField;
private JTextField __wdJTextField;
private JTextField __wellNameJTextField;
private JTextField __wellxNoJTextField;
private JTextField __wellxSufJTextField;
private JTextField __wellxRplJTextField;
private JTextField __yieldJTextField;

private JCheckBox __meterJCheckBox;
private JCheckBox __logJCheckBox;
private JCheckBox __abreqJCheckBox;
private JCheckBox __qualJCheckBox;


private HydroBase_WellApplicationGeoloc __wellApp;

private HydroBase_WellApplicationView __wellView;

/**
Constructor.
@param parent the parent frame that opened this.
@param wellApp the Hydrobase_WellApplication object with data to fill in this.
*/
public HydroBase_GUI_WellApplication(JFrame parent, 
HydroBase_WellApplicationGeoloc wellApp) {
        __wellApp = wellApp;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());	
        setupGUI();
	setVisible(true);
}

/**
Constructor.
@param parent the parent frame that opened this.
@param wellApp the Hydrobase_WellApplication object with data to fill in this.
*/
public HydroBase_GUI_WellApplication(JFrame parent, 
HydroBase_WellApplicationView wellView) {
        __wellView = wellView;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());	
        setupViewGUI();
	setVisible(true);
}

/**
Responds to action events.
@param event the event that happened.
*/
public void actionPerformed(ActionEvent event) {
	String routine = "actionPerformed";
        String actionCommand = event.getActionCommand();

        if (actionCommand.equals(__BUTTON_CLOSE)) {
		closeClicked();
        }
        else if (actionCommand.equals(__BUTTON_EXPORT)) {
		try {
	 		// First format the output...
			List outputStrings = formatOutput(
				HydroBase_GUI_Util.SCREEN_VIEW);
 			// Now export, letting the user decide the file...
			ExportJGUI.export(this, outputStrings);			
		} 
		catch (Exception ex) {
			Message.printWarning(1, routine, "Error in export.");
			Message.printWarning (2, routine, ex);
		}		
        }
        else if (actionCommand.equals(__BUTTON_HELP)) {
        }
        else if (actionCommand.equals(__BUTTON_PRINT)) {
		try {
	 		// First format the output...
			List outputStrings = formatOutput(
				HydroBase_GUI_Util.SCREEN_VIEW);
	 		// Now print...
			PrintJGUI.print(this, outputStrings);
		}
		catch (Exception ex) {
			Message.printWarning(1, routine, "Error during "
				+ "printing.");
			Message.printWarning (2, routine, ex);
		}		
        }		
}

/**
Closes the GUI.
*/
protected void closeClicked() {
        setVisible(false);
}

/**
Cleans up the data members.
*/
public void finalize()
throws Throwable {
	__closeJButton = null;
	__exportJButton = null;
	__printJButton = null;
	__abcoDateJTextField = null;
	__abrDateJTextField = null;
	__acreFtJTextField = null;
	__actCodeJTextField = null;
	__actDateJTextField = null;
	__aquifer1JTextField = null;
	__aquifer2JTextField = null;
	__areaIrrJTextField = null;
	__basinJTextField = null;
	__blockJTextField = null;
	__bperfJTextField = null;
	__caseNoJTextField = null;
	__commentJTextField = null;
	__depthJTextField = null;
	__divJTextField = null;
	__drillerLicJTextField = null;
	__elevJTextField = null;
	__engineerJTextField = null;
	__exDateJTextField = null;
	__filingJTextField = null;
	__irrMeasJTextField = null;
	__levelJTextField = null;
	__lotJTextField = null;
	__mdJTextField = null;
	__nbuDateJTextField = null;
	__noticeDateJTextField = null;
	__npDateJTextField = null;
	__nwcDateJTextField = null;
	__pyieldJTextField = null;
	__pacreftJTextField = null;
	__parcelNoJTextField = null;
	__parcelSizeJTextField = null;
	__pcDateJTextField = null;
	__pdepthJTextField = null;
	__permitNoJTextField = null;
	__permitRplJTextField = null;
	__permitSufJTextField = null;
	__piDateJTextField = null;
	__pumpLicJTextField = null;
	__receiptJTextField = null;
	__saDateJTextField = null;
	__sbuDateJTextField = null;
	__statCodeJTextField = null;
	__statDateJTextField = null;
	__statusJTextField = null;
	__statuteJTextField = null;
	__subdivNameJTextField = null;
	__tperfJTextField = null;
	__tranCodeJTextField = null;
	__tranDateJTextField = null;
	__use1JTextField = null;
	__use2JTextField = null;
	__use3JTextField = null;
	__userJTextField = null;
	__waDateJTextField = null;
	__wcDateJTextField = null;
	__wdJTextField = null;
	__wellNameJTextField = null;
	__wellxNoJTextField = null;
	__wellxSufJTextField = null;
	__wellxRplJTextField = null;
	__yieldJTextField = null;
	__meterJCheckBox = null;
	__logJCheckBox = null;
	__abreqJCheckBox = null;
	__qualJCheckBox = null;
	__wellApp = null;
	__wellView = null;
	super.finalize();
}

/**
Formats the data for output.
@param FORMAT format flag definied in HydroBase_GUI_Util (SCREEN_VIEW, 
SUMMARY) or a flag that specifies the delimiter to use.
@return a formatted Vector for exporting, printing, etc..
*/
public List formatOutput(int FORMAT) {
	List v = new Vector();
	List tmpV = new Vector();

        char delim = HydroBase_GUI_Util.getDelimiterForFormat(FORMAT);

/*
	v.add("Name");
	v.add(__xrefOwnerNameJTextField.getText());

        v.add("");
	v.add("City");
	v.add(__xrefCityJTextField.getText());
*/
        v.add("");
	v.add("Case No             XRef#");
	v.add(StringUtil.formatString(
		__caseNoJTextField.getText(), "%-20.20s")
		+ __wellxNoJTextField.getText() + " "
		+ __wellxSufJTextField.getText() + " "
		+ __wellxRplJTextField.getText());

        v.add("");
	v.add("Well Name");
	v.add(__wellNameJTextField.getText());

        v.add("");
	v.add("Statute");
	v.add(__statuteJTextField.getText());

/*
        v.add("");
	v.add("County");
	v.add(__xrefCtyJTextField.getText());

        v.add("");
	v.add("Location Coordinates");
	v.add(__xrefLocationJTextField.getText());
*/

        v.add("");
	v.add("Subdivision Name");
	v.add(__subdivNameJTextField.getText());

        v.add("");
	v.add("Lot     Block   Filing");
	tmpV.add(__lotJTextField.getText());
	tmpV.add(__blockJTextField.getText());
	tmpV.add(__filingJTextField.getText());
	v.add(StringUtil.formatString(tmpV, "%-8.8s%-8.8s%s"));

        v.add("");
	v.add("Parcel ID");
	v.add(__parcelNoJTextField.getText());

        v.add("");
	v.add("Acres in Tract");
	v.add(__parcelSizeJTextField.getText());

        v.add("");
	v.add("Use 1");
	v.add(__use1JTextField.getText());

        v.add("");
	v.add("Use 2");
	v.add(__use2JTextField.getText());

        v.add("");
	v.add("Use 3");
	v.add(__use3JTextField.getText());

        v.add("");
	v.add("Area Irrigated");
	v.add(__areaIrrJTextField.getText().trim() + " "
		+ __irrMeasJTextField.getText());

        v.add("");
	v.add("          Pump Rate   Acre Feet   Depth (ft)");
	tmpV.clear();
	tmpV.add(__pyieldJTextField.getText());
	tmpV.add(__pacreftJTextField.getText());
	tmpV.add(__pdepthJTextField.getText());
	v.add("Proposed  "
		+ StringUtil.formatString(tmpV, "%-12.12s%-12.12s%s"));
	tmpV.clear();
	tmpV.add(__yieldJTextField.getText());
	tmpV.add(__acreFtJTextField.getText());
	tmpV.add(__depthJTextField.getText());
	v.add("Actual    "
		+ StringUtil.formatString(tmpV, "%-12.12s%-12.12s%s"));

        v.add("");
	v.add("Elevation Top       Bottom    Water Level");
	tmpV.clear ();
	tmpV.add(__elevJTextField.getText());
	tmpV.add(__tperfJTextField.getText());
	tmpV.add(__bperfJTextField.getText());
	tmpV.add(__levelJTextField.getText());
	v.add(StringUtil.formatString(
		tmpV, "%-10.10s%-10.10s%-10.10s%-10.10s"));

        v.add("");
	v.add("Aquifers");
	v.add(__aquifer1JTextField.getText());
	v.add(__aquifer2JTextField.getText());

        v.add("");
	v.add("Driller        Pump");
	v.add(StringUtil.formatString(
		__drillerLicJTextField.getText(), "%-15.15s")
		+ __pumpLicJTextField.getText());

        v.add("");
	v.add("Flow Meter  Geo.Log   Qual  Abandon");
	tmpV.clear ();
	tmpV.add(JGUIUtil.toString(__meterJCheckBox));
	tmpV.add(JGUIUtil.toString(__logJCheckBox));
	tmpV.add(JGUIUtil.toString(__qualJCheckBox));
	tmpV.add(JGUIUtil.toString(__abreqJCheckBox));
	v.add(StringUtil.formatString(
		tmpV, "%-12.12s%-10.10s%-6.6s%s"));

        v.add("");
	v.add("Comment");
	v.add(__commentJTextField.getText());

        v.add("");
	v.add("Receipt");
	v.add(__receiptJTextField.getText());

        v.add("");
	v.add("Permit");
	v.add(__permitNoJTextField.getText() + " " +
		__permitSufJTextField.getText() + " "
		+ __permitRplJTextField.getText());

        v.add("");
	v.add("Div: " + __divJTextField.getText() + delim
		+ "WD: " + __wdJTextField.getText());
	v.add("Bas: " + __basinJTextField.getText() + delim
		+ "MD: " + __mdJTextField.getText());
	v.add("Eng: " + __engineerJTextField.getText() + delim
		+ "User: " + __userJTextField.getText());

        v.add("");
	v.add("Act: " + __actCodeJTextField.getText() + delim
		+ __actDateJTextField.getText());
	v.add("Stat: " + __statCodeJTextField.getText() + delim
		+ __statDateJTextField.getText());
	v.add("Tran: " + __tranCodeJTextField.getText() + delim
		+ __tranDateJTextField.getText());

        v.add("");
	v.add("NP:" + delim + delim + __npDateJTextField.getText());
	v.add("EX:" + delim + delim + __exDateJTextField.getText());
	v.add("Notice:" + delim + __noticeDateJTextField.getText());
	v.add("WA:" + delim + delim + __waDateJTextField.getText());
	v.add("WC:" + delim + delim + __wcDateJTextField.getText());
	v.add("NWC:" + delim + delim + __nwcDateJTextField.getText());
	v.add("PI:" + delim + delim + __piDateJTextField.getText());
	v.add("PC:" + delim + delim + __pcDateJTextField.getText());
	v.add("SA:" + delim + delim + __saDateJTextField.getText());
	v.add("SBU:" + delim + delim + __sbuDateJTextField.getText());
	v.add("NBU:" + delim + delim + __nbuDateJTextField.getText());
	v.add("ABR:" + delim + delim + __abrDateJTextField.getText());
	v.add("ABCO:" + delim + delim + __abcoDateJTextField.getText());

        return v;
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	int x=0;
	int y=0;
	int leftJPanelY=0;
	int centerJPanelY=0;
	int rightJPanelY=0;

        // objects used throughout the GUI layout
        Insets insetsTLBR = new Insets(4,4,4,4);
        Insets insetsTNNN = new Insets(14,0,0,0);
        Insets insetsTLNR = new Insets(4,4,0,4);
        Insets insetsNNNN = new Insets(0,0,0,0);
        Insets insetsNNNNS = new Insets(0,0,0,0);

        GridBagLayout gbl = new GridBagLayout();

        addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent evt) {
			closeClicked();
		}
	});

	// the top panel contains 3 main panels: left, center, and right
	// the bottom panel contains the status bar and buttons
        JPanel topJPanel = new JPanel();
        topJPanel.setLayout(gbl);
        getContentPane().add("North", topJPanel);

	// the left panel contains the owner name, well name, location, 
	// subdivision, case number, lot size, etc.

	// 
	// LEFT PANEL
	//
	JPanel leftJPanel = new JPanel();
	leftJPanel.setLayout(gbl);
	// topJPanel.add("West", leftJPanel);
        JGUIUtil.addComponent(topJPanel, leftJPanel, 
		0, 0, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);

	//
        // JPanel 0:  owner name and city
        JPanel panel_0 = new JPanel();
        panel_0.setLayout(gbl);
        JGUIUtil.addComponent(leftJPanel, panel_0, 
		0, leftJPanelY++, 1, 1, 0, 0, insetsTLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
/*
	The xref fields are commented out in HydroBase_WellApplication
	
        JGUIUtil.addComponent(panel_0, new JLabel("Name:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __xrefOwnerNameJTextField = new JTextField();
        __xrefOwnerNameJTextField.setText(__wellApp.getXrefOwnerName());
        JGUIUtil.addComponent(panel_0, __xrefOwnerNameJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
        JGUIUtil.addComponent(panel_0, new JLabel("City:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __xrefCityJTextField = new JTextField();
        __xrefCityJTextField.setText("" + __wellApp.getXrefCity());
        JGUIUtil.addComponent(panel_0, __xrefCityJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
*/	
	//
        // JPanel 1:  case_no, statute, well_name, xref_cty, xref_location
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(gbl);
        JGUIUtil.addComponent(leftJPanel, panel_1, 
		0, leftJPanelY++, 1, 1, 0, 0, insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	y=0;
	x=0;
        JGUIUtil.addComponent(panel_1, new JLabel("Case No.:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_1, new JLabel("XRef#:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        __caseNoJTextField = new JTextField();
	__caseNoJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__caseNoJTextField, __wellApp.getCase_no());
        JGUIUtil.addComponent(panel_1, __caseNoJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __wellxNoJTextField = new JTextField(5);
	__wellxNoJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__wellxNoJTextField, __wellApp.getWellxno());
        JGUIUtil.addComponent(panel_1, __wellxNoJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __wellxSufJTextField = new JTextField(3);
	__wellxSufJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__wellxSufJTextField, 
		__wellApp.getWellxsuf());
        JGUIUtil.addComponent(panel_1, __wellxSufJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __wellxRplJTextField = new JTextField(2);
	__wellxRplJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__wellxRplJTextField, 
		__wellApp.getWellxrpl());
        JGUIUtil.addComponent(panel_1, __wellxRplJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_1, new JLabel("Well Name:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_1, new JLabel("Statute:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        __wellNameJTextField = new JTextField();
	__wellNameJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__wellNameJTextField, 
		__wellApp.getWell_name());
        JGUIUtil.addComponent(panel_1, __wellNameJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __statuteJTextField = new JTextField();
	__statuteJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__statuteJTextField, __wellApp.getStatute());
        JGUIUtil.addComponent(panel_1, __statuteJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//
        // JPanel location:   county, location
        JPanel panel_location = new JPanel();
        panel_location.setLayout(gbl);
        JGUIUtil.addComponent(leftJPanel, panel_location, 
		0, leftJPanelY++, 1, 1, 0, 0, insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
	/*
        JGUIUtil.addComponent(panel_location, new JLabel("County:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        __xrefCtyJTextField = new JTextField();
        __xrefCtyJTextField.setText(__wellApp.getXrefCty());
        JGUIUtil.addComponent(panel_location, __xrefCtyJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
*/
/*
        JGUIUtil.addComponent(panel_location, 
		new JLabel("Location Coordinates:"),
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __xrefLocationJTextField = new JTextField();
        __xrefLocationJTextField.setText(__wellApp.getXrefLocation());
        JGUIUtil.addComponent(panel_location, __xrefLocationJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
*/
	//
        // JPanel 2:  subdivision_name, lot, block, filing, 
	// parcel_no, parcel_size
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(gbl);
        JGUIUtil.addComponent(leftJPanel, panel_2, 
		0, leftJPanelY++, 1, 1, 0, 0, insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0; y=0;
        JGUIUtil.addComponent(panel_2, new JLabel("Subdivision:"), 
		x, y++, 3, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __subdivNameJTextField = new JTextField();
	__subdivNameJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__subdivNameJTextField, 
		__wellApp.getSubdiv_name());
        JGUIUtil.addComponent(panel_2, __subdivNameJTextField, 
		x, y++, 3, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_2, new JLabel("Lot:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_2, new JLabel("Block:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_2, new JLabel("Filing:"), 
		x++, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        __lotJTextField = new JTextField();
	__lotJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__lotJTextField, __wellApp.getLot()
		+ "  ");
        JGUIUtil.addComponent(panel_2, __lotJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __blockJTextField = new JTextField();
	__blockJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__blockJTextField, __wellApp.getBlock()
		+ "  ");
        JGUIUtil.addComponent(panel_2, __blockJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
        __filingJTextField = new JTextField();
	__filingJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__filingJTextField, __wellApp.getFiling()
		+ "  ");
        JGUIUtil.addComponent(panel_2, __filingJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_2, new JLabel("Parcel ID:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        JGUIUtil.addComponent(panel_2, new JLabel("Acres in Tract:"), 
		x++, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        __parcelNoJTextField = new JTextField();
	__parcelNoJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__parcelNoJTextField, 
		__wellApp.getParcel_no());
        JGUIUtil.addComponent(panel_2, __parcelNoJTextField, 
		x++, y, 2, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x++;
        __parcelSizeJTextField = new JTextField();
	__parcelSizeJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__parcelSizeJTextField, 
		__wellApp.getParcel_size(), "%7.2f");
        JGUIUtil.addComponent(panel_2, __parcelSizeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
		
	//
	// CENTER PANEL
	//
	JPanel centerJPanel = new JPanel();
	centerJPanel.setLayout(gbl);
	// topJPanel.add("Center", centerJPanel);
        JGUIUtil.addComponent(topJPanel, centerJPanel, 
		1, 0, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	//
        // panel3: use1, use2, use3, area_irr, irr_meas, pyeild, yield,
	// pacreft, acreft, pdepth, depth, elev, tperf, bperf, level,
	// driller_lic, pump_lic, meter, log, abreq, qual, comment
        JPanel panel_3 = new JPanel();
        panel_3.setLayout(gbl);
        JGUIUtil.addComponent(centerJPanel, panel_3, 
		0, centerJPanelY++, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Use 1:"), 
		x, y++, 4, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __use1JTextField = new JTextField();
	__use1JTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__use1JTextField, __wellApp.getUse1());
        JGUIUtil.addComponent(panel_3, __use1JTextField, 
		x, y++, 4, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(panel_3, new JLabel("Use 2:"), 
		x, y++, 4, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __use2JTextField = new JTextField();
	__use2JTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__use2JTextField, __wellApp.getUse2());
        JGUIUtil.addComponent(panel_3, __use2JTextField, 
		x, y++, 4, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(panel_3, new JLabel("Use 3:"), 
		x, y++, 4, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __use3JTextField = new JTextField();
	__use3JTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__use3JTextField, __wellApp.getUse3());
        JGUIUtil.addComponent(panel_3, __use3JTextField, 
		x, y++, 4, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// area irrigated
        JGUIUtil.addComponent(panel_3, new JLabel("Area Irrigated:"), 
		x, y++, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __areaIrrJTextField = new JTextField();
	__areaIrrJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__areaIrrJTextField, 
		__wellApp.getArea_irr(), "%8.2f");
        JGUIUtil.addComponent(panel_3, __areaIrrJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __irrMeasJTextField = new JTextField();
	__irrMeasJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__irrMeasJTextField,__wellApp.getIrr_meas());
        JGUIUtil.addComponent(panel_3, __irrMeasJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//
	// proposed/actual yield, acreft, depth
	x=1;	// skip first column
        JGUIUtil.addComponent(panel_3, new JLabel("Pump Rate:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Acre Feet:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Depth (ft):"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Proposed:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __pyieldJTextField = new JTextField();
	__pyieldJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__pyieldJTextField, 
		__wellApp.getPyield(), "%8.2f");
        JGUIUtil.addComponent(panel_3, __pyieldJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __pacreftJTextField = new JTextField();
	__pacreftJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__pacreftJTextField, 
		__wellApp.getPacreft(), "%8.2f");
        JGUIUtil.addComponent(panel_3, __pacreftJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
        __pdepthJTextField = new JTextField();
	__pdepthJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__pdepthJTextField, __wellApp.getPdepth());
        JGUIUtil.addComponent(panel_3, __pdepthJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Actual:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __yieldJTextField = new JTextField();
	__yieldJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__yieldJTextField, 
		__wellApp.getYield(), "%8.2f");
        JGUIUtil.addComponent(panel_3, __yieldJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __acreFtJTextField = new JTextField();
	__acreFtJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__acreFtJTextField, 
		__wellApp.getAcreft(), "%8.2f");
        JGUIUtil.addComponent(panel_3, __acreFtJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
        __depthJTextField = new JTextField();
	__depthJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__depthJTextField, __wellApp.getDepth());
        JGUIUtil.addComponent(panel_3, __depthJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// elevation, top, bottom, water level
	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Elevation:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Top:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Bottom:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Water Level:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	
	x=0;
        __elevJTextField = new JTextField();
	__elevJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__elevJTextField, __wellApp.getElev(),"%8d");
        JGUIUtil.addComponent(panel_3, __elevJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __tperfJTextField = new JTextField();
	__tperfJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__tperfJTextField, __wellApp.getTperf());
        JGUIUtil.addComponent(panel_3, __tperfJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __bperfJTextField = new JTextField();
	__bperfJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__bperfJTextField, __wellApp.getBperf());
        JGUIUtil.addComponent(panel_3, __bperfJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __levelJTextField = new JTextField();
	__levelJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__levelJTextField, __wellApp.getLevel());
        JGUIUtil.addComponent(panel_3, __levelJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// aquifers
	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Aquifers:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __aquifer1JTextField = new JTextField();
	__aquifer1JTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__aquifer1JTextField, 
		__wellApp.getAquifer1());
        JGUIUtil.addComponent(panel_3, __aquifer1JTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __aquifer2JTextField = new JTextField();
	__aquifer2JTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__aquifer2JTextField, 
		__wellApp.getAquifer2());
        JGUIUtil.addComponent(panel_3, __aquifer2JTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// driller, pump licenses
	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Driller:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Pump:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        __drillerLicJTextField = new JTextField();
	__drillerLicJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__drillerLicJTextField, 
		__wellApp.getDriller_lic());
        JGUIUtil.addComponent(panel_3, __drillerLicJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __pumpLicJTextField = new JTextField();
	__pumpLicJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__pumpLicJTextField, 
		__wellApp.getPump_lic());
        JGUIUtil.addComponent(panel_3, __pumpLicJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Flow Meter:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Geo. Log:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Qual:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Abandon:"), 
		x++, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
	int tempInt = __wellApp.getMeter();
	__meterJCheckBox = new JCheckBox();
	__meterJCheckBox.setEnabled(false);
	if (tempInt == 1) {
		__meterJCheckBox.setSelected(true);
	}
        JGUIUtil.addComponent(panel_3, __meterJCheckBox,
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	tempInt = __wellApp.getLog();
        __logJCheckBox = new JCheckBox();
	__logJCheckBox.setEnabled(false);
	if (tempInt == 1) {
		__logJCheckBox.setSelected(true);
	}
        JGUIUtil.addComponent(panel_3, __logJCheckBox, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	tempInt = __wellApp.getQual();
        __qualJCheckBox = new JCheckBox();
	__qualJCheckBox.setEnabled(false);
	if (tempInt == 1) {
		__qualJCheckBox.setSelected(true);
	}
        JGUIUtil.addComponent(panel_3, __qualJCheckBox, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	tempInt = __wellApp.getAbreq();
        __abreqJCheckBox = new JCheckBox();
	__abreqJCheckBox.setEnabled(false);
	if (tempInt == 1) {
		__abreqJCheckBox.setSelected(true);
	}
        JGUIUtil.addComponent(panel_3, __abreqJCheckBox, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Comment:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __commentJTextField = new JTextField(40);
	__commentJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__commentJTextField, __wellApp.getComment());
        JGUIUtil.addComponent(panel_3, __commentJTextField, 
		x, y++, 4, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//
	// RIGHT PANEL
	//
	JPanel rightJPanel = new JPanel();
	rightJPanel.setLayout(gbl);
	// topJPanel.add("East", rightJPanel);
        JGUIUtil.addComponent(topJPanel, rightJPanel, 
		2, 0, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	//
        // panel_Receipt: receipt and permit
        JPanel panel_Receipt = new JPanel();
        panel_Receipt.setLayout(gbl);
        JGUIUtil.addComponent(rightJPanel, panel_Receipt, 
		0, rightJPanelY++, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_Receipt, new JLabel("Receipt:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __receiptJTextField = new JTextField();
	__receiptJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__receiptJTextField, __wellApp.getReceipt());
        JGUIUtil.addComponent(panel_Receipt, __receiptJTextField, 
		x, y++, 3, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_Receipt, new JLabel("Permit:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __permitNoJTextField = new JTextField(5);
	__permitNoJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__permitNoJTextField, 
		__wellApp.getPermitno());
        JGUIUtil.addComponent(panel_Receipt, __permitNoJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __permitSufJTextField = new JTextField(5);
	__permitSufJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__permitSufJTextField, 
		__wellApp.getPermitsuf());
        JGUIUtil.addComponent(panel_Receipt, __permitSufJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __permitRplJTextField = new JTextField(5);
	__permitRplJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__permitRplJTextField, 
		__wellApp.getPermitrpl());
        JGUIUtil.addComponent(panel_Receipt, __permitRplJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//
        // panel__div: div, wd, user, basin, md, engineer
        JPanel panel__div = new JPanel();
        panel__div.setLayout(gbl);
        JGUIUtil.addComponent(rightJPanel, panel__div, 
		0, rightJPanelY++, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0; y=0;
        JGUIUtil.addComponent(panel__div, new JLabel("Div:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __divJTextField = new JTextField(3);
	__divJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__divJTextField, __wellApp.getDiv());
        JGUIUtil.addComponent(panel__div, __divJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
  
        JGUIUtil.addComponent(panel__div, new JLabel("WD:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __wdJTextField = new JTextField(4);
	__wdJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__wdJTextField, __wellApp.getWD());
        JGUIUtil.addComponent(panel__div, __wdJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
  
	x=0;
        JGUIUtil.addComponent(panel__div, new JLabel("Bas:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __basinJTextField = new JTextField(3);
	__basinJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__basinJTextField, __wellApp.getBasin());
        JGUIUtil.addComponent(panel__div, __basinJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
  
        JGUIUtil.addComponent(panel__div, new JLabel("MD:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __mdJTextField = new JTextField(4);
	__mdJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__mdJTextField, __wellApp.getMD());
        JGUIUtil.addComponent(panel__div, __mdJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
  
	x=0;
        JGUIUtil.addComponent(panel__div, new JLabel("Eng:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __engineerJTextField = new JTextField(3);
	__engineerJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__engineerJTextField, 
		__wellApp.getEngineer());
        JGUIUtil.addComponent(panel__div, __engineerJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
  
        JGUIUtil.addComponent(panel__div, new JLabel("User:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __userJTextField = new JTextField(4);
	__userJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__userJTextField, __wellApp.getUser());
        JGUIUtil.addComponent(panel__div, __userJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//
        // panel_dates: act, stat, tran, np, ex, notice, wa, wc, 
	// nwc, pi, pc, sa, sbu, nbu, abr, abco
        JPanel panel_dates = new JPanel();
        panel_dates.setLayout(gbl);
        JGUIUtil.addComponent(rightJPanel, panel_dates, 
		0, rightJPanelY++, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0; y=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("Act:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __actCodeJTextField = new JTextField(3);
	__actCodeJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__actCodeJTextField, __wellApp.getActcode());
        JGUIUtil.addComponent(panel_dates, __actCodeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __actDateJTextField = new JTextField(9);
	__actDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__actDateJTextField, __wellApp.getActdate());
        JGUIUtil.addComponent(panel_dates, __actDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("Stat:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __statCodeJTextField = new JTextField(3);
	__statCodeJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__statCodeJTextField, 
		__wellApp.getStatcode());
        JGUIUtil.addComponent(panel_dates, __statCodeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __statDateJTextField = new JTextField(9);
	__statDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__statDateJTextField, 
		__wellApp.getStatdate());
        JGUIUtil.addComponent(panel_dates, __statDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("Tran:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __tranCodeJTextField = new JTextField(3);
	__tranCodeJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__tranCodeJTextField, 
		__wellApp.getTrancode());
        JGUIUtil.addComponent(panel_dates, __tranCodeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __tranDateJTextField = new JTextField(9);
	__tranDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__tranDateJTextField, 
		__wellApp.getTrandate());
        JGUIUtil.addComponent(panel_dates, __tranDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
  
	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("NP:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __npDateJTextField = new JTextField(9);
	__npDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__npDateJTextField, __wellApp.getNpdate());
        JGUIUtil.addComponent(panel_dates, __npDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("EX:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __exDateJTextField = new JTextField(9);
	__exDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__exDateJTextField, __wellApp.getExdate());
        JGUIUtil.addComponent(panel_dates, __exDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("Notice:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __noticeDateJTextField = new JTextField(9);
	__noticeDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__noticeDateJTextField, 
		__wellApp.getNoticedate());
        JGUIUtil.addComponent(panel_dates, __noticeDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("WA:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __waDateJTextField = new JTextField(9);
	__waDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__waDateJTextField, __wellApp.getWadate());
        JGUIUtil.addComponent(panel_dates, __waDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("WC:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __wcDateJTextField = new JTextField(9);
	__wcDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__wcDateJTextField, __wellApp.getWcdate());
        JGUIUtil.addComponent(panel_dates, __wcDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("NWC:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __nwcDateJTextField = new JTextField(9);
	__nwcDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__nwcDateJTextField, __wellApp.getNwcdate());
        JGUIUtil.addComponent(panel_dates, __nwcDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("PI:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __piDateJTextField = new JTextField(9);
	__piDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__piDateJTextField, __wellApp.getPidate());
        JGUIUtil.addComponent(panel_dates, __piDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("PC:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __pcDateJTextField = new JTextField(9);
	__pcDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__pcDateJTextField, __wellApp.getPcdate());
        JGUIUtil.addComponent(panel_dates, __pcDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("SA:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __saDateJTextField = new JTextField(9);
	__saDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__saDateJTextField, __wellApp.getSadate());
        JGUIUtil.addComponent(panel_dates, __saDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("SBU:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __sbuDateJTextField = new JTextField(9);
	__sbuDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__sbuDateJTextField, __wellApp.getSbudate());
        JGUIUtil.addComponent(panel_dates, __sbuDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("NBU:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __nbuDateJTextField = new JTextField(9);
	__nbuDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__nbuDateJTextField, __wellApp.getNbudate());
        JGUIUtil.addComponent(panel_dates, __nbuDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("ABR:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __abrDateJTextField = new JTextField(9);
	__abrDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__abrDateJTextField, __wellApp.getAbrdate());
        JGUIUtil.addComponent(panel_dates, __abrDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("ABCO:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __abcoDateJTextField = new JTextField(9);
	__abcoDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__abcoDateJTextField, 
		__wellApp.getAbcodate());
        JGUIUtil.addComponent(panel_dates, __abcoDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
  
        // Bottom JPanel
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);

        // button panel
        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add("North", buttonJPanel);

        __printJButton = new JButton(__BUTTON_PRINT);
	__printJButton.setToolTipText("Print form data.");
        __printJButton.addActionListener(this);
        buttonJPanel.add(__printJButton);

        __exportJButton = new JButton(__BUTTON_EXPORT);
	__exportJButton.setToolTipText("Export data to a file.");
        __exportJButton.addActionListener(this);
        buttonJPanel.add(__exportJButton);

        __closeJButton = new JButton(__BUTTON_CLOSE);
	__closeJButton.setToolTipText("Close form.");
        __closeJButton.addActionListener(this);
        buttonJPanel.add(__closeJButton);

//        __helpJButton = new JButton(__BUTTON_HELP);
//        __helpJButton.addActionListener(this);
//	__helpJButton.setEnabled(false);
//        buttonJPanel.add(__helpJButton);

        // Bottom: South: South JPanel
        JPanel statusJPanel = new JPanel();
        statusJPanel.setLayout(gbl);
        bottomJPanel.add("South", statusJPanel);

        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(statusJPanel, __statusJTextField, 
		0, 1, 10, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // Frame settings
	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}	
        setTitle(app + "Well Permit");
        pack();

	// increase the width so that all columns will be visible in
	// the equipment list.
	Dimension d = getSize();
	setSize(d.width + 50, d.height);

	JGUIUtil.center(this);
}

/**
Sets up the GUI.
*/
private void setupViewGUI() {
	int x=0;
	int y=0;
	int leftJPanelY=0;
	int centerJPanelY=0;
	int rightJPanelY=0;

        // objects used throughout the GUI layout
        Insets insetsTLBR = new Insets(4,4,4,4);
        Insets insetsTNNN = new Insets(14,0,0,0);
        Insets insetsTLNR = new Insets(4,4,0,4);
        Insets insetsNNNN = new Insets(0,0,0,0);
        Insets insetsNNNNS = new Insets(0,0,0,0);

        GridBagLayout gbl = new GridBagLayout();

        addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent evt) {
			closeClicked();
		}
	});

	// the top panel contains 3 main panels: left, center, and right
	// the bottom panel contains the status bar and buttons
        JPanel topJPanel = new JPanel();
        topJPanel.setLayout(gbl);
        getContentPane().add("North", topJPanel);

	// the left panel contains the owner name, well name, location, 
	// subdivision, case number, lot size, etc.

	// 
	// LEFT PANEL
	//
	JPanel leftJPanel = new JPanel();
	leftJPanel.setLayout(gbl);
	// topJPanel.add("West", leftJPanel);
        JGUIUtil.addComponent(topJPanel, leftJPanel, 
		0, 0, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);

	//
        // JPanel 0:  owner name and city
        JPanel panel_0 = new JPanel();
        panel_0.setLayout(gbl);
        JGUIUtil.addComponent(leftJPanel, panel_0, 
		0, leftJPanelY++, 1, 1, 0, 0, insetsTLBR, GridBagConstraints.NONE, GridBagConstraints.WEST);
/*
	The xref fields are commented out in HydroBase_WellApplication
	
        JGUIUtil.addComponent(panel_0, new JLabel("Name:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __xrefOwnerNameJTextField = new JTextField();
        __xrefOwnerNameJTextField.setText(__wellView.getXrefOwnerName());
        JGUIUtil.addComponent(panel_0, __xrefOwnerNameJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
        JGUIUtil.addComponent(panel_0, new JLabel("City:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __xrefCityJTextField = new JTextField();
        __xrefCityJTextField.setText("" + __wellView.getXrefCity());
        JGUIUtil.addComponent(panel_0, __xrefCityJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
*/	
	//
        // JPanel 1:  case_no, statute, well_name, xref_cty, xref_location
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(gbl);
        JGUIUtil.addComponent(leftJPanel, panel_1, 
		0, leftJPanelY++, 1, 1, 0, 0, insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	y=0;
	x=0;
        JGUIUtil.addComponent(panel_1, new JLabel("Case No.:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_1, new JLabel("XRef#:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        __caseNoJTextField = new JTextField();
	__caseNoJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__caseNoJTextField, __wellView.getCase_no());
        JGUIUtil.addComponent(panel_1, __caseNoJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __wellxNoJTextField = new JTextField(5);
	__wellxNoJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__wellxNoJTextField, 
		__wellView.getWellxno());
        JGUIUtil.addComponent(panel_1, __wellxNoJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __wellxSufJTextField = new JTextField(3);
	__wellxSufJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__wellxSufJTextField, 
		__wellView.getWellxsuf());
        JGUIUtil.addComponent(panel_1, __wellxSufJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __wellxRplJTextField = new JTextField(2);
	__wellxRplJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__wellxRplJTextField, 
		__wellView.getWellxrpl());
        JGUIUtil.addComponent(panel_1, __wellxRplJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_1, new JLabel("Well Name:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_1, new JLabel("Statute:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        __wellNameJTextField = new JTextField();
	__wellNameJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__wellNameJTextField, 
		__wellView.getWell_name());
        JGUIUtil.addComponent(panel_1, __wellNameJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __statuteJTextField = new JTextField();
	__statuteJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__statuteJTextField, 
		__wellView.getStatute());
        JGUIUtil.addComponent(panel_1, __statuteJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//
        // JPanel location:   county, location
        JPanel panel_location = new JPanel();
        panel_location.setLayout(gbl);
        JGUIUtil.addComponent(leftJPanel, panel_location, 
		0, leftJPanelY++, 1, 1, 0, 0, insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
	/*
        JGUIUtil.addComponent(panel_location, new JLabel("County:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        __xrefCtyJTextField = new JTextField();
        __xrefCtyJTextField.setText(__wellView.getXrefCty());
        JGUIUtil.addComponent(panel_location, __xrefCtyJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
*/
/*
        JGUIUtil.addComponent(panel_location, 
		new JLabel("Location Coordinates:"),
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __xrefLocationJTextField = new JTextField();
        __xrefLocationJTextField.setText(__wellView.getXrefLocation());
        JGUIUtil.addComponent(panel_location, __xrefLocationJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
*/
	//
        // JPanel 2:  subdivision_name, lot, block, filing, 
	// parcel_no, parcel_size
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(gbl);
        JGUIUtil.addComponent(leftJPanel, panel_2, 
		0, leftJPanelY++, 1, 1, 0, 0, insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0; y=0;
        JGUIUtil.addComponent(panel_2, new JLabel("Subdivision:"), 
		x, y++, 3, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __subdivNameJTextField = new JTextField();
	__subdivNameJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__subdivNameJTextField, 
		__wellView.getSubdiv_name());
        JGUIUtil.addComponent(panel_2, __subdivNameJTextField, 
		x, y++, 3, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_2, new JLabel("Lot:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_2, new JLabel("Block:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_2, new JLabel("Filing:"), 
		x++, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        __lotJTextField = new JTextField();
	__lotJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__lotJTextField, __wellView.getLot()
		+ "  ");
        JGUIUtil.addComponent(panel_2, __lotJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __blockJTextField = new JTextField();
	__blockJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__blockJTextField, __wellView.getBlock()
		+ "  ");
        JGUIUtil.addComponent(panel_2, __blockJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
        __filingJTextField = new JTextField();
	__filingJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__filingJTextField, __wellView.getFiling()
		+ "  ");
        JGUIUtil.addComponent(panel_2, __filingJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_2, new JLabel("Parcel ID:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        JGUIUtil.addComponent(panel_2, new JLabel("Acres in Tract:"), 
		x++, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        __parcelNoJTextField = new JTextField();
	__parcelNoJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__parcelNoJTextField, 
		__wellView.getParcel_no());
        JGUIUtil.addComponent(panel_2, __parcelNoJTextField, 
		x++, y, 2, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x++;
        __parcelSizeJTextField = new JTextField();
	__parcelSizeJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__parcelSizeJTextField, 
		__wellView.getParcel_size(), "%7.2f");
        JGUIUtil.addComponent(panel_2, __parcelSizeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
		
	//
	// CENTER PANEL
	//
	JPanel centerJPanel = new JPanel();
	centerJPanel.setLayout(gbl);
	// topJPanel.add("Center", centerJPanel);
        JGUIUtil.addComponent(topJPanel, centerJPanel, 
		1, 0, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	//
        // panel3: use1, use2, use3, area_irr, irr_meas, pyeild, yield,
	// pacreft, acreft, pdepth, depth, elev, tperf, bperf, level,
	// driller_lic, pump_lic, meter, log, abreq, qual, comment
        JPanel panel_3 = new JPanel();
        panel_3.setLayout(gbl);
        JGUIUtil.addComponent(centerJPanel, panel_3, 
		0, centerJPanelY++, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Use 1:"), 
		x, y++, 4, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __use1JTextField = new JTextField();
	__use1JTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__use1JTextField, __wellView.getUse1());
        JGUIUtil.addComponent(panel_3, __use1JTextField, 
		x, y++, 4, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(panel_3, new JLabel("Use 2:"), 
		x, y++, 4, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __use2JTextField = new JTextField();
	__use2JTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__use2JTextField, __wellView.getUse2());
        JGUIUtil.addComponent(panel_3, __use2JTextField, 
		x, y++, 4, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(panel_3, new JLabel("Use 3:"), 
		x, y++, 4, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __use3JTextField = new JTextField();
	__use3JTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__use3JTextField, __wellView.getUse3());
        JGUIUtil.addComponent(panel_3, __use3JTextField, 
		x, y++, 4, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// area irrigated
        JGUIUtil.addComponent(panel_3, new JLabel("Area Irrigated:"), 
		x, y++, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __areaIrrJTextField = new JTextField();
	__areaIrrJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__areaIrrJTextField, 
		__wellView.getArea_irr(), "%8.2f");
        JGUIUtil.addComponent(panel_3, __areaIrrJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __irrMeasJTextField = new JTextField();
	__irrMeasJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__irrMeasJTextField,
		__wellView.getIrr_meas());
        JGUIUtil.addComponent(panel_3, __irrMeasJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//
	// proposed/actual yield, acreft, depth
	x=1;	// skip first column
        JGUIUtil.addComponent(panel_3, new JLabel("Pump Rate:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Acre Feet:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Depth (ft):"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Proposed:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __pyieldJTextField = new JTextField();
	__pyieldJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__pyieldJTextField, 
		__wellView.getPyield(), "%8.2f");
        JGUIUtil.addComponent(panel_3, __pyieldJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __pacreftJTextField = new JTextField();
	__pacreftJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__pacreftJTextField, 
		__wellView.getPacreft(), "%8.2f");
        JGUIUtil.addComponent(panel_3, __pacreftJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
        __pdepthJTextField = new JTextField();
	__pdepthJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__pdepthJTextField, __wellView.getPdepth());
        JGUIUtil.addComponent(panel_3, __pdepthJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Actual:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __yieldJTextField = new JTextField();
	__yieldJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__yieldJTextField, 
		__wellView.getYield(), "%8.2f");
        JGUIUtil.addComponent(panel_3, __yieldJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __acreFtJTextField = new JTextField();
	__acreFtJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__acreFtJTextField, 
		__wellView.getAcreft(), "%8.2f");
        JGUIUtil.addComponent(panel_3, __acreFtJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
        __depthJTextField = new JTextField();
	__depthJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__depthJTextField, __wellView.getDepth());
        JGUIUtil.addComponent(panel_3, __depthJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// elevation, top, bottom, water level
	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Elevation:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Top:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Bottom:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Water Level:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	
	x=0;
        __elevJTextField = new JTextField();
	__elevJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__elevJTextField, 
		__wellView.getElev(),"%8d");
        JGUIUtil.addComponent(panel_3, __elevJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __tperfJTextField = new JTextField();
	__tperfJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__tperfJTextField, __wellView.getTperf());
        JGUIUtil.addComponent(panel_3, __tperfJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __bperfJTextField = new JTextField();
	__bperfJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__bperfJTextField, __wellView.getBperf());
        JGUIUtil.addComponent(panel_3, __bperfJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __levelJTextField = new JTextField();
	__levelJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__levelJTextField, __wellView.getLevel());
        JGUIUtil.addComponent(panel_3, __levelJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// aquifers
	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Aquifers:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __aquifer1JTextField = new JTextField();
	__aquifer1JTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__aquifer1JTextField, 
		__wellView.getAquifer1());
        JGUIUtil.addComponent(panel_3, __aquifer1JTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __aquifer2JTextField = new JTextField();
	__aquifer2JTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__aquifer2JTextField, 
		__wellView.getAquifer2());
        JGUIUtil.addComponent(panel_3, __aquifer2JTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	// driller, pump licenses
	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Driller:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Pump:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        __drillerLicJTextField = new JTextField();
	__drillerLicJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__drillerLicJTextField, 
		__wellView.getDriller_lic());
        JGUIUtil.addComponent(panel_3, __drillerLicJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __pumpLicJTextField = new JTextField();
	__pumpLicJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__pumpLicJTextField, 
		__wellView.getPump_lic());
        JGUIUtil.addComponent(panel_3, __pumpLicJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Flow Meter:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Geo. Log:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Qual:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Abandon:"), 
		x++, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
	int tempInt = __wellView.getMeter();
	__meterJCheckBox = new JCheckBox();
	__meterJCheckBox.setEnabled(false);
	if (tempInt == 1) {
		__meterJCheckBox.setSelected(true);
	}
        JGUIUtil.addComponent(panel_3, __meterJCheckBox,
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	tempInt = __wellView.getLog();
        __logJCheckBox = new JCheckBox();
	__logJCheckBox.setEnabled(false);
	if (tempInt == 1) {
		__logJCheckBox.setSelected(true);
	}
        JGUIUtil.addComponent(panel_3, __logJCheckBox, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	tempInt = __wellView.getQual();
        __qualJCheckBox = new JCheckBox();
	__qualJCheckBox.setEnabled(false);
	if (tempInt == 1) {
		__qualJCheckBox.setSelected(true);
	}
        JGUIUtil.addComponent(panel_3, __qualJCheckBox, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	tempInt = __wellView.getAbreq();
        __abreqJCheckBox = new JCheckBox();
	__abreqJCheckBox.setEnabled(false);
	if (tempInt == 1) {
		__abreqJCheckBox.setSelected(true);
	}
        JGUIUtil.addComponent(panel_3, __abreqJCheckBox, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_3, new JLabel("Comment:"), 
		x, y++, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __commentJTextField = new JTextField(40);
	__commentJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__commentJTextField, 
		__wellView.getComment());
        JGUIUtil.addComponent(panel_3, __commentJTextField, 
		x, y++, 4, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//
	// RIGHT PANEL
	//
	JPanel rightJPanel = new JPanel();
	rightJPanel.setLayout(gbl);
	// topJPanel.add("East", rightJPanel);
        JGUIUtil.addComponent(topJPanel, rightJPanel, 
		2, 0, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	//
        // panel_Receipt: receipt and permit
        JPanel panel_Receipt = new JPanel();
        panel_Receipt.setLayout(gbl);
        JGUIUtil.addComponent(rightJPanel, panel_Receipt, 
		0, rightJPanelY++, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_Receipt, new JLabel("Receipt:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __receiptJTextField = new JTextField();
	__receiptJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__receiptJTextField, 
		__wellView.getReceipt());
        JGUIUtil.addComponent(panel_Receipt, __receiptJTextField, 
		x, y++, 3, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_Receipt, new JLabel("Permit:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __permitNoJTextField = new JTextField(5);
	__permitNoJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__permitNoJTextField, 
		__wellView.getPermitno());
        JGUIUtil.addComponent(panel_Receipt, __permitNoJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __permitSufJTextField = new JTextField(5);
	__permitSufJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__permitSufJTextField, 
		__wellView.getPermitsuf());
        JGUIUtil.addComponent(panel_Receipt, __permitSufJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __permitRplJTextField = new JTextField(5);
	__permitRplJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__permitRplJTextField, 
		__wellView.getPermitrpl());
        JGUIUtil.addComponent(panel_Receipt, __permitRplJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//
        // panel__div: div, wd, user, basin, md, engineer
        JPanel panel__div = new JPanel();
        panel__div.setLayout(gbl);
        JGUIUtil.addComponent(rightJPanel, panel__div, 
		0, rightJPanelY++, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0; y=0;
        JGUIUtil.addComponent(panel__div, new JLabel("Div:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __divJTextField = new JTextField(3);
	__divJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__divJTextField, __wellView.getDiv());
        JGUIUtil.addComponent(panel__div, __divJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
  
        JGUIUtil.addComponent(panel__div, new JLabel("WD:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __wdJTextField = new JTextField(4);
	__wdJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__wdJTextField, __wellView.getWD());
        JGUIUtil.addComponent(panel__div, __wdJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
  
	x=0;
        JGUIUtil.addComponent(panel__div, new JLabel("Bas:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __basinJTextField = new JTextField(3);
	__basinJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__basinJTextField, __wellView.getBasin());
        JGUIUtil.addComponent(panel__div, __basinJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
  
        JGUIUtil.addComponent(panel__div, new JLabel("MD:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __mdJTextField = new JTextField(4);
	__mdJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__mdJTextField, __wellView.getMD());
        JGUIUtil.addComponent(panel__div, __mdJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
  
	x=0;
        JGUIUtil.addComponent(panel__div, new JLabel("Eng:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __engineerJTextField = new JTextField(3);
	__engineerJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__engineerJTextField, 
		__wellView.getEngineer());
        JGUIUtil.addComponent(panel__div, __engineerJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
  
        JGUIUtil.addComponent(panel__div, new JLabel("User:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.EAST);

        __userJTextField = new JTextField(4);
	__userJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__userJTextField, __wellView.getUser());
        JGUIUtil.addComponent(panel__div, __userJTextField, 
		x, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	//
        // panel_dates: act, stat, tran, np, ex, notice, wa, wc, 
	// nwc, pi, pc, sa, sbu, nbu, abr, abco
        JPanel panel_dates = new JPanel();
        panel_dates.setLayout(gbl);
        JGUIUtil.addComponent(rightJPanel, panel_dates, 
		0, rightJPanelY++, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0; y=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("Act:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __actCodeJTextField = new JTextField(3);
	__actCodeJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__actCodeJTextField, 
		__wellView.getActcode());
        JGUIUtil.addComponent(panel_dates, __actCodeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __actDateJTextField = new JTextField(9);
	__actDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__actDateJTextField, 
		__wellView.getActdate());
        JGUIUtil.addComponent(panel_dates, __actDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("Stat:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __statCodeJTextField = new JTextField(3);
	__statCodeJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__statCodeJTextField, 
		__wellView.getStatcode());
        JGUIUtil.addComponent(panel_dates, __statCodeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __statDateJTextField = new JTextField(9);
	__statDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__statDateJTextField, 
		__wellView.getStatdate());
        JGUIUtil.addComponent(panel_dates, __statDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("Tran:"), 
		x++, y, 1, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __tranCodeJTextField = new JTextField(3);
	__tranCodeJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__tranCodeJTextField, 
		__wellView.getTrancode());
        JGUIUtil.addComponent(panel_dates, __tranCodeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __tranDateJTextField = new JTextField(9);
	__tranDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__tranDateJTextField, 
		__wellView.getTrandate());
        JGUIUtil.addComponent(panel_dates, __tranDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
  
	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("NP:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __npDateJTextField = new JTextField(9);
	__npDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__npDateJTextField, __wellView.getNpdate());
        JGUIUtil.addComponent(panel_dates, __npDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("EX:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __exDateJTextField = new JTextField(9);
	__exDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__exDateJTextField, __wellView.getExdate());
        JGUIUtil.addComponent(panel_dates, __exDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("Notice:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __noticeDateJTextField = new JTextField(9);
	__noticeDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__noticeDateJTextField, 
		__wellView.getNoticedate());
        JGUIUtil.addComponent(panel_dates, __noticeDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("WA:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __waDateJTextField = new JTextField(9);
	__waDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__waDateJTextField, __wellView.getWadate());
        JGUIUtil.addComponent(panel_dates, __waDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("WC:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __wcDateJTextField = new JTextField(9);
	__wcDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__wcDateJTextField, __wellView.getWcdate());
        JGUIUtil.addComponent(panel_dates, __wcDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("NWC:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __nwcDateJTextField = new JTextField(9);
	__nwcDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__nwcDateJTextField, 
		__wellView.getNwcdate());
        JGUIUtil.addComponent(panel_dates, __nwcDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("PI:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __piDateJTextField = new JTextField(9);
	__piDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__piDateJTextField, __wellView.getPidate());
        JGUIUtil.addComponent(panel_dates, __piDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("PC:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __pcDateJTextField = new JTextField(9);
	__pcDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__pcDateJTextField, __wellView.getPcdate());
        JGUIUtil.addComponent(panel_dates, __pcDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("SA:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __saDateJTextField = new JTextField(9);
	__saDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__saDateJTextField, __wellView.getSadate());
        JGUIUtil.addComponent(panel_dates, __saDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("SBU:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __sbuDateJTextField = new JTextField(9);
	__sbuDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__sbuDateJTextField, 
		__wellView.getSbudate());
        JGUIUtil.addComponent(panel_dates, __sbuDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("NBU:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __nbuDateJTextField = new JTextField(9);
	__nbuDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__nbuDateJTextField, 
		__wellView.getNbudate());
        JGUIUtil.addComponent(panel_dates, __nbuDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("ABR:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __abrDateJTextField = new JTextField(9);
	__abrDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__abrDateJTextField, 
		__wellView.getAbrdate());
        JGUIUtil.addComponent(panel_dates, __abrDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_dates, new JLabel("ABCO:"), 
		x++, y, 2, 1, 0, 0, insetsNNNNS, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;
        __abcoDateJTextField = new JTextField(9);
	__abcoDateJTextField.setEditable(false);
        HydroBase_GUI_Util.setText(__abcoDateJTextField, 
		__wellView.getAbcodate());
        JGUIUtil.addComponent(panel_dates, __abcoDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
  
        // Bottom JPanel
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);

        // button panel
        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add("North", buttonJPanel);

        __printJButton = new JButton(__BUTTON_PRINT);
	__printJButton.setToolTipText("Print form data.");
        __printJButton.addActionListener(this);
        buttonJPanel.add(__printJButton);

        __exportJButton = new JButton(__BUTTON_EXPORT);
	__exportJButton.setToolTipText("Export data to a file.");
        __exportJButton.addActionListener(this);
        buttonJPanel.add(__exportJButton);

        __closeJButton = new JButton(__BUTTON_CLOSE);
	__closeJButton.setToolTipText("Close form.");
        __closeJButton.addActionListener(this);
        buttonJPanel.add(__closeJButton);

//        __helpJButton = new JButton(__BUTTON_HELP);
//        __helpJButton.addActionListener(this);
//	__helpJButton.setEnabled(false);
//        buttonJPanel.add(__helpJButton);

        // Bottom: South: South JPanel
        JPanel statusJPanel = new JPanel();
        statusJPanel.setLayout(gbl);
        bottomJPanel.add("South", statusJPanel);

        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(statusJPanel, __statusJTextField, 
		0, 1, 10, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // Frame settings
	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}	
        setTitle(app + "Well Permit");
        pack();

	// increase the width so that all columns will be visible in
	// the equipment list.
	Dimension d = getSize();
	setSize(d.width + 50, d.height);

	JGUIUtil.center(this);
}

/**
Sets the GUI visible or not.
@param state if true, the GUI is made visible.
*/
public synchronized void setVisible(boolean state) {
        super.setVisible(state);
        if (state) {
                Message.setTopLevel(this);
        }
}

}
