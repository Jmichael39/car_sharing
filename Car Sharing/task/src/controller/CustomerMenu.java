package controller;

import model.Car;
import model.Company;
import model.Customer;

import java.util.List;

import static controller.MainMenu.*;
import static controller.ManagerMenu.listCompanies;

public class CustomerMenu {

    public static void customerMainMenu() {
        List<Customer> customers = customerDAO.getCustomers();

        if (customers.isEmpty()) {
            System.out.println("\nThe customer list is empty!\n");
            mainMenu();
        } else {
            listCustomers(customers);

            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 0) {
                System.out.println();
                mainMenu();
            }
            Customer customer = customers.get(choice - 1);
            if (choice == 0) {
                mainMenu();
            } else {
                customerMenu(customer);
            }
        }
    }

    private static void listCustomers(List<Customer> customers) {
        System.out.println("\nCustomer list:");
        int i = 1;
        for (Customer customer : customers) {
            System.out.println(i + ". " + customer.getName());
            i++;
        }
        System.out.println("0. Back");
    }

    private static void customerMenu(Customer customer) {

        System.out.println("\n1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");
        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> rentCar(customer);
            case 2 -> returnCar(customer);
            case 3 -> myRentedCar(customer);
            case 0 -> {
                System.out.println();
                mainMenu();
            }
        }
    }

    private static void rentCar(Customer customer) {
        // Update customer status against the db
        customer = updateCustomerStatus(customer);
        List<Company> companies = companyDAO.getCompanies();

        if (customer.getRented_car_id() != 0) {
            System.out.println("\nYou've already rented a car!");
            customerMenu(customer);
        } else if (companies.isEmpty()) {
            System.out.println("\nThe company list is empty!");
            customerMenu(customer);
        } else {
            System.out.println("\nChoose a company:");
            listCompanies();

            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 0) {
                customerMenu(customer);
            }
            Company company = companies.get(choice - 1);

            List<Car> cars = carDAO.getAvailableCars(company.getId());
            rentCarHelper(company, customer, cars);
        }

    }

    private static void rentCarHelper(Company company, Customer customer, List<Car> carList) {
        if (carList.isEmpty()) {
            System.out.println("\nNo available cars in the '" + company.getName() + "' company");
            customerMenu(customer);
        } else {
            listCars(carList);
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 0) {
                rentCar(customer);
            } else {
                Car rentedCar = carList.get(choice - 1);
                customerDAO.rentCar(rentedCar, customer);

                System.out.println("\nYou rented '" + rentedCar.getName() + "'");
                customerMenu(customer);
            }
        }
    }

    private static void returnCar(Customer customer) {

        if (customer.getReturned()) {
            System.out.println("\nYou've returned a rented car!");
            customerMenu(customer);
        } else if (customer.getRented_car_id() == 0) {
            System.out.println("\nYou didn't rent a car!");
            customerMenu(customer);
        } else {
            customerDAO.returnCar(customer);
            System.out.println("\nYou've returned a rented car!");
            customerMenu(customer);
        }
    }

    private static void myRentedCar(Customer customer) {
        customer = updateCustomerStatus(customer);

        if (customer.getRented_car_id() == 0) {
            System.out.println("\nYou didn't rent a car!");
            customerMenu(customer);
        } else {
            Car myRentedCar = carDAO.getCarById(customer.getRented_car_id()).get(0);
            customerDAO.myRentedCar(customer, myRentedCar);
            customerMenu(customer);
        }
    }

    public static void createCustomer() {
        System.out.println("\nEnter the customer name: ");
        customerDAO.addCustomer(new Customer(sc.nextLine(), 0));
        System.out.println("The customer was added!\n");
        mainMenu();
    }

    private static Customer updateCustomerStatus(Customer customer) {
        customer = customerDAO.getCustomerById(customer.getId());
        return customer;
    }

    private static void listCars(List<Car> carList) {
        int i = 1;
        System.out.println("\nChoose a car:");
        for (Car car : carList) {
            System.out.println(i + ". " + car.getName());
            i++;
        }
        System.out.println("0. Back");
    }
}
