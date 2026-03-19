package android.system.virtualmachine;

public class VirtualMachineCustomImageConfig {
    public String getName() { throw new RuntimeException("Stub!"); }
    public boolean useTouch() { throw new RuntimeException("Stub!"); }
    public boolean useMouse() { throw new RuntimeException("Stub!"); }
    public boolean useKeyboard() { throw new RuntimeException("Stub!"); }
    public boolean useTrackpad() { throw new RuntimeException("Stub!"); }
    public boolean useSwitches() { throw new RuntimeException("Stub!"); }

    public static class Builder {
        public Builder() {}
        public Builder setName(String name) { return this; }
        public Builder setBootloaderPath(String path) { return this; }
        public Builder setKernelPath(String path) { return this; }
        public Builder setInitrdPath(String path) { return this; }
        public Builder useNetwork(boolean network) { return this; }
        public Builder useAutoMemoryBalloon(boolean auto) { return this; }
        public Builder useTouch(boolean touch) { return this; }
        public Builder useKeyboard(boolean keyboard) { return this; }
        public Builder useMouse(boolean mouse) { return this; }
        public Builder useTrackpad(boolean trackpad) { return this; }
        public Builder useSwitches(boolean switches) { return this; }
        public Builder setAudioConfig(AudioConfig config) { return this; }
        public Builder setDisplayConfig(DisplayConfig config) { return this; }
        public Builder setGpuConfig(GpuConfig config) { return this; }
        public Builder addParam(String param) { return this; }
        public Builder addDisk(Disk disk) { return this; }
        public Builder addSharedPath(SharedPath path) { return this; }
        public VirtualMachineCustomImageConfig build() { throw new RuntimeException("Stub!"); }
    }

    public static class AudioConfig {
        public static class Builder {
            public Builder() {}
            public Builder setUseSpeaker(boolean use) { return this; }
            public Builder setUseMicrophone(boolean use) { return this; }
            public AudioConfig build() { throw new RuntimeException("Stub!"); }
        }
    }

    public static class DisplayConfig {
        public static class Builder {
            public Builder() {}
            public Builder setWidth(int width) { return this; }
            public Builder setHeight(int height) { return this; }
            public Builder setHorizontalDpi(int dpi) { return this; }
            public Builder setVerticalDpi(int dpi) { return this; }
            public Builder setRefreshRate(int rate) { return this; }
            public DisplayConfig build() { throw new RuntimeException("Stub!"); }
        }
    }

    public static class GpuConfig {
        public static class Builder {
            public Builder() {}
            public Builder setBackend(String backend) { return this; }
            public Builder setPciAddress(String address) { return this; }
            public Builder setRendererFeatures(String features) { return this; }
            public Builder setRendererUseEgl(boolean use) { return this; }
            public Builder setRendererUseGles(boolean use) { return this; }
            public Builder setRendererUseGlx(boolean use) { return this; }
            public Builder setRendererUseSurfaceless(boolean use) { return this; }
            public Builder setRendererUseVulkan(boolean use) { return this; }
            public Builder setContextTypes(String[] types) { return this; }
            public GpuConfig build() { throw new RuntimeException("Stub!"); }
        }
    }

    public static abstract class Disk {
        public void addPartition(Partition partition) { throw new RuntimeException("Stub!"); }

        public static class RWDisk extends Disk {
            public RWDisk(String path) {}
        }
        public static class RODisk extends Disk {
            public RODisk(String path) {}
        }
    }

    public static class Partition {
        public Partition(String label, String path, boolean writable, String guid) {}
    }

    public static class SharedPath {
        public SharedPath(String path, int hostUid, int hostGid, int guestUid, int guestGid,
                         int mask, String tag, String socketTag, boolean appDomain, String socketPath) {}
    }
}
