package CouponRedeemSystem.Test;

import CouponRedeemSystem.Test.model.MainTest;
import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class PasswordTest extends MainTest {

  @After
  public void reset() {
    if (
      passwordManager.checkPasswordValid(userName, password).equals("success")
    ) {
      passwordManager.deletePassword(userName);
    }
  }

  // Already have a reference table
  @Test
  public void getPasswordRefTableTest() {
    JSONObject refTable = passwordManager.getPasswordRefTable();
    JSONObject expected = jsonFileManager.searchJSON("ReferenceTable");

    Assert.assertEquals(expected.toString(), refTable.toString());
  }

  // No reference table
  @Test
  public void getPasswordRefTableTest2() {
    jsonFileManager.deleteJSON("Password", "ReferenceTable");
    JSONObject refTable = passwordManager.getPasswordRefTable();
    JSONObject expected = new JSONObject();

    Assert.assertEquals(expected.toString(), refTable.toString());
  }

  @Test
  public void createPasswordTest() {
    boolean result = passwordManager.createNewPassword(userName, password);

    Assert.assertEquals(true, result);
  }

  @Test
  public void deletePasswordTest() {
    passwordManager.createNewPassword(userName, password);
    boolean result = passwordManager.deletePassword(userName);

    Assert.assertEquals(true, result);
  }

  @Test
  public void deletePasswordTestFail() {
    boolean result = passwordManager.deletePassword(userName);

    Assert.assertEquals(false, result);
  }

  @Test
  public void checkPasswordTest() {
    passwordManager.createNewPassword(userName, password);
    String result = passwordManager.checkPasswordValid(userName, password);

    Assert.assertEquals("success", result);
  }

  @Test
  public void checkPasswordTestFail() {
    String result = passwordManager.checkPasswordValid(userName, password);

    Assert.assertEquals("not found", result);
  }

  @Test
  public void checkPasswordTestFail2() {
    passwordManager.createNewPassword(userName, password);
    String result = passwordManager.checkPasswordValid(userName, "password");

    Assert.assertEquals("not correct", result);
  }
}
