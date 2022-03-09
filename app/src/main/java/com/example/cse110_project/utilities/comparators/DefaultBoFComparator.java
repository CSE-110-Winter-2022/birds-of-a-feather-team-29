package com.example.cse110_project.utilities.comparators;

import com.example.cse110_project.databases.bof.BoFCourseDao;
import com.example.cse110_project.databases.bof.BoFStudent;

import java.util.Comparator;

public class DefaultBoFComparator implements Comparator<BoFStudent> {

    private BoFCourseDao cd;

    public DefaultBoFComparator(BoFCourseDao cd) { this.cd = cd; }

    @Override
    public int compare(BoFStudent s1, BoFStudent s2) {
        int s1NumOfSharedCourses = cd.getForStudent(s1.getStudentId()).size();
        int s2NumOfSharedCourses = cd.getForStudent(s2.getStudentId()).size();
        return Integer.compare(s2NumOfSharedCourses, s1NumOfSharedCourses);
    }

}
