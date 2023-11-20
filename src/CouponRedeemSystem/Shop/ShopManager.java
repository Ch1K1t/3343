package CouponRedeemSystem.Shop;

import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import org.apache.commons.beanutils.LazyDynaBean;

public class ShopManager {

  static final String SHOP_JSON_FILE_NAME = "Shop.json";
  static final String SHOP_JSON_FILE_PATH = "Shop";
  private static ShopManager instance;
  private CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

  private ShopManager() {}

  public static ShopManager getInstance() {
    if (instance == null) instance = new ShopManager();
    return instance;
  }

  // public void loadAllShopsFromStorage() {
  //   try {
  //     JSONObject json = jsonFileManager.searchJSON(SHOP_JSON_FILE_NAME);
  //     for (Object shop : json.getJSONObject("List of Shops").keySet()) {
  //       String shopId = (String) shop;
  //       JSONObject shopObject = json
  //         .getJSONObject("List of Shops")
  //         .getJSONObject(shopId);
  //       Shop s = new Shop(
  //         shopObject.getString(shopId),
  //         shopObject.getString("shopName")
  //       );

  //       // load approve coupons in shop
  //       s.loadApprovedCoupons(shopObject);
  //       shops.put(shopObject.getString(shopId), s);
  //     }
  //     noOfShops = json.getInt("number of Shops");
  //   } catch (IOException e) {
  //     e.printStackTrace();
  //   }
  // }

  public void createShop(String shopName) {
    Shop shop = new Shop(shopName);
    LazyDynaBean bean = new LazyDynaBean();
    bean.set("shopName", shop.getShopName());
    bean.set("purchasableCoupons", shop.getPurchasableCoupons());

    jsonFileManager.modifyJSON(SHOP_JSON_FILE_PATH, shopName, bean);
  }
  // public Shop RemoveShop(String shopName) {
  //   Shop shop = null;
  //   if (shops.get(shopName) != null) shops.remove(shopName);
  //   return shop;
  // }

  // public Shop findShop(String shopName) {
  //   Shop shop = null;
  //   shops.get(shopName);
  //   return shop;
  // }
}
