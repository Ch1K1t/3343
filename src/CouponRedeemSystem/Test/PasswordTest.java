package CouponRedeemSystem.Test;

import CouponRedeemSystem.Test.model.MainTest;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class PasswordTest extends MainTest {

  @Test
  public void getPasswordRefTableTest() {
    // Already have a reference table
    JSONObject jsonObject = passwordManager.getPasswordRefTable();
    JSONObject expected = jsonFileManager.searchJSON("ReferenceTable");

    Assert.assertEquals(expected.toString(), jsonObject.toString());
  }

  @Test
  public void getPasswordRefTableTest2() {
    // No reference table
    jsonFileManager.deleteJSON("Password", "ReferenceTable");
    JSONObject jsonObject = passwordManager.getPasswordRefTable();
    JSONObject expected = new JSONObject();

    Assert.assertEquals(expected.toString(), jsonObject.toString());
  }

  @Test
  public void createPasswordTest() {
    String username = "userTest";
    String password = "passwordTest";

    boolean result = passwordManager.createNewPassword(username, password);
    Assert.assertEquals(true, result);
    passwordManager.deletePassword(username);
  }

  @Test
  public void createPasswordFailTest() {}

  @Test
  public void deletePasswordTest() {
    String username = "userTest";
    String password = "passwordTest";

    passwordManager.createNewPassword(username, password);
    boolean result = passwordManager.deletePassword(username);
    Assert.assertEquals(true, result);
  }

  @Test
  public void deletePasswordFailTest() {
    String username = "userTest";

    boolean result = passwordManager.deletePassword(username);
    Assert.assertEquals(false, result);
  }

  @Test
  public void checkPasswordTest() {
    String username = "userTest";
    String password = "passwordTest";

    passwordManager.createNewPassword(username, password);
    boolean result = passwordManager.checkPasswordValid(username, password);
    Assert.assertEquals(true, result);
    passwordManager.deletePassword(username);
  }

  @Test
  public void checkPasswordFailTest1() {
    // No password
    String username = "userTest";
    String password = "passwordTest";

    boolean result = passwordManager.checkPasswordValid(username, password);
    Assert.assertEquals(false, result);
  }

  @Test
  public void checkPasswordFailTest2() {
    // Wrong password
    String username = "userTest";
    String password = "passwordTest";

    passwordManager.createNewPassword(username, password);
    boolean result = passwordManager.checkPasswordValid(username, "password");
    Assert.assertEquals(false, result);
  }
}
