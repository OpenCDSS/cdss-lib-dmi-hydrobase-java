
package us.co.state.dwr.hbguest;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StructureParcelUseTS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StructureParcelUseTS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wdid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cal_year" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="percent_irrig" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="area" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="land_use" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="irrig_type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StructureParcelUseTS", propOrder = {
    "wdid",
    "calYear",
    "percentIrrig",
    "area",
    "landUse",
    "irrigType"
})
public class StructureParcelUseTS {

    protected String wdid;
    @XmlElement(name = "cal_year")
    protected short calYear;
    @XmlElement(name = "percent_irrig", required = true)
    protected BigDecimal percentIrrig;
    @XmlElement(required = true)
    protected BigDecimal area;
    @XmlElement(name = "land_use")
    protected String landUse;
    @XmlElement(name = "irrig_type")
    protected String irrigType;

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
     * Gets the value of the percentIrrig property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPercentIrrig() {
        return percentIrrig;
    }

    /**
     * Sets the value of the percentIrrig property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPercentIrrig(BigDecimal value) {
        this.percentIrrig = value;
    }

    /**
     * Gets the value of the area property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * Sets the value of the area property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setArea(BigDecimal value) {
        this.area = value;
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
     * Gets the value of the irrigType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIrrigType() {
        return irrigType;
    }

    /**
     * Sets the value of the irrigType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIrrigType(String value) {
        this.irrigType = value;
    }

}
