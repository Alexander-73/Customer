/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customerbusiness.facades;

import customerproject.customerbusiness.datamodel.Customer;
import java.io.File;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author timovaananen
 */
@Local
public interface CustomerManagerLocal {

   public void saveCustomer(File f, Customer cust);
   
   public List<Customer> getCustomers(File f);

   public void editCustomer(File file, Customer customer);
   
   public void deleteCustomer(File file, Customer customer);
   
   
}
