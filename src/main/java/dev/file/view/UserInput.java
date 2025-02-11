package dev.file.view;

import java.util.Scanner;

public class UserInput {
	
	public String inputCommand() {
		
		Scanner scanner = new Scanner(System.in);
		
		String command = scanner.nextLine();
		
		return command;
	}
}
