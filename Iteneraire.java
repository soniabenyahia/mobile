package com.upec.androidtemplate20192020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

public class Iteneraire extends AppCompatActivity {
    EditText textdepart , textArrival ;
    Button recherche ;
    Button buttonDate;
    String datetime,dateins,timeins;
    Calendar date ;
    LinkedList<Travel> travel = new  LinkedList<>();
    GareManager databaseG ;
    LinkedList<ListTravel> travels=new LinkedList<>();
    ListTravelAdapter adapt=new ListTravelAdapter(this);
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iteneraire);
        databaseG = new GareManager (this);
        databaseG.OpenBD ();
        databaseG.initialiser_BD();
        textdepart =  findViewById(R.id.textDepart);
        textArrival =  findViewById(R.id.textArrival);
        recyclerView = findViewById(R.id.recycler);
        buttonDate = findViewById(R.id.buttonDate);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapt.setTravel(travels);
        recyclerView.setAdapter(adapt);
        recyclerView.invalidate();

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar Date = Calendar.getInstance();
                date = Calendar.getInstance();
                new DatePickerDialog(Iteneraire.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.set(year, monthOfYear, dayOfMonth);
                        new TimePickerDialog(Iteneraire.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                date.set(Calendar.MINUTE, minute);
                                //test
                                System.out.println("The choosen one " + date.getTime());
                            }
                        }, Date.get(Calendar.HOUR_OF_DAY), Date.get(Calendar.MINUTE), true ).show();
                    }
                }, Date.get(Calendar.YEAR), Date.get(Calendar.MONTH), Date.get(Calendar.DATE)).show();

            }
        });




        final Context context =this;
        recherche = findViewById(R.id.bRecherche);
        recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("test depart "+textdepart.getText().toString());
                CalculeIteneraire journey = new CalculeIteneraire(Iteneraire.this,textdepart.getText().toString(),textArrival.getText().toString());

                dateins = new SimpleDateFormat("yyyyMMdd").format(date.getTime());
                timeins = new SimpleDateFormat("HHmm").format(date.getTime());
                datetime = dateins+"T"+timeins+"00";

                journey.setDateTime(datetime);
                journey.setT(travel);
                journey.setTravels(travels);
                LinkedList<ListTravel>  listTravels = new LinkedList<>();
                try {
                    listTravels = journey.execute().get();
                    if(listTravels == null) System.out.println("nulll");
                    else System.out.println("list travels test "+listTravels.get(0).toString());
                    System.out.println("______test suze of list travels" + listTravels.size());
                    //teste pour afficher la liste des lignes
                    adapt.setTravel(listTravels);
                    recyclerView.setAdapter( adapt);
                    recyclerView.invalidate();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void notifyItem(ListTravel t){
        MyAdapterIteneraire adapterIteneraire =new MyAdapterIteneraire();
        adapterIteneraire.setTravel(t.travels);
        recyclerView.setAdapter(adapterIteneraire);
        recyclerView.invalidate();
    }



}
