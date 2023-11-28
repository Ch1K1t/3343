package CouponRedeemSystem.Test;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.Util.Util;
import CouponRedeemSystem.Test.model.MainTest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class AccountTest extends MainTest {

  private final int age = 20;

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
    Shop shop = shopManager.getShop("shopTest");
    if (shop != null) {
      shopManager.deleteShop(shop);
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
      Util.sdf.parse(dateOfBirth) +
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
    String type = "Redeemable";

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
      couponExpirationDate
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

    Shop shop = shopManager.createShop(shopName);
    String couponCode = "pCouponTest";
    String type = "Purchasable";

    Coupon coupon = couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      points,
      shop,
      type,
      couponExpirationDate
    );

    account.pointsToCoupon(coupon);

    Assert.assertEquals(85.0, account.getPoints(), 0.0);

    couponIDs.add(coupon.getCouponCode());
    Assert.assertEquals(couponIDs, account.getCouponIDs());

    Assert.assertEquals(account, coupon.getOwner());
  }

  @Test
  public void useCouponTest() {
    String role = "User";

    Account account = accountManager.createAccount(
      userName,
      role,
      telNo,
      dateOfBirth
    );
    account.addCouponID("pCouponTest");
    accountManager.updateAccount(account);

    String couponCode = "pCouponTest";
    String type = "Purchasable";
    Shop shop = shopManager.createShop(shopName);
    Coupon coupon = couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      points,
      shop,
      type,
      couponExpirationDate
    );
    coupon.setOwner(account);
    couponManager.updateCoupon(coupon);

    account.useCoupon(coupon);

    Assert.assertEquals("[]", account.getCouponIDs().toString());
    Assert.assertEquals(false, coupon.isActive());
    Assert.assertEquals(userName, coupon.getOwner().getUserName());
  }
}
