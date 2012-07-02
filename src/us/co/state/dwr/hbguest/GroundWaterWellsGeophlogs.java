
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short glogtop;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short glogbase;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short glogthickness;

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
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getGlogtop() {
        return glogtop;
    }

    /**
     * Sets the value of the glogtop property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setGlogtop(Short value) {
        this.glogtop = value;
    }

    /**
     * Gets the value of the glogbase property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getGlogbase() {
        return glogbase;
    }

    /**
     * Sets the value of the glogbase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setGlogbase(Short value) {
        this.glogbase = value;
    }

    /**
     * Gets the value of the glogthickness property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getGlogthickness() {
        return glogthickness;
    }

    /**
     * Sets the value of the glogthickness property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setGlogthickness(Short value) {
        this.glogthickness = value;
    }

}
