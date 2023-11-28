package CouponRedeemSystem.Test;

import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.Test.model.MainTest;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ShopTest extends MainTest {

  @After
  public void reset() {
    Shop shop = shopManager.getShop(shopName);
    if (shop != null) {
      shopManager.deleteShop(shop);
    }
  }

  @Test
  public void createShopTest() {
    String shopName = "shopTest";

    Shop shop = shopManager.createShop(shopName);

    String expectedOutput =
      "Shop{shopName=\"" +
      shopName +
      "\", couponList=[], staffList=[], discountList=[]}";

    Assert.assertEquals(expectedOutput, shop.toString());
    jsonFileManager.deleteJSON("Shop", shopName);
  }

  @Test
  public void createShopTestFail() {
    shopManager.createShop(shopName);
    Shop shop2 = shopManager.createShop(shopName);

    Assert.assertEquals(null, shop2);
  }

  @Test
  public void updateShopTest() {
    shopManager.createShop(shopName);

    JSONObject shopJson = jsonFileManager.searchJSON(shopName);

    String expectedOutput =
      "{\"shopName\":\"" +
      shopName +
      "\", \"couponList\":[], \"staffList\":[], \"discountList\":[]}";

    Assert.assertEquals(expectedOutput, shopManager.jsonToString(shopJson));
  }

  @Test
  public void deleteShopTest() {
    Shop shop = shopManager.createShop(shopName);
    boolean result = shopManager.deleteShop(shop);

    Assert.assertEquals(true, result);
  }

  @Test
  public void deleteShopTestFail() {
    Shop shop = new Shop(shopName);
    boolean result = shopManager.deleteShop(shop);

    Assert.assertEquals(false, result);
  }

  @Test
  public void getShopTest() {
    Shop shop = shopManager.createShop(shopName);
    Shop shop2 = shopManager.getShop(shopName);

    Assert.assertEquals(shop.toString(), shop2.toString());
  }

  @Test
  public void getShopTestFail() {
    Shop shop = shopManager.getShop(shopName);

    Assert.assertEquals(null, shop);
  }

  @Test
  public void getShopByStaffTest() {
    Shop shop = shopManager.createShop(shopName);
    shop.addStaff(staffName);
    shopManager.updateShop(shop);

    Shop shop2 = shopManager.getShopByStaff(staffName);

    Assert.assertEquals(shop.toString(), shop2.toString());
  }

  @Test
  public void getShopByStaffTestFail() {
    Shop shop = shopManager.getShopByStaff(staffName);

    Assert.assertEquals(null, shop);
  }

  // Please note that this test will fail if there are already existing shops in the database
  @Test
  public void getShopListTest() {
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
    Shop shop = shopManager.createShop(shopName);
    JSONObject shopJson = jsonFileManager.searchJSON(shopName);
    Shop shop2 = shopManager.extractShopFromJson(shopJson);

    Assert.assertEquals(shop.toString(), shop2.toString());
  }
}
