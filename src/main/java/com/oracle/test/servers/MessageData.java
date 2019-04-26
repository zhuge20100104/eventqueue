package com.oracle.test.servers;

public class MessageData {
    public MessageCallBack  messageCallBack;
    public String fromName;
    public String Message;

    @Override
    public boolean equals(Object obj) {
        MessageData data = (MessageData) obj;
        return data.fromName.equals(this.fromName) && data.Message.equals(this.Message) &&
                data.messageCallBack.equals(this.messageCallBack);
    }
}
