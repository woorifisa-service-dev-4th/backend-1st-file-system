package filemanagercli.operations;

import filemanagercli.VirtualFileSystem;
import java.util.Map;

/**
 * 파일 시스템 정보 관련 기능 (df, du)
 */
public class FileSystemInfoOperations {
    private final VirtualFileSystem vfs;

    public FileSystemInfoOperations(VirtualFileSystem vfs) {
        this.vfs = vfs;
    }

    /**
     * 파일 시스템 전체 사용량 확인 (df 명령어)
     */
    public void showDiskUsage() {
        int totalFiles = vfs.getFiles().size();
        System.out.println("Disk Usage:");
        System.out.println("Total Files: " + totalFiles);
    }

    /**
     * 특정 디렉토리의 크기 확인 (du 명령어)
     */
    public void showDirectorySize(String path) {
        if (!vfs.exists(path)) {
            System.out.println("Error: Directory not found: " + path);
            return;
        }
        int fileCount = 0;
        for (String key : vfs.getFiles().keySet()) {
            if (key.startsWith(path)) {
                fileCount++;
            }
        }
        System.out.println("Size of " + path + ": " + fileCount + " files");
    }
}
