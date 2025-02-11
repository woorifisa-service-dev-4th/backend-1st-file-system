package dev.file.service;

import java.io.File;

public class DirectoryManager {
    private String currentDirectory;

    public DirectoryManager() {
        this.currentDirectory = System.getProperty("user.dir");
    }

    public String getCurrentDirectory() {
        return this.currentDirectory;
    }
    
    public void setNewDirectory (String path) {
    	File dir = new File(path);
    	
    	this.currentDirectory = dir.getAbsolutePath();
    }
}