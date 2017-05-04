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
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import org.primefaces.context.RequestContext;

/**
 * Message manager
 *
 * @author divyansh
 */
@Named(value = "messageManager")
@SessionScoped
public class MessageManager implements Serializable {

    private List<Message> locationMessages;

    private Location locationEngaged;

    @Inject
    private AccountManager accountManager;

    private Publisher publisher;

    @EJB
    private MessageFacade messageFacade;

    @EJB
    private LocationFacade locationFacade;

    /**
     * Constructor
     */
    public MessageManager() {
        try {
            publisher = new Publisher();
//            subscriber = new Subscriber();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*
     * Click button
    */
    public void clickButton() {
        String jsToRun = "document.getElementsByClassName('b"
                + this.locationEngaged.getAlternateName() + this.locationEngaged.getId() + "')[0].click()";
        System.out.println(jsToRun);
        RequestContext.getCurrentInstance().execute(jsToRun);
    }

    /**
     * Send message
     */
    public void sendMessage() {
        // Get current user
        Responder currentUser = accountManager.getSelected();
        
        Date date = new Date();
        Random rand = new Random(date.getTime());
        int randId = Math.abs(rand.nextInt());
        
        // Set message information
        Message msg = new Message();
        msg.setDescription(this.messageBeingTyped);
        msg.setId(randId);
        msg.setSenderLocation(currentUser.getLocationId());
        msg.setReceiverLocation(this.locationEngaged);
        msg.setTimeStamp(date);
        messageFacade.create(msg);
        
        // Send message
        try {
            publisher.sendMessageToTopic(this.messageBeingTyped, randId, currentUser.getLocationId().getId(), this.locationEngaged.getId(), date);
            messagesBetweenSelectedLocationAndUser();
        } catch (JMSException e) {
            System.out.println(e);
        }
        
        this.messageBeingTyped = "";
    }

    /*
     * Send trigger
    */
    public void sendTrigger(Location sender, String description) {
        Date date = new Date();
        Random rand = new Random(date.getTime());
        locationFacade.findAll().forEach(loc -> {
            int randId = Math.abs(rand.nextInt());
            Message msg = new Message();
            msg.setDescription("Emergency triggered!: " + description);
            msg.setId(randId);
            msg.setSenderLocation(sender);
            msg.setReceiverLocation(loc);
            msg.setTimeStamp(date);
            messageFacade.create(msg);
            try {
                publisher.sendMessageToTopic("Emergency triggered!: " + description, randId, sender.getId(), loc.getId(), date);
                messagesBetweenSelectedLocationAndUser();
            } catch (JMSException e) {
                System.out.println(e);
            }
        });
    }

    /**
     * Get messages between selected location and user
     * @return list of message
     */
    public List<Message> messagesBetweenSelectedLocationAndUser() {
        System.out.println("messagesBetweenSelectedLocationAndUser running");

        if (this.locationEngaged == null) {
            return new ArrayList();
        }
        int userLocId = accountManager.getSelected().getLocationId().getId();
        locationMessages = messageFacade.findAll().stream().distinct()
                .filter(message
                        -> (message.getReceiverLocation().getId() == userLocId && Objects.equals(message.getSenderLocation().getId(), this.locationEngaged.getId()))
                || (message.getSenderLocation().getId() == userLocId && Objects.equals(message.getReceiverLocation().getId(), this.locationEngaged.getId())))
                .sorted((Message one, Message two) -> -(one.getTimeStamp().compareTo(two.getTimeStamp())))
                .collect(Collectors.toList());
        System.out.println("Message sample: " + locationMessages);
        return locationMessages;
    }

    /**
     * Check if this message comes from myself
     * @param msgId message id
     * @return true if it comes from myself, otherwise false.
     */
    public boolean messageFromMe(int msgId) {
        Message msg = messageFacade.find(msgId);
        if (accountManager.getSelected() == null || msg == null) {
            return false;
        }
        boolean toReturn = msg.getSenderLocation().equals(accountManager.getSelected().getLocationId());
        return toReturn;
    }

    /**
     * Get message location
     * @return list of location
     */
    public List<Location> getMessageLocations() {
        List<Location> locations = locationFacade.findAll();
        return locations;
    }

    /**
     * Get message being typed
     * @return message
     */
    public String getMessageBeingTyped() {
        return messageBeingTyped;
    }

    /**
     * Set message being typed
     * @param messageBeingTyped message
     */
    public void setMessageBeingTyped(String messageBeingTyped) {
        this.messageBeingTyped = messageBeingTyped;
    }
    private String messageBeingTyped;

    /**
     * Get location engaged
     * @return location
     */
    public Location getLocationEngaged() {
//        System.out.println("Get location engaged is running with value: " + this.locationEngaged.getLocationName());
        return locationEngaged;
    }

    /**
     * Set location engaged
     * @param locationEngaged location
     */
    public void setLocationEngaged(Location locationEngaged) {
//        System.out.println("New location picked: " + locationEngaged.getLocationName());
        this.locationEngaged = locationEngaged;
        messagesBetweenSelectedLocationAndUser();
    }

    /**
     * Get message facade
     * @return message facade
     */
    public MessageFacade getMessageFacade() {
        return messageFacade;
    }

    /**
     * Get location facade
     * @return location facade
     */
    public LocationFacade getLocationFacade() {
        return locationFacade;
    }

    /**
     * Get location messages
     * @return list of message
     */
    public List<Message> getLocationMessages() {
        return locationMessages;
    }

    /**
     * Set location message
     * @param locationMessages list of message
     */
    public void setLocationMessages(List<Message> locationMessages) {
        this.locationMessages = locationMessages;
    }
}
