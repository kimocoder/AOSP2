package android.crosvm;

import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.view.Surface;

public interface ICrosvmAndroidDisplayService {
    void setSurface(Surface surface, boolean forCursor) throws RemoteException;
    void removeSurface(boolean forCursor) throws RemoteException;
    void drawSavedFrameForSurface(boolean forCursor) throws RemoteException;
    void saveFrameForSurface(boolean forCursor) throws RemoteException;
    void setCursorStream(ParcelFileDescriptor pfd) throws RemoteException;

    abstract class Stub implements ICrosvmAndroidDisplayService {
        public static ICrosvmAndroidDisplayService asInterface(IBinder binder) {
            throw new RuntimeException("Stub!");
        }
    }
}
