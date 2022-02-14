package com.finalproject.internetpro.entity;

import java.util.HashMap;
import java.util.Map;

public class Tariff {
    protected static final long serialVersionUID = 1L;
    protected int id;
    protected Service service;
    protected double cost;
    protected int daysOfTariff;
    protected Map<Integer,String> description;
    protected Integer status;

    public Integer getId() {return id;}
    public void setId(int id) {this.id = id; }

    public Service getService() {return service;}
    public void setService(Service service) {this.service = service;}

    public Double getCost() {return cost;}
    public void setCost(double cost) {this.cost = cost;}

    public Integer getDaysOfTariff() {return daysOfTariff;}
    public void setDaysOfTariff(int daysOfTariff) {this.daysOfTariff = daysOfTariff;}

    public Map<Integer,String> getDescription() {return description;}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Tariff() {}

    public Tariff(int id, Service service, double cost, int daysOfTariff) {
        this.id = id;
        this.service = service;
        this.cost = cost;
        this.daysOfTariff = daysOfTariff;
    }

    public Tariff(int id, Service service, double cost, int daysOfTariff, Map<Integer, String> description) {
        this.id = id;
        this.service = service;
        this.cost = cost;
        this.daysOfTariff = daysOfTariff;
        this.description = description;
    }

    public void setDescription(Map<Integer,String> description) {this.description = description;}
    public void putDescription(Integer key,String value)
    {
        if(description == null)description = new HashMap<>();
        this.description.put(key,value);
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", service=" + service +
                ", cost=" + cost +
                ", daysOfTariff=" + daysOfTariff +
                ", description=" + description +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tariff tariff = (Tariff) o;

        if (Double.compare(tariff.cost, cost) != 0) return false;
        if (daysOfTariff != tariff.daysOfTariff) return false;
        if (service != null ? !service.equals(tariff.service) : tariff.service != null) return false;
        return description != null ? description.equals(tariff.description) : tariff.description == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = service != null ? service.hashCode() : 0;
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + daysOfTariff;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
