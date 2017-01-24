package changer.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

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
			for(int index = 1; index<=3;index++){
			fileHandler.ExportResource("resources/Desktop"+index+".jpg","Desktop"+index+".jpg");}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		fileHandler = new FileHandler("/Users/"+userName+"/Documents/Backgrounds");
		fileHandler.writeData("com.user.loginscript", generatePlist(userName), "plist","/Users/"+userName+"/Documents/Backgrounds");
		try{
		Process process = Runtime.getRuntime().exec("/usr/bin/sudo -S sudo cp " + "/Users/"+userName+"/Documents/Backgrounds/com.user.loginscript.plist" + " /Library/LaunchAgents/com.user.loginscript.plist");
		Writer toSudo = new OutputStreamWriter(process.getOutputStream());
		toSudo.write(password);
		toSudo.write('\n');  // sudo's docs demand a newline after the password
		toSudo.close();      // but closing the stream might be sufficient}
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,"Incorrect Password try again");
			System.exit(0);
		}
		try
		{
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e1)
		{
			System.out.println("Noooooo");
			e1.printStackTrace();
		}
		if(!Files.exists(Paths.get("/Library/LaunchAgents/com.user.loginscript.plist")))
		{
			JOptionPane.showMessageDialog(null,"Incorrect Password try again");
		}
		
		try
		{
			Files.delete(Paths.get("/Users/"+userName+"/Documents/Backgrounds/com.user.loginscript.plist"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileHandler.writeData("StartScript", generateSh(userName), "sh");
		fileHandler.writeData("BackgroundData", generateTxt(userName,password));
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
	"\n\t<string>/Users/"+userName+"/Documents/Backgrounds/StartScript.sh</string>"+
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
				"\ncd /Users/"+userName+"/Documents/Backgrounds/"+
				"\njava -jar DontRunMe.jar";
		return data;

	}
	private String generateTxt(String userName, String Password)
	{
		return "UserName: "+userName+
				"\nPassword: "+ Password+
				"\nRunFixMode: "+ "false"+
				"\nRunSlideShow: "+ "true"+
				"\nSlideShowTimer: "+ "M:5,S:0,MS:0"+
				"\nUpdate: " + "false"+
				"\nVersion: " + "1.0.0";
	}
	

}
