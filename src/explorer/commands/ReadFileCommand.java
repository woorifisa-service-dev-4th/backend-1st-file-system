package explorer.commands;

import explorer.operations.FileOperations;
import java.nio.file.Path;

/**
 * ReadFileCommand - cat 명령어 실행
 */
public class ReadFileCommand implements Command {
    public Path execute(Path currentPath, String args) {
        FileOperations.readFile(currentPath, args);
        return currentPath;
    }
}