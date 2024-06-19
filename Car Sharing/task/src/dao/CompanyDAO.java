package dao;

import model.Company;

import java.util.List;

public interface CompanyDAO {
    List<Company> getCompanies();
    void addCompany(Company company);
}
