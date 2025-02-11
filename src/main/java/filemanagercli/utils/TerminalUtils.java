package filemanagercli.utils;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import java.io.IOException;

/**
 * 터미널 관련 유틸리티 (JLine 사용)
 */
public class TerminalUtils {
    public static Terminal getTerminal() {
        try {
            return TerminalBuilder.builder().system(true).build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize terminal", e);
        }
    }
}
