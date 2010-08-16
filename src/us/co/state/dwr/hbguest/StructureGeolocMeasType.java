
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StructureGeolocMeasType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StructureGeolocMeasType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.dwr.state.co.us/}Structure">
 *       &lt;sequence>
 *         &lt;element name="meas_num" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="meas_type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="time_step" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="start_year" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="end_year" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StructureGeolocMeasType", propOrder = {
    "measNum",
    "measType",
    "timeStep",
    "identifier",
    "startYear",
    "endYear"
})
public class StructureGeolocMeasType
    extends Structure
{

    @XmlElement(name = "meas_num")
    protected int measNum;
    @XmlElement(name = "meas_type")
    protected String measType;
    @XmlElement(name = "time_step")
    protected String timeStep;
    protected String identifier;
    @XmlElement(name = "start_year")
    protected short startYear;
    @XmlElement(name = "end_year")
    protected short endYear;

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

    /**
     * Gets the value of the startYear property.
     * 
     */
    public short getStartYear() {
        return startYear;
    }

    /**
     * Sets the value of the startYear property.
     * 
     */
    public void setStartYear(short value) {
        this.startYear = value;
    }

    /**
     * Gets the value of the endYear property.
     * 
     */
    public short getEndYear() {
        return endYear;
    }

    /**
     * Sets the value of the endYear property.
     * 
     */
    public void setEndYear(short value) {
        this.endYear = value;
    }

}
