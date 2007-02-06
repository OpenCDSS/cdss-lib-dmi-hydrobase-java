//-----------------------------------------------------------------------------
// HydroBase_GUI_OtherQuery - Other Query GUI
//-----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 01 Jun 1998	CGB, RTi		Created initial version. Modified from
//					from HBStationGUI.java
// 15 Feb 1999	Steven A. Malers, RTi	Changed some of the query code because
//					of changes in the low-level code.
// 04 Apr 1999	SAM, RTi		Add HBDMI to queries.
// 21 May 1999	SAM, RTi		Standardize reports with View Data.
//					Clean up code some.
// 16 May 2001	SAM, RTi		Change so report does not page.  Java
//					does not seem to have the limitation
//					any more.  Add finalize().  Change GUI
//					to JGUIUtil.  Change GUI strings to
//					not be static to save some memory.
//					Add more descriptive title to 
//					ReportGUI.
//					Change Crop Growth Characteristics to
//					Consumptive Use Method Data and add a
//					new summary report(this is kind of
//					hacked in but needed to keep the old
//					code working).
// 12 Jun 2001	SAM, RTi		Change so interface is not resizable.
//					Change "Is" to choices since all are
//					known.
//-----------------------------------------------------------------------------
// 2003-05-29	J. Thomas Sapienza, RTi	Initial swing version.
// 2003-05-30	JTS, RTi		Added code to copy from the table
// 2003-06-02	JTS, RTi		Added code so an hourglass displays when
//					sorting the table
// 2003-07-28	JTS, RTi		* Updated JWorksheet code to stop using
//					  deprecated methods.
//					* Removed old JWorksheet method of
//					  enabling copy/paste.
// 2003-09-23	JTS, RTi		Changed the export code to use 
//					the new export code in 
//					HydroBase_GUI_Util.
// 2004-01-20	JTS, RTi		Changed to use the new JWorksheet method
//					of displaying a row count column.
// 2004-06-16	JTS, RTi		Changed how agstats is queried.
// 2005-02-10	JTS, RTi		* Agstats was split out to Colorado 
//					  and National agriculatural statistics.
// 2005-02-14	JTS, RTi		Checked all dmi calls to make sure they
//					use stored procedures.
// 2005-04-28	JTS, RTi		Added all data members to finalize().
// 2005-06-22	JTS, RTi		* Column widths now come from the 
//					  table model, not the cell renderer.
//					* The table-specific cell renderers 
//					  were removed and replaced with a 
//					  single generic one.
// 2005-06-28	JTS, RTi		Removed DMI parameters from table models
// 2005-07-06	SAM, RTi		* Remove comment about irrigated crop
//					  numbers since they are no longer used.
// 2006-10-31	SAM, RTi		Change from ...CASS...InputFilter to
//					...CASSCropStats... input filter.
//-----------------------------------------------------------------------------
// EndHeader

package DWR.DMI.HydroBaseDMI;
 
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.GUI.JGUIUtil; 
import RTi.Util.GUI.JScrollWorksheet;
import RTi.Util.GUI.JWorksheet;
import RTi.Util.GUI.ReportJFrame;
import RTi.Util.GUI.SimpleJButton; 
import RTi.Util.GUI.SimpleJComboBox;

import RTi.Util.IO.ExportJGUI;
import RTi.Util.IO.PrintJGUI;
import RTi.Util.IO.PropList;

import RTi.Util.Message.Message;

import RTi.Util.String.StringUtil;

import RTi.Util.Time.StopWatch;

