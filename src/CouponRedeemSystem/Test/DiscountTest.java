package CouponRedeemSystem.Test;

import CouponRedeemSystem.Discount.model.Discount;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.Test.model.MainTest;
import java.text.ParseException;
import java.util.Date;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

public class DiscountTest extends MainTest {

  @Test
  public void createDiscountTest() throws ParseException {
    String discountName = "discountTest";
    Shop shop = new Shop("shopTest");
    double value = 10.0;
    String startDate = "11/11/2023";
    int day = 10;
    String expireDate = sdf.format(
      DateUtils.addDays(sdf.parse(startDate), day)
    );

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
    discountManager.deleteDiscount(discount);
  }

  @Test
  public void createDiscountTestFail() throws ParseException {
    String discountName = "discountTest";
    Shop shop = new Shop("shopTest");
    double value = 10.0;
    String startDate = "11/11/2023";
    int day = 10;

    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDate,
      day
    );
    Discount discount2 = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDate,
      day
    );

    Assert.assertEquals(null, discount2);
    discountManager.deleteDiscount(discount);
  }

  @Test
  public void updateDiscountTest() throws ParseException {
    String discountName = "discountTest";
    Shop shop = new Shop("shopTest");
    double value = 10.0;
    String startDate = "11/11/2023";
    int day = 10;
    String expireDate = sdf.format(
      DateUtils.addDays(sdf.parse(startDate), day)
    );

    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDate,
      day
    );

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
    discountManager.deleteDiscount(discount);
  }

  @Test
  public void deleteDiscountTest() {
    String discountName = "discountTest";
    Shop shop = new Shop("shopTest");
    double value = 10.0;
    String startDate = "11/11/2023";
    int day = 10;

    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDate,
      day
    );

    boolean result = discountManager.deleteDiscount(discount);

    Assert.assertEquals(true, result);
  }

  @Test
  public void deleteDiscountTestFail() throws ParseException {
    String discountName = "discountTest";
    Shop shop = new Shop("shopTest");
    double value = 10.0;
    String startDate = "11/11/2023";
    int day = 10;

    String expireDate = sdf.format(
      DateUtils.addDays(sdf.parse(startDate), day)
    );
    Discount discount = new Discount(
      discountName,
      shop,
      value,
      startDate,
      expireDate
    );

    boolean result = discountManager.deleteDiscount(discount);

    Assert.assertEquals(false, result);
  }

  @Test
  public void getDiscountTest() {
    String discountName = "discountTest";
    String shopName = "shopTest";
    Shop shop = shopManager.createShop(shopName);
    double value = 10.0;
    String startDate = "11/11/2023";
    int day = 10;

    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDate,
      day
    );

    Discount discount2 = discountManager.getDiscount(discountName);

    Assert.assertEquals(discount.toString(), discount2.toString());
    discountManager.deleteDiscount(discount);
    shopManager.deleteShop(shop);
  }

  @Test
  public void getDiscountTestFail() {
    String discountName = "discountTest";

    Discount discount = discountManager.getDiscount(discountName);

    Assert.assertEquals(null, discount);
  }

  @Test
  public void extractDiscountFromJsonTest() {
    String discountName = "discountTest";
    Shop shop = shopManager.createShop("shopTest");
    double value = 10.0;
    String startDate = "11/11/2023";
    int day = 10;

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
    discountManager.deleteDiscount(discount);
    shopManager.deleteShop(shop);
  }

  @Test
  public void validateTimeTest() {
    String discountName = "discountTest";
    Shop shop = shopManager.createShop("shopTest");
    double value = 10.0;
    String startDate = sdf.format(DateUtils.addDays(new Date(), -1));
    int day = 10;

    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDate,
      day
    );

    Assert.assertEquals(true, discount.validateTime());
    discountManager.deleteDiscount(discount);
    shopManager.deleteShop(shop);
  }

  @Test
  public void validateTimeTestFail() {
    String discountName = "discountTest";
    Shop shop = shopManager.createShop("shopTest");
    double value = 10.0;
    String startDate = sdf.format(DateUtils.addDays(new Date(), 1));
    int day = 10;

    Discount discount = discountManager.createDiscount(
      discountName,
      shop,
      value,
      startDate,
      day
    );

    Assert.assertEquals(false, discount.validateTime());
    discountManager.deleteDiscount(discount);
    shopManager.deleteShop(shop);
  }
}
