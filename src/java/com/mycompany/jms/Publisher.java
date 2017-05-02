/*
 * Created by Divyansh Gupta on 2017.05.01  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.jms;

import java.util.Date;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Publisher {

    private TopicConnectionFactory connectionFactory;
    private TopicConnection topicConnection;
    private TopicSession topicSession;
    private final String topicName = "jms/DisasterRecoveryTopic";
    private Topic topic;
    private TopicPublisher topicPublisher;

    public Publisher(String uname, String pwd) throws NamingException, JMSException {
        InitialContext ctx = new InitialContext();
        connectionFactory = (TopicConnectionFactory) ctx.lookup("jms/DisasterRecoveryConnectionFactory");
        topic = (Topic) ctx.lookup(topicName);
        topicConnection = connectionFactory.createTopicConnection(uname, pwd);
        topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        topicPublisher = topicSession.createPublisher(topic);
        topicConnection.start();
    }

    public void closeConnection() throws JMSException {
        topicConnection.stop();
    }

    public void sendMessageToTopic(String msgText, int msgId, int senderLocation, int recieverLocation, Date date) throws JMSException {
        Message msg = topicSession.createTextMessage(msgText);
        msg.setJMSMessageID(String.valueOf(msgId));
        msg.setIntProperty("SenderLocationId", senderLocation);
        msg.setIntProperty("RecieverLocationId", recieverLocation);
        msg.setJMSTimestamp(date.getTime());
        topicPublisher.publish(msg);
    }
}
