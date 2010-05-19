
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StructureMeasType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StructureMeasType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="meas_num" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="wdid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="meas_type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="time_step" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="start_year" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="end_year" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StructureMeasType", propOrder = {
    "measNum",
    "wdid",
    "measType",
    "timeStep",
    "identifier",
    "startYear",
    "endYear"
})
public class StructureMeasType {

    @XmlElement(name = "meas_num")
    protected int measNum;
    protected String wdid;
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
