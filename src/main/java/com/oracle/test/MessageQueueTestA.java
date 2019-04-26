package com.oracle.test;

import com.oracle.test.servers.EventQueueServer;
import com.oracle.test.servers.MessageCallBack;
import com.oracle.test.servers.MessageData;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MessageQueueTestA {

    private EventQueueServer eventServer;
    @BeforeTest
    void before() {
        eventServer = new EventQueueServer();

        MessageData data = new MessageData();
        eventServer.subscribe("A");
        eventServer.startServer();
    }

    @Test
    void printAMessage() {
        System.out.println("Print A Message");
        eventServer.publish("A", "MessageA", new MessageCallBack() {
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

