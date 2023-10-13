package CouponRedeemSystem.System.File;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import CouponRedeemSystem.System.File.model.TextFile;

public class FileManager {
	private static FileManager instance;
	
	private FileManager() {}
	
	public static FileManager getInstance() {
		instance = instance == null ? new FileManager() : instance;
		return instance;
	}
	
	private String getFolderPath(String dirName) {
		return "../../../../Data/" + dirName;
	}
	
	private File getTextFile(String dirName, TextFile file) throws IOException {
		String dirPathString = getFolderPath(dirName);
		File folder = new File(dirPathString);
		if(!folder.exists()) {
			folder.mkdir();
		}
		File realFile = new File(folder.getPath() + file.getFileName());
		if (!realFile.exists()) {
			realFile.createNewFile();
		}
		return realFile; 
	}
	
	public void modifyFile(String dirName, TextFile file) throws IOException {
		File textFile = getTextFile(dirName, file);
		FileWriter fileWriter = new FileWriter(textFile);
		fileWriter.write(file.getFileContent());
		fileWriter.close();
	}
	
	public void deleteFile(String dirName, File file) throws IOException {
		File textFile = file;
		textFile.delete();
	}
	
	public File searchFile(String dirName, String fileName) {
		String dirPathString = getFolderPath(dirName);
		File folder = new File(dirPathString);
		File[] folderList = folder.listFiles();
		for (File file: folderList) {
			if (file.getName().equals(fileName)) {
				return file;
			}
		}
		return null;
	}
}
