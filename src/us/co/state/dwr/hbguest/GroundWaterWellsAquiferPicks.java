
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GroundWaterWellsAquiferPicks complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroundWaterWellsAquiferPicks">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.dwr.state.co.us/}GroundWaterWells">
 *       &lt;sequence>
 *         &lt;element name="aquifer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pickstop" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="picksbase" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="picksthickness" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroundWaterWellsAquiferPicks", propOrder = {
    "aquifer",
    "pickstop",
    "picksbase",
    "picksthickness"
})
public class GroundWaterWellsAquiferPicks
    extends GroundWaterWells
{

    protected String aquifer;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer pickstop;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer picksbase;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer picksthickness;

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
     * Gets the value of the pickstop property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPickstop() {
        return pickstop;
    }

    /**
     * Sets the value of the pickstop property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPickstop(Integer value) {
        this.pickstop = value;
    }

    /**
     * Gets the value of the picksbase property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPicksbase() {
        return picksbase;
    }

    /**
     * Sets the value of the picksbase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPicksbase(Integer value) {
        this.picksbase = value;
    }

    /**
     * Gets the value of the picksthickness property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPicksthickness() {
        return picksthickness;
    }

    /**
     * Sets the value of the picksthickness property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPicksthickness(Integer value) {
        this.picksthickness = value;
    }

}
