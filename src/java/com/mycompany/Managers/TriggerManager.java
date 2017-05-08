package com.mycompany.Managers;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ejb.EJB;
import com.mycompany.DisasterRecovery.Location;
import com.mycompany.DisasterRecovery.Responder;
import com.mycompany.sessionbeans.LocationFacade;
import javax.inject.Inject;

/*
 * Created by Divyansh Gupta on 2017.04.11  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */

/**
 * This class handles triggering and untriggering emergencies at
 * user-defined locations.
 * 
 * @author divyansh
 */
@Named(value = "triggerManager")
@SessionScoped
public class TriggerManager implements Serializable {

    /*
    The instance variable 'locationFacade' is annotated with the @EJB annotation.
    The @EJB annotation directs the EJB Container (of the GlassFish AS) to inject (store) the object reference
    of the LocationFacade object, after it is instantiated at runtime, into the instance variable 'locationFacade'.
     */
    @EJB
    LocationFacade locationFacade;

    @Inject
    MessageManager messageManager;

    private String emergencyDescription;

    /**
     * Get emergency description text field input.
     *
     * @return emergency description
     */
    public String getEmergencyDescription() {
        return emergencyDescription;
    }

    /**
     * Set emergency description for a location in which you are triggering an
     * emergency.
     *
     * @param emergencyDescription emergency description
     */
    public void setEmergencyDescription(String emergencyDescription) {
        this.emergencyDescription = emergencyDescription;
    }

    /**
     * Trigger emergency at user location.
     *
     * @param user responder to trigger an emergency for.
     * @return map page
     */
    public String triggerEmergency(Responder user) {
        Location userLocation = user.getLocationId();
        userLocation.setTriggered(Boolean.TRUE);
        userLocation.setEmergencyDescription(emergencyDescription);
        locationFacade.edit(userLocation);
        messageManager.sendTrigger(userLocation, emergencyDescription);
        return "/Map.xhtml?faces-redirect=true";
    }

    /**
     * Untrigger emergency at user's location.
     *
     * @param user responder
     * @return map page
     */
    public String unTriggerEmergency(Responder user) {
        Location userLocation = user.getLocationId();
        userLocation.setTriggered(Boolean.FALSE);
        userLocation.setEmergencyDescription("");
        locationFacade.edit(userLocation);
        return "/Map.xhtml?faces-redirect=true";
    }

    /**
     * Check status of location
     *
     * @param user responder
     * @return return true if triggered, otherwise return false
     */
    public boolean userLocationIsTriggered(Responder user) {
        if (user == null) {
            return false;
        }
        Location userLocation = user.getLocationId();
        if (userLocation == null) {
            return false;
        }
        return userLocation.getTriggered();
    }
}
