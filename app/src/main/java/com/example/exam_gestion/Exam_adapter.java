package com.example.exam_gestion;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Exam_adapter extends RecyclerView.Adapter<Exam_adapter.ExamViewHolder> {

    private ArrayList<Exam> mExams;
    Context ctx;

    public Exam_adapter(ArrayList<Exam> exams) {
       mExams = exams;
    }


    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_item, parent, false);
        ExamViewHolder evh = new ExamViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
        final Exam currentItem = mExams.get(position);
        holder.level.setText(currentItem.getLevel());
        holder.section.setText(currentItem.getSection());
        holder.groupe.setText(String.valueOf(currentItem.getGroupe()));
        holder.module.setText(currentItem.getModule());
        holder.room.setText(String.valueOf(currentItem.getRoom()));
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());
        holder.duration.setText(String.valueOf(currentItem.getDuration()));
        holder.mainitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),Admin_presence.class);
                i.putExtra("ID",currentItem.getId());
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mExams.size();
    }

    public class ExamViewHolder extends RecyclerView.ViewHolder {

        private TextView level;
        private TextView section;
        private TextView groupe;
        private TextView module;
        private TextView room;
        private TextView time;
        private TextView date;
        private TextView duration;
        private LinearLayout mainitem;


        public ExamViewHolder(@NonNull View itemView) {
            super(itemView);
            level =itemView.findViewById(R.id.levels);
           section =itemView.findViewById(R.id.sections);
           groupe =itemView.findViewById(R.id.groups);
           module =itemView.findViewById(R.id.modules);
            room =itemView.findViewById(R.id.rooms);
            date =itemView.findViewById(R.id.dates);
            time =itemView.findViewById(R.id.times);
            duration =itemView.findViewById(R.id.durations);
            mainitem = itemView.findViewById(R.id.itemmain);


        }
    }
}
