//-----------------------------------------------------------------------------
// HydroBase_GUI_CASS_InputFilter_JPanel - Input filter for Colorado 
//	agricultural statistic queries in the other data GUI.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 2005-02-10	J. Thomas Sapienza, RTi	Initial version.
// 2006-10-30	Steven A. Malers, RTi	Deprecate the class - use
//					HydroBase_GUI_CASSCropStats_InputFilter_
//					JPanel.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;

import RTi.Util.IO.PropList;

import RTi.Util.String.StringUtil;

/**
This class is an input filter panel for when ag stats data are queried from the
Other Data GUI.
*/
public class HydroBase_GUI_CASS_InputFilter_JPanel
extends InputFilter_JPanel {

/**
Constructor.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@deprecated Use HydroBase_GUI_CASSCropStats_InputFilter_JPanel or
HydroBase_GUI_CASSLivestockStats_InputFilter_JPanel.
*/
public HydroBase_GUI_CASS_InputFilter_JPanel(HydroBaseDMI dmi) {
	String rd = dmi.getRightIdDelim();
	String ld = dmi.getLeftIdDelim();

	List filters = new Vector();
	
	String tableName = HydroBase_GUI_Util._CASS_TABLE_NAME + "." + ld;
	
	filters.add(new InputFilter("", "", StringUtil.TYPE_STRING,
		null, null, false));

	List counties = dmi.getCountyRef();
	HydroBase_CountyRef county = null;
	int size = counties.size();
	List v1 = new Vector();
	for (int i = 0; i < size; i++) {
		county = (HydroBase_CountyRef)counties.get(i);
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
