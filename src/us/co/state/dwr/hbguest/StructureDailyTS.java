
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StructureDailyTS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StructureDailyTS">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.dwr.state.co.us/}StructureTSCommon">
 *       &lt;sequence>
 *         &lt;element name="meas_date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amt" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="obs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StructureDailyTS", propOrder = {
    "measDate",
    "amt",
    "obs"
})
public class StructureDailyTS
    extends StructureTSCommon
{

    @XmlElement(name = "meas_date")
    protected String measDate;
    protected double amt;
    protected String obs;

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
     * Gets the value of the amt property.
     * 
     */
    public double getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     * 
     */
    public void setAmt(double value) {
        this.amt = value;
    }

    /**
     * Gets the value of the obs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObs() {
        return obs;
    }

    /**
     * Sets the value of the obs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObs(String value) {
        this.obs = value;
    }

}
