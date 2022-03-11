package com.example.cse110_project.utilities;

public class Constants {
    // App version
    public static final String APP_VERSION = "Birds of a Feather v0.0.1";

    // Constants for Link to head shot page
    public static final String DEFAULT_PIC_LINK = "https://lh3.googleusercontent.com/pw/AM-JKLWck" +
            "aAiQnn9K7DpJJGQgQxhDkIpsqGkpPluj5-kKN4fm3kJ4S0yM3hgUNnfFGLC8tFgYZicHvhIZCjwSNqzcanv2" +
            "8VJ4YkX_56rZejnAOU7wbk7Wiwbb8m-lzGtNYurHVdbRaLidgPyhk0mikN7dXaI=s225-no";
    public static final String IMAGE_NOT_FOUND_PIC = "https://lh3.googleusercontent.com/EOrcLBJL" +
            "W8btt7p7MUSRAJxR5n8OPoCA-2eJZHBTSppZEeC8BmLWBzsWQqX7AbyrF7YRE_LE3vaYp_g7PvPD3cswdOG" +
            "IORMhWgd6hRZStEAMo0vH-aFDgoEUFkjcFHUL4g1qi1BpywczZPFn6nT6jNQ_b7L-uQNPpIoXBkB41Oervf" +
            "OaRujJ8xlNDETi4W2wBCrqmSVmEH-yiv_34HOi4XXIOt0HHS8obgL9NApjzUAi1HrJxmXlJ2VnS6gRU3wsg8" +
            "pHIlQ-lRTIdozRSUqf8ekKzjoI48IWSkt4-jOXTLzUE3QVuC9LO3SEoA915KODiCQ0zxPqPeI9DVfdfAWp5U" +
            "2j52kyESM28TeUzwBRD0Cf475ntZ5U7wPFovsWd_wnO9MqSDFRtLax21eyP0BrxX9FSJGxajrIL9EPm5Z44-" +
            "XzN8LVpZ3DVVc-HXSqu9jxd3EZCFwGr7FvsS2WlQsKrzYq7C45QXukoaN2B9ZkV2N1Kz8IELRD1FJMARn-ck" +
            "iz75c4cxfpwZ70fLKyj6_bgsO_yHMyDcv6RT9Ipu77Cz18MnB_pjm2r-0PDUgdgzwVtpPd85aaDMCIlsjZEk" +
            "Q5qGmcZdHjoNaNgFyByX5G_BNPAXKOWOJk7UYmQyqjQkFOLmdfH6VXE5iZowYA14UeEWQqM15Bt4Q1AR9Zyw" +
            "BGpQzuQJxiSYkVNLDMJL_D25hdoY6kb0Ly9UWdX1f3P2gVIsOh=s250-no?authuser=0";
    public static final String USER_URL_KEY = "userURL";
    public static final String USER_FIRST_NAME = "userFirstName";

    // Constants for SharedPreferences databases
    public static final String CURR_ENTERED_COURSES_DB = "currEnteredCourses";
    public static final String MAIN_USER_COURSE_DB = "userCourseInformation";
    public static final String USER_INFO = "userInformation";


    // Constants for Adding Courses page
    public static final String SPACE = " ";
    public static final String YEAR_KEY = "year";
    public static final String QTR_KEY = "quarter";
    public static final String INIT_SUBJECT_KEY = "initSubject";
    public static final String SUBJECT_KEY = "subjectKey";
    public static final String INIT_COURSE_NUMBER = "initCourseNumber";

    // Constants for Home page
    public static final String START = "Start";
    public static final String STOP = "Stop";
    public static final String COMMA = ",";
    public static final String Bluetooth_Not_Supported = "Device doesn't support Bluetooth!" +
            " Please check if Bluetooth is on.";
    public static final int REQUEST_ENABLE_BT = 1;

    // Constants for Adapter classes
    public static final String LEFT_PARENTHESIS = " (";
    public static final String RIGHT_PARENTHESIS = ")";
    public static final String COMMA_SPACE = ", ";
    public static final String BOF_STUDENT_ID = "bof_student_id";

    // Constants for Pop-up messages
    public static final String OK = "Ok";
    public static final String WARNING = "Warning!";
    public static final String ALERT = "Alert!";
    public static final String TOO_MANY_COURSES_WARNING = "Exceeding maximum of 6 courses per " +
            "quarter. Please press Back to continue.";
    public static final String NO_COURSE_ENTERED = "Please enter a course number. If finished, " +
            "press Back to continue.";
    public static final String DUPLICATE_COURSE = "Course has already been entered. Please " +
            "enter another course or press Back.";
    public static final String NO_SUB_OR_COURSE_NUMBER_WARNING = "Please enter a subject or " +
            "course number in the respective empty field.";
    public static final String NO_CLASSES_ENTERED_WARNING = "Please enter at least one class " +
            "for a selected year and quarter to proceed to the next page.";
    public static final String INVALID_NAME = "Invalid name! Valid characters: A-Z, a-z, space " +
            "character";
}
