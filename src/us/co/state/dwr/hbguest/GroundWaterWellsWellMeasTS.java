
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GroundWaterWellsWellMeasTS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroundWaterWellsWellMeasTS">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.dwr.state.co.us/}GroundWaterWells">
 *       &lt;sequence>
 *         &lt;element name="meas_date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wl_depth" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="wl_elev" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="meas_by" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroundWaterWellsWellMeasTS", propOrder = {
    "measDate",
    "wlDepth",
    "wlElev",
    "measBy"
})
public class GroundWaterWellsWellMeasTS
    extends GroundWaterWells
{

    @XmlElement(name = "meas_date")
    protected String measDate;
    @XmlElement(name = "wl_depth", required = true, type = Double.class, nillable = true)
    protected Double wlDepth;
    @XmlElement(name = "wl_elev", required = true, type = Double.class, nillable = true)
    protected Double wlElev;
    @XmlElement(name = "meas_by")
    protected String measBy;

    /**
     * Gets the value of the measDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasDate() {
        return measDate;
    }

    /**
     * Sets the value of the measDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasDate(String value) {
        this.measDate = value;
    }

    /**
     * Gets the value of the wlDepth property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWlDepth() {
        return wlDepth;
    }

    /**
     * Sets the value of the wlDepth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWlDepth(Double value) {
        this.wlDepth = value;
    }

    /**
     * Gets the value of the wlElev property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWlElev() {
        return wlElev;
    }

    /**
     * Sets the value of the wlElev property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWlElev(Double value) {
        this.wlElev = value;
    }

    /**
     * Gets the value of the measBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasBy() {
        return measBy;
    }

    /**
     * Sets the value of the measBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasBy(String value) {
        this.measBy = value;
    }

}
