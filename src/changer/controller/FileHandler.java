package changer.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class FileHandler
{

	private File mainDirectory;

	public FileHandler()
	{
		try
		{
			String parentPath = getParentDirectory();
			String Path = parentPath + "/" + "Backgrounds" + "/";
			if (Files.exists(Paths.get(Path)))
			{
				System.out.println("exists");
				mainDirectory = new File(Path);
			} else
			{
				mainDirectory = new File(Path);
				System.out.println("making crap");
				mainDirectory.mkdir();
			}
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}

	public FileHandler(String path)
	{
		String parentPath = path;
		String Path = parentPath + "/";// +"Backgrounds"+"/";
		if (Files.exists(Paths.get(Path)))
		{
			mainDirectory = new File(Path);
		} else
		{
			mainDirectory = new File(Path);
			mainDirectory.mkdir();
		}
	}

	private String getParentDirectory() throws UnsupportedEncodingException
	{
		String parentPath = "";
		URL childPath = FileHandler.class.getProtectionDomain().getCodeSource().getLocation();
		String childFilePath = URLDecoder.decode(childPath.getFile(), "UTF-8");
		parentPath = new File(childFilePath).getParentFile().getPath();
		return parentPath;
	}

	public void writeData(String fileName, String stringData)
	{
		byte data[] = stringData.getBytes();
		String fileType = ".txt";
		Path path = Paths.get(mainDirectory.getPath() + "/" + fileName + fileType);
		try
		{
			Files.write(path, data, StandardOpenOption.CREATE);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void writeData(String fileName, String stringData, String fileType)
	{
		byte data[] = stringData.getBytes();
		fileType = "." + fileType;
		Path path = Paths.get(mainDirectory.getPath() + "/" + fileName + fileType);
		try
		{
			Files.write(path, data, StandardOpenOption.CREATE);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void writeData(String fileName, String stringData, String fileType, String savePath)
	{
		byte data[] = stringData.getBytes();
		fileType = "." + fileType;
		Path path = Paths.get(savePath + "/" + fileName + fileType);
		try
		{
			Files.write(path, data, StandardOpenOption.CREATE);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public String readData(String fileName)
	{
		String Data = "";
		Path path = Paths.get(mainDirectory.getPath() + "/" + fileName + ".txt");
		try
		{
			Data = new String(Files.readAllBytes(path), "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			Data = null;
		}
		return Data;
	}

	public void ExportResource(String resourceName, String saveName) throws Exception
	{
		System.out.println(FileHandler.class.getResource(resourceName));
		System.out.println(mainDirectory.getAbsolutePath());
		System.out.println(FileHandler.class.getResource(resourceName).toURI());

		final Map<String, String> env = new HashMap<>();
		final String[] array = FileHandler.class.getResource(resourceName).toURI().toString().split("!");
		final FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), env);
		final Path path = fs.getPath(array[1]);
		Files.copy(path, Paths.get(mainDirectory.getAbsolutePath() + "/" + saveName), StandardCopyOption.REPLACE_EXISTING);
		fs.close();
	}
}