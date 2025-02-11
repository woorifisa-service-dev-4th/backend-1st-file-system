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
        if (!children.contains(file)) {
            children.add(file);
        }
    }

    public void removeFile(VirtualFile file) {
        children.remove(file);
    }

    // ✅ 원래는 `Collections.unmodifiableList()`를 반환했지만, 변경 가능한 리스트 반환
    public List<VirtualFile> getChildren() {
        return new ArrayList<>(children);
    }
}
