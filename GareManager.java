package com.upec.androidtemplate20192020;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GareManager {

    public static final String TABLE_NAME = "gare";
    public static final String ID_Gare = "id_gare";
    public static final String NOM_Gare = "nom_gare";
    public static final String LONGT_Gare = "longt_gare";
    public static final String LAT_Gare = "lat_gare";
    public static String CREATE_TB_GARE ="CREATE TABLE" +" " +TABLE_NAME+
            " (" +
            " "+ ID_Gare+" INTEGER PRIMARY KEY AUTOINCREMENT" +
            " , "+NOM_Gare+" TEXT"+
            " , " +LONGT_Gare+" TEXT"+
            " , "+LAT_Gare+" TEXT" +
            ");";

    private MySQLDB  myBDS ;
    private SQLiteDatabase db ;

    public GareManager(Context context){
        myBDS = MySQLDB.getInstance(context);
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void OpenBD(){
        //ouvrire la table pour lecture et écriture
        db = myBDS.getWritableDatabase ();

    }

    public void CloseBD(){
        //Fermer l'accées à la base de données
        db.close ();
    }


    public Coordinate getCoord(String name) throws IOException {
        //récupérer les coordonnées d'une gare
        Coordinate coordinate = new Coordinate();
        try {
            Cursor c = db.rawQuery("SELECT " + LAT_Gare + "," + LONGT_Gare + " FROM " + TABLE_NAME + " WHERE " + NOM_Gare + " = '" + name + "'", null);
            if (c.moveToFirst()) {
                do {
                    coordinate.setLatitide(c.getString(c.getColumnIndex(LAT_Gare)));
                    coordinate.setLongtitude(c.getString(c.getColumnIndex(LONGT_Gare)));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coordinate;
    }

    public void initialiser_BD(){
        Cursor cursor = db.rawQuery("SELECT id_gare FROM gare WHERE id_gare='1' ",null);
        if(cursor.getCount()==0) {
            try {
                //lire le fichier
                FileReader file = new FileReader("sdcard/Android/data/com.upec.androidtemplate20192020/files/Downloads/nameStops.csv");
                System.out.println("-------- read file success--------");
                BufferedReader buffer = new BufferedReader(file);
                ContentValues contentValues = new ContentValues();

                String ligne = "";

                db.beginTransaction();
                while ((ligne = buffer.readLine()) != null) {
                    String[] str = ligne.split(";");
                    String geopoint = str[0];
                    String[] coordinate = geopoint.split(",");

                    String latitude = coordinate[0];
                    String longitude ="";
                    if(coordinate.length > 1){
                        longitude = coordinate[1];
                    }
                    String Name = str[5];
                    contentValues.put("nom_gare", Name);
                    contentValues.put("longt_gare", longitude);
                    contentValues.put("lat_gare", latitude);
                    db.insert("gare", null, contentValues);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public List<Gare> getAll() {
        List<Gare> recordsList = new ArrayList<>();

        String sql = "";
        sql += "SELECT * FROM " + TABLE_NAME;
        sql += " ORDER BY " + ID_Gare + " DESC";

        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String gareName = cursor.getString(cursor.getColumnIndex(NOM_Gare));
                String gareLongt = cursor .getString(cursor.getColumnIndex(LONGT_Gare));
                String gareLat = cursor .getString(cursor.getColumnIndex(LAT_Gare));

                Gare gare = new Gare();
                gare.setLatitude (gareLat);
                gare.setLongitute (gareLongt);
                gare.setName(gareName);

                recordsList.add(gare);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return recordsList;
    }


    public List<Gare> read(String recherche) {

        List<Gare> List = new ArrayList<Gare> ();

        String sql = "";
        sql += "SELECT * FROM " + TABLE_NAME;
        sql += " WHERE " + NOM_Gare + " LIKE '%" + recherche + "%'";
        sql += " ORDER BY " + ID_Gare + " DESC";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String gareName = cursor.getString(cursor.getColumnIndex(NOM_Gare));
                String gareLongitude = cursor .getString(cursor.getColumnIndex(LONGT_Gare));
                String gareLatitude = cursor .getString(cursor.getColumnIndex(LAT_Gare));

                Gare gare = new Gare();
                gare.setLongitute (gareLongitude);
                gare.setLatitude (gareLatitude);
                gare.setName(gareName);

                List.add(gare);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return List;
    }

}
