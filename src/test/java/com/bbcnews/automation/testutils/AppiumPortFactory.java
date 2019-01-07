package com.bbcnews.automation.testutils;

import java.net.ServerSocket;

public class AppiumPortFactory {
    public static int create() throws Exception {
        ServerSocket socket = new ServerSocket(0);
        socket.setReuseAddress(true);
        int port = socket.getLocalPort();
        socket.close();
        return port;
    }

    public static void main(String[] args) throws Exception
    {
        AppiumPortFactory portFactory = new AppiumPortFactory();
        for (int i = 0; i < 10; i++)
            System.out.println(portFactory.create());
    }

}