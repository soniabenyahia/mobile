package com.upec.androidtemplate20192020;

public class Gare {

    private String name;
    private String latitude ;
    private String longitude;

    public Gare(){

    }

    public String getLatitude() {

        return latitude;
    }

    public String getLongitute() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitute(String longitude) {

        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }
}
