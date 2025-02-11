package filemanagercli.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSystemUtils {
    public static boolean fileExists(String path) {
        return new File(path).exists();
    }

    public static boolean isDirectory(String path) {
        return new File(path).isDirectory();
    }

    public static String readFile(String path) throws Exception {
        return Files.readString(Paths.get(path));
    }
}
