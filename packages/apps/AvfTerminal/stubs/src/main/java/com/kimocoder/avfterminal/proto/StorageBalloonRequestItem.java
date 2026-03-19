package com.kimocoder.avfterminal.proto;

public class StorageBalloonRequestItem {
    public static Builder newBuilder() { return new Builder(); }

    public static class Builder {
        public Builder setAvailableBytes(long bytes) { return this; }
        public StorageBalloonRequestItem build() { return new StorageBalloonRequestItem(); }
    }
}
