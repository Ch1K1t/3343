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

  @Test
  public void deleteShopTestFail() {
    String shopName = "shopTest";

    Shop shop = new Shop(shopName);
    boolean result = shopManager.deleteShop(shop);
    Assert.assertEquals(false, result);
  }

  @Test
  public void getShopTest() {
    String shopName = "shopTest";

    Shop shop = shopManager.createShop(shopName);
    Shop shop2 = shopManager.getShop(shopName);

    Assert.assertEquals(shop.toString(), shop2.toString());
    shopManager.deleteShop(shop);
  }

  @Test
  public void getShopTestFail() {
    String shopName = "shopTest";

    Shop shop = shopManager.getShop(shopName);

    Assert.assertEquals(null, shop);
  }

  @Test
  public void getShopByStaffTest() {
    String shopName = "shopTest";
    String staffName = "staffTest";

    Shop shop1 = shopManager.createShop(shopName);
    shop1.addStaff(staffName);
    shopManager.updateShop(shop1);

    Shop shop2 = shopManager.getShopByStaff(staffName);

    Assert.assertEquals(shop1.toString(), shop2.toString());
    shopManager.deleteShop(shop1);
  }

  @Test
  public void getShopByStaffTestFail() {
    String staffName = "staffTest";

    Shop shop = shopManager.getShopByStaff(staffName);

    Assert.assertEquals(null, shop);
  }

  @Test
  public void getShopListTest() {
    String shopName = "shopTest";
    String shopName2 = "shopTest2";

    List<Shop> shopList = new ArrayList<>();
    shopList.add(shopManager.createShop(shopName));
    shopList.add(shopManager.createShop(shopName2));

    List<Shop> shopList2 = shopManager.getShopList();

    for (int i = 0; i < shopList.size(); i++) {
      Assert.assertEquals(
        shopList.get(i).toString(),
        shopList2.get(i).toString()
      );
      shopManager.deleteShop(shopList.get(i));
    }
  }

  @Test
  public void extractShopFromJsonTest() {
    String shopName = "shopTest";

    Shop shop1 = shopManager.createShop(shopName);
    JSONObject shopJson = jsonFileManager.searchJSON(shopName);
    Shop shop2 = shopManager.extractShopFromJson(shopJson);

    Assert.assertEquals(shop1.toString(), shop2.toString());
    shopManager.deleteShop(shop1);
  }
}
