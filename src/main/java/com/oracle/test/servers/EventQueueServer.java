package com.oracle.test.servers;



import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


public class EventQueueServer extends ThreadServer {

    private  static final EventQueueServer instance = new EventQueueServer();

    public  static EventQueueServer getServer() {
        return instance;
    }

    private Queue<Message> clientMessages;

    private Map<String, MessageData> eventMap;

    private EventQueueServer() {
        super();
        clientMessages = new ArrayBlockingQueue<Message>(3000);
        eventMap = new HashMap<String, MessageData>();
    }



    public void subscribe(String fromName,MessageData messageData) {
        this.eventMap.put(fromName, messageData);

    }

    public void unsubscribe(String fromName) {
        this.eventMap.remove(fromName);
    }


    public void publish(String fromName, String message) {
        Message msg = new Message();
        msg.fromName = fromName;
        msg.message = message;
        this.clientMessages.add(msg);
    }


    @Override
    public void run() {
        while (this.running) {
            Message msg = this.clientMessages.poll();

            if(msg!=null) {
                if (eventMap.size() > 0) {
                    for (Map.Entry<String, MessageData> entry : eventMap.entrySet()) {
                        System.out.println("Send msg to: " + entry.getKey());
                        MessageData data0 = entry.getValue();
                        data0.messageCallBack.handle(msg);

                    }
                }
            }
        }
    }
}
