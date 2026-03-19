package android.system.virtualmachine;

import android.content.Context;

public class VirtualMachineConfig {
    public static final int CPU_TOPOLOGY_ONE_CPU = 0;
    public static final int CPU_TOPOLOGY_MATCH_HOST = 1;
    public static final int DEBUG_LEVEL_NONE = 0;
    public static final int DEBUG_LEVEL_FULL = 1;

    public int getDebugLevel() { throw new RuntimeException("Stub!"); }
    public VirtualMachineCustomImageConfig getCustomImageConfig() { throw new RuntimeException("Stub!"); }

    public static class Builder {
        public Builder(Context context) {}
        public Builder setProtectedVm(boolean protectedVm) { return this; }
        public Builder setMemoryBytes(long memoryBytes) { return this; }
        public Builder setConsoleInputDevice(String device) { return this; }
        public Builder setCpuTopology(int cpuTopology) { return this; }
        public Builder setCustomImageConfig(VirtualMachineCustomImageConfig config) { return this; }
        public Builder setDebugLevel(int debugLevel) { return this; }
        public Builder setVmOutputCaptured(boolean captured) { return this; }
        public Builder setConnectVmConsole(boolean connect) { return this; }
        public VirtualMachineConfig build() { throw new RuntimeException("Stub!"); }
    }
}
