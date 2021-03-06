
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GroundWaterWellsMeasType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroundWaterWellsMeasType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.dwr.state.co.us/}GroundWaterWells">
 *       &lt;sequence>
 *         &lt;element name="well_meas_num" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="meas_type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="time_step" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="start_year" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="end_year" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="meas_count" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="identifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroundWaterWellsMeasType", propOrder = {
    "wellMeasNum",
    "measType",
    "timeStep",
    "startYear",
    "endYear",
    "measCount",
    "identifier"
})
public class GroundWaterWellsMeasType
    extends GroundWaterWells
{

    @XmlElement(name = "well_meas_num")
    protected int wellMeasNum;
    @XmlElement(name = "meas_type")
    protected String measType;
    @XmlElement(name = "time_step")
    protected String timeStep;
    @XmlElement(name = "start_year", required = true, type = Short.class, nillable = true)
    protected Short startYear;
    @XmlElement(name = "end_year", required = true, type = Short.class, nillable = true)
    protected Short endYear;
    @XmlElement(name = "meas_count", required = true, type = Integer.class, nillable = true)
    protected Integer measCount;
    protected String identifier;

    /**
     * Gets the value of the wellMeasNum property.
     * 
     */
    public int getWellMeasNum() {
        return wellMeasNum;
    }

    /**
     * Sets the value of the wellMeasNum property.
     * 
     */
    public void setWellMeasNum(int value) {
        this.wellMeasNum = value;
    }

    /**
     * Gets the value of the measType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasType() {
        return measType;
    }

    /**
     * Sets the value of the measType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasType(String value) {
        this.measType = value;
    }

    /**
     * Gets the value of the timeStep property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeStep() {
        return timeStep;
    }

    /**
     * Sets the value of the timeStep property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeStep(String value) {
        this.timeStep = value;
    }

    /**
     * Gets the value of the startYear property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getStartYear() {
        return startYear;
    }

    /**
     * Sets the value of the startYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setStartYear(Short value) {
        this.startYear = value;
    }

    /**
     * Gets the value of the endYear property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getEndYear() {
        return endYear;
    }

    /**
     * Sets the value of the endYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setEndYear(Short value) {
        this.endYear = value;
    }

    /**
     * Gets the value of the measCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMeasCount() {
        return measCount;
    }

    /**
     * Sets the value of the measCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMeasCount(Integer value) {
        this.measCount = value;
    }

    /**
     * Gets the value of the identifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the identifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifier(String value) {
        this.identifier = value;
    }

}
