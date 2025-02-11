package filemanager.command;

import filemanager.FileManager;
import filemanager.utils.PathUtils;
import java.io.File;
import java.util.Arrays;

public class LsCommand implements Command {
    @Override
    public void execute(FileManager fm, String... args) {
        File targetDirectory = fm.getCurrentDirectory();

        if (args.length > 0) {
            File inputPath = new File(args[0]);

            if (inputPath.isAbsolute()) {
                targetDirectory = inputPath;
            } else {
                targetDirectory = new File(fm.getCurrentDirectory(), args[0]);
            }
        }

        if (!PathUtils.isValidDirectory(targetDirectory)) {
            System.out.println("오류: 디렉터리를 찾을 수 없습니다.");
            return;
        }

        System.out.println("\n📂 " + PathUtils.getCanonicalPath(targetDirectory));

        File[] files = targetDirectory.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("디렉터리가 비어 있습니다.");
            return;
        }

        Arrays.sort(files);

        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("[D] " + file.getName());
            } else {
                System.out.println("[F] " + file.getName());
            }
        }
        System.out.println();
    }
}
