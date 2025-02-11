package filemanagercli.operations;

import filemanagercli.VirtualFileSystem;
import filemanagercli.exceptions.FileSystemException;
import filemanagercli.models.VirtualDirectory;
import filemanagercli.models.VirtualFile;

import java.util.ArrayList;
import java.util.List;

public class DirectoryOperations {
    private final VirtualFileSystem vfs;

    public DirectoryOperations(VirtualFileSystem vfs) {
        this.vfs = vfs;
    }

    public void createDirectory(String path) {
        String absolutePath = vfs.resolvePath(path);
        if (vfs.exists(absolutePath)) {
            System.out.println("Error: Directory already exists: " + absolutePath);
            return;
        }
        vfs.addFile(absolutePath, true, "drwxr-xr-x", "root", "root", "");
        System.out.println("Created directory: " + absolutePath);
    }

    public void removeDirectory(String path) throws FileSystemException {
        if (!vfs.exists(path)) {
            throw new FileSystemException("Error: Directory not found: " + path);
        }

        VirtualFile file = vfs.getFiles().get(path);
        if (!(file instanceof VirtualDirectory)) {
            throw new FileSystemException("Error: Not a directory: " + path);
        }

        VirtualDirectory dir = (VirtualDirectory) file;
        if (!dir.getChildren().isEmpty()) {
            throw new FileSystemException("Error: Directory is not empty: " + path);
        }

        vfs.getFiles().remove(path);
        System.out.println("Deleted directory: " + path);
    }

    public void listFiles() {
        String currentPath = vfs.getCurrentPath();
        System.out.println("Current Directory: " + currentPath);

        List<String> files = getFilesInDirectory(currentPath);

        if (files.isEmpty()) {
            System.out.println("Directory is empty.");
        } else {
            for (String file : files) {
                System.out.println(file);
            }
        }
    }

    private List<String> getFilesInDirectory(String directory) {
        List<String> directories = new ArrayList<>();
        List<String> regularFiles = new ArrayList<>();

        for (String path : vfs.getFiles().keySet()) {
            if (path.startsWith(directory + "/")) {
                String[] parts = path.replace(directory + "/", "").split("/");
                if (parts.length == 1) {
                    VirtualFile file = vfs.getFiles().get(path);
                    String info = (file.isDirectory() ? "[DIR] " : "-rw-r--r--  root  root  ") + parts[0];

                    if (file.isDirectory()) {
                        directories.add(info);
                    } else {
                        regularFiles.add(info);
                    }
                }
            }
        }

        directories.sort(String::compareTo);
        regularFiles.sort(String::compareTo);

        List<String> result = new ArrayList<>();
        result.addAll(directories);
        result.addAll(regularFiles);

        return result;
    }

    public void printTree(String path) {
        if (path == null) {
            path = vfs.getCurrentPath();
        }

        if (!vfs.exists(path)) {
            System.out.println("Error: Directory not found: " + path);
            return;
        }
        if (!vfs.getFiles().get(path).isDirectory()) {
            System.out.println("Error: Not a directory: " + path);
            return;
        }

        System.out.println(path);
        printTreeRecursive(path, 1);
    }

    private void printTreeRecursive(String path, int level) {
        for (String key : vfs.getFiles().keySet()) {
            if (key.startsWith(path + "/")) {
                String[] parts = key.split("/");
                String name = parts[parts.length - 1];

                System.out.println("  ".repeat(level) + "|-- " + 
                    (vfs.getFiles().get(key).isDirectory() ? "[DIR] " : "") + name);

                if (vfs.getFiles().get(key).isDirectory()) {
                    printTreeRecursive(key, level + 1);
                }
            }
        }
    }

    public void getCurrentDirectory() {
        System.out.println(vfs.getCurrentPath());
    }

    public void changeDirectory(String path) {
        String newPath = vfs.resolvePath(path);
        if (path.equals("..")) {
            vfs.moveToParentDirectory();
            return;
        }

        if (!vfs.exists(newPath) || !vfs.getFiles().get(newPath).isDirectory()) {
            System.out.println("Error: No such directory: " + newPath);
            return;
        }
        vfs.setCurrentPath(newPath);
    }
}
