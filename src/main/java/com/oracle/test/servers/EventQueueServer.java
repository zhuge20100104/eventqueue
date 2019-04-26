package com.oracle.test.servers;

import sun.plugin2.message.Message;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class EventQueueServer extends ThreadServer {

    private static final int THREAD_NUM = 8;


    private ExecutorService executor;

    private Map<String, Queue<MessageData>> eventMap;

    public EventQueueServer() {
        super();
        eventMap = new HashMap<String, Queue<MessageData>>();
        executor = Executors.newFixedThreadPool(THREAD_NUM);
    }

    public static class MessageHandler implements Runnable {

        private MessageData data;

        public MessageHandler(MessageData data) {
            this.data = data;
        }


        @Override
        public void run() {
            data.messageCallBack.handle(data.Message);
        }
    }


    public void subscribe(String fromName) {
        this.eventMap.put(fromName, new ArrayBlockingQueue<MessageData>(1000));

    }

    public void unsubscribe(String fromName) {
        this.eventMap.remove(fromName);
    }


    public void publish(String fromName, String message, MessageCallBack callBack) {
        MessageData data = new MessageData();
        data.fromName = fromName;
        data.Message = message;
        data.messageCallBack = callBack;
        Queue<MessageData> eventQueue = this.eventMap.get(fromName);
        eventQueue.add(data);

    }


    @Override
    public void run() {
        while (this.running) {
//            MessageData data = this.mQueue.poll();

            if(eventMap.size()>0) {
                for(Map.Entry<String, Queue<MessageData>> entry: eventMap.entrySet()) {
                    Queue<MessageData> datas = entry.getValue();
                    if(datas.size() > 0) {
                        MessageData data = datas.poll();
                        executor.submit(new MessageHandler(data), data);
                    }
                }
            }
        }
    }
}
