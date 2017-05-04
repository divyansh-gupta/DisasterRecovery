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
 *
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

    public MessageManager() {
        try {
            publisher = new Publisher();
//            subscriber = new Subscriber();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void clickButton() {
        String jsToRun = "document.getElementsByClassName('b"
                + this.locationEngaged.getAlternateName() + this.locationEngaged.getId() + "')[0].click()";
        System.out.println(jsToRun);
        RequestContext.getCurrentInstance().execute(jsToRun);
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
            publisher.sendMessageToTopic(this.messageBeingTyped, randId, currentUser.getLocationId().getId(), this.locationEngaged.getId(), date);
            messagesBetweenSelectedLocationAndUser();
        } catch (JMSException e) {
            System.out.println(e);
        }
        this.messageBeingTyped = "";
    }

    public void sendTrigger(Location sender, String description) {
        Date date = new Date();
        Random rand = new Random(date.getTime());
        locationFacade.findAll().forEach(loc -> {
            int randId = Math.abs(rand.nextInt());
            Message msg = new Message();
            msg.setDescription("Emergency triggered!: " + description);
            msg.setId(randId);
            msg.setSenderLocation(sender);
            msg.setRecieverLocation(loc);
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

    public List<Message> messagesBetweenSelectedLocationAndUser() {
        System.out.println("messagesBetweenSelectedLocationAndUser running");

        if (this.locationEngaged == null) {
            return new ArrayList();
        }
        int userLocId = accountManager.getSelected().getLocationId().getId();
        locationMessages = messageFacade.findAll().stream().distinct()
                .filter(message
                        -> (message.getRecieverLocation().getId() == userLocId && Objects.equals(message.getSenderLocation().getId(), this.locationEngaged.getId()))
                || (message.getSenderLocation().getId() == userLocId && Objects.equals(message.getRecieverLocation().getId(), this.locationEngaged.getId())))
                .sorted((Message one, Message two) -> -(one.getTimeStamp().compareTo(two.getTimeStamp())))
                .collect(Collectors.toList());
        System.out.println("Message sample: " + locationMessages);
        return locationMessages;
    }

    public boolean messageFromMe(int msgId) {
        Message msg = messageFacade.find(msgId);
        if (accountManager.getSelected() == null || msg == null) {
            return false;
        }
        boolean toReturn = msg.getSenderLocation().equals(accountManager.getSelected().getLocationId());
        return toReturn;
    }

    public List<Location> getMessageLocations() {
        List<Location> locations = locationFacade.findAll();
        return locations;
    }

    public String getMessageBeingTyped() {
        return messageBeingTyped;
    }

    public void setMessageBeingTyped(String messageBeingTyped) {
        this.messageBeingTyped = messageBeingTyped;
    }
    private String messageBeingTyped;

    public Location getLocationEngaged() {
//        System.out.println("Get location engaged is running with value: " + this.locationEngaged.getLocationName());
        return locationEngaged;
    }

    public void setLocationEngaged(Location locationEngaged) {
//        System.out.println("New location picked: " + locationEngaged.getLocationName());
        this.locationEngaged = locationEngaged;
        messagesBetweenSelectedLocationAndUser();
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

    public List<Message> getLocationMessages() {
        return locationMessages;
    }

    public void setLocationMessages(List<Message> locationMessages) {
        this.locationMessages = locationMessages;
    }
}
