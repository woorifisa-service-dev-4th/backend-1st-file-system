package filemanagercli;

import filemanagercli.models.VirtualFile;
import filemanagercli.models.VirtualDirectory;
import java.util.*;

/**
 * 가상의 파일 시스템을 관리하는 클래스
 */
public class VirtualFileSystem {
    private final Map<String, VirtualFile> files;
    private final Map<String, String> fileContents; // 파일 내용 저장
    private String currentPath = "/";

    public VirtualFileSystem() {
        this.files = new HashMap<>();
        this.fileContents = new HashMap<>();
        initializeRootDirectory();
    }

    private void initializeRootDirectory() {
        files.put("/", new VirtualDirectory("/"));
    }

    public boolean exists(String path) {
        return files.containsKey(path);
    }

    public void addFile(String path, VirtualFile file) {
        files.put(path, file);
    }

    public void deleteFile(String path) {
        files.remove(path);
        fileContents.remove(path);
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

    /**
     * 특정 디렉토리 내 파일 목록 조회
     */
    public List<String> listFiles(String directory) {
        if (!files.containsKey(directory) || !files.get(directory).isDirectory()) {
            return Collections.emptyList();
        }

        return files.keySet().stream()
            .filter(path -> path.startsWith(directory) && !path.equals(directory))
            .map(path -> path.replace(directory + "/", ""))
            .sorted()
            .toList();
    }

    /**
     * 파일 내용 조회
     */
    public String getFileContent(String path) {
        return fileContents.getOrDefault(path, "");
    }

    /**
     * 파일 내용 업데이트
     */
    public void updateFileContent(String path, String content) {
        if (files.containsKey(path) && !files.get(path).isDirectory()) {
            fileContents.put(path, content);
        }
    }

    /**
     * 파일 내용 추가 (echo >>)
     */
    public void appendToFile(String path, String content) {
        fileContents.put(path, fileContents.getOrDefault(path, "") + "\n" + content);
    }

    /**
     * 상위 디렉토리 이동 (cd ..)
     */
    public void moveToParentDirectory() {
        if (currentPath.equals("/")) {
            System.out.println("Error: Already at root directory.");
            return;
        }
        int lastSlash = currentPath.lastIndexOf('/');
        currentPath = currentPath.substring(0, lastSlash);
    }

    /**
     * 입력된 경로를 현재 경로 기준으로 변환 (절대 경로)
     */
    public String resolvePath(String path) {
        if (path.startsWith("/")) {
            return path;
        }
        return currentPath + "/" + path;
    }

    /**
     * 전체 파일 시스템 반환
     */
    public Map<String, VirtualFile> getFiles() {
        return files;
    }
}
