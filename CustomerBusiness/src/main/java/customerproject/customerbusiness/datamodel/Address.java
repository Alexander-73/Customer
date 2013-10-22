package customerproject.customerbusiness.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Pattern;

/**
 *
 * @author timovaananen
 */
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name="Address")
public class Address implements Serializable {
   
    public enum ADDRESSTYPE {VISITING_ADDRESS, HOME_ADDRESS}
    
    //@XmlElement(name="Street")
   // private List<String> streets = new ArrayList<String>();
    private String streets[] = new String[2];
    //@XmlElement(name="PostalCode")
    @Pattern(regexp="^[0-9]{5}", message="Please provide valid postal code")
    private String postalCode;
    //@XmlElement(name="Town")
    private String town;
    //@XmlElement(name="Type")
    private ADDRESSTYPE addressType;
    
    public Address() {}
    public ADDRESSTYPE getAddressType() {
        return addressType;
    }

    public void setType(ADDRESSTYPE type) {
        this.addressType = type;
    }
   
   /* public List<String> getStreet() {
        return streets;
    }

    public void setStreet(List<String> streets) {
        this.streets = streets;
    }*/
    
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
    
   /* public List<String> getStreets() {
        return streets;
    }

    public void setStreets(List<String> streets) {
        this.streets = streets;
    }*/
    public String[] getStreets() {
        return this.streets;
    }

    public void setStreets(String[] streets) {
        this.streets = streets;
    }
    
    
    
}
