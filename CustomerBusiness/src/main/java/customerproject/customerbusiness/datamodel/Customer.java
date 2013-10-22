package customerproject.customerbusiness.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author timovaananen
 */
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name="Customer", namespace = "http://www.arcusys.fi/customer-example")
//@XmlType(namespace = "http://www.arcusys.fi/customer-example")
public class Customer implements Serializable {
    
    //@XmlElement(name = "Name", required = true)
    private String name;
    //@XmlElement(name = "Notes")
    private String notes;
    //@XmlElement(name = "Address")
    private Address address;
    //@XmlElement(name="Phone")
    private List<ContactType> phones = new ArrayList<ContactType>();
    //@XmlElement(name="Email")
    private List<ContactType> emails = new ArrayList<ContactType>();

    private List<ContactType> contacts = new ArrayList<ContactType>();
    
    
    public Customer() {
    
        this.address = new Address();
    }
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
     public List<ContactType> getPhones() {
        return phones;
    }

    public void setPhones(List<ContactType> phones) {
        this.phones = phones;
    }
    
     public List<ContactType> getEmails() {
        return emails;
    }

    public void setEmails(List<ContactType> emails) {
        this.emails = emails;
    }
    
    public List<ContactType> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactType> contacts) {
        this.contacts = contacts;
    }

    
}
