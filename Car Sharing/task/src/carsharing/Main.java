package carsharing;

import controller.MainMenu;
import dao.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Dependency Injection setup (could use a framework like Spring)
        String url = "jdbc:h2:tcp://localhost/~/test";
        CompanyDAO companyDAO = new CompanyDAOImpl(url);
        CarDAO carDAO = new CarDAOImpl(url);
        CustomerDAO customerDAO = new CustomerDAOImpl(url);
        Scanner sc = new Scanner(System.in);

        MainMenu mainMenu = new MainMenu(sc, companyDAO, carDAO, customerDAO);
        mainMenu.run();
    }
}