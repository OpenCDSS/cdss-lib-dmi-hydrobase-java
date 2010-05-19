
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FrostDates complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FrostDates">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="meas_num" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cal_year" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="l28s" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="f28f" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="l32s" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="f32f" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FrostDates", propOrder = {
    "measNum",
    "calYear",
    "l28S",
    "f28F",
    "l32S",
    "f32F"
})
public class FrostDates {

    @XmlElement(name = "meas_num")
    protected int measNum;
    @XmlElement(name = "cal_year")
    protected short calYear;
    @XmlElement(name = "l28s")
    protected String l28S;
    @XmlElement(name = "f28f")
    protected String f28F;
    @XmlElement(name = "l32s")
    protected String l32S;
    @XmlElement(name = "f32f")
    protected String f32F;

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
     * Gets the value of the l28S property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL28S() {
        return l28S;
    }

    /**
     * Sets the value of the l28S property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL28S(String value) {
        this.l28S = value;
    }

    /**
     * Gets the value of the f28F property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getF28F() {
        return f28F;
    }

    /**
     * Sets the value of the f28F property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setF28F(String value) {
        this.f28F = value;
    }

    /**
     * Gets the value of the l32S property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL32S() {
        return l32S;
    }

    /**
     * Sets the value of the l32S property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL32S(String value) {
        this.l32S = value;
    }

    /**
     * Gets the value of the f32F property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getF32F() {
        return f32F;
    }

    /**
     * Sets the value of the f32F property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setF32F(String value) {
        this.f32F = value;
    }

}
