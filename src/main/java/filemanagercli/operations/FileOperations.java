package filemanagercli.operations;

import java.util.List;
import java.util.stream.Collectors;

import filemanagercli.VirtualFileSystem;
import filemanagercli.models.VirtualFile;
import filemanagercli.exceptions.FileSystemException;

public class FileOperations {
    private final VirtualFileSystem vfs;

    public FileOperations(VirtualFileSystem vfs) {
        this.vfs = vfs;
    }

    public void createFile(String path) throws FileSystemException {
        String absolutePath = vfs.resolvePath(path);

        if (vfs.exists(absolutePath)) {
            throw new FileSystemException("File already exists: " + absolutePath);
        }

        vfs.addFile(absolutePath, false, "-rw-r--r--", "root", "root", "");
        System.out.println("Created file: " + absolutePath);
    }


    public void deleteFile(String path) throws FileSystemException {
        if (!vfs.exists(path)) {
            throw new FileSystemException("File not found: " + path);
        }
        vfs.deleteFile(path);
    }

    public void readFile(String path) throws FileSystemException {
        if (!vfs.exists(path)) {
            throw new FileSystemException("File not found: " + path);
        }
        if (vfs.getFiles().get(path).isDirectory()) {
            throw new FileSystemException("Cannot read a directory: " + path);
        }
        System.out.println("Reading file: " + path + " (dummy content)");
    }

    public void moveFile(String source, String destination) throws FileSystemException {
        if (!vfs.exists(source)) {
            throw new FileSystemException("Source file not found: " + source);
        }

        VirtualFile file = vfs.getFiles().remove(source);
        vfs.getFiles().put(destination, file);
        System.out.println("Moved file: " + source + " → " + destination);
    }

    public void copyFile(String source, String destination) throws FileSystemException {
        if (!vfs.exists(source)) {
            throw new FileSystemException("Source file not found: " + source);
        }
        VirtualFile original = vfs.getFiles().get(source);
        vfs.getFiles().put(destination, new VirtualFile(destination, original.isDirectory()));
    }

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

    public void readFiles(String[] paths) {
        for (String path : paths) {
            String absolutePath = vfs.resolvePath(path);
            if (!vfs.exists(absolutePath)) {
                System.out.println("Error: File not found: " + path);
                continue;
            }
            if (vfs.getFiles().get(absolutePath).isDirectory()) {
                System.out.println("Error: Cannot read a directory: " + path);
                continue;
            }
            String content = vfs.getFileContent(absolutePath);
            System.out.println("===== " + path + " =====");
            System.out.println(content.isEmpty() ? "(Empty file)" : content);
        }
    }
    
}
