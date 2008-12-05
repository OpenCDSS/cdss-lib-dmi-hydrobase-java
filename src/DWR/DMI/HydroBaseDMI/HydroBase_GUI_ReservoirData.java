//-----------------------------------------------------------------------------
// HydroBase_GUI_ReservoirData - Reservoir Data GUI
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 25 Sep 1997	DLG, RTi 		Created initial version.
// 26 Sep 1997  DLG, RTi 		Added area capacity query.
// 07 Dec 1997	SAM, RTi		Enable export, print.
// 29 Apr 1998  DLG, RTi		Updated to 1.1 event model, added
//					javadoc comments.
// 15 Feb 1999	Steven A. Malers, RTi	Fix bug where period was being displayed
//					as the structure number.
// 04 Apr 1999	SAM, RTi		Added HBDMI to all queries.
// 2001-11-12	SAM, RTi		Change GUI to JGUIUtil.
//					Remove import *.
//-----------------------------------------------------------------------------
// 2003-10-02	J. Thomas Sapienza, RTi	Initial Swing version.
// 2004-01-21	JTS, RTi		Changed to use the new JWorksheet method
//					of displaying a row count column.
// 2005-02-14	JTS, RTi		Converted all queries to use stored
//					procedures.
// 2005-06-22	JTS, RTi		* Column widths now come from the 
//					  table model, not the cell renderer.
//					* The table-specific cell renderers 
//					  were removed and replaced with a 
//					  single generic one.
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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
This class is a GUI for displaying additional reservoir data.
*/
public class HydroBase_GUI_ReservoirData 
extends JFrame 
implements ActionListener, WindowListener {

/**
Button labels.
*/
private final String	
	__BUTTON_CLOSE = 	"Close",
	__BUTTON_EXPORT = 	"Export",
	__BUTTON_HELP = 	"Help",
	__BUTTON_PRINT = 	"Print";

/**
DMI for communicating with the database.
*/
private HydroBaseDMI __dmi;

/**
The number of structure being displayed in the gui.
*/
private int __structureNum;

/**
Text fields for holding data.
*/
private JTextField       
	__divJTextField,
	__drainageAreaJTextField,
	__fromJTextField,
	__idJTextField,
	__maxJTextField,
	__normalJTextField,
	__statusJTextField,
	__structureJTextField,
	__surfaceAreaJTextField,
	__toJTextField,
	__wdJTextField;

/**
Worksheet for holding area cap data.
*/
private JWorksheet __worksheet;

/**
The name of the structure displayed in the gui.
*/
private String __structureName;

/**
Constructor.
@param dmi the dmi to use for communicating with the database.
@param structureName the name of the structure.
@param structureNum the number of the structure.
*/
public HydroBase_GUI_ReservoirData(HydroBaseDMI dmi, String structureName, 
int structureNum) {
	__dmi = dmi;
        __structureNum = structureNum;
        __structureName = structureName;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());
        setupGUI();

	submitAndDisplayReservoirQuery();
	submitAndDisplayAreaCapQuery();

	int wd = (new Integer(__wdJTextField.getText().trim())).intValue();
	int id = (new Integer(__idJTextField.getText().trim())).intValue();
	String name = __structureName;

	String rest = "Structure Data - Reservoir Data - "
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
	String routine = "HydroBase_GUI_ReservoirData.actionPerformed";
	String s = evt.getActionCommand();

	if (s.equals(__BUTTON_CLOSE)) {
		closeClicked();
        }
	else if (s.equals(__BUTTON_EXPORT)) {
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
			List outputStrings = formatOutput(format);
 			// Now export, letting the user decide the file...
			HydroBase_GUI_Util.export(this, eff[0], outputStrings);
		} 
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}		
	}
	else if (s.equals(__BUTTON_HELP)) {
	}
	else if (s.equals(__BUTTON_PRINT)) {
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
			List outputStrings = formatOutput(format);
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
	setVisible(false);
	dispose();
}

