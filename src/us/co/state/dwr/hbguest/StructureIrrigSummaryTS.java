
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StructureIrrigSummaryTS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StructureIrrigSummaryTS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wdid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cal_year" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="land_use" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="acres_total" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="acres_by_drip" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="acres_by_flood" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="acres_by_furrow" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="acres_by_sprinkler" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="acres_by_groundwater" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StructureIrrigSummaryTS", propOrder = {
    "wdid",
    "calYear",
    "landUse",
    "acresTotal",
    "acresByDrip",
    "acresByFlood",
    "acresByFurrow",
    "acresBySprinkler",
    "acresByGroundwater"
})
public class StructureIrrigSummaryTS {

    protected String wdid;
    @XmlElement(name = "cal_year")
    protected short calYear;
    @XmlElement(name = "land_use")
    protected String landUse;
    @XmlElement(name = "acres_total", required = true, type = Double.class, nillable = true)
    protected Double acresTotal;
    @XmlElement(name = "acres_by_drip", required = true, type = Double.class, nillable = true)
    protected Double acresByDrip;
    @XmlElement(name = "acres_by_flood", required = true, type = Double.class, nillable = true)
    protected Double acresByFlood;
    @XmlElement(name = "acres_by_furrow", required = true, type = Double.class, nillable = true)
    protected Double acresByFurrow;
    @XmlElement(name = "acres_by_sprinkler", required = true, type = Double.class, nillable = true)
    protected Double acresBySprinkler;
    @XmlElement(name = "acres_by_groundwater", required = true, type = Double.class, nillable = true)
    protected Double acresByGroundwater;

    /**
     * Gets the value of the wdid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWdid() {
        return wdid;
    }

    /**
     * Sets the value of the wdid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWdid(String value) {
        this.wdid = value;
    }

    /**
     * Gets the value of the calYear property.
     * 
     */
    public short getCalYear() {
        return calYear;
    }

    /**
     * Sets the value of the calYear property.
     * 
     */
    public void setCalYear(short value) {
        this.calYear = value;
    }

    /**
     * Gets the value of the landUse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLandUse() {
        return landUse;
    }

    /**
     * Sets the value of the landUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLandUse(String value) {
        this.landUse = value;
    }

    /**
     * Gets the value of the acresTotal property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAcresTotal() {
        return acresTotal;
    }

    /**
     * Sets the value of the acresTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAcresTotal(Double value) {
        this.acresTotal = value;
    }

    /**
     * Gets the value of the acresByDrip property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAcresByDrip() {
        return acresByDrip;
    }

    /**
     * Sets the value of the acresByDrip property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAcresByDrip(Double value) {
        this.acresByDrip = value;
    }

    /**
     * Gets the value of the acresByFlood property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAcresByFlood() {
        return acresByFlood;
    }

    /**
     * Sets the value of the acresByFlood property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAcresByFlood(Double value) {
        this.acresByFlood = value;
    }

    /**
     * Gets the value of the acresByFurrow property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAcresByFurrow() {
        return acresByFurrow;
    }

    /**
     * Sets the value of the acresByFurrow property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAcresByFurrow(Double value) {
        this.acresByFurrow = value;
    }

    /**
     * Gets the value of the acresBySprinkler property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAcresBySprinkler() {
        return acresBySprinkler;
    }

    /**
     * Sets the value of the acresBySprinkler property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAcresBySprinkler(Double value) {
        this.acresBySprinkler = value;
    }

    /**
     * Gets the value of the acresByGroundwater property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAcresByGroundwater() {
        return acresByGroundwater;
    }

    /**
     * Sets the value of the acresByGroundwater property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAcresByGroundwater(Double value) {
        this.acresByGroundwater = value;
    }

}
