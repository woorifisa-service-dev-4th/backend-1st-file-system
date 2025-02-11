package filemanagercli.exceptions;

/**
 * 잘못된 명령어 입력 시 발생하는 예외
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}
