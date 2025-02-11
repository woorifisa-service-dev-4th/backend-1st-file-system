package explorer.commands;

import explorer.operations.FileOperations;
import java.nio.file.Path;

/**
 * MoveCommand - mv 명령어 실행
 */
public class MoveCommand implements Command {
    public Path execute(Path currentPath, String args) {
        String[] params = args.split(" ", 2);
        if (params.length < 2) {
            System.out.println("사용법: mv <원본 파일/폴더> <목적지>");
        } else {
            FileOperations.moveFileOrDirectory(currentPath, params[0], params[1]);
        }
        return currentPath;
    }
}
