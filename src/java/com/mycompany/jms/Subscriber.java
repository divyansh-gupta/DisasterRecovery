/*
 * Created by Divyansh Gupta on 2017.05.01  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.jms;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
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

    Set<Session> connectedSessions = new HashSet();

    // On message
    @Override
    public void onMessage(javax.jms.Message msg) {
        try {
            String msgTxt = ((TextMessage) msg).getText();
            System.out.println("on message running!: " + msgTxt);
            sendMessageToAll();
        } catch (JMSException e) {
            System.out.println(e);
        }
    }

    // Open
    @OnOpen
    public void open(Session session) {
        connectedSessions.add(session);
        System.out.println("Hello!");

    }

    // close
    @OnClose
    public void close(Session session) {
        connectedSessions.remove(session);
        System.out.println("Bye bye!");
    }

    // On error
    @OnError
    public void onError(Throwable error) {
        System.out.println("Websocket error!");
    }

    @OnMessage
    public void sendMessageToAll() {
        connectedSessions.forEach((sess) -> {
            if (sess.isOpen()) {
                try {
                    sess.getBasicRemote().sendText("Got a new message for ya, pal!");
                } catch (IOException ex) {
                    Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                connectedSessions.remove(sess);
            }
        });
    }

    // Handle message
    @OnMessage
    public void handleMessage(String message, Session session) {
        session.getOpenSessions().forEach((sess) -> {
            if (sess.isOpen()) {
                try {
                    sess.getBasicRemote().sendText("Got a new message for ya, pal!");
                } catch (IOException ex) {
                    Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
