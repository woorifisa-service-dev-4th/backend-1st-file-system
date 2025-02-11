package dev.file.service;

import java.io.File;
import java.io.IOException;

public class CDHandler {
    private DirectoryManager directoryManager;

    public CDHandler(DirectoryManager directoryManager) {
        this.directoryManager = directoryManager;
    }

    public void changeDirectory(String path) {
    	File dir = new File(directoryManager.getCurrentDirectory(), path);
    	
    	try {
    		dir = dir.getCanonicalFile();
    		
    		if (!dir.exists() || !dir.isDirectory()) {
    			System.out.println("오류: 디렉토리를 찾을 수 없습니다.");
    			return;
    		}
    		
    		String newPath = dir.getAbsolutePath();
    		
    		directoryManager.setNewDirectory(newPath);
    	} catch (IOException e) {
    		System.out.println(e.getMessage());
    	}
    }
}