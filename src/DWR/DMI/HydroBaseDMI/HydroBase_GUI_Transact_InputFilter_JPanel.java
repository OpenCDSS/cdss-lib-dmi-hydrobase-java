// HydroBase_GUI_Transact_InputFilter_JPanel - Input filter for transact queries.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2023 Colorado Department of Natural Resources

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

package DWR.DMI.HydroBaseDMI;

import java.awt.event.MouseListener;

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilterCriterionType;
import RTi.Util.GUI.InputFilter_JPanel;

import RTi.Util.String.StringUtil;

@SuppressWarnings("serial")
public class HydroBase_GUI_Transact_InputFilter_JPanel
extends InputFilter_JPanel {

/**
Constructor.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location
entry text field is clicked in.  Cannot be null.
@param queryByExample if true, then the filter should be usable.
If false, then the GUI was opened from another screen with particular query settings that should not be changed.
*/
public HydroBase_GUI_Transact_InputFilter_JPanel(HydroBaseDMI dmi,
MouseListener listener, boolean queryByExample) {
	if (queryByExample) {
		setupNormalFilters(dmi, listener);
	}
	else {
		setupStructureFilter(dmi);
	}
}

/**
Sets up filters for regular GUI operation when query by example must be done.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location entry text field is clicked in.  Cannot be null.
*/
private void setupNormalFilters(HydroBaseDMI dmi, MouseListener listener) {
	String rd = dmi.getFieldRightEscape();
	String ld = dmi.getFieldLeftEscape();

	InputFilter filter = null;

	List<InputFilter> filters = new Vector<>();

	List<String> types = new Vector<>();
	types.add("A");
	types.add("C");
	types.add("CA");

	String tableName = HydroBase_GUI_Util._TRANS_TABLE_NAME + "." + ld;

	filters.add(new InputFilter("", "", StringUtil.TYPE_STRING, null, null, false));

	filters.add(new InputFilter("Aband",
		tableName + "aband" + rd, "aband", StringUtil.TYPE_STRING,
		null, null, false));
	filters.add(new InputFilter("Action Comment",
		tableName + "action_comment" + rd, "action_comment",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Action Update",
		tableName + "action_update" + rd, "action_update",
		StringUtil.TYPE_STRING, null, null, false));

	filter = new InputFilter("Adj Date",
		tableName + "adj_date" + rd, "adj_date", StringUtil.TYPE_STRING,
		null, null, false);
	filter.removeConstraint(InputFilterCriterionType.INPUT_STARTS_WITH.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_ENDS_WITH.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_CONTAINS.toString());		
	filters.add(filter);

	filters.add(new InputFilter("Adj Type",
		tableName + "adj_type" + rd, "adj_type", StringUtil.TYPE_STRING,
		null, null, false));		
	filters.add(new InputFilter("Administration Number",
		tableName + "admin_no" + rd, "admin_no",
		StringUtil.TYPE_DOUBLE, null, null, false));

	filter = new InputFilter("Apro Date",
		tableName + "apro_date" + rd, "apro_date",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilterCriterionType.INPUT_STARTS_WITH.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_ENDS_WITH.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_CONTAINS.toString());				
	filters.add(filter);

	filters.add(new InputFilter("Associated ID",
		tableName + "assoc_id" + rd, "id", StringUtil.TYPE_INTEGER,
		null, null, false));
	filters.add(new InputFilter("Associated Type",
		tableName + "assoc_type" + rd, "assoc_type",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Associated WD",
		tableName + "assoc_wd" + rd, "wd", StringUtil.TYPE_INTEGER,
		null, null, false));
	filters.add(new InputFilter("Aug Role",
		tableName + "aug_role" + rd, "aug_role",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Case Number",
		tableName + "case_no" + rd, "case_no", StringUtil.TYPE_STRING,
		null, null, false));

	List<HydroBase_CountyRef> counties = dmi.getCountyRef();
	HydroBase_CountyRef county = null;
	int size = counties.size();
	List<String> v1 = new Vector<>();
	List<String> v2 = new Vector<>();
	for (int i = 0; i < size; i++) {
		county = counties.get(i);
		if (county.getCty() > 0) {
			v1.add(county.getCounty());
			v2.add("" + county.getCty());
		}
	}
		
	filters.add(new InputFilter("County",
		tableName + "cty" + rd, "cty", StringUtil.TYPE_STRING,
		v1, v2, false));

	filters.add(new InputFilter("Decreed Rate, cfs",
		tableName + "rate_amt" + rd, "rate_amt",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Decreed Volume, acft",
		tableName + "vol_amt" + rd, "vol_amt",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Last Due Diligence",
		tableName + "last_due_dil" + rd, "last_due_dil",
		StringUtil.TYPE_STRING, null, null, false));

	// Create the input filter for the PLSS location.
	filter = new InputFilter("Location",
		"PLSS_Location", "PLSS_Location", StringUtil.TYPE_STRING,
		null, null, false);
	// All constraints other than EQUALS are removed because PLSS locations
	// are compared in a special way.
	filter.removeConstraint(InputFilterCriterionType.INPUT_ONE_OF.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_STARTS_WITH.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_ENDS_WITH.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_CONTAINS.toString());
	// The PLSS Location text field is not editable because users must go
	// through the PLSS Location JDialog to build a location.
	filter.setInputJTextFieldEditable(false);
	// This listener must be set up so that the location builder dialog
	// can be opened when the PLSS Location text field is clicked on.
	filter.addInputComponentMouseListener(listener);
	filter.setInputComponentToolTipText("Click in this field to build "
		+ "a PLSS Location to use as a query constraint.");
	filter.setInputJTextFieldWidth(20);
	filters.add(filter);
		
	filters.add(new InputFilter("Order Number",
		tableName + "order_no" + rd, "order_no",
		StringUtil.TYPE_INTEGER, null, null, false));

	filter = new InputFilter("Padj Date",
		tableName + "padj_date" + rd, "padj_date",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilterCriterionType.INPUT_STARTS_WITH.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_ENDS_WITH.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_CONTAINS.toString());		
	filters.add(filter);

	filters.add(new InputFilter("Plan ID",
		tableName + "plan_id" + rd, "plan_id", StringUtil.TYPE_INTEGER,
		null, null, false));
	filters.add(new InputFilter("Plan WD",
		tableName + "plan_wd" + rd, "plan_wd", StringUtil.TYPE_INTEGER,
		null, null, false));

	filters.add(new InputFilter("Priority Number",
		tableName + "prior_no" + rd, "prior_no",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Rate Amount (cfs)",
		tableName + "rate_amt" + rd, "rate_amt", StringUtil.TYPE_DOUBLE,
		null, null, false));
	filters.add(new InputFilter("Rights associated with Case No.",
		tableName + "case_no" + rd, "case_no",
		StringUtil.TYPE_STRING, null, null, false));		
	filters.add(new InputFilter("Status Type (C, A, or CA)",
		tableName + "status_type" + rd, "status_type",
		StringUtil.TYPE_STRING, types, types, false));		
	filters.add(new InputFilter("Structure ID",
		tableName + "id" + rd, "id",
		StringUtil.TYPE_INTEGER, null, null, false));

	List<HydroBase_DssStructureType> strTypes = dmi.getDssStructureTypeList();
	HydroBase_DssStructureType strType = null;
	v1 = new Vector<>();
	v2 = new Vector<>();
	for (int i = 0; i < strTypes.size(); i++) {
		strType = strTypes.get(i);
		v1.add(strType.getStr_type() + " - " + strType.getStr_type_desc());
		v2.add(strType.getStr_type());
	}
		
	filters.add(new InputFilter("Structure Type",
		ld + "structure.strtype" + rd, "strtype",
		StringUtil.TYPE_STRING, v1, v2, false));
	filters.add(new InputFilter("Transfer Num",
		tableName + "trans_num" + rd, "trans_num",
		StringUtil.TYPE_INTEGER, null, null, false));
	filters.add(new InputFilter("Trans Type",
		tableName + "transfer_type" + rd, "transfer_type",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Transfer WD",
		tableName + "tran_wd" + rd, "tran_wd",
		StringUtil.TYPE_INTEGER, null, null, false));
	filters.add(new InputFilter("Use",
		tableName + "use" + rd, "use",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Volume Amount (acft)",
		tableName + "vol_amt" + rd, "vol_amt", StringUtil.TYPE_DOUBLE,
		null, null, false));
	filters.add(new InputFilter("Water Right Name",
		tableName + "wr_name" + rd, "wr_name",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Water Source",
		tableName + "wd_stream_name" + rd, "wd_stream_name",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("WD",
		tableName + "wd" + rd, "wd",
		StringUtil.TYPE_INTEGER, null, null, false));

	setToolTipText("<html>HydroBase queries can be filtered<br>based on ground water data.</html>");
	setInputFilters(filters, 3, 42);
}

/**
Sets up filters for GUI operation when query by example cannot be done.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location
entry text field is clicked in.  Cannot be null.
*/
private void setupStructureFilter(HydroBaseDMI dmi) {
	String rd = dmi.getFieldRightEscape();
	String ld = dmi.getFieldLeftEscape();

	InputFilter filter = null;

	List<InputFilter> filters = new Vector<>();

	String tableName = HydroBase_GUI_Util._TRANS_TABLE_NAME + "." + ld;

	filter = new InputFilter("Structure Number",
		tableName + "structure_num" + rd, "structure_num",
		StringUtil.TYPE_INTEGER, null, null, false);
	filter.removeConstraint(InputFilterCriterionType.INPUT_BETWEEN.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_LESS_THAN.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_GREATER_THAN.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_LESS_THAN_OR_EQUAL_TO.toString());
	filter.removeConstraint(InputFilterCriterionType.INPUT_GREATER_THAN_OR_EQUAL_TO.toString());
	filter.setInputJTextFieldEditable(false);
	filters.add(filter);

	setToolTipText("<html>HydroBase queries can be filtered<br>based on ground water data.</html>");
	setInputFilters(filters, 1, 1);
}

}