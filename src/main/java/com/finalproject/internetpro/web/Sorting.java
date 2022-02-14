package com.finalproject.internetpro.web;

import com.finalproject.internetpro.entity.Tariff;

import java.util.Comparator;
import java.util.List;

public class Sorting {
    public static void listSort(List<Tariff> list, boolean AZ, boolean cost){
        list.sort(new Comparator<Tariff>() {
            @Override
            public int compare(Tariff o1, Tariff o2) {
                if (AZ && cost) {
                    if (o1.getService().getServiceName().equalsIgnoreCase(o2.getService().getServiceName()))
                        return (int) (o1.getCost() - o2.getCost());
                    return o1.getService().getServiceName().compareToIgnoreCase(o2.getService().getServiceName());
                } else if (AZ) {
                    if (o1.getService().getServiceName().equalsIgnoreCase(o2.getService().getServiceName()))
                        return (int) (o2.getCost() - o1.getCost());
                    return o1.getService().getServiceName().compareToIgnoreCase(o2.getService().getServiceName());
                } else if (cost) {
                    if (o1.getService().getServiceName().equalsIgnoreCase(o2.getService().getServiceName()))
                        return (int) (o1.getCost() - o2.getCost());
                    return o2.getService().getServiceName().compareToIgnoreCase(o1.getService().getServiceName());
                } else {
                    if (o1.getService().getServiceName().equalsIgnoreCase(o2.getService().getServiceName()))
                        return (int) (o2.getCost() - o1.getCost());
                    return o2.getService().getServiceName().compareToIgnoreCase(o1.getService().getServiceName());
                }
            }
        });
    }
}
