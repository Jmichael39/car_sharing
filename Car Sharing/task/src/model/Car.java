package model;

public class Car {
    private String name;
    private int id;
    private int company_id;

    public Car(int id, String name, int company_id) {
        this.name = name;
        this.id = id;
        this.company_id = company_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", company_id=" + company_id +
                '}';
    }
}
