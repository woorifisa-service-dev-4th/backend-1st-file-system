package filemanagercli;

import filemanagercli.exceptions.CommandException;
import filemanagercli.exceptions.FileSystemException;

import java.util.Scanner;

public class FileManagerCLI {
    private final VirtualFileSystem vfs;
    private final CommandHandler commandHandler;

    public FileManagerCLI() {
        this.vfs = new VirtualFileSystem();
        this.commandHandler = new CommandHandler(vfs);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to File Manager CLI!");

        while (true) {
            System.out.print("> ");
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
