package com.example.cse110_project.utilities;

import com.example.cse110_project.prevcourses.db.AppDatabase;
import com.example.cse110_project.prevcourses.db.DefaultCourse;
import com.example.cse110_project.prevcourses.db.DefaultStudent;

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
                        "Fall", "CSE 11"),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2017",
                        "Fall","CSE 12"),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2017",
                        "Fall","CSE 21"),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2019",
                        "Spring","CSE 100"),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2019",
                        "Spring","CSE 140"),
                new DefaultCourse(defStudentsList.get(0).getStudentId(), "2019",
                        "Spring","CSE 105"),

                new DefaultCourse(defStudentsList.get(1).getStudentId(), "2018",
                        "Winter","CSE 11"),
                new DefaultCourse(defStudentsList.get(1).getStudentId(), "2018",
                        "Winter","CSE 12"),
                new DefaultCourse(defStudentsList.get(1).getStudentId(), "2018",
                        "Winter","CSE 21"),
                new DefaultCourse(defStudentsList.get(1).getStudentId(), "2019",
                        "Fall", "CSE 100"),

                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2018",
                        "Spring","CSE 15L"),
                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                        "Summer Session I","CSE 191"),
                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                        "Fall","CSE 142"),
                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                        "Fall","CSE 112"),
                new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                        "Fall","CSE 167")
        };

        for (DefaultCourse defaultCourse : defaultCourseList) {
            db.DefaultCourseDao().insert(defaultCourse);
        }
    }

    private static void clearDefaultDatabase(AppDatabase db) {
        db.clearAllTables();
    }
}
