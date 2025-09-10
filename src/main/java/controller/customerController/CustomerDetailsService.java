package controller.customerController;

import javafx.collections.ObservableList;
import model.Customer;

public interface CustomerDetailsService {
    void addCustomerDetails(Customer customer);
    void deleteCustomerDetails(String customerId);
    void updateCustomerDetails(Customer customer);
    ObservableList<Customer> getAllCustomerDetails();
}
