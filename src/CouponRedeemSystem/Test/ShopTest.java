package CouponRedeemSystem.Test;

import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.Test.model.MainTest;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class ShopTest extends MainTest {

  @Test
  public void createShopTest() {
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
  public void createShopTestFail() {
    String shopName = "shopTest";

    Shop shop = shopManager.createShop(shopName);
    Shop shop2 = shopManager.createShop(shopName);

    Assert.assertEquals(null, shop2);
    shopManager.deleteShop(shop);
  }

  @Test
  public void updateShopTest() {
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
  public void deleteShopTest() {
    String shopName = "shopTest";

    Shop shop = shopManager.createShop(shopName);
    boolean result = shopManager.deleteShop(shop);
    Assert.assertEquals(true, result);
  }
}