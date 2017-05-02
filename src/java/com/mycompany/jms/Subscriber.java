/*
 * Created by Divyansh Gupta on 2017.05.01  * 
 * Copyright © 2017 Divyansh Gupta. All rights reserved. * 
 */
package com.mycompany.jms;

import javax.jms.JMSException;
import javax.jms.Message;
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
    public void onMessage(Message msg) {
        try {
            TextMessage txtMsg = (TextMessage) msg;
            String text = txtMsg.getText();

            if (!text.equalsIgnoreCase("exit")) {
                System.out.println("Notice text: " + text);
            } else {
                System.out.println("Good");
            }
        } catch (JMSException je) {
            je.printStackTrace();
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
