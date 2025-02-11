package explorer.commands;

import java.nio.file.Path;

/**
 * Command - 모든 명령어 공통 인터페이스
 */
public interface Command {
    Path execute(Path currentPath, String args);
}
