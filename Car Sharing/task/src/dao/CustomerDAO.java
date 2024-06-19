package dao;

import model.Car;
import model.Company;
import model.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> getCustomers();
    Customer getCustomerById(int id);
    void addCustomer(Customer customer);
    void rentCar(Car car, Customer customer);
    void returnCar(Customer customer);
    void myRentedCar(Customer customer, Car car);
}
