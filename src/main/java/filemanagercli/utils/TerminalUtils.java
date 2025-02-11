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
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing the screen: " + e.getMessage());
        }
    }
    /**
     * 터미널 화면 지우기 (clear 명령어)
     */
    public void clearTerminal() {
        TerminalUtils.clearScreen();
    }
}
