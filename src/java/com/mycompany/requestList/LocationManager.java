/*
 * Created by Jinwoo Yom on 2017.04.11  * 
 * Copyright © 2017 Jinwoo Yom. All rights reserved. * 
 */
package com.mycompany.requestList;

import com.mycompany.DisasterRecovery.Location;
import com.mycompany.DisasterRecovery.Request;
import com.mycompany.DisasterRecovery.Responder;
import com.mycompany.sessionbeans.RequestFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jinwoo Yom
 */
@Named(value = "LocationManager")
@SessionScoped
public class LocationManager implements Serializable {
    
    @EJB
    private com.mycompany.sessionbeans.RequestFacade requestFacade;
    
    private List<Request> items = null;
    
    private Location selected;
    
    @PersistenceContext(unitName = "DisasterRecoveryPU")
    private EntityManager em;
    
    public RequestFacade getRequestFacade() {
        return requestFacade;
    }
    
//    @Override
//    protected EntityManager getEntityManager() {
//        return em;
//    }
    
    public String getLocation() {
        //return selected.getLocationName();
        return "Blacksburg";
    }
    
    public List<Request> getItems() {
        if (items == null) {
            items = getRequestFacade().findByLocation("Blacksburg");
        }
        return items;
    }
}

