package us.co.state.dwr.hbguest;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.List;
import java.util.Vector;

import DWR.DMI.HydroBaseDMI.HydroBase_WaterDistrict;
import DWR.DMI.HydroBaseDMI.HydroBase_WaterDivision;
import RTi.Util.GUI.InputFilter;
import RTi.Util.GUI.InputFilter_JPanel;

import RTi.Util.String.StringUtil;

public class ColoradoWaterHBGuest_GUI_StructureGeolocMeasType_InputFilter_JPanel
extends InputFilter_JPanel
implements MouseListener
{

/**
Create an InputFilter_JPanel for ColoradoWaterHBGuest web services queries, which will return
HydroBase_StructureGeolocStructMeasType in the Java API, consistent with HydroBase.  This is used by TSTool.
Default filter panel properties are used (e.g., 3 filter groups).
@return a JPanel containing InputFilter instances for HydroBase_StructureGeolocStructMeasType queries.
@param hdmi HydroBaseDMI instance.
@param include_SFUT If true, include a filter for the SFUT.
@exception Exception if there is an error.
*/
public ColoradoWaterHBGuest_GUI_StructureGeolocMeasType_InputFilter_JPanel (
    ColoradoWaterHBGuestService service, boolean include_SFUT )
throws Exception
{	this ( service, include_SFUT, -1, -1 );
}

/**
Create an InputFilter_JPanel for ColoradoWaterHBGuest web services queries, which will return
HydroBase_StructureGeolocStructMeasType in the Java API, consistent with HydroBase.  This is used by TSTool.
@return a JPanel containing InputFilter instances for HydroBase_StructureGeolocStructMeasType queries.
@param hdmi HydroBaseDMI instance.
@param include_SFUT If true, include a filter for the SFUT.
@param filter_props Properties to configure the input filter, passed to the base class.
@exception Exception if there is an error.
*/
public ColoradoWaterHBGuest_GUI_StructureGeolocMeasType_InputFilter_JPanel (
    ColoradoWaterHBGuestService service, boolean include_SFUT,
	int numFilterGroups, int numWhereChoicesToDisplay )
throws Exception
{	// Fill in the county for input filters...
    /*
	List county_data_Vector = hbdmi.getCountyRef();
	List county_Vector = new Vector ( county_data_Vector.size() );
	List county_internal_Vector = new Vector ( county_data_Vector.size());
	HydroBase_CountyRef county;
	int size = county_data_Vector.size();
	for ( int i = 0; i < size; i++ ) {
		county = (HydroBase_CountyRef)county_data_Vector.get(i);
		county_Vector.add ( county.getCounty() + ", " + county.getST() );
		county_internal_Vector.add (county.getCounty() );
	}
	*/

	// Fill in the water district data for input filters...

	List<HydroBase_WaterDistrict> district_data_Vector = service.getWaterDistrictList();
	List district_Vector = new Vector ( district_data_Vector.size() );
	List district_internal_Vector = new Vector(district_data_Vector.size());
	for ( HydroBase_WaterDistrict wd : district_data_Vector ) {
		district_Vector.add (wd.getWD() + " - " + wd.getWd_name());
		district_internal_Vector.add ("" + wd.getWD() );
	}

	// Fill in the division data for input filters...

	List<HydroBase_WaterDivision> division_data_Vector = service.getWaterDivisionList();
	List<String> division_Vector = new Vector ( 7 );
	List<String> division_internal_Vector = new Vector ( 7 );
	for ( HydroBase_WaterDivision div: division_data_Vector ) {
		division_Vector.add (div.getDiv() + " - " + div.getDiv_name());
		division_internal_Vector.add ("" + div.getDiv() );
	}
	
	// Now define the input filters

	List<InputFilter> input_filters = new Vector(8);
	input_filters.add ( new InputFilter ("", "",
	    StringUtil.TYPE_STRING, null, null, true ) ); // Blank to disable filter

	/* Not enabled yet
	List v1 = new Vector();
	List v2 = new Vector();
	List cius = hbdmi.getCIUVector();
	size = cius.size();
	HydroBase_RefCIU ciu = null;
	for (int i = 0; i < size; i++) {
		ciu = (HydroBase_RefCIU)cius.get(i);
		v1.add(ciu.getCode() + " - " + ciu.getDescription());
		v2.add(ciu.getCode());
	}
	*/

	InputFilter filter;
	/* Not enabled yet
	filter = new InputFilter ( "County Name", "geoloc.county", "county",
		StringUtil.TYPE_STRING, county_Vector, county_internal_Vector, true );
	filter.setTokenInfo(",",0);
	input_filters.add ( filter );
	*/
	
	filter = new InputFilter ( "District", "geoloc.wd", "wd",
		StringUtil.TYPE_INTEGER, district_Vector, district_internal_Vector, false );
	filter.setTokenInfo("-",0);
	input_filters.add ( filter );

	filter = new InputFilter ( "Division", "geoloc.div", "div",
		StringUtil.TYPE_INTEGER, division_Vector, division_internal_Vector, false );
	filter.setTokenInfo("-",0);
	input_filters.add ( filter );
	
	/*
	input_filters.add ( new InputFilter ( "Elevation", "geoloc.elev", "elev",
		StringUtil.TYPE_DOUBLE, null, null, true ) );
		*/
	
	/*
	input_filters.add ( new InputFilter ( "HUC", "geoloc.huc", "huc",
		StringUtil.TYPE_STRING, null, null, true ) );
		*/

	/*
	input_filters.add ( new InputFilter ( "Latitude", "geoloc.latdecdeg", "latdecdeg",
		StringUtil.TYPE_DOUBLE, null, null, true ) );
		
	input_filters.add ( new InputFilter ( "Longitude", "geoloc.longdecdeg", "longdecdeg",
		StringUtil.TYPE_DOUBLE, null, null, true ) );
		*/

	/*
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
	*/

	/*
	input_filters.add(new InputFilter("Stream Mile", "str_mile", "str_mile", 
		StringUtil.TYPE_DOUBLE, null, null, true));
		*/
/*
	input_filters.add ( new InputFilter ( "Structure ID", "id", "id",
		StringUtil.TYPE_INTEGER, null, null, true ) );
		
	input_filters.add ( new InputFilter ( "Structure Name", "str_name", "str_name",
		StringUtil.TYPE_STRING, null, null, true ) );
		*/
	
	/*
    input_filters.add ( new InputFilter ( "Structure WDID", "wdid", "wdid",
        StringUtil.TYPE_INTEGER, null, null, true ) );
        */

	/* Not enabled yet
	if ( include_SFUT ) {
		input_filters.add ( new InputFilter ("SFUT", "struct_meas_type.identifier", "identifier",
			StringUtil.TYPE_STRING, null, null, true ) );
	}
	*/

	/*
	input_filters.add ( new InputFilter ( "UTM X", "utm_x", "utm_x",
		StringUtil.TYPE_DOUBLE, null, null, true ) );		

	input_filters.add ( new InputFilter ( "UTM Y", "utm_y", "utm_y",
		StringUtil.TYPE_DOUBLE, null, null, true ) );
		*/

	if ( numFilterGroups < 0 ) {
		// TODO SAM 2010-07-21 need larger default?
		numFilterGroups = 3;
		numWhereChoicesToDisplay = input_filters.size();
	}
	setToolTipText ( "<html>ColoradoWaterHBGuest queries can be filtered based on structure data.</html>" );
	setInputFilters ( input_filters, numFilterGroups, numWhereChoicesToDisplay );
}

public void mouseClicked(MouseEvent event) {}

public void mouseExited(MouseEvent event) {}

public void mouseEntered(MouseEvent event) {}

/**
Responds to mouse pressed events.
@param event the event that happened.
*/
public void mousePressed(MouseEvent event) {
    /** Not enabled - used for PLSS query
	JFrame temp = new JFrame();
	JGUIUtil.setIcon(temp, JGUIUtil.getIconImage());	
	HydroBase_GUI_Util.buildLocation(temp, (JTextField)event.getSource());
	*/
}

public void mouseReleased(MouseEvent event) {}

}