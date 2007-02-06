//-----------------------------------------------------------------------------
// HydroBase_GUI_WaterRight - Water Right Detail GUI for displaying NetAmts
//	and Transact data.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 17 Oct 2000	Catherine E. Nutting-Lane, RTi
//				Started initial version.
// 23 Oct 2000	CEN, RTi	Completed initial version.
// 16 Jul 2001	SAM, RTi	Change "Prior No.:" to "Prior/Case No.:" to
//				agree with the data dictionary.  Get rid of
//				extra new String()calls.  Change GUI to
//				GUIUtil.  Add finalize().
// 2002-02-27	SAM, RTi	Set the status text field background to light
//				gray.
// 2002-08-22	SAM, RTi	Fix location fields.  County was not being
//				displayed and all other fields were shifted to
//				the left.
//-----------------------------------------------------------------------------
// 2003-03-13	J. Thomas Sapienza, RTi	Initial SWING version.
// 2003-03-14	JTS, RTi		Finished initial swing version.
// 2003-03-20	JTS, RTi		* Revised after SAM's review.
//					* Javadoc'd.
// 2003-03-25	JTS, RTi		* Removed references to
//					  HydroBase_GUI_Util parent object.
// 2003-03-27	JTS, RTi		Converted literal button labels to
//					__BUTTON_ Strings.
// 2003-04-04	JTS, RTi		Changed GUIUtil to JGUIUtil.
// 2005-04-28	JTS, RTi		Added all data members to finalize().
// 2005-07-11	JTS, RTi		* Stopped using X* fields.
//					* Transact had two strtype fields.  
//					  Removed the second one.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;

import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import RTi.DMI.DMIUtil;

import RTi.Util.IO.PrintJGUI;

import RTi.Util.GUI.JGUIUtil;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

