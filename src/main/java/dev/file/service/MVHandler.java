package dev.file.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class MVHandler {
	private DirectoryManager directoryManager;
	
	public MVHandler(DirectoryManager directoryManager) {
		this.directoryManager = directoryManager;
	}

    public void move(String sourcePath, String destinationPath) {
        File source = new File(directoryManager.getCurrentDirectory(), sourcePath);
        File destination = new File(directoryManager.getCurrentDirectory(), destinationPath);

        if (!source.exists()) {
            System.out.println("오류: 파일 또는 디렉토리를 찾을 수 없습니다.");
            return;
        }

        if (destination.isDirectory()) {
            destination = new File(destination, source.getName());
        }

        try {
            Files.move(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("파일이 " + destination.getAbsolutePath() + " 폴더로 이동되었습니다.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}