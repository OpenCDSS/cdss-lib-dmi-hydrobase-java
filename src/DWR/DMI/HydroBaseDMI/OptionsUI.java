// OptionsUI - interface to connect application to options UI.

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

/**
Provide interface to connect application to options UI.
*/
public interface OptionsUI {

/**
Enable options in application based on the user level from HydroBase.
@param level Need to fill in from HydroBase. 
*/
public void enableMenusBasedOnUserLevel ( String level );

/**
Indicate whether the parent is view only (no editing).
@return true if the application is view-only (StateView) or
false if editing is enabled (CWRAT)
*/
public boolean getViewOnly();

/**
Set up a new user.
@param login User login.
@param password User password.
*/
public void setupNewUser ( String login, String password );

}
