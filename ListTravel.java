package com.upec.androidtemplate20192020;

import java.util.LinkedList;

public class ListTravel {
    LinkedList<Travel> travels ;
    String duration;

    public ListTravel(LinkedList<Travel> travels,String duration){
        this.travels = travels ;
        this.duration = duration;
    }
}
