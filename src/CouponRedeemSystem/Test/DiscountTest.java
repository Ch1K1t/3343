package CouponRedeemSystem.Test;

import CouponRedeemSystem.Discount.model.Discount;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.Test.model.MainTest;
import java.text.ParseException;
import java.util.Date;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class DiscountTest extends MainTest {

  private final Shop shop = shopManager.createShop(shopName);

  @After
  public void reset() {
    Shop shop = shopManager.getShop(shopName);
    if (shop != null) {
      shopManager.deleteShop(shop);
    }
    Discount discount = discountManager.getDiscount(discountName);
    if (discount != null) {
      discountManager.deleteDiscount(discount);
    }
  }

  @Test
  public void createDiscountTest() throws ParseException {
    Date startDate = sdf.parse(startDateStr);
    Date expireDate = sdf.parse(expireDateStr);

    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDateStr,
      day
    );

    String expectedOutput =
      "Discount{discountName=\"" +
      discountName +
      "\", shop=" +
      shop +
      ", startDate=" +
      startDate +
      ", expireDate=" +
      expireDate +
      ", value=" +
      value +
      "}";

    Assert.assertEquals(expectedOutput, discount.toString());
  }

  @Test
  public void createDiscountTestFail() throws ParseException {
    discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDateStr,
      day
    );
    Discount discount2 = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDateStr,
      day
    );

    Assert.assertEquals(null, discount2);
  }

  @Test
  public void updateDiscountTest() throws ParseException {
    discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDateStr,
      day
    );

    JSONObject discountJson = jsonFileManager.searchJSON(discountName);

    String expectedOutput =
      "{\"discountName\":\"" +
      discountName +
      "\", \"shop\":\"" +
      shopName +
      "\", \"startDate\":\"" +
      startDateStr +
      "\", \"expireDate\":\"" +
      expireDateStr +
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
    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDateStr,
      day
    );

    boolean result = discountManager.deleteDiscount(discount);

    Assert.assertEquals(true, result);
  }

  @Test
  public void deleteDiscountTestFail() throws ParseException {
    Discount discount = new Discount(
      discountName,
      shop,
      value,
      startDateStr,
      expireDateStr
    );

    boolean result = discountManager.deleteDiscount(discount);

    Assert.assertEquals(false, result);
  }

  @Test
  public void getDiscountTest() {
    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDateStr,
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
    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDateStr,
      day
    );

    JSONObject discountJson = jsonFileManager.searchJSON(discountName);
    Discount discount2 = discountManager.extractDiscountFromJson(discountJson);

    Assert.assertEquals(discount.toString(), discount2.toString());
  }

  @Test
  public void validateTimeTest() {
    String startDateStr = sdf.format(DateUtils.addDays(new Date(), -1));

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
    String startDate = sdf.format(DateUtils.addDays(new Date(), 1));

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
