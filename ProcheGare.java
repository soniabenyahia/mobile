package com.upec.androidtemplate20192020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.nio.BufferUnderflowException;
import java.util.LinkedList;

public class ProcheGare extends AppCompatActivity {
    TextView PlusProche ;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proche_gare);
        PlusProche = findViewById(R.id.ProcheGare);
        LinkedList<InformationProximite> info = new LinkedList<>();
        Intent intent =getIntent();
        Bundle b = new Bundle();
        b = intent.getExtras();
        final Context context =this;

        try {
           Coordinate coordinate = new Coordinate(b.getString("log").toString(), b.getString("lat").toString());
           System.out.println("coordoné : log "+coordinate.getLongtitude() +" lat :"+coordinate.getLatitide());
           Geolocalisation geolocalisation = new Geolocalisation(coordinate);

           System.out.println("geo lat"+ geolocalisation.coord.getLatitide()+", geo log" +geolocalisation.coord.getLongtitude());

           info = geolocalisation.execute().get();


           if (info.size() > 0) {
               System.out.println("premier gare plus proche " + info.get(0).getName());
               PlusProche.setText("les arrêts joignables");
               MyAdapterGeo adapterGeo = new MyAdapterGeo(context,info);
               list = (ListView) findViewById(R.id.list1);
               System.out.println("set adapter");
               System.out.println("---Distance-----"+info.get(0).getDistance());

               list.setAdapter(adapterGeo);
           }else{
               PlusProche.setText(" Aucun  arrête joignable trouver ");
           }

       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}
