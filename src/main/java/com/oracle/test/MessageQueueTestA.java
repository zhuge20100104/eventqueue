package com.oracle.test;

import com.oracle.test.servers.EventQueueServer;
import com.oracle.test.servers.Message;
import com.oracle.test.servers.MessageCallBack;
import com.oracle.test.servers.MessageData;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MessageQueueTestA {

    private EventQueueServer eventServer;
    @BeforeTest
    void before() {
        eventServer = EventQueueServer.getServer();


    }

    @Test
    void printAMessage() {

        MessageData data = new MessageData();
        data.message.fromName = "A";
        data.message.message = "";
        data.messageCallBack = new MessageCallBack() {
            @Override
            public void handle(Message message) {
                System.out.println(message.message);
                aHandler();
            }
        };

        eventServer.subscribe("A",data);
        eventServer.startServer();
        eventServer.publish("A", "MessageA" );
    }

    private void aHandler() {
        System.out.println("In aHandler");
        System.out.println("Print A Message");
    }

    @AfterTest
    void after() {
        eventServer.stopServer();
    }
}

