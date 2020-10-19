package com.example.exam_gestion;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Etudaint_adapter extends RecyclerView.Adapter<Etudaint_adapter.Etudaint_viewholder> {



    private Context mCtx;
    private ArrayList<etudaint> EtudaintList;

    public Etudaint_adapter(Context ctx, ArrayList<etudaint> productList) {
        mCtx = ctx;
        this.EtudaintList = productList;
    }

    @NonNull
    @Override
    public Etudaint_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.prof_item, parent, false);
        return new Etudaint_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Etudaint_viewholder holder, int position) {
        holder.bind(EtudaintList.get(position));
    }

    @Override
    public int getItemCount() {
        return EtudaintList.size();
    }

    class Etudaint_viewholder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView presence;


        public Etudaint_viewholder(@NonNull View itemView) {
            super(itemView);
          name = itemView.findViewById(R.id.profitem);
         presence = itemView.findViewById(R.id.imageViewselected);

        }
        void bind(final etudaint etudaint ){
            if (etudaint.isPresence()==1){
           etudaint.setChecked(true);

            }

            else {
               etudaint.setChecked(false);
            }
          presence.setVisibility(etudaint.isChecked() ? View.VISIBLE : View.GONE);
           name.setText(etudaint.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 etudaint.setChecked(!etudaint.isChecked());
                    presence.setVisibility(etudaint.isChecked() ? View.VISIBLE  : View.GONE);

                }
            });
        }
    }

    public ArrayList<etudaint> getAll() {
        return EtudaintList;
    }
    public ArrayList<etudaint> getSelected() {

        ArrayList<etudaint> selected = new ArrayList<>();
        ArrayList<etudaint> notselected = new ArrayList<>();
        for (int i = 0; i < EtudaintList.size(); i++) {
            if (EtudaintList.get(i).isChecked()) {
                selected.add(EtudaintList.get(i));


            }
        }
      return  selected ;


    }
    public ArrayList<etudaint> getnotSelected() {


        ArrayList<etudaint> notselected = new ArrayList<>();
        for (int i = 0; i < EtudaintList.size(); i++) {
            if (!EtudaintList.get(i).isChecked()) {
                notselected.add(EtudaintList.get(i));
            }
        }
        return  notselected ;
    }
}
