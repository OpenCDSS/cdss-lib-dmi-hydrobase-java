// HydroBaseDataStoreFactory - Factory to instantiate HydroBaseDataStore instances.

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

import RTi.Util.IO.IOUtil;
import RTi.Util.IO.PropList;
import RTi.Util.Message.Message;
import riverside.datastore.DataStore;
import riverside.datastore.DataStoreFactory;

/**
Factory to instantiate HydroBaseDataStore instances.
*/
public class HydroBaseDataStoreFactory implements DataStoreFactory
{

/**
Create a HydroBaseDataStore instance and open the encapsulated HydroBaseDMI using the specified properties.
*/
public DataStore create ( PropList props )
{	String routine = "HydroBaseDataStoreFactor.create";
    String name = props.getValue ( "Name" );
    String description = props.getValue ( "Description" );
    if ( description == null ) {
        description = "";
    }
    String databaseEngine = props.getValue ( "DatabaseEngine" );
    String databaseServer = props.getValue ( "DatabaseServer" );
    String databaseName = props.getValue ( "DatabaseName" );
    String systemLogin = props.getValue ( "SystemLogin" );
    String systemPassword = props.getValue ( "SystemPassword" );
    // The following is used if making an ODBC DSN connection to a HydroBase database, rather than above
    String odbcName = IOUtil.expandPropertyForEnvironment("OdbcName",props.getValue("OdbcName"));
    String connectionProperties = IOUtil.expandPropertyForEnvironment("ConnectionProperties",props.getValue("ConnectionProperties"));
    // Create an initial datastore instance here with null DMI placeholder - it will be recreated below with DMI
    HydroBaseDataStore ds = new HydroBaseDataStore ( name, description, null );
    // Set the properties before there is a chance to throw an exception
    ds.setProperties(props);
    try {
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
            hdmi.setAdditionalConnectionProperties(connectionProperties);
            // The following will thrown an exception
            hdmi.open();
            ds = new HydroBaseDataStore( name, description, hdmi );
            return ds;
        }
        else {
	        int port = -1; // Default
	        boolean useStoredProcedures = true; // Always the case with newer HydroBase
	        HydroBaseDMI dmi = new HydroBaseDMI ( databaseEngine, databaseServer, databaseName,
	        port, // Don't use the port number - use the database instance instead
	        systemLogin, // OK if null - use read-only guest
	        systemPassword, // OK if null - use read-only guest
	        useStoredProcedures );
	        dmi.open();
	        ds = new HydroBaseDataStore ( name, description, dmi );
	        ds.setProperties(props);
	        return ds;
        }
    }
    catch ( Exception e ) {
    	// Don't rethrow an exception because want datastore to be created with unopened DMI
        Message.printWarning(3,routine,e);
        ds.setStatus(1);
        ds.setStatusMessage("" + e);
    }
    return ds;
}

}
