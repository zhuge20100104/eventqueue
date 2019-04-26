package com.oracle.test.servers;

public class ThreadServer extends Thread {
    private Thread serverThread;
    protected boolean running = false;

    public ThreadServer() {
        this.serverThread = null;
        this.running = false;
    }

    public void startServer() {
        if(this.running == true) {
            return;
        }

        this.running = true;
        this.serverThread = new Thread(this);
        this.serverThread.start();
    }


    public void stopServer() {
        this.running = false;
    }


}
