package customerproject.customerweb;

import customerproject.customerbusiness.datamodel.Address;
import customerproject.customerbusiness.datamodel.Customer;
import customerproject.customerbusiness.datamodel.Customers;
import customerproject.customerbusiness.datamodel.ContactType;
import customerproject.customerbusiness.datamodel.EmailModel;
import customerproject.customerbusiness.datamodel.Phone;
import customerproject.customerbusiness.facades.CustomerManagerLocal;
import customerproject.customerbusiness.services.Parser;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;



/**
 *
 * @author timovaananen
 */
@ManagedBean(name="customerBean")
@SessionScoped
public class CustomerBean implements Serializable {
    
    private boolean editable;
    private Customer customer;
    private File file;
    private InputStream inputStream;
    
    private static final String xmlResource = "/Data.xml";
    private List<Customer> customers = new ArrayList<Customer>();
    private Customer selectedCustomer;
  
    private String addressType;
    
    @Pattern(regexp="^[+358]{4}[0-9]*", message="Please provide phonenumber eg.in format +35844123456")
    private String phone1;

    //@Pattern(regexp="^[+358]{4}[0-9]*", message="Please provide phonenumber eg.in format +35844123456")
    private String phone2;
    private String phone1Type;
    private String phone2Type;
    
   // @Email(message="Please provide a valid email address")
   // @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    private String email1;
    //@Email(message="Please provide a valid email address")
    //@Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    private String email2;
    private String email1Type;
    private String email2Type;
    
    @EJB 
    private CustomerManagerLocal manager;

    public CustomerBean() throws URISyntaxException {
        System.out.println("CustomerBean()");
        
        URL url = this.getClass().getResource(xmlResource);
        System.out.println("Url: "+url);
        this.file = new File(url.toURI());
       
    }
   
    @PostConstruct
    public void init() {
        
        System.out.println("init()");
        this.editable = false;
        this.customer = new Customer();
      
        this.phone1 = "";
        this.phone2 = "";
        this.email1 = "";
        this.email2 = "";
        
        this.addressType = Address.ADDRESSTYPE.HOME_ADDRESS.toString();
        this.phone1Type = ContactType.CONTACTTYPE.MOBILE_PHONE.toString();
        this.phone2Type = ContactType.CONTACTTYPE.MOBILE_PHONE.toString();
        this.email1Type = ContactType.CONTACTTYPE.HOME_EMAIL.toString();
        this.email2Type = ContactType.CONTACTTYPE.HOME_EMAIL.toString();
        
        if(this.customers.isEmpty()) {
            this.customers = manager.getCustomers(this.file);
            System.out.println("Size: "+this.customers.size());
            for(int i = 0; i < this.customers.size(); i++)
               System.out.println("Name: "+this.customers.get(i).getName());
        }
    }
    
    public void manageCustomer() throws URISyntaxException {
        
        System.out.println("Name: "+this.customer.getName());
        System.out.println("Note: "+this.customer.getNotes());
      
        System.out.println("Street1: "+this.customer.getAddress().getStreets()[0]);
        System.out.println("Street2: "+this.customer.getAddress().getStreets()[1]);
        System.out.println("Postal code: "+this.customer.getAddress().getPostalCode());
        System.out.println("Town: "+this.customer.getAddress().getTown());
        System.out.println("Address type: "+Address.ADDRESSTYPE.valueOf(this.addressType));
        
        this.customer.getAddress().setType(Address.ADDRESSTYPE.valueOf(this.addressType));
        if(this.editable)
            this.customer.getContacts().clear();
      
        this.customer.getContacts().add(new Phone(ContactType.CONTACTTYPE.valueOf(this.phone1Type), this.phone1));
        if(!this.getPhone2().equals(""))
            this.customer.getContacts().add(new Phone(ContactType.CONTACTTYPE.valueOf(this.phone2Type), this.phone2));
        if(!this.getEmail1().equals(""))
            this.customer.getContacts().add(new EmailModel(ContactType.CONTACTTYPE.valueOf(this.email1Type), this.email1));
        if(!this.getEmail2().equals(""))
            this.customer.getContacts().add(new EmailModel(ContactType.CONTACTTYPE.valueOf(this.email2Type), this.email2));
     
        if(!this.editable)
        {
            this.manager.saveCustomer(this.file, this.customer);
        
            this.customers.add(this.customer);
        }
        else
        { 
           this.manager.editCustomer(this.file, this.customer); 
        }
       
        init();
    }
    
    public void removeCustomer() {
        
        System.out.println("Remove customer: "+this.selectedCustomer.getName());
        
        this.customers.remove(this.selectedCustomer);
        this.manager.deleteCustomer(this.file, this.selectedCustomer);
    }
    
    public void editCustomer() {
        
        this.editable = true;
        this.customer = this.selectedCustomer;
       
        this.addressType = this.customer.getAddress().getAddressType().toString();
        
        this.phone1 = this.customer.getContacts().get(0).getValue();
        this.phone1Type = this.customer.getContacts().get(0).getContactType().toString();
    }
    
    public void addCustomer() {
        this.editable = false;
    }
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
     public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    
    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInput(InputStream input) {
        this.inputStream = input;
    }
   
     public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
    
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
      public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone1Type() {
        return phone1Type;
    }

    public void setPhone1Type(String phone1Type) {
        this.phone1Type = phone1Type;
    }

    public String getPhone2Type() {
        return phone2Type;
    }

    public void setPhone2Type(String phone2Type) {
        this.phone2Type = phone2Type;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmail1Type() {
        return email1Type;
    }

    public void setEmail1Type(String email1Type) {
        this.email1Type = email1Type;
    }

    public String getEmail2Type() {
        return email2Type;
    }

    public void setEmail2Type(String email2Type) {
        this.email2Type = email2Type;
    }
    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
    
}
