package explorer.commands;

import java.nio.file.Path;

import explorer.operations.DirectoryOperations;

public class ChangeDirectoryCommand implements Command {
    public Path execute(Path currentPath, String args) {
        return DirectoryOperations.changeDirectory(currentPath, args);
    }
}