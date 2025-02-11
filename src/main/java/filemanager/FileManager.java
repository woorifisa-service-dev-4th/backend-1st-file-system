package filemanager;

import filemanager.command.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileManager {
    private File currentDirectory;
    private final File rootDirectory;
    private final Map<String, Command> commands = new HashMap<>();

    public FileManager(String initialPath) {
        this.currentDirectory = new File(initialPath);
        this.rootDirectory = new File(initialPath);

        if (!currentDirectory.exists() || !currentDirectory.isDirectory()) {
            throw new IllegalArgumentException("초기 경로가 유효하지 않습니다.");
        }

        commands.put("ls", new LsCommand());
        commands.put("cd", new CdCommand());
        commands.put("mv", new MvCommand());
    }

    public File getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(File newDirectory) {
        if (newDirectory.exists() && newDirectory.isDirectory()) {
            this.currentDirectory = newDirectory;
        } else {
            System.out.println("오류: 유효하지 않은 디렉터리입니다.");
        }
    }

    public File getRootDirectory() {
        return rootDirectory;
    }

    public void executeCommand(String command, String... args) {
        Command cmd = commands.get(command);
        if (cmd != null) {
            cmd.execute(this, args);
        } else {
            System.out.println("오류: 지원되지 않는 명령어입니다.");
        }
    }

    public static void main(String[] args) {
        FileManager fm = new FileManager(System.getProperty("user.dir"));
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(fm.getCurrentDirectory().getAbsolutePath() + " $ ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+");
            String command = parts[0];
            String[] params = new String[parts.length - 1];
            System.arraycopy(parts, 1, params, 0, params.length);

            if ("exit".equals(command)) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }

            fm.executeCommand(command, params);
        }
    }
}
