/*
 * Created by Divyansh Gupta on 2017.04.16  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.DisasterRecovery;

import com.mycompany.sessionbeans.LocationFacade;
import com.mycompany.sessionbeans.NeedFacade;
import com.mycompany.sessionbeans.RequestFacade;
import com.mycompany.sessionbeans.ResponderFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

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

    private MapModel advancedModel;
    private Marker marker;

    private static final String TRIGGERED_ICON = "http://maps.google.com/mapfiles/ms/micons/red-dot.png";
    private static final String UNTRIGGERED_ICON = "http://maps.google.com/mapfiles/ms/micons/green-dot.png";

    @PostConstruct
    public void init() {
        advancedModel = new DefaultMapModel();

        requestFacade.findAll().forEach((request) -> {
            LatLng coord = new LatLng(request.getToLocationId().getLatitude().doubleValue(), request.getToLocationId().getLongitude().doubleValue());
            Marker newMarker = new Marker(coord, request.getToLocationId().getLocationName(), request.getId());

            if (request.getToLocationId().isTriggered()) {
                newMarker.setIcon(TRIGGERED_ICON);
            } else {
                newMarker.setIcon(UNTRIGGERED_ICON);
            }

            advancedModel.addOverlay(newMarker);
        });
    }

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

    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }

    public Marker getMarker() {
        return marker;
    }
}
