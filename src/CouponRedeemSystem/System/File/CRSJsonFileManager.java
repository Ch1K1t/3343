package CouponRedeemSystem.System.File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.io.IOUtils;

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
   * Creates the JSON file.
   *
   * @param dirName the dir name
   * @param fileName the file name
   * @return the file
   */
  public File createJSONFile(String dirName, String fileName) {
    try {
      String pathStr = "Data/" + dirName;
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
   * Modify JSON.
   *
   * @param dirName the dir name
   * @param fileName the file name
   * @param content the content
   */
  public void modifyJSON(String dirName, String fileName, JSONObject content) {
    try {
      File file = createJSONFile(dirName, fileName);
      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(content.toString());
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Delete JSON.
   *
   * @param dirName the dir name
   * @param fileName the file name
   */
  public void deleteJSON(String dirName, String fileName) {
    File file = new File("Data/" + dirName + "/" + fileName + ".json");
    file.delete();
  }

  /**
   * Search JSON.
   *
   * @param fileName the file name
   * @return the JSON object
   */
  public JSONObject searchJSON(String fileName) {
    File rootDirectory = new File("Data");

    File jsonFile = searchFile(fileName + ".json", rootDirectory);
    if (jsonFile == null || jsonFile.isDirectory()) {
      return null;
    }
    return convertFileTextToJSON(jsonFile);
  }

  /**
   * Search file.
   *
   * @param fileName the file name
   * @param directory the directory
   * @return the file
   */
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
   */
  public JSONObject convertFileTextToJSON(File file) {
    try {
      InputStream iStream = new FileInputStream(file);
      String jsonText = IOUtils.toString(iStream, "UTF-8");
      if (jsonText.isBlank()) jsonText = "{}";
      iStream.close();
      return (JSONObject) JSONSerializer.toJSON(jsonText);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
