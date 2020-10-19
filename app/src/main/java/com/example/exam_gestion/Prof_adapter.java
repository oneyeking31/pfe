package com.example.exam_gestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Prof_adapter extends RecyclerView.Adapter<Prof_adapter.Prof_viewholder> {


    private ArrayList<prof> proflist;
    private Context mCtx;




    public Prof_adapter(Context ctx, ArrayList<prof> productList) {
        this.mCtx = ctx;
        this.proflist = productList;
    }
    public void setEprofs(ArrayList<prof> employees) {
        this.proflist = new ArrayList<>();
        this.proflist = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Prof_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.prof_item, parent, false);
        return new Prof_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Prof_viewholder holder, final int position) {
      holder.bind(proflist.get(position));

    }

    @Override
    public int getItemCount() {
        return proflist.size();
    }


    public  class Prof_viewholder extends RecyclerView.ViewHolder {
        TextView profname;
        ImageView itemselected;

        public Prof_viewholder(@NonNull View itemView) {
            super(itemView);
            profname = itemView.findViewById(R.id.profitem);
            itemselected = itemView.findViewById(R.id.imageViewselected);

        }
        void bind(final prof prof){
         itemselected.setVisibility(prof.isChecked() ? View.VISIBLE : View.GONE);
            profname.setText(prof.getName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               prof.setChecked(!prof.isChecked());
                itemselected.setVisibility(prof.isChecked() ? View.VISIBLE  : View.GONE);

            }
        });
        }

        }

    public ArrayList<prof> getAll() {
        return proflist;
    }
    public ArrayList<prof> getSelected() {

        ArrayList<prof> selected = new ArrayList<>();
            for (int i = 0; i < proflist.size(); i++) {
                if (proflist.get(i).isChecked()) {
                    selected.add(proflist.get(i));
                }
            }

            return selected;
        }


}
