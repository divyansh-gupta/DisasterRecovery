/*
 * Created by Divyansh Gupta on 2017.05.01  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.Managers;

import com.mycompany.DisasterRecovery.Location;
import com.mycompany.sessionbeans.MessageFacade;
import com.mycompany.DisasterRecovery.Message;
import com.mycompany.DisasterRecovery.Responder;
import com.mycompany.jms.Publisher;
import com.mycompany.jms.Subscriber;
import com.mycompany.sessionbeans.LocationFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 *
 *
 * @author divyansh
 */
@Named(value = "messageManager")
@SessionScoped
public class MessageManager implements Serializable {

    private Map<Location, List<Message>> locationMessagesMap;
    private Publisher publisher;
    private Subscriber subscriber;
    private Location locationEngaged;

    public String getMessageBeingTyped() {
        return messageBeingTyped;
    }

    public void setMessageBeingTyped(String messageBeingTyped) {
        this.messageBeingTyped = messageBeingTyped;
    }
    private String messageBeingTyped;

    public Location getLocationEngaged() {
        return locationEngaged;
    }

    public void setLocationEngaged(Location locationEngaged) {
        this.locationEngaged = locationEngaged;
    }

    @Inject
    private AccountManager accountManager;

    @EJB
    private MessageFacade messageFacade;

    @EJB
    private LocationFacade locationFacade;

    public MessageManager() {
        try {
            publisher = new Publisher("Publisher", "Password");
            subscriber = new Subscriber("Subscriber", "Password");
        } catch (JMSException | NamingException e) {
            System.err.println(e);
            System.exit(-1);
        }
    }

    public void sendMessage() {
        Responder currentUser = accountManager.getSelected();
        Date date = new Date();
        Random rand = new Random(date.getTime());
        int randId = Math.abs(rand.nextInt());
        Message msg = new Message();
        msg.setDescription(this.messageBeingTyped);
        msg.setId(randId);
        msg.setSenderLocation(currentUser.getLocationId());
        msg.setRecieverLocation(this.locationEngaged);
        msg.setTimeStamp(date);
        messageFacade.create(msg);
        try {
            publisher.sendMessageToTopic(this.messageBeingTyped, randId, currentUser.getLocationId().getId(), locationEngaged.getId(), date);
        } catch (JMSException e) {
            System.err.println(e);
            System.exit(-1);
        }
        this.messageBeingTyped = "";
    }

    public Map<Location, List<Message>> getLocationMessagesMap() {
        int userLocId = accountManager.getSelected().getLocationId().getId();
        locationMessagesMap = messageFacade.findAll().stream().distinct()
                .filter(message -> (message.getRecieverLocation().getId() == userLocId) || (message.getSenderLocation().getId() == userLocId))
                .collect(Collectors.groupingBy(Message::getSenderLocation));
        System.out.println(locationMessagesMap);
        return locationMessagesMap;
    }

    public List<Message> messagesBetweenSelectedLocationAndUser() {
        if (this.locationEngaged == null) {
            return new ArrayList();
        }
        List<Message> compiled = getLocationMessagesMap().get(this.locationEngaged);
        compiled.addAll(locationMessagesMap.get(accountManager.getSelected().getLocationId()));
        return compiled;
    }

    public List<Location> getMessageLocations() {
        List<Location> locations = new ArrayList(getLocationMessagesMap().keySet());
        if (locations.size() > 0) {
            this.locationEngaged = locations.get(0);
        }
        return locations;
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
