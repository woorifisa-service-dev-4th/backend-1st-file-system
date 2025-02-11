package explorer.commands;

import explorer.operations.FileOperations;
import java.nio.file.Path;

/**
 * DeleteFileCommand - rm 명령어 실행 (디렉토리 삭제 기능 추가)
 */
public class DeleteFileCommand implements Command {
    public Path execute(Path currentPath, String args) {
        FileOperations.deleteFileOrDirectory(currentPath, args);
        return currentPath;
    }
}