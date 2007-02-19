package DWR.DMI.HydroBaseDMI;

import RTi.GIS.GeoView.GeoViewJPanel;

/**
Interface to allow an application to provide a general GeoView map
interface.  This interface is being implemented to decouple CWRAT from
HydroBaseDMI.
*/
public interface GeoViewUI {

/**
Return the GeoViewJPanel instance in the main application.
*/
public GeoViewJPanel getGeoViewJPanel();

/**
Open a map file and display the map.
*/
public void openGVP ( String filename );

/**
Indicate whether the map is visible in the application.
@return true if the map is visible in the main application.
*/
public boolean isMapVisible();
}
