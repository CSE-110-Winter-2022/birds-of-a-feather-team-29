package com.example.cse110_project;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse110_project.databases.favorite.Favorite;

import java.util.List;

public class FavStudentAdapter extends RecyclerView.Adapter<FavStudentAdapter.ViewHolder>{

    private final List<Favorite> students;

    public FavStudentAdapter(List<Favorite> students) {
        super();
        this.students = students;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fav_student_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setStudent(students.get(position));

    }

    @Override
    public int getItemCount() {
        return this.students.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView favTextView;
        private Favorite student;


        public ViewHolder(View itemView) {
            super(itemView);
            this.favTextView = itemView.findViewById(R.id.fav_row_text);
        }

        public void setStudent(Favorite student) {
            this.student = student;
            this.favTextView.setText(student.getName());
        }
    }
}
