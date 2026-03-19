package android.os;

public class ServiceManagerStub {
    public static IBinder waitForService(String name) {
        throw new RuntimeException("Stub!");
    }
}
