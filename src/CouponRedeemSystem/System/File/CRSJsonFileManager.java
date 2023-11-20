package CouponRedeemSystem.System.File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.io.IOUtils;

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
    return "Data/" + dirName;
  }

  /**
   * Creates New File.
   *
   * @param dirName  the dir name
   * @param fileName the file name
   * @return the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public File createJson(String dirName, String fileName) {
    try {
      String pathStr = getDirectoryPath(dirName);
      File dir = new File(pathStr);
      if (!dir.exists()) {
        dir.mkdir();
      }
      File file = new File(pathStr + "/" + fileName + ".json");
      if (!file.exists()) {
        file.createNewFile();
      }
      return file;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Modify JSON by DynaBean.
   *
   * @param dirName  the dir name
   * @param fileName the file name
   * @param content  the content
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void modifyJSON(String dirName, String fileName, DynaBean content) {
    modifyJSON(dirName, fileName, JSONObject.fromObject(content));
  }

  /**
   * Modify JSON by JSONObject.
   *
   * @param dirName  the dir name
   * @param fileName the file name
   * @param content  the content
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void modifyJSON(String dirName, String fileName, JSONObject content) {
    try {
      File file = createJson(dirName, fileName);
      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(content.toString());
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Safe modify JSON by key.
   *
   * @param dirName  the dir name
   * @param fileName the file name
   * @param key      the key
   * @param obj      the obj
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void safeModifyJSONByKey(
    String dirName,
    String fileName,
    String key,
    Object obj
  ) {
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
   * @param dirName  the dir name
   * @param fileName the file name
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void deleteJSON(String dirName, String fileName) {
    File file = createJson(dirName, fileName);
    if (!file.delete()) {
      System.out.println("Delete is failed!");
    }
  }

  /**
   * Removes the element.
   *
   * @param dirName  the dir name
   * @param fileName the file name
   * @param key      the key
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void removeElement(String dirName, String fileName, String key) {
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
  public JSONObject searchJSON(String fileName) {
    return searchJSON(fileName, null);
  }

  public JSONObject searchJSON(String fileName, File[] fileList) {
    File jsonFile = searchFile(fileName);
    if (jsonFile == null || jsonFile.isDirectory()) {
      return null;
    }
    return convertFileTextToJSON(jsonFile);
  }

  /**
   * Search file.
   *
   * @param fileName the file name
   * @return the file
   */

  public File searchFile(String fileName) {
    File rootDirectory = new File("Data");
    return searchFile(fileName + ".json", rootDirectory);
  }

  public File searchFile(String fileName, File directory) {
    if (directory == null || !directory.isDirectory()) {
      return null;
    }

    File[] fileList = directory.listFiles();
    if (fileList != null) {
      for (File file : fileList) {
        if (file.isDirectory()) {
          File result = searchFile(fileName, file);
          if (result != null) {
            return result;
          }
        } else if (file.getName().equals(fileName)) {
          return file;
        }
      }
    }

    return null;
  }

  /**
   * Convert file text to JSON.
   *
   * @param file the file
   * @return the JSON object
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public JSONObject convertFileTextToJSON(File file) {
    try {
      InputStream iStream = new FileInputStream(file);
      String jsonText = IOUtils.toString(iStream);
      if (jsonText.isBlank()) jsonText = "{}";
      iStream.close();
      return (JSONObject) JSONSerializer.toJSON(jsonText);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
