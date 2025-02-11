package filemanagercli.operations;

import filemanagercli.VirtualFileSystem;
import filemanagercli.models.VirtualFile;
import filemanagercli.exceptions.FileSystemException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 파일 검색 관련 기능 (find, grep)
 */
public class FileSearchOperations {
    private final VirtualFileSystem vfs;

    public FileSearchOperations(VirtualFileSystem vfs) {
        this.vfs = vfs;
    }

    /**
     * 특정 키워드가 포함된 파일 검색 (find 명령어)
     */
    public void findFiles(String keyword) {
        List<String> results = vfs.getFiles().keySet().stream()
                .filter(path -> path.contains(keyword))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("No files found matching: " + keyword);
        } else {
            results.forEach(System.out::println);
        }
    }

    /**
     * 특정 파일 내에서 키워드 검색 (grep 명령어)
     */
    public void grep(String keyword, String filePath) throws FileSystemException {
        if (!vfs.exists(filePath)) {
            throw new FileSystemException("File not found: " + filePath);
        }
        if (vfs.getFile(filePath).isDirectory()) {
            throw new FileSystemException("Cannot grep a directory: " + filePath);
        }

        String content = vfs.getFileContent(filePath);
        String[] lines = content.split("\n");
        for (String line : lines) {
            if (line.contains(keyword)) {
                System.out.println(line);
            }
        }
    }
}
