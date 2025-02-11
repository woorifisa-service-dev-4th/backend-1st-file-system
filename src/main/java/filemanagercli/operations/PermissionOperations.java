package filemanagercli.operations;

import filemanagercli.VirtualFileSystem;
import filemanagercli.exceptions.FileSystemException;
import java.util.HashMap;
import java.util.Map;

public class PermissionOperations {
    private final VirtualFileSystem vfs;
    private final Map<String, String> filePermissions = new HashMap<>();
    private final Map<String, String> fileOwners = new HashMap<>();
    private final Map<String, String> fileGroups = new HashMap<>();

    public PermissionOperations(VirtualFileSystem vfs) {
        this.vfs = vfs;
    }

    public void changePermissions(String path, String permissions) throws FileSystemException {
        if (!vfs.exists(path)) {
            throw new FileSystemException("File not found: " + path);
        }
        filePermissions.put(path, permissions);
        System.out.println("Permissions changed for " + path + " to " + permissions);
    }

    public void changeOwner(String path, String owner) throws FileSystemException {
        if (!vfs.exists(path)) {
            throw new FileSystemException("File not found: " + path);
        }
        fileOwners.put(path, owner);
        System.out.println("Owner changed for " + path + " to " + owner);
    }

    public void changeGroup(String path, String group) throws FileSystemException {
        if (!vfs.exists(path)) {
            throw new FileSystemException("File not found: " + path);
        }
        fileGroups.put(path, group);
        System.out.println("Group changed for " + path + " to " + group);
    }
}
