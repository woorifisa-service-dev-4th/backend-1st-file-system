package filemanagercli;

import filemanagercli.exceptions.CommandException;
import filemanagercli.exceptions.FileSystemException;
import java.util.Scanner;

/**
 * 파일 탐색기 CLI 실행 클래스
 */
public class FileManagerCLI {
    private final VirtualFileSystem vfs;
    private final CommandHandler commandHandler;

    public FileManagerCLI() {
        this.vfs = new VirtualFileSystem();
        this.commandHandler = new CommandHandler(vfs);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to File Manager CLI! Type 'help' for a list of commands.");

        while (true) {
            System.out.print(vfs.getCurrentPath() + " $ ");
            String commandLine = scanner.nextLine().trim();

            if (commandLine.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            }

            try {
                commandHandler.executeCommand(commandLine);
            } catch (CommandException | FileSystemException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        new FileManagerCLI().start();
    }
}
