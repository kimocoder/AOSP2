package android.os;

import java.io.File;

public class FileUtilsStub {
    public static long parseSize(String sizeString) {
        String s = sizeString.trim().toUpperCase();
        long multiplier = 1;
        if (s.endsWith("MB")) {
            multiplier = 1024L * 1024L;
            s = s.substring(0, s.length() - 2);
        } else if (s.endsWith("GB")) {
            multiplier = 1024L * 1024L * 1024L;
            s = s.substring(0, s.length() - 2);
        } else if (s.endsWith("KB")) {
            multiplier = 1024L;
            s = s.substring(0, s.length() - 2);
        }
        return Long.parseLong(s.trim()) * multiplier;
    }

    public static boolean deleteContentsAndDir(File dir) {
        if (dir == null || !dir.exists()) return false;
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteContentsAndDir(file);
                } else {
                    file.delete();
                }
            }
        }
        return dir.delete();
    }
}
