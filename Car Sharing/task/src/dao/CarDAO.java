package dao;

import model.Car;

import java.util.List;

public interface CarDAO {
    List<Car> getCarById(int company_id);
    List<Car> getAvailableCars(int company_id);
    void addCar(Car car);
}
