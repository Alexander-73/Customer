package customerproject.customerbusiness.services;

import customerproject.customerbusiness.datamodel.Address;
import customerproject.customerbusiness.datamodel.ContactType;
import customerproject.customerbusiness.datamodel.Customer;

import customerproject.customerbusiness.datamodel.EmailModel;
import customerproject.customerbusiness.datamodel.Phone;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


/**
 *
 * @author timovaananen
 * 15-20.10.2013
 * Parser.java
 * 
 * Parser class is responsible for xml dom manipulation
 * 
 * 
 * 
 */
@Stateless
@LocalBean
public class Parser implements Serializable {

    private static final String NS = "http://www.arcusys.fi/customer-example";
   
    public Parser() {
        
    }
    
    /*
     * 
     * @author Timo Väänänen
     * 17.10.2013
     * method saveCustomer
     * Param: Customer object, which is inserted to the DOM with JDOM
     */
    public void saveCustomer(File f, Customer cust) throws JDOMException {
        
        System.out.println("saveCustomer()");
        XMLOutputter out = new XMLOutputter();
        out.setFormat(Format.getPrettyFormat());
        SAXBuilder saxBuilder = new SAXBuilder();
        Element rootElement = null;
        Element contactElement = null;
       
        Document doc = null;  
        try {
                doc = saxBuilder.build(f);
                rootElement = doc.getRootElement();
                System.out.println("Root: "+rootElement);
                
            } catch (IOException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
           
           Namespace NS = Namespace.getNamespace(Parser.NS);
            Element myElement = new Element("Customer", Parser.NS);
            
            myElement.addContent(new Element("Name", Parser.NS)
                       .setText(cust.getName()));
            
            if(!cust.getNotes().equals("")) {
                myElement.addContent(new Element("Notes", Parser.NS)
                       .setText(cust.getNotes()));
                
            }
            Element addressElement = new Element("Address", Parser.NS);
            addressElement.addContent(new Element("Type", Parser.NS).setText(cust.getAddress().getAddressType().toString()));
            /*for(int i = 0; i < cust.getAddress().getStreets().length; i++)
            {*/
            addressElement.addContent(new Element("Street", Parser.NS).setText(cust.getAddress().getStreets()[0]));
            if(!cust.getAddress().getStreets()[1].equals(""))
                 addressElement.addContent(new Element("Street", Parser.NS).setText(cust.getAddress().getStreets()[1]));    
            //}
            addressElement.addContent(new Element("PostalCode", Parser.NS).setText(cust.getAddress().getPostalCode()));
            addressElement.addContent(new Element("Town", Parser.NS).setText(cust.getAddress().getTown()));
           
            myElement.addContent(addressElement);
            
            for(int j = 0; j < cust.getContacts().size(); j++) {
                if(cust.getContacts().get(j) instanceof Phone) {
                    contactElement = new Element("Phone", Parser.NS);
                }
                else if(cust.getContacts().get(j) instanceof EmailModel) {
                    contactElement = new Element("Email", Parser.NS);
                }   
                contactElement.addContent(new Element("Type", Parser.NS).setText(cust.getContacts().get(j).getContactType().toString()));
                contactElement.addContent(new Element("Value", Parser.NS).setText(cust.getContacts().get(j).getValue()));
                
                myElement.addContent(contactElement);
            }
            rootElement.addContent(myElement);
            
            try { 
                FileWriter writer = new FileWriter(f);
                out.output(doc, writer);
            } catch (IOException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        
        
        
    }
    
    public List<Customer> getCustomers(File f) throws FileNotFoundException {
        
        SAXBuilder builder = new SAXBuilder();
        List<Customer> customerList = new ArrayList<Customer>();
        Document xml = null;
        try {
            xml = builder.build(f);
        } catch (JDOMException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          
          }
             
        Element root = xml.getRootElement();
             
        System.out.println("Root element of XML document is : " + root.getName());
        System.out.println("Number of customers in this XML : " + root.getChildren().size());
        
        List<Element> customers = root.getChildren("Customer", Namespace.getNamespace(Parser.NS));
             
        for(int i = 0; i < customers.size(); i++) {
            System.out.println("Create new customer");
            Customer cust = new Customer();
           
            List<Element> custChildElement = customers.get(i).getChildren();  //get Name, Notes, Address, Phone, Email
            for(int j = 0; j < custChildElement.size(); j++)
            {
                Element elem = custChildElement.get(j);
                elem.setNamespace(Namespace.NO_NAMESPACE);
                if(elem.getName().equals("Name"))
                    cust.setName(elem.getText());
                else if(elem.getName().equals("Notes"))
                    cust.setNotes(elem.getText());
                else if(elem.getName().equals("Phone"))
                {
                    List<Element> phoneElems = elem.getChildren();
                    phoneElems.get(0).setNamespace(Namespace.NO_NAMESPACE);  //Type
                    phoneElems.get(1).setNamespace(Namespace.NO_NAMESPACE);   //Value
                    
                    cust.getContacts().add(new Phone(ContactType.CONTACTTYPE.valueOf(phoneElems.get(0).getText()), phoneElems.get(1).getText()));
                }
                else if(elem.getName().equals("Email"))
                {
                    List<Element> emailElems = elem.getChildren();
                    emailElems.get(0).setNamespace(Namespace.NO_NAMESPACE);  //Type
                    emailElems.get(1).setNamespace(Namespace.NO_NAMESPACE);   //Value
                   
                    cust.getContacts().add(new EmailModel(ContactType.CONTACTTYPE.valueOf(emailElems.get(0).getText()), emailElems.get(1).getText()));
                }
                else if(elem.getName().equals("Address"))
                {
                    //List<String> tempStreets = new ArrayList<String>();
                    
                    String[] tempStreets = new String[2];
                    List<Element> addressElems = elem.getChildren();
                    
                    if(addressElems.size() == 5)
                    {
                        addressElems.get(0).setNamespace(Namespace.NO_NAMESPACE);  //Type
                        addressElems.get(1).setNamespace(Namespace.NO_NAMESPACE);   //Street
                        addressElems.get(2).setNamespace(Namespace.NO_NAMESPACE);   //Stret
                        addressElems.get(3).setNamespace(Namespace.NO_NAMESPACE);   //postal
                        addressElems.get(4).setNamespace(Namespace.NO_NAMESPACE);   //town
                        
                        tempStreets[0] = addressElems.get(1).getText();
                        tempStreets[1] = addressElems.get(2).getText();
                       
                        cust.getAddress().setPostalCode(addressElems.get(3).getText());
                        cust.getAddress().setTown(addressElems.get(4).getText());
                    }
                    else
                    {
                        addressElems.get(0).setNamespace(Namespace.NO_NAMESPACE);  //Type
                        addressElems.get(1).setNamespace(Namespace.NO_NAMESPACE);   //Street
                        addressElems.get(2).setNamespace(Namespace.NO_NAMESPACE);   //postal
                        addressElems.get(3).setNamespace(Namespace.NO_NAMESPACE);   //town
                        
                        tempStreets[0] = addressElems.get(1).getText();
                        cust.getAddress().setPostalCode(addressElems.get(2).getText());
                        cust.getAddress().setTown(addressElems.get(3).getText());
                    }
                    cust.getAddress().setType(Address.ADDRESSTYPE.valueOf(addressElems.get(0).getText()));
                    cust.getAddress().setStreets(tempStreets);
                    
                }
            }
        
            customerList.add(cust);
        }
        return customerList;
      
    }

    public void editCustomer(File f, Customer cust) throws JDOMException {
        
        deleteCustomer(f, cust);
        saveCustomer(f, cust);
    }

    public void deleteCustomer(File f, Customer cust) {
        
        boolean found = false;
        XMLOutputter out = new XMLOutputter();
        out.setFormat(Format.getPrettyFormat());
        SAXBuilder builder = new SAXBuilder();
        List<Customer> customerList = new ArrayList<Customer>();
        Document xml = null;
        try {
            xml = builder.build(f);
        } catch (JDOMException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          
          }
             
        Element root = xml.getRootElement();
          
        List customerChildren = root.getChildren();
        
        System.out.println("Children "+customerChildren.size());
        
        Iterator itr = customerChildren.iterator();
        while (itr.hasNext() && !found) {
            Element customer = (Element) itr.next();
            customer.setNamespace(Namespace.NO_NAMESPACE);   //for comparison, have to remove namespace definition
            List customerFeatureList = customer.getChildren();
            for(int i = 0; i < customerFeatureList.size(); i++)
            {
                Element elem = (Element) customerFeatureList.get(i);
                
                elem.setNamespace(Namespace.NO_NAMESPACE);
                System.out.println("Elem: "+elem);
                if(elem.getName().equals("Name"))
                {
                    System.out.println("Found name");
                    if(elem.getText().equals(cust.getName()))
                    {
                        System.out.println("Found: "+cust.getName());
                        itr.remove();
                        found = true;
                        break;
                    }
                }
               
            }
            if(!found)                   //remember to set namespace back again for parent and child elements
            {
                customer.setNamespace(Namespace.getNamespace(Parser.NS));
                for(int i = 0; i < customerFeatureList.size(); i++)
                {
                    Element tempElem = (Element) customerFeatureList.get(i);
                    tempElem.setNamespace(Namespace.getNamespace(Parser.NS));
                }
                
            }
          
        }
        
          try { 
                FileWriter writer = new FileWriter(f);
                out.output(xml, writer);
            } catch (IOException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        
       
    }
}