/**
This class displays record information about a single record from either the
NetAmts or Transact table.
*/
public class HydroBase_GUI_WaterRight 
extends JFrame 
implements ActionListener {

/**
Used to refer to the NetAmts table and its data.
*/
public final static int	NET_AMOUNTS = 0;
/**
Used to refer to the Transact table and its data.
*/
public final static int TRANSACT = 1;

/**
Reference to DMI object.
*/
private HydroBaseDMI __dmi;

/**
The current HydroBase_NetAmts record.
*/
private HydroBase_NetAmts __hydroBaseNetAmts;
/**
The current HydroBase_Transact record.
*/
private HydroBase_Transact __hydroBaseTransact;
/**
The kind of table data being displayed (NET_AMOUNTS or TRANSACT).
*/
private int __table;

/**
Button to close the form.
*/
private JButton __closeJButton;
/**
Button to export the data.
*/
private JButton __exportJButton;
/**
Button to open the help.
*/
private JButton __helpJButton;
/**
Button to print the data.
*/
private JButton __printJButton;

/**
Area for the action comment field.
*/
private JTextArea __actionCommentJTextArea;

/**
Text field to hold data for aband data
*/
private JTextField __abandJTextField;
/**
Text field to hold data for action update data
*/
private JTextField __actionUpdateJTextField;
/**
Text field to hold data for adj date data
*/
private JTextField __adjDateJTextField;
/**
Text field to hold data for adj type data
*/
private JTextField __adjTypeJTextField;
/**
Text field to hold data for admin no data
*/
private JTextField __adminNoJTextField;
/**
Text field to hold data for apro date data
*/
private JTextField __aproDateJTextField;
/**
Text field to hold data for assoc id data
*/
private JTextField __assocIDJTextField;
/**
Text field to hold data for assoc type data
*/
private JTextField __assocTypeJTextField;
/**
Text field to hold data for assoc wd data
*/
private JTextField __assocWDJTextField;
/**
Text field to hold data for aug role data
*/
private JTextField __augRoleJTextField;
/**
Text field to hold data for case no data
*/
private JTextField __caseNoJTextField;
/**
Text field to hold data for county data
*/
private JTextField __countyJTextField;
/**
Text field to hold data for div data
*/
private JTextField __divJTextField;
/**
Text field to hold data for id data
*/
private JTextField __idJTextField;
/**
Text field to hold data for last due dil data
*/
private JTextField __lastDueDilJTextField;
/**
Text field to hold data for order no data
*/
private JTextField __orderNoJTextField;
/**
Text field to hold data for padj date data
*/
private JTextField __padjDateJTextField;
/**
Text field to hold data for plan id data
*/
private JTextField __planIDJTextField;
/**
Text field to hold data for plan wd data
*/
private JTextField __planWDJTextField;
/**
Text field to hold data for pm data
*/
private JTextField __pmJTextField;
/**
Text field to hold data for prior no data
*/
private JTextField __priorNoJTextField;
/**
Text field to hold data for q10 data
*/
private JTextField __q10JTextField;
/**
Text field to hold data for q40 data
*/
private JTextField __q40JTextField;
/**
Text field to hold data for q160 data
*/
private JTextField __q160JTextField;
/**
Text field to hold data for rate abs data
*/
private JTextField __rateAbsJTextField;
/**
Text field to hold data for rate amt data
*/
private JTextField __rateAmtJTextField;
/**
Text field to hold data for rate apex data
*/
private JTextField __rateApexJTextField;
/**
Text field to hold data for rate cond data
*/
private JTextField __rateCondJTextField;
/**
Text field to hold data for rng data
*/
private JTextField 
	__rngJTextField,
	__rdirJTextField;
/**
Text field to hold data for sec data
*/
private JTextField __secJTextField;
/**
Text field to hold data for seca data
*/
private JTextField __secaJTextField;
/**
Text field to hold data for source data
*/
private JTextField __sourceJTextField;
/**
Text field to hold data for status data
*/
private JTextField __statusJTextField;
/**
Text field to hold data for struct type data
*/
private JTextField __structTypeJTextField;
/**
Text field to hold data for tran id data
*/
private JTextField __tranIDJTextField;
/**
Text field to hold data for trans type data
*/
private JTextField __transTypeJTextField;
/**
Text field to hold data for tran wd data
*/
private JTextField __tranWDJTextField;
/**
Text field to hold data for twn data
*/
private JTextField 
	__twnJTextField,
	__tdirJTextField;
/**
Text field to hold data for use type data
*/
private JTextField __useTypeJTextField;
/**
Text field to hold data for vol abs data
*/
private JTextField __volAbsJTextField;
/**
Text field to hold data for vol amt data
*/
private JTextField __volAmtJTextField;
/**
Text field to hold data for vol apex data
*/
private JTextField __volApexJTextField;
/**
Text field to hold data for vol cond data
*/
private JTextField __volCondJTextField;
/**
Text field to hold data for wd data
*/
private JTextField __wdJTextField;
/**
Text field to hold data for wrname data
*/
private JTextField __wrnameJTextField;
/**
Text field to hold data for wrstatus data
*/
private JTextField __wrstatusJTextField;

/**
Misc Strings.
*/
private final String 
	__BUTTON_CLOSE = 	"Close",
	__BUTTON_EXPORT = 	"Export",
	__BUTTON_HELP = 	"Help",
	__BUTTON_PRINT = 	"Print";

/**
Constructor for a GUI to show a NetAmts object.
@param dmi open and non-null HydroBaseDMI object
@param n the HydroBase_NetAmts record to display.
*/
public HydroBase_GUI_WaterRight(HydroBaseDMI dmi, HydroBase_NetAmts n) 
throws Exception {
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	initialize(dmi, NET_AMOUNTS, n, null);
}

/**
Constructor for a GUI to show a Transact object.
@param dmi open and non-null HydroBaseDMI object
@param t the HydroBase_Transact record to display.
*/
public HydroBase_GUI_WaterRight(HydroBaseDMI dmi, HydroBase_Transact t) 
throws Exception {
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
	initialize(dmi, TRANSACT, null, t);
}

/**
Responds to action performed events.
@param event the ActionEvent that happened.
*/
public void actionPerformed(ActionEvent event) {
        String actionCommand = event.getActionCommand();
	String routine = "HydroBase_GUI_WaterRight.actionPerformed()";

        if (__BUTTON_CLOSE.equals(actionCommand)) {
		closeClicked();
        }
        else if (__BUTTON_EXPORT.equals(actionCommand)) {
		try {
			String[] eff = 
				HydroBase_GUI_Util.getExportFilenameAndFormat(
				this, 
				HydroBase_GUI_Util
				.getScreenFormatsAndExtensions());

			if (eff == null) {
				return ;
			}

	 		// First format the output...
			Vector outputStrings = formatOutput();
 			// Now export, letting the user decide the file...
			HydroBase_GUI_Util.export(this, eff[0], outputStrings);
		} 
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}
        }
        else if (__BUTTON_HELP.equals(actionCommand)) {                       
        }
        else if ("Print".equals(actionCommand)) {
		try {
			Vector outputStrings = formatOutput();
	 		// Now print...
			PrintJGUI.print(this, outputStrings);
		}
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}			
        }
}

/**
Close the GUI. Overrides the super.closeClicked();
*/
protected void closeClicked() {
	String routine = "HydroBase_GUI_WaterRight.closeClicked()";
	try {
		setVisible(false);
	} 
	catch(Exception e) {
		Message.printWarning (2, routine, e);
	}
}

