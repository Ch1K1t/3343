package CouponRedeemSystem.Test;

import CouponRedeemSystem.System.Util.Util;
import CouponRedeemSystem.Test.model.MainTest;
import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PasswordTest extends MainTest {

  @Before
  @After
  public void reset() {
    Util.clearSystem();
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
    passwordManager.createNewPassword(userName, password);
    JSONObject jsonObject = passwordManager.getPasswordRefTable();
    Object text = jsonObject.get(userName);

    Assert.assertNotEquals(null, text);
  }

  @Test
  public void deletePasswordTest() {
    passwordManager.createNewPassword(userName, password);
    passwordManager.deletePassword(userName);

    JSONObject jsonObject = passwordManager.getPasswordRefTable();
    Object text = jsonObject.get(userName);

    Assert.assertEquals(null, text);
  }

  @Test
  public void checkPasswordTest() {
    passwordManager.createNewPassword(userName, password);
    String result = passwordManager.checkPasswordValid(userName, password);

    Assert.assertEquals("success", result);
  }

  // Check non-exist user
  @Test
  public void checkPasswordTestFail() {
    String result = passwordManager.checkPasswordValid(userName, password);

    Assert.assertEquals("not found", result);
  }

  // Check wrong password
  @Test
  public void checkPasswordTestFail2() {
    passwordManager.createNewPassword(userName, password);
    String result = passwordManager.checkPasswordValid(userName, "password");

    Assert.assertEquals("not correct", result);
  }
}
