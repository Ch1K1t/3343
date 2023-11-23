package CouponRedeemSystem.Test;

import CouponRedeemSystem.Discount.model.Discount;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.Test.model.MainTest;
import java.text.ParseException;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class DiscountTest extends MainTest {

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
