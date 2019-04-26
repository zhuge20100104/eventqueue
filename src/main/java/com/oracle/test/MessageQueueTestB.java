package com.oracle.test;

import com.oracle.test.servers.EventQueueServer;
import com.oracle.test.servers.Message;
import com.oracle.test.servers.MessageCallBack;
import com.oracle.test.servers.MessageData;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MessageQueueTestB {

    private EventQueueServer eventServer;
    @BeforeTest
    void before() {
        eventServer = EventQueueServer.getServer();
    }

    @Test
    void printBMessage() {
        MessageData data = new MessageData();
        data.message.fromName = "B";
        data.message.message = "";

        data.messageCallBack = new MessageCallBack() {
            @Override
            public void handle(Message message) {
                System.out.println(message.message);
                bHandler();
            }
        };

        eventServer.subscribe("B",data);
        eventServer.startServer();

        eventServer.publish("B", "MessageB" );
    }

    private void bHandler() {
        System.out.println("In bHandler");
        System.out.println("Print B Message");
    }

    @AfterTest
    void after() {
        eventServer.stopServer();
    }
}
