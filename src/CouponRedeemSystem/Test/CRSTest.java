package CouponRedeemSystem.Test;

import CouponRedeemSystem.System.Util.Util;
import CouponRedeemSystem.Test.model.MainTest;
import java.io.File;
import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class CRSTest extends MainTest {

  @After
  public void reset() {
    jsonFileManager.deleteJSON(dirName, fileName);
    Util.clearSystem();
  }

  @Test
  public void createJSONTest() {
    File file = jsonFileManager.createJSONFile(dirName, fileName);

    Assert.assertEquals(true, file.exists());
  }

  @Test
  public void searchJSONTest() {
    jsonFileManager.createJSONFile(dirName, fileName);
    JSONObject json = jsonFileManager.searchJSON(fileName);
    JSONObject expectedJson = new JSONObject();

    Assert.assertEquals(expectedJson, json);
  }

  @Test
  public void searchJSONTestFail() {
    JSONObject json = jsonFileManager.searchJSON(fileName);
    Assert.assertEquals(null, json);
  }

  @Test
  public void searchFileTest() {
    jsonFileManager.createJSONFile(dirName, fileName);
    File file = jsonFileManager.searchFile(fileName + ".json", directory);
    File expectedFile = new File("Data/" + dirName + "/" + fileName + ".json");

    Assert.assertEquals(expectedFile, file);
  }

  @Test
  public void searchFileTestFail() {
    File file = jsonFileManager.searchFile(fileName, directory);

    Assert.assertEquals(null, file);
  }

  @Test
  public void modifyJSONTest() {
    JSONObject content = new JSONObject();
    content.put("test", "test");

    jsonFileManager.modifyJSON(dirName, fileName, content);
    JSONObject json = jsonFileManager.searchJSON(fileName);

    Assert.assertEquals(content, json);
  }

  @Test
  public void deleteJSONTest() {
    jsonFileManager.createJSONFile(dirName, fileName);
    jsonFileManager.deleteJSON(dirName, fileName);
    JSONObject json = jsonFileManager.searchJSON(fileName);

    Assert.assertEquals(null, json);
  }

  @Test
  public void convertFileTextToJSONTest() {
    JSONObject content = new JSONObject();
    content.put("test", "test");
    jsonFileManager.modifyJSON(dirName, fileName, content);
    File file = jsonFileManager.searchFile(fileName + ".json", directory);
    JSONObject json = jsonFileManager.convertFileTextToJSON(file);

    Assert.assertEquals(content, json);
  }
}
