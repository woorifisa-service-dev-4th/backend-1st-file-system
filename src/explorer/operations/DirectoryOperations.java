package explorer.operations;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DirectoryOperations {
    public static void listDirectory(Path currentPath, String subPath) {
        Path path = subPath.isEmpty() ? currentPath : currentPath.resolve(subPath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            System.out.println("오류: 디렉토리를 찾을 수 없습니다.");
            return;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            List<Path> directories = new ArrayList<>();
            List<Path> files = new ArrayList<>();
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    directories.add(entry);
                } else {
                    files.add(entry);
                }
            }
            directories.sort(Comparator.comparing(p -> p.getFileName().toString()));
            files.sort(Comparator.comparing(p -> p.getFileName().toString()));

            for (Path dir : directories) {
                System.out.println("[D] " + dir.getFileName());
            }
            for (Path file : files) {
                System.out.println("[F] " + file.getFileName());
            }
        } catch (IOException e) {
            System.out.println("오류: 디렉토리를 읽을 수 없습니다.");
        }
    }

    public static Path changeDirectory(Path currentPath, String newDir) {
        Path newPath = currentPath.resolve(newDir).normalize();
        if (Files.exists(newPath) && Files.isDirectory(newPath)) {
            return newPath;
        } else {
            System.out.println("오류: 디렉토리를 찾을 수 없습니다.");
            return currentPath;
        }
    }
}
