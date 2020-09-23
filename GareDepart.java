package com.upec.androidtemplate20192020;

import android.location.Location;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class GareDepart extends AsyncTask <Void, Void, String> {

   private String datetime;
   private Coordinate coordinate = new Coordinate ();
   private ArrayList<Travel> travel = new ArrayList<Travel> ();


    public GareDepart(){

    }

    public ArrayList<Travel> getTravel() {
        return travel;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }


    @Override
    protected String doInBackground(Void... voids) {

        try {
            // URL pour se connecter et récupérer tous les departures d'une gare (les coordonnées et datetime)
            HttpsURLConnection  httpsURLConnection = (HttpsURLConnection) new URL ("https://api.navitia.io/v1/coverage/fr-idf/coord/" + coordinate.getLongtitude () + ";" + coordinate.getLatitide () + "/departures?from_datetime="+datetime+"&count=20").openConnection();
            String myToken = "3d91a130-586b-461a-8add-a42414f1b226";
            String token = android.util.Base64.encodeToString (myToken.getBytes (), android.util.Base64.DEFAULT);
            httpsURLConnection.setRequestProperty("Authorization", "Basic " + token);
            httpsURLConnection.connect();

            JSONObject object = new JSONObject(readInStream(httpsURLConnection.getInputStream()));
            //récupérer le tableau de json (departures)
            JSONArray departures = object.getJSONArray("departures");
            //parcourir le tableau json et récupérer tous les données et créer l'objet de type Travel et l'ajouter à liste travel
            for (int i= 0 ; i < departures.length () ; i++){
                Travel travelA = new Travel(
                        departures.getJSONObject(i).getJSONObject("stop_date_time").getString("arrival_date_time"),
                        departures.getJSONObject(i).getJSONObject("stop_date_time").getString("departure_date_time"),
                        departures.getJSONObject(i).getJSONObject("stop_point").getString("label"),
                        departures.getJSONObject(i).getJSONObject("display_informations").getString("direction"),
                        departures.getJSONObject(i).getJSONObject("display_informations").getString("commercial_mode") + ":" + departures.getJSONObject(i).getJSONObject("display_informations").getString("code"),
                        departures.getJSONObject(i).getJSONObject("display_informations").getString("direction"),
                        departures.getJSONObject(i).getJSONArray("links").getJSONObject(2).getString("id")
                        );
                travel.add (travelA);
         }
        } catch (IOException e) {
            e.printStackTrace ();
        } catch (JSONException e) {
            e.printStackTrace ();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute (s);
    }

    private String readInStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        char[] buffer = new char[64];

        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        int n = 1;
        while (n > 0) {
            n = reader.read(buffer);
            if (n > 0) stringBuilder.append(buffer, 0, n);
        }
        return stringBuilder.toString();
    }

}