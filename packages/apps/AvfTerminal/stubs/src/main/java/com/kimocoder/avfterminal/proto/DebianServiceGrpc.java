package com.kimocoder.avfterminal.proto;

import io.grpc.stub.StreamObserver;

public class DebianServiceGrpc {
    public static abstract class DebianServiceImplBase implements io.grpc.BindableService {
        public void reportVmActivePorts(ReportVmActivePortsRequest request,
                StreamObserver<ReportVmActivePortsResponse> responseObserver) {
        }

        public void openForwardingRequestQueue(QueueOpeningRequest request,
                StreamObserver<ForwardingRequestItem> responseObserver) {
        }

        public void openShutdownRequestQueue(ShutdownQueueOpeningRequest request,
                StreamObserver<ShutdownRequestItem> responseObserver) {
        }

        public void openStorageBalloonRequestQueue(StorageBalloonQueueOpeningRequest request,
                StreamObserver<StorageBalloonRequestItem> responseObserver) {
        }

        @Override
        public io.grpc.ServerServiceDefinition bindService() {
            throw new RuntimeException("Stub!");
        }
    }
}
