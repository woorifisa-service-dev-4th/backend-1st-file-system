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

    public void printCurrentDirectory() {
        System.out.println(vfs.getCurrentPath());
    }

    /**
     * 디렉토리 트리 출력 (tree 명령어)
     */
    public void printTree(String path, int level) throws FileSystemException {
        if (!vfs.exists(path)) {
            throw new FileSystemException("Error: Directory not found: " + path);
        }

        VirtualFile file = vfs.getFile(path);
        if (!file.isDirectory()) {
            throw new FileSystemException("Error: Not a directory: " + path);
        }

        System.out.println((level == 0 ? "" : "  ".repeat(level) + "|-- ") + file.getPath());

        VirtualDirectory dir = (VirtualDirectory) file;
        for (VirtualFile child : dir.getChildren()) {
            printTree(child.getPath(), level + 1);
        }
    }

    public void printTreeCommand(String path) {
        try {
            if (path == null) {
                path = vfs.getCurrentPath();
            }
            printTree(path, 0);
        } catch (FileSystemException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 파일 목록 출력 (ls 명령어)
     */
    public void listFiles(String[] args) {
        String currentPath = vfs.getCurrentPath();
        boolean longFormat = false;  // -l 옵션
        boolean showHidden = false;  // -a 옵션
        boolean recursive = false;   // -R 옵션

        for (String arg : args) {
            switch (arg) {
                case "-l":
                    longFormat = true;
                    break;
                case "-a":
                    showHidden = true;
                    break;
                case "-R":
                    recursive = true;
                    break;
            }
        }

        List<String> files = vfs.listFiles(currentPath);

        if (files.isEmpty()) {
            System.out.println("Directory is empty.");
            return;
        }

        for (String file : files) {
            if (!showHidden && file.startsWith(".")) {
                continue; // 숨김 파일 제외
            }
            if (longFormat) {
                System.out.printf("%-10s %-8s %-8s %10d %s\n",
                        vfs.getFilePermissions(file),
                        vfs.getFile(file),
                        vfs.getFile(file),
                        vfs.getFile(file),
                        file);
            } else {
                System.out.println(file);
            }
        }

        if (recursive) {
            System.out.println("\nRecursive listing:");
            for (String file : files) {
                if (vfs.isDirectory(file)) {
                    System.out.println("\n" + file + "/");
                    listFiles(new String[]{"-l", "-a"});
                }
            }
        }
    }

    /**
     * 디렉토리 생성 (mkdir -p 지원)
     */
    public void createDirectory(String path, boolean createParents) throws FileSystemException {
        if (vfs.exists(path)) {
            throw new FileSystemException("Error: Directory already exists: " + path);
        }

        if (createParents) {
            String[] parts = path.split("/");
            StringBuilder parentPath = new StringBuilder();
            for (String part : parts) {
                if (part.isEmpty()) continue;
                parentPath.append("/").append(part);
                if (!vfs.exists(parentPath.toString())) {
                    vfs.addFile(parentPath.toString(), new VirtualDirectory(parentPath.toString()));
                }
            }
        } else {
            vfs.addFile(path, new VirtualDirectory(path));
        }
    }

}
