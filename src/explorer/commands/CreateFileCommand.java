package explorer.commands;

import explorer.operations.FileOperations;
import java.nio.file.Path;

/**
 * CreateFileCommand - touch 명령어 실행
 */
public class CreateFileCommand implements Command {
    public Path execute(Path currentPath, String args) {
        FileOperations.createFile(currentPath, args);
        return currentPath;
    }
}