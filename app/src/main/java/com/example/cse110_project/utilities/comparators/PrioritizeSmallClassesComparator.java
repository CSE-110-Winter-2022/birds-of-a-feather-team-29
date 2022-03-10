package com.example.cse110_project.utilities.comparators;

import com.example.cse110_project.databases.bof.BoFCourseDao;
import com.example.cse110_project.databases.bof.BoFStudent;

import java.util.Comparator;

public class PrioritizeSmallClassesComparator implements BoFComparator {

    @Override
    public int compare(BoFStudent s1, BoFStudent s2) {
        double s1TotalSizeScore = s1.getSizeScore();
        double s2TotalSizeScore = s2.getSizeScore();

        if ((s2.getIsWaving() && s1.getIsWaving())
                || (!s2.getIsWaving() && !s1.getIsWaving())) {
            return Double.compare(s2TotalSizeScore, s1TotalSizeScore);
        } else if (s1.getIsWaving()) {
            return Double.compare(s2TotalSizeScore, Integer.MAX_VALUE);
        } else {
            return Double.compare(Integer.MAX_VALUE, s1TotalSizeScore);
        }
    }

}
