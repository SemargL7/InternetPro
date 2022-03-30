package com.finalproject.internetpro.web;

import com.finalproject.internetpro.entity.Tariff;

import java.util.Comparator;
import java.util.List;

public class Sorting {
    public static void listSort(List<Tariff> list, boolean AZ, boolean cost){
        list.sort(new Comparator<Tariff>() {
            private int ifEmpty(Tariff o1, Tariff o2){
                if(o1.getService().isEmpty() && o2.getService().isEmpty())
                    return cost?(int) (o1.getCost() - o2.getCost()):(int) (o2.getCost() - o1.getCost());
                else if(o1.getService().isEmpty())
                    return 1;
                else
                    return -1;
            }

            @Override
            public int compare(Tariff o1, Tariff o2) {

                if(o1.getService().isEmpty() || o2.getService().isEmpty()){

                    return ifEmpty(o1, o2);

                } else if (AZ && cost) {

                    if (o1.getService().get(0).getServiceName().equalsIgnoreCase(o2.getService().get(0).getServiceName()))
                        return (int) (o1.getCost() - o2.getCost());
                    return o1.getService().get(0).getServiceName().compareToIgnoreCase(o2.getService().get(0).getServiceName());

                } else if (AZ) {

                    return o1.getService().get(0).getServiceName().compareToIgnoreCase(o2.getService().get(0).getServiceName());

                } else if (cost) {

                    return (int) (o1.getCost() - o2.getCost());

                }
                return 0;
            }
        });
    }
}
