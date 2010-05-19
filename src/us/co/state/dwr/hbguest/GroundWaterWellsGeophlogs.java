
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GroundWaterWellsGeophlogs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroundWaterWellsGeophlogs">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.dwr.state.co.us/}GroundWaterWells">
 *       &lt;sequence>
 *         &lt;element name="aquifer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="glogtop" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="glogbase" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="glogthickness" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroundWaterWellsGeophlogs", propOrder = {
    "aquifer",
    "glogtop",
    "glogbase",
    "glogthickness"
})
public class GroundWaterWellsGeophlogs
    extends GroundWaterWells
{

    protected String aquifer;
    protected short glogtop;
    protected short glogbase;
    protected short glogthickness;

    /**
     * Gets the value of the aquifer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAquifer() {
        return aquifer;
    }

    /**
     * Sets the value of the aquifer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAquifer(String value) {
        this.aquifer = value;
    }

    /**
     * Gets the value of the glogtop property.
     * 
     */
    public short getGlogtop() {
        return glogtop;
    }

    /**
     * Sets the value of the glogtop property.
     * 
     */
    public void setGlogtop(short value) {
        this.glogtop = value;
    }

    /**
     * Gets the value of the glogbase property.
     * 
     */
    public short getGlogbase() {
        return glogbase;
    }

    /**
     * Sets the value of the glogbase property.
     * 
     */
    public void setGlogbase(short value) {
        this.glogbase = value;
    }

    /**
     * Gets the value of the glogthickness property.
     * 
     */
    public short getGlogthickness() {
        return glogthickness;
    }

    /**
     * Sets the value of the glogthickness property.
     * 
     */
    public void setGlogthickness(short value) {
        this.glogthickness = value;
    }

}