public class HydroBase_GUI_OtherQuery 
extends JFrame 
implements ActionListener, ItemListener, KeyListener, WindowListener {

/**
Button strings.
*/
private final String 	__BUTTON_GET_DATA = "Get Data",
			__BUTTON_CLOSE = "Close",
			__BUTTON_HELP = "Help",
			__BUTTON_PRINT = "Print",
			__BUTTON_EXPORT = "Export";
					
/**
General strings.
*/
private final String	__SUMMARY        = "Report Summary",
			__COUNTY_NAME    = "County",
			__ALL		= "All",
			__ALL_METHODS = "All CU Methods";

/**
Data type strings.
*/
private final String	__DTYPE_CASS	= "Colorado Agricultural Statistics",
			__DTYPE_NASS 	= "National Agricultural Statistics",
			__DTYPE_CROPC  	= "Crop Characteristics",
			__DTYPE_CROPG	= "Crop Growth Characteristics",
			__DTYPE_HUM	= "Human and Livestock " +
					  "Consumptive Use Characteristics";

/**
Reference to the dmi with which database communication will take place.
*/
private HydroBaseDMI __dmi;

/**
Colorado agstats query input filters.
*/
private HydroBase_GUI_CASSCropStats_InputFilter_JPanel __cassFilterJPanel = null;

/**
National agstats query input filters.
*/
private HydroBase_GUI_NASS_InputFilter_JPanel __nassFilterJPanel = null;

/**
Crop growth query input filters.
*/
private HydroBase_GUI_CropGrowth_InputFilter_JPanel __cropGrowthFilterJPanel 
	= null;

/**
Label for the worksheet.
*/
private JLabel __tableJLabel;

/**
Status bar for the GUI.
*/
private JTextField __statusJTextField;

/**
The table in which the data for most of the views is shown.
*/
private JWorksheet __worksheet;

/**
Button that does exports.
*/
private SimpleJButton __exportButton;
/**
Button that prints the table data.
*/
private SimpleJButton __printButton;

/**
Combo box to hold the kind of data that is being displayed.
*/
private SimpleJComboBox __dataTypeJComboBox;

/**
The label string displayed above the worksheet.
*/
private String __tableLabelString = null;

/**
Vector to hold two-element arrays of strings.  [0] contains the actual method
name, capitalized etc.  [1] contains the pretty-looking method name.
*/
private Vector __methods = null;

/**
Constructor.
@param dmi a non-null and connected dmi for doing the queries with.
*/
public HydroBase_GUI_OtherQuery(HydroBaseDMI dmi) {
	String routine = "HydroBase_GUI_OtherQuery";
	__dmi = dmi;
	JGUIUtil.setIcon(this, JGUIUtil.getIconImage());

	try {
		__methods = new Vector();
		Vector v = __dmi.readCUMethodList(true);
		int size = v.size();
		HydroBase_CUMethod m = null;
		String[] record = null;
		for (int i = 0; i < size; i++) {
			m = (HydroBase_CUMethod)v.elementAt(i);
			record = new String[2];
			record[0] = m.getMethod_desc();
			record[1] = m.getMethod_desc().trim();
//			record[1] = capitalizeNicely(m.getMethod_desc().trim());
			__methods.add(m.getMethod_desc());
		}
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading from "
			+ "database");
		Message.printWarning(2, routine, e);
		__methods = new Vector();
	}		

	setupGUI();
}

/**
Responds to action performed events.
@param evt the event that happened.
*/
public void actionPerformed(ActionEvent evt) {
	String routine = "actionPerformed";
        String s = evt.getActionCommand();

        if (s.equals(__BUTTON_HELP)) {
        }
        else if (s.equals(__BUTTON_CLOSE)) { 
                closeClicked();
        }
        else if (s.equals(__BUTTON_GET_DATA)) {
                submitQuery();
        }
	else if (s.equals(__BUTTON_EXPORT)) {
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
			Vector outputStrings = formatOutput(format);
 			// Now export, letting the user decide the file...
			HydroBase_GUI_Util.export(this, eff[0], outputStrings);
		} 
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}	
	}
	else if (s.equals(__BUTTON_PRINT)){ 
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
			Vector outputStrings = formatOutput(format);
	 		// Now print...
			PrintJGUI.print(this, outputStrings, 8);
		}
		catch (Exception ex) {
			Message.printWarning (2, routine, ex);
		}			
	}
}

/**
Capitalizes a string nicely.  It capitalizes all the first letters of a word, 
words being determined by characters following spaces, '-' or '_'.
@param s the String to capitalize nicely.
@return the nicely-capitalized String.
*/
private String capitalizeNicely(String s) {
	if (s == null || s.length() == 0) {
		return s;
	}
	if (s.length() == 1) {
		return s.toUpperCase();
	}

	s = s.toLowerCase();

	String[] delims = new String[3];
	delims[0] = " ";
	delims[1] = "_";
	delims[2] = "-";

	for (int i = 0; i < delims.length; i++) {
		String[] strings = s.split(delims[i]);
		String nice = "";
		String firstLetter = null;
		String rest = null;
		
		for (int j = 0; j < strings.length; j++) {
			if (strings[j].length() > 0) {
				firstLetter = strings[j].substring(0,1);
				rest = strings[j].substring(1);
				if (j > 0) {
					nice += delims[i];
				}
				nice += firstLetter.toUpperCase() + rest;
			}
			else {
				nice += delims[i];
			}
		}
		s = nice;
	}
	return s;
}

/**
Closes the GUI.
*/
protected void closeClicked() {
        setVisible(false);
}

/**
Fills in the JComboBoxes appropriately after a data type is selected from
the __dataTypeJComboBox.
*/
private void dataTypeJComboBoxClicked() {
	String routine = "dataTypeJComboBoxClicked";

        String dtype  = __dataTypeJComboBox.getSelected().trim();

	if (dtype.equals(__DTYPE_CASS)) {
		__cassFilterJPanel.setVisible(true);
		__nassFilterJPanel.setVisible(false);
		__cropGrowthFilterJPanel.setVisible(false);
		__tableLabelString = "Colorado Agricultural Statistics Data: ";
		__tableJLabel.setText(__tableLabelString);
	}
	else if (dtype.equals(__DTYPE_NASS)) {
		__cassFilterJPanel.setVisible(false);
		__nassFilterJPanel.setVisible(true);
		__cropGrowthFilterJPanel.setVisible(false);
		__tableLabelString = "National Agricultural Statistics Data: ";
		__tableJLabel.setText(__tableLabelString);
	}	
	else if (dtype.equals(__DTYPE_CROPC)) {
		__cassFilterJPanel.setVisible(false);
		__nassFilterJPanel.setVisible(false);
		__cropGrowthFilterJPanel.setVisible(false);
		__tableLabelString = "Crop Characteristics Data: ";
		__tableJLabel.setText(__tableLabelString);
	} 
	else if (dtype.equals(__DTYPE_CROPG)) {
		__cassFilterJPanel.setVisible(false);
		__nassFilterJPanel.setVisible(false);
		__cropGrowthFilterJPanel.setVisible(true);
		__tableLabelString = "Crop Growth Characteristics Data: ";
		__tableJLabel.setText(__tableLabelString);
	} 
	else if (dtype.equals(__DTYPE_HUM)) {
		__cassFilterJPanel.setVisible(false);
		__nassFilterJPanel.setVisible(false);
		__cropGrowthFilterJPanel.setVisible(false);
		__tableLabelString = "Human and Livestock Consumptive Use "
			+ "Characteristics Data: ";
		__tableJLabel.setText(__tableLabelString);
	} 
}

/**
Displays results from queries in the table.
@param results the results to display in the table.
*/
private void displayResults(Vector results) {
	String routine = "displayResults";
        String dtype = __dataTypeJComboBox.getSelected().trim();
	try {
	if (dtype.equals(__DTYPE_CASS)) {
		HydroBase_TableModel_AgriculturalCASSCropStats tm = 
			new HydroBase_TableModel_AgriculturalCASSCropStats(
			results);
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
		__worksheet.setCellRenderer(cr);
		__worksheet.setModel(tm);
		__worksheet.setColumnWidths(tm.getColumnWidths());
	}	
	else if (dtype.equals(__DTYPE_NASS)) {
		HydroBase_TableModel_AgriculturalNASSCropStats tm = 
			new HydroBase_TableModel_AgriculturalNASSCropStats(
			results);
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
		__worksheet.setCellRenderer(cr);
		__worksheet.setModel(tm);
		__worksheet.setColumnWidths(tm.getColumnWidths());
	}		
	else if ( dtype.equals(__DTYPE_CROPC)) {
		HydroBase_TableModel_Cropchar tm = 
			new HydroBase_TableModel_Cropchar(results);
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
		__worksheet.setCellRenderer(cr);
		__worksheet.setModel(tm);
		__worksheet.setColumnWidths(tm.getColumnWidths());	
	}
	else if ( dtype.equals(__DTYPE_CROPG)) {
		// not applicable
	}
	else if ( dtype.equals(__DTYPE_HUM)) {
		HydroBase_TableModel_CUCoeff tm = 
			new HydroBase_TableModel_CUCoeff(results);
		HydroBase_CellRenderer cr = new HydroBase_CellRenderer(tm);
		__worksheet.setCellRenderer(cr);
		__worksheet.setModel(tm);
		__worksheet.setColumnWidths(tm.getColumnWidths());		
	}
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error building table");
		Message.printWarning(2, routine, e);
	}
}

/**
Clean up for garbage collection.
*/
protected void finalize()
throws Throwable {
	__dmi = null;
	__cassFilterJPanel = null;
	__nassFilterJPanel = null;
	__cropGrowthFilterJPanel = null;
	__tableJLabel = null;
	__statusJTextField = null;
	__worksheet = null;
	__exportButton = null;
	__printButton = null;
	__dataTypeJComboBox = null;
	__tableLabelString = null;
	__methods = null;
	super.finalize();
}

/**
Formats crop growth data for display in a report.
@param vectors An array of 3 vectors.  [0] contains the vector of records
read from CUBlaneyCriddle, [1] contains the vector of records read from
CUModHargreaves, [2] contains the Vector of records read from CUPenmanMonteith.
@return the number of records read in by the database in total of all three
Vectors.
*/
private int formatCropGrowthReport(Vector[] vectors) {
	if (vectors == null) {
		return -1;
	}

        PropList reportProp = new PropList("ReportJFrame.props");
        reportProp.set("TotalWidth=750");
        reportProp.set("TotalHeight=550"); 
        reportProp.set("Title=" + __SUMMARY);
        reportProp.set("DisplayFont=Courier");
        reportProp.set("DisplayStyle=" + Font.PLAIN);
        reportProp.set("DisplaySize=11");
        reportProp.set("PrintFont=Courier");
        reportProp.set("PrintStyle=" + Font.PLAIN);
        reportProp.set("PrintSize=7");
        //reportProp.set("PageLength=100");
        reportProp.set("PageLength=50000");
	String report_title= __dataTypeJComboBox.getSelected().trim() 
		+ " Report";
	reportProp.set("Title="+report_title);

	int recordCount = 0;
	
	Vector strings = new Vector();
	strings.add("Crop Growth Characteristics by Consumptive Use (CU) "
		+ "Method");
	Vector blaney = vectors[0];
	String current = "";
	if (blaney != null && blaney.size() > 0) {
		int size = blaney.size();
		recordCount += size;
		HydroBase_CUBlaneyCriddle bc = null;
		for (int i = 0; i < size; i++) {
			bc = (HydroBase_CUBlaneyCriddle)blaney.elementAt(i);
			if (!current.equals(bc.getMethod_desc())) {
				strings.add("");
				current = bc.getMethod_desc();
				strings.add(current);
				strings.add("");
				strings.add("                      Curve   "
					+ "    Curve   Crop Growth" );
				strings.add("Crop                  Type    "
					+ "    Value   Coefficient" );
				strings.add("------------------------------"
					+ "-------------------------" );
				
			}

			strings.add(formatCUBlaneyCriddle(bc));
		}
	}

	Vector hargreaves = vectors[1];
	if (hargreaves != null && hargreaves.size() > 0) {
		int size = hargreaves.size();
		recordCount += size;
		HydroBase_CUModHargreaves mh = null;
		strings.add("");
		strings.add("MODIFIED-HARGREAVES");
		strings.add("");
		strings.add("                      Days to    Days to     "
			+ "Days to  Days to Days to   Days to  Crop     Crop"
			+ "     Crop" );
		strings.add("Crop                  10% Growth 80% Growth  1st "
			+ "Cut  2nd Cut Maturity  Harvest  Coef K1  Coef K2  "
			+ "Coef K3" );
		strings.add("-----------------------------------------------"
			+ "-------------------------------------------------"
			+ "----------" );
		
		for (int i = 0; i < size; i++) {
			mh = (HydroBase_CUModHargreaves)hargreaves.elementAt(i);
			strings.add(formatCUModHargreaves(mh));
		}		
	}

	current = "";
	Vector penman = vectors[2];
	if (penman != null && penman.size() > 0) {
		int size = penman.size();
		recordCount += size;
		HydroBase_CUPenmanMonteith pm = null;
		strings.add("");
		strings.add("PENMAN-MONTEITH_ALFALFA");
		strings.add("");
		strings.add("                         Growth   Curve   Crop "
			+ "Growth" );
		strings.add("Crop                     Stage    Value   "
			+ "Coefficient" );
		strings.add("------------------------------------------"
			+ "-------------" );
		
		
		for (int i = 0; i < size; i++) {
			pm = (HydroBase_CUPenmanMonteith)penman.elementAt(i);
			strings.add(formatCUPenmanMonteith(pm));
		}		
	}

	ReportJFrame j = new ReportJFrame(strings, reportProp);

	return recordCount;
}

/**
Formats a HydroBase_CUBlaneyCriddle record for output into the crop growth
report.
@param bc the HydroBase_CUBlaneyCriddle record to format the data of
@return a String representing the HydroBase_CUBlaneyCriddle object's data.
*/
private String formatCUBlaneyCriddle(HydroBase_CUBlaneyCriddle bc) {
	String name = bc.getCropname();
	String type = bc.getCurve_type();	
	int value = bc.getCurve_value();
	double coeff = bc.getCropgrowcoeff();

	name = StringUtil.formatString(name, "%-21.21s");
	type = StringUtil.formatString(type, "%-8.8s");

	String valueS = null;
	if (DMIUtil.isMissing(value)) {
		valueS = "        ";
	}
	else {
		valueS = StringUtil.formatString(
			StringUtil.formatString(value, "%8d"), "%8.8s");
	}
	String coeffS = null;
	if (DMIUtil.isMissing(coeff)) {
		coeffS = "        ";
	}
	else {
		coeffS = StringUtil.formatString(
			StringUtil.formatString(coeff, "%6.2f"), "%8.8s");
	}

	return name + " " + type + " " + valueS + " " + coeffS;
}

/**
Formats a HydroBase_CUModHargreaves record for output into the crop growth
report.
@param bc the HydroBase_CUModHargreaves record to format the data of
@return a String representing the HydroBase_CUModHargreaves object's data.
*/
private String formatCUModHargreaves(HydroBase_CUModHargreaves mh) {
	String name = mh.getCropname();
	int cover10 = mh.getCover_10();
	int cover80 = mh.getCover_80();
	int firstcut = mh.getFirstcut();
	int secondcut = mh.getSecondcut();
	int maturity = mh.getMaturity();
	int harvest = mh.getHarvest();
	float k1 = mh.getK1();
	float k2 = mh.getK2();
	float k3 = mh.getK3();

	name = StringUtil.formatString(name, "%-21.21s");

	String cover10S = null;
	if (DMIUtil.isMissing(cover10)) {
		cover10S = "          ";
	}
	else {
		cover10S = StringUtil.formatString(
			StringUtil.formatString(cover10, "%10d"), "%10.10s");
	}

	String cover80S = null;
	if (DMIUtil.isMissing(cover80)) {
		cover80S = "          ";
	}
	else {
		cover80S = StringUtil.formatString(
			StringUtil.formatString(cover80, "%10d"), "%10.10s");
	}

	String firstcutS = null;
	if (DMIUtil.isMissing(firstcut)) {
		firstcutS = "        ";
	}
	else {
		firstcutS = StringUtil.formatString(
			StringUtil.formatString(firstcut, "%8d"), "%8.8s");
	}

	String secondcutS = null;
	if (DMIUtil.isMissing(secondcut)) {
		secondcutS = "        ";
	}
	else {
		secondcutS = StringUtil.formatString(
			StringUtil.formatString(secondcut, "%8d"), "%8.8s");
	}

	String maturityS = null;
	if (DMIUtil.isMissing(maturity)) {
		maturityS = "        ";
	}
	else {
		maturityS = StringUtil.formatString(
			StringUtil.formatString(maturity, "%8d"), "%8.8s");
	}

	String harvestS = null;
	if (DMIUtil.isMissing(harvest)) {
		harvestS = "        ";
	}
	else {
		harvestS = StringUtil.formatString(
			StringUtil.formatString(harvest, "%8d"), "%8.8s");
	}

	String k1S = null;
	if (DMIUtil.isMissing(k1)) {
		k1S = "        ";
	}
	else {
		k1S = StringUtil.formatString(
			StringUtil.formatString(k1, "%8.3f"), "%8.8s");
	}

	String k2S = null;
	if (DMIUtil.isMissing(k2)) {
		k2S = "        ";
	}
	else {
		k2S = StringUtil.formatString(
			StringUtil.formatString(k2, "%8.3f"), "%8.8s");
	}

	String k3S = null;
	if (DMIUtil.isMissing(k3)) {
		k3S = "        ";
	}
	else {
		k3S = StringUtil.formatString(
			StringUtil.formatString(k3, "%8.3f"), "%8.8s");
	}

	return name + " " + cover10S + " " + cover80S + " " + firstcutS + " "
		+ secondcutS + " " + maturityS + " " + harvestS + " " + k1S 
		+ " " + k2S + " " + k3S;
}

/**
Formats a HydroBase_CUPenmanMonteith record for output into the crop growth
report.
@param bc the HydroBase_CUPenmanMonteith record to format the data of
@return a String representing the HydroBase_CUPenmanMonteith object's data.
*/
private String formatCUPenmanMonteith(HydroBase_CUPenmanMonteith pm) {
	String name = pm.getCropname();
	int stage = pm.getGrowthstage_no();
	int value = pm.getCurve_value();
	double coeff = pm.getCropgrowcoeff();

	name = StringUtil.formatString(name, "%-21.21s");

	String stageS = null;
	if (DMIUtil.isMissing(stage)) {
		stageS = "        ";
	}
	else {
		stageS = StringUtil.formatString(
			StringUtil.formatString(stage, "%8d"), "%8.8s");
	}
	
	String valueS = null;
	if (DMIUtil.isMissing(value)) {
		valueS = "        ";
	}
	else {
		valueS = StringUtil.formatString(
			StringUtil.formatString(value, "%8d"), "%8.8s");
	}
	String coeffS = null;
	if (DMIUtil.isMissing(coeff)) {
		coeffS = "        ";
	}
	else {
		coeffS = StringUtil.formatString(
			StringUtil.formatString(coeff, "%6.2f"), "%8.8s");
	}

	return name + " " + stageS + " " + valueS + " " + coeffS;
}

/**
Responsible for formatting output.
@param format format delimiter flag defined in this class
@return formatted Vector for exporting, printing, etc..
*/
private Vector formatOutput(int format) {
        int size = __worksheet.getRowCount();
        if (size == 0) {
                return new Vector();
        }

        char delim = HydroBase_GUI_Util.getDelimiterForFormat(format);
        
	// Delimited.  Output in the order shown on the screen
	int colCount = __worksheet.getColumnCount();
	String s = "";			

	Vector v = new Vector();
	for (int j = 0; j < colCount; j++) {
		s += __worksheet.getColumnName(j, true) + delim;
	}
	v.addElement(s);

	int[] selected = __worksheet.getSelectedRows();
	int length = selected.length;
	boolean isSelected = false;
	String o;
	String d = "";			
        for (int i = 0; i < size; i++) {
		isSelected = false;
		if (length == 0) {
			isSelected = true;
		}
		else {
			for (int j = 0; j < length; j++) {
				if (i == selected[j]) {
					isSelected = true;
				}
			}
		}
		if (isSelected) {
			s = "";
			for (int j = 0; j < colCount; j++) {
				o = __worksheet.getValueAtAsString(i, j);
				s += o + delim;
			}
			v.addElement(s);			
		}
	}

        return v;
}

/**
Returns the filter value from the specified filter panel.
@param panel the filter panel from which to return the filter value.
@return the filter value from the specified filter panel.
*/
private String getFilterValue(InputFilter_JPanel panel) {
	InputFilter filter = panel.getInputFilter(0);

	if (filter.getWhereLabel().trim().equals("")) {
		return null;
	}

	return filter.getInputInternal().trim();
}
	
/**
Respond to ItemEvents.
@param evt ItemEvent object.
*/
public void itemStateChanged(ItemEvent evt) {
        Object o = evt.getItemSelectable();

        if (o.equals(__dataTypeJComboBox)) {
        	dataTypeJComboBoxClicked();
        }
}

/**
Responds to key pressed events.
@param e the KeyEvent that happened.
*/
public void keyPressed(KeyEvent e) { 
	int code = e.getKeyCode();
	if (code == e.VK_ENTER) {
		submitQuery();
	}
}
/**
Responds to key released events; does nothing.
@param e the KeyEvent that happened.
*/
public void keyReleased(KeyEvent e) {}

/**
Responds to key typed events; does nothing.
@param e the KeyEvent that happened.
*/
public void keyTyped(KeyEvent e) {}

/**
Readies the GUI for user interaction.
*/
private void ready() {
        __statusJTextField.setText("Ready");
        JGUIUtil.setWaitCursor(this, false);
}

/**
Sets up the GUI.
*/
private void setupGUI() {
	addWindowListener(this);
        
        // objects used throughout the GUI layout
        Insets insetsNLNR = new Insets(0,7,0,7);
        Insets insetsNNNR = new Insets(0,0,0,7);
        Insets insetsNLNN = new Insets(0,7,0,0);
        Insets insetsTLBR = new Insets(7,7,7,7);
        Insets insetsTLNN = new Insets(7,7,0,0);
        Insets insetsNLBR = new Insets(0,7,7,7);
        Insets insetsTLNR = new Insets(7,7,0,7);
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        
        // North JPanel
        JPanel northJPanel = new JPanel();
        northJPanel.setLayout(new BorderLayout());
        getContentPane().add("North", northJPanel);
        
        // North West JPanel
        JPanel northWJPanel = new JPanel();
        northWJPanel.setLayout(gbl);
        northJPanel.add("West", northWJPanel);
        
        JGUIUtil.addComponent(northWJPanel, new JLabel("Query Options:"), 
                0, 0, 2, 1, 0, 0, insetsTLNN, gbc.NONE, gbc.WEST);

        JGUIUtil.addComponent(northWJPanel, new JLabel("Data Type:"), 
                0, 1, 1, 1, 0, 0, insetsNLNN, gbc.NONE, gbc.WEST);

	__dataTypeJComboBox = new SimpleJComboBox();
	__dataTypeJComboBox.add(__DTYPE_CASS);
	__dataTypeJComboBox.add(__DTYPE_NASS);
	__dataTypeJComboBox.add(__DTYPE_CROPC);
	__dataTypeJComboBox.add(__DTYPE_CROPG);
	__dataTypeJComboBox.add(__DTYPE_HUM);
        JGUIUtil.addComponent(northWJPanel, __dataTypeJComboBox, 
                1, 1, 5, 1, 0, 0, gbc.HORIZONTAL, gbc.WEST);
	__dataTypeJComboBox.addItemListener(this);

	__cassFilterJPanel = new HydroBase_GUI_CASSCropStats_InputFilter_JPanel(__dmi);
	__nassFilterJPanel = new HydroBase_GUI_NASS_InputFilter_JPanel(__dmi);
	__cropGrowthFilterJPanel 
		= new HydroBase_GUI_CropGrowth_InputFilter_JPanel(
		__dmi, __methods);

	__cassFilterJPanel.addEventListeners(this);
	__nassFilterJPanel.addEventListeners(this);
	__cropGrowthFilterJPanel.addEventListeners(this);

	JGUIUtil.addComponent(northWJPanel, __cassFilterJPanel,
		0, 2, 3, 1, 0, 0, insetsNLNN, gbc.NONE, gbc.WEST);
	JGUIUtil.addComponent(northWJPanel, __nassFilterJPanel,
		0, 2, 3, 1, 0, 0, insetsNLNN, gbc.NONE, gbc.WEST);
	JGUIUtil.addComponent(northWJPanel, __cropGrowthFilterJPanel,
		0, 2, 3, 1, 0, 0, insetsNLNN, gbc.NONE, gbc.WEST);

	__cropGrowthFilterJPanel.setVisible(false);

        SimpleJButton get = new SimpleJButton(__BUTTON_GET_DATA, this );
	get.setToolTipText("Retrieve data from database.");
        JGUIUtil.addComponent(northWJPanel, get, 
		6, 2, 1, 1, 0, 0, insetsTLBR, gbc.NONE, gbc.WEST);
		
        // Center Filler JPanel
        JPanel centerJPanel = new JPanel();
        centerJPanel.setLayout(gbl);
	getContentPane().add("Center", centerJPanel);        

	PropList p = 
		new PropList("HydroBase_GUI_OtherQuery.JWorksheet");

//	p.add("JWorksheet.CellFont=Courier");
//	p.add("JWorksheet.CellStyle=Plain");
//	p.add("JWorksheet.CellSize=11");
//	p.add("JWorksheet.HeaderFont=Arial");
//	p.add("JWorksheet.HeaderStyle=Plain");
//	p.add("JWorksheet.HeaderSize=11");
//	p.add("JWorksheet.HeaderBackground=LightGray");
	p.add("JWorksheet.ShowRowHeader=true");
	p.add("JWorksheet.AllowCopy=true");	
	p.add("JWorksheet.ShowPopupMenu=true");

	JScrollWorksheet jsw = new JScrollWorksheet(0, 0, p);
	__worksheet = jsw.getJWorksheet();
	
	__worksheet.setHourglassJFrame(this);
	__tableJLabel = new JLabel();
	JGUIUtil.addComponent(centerJPanel, __tableJLabel, 
		1, 1, 7, 1, 1, 0, 
		insetsNLNN, gbc.HORIZONTAL, gbc.WEST);	
	JGUIUtil.addComponent(centerJPanel, jsw,
		1, 2, 7, 3, 1, 1, insetsNLBR, gbc.BOTH, gbc.WEST);
        
        //Bottom JPanel(Consist of two more JPanels)
        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BorderLayout());
        getContentPane().add("South", bottomJPanel);
                                
        // Bottom Center JPanel
        JPanel bottomCJPanel = new JPanel();
        bottomCJPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomJPanel.add("Center", bottomCJPanel);

	__printButton = new SimpleJButton(__BUTTON_PRINT, this);
	bottomCJPanel.add(__printButton);
	__printButton.setToolTipText("Print form data.");
	__printButton.setEnabled(false);
	
	__exportButton = new SimpleJButton(__BUTTON_EXPORT, this);
	__exportButton.setToolTipText("Export data to a file.");
	bottomCJPanel.add(__exportButton);
	__exportButton.setEnabled(false);

        SimpleJButton close = new SimpleJButton(__BUTTON_CLOSE, this);
	close.setToolTipText("Close form.");
        bottomCJPanel.add(close);
        
//        SimpleJButton help = new SimpleJButton(__BUTTON_HELP, this);
//        bottomCJPanel.add(help);
        
        // Bottom South JPanel
        JPanel bottomSJPanel = new JPanel();
        bottomSJPanel.setLayout(gbl);
        bottomJPanel.add("South", bottomSJPanel);
        
        __statusJTextField = new JTextField();
        __statusJTextField.setEditable(false);
        JGUIUtil.addComponent(bottomSJPanel, __statusJTextField, 0, 1, 
                10, 1, 1, 0, gbc.HORIZONTAL, gbc.WEST);
                
	// set defaults, based off initial data type choice
	__dataTypeJComboBox.select(__DTYPE_CASS); // default upon create
	dataTypeJComboBoxClicked();
                
        // Frame settings
        setTitle("Other Data Query");
	if (	(JGUIUtil.getAppNameForWindows() == null) ||
		JGUIUtil.getAppNameForWindows().equals("") ) {
		setTitle ( "Other Data - Query" );
	}
	else {	setTitle( JGUIUtil.getAppNameForWindows() +
		" - Other Data - Query" );
	}	
        pack();
        setSize(700, 550);
        
	__tableJLabel.setText(HydroBase_GUI_Util.LIST_LABEL);

        JGUIUtil.center(this);
        setVisible(true);
}

/**
Sets the GUI as visible or not.
@param state if true, the gui is set visible.  If false, it is not.
*/
public void setVisible(boolean state) {
        if (state) {
		__worksheet.clear();
		__dataTypeJComboBox.select(__DTYPE_CASS); // default upon create
		dataTypeJComboBoxClicked();

		// set up current choices for data type and time step
                ready();
        }
        super.setVisible(state);
}

/**
Submits the query and fills the data.
*/
private void submitQuery() {
	String routine = "submitQuery";

        String dtype = __dataTypeJComboBox.getSelected().trim();

	JGUIUtil.setWaitCursor(this, true);
        __statusJTextField.setText("Retrieving data...");

	StopWatch sw = new StopWatch();
	sw.start();
	Vector results = null;
        __tableJLabel.setText(HydroBase_GUI_Util.LIST_LABEL);	
	int records = 0;
	try {
	if (dtype.equals(__DTYPE_CASS)) {
		if (!__cassFilterJPanel.checkInput(true)) {
			ready();
			return;
		}		
		results = __dmi.readAgriculturalCASSCropStatsList(
			__cassFilterJPanel, null, null,
			null, null, null, false);
		records = results.size();
	}
	else if (dtype.equals(__DTYPE_NASS)) {
		if (!__nassFilterJPanel.checkInput(true)) {
			ready();
			return;
		}		
		results = __dmi.readAgriculturalNASSCropStatsList(
			__nassFilterJPanel, null, null,
			null, null, false);
		records = results.size();
	}	
	else if ( dtype.equals(__DTYPE_CROPC)) {
		results = __dmi.readCropcharList();
		records = results.size();
	}
	else if ( dtype.equals(__DTYPE_CROPG)) {
		if (!__cropGrowthFilterJPanel.checkInput(true)) {
			ready();
			return;
		}		
		__worksheet.clear();
		String realMethod = getFilterValue(__cropGrowthFilterJPanel);
		if (realMethod == null) {
			realMethod = "*";
		}
				
		records = formatCropGrowthReport(
			__dmi.readCropGrowthList(__cropGrowthFilterJPanel,
			realMethod));
		String plural = "";
		if (records != 1) {
			plural = "s";
		}
				
		sw.stop();
	        String status = "" + records + " record" + plural 
			+ " returned in " + sw.getSeconds() + " seconds.";
		__tableJLabel.setText(__tableLabelString + status);
        	__statusJTextField.setText(status);

		__exportButton.setEnabled(false);
		__printButton.setEnabled(false);
		JGUIUtil.setWaitCursor(this, false);
		return;
	}
	else if ( dtype.equals(__DTYPE_HUM)) {
		results = __dmi.readCUCoeffList();
		records = results.size();
	}

	displayResults(results);

        int displayed = __worksheet.getRowCount();
	String plural = "";
	if (displayed != 1) {
		plural = "s";
	}
	
	sw.stop();
        String status = "" + results.size() + " record" + plural 
		+ " returned in " + sw.getSeconds() + " seconds.";
        __statusJTextField.setText(status);
	__tableJLabel.setText("Other Data Records: " + status);
	if (displayed > 0) {
		__exportButton.setEnabled(true);
		__printButton.setEnabled(true);
	}
	else {
		__exportButton.setEnabled(false);
		__printButton.setEnabled(false);
	}	
	
	}
	catch (Exception e) {
		Message.printWarning(1, routine, "Error reading from database");
		Message.printWarning(2, routine, e);
	}

	JGUIUtil.setWaitCursor(this, false);
}

/**
Responds to window activated events.
@param e the WindowEvent event that happened.
*/
public void windowActivated(WindowEvent e) {}
/**
Responds to window deactivated events.
@param e the WindowEvent event that happened.
*/
public void windowDeactivated(WindowEvent e) {}
/**
Responds to window deiconified events.
@param e the WindowEvent event that happened.
*/
public void windowDeiconified(WindowEvent e) {}
/**
Responds to window closed events.
@param e the WindowEvent event that happened.
*/
public void windowClosed(WindowEvent e) {}
/**
Responds to window closing events.
@param e the WindowEvent event that happened.
*/
public void windowClosing(WindowEvent e) {
	closeClicked();
}
/**
Responds to window iconified events.
@param e the WindowEvent event that happened.
*/
public void windowIconified(WindowEvent e) {}
/**
Responds to window opened events.
@param e the WindowEvent event that happened.
*/
public void windowOpened(WindowEvent e) {}

}