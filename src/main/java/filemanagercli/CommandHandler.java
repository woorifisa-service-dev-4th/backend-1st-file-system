package filemanagercli;

import filemanagercli.exceptions.CommandException;
import filemanagercli.exceptions.FileSystemException;
import filemanagercli.operations.*;
import filemanagercli.utils.TerminalUtils;

import java.util.Arrays;

/**
 * CLI에서 실행되는 명령어를 처리하는 클래스
 */
public class CommandHandler {
    private final FileOperations fileOps;
    private final DirectoryOperations dirOps;
    private final PermissionOperations permOps;
    private final FileSearchOperations searchOps;
    private final FileContentOperations contentOps;
    private final FileSystemInfoOperations fsOps;
    private final HelpHandler helpHandler;

    public CommandHandler(VirtualFileSystem vfs) {
        this.fileOps = new FileOperations(vfs);
        this.dirOps = new DirectoryOperations(vfs);
        this.permOps = new PermissionOperations(vfs);
        this.searchOps = new FileSearchOperations(vfs);
        this.contentOps = new FileContentOperations(vfs);
        this.fsOps = new FileSystemInfoOperations(vfs);
        this.helpHandler = new HelpHandler();
    }

    public void executeCommand(String commandLine) throws CommandException, FileSystemException {
        String[] args = commandLine.split("\\s+");
        if (args.length == 0 || args[0].isEmpty()) return;

        String command = args[0];

        switch (command) {
            case "help":
                helpHandler.showGeneralHelp();
                break;
            case "pwd":
                dirOps.printCurrentDirectory();
                break;
            case "dir":
            case "ls":
                dirOps.listFiles(Arrays.copyOfRange(args, 1, args.length));
                break;
            case "tree":
                dirOps.printTreeCommand(args.length > 1 ? args[1] : null);
                break;
            case "cd":
                if (args.length < 2) throw new CommandException("Usage: cd <directory>");
                dirOps.changeDirectory(args[1]);
                break;
            case "mkdir":
                if (args.length < 2) throw new CommandException("Usage: mkdir <directory>");
                boolean createParents = Arrays.asList(args).contains("-p");
                dirOps.createDirectory(args[1], createParents);
                break;
            case "rm":
                boolean recursive = Arrays.asList(args).contains("-r");
                if (args.length < 2) throw new CommandException("Usage: rm [-r] <file>");
                fileOps.deleteFile(args[1], recursive);
                break;
            case "cp":
                boolean cpRecursive = Arrays.asList(args).contains("-r");
                if (args.length < 3) throw new CommandException("Usage: cp [-r] <source> <destination>");
                fileOps.copyFile(args[1], args[2], cpRecursive);
                break;
            case "ln":
                fileOps.createHardLink(args[1], args[2]);
                break;
            case "ln -s":
                fileOps.createSymbolicLink(args[1], args[2]);
                break;
            case "df":
                fsOps.showDiskUsage();
                break;
            case "du":
                fsOps.showDirectorySize(args[1]);
                break;
            case "clear":
                TerminalUtils.clearScreen();
                break;
                case "find":
                if (args.length < 2) throw new CommandException("Usage: find <keyword>");
                searchOps.findFiles(args[1]);
                break;
            case "grep":
                if (args.length < 3) throw new CommandException("Usage: grep <keyword> <file>");
                searchOps.grep(args[1], args[2]);
                break;
            case "chmod":
                permOps.changePermissions(args[2], args[1]);
                break;
            case "chown":
                permOps.changeOwner(args[2], args[1]);
                break;
            case "chgrp":
                permOps.changeGroup(args[2], args[1]);
                break;
            case "nano":
                contentOps.editFileInteractive(args[1]);
                break;
            case "exit":
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                throw new CommandException("Unknown command: " + command);
        }
    }
}
