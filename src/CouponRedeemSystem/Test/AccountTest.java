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
  public void createNonUserAccountObjectTest() {
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
  public void createUserAccountObjectTest() throws ParseException {
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
  public void createNonUserAccountJSONTest() {
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
  public void createUserAccountJSONTest() {
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
}
