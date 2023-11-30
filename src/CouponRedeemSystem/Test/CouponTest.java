package CouponRedeemSystem.Test;

import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.Util.Util;
import CouponRedeemSystem.Test.model.MainTest;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CouponTest extends MainTest {

  private final Shop shop = shopManager.createShop(shopName);

  @Before
  @After
  public void reset() {
    Util.clearSystem();
  }

  @Test
  public void createPurchasableCouponTest() throws ParseException {
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

    String expectedOutput =
      "Coupon{couponCode=\"" +
      couponCode +
      "\", intrinsicValue=" +
      intrinsicValue +
      ", purchasingValue=" +
      purchasingValue +
      ", shop=" +
      shop +
      ", owner=" +
      null +
      ", active=" +
      true +
      ", type=\"" +
      type +
      "\", expirationDate=" +
      Util.sdf.parse(expirationDate) +
      "}";

    Assert.assertEquals(expectedOutput, coupon.toString());
  }

  @Test
  public void createRedeemableCouponTest() throws ParseException {
    String couponCode = "rCouponTest";
    String type = "Redeemable";

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
      Util.sdf.parse(expirationDate) +
      "}";

    Assert.assertEquals(expectedOutput, coupon.toString());
  }

  @Test
  public void updatePurchasableCouponTest() {
    String couponCode = "pCouponTest";
    String type = "Purchasable";

    couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      purchasingValue,
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
      ", \"purchasingValue\":" +
      purchasingValue +
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
  }

  @Test
  public void updateRedeemableCouponTest() {
    String couponCode = "rCouponTest";
    String type = "Redeemable";

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
  }

  @Test
  public void deleteCouponTest() {
    String couponCode = "rCouponTest";
    String type = "Redeemable";

    Coupon coupon = couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      type,
      expirationDate
    );

    couponManager.deleteCoupon(coupon);
    Coupon coupon2 = couponManager.getCoupon(couponCode);

    Assert.assertEquals(null, coupon2);
  }

  @Test
  public void getCouponTest() throws ParseException {
    String couponCode = "rCouponTest";
    String type = "Redeemable";

    Coupon coupon = couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      type,
      expirationDate
    );

    Coupon result = couponManager.getCoupon(couponCode);

    Assert.assertEquals(coupon.toString(), result.toString());
  }

  @Test
  public void getCouponTestFail() {
    String couponCode = "rCouponTest";

    Coupon result = couponManager.getCoupon(couponCode);

    Assert.assertEquals(null, result);
  }

  @Test
  public void extractCouponFromJsonTest() {
    String couponCode = "rCouponTest";
    String type = "Redeemable";

    Coupon coupon = couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      intrinsicValue,
      null,
      type,
      expirationDate
    );

    JSONObject couponJson = jsonFileManager.searchJSON(couponCode);
    Coupon coupon2 = couponManager.extractCouponFromJson(couponJson);

    Assert.assertEquals(coupon.toString(), coupon2.toString());
  }

  @Test
  public void pointConversionTest() {
    String couponCode = "rCouponTest";
    String type = "Redeemable";
    String expirationDate = Util.sdf.format(DateUtils.addYears(new Date(), 1));

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

    double result = coupon.pointConversion();

    if (isThisYearLeap) {
      Assert.assertEquals(intrinsicValue + 182, result, 0.0);
    } else if (isNextYearLeap) {
      Assert.assertEquals(intrinsicValue + 183, result, 0.0);
    } else {
      Assert.assertEquals(intrinsicValue + 182.5, result, 0.0);
    }
  }
}
