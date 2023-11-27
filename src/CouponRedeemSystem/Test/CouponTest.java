package CouponRedeemSystem.Test;

import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.RedeemableCoupon;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.Test.model.MainTest;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

public class CouponTest extends MainTest {

  @Test
  public void createPurchasableCouponTest() throws ParseException {
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
    couponManager.deleteCoupon(coupon);
  }

  @Test
  public void createPurchasableCouponTestFail() {
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
    Coupon coupon2 = couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      points,
      shop,
      type,
      expirationDate
    );

    Assert.assertEquals(null, coupon2);
    couponManager.deleteCoupon(coupon);
  }

  @Test
  public void createRedeemableCouponTest() throws ParseException {
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
    couponManager.deleteCoupon(coupon);
  }

  @Test
  public void createRedeemableCouponTestFail() {
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
    Coupon coupon2 = couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      type,
      expirationDate
    );

    Assert.assertEquals(null, coupon2);
    couponManager.deleteCoupon(coupon);
  }

  @Test
  public void updatePurchasableCouponTest() {
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
    couponManager.deleteCoupon(coupon);
  }

  @Test
  public void updateRedeemableCouponTest() {
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
    couponManager.deleteCoupon(coupon);
  }

  @Test
  public void deleteCouponTest() {
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

    boolean result = couponManager.deleteCoupon(coupon);
    Assert.assertEquals(true, result);
  }

  @Test
  public void deleteCouponTestFail() throws ParseException {
    String couponCode = "rCouponTest";
    double intrinsicValue = 10.0;
    String type = "Redeemable";
    String expirationDate = "11/11/2025";

    Coupon coupon = new RedeemableCoupon(
      couponCode,
      intrinsicValue,
      true,
      type,
      expirationDate
    );

    boolean result = couponManager.deleteCoupon(coupon);

    Assert.assertEquals(false, result);
  }

  @Test
  public void getCouponTest() throws ParseException {
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

    Coupon result = couponManager.getCoupon(couponCode);

    Assert.assertEquals(coupon.toString(), result.toString());
    couponManager.deleteCoupon(coupon);
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
    double intrinsicValue = 10.0;
    String type = "Redeemable";
    String expirationDate = "11/11/2025";

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
    couponManager.deleteCoupon(coupon);
  }

  @Test
  public void pointConversionTest() {
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

    double result = coupon.pointConversion();

    if (isThisYearLeap) {
      Assert.assertEquals(intrinsicValue + 182, result, 0.0);
    } else if (isNextYearLeap) {
      Assert.assertEquals(intrinsicValue + 183, result, 0.0);
    } else {
      Assert.assertEquals(intrinsicValue + 182.5, result, 0.0);
    }

    couponManager.deleteCoupon(coupon);
  }
}
