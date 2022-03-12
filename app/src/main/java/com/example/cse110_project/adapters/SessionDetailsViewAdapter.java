package com.example.cse110_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse110_project.R;
import com.example.cse110_project.databases.session.SessionStudent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SessionDetailsViewAdapter extends RecyclerView.Adapter<SessionDetailsViewAdapter.ViewHolder> {
    private final List<SessionStudent> sessionStudentList;
    private View view;


    public SessionDetailsViewAdapter(List<SessionStudent> sessionStudentList) {
        super();
        this.sessionStudentList = sessionStudentList;
    }

    @NonNull
    @Override
    public SessionDetailsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.session_details_row, parent, false);
        this.view = view;
        return new SessionDetailsViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionDetailsViewAdapter.ViewHolder holder, int position) {
        SessionStudent oneStudent = sessionStudentList.get(position);
        holder.setSessionDetails(oneStudent);
        ImageView headShotView = view.findViewById(R.id.imageView2);

        try {
            Picasso.get().load(oneStudent.getUrl()).resize(150,150).into(headShotView);
        } catch (IllegalStateException ignored) {}
    }

    @Override
    public int getItemCount() { return this.sessionStudentList.size(); }

    public SessionStudent getSessionStudent(int position) {
        return sessionStudentList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView studentNameTextView;
        private final TextView numOfSharedCoursesTextView;
        private SessionStudent sessionStudent;

        ViewHolder(View itemView) {
            super(itemView);
            this.studentNameTextView = itemView.findViewById(R.id.session_details_name_row);
            this.numOfSharedCoursesTextView = itemView.findViewById(R.id.session_details_num_shared_row);
        }

        public void setSessionDetails(SessionStudent sessionStudent) {
            this.sessionStudent = sessionStudent;
            this.studentNameTextView.setText(this.sessionStudent.getSessionStudentName());
            this.numOfSharedCoursesTextView.setText(Integer.toString(this.sessionStudent.getNumOfSharedCourses()));
        }
    }
}
