package CouponRedeemSystem.Test;

import CouponRedeemSystem.Test.model.MainTest;
import java.io.File;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class CRSTest extends MainTest {

  @Test
  public void createJSONTest() {
    String dirName = "Test";
    String fileName = "test";

    File file = jsonFileManager.createJSONFile(dirName, fileName);
    Assert.assertEquals(true, file.exists());
    jsonFileManager.deleteJSON(dirName, fileName);
  }

  @Test
  public void createJSONFileFailTest() {}

  @Test
  public void modifyJSONTest() {
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
  public void modifyJSONFileFailTest() {}

  @Test
  public void searchFileTest() {
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
  public void searchFileFailTest() {
    String dirName = "Test";
    String fileName = "test";

    File file = jsonFileManager.searchFile(
      fileName,
      new File("Data/" + dirName)
    );
    Assert.assertEquals(null, file);
  }

  @Test
  public void convertFileTextToJSONTest() {
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
  public void convertFileTextToJSONFailTest() {}

  @Test
  public void searchJSONTest() {
    String dirName = "Test";
    String fileName = "test";

    jsonFileManager.createJSONFile(dirName, fileName);
    JSONObject json = jsonFileManager.searchJSON(fileName);
    JSONObject expectedJson = new JSONObject();
    Assert.assertEquals(expectedJson, json);
    jsonFileManager.deleteJSON(dirName, fileName);
  }

  @Test
  public void searchJSONFailTest() {
    String fileName = "test";

    JSONObject json = jsonFileManager.searchJSON(fileName);
    Assert.assertEquals(null, json);
  }

  @Test
  public void deleteJSONTest() {
    String dirName = "Test";
    String fileName = "test";

    jsonFileManager.createJSONFile(dirName, fileName);
    jsonFileManager.deleteJSON(dirName, fileName);
    JSONObject jsonObject = jsonFileManager.searchJSON(fileName);
    Assert.assertEquals(null, jsonObject);
  }
}
