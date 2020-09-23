package com.upec.androidtemplate20192020;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


public class Main1Activity extends AppCompatActivity {
    GareManager database;
    EditText gare;
    LocationManager locationManager = null;


    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        database = new GareManager(this);
        database.OpenBD();


        //créer le cursor pour virifier la base de données rempli ou pas
        try (Cursor c = database.getDb().rawQuery("SELECT id_gare FROM gare WHERE id_gare='1' ", null)) {
            if (c.getCount() == 0) DownloadFile();
        }

        //pour déplacer vers la pagage de recherche une gare
        Button button = findViewById(R.id.button);
        final Intent intent = new Intent(this, AffichageDepart.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent, 1);
            }
        });

        //pour déplacer vers la page iteneraire
        Button button1 = findViewById(R.id.button2);
        final Intent intent1 = new Intent(this, Iteneraire.class);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent1, 1);
            }
        });


        //geocalisation
            Button geocalisation = findViewById(R.id.Geo);
            final Intent intent2 = new Intent(this,ProcheGare.class);
            final locationListener location  = new locationListener(this);

        geocalisation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle =new Bundle();

                    //tester avec mes coordonnée
                   // Coordinate coordinate = new Coordinate("2.2315008","48.9684992");

                    Coordinate coordinate = new Coordinate(Double.toString(location.getLongitude()), Double.toString(location.getLatitude()));
                    bundle.putString("lat",coordinate.getLatitide().toString());
                    bundle.putString("log",coordinate.getLongtitude().toString());

                    intent2.putExtras(bundle);

                    System.out.println("------------Coordonnées de géocalisation----------");
                    System.out.println("lat " +location.getLatitude() + " long " + location.getLongitude());

                    startActivityForResult(intent2, 1);

                }

            });
        }


        private void DownloadFile () {
            String Url = "https://www.data.gouv.fr/fr/datasets/r/b83d51c3-6814-4fac-b617-c955d543ee85";
            DownloadManager.Request request1 = new DownloadManager.Request(Uri.parse(Url));
            request1.setDescription("Station data base");
            request1.setTitle("nameStops.csv");
            request1.setVisibleInDownloadsUi(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request1.allowScanningByMediaScanner();
                request1.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            }
            //télécharger fichier nameStops.csv
           // /sdcard/Android/data/com.upec.androidtemplate20192020/files/Downloads/
            request1.setDestinationInExternalFilesDir(getApplicationContext(), "Downloads/", "nameStops.csv");
            DownloadManager manager1 = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Objects.requireNonNull(manager1).enqueue(request1);
            if (DownloadManager.STATUS_SUCCESSFUL == 8) {
                System.out.println("succes ");
            } else System.out.println("Probleme dans téléchargement!");
        }

}



