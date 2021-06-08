// HydroBaseDataStore - Data store for HydroBase database.

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

import java.io.IOException;

import RTi.DMI.AbstractDatabaseDataStore;
import RTi.DMI.DMI;
import RTi.Util.IO.IOUtil;
import RTi.Util.IO.PropList;
import RTi.Util.String.StringUtil;
import riverside.datastore.DataStoreVersionChecker;

/**
Data store for HydroBase database.  This class maintains the database connection information in a general way.
*/
public class HydroBaseDataStore extends AbstractDatabaseDataStore implements DataStoreVersionChecker
{
    
/**
Indicate whether this datastore is wrapping a legacy DMI, for example as used with previously used with TSTool.
This flag is needed because it is possible that the legacy DMI will be named "HydroBase" and a new datastore
also will be named "HydroBase".  This may be done during transition to allow the datastore to be phased in with the
same name.  However, there needs to be some way to tell the two datastores apart, for example to allow a list
of input filter panels to be searched in response to a user selecting "HydroBase" - in this case the input filter
corresponding to the data store should be used in preference to the one used with the legacy DMI.
*/
private boolean __isLegacyDMI = false;
    
/**
Construct a data store given a DMI instance, which is assumed to be open.
@param name identifier for the data store
@param description name for the data store
@param dmi DMI instance to use for the data store.
*/
public HydroBaseDataStore ( String name, String description, DMI dmi )
{
    setName ( name );
    setDescription ( description );
    setDMI ( dmi );
}

/**
Construct a data store given a DMI instance, which is assumed to be open.  This version is used to wrap a
legacy DMI instance and legacyDMI can be specified so that the instance can be differentiated from a more
modern datastore instance.
@param name identifier for the data store
@param description name for the data store
@param dmi DMI instance to use for the data store.
@param isLegacyDMI specify as true if the data store is a 
*/
public HydroBaseDataStore ( String name, String description, DMI dmi, boolean isLegacyDMI )
{   this ( name, description, dmi );
    __isLegacyDMI = isLegacyDMI;
}

/**
 * Check the database version using an operator and version string.
 * @pra
 * @param operator the operator to use to compare the datastore version with 'version',
 * ">", ">=", "<", "<=", "=" or "==".
 * @param version the version to compare to, format depends on the database version format.
 */
public boolean checkVersion ( String operator, String version ) {
	String dbVersion = getVersionForCheck();
	if ( (dbVersion == null) || dbVersion.isEmpty() ) {
		// Unable to do check.
		return false;
	}
	else {
		// HydroBase versions are strings of format YYYYMMDD so can do alphabetical comparison.
		return StringUtil.compareUsingOperator(dbVersion, operator, version);
	}
}

/**
Factory method to construct a data store connection from a properties file.
@param filename name of file containing property strings
*/
public static HydroBaseDataStore createFromFile ( String filename )
throws IOException, Exception
{
    // Read the properties from the file
    PropList props = new PropList ("");
    props.setPersistentName ( filename );
    props.readPersistent ( false );
    String name = IOUtil.expandPropertyForEnvironment("Name",props.getValue("Name"));
    String description = IOUtil.expandPropertyForEnvironment("Description",props.getValue("Description"));
    String databaseEngine = IOUtil.expandPropertyForEnvironment("DatabaseEngine",props.getValue("DatabaseEngine"));
    String databaseServer = IOUtil.expandPropertyForEnvironment("DatabaseServer",props.getValue("DatabaseServer"));
    String databaseName = IOUtil.expandPropertyForEnvironment("DatabaseName",props.getValue("DatabaseName"));
    String systemLogin = IOUtil.expandPropertyForEnvironment("SystemLogin",props.getValue("SystemLogin"));
    String systemPassword = IOUtil.expandPropertyForEnvironment("SystemPassword",props.getValue("SystemPassword"));
    // The following is used if making an ODBC DSN connection to a HydroBase database, rather than above
    String odbcName = IOUtil.expandPropertyForEnvironment("OdbcName",props.getValue("OdbcName"));
    
    if ( (odbcName != null) && !odbcName.equals("") ) {
    	/*
        // An ODBC connection is configured so use it
        GenericDMI dmi = null;
        dmi = new GenericDMI (
            databaseEngine, // Needed for internal SQL handling
            odbcName, // Must be configured on the machine
            systemLogin, // OK if null - use read-only guest
            systemPassword ); // OK if null - use read-only guest
            */
        // Have to substitute the DMI from above into a new HydroBaseDMI
    	boolean useStoredProcedures = true; // Newer databases should have stored procedures
        HydroBaseDMI hdmi = new HydroBaseDMI(databaseEngine, odbcName, systemLogin, systemPassword, useStoredProcedures );
        hdmi.open();
        HydroBaseDataStore ds = new HydroBaseDataStore( name, description, hdmi );
        return ds;
    }
    else {
	    // Get the properties and create an instance
	    int port = -1; // Determine port from instance name
	    boolean useStoredProcedures = true; // Always the case with newer database
	    HydroBaseDMI dmi = new HydroBaseDMI ( databaseEngine, databaseServer,
	        databaseName, port, systemLogin, systemPassword, useStoredProcedures );
	    dmi.open();
	    HydroBaseDataStore ds = new HydroBaseDataStore( name, description, dmi );
	    return ds;
    }
}

/**
Indicate whether the datastore is a legacy DMI wrapper.
*/
public boolean getIsLegacyDMI ()
{
    return __isLegacyDMI;
}

/**
 * Return the version for version checks, in form YYYYMMDD,
 * which is what HydroBase database distributions use.
 * @return the database version string used in checks, taken from the database name,
 * or null if no database connection.
 */
public String getVersionForCheck () {
	if ( getDMI() == null ) {
		// No DMI instance.
		return null;
	}
	HydroBaseDMI dmi = (HydroBaseDMI)getDMI();
	if ( !dmi.isOpen() ) {
		// No database connection.
		return null;
	}
	return dmi.getDatabaseVersionFromName();
}

}