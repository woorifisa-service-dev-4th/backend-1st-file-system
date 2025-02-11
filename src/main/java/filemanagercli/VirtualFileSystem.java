package filemanagercli;

import filemanagercli.models.VirtualFile;
import filemanagercli.models.VirtualDirectory;
import filemanagercli.models.SymbolicLink;
import java.util.*;

/**
 * 가상의 파일 시스템을 관리하는 클래스
 */
public class VirtualFileSystem {
    private final Map<String, VirtualFile> files;
    private final Map<String, String> fileContents; // 파일 내용 저장
    private final Map<String, String> filePermissions; // 파일 권한
    private final Map<String, String> fileOwners; // 파일 소유자
    private final Map<String, String> fileGroups; // 파일 그룹
    private String currentPath = "/";

    public VirtualFileSystem() {
        this.files = new HashMap<>();
        this.fileContents = new HashMap<>();
        this.filePermissions = new HashMap<>();
        this.fileOwners = new HashMap<>();
        this.fileGroups = new HashMap<>();
        initializeRootDirectory();
    }

    private void initializeRootDirectory() {
        files.put("/", new VirtualDirectory("/"));
        filePermissions.put("/", "drwxr-xr-x");
        fileOwners.put("/", "root");
        fileGroups.put("/", "root");
    }

    public boolean exists(String path) {
        return files.containsKey(path);
    }

    /**
     * 파일 또는 디렉토리 추가
     */
    public void addFile(String path, VirtualFile file) {
        files.put(path, file);

        // ✅ 부모 디렉토리에 추가 (디렉토리 구조 유지)
        int lastSlash = path.lastIndexOf('/');
        if (lastSlash > 0) {
            String parentPath = path.substring(0, lastSlash);
            if (files.containsKey(parentPath) && files.get(parentPath) instanceof VirtualDirectory) {
                VirtualDirectory parent = (VirtualDirectory) files.get(parentPath);
                parent.addFile(file);
                System.out.println("[DEBUG] Added " + path + " to " + parentPath);
            } else {
                System.out.println("[DEBUG] Parent directory not found for: " + path);
            }
        }
    }




    public void deleteFile(String path) {
        files.remove(path);
        fileContents.remove(path);
        filePermissions.remove(path);
        fileOwners.remove(path);
        fileGroups.remove(path);
    }

    /**
     * 특정 디렉토리 내 파일 목록 조회 (ls 명령어)
     */
    public List<String> listFiles(String directory) {
        if (!files.containsKey(directory) || !files.get(directory).isDirectory()) {
            return Collections.emptyList();
        }

        VirtualDirectory dir = (VirtualDirectory) files.get(directory);

        // ✅ 디렉토리 내 children 목록을 가져와 반환
        return dir.getChildren().stream()
                .map(VirtualFile::getPath)
                .map(path -> path.substring(directory.length() + (directory.equals("/") ? 0 : 1)))
                .sorted()
                .toList();
    }



    public VirtualFile getFile(String path) {
        return files.get(path);
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String path) {
        if (files.containsKey(path) && files.get(path).isDirectory()) {
            this.currentPath = path;
        } else {
            System.out.println("Error: Invalid directory.");
        }
    }

    public boolean isDirectory(String path) {
        return files.containsKey(path) && files.get(path).isDirectory();
    }

    public String getFileContent(String path) {
        return fileContents.getOrDefault(path, "");
    }

    public void updateFileContent(String path, String content) {
        if (files.containsKey(path) && !files.get(path).isDirectory()) {
            fileContents.put(path, content);
        }
    }

    public void appendToFile(String path, String content) {
        fileContents.put(path, fileContents.getOrDefault(path, "") + "\n" + content);
    }

    public void moveToParentDirectory() {
        if (currentPath.equals("/")) {
            System.out.println("Error: Already at root directory.");
            return;
        }
        int lastSlash = currentPath.lastIndexOf('/');
        currentPath = currentPath.substring(0, lastSlash);
    }

    public String resolvePath(String path) {
        if (path.startsWith("/")) {
            return path;
        }
        return currentPath + "/" + path;
    }

    public Map<String, VirtualFile> getFiles() {
        return files;
    }

    public long getTotalFileSize() {
        return fileContents.values().stream().mapToLong(String::length).sum();
    }

    public long getDirectorySize(String path) {
        if (!files.containsKey(path) || !files.get(path).isDirectory()) {
            return 0;
        }

        return files.keySet().stream()
                .filter(p -> p.startsWith(path))
                .mapToLong(this::getFileSize)
                .sum();
    }

    public long getFileSize(String path) {
        return fileContents.getOrDefault(path, "").length();
    }

    /**
     * 파일 권한, 소유자, 그룹 반환
     */
    public String getFilePermissions(String path) {
        return filePermissions.getOrDefault(path, "rw-r--r--");
    }

    public String getFileOwner(String path) {
        return fileOwners.getOrDefault(path, "user");
    }

    public String getFileGroup(String path) {
        return fileGroups.getOrDefault(path, "user");
    }

    /**
     * 심볼릭 링크 해석
     */
    public String resolveSymbolicLink(String path) {
        VirtualFile file = files.get(path);
        if (file instanceof SymbolicLink) {
            return ((SymbolicLink) file).getTarget();
        }
        return path;
    }

    /**
     * 파일 권한 변경
     */
    public void setFilePermissions(String path, String permissions) {
        filePermissions.put(path, permissions);
    }

    /**
     * 파일 소유자 변경
     */
    public void setFileOwner(String path, String owner) {
        fileOwners.put(path, owner);
    }

    /**
     * 파일 그룹 변경
     */
    public void setFileGroup(String path, String group) {
        fileGroups.put(path, group);
    }


    /**
     * 특정 디렉토리의 파일 및 폴더 목록 반환
     */
    public List<VirtualFile> getDirectoryContents(String directory) {
        if (!files.containsKey(directory) || !files.get(directory).isDirectory()) {
            return Collections.emptyList();
        }

        VirtualDirectory dir = (VirtualDirectory) files.get(directory);
        return new ArrayList<>(dir.getChildren());
    }
}
