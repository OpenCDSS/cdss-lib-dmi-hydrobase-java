package us.co.state.dwr.hbguest.datastore;

import java.net.URI;

import RTi.Util.IO.PropList;
import riverside.datastore.DataStore;
import riverside.datastore.DataStoreFactory;

/**
Factory to instantiate ColoradoWaterHBGuestDataStore instances.
*/
public class ColoradoWaterHBGuestDataStoreFactory implements DataStoreFactory
{

/**
Create a ColoradoWaterHBGuestDataStore instance from properties.
*/
public DataStore create ( PropList props )
{
    String name = props.getValue ( "Name" );
    String description = props.getValue ( "Description" );
    if ( description == null ) {
        description = "";
    }
    String serviceRootURI = props.getValue ( "ServiceRootURI" );
    try {
    	ColoradoWaterHBGuestDataStore ds = new ColoradoWaterHBGuestDataStore ( name, description, new URI(serviceRootURI) );
    	ds.setProperties(props);
    	return ds;
    }
    catch ( Exception e ) {
        throw new RuntimeException ( e );
    }
}

}