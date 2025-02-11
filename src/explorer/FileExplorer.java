package explorer;

import explorer.commands.*;
import explorer.operations.*;
import java.nio.file.*;
import java.util.*;

/**
 * FileExplorer - 메인 실행 클래스
 * 사용자로부터 초기 경로를 입력받아 파일 시스템을 탐색할 수 있도록 함
 * 지원 명령어: ls, mv, cd, cat, touch, rm
 */
public class FileExplorer {
    private static Path currentPath; // 현재 탐색 중인 디렉토리 경로
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Command> commands = new HashMap<>();

    // 명령어 등록
    static {
        commands.put("ls", new ListCommand());
        commands.put("mv", new MoveCommand());
        commands.put("cd", new ChangeDirectoryCommand());
        commands.put("cat", new ReadFileCommand());
        commands.put("touch", new CreateFileCommand());
        commands.put("rm", new DeleteFileCommand());
    }

    public static void main(String[] args) {
        System.out.print("초기 파일 탐색 경로를 입력하세요: ");
        String userInputPath = scanner.nextLine().trim();
        currentPath = Paths.get(userInputPath).toAbsolutePath().normalize();

        if (!Files.exists(currentPath) || !Files.isDirectory(currentPath)) {
            System.out.println("오류: 유효한 디렉토리를 입력하세요.");
            return;
        }

        while (true) {
            System.out.print(currentPath + " $ ");
            String input = scanner.nextLine().trim();

            if (input.equals("exit")) {
                System.out.println("프로그램 종료");
                break;
            }

            String[] parts = input.split(" ", 2);
            Command command = commands.get(parts[0]);

            if (command != null) {
                currentPath = command.execute(currentPath, parts.length > 1 ? parts[1] : "");
            } else {
                System.out.println("알 수 없는 명령어: " + parts[0]);
            }
        }
    }
}