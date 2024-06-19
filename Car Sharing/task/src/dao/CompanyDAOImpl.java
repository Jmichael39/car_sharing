package dao;

import model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {
    private final String URL;
    static final String JDBC_DRIVER = "org.h2.Driver";

    public CompanyDAOImpl(String URL) {
        this.URL = URL;

        try (Connection conn = DriverManager.getConnection(URL)) {
            Class.forName(JDBC_DRIVER);
            conn.setAutoCommit(true);
            Statement statement = conn.createStatement();

            //statement.execute("DROP TABLE IF EXISTS CUSTOMER");
            //statement.execute("DROP TABLE IF EXISTS CAR");
            //statement.execute("DROP TABLE IF EXISTS COMPANY");

            statement.execute("CREATE TABLE IF NOT EXISTS COMPANY (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME VARCHAR(255) UNIQUE NOT NULL)");

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Company> getCompanies() {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT id, name FROM company ORDER BY ID";

        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                companies.add(new Company(resultSet.getString("name")
                        , resultSet.getInt("id")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return companies;
    }

    @Override
    public void addCompany(Company company) {
        String sql = "INSERT INTO company (name) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.executeUpdate();
            System.out.println("The company was created!");
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    company.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
