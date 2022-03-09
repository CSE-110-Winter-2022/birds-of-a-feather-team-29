package com.example.cse110_project.utilities;

/**
 * Class contains prioritization algorithms for sorting based on the two sorting options the
 * User has access to
 * */
public class PrioritizationAlgorithms {

    /**
     * Calculates the ...
     * */
    public static double calcClassSizeScore(double currScore, char classSize) {
        double[] weights = { 1.00, 0.33, 0.18, 0.10, 0.06, 0.03 };
        char[] classSizes = { 'T', 'S', 'M', 'L', 'H', 'G' };
        for (int i = 0; i < classSizes.length; i++) {
            if (classSize == classSizes[i]) { return currScore + weights[i]; }
        }
        return 0;
    }

    /**
     * Calculates the ...
     * */
    public static int calcRecentScore(int currScore, String year, String quarter) {
        String[] quarters = { "Fall", "Winter", "Spring" };
        int[] rememberScores = { 5, 4, 3, 2 };
        int yearDiff = 2022 - Integer.parseInt(year);
        int ageScore = (yearDiff * 4) - 3;
        for (int i = 0; i < quarters.length; i++) {
            if (quarter.equals(quarters[i])) {
                ageScore = (yearDiff * 4) - i;
                break;
            }
        }
        if (ageScore > 3) { return currScore + 1; }
        for (int i = 0; i < rememberScores.length; i++) {
            if (ageScore == i) { return currScore + rememberScores[i]; }
        }
        return 0;
    }
}
