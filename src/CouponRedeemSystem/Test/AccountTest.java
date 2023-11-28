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
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class AccountTest extends MainTest {

  // private final String userName = "accountTest";
  // private final String password = "passwordTest";
  private final int age = 20;

  // private final String telNo = "12345678";
  // private final String dateOfBirth = "11/11/2000";

  @After
  public void reset() {
    if (
      passwordManager.checkPasswordValid(userName, password).equals("success")
    ) {
      passwordManager.deletePassword(userName);
    }
    Account account = accountManager.getAccount(userName);
    if (account != null) {
      accountManager.deleteAccount(account);
    }
    Coupon coupon = couponManager.getCoupon("rCouponTest");
    if (coupon != null) {
      couponManager.deleteCoupon(coupon);
    }
    coupon = couponManager.getCoupon("pCouponTest");
    if (coupon != null) {
      couponManager.deleteCoupon(coupon);
    }
  }

  @Test
  public void createPasswordTest() {
    accountManager.createPassword(userName, password);
    JSONObject jsonObject = passwordManager.getPasswordRefTable();
    String text = jsonObject.get(userName).toString();
    Assert.assertEquals(
      true,
      encryptionManager.decryption(text).equals(password)
    );
  }

  @Test
  public void createPasswordTestFail() {
    String role = "Admin";

    accountManager.createAccount(userName, role);
    boolean result = accountManager.createPassword(userName, password);
    Assert.assertEquals(false, result);
  }

  @Test
  public void createNonUserAccountTest() {
    String[] roleList = new String[] { "Admin", "Shop", "Staff" };

    for (String role : roleList) {
      Account account = accountManager.createAccount(userName, role);

      String expectedOutput =
        "Account{userName=\"" + "" + userName + "\", role=\"" + role + "\"}";

      Assert.assertEquals(expectedOutput, account.toString());
    }
  }

  @Test
  public void createUserAccountTest() throws ParseException {
    String role = "User";

    Account account = accountManager.createAccount(
      userName,
      role,
      telNo,
      dateOfBirth
    );

    String expectedOutput =
      "Account{userName=\"" +
      userName +
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
      ", couponIDs=[]}";

    Assert.assertEquals(expectedOutput, account.toString());
  }

  @Test
  public void updateNonUserAccount() {
    String[] roleList = new String[] { "Admin", "Shop", "Staff" };

    for (String role : roleList) {
      accountManager.createAccount(userName, role);

      JSONObject accountJson = jsonFileManager.searchJSON(userName);

      String expectedOutput =
        "{\"userName\":\"" + userName + "\", \"role\":\"" + role + "\"}";

      Assert.assertEquals(
        expectedOutput,
        accountManager.jsonToString(accountJson)
      );
    }
  }

  @Test
  public void updateUserAccount() {
    String role = "User";

    accountManager.createAccount(userName, role, telNo, dateOfBirth);

    JSONObject accountJson = jsonFileManager.searchJSON(userName);

    String expectedOutput =
      "{\"userName\":\"" +
      userName +
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
  }

  @Test
  public void deleteAccountTest() {
    String role = "Admin";

    Account account = accountManager.createAccount(userName, role);
    boolean result = accountManager.deleteAccount(account);
    Assert.assertEquals(true, result);
  }

  @Test
  public void deleteAccountTestFail() {
    String role = "Admin";

    Account account = new Account(userName, role);
    boolean result = accountManager.deleteAccount(account);
    Assert.assertEquals(false, result);
  }

  @Test
  public void getAccountTest() {
    String role = "Admin";

    Account account = accountManager.createAccount(userName, role);
    Account account2 = accountManager.getAccount(userName);
    Assert.assertEquals(account.toString(), account2.toString());
  }

  @Test
  public void getAccountTestFail() {
    Account account = accountManager.getAccount(userName);
    Assert.assertEquals(null, account);
  }

  @Test
  public void extractAccountFromJsonTest() {
    String role = "Admin";

    Account account = accountManager.createAccount(userName, role);
    JSONObject jsonObject = jsonFileManager.searchJSON(userName);
    Account account2 = accountManager.extractAccountFromJson(jsonObject);

    Assert.assertEquals(account.toString(), account2.toString());
  }

  @Test
  public void couponToPointsTest() {
    String role = "User";

    Account account = accountManager.createAccount(
      userName,
      role,
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
  }

  @Test
  public void pointsToCouponTest() {
    List<String> couponIDs = new ArrayList<String>();
    String role = "User";

    Account account = accountManager.createAccount(
      userName,
      role,
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

    couponIDs.add(coupon.getCouponCode());
    Assert.assertEquals(couponIDs, account.getCouponIDs());

    Assert.assertEquals(account, coupon.getOwner());
  }
}