/**
Cleans up member variables.
*/
public void finalize()
throws Throwable {
	__dmi = null;
	__divJTextField = null;
	__drainageAreaJTextField = null;
	__fromJTextField = null;
	__idJTextField = null;
	__maxJTextField = null;
	__normalJTextField = null;
	__statusJTextField = null;
	__structureJTextField = null;
	__surfaceAreaJTextField = null;
	__toJTextField = null;
	__wdJTextField = null;
	__worksheet = null;
	__structureName = null;
	super.finalize();
}

/**
Formats output for printing or exporting.
@param format the format in which to format the data.
*/
public List formatOutput(int format) {
	List v = new Vector();

	String s0 = null;
	String s1 = null;
	String s2 = null;
	String s3 = null;

	int size = __worksheet.getRowCount();

	if (format == HydroBase_GUI_Util.SCREEN_VIEW) {
		// The output is pretty simple since the GUI is so simple...
		v.add(HydroBase_GUI_Util.formatStructureHeader(
			HydroBase_GUI_Util.trimText(__structureJTextField),
			HydroBase_GUI_Util.trimText(__divJTextField),
			HydroBase_GUI_Util.trimText(__wdJTextField),
			HydroBase_GUI_Util.trimText(__idJTextField), format));
		v.add("");
		v.add("NORMAL STORAGE: " 
			+ StringUtil.formatString(HydroBase_GUI_Util.trimText(
			__normalJTextField), "%-10.0f")
			+ "MAXIMUM STORAGE: " +
			StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__maxJTextField), "%-10.0f")
			+ "SURFACE AREA: " +
			StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__surfaceAreaJTextField),
			"%-10.0f") + "DRAINAGE AREA: " +
			StringUtil.formatString(
			HydroBase_GUI_Util.trimText(__drainageAreaJTextField), 
			"%-10.0f"));
		if (size > 0) {
			v.add("");
			v.add("AREA CAPACITY TABLE");
			v.add("ELEVATION      GAGE HT.      "
				+ "SURFACE        VOLUME");
			v.add("(FEET)      (FEET)     AREA(ACRES)    "
				+ "(AF)");
			v.add("______________________________________"
				+ "_________________________________________"
				+ "_________________________________________");

			for (int i = 0; i < size; i++) {
				s0 = __worksheet.getValueAtAsString(i, 0,
					"%6.0f");
				s1 = __worksheet.getValueAtAsString(i, 1,
					"%14.1f");
				s2 = __worksheet.getValueAtAsString(i, 2,
					"%16.0f");
				s3 = __worksheet.getValueAtAsString(i, 3,
					"%13.0f");
				v.add(
					StringUtil.formatString(s0, "%6.6s")
					+ StringUtil.formatString(s1, "%14.14s")
					+ StringUtil.formatString(s2, "%16.16s")
					+StringUtil.formatString(s3,"%13.13s"));
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
		v.add("NORMAL STORAGE" + delim + "MAXIMUM STORAGE" 
			+ delim + "SURFACE AREA" + delim + "DRAINAGE AREA" 
			+ delim);
		v.add(
			HydroBase_GUI_Util.trimText(__normalJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__maxJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__surfaceAreaJTextField) 
			+ delim
			+ HydroBase_GUI_Util.trimText(__drainageAreaJTextField) 
			+ delim);
		if (size > 0) {
			v.add("");
			v.add("ELEVATION(FEET)" + delim 
				+ "GAGE HT.(FEET)" + delim 
				+ "SURFACE AREA(ACRES)" + delim + "VOLUME(AF)" 
				+ delim);
				for (int i = 0; i < size; i++) {
				s0 = __worksheet.getValueAtAsString(i, 0);
				s1 = __worksheet.getValueAtAsString(i, 1);
				s2 = __worksheet.getValueAtAsString(i, 2);
				s3 = __worksheet.getValueAtAsString(i, 3);
				v.add(s0 + delim + s1 + delim 
					+ s2 + delim + s3 + delim);
			}
		}
	}	

	return v;
}

