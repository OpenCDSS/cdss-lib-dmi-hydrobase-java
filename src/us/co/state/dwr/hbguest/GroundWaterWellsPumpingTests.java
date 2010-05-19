
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GroundWaterWellsPumpingTests complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroundWaterWellsPumpingTests">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.dwr.state.co.us/}GroundWaterWells">
 *       &lt;sequence>
 *         &lt;element name="tswl" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="tfwl" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="testq" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="testtime" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="trans" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="k" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="storativity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="leakance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toptestint" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="basetestint" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="drawdown" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="testdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ptsource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pttype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ptmon" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ptobs" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ptmultiple" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="sp_cap" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroundWaterWellsPumpingTests", propOrder = {
    "tswl",
    "tfwl",
    "testq",
    "testtime",
    "trans",
    "k",
    "storativity",
    "leakance",
    "toptestint",
    "basetestint",
    "drawdown",
    "testdate",
    "ptsource",
    "pttype",
    "ptmon",
    "ptobs",
    "ptmultiple",
    "spCap"
})
public class GroundWaterWellsPumpingTests
    extends GroundWaterWells
{

    protected float tswl;
    protected float tfwl;
    protected float testq;
    protected float testtime;
    protected int trans;
    protected String k;
    protected String storativity;
    protected String leakance;
    protected int toptestint;
    protected int basetestint;
    protected float drawdown;
    protected String testdate;
    protected String ptsource;
    protected String pttype;
    protected boolean ptmon;
    protected boolean ptobs;
    protected boolean ptmultiple;
    @XmlElement(name = "sp_cap")
    protected float spCap;

    /**
     * Gets the value of the tswl property.
     * 
     */
    public float getTswl() {
        return tswl;
    }

    /**
     * Sets the value of the tswl property.
     * 
     */
    public void setTswl(float value) {
        this.tswl = value;
    }

    /**
     * Gets the value of the tfwl property.
     * 
     */
    public float getTfwl() {
        return tfwl;
    }

    /**
     * Sets the value of the tfwl property.
     * 
     */
    public void setTfwl(float value) {
        this.tfwl = value;
    }

    /**
     * Gets the value of the testq property.
     * 
     */
    public float getTestq() {
        return testq;
    }

    /**
     * Sets the value of the testq property.
     * 
     */
    public void setTestq(float value) {
        this.testq = value;
    }

    /**
     * Gets the value of the testtime property.
     * 
     */
    public float getTesttime() {
        return testtime;
    }

    /**
     * Sets the value of the testtime property.
     * 
     */
    public void setTesttime(float value) {
        this.testtime = value;
    }

    /**
     * Gets the value of the trans property.
     * 
     */
    public int getTrans() {
        return trans;
    }

    /**
     * Sets the value of the trans property.
     * 
     */
    public void setTrans(int value) {
        this.trans = value;
    }

    /**
     * Gets the value of the k property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getK() {
        return k;
    }

    /**
     * Sets the value of the k property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setK(String value) {
        this.k = value;
    }

    /**
     * Gets the value of the storativity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorativity() {
        return storativity;
    }

    /**
     * Sets the value of the storativity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorativity(String value) {
        this.storativity = value;
    }

    /**
     * Gets the value of the leakance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeakance() {
        return leakance;
    }

    /**
     * Sets the value of the leakance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeakance(String value) {
        this.leakance = value;
    }

    /**
     * Gets the value of the toptestint property.
     * 
     */
    public int getToptestint() {
        return toptestint;
    }

    /**
     * Sets the value of the toptestint property.
     * 
     */
    public void setToptestint(int value) {
        this.toptestint = value;
    }

    /**
     * Gets the value of the basetestint property.
     * 
     */
    public int getBasetestint() {
        return basetestint;
    }

    /**
     * Sets the value of the basetestint property.
     * 
     */
    public void setBasetestint(int value) {
        this.basetestint = value;
    }

    /**
     * Gets the value of the drawdown property.
     * 
     */
    public float getDrawdown() {
        return drawdown;
    }

    /**
     * Sets the value of the drawdown property.
     * 
     */
    public void setDrawdown(float value) {
        this.drawdown = value;
    }

    /**
     * Gets the value of the testdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestdate() {
        return testdate;
    }

    /**
     * Sets the value of the testdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestdate(String value) {
        this.testdate = value;
    }

    /**
     * Gets the value of the ptsource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPtsource() {
        return ptsource;
    }

    /**
     * Sets the value of the ptsource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPtsource(String value) {
        this.ptsource = value;
    }

    /**
     * Gets the value of the pttype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPttype() {
        return pttype;
    }

    /**
     * Sets the value of the pttype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPttype(String value) {
        this.pttype = value;
    }

    /**
     * Gets the value of the ptmon property.
     * 
     */
    public boolean isPtmon() {
        return ptmon;
    }

    /**
     * Sets the value of the ptmon property.
     * 
     */
    public void setPtmon(boolean value) {
        this.ptmon = value;
    }

    /**
     * Gets the value of the ptobs property.
     * 
     */
    public boolean isPtobs() {
        return ptobs;
    }

    /**
     * Sets the value of the ptobs property.
     * 
     */
    public void setPtobs(boolean value) {
        this.ptobs = value;
    }

    /**
     * Gets the value of the ptmultiple property.
     * 
     */
    public boolean isPtmultiple() {
        return ptmultiple;
    }

    /**
     * Sets the value of the ptmultiple property.
     * 
     */
    public void setPtmultiple(boolean value) {
        this.ptmultiple = value;
    }

    /**
     * Gets the value of the spCap property.
     * 
     */
    public float getSpCap() {
        return spCap;
    }

    /**
     * Sets the value of the spCap property.
     * 
     */
    public void setSpCap(float value) {
        this.spCap = value;
    }

}
