package DWR.DMI.HydroBaseDMI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTextField;

import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;
import RTi.Util.GUI.JGUIUtil;

import RTi.Util.String.StringUtil;

/**
Filter for HydroBase_StructureGeolocStructMeasType object queries.
*/
@SuppressWarnings("serial")
public class HydroBase_GUI_StructureGeolocStructMeasType_InputFilter_JPanel
extends InputFilter_JPanel
implements MouseListener
{

/**
Data store used with input filter.
*/
private HydroBaseDataStore __dataStore = null;

/**
Indicates whether the filter is for SFUT time series.
*/
private boolean __includeSFUT = false;

/**
Create an InputFilter_JPanel for creating where clauses
for HydroBase_StructureGeolocStructMeasType queries.  This is used by TSTool.
Default filter panel properties are used (e.g., 3 filter groups).
@return a JPanel containing InputFilter instances for HydroBase_StructureGeolocStructMeasType queries.
@param dataStore HydroBase datastore for database connection.
@param includeSFUT If true, include a filter for the SFUT.
@exception Exception if there is an error.
*/
public HydroBase_GUI_StructureGeolocStructMeasType_InputFilter_JPanel ( HydroBaseDataStore dataStore, boolean includeSFUT )
throws Exception
{	this ( dataStore, includeSFUT, -1, -1 );
}

/**
Create an InputFilter_JPanel for creating where clauses
for HydroBase_StructureGeolocStructMeasType queries.  This is used by TSTool.
@return a JPanel containing InputFilter instances for HydroBase_StructureGeolocStructMeasType queries.
@param dataStore HydroBase datastore for database connection.
@param includeSFUT If true, include a filter for the SFUT.
@param numFilterGroups number of filter groups to display
@param numWhereChoicesToDisplay maximum number of choices to display in drop down lists.
@exception Exception if there is an error.
*/
public HydroBase_GUI_StructureGeolocStructMeasType_InputFilter_JPanel (
    HydroBaseDataStore dataStore, boolean includeSFUT, int numFilterGroups, int numWhereChoicesToDisplay )
throws Exception
{   __dataStore = dataStore;
    __includeSFUT = includeSFUT;
    HydroBaseDMI hbdmi = (HydroBaseDMI)dataStore.getDMI();
    // County choices...
	List<HydroBase_CountyRef> countyDataList = hbdmi.getCountyRef();
	List<String> countyList = new Vector<String> ( countyDataList.size() );
	List<String> countyInternalList = new Vector<String> ( countyDataList.size());
	HydroBase_CountyRef county;
	int size = countyDataList.size();
	for ( int i = 0; i < size; i++ ) {
		county = countyDataList.get(i);
		countyList.add ( county.getCounty() + ", " + county.getST() );
		countyInternalList.add (county.getCounty() );
	}

	// Water district choices...
	List<HydroBase_WaterDistrict> districtDataList = hbdmi.getWaterDistricts();
	List<String> districtList = new Vector<String> ( districtDataList.size() );
	List<String> districtInternalList=new Vector<String>(districtDataList.size());
	HydroBase_WaterDistrict wd;
	size = districtDataList.size();
	for ( int i = 0; i < size; i++ ) {
		wd = districtDataList.get(i);
		districtList.add (wd.getWD() + " - "+wd.getWd_name());
		districtInternalList.add ("" + wd.getWD() );
	}

	// Water division choices...
	List<HydroBase_WaterDivision> divisionDataList = hbdmi.getWaterDivisions();
	List<String> divisionList = new Vector<String>( 7 );
	List<String> divisionInternalList = new Vector<String>( 7 );
	HydroBase_WaterDivision div;
	size = divisionDataList.size();
	for ( int i = 0; i < size; i++ ) {
		div = divisionDataList.get(i);
		divisionList.add (div.getDiv() + " - " + div.getDiv_name());
		divisionInternalList.add ("" + div.getDiv() );
	}
	
	// DSS structure type choices...
    List<HydroBase_DssStructureType> dssStructureTypeDataList = hbdmi.getDssStructureTypeList();
    List<String> dssStructureTypeList = new Vector<String>();
    List<String> dssStructureTypeInternalList = new Vector<String>();
    HydroBase_DssStructureType dssStructureType;
    size = dssStructureTypeDataList.size();
    for ( int i = 0; i < size; i++ ) {
        dssStructureType = dssStructureTypeDataList.get(i);
        dssStructureTypeList.add (dssStructureType.getStr_type() + " - " + dssStructureType.getStr_type_desc());
        dssStructureTypeInternalList.add ("" + dssStructureType.getStr_type() );
    }
    
    List<String> adminStructureTypeList = null;
    List<String> adminStructureTypeInternalList = null;
    if (hbdmi.isDatabaseVersionAtLeast(HydroBaseDMI.VERSION_20130404)) {
        // Admin structure type choices...
        List<HydroBase_AdminStructureType> adminStructureTypeDataList = hbdmi.getAdminStructureTypeList();
        adminStructureTypeList = new Vector<String>();
        adminStructureTypeInternalList = new Vector<String>();
        HydroBase_AdminStructureType adminStructureType;
        size = adminStructureTypeDataList.size();
        for ( int i = 0; i < size; i++ ) {
            adminStructureType = adminStructureTypeDataList.get(i);
            adminStructureTypeList.add (adminStructureType.getStrtype() + " - " + adminStructureType.getStrtype_desc());
            adminStructureTypeInternalList.add ("" + adminStructureType.getStrtype() );
        }
    }

	// Currently in use choices
    List<HydroBase_RefCIU> ciuDataList = hbdmi.getCIUVector();
    List<String> ciuList = new Vector<String>();
    List<String> ciuInternalList = new Vector<String>();
    size = ciuDataList.size();
    HydroBase_RefCIU ciu = null;
    for (int i = 0; i < size; i++) {
        ciu = ciuDataList.get(i);
        ciuList.add(ciu.getCode() + " - " + ciu.getDescription());
        ciuInternalList.add(ciu.getCode());
    }
    
	List<InputFilter> input_filters = new Vector<InputFilter>(8);
	input_filters.add ( new InputFilter (
		"", "", StringUtil.TYPE_STRING,
		null, null, true ) );	// Blank to disable filter
		
	InputFilter filter = new InputFilter (
		"County Name", "geoloc.county", "county",
		StringUtil.TYPE_STRING,
		countyList, countyInternalList, true );
	filter.setTokenInfo(",",0);
	input_filters.add ( filter );
	
	filter = new InputFilter (
		"District", "geoloc.wd", "wd",
		StringUtil.TYPE_INTEGER,
		districtList, districtInternalList, true );
	filter.setTokenInfo("-",0);
	input_filters.add ( filter );

	filter = new InputFilter (
		"Division", "geoloc.div", "div",
		StringUtil.TYPE_INTEGER,
		divisionList, divisionInternalList, true );
	filter.setTokenInfo("-",0);
	input_filters.add ( filter );
	
    if (hbdmi.isDatabaseVersionAtLeast(HydroBaseDMI.VERSION_20130404)) {
        input_filters.add ( new InputFilter (
            "DSS Structure Type", "structure.str_type", "str_type",
            StringUtil.TYPE_STRING,
            dssStructureTypeList, dssStructureTypeInternalList, true ) );
    }
	
	input_filters.add ( new InputFilter (
		"Elevation", "geoloc.elev", "elev",
		StringUtil.TYPE_DOUBLE,
		null, null, true ) );	
	
	input_filters.add ( new InputFilter (
		"HUC", "geoloc.huc", "huc",
		StringUtil.TYPE_STRING,
		null, null, true ) );

	input_filters.add ( new InputFilter (
		"Latitude", "geoloc.latdecdeg", "latdecdeg",
		StringUtil.TYPE_DOUBLE,
		null, null, true ) );	
		
	input_filters.add ( new InputFilter (
		"Longitude", "geoloc.longdecdeg", "longdecdeg",
		StringUtil.TYPE_DOUBLE,
		null, null, true ) );	

	// create the input filter for the PLSS Location
	filter = new InputFilter(
		HydroBase_GUI_Util._PLSS_LOCATION_LABEL,
		HydroBase_GUI_Util._PLSS_LOCATION, 
		HydroBase_GUI_Util._PLSS_LOCATION, StringUtil.TYPE_STRING,
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
	filter.addInputComponentMouseListener(this);
	filter.setInputComponentToolTipText("Click in this field to build a PLSS Location to use as a query constraint.");
	filter.setInputJTextFieldWidth(20);
	input_filters.add(filter);

    if ( includeSFUT ) {
        input_filters.add ( new InputFilter (
            "SFUT", "struct_meas_type.identifier", "identifier",
            StringUtil.TYPE_STRING,
            null, null, true ) );
    }

	if (hbdmi.isDatabaseVersionAtLeast(HydroBaseDMI.VERSION_19990305)) {
		input_filters.add(new InputFilter("Stream Mile",
			"geoloc.str_mile", "str_mile", 
			StringUtil.TYPE_DOUBLE, null, null, false));
	}
	else {
		input_filters.add(new InputFilter("Stream Mile",
			"structure.abbrev", "abbrev", 
			StringUtil.TYPE_DOUBLE, null, null, false));
	}
	
	/* Not in HydroBase StructureStructMeasType view
    input_filters.add ( new InputFilter (
       "Structure CIU", "structure.ciu", "ciu",
       StringUtil.TYPE_STRING,
       ciuList, ciuInternalList, true ) );
       */

	input_filters.add ( new InputFilter (
		"Structure ID", "structure.id", "id",
		StringUtil.TYPE_INTEGER,
		null, null, true ) );
		
	input_filters.add ( new InputFilter (
		"Structure Name", "structure.str_name", "str_name",
		StringUtil.TYPE_STRING,
		null, null, true ) );

	if (hbdmi.isDatabaseVersionAtLeast(HydroBaseDMI.VERSION_20130404)) {
       input_filters.add ( new InputFilter (
           "Structure Type", "structure.strtype", "strtype",
           StringUtil.TYPE_STRING,
           adminStructureTypeList, adminStructureTypeInternalList, true ) );
       
       input_filters.add ( new InputFilter (
           "Structure WDID", "structure.wdid", "wdid",
           StringUtil.TYPE_STRING,
           null, null, true ) );
	}

	input_filters.add ( new InputFilter (
		"UTM X", "geoloc.utm_x", "utm_x",
		StringUtil.TYPE_DOUBLE,
		null, null, true ) );		

	input_filters.add ( new InputFilter (
		"UTM Y", "geoloc.utm_y", "utm_y",
		StringUtil.TYPE_DOUBLE,
		null, null, true ) );		

	if ( numFilterGroups < 0 ) {
		// TODO SAM 2012-09-05 - need larger default?
		numFilterGroups = 3;
	}
    // Now create the filters, in alphabetical order of the label
    if ( numWhereChoicesToDisplay < 0 ) {
        // Default to 20
        numWhereChoicesToDisplay = 20;
    }
	setToolTipText ( "<html>HydroBase queries can be filtered based on structure data.</html>" );
	setInputFilters ( input_filters, numFilterGroups, numWhereChoicesToDisplay );
}

/**
Return the datastore used with the filter.
*/
public HydroBaseDataStore getDataStore ()
{
    return __dataStore;
}

/**
Return whether the filter includes SFUT.
*/
public boolean getIncludeSFUT()
{
    return __includeSFUT;
}

public void mouseClicked(MouseEvent event) {}

public void mouseExited(MouseEvent event) {}

public void mouseEntered(MouseEvent event) {}

/**
Responds to mouse pressed events.
@param event the event that happened.
*/
public void mousePressed(MouseEvent event) {
	JFrame temp = new JFrame();
	JGUIUtil.setIcon(temp, JGUIUtil.getIconImage());	
	HydroBase_GUI_Util.buildLocation(temp, (JTextField)event.getSource());
}

public void mouseReleased(MouseEvent event) {}

}