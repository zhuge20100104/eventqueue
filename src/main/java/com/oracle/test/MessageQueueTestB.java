package com.oracle.test;

import com.oracle.test.servers.EventQueueServer;
import com.oracle.test.servers.MessageCallBack;
import com.oracle.test.servers.MessageData;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MessageQueueTestB {

    private EventQueueServer eventServer;
    @BeforeTest
    void before() {
        eventServer = new EventQueueServer();

        MessageData data = new MessageData();
        eventServer.subscribe("B");
        eventServer.startServer();
    }

    @Test
    void printBMessage() {
        System.out.println("Print B Message");
        eventServer.publish("B", "MessageB", new MessageCallBack() {
            @Override
            public void handle(String message) {
                System.out.println(message);
            }
        });
    }

    @AfterTest
    void after() {

        eventServer.stopServer();
    }
}
