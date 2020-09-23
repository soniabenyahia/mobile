package com.upec.androidtemplate20192020;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class MyAdapterIteneraire extends RecyclerView.Adapter<MyAdapterIteneraire.MyViewHolder> {
    private LinkedList<Travel> travel;

    public void setTravel(LinkedList<Travel> travel) {
        this.travel = travel;
    }
    @Override
    public int getItemCount() {
        return travel.size();
    }


    @Override
    public MyAdapterIteneraire.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_view2, parent, false);
        return new MyAdapterIteneraire.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyAdapterIteneraire.MyViewHolder holder, int position) {
        Travel dep= travel.get(position);
        holder.display(dep);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView;
        TextView departTime;
        TextView arrivalTime;
        TextView duration;
        TextView from;
        TextView to;
        TextView ligne;

        private Travel travel1;

        public MyViewHolder(final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.fleche);
            departTime = itemView.findViewById(R.id.departTime);
            arrivalTime = itemView.findViewById(R.id.arrivalTime);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.to);
            ligne = itemView.findViewById(R.id.ligneIt);
            duration=itemView.findViewById(R.id.duree);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

        public void display(Travel depart) {
            travel1 =depart;
            imageView.setBackgroundResource(image( travel1));
            String[] dep = travel1.getDate_departure().split("T");
            String heuredep = dep[1].substring(0,2).toString()+":"+dep[1].substring(2,4).toString();
            String[] arriv = travel1.getDate_arrival().split("T");
            String heureArriv = arriv[1].substring(0,2).toString()+":"+arriv[1].substring(2,4).toString();

            departTime.setText( "depart :" + heuredep);
            from.setText("from : "+travel1.getFrom());
            to.setText("to :"+travel1.getTo());
            arrivalTime.setText("Arriver :" + heureArriv);
            if(!travel1.getType().toUpperCase().equals("WAITING")
                    &&!travel1.getType().toUpperCase().equals("TRANSFER")
                    &&!travel1.equals("WALKING"))
                ligne.setText(travel1.getLigne()+" vers "+travel1.getDirection());
            if(travel1.getType().toUpperCase().equals("WAITING")){
                imageView.setBackgroundResource(R.drawable.waiting1);
                from.setText("");
                to.setText("");
            }
            if(travel1.getType().toUpperCase().equals("TRANSFER"))
                if(travel1.getMode().toUpperCase().equals("WALKING")){
                    imageView.setBackgroundResource(R.drawable.walk);
                    ligne.setText("");
                }
            if(travel1.getType().equals("street_network")){
                ligne.setText("");
                imageView.setBackgroundResource(R.drawable.walk);
            }
            if(travel1.getType().equals("crow_fly")){
                ligne.setText("");
                imageView.setBackgroundResource(R.drawable.walk);
            }

            //duration.setText("durré: "+travel1.getDuration());
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
