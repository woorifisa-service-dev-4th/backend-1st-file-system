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
        initializeProjectStructure(); // ✅ 기본 디렉토리 및 파일 추가
    }

    /**
     * 루트 디렉토리 초기화
     */
    private void initializeRootDirectory() {
        files.put("/", new VirtualDirectory("/"));
        filePermissions.put("/", "drwxr-xr-x");
        fileOwners.put("/", "root");
        fileGroups.put("/", "root");
    }

    /**
     * 기본 프로젝트 구조 초기화
     */
    private void initializeProjectStructure() {
        createDirectory("/empty_project");
        createDirectory("/empty_project/data");
        createDirectory("/empty_project/src");
        createDirectory("/empty_project/logs");
        createDirectory("/empty_project/docs");
        createDirectory("/empty_project/tests");
        createDirectory("/empty_project/scripts");

        createFile("/empty_project/data/raw_data.csv", "id,value\n1,100\n2,200");
        createFile("/empty_project/data/processed_data.json", "{\"id\": 1, \"value\": 100}");
        createFile("/empty_project/data/config.yaml", "setting: default");

        createFile("/empty_project/src/main.py", "print('Hello, World!')");
        createFile("/empty_project/src/helper.py", "# Helper functions");
        createFile("/empty_project/src/analyzer.py", "# Data analyzer");
        createFile("/empty_project/src/processor.py", "# Data processor");

        createFile("/empty_project/logs/app.log", "Log started...");
        createFile("/empty_project/logs/error.log", "Error log...");

        createFile("/empty_project/docs/README.md", "# Project README");
        createFile("/empty_project/docs/requirements.txt", "numpy\npandas");
        createFile("/empty_project/docs/changelog.md", "Version 1.0 - Initial release");

        createFile("/empty_project/tests/test_main.py", "def test_main(): pass");
        createFile("/empty_project/tests/test_helper.py", "def test_helper(): pass");
        createFile("/empty_project/tests/test_analyzer.py", "def test_analyzer(): pass");

        createFile("/empty_project/scripts/setup.sh", "#!/bin/bash\necho 'Setup'");
        createFile("/empty_project/scripts/run.sh", "#!/bin/bash\necho 'Run'");
        createFile("/empty_project/scripts/clean.sh", "#!/bin/bash\necho 'Clean'");
    }

    /**
     * 디렉토리 추가
     */
    private void createDirectory(String path) {
        if (!files.containsKey(path)) {
            files.put(path, new VirtualDirectory(path));
            filePermissions.put(path, "drwxr-xr-x");
            fileOwners.put(path, "user");
            fileGroups.put(path, "user");
        }
    }

    /**
     * 파일 추가 및 기본 내용 설정
     */
    private void createFile(String path, String content) {
        if (!files.containsKey(path)) {
            files.put(path, new VirtualFile(path, false));
            fileContents.put(path, content);
            filePermissions.put(path, "-rw-r--r--");
            fileOwners.put(path, "user");
            fileGroups.put(path, "user");
        }
    }

    public boolean exists(String path) {
        return files.containsKey(path);
    }

    public void addFile(String path, VirtualFile file) {
        files.put(path, file);
        System.out.println("[DEBUG] Added file: " + path);
    
        int lastSlash = path.lastIndexOf('/');
        if (lastSlash >= 0) {
            String parentPath = (lastSlash == 0) ? "/" : path.substring(0, lastSlash);
    
            if (files.containsKey(parentPath) && files.get(parentPath) instanceof VirtualDirectory) {
                VirtualDirectory parentDir = (VirtualDirectory) files.get(parentPath);
                parentDir.addFile(file);
                System.out.println("[DEBUG] Added to VirtualDirectory: " + path + " under " + parentPath);
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

    public List<String> listFiles(String directory) {
        if (!files.containsKey(directory) || !(files.get(directory) instanceof VirtualDirectory)) {
            return Collections.emptyList();
        }

        VirtualDirectory dir = (VirtualDirectory) files.get(directory);

        return dir.getChildren().stream()
                .map(VirtualFile::getPath)
                .map(path -> path.replace(directory + "/", ""))
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
            System.out.println("Warning: Already at root directory.");
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

    // public List<VirtualFile> getDirectoryContents(String directory) {
    //     if (!files.containsKey(directory) || !files.get(directory).isDirectory()) {
    //         // System.out.println("[DEBUG] getDirectoryContents: Directory not found: " + directory);
    //         return Collections.emptyList();
    //     }
    
    //     VirtualDirectory dir = (VirtualDirectory) files.get(directory);
    //     List<VirtualFile> contents = new ArrayList<>(dir.getChildren());
    
    //     // System.out.println("[DEBUG] getDirectoryContents: " + directory + " → " + contents.size() + " items found");
    //     for (VirtualFile file : contents) {
    //         System.out.println("  - " + file.getPath());
    //     }
    
    //     return contents;
    // }
    public List<VirtualFile> getDirectoryContents(String directory) {
        if (!files.containsKey(directory) || !(files.get(directory) instanceof VirtualDirectory)) {
            System.out.println("[DEBUG] getDirectoryContents: Directory not found or not a directory: " + directory);
            return Collections.emptyList();
        }
    
        VirtualDirectory dir = (VirtualDirectory) files.get(directory);
        List<VirtualFile> contents = new ArrayList<>(dir.getChildren());
    
        System.out.println("[DEBUG] getDirectoryContents: " + directory + " → " + contents.size() + " items found");
        for (VirtualFile file : contents) {
            System.out.println("  - " + file.getPath() + " (Type: " + (file.isDirectory() ? "Directory" : "File") + ")");
        }
    
        return contents;
    }
    
    
}
