package CouponRedeemSystem.Test;

import CouponRedeemSystem.Test.model.MainTest;
import org.junit.Assert;
import org.junit.Test;

public class PasswordTest extends MainTest {

  @Test
  public void createPassword() {
    String username = "userTest";
    String password = "passwordTest";

    boolean result = passwordManager.createNewPassword(username, password);
    Assert.assertEquals(true, result);
    passwordManager.deletePassword(username);
  }

  @Test
  public void createPasswordFail() {}

  @Test
  public void deletePassword() {
    String username = "userTest";
    String password = "passwordTest";

    passwordManager.createNewPassword(username, password);
    boolean result = passwordManager.deletePassword(username);
    Assert.assertEquals(true, result);
  }

  @Test
  public void deletePasswordFail() {
    String username = "userTest";

    boolean result = passwordManager.deletePassword(username);
    Assert.assertEquals(false, result);
  }

  @Test
  public void checkPassword() {
    String username = "userTest";
    String password = "passwordTest";

    passwordManager.createNewPassword(username, password);
    boolean result = passwordManager.checkPasswordValid(username, password);
    Assert.assertEquals(true, result);
    passwordManager.deletePassword(username);
  }

  @Test
  public void checkPasswordFail() {
    String username = "userTest";
    String password = "passwordTest";

    passwordManager.createNewPassword(username, password);
    boolean result = passwordManager.checkPasswordValid(username, "password");
    Assert.assertEquals(false, result);
    passwordManager.deletePassword(username);
  }
}
