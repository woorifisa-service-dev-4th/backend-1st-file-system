package dev.file.service;

import java.io.File;
import java.util.Arrays;

public class LSHandler {
	
	public void printDirectoryList(String directory) {
		File dir = new File(directory);
		
		if (!dir.exists()) {
			System.out.println("오류: 디렉토리를 찾을 수 없습니다.");
			return;
		}
		
		String[] fileList = dir.list();
		
		if (fileList.length == 0) {
			System.out.println("디렉토리가 비어 있습니다.");
			return;
		}
		
		Arrays.sort(fileList);
		for (String filename : fileList) {
			File file = new File(dir, filename);
			if (file.isDirectory()) {
				System.out.print("[D] ");
			} else {
				System.out.print("[F] ");
			}
			System.out.println(filename);
		}
	}
}
