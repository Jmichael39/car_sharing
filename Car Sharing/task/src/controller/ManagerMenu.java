package controller;

import model.Company;

import java.util.List;

import static controller.CompanyMenu.companyMenu;
import static controller.MainMenu.*;

public class ManagerMenu {
    static void managerMenu() {

        boolean quit = false;
        while (!quit) {
            System.out.println("\n1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> companyList();
                case 2 -> createCompany();
                case 0 -> {
                    System.out.println();
                    mainMenu();
                    quit = true;
                }
            }
        }
    }

    static void companyList() {
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
