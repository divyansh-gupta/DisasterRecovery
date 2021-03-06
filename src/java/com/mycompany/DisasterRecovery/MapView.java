/*
 * Created by Divyansh Gupta on 2017.04.16  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.DisasterRecovery;

import com.mycompany.Managers.AccountManager;
import com.mycompany.sessionbeans.LocationFacade;
import com.mycompany.sessionbeans.NeedFacade;
import com.mycompany.sessionbeans.RequestFacade;
import com.mycompany.sessionbeans.ResponderFacade;
import com.mycompany.requestList.LocationManager;
import java.io.Serializable;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Jinwoo Yom
 */
@ManagedBean
@ViewScoped
public class MapView implements Serializable {

    @EJB
    private LocationFacade locationFacade;

    @EJB
    private ResponderFacade responderFacade;

    @EJB
    private NeedFacade needFacade;

    @EJB
    private RequestFacade requestFacade;

    @Inject
    private LocationManager locationManager;
    
    @Inject
    private AccountManager accountManager;

    private MapModel advancedModel;
    private Marker marker;

    private static final String TRIGGERED_ICON = "https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png";
    private static final String UNTRIGGERED_ICON = "http://maps.google.com/mapfiles/ms/micons/green-dot.png";
    private static final String MYLOCATION_ICON = "resources/images/mylocation.png";

    /**
     * Initialization of Mapview
     */
    @PostConstruct
    public void init() {
        advancedModel = new DefaultMapModel();

        locationFacade.findAll().forEach((location) -> {
            LatLng coord = new LatLng(location.getLatitude().doubleValue(), location.getLongitude().doubleValue());
            Marker newMarker = new Marker(coord, location.getLocationName(), location);
            newMarker.setIcon(UNTRIGGERED_ICON);
            
            if (accountManager.isLoggedIn() && Objects.equals(accountManager.getSelected().getLocationId().getId(), location.getId())) {
                newMarker.setIcon(MYLOCATION_ICON);
            }

            if (location.getTriggered()) {
                newMarker.setIcon(TRIGGERED_ICON);
            }
                                    
            advancedModel.addOverlay(newMarker);
        });
    }

    /**
     * Get map center coordinate
     * @return Map Center
     */
    public String getMapCenter() {
        Location center = locationFacade.find(1);
        if (responderFacade.isLoggedIn()) {
            Responder user = responderFacade.getLoggedIn();
            if (user != null) {
                center = user.getLocationId();
            }
        }
        if (center == null) {
            return "37.2296, 80.4139";
        }
        return center.getLatitude().doubleValue() + ", " + center.getLongitude().doubleValue();
    }

    /**
     * Get advanced model for Google map
     * @return Advance Model
     */
    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    /**
     * Runs when a marker is selected
     * @param event marker selected event
     */
    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        locationManager.setSelected(locationFacade.findLocationByName(marker.getTitle()));
    }
    
    /**
     * Get selected Marker description
     * @return Emergency Description of selected location
     */
    public String getData() {
        return locationManager.getSelected().getEmergencyDescription();
    }

    /**
     * Get marker
     * @return Selected marker
     */
    public Marker getMarker() {
        return marker;
    }
}
