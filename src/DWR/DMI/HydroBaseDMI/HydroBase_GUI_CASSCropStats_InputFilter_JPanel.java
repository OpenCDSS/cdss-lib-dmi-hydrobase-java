//-----------------------------------------------------------------------------
// HydroBase_GUI_CASSCropStats_InputFilter_JPanel - Input filter for Colorado 
//	Agricultural (crop) statistic queries
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 2005-02-10	J. Thomas Sapienza, RTi	Initial version.
// 2006-10-30	Steven A. Malers, RTi	Copy
//					HydroBase_GUI_CASS_InputFilter_JPanel
//					and make specific to crop statistics
//					because there are now also livestock
//					statistics.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;

import RTi.Util.String.StringUtil;

/**
This class is an input filter panel for CASS Crop Statistics.  See also the
similar filter for Livestock statistics.
*/
public class HydroBase_GUI_CASSCropStats_InputFilter_JPanel
extends InputFilter_JPanel {

/**
Constructor.
@param dmi the dmi to use to connect to the database.  Cannot be null.
*/
public HydroBase_GUI_CASSCropStats_InputFilter_JPanel(HydroBaseDMI dmi) {
	String rd = dmi.getRightIdDelim();
	String ld = dmi.getLeftIdDelim();

	List filters = new Vector();
	
	String tableName = HydroBase_GUI_Util._CASS_TABLE_NAME + "." + ld;
	
	filters.add(new InputFilter("", "", StringUtil.TYPE_STRING, null, null, false));

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
		
	setToolTipText("<html>HydroBase queries can be filtered<br>based on agricultural crop statistic data.</html>");
	setInputFilters(filters, 1, 2);
}

}