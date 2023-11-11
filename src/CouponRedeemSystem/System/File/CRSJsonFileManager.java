package CouponRedeemSystem.System.File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.io.IOUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

// TODO: Auto-generated Javadoc
/**
 * The Class CRSJsonFileManager.
 */
public class CRSJsonFileManager {
	
	/** The instance. */
	private static CRSJsonFileManager instance;
	
	/**
	 * Instantiates a new CRS json file manager.
	 */
	private CRSJsonFileManager() {}
	
	/**
	 * Gets the single instance of CRSJsonFileManager.
	 *
	 * @return single instance of CRSJsonFileManager
	 */
	public static CRSJsonFileManager getInstance() {
		if (instance == null) {
			instance = new CRSJsonFileManager();
		}
		return instance;
	}
	
	/**
	 * Gets the directory path.
	 *
	 * @param dirName the dir name
	 * @return the directory path
	 */
	private String getDirectoryPath(String dirName) {
		return "Data\\" + dirName;
	}
	
	/**
	 * Creates New File.
	 *
	 * @param dirName the dir name
	 * @param fileName the file name
	 * @return the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private File createJson(String dirName, String fileName) throws IOException {
		String pathStr = getDirectoryPath(dirName);
		File dir = new File(pathStr);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File file = new File(pathStr + "\\" + fileName + ".json");
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}
	
	/**
	 * Modify JSON by DynaBean.
	 *
	 * @param dirName the dir name
	 * @param fileName the file name
	 * @param content the content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void modifyJSON(String dirName, String fileName, DynaBean content) throws IOException {
		modifyJSON(dirName, fileName, JSONObject.fromObject(content));
	}
	
	/**
	 * Modify JSON by JSONObject.
	 *
	 * @param dirName the dir name
	 * @param fileName the file name
	 * @param content the content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void modifyJSON(String dirName, String fileName, JSONObject content) throws IOException {
		File file = createJson(dirName, fileName);
		FileWriter fileWriter = new FileWriter(file);
		System.out.println(content.toString());
		System.out.println(file.getAbsolutePath());
		fileWriter.write(content.toString());
		fileWriter.close();
	}
	
	/**
	 * Safe modify JSON by key.
	 *
	 * @param dirName the dir name
	 * @param fileName the file name
	 * @param key the key
	 * @param obj the obj
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void safeModifyJSONByKey(String dirName, String fileName, String key, Object obj) throws IOException {
		File file = createJson(dirName, fileName);
		JSONObject jsonObject = convertFileTextToJSON(file);
		if (jsonObject.containsKey(key)) {
			jsonObject.remove(key);
		}
		jsonObject.put(key, jsonObject);
		modifyJSON(dirName, fileName, jsonObject);
	}
	
	/**
	 * Delete JSON.
	 *
	 * @param dirName the dir name
	 * @param fileName the file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void deleteJSON(String dirName, String fileName) throws IOException {
		File file = createJson(dirName, fileName);
		if(!file.delete()) {
			System.out.println("Delete is failed!");
		}
	}
	
	/**
	 * Removes the element.
	 *
	 * @param dirName the dir name
	 * @param fileName the file name
	 * @param key the key
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void removeElement(String dirName, String fileName, String key) throws IOException {
		File file = createJson(dirName, fileName);
		JSONObject jsonObject = convertFileTextToJSON(file);
		if (jsonObject.containsKey(key)) {
			jsonObject.remove(key);
			modifyJSON(dirName, fileName, jsonObject);
		} else {
			System.out.println("Key is not exist in this Json");
		}
	}
	
	/**
	 * Search JSON.
	 *
	 * @param fileName the file name
	 * @return the JSON object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JSONObject searchJSON(String fileName, File[] fileList) throws IOException {
		File jsonFile = searchFile(fileName, null);
		if (jsonFile.isDirectory()) {
			System.out.println("JSON not Found! Return emmpty json");
			return new JSONObject();
		}
		return convertFileTextToJSON(jsonFile);
	}
	
	/**
	 * Searh file.
	 *
	 * @param fileName the file name
	 * @return the file
	 */

	public File searchFile(String fileName, File[] fileList) {
		File rootDirectory = new File("Data");
		if(fileList == null) {
			fileList = rootDirectory.listFiles();
		} 
		File returnFile = rootDirectory;
		for (File file: fileList) {
			if (file.isDirectory()) {
				returnFile = searchFile(fileName, file.listFiles());
			} else if (file.getName().equals(fileName)) {
				returnFile = file;
			}
			if (returnFile != rootDirectory) {
				return returnFile;
			} 
		}
		System.out.println("File not found!");
		return returnFile;
	}
	
	/**
	 * Convert file text to JSON.
	 *
	 * @param file the file
	 * @return the JSON object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private JSONObject convertFileTextToJSON(File file) throws IOException {
		InputStream iStream = new FileInputStream(file);
		String jsonText = IOUtils.toString(iStream);
		return (JSONObject) JSONSerializer.toJSON(jsonText);
	}
}
