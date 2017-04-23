package com.mycompany.Managers;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ejb.EJB;
import com.mycompany.DisasterRecovery.Location;
import com.mycompany.DisasterRecovery.Responder;
import com.mycompany.sessionbeans.LocationFacade;

/*
 * Created by Divyansh Gupta on 2017.04.11  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */

/**
 *
 * @author divyansh
 */
@Named(value = "triggerManager")
@SessionScoped
public class TriggerManager implements Serializable {
    
    @EJB
    LocationFacade locationFacade;
    
    private String emergencyDescription;

    public String getEmergencyDescription() {
        return emergencyDescription;
    }

    public void setEmergencyDescription(String emergencyDescription) {
        this.emergencyDescription = emergencyDescription;
    }
    
    public String triggerEmergency(Responder user) {
        Location userLocation = user.getLocationId();
        // TODO: Send Message to Nearby Responders
        userLocation.setTriggered(Boolean.TRUE);
        locationFacade.edit(userLocation);
        return "/Map.xhtml?faces-redirect=true";
    }
    
    public String unTriggerEmergency(Responder user) {
        Location userLocation = user.getLocationId();
        userLocation.setTriggered(Boolean.FALSE);
        locationFacade.edit(userLocation);
        return "/Profile.xhtml?faces-redirect=true";
    }
    
    public boolean userLocationIsTriggered(Responder user) {
        if (user == null) {
            return false;
        }
        Location userLocation = user.getLocationId();
        if (userLocation == null) {
            return false;
        }
        return userLocation.isTriggered();
    }
}
