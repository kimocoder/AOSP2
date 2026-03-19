package android.system.virtualizationservice_internal;

import android.os.IBinder;

public interface IVirtualizationServiceInternal {
    IBinder waitDisplayService();

    abstract class Stub implements IVirtualizationServiceInternal {
        public static IVirtualizationServiceInternal asInterface(IBinder binder) {
            throw new RuntimeException("Stub!");
        }
    }
}
