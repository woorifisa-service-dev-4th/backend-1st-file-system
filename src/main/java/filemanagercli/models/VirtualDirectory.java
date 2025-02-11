package filemanagercli.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VirtualDirectory extends VirtualFile {
    private final List<VirtualFile> children;

    public VirtualDirectory(String path) {
        super(path, true);
        this.children = new ArrayList<>();
    }

    public void addFile(VirtualFile file) {
        children.add(file);
    }

    public void removeFile(VirtualFile file) {
        children.remove(file);
    }

    public List<VirtualFile> getChildren() {
        return Collections.unmodifiableList(children);
    }
}
