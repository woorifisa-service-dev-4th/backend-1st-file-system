package filemanager.utils;

import java.io.File;
import java.io.IOException;

public class PathUtils {

    public static String getCanonicalPath(File path) {
        try {
            return path.getCanonicalPath();
        } catch (IOException e) {
            System.out.println("오류: 경로를 변환할 수 없습니다.");
            return path.getAbsolutePath();
        }
    }

    public static File getParentDirectory(File currentDirectory) {
        return currentDirectory.getParentFile();
    }

    public static boolean isValidDirectory(File directory) {
        return directory.exists() && directory.isDirectory();
    }
}
