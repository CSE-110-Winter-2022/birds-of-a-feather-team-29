package com.example.cse110_project.utilities;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.def.DefaultCourse;
import com.example.cse110_project.databases.def.DefaultStudent;

import java.util.List;

public class PrepopulateDatabase {
    public static void populateDefaultDatabase(AppDatabase db) {
        clearDefaultDatabase(db);

        DefaultStudent[] defaultStudentList = {
                new DefaultStudent("Steel"),
                new DefaultStudent("Sandy"),
                new DefaultStudent("Aiko")
        };

        for (DefaultStudent dS : defaultStudentList) {
            db.DefaultStudentDao().insert(dS);
        }

        List<DefaultStudent> defStudentsList = db.DefaultStudentDao().getAll();

        DefaultCourse[] defaultCourseList = {
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2017",
                        "Fall","Tiny (1-40)", "CSE", "11", false),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2017",
                        "Fall","Tiny (1-40)","CSE", "12", false),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2017",
                        "Fall","Tiny (1-40)","CSE", "21", false),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2019",
                        "Spring","Tiny (1-40)","CSE","100", false),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2019",
                        "Spring","Tiny (1-40)","CSE","140", false),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2019",
                        "Spring","Tiny (1-40)","CSE","105", false),

                new DefaultCourse(defStudentsList.get(1).getStudentId(), "2018",
                        "Winter","Large (150-250)","CSE","11", false),
                new DefaultCourse(defStudentsList.get(1).getStudentId(), "2018",
                        "Winter","Large (150-250)","CSE","12", false),
                new DefaultCourse(defStudentsList.get(1).getStudentId(), "2018",
                        "Winter","Large (150-250)","CSE","21", false),
                new DefaultCourse(defStudentsList.get(1).getStudentId(), "2019",
                        "Fall","Large (150-250)", "CSE","100", false),

                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2018",
                        "Spring","Huge (250-400)","CSE","15L", false),
                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                        "Summer Session I","Huge (250-400)","CSE","191", false),
                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                        "Fall","Huge (250-400)","CSE","142", false),
                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                        "Fall","Huge (250-400)","CSE","112", false),
                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                        "Fall","Huge (250-400)","CSE","167", false)
        };

        for (DefaultCourse defaultCourse : defaultCourseList) {
            db.DefaultCourseDao().insert(defaultCourse);
        }
    }

    private static void clearDefaultDatabase(AppDatabase db) {
        db.DefaultStudentDao().delete();
        db.DefaultCourseDao().delete();
        db.BoFStudentDao().delete();
        db.BoFCourseDao().delete();
    }
}
