package com.kimocoder.avfterminal.proto;

public class ActivePort {
    private int port;
    private String comm;

    public int getPort() { return port; }
    public String getComm() { return comm; }

    public static ActivePort getDefaultInstance() { return new ActivePort(); }
}
