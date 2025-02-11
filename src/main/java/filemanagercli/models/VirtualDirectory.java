package filemanagercli.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 가상 디렉토리를 나타내는 클래스
 */
public class VirtualDirectory extends VirtualFile {
    private final List<VirtualFile> children;

    public VirtualDirectory(String path) {
        super(path, true);
        this.children = new ArrayList<>();
    }

    public void addFile(VirtualFile file) {
        if (!children.contains(file)) {  // ✅ 중복 방지
            children.add(file);
            // System.out.println("[DEBUG] Added to VirtualDirectory: " + file.getPath() + " under " + getPath());
        }
    }

    public void removeFile(VirtualFile file) {
        children.remove(file);
    }

    public List<VirtualFile> getChildren() {
        // System.out.println("[DEBUG] VirtualDirectory Children of " + getPath() + ": " + children.size() + " items");
        return Collections.unmodifiableList(children);
    }
}
