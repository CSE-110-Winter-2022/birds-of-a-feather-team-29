package com.example.cse110_project.utilities;

import com.example.cse110_project.databases.bof.BoFCourseDao;
import com.example.cse110_project.databases.bof.BoFStudent;

import java.util.Comparator;

public class BoFStudentComparator implements Comparator<BoFStudent> {
    private BoFCourseDao cd;

    public BoFStudentComparator(BoFCourseDao cd) { this.cd = cd; }

    @Override
    public int compare(BoFStudent s1, BoFStudent s2) {
        int s1NumOfSharedClasses = cd.getForStudent(s1.getStudentId()).size();
        int s2NumOfSharedClasses = cd.getForStudent(s2.getStudentId()).size();

        return Integer.compare(s2NumOfSharedClasses, s1NumOfSharedClasses);
    }
}
