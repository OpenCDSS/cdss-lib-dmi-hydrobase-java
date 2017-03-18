//-----------------------------------------------------------------------------
// HydroBase_GUI_PumpTest - Pump Test Detail GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 05 Jun 2000	Catherine E. Nutting-Lane, RTi
//					Created initial version.
// 2001-11-12	SAM, RTi		Change GUI to JGUIUtil.
//-----------------------------------------------------------------------------
// 2003-05-28	J. Thomas Sapienza, RTi	Initial Swing version.
// 2003-09-23	JTS, RTi		Changed the export code to use 
//					the new export code in 
//					HydroBase_GUI_Util.
// 2004-01-07	JTS, RTi		* Numbers no longer float in the middle
//					  of their text field due to 
//				 	  justification issues.
//					* Help button disabled.
//					* Several fields widened.
// 2004-01-12	SAM, RTi		* Set title consistent with other
//					  windows.
//					* Comment out help button.
//					* Add tool tip text.
// 2005-04-28	JTS, RTi		Added finalize().
// 2005-05-09	JTS, RTi		HydroBase_GroundWaterWellsPumpingTest 
//					objects now used for SP and 
//					non-SP queries.
// 2005-07-06	JTS, RTi		* Added Permit Info, Loc num, and 
//					  Site ID fields.
//					* Reorganized the layout of other 
//					  fields.
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
import java.awt.event.WindowListener;

import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JGUIUtil;
import RTi.Util.GUI.SimpleJButton;

import RTi.Util.IO.PrintJGUI;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

/**
GUI for displaying a HydroBase_PumpTest object.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_PumpTest 
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
Currently displayed HydroBase_PumpTest 
*/
private HydroBase_GroundWaterWellsPumpingTest __hydroBasePumpTestView = null;

/**
GUI buttons.
*/
private JButton 
	__closeJButton,
	__exportJButton,
	__printJButton;

/**
GUI checkboxes.
*/
private JCheckBox 
	__monJCheckBox,
	__multJCheckBox,
	__obsJCheckBox;

/**
GUI text fields.
*/
private JTextField 
	__basetestintJTextField,
	__drawdownJTextField,
	__idJTextField,
	__kJTextField,
	__leakanceJTextField,
	__locNumJTextField,
	__nameJTextField,
	__permitInfoJTextField,
	__pmJTextField,
	__ptsourceJTextField,
	__pttypeJTextField,
	__q10JTextField,
	__q160JTextField,
	__q40JTextField,
	__rdirJTextField,
	__rngJTextField,
	__secJTextField,
	__secaJTextField,
	__siteIDJTextField,
	__statusJTextField,
	__storativityJTextField,
	__tdirJTextField,
	__testdateJTextField,
	__testqJTextField,
	__testtimeJTextField,
	__tfwlJTextField,
	__toptestintJTextField,
	__transJTextField,
	__tsJTextField,
	__tswlJTextField,
	__wdJTextField;

/**
Constructor.
@param parent the JFrame that instantiated this GUI.
@param hydroBasePumpTestView the HydroBase_GroundWaterWellsPumpingTest object 
to display in the GUI.
*/
public HydroBase_GUI_PumpTest(JFrame parent, 
HydroBase_GroundWaterWellsPumpingTest hydroBasePumpTestView) {
        __hydroBasePumpTestView = hydroBasePumpTestView;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
        setupViewGUI();
}

/**
Responds to action events.
@param event the ActionEvent that occurred.
*/
public void actionPerformed(ActionEvent event) {
	String routine = "actionPerformed";
        String actionCommand = event.getActionCommand();

        if (actionCommand.equals(__BUTTON_CLOSE)){ 
		closeClicked();
        }
        else if (actionCommand.equals(__BUTTON_EXPORT)) {
		try {
			String[] eff = 
				HydroBase_GUI_Util.getExportFilenameAndFormat(
				this, 
				HydroBase_GUI_Util
				.getDelimitedFormatsAndExtensions());

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
				HydroBase_GUI_Util.getDelimitedFormats());
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

private void closeClicked() {
	setVisible(false);
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	__hydroBasePumpTestView = null;
	__closeJButton = null;
	__exportJButton = null;
	__printJButton = null;
	__monJCheckBox = null;
	__multJCheckBox = null;
	__obsJCheckBox = null;
	__basetestintJTextField = null;
	__drawdownJTextField = null;
	__idJTextField = null;
	__kJTextField = null;
	__leakanceJTextField = null;
	__nameJTextField = null;
	__pmJTextField = null;
	__ptsourceJTextField = null;
	__pttypeJTextField = null;
	__q10JTextField = null;
	__q160JTextField = null;
	__q40JTextField = null;
	__rdirJTextField = null;
	__rngJTextField = null;
	__secJTextField = null;
	__secaJTextField = null;
	__statusJTextField = null;
	__storativityJTextField = null;
	__tdirJTextField = null;
	__testdateJTextField = null;
	__testqJTextField = null;
	__testtimeJTextField = null;
	__tfwlJTextField = null;
	__toptestintJTextField = null;
	__transJTextField = null;
	__tsJTextField = null;
	__tswlJTextField = null;
	__wdJTextField = null;
	__locNumJTextField = null;
	__siteIDJTextField = null;
	__permitInfoJTextField = null;

	super.finalize();
}

/**
Sets up the GUI for a HydroBase_GroundWaterWellsPumpingTest object.
*/
private void setupViewGUI() {
	int x=0;
	int y=0;

        // objects used throughout the GUI layout
        Insets insetsTLNN = new Insets(7,7,0,0);
        Insets insetsTNNN = new Insets(14,0,0,0);
        Insets insetsTNBN = new Insets(14,0,14,0);
        Insets insetsNLNR = new Insets(0,7,0,7);
        Insets insetsTLNR = new Insets(7,7,0,7);
        Insets insetsTNNR = new Insets(7,0,0,7);
        GridBagLayout gbl = new GridBagLayout();

        addWindowListener(this);

        JPanel topJPanel = new JPanel();
        topJPanel.setLayout(gbl);
        getContentPane().add("North", topJPanel);

        // JPanel 0:  wd, id, name
        JPanel panel_0 = new JPanel();
        panel_0.setLayout(gbl);
        JGUIUtil.addComponent(topJPanel, panel_0, 0, 0, 1, 1, 0, 0, 
		insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(panel_0, new JLabel("WD:"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_0, new JLabel("ID:"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	JGUIUtil.addComponent(panel_0, new JLabel("Loc Num:"),
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	JGUIUtil.addComponent(panel_0, new JLabel("Site ID"),
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	JGUIUtil.addComponent(panel_0, new JLabel("Permit Info"),
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	y++;
	x = 0;
        __wdJTextField = new JTextField(4);
        __wdJTextField.setText("" + __hydroBasePumpTestView.getWD());
        __wdJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_0, __wdJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __idJTextField = new JTextField(6);
	if (__hydroBasePumpTestView.getID() < 0) {
		__idJTextField.setText("");
	}
	else {
	        __idJTextField.setText("" + __hydroBasePumpTestView.getID());
	}
        __idJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_0, __idJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	__locNumJTextField = new JTextField(20);
	__locNumJTextField.setText("" + __hydroBasePumpTestView.getLoc_num());
	__locNumJTextField.setEditable(false);
	JGUIUtil.addComponent(panel_0, __locNumJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	
	__siteIDJTextField = new JTextField(10);
	__siteIDJTextField.setText("" + __hydroBasePumpTestView.getSite_id());
	__siteIDJTextField.setEditable(false);
	JGUIUtil.addComponent(panel_0, __siteIDJTextField,
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	String s = "";
	int i = __hydroBasePumpTestView.getPermitno();
	if (!DMIUtil.isMissing(i)) {
		s += i;
	}

	s += "-";

	String temp = __hydroBasePumpTestView.getPermitsuf();
	if (!DMIUtil.isMissing(temp)) {
		s += temp;
	}
			
	s += "-";

	temp = __hydroBasePumpTestView.getPermitrpl();
	if (!DMIUtil.isMissing(temp)) {
		s += temp;
	}

	__permitInfoJTextField = new JTextField(12);
	__permitInfoJTextField.setText(s);
	__permitInfoJTextField.setEditable(false);
	JGUIUtil.addComponent(panel_0, __permitInfoJTextField,	
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x = 0;
	y++;
        JGUIUtil.addComponent(panel_0, new JLabel("Name:"), 
		x, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x = 0;
	y++;
        __nameJTextField = new JTextField(30);
        __nameJTextField.setText("" + __hydroBasePumpTestView.getStr_name());
        __nameJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_0, __nameJTextField, 
		x++, y, 3, 1, 1, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	y++;
	x=0;
        // JPanel location:  pm, tdir, rdir, seca, q160, q40, q10, ts, rng, sec
        JPanel panel_location = new JPanel();
        panel_location.setLayout(gbl);
        JGUIUtil.addComponent(topJPanel, panel_location, 0, 1, 1, 1, 0, 0, 
		insetsTNNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(panel_location, new JLabel("PM:"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
  
        JGUIUtil.addComponent(panel_location, new JLabel("TWN:"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;

        JGUIUtil.addComponent(panel_location, new JLabel("RNG:"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
	x++;

        JGUIUtil.addComponent(panel_location, new JLabel("SEC:"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x++;
        JGUIUtil.addComponent(panel_location, new JLabel("1/4:"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(panel_location, new JLabel("1/4 1/4:"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(panel_location, new JLabel("1/4 1/4 1/4:"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
	y++;
        __pmJTextField = new JTextField(4);
        __pmJTextField.setText("" + __hydroBasePumpTestView.getPM());
        __pmJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_location, __pmJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __tsJTextField = new JTextField();
        __tsJTextField.setText("" + __hydroBasePumpTestView.getTS());
        __tsJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_location, __tsJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __tdirJTextField = new JTextField(2);
        __tdirJTextField.setText("" + __hydroBasePumpTestView.getTdir());
        __tdirJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_location, __tdirJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __rngJTextField = new JTextField();
        __rngJTextField.setText("" + __hydroBasePumpTestView.getRng());
        __rngJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_location, __rngJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __rdirJTextField = new JTextField(2);
        __rdirJTextField.setText("" + __hydroBasePumpTestView.getRdir());
        __rdirJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_location, __rdirJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __secJTextField = new JTextField();
        __secJTextField.setText("" + __hydroBasePumpTestView.getSec());
        __secJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_location, __secJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __secaJTextField = new JTextField(2);
        __secaJTextField.setText("" + __hydroBasePumpTestView.getSeca());
        __secaJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_location, __secaJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __q160JTextField = new JTextField(3);
        __q160JTextField.setText("" + __hydroBasePumpTestView.getQ160());
        __q160JTextField.setEditable(false);
        JGUIUtil.addComponent(panel_location, __q160JTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __q40JTextField = new JTextField(4);
        __q40JTextField.setText("" + __hydroBasePumpTestView.getQ40());
        __q40JTextField.setEditable(false);
        JGUIUtil.addComponent(panel_location, __q40JTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __q10JTextField = new JTextField(4);
        __q10JTextField.setText("" + __hydroBasePumpTestView.getQ10());
        __q10JTextField.setEditable(false);
        JGUIUtil.addComponent(panel_location, __q10JTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // JPanel 1:  tswl fgwl, testq, testing, testdate, ptsource, pttype
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(gbl);

	x=0;
        JGUIUtil.addComponent(panel_1, new JLabel("Test Type:"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_1, new JLabel("Data Source:"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_1, new JLabel("Test Date:"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_1, new JLabel("Test Duration (hrs):"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	y++;
	x=0;
        __pttypeJTextField = new JTextField();
        __pttypeJTextField.setText("" + __hydroBasePumpTestView.getPttype());
        __pttypeJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_1, __pttypeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __ptsourceJTextField = new JTextField();
        __ptsourceJTextField.setText("" 
		+ __hydroBasePumpTestView.getPtsource());
        __ptsourceJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_1, __ptsourceJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __testdateJTextField = new JTextField();
	if (__hydroBasePumpTestView.getTestdate()!= null)
        __testdateJTextField.setText("" 
		+ __hydroBasePumpTestView.getTestdate());
        __testdateJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_1, __testdateJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __testtimeJTextField = new JTextField();
	if (!DMIUtil.isMissing(__hydroBasePumpTestView.getTesttime())) {
	        __testtimeJTextField.setText(StringUtil.formatString(
			__hydroBasePumpTestView.getTesttime(), "%8.2f").trim());
	}
        __testtimeJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_1, __testtimeJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        JGUIUtil.addComponent(topJPanel, panel_1, 0, 2, 1, 1, 0, 0, 
		insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	y++;
	x=0;
        JGUIUtil.addComponent(panel_1, new JLabel("Static Level (ft):"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
  
        JGUIUtil.addComponent(panel_1, new JLabel("Final Level (ft):"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(panel_1, new JLabel("Water Level Change (ft):"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(panel_1, new JLabel("Average Flow (gpm):"), 
		x++, y++, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);


	x=0;
        __tswlJTextField = new JTextField();
	if (!DMIUtil.isMissing(__hydroBasePumpTestView.getTswl())) {
	        __tswlJTextField.setText(StringUtil.formatString(
			__hydroBasePumpTestView.getTswl(), "%8.2f").trim());
	}
        __tswlJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_1, __tswlJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __tfwlJTextField = new JTextField();
	if (!DMIUtil.isMissing(__hydroBasePumpTestView.getTfwl())) {
	        __tfwlJTextField.setText(StringUtil.formatString(
			__hydroBasePumpTestView.getTfwl(), "%8.2f").trim());
	}
        __tfwlJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_1, __tfwlJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __drawdownJTextField = new JTextField();
	if (!DMIUtil.isMissing(__hydroBasePumpTestView.getDrawdown())) {
	        __drawdownJTextField.setText(StringUtil.formatString(
			__hydroBasePumpTestView.getDrawdown(), "%8.2f").trim());
	}
        __drawdownJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_1, __drawdownJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __testqJTextField = new JTextField();
	if (!DMIUtil.isMissing(__hydroBasePumpTestView.getTestq())) {
	        __testqJTextField.setText(StringUtil.formatString(
			__hydroBasePumpTestView.getTestq(), "%8.1f").trim());
	}
        __testqJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_1, __testqJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_1, new JLabel("Top of Test Interval (ft):"),
		x++, y, 2, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x++;
        JGUIUtil.addComponent(panel_1, 
		new JLabel("Base of Test Interval (ft):"),
		x++, y++, 2, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        __toptestintJTextField = new JTextField();
	if (!DMIUtil.isMissing(__hydroBasePumpTestView.getToptestint())) {
	        __toptestintJTextField.setText("" 
			+ __hydroBasePumpTestView.getToptestint());
	}
        __toptestintJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_1, __toptestintJTextField, 
		x++, y, 2, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
	x++;
        __basetestintJTextField = new JTextField();

	if (!DMIUtil.isMissing(__hydroBasePumpTestView.getBasetestint())) {
	        __basetestintJTextField.setText("" 
			+ __hydroBasePumpTestView.getBasetestint());
	}
        __basetestintJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_1, __basetestintJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // JPanel 2:  trans, k, storativity, leakance
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(gbl);
        JGUIUtil.addComponent(topJPanel, panel_2, 0, 3, 1, 1, 0, 0, 
		insetsTNNN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	x=0;
        JGUIUtil.addComponent(panel_2, new JLabel("Transmissivity (gpd/ft):"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
  
        JGUIUtil.addComponent(panel_2, new JLabel(
		"Hydraulic Conductivity (ft/d):"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(panel_2, new JLabel("Storativity:"), 
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JGUIUtil.addComponent(panel_2, new JLabel("Leakance:"), 
		x++, y++, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);


	x=0;
        __transJTextField = new JTextField();
	if (!DMIUtil.isMissing(__hydroBasePumpTestView.getTrans())) {
	        __transJTextField.setText(StringUtil.formatString(
			__hydroBasePumpTestView.getTrans(), "%8.0f").trim());
	}
        __transJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_2, __transJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __kJTextField = new JTextField();
	if (!DMIUtil.isMissing(__hydroBasePumpTestView.getK())) {
	        __kJTextField.setText(StringUtil.formatString(
			__hydroBasePumpTestView.getK(), "%8.3f").trim());
	}
        __kJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_2, __kJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __storativityJTextField = new JTextField();
	if (!DMIUtil.isMissing(__hydroBasePumpTestView.getStorativity())) {
	        __storativityJTextField.setText(StringUtil.formatString(
			__hydroBasePumpTestView.getStorativity(), 
			"%12.6f").trim());
	}
        __storativityJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_2, __storativityJTextField, 
		x++, y, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        __leakanceJTextField = new JTextField();
	if (!DMIUtil.isMissing(__hydroBasePumpTestView.getLeakance())) {
	        __leakanceJTextField.setText(StringUtil.formatString(
			__hydroBasePumpTestView.getLeakance(), 
			"%12.5f").trim());
	}
        __leakanceJTextField.setEditable(false);
        JGUIUtil.addComponent(panel_2, __leakanceJTextField, 
		x++, y++, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // JPanel 3:  ptmon, ptobs, ptmultiple
        JPanel panel_3 = new JPanel();
        panel_3.setLayout(gbl);
        JGUIUtil.addComponent(topJPanel, panel_3, 0, 4, 1, 1, 0, 0, 
		insetsTNBN, GridBagConstraints.NONE, GridBagConstraints.WEST);

	// since disabling checkboxes sets the text to light gray(hard to 
	// read and unnecessary in this case), each checkbox will be followed
	// by a label.
	x=0;
	int checkValue = __hydroBasePumpTestView.getPtmon();
	boolean checkIt;
	if (checkValue == 0) {
		checkIt = false;
	}
	else {
		checkIt = true;
	}
	__monJCheckBox = new JCheckBox("", checkIt);
	__monJCheckBox.setEnabled(false);
        JGUIUtil.addComponent(panel_3, __monJCheckBox,
		x++, y, 1, 1, 0, 0, insetsTLNN, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Includes Monitor Well"),
		x++, y, 1, 1, 0, 0, insetsTNNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
  
	checkValue = __hydroBasePumpTestView.getPtobs();
	if (checkValue == 0) {
		checkIt = false;
	}
	else {
		checkIt = true;
	}
	__obsJCheckBox = new JCheckBox("",checkIt);
	__obsJCheckBox.setEnabled(false);
        JGUIUtil.addComponent(panel_3, __obsJCheckBox,
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Includes Observed Well"),
		x++, y, 1, 1, 0, 0, insetsTNNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	checkValue = __hydroBasePumpTestView.getPtmultiple();
	if (checkValue == 0) {
		checkIt = false;
	}
	else {
		checkIt = true;
	}
	__multJCheckBox = new JCheckBox("", checkIt);
	__multJCheckBox.setEnabled(false);
        JGUIUtil.addComponent(panel_3, __multJCheckBox,
		x++, y, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(panel_3, new JLabel("Multiple Tests Available"),
		x++, y, 1, 1, 0, 0, insetsTNNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        // Center
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(new BorderLayout());
        getContentPane().add("Center", centerJPanel);

        // Bottom JPanel
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);

        // button panel
        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add("North", buttonJPanel);

        __printJButton = new SimpleJButton(__BUTTON_PRINT, this);
        __printJButton.setToolTipText (
		"Print pumping test data in a selected format." );
        __printJButton.setEnabled(true);
        buttonJPanel.add(__printJButton);

        __exportJButton = new SimpleJButton(__BUTTON_EXPORT, this);
        __exportJButton.setEnabled(true);
        __exportJButton.setToolTipText (
		"Export pumping test data in a selected format." );
        buttonJPanel.add(__exportJButton);

        __closeJButton = new SimpleJButton(__BUTTON_CLOSE, this);
        __closeJButton.setToolTipText ( "Close this window." );
        buttonJPanel.add(__closeJButton);

        // Bottom: South: South JPanel
        JPanel statusJPanel = new JPanel();
        statusJPanel.setLayout(gbl);
        bottomJPanel.add("South", statusJPanel);

        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(statusJPanel, __statusJTextField, 
		0, 1, 10, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // Frame settings
	if (	(JGUIUtil.getAppNameForWindows() == null) ||
		JGUIUtil.getAppNameForWindows().equals("") ) {
		setTitle ( "Ground Water Data - Pumping Test - " +
		__hydroBasePumpTestView.getStr_name() );
	}
	else {	setTitle( JGUIUtil.getAppNameForWindows() +
		" - Ground Water Data - Pumping Test - " +
		__hydroBasePumpTestView.getStr_name() );
	}
        pack();

	// increase the width so that all columns will be visible in
	// the equipment list.
	Dimension d = getSize();
	setSize(d.width + 50, d.height);

	JGUIUtil.center(this);

	setVisible(true);
}

/**
Shows the GUI.
@param state if true, the gui will be set visible.  If false, it will not.
*/
public void setVisible(boolean state) {
        super.setVisible(state);
        if (state) {
                Message.setTopLevel(this);
        }
}

/**
Formats the output for an export or a print.
@param format the format in which the output should be formatted.
*/
public List<String> formatOutput(int format) {
	List<String> v = new Vector<String>();

        char delim = HydroBase_GUI_Util.getDelimiterForFormat(format);

        v.add("WD" + delim + "ID" + delim + "Loc Num" + delim 
		+ "Site ID" + delim + "Permit Info");
        v.add(__wdJTextField.getText() + delim + 
		__idJTextField.getText() + delim + __locNumJTextField.getText()
		+ delim + __siteIDJTextField.getText() + delim
		+ __permitInfoJTextField.getText());

	v.add("Name");
	v.add(__nameJTextField.getText());

        v.add("");
	v.add("PM" + delim + "TWN" + delim + "NS" + delim + "RNG" 
		+ delim + "EW" + delim + "SEC" + delim + "SECA" 
		+ delim + "" + delim + "Q160" + delim + "Q40" + delim + "Q10");
        v.add(__pmJTextField.getText() + delim 
		+ __tsJTextField.getText() + delim + __tdirJTextField.getText()
		+ delim + __rngJTextField.getText() + __rdirJTextField.getText()
		+ delim + __secJTextField.getText() + delim
		+ __secaJTextField.getText() + delim 
		+__q160JTextField.getText() + delim
		+ __q40JTextField.getText() + delim 
		+ __q10JTextField.getText());

        v.add("");
	v.add("Static Level (ft)" + delim + "Final Level (ft)" + delim
			+ "Water Level Change (ft)" + delim 
			+ "Average Flow (gpm)");
        v.add(__tswlJTextField.getText() + delim +
                	__tfwlJTextField.getText() + delim +
                	__drawdownJTextField.getText() + delim +
                	__testqJTextField.getText());

        v.add("");
	v.add("Duration (hrs)" + delim +	"Test Date" + delim 
		+ "Data Source" + delim + "Test Type");
        v.add(__testtimeJTextField.getText() + delim
		+ __testdateJTextField.getText() + delim
		+ __ptsourceJTextField.getText() + delim 
		+ __pttypeJTextField.getText());

        v.add("");
	v.add("Top of Test Interval" + delim + "Base of Test Interval");
        v.add(__toptestintJTextField.getText() + delim 
		+ __basetestintJTextField.getText());

        v.add("");
	v.add("Transmissivity (gpd/ft)" + delim + "Hydraulic " + 
		"Conductivity (ft/d)" + delim + "Storativity" + delim
		+ "Leakance");
        v.add(__transJTextField.getText() + delim 
		+ __kJTextField.getText() + delim 
		+ __storativityJTextField.getText() + delim 
		+ __leakanceJTextField.getText());

        v.add("");
	v.add("Includes Monitor Well" + delim 
		+ "Includes Observed Well" + delim +"Multiple Tests Available");

	String mult = null;
	String obs = null;
	String mon = null;
	
	if (__multJCheckBox.isSelected() == true) {
		mult = "[X]";
	}
	else {
		mult = "[ ]";
	}

	if (__obsJCheckBox.isSelected() == true) {
		obs = "[X]";
	}
	else {
		obs = "[  ]";
	}
	
	if (__monJCheckBox.isSelected() == true) {
		mon = "[X]";
	}
	else {
		mon = "[ ]";
	}

	v.add(mon + delim + obs + delim + mult);

        return v;
}

/**
Responds to window activated events; does nothing.
@param e the WindowEvent that happened.
*/
public void windowActivated(WindowEvent e) {}
/**
Responds to window closed events; does nothing.
@param e the WindowEvent that happened.
*/
public void windowClosed(WindowEvent e) {}
/**
Responds to window closing events.
@param e the WindowEvent that happened.
*/
public void windowClosing(WindowEvent e) {
	closeClicked();
}
/**
Responds to window deactivated events; does nothing.
@param e the WindowEvent that happened.
*/
public void windowDeactivated(WindowEvent e) {}
/**
Responds to window deiconified events; does nothing.
@param e the WindowEvent that happened.
*/
public void windowDeiconified(WindowEvent e) {}
/**
Responds to window iconfied events; does nothing.
@param e the WindowEvent that happened.
*/
public void windowIconified(WindowEvent e) {}
/**
Responds to window opened events; does nothing.
@param e the WindowEvent that happened.
*/
public void windowOpened(WindowEvent e) {}

}
