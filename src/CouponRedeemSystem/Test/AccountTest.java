package CouponRedeemSystem.Test;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.Util.Util;
import CouponRedeemSystem.Test.model.MainTest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountTest extends MainTest {

  @Before
  @After
  public void reset() {
    Util.clearSystem();
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
      dateOfBirth,
      telNo
    );
    System.out.println(account);

    String expectedOutput =
      "Account{userName=\"" +
      userName +
      "\", role=\"" +
      role +
      "\", points=0.0" +
      ", couponIDs=[]" +
      ", dateOfBirth=" +
      Util.sdf.parse(dateOfBirth) +
      ", age=" +
      age +
      ", telNo=\"" +
      telNo +
      "\"}";

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

    accountManager.createAccount(userName, role, dateOfBirth, telNo);

    JSONObject accountJson = jsonFileManager.searchJSON(userName);

    String expectedOutput =
      "{\"userName\":\"" +
      userName +
      "\", \"role\":\"" +
      role +
      "\", \"points\":0.0" +
      ", \"couponIDs\":[]" +
      "\", \"dateOfBirth\":\"" +
      dateOfBirth +
      "\", \"age\":" +
      age +
      ", \"telNo\":\"" +
      telNo +
      "}";

    Assert.assertEquals(
      expectedOutput,
      accountManager.jsonToString(accountJson)
    );
  }

  @Test
  public void deleteAccountTest() {
    String role = "Admin";

    Account account = accountManager.createAccount(userName, role);
    accountManager.deleteAccount(account);
    Account account2 = accountManager.getAccount(userName);

    Assert.assertEquals(null, account2);
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
  public void calculateAgeTest() throws ParseException {
    Date dob = Util.sdf.parse(dateOfBirth);

    Assert.assertEquals(Integer.parseInt(age), Account.calculateAge(dob));
  }

  @Test
  public void couponToPointsTest() {
    String role = "User";

    Account account = accountManager.createAccount(
      userName,
      role,
      dateOfBirth,
      telNo
    );

    String couponCode = "rCouponTest";
    String type = "Redeemable";
    String expirationDate = new SimpleDateFormat("dd/MM/yyyy").format(DateUtils.addYears(new Date(), 1));

    Coupon coupon = couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      type,
      expirationDate
    );

    account.couponToPoints(coupon);

    Assert.assertEquals(intrinsicValue + 183, account.getPoints(), 0.0);

    Assert.assertEquals(false, coupon.isActive());
  }

  @Test
  public void pointsToCouponTest() {
    List<String> couponIDs = new ArrayList<String>();
    String role = "User";

    Account account = accountManager.createAccount(
      userName,
      role,
      dateOfBirth,
      telNo
    );
    account.addPoints(100.0);
    accountManager.updateAccount(account);

    Shop shop = shopManager.createShop(shopName);
    String couponCode = "pCouponTest";
    String type = "Purchasable";

    Coupon coupon = couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      purchasingValue,
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

  @Test
  public void useCouponTest() {
    String role = "User";

    Account account = accountManager.createAccount(
      userName,
      role,
      dateOfBirth,
      telNo
    );
    account.addCouponID("pCouponTest");
    accountManager.updateAccount(account);

    String couponCode = "pCouponTest";
    String type = "Purchasable";
    Shop shop = shopManager.createShop(shopName);
    Coupon coupon = couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      purchasingValue,
      shop,
      type,
      expirationDate
    );
    coupon.setOwner(account);
    couponManager.updateCoupon(coupon);

    account.useCoupon(coupon);

    Assert.assertEquals("[]", account.getCouponIDs().toString());
    Assert.assertEquals(false, coupon.isActive());
    Assert.assertEquals(userName, coupon.getOwner().getUserName());
  }
}
