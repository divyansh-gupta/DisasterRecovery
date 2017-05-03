/*
 * Created by Divyansh Gupta on 2017.05.01  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.jms;

import com.mycompany.Managers.MessageManager;
import com.mycompany.sessionbeans.LocationFacade;
import com.mycompany.sessionbeans.MessageFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@MessageDriven(mappedName = "jms/DisasterRecoveryTopic", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode",
            propertyValue = "Auto-acknowledge")
    ,
        @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Topic")
})
@ApplicationScoped
@ServerEndpoint("/actions")
public class Subscriber implements MessageListener, Serializable {

    @EJB
    private LocationFacade locationFacade;

    private Set<Session> allSessions = new HashSet();

    public Subscriber() {

    }

    @Override
    public void onMessage(javax.jms.Message msg) {
        try {
            String msgTxt = ((TextMessage) msg).getText();
            System.out.println("on message running!: " + msgTxt);
            this.sendMessageToAll(msgTxt);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @OnOpen
    public void open(Session session) {
        System.out.println("Helloo");
        this.allSessions.add(session);
    }

    @OnClose
    public void close(Session session) {
        this.allSessions.remove(session);
        System.out.println("Helloo closed");
    }

    @OnError
    public void onError(Throwable error) {
        System.out.println("Helloo error");
    }

    public void sendMessageToAll(String msgTxt) {
        allSessions.forEach((sess) -> {
            System.out.println(sess);
            if (sess.isOpen()) {
                try {
                    sess.getBasicRemote().sendText("Nice to meet ya pal");
                } catch (IOException ex) {
                    Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                this.allSessions.remove(sess);
            }
        });
    }

    @OnMessage
    public void handleMessage(String message, Session session) {

    }
}
