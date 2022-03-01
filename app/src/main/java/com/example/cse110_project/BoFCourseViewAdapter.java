package com.example.cse110_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.cse110_project.databases.bof.BoFCourse;
import com.example.cse110_project.utilities.Constants;

public class BoFCourseViewAdapter extends RecyclerView.Adapter<BoFCourseViewAdapter.ViewHolder> {
    private final List<BoFCourse> courses;

    public BoFCourseViewAdapter(List<BoFCourse> courses) {
        super();
        this.courses = courses;
    }

    @NonNull
    @Override
    public BoFCourseViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.bof_courses_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoFCourseViewAdapter.ViewHolder holder, int position) {
        holder.setCourse(courses.get(position));
    }

    @Override
    public int getItemCount() {
        return this.courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseTextView;
        private BoFCourse course;

        ViewHolder(View itemView) {
            super(itemView);
            this.courseTextView = itemView.findViewById(R.id.courses_row_text);
        }

        public void setCourse(BoFCourse course) {
            this.course = course;
            this.courseTextView.setText(course.course + Constants.SPACE + course.getCourseNum() +
                    Constants.LEFT_PARENTHESIS + course.getQuarter() + Constants.COMMA_SPACE +
                    course.getYear() + Constants.RIGHT_PARENTHESIS);
        }
    }
}
