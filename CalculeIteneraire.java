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
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

public class CalculeIteneraire extends AsyncTask<Void,Void, LinkedList<ListTravel>> {
    String addDepart, addArrival, dateTime;
    String lat, lon;
    Iteneraire iteneraire;

    LinkedList<Travel> t = new LinkedList<>();
    LinkedList<ListTravel> travels = new LinkedList<>();
    LinkedList<ListTravel> journey;


    public CalculeIteneraire(Iteneraire iteneraire, String addDepart, String addArrival){
        this.addDepart = addDepart ;
        this.addArrival = addArrival;
        this.iteneraire = iteneraire;
    }


    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public void setT(LinkedList<Travel> t) {
        this.t = t;
    }
    public void setTravels(LinkedList<ListTravel> travels) {
        this.travels = travels;
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



    @Override
    protected LinkedList<ListTravel> doInBackground(Void... voids) {
        try{
            //depart
            HttpsURLConnection depart = (HttpsURLConnection) new URL(" https://api-adresse.data.gouv.fr/search/?q=" + addDepart ).openConnection();
            depart.setRequestMethod("GET");
            depart.setRequestProperty("Authorization", "Basic ");
            depart.connect();

            //destination
            HttpsURLConnection destination = (HttpsURLConnection) new URL(" https://api-adresse.data.gouv.fr/search/?q=" + addArrival).openConnection();
            destination.setRequestMethod("GET");
            destination.setRequestProperty("Authorization", "Basic ");
            destination.connect();

            JSONObject departjson = new JSONObject(readInStream(depart.getInputStream()));
            JSONObject destinationjson = new JSONObject(readInStream(destination.getInputStream()));


            if(!addDepart.toUpperCase().equals("MA POSITION")) {
                System.out.println("rentrer dans condition ");
                System.out.println("taille de tableau coordinates "+ departjson.getJSONArray("features").getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates").length());
                lon = departjson.getJSONArray("features").getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates").getString(0);
                System.out.println("lon depart "+lon);
                lat = departjson.getJSONArray("features").getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates").getString(1);
                System.out.println("lat depart "+lat);
            }

            System.out.println("dateTime -----"+dateTime);
            HttpsURLConnection connection;
            connection = (HttpsURLConnection) new URL("https://api.navitia.io/v1/coverage/fr-idf/journeys?from=" + lon + ";" +
                    lat + "&to=" +
                    destinationjson.getJSONArray("features").getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates").getString(0) + ";" + destinationjson.getJSONArray("features").getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates").getString(1) +
                    "&datetime=" + dateTime +
                    "&count=10"
            ).openConnection();

            String myToken = "3d91a130-586b-461a-8add-a42414f1b226";
            String token = android.util.Base64.encodeToString (myToken.getBytes (), android.util.Base64.DEFAULT);
            connection.setRequestProperty("Authorization", "Basic " + token);
            connection.connect();

            JSONObject obj = new JSONObject(readInStream(connection.getInputStream()));

            JSONArray arr = obj.getJSONArray("journeys");

            LinkedList<String> duration = new LinkedList<>();

            LinkedList<JSONArray> section = new LinkedList<JSONArray>();

            for (int i = 0; i < arr.length(); i++) {
                section.add(arr.getJSONObject(i).getJSONArray("sections"));
                Travel travel = new Travel();
                travel.setDate_departure(arr.getJSONObject(i).getString("departure_date_time"));
                travel.setDate_arrival(arr.getJSONObject(i).getString("arrival_date_time"));
                Long totalSecs = arr.getJSONObject(i).getLong("duration");

                if(((long)(totalSecs % 3600) / 60) > 10){
                    duration.add("0"+(long) totalSecs / 3600+"h"+(long)(totalSecs % 3600) / 60+"min");
                } else {
                    duration.add("0"+(long) totalSecs / 3600+"h0"+(long)(totalSecs % 3600) / 60+"min");
                }

            }
            journey = new LinkedList<ListTravel>();

            for (int j = 0; j < section.size(); j++) {
                for (int i = 0; i < section.get(j).length(); i++) {

                    Travel travelV = new Travel();
                    if (!section.get(j).getJSONObject(i).getString("type").equals("waiting")) {
                        travelV.setFrom(section.get(j).getJSONObject(i).getJSONObject("from").getString("name"));
                        travelV.setTo(section.get(j).getJSONObject(i).getJSONObject("to").getString("name"));
                        travelV.setDate_departure(section.get(j).getJSONObject(i).getString("departure_date_time"));
                        travelV.setDate_arrival(section.get(j).getJSONObject(i).getString("arrival_date_time"));
                        if (section.get(j).getJSONObject(i).getString("type").equals("public_transport")) {
                            travelV.setDirection(section.get(j).getJSONObject(i).getJSONObject("display_informations").getString("direction"));
                            travelV.setLigne(section.get(j).getJSONObject(i).getJSONObject("display_informations").getString("commercial_mode") + ":" + section.get(j).getJSONObject(i).getJSONObject("display_informations").getString("code"));
                        } else {
                            if (section.get(j).getJSONObject(i).getString("type").equals("transfer"))
                                travelV.setMode(section.get(j).getJSONObject(i).getString("transfer_type"));
                                travelV.setLigne("vide");
                        }
                    } else {
                        travelV.setDate_departure(section.get(j).getJSONObject(i).getString("departure_date_time"));
                        travelV.setDate_arrival(section.get(j).getJSONObject(i).getString("arrival_date_time"));
                        travelV.setLigne(section.get(j).getJSONObject(i).getString("type") + ":vide");
                    }
                    travelV.setType(section.get(j).getJSONObject(i).getString("type"));
                    t.add(travelV);
                }

                journey.add(new ListTravel(t,duration.get(j)));
                t= new LinkedList<>();
            }

            System.out.println("teste fin duration "+journey.get(0).duration);
            return journey;

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(LinkedList<ListTravel> s) {
        super.onPostExecute(s);
    }
}
