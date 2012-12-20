package DWR.DMI.HydroBaseDMI;

import RTi.Util.IO.PropList;
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
{
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
    try {
        int port = -1; // Default
        boolean useStoredProcedures = true; // Always the case with newer HydroBase
        HydroBaseDMI dmi = new HydroBaseDMI ( databaseEngine, databaseServer, databaseName,
        port, // Don't use the port number - use the database instance instead
        systemLogin, // OK if null - use read-only guest
        systemPassword, // OK if null - use read-only guest
        useStoredProcedures );
        dmi.open();
        return new HydroBaseDataStore ( name, description, dmi );
    }
    catch ( Exception e ) {
        // TODO SAM 2010-09-02 Wrap the exception because need to move from default Exception
        throw new RuntimeException ( e );
    }
}

}