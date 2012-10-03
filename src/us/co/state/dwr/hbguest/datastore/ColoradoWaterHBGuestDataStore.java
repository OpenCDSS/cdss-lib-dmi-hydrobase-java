package us.co.state.dwr.hbguest.datastore;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import riverside.datastore.AbstractWebServiceDataStore;
import us.co.state.dwr.hbguest.ColoradoWaterHBGuestService;

import RTi.Util.IO.IOUtil;
import RTi.Util.IO.PropList;

/**
Data store for ColoradoWaterHBGuest web service.  This class maintains the web service information in a general way.
This data store was created from a previous "input type" convention.  Consequently, this code is mainly a
wrapper for code that previously was developed.
*/
public class ColoradoWaterHBGuestDataStore extends AbstractWebServiceDataStore
{

/**
ColoradoWaterHBGuest instance used as SOAP API.
*/
private ColoradoWaterHBGuestService __coloradoWaterHBGuestService = null;
    
/**
Constructor for web service.
*/
public ColoradoWaterHBGuestDataStore ( String name, String description, URI serviceRootURI )
throws URISyntaxException, IOException
{
    setName ( name );
    setDescription ( description );
    setServiceRootURI ( serviceRootURI );
    
    // TODO SAM 2012-05-03 This should use the ServiceRootAPI but it does not...
    setColoradoWaterHBGuestService ( new ColoradoWaterHBGuestService(serviceRootURI.toString()) );
}

/**
Factory method to construct a data store connection from a properties file.
@param filename name of file containing property strings
*/
public static ColoradoWaterHBGuestDataStore createFromFile ( String filename )
throws IOException, Exception
{
    // Read the properties from the file
    PropList props = new PropList ("");
    props.setPersistentName ( filename );
    props.readPersistent ( false );
    String name = IOUtil.expandPropertyForEnvironment("Name",props.getValue("Name"));
    String description = IOUtil.expandPropertyForEnvironment("Description",props.getValue("Description"));
    String serviceRootURI = IOUtil.expandPropertyForEnvironment("ServiceRootURI",props.getValue("ServiceRootURI"));
    
    // Get the properties and create an instance

    ColoradoWaterHBGuestDataStore ds = new ColoradoWaterHBGuestDataStore( name, description, new URI(serviceRootURI) );
    return ds;
}

/**
Return the ColoradoWaterHBGuest instance used by the data store.
@return the ColoradoWaterHBGuest instance used by the data store
*/
public ColoradoWaterHBGuestService getColoradoWaterHBGuestService ()
{
    return __coloradoWaterHBGuestService;
}

/**
Set the ColoradoWaterHBGuest instance that is used as the API
*/
private void setColoradoWaterHBGuestService ( ColoradoWaterHBGuestService coloradoWaterHBGuestService )
{
    __coloradoWaterHBGuestService = coloradoWaterHBGuestService;
}

}