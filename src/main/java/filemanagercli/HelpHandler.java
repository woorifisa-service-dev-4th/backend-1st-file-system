package filemanagercli;


public class HelpHandler {

    public void showGeneralHelp() {
        System.out.println("Available Commands:");
        System.out.println("  ls, dir            - List files and directories");
        System.out.println("  touch <file>       - Create an empty file");
        System.out.println("  mkdir <directory>  - Create a new directory");
        System.out.println("  rm <file>          - Remove a file");
        System.out.println("  rmdir <directory>  - Remove an empty directory");
        System.out.println("  mv <src> <dst>     - Move/rename a file or directory");
        System.out.println("  cp <src> <dst>     - Copy a file");
        System.out.println("  find <keyword>     - Find files containing a keyword");
        System.out.println("  chmod <perm> <file> - Change file permissions");
        System.out.println("  chown <owner> <file> - Change file owner");
        System.out.println("  chgrp <group> <file> - Change file group");
        System.out.println("  clear              - Clear the terminal screen");
        System.out.println("  exit               - Exit the CLI");
        System.out.println("  help, /help, /?    - Show this help message");
        System.out.println("\nUse '<command> --help' for more details.");
        System.out.println("  tree               - Display directory structure");
    }

    public void showHelp(String command) {
        switch (command) {
            case "ls":
            case "dir":
                System.out.println("Usage: ls | dir");
                System.out.println("Lists files and directories in the current location.");
                break;
            case "touch":
                System.out.println("Usage: touch <file>");
                System.out.println("Creates an empty file.");
                break;
            case "mkdir":
                System.out.println("Usage: mkdir <directory>");
                System.out.println("Creates a new directory.");
                break;
            case "rm":
                System.out.println("Usage: rm <file>");
                System.out.println("Removes a file.");
                break;
            case "rmdir":
                System.out.println("Usage: rmdir <directory>");
                System.out.println("Removes an empty directory.");
                break;
            case "mv":
                System.out.println("Usage: mv <source> <destination>");
                System.out.println("Moves or renames a file or directory.");
                break;
            case "cp":
                System.out.println("Usage: cp <source> <destination>");
                System.out.println("Copies a file.");
                break;
            case "find":
                System.out.println("Usage: find <keyword>");
                System.out.println("Finds files containing the keyword.");
                break;
            case "chmod":
                System.out.println("Usage: chmod <permissions> <file>");
                System.out.println("Changes file permissions.");
                break;
            case "chown":
                System.out.println("Usage: chown <owner> <file>");
                System.out.println("Changes file owner.");
                break;
            case "chgrp":
                System.out.println("Usage: chgrp <group> <file>");
                System.out.println("Changes file group.");
                break;
            case "tree":
                System.out.println("Usage: tree");
                System.out.println("Displays the directory structure as a tree.");
                break;
            default:
                System.out.println("No help available for command: " + command);
        }
    }
}
