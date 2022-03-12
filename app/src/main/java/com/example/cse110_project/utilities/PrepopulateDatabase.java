package com.example.cse110_project.utilities;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.def.DefaultCourse;
import com.example.cse110_project.databases.def.DefaultStudent;

import java.util.List;

public class PrepopulateDatabase {
    public static void populateDefaultDatabase(AppDatabase db) {
        clearDefaultDatabase(db);

        DefaultStudent[] defaultStudentList = {
                new DefaultStudent("Steel", Constants.DEFAULT_PIC_LINK),
                new DefaultStudent("Sandy", Constants.DEFAULT_PIC_LINK),
                new DefaultStudent("Aiko", Constants.DEFAULT_PIC_LINK)
        };

        for (DefaultStudent dS : defaultStudentList) {
            db.DefaultStudentDao().insert(dS);
        }

        List<DefaultStudent> defStudentsList = db.DefaultStudentDao().getAll();

        DefaultCourse[] defaultCourseList = {
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2017",
                        "Fall", "CSE", "11", "Tiny (1-40)",false),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2017",
                        "Fall","CSE", "12", "Tiny (1-40)",false),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2017",
                        "Fall","CSE", "21","Tiny (1-40)", false),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2019",
                        "Spring","CSE","100","Tiny (1-40)", false),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2019",
                        "Spring","CSE","140","Tiny (1-40)", false),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2019",
                        "Spring","CSE","105","Tiny (1-40)", false),

                new DefaultCourse(defStudentsList.get(1).getStudentId(), "2018",
                        "Winter","CSE","11","Large (150-250)", false),
                new DefaultCourse(defStudentsList.get(1).getStudentId(), "2018",
                        "Winter","CSE","12","Large (150-250)",false),
                new DefaultCourse(defStudentsList.get(1).getStudentId(), "2018",
                        "Winter","CSE","21","Large (150-250)", false),
                new DefaultCourse(defStudentsList.get(1).getStudentId(), "2019",
                        "Fall", "CSE","100","Large (150-250)", false),

                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2018",
                        "Spring","CSE","15L","Huge (250-400)", false),
                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                        "Summer Session I","CSE","191","Huge (250-400)", false),
                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                        "Fall","CSE","142","Huge (250-400)", false),
                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                        "Fall","CSE","112","Huge (250-400)", false),
                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                        "Fall","CSE","167", "Huge (250-400)",false)
        };

        for (DefaultCourse defaultCourse : defaultCourseList) {
            db.DefaultCourseDao().insert(defaultCourse);
        }
    }

    private static void clearDefaultDatabase(AppDatabase db) {
//        db.DefaultStudentDao().delete();
//        db.DefaultCourseDao().delete();
        db.BoFStudentDao().delete();
        db.BoFCourseDao().delete();
    }
}
