
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

    protected int dunctop;
    protected int duncbase;
    protected int dunctopbasek;
    protected int dcontop;
    protected int d500;
    @XmlElement(name = "dcon500k")
    protected int dcon500K;
    @XmlElement(name = "_2d500")
    protected int _2D500;
    protected int d1000;
    @XmlElement(name = "d500to1000k")
    protected int d500To1000K;
    @XmlElement(name = "_2d1000")
    protected int _2D1000;
    protected int _1500;
    @XmlElement(name = "d1000_1500k")
    protected int d10001500K;
    @XmlElement(name = "_2d1500")
    protected int _2D1500;
    protected int d2000;
    @XmlElement(name = "d1500_2000k")
    protected int d15002000K;
    @XmlElement(name = "_2d2000")
    protected int _2D2000;
    protected int d2500;
    @XmlElement(name = "d2000_2500")
    protected int d20002500;
    @XmlElement(name = "_2d2500")
    protected int _2D2500;
    protected int d3000;
    @XmlElement(name = "_2d3000")
    protected int _2D3000;
    @XmlElement(name = "d2500_3000")
    protected int d25003000;
    protected int d3500;
    @XmlElement(name = "d3000_3500k")
    protected int d30003500K;
    @XmlElement(name = "_2d3500")
    protected int _2D3500;
    protected int d4000;
    @XmlElement(name = "d3500_4000k")
    protected int d35004000K;

    /**
     * Gets the value of the dunctop property.
     * 
     */
    public int getDunctop() {
        return dunctop;
    }

    /**
     * Sets the value of the dunctop property.
     * 
     */
    public void setDunctop(int value) {
        this.dunctop = value;
    }

    /**
     * Gets the value of the duncbase property.
     * 
     */
    public int getDuncbase() {
        return duncbase;
    }

    /**
     * Sets the value of the duncbase property.
     * 
     */
    public void setDuncbase(int value) {
        this.duncbase = value;
    }

    /**
     * Gets the value of the dunctopbasek property.
     * 
     */
    public int getDunctopbasek() {
        return dunctopbasek;
    }

    /**
     * Sets the value of the dunctopbasek property.
     * 
     */
    public void setDunctopbasek(int value) {
        this.dunctopbasek = value;
    }

    /**
     * Gets the value of the dcontop property.
     * 
     */
    public int getDcontop() {
        return dcontop;
    }

    /**
     * Sets the value of the dcontop property.
     * 
     */
    public void setDcontop(int value) {
        this.dcontop = value;
    }

    /**
     * Gets the value of the d500 property.
     * 
     */
    public int getD500() {
        return d500;
    }

    /**
     * Sets the value of the d500 property.
     * 
     */
    public void setD500(int value) {
        this.d500 = value;
    }

    /**
     * Gets the value of the dcon500K property.
     * 
     */
    public int getDcon500K() {
        return dcon500K;
    }

    /**
     * Sets the value of the dcon500K property.
     * 
     */
    public void setDcon500K(int value) {
        this.dcon500K = value;
    }

    /**
     * Gets the value of the 2D500 property.
     * 
     */
    public int get2D500() {
        return _2D500;
    }

    /**
     * Sets the value of the 2D500 property.
     * 
     */
    public void set2D500(int value) {
        this._2D500 = value;
    }

    /**
     * Gets the value of the d1000 property.
     * 
     */
    public int getD1000() {
        return d1000;
    }

    /**
     * Sets the value of the d1000 property.
     * 
     */
    public void setD1000(int value) {
        this.d1000 = value;
    }

    /**
     * Gets the value of the d500To1000K property.
     * 
     */
    public int getD500To1000K() {
        return d500To1000K;
    }

    /**
     * Sets the value of the d500To1000K property.
     * 
     */
    public void setD500To1000K(int value) {
        this.d500To1000K = value;
    }

    /**
     * Gets the value of the 2D1000 property.
     * 
     */
    public int get2D1000() {
        return _2D1000;
    }

    /**
     * Sets the value of the 2D1000 property.
     * 
     */
    public void set2D1000(int value) {
        this._2D1000 = value;
    }

    /**
     * Gets the value of the 1500 property.
     * 
     */
    public int get1500() {
        return _1500;
    }

    /**
     * Sets the value of the 1500 property.
     * 
     */
    public void set1500(int value) {
        this._1500 = value;
    }

    /**
     * Gets the value of the d10001500K property.
     * 
     */
    public int getD10001500K() {
        return d10001500K;
    }

    /**
     * Sets the value of the d10001500K property.
     * 
     */
    public void setD10001500K(int value) {
        this.d10001500K = value;
    }

    /**
     * Gets the value of the 2D1500 property.
     * 
     */
    public int get2D1500() {
        return _2D1500;
    }

    /**
     * Sets the value of the 2D1500 property.
     * 
     */
    public void set2D1500(int value) {
        this._2D1500 = value;
    }

    /**
     * Gets the value of the d2000 property.
     * 
     */
    public int getD2000() {
        return d2000;
    }

    /**
     * Sets the value of the d2000 property.
     * 
     */
    public void setD2000(int value) {
        this.d2000 = value;
    }

    /**
     * Gets the value of the d15002000K property.
     * 
     */
    public int getD15002000K() {
        return d15002000K;
    }

    /**
     * Sets the value of the d15002000K property.
     * 
     */
    public void setD15002000K(int value) {
        this.d15002000K = value;
    }

    /**
     * Gets the value of the 2D2000 property.
     * 
     */
    public int get2D2000() {
        return _2D2000;
    }

    /**
     * Sets the value of the 2D2000 property.
     * 
     */
    public void set2D2000(int value) {
        this._2D2000 = value;
    }

    /**
     * Gets the value of the d2500 property.
     * 
     */
    public int getD2500() {
        return d2500;
    }

    /**
     * Sets the value of the d2500 property.
     * 
     */
    public void setD2500(int value) {
        this.d2500 = value;
    }

    /**
     * Gets the value of the d20002500 property.
     * 
     */
    public int getD20002500() {
        return d20002500;
    }

    /**
     * Sets the value of the d20002500 property.
     * 
     */
    public void setD20002500(int value) {
        this.d20002500 = value;
    }

    /**
     * Gets the value of the 2D2500 property.
     * 
     */
    public int get2D2500() {
        return _2D2500;
    }

    /**
     * Sets the value of the 2D2500 property.
     * 
     */
    public void set2D2500(int value) {
        this._2D2500 = value;
    }

    /**
     * Gets the value of the d3000 property.
     * 
     */
    public int getD3000() {
        return d3000;
    }

    /**
     * Sets the value of the d3000 property.
     * 
     */
    public void setD3000(int value) {
        this.d3000 = value;
    }

    /**
     * Gets the value of the 2D3000 property.
     * 
     */
    public int get2D3000() {
        return _2D3000;
    }

    /**
     * Sets the value of the 2D3000 property.
     * 
     */
    public void set2D3000(int value) {
        this._2D3000 = value;
    }

    /**
     * Gets the value of the d25003000 property.
     * 
     */
    public int getD25003000() {
        return d25003000;
    }

    /**
     * Sets the value of the d25003000 property.
     * 
     */
    public void setD25003000(int value) {
        this.d25003000 = value;
    }

    /**
     * Gets the value of the d3500 property.
     * 
     */
    public int getD3500() {
        return d3500;
    }

    /**
     * Sets the value of the d3500 property.
     * 
     */
    public void setD3500(int value) {
        this.d3500 = value;
    }

    /**
     * Gets the value of the d30003500K property.
     * 
     */
    public int getD30003500K() {
        return d30003500K;
    }

    /**
     * Sets the value of the d30003500K property.
     * 
     */
    public void setD30003500K(int value) {
        this.d30003500K = value;
    }

    /**
     * Gets the value of the 2D3500 property.
     * 
     */
    public int get2D3500() {
        return _2D3500;
    }

    /**
     * Sets the value of the 2D3500 property.
     * 
     */
    public void set2D3500(int value) {
        this._2D3500 = value;
    }

    /**
     * Gets the value of the d4000 property.
     * 
     */
    public int getD4000() {
        return d4000;
    }

    /**
     * Sets the value of the d4000 property.
     * 
     */
    public void setD4000(int value) {
        this.d4000 = value;
    }

    /**
     * Gets the value of the d35004000K property.
     * 
     */
    public int getD35004000K() {
        return d35004000K;
    }

    /**
     * Sets the value of the d35004000K property.
     * 
     */
    public void setD35004000K(int value) {
        this.d35004000K = value;
    }

}
