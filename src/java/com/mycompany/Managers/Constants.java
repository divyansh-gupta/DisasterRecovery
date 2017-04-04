/*
 * Created by Osman Balci on 2017.03.26  * 
 * Copyright © 2017 Osman Balci. All rights reserved. * 
 */
package com.mycompany.Managers;

/**
 *
 * @author andre
 */
public final class Constants {
    
        public static final String GMAPS_GEOCODE_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
        public static final String GMAPS_GEOCODE_API = "key=***REMOVED***";
        //    public static final String FILES_ABSOLUTE_PATH = "/Users/Balci/CloudStorage/FileStorage/";
        public static final String FILES_ABSOLUTE_PATH = "/home/cloudsd/Kaul/CloudStorage/FileStorage";

//        public static final String FILES_ABSOLUTE_PATH = "C:\\Users\\Bhaanu Kaul\\Desktop\\School\\CS 3984\\CloudStorage\\FileStorage\\";


//        public static final String PHOTOS_ABSOLUTE_PATH = "/Users/Balci/CloudStorage/PhotoStorage/";
        public static final String PHOTOS_ABSOLUTE_PATH = "/home/cloudsd/Kaul/CloudStorage/PhotoStorage";

//    public static final String PHOTOS_ABSOLUTE_PATH = "C:\\Users\\Bhaanu Kaul\\Desktop\\School\\CS 3984\\CloudStorage\\PhotoStorage\\";

    /*
    In glassfish-web.xml file, we designated the '/CloudStorage/' directory as the
    Alternate Document Root directory with the following statement:
        
        <property name="alternatedocroot_1" value="from=/CloudStorage/* dir=/Users/Balci" />
    
    Relative path is defined with respect to the Alternate Document Root starting with 'CloudStorage'.
     */
    public static final String FILES_RELATIVE_PATH = "CloudStorage/FileStorage/";
    public static final String PHOTOS_RELATIVE_PATH = "CloudStorage/PhotoStorage/";
    public static final String DEFAULT_PHOTO_RELATIVE_PATH = "CloudStorage/PhotoStorage/defaultUserPhoto.png";

    /* Temporary filename */
    public static final String TEMP_FILE = "tmp_file";

    /* =========== Our Design Decision ===========
        We decided to scale down the user's uploaded photo to 200x200 px,
        which we call the Thumbnail photo, and use it.
    
        We do not want to use the uploaded photo as is, which may be
        very large in size degrading performance.
     */
    public static final Integer THUMBNAIL_SIZE = 200;

    /* United States postal state abbreviations */
    public static final String[] STATES = {"AK", "AL", "AR", "AZ", "CA", "CO", "CT",
        "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA",
        "MD", "ME", "MH", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM",
        "NV", "NY", "OH", "OK", "OR", "PA", "PR", "PW", "RI", "SC", "SD", "TN", "TX", "UT",
        "VA", "VI", "VT", "WA", "WI", "WV", "WY"};

    /* Security questions to reset password  */
    public static final String[] QUESTIONS = {
        "In what city were you born?",
        "What is your mother's maiden name?",
        "What elementary school did you attend?",
        "What was the make of your first car?",
        "What is your father's middle name?",
        "What is the name of your most favorite pet?",
        "What street did you grow up on?"
    };
    
}
