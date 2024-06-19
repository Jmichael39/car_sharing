package controller;

import dao.*;
import model.Company;

import java.util.List;
import java.util.Scanner;

import static controller.CompanyMenu.*;
import static controller.CustomerMenu.createCustomer;
import static controller.CustomerMenu.customerMainMenu;

public class MainMenu {
    static CompanyDAO companyDAO;
    static CarDAO carDAO;
    static CustomerDAO customerDAO;

    static Scanner sc = new Scanner(System.in);
    static String URL;
    //static final String URL = "jdbc:h2:tcp://localhost/~/test";

    public static void run(String DbName) {
        URL = "jdbc:h2:./src/carsharing/db/" + DbName;
        companyDAO = new CompanyDAOImpl(URL);
        carDAO = new CarDAOImpl(URL);
        customerDAO = new CustomerDAOImpl(URL);
        mainMenu();
    }

    public static void mainMenu() {

        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");
            int option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 1 -> managerMenu();
                case 2 -> customerMainMenu();
                case 3 -> createCustomer();
                case 0 -> {
                    sc.close();
                    System.exit(0);
                }
            }
        }
    }

    public static void managerMenu() {

        boolean quit = false;
        while (!quit) {
            System.out.println("\n1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> chooseCompany();
                case 2 -> createCompany();
                case 0 -> {
                    System.out.println();
                    mainMenu();
                    quit = true;
                }
            }
        }
    }

    static void chooseCompany() {
        List<Company> companies = companyDAO.getCompanies();

        if (companies.isEmpty()) {
            System.out.println("\nThe company list is empty!");
            return;
        } else {
            System.out.println("\nChoose the company:");
            listCompanies();
        }

        int choice = Integer.parseInt(sc.nextLine());

        if (choice != 0) {
            Company company = companies.get(choice - 1);
            System.out.println("\n'" + company.getName() + "' company");
            companyMenu(company);
        } else {
            managerMenu();
        }
    }

    static void createCompany() {
        System.out.println("\nEnter the company name:");
        String name = sc.nextLine();

        Company newCompany = new Company(name, 0);
        companyDAO.addCompany(newCompany);
    }

    static void listCompanies() {
        List<Company> companies = companyDAO.getCompanies();

        int i = 1;
        for (Company company : companies) {
            System.out.println(i + ". " + company.getName());
            i++;
        }
        System.out.println("0. Back");
    }
}
