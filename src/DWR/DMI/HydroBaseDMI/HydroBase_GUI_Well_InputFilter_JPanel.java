//-----------------------------------------------------------------------------
// HydroBase_GUI_Well_InputFilter_JPanel - Input filter for well queries.
//-----------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//-----------------------------------------------------------------------------
// History:
//
// 2005-01-10	J. Thomas Sapienza, RTi	Initial version.
// 2005-02-01	JTS, RTi		Renamed from
//					HydroBase_InputFilter_JPanel_Well
// 2005-01-31	JTS, RTi		* Location queries are now handled via
//					  Input Filters.
//					* A MouseListener is now passed to the
//					  constructor for doing location builds.
// 2005-03-24	JTS, RTi		Removed a number of fields that are 
//					not returned from the stored procedure.
//-----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.awt.event.MouseListener;

import java.util.List;
import java.util.Vector;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;

import RTi.Util.String.StringUtil;

/**
This class is an input filter for querying well data on the Well GUI.
*/
public class HydroBase_GUI_Well_InputFilter_JPanel
extends InputFilter_JPanel {

/**
Constructor.
@param dmi the dmi to use to connect to the database.  Cannot be null.
@param listener the mouse listener to use for responding when the Location
entry text field is clicked in.  Cannot be null.
*/
public HydroBase_GUI_Well_InputFilter_JPanel(HydroBaseDMI dmi, MouseListener listener) {
	String rd = dmi.getRightIdDelim();
	String ld = dmi.getLeftIdDelim();

	InputFilter filter = null;
	List filters = new Vector();

	String wellTableName = HydroBase_GUI_Util._WELL_APPLICATION_TABLE_NAME + "." + ld;
	String geolocTableName = HydroBase_GUI_Util._GEOLOC_TABLE_NAME + "." + ld;

	filters.add(new InputFilter("", "", StringUtil.TYPE_STRING, null, null, false));

	filter = new InputFilter("ABCODate",
		wellTableName + "abcodate" + rd, "abcodate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);

	filter = new InputFilter("ABRDate",
		wellTableName + "abrdate" + rd, "abrdate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);

	filters.add(new InputFilter("Abreq",
		wellTableName + "abreq" + rd, "abreq",
		StringUtil.TYPE_INTEGER, null, null, false));
	filters.add(new InputFilter("Acreft",
		wellTableName + "acreft" + rd, "acreft",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Actcode",
		wellTableName + "actcode" + rd, "actcode",
		StringUtil.TYPE_STRING, null, null, false));

	filter = new InputFilter("Actdate",
		wellTableName + "actdate" + rd, "actdate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);				
	filters.add(filter);

	filters.add(new InputFilter("Aquifer 1",
		wellTableName + "aquifer1" + rd, "aquifer1",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Aquifer 2",
		wellTableName + "aquifer2" + rd, "aquifer2",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Area irrigated",
		wellTableName + "area_irr" + rd, "area_irr",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("BPerf",
		wellTableName + "bperf" + rd, "bperf",
		StringUtil.TYPE_INTEGER, null, null, false));		
	filters.add(new InputFilter("Basin",
		wellTableName + "basin" + rd, "basin",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Block",
		wellTableName + "block" + rd, "block",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Case number",
		wellTableName + "case_no" + rd, "case_no",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Comment",
		wellTableName + "comment" + rd, "comment",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Depth, feet",
		wellTableName + "depth" + rd, "depth", StringUtil.TYPE_INTEGER,
		null, null, false));
	filters.add(new InputFilter("Div",
		wellTableName + "div" + rd, "div", StringUtil.TYPE_INTEGER,
		null, null, false));
	filters.add(new InputFilter("Driller license",
		wellTableName + "driller_lic" + rd, "driller_lic",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Elevation",
		wellTableName + "elev" + rd, "elev",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Engineer",
		wellTableName + "engineer" + rd, "engineer",
		StringUtil.TYPE_STRING, null, null, false));

	filter = new InputFilter("EXDate",
		wellTableName + "exdate" + rd, "exdate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);				
	filters.add(filter);

	filters.add(new InputFilter("Filing",
		wellTableName + "filing" + rd, "filing",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Latitude",
		geolocTableName + "latdecdeg" + rd, "latdecdeg",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Level",
		wellTableName + "level" + rd, "level",
		StringUtil.TYPE_INTEGER, null, null, false));
	filters.add(new InputFilter("Log",
		wellTableName + "log" + rd, "log",
		StringUtil.TYPE_INTEGER, null, null, false));
	filters.add(new InputFilter("Longitude",
		geolocTableName + "longdecdeg" + rd, "longdecdeg",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Lot",
		wellTableName + "lot" + rd, "lot",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("MD",
		wellTableName + "md" + rd, "md",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Meter",
		wellTableName + "meter" + rd, "meter",
		StringUtil.TYPE_INTEGER, null, null, false));

	filter = new InputFilter("NBUDate",
		wellTableName + "nbudate" + rd, "nbudate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);

	filter = new InputFilter("NPDate",
		wellTableName + "npdate" + rd, "npdate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);

	filter = new InputFilter("NWCDate",
		wellTableName + "nwcdate" + rd, "nwcdate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);		

	filter = new InputFilter("Notice date",
		wellTableName + "noticedate" + rd, "noticedate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);

	filters.add(new InputFilter("Parcel size",
		wellTableName + "parcel_size" + rd, "parcel_size",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Permit number",
		wellTableName + "permitno" + rd, "permitno", 
		StringUtil.TYPE_INTEGER, null, null, false));
	filters.add(new InputFilter("Permit rpl",
		wellTableName + "permitrpl" + rd, "permitrpl",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Permit suffix",
		wellTableName + "permitsuf" + rd, "permitsuf",
		StringUtil.TYPE_STRING, null, null, false));

	// create the input filter for the PLSS Location
	filter = new InputFilter("PLSS Location",
		"PLSS_Location", "PLSS_Location", StringUtil.TYPE_STRING,
		null, null, false);
	// all constraints other than EQUALS are removed because PLSS Locations
	// are compared in a special way
	filter.removeConstraint(InputFilter.INPUT_ONE_OF);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);
	// the PLSS Location text field is not editable because users must go
	// through the PLSS Location JDialog to build a location
	filter.setInputJTextFieldEditable(false);
	// this listener must be set up so that the location builder dialog
	// can be opened when the PLSS Location text field is clicked on.
	filter.addInputComponentMouseListener(listener);
	filter.setInputComponentToolTipText("Click in this field to build "
		+ "a PLSS Location to use as a query constraint.");
	filter.setInputJTextFieldWidth(20);
	filters.add(filter);

	filters.add(new InputFilter("PAcre feet",
		wellTableName + "pacreft" + rd, "pacreft",
		StringUtil.TYPE_DOUBLE, null, null, false));

	filter = new InputFilter("PCDate",
		wellTableName + "pcdate" + rd, "pcdate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);

	filters.add(new InputFilter("PDepth",
		wellTableName + "pdepth" + rd, "pdepth",
		StringUtil.TYPE_INTEGER, null, null, false));

	filter = new InputFilter("PIDate",
		wellTableName + "pidate" + rd, "pidate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);

	filters.add(new InputFilter("PYield",
		wellTableName + "pyield" + rd, "pyield",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Pump license",
		wellTableName + "pump_lic" + rd, "pump_lic",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Qual",
		wellTableName + "qual" + rd, "qual",
		StringUtil.TYPE_INTEGER, null, null, false));
	filters.add(new InputFilter("Receipt",
		wellTableName + "recepit" + rd, "receipt",
		StringUtil.TYPE_STRING, null, null, false));

	filter = new InputFilter("SADate",
		wellTableName + "sadate" + rd, "sadate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);		

	filter = new InputFilter("SBUDate",
		wellTableName + "sbudate" + rd, "sbudate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);

	filters.add(new InputFilter("Statcode",
		wellTableName + "statcode" + rd, "statcode",
		StringUtil.TYPE_STRING, null, null, false));

	filter = new InputFilter("Statdate",
		wellTableName + "statdate" + rd, "statdate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);		

	filters.add(new InputFilter("Statute",
		wellTableName + "statute" + rd, "statute",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Subdivision name",
		wellTableName + "subdiv_name", "subdiv_name",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("TPerf",
		wellTableName + "tperf" + rd, "tperf",
		StringUtil.TYPE_INTEGER, null, null, false));
	filters.add(new InputFilter("Trancode",
		wellTableName + "trancode" + rd, "trancode",
		StringUtil.TYPE_STRING, null, null, false));

	filter = new InputFilter("Trandate",
		wellTableName + "trandate" + rd, "trandate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);

	filters.add(new InputFilter("Use 1",
		wellTableName + "use1" + rd, "use1",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Use 2",
		wellTableName + "use2" + rd, "use2",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Use 3",
		wellTableName + "use3" + rd, "use3",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("UTM X",
		geolocTableName + "utm_x" + rd, "utm_x",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("UTM Y",
		geolocTableName + "utm_y" + rd, "utm_y",
		StringUtil.TYPE_DOUBLE, null, null, false));
	filters.add(new InputFilter("Valid permit",
		wellTableName + "valid_permit" + rd, "valid_permit",
		StringUtil.TYPE_INTEGER, null, null, false));

	filter = new InputFilter("WADate",
		wellTableName + "wadate" + rd, "wadate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);

	filter = new InputFilter("WCDate",
		wellTableName + "wcdate" + rd, "wcdate",
		StringUtil.TYPE_STRING, null, null, false);
	filter.removeConstraint(InputFilter.INPUT_STARTS_WITH);
	filter.removeConstraint(InputFilter.INPUT_ENDS_WITH);
	filter.removeConstraint(InputFilter.INPUT_CONTAINS);		
	filters.add(filter);

	filters.add(new InputFilter("WD",
		wellTableName + "Wd" + rd, "wd", StringUtil.TYPE_INTEGER,
		null, null, false));
	filters.add(new InputFilter("Well name",
		wellTableName + "well_name" + rd, "well_name", 
		StringUtil.TYPE_STRING, null, null, false));		
	filters.add(new InputFilter("Well type",
		wellTableName + "well_type" + rd, "well_type",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Wellxno",
		wellTableName + "wellxno" + rd, "wellxno",
		StringUtil.TYPE_INTEGER, null, null, false));
	filters.add(new InputFilter("Wellxrpl",
		wellTableName + "wellxrpl" + rd, "wellxrpl",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Wellxsuf",
		wellTableName + "wellxsuf" + rd, "wellxsuf",
		StringUtil.TYPE_STRING, null, null, false));
	filters.add(new InputFilter("Yield, gpm",
		wellTableName + "yield" + rd, "yield", StringUtil.TYPE_DOUBLE,
		null, null, false));
		
	setToolTipText("<html>HydroBase queries can be filtered<br>based on well data.</html>");
	setInputFilters(filters, 3, 30);
}

}