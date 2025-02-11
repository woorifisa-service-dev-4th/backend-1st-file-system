package filemanagercli.operations;

import filemanagercli.VirtualFileSystem;
import filemanagercli.models.SymbolicLink;
import filemanagercli.models.VirtualDirectory;
import filemanagercli.models.VirtualFile;
import filemanagercli.exceptions.FileSystemException;
import java.util.Date;

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

    /**
     * 파일 또는 디렉토리 복사 (cp -r 지원)
     */
    public void copyFile(String source, String destination, boolean recursive) throws FileSystemException {
        if (!vfs.exists(source)) {
            throw new FileSystemException("Error: Source file not found: " + source);
        }

        VirtualFile original = vfs.getFile(source);

        if (original.isDirectory()) {
            if (!recursive) {
                throw new FileSystemException("Error: Cannot copy directory without -r flag: " + source);
            }

            VirtualDirectory newDir = new VirtualDirectory(destination);
            vfs.addFile(destination, newDir);

            VirtualDirectory sourceDir = (VirtualDirectory) original;
            for (VirtualFile child : sourceDir.getChildren()) {
                copyFile(child.getPath(), destination + "/" + child.getPath(), true);
            }
        } else {
            vfs.addFile(destination, new VirtualFile(destination, false));
        }

        System.out.println("Copied: " + source + " → " + destination);
    }


    public void deleteFile(String path) throws FileSystemException {
        if (!vfs.exists(path)) {
            throw new FileSystemException("File not found: " + path);
        }
        vfs.deleteFile(path);
    }

    /**
     * 파일 또는 디렉토리 삭제 (rm -r 지원)
     */
    public void deleteFile(String path, boolean recursive) throws FileSystemException {
        if (!vfs.exists(path)) {
            throw new FileSystemException("Error: File not found: " + path);
        }

        VirtualFile file = vfs.getFile(path);
        if (file.isDirectory()) {
            if (!recursive) {
                throw new FileSystemException("Error: Cannot delete non-empty directory without -r flag: " + path);
            }

            VirtualDirectory dir = (VirtualDirectory) file;
            for (VirtualFile child : dir.getChildren()) {
                deleteFile(child.getPath(), true);
            }
        }

        vfs.deleteFile(path);
        System.out.println("Deleted: " + path);
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

     /**
     * 파일 상세 정보 출력 (stat 명령어)
     */
    public void printFileStats(String path) throws FileSystemException {
        if (!vfs.exists(path)) {
            throw new FileSystemException("Error: File not found: " + path);
        }

        VirtualFile file = vfs.getFile(path);
        System.out.println("File: " + file.getPath());
        System.out.println("Type: " + (file.isDirectory() ? "Directory" : "File"));
        System.out.println("Size: " + vfs.getFile(path) + " bytes");
        System.out.println("Permissions: " + vfs.getFilePermissions(path));
        System.out.println("Owner: " + vfs.getFile(path));
        System.out.println("Group: " + vfs.getFile(path));
        System.out.println("Last Modified: " + new Date());
    }

    /**
     * 심볼릭 링크 생성 (ln -s)
     */
    public void createSymbolicLink(String target, String linkPath) throws FileSystemException {
        if (!vfs.exists(target)) {
            throw new FileSystemException("Error: Target does not exist: " + target);
        }
        if (vfs.exists(linkPath)) {
            throw new FileSystemException("Error: Link name already exists: " + linkPath);
        }

        vfs.addFile(linkPath, new SymbolicLink(linkPath, target));
        System.out.println("Created symbolic link: " + linkPath + " → " + target);
    }

    /**
     * 하드 링크 생성 (ln)
     */
    public void createHardLink(String target, String linkPath) throws FileSystemException {
        if (!vfs.exists(target)) {
            throw new FileSystemException("Error: Target does not exist: " + target);
        }
        if (vfs.exists(linkPath)) {
            throw new FileSystemException("Error: Link name already exists: " + linkPath);
        }

        VirtualFile original = vfs.getFile(target);
        vfs.addFile(linkPath, original);
        System.out.println("Created hard link: " + linkPath + " → " + target);
    }

}
