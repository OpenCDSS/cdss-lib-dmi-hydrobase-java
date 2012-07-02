
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StructureDiversionComments complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StructureDiversionComments">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wdid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="meas_num" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="meas_type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="irr_year" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="not_used" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="acres_irrig" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StructureDiversionComments", propOrder = {
    "wdid",
    "measNum",
    "measType",
    "irrYear",
    "notUsed",
    "comment",
    "acresIrrig"
})
public class StructureDiversionComments {

    protected String wdid;
    @XmlElement(name = "meas_num")
    protected int measNum;
    @XmlElement(name = "meas_type")
    protected String measType;
    @XmlElement(name = "irr_year")
    protected int irrYear;
    @XmlElement(name = "not_used")
    protected String notUsed;
    @XmlElement(name = "Comment")
    protected String comment;
    @XmlElement(name = "acres_irrig", required = true, type = Float.class, nillable = true)
    protected Float acresIrrig;

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
     * Gets the value of the irrYear property.
     * 
     */
    public int getIrrYear() {
        return irrYear;
    }

    /**
     * Sets the value of the irrYear property.
     * 
     */
    public void setIrrYear(int value) {
        this.irrYear = value;
    }

    /**
     * Gets the value of the notUsed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotUsed() {
        return notUsed;
    }

    /**
     * Sets the value of the notUsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotUsed(String value) {
        this.notUsed = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the acresIrrig property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getAcresIrrig() {
        return acresIrrig;
    }

    /**
     * Sets the value of the acresIrrig property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setAcresIrrig(Float value) {
        this.acresIrrig = value;
    }

}
