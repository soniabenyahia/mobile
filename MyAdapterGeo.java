package com.upec.androidtemplate20192020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

public class MyAdapterGeo extends BaseAdapter {
    LinkedList<InformationProximite> info =new LinkedList<>();
    private final Context context;

    public MyAdapterGeo(Context context, LinkedList<InformationProximite> info ){
        this.context = context;
        this.info = info ;
    }

    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public String getItem(int position) {
        return info.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //pour affichier les liex proximité
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view1 = inflater.inflate(R.layout.row_view1, parent, false);

        TextView name = (TextView) view1.findViewById(R.id.name);
        name.setText("Nom "+info.get(position).getName());

        TextView distance = (TextView) view1.findViewById(R.id.distance);
        distance.setText("Distance "+ info.get(position).getDistance());

        return view1;
    }
}
