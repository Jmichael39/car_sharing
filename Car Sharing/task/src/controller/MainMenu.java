package controller;

import dao.*;

import java.util.Scanner;

import static controller.CustomerMenu.createCustomer;
import static controller.CustomerMenu.customerMainMenu;
import static controller.ManagerMenu.managerMenu;

public class MainMenu {
    protected static CompanyDAO companyDAO;
    protected static CarDAO carDAO;
    protected static CustomerDAO customerDAO;
    protected static Scanner sc;

    public MainMenu(Scanner sc, CompanyDAO companyDAO, CarDAO carDAO, CustomerDAO customerDAO) {
        MainMenu.sc = sc;
        MainMenu.companyDAO = companyDAO;
        MainMenu.carDAO = carDAO;
        MainMenu.customerDAO = customerDAO;
    }

    public void run() {
        mainMenu();
    }

    static void mainMenu() {

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
}
