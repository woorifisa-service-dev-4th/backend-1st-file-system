package filemanagercli.operations;

import filemanagercli.VirtualFileSystem;
import filemanagercli.models.VirtualFile;
import filemanagercli.exceptions.FileSystemException;

/**
 * 파일 조작 관련 기능 (touch, rm, mv, cp, cat)
 */
public class FileOperations {
    private final VirtualFileSystem vfs;

    public FileOperations(VirtualFileSystem vfs) {
        this.vfs = vfs;
    }

    public void createFile(String path) throws FileSystemException {
        if (vfs.exists(path)) {
            throw new FileSystemException("File already exists: " + path);
        }
        vfs.addFile(path, new VirtualFile(path, false));
    }

    public void deleteFile(String path) throws FileSystemException {
        if (!vfs.exists(path)) {
            throw new FileSystemException("File not found: " + path);
        }
        vfs.deleteFile(path);
    }

    public void moveFile(String source, String destination) throws FileSystemException {
        if (!vfs.exists(source)) {
            throw new FileSystemException("Source file not found: " + source);
        }
        VirtualFile file = vfs.getFile(source);
        vfs.deleteFile(source);
        vfs.addFile(destination, file);
    }

    public void copyFile(String source, String destination) throws FileSystemException {
        if (!vfs.exists(source)) {
            throw new FileSystemException("Source file not found: " + source);
        }
        VirtualFile original = vfs.getFile(source);
        vfs.addFile(destination, new VirtualFile(destination, original.isDirectory()));
    }

    public void readFile(String path) throws FileSystemException {
        if (!vfs.exists(path)) {
            throw new FileSystemException("File not found: " + path);
        }
        if (vfs.getFile(path).isDirectory()) {
            throw new FileSystemException("Cannot read a directory: " + path);
        }
        System.out.println("Reading file: " + path + " (dummy content)");
    }
}
