package com.finalproject.internetpro.model;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class User {
    protected static final long serialVersionUID = 1L;
    protected int id;
    protected String name;
    protected String surname;
    protected String email;
    protected String password;
    protected double balance;
    protected Date dateOfBirth;
    protected boolean blocked;
    //protected boolean specialAccess;
    protected UserAccess access;
    protected List<Tariff> tariffs;


    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() { return name;}
    public void setName(String name) {this.name = name;}
    public String getSurname() {return surname;}
    public void setSurname(String surname) {this.surname = surname;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public double getBalance() {return balance;}
    public void setBalance(double balance) {this.balance = balance;}
    public Date getDateOfBirth() {return dateOfBirth;}
    public void setDateOfBirth(Date dateOfBirth) {this.dateOfBirth = dateOfBirth;}
    public boolean isBlocked() {return blocked;}
    public void setBlocked(boolean blocked) {this.blocked = blocked;}
    public UserAccess getUserAccess() {
        return access;
    }
    public void setUserAccess(UserAccess access) {
        this.access = access;
    }
    public List<Tariff> getTariffs() {return tariffs;}
    public void setTariffs(List<Tariff> tariffs) {this.tariffs = tariffs;}
    public void addTariff(Tariff tariff){
        if(tariffs == null){
            tariffs = new ArrayList<>();
        }
        if (!tariffs.contains(tariff)) {
            tariffs.add(tariff);
        }
    }
    public void removeTariff(Tariff tariff){
        if(tariffs != null){
            tariffs.remove(tariff);
        }
    }

    public User() {
    }
    public User(int id, String name, String surname, String email, String password, double balance, Date dateOfBirth, boolean blocked, UserAccess access) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.dateOfBirth = dateOfBirth;
        this.blocked = blocked;
        this.access = access;
        tariffs = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (Double.compare(user.balance, balance) != 0) return false;
        if (blocked != user.blocked) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(user.dateOfBirth) : user.dateOfBirth != null) return false;
        return access == user.access;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (blocked ? 1 : 0);
        result = 31 * result + (access != null ? access.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", dateOfBirth=" + dateOfBirth +
                ", blocked=" + blocked +
                ", access=" + access +
                ", tariffs=" + tariffs +
                '}';
    }
}
