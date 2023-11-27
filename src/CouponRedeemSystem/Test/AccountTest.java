package CouponRedeemSystem.Test;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.Test.model.MainTest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
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

  @Test
  public void couponToPointsTest() {
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

    String couponCode = "rCouponTest";
    double intrinsicValue = 10.0;
    String type = "Redeemable";
    String expirationDate = sdf.format(DateUtils.addYears(new Date(), 1));

    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    int thisYear = cal.get(Calendar.YEAR);
    boolean isThisYearLeap =
      ((thisYear % 4 == 0) && (thisYear % 100 != 0) || (thisYear % 400 == 0));
    int nextYear = cal.get(Calendar.YEAR) + 1;
    boolean isNextYearLeap =
      ((nextYear % 4 == 0) && (nextYear % 100 != 0) || (nextYear % 400 == 0));

    Coupon coupon = couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      type,
      expirationDate
    );

    account.couponToPoints(coupon);

    if (isThisYearLeap) {
      Assert.assertEquals(intrinsicValue + 182, account.getPoints(), 0.0);
    } else if (isNextYearLeap) {
      Assert.assertEquals(intrinsicValue + 183, account.getPoints(), 0.0);
    } else {
      Assert.assertEquals(intrinsicValue + 182.5, account.getPoints(), 0.0);
    }

    Assert.assertEquals(false, coupon.isActive());

    accountManager.deleteAccount(account);
    couponManager.deleteCoupon(coupon);
  }

  @Test
  public void pointsToCouponTest() {
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
    account.setPoints(100.0);
    accountManager.updateAccount(account);

    Shop shop = new Shop("shopTest");
    String couponCode = "pCouponTest";
    double intrinsicValue = 10.0;
    double points = 15.0;
    String type = "Purchasable";
    String expirationDate = "11/11/2025";

    Coupon coupon = couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      points,
      shop,
      type,
      expirationDate
    );

    account.pointsToCoupon(coupon);

    Assert.assertEquals(85.0, account.getPoints(), 0.0);
    List<String> couponIDs = new ArrayList<String>();
    couponIDs.add(coupon.getCouponCode());
    Assert.assertEquals(couponIDs, account.getCouponIDs());

    Assert.assertEquals(account, coupon.getOwner());

    accountManager.deleteAccount(account);
    couponManager.deleteCoupon(coupon);
  }
}
