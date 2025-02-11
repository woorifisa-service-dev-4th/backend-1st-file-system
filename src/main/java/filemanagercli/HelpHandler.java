package filemanagercli;

/**
 * 명령어 도움말을 제공하는 클래스
 */
public class HelpHandler {
    /**
     * 전체 명령어 목록을 출력하는 도움말
     */
    public void showGeneralHelp() {
        System.out.println("Available Commands:");
        System.out.println("  pwd                - Print current directory");
        System.out.println("  ls                 - List files in current directory");
        System.out.println("  cd <dir>           - Change directory");
        System.out.println("  mkdir <dir>        - Create a new directory");
        System.out.println("  rmdir <dir>        - Remove an empty directory");
        System.out.println("  rm <file>          - Remove a file");
        System.out.println("  touch <file>       - Create an empty file");
        System.out.println("  mv <src> <dst>     - Move/rename a file or directory");
        System.out.println("  cp <src> <dst>     - Copy a file");
        System.out.println("  cat <file>         - Read a file");
        System.out.println("  echo <text> >> <file> - Append text to a file");
        System.out.println("  find <keyword>     - Find files by name");
        System.out.println("  grep <keyword> <file> - Search for a keyword in a file");
        System.out.println("  chmod <perm> <file> - Change file permissions");
        System.out.println("  chown <owner> <file> - Change file owner");
        System.out.println("  chgrp <group> <file> - Change file group");
        System.out.println("  df                 - Show disk usage");
        System.out.println("  du <dir>           - Show directory size");
        System.out.println("  tree               - Show directory structure");
        System.out.println("  exit               - Exit the CLI");
        System.out.println("  help               - Show this help message");
        System.out.println("\nUse '<command> --help' for more details.");
    }

    /**
     * 특정 명령어에 대한 도움말을 출력하는 메서드
     */
    public void showHelp(String command) {
        switch (command) {
            case "pwd":
                System.out.println("Usage: pwd");
                System.out.println("Prints the current directory path.");
                break;
            case "ls":
                System.out.println("Usage: ls");
                System.out.println("Lists files in the current directory.");
                break;
            case "cd":
                System.out.println("Usage: cd <directory>");
                System.out.println("Changes the current working directory.");
                break;
            case "mkdir":
                System.out.println("Usage: mkdir <directory>");
                System.out.println("Creates a new directory.");
                break;
            case "rmdir":
                System.out.println("Usage: rmdir <directory>");
                System.out.println("Removes an empty directory.");
                break;
            case "rm":
                System.out.println("Usage: rm <file>");
                System.out.println("Removes a file.");
                break;
            case "touch":
                System.out.println("Usage: touch <file>");
                System.out.println("Creates an empty file.");
                break;
            case "mv":
                System.out.println("Usage: mv <source> <destination>");
                System.out.println("Moves or renames a file or directory.");
                break;
            case "cp":
                System.out.println("Usage: cp <source> <destination>");
                System.out.println("Copies a file.");
                break;
            case "cat":
                System.out.println("Usage: cat <file>");
                System.out.println("Displays the contents of a file.");
                break;
            case "echo":
                System.out.println("Usage: echo <text> >> <file>");
                System.out.println("Appends text to a file.");
                break;
            case "find":
                System.out.println("Usage: find <keyword>");
                System.out.println("Finds files containing the keyword in their name.");
                break;
            case "grep":
                System.out.println("Usage: grep <keyword> <file>");
                System.out.println("Searches for a keyword within a file.");
                break;
            case "chmod":
                System.out.println("Usage: chmod <permissions> <file>");
                System.out.println("Changes file permissions.");
                break;
            case "chown":
                System.out.println("Usage: chown <owner> <file>");
                System.out.println("Changes the owner of a file.");
                break;
            case "chgrp":
                System.out.println("Usage: chgrp <group> <file>");
                System.out.println("Changes the group of a file.");
                break;
            case "df":
                System.out.println("Usage: df");
                System.out.println("Displays disk usage.");
                break;
            case "du":
                System.out.println("Usage: du <directory>");
                System.out.println("Shows the size of a directory.");
                break;
            case "tree":
                System.out.println("Usage: tree");
                System.out.println("Displays the directory structure.");
                break;
            default:
                System.out.println("No help available for command: " + command);
        }
    }
}
