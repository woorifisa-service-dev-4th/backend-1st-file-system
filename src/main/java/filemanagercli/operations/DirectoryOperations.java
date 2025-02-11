package filemanagercli.operations;

import filemanagercli.VirtualFileSystem;
import filemanagercli.models.VirtualDirectory;
import filemanagercli.models.VirtualFile;
import filemanagercli.exceptions.FileSystemException;
import java.util.List;

/**
 * 디렉토리 조작 기능 (ls, cd, mkdir, rmdir, tree)
 */
public class DirectoryOperations {
    private final VirtualFileSystem vfs;

    public DirectoryOperations(VirtualFileSystem vfs) {
        this.vfs = vfs;
    }

    public void createDirectory(String path) throws FileSystemException {
        if (vfs.exists(path)) {
            throw new FileSystemException("Directory already exists: " + path);
        }
        vfs.addFile(path, new VirtualDirectory(path));
    }

    public void removeDirectory(String path) throws FileSystemException {
        if (!vfs.exists(path)) {
            throw new FileSystemException("Directory not found: " + path);
        }
        VirtualFile file = vfs.getFile(path);
        if (!(file instanceof VirtualDirectory)) {
            throw new FileSystemException("Not a directory: " + path);
        }
        VirtualDirectory dir = (VirtualDirectory) file;
        if (!dir.getChildren().isEmpty()) {
            throw new FileSystemException("Directory is not empty: " + path);
        }
        vfs.deleteFile(path);
    }

    public void changeDirectory(String path) throws FileSystemException {
        if (!vfs.exists(path)) {
            throw new FileSystemException("No such directory: " + path);
        }
        if (!vfs.getFile(path).isDirectory()) {
            throw new FileSystemException("Not a directory: " + path);
        }
        vfs.setCurrentPath(path);
    }

    public void listFiles(String[] args) {
        String currentPath = vfs.getCurrentPath();
        List<String> files = vfs.listFiles(currentPath);
        for (String file : files) {
            System.out.println(file);
        }
    }

    public void printCurrentDirectory() {
        System.out.println(vfs.getCurrentPath());
    }
}
