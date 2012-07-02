
package us.co.state.dwr.hbguest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GroundWaterWellsDrillersksum complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroundWaterWellsDrillersksum">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.dwr.state.co.us/}GroundWaterWells">
 *       &lt;sequence>
 *         &lt;element name="dunctop" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="duncbase" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dunctopbasek" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dcontop" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="d500" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dcon500k" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="_2d500" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="d1000" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="d500to1000k" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="_2d1000" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="_1500" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="d1000_1500k" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="_2d1500" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="d2000" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="d1500_2000k" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="_2d2000" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="d2500" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="d2000_2500" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="_2d2500" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="d3000" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="_2d3000" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="d2500_3000" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="d3500" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="d3000_3500k" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="_2d3500" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="d4000" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="d3500_4000k" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroundWaterWellsDrillersksum", propOrder = {
    "dunctop",
    "duncbase",
    "dunctopbasek",
    "dcontop",
    "d500",
    "dcon500K",
    "_2D500",
    "d1000",
    "d500To1000K",
    "_2D1000",
    "_1500",
    "d10001500K",
    "_2D1500",
    "d2000",
    "d15002000K",
    "_2D2000",
    "d2500",
    "d20002500",
    "_2D2500",
    "d3000",
    "_2D3000",
    "d25003000",
    "d3500",
    "d30003500K",
    "_2D3500",
    "d4000",
    "d35004000K"
})
public class GroundWaterWellsDrillersksum
    extends GroundWaterWells
{

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer dunctop;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer duncbase;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer dunctopbasek;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer dcontop;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer d500;
    @XmlElement(name = "dcon500k", required = true, type = Integer.class, nillable = true)
    protected Integer dcon500K;
    @XmlElement(name = "_2d500", required = true, type = Integer.class, nillable = true)
    protected Integer _2D500;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer d1000;
    @XmlElement(name = "d500to1000k", required = true, type = Integer.class, nillable = true)
    protected Integer d500To1000K;
    @XmlElement(name = "_2d1000", required = true, type = Integer.class, nillable = true)
    protected Integer _2D1000;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer _1500;
    @XmlElement(name = "d1000_1500k", required = true, type = Integer.class, nillable = true)
    protected Integer d10001500K;
    @XmlElement(name = "_2d1500", required = true, type = Integer.class, nillable = true)
    protected Integer _2D1500;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer d2000;
    @XmlElement(name = "d1500_2000k", required = true, type = Integer.class, nillable = true)
    protected Integer d15002000K;
    @XmlElement(name = "_2d2000", required = true, type = Integer.class, nillable = true)
    protected Integer _2D2000;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer d2500;
    @XmlElement(name = "d2000_2500", required = true, type = Integer.class, nillable = true)
    protected Integer d20002500;
    @XmlElement(name = "_2d2500", required = true, type = Integer.class, nillable = true)
    protected Integer _2D2500;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer d3000;
    @XmlElement(name = "_2d3000", required = true, type = Integer.class, nillable = true)
    protected Integer _2D3000;
    @XmlElement(name = "d2500_3000", required = true, type = Integer.class, nillable = true)
    protected Integer d25003000;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer d3500;
    @XmlElement(name = "d3000_3500k", required = true, type = Integer.class, nillable = true)
    protected Integer d30003500K;
    @XmlElement(name = "_2d3500", required = true, type = Integer.class, nillable = true)
    protected Integer _2D3500;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer d4000;
    @XmlElement(name = "d3500_4000k", required = true, type = Integer.class, nillable = true)
    protected Integer d35004000K;

    /**
     * Gets the value of the dunctop property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDunctop() {
        return dunctop;
    }

    /**
     * Sets the value of the dunctop property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDunctop(Integer value) {
        this.dunctop = value;
    }

    /**
     * Gets the value of the duncbase property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDuncbase() {
        return duncbase;
    }

    /**
     * Sets the value of the duncbase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDuncbase(Integer value) {
        this.duncbase = value;
    }

    /**
     * Gets the value of the dunctopbasek property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDunctopbasek() {
        return dunctopbasek;
    }

    /**
     * Sets the value of the dunctopbasek property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDunctopbasek(Integer value) {
        this.dunctopbasek = value;
    }

    /**
     * Gets the value of the dcontop property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDcontop() {
        return dcontop;
    }

    /**
     * Sets the value of the dcontop property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDcontop(Integer value) {
        this.dcontop = value;
    }

    /**
     * Gets the value of the d500 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getD500() {
        return d500;
    }

    /**
     * Sets the value of the d500 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setD500(Integer value) {
        this.d500 = value;
    }

    /**
     * Gets the value of the dcon500K property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDcon500K() {
        return dcon500K;
    }

    /**
     * Sets the value of the dcon500K property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDcon500K(Integer value) {
        this.dcon500K = value;
    }

    /**
     * Gets the value of the 2D500 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer get2D500() {
        return _2D500;
    }

    /**
     * Sets the value of the 2D500 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void set2D500(Integer value) {
        this._2D500 = value;
    }

    /**
     * Gets the value of the d1000 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getD1000() {
        return d1000;
    }

    /**
     * Sets the value of the d1000 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setD1000(Integer value) {
        this.d1000 = value;
    }

    /**
     * Gets the value of the d500To1000K property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getD500To1000K() {
        return d500To1000K;
    }

    /**
     * Sets the value of the d500To1000K property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setD500To1000K(Integer value) {
        this.d500To1000K = value;
    }

    /**
     * Gets the value of the 2D1000 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer get2D1000() {
        return _2D1000;
    }

    /**
     * Sets the value of the 2D1000 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void set2D1000(Integer value) {
        this._2D1000 = value;
    }

    /**
     * Gets the value of the 1500 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer get1500() {
        return _1500;
    }

    /**
     * Sets the value of the 1500 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void set1500(Integer value) {
        this._1500 = value;
    }

    /**
     * Gets the value of the d10001500K property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getD10001500K() {
        return d10001500K;
    }

    /**
     * Sets the value of the d10001500K property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setD10001500K(Integer value) {
        this.d10001500K = value;
    }

    /**
     * Gets the value of the 2D1500 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer get2D1500() {
        return _2D1500;
    }

    /**
     * Sets the value of the 2D1500 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void set2D1500(Integer value) {
        this._2D1500 = value;
    }

    /**
     * Gets the value of the d2000 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getD2000() {
        return d2000;
    }

    /**
     * Sets the value of the d2000 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setD2000(Integer value) {
        this.d2000 = value;
    }

    /**
     * Gets the value of the d15002000K property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getD15002000K() {
        return d15002000K;
    }

    /**
     * Sets the value of the d15002000K property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setD15002000K(Integer value) {
        this.d15002000K = value;
    }

    /**
     * Gets the value of the 2D2000 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer get2D2000() {
        return _2D2000;
    }

    /**
     * Sets the value of the 2D2000 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void set2D2000(Integer value) {
        this._2D2000 = value;
    }

    /**
     * Gets the value of the d2500 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getD2500() {
        return d2500;
    }

    /**
     * Sets the value of the d2500 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setD2500(Integer value) {
        this.d2500 = value;
    }

    /**
     * Gets the value of the d20002500 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getD20002500() {
        return d20002500;
    }

    /**
     * Sets the value of the d20002500 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setD20002500(Integer value) {
        this.d20002500 = value;
    }

    /**
     * Gets the value of the 2D2500 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer get2D2500() {
        return _2D2500;
    }

    /**
     * Sets the value of the 2D2500 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void set2D2500(Integer value) {
        this._2D2500 = value;
    }

    /**
     * Gets the value of the d3000 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getD3000() {
        return d3000;
    }

    /**
     * Sets the value of the d3000 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setD3000(Integer value) {
        this.d3000 = value;
    }

    /**
     * Gets the value of the 2D3000 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer get2D3000() {
        return _2D3000;
    }

    /**
     * Sets the value of the 2D3000 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void set2D3000(Integer value) {
        this._2D3000 = value;
    }

    /**
     * Gets the value of the d25003000 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getD25003000() {
        return d25003000;
    }

    /**
     * Sets the value of the d25003000 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setD25003000(Integer value) {
        this.d25003000 = value;
    }

    /**
     * Gets the value of the d3500 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getD3500() {
        return d3500;
    }

    /**
     * Sets the value of the d3500 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setD3500(Integer value) {
        this.d3500 = value;
    }

    /**
     * Gets the value of the d30003500K property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getD30003500K() {
        return d30003500K;
    }

    /**
     * Sets the value of the d30003500K property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setD30003500K(Integer value) {
        this.d30003500K = value;
    }

    /**
     * Gets the value of the 2D3500 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer get2D3500() {
        return _2D3500;
    }

    /**
     * Sets the value of the 2D3500 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void set2D3500(Integer value) {
        this._2D3500 = value;
    }

    /**
     * Gets the value of the d4000 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getD4000() {
        return d4000;
    }

    /**
     * Sets the value of the d4000 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setD4000(Integer value) {
        this.d4000 = value;
    }

    /**
     * Gets the value of the d35004000K property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getD35004000K() {
        return d35004000K;
    }

    /**
     * Sets the value of the d35004000K property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setD35004000K(Integer value) {
        this.d35004000K = value;
    }

}
