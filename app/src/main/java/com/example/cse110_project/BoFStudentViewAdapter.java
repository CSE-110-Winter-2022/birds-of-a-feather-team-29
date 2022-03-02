package com.example.cse110_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse110_project.prevcourses.db.AppDatabase;
import com.example.cse110_project.prevcourses.db.BoFCourseDao;
import com.example.cse110_project.prevcourses.db.BoFStudent;
import com.example.cse110_project.prevcourses.db.Favorite;
import com.example.cse110_project.prevcourses.db.FavoriteDao;
import com.example.cse110_project.utilities.BoFStudentComparator;
import com.example.cse110_project.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class BoFStudentViewAdapter extends RecyclerView.Adapter<BoFStudentViewAdapter.ViewHolder> {
    private final List<BoFStudent> students;
    private BoFCourseDao cd;
    private static FavoriteDao favoriteD;
    private static BoFStudent oneStudent;
    public boolean isFav;
    public View view;
    private static final String TAG = "MyActivity";
    private static Favorite favStudent;
    public static long favId;




    public BoFStudentViewAdapter(List<BoFStudent> students, BoFCourseDao cd, FavoriteDao favoriteD) {
        super();
        this.students = students;
        this.cd = cd;
        this.favoriteD = favoriteD;
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
        this.view = view;




        return new ViewHolder(view, cd);
    }

    @Override
    public void onBindViewHolder(@NonNull BoFStudentViewAdapter.ViewHolder holder, int position) {
        oneStudent = students.get(position);
        holder.setStudent(oneStudent);
        isFav = false;
        List<Favorite> favList =  favoriteD.getAll();
        for(int i = 0; i < favList.size(); i++){
            String studentName = oneStudent.getName();
            if(favList.get(i).getName().compareTo(studentName) == 0){
                isFav = true;

            }
        }

        TextView starView = view.findViewById(R.id.star_view);
        if(isFav == true){

            starView.setTextColor(Color.parseColor("#FFD600"));
            Log.v(TAG, "student: " + oneStudent.getName() + " isFavorite is true");
        }
        else {
            starView.setTextColor(Color.parseColor("#9E9E9E"));
            Log.v(TAG, "student: " + oneStudent.getName() + " isFavorite is false");
        }


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

        private boolean isfavourite;

        ViewHolder(View itemView, BoFCourseDao cd) {
            super(itemView);
            this.studentNameView = itemView.findViewById(R.id.student_row_name);
            this.cd = cd;

            this.numOfSharedCoursesView = itemView.findViewById(R.id.num_shared_courses_textview);


            TextView starView = itemView.findViewById(R.id.star_view);
            starView.setOnClickListener((view) -> {
                if(!isfavourite){
                    // if the star is not already selected and you select it
                    isfavourite = true;
                    starView.setTextColor(Color.parseColor("#FFD600"));
                   // oneStudent.setFavorite(isfavourite);
                    favStudent = new Favorite(oneStudent.getName());
                    favId = favoriteD.insert(favStudent);

                    Log.v(TAG, "student: " + oneStudent.getName() + " isFavorite is now true");


                    // add the student to database
                }else{
                    // if the star is already selected and you unselect it
                    isfavourite = false;
                    starView.setTextColor(Color.parseColor("#9E9E9E"));
                    oneStudent.setFavorite(isfavourite);
                    favoriteD.deleteById(favId);
                    favStudent = null;
                    Log.v(TAG, "student: " + oneStudent.getName() + " isFavorite is now false");
                    // remove student from database
                }


            });
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
