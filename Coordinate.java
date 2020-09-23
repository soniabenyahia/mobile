package com.upec.androidtemplate20192020;

public class Coordinate {
   private String  longitude ;
    private String latitude;

    public Coordinate(){ }

    public Coordinate(String longitude ,String latitude) {
        this.longitude = longitude;
        this.latitude = latitude ;
    }

    public void setLatitide(String latitude) {

        this.latitude = latitude;
    }

    public void setLongtitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitide() {
        return latitude;
    }

    public String getLongtitude() {
        return longitude;
    }
}
