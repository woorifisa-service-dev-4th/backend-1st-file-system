package filemanagercli.utils;

public class CommandUtils {
    public static String getArgument(String[] args, int index, String defaultValue) {
        return (args.length > index) ? args[index] : defaultValue;
    }
}
