package com.upec.androidtemplate20192020;

import java.util.LinkedList;

class InformationProximite {
    String name;
    String stop_id;
    double lat;
    double lon;
    long distance;

    LinkedList<Travel> depart=new LinkedList<>();
    public InformationProximite() {
    }

    public InformationProximite(String name, String stop_id, double lat, double lon,long distance,LinkedList<Travel> depart) {
        this.name = name;
        this.stop_id = stop_id;
        this.lat = lat;
        this.lon = lon;
        this.distance=distance;
        this.depart=depart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDistance() {
        return Long.toString(distance);
    }
}
