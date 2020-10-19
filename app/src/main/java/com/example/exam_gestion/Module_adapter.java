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

public class Module_adapter extends RecyclerView.Adapter<Module_adapter.Module_ViewHolder> {


    private Context context;
    private ArrayList<Module> Modules;
    private int checkedPosition = 0;

    public Module_adapter(Context context, ArrayList<Module> modules) {
        this.context = context;
        Modules = modules;
    }
    public void setModules(ArrayList<Module> Modules) {
        this.Modules = new ArrayList<>();
        this.Modules = Modules;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Module_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.module_item, parent, false);
        return new Module_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Module_ViewHolder holder, int position) {
        holder.bind(Modules.get(position));
    }

    @Override
    public int getItemCount() {
         return Modules.size();
    }

    public class Module_ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        public Module_ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void bind(final Module module) {
            if (checkedPosition == -1) {
                imageView.setVisibility(View.GONE);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE);
                }
            }
            textView.setText(module.getName());

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
    public Module getSelected() {
        if (checkedPosition != -1) {
            return Modules.get(checkedPosition);
        }
        return null;
    }
}
