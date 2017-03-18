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

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;

import RTi.Util.String.StringUtil;

/**
This class in an input filter for querying NASS data in the Other Data GUI.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_NASS_InputFilter_JPanel
extends InputFilter_JPanel {

/**
Constructor.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location
entry text field is clicked in.  Cannot be null.
*/
public HydroBase_GUI_NASS_InputFilter_JPanel(HydroBaseDMI dmi) {
	String rd = dmi.getFieldRightEscape();
	String ld = dmi.getFieldLeftEscape();

	List<InputFilter> filters = new Vector<InputFilter>();
	
	String tableName = HydroBase_GUI_Util._NASS_TABLE_NAME + "." + ld;
	
	filters.add(new InputFilter("", "", StringUtil.TYPE_STRING, null, null, false));

	List<HydroBase_CountyRef> counties = dmi.getCountyRef();
	HydroBase_CountyRef county = null;
	int size = counties.size();
	List<String> v1 = new Vector<String>();
	for (int i = 0; i < size; i++) {
		county = counties.get(i);
		if (county.getCty() > 0) {
			v1.add(county.getCounty());
		}
	}
		
	filters.add(new InputFilter("County",
		tableName + "county" + rd, "county", StringUtil.TYPE_STRING,
		v1, v1, false));
		
	setToolTipText("<html>HydroBase queries can be filtered<br>based on agricultural statistic data.</html>");
	setInputFilters(filters, 1, 2);
}

}