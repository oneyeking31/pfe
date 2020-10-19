package com.example.exam_gestion;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Prsence_Adapter extends RecyclerView.Adapter<Prsence_Adapter.Presence__Viewholder> {
    private Context mCtx;
    private ArrayList<Presence> mPresences;

    public Prsence_Adapter(Context ctx, ArrayList<Presence> presences) {
        mCtx = ctx;
        this.mPresences = presences;
    }
    public void setEprofs(ArrayList<Presence> presenceArrayList) {
        this.mPresences = new ArrayList<>();
        this.mPresences = presenceArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Presence__Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.presence_item, parent, false);
        return new Presence__Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Presence__Viewholder holder, int position) {
        final Presence currentItem = mPresences.get(position);
        holder.name.setText(currentItem.getName());
        if (currentItem.isPresence()==1){
            holder.presence.setText("present");
            holder.presence.setTextColor(Color.GREEN);
        }
        else {
            holder.presence.setText("absent");
            holder.presence.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() { return mPresences.size();}

    public class Presence__Viewholder extends RecyclerView.ViewHolder {

        TextView name;
        TextView presence;

        public Presence__Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameitem);
            presence = itemView.findViewById(R.id.presentitem);


        }
    }
}
