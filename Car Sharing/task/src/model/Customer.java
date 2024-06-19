package model;

public class Customer {
    private String name;
    private int id;
    private int rented_car_id;
    private boolean returned;

    public Customer(String name, int id) {
        this.name = name;
        this.id = id;
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

    public int getRented_car_id() {
        return rented_car_id;
    }

    public void setRented_car_id(int rented_car_id) {
        this.rented_car_id = rented_car_id;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean getReturned() {
        return returned;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", rented_car_id=" + rented_car_id +
                ", returned=" + returned +
                '}';
    }
}
