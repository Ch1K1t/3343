package CouponRedeemSystem.Shop;

import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.PurchasableCoupon;
import CouponRedeemSystem.Coupon.model.RedeemableCoupon;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.LazyDynaBean;

public class ShopManager {

  static final String SHOP_JSON_FILE_NAME = "Shop.json";
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

    jsonFileManager.modifyJSON("Shop", shopName, bean);
    System.out.println();
    System.out.println("Shop created successfully");
  }

  public void deleteShop(String shopName) {
    Shop shop = getShop(shopName);
    if (shop == null) {
      System.out.println("Shop " + shopName + " does not exist");
      return;
    }
    jsonFileManager.deleteJSON("Shop", shopName);
    System.out.println();
    System.out.println("Shop removed successfully");
  }

  public Shop getShop(String shopName) {
    JSONObject shopJson = jsonFileManager.searchJSON(shopName);

    if (shopJson == null) {
      System.out.println("Shop " + shopName + " does not exist");
      return null;
    } else {
      // Extract coupon details from JSON and return the Account object
      return extractShopFromJson(shopJson);
    }
  }

  private Shop extractShopFromJson(JSONObject shopJson) {
    String shopName = shopJson.getString("shopName");
    JSONArray Arr = shopJson.getJSONArray("purchasableCoupons");
    List<String> purchasableCoupons = new ArrayList<>();
    for (int i = 0; i < Arr.size(); i++) {
      purchasableCoupons.add(Arr.getString(i));
    }
    return new Shop(shopName, purchasableCoupons);
  }
}
