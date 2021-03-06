// HydroBase_GUI_CASSCropStats_InputFilter_JPanel - Input filter for Colorado Agricultural (crop) statistic queries

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2019 Colorado Department of Natural Resources

CDSS HydroBase Database Java Library is free software:  you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    CDSS HydroBase Database Java Library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with CDSS HydroBase Database Java Library.  If not, see <https://www.gnu.org/licenses/>.

NoticeEnd */

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
@SuppressWarnings("serial")
public class HydroBase_GUI_CASSCropStats_InputFilter_JPanel
extends InputFilter_JPanel {

/**
Constructor.
@param dmi the dmi to use to connect to the database.  Cannot be null.
*/
public HydroBase_GUI_CASSCropStats_InputFilter_JPanel(HydroBaseDMI dmi) {
	String rd = dmi.getFieldRightEscape();
	String ld = dmi.getFieldLeftEscape();

	List<InputFilter> filters = new Vector<InputFilter>();
	
	String tableName = HydroBase_GUI_Util._CASS_TABLE_NAME + "." + ld;
	
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
		
	setToolTipText("<html>HydroBase queries can be filtered<br>based on agricultural crop statistic data.</html>");
	setInputFilters(filters, 1, 2);
}

}
