package ru.mycompany.mvc_project;

public class User {

    private String Name;
    private String lastName;
    private String patronymic;
    private int age;
    private int salary;
    private String address;
    private String email;
    private String workPlace;



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String toString(){
        return "Name:" + this.getName()+"/n"+
                "lastName:" + this.getLastName()+"/n"+
                "patronymic:" + this.getPatronymic()+"/n"+
                "age:" + Integer.toString(this.getAge())+"/n"+
                "salary:" + Integer.toString(this.getSalary())+"/n"+
                "address:" + this.getAddress()+"/n"+
                "email:" + this.getEmail()+"/n"+
                "workPlace:" + this.workPlace;
    }
}
