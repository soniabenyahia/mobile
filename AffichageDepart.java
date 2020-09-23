package com.upec.androidtemplate20192020;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AffichageDepart extends AppCompatActivity {
    GareManager databaseG ;
    Button button;
    AutoCompleteTextView editText;
    Button date ;
    Calendar date1=Calendar.getInstance();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_affichage_depart);
        //création de gareManager
        databaseG = new GareManager (this);
        databaseG.OpenBD ();
        databaseG.initialiser_BD();

        editText = findViewById(R.id.editText);
        List<Gare> listgare = databaseG.getAll();
        List<String> gareNames = new ArrayList<>();
        for(Gare g : listgare) {
            gareNames.add(g.getName());
        }


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AffichageDepart.this,
                                                                 android.R.layout.simple_list_item_1, gareNames);


        editText.setAdapter(myAdapter);


        button = findViewById(R.id.button1);
        date = findViewById(R.id.buttondDate);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar Date = Calendar.getInstance();
                date1 = Calendar.getInstance();
                new DatePickerDialog(AffichageDepart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date1.set(year, monthOfYear, dayOfMonth);
                        new TimePickerDialog(AffichageDepart.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                date1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                date1.set(Calendar.MINUTE, minute);
                                //test
                                System.out.println("The choosen one " + date1.getTime());
                            }
                        }, Date.get(Calendar.HOUR_OF_DAY), Date.get(Calendar.MINUTE), true ).show();
                    }
                }, Date.get(Calendar.YEAR), Date.get(Calendar.MONTH), Date.get(Calendar.DATE)).show();

            }
        });

        final Context context =this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nom_gare = editText.getText().toString();

                System.out.println("nom de gare:  "+nom_gare);

                Coordinate coordinateG = new Coordinate();
                ArrayList<Travel> travel = new ArrayList<Travel>();
                try {
                   coordinateG = databaseG.getCoord(nom_gare);
                   GareDepart depart = new GareDepart();

                    String dateins = new SimpleDateFormat("yyyyMMdd").format(date1.getTime());
                    String timeins = new SimpleDateFormat("HHmmss").format(date1.getTime());
                    String datetime = dateins+"T"+timeins;
                    System.out.println("date and time :" + datetime);
                    depart.setDatetime(datetime);

                   depart.setCoordinate(coordinateG);
                   travel = depart.getTravel();
                   String objet =depart.execute().get();

                   //teste pour afficher la liste des lignes
                   MyAdapterDepart adapterDepart = new MyAdapterDepart(context,travel);
                   ListView liste = (ListView) findViewById(R.id.list);

                   liste.setAdapter(adapterDepart);
                   ///--------test -----
                    System.out.println("teste taile de la liste " + travel.size());

                   System.out.println("-------------------------------Coordinate gare----------------");

                   System.out.println("lat :"+coordinateG.getLatitide()+" "+"longt :"+coordinateG.getLongtitude());
                   if(travel.size()> 0) {
                       System.out.println("Line indice 0 :" + travel.get(0).getLigne());
                   }

               } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

    }

}
