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
        long totalSize = vfs.getTotalFileSize();
        System.out.println("Filesystem      Size     Used     Available");
        System.out.printf("VirtualFS       %d KB   %d KB    %d KB\n",
                totalSize / 1024, totalSize / 2048, (totalSize / 1024) - (totalSize / 2048));
    }

    /**
     * 특정 디렉토리의 크기 확인 (du -sh)
     */
    public void showDirectorySize(String path) {
        if (!vfs.exists(path)) {
            System.out.println("Error: Directory not found: " + path);
            return;
        }

        long totalSize = vfs.getDirectorySize(path);
        System.out.printf("Size of %s: %d KB\n", path, totalSize / 1024);
    }


}