/**
Fills in the JTextFields on the form with the proper data from the current
record.
*/
private void fillJTextFields() {
	if (__table == NET_AMOUNTS) {
	HydroBase_NetAmts n = __hydroBaseNetAmts;
	Message.printStatus(1, "", "" + n);
	HydroBase_GUI_Util.setText(__wrnameJTextField, n.getWr_name());
	HydroBase_GUI_Util.setText(__sourceJTextField, n.getWd_stream_name());
	HydroBase_GUI_Util.setText(__pmJTextField, n.getPM());
	HydroBase_GUI_Util.setText(__twnJTextField, n.getTS());
	HydroBase_GUI_Util.setText(__tdirJTextField, n.getTdir());
	HydroBase_GUI_Util.setText(__rngJTextField, n.getRng());
	HydroBase_GUI_Util.setText(__rdirJTextField, n.getRdir());
	HydroBase_GUI_Util.setText(__q160JTextField, n.getQ160());
	HydroBase_GUI_Util.setText(__q40JTextField, n.getQ40());
	HydroBase_GUI_Util.setText(__q10JTextField, n.getQ10());
	HydroBase_GUI_Util.setText(__adjDateJTextField, n.getAdj_type());
	HydroBase_GUI_Util.setText(__useTypeJTextField, n.getUse());
	HydroBase_GUI_Util.setText(__priorNoJTextField, n.getPri_case_no());
	HydroBase_GUI_Util.setText(__secaJTextField, n.getSeca());
	HydroBase_GUI_Util.setText(__structTypeJTextField, n.getStrtype());
       	__actionCommentJTextArea.setText(n.getAction_comment());

	HydroBase_GUI_Util.setText(__divJTextField, n.getDiv());
	HydroBase_GUI_Util.setText(__wdJTextField, n.getWD());
	HydroBase_GUI_Util.setText(__idJTextField, n.getID());
	HydroBase_GUI_Util.setText(__countyJTextField, 
		__dmi.lookupCountyName(n.getCty()));
	HydroBase_GUI_Util.setText(__secJTextField, n.getSec());
	HydroBase_GUI_Util.setText(__orderNoJTextField, n.getOrder_no());
	HydroBase_GUI_Util.setText(__adminNoJTextField, n.getAdmin_no(),
		"%11.5f");
	HydroBase_GUI_Util.setText(__rateAbsJTextField, n.getNet_rate_abs(),
		"%12.4f");
	HydroBase_GUI_Util.setText(__rateApexJTextField, n.getNet_rate_apex(), 
		"%12.4f");
	HydroBase_GUI_Util.setText(__volAbsJTextField, n.getNet_vol_abs(), 
		"%12.4f");
	HydroBase_GUI_Util.setText(__volApexJTextField, n.getNet_vol_apex(),
		"%12.4f");
	HydroBase_GUI_Util.setText(__rateCondJTextField, n.getNet_rate_cond(), 
		"%12.4f");
	HydroBase_GUI_Util.setText(__volCondJTextField, n.getNet_vol_cond(), 
		"%12.4f");
	HydroBase_GUI_Util.setText(__adjDateJTextField, n.getAdj_date());
	HydroBase_GUI_Util.setText(__padjDateJTextField, n.getPadj_date());
	HydroBase_GUI_Util.setText(__aproDateJTextField, n.getApro_date());
	}
	else {
	HydroBase_Transact t = __hydroBaseTransact;
	Message.printStatus(1, "", "" + t);
	HydroBase_GUI_Util.setText(__wrnameJTextField, t.getWr_name());
	HydroBase_GUI_Util.setText(__sourceJTextField, t.getWd_stream_name());
	HydroBase_GUI_Util.setText(__pmJTextField, t.getPM());
	HydroBase_GUI_Util.setText(__secaJTextField, t.getSeca());
	HydroBase_GUI_Util.setText(__twnJTextField, t.getTS());
	HydroBase_GUI_Util.setText(__tdirJTextField, t.getTdir());
	HydroBase_GUI_Util.setText(__rngJTextField, t.getRng());
	HydroBase_GUI_Util.setText(__rdirJTextField, t.getRdir());
	HydroBase_GUI_Util.setText(__q160JTextField, t.getQ160());
	HydroBase_GUI_Util.setText(__q40JTextField, t.getQ40());
	HydroBase_GUI_Util.setText(__q10JTextField, t.getQ10());
	HydroBase_GUI_Util.setText(__adjTypeJTextField, t.getAdj_type());
	HydroBase_GUI_Util.setText(__useTypeJTextField, t.getUse());
	HydroBase_GUI_Util.setText(__priorNoJTextField, t.getPrior_no());
	HydroBase_GUI_Util.setText(__abandJTextField, t.getAband());
	HydroBase_GUI_Util.setText(__wrstatusJTextField, t.getStatus_type());
	HydroBase_GUI_Util.setText(__caseNoJTextField, t.getCase_no());
	HydroBase_GUI_Util.setText(__lastDueDilJTextField, t.getLast_due_dil());

	__actionCommentJTextArea.setText(t.getAction_comment());

	HydroBase_GUI_Util.setText(__assocTypeJTextField, t.getAssoc_type());
	HydroBase_GUI_Util.setText(__augRoleJTextField, t.getAug_role());
	HydroBase_GUI_Util.setText(__transTypeJTextField, t.getTransfer_type());
	HydroBase_GUI_Util.setText(__structTypeJTextField, t.getStrtype());
	HydroBase_GUI_Util.setText(__divJTextField, t.getDiv());
	HydroBase_GUI_Util.setText(__wdJTextField, t.getWD());
	HydroBase_GUI_Util.setText(__idJTextField, t.getID());
	HydroBase_GUI_Util.setText(__countyJTextField, 
		__dmi.lookupCountyName(t.getCty()));
	HydroBase_GUI_Util.setText(__secJTextField, t.getSec());
	HydroBase_GUI_Util.setText(__orderNoJTextField, t.getOrder_no());
	HydroBase_GUI_Util.setText(__assocWDJTextField, t.getAssoc_wd());
	HydroBase_GUI_Util.setText(__assocIDJTextField, t.getAssoc_id());
	HydroBase_GUI_Util.setText(__planWDJTextField, t.getPlan_wd());
	HydroBase_GUI_Util.setText(__planIDJTextField, t.getPlan_id());
	HydroBase_GUI_Util.setText(__tranWDJTextField, t.getTran_wd());
	HydroBase_GUI_Util.setText(__tranIDJTextField, t.getTran_id());
	HydroBase_GUI_Util.setText(__adminNoJTextField, t.getAdmin_no(),
		"%11.5f");
	HydroBase_GUI_Util.setText(__rateAmtJTextField, t.getRate_amt(),
		"%12.4f");
	HydroBase_GUI_Util.setText(__volAmtJTextField, t.getVol_amt(),"%12.1f");
	HydroBase_GUI_Util.setText(__adjDateJTextField, t.getAdj_date());
	HydroBase_GUI_Util.setText(__padjDateJTextField, t.getPadj_date());
	HydroBase_GUI_Util.setText(__aproDateJTextField, t.getApro_date());
	HydroBase_GUI_Util.setText(__actionUpdateJTextField,
		t.getAction_update());
	}
}

