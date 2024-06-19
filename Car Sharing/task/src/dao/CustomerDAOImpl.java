package dao;

import model.Car;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    private final String URL;

    public CustomerDAOImpl(String URL) {
        this.URL = URL;

        try (Connection conn = DriverManager.getConnection(URL)) {
            conn.setAutoCommit(true);

            String query = "CREATE TABLE IF NOT EXISTS CUSTOMER " +
                    "(ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(255) UNIQUE NOT NULL," +
                    "RENTED_CAR_ID INT DEFAULT NULL," +
                    "CONSTRAINT FK_CAR_ID FOREIGN KEY (RENTED_CAR_ID)" +
                    "REFERENCES CAR (ID))";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM CUSTOMER";

        try (Connection conn = DriverManager.getConnection(URL)) {
            ResultSet rs = conn.createStatement().executeQuery(query);

            while (rs.next()) {
                customers.add(new Customer(rs.getString("name")
                        , rs.getInt("id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customers;
    }

    @Override
    public Customer getCustomerById(int id) {
        String query = "SELECT * FROM CUSTOMER WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int iD = rs.getInt("id");
                Customer customer = new Customer(name, iD);
                customer.setRented_car_id(rs.getInt("RENTED_CAR_ID"));
                return customer;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void addCustomer(Customer customer) {
        String query = "INSERT INTO CUSTOMER (name) VALUES ( ? )";

        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, customer.getName());
            pstmt.executeUpdate();

            // Retrieve the generated keys
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    customer.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rentCar(Car car, Customer customer) {
        String query = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?";
        String secQuery = "UPDATE CAR SET AVAILABLE = FALSE WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, car.getId());
            pstmt.setInt(2, customer.getId());
            pstmt.executeUpdate();

            customer.setRented_car_id(car.getId());
            customer.setReturned(false);

            PreparedStatement secStmt = conn.prepareStatement(secQuery);
            secStmt.setInt(1, car.getId());
            secStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void returnCar(Customer customer) {
        String query = "UPDATE CAR SET AVAILABLE = TRUE WHERE ID = ?";
        String secQuery = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, customer.getRented_car_id());
            pstmt.executeUpdate();

            PreparedStatement sceStmt = conn.prepareStatement(secQuery);
            sceStmt.setInt(1, customer.getId());
            sceStmt.executeUpdate();

            customer.setRented_car_id(0);
            customer.setReturned(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void myRentedCar(Customer customer, Car car) {
        String query = "SELECT NAME FROM CAR WHERE ID = ?";
        String secQuery = "SELECT NAME FROM COMPANY WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, customer.getRented_car_id());
            ResultSet rs = pstmt.executeQuery();

            String carName = rs.next() ? rs.getString("NAME") : null;

            PreparedStatement secStmt = conn.prepareStatement(secQuery);
            secStmt.setInt(1, car.getCompany_id());
            ResultSet rs2 = secStmt.executeQuery();

            String companyName = rs2.next() ? rs2.getString("NAME") : null;

            System.out.println("\nYour rented car:");
            System.out.println(carName);
            System.out.println("Company:");
            System.out.println(companyName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
