package dev.file;

import dev.file.controller.IOController;

public class MainApplication {

	public static void main(String[] args) {
		IOController ioController = new IOController();
		
		ioController.run();
	}

}