/**
Sets up the GUI objects.
*/
private void setupGUI() {
	String routine = "HydroBase_GUI_ReservoirData.setupGUI";
	addWindowListener(this);

        // objects used throughout the GUI layout
        Insets insetsTLBR = new Insets(7,7,7,7);
        Insets insetsNLNR = new Insets(0,7,0,7);
        Insets insetsNLBR = new Insets(0,7,7,7);
        Insets insetsTLNR = new Insets(7,7,0,7);
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

        __structureJTextField = new JTextField(20);
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

        __idJTextField = new JTextField(5);
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

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Normal Storage"), 
		0, 2, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(centerWJPanel, new JLabel("Maximum Storage"), 
		1, 2, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(centerWJPanel, new JLabel("Surface Area"), 
		2, 2, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);
        JGUIUtil.addComponent(centerWJPanel, new JLabel("Drainage Area"), 
		3, 2, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __normalJTextField = new JTextField(15);          
        __normalJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __normalJTextField, 
		0, 3, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __maxJTextField = new JTextField(15);      
        __maxJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __maxJTextField, 
		1, 3, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __surfaceAreaJTextField = new JTextField(15);      
        __surfaceAreaJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __surfaceAreaJTextField, 
		2, 3, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        __drainageAreaJTextField = new JTextField(15);
        __drainageAreaJTextField.setEditable(false);
        JGUIUtil.addComponent(centerWJPanel, __drainageAreaJTextField, 
		3, 3, 1, 1, 0, 0, insetsNLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

        JPanel timeJPanel = new JPanel();
        timeJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JGUIUtil.addComponent(centerWJPanel, timeJPanel, 
		0, 5, 4, 1, 0, 0, insetsTLBR, GridBagConstraints.HORIZONTAL, GridBagConstraints.EAST);     

        timeJPanel.add(new JLabel("Reservoir EOM Record Period"));

        __fromJTextField = new JTextField(15);
        __fromJTextField.setEditable(false);
        timeJPanel.add(__fromJTextField);

        timeJPanel.add(new JLabel("to"));

        __toJTextField = new JTextField(15);
        __toJTextField.setEditable(false);
        timeJPanel.add(__toJTextField);

        JGUIUtil.addComponent(centerWJPanel, new JLabel("Capacity Table"), 
		0, 6, 1, 1, 0, 0, insetsTLNR, GridBagConstraints.NONE, GridBagConstraints.WEST);

	PropList p = new PropList("HydroBase_GUI_ReservoirData.JWorksheet");
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
	p.add("JWorksheet.SelectionMode=ExcelSelection");

	int[] widths = null;
	JScrollWorksheet jsw = null;
	try {
		HydroBase_TableModel_ReservoirData tm = new
			HydroBase_TableModel_ReservoirData(new Vector());
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
	
		jsw = new JScrollWorksheet(cr, tm, p);
		__worksheet = jsw.getJWorksheet();

		widths = tm.getColumnWidths();
	}
	catch (Exception e) {
		Message.printWarning(2, routine, e);
		jsw = new JScrollWorksheet(0, 0, p);
		__worksheet = jsw.getJWorksheet();
	}
	__worksheet.setPreferredScrollableViewportSize(null);
	__worksheet.setHourglassJFrame(this);

        JGUIUtil.addComponent(centerWJPanel, jsw, 
		0, 7, 4, 1, 1, 1, insetsNLBR, GridBagConstraints.BOTH, GridBagConstraints.WEST);

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
	print.setToolTipText("Print form data.");
        bottomSouthNorthJPanel.add(print);
	SimpleJButton export = new SimpleJButton(__BUTTON_EXPORT, this);
	export.setToolTipText("Export data to a file.");
        bottomSouthNorthJPanel.add(export);
	SimpleJButton close = new SimpleJButton(__BUTTON_CLOSE, this);
	close.setToolTipText("Close form.");
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
		0, 1, 10, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

        // Frame settings
	
	String app = JGUIUtil.getAppNameForWindows();
	if (app == null || app.trim().equals("")) {
		app = "";
	}
	else {
		app += " - ";	
	}
	
        setTitle(app + "Reservoir Data");
        pack(); 
	setSize(getWidth(), getHeight() + 100);
        JGUIUtil.center(this);
        setVisible(true);        

	if (widths != null) {
		__worksheet.setColumnWidths(widths);
	}
}

/**
Submits an area cap query and displays the results in the worksheet.
*/
private void submitAndDisplayAreaCapQuery() {	
	String routine = "HydroBase_GUI_ReservoirData"
		+ ".submitAndDisplayAreaCapQuery";
	JGUIUtil.setWaitCursor(this, true);
	
	List results = null;
	try {
		results = __dmi.readAreaCapListForStructure_num(__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	if (results == null || results.size() == 0) {
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	__worksheet.setData(results);

	JGUIUtil.setWaitCursor(this, false);
}

/**
Submits a reservoir structure query and displays the results in the GUI.
*/
private void submitAndDisplayReservoirQuery() {
	String routine = "HydroBase_GUI_ReservoirData"
		+ ".submitAndDisplayReservoirQuery";
	JGUIUtil.setWaitCursor(this, true);
	
	HydroBase_StructureReservoir data = null;
	try {
		data = __dmi.readStructureReservoirForStructure_num(
			__structureNum);
	}
	catch (Exception e) {
		Message.printWarning(1, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	if (data == null) {
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	// Place data into the appropriate instantiated component object
	int curInt = data.getDiv();
	if (!DMIUtil.isMissing(curInt)) {
		__divJTextField.setText("" + curInt);
	}

	curInt = data.getWD();
	if (!DMIUtil.isMissing(curInt)) {
		__wdJTextField.setText("" + curInt);
	}

	curInt = data.getID();
	if (!DMIUtil.isMissing(curInt)) {
		__idJTextField.setText("" + curInt);
	}

	double curDouble = data.getNormal_storage();
	if (!DMIUtil.isMissing(curDouble)) {
		__normalJTextField.setText(
			StringUtil.formatString(curDouble, "%6.1f"));
	}

	curDouble = data.getMax_storage();
	if (!DMIUtil.isMissing(curDouble)) {
		__maxJTextField.setText(
			StringUtil.formatString(curDouble, "%6.1f"));
	}

	curDouble = data.getSurface_area();
	if (!DMIUtil.isMissing(curDouble)) {
		__surfaceAreaJTextField.setText(
			StringUtil.formatString(curDouble, "%6.1f"));
	}

	curDouble = data.getDrain_area();
	if (!DMIUtil.isMissing(curDouble)) {
		__drainageAreaJTextField.setText(
			StringUtil.formatString(curDouble, "%6.1f"));
	}        

	List results = null;
	try {
		results = __dmi.readStructMeasTypeListForStructure_numMeas_type(
			__structureNum, "ResEOM");
	}
	catch (Exception e) {
		Message.printWarning(1, routine, e);
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	if (results == null || results.size() == 0) {
		JGUIUtil.setWaitCursor(this, false);
		return;
	}

	Object o = results.get(0);
	HydroBase_StructMeasTypeView smt 
		= (HydroBase_StructMeasTypeView)o;

	curInt = smt.getStart_year();
	if (!DMIUtil.isMissing(curInt)) {
		__fromJTextField.setText("" + curInt);
	}

	curInt = smt.getEnd_year();
	if (!DMIUtil.isMissing(curInt)) {
		__toJTextField.setText("" + curInt);
	}

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
