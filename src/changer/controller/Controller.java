package changer.controller;

import java.io.File;
import java.net.URL;

import javax.swing.JOptionPane;

public class Controller
{
	private FileHandler fileHandler; 
	
	public Controller()
	{
		fileHandler = new FileHandler();
	}
	
	public void start()
	{
		String userName = JOptionPane.showInputDialog("Please Enter Your UserName");
		String password = JOptionPane.showInputDialog("Please Enter Your Password");
		fileHandler = new FileHandler("/Users/"+userName+"/Documents/Backgrounds");
		fileHandler = new FileHandler("/Users/"+userName+"/Documents/Backgrounds/Pictures");
		try
		{
			fileHandler.ExportResource("/resources/Desktop1.jpg");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		fileHandler = new FileHandler("/Users/"+userName+"/Documents/Backgrounds");
		fileHandler.writeData("com.user.loginscript", generatePlist(userName), "plist");
		fileHandler.writeData("StartScript", generateSh(userName), "sh");
	}
	
	
	private String generatePlist(String userName)
	{
		String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
"\n<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">"+
"\n<plist version=\"1.0\">"+
"\n<dict>"+
	"\n\t<key>Label</key>"+
	"\n\t<string>com.user.loginscript</string>"+
	"\n\t<key>Program</key>"+
	"\n\t<string>/Users/"+userName+"/Desktop/Randomizer/goodstuff.sh</string>"+
	"\n\t<key>RunAtLoad</key>"+
	"\n\t<true/>"+
"\n</dict>"+
"\n</plist>";
		return data;
	}
	private String generateSh(String userName)
	{
		String data = "#!/bin/bash"+
				"\n#coolstuff"+
				"\ncd /Users/"+userName+"/Desktop/Randomizer/"+
				"\njava -jar RandomChanger.jar";
		return data;

	}
	

}
