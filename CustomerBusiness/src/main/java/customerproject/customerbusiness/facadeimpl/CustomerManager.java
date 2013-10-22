/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customerbusiness.facadeimpl;

import customerproject.customerbusiness.datamodel.Customer;
import customerproject.customerbusiness.facades.CustomerManagerLocal;
import customerproject.customerbusiness.services.Parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.xml.bind.JAXBException;
import org.jdom.JDOMException;

/**
 *
 * @author timovaananen
 */
@Stateless
public class CustomerManager implements CustomerManagerLocal, Serializable {

    @EJB 
    Parser parser;
    
    public void saveCustomer(File f, Customer cust)
    {
        try {
            parser.saveCustomer(f, cust);
        } catch (JDOMException ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    
    @Override
    public List<Customer> getCustomers(File f)
    {
        List<Customer> customers = new ArrayList<Customer>();
        try {
            customers = parser.getCustomers(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return customers;
    }
    @Override
    public void editCustomer(File f, Customer cust)
    {
        try {
            parser.editCustomer(f, cust);
        } catch (JDOMException ex) {
            Logger.getLogger(CustomerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void deleteCustomer(File f, Customer cust)
    {
        parser.deleteCustomer(f, cust);
    }

}
