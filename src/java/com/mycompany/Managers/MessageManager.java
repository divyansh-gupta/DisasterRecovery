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
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Random;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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

    public Map<Location, List<Message>> getLocationMessagesMap() {
        return locationMessagesMap;
    }

    public void sendMessage(String msgTxt, Location sendTo, Responder currentUser) {
        Date date = new Date();
        Random rand = new Random(date.getTime());
        int randId = Math.abs(rand.nextInt());
        Message msg = new Message();
        msg.setDescription(msgTxt);
        msg.setId(randId);
        msg.setSenderLocation(currentUser.getLocationId());
        msg.setRecieverLocation(sendTo);
        msg.setTimeStamp(date);
        messageFacade.create(msg);
        try {
            publisher.sendMessageToTopic(msgTxt, randId, currentUser.getLocationId().getId(), sendTo.getId(), date);
        } catch (JMSException e) {
            System.err.println(e);
            System.exit(-1);
        }
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
