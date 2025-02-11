package dev.file.controller;

import dev.file.service.LSHandler;
import dev.file.service.CDHandler;
import dev.file.service.DirectoryManager;
import dev.file.service.MVHandler;

import dev.file.view.UserInput;

public class IOController {

	private DirectoryManager directoryManager;
	private LSHandler ls;
	private CDHandler cd;
	private MVHandler mv;
	
	public IOController() {
		this.directoryManager = new DirectoryManager();
		this.ls = new LSHandler();
		this.cd = new CDHandler(directoryManager);
		this.mv = new MVHandler(directoryManager);
	}
	
	public void run() {
		UserInput userInput = new UserInput();
		
		while (true) {
			System.out.print(directoryManager.getCurrentDirectory() + " $ ");
			
			String input = userInput.inputCommand();
			
			String[] command = input.split(" ");
			
			if (command[0].equals("ls")) {
				if (command.length > 1) {
					ls.printDirectoryList(command[1]);
				} else {
					ls.printDirectoryList(directoryManager.getCurrentDirectory());
				}
			}
			
			if (command[0].equals("cd")) {
				cd.changeDirectory(command[1]);
			}
			
			if (command[0].equals("mv")) {
				mv.move(command[1], command[2]);
			}
		}
	}
}
