package DWR.DMI.HydroBaseDMI;

import java.io.IOException;

import RTi.DMI.AbstractDatabaseDataStore;
import RTi.DMI.DMI;
import RTi.Util.IO.IOUtil;
import RTi.Util.IO.PropList;

/**
Data store for HydroBase database.  This class maintains the database connection information in a general way.
*/
public class HydroBaseDataStore extends AbstractDatabaseDataStore
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
    
    // Get the properties and create an instance
    int port = -1; // Determine port from instance name
    boolean useStoredProcedures = true; // Always the case with newer database
    HydroBaseDMI dmi = new HydroBaseDMI ( databaseEngine, databaseServer,
        databaseName, port, systemLogin, systemPassword, useStoredProcedures );
    dmi.open();
    HydroBaseDataStore ds = new HydroBaseDataStore( name, description, dmi );
    return ds;
}

/**
Indicate whether the datastore is a legacy DMI wrapper.
*/
public boolean getIsLegacyDMI ()
{
    return __isLegacyDMI;
}

}