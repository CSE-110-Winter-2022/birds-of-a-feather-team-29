package com.example.cse110_project;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cse110_project.utilities.PrioritizationAlgorithms;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PrioritizationAlgorithmsTest_MS2_US6 {

    @Test
    public void test_Func_calcClassSizeScore_One_Class() {
        assert(PrioritizationAlgorithms.calcClassSizeScore(1.00, 'S') == 1.33);
    }

    @Test
    public void test_Func_calcClassSizeScore_Many_Classes() {
        double sizeScore = 0.0;
        sizeScore = PrioritizationAlgorithms.calcClassSizeScore(sizeScore, 'H')
                + PrioritizationAlgorithms.calcClassSizeScore(sizeScore, 'T')
                + PrioritizationAlgorithms.calcClassSizeScore(sizeScore, 'M');
        assert(sizeScore == 1.24);
    }

    @Test
    public void test_Func_calcRecentScore_Previous_Quarter() {
        assert(PrioritizationAlgorithms.calcRecentScore(0, "2022", "Fall") == 5);
    }

    @Test
    public void test_Func_calcRecentScore_Last_Year_Same_Quarter() {
        assert(PrioritizationAlgorithms.calcRecentScore(30, "2021", "Winter") == 32);
    }

    @Test
    public void test_Func_calcRecentScore_Last_Year_Diff_Quarter() {
        assert(PrioritizationAlgorithms.calcRecentScore(25, "2021", "Summer Session I") == 29);
    }

    @Test
    public void test_Func_calcRecentScore_Many_Years_Same_Quarter() {
        assert(PrioritizationAlgorithms.calcRecentScore(43, "2017", "Winter") == 44);
    }

    @Test
    public void test_Func_calcRecentScore_Many_Years_Diff_Quarter() {
        assert(PrioritizationAlgorithms.calcRecentScore(14, "2017", "Spring") == 15);
    }

}
