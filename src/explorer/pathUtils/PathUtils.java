package explorer.pathUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class PathUtils {
    public static Path getAbsolutePath(Path currentPath, String relativePath) {
        return currentPath.resolve(relativePath).normalize();
    }

    public static boolean exists(Path path) {
        return Files.exists(path);
    }

    public static boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }

    public static boolean isFile(Path path) {
        return Files.isRegularFile(path);
    }
}