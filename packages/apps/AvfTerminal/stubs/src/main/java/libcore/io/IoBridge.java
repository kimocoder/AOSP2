package libcore.io;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

public class IoBridge {
    public static int read(FileDescriptor fd, byte[] bytes, int offset, int length) throws IOException {
        FileInputStream fis = new FileInputStream(fd);
        return fis.read(bytes, offset, length);
    }
}
