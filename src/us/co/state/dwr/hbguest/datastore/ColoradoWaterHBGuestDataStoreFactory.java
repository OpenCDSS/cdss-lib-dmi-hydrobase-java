// ColoradoWaterHBGuestDataStoreFactory - Factory to create ColoradoWaterHBGuestDataStore instances.

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
