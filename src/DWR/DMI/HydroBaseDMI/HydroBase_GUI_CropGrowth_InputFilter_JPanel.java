//-----------------------------------------------------------------------------
// HydroBase_GUI_CropGrowth_InputFilter_JPanel - Input filter for crop growth 
//	queries.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 2005-01-10	J. Thomas Sapienza, RTi	Initial version.
// 2005-02-01	JTS, RTi		Renamed from
//					HydroBase_InputFilter_JPanel_CropGrowth
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;

import RTi.Util.IO.PropList;

import RTi.Util.String.StringUtil;

/**
This class is an input filter for querying crop growth data from the
Other Data GUI.
*/
public class HydroBase_GUI_CropGrowth_InputFilter_JPanel
extends InputFilter_JPanel {

/**
Constructor.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param methods a Vector of methods to use for querying consumptive use
types.  Cannot be null.
*/
public HydroBase_GUI_CropGrowth_InputFilter_JPanel(HydroBaseDMI dmi,
Vector methods) {
	String rd = dmi.getRightIdDelim();
	String ld = dmi.getLeftIdDelim();

	Vector filters = new Vector();

	filters.add(new InputFilter("", "", StringUtil.TYPE_STRING,
		null, null, false));

	filters.add(new InputFilter("Consumptive Use Method",
		"X", "method_desc", StringUtil.TYPE_STRING, 
		methods, methods, false));
		
	PropList filterProps = new PropList("InputFilter");
	filterProps.set("NumFilterGroups=1");
	filterProps.set("NumWhereRowsToDisplay=2");
	setToolTipText("<HTML>HydroBase queries can be filtered" 
		+ "<BR>based on agricultural statistic data.</HTML>");
	setInputFilters(filters, filterProps);
}

}
