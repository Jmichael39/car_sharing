package controller;

import model.Car;
import model.Company;

import java.util.List;

import static controller.MainMenu.*;

public class CompanyMenu {

    static void companyMenu(Company company) {
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");

        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1 -> listCars(company);
            case 2 -> createCar(company);
            case 0 -> managerMenu();
        }
    }

    static void listCars(Company company) {
        List<Car> cars = carDAO.getAvailableCars(company.getId());

        if (cars.isEmpty()) {
            System.out.println("\nThe car list is empty!\n");
            companyMenu(company);
        } else {
            carList(cars);
            companyMenu(company);
        }
    }

    private static void carList(List<Car> cars) {
        System.out.println("\nCar list: ");
        int i = 1;
        for (Car car : cars) {
            System.out.println(i + ". " + car.getName());
            i++;
        }
        System.out.println();
    }

    static void createCar(Company company) {
        System.out.println("\nEnter the car name:");
        String name = sc.nextLine();

        carDAO.addCar(new Car(0, name, company.getId()));
        System.out.println("The car was added!\n");
        companyMenu(company);
    }
}
