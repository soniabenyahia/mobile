package com.upec.androidtemplate20192020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapterDepart extends BaseAdapter {
    private final Context context;
    ArrayList<Travel> travels = new ArrayList<Travel>();


    public MyAdapterDepart(Context context,ArrayList<Travel> travels){
        this.travels = travels ;
        this.context = context;
    }

    @Override
    public int getCount() {
        return travels.size();
    }

    @Override
    public  String getItem(int position) {
        return travels.get(position).getLigne();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //pour affichier les lignes
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.row_view,null);

        ImageView image =  view1.findViewById(R.id.image);
        TextView textDirection = (TextView) view1.findViewById(R.id.textDirection);
        TextView textDate_depart = (TextView) view1.findViewById(R.id.textDateDepart);

        String[] depart = travels.get(position).getDate_departure().split("T");
        String heuredep = depart[1].substring(0,2).toString()+":"+depart[1].substring(2,4).toString();

        System.out.println("Ligne" +travels.get(position).getLigne());

        String [] line= travels.get(position).getLigne().split(":");

        if(line.length > 1) {
            if (line[1].toUpperCase().equals("2")) image.setImageResource(R.drawable.ligne2);
            else if (line[1].toUpperCase().equals("1")) image.setImageResource(R.drawable.ligne1);
            else if (line[1].toUpperCase().equals("3")) image.setImageResource(R.drawable.ligne3);
            else if (line[1].toUpperCase().equals("4")) image.setImageResource(R.drawable.ligne4);
            else if (line[1].toUpperCase().equals("5")) image.setImageResource(R.drawable.ligne5);
            else if (line[1].toUpperCase().equals("6")) image.setImageResource(R.drawable.ligne6);
            else if (line[1].toUpperCase().equals("7")) image.setImageResource(R.drawable.ligne7);
            else if (line[1].toUpperCase().equals("8")) image.setImageResource(R.drawable.ligne8);
            else if (line[1].toUpperCase().equals("9")) image.setImageResource(R.drawable.ligne9);
            else if (line[1].toUpperCase().equals("10")) image.setImageResource(R.drawable.ligne10);
            else if (line[1].toUpperCase().equals("11")) image.setImageResource(R.drawable.ligne11);
            else if (line[1].toUpperCase().equals("12")) image.setImageResource(R.drawable.ligne12);
            else if (line[1].toUpperCase().equals("13")) image.setImageResource(R.drawable.ligne13);
            else if (line[1].toUpperCase().equals("A")) image.setImageResource(R.drawable.rera);
            else if (line[1].toUpperCase().equals("B")) image.setImageResource(R.drawable.rerb);
            else if (line[1].toUpperCase().equals("C")) image.setImageResource(R.drawable.rerc);
            else if (line[1].toUpperCase().equals("D")) image.setImageResource(R.drawable.rerd);
            else if (line[1].toUpperCase().equals("E")) image.setImageResource(R.drawable.rere);
            else if (line[1].toUpperCase().equals("J")) image.setImageResource(R.drawable.trainj);
            else image.setImageResource(R.drawable.bus);
        }


        textDirection.setText("direction: "+travels.get(position).getDirection());
        textDate_depart.setText("depart: "+ heuredep );


            return view1;
        }

}
