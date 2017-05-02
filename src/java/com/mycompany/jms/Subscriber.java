/*
 * Created by Divyansh Gupta on 2017.05.01  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.jms;

import com.mycompany.DisasterRecovery.Location;
import com.mycompany.DisasterRecovery.Message;
import com.mycompany.Managers.MessageManager;
import com.mycompany.sessionbeans.LocationFacade;
import com.mycompany.sessionbeans.MessageFacade;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSubscriber;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Subscriber implements MessageListener {

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

    public Subscriber(String uname, String pwd) throws NamingException, JMSException {
        InitialContext ctx = new InitialContext();
        connectionFactory = (TopicConnectionFactory) ctx.lookup("jms/DisasterRecoveryConnectionFactory");
        topic = (Topic) ctx.lookup(topicName);
        connection = connectionFactory.createTopicConnection(uname, pwd);
        subscriberSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        topicSubscriber = subscriberSession.createSubscriber(topic);
        topicSubscriber.setMessageListener(this);
        connection.start();
    }

    @Override
    public void onMessage(javax.jms.Message msg) {
        TextMessage txtMsg = (TextMessage) msg;
//            String msgTxt = txtMsg.getText();
//            Location senderLocation = locationFacade.findById(txtMsg.getIntProperty("SenderLocationId"));
//            Location recieverLocation = locationFacade.findById(txtMsg.getIntProperty("RecieverLocationId"));
//            Date timestamp = new Date(msg.getJMSTimestamp());
        
        try {
            Message message = messageFacade.find(Integer.valueOf(txtMsg.getJMSMessageID()));
            Map<Location, Map<Integer, Message>> toLocationMessageMap = messageManager.getLocationMessagesMap();
            
        } catch (JMSException ex) {
            Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exit() {
        try {
            connection.close();
        } catch (JMSException je) {
            System.out.println("JMSExceptionrred");
        }
        System.exit(0);
    }
}
