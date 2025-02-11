package filemanagercli.utils;

/**
 * 명령어 관련 유틸리티
 */
public class CommandUtils {
    /**
     * 명령어에서 인덱스 위치의 인자를 가져옴 (기본값 포함)
     */
    public static String getArgument(String[] args, int index, String defaultValue) {
        return (args.length > index) ? args[index] : defaultValue;
    }
}
