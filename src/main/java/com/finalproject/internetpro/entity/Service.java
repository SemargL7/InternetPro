package com.finalproject.internetpro.entity;


public class Service {
    protected static final long serialVersionUID = 1L;
    protected int id;
    protected String serviceName;
    public int getId() {return id; }
    public void setId(int id) {this.id = id;}
    public String getServiceName() {return serviceName;}
    public void setServiceName(String serviceName) {this.serviceName = serviceName;}

    public Service() {}

    public Service(int id, String serviceName) {
        this.id = id;
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        return serviceName != null ? serviceName.equals(service.serviceName) : service.serviceName == null;
    }

    @Override
    public int hashCode() {
        return serviceName != null ? serviceName.hashCode() : 0;
    }
}
