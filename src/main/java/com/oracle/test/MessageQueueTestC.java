package com.oracle.test;

import com.oracle.test.servers.EventQueueServer;
import com.oracle.test.servers.Message;
import com.oracle.test.servers.MessageCallBack;
import com.oracle.test.servers.MessageData;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MessageQueueTestC {

    private EventQueueServer eventServer;
    @BeforeTest
    void before() {
        eventServer = EventQueueServer.getServer();


    }

    @Test
    void printCMessage() {

        MessageData data = new MessageData();
        data.message.fromName = "C";
        data.message.message = "";
        data.messageCallBack = new MessageCallBack() {
            @Override
            public void handle(Message message) {
                System.out.println(message.message);
                cHandler();
            }
        };

        eventServer.subscribe("C",data);
        eventServer.startServer();

    }


    private void cHandler() {
        System.out.println("In cHandler");
        System.out.println("Print C Message");
    }

    @AfterTest
    void after() {
        eventServer.stopServer();
    }
}
