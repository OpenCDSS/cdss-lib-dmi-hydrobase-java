
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GroundWaterWellsVolcanics complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroundWaterWellsVolcanics">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.dwr.state.co.us/}GroundWaterWells">
 *       &lt;sequence>
 *         &lt;element name="voltop" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="volbase" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroundWaterWellsVolcanics", propOrder = {
    "voltop",
    "volbase"
})
public class GroundWaterWellsVolcanics
    extends GroundWaterWells
{

    protected int voltop;
    protected int volbase;

    /**
     * Gets the value of the voltop property.
     * 
     */
    public int getVoltop() {
        return voltop;
    }

    /**
     * Sets the value of the voltop property.
     * 
     */
    public void setVoltop(int value) {
        this.voltop = value;
    }

    /**
     * Gets the value of the volbase property.
     * 
     */
    public int getVolbase() {
        return volbase;
    }

    /**
     * Sets the value of the volbase property.
     * 
     */
    public void setVolbase(int value) {
        this.volbase = value;
    }

}
