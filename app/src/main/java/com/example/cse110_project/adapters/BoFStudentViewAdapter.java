package com.example.cse110_project.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse110_project.R;
import com.example.cse110_project.StudentDetailActivity;
import com.example.cse110_project.databases.bof.BoFCourseDao;
import com.example.cse110_project.databases.bof.BoFStudent;
import com.example.cse110_project.databases.favorite.Favorite;
import com.example.cse110_project.databases.favorite.FavoriteDao;
import com.example.cse110_project.utilities.comparators.BoFComparator;
import com.example.cse110_project.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class BoFStudentViewAdapter extends RecyclerView.Adapter<BoFStudentViewAdapter.ViewHolder> {
    private final List<BoFStudent> students;
    private BoFCourseDao cd;
    private static FavoriteDao favoriteD;
    private static BoFStudent oneStudent;
    public static boolean isFav;
    public View view;
    private static final String TAG = "MyActivity";
    private static Favorite favStudent;
    public static long favId;

    public BoFStudentViewAdapter(List<BoFStudent> students, BoFCourseDao cd, FavoriteDao favoriteD,
                                 BoFComparator comparator) {
        super();
        this.students = students;
        this.cd = cd;
        this.favoriteD = favoriteD;
        students.sort(comparator);
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
//        holder.setStudent(students.get(position));
        oneStudent = students.get(position);

        // ---
        if (oneStudent.getIsWaving()) {
            TextView handWave = view.findViewById(R.id.hand_view);
            handWave.setVisibility(View.VISIBLE);
        }

        Log.v(TAG, "student: " + oneStudent.getName());
        holder.setStudent(oneStudent);
        isFav = oneStudent.getFavorite();

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
        }
        else {
            starView.setTextColor(Color.parseColor("#9E9E9E"));
        }

        Log.v(TAG, "student: " + oneStudent.getName() + " isFavorite is " + isFav);
    }

    @Override
    public int getItemCount() {
        return this.students.size();
    }

    public BoFStudent getBoFStudent(int position) {
        return students.get(position);
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

            TextView starView = itemView.findViewById(R.id.star_view);
            starView.setOnClickListener((view) -> {


                String name = this.studentNameView.getText().toString();
                List<Favorite> favList =  favoriteD.getAll();
                List<String> nameList = new ArrayList<>();

                // Get an array list of favorite student names
                for(int i = 0; i < favList.size(); i++) {
                    nameList.add(favList.get(i).getName());
                }

                // If the user is not in the favorite list yet
                if(!nameList.contains(name)){
                    isFav = true;
                    oneStudent.setFavorite(true);
                    starView.setTextColor(Color.parseColor("#FFD600"));

                    // Create a Favorite student object and insert
                    favStudent = new Favorite(name);
                    favId = favoriteD.insert(favStudent);

                    Log.v(TAG, "student: " + name + " isFavorite is now true");
                    Toast.makeText(itemView.getContext(), "Saved to Favorites", Toast.LENGTH_SHORT).show();
                }
                else{
                    // if the star is already selected and you unselect it
                    isFav = false;
                    starView.setTextColor(Color.parseColor("#9E9E9E"));
                    oneStudent.setFavorite(false);

                    int index = 0;
                    for(int i = 0; i < favList.size(); i++){
                        if(favList.get(i).getName().compareTo(name) == 0){
                            index = i;
                            break;
                        }
                        index = -1;
                    }

                    if(index >= 0) {
                        favoriteD.delete(favoriteD.getAll().get(index));
                        Log.v(TAG, "done, deleted!");
                    }

                    favStudent = null;
                    Log.v(TAG, "student: " + name + " isFavorite is now false");
                    Toast.makeText(itemView.getContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();
                }
//                Log.v(TAG, "student: " + oneStudent.getName() + " isFavorite is now " + oneStudent.getFavorite());
            });
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
