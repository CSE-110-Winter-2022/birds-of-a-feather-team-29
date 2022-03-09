package com.example.cse110_project.utilities.comparators;

import com.example.cse110_project.databases.bof.BoFStudent;

import java.util.Comparator;

public interface BoFComparator extends Comparator<BoFStudent> {
    int compare(BoFStudent s1, BoFStudent s2);
}