/**
Clean up for garbage collection.
*/
protected void finalize()
throws Throwable {
	__dmi = null;
	__hydroBaseNetAmts = null;
	__hydroBaseTransact = null;
	__closeJButton = null;
	__exportJButton = null;
	__helpJButton = null;
	__printJButton = null;
	__actionCommentJTextArea = null;
	__abandJTextField = null;
	__actionUpdateJTextField = null;
	__adjDateJTextField = null;
	__adjTypeJTextField = null;
	__adminNoJTextField = null;
	__aproDateJTextField = null;
	__assocIDJTextField = null;
	__assocTypeJTextField = null;
	__assocWDJTextField = null;
	__augRoleJTextField = null;
	__caseNoJTextField = null;
	__countyJTextField = null;
	__divJTextField = null;
	__idJTextField = null;
	__lastDueDilJTextField = null;
	__orderNoJTextField = null;
	__padjDateJTextField = null;
	__planIDJTextField = null;
	__planWDJTextField = null;
	__pmJTextField = null;
	__priorNoJTextField = null;
	__q10JTextField = null;
	__q40JTextField = null;
	__q160JTextField = null;
	__rateAbsJTextField = null;
	__rateAmtJTextField = null;
	__rateApexJTextField = null;
	__rateCondJTextField = null;
	__rngJTextField = null;
	__rdirJTextField = null;
	__secJTextField = null;
	__secaJTextField = null;
	__sourceJTextField = null;
	__statusJTextField = null;
	__structTypeJTextField = null;
	__tranIDJTextField = null;
	__transTypeJTextField = null;
	__tranWDJTextField = null;
	__twnJTextField = null;
	__tdirJTextField = null;
	__useTypeJTextField = null;
	__volAbsJTextField = null;
	__volAmtJTextField = null;
	__volApexJTextField = null;
	__volCondJTextField = null;
	__wdJTextField = null;
	__wrnameJTextField = null;
	__wrstatusJTextField = null;

	super.finalize();
}

