package filemanagercli;

import filemanagercli.exceptions.CommandException;
import filemanagercli.exceptions.FileSystemException;
import filemanagercli.operations.DirectoryOperations;
import filemanagercli.operations.FileOperations;
import filemanagercli.operations.PermissionOperations;
import java.util.Arrays;

/**
 * CLI에서 실행되는 명령어를 처리하는 클래스
 */
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
        if (args.length == 0 || args[0].isEmpty()) return;

        String command = args[0];

        switch (command) {
            case "help":
                helpHandler.showGeneralHelp();
                break;
            case "pwd":
                dirOps.printCurrentDirectory();
                break;
            case "ls":
                dirOps.listFiles(Arrays.copyOfRange(args, 1, args.length));
                break;
            case "cd":
                if (args.length < 2) throw new CommandException("Usage: cd <directory>");
                dirOps.changeDirectory(args[1]);
                break;
            case "mkdir":
                if (args.length < 2) throw new CommandException("Usage: mkdir <directory>");
                dirOps.createDirectory(args[1]);
                break;
            case "rm":
                if (args.length < 2) throw new CommandException("Usage: rm <file>");
                fileOps.deleteFile(args[1]);
                break;
            case "touch":
                if (args.length < 2) throw new CommandException("Usage: touch <file>");
                fileOps.createFile(args[1]);
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
