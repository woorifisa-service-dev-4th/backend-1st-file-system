package filemanager.command;

import filemanager.FileManager;
import filemanager.utils.FileUtils;
import filemanager.utils.PathUtils;
import java.io.File;

public class MvCommand implements Command {
    @Override
    public void execute(FileManager fm, String... args) {
        if (args.length < 2) {
            System.out.println("오류: 원본과 대상 경로를 입력하세요.");
            return;
        }

        File source = new File(fm.getCurrentDirectory(), args[0]);
        System.out.println(">>> DEBUG: 원본 파일 입력값 - " + args[0]);
        System.out.println(">>> DEBUG: 원본 파일 초기 경로 - " + source.getAbsolutePath());

        source = new File(PathUtils.getCanonicalPath(source));
        System.out.println(">>> DEBUG: 원본 파일 변환된 경로 - " + source.getAbsolutePath());

        if (!source.exists()) {
            System.out.println("오류: 파일 또는 폴더를 찾을 수 없습니다.");
            return;
        }

        File destination;
        if (args[1].startsWith(File.separator)) {
            destination = new File(args[1]);
        } else {
            destination = new File(fm.getRootDirectory(), args[1]);
        }

        destination = new File(PathUtils.getCanonicalPath(destination));
        System.out.println(">>> DEBUG: 대상 파일 변환된 경로 - " + destination.getAbsolutePath());

        if (destination.exists() && destination.isDirectory()) {
            destination = new File(destination, source.getName());
        } else {
            String sourceName = source.getName();
            int dotIndex = sourceName.lastIndexOf(".");
            if (dotIndex != -1 && !destination.getName().contains(".")) {
                String extension = sourceName.substring(dotIndex);
                destination = new File(destination.getAbsolutePath() + extension);
                System.out.println(">>> DEBUG: 확장자 자동 추가 - " + destination.getAbsolutePath());
            }

            File parentDir = destination.getParentFile();
            if (parentDir == null || !parentDir.exists()) {
                System.out.println("오류: 대상 경로를 찾을 수 없습니다.");
                return;
            }
        }

        if (FileUtils.moveFileOrDirectory(source, destination)) {
            System.out.println("✅ " + source.getAbsolutePath() + " → " + destination.getAbsolutePath() + "로 이동되었습니다.");
        } else {
            System.out.println("오류: 이동 중 문제가 발생했습니다.");
        }
    }
}
