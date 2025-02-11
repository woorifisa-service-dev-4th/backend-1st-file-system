package filemanager.command;

import filemanager.FileManager;

public interface Command {
    void execute(FileManager fm, String... args);
}

