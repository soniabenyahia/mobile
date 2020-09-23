package com.upec.androidtemplate20192020;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

class ListTravelAdapter extends RecyclerView.Adapter<ListTravelAdapter.MyViewHolder>{
    private LinkedList<ListTravel> travel;
    private  Iteneraire iteneraire;

    public ListTravelAdapter(Iteneraire iteneraire) {
        super();
        this.iteneraire = iteneraire;

    }

    public void setTravel(LinkedList<ListTravel> travel) {
        this.travel = travel;
    }

    @Override
    public int getItemCount() {
        return travel.size();
    }

    @Override
    public ListTravelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_view3, parent, false);
        return new  ListTravelAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ListTravelAdapter.MyViewHolder holder, int position) {
        ListTravel depart= travel.get(position);
        holder.display(depart);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView imageViewA;
        TextView departTime;
        TextView arrivalTime;
        LinearLayout hor;
        TextView duree;

        private ListTravel travel1;

        public MyViewHolder(final View itemView) {
            super(itemView);
            imageView = new ImageView(itemView.getContext());
            imageViewA = new ImageView(itemView.getContext());
            departTime = itemView.findViewById(R.id.depart);
            arrivalTime = itemView.findViewById(R.id.arriver);
            hor = itemView.findViewById(R.id.horizontal);
            duree = itemView.findViewById(R.id.duree);
        }

        public void display(ListTravel depart) {
            travel1 = depart;

            hor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iteneraire.notifyItem(travel1);
                }
            });

            String[] dep =travel1.travels.get(0).getDate_departure().split("T");
            String heuredep = dep[1].substring(0,2).toString()+":"+dep[1].substring(2,4).toString();

            String []arrv = new String[20];
            String heureArr = "";
            departTime.setText(heuredep);
            duree.setText("Durée : " + depart.duration);
            for (int i = 0; i < travel1.travels.size(); i++) {

                imageView = new ImageView(itemView.getContext());
                imageViewA = new ImageView(itemView.getContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(60, 70);
                imageView.setLayoutParams(layoutParams);
                imageViewA.setLayoutParams(new LinearLayout.LayoutParams(60, 70));


                if (i + 1 == travel1.travels.size()) {
                    imageView.setBackgroundResource(image(travel1.travels.get(i)));
                    arrv = travel1.travels.get(i).getDate_arrival().split("T");
                    heureArr = arrv[1].substring(0,2).toString()+":"+arrv[1].substring(2,4).toString();
                    arrivalTime.setText(heureArr);
                    hor.addView(imageView);
                } else {

                    if (!travel1.travels.get(i).getType().toUpperCase().equals("WAITING")
                            && !travel1.travels.get(i).getType().toUpperCase().equals("TRANSFER")
                            && !travel1.travels.get(i).getType().toUpperCase().equals("STREET_NETWORK")
                            && !travel1.travels.get(i).getType().toUpperCase().equals("CROW_FLY")
                            && !travel1.equals("WALKING")){
                        imageView.setBackgroundResource(image(travel1.travels.get(i)));

                    }
                    if (travel1.travels.get(i).getType().toUpperCase().equals("WAITING")) {
                        imageView.setBackgroundResource(R.drawable.waiting1);
                    }
                    if (travel1.travels.get(i).getType().toUpperCase().equals("TRANSFER")) {
                        if (travel1.travels.get(i).getMode().toUpperCase().equals("WALKING")) {
                            imageView.setBackgroundResource(R.drawable.walk);
                        }
                    }
                    if (travel1.travels.get(i).getType().equals("street_network")) {
                        imageView.setBackgroundResource(R.drawable.walk);
                    }
                    if (travel1.travels.get(i).getType().equals("crow_fly")) {
                        imageView.setBackgroundResource(R.drawable.walk);
                    }


                    hor.addView(imageView);
                    hor.addView(imageViewA);
                    hor.invalidate();
                }
            }
        }

        private int image(Travel travel) {
            String[] line = travel.getLigne().split(":");
            System.out.println("linr size" + line.length);
            System.out.println("line " + travel.getLigne().toString());

            if (line.length > 1) {
                if (line[1].toUpperCase().equals("2")) return R.drawable.ligne2;
            else  if(line[1].toUpperCase().equals("1"))return R.drawable.ligne1;
            else  if(line[1].toUpperCase().equals("3")) return R.drawable.ligne3;
            else  if(line[1].toUpperCase().equals("4")) return  R.drawable.ligne4;
            else  if(line[1].toUpperCase().equals("5")) return R.drawable.ligne5;
            else  if(line[1].toUpperCase().equals("6")) return R.drawable.ligne6;
            else  if(line[1].toUpperCase().equals("7")) return R.drawable.ligne7;
            else  if(line[1].toUpperCase().equals("8")) return R.drawable.ligne8;
            else  if(line[1].toUpperCase().equals("9")) return R.drawable.ligne9;
            else  if(line[1].toUpperCase().equals("10")) return R.drawable.ligne10;
            else  if(line[1].toUpperCase().equals("11"))return R.drawable.ligne11;
            else  if(line[1].toUpperCase().equals("12")) return R.drawable.ligne12;
            else  if(line[1].toUpperCase().equals("13")) return R.drawable.ligne13;
            else  if(line[1].toUpperCase().equals("A")) return R.drawable.rera;
            else  if(line[1].toUpperCase().equals("B")) return R.drawable.rerb;
            else  if(line[1].toUpperCase().equals("C")) return R.drawable.rerc;
            else  if(line[1].toUpperCase().equals("D")) return R.drawable.rerd;
            else  if(line[1].toUpperCase().equals("E")) return R.drawable.rere;
            else  if(line[1].toUpperCase().equals("J")) return R.drawable.trainj;
            else return R.drawable.bus;
            }
           return 0;
        }

    }

}

