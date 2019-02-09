// GeoViewUI - interface to allow an application to provide a general GeoView map interface

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
