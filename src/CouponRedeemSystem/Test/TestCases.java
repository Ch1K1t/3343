package CouponRedeemSystem.Test;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.PurchasableCoupon;
import CouponRedeemSystem.Discount.DiscountManager;
import CouponRedeemSystem.Discount.model.Discount;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import CouponRedeemSystem.System.Password.PasswordManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class TestCases {

  private final PasswordManager passwordManager = PasswordManager.getInstance();
  private final CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
  private final AccountManager accountManager = AccountManager.getInstance();
  private final CouponManager couponManager = CouponManager.getInstance();
  private final ShopManager shopManager = ShopManager.getInstance();
  private final DiscountManager discountManager = DiscountManager.getInstance();
  private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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

  @Test
  public void createNonUserAccountObject() {
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
  public void createUserAccountObject() throws ParseException {
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
  public void createNonUserAccountJSON() {
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
  public void createUserAccountJSON() {
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
  public void createPurchasableCouponObject() throws ParseException {
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

    String expectedOutput =
      "Coupon{couponCode=\"" +
      couponCode +
      "\", intrinsicValue=" +
      intrinsicValue +
      ", points=" +
      points +
      ", shop=" +
      shop +
      ", owner=" +
      null +
      ", active=" +
      true +
      ", type=\"" +
      type +
      "\", expirationDate=" +
      sdf.parse(expirationDate) +
      "}";

    Assert.assertEquals(expectedOutput, coupon.toString());
    jsonFileManager.deleteJSON("Coupon/Purchasable", couponCode);
  }

  @Test
  public void createRedeemableCouponObject() throws ParseException {
    String couponCode = "rCouponTest";
    double intrinsicValue = 10.0;
    String type = "Redeemable";
    String expirationDate = "11/11/2025";

    Coupon coupon = couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      type,
      expirationDate
    );

    String expectedOutput =
      "Coupon{couponCode=\"" +
      couponCode +
      "\", intrinsicValue=" +
      intrinsicValue +
      ", active=" +
      true +
      ", type=\"" +
      type +
      "\", expirationDate=" +
      sdf.parse(expirationDate) +
      "}";

    Assert.assertEquals(expectedOutput, coupon.toString());
    jsonFileManager.deleteJSON("Coupon/Redeemable", couponCode);
  }

  @Test
  public void createPurchasableCouponJSON() {
    Shop shop = new Shop("shopTest");
    String couponCode = "pCouponTest";
    double intrinsicValue = 10.0;
    double points = 15.0;
    String type = "Purchasable";
    String expirationDate = "11/11/2025";

    couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      points,
      shop,
      type,
      expirationDate
    );

    JSONObject couponJson = jsonFileManager.searchJSON("pCouponTest");

    String expectedOutput =
      "{\"couponCode\":\"" +
      couponCode +
      "\", \"intrinsicValue\":" +
      intrinsicValue +
      ", \"points\":" +
      points +
      ", \"shop\":\"" +
      shop.getShopName() +
      "\", \"owner\":" +
      null +
      ", \"active\":" +
      true +
      ", \"type\":\"" +
      type +
      "\", \"expirationDate\":\"" +
      expirationDate +
      "\"}";

    Assert.assertEquals(expectedOutput, couponManager.jsonToString(couponJson));
    jsonFileManager.deleteJSON("Coupon/Purchasable", couponCode);
  }

  @Test
  public void createRedeemableCouponJSON() {
    String couponCode = "rCouponTest";
    double intrinsicValue = 10.0;
    String type = "Redeemable";
    String expirationDate = "11/11/2025";

    couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      type,
      expirationDate
    );

    JSONObject couponJson = jsonFileManager.searchJSON("rCouponTest");

    String expectedOutput =
      "{\"couponCode\":\"" +
      couponCode +
      "\", \"intrinsicValue\":" +
      intrinsicValue +
      ", \"active\":" +
      true +
      ", \"type\":\"" +
      type +
      "\", \"expirationDate\":\"" +
      expirationDate +
      "\"}";

    Assert.assertEquals(expectedOutput, couponManager.jsonToString(couponJson));
    jsonFileManager.deleteJSON("Coupon/Redeemable", couponCode);
  }

  @Test
  public void createShopObject() {
    String shopName = "shopTest";
    ArrayList<String> couponList = new ArrayList<>();
    ArrayList<String> staffList = new ArrayList<>();
    ArrayList<String> discountList = new ArrayList<>();

    Shop shop = shopManager.createShop(shopName);

    String expectedOutput =
      "Shop{shopName=\"" +
      shopName +
      "\", couponList=" +
      couponList +
      ", staffList=" +
      staffList +
      ", discountList=" +
      discountList +
      "}";

    Assert.assertEquals(expectedOutput, shop.toString());
    jsonFileManager.deleteJSON("Shop", shopName);
  }

  @Test
  public void createShopJSON() {
    String shopName = "shopTest";
    List<String> couponList = new ArrayList<>();
    List<String> staffList = new ArrayList<>();
    List<String> discountList = new ArrayList<>();

    shopManager.createShop(shopName);

    JSONObject shopJson = jsonFileManager.searchJSON(shopName);

    String expectedOutput =
      "{\"shopName\":\"" +
      shopName +
      "\", \"couponList\":" +
      couponList +
      ", \"staffList\":" +
      staffList +
      ", \"discountList\":" +
      discountList +
      "}";

    Assert.assertEquals(expectedOutput, shopManager.jsonToString(shopJson));
    jsonFileManager.deleteJSON("Shop", shopName);
  }

  @Test
  public void createDiscountObject() throws ParseException {
    Shop shop = new Shop("shopTest");
    String discountName = "discountTest";
    double value = 10.0;
    String startDate = "11/11/2020";
    int day = 10;
    String expireDate = "21/11/2020";

    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDate,
      day
    );

    String expectedOutput =
      "Discount{discountName=\"" +
      discountName +
      "\", shop=" +
      shop +
      ", startDate=" +
      sdf.parse(startDate) +
      ", expireDate=" +
      sdf.parse(expireDate) +
      ", value=" +
      value +
      "}";

    Assert.assertEquals(expectedOutput, discount.toString());
    jsonFileManager.deleteJSON("Discount", discountName);
  }

  @Test
  public void createDiscountJSON() {
    Shop shop = new Shop("shopTest");
    String discountName = "discountTest";
    double value = 10.0;
    String startDate = "11/11/2020";
    int day = 10;
    String expireDate = "21/11/2020";

    discountManager.createDiscount(discountName, shop, value, startDate, day);

    JSONObject discountJson = jsonFileManager.searchJSON(discountName);

    String expectedOutput =
      "{\"discountName\":\"" +
      discountName +
      "\", \"shop\":\"" +
      shop.getShopName() +
      "\", \"startDate\":\"" +
      startDate +
      "\", \"expireDate\":\"" +
      expireDate +
      "\", \"value\":" +
      value +
      "}";

    Assert.assertEquals(
      expectedOutput,
      discountManager.jsonToString(discountJson)
    );
    jsonFileManager.deleteJSON("Discount", discountName);
  }
}
