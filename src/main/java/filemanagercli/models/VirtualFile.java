package filemanagercli.models;

import java.util.Objects;

/**
 * 가상 파일을 나타내는 클래스
 */
public class VirtualFile {
    private final String path;
    private final boolean isDirectory;

    public VirtualFile(String path, boolean isDirectory) {
        this.path = path;
        this.isDirectory = isDirectory;
    }

    public String getPath() {
        return path;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VirtualFile that = (VirtualFile) obj;
        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    @Override
    public String toString() {
        return (isDirectory ? "[DIR] " : "[FILE] ") + path;
    }
}
