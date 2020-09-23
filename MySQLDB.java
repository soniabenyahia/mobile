package com.upec.androidtemplate20192020;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "mySQL.db" ;
    public static final int DB_VERSION = 1 ;
    private static MySQLDB instance ;

    public MySQLDB(Context context){
        super(context,DB_NAME,null, DB_VERSION);
    }

    public static synchronized  MySQLDB getInstance(Context context) {
        if(instance == null) return new MySQLDB (context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Creation de le base de donnée ( table gare) avec la requete (GareManager.CREATE_TB_GARE)
        sqLiteDatabase.execSQL (GareManager.CREATE_TB_GARE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Mise à jours de la base de données
        onCreate (db);
    }
}
