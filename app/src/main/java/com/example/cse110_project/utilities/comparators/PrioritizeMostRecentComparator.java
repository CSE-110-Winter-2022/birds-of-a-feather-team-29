package com.example.cse110_project.utilities.comparators;

import com.example.cse110_project.databases.bof.BoFCourseDao;
import com.example.cse110_project.databases.bof.BoFStudent;

import java.util.Comparator;

public class PrioritizeMostRecentComparator implements BoFComparator {

    @Override
    public int compare(BoFStudent s1, BoFStudent s2) {
        int s1TotalRecentScore = s1.getRecentScore();
        int s2TotalRecentScore = s2.getRecentScore();

        if ((s2.getIsWaving() && s1.getIsWaving())
                || (!s2.getIsWaving() && !s1.getIsWaving())) {
            return Integer.compare(s2TotalRecentScore, s1TotalRecentScore);
        } else if (s1.getIsWaving()) {
            return Integer.compare(s2TotalRecentScore, s1TotalRecentScore + 100);
        } else {
            return Integer.compare(s2TotalRecentScore + 100, s1TotalRecentScore);
        }
    }

}
