package explorer.operations;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

/**
 * FileOperations - 파일 관련 기능을 제공하는 클래스
 */
public class FileOperations {

    public static void moveFileOrDirectory(Path currentPath, String source, String target) {
        Path sourcePath = currentPath.resolve(source);
        Path targetPath = currentPath.resolve(target);
        if (!Files.exists(sourcePath)) {
            System.out.println("오류: 파일 또는 폴더를 찾을 수 없습니다.");
            return;
        }
        try {
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("파일이 " + targetPath + "로 이동되었습니다.");
        } catch (IOException e) {
            System.out.println("오류: 파일을 이동할 수 없습니다.");
        }
    }

    public static void readFile(Path currentPath, String fileName) {
        Path filePath = currentPath.resolve(fileName);
        if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
            System.out.println("오류: 파일을 찾을 수 없습니다.");
            return;
        }
        try {
            // 파일 내용을 한 줄씩 출력 (try-with-resources 사용)
            System.out.println(" " + fileName + " 파일 내용:");

            try (Stream<String> lines = Files.lines(filePath)) {
                lines.forEach(System.out::println);
            }
        } catch (IOException e) {
            System.out.println("오류: 파일을 읽을 수 없습니다. " + e.getMessage());
        }
    }

    public static void createFile(Path currentPath, String fileName) {
        Path filePath = currentPath.resolve(fileName);
        try {
            Files.createFile(filePath);
            System.out.println("파일이 생성되었습니다: " + filePath);
        } catch (IOException e) {
            System.out.println("오류: 파일을 생성할 수 없습니다.");
        }
    }

    public static void deleteFileOrDirectory(Path currentPath, String target) {
        Path targetPath = currentPath.resolve(target);
        if (!Files.exists(targetPath)) {
            System.out.println("오류: 파일 또는 폴더를 찾을 수 없습니다.");
            return;
        }
        try {
            if (Files.isDirectory(targetPath)) {
                Files.walkFileTree(targetPath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
                System.out.println("디렉토리가 삭제되었습니다: " + targetPath);
            } else {
                Files.delete(targetPath);
                System.out.println("파일이 삭제되었습니다: " + targetPath);
            }
        } catch (IOException e) {
            System.out.println("오류: 파일 또는 폴더를 삭제할 수 없습니다.");
        }
    }
}