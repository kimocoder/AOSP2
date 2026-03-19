package com.kimocoder.avfterminal.proto;

public class ReportVmActivePortsResponse {
    public static Builder newBuilder() { return new Builder(); }

    public static class Builder {
        public Builder setSuccess(boolean success) { return this; }
        public ReportVmActivePortsResponse build() { return new ReportVmActivePortsResponse(); }
    }
}
