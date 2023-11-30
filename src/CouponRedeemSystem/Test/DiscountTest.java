package CouponRedeemSystem.Test;

import CouponRedeemSystem.Discount.model.Discount;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.Util.Util;
import CouponRedeemSystem.Test.model.MainTest;
import java.text.ParseException;
import java.util.Date;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DiscountTest extends MainTest {

  @Before
  @After
  public void reset() {
    Util.clearSystem();
  }

  @Test
  public void createDiscountTest() throws ParseException {
    Shop shop = shopManager.createShop(shopName);
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
      Util.sdf.parse(startDate) +
      ", endDate=" +
      Util.sdf.parse(endDate) +
      ", value=" +
      value +
      "}";

    Assert.assertEquals(expectedOutput, discount.toString());
  }

  @Test
  public void updateDiscountTest() throws ParseException {
    Shop shop = shopManager.createShop(shopName);
    discountManager.createDiscount(discountName, shop, value, startDate, day);

    JSONObject discountJson = jsonFileManager.searchJSON(discountName);

    String expectedOutput =
      "{\"discountName\":\"" +
      discountName +
      "\", \"shop\":\"" +
      shopName +
      "\", \"startDate\":\"" +
      startDate +
      "\", \"endDate\":\"" +
      endDate +
      "\", \"value\":" +
      value +
      "}";

    Assert.assertEquals(
      expectedOutput,
      discountManager.jsonToString(discountJson)
    );
  }

  @Test
  public void deleteDiscountTest() {
    Shop shop = shopManager.createShop(shopName);
    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDate,
      day
    );

    discountManager.deleteDiscount(discount);
    Discount discount2 = discountManager.getDiscount(discountName);

    Assert.assertEquals(null, discount2);
  }

  @Test
  public void getDiscountTest() {
    Shop shop = shopManager.createShop(shopName);
    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDate,
      day
    );

    Discount discount2 = discountManager.getDiscount(discountName);

    Assert.assertEquals(discount.toString(), discount2.toString());
  }

  @Test
  public void getDiscountTestFail() {
    Discount discount = discountManager.getDiscount(discountName);

    Assert.assertEquals(null, discount);
  }

  @Test
  public void extractDiscountFromJsonTest() {
    Shop shop = shopManager.createShop(shopName);
    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDate,
      day
    );

    JSONObject discountJson = jsonFileManager.searchJSON(discountName);
    Discount discount2 = discountManager.extractDiscountFromJson(discountJson);

    Assert.assertEquals(discount.toString(), discount2.toString());
  }

  @Test
  public void validateTimeTest() {
    String startDateStr = Util.sdf.format(DateUtils.addDays(new Date(), -1));

    Shop shop = shopManager.createShop(shopName);
    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDateStr,
      day
    );

    Assert.assertEquals(true, discount.validateTime());
  }

  @Test
  public void validateTimeTestFail() {
    String startDate = Util.sdf.format(DateUtils.addDays(new Date(), 1));

    Shop shop = shopManager.createShop(shopName);
    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDate,
      day
    );

    Assert.assertEquals(false, discount.validateTime());
  }
}
