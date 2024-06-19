package dao;

import model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {
    private final String URL;

    public CarDAOImpl(String URL) {
        this.URL = URL;

        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS CAR (ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(255) UNIQUE NOT NULL," +
                    "COMPANY_ID INT NOT NULL, " +
                    "AVAILABLE BOOLEAN DEFAULT TRUE," +
                    "CONSTRAINT fk_id FOREIGN KEY (COMPANY_ID)" +
                    "REFERENCES COMPANY (ID))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> getCarById(int companyId) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM car WHERE company_id = ?";

        return getCars(companyId, cars, sql);
    }

    @Override
    public List<Car> getAvailableCars(int companyId) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM car WHERE AVAILABLE = TRUE AND COMPANY_ID = ?";

        return getCars(companyId, cars, sql);
    }

    @Override
    public void addCar(Car car) {
        String sql = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, car.getName());
            preparedStatement.setInt(2, car.getCompany_id());

            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    car.setId(rs.getInt(1));
                }
            }

            car.setCompany_id(car.getCompany_id());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Car> getCars(int companyId, List<Car> cars, String sql) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int carId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int compId = resultSet.getInt("company_id");
                cars.add(new Car(carId, name, compId));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cars;
    }

}
