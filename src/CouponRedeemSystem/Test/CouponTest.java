package CouponRedeemSystem.Test;

import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.Test.model.MainTest;
import java.text.ParseException;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class CouponTest extends MainTest {

  @Test
  public void createPurchasableCouponObjectTest() throws ParseException {
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
  public void createRedeemableCouponObjectTest() throws ParseException {
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
  public void createPurchasableCouponJSONTest() {
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
  public void createRedeemableCouponJSONTest() {
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
}
