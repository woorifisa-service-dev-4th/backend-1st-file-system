package filemanager.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUtils {

    public static boolean moveFileOrDirectory(File source, File destination) {
        try {
            Files.move(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            System.out.println("오류: 파일을 이동할 수 없습니다. " + e.getMessage());
            return false;
        }
    }
}
