/*
 * Created by Divyansh Gupta on 2017.05.01  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.Managers;

import com.mycompany.DisasterRecovery.Location;
import com.mycompany.sessionbeans.MessageFacade;
import com.mycompany.DisasterRecovery.Message;
import com.mycompany.sessionbeans.LocationFacade;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 *
 * @author divyansh
 */
@Named(value = "messageManager")
@SessionScoped
public class MessageManager implements Serializable {

    private Map<Location, List<Message>> locationMessagesMap;

    @EJB
    private MessageFacade messageFacade;

    @EJB
    private LocationFacade locationFacade;

    public Map<Location, List<Message>> getLocationMessagesMap() {
        return locationMessagesMap;
    }

    public void setLocationMessagesMap(Map<Location, List<Message>> locationMessagesMap) {
        this.locationMessagesMap = locationMessagesMap;
    }

    public MessageFacade getMessageFacade() {
        return messageFacade;
    }

    public void setMessageFacade(MessageFacade messageFacade) {
        this.messageFacade = messageFacade;
    }

    public LocationFacade getLocationFacade() {
        return locationFacade;
    }

    public void setLocationFacade(LocationFacade locationFacade) {
        this.locationFacade = locationFacade;
    }

}
