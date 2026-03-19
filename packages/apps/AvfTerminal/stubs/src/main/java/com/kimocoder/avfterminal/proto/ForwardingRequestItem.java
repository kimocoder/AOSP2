package com.kimocoder.avfterminal.proto;

public class ForwardingRequestItem {
    public static Builder newBuilder() { return new Builder(); }

    public static class Builder {
        public Builder setGuestTcpPort(int port) { return this; }
        public Builder setVsockPort(int port) { return this; }
        public ForwardingRequestItem build() { return new ForwardingRequestItem(); }
    }
}
