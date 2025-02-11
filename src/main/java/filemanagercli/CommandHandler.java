package filemanagercli;

import java.util.Arrays;

import filemanagercli.exceptions.CommandException;
import filemanagercli.exceptions.FileSystemException;
import filemanagercli.operations.FileOperations;
import filemanagercli.operations.DirectoryOperations;
import filemanagercli.operations.PermissionOperations;

public class CommandHandler {
    private final FileOperations fileOps;
    private final DirectoryOperations dirOps;
    private final PermissionOperations permOps;
    private final HelpHandler helpHandler;

    public CommandHandler(VirtualFileSystem vfs) {
        this.fileOps = new FileOperations(vfs);
        this.dirOps = new DirectoryOperations(vfs);
        this.permOps = new PermissionOperations(vfs);
        this.helpHandler = new HelpHandler();
    }

    public void executeCommand(String commandLine) throws CommandException, FileSystemException {
        String[] args = commandLine.split("\\s+");
        if (args.length == 0) return;

        String command = args[0];

        if (args.length > 1 && args[1].equals("--help")) {
            helpHandler.showHelp(command);
            return;
        }

        switch (command) {
            case "help":
            case "/help":
            case "/?":
                helpHandler.showGeneralHelp();
                break;

            case "tree":
                dirOps.printTree(args.length > 1 ? args[1] : null); // ✅ 개선: 경로 인자 없을 시 null 처리
                break;

            case "touch":
                if (args.length < 2) throw new CommandException("Usage: touch <file>");
                fileOps.createFile(args[1]);
                break;

            case "mkdir":
                if (args.length < 2) throw new CommandException("Usage: mkdir <directory>");
                dirOps.createDirectory(args[1]);
                break;

            case "rm":
                if (args.length < 2) throw new CommandException("Usage: rm <file>");
                fileOps.deleteFile(args[1]);
                break;

            case "rmdir":
                if (args.length < 2) throw new CommandException("Usage: rmdir <directory>");
                dirOps.removeDirectory(args[1]);
                break;

            case "mv":
                if (args.length < 3) throw new CommandException("Usage: mv <source> <destination>");
                fileOps.moveFile(args[1], args[2]);
                break;

            case "cp":
                if (args.length < 3) throw new CommandException("Usage: cp <source> <destination>");
                fileOps.copyFile(args[1], args[2]);
                break;

            case "ls":
            case "dir":
                dirOps.listFiles();
                break;

            case "pwd":
                dirOps.getCurrentDirectory();
                break;

            case "cd":
                if (args.length < 2) throw new CommandException("Usage: cd <directory>");
                dirOps.changeDirectory(args[1]);
                break;

            case "cat":
                if (args.length < 2) throw new CommandException("Usage: cat <file1> [file2 ...]");
                fileOps.readFiles(Arrays.copyOfRange(args, 1, args.length));
                break;

            case "find":
                if (args.length < 2) throw new CommandException("Usage: find <keyword>");
                fileOps.findFiles(args[1]);
                break;

            case "chmod":
                if (args.length < 3) throw new CommandException("Usage: chmod <permissions> <file>");
                permOps.changePermissions(args[2], args[1]);
                break;

            case "chown":
                if (args.length < 3) throw new CommandException("Usage: chown <owner> <file>");
                permOps.changeOwner(args[2], args[1]);
                break;

            case "chgrp":
                if (args.length < 3) throw new CommandException("Usage: chgrp <group> <file>");
                permOps.changeGroup(args[2], args[1]);
                break;

            default:
                throw new CommandException("Unknown command: " + command);
        }
    }
}
