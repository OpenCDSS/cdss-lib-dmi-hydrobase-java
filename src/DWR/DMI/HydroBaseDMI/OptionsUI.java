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
