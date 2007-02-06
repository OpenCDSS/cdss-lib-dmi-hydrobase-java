//-----------------------------------------------------------------------------
// HydroBase_GUI_NASS_InputFilter_JPanel - Input filter for National 
//	agricultural statistic queries in the other data GUI.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 2005-02-10	J. Thomas Sapienza, RTi	Initial version.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;

import RTi.Util.IO.PropList;

import RTi.Util.String.StringUtil;

/**
This class in an input filter for querying nass data in the Other Data GUI.
*/
public class HydroBase_GUI_NASS_InputFilter_JPanel
extends InputFilter_JPanel {

/**
Constructor.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location
entry text field is clicked in.  Cannot be null.
*/
public HydroBase_GUI_NASS_InputFilter_JPanel(HydroBaseDMI dmi) {
	String rd = dmi.getRightIdDelim();
	String ld = dmi.getLeftIdDelim();

	Vector filters = new Vector();
	
	String tableName = HydroBase_GUI_Util._NASS_TABLE_NAME + "." + ld;
	
	filters.add(new InputFilter("", "", StringUtil.TYPE_STRING,
		null, null, false));

	Vector counties = dmi.getCountyRef();
	HydroBase_CountyRef county = null;
	int size = counties.size();
	Vector v1 = new Vector();
	for (int i = 0; i < size; i++) {
		county = (HydroBase_CountyRef)counties.elementAt(i);
		if (county.getCty() > 0) {
			v1.add(county.getCounty());
		}
	}
		
	filters.add(new InputFilter("County",
		tableName + "county" + rd, "county", StringUtil.TYPE_STRING,
		v1, v1, false));
		
	PropList filterProps = new PropList("InputFilter");
	filterProps.set("NumFilterGroups=1");
	filterProps.set("NumWhereRowsToDisplay=2");
	setToolTipText("<HTML>HydroBase queries can be filtered" 
		+ "<BR>based on agricultural statistic data.</HTML>");
	setInputFilters(filters, filterProps);
}

}