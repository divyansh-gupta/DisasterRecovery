/*
 * Created by Junjie Cheng on 2017.04.16  * 
 * Copyright © 2017 Junjie Cheng. All rights reserved. * 
 */
package com.mycompany.Managers;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named(value = "requestManager")

/**
 *
 * @author cheng
 */
public class RequestManager implements Serializable {
    
    private int id;
    private String status;
    private int from_location_id;
    private int to_location_id;
    
    
    
}
