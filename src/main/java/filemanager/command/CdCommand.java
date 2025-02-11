package filemanager.command;

import filemanager.FileManager;
import filemanager.utils.PathUtils;
import java.io.File;

public class CdCommand implements Command {
    @Override
    public void execute(FileManager fm, String... args) {
        if (args.length == 0) {
            System.out.println("오류: 이동할 디렉터리를 입력하세요.");
            return;
        }

        File newDir;
        if ("..".equals(args[0])) {
            newDir = PathUtils.getParentDirectory(fm.getCurrentDirectory());
        } else {
            newDir = new File(fm.getCurrentDirectory(), args[0]);
        }

        if (PathUtils.isValidDirectory(newDir)) {
            fm.setCurrentDirectory(newDir);
            System.out.println("현재 디렉터리: " + PathUtils.getCanonicalPath(newDir));
        } else {
            System.out.println("오류: 디렉터리를 찾을 수 없습니다.");
        }
    }
}
