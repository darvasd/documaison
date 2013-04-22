//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.22 at 10:38:07 PM CEST 
//


package hu.documaison.bll.ws.doidata;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.crossref.org/qrschema/2.0}title" minOccurs="0"/>
 *         &lt;element ref="{http://www.crossref.org/qrschema/2.0}contributors" minOccurs="0"/>
 *         &lt;element ref="{http://www.crossref.org/qrschema/2.0}institution_name" minOccurs="0"/>
 *         &lt;element ref="{http://www.crossref.org/qrschema/2.0}year" minOccurs="0"/>
 *         &lt;element ref="{http://www.crossref.org/qrschema/2.0}doi"/>
 *       &lt;/sequence>
 *       &lt;attribute name="fl_count">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "title",
    "contributors",
    "institutionName",
    "year",
    "doi"
})
@XmlRootElement(name = "database_cite")
public class DatabaseCite {

    protected Title title;
    protected Contributors contributors;
    @XmlElement(name = "institution_name")
    protected InstitutionName institutionName;
    protected Year year;
    @XmlElement(required = true)
    protected Doi doi;
    @XmlAttribute(name = "fl_count")
    protected BigInteger flCount;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link Title }
     *     
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link Title }
     *     
     */
    public void setTitle(Title value) {
        this.title = value;
    }

    /**
     * Gets the value of the contributors property.
     * 
     * @return
     *     possible object is
     *     {@link Contributors }
     *     
     */
    public Contributors getContributors() {
        return contributors;
    }

    /**
     * Sets the value of the contributors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Contributors }
     *     
     */
    public void setContributors(Contributors value) {
        this.contributors = value;
    }

    /**
     * Gets the value of the institutionName property.
     * 
     * @return
     *     possible object is
     *     {@link InstitutionName }
     *     
     */
    public InstitutionName getInstitutionName() {
        return institutionName;
    }

    /**
     * Sets the value of the institutionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstitutionName }
     *     
     */
    public void setInstitutionName(InstitutionName value) {
        this.institutionName = value;
    }

    /**
     * Gets the value of the year property.
     * 
     * @return
     *     possible object is
     *     {@link Year }
     *     
     */
    public Year getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     * 
     * @param value
     *     allowed object is
     *     {@link Year }
     *     
     */
    public void setYear(Year value) {
        this.year = value;
    }

    /**
     * Gets the value of the doi property.
     * 
     * @return
     *     possible object is
     *     {@link Doi }
     *     
     */
    public Doi getDoi() {
        return doi;
    }

    /**
     * Sets the value of the doi property.
     * 
     * @param value
     *     allowed object is
     *     {@link Doi }
     *     
     */
    public void setDoi(Doi value) {
        this.doi = value;
    }

    /**
     * Gets the value of the flCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFlCount() {
        return flCount;
    }

    /**
     * Sets the value of the flCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFlCount(BigInteger value) {
        this.flCount = value;
    }

}
