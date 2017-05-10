/*
 * Created by Osman Balci on 2017.03.26  * 
 * Copyright © 2017 Osman Balci. All rights reserved. * 
 */
package com.mycompany.Managers;

/**
 * Class contains constant values for the DisasterRecovery
 * project (API Keys etc).
 * @author andre
 */
public final class Constants {

    /**
     * Google map decode url
     */
    public static final String GMAPS_GEOCODE_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";

    /**
     * Google map API key
     */
    public static final String GMAPS_GEOCODE_API = "key=API_KEY_HERE";
    public static final String PHOTOS_ABSOLUTE_PATH = "/home/cloudsd/FileSorageLocation-team5/";
    public static final String PHOTOS_RELATIVE_PATH = "FileSorageLocation-team5/";
    public static final String DEFAULT_PHOTO_RELATIVE_PATH = "FileSorageLocation-team5/";
    public static final String SET_DEFAULT_PHOTO_RELATIVE_PATH = "defaultResponderPhoto.png";
    
    /* Temporary filename */
    public static final String TEMP_FILE = "tmp_file";

    /* =========== Our Design Decision ===========
        We decided to scale down the user's uploaded photo to 200x200 px,
        which we call the Thumbnail photo, and use it.
    
        We do not want to use the uploaded photo as is, which may be
        very large in size, degrading performance.
     */

    /**
     * Photo size
     */

    public static final Integer THUMBNAIL_SIZE = 200;

    /* United States postal state abbreviations */

    /**
     * States name
     */

    public static final String[] STATES = {"AK", "AL", "AR", "AZ", "CA", "CO", "CT",
        "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA",
        "MD", "ME", "MH", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM",
        "NV", "NY", "OH", "OK", "OR", "PA", "PR", "PW", "RI", "SC", "SD", "TN", "TX", "UT",
        "VA", "VI", "VT", "WA", "WI", "WV", "WY"};

}
