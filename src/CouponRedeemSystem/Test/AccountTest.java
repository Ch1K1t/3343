package CouponRedeemSystem.Test;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Test.model.MainTest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class AccountTest extends MainTest {

  @Test
  public void createPasswordTest() {
    String username = "userTest";
    String password = "passwordTest";

    accountManager.createPassword(username, password);
    JSONObject jsonObject = passwordManager.getPasswordRefTable();
    String text = jsonObject.get(username).toString();
    Assert.assertEquals(
      true,
      encryptionManager.decryption(text).equals(password)
    );
    passwordManager.deletePassword(username);
  }

  @Test
  public void createPasswordTestFail() {
    String username = "userTest";
    String role = "Admin";
    String password = "passwordTest";

    Account account = accountManager.createAccount(username, role);
    boolean result = accountManager.createPassword(username, password);
    Assert.assertEquals(false, result);
    passwordManager.deletePassword(username);
    accountManager.deleteAccount(account);
  }

  @Test
  public void createNonUserAccountTest() {
    String username = "userTest";
    String[] roleList = new String[] { "Admin", "Shop", "Staff" };

    for (String role : roleList) {
      Account account = accountManager.createAccount(username, role);

      String expectedOutput =
        "Account{userName=\"" + "" + username + "\", role=\"" + role + "\"}";

      Assert.assertEquals(expectedOutput, account.toString());
      accountManager.deleteAccount(account);
    }
  }

  @Test
  public void createUserAccountTest() throws ParseException {
    String username = "userTest";
    String role = "User";
    int age = 20;
    String telNo = "12345678";
    String dateOfBirth = "11/11/2000";
    List<String> couponIDs = new ArrayList<String>();

    Account account = accountManager.createAccount(
      username,
      role,
      age,
      telNo,
      dateOfBirth
    );

    String expectedOutput =
      "Account{userName=\"" +
      username +
      "\", role=\"" +
      role +
      "\", age=" +
      age +
      ", telNo=\"" +
      telNo +
      "\", dateOfBirth=" +
      sdf.parse(dateOfBirth) +
      ", points=" +
      0.0 +
      ", couponIDs=" +
      couponIDs +
      "}";

    Assert.assertEquals(expectedOutput, account.toString());
    accountManager.deleteAccount(account);
  }

  @Test
  public void updateNonUserAccount() {
    String username = "userTest";
    String[] roleList = new String[] { "Admin", "Shop", "Staff" };

    for (String role : roleList) {
      Account account = accountManager.createAccount(username, role);

      JSONObject accountJson = jsonFileManager.searchJSON(username);

      String expectedOutput =
        "{\"userName\":\"" + username + "\", \"role\":\"" + role + "\"}";

      Assert.assertEquals(
        expectedOutput,
        accountManager.jsonToString(accountJson)
      );
      accountManager.deleteAccount(account);
    }
  }

  @Test
  public void updateUserAccount() {
    String username = "userTest";
    String role = "User";
    int age = 20;
    String telNo = "12345678";
    String dateOfBirth = "11/11/2000";

    Account account = accountManager.createAccount(
      username,
      role,
      age,
      telNo,
      dateOfBirth
    );

    JSONObject accountJson = jsonFileManager.searchJSON(username);

    String expectedOutput =
      "{\"userName\":\"" +
      username +
      "\", \"role\":\"" +
      role +
      "\", \"age\":" +
      age +
      ", \"telNo\":\"" +
      telNo +
      "\", \"dateOfBirth\":\"" +
      dateOfBirth +
      "\", \"points\":" +
      0.0 +
      ", \"couponIDs\":[]}";

    Assert.assertEquals(
      expectedOutput,
      accountManager.jsonToString(accountJson)
    );
    accountManager.deleteAccount(account);
  }

  @Test
  public void deleteAccountTest() {
    String username = "userTest";
    String role = "Admin";

    Account account = accountManager.createAccount(username, role);
    boolean result = accountManager.deleteAccount(account);
    Assert.assertEquals(true, result);
  }

  @Test
  public void deleteAccountTestFail() {
    String username = "userTest";
    String role = "Admin";

    Account account = new Account(username, role);
    boolean result = accountManager.deleteAccount(account);
    Assert.assertEquals(false, result);
  }

  @Test
  public void getAccountTest() {
    String username = "userTest";
    String role = "Admin";

    Account account1 = accountManager.createAccount(username, role);
    Account account2 = accountManager.getAccount(username);
    Assert.assertEquals(account1.toString(), account2.toString());
    accountManager.deleteAccount(account1);
  }

  @Test
  public void getAccountTestFail() {
    String username = "userTest";

    Account account = accountManager.getAccount(username);
    Assert.assertEquals(null, account);
  }

  @Test
  public void extractAccountFromJsonTest() {
    String username = "userTest";
    String role = "Admin";

    Account account1 = accountManager.createAccount(username, role);
    JSONObject jsonObject = jsonFileManager.searchJSON(username);
    Account account2 = accountManager.extractAccountFromJson(jsonObject);

    Assert.assertEquals(account1.toString(), account2.toString());
    accountManager.deleteAccount(account1);
  }
}
