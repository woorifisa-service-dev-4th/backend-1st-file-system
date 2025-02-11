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

    // public void createDirectory(String path) throws FileSystemException {
    //     if (vfs.exists(path)) {
    //         throw new FileSystemException("Directory already exists: " + path);
    //     }
        
    //     vfs.addFile(path, new VirtualDirectory(path));
    
    //     // ✅ 디버깅 로그 추가
    //     System.out.println("[DEBUG] Directory created: " + path);
    //     System.out.println("[DEBUG] Current files: " + vfs.getFiles().keySet());
    // }


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
        if (path.equals("..")) { // ✅ 부모 디렉토리로 이동
            String currentPath = vfs.getCurrentPath();
            if (currentPath.equals("/")) {
                throw new FileSystemException("Already at root directory.");
            }
    
            int lastSlash = currentPath.lastIndexOf('/');
            String parentPath = (lastSlash <= 0) ? "/" : currentPath.substring(0, lastSlash);
            vfs.setCurrentPath(parentPath);
            return;
        }
    
        // ✅ 상대 경로 변환
        if (!path.startsWith("/")) {
            path = vfs.getCurrentPath() + (vfs.getCurrentPath().equals("/") ? "" : "/") + path;
        }
    
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
        boolean longFormat = false;
        boolean showHidden = false;
        boolean recursive = false;
    
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
    
        List<VirtualFile> fileList = vfs.getDirectoryContents(currentPath);
        // System.out.println("[DEBUG] Directory Contents of " + currentPath + ": " + fileList.size() + " items");
    
        if (fileList.isEmpty()) {
            System.out.println("Directory is empty.");
            return;
        }
    
        for (VirtualFile file : fileList) {
            String fileName = file.getPath().substring(currentPath.length() + (currentPath.equals("/") ? 0 : 1));
    
            if (!showHidden && fileName.startsWith(".")) {
                continue;
            }
    
            // System.out.println("[DEBUG] Listing: " + file.getPath());
    
            if (longFormat) {
                System.out.printf("%-10s %-8s %-8s %10d %s\n",
                        vfs.getFilePermissions(file.getPath()),
                        vfs.getFileOwner(file.getPath()),
                        vfs.getFileGroup(file.getPath()),
                        vfs.getFileSize(file.getPath()),
                        fileName);
            } else {
                System.out.println(fileName);
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

        String currentPath = vfs.getCurrentPath(); // ✅ 현재 경로 가져오기

        if (!path.startsWith("/")) {
            path = currentPath + (currentPath.equals("/") ? "" : "/") + path; // ✅ 상대 경로 변환
        }

        if (createParents) {
            String[] parts = path.split("/");
            StringBuilder parentPath = new StringBuilder("/");

            for (String part : parts) {
                if (part.isEmpty()) continue;
                if (!parentPath.toString().equals("/")) {
                    parentPath.append("/");
                }
                parentPath.append(part);

                if (!vfs.exists(parentPath.toString())) {
                    vfs.addFile(parentPath.toString(), new VirtualDirectory(parentPath.toString()));
                    // System.out.println("[DEBUG] Created directory: " + parentPath);
                }
            }
        } else {
            vfs.addFile(path, new VirtualDirectory(path));
            // System.out.println("[DEBUG] Created directory: " + path);
        }
        // vfs.printDebugFileSystem();
    }


}
