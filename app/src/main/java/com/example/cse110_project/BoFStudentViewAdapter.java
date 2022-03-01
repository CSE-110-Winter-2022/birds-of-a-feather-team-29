package com.example.cse110_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse110_project.databases.bof.BoFCourseDao;
import com.example.cse110_project.databases.bof.BoFStudent;
import com.example.cse110_project.utilities.BoFStudentComparator;
import com.example.cse110_project.utilities.Constants;

import java.util.List;

public class BoFStudentViewAdapter extends RecyclerView.Adapter<BoFStudentViewAdapter.ViewHolder> {
    private final List<BoFStudent> students;
    private BoFCourseDao cd;

    public BoFStudentViewAdapter(List<BoFStudent> students, BoFCourseDao cd) {
        super();
        this.students = students;
        this.cd = cd;
        students.sort(new BoFStudentComparator(cd));
    }

    public void clear(){
        int size = students.size();
        this.students.clear();
        notifyItemRangeRemoved(0, size);
    }

    @NonNull
    @Override
    public BoFStudentViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.bof_student_row, parent, false);

        return new ViewHolder(view, cd);
    }

    @Override
    public void onBindViewHolder(@NonNull BoFStudentViewAdapter.ViewHolder holder, int position) {
        holder.setStudent(students.get(position));
    }

    @Override
    public int getItemCount() {
        return this.students.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView studentNameView;
        private final TextView numOfSharedCoursesView;
        private BoFStudent student;
        private BoFCourseDao cd;

        ViewHolder(View itemView, BoFCourseDao cd) {
            super(itemView);
            this.studentNameView = itemView.findViewById(R.id.student_row_name);
            this.cd = cd;
            this.numOfSharedCoursesView = itemView.findViewById(R.id.num_shared_courses_textview);
            itemView.setOnClickListener(this);
        }

        public void setStudent(BoFStudent student) {
            this.student = student;
            this.studentNameView.setText(student.getName());
            this.numOfSharedCoursesView.setText(Integer.toString(cd.getForStudent(student.getStudentId()).size()));
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, StudentDetailActivity.class);
            intent.putExtra(Constants.BOF_STUDENT_ID, this.student.getStudentId());

            context.startActivity(intent);
        }
    }
}
