package filemanagercli.models;

/**
 * 심볼릭 링크 모델
 */
public class SymbolicLink extends VirtualFile {
    private final String target;

    public SymbolicLink(String path, String target) {
        super(path, false);
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "[SYMLINK] " + getPath() + " → " + target;
    }
    
}
