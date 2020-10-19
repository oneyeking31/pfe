package com.example.exam_gestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Respo_adapter extends RecyclerView.Adapter<Respo_adapter.Respo_ViewHolder> {


    private Context context;
    private ArrayList<Responsable> Responsables;
    private int checkedPosition = 0;
    public Respo_adapter(Context context, ArrayList<Responsable> Responsables) {
        this.context = context;
        this.Responsables = Responsables;
    }

    public void setResponsables(ArrayList<Responsable> Responsables) {
        this.Responsables = new ArrayList<>();
        this.Responsables = Responsables;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Respo_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.module_item, parent, false);
        return new Respo_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Respo_ViewHolder holder, int position) {
        holder.bind(Responsables.get(position));
    }

    @Override
    public int getItemCount() {
        return Responsables.size();
    }

    public class Respo_ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        public Respo_ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }
        void bind(final Responsable responsable) {
            if (checkedPosition == -1) {
                imageView.setVisibility(View.GONE);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE);
                }
            }
            textView.setText(responsable.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageView.setVisibility(View.VISIBLE);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }
    public Responsable getSelected() {
        if (checkedPosition != -1) {
            return Responsables.get(checkedPosition);
        }
        return null;
    }
}
