package CouponRedeemSystem.Test;

import CouponRedeemSystem.Test.model.MainTest;
import java.io.File;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class CRSTest extends MainTest {

  @Test
  public void createJSONFile() {
    String dirName = "Test";
    String fileName = "test";

    File file = jsonFileManager.createJSONFile(dirName, fileName);
    Assert.assertEquals(true, file.exists());
    jsonFileManager.deleteJSON(dirName, fileName);
  }

  @Test
  public void createJSONFileFail() {}

  @Test
  public void modifyJSONFile() {
    String dirName = "Test";
    String fileName = "test";
    JSONObject content = new JSONObject();
    content.put("test", "test");

    jsonFileManager.modifyJSON(dirName, fileName, content);
    JSONObject json = jsonFileManager.searchJSON(fileName);
    Assert.assertEquals(content, json);
    jsonFileManager.deleteJSON(dirName, fileName);
  }

  @Test
  public void modifyJSONFileFail() {}

  @Test
  public void searchFile() {
    String dirName = "Test";
    String fileName = "test";

    jsonFileManager.createJSONFile(dirName, fileName);
    File file = jsonFileManager.searchFile(
      fileName + ".json",
      new File("Data/" + dirName)
    );
    File expectedFile = new File("Data/" + dirName + "/" + fileName + ".json");
    Assert.assertEquals(expectedFile, file);
    jsonFileManager.deleteJSON(dirName, fileName);
  }

  @Test
  public void searchFileFail() {
    String dirName = "Test";
    String fileName = "test";

    File file = jsonFileManager.searchFile(
      fileName,
      new File("Data/" + dirName)
    );
    Assert.assertEquals(null, file);
  }

  @Test
  public void convertFileTextToJSON() {
    String dirName = "Test";
    String fileName = "test";
    String contentKey = "test";
    String contentValue = "test";

    JSONObject content = new JSONObject();
    content.put(contentKey, contentValue);
    jsonFileManager.modifyJSON(dirName, fileName, content);
    File file = jsonFileManager.searchFile(
      fileName + ".json",
      new File("Data/" + dirName)
    );
    JSONObject json = jsonFileManager.convertFileTextToJSON(file);
    Assert.assertEquals(content, json);
    jsonFileManager.deleteJSON(dirName, fileName);
  }

  @Test
  public void convertFileTextToJSONFail() {}

  @Test
  public void searchJSONFile() {
    String dirName = "Test";
    String fileName = "test";

    jsonFileManager.createJSONFile(dirName, fileName);
    JSONObject json = jsonFileManager.searchJSON(fileName);
    JSONObject expectedJson = new JSONObject();
    Assert.assertEquals(expectedJson, json);
    jsonFileManager.deleteJSON(dirName, fileName);
  }

  @Test
  public void searchJSONFileFail() {
    String fileName = "test";

    JSONObject json = jsonFileManager.searchJSON(fileName);
    Assert.assertEquals(null, json);
  }

  @Test
  public void deleteJSONFile() {
    String dirName = "Test";
    String fileName = "test";

    jsonFileManager.createJSONFile(dirName, fileName);
    jsonFileManager.deleteJSON(dirName, fileName);
    JSONObject jsonObject = jsonFileManager.searchJSON(fileName);
    Assert.assertEquals(null, jsonObject);
  }
}
