package CouponRedeemSystem.Shop;

import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.LazyDynaBean;

public class ShopManager {

  private static ShopManager instance;
  private CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

  private ShopManager() {}

  public static ShopManager getInstance() {
    if (instance == null) instance = new ShopManager();
    return instance;
  }

  public void createShop(String shopName) {
    Shop shop = new Shop(shopName);

    LazyDynaBean bean = new LazyDynaBean();
    bean.set("shopName", shop.getShopName());
    bean.set("purchasableCoupons", shop.getPurchasableCoupons());
    bean.set("staffs", shop.getStaffs());

    jsonFileManager.modifyJSON("Shop", shopName, bean);

    System.out.println();
    System.out.println("Shop created");
  }

  public void deleteShop(String shopName) {
    Shop shop = getShop(shopName);
    if (shop == null) {
      System.out.println("Shop " + shopName + " does not exist");
      return;
    }

    jsonFileManager.deleteJSON("Shop", shopName);

    System.out.println();
    System.out.println("Shop deleted");
  }

  public void updateShop(Shop shop) {
    LazyDynaBean bean = new LazyDynaBean();
    bean.set("shopName", shop.getShopName());
    bean.set("purchasableCoupons", shop.getPurchasableCoupons());
    bean.set("staffs", shop.getStaffs());

    jsonFileManager.modifyJSON("Shop", shop.getShopName(), bean);

    System.out.println();
    System.out.println("Shop updated");
  }

  public Shop getShop(String shopName) {
    JSONObject shopJson = jsonFileManager.searchJSON(shopName);

    if (shopJson == null) {
      return null;
    } else {
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
    JSONArray Arr2 = shopJson.getJSONArray("staffs");
    List<String> staffs = new ArrayList<>();
    for (int i = 0; i < Arr2.size(); i++) {
      staffs.add(Arr2.getString(i));
    }

    return new Shop(shopName, purchasableCoupons, staffs);
  }

  public void generateDemoShop() {
    createShop("shop1");
    createShop("shop2");
  }
}