/**
Formats the data for output as export or print.
@return a Vector of strings, each of which is a line in the GUI.
*/
public Vector formatOutput() {
	Vector v = new Vector(30, 5);
	Vector tmpV = new Vector(10,5);

	if (__table == NET_AMOUNTS) {
		v.addElement("Water Right - Net Amounts");
	}
	else {
		v.addElement("Water Right - Transaction");
	}

	v.addElement("Water Right Name");
	v.addElement(__wrnameJTextField.getText());

        v.addElement("");
	v.addElement("DIV   WD    ID");
	tmpV.removeAllElements();
	tmpV.addElement(__divJTextField.getText());
	tmpV.addElement(__wdJTextField.getText());
	tmpV.addElement(__idJTextField.getText());
	v.addElement(StringUtil.formatString(tmpV, "%-6.6s%-6.6s%-6.6s"));

        v.addElement("");
	v.addElement("Water Source");
	v.addElement(__sourceJTextField.getText());

        v.addElement("");
	v.addElement("County");
	v.addElement(__countyJTextField.getText());
	v.addElement("Location Coordinates");
	v.addElement(
		"PM    TWN   TDIR   RNG   RDIR   SEC   SECA  Q160  Q40   Q10");
	tmpV.removeAllElements();
	tmpV.addElement(__pmJTextField.getText());
	tmpV.addElement(__twnJTextField.getText());
	tmpV.addElement(__tdirJTextField.getText());
	tmpV.addElement(__rngJTextField.getText());
	tmpV.addElement(__rdirJTextField.getText());
	tmpV.addElement(__secJTextField.getText());
	tmpV.addElement(__secaJTextField.getText());
	tmpV.addElement(__q160JTextField.getText());
	tmpV.addElement(__q40JTextField.getText());
	tmpV.addElement(__q10JTextField.getText());
	v.addElement(StringUtil.formatString(tmpV, 
		"%-6.6s%-6.6s%-6.6s%-6.6s%-6.6s%-6.6s%-6.6s%-6.6s"));

	v.addElement("");
	v.addElement(
		"Adjudication Date   Prior Adj Date   Appropriation Date");
	tmpV.removeAllElements();
	tmpV.addElement(__adjDateJTextField.getText());
	tmpV.addElement(__padjDateJTextField.getText());
	tmpV.addElement(__aproDateJTextField.getText());
	v.addElement(StringUtil.formatString(tmpV, "%-20.20s%-17.17s%s"));

	v.addElement("");
	if (__table == NET_AMOUNTS) {
		v.addElement(
		"Administration No   Order No         Prior/Case No");
	}
	else {	
		v.addElement(
		"Administration No   Order No         Prior No");
	}
	tmpV.removeAllElements();
	tmpV.addElement(__adminNoJTextField.getText());
	tmpV.addElement(__orderNoJTextField.getText());
	tmpV.addElement(__priorNoJTextField.getText());
	v.addElement(StringUtil.formatString(tmpV, "%-20.20s%-17.17s%s"));

	v.addElement("");
	v.addElement(
		"Adjudication Type   Use Type         Struct Type");
	tmpV.removeAllElements();
	tmpV.addElement(__adjTypeJTextField.getText());
	tmpV.addElement(__useTypeJTextField.getText());
	tmpV.addElement(__structTypeJTextField.getText());
	v.addElement(StringUtil.formatString(tmpV, "%-20.20s%-17.17s%s"));

	if (__table == NET_AMOUNTS)
	{
	v.addElement("");
	v.addElement("Rate Abs (cfs):         Vol Abs (acft)");
	tmpV.removeAllElements();
	tmpV.addElement(__rateAbsJTextField.getText().trim());
	tmpV.addElement(__volAbsJTextField.getText().trim());
	v.addElement(StringUtil.formatString(tmpV, "%-24.24s%s"));

	v.addElement("");
	v.addElement("Rate Cond (cfs):        Vol Cond (acft)");
	tmpV.removeAllElements();
	tmpV.addElement(__rateCondJTextField.getText().trim());
	tmpV.addElement(__volCondJTextField.getText().trim());
	v.addElement(StringUtil.formatString(tmpV, "%-24.24s%s"));

	v.addElement("");
	v.addElement("Rate Apex (cfs)        Vol Apex (acft)");
	tmpV.removeAllElements();
	tmpV.addElement(__rateApexJTextField.getText().trim());
	tmpV.addElement(__volApexJTextField.getText().trim());
	v.addElement(StringUtil.formatString(tmpV, "%-24.24s%s"));
	}
	else {
		// if (__table == TRANSACT)
	v.addElement("");
	v.addElement("Rate Amt (cfs)         Vol Amt (acft)");
	tmpV.removeAllElements();
	tmpV.addElement(__rateAmtJTextField.getText().trim());
	tmpV.addElement(__volAmtJTextField.getText().trim());
	v.addElement(StringUtil.formatString(tmpV, "%-24.24s%s"));

	v.addElement("");
	v.addElement("Aband       Status      Case No");
	tmpV.removeAllElements();
	tmpV.addElement(__abandJTextField.getText());
	tmpV.addElement(__wrstatusJTextField.getText());
	tmpV.addElement(__caseNoJTextField.getText());
	v.addElement(StringUtil.formatString(tmpV, "%-12.12s%-12.12s%s"));

	v.addElement("");
	v.addElement("Assoc Type  Assoc WD    Assoc ID");
	tmpV.removeAllElements();
	tmpV.addElement(__assocTypeJTextField.getText());
	tmpV.addElement(__assocWDJTextField.getText());
	tmpV.addElement(__assocIDJTextField.getText());
	v.addElement(StringUtil.formatString(tmpV, "%-12.12s%-12.12s%s"));

	v.addElement("");
	v.addElement("Aug Role    Plan WD     Plan ID");
	tmpV.removeAllElements();
	tmpV.addElement(__augRoleJTextField.getText());
	tmpV.addElement(__planWDJTextField.getText());
	tmpV.addElement(__planIDJTextField.getText());
	v.addElement(StringUtil.formatString(tmpV, "%-12.12s%-12.12s%s"));

	v.addElement("");
	v.addElement("Trans Type  Trans WD    Trans ID");
	tmpV.removeAllElements();
	tmpV.addElement(__transTypeJTextField.getText());
	tmpV.addElement(__tranWDJTextField.getText());
	tmpV.addElement(__tranIDJTextField.getText());
	v.addElement(StringUtil.formatString(tmpV, "%-12.12s%-12.12s%s"));

	v.addElement("");
	v.addElement("LastDueDil  Action Update");
	tmpV.removeAllElements();
	tmpV.addElement(__lastDueDilJTextField.getText());
	tmpV.addElement(__actionUpdateJTextField.getText());
	v.addElement(StringUtil.formatString(tmpV, "%-12.12s%s"));
	}

	v.addElement("");
	v.addElement("Action Comment");
	v.addElement(__actionCommentJTextArea.getText());

        return v;
}

