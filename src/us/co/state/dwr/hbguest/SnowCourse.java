
package us.co.state.dwr.hbguest;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SnowCourse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SnowCourse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="meas_num" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="meas_date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="depth" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="snow_water_equiv" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SnowCourse", propOrder = {
    "measNum",
    "measDate",
    "depth",
    "snowWaterEquiv",
    "flag"
})
public class SnowCourse {

    @XmlElement(name = "meas_num")
    protected int measNum;
    @XmlElement(name = "meas_date")
    protected String measDate;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short depth;
    @XmlElement(name = "snow_water_equiv", required = true, nillable = true)
    protected BigDecimal snowWaterEquiv;
    protected String flag;

    /**
     * Gets the value of the measNum property.
     * 
     */
    public int getMeasNum() {
        return measNum;
    }

    /**
     * Sets the value of the measNum property.
     * 
     */
    public void setMeasNum(int value) {
        this.measNum = value;
    }

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
     * Gets the value of the depth property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getDepth() {
        return depth;
    }

    /**
     * Sets the value of the depth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setDepth(Short value) {
        this.depth = value;
    }

    /**
     * Gets the value of the snowWaterEquiv property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSnowWaterEquiv() {
        return snowWaterEquiv;
    }

    /**
     * Sets the value of the snowWaterEquiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSnowWaterEquiv(BigDecimal value) {
        this.snowWaterEquiv = value;
    }

    /**
     * Gets the value of the flag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlag() {
        return flag;
    }

    /**
     * Sets the value of the flag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlag(String value) {
        this.flag = value;
    }

}
