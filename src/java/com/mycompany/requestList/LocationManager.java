/*
 * Created by Jinwoo Yom on 2017.04.11  * 
 * Copyright © 2017 Jinwoo Yom. All rights reserved. * 
 */
package com.mycompany.requestList;

import com.mycompany.DisasterRecovery.Location;
import com.mycompany.DisasterRecovery.Request;
import com.mycompany.DisasterRecovery.Responder;
import com.mycompany.sessionbeans.LocationFacade;
import com.mycompany.sessionbeans.RequestFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
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
    
    @EJB
    private com.mycompany.sessionbeans.LocationFacade locationFacade;
    
    private List<Request> items = null;
    
    private Location selected;
    
    private Request request_selected;
    @Resource
    private javax.transaction.UserTransaction utx;
    
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
        return selected.getLocationName().replace('_', ' ');
        //return "Blacksburg";
    }
    
    public List<Request> getItems() {
        System.out.println("SELECTED: " + this.selected.getId());
        //Location newLoc = new Location(1, "newLoc", new BigDecimal(1), new BigDecimal(1), true);
        items = getRequestFacade().findByLocation(this.selected);
        //items = getRequestFacade().findByLocation(newLoc);
        System.out.println("List items: " + items);
        return items;
    }

    public Location getSelected() {
        return selected;
    }

    public void setSelected(Location selected) {
        this.selected = selected;
    }
    
    public String getLocationNameWithId(int x) {
        Location loc = locationFacade.findById(x);
        return loc.getLocationName();
    }

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    public LocationFacade getLocationFacade() {
        return locationFacade;
    }

    public void setLocationFacade(LocationFacade locationFacade) {
        this.locationFacade = locationFacade;
    }

    public Request getRequest_selected() {
        return request_selected;
    }

    public void setRequest_selected(Request request_selected) {
        this.request_selected = request_selected;
    }
}

