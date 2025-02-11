package filemanagercli.operations;

import filemanagercli.VirtualFileSystem;
import filemanagercli.exceptions.FileSystemException;

/**
 * 파일 내용 조작 기능 (cat, echo, nano)
 */
public class FileContentOperations {
    private final VirtualFileSystem vfs;

    public FileContentOperations(VirtualFileSystem vfs) {
        this.vfs = vfs;
    }

    /**
     * 파일 내용 읽기 (cat 명령어)
     */
    public void readFile(String filePath) throws FileSystemException {
        if (!vfs.exists(filePath)) {
            throw new FileSystemException("File not found: " + filePath);
        }
        if (vfs.getFile(filePath).isDirectory()) {
            throw new FileSystemException("Cannot read a directory: " + filePath);
        }

        String content = vfs.getFileContent(filePath);
        System.out.println("===== " + filePath + " =====");
        System.out.println(content.isEmpty() ? "(Empty file)" : content);
    }

    /**
     * 파일 내용 추가 (echo 명령어)
     */
    public void appendToFile(String filePath, String content) throws FileSystemException {
        if (!vfs.exists(filePath)) {
            throw new FileSystemException("File not found: " + filePath);
        }
        if (vfs.getFile(filePath).isDirectory()) {
            throw new FileSystemException("Cannot write to a directory: " + filePath);
        }

        vfs.appendToFile(filePath, content);
        System.out.println("Appended to file: " + filePath);
    }

    /**
     * 파일 편집 (nano 명령어) - 간단한 편집 기능
     */
    public void editFile(String filePath, String newContent) throws FileSystemException {
        if (!vfs.exists(filePath)) {
            throw new FileSystemException("File not found: " + filePath);
        }
        if (vfs.getFile(filePath).isDirectory()) {
            throw new FileSystemException("Cannot edit a directory: " + filePath);
        }

        vfs.updateFileContent(filePath, newContent);
        System.out.println("File updated: " + filePath);
    }
}
