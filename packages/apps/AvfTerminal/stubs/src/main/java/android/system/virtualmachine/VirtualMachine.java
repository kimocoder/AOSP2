package android.system.virtualmachine;

import android.os.ParcelFileDescriptor;
import android.view.KeyEvent;
import android.view.MotionEvent;

import java.io.InputStream;
import java.util.concurrent.Executor;

public class VirtualMachine {
    public static final int STATUS_RUNNING = 2;

    public String getName() { throw new RuntimeException("Stub!"); }
    public VirtualMachineConfig getConfig() { throw new RuntimeException("Stub!"); }
    public void setConfig(VirtualMachineConfig config) throws VirtualMachineException { throw new RuntimeException("Stub!"); }
    public void setCallback(Executor executor, VirtualMachineCallback callback) { throw new RuntimeException("Stub!"); }
    public void run() throws VirtualMachineException { throw new RuntimeException("Stub!"); }
    public void stop() { throw new RuntimeException("Stub!"); }
    public int getStatus() { throw new RuntimeException("Stub!"); }
    public InputStream getConsoleOutput() throws VirtualMachineException { throw new RuntimeException("Stub!"); }
    public InputStream getLogOutput() throws VirtualMachineException { throw new RuntimeException("Stub!"); }
    public void setMemoryBalloonByPercent(int percent) { throw new RuntimeException("Stub!"); }
    public boolean sendKeyEvent(KeyEvent event) { throw new RuntimeException("Stub!"); }
    public boolean sendMouseEvent(MotionEvent event) { throw new RuntimeException("Stub!"); }
    public boolean sendMultiTouchEvent(MotionEvent event) { throw new RuntimeException("Stub!"); }
    public boolean sendTrackpadEvent(MotionEvent event) { throw new RuntimeException("Stub!"); }
    public void sendTabletModeEvent(boolean tabletMode) { throw new RuntimeException("Stub!"); }
}
