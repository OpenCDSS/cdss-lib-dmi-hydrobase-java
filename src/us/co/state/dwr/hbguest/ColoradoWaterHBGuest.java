
package us.co.state.dwr.hbguest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "ColoradoWaterHBGuest", targetNamespace = "http://www.dwr.state.co.us/", wsdlLocation = "http://www.dwr.state.co.us/HBGuest/HBGuestWebService.asmx?WSDL")
public class ColoradoWaterHBGuest
    extends Service
{

    private final static URL COLORADOWATERHBGUEST_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(us.co.state.dwr.hbguest.ColoradoWaterHBGuest.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = us.co.state.dwr.hbguest.ColoradoWaterHBGuest.class.getResource(".");
            url = new URL(baseUrl, "http://www.dwr.state.co.us/HBGuest/HBGuestWebService.asmx?WSDL");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://www.dwr.state.co.us/HBGuest/HBGuestWebService.asmx?WSDL', retrying as a local file");
            logger.warning(e.getMessage());
        }
        COLORADOWATERHBGUEST_WSDL_LOCATION = url;
    }

    public ColoradoWaterHBGuest(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ColoradoWaterHBGuest() {
        super(COLORADOWATERHBGUEST_WSDL_LOCATION, new QName("http://www.dwr.state.co.us/", "ColoradoWaterHBGuest"));
    }

    /**
     * 
     * @return
     *     returns ColoradoWaterHBGuestSoap
     */
    @WebEndpoint(name = "ColoradoWaterHBGuestSoap")
    public ColoradoWaterHBGuestSoap getColoradoWaterHBGuestSoap() {
        return super.getPort(new QName("http://www.dwr.state.co.us/", "ColoradoWaterHBGuestSoap"), ColoradoWaterHBGuestSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ColoradoWaterHBGuestSoap
     */
    @WebEndpoint(name = "ColoradoWaterHBGuestSoap")
    public ColoradoWaterHBGuestSoap getColoradoWaterHBGuestSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.dwr.state.co.us/", "ColoradoWaterHBGuestSoap"), ColoradoWaterHBGuestSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns ColoradoWaterHBGuestSoap
     */
    @WebEndpoint(name = "ColoradoWaterHBGuestSoap12")
    public ColoradoWaterHBGuestSoap getColoradoWaterHBGuestSoap12() {
        return super.getPort(new QName("http://www.dwr.state.co.us/", "ColoradoWaterHBGuestSoap12"), ColoradoWaterHBGuestSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ColoradoWaterHBGuestSoap
     */
    @WebEndpoint(name = "ColoradoWaterHBGuestSoap12")
    public ColoradoWaterHBGuestSoap getColoradoWaterHBGuestSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.dwr.state.co.us/", "ColoradoWaterHBGuestSoap12"), ColoradoWaterHBGuestSoap.class, features);
    }

}