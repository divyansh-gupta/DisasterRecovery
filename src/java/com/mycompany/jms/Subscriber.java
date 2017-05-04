/*
 * Created by Divyansh Gupta on 2017.05.01  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.jms;

import com.mycompany.Managers.MessageManager;
import com.mycompany.sessionbeans.LocationFacade;
import com.mycompany.sessionbeans.MessageFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSubscriber;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;

public class Subscriber implements MessageListener, Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private TopicConnectionFactory connectionFactory;
    private TopicConnection connection;
    private TopicSession subscriberSession;
    private final String topicName = "jms/DisasterRecoveryTopic";
    private Topic topic;
    private TopicSubscriber topicSubscriber;

    @Inject
    private MessageManager messageManager;

    @EJB
    private MessageFacade messageFacade;

    @EJB
    private LocationFacade locationFacade;

    /*
     Constructor
    */
    public Subscriber() throws NamingException, JMSException {
        InitialContext ctx = new InitialContext();
        connectionFactory = (TopicConnectionFactory) ctx.lookup("jms/DisasterRecoveryConnectionFactory");
        topic = (Topic) ctx.lookup(topicName);
        connection = connectionFactory.createTopicConnection();
        subscriberSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        topicSubscriber = subscriberSession.createSubscriber(topic);
        topicSubscriber.setMessageListener(this);
        connection.start();
    }

    /*
     On message
    */
    @Override
    public void onMessage(javax.jms.Message msg) {
        String buttonID = messageManager.getLocationEngaged().getAlternateName() + messageManager.getLocationEngaged().getId();
        String jsToExecute = "document.getElementsByClassName(" + buttonID + ")[0].click()";
        messageManager.messagesBetweenSelectedLocationAndUser();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute(jsToExecute);
    }

    /*
     exit
    */
    public void exit() {
        try {
            connection.close();
        } catch (JMSException je) {
            System.out.println("JMSExceptionrred");
        }
        System.exit(0);
    }
}
