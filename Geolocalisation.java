package com.upec.androidtemplate20192020;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

public class Geolocalisation extends AsyncTask<Void, Void, LinkedList<InformationProximite>> {
    Coordinate coord;
    String places_nearby="";
    double lat;
    double lon;

    LinkedList<InformationProximite> info=new LinkedList<>();

    public  Geolocalisation( Coordinate coord) {
        this.coord = coord;
    }


    @Override
    protected LinkedList<InformationProximite> doInBackground(Void... voids) {
        System.out.println("test Geol");
        try {
            //tester avec mes coordonné
           //HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.navitia.io/v1/coverage/fr-idf/coord/"+2.0507426+";"+48.9321777+"/places_nearby").openConnection();


           HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.navitia.io/v1/coverage/fr-idf/coord/"+coord.getLongtitude()+";"+coord.getLatitide()+"/places_nearby").openConnection();

            String myToken = "3d91a130-586b-461a-8add-a42414f1b226";
            String encoded = android.util.Base64.encodeToString (myToken.getBytes (), android.util.Base64.DEFAULT);

            connection.setRequestProperty("Authorization", "Basic " + encoded);

            JSONObject obj = new JSONObject(readInStream(connection.getInputStream()));
            JSONArray arr = obj.getJSONArray("places_nearby");
            for (int i = 0; i < arr.length(); i++) {
                //JSONObject pnrb = new JSONObject(arr.getJSONObject(i).getString("stop_areas"));
                if(arr.getJSONObject(i).getString("embedded_type").equals("stop_area")){
                    LinkedList<Travel> v = new LinkedList<>();
                    lat = arr.getJSONObject(i).getJSONObject("stop_area").getJSONObject("coord").getDouble("lat");
                    lon = arr.getJSONObject(i).getJSONObject("stop_area").getJSONObject("coord").getDouble("lon");
                    places_nearby += "Nom : "+ arr.getJSONObject(i).getString("name")+"\t ID : "+arr.getJSONObject(i).getString("id")+"\n";
                    connection = (HttpsURLConnection) new URL("https://api.navitia.io/v1/coverage/fr-idf/stop_areas/"+arr.getJSONObject(i).getString("id")+"/departures?").openConnection();
                    String my = "3d91a130-586b-461a-8add-a42414f1b226";
                    encoded = android.util.Base64.encodeToString (my.getBytes (), android.util.Base64.DEFAULT);

                    connection.setRequestProperty("Authorization", "Basic " + encoded);
                    JSONObject obj1 = new JSONObject(readInStream(connection.getInputStream()));
                    JSONArray tmp=obj1.getJSONArray("departures");
                    for (int j=0;j<tmp.length();j++){
                        Travel T1=new Travel();
                        T1.setDate_departure(tmp.getJSONObject(j).getJSONObject("display_informations").getString("label"));
                        T1.setDirection(tmp.getJSONObject(j).getJSONObject("display_informations").getString("direction"));
                        T1.setLigne(tmp.getJSONObject(j).getJSONObject("stop_date_time").getString("departure_date_time"));
                        v.add(T1);
                    }
                    info.add(new InformationProximite(arr.getJSONObject(i).getString("name"),arr.getJSONObject(i).getString("id"),lat,lon,arr.getJSONObject(i).getLong("distance"),v));
                }
            }
            System.out.println("Geolocalisation");
            System.out.println("premier info  "+info.get(0).getName());

        } catch (Exception e) {
            e.printStackTrace();

        }

        return info;
    }

    @Override
    protected void onPostExecute(LinkedList<InformationProximite> s) {
        super.onPostExecute(s);
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


