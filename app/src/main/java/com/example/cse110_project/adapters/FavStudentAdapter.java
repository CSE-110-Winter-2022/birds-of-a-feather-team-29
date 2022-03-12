package com.example.cse110_project.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse110_project.R;
import com.example.cse110_project.databases.favorite.Favorite;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavStudentAdapter extends RecyclerView.Adapter<FavStudentAdapter.ViewHolder>{

    private final List<Favorite> students;
    private View view;

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
        this.view = view;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorite oneStudent = students.get(position);
        holder.setStudent(oneStudent);

        ImageView headShotView = view.findViewById(R.id.imageView);
        Picasso.get().load(oneStudent.getUrl()).resize(150,150).into(headShotView);
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