/**
Initialization routine.  Sets up the GUI and displays the appropriate record.
@param dmi open and non-null HydroBaseDMI object
@param table the kind of table (TRANSACT or NET_AMOUNTS) of the record to 
display.
@param n the HydroBase_NetAmts record to display (or null, if table == TRANSACT)
@param t the HydroBase_Transact record to display (or null, if 
table == NET_AMOUNTS)
@throws Exception if an error occurs
*/
private void initialize(HydroBaseDMI dmi, int table, HydroBase_NetAmts n, 
HydroBase_Transact t) 
throws Exception {
	String obj = "HydroBase_GUI_WaterRight";
	if (dmi == null) {
		throw new Exception ("Null dmi passed to "
			+ obj + " "
			+ "constructor.  DMI object must be open and "
			+ "connected before passing in.");
	}
	if (!dmi.isOpen()) {
		throw new Exception ("Unopened dmi passed to "
			+ obj + " "
			+ "constructor.  DMI object must be open and "
			+ "connected before passing in.");
	}

	__dmi = dmi;
	__table = table;
        __hydroBaseNetAmts = n;
        __hydroBaseTransact = t;

        setupGUI();
	setVisible(true);
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	Message.setTopLevel(this);
	int x = 0;
	int y = 0;
	int entireJPanelY = 0;

        // objects used throughout the GUI layout
        Insets insetsTLBR = new Insets(4,4,4,4);
        Insets insetsTNNN = new Insets(14,0,0,0);
        Insets insetsTNBN = new Insets(14,0,14,0);
        Insets insetsTLNR = new Insets(4,4,0,4);
        Insets insetsNLNN = new Insets(0,4,0,0);
        Insets insetsNNNN = new Insets(0,0,0,0);
        Insets insetsNNNNS = new Insets(0,0,0,0);
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent evt) {
			closeClicked();
		}
	});

	// the top panel contains 7 main panels
	// the bottom panel contains the status bar and buttons
        JPanel topJPanel = new JPanel();
        topJPanel.setLayout(gbl);
	getContentPane().add("North", topJPanel);

	// FIRST PANEL - water right name, water source(trans only), 
	//	wd, id, div
	//
	JPanel firstJPanel = new JPanel();
	firstJPanel.setLayout(gbl);
        JGUIUtil.addComponent(topJPanel, firstJPanel, 
		0, entireJPanelY++, 1, 1, 0, 0, 
		insetsNNNN, gbc.NONE, gbc.NORTHWEST);

        JGUIUtil.addComponent(firstJPanel, new JLabel(" Water Right Name:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(firstJPanel, new JLabel("DIV:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(firstJPanel, new JLabel(" WD:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(firstJPanel, new JLabel("ID:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(firstJPanel, new JLabel(" Water Source:"), 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);

	x = 0;
        __wrnameJTextField = new JTextField(20);
	__wrnameJTextField.setEditable(false);
        JGUIUtil.addComponent(firstJPanel, __wrnameJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);

        __divJTextField = new JTextField(3);
	__divJTextField.setEditable(false);
        JGUIUtil.addComponent(firstJPanel, __divJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
	
        __wdJTextField = new JTextField(3);
	__wdJTextField.setEditable(false);
        JGUIUtil.addComponent(firstJPanel, __wdJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
	
        __idJTextField = new JTextField(4);
	__idJTextField.setEditable(false);
        JGUIUtil.addComponent(firstJPanel, __idJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
	
       	__sourceJTextField = new JTextField(25);
	__sourceJTextField.setEditable(false);
       	JGUIUtil.addComponent(firstJPanel, __sourceJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);

	// SECOND PANEL - pm, twn, rng, sec, seca, Q160, Q40, Q10
	//
	JPanel secondJPanel = new JPanel();
	secondJPanel.setLayout(gbl);
        JGUIUtil.addComponent(topJPanel, secondJPanel, 
		0, entireJPanelY++, 1, 1, 0, 0, 
		insetsTNNN, gbc.NONE, gbc.NORTHWEST);

	x = 0;
	y = 0;
        JGUIUtil.addComponent(secondJPanel, new JLabel("County:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(secondJPanel, new JLabel("PM:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(secondJPanel, new JLabel("TWN:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
	x++;
        JGUIUtil.addComponent(secondJPanel, new JLabel("RNG:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
	x++;
        JGUIUtil.addComponent(secondJPanel, new JLabel("SEC:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
	x++;	// seca has no label
        JGUIUtil.addComponent(secondJPanel, new JLabel("1/4:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(secondJPanel, new JLabel("1/4 1/4:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(secondJPanel, new JLabel("1/4 1/4 1/4:"), 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);

	x = 0;
        __countyJTextField = new JTextField(12);
	__countyJTextField.setEditable(false);
        JGUIUtil.addComponent(secondJPanel, __countyJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __pmJTextField = new JTextField(3);
	__pmJTextField.setEditable(false);
        JGUIUtil.addComponent(secondJPanel, __pmJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __twnJTextField = new JTextField(4);
	__twnJTextField.setEditable(false);
        JGUIUtil.addComponent(secondJPanel, __twnJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __tdirJTextField = new JTextField(4);
	__tdirJTextField.setEditable(false);
        JGUIUtil.addComponent(secondJPanel, __tdirJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __rngJTextField = new JTextField(4);
	__rngJTextField.setEditable(false);
        JGUIUtil.addComponent(secondJPanel, __rngJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __rdirJTextField = new JTextField(4);
	__rdirJTextField.setEditable(false);
        JGUIUtil.addComponent(secondJPanel, __rdirJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __secJTextField = new JTextField(4);
	__secJTextField.setEditable(false);
        JGUIUtil.addComponent(secondJPanel, __secJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __secaJTextField = new JTextField(4);
	__secaJTextField.setEditable(false);
        JGUIUtil.addComponent(secondJPanel, __secaJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __q160JTextField = new JTextField(5);
	__q160JTextField.setEditable(false);
        JGUIUtil.addComponent(secondJPanel, __q160JTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __q40JTextField = new JTextField(4);
	__q40JTextField.setEditable(false);
        JGUIUtil.addComponent(secondJPanel, __q40JTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __q10JTextField = new JTextField(4);
	__q10JTextField.setEditable(false);
        JGUIUtil.addComponent(secondJPanel, __q10JTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);

	// THIRD PANEL - adj date, padj date, apro date, admin no, 
	// 	order no, prior no, adj type, use type
	//
	JPanel thirdJPanel = new JPanel();
	thirdJPanel.setLayout(gbl);
        JGUIUtil.addComponent(topJPanel, thirdJPanel, 
		0, entireJPanelY++, 1, 1, 0, 0, 
		insetsTNNN, gbc.NONE, gbc.NORTHWEST);

	x = 0;
	y = 0;
        JGUIUtil.addComponent(thirdJPanel, new JLabel("Adjudication Date:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(thirdJPanel,new 
		JLabel("Prior Adjudication Date:"),
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(thirdJPanel, new JLabel("Appropriation Date:"), 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);

	x = 0;
        __adjDateJTextField = new JTextField();
	__adjDateJTextField.setEditable(false);
        JGUIUtil.addComponent(thirdJPanel, __adjDateJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __padjDateJTextField = new JTextField();
	__padjDateJTextField.setEditable(false);
        JGUIUtil.addComponent(thirdJPanel, __padjDateJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __aproDateJTextField = new JTextField();
	__aproDateJTextField.setEditable(false);
        JGUIUtil.addComponent(thirdJPanel, __aproDateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);

	x = 0;
        JGUIUtil.addComponent(thirdJPanel, new JLabel("Administration No:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(thirdJPanel, new JLabel("Order No:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(thirdJPanel, new JLabel("Prior/Case No:"), 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);

	x = 0;
        __adminNoJTextField = new JTextField();
	__adminNoJTextField.setEditable(false);
        JGUIUtil.addComponent(thirdJPanel, __adminNoJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __orderNoJTextField = new JTextField();
	__orderNoJTextField.setEditable(false);
        JGUIUtil.addComponent(thirdJPanel, __orderNoJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __priorNoJTextField = new JTextField();
	__priorNoJTextField.setEditable(false);
        JGUIUtil.addComponent(thirdJPanel, __priorNoJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);


	x = 0;
        JGUIUtil.addComponent(thirdJPanel, new JLabel("Adjudication Type:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(thirdJPanel, new JLabel("Use Type:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(thirdJPanel, new JLabel("Struct Type:"), 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);

	x = 0;
        __adjTypeJTextField = new JTextField();
	__adjTypeJTextField.setEditable(false);
        JGUIUtil.addComponent(thirdJPanel, __adjTypeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __useTypeJTextField = new JTextField();
	__useTypeJTextField.setEditable(false);
        JGUIUtil.addComponent(thirdJPanel, __useTypeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __structTypeJTextField = new JTextField();
	__structTypeJTextField.setEditable(false);
        JGUIUtil.addComponent(thirdJPanel, __structTypeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
	
	// 
	// FOURTH PANEL - NET ONLY!  
	//	rate abs, vol abs, rate cond, vol cond, rate apex, vol apex
	//
	if (__table == NET_AMOUNTS) {
	JPanel fourthJPanel = new JPanel();
	fourthJPanel.setLayout(gbl);
        JGUIUtil.addComponent(topJPanel, fourthJPanel, 
		0, entireJPanelY++, 1, 1, 0, 0, 
		insetsTNNN, gbc.NONE, gbc.NORTHWEST);

	x = 0;
	y = 0;
        JGUIUtil.addComponent(fourthJPanel, new JLabel("Rate Abs (cfs):"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fourthJPanel, new JLabel("Vol Abs (acft):"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fourthJPanel, new JLabel("Rate Cond (cfs):"), 
		x++, y, 1, 1, 0, 0, insetsNLNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fourthJPanel, new JLabel("Vol Cond (acft):"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fourthJPanel, new JLabel("Rate Apex (cfs):"), 
		x++, y, 1, 1, 0, 0, insetsNLNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fourthJPanel, new JLabel("Vol Apex (acft):"), 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
	
	x = 0;
        __rateAbsJTextField = new JTextField();
	__rateAbsJTextField.setEditable(false);
        JGUIUtil.addComponent(fourthJPanel, __rateAbsJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __volAbsJTextField = new JTextField();
	__volAbsJTextField.setEditable(false);
        JGUIUtil.addComponent(fourthJPanel, __volAbsJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __rateCondJTextField = new JTextField();
	__rateCondJTextField.setEditable(false);
        JGUIUtil.addComponent(fourthJPanel, __rateCondJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNN, gbc.HORIZONTAL, gbc.WEST);
        __volCondJTextField = new JTextField();
	__volCondJTextField.setEditable(false);
        JGUIUtil.addComponent(fourthJPanel, __volCondJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __rateApexJTextField = new JTextField();
	__rateApexJTextField.setEditable(false);
        JGUIUtil.addComponent(fourthJPanel, __rateApexJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNN, gbc.HORIZONTAL, gbc.WEST);
        __volApexJTextField = new JTextField();
	__volApexJTextField.setEditable(false);
        JGUIUtil.addComponent(fourthJPanel, __volApexJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);


	x = 0;
        JGUIUtil.addComponent(fourthJPanel, new JLabel("Action Comment:"), 
		x, y++, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);

        __actionCommentJTextArea = new JTextArea("", 4, 65);
	__actionCommentJTextArea.setEditable(false);
	__actionCommentJTextArea.setBackground(
		__volApexJTextField.getBackground());
	JScrollPane jsp1 = new JScrollPane(__actionCommentJTextArea);
        JGUIUtil.addComponent(fourthJPanel, jsp1, 
		x, y, 6, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
	}


	// FIFTH PANEL - TRANSACT ONLY!  
	//	rate amt, vol amt, aband, status, case no, last due dil, 
	//	action comment, action update, assoc type, assoc wd, 
	//	assoc id, aug role, plan wd, plan id, trans type, trans wd, 
	//	trans id, struct type
	//
	if (__table == TRANSACT) {
	JPanel fifthJPanel = new JPanel();
	fifthJPanel.setLayout(gbl);
        JGUIUtil.addComponent(topJPanel, fifthJPanel, 
		0, entireJPanelY++, 1, 1, 0, 0, 
		insetsTNNN, gbc.NONE, gbc.NORTHWEST);

	x = 0;
	y = 0;
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Rate Amt (cfs):"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Vol Amt (acft):"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Aband:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Status:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Case No:"), 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);

	x = 0;
        __rateAmtJTextField = new JTextField();
	__rateAmtJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __rateAmtJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __volAmtJTextField = new JTextField();
	__volAmtJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __volAmtJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __abandJTextField = new JTextField();
	__abandJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __abandJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __wrstatusJTextField = new JTextField();
	__wrstatusJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __wrstatusJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __caseNoJTextField = new JTextField();
	__caseNoJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __caseNoJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);

	x = 0;
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Assoc Type:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Assoc WD:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Assoc ID:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Aug Role:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Plan WD:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Plan ID:"), 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);

	x = 0;
        __assocTypeJTextField = new JTextField();
	__assocTypeJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __assocTypeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __assocWDJTextField = new JTextField();
	__assocWDJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __assocWDJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __assocIDJTextField = new JTextField();
	__assocIDJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __assocIDJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __augRoleJTextField = new JTextField();
	__augRoleJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __augRoleJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __planWDJTextField = new JTextField();
	__planWDJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __planWDJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __planIDJTextField = new JTextField();
	__planIDJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __planIDJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);

	x = 0;
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Trans Type:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Tran WD:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Tran ID:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Last Due Dil:"), 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Action Update:"), 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
	
	x = 0;
        __transTypeJTextField = new JTextField();
	__transTypeJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __transTypeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __tranWDJTextField = new JTextField();
	__tranWDJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __tranWDJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __tranIDJTextField = new JTextField();
	__tranIDJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __tranIDJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __lastDueDilJTextField = new JTextField();
	__lastDueDilJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __lastDueDilJTextField, 
		x++, y, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);
        __actionUpdateJTextField = new JTextField();
	__actionUpdateJTextField.setEditable(false);
        JGUIUtil.addComponent(fifthJPanel, __actionUpdateJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);

	x = 0;
        JGUIUtil.addComponent(fifthJPanel, new JLabel("Action Comment:"), 
		x, y++, 3, 1, 0, 0, insetsNNNN, gbc.NONE, gbc.WEST);
        __actionCommentJTextArea = new JTextArea("", 2, 65);
	__actionCommentJTextArea.setEditable(false);
	JScrollPane jsp2 = new JScrollPane(__actionCommentJTextArea);
	jsp2.setHorizontalScrollBarPolicy(
		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JGUIUtil.addComponent(fifthJPanel, jsp2, 
		x, y, 6, 1, 0, 0, insetsNNNN, gbc.HORIZONTAL, gbc.WEST);

	}

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
//        buttonJPanel.add(__helpJButton);

        // Bottom: South: South JPanel
        JPanel statusJPanel = new JPanel();
        statusJPanel.setLayout(gbl);
        bottomJPanel.add("South", statusJPanel);

        __statusJTextField = new JTextField();
	__statusJTextField.setEditable(false);
        JGUIUtil.addComponent(statusJPanel, __statusJTextField, 
		0, 1, 10, 1, 1, 0, gbc.HORIZONTAL, gbc.WEST);
        //__statusJTextField.setBackground(Color.lightGray);

        // Frame settings
	fillJTextFields();
        //setBackground(Color.lightGray);
	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}	
	if (__table == TRANSACT) {
        	setTitle(app + "Water Right - Transaction");
	}
	else {
        	setTitle(app + "Water Right - Net Amounts");
	}
        pack();

	// increase the width so that all columns will be visible in
	// the equipment list.
	Dimension d = getSize();
	setSize(d.width + 50, d.height);

	JGUIUtil.center(this);
}

/**
Sets the visible state of the form.
@param state whether the form is visible (true) or not (false).
*/
public void setVisible(boolean state) {
        super.setVisible(state);
}

}