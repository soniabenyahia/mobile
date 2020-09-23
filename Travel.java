package com.upec.androidtemplate20192020;

public class Travel {
    private String direction ;
    private String date_arrival ;
    private String date_departure;
    private String from;
    private String to ;
    private String type;
    private String duration;
    private String mode;
    private String path;
    private String ligne ;

    public Travel(){
        this.direction = "vide" ;
        this.date_arrival = "vide" ;
        this.date_departure = "vide";
        this.from = "vide";
        this.to = "vide";
        this.type = "vide";
        this.duration = "vide";
        this.mode = "vide";
        this.path = "vide";
        this.ligne = "vide";
    }

    public Travel(String date_arrival ,String date_departure ,String from ,String to ,String ligne ,String direction,String path){
        this.direction = direction;
        this.date_arrival = date_arrival ;
        this.date_departure = date_departure;
        this.from = from;
        this.to = to;
        this.ligne = ligne;
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate_arrival() {
        return date_arrival;
    }

    public String getDate_departure() {
        return date_departure;
    }

    public String getDirection() {
        return direction;
    }

    public String getFrom() {
        return from;
    }

    public String getLigne() {
        return ligne;
    }

    public String getMode() {
        return mode;
    }

    public String getTo() {
        return to;
    }

    public void setDate_arrival(String date_arrival) {
        this.date_arrival = date_arrival;
    }

    public void setDate_departure(String date_departure) {
        this.date_departure = date_departure;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setLigne(String ligne) {
        this.ligne = ligne;
    }

}
