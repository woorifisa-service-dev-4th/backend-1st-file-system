package explorer.commands;

import explorer.operations.DirectoryOperations;
import java.nio.file.Path;

/**
 * ListCommand - ls 명령어 실행
 */
public class ListCommand implements Command {
    public Path execute(Path currentPath, String args) {
        DirectoryOperations.listDirectory(currentPath, args);
        return currentPath;
    }
}
