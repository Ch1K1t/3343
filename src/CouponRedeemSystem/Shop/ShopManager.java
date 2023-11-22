package CouponRedeemSystem.Shop;

import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.File;
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

  public boolean createShop(String shopName) {
    Shop shop = new Shop(shopName);

    LazyDynaBean bean = new LazyDynaBean();
    bean.set("shopName", shop.getShopName());
    bean.set("purchasableCouponList", shop.getPurchasableCouponList());
    bean.set("staffList", shop.getStaffList());

    return jsonFileManager.modifyJSON("Shop", shopName, bean);
  }

  public boolean deleteShop(String shopName) {
    Shop shop = getShop(shopName);
    if (shop == null) {
      System.out.println("Shop " + shopName + " does not exist");
      return false;
    }

    return jsonFileManager.deleteJSON("Shop", shopName);
  }

  public boolean updateShop(Shop shop) {
    LazyDynaBean bean = new LazyDynaBean();
    bean.set("shopName", shop.getShopName());
    bean.set("purchasableCouponList", shop.getPurchasableCouponList());
    bean.set("staffList", shop.getStaffList());

    return jsonFileManager.modifyJSON("Shop", shop.getShopName(), bean);
  }

  public Shop getShop(String shopName) {
    JSONObject shopJson = jsonFileManager.searchJSON(shopName);

    if (shopJson == null) {
      return null;
    } else {
      return extractShopFromJson(shopJson);
    }
  }

  public List<Shop> getShopList() {
    List<Shop> shopList = new ArrayList<>();
    File[] fileArr = new File("Data/Shop").listFiles();
    for (File file : fileArr) {
      JSONObject jsonObject = jsonFileManager.convertFileTextToJSON(file);
      Shop shop = extractShopFromJson(jsonObject);
      shopList.add(shop);
    }

    return shopList;
  }

  private Shop extractShopFromJson(JSONObject shopJson) {
    String shopName = shopJson.getString("shopName");
    JSONArray Arr = shopJson.getJSONArray("purchasableCouponList");
    List<String> purchasableCouponList = new ArrayList<>();
    for (int i = 0; i < Arr.size(); i++) {
      purchasableCouponList.add(Arr.getString(i));
    }
    JSONArray Arr2 = shopJson.getJSONArray("staffList");
    List<String> staffList = new ArrayList<>();
    for (int i = 0; i < Arr2.size(); i++) {
      staffList.add(Arr2.getString(i));
    }

    return new Shop(shopName, purchasableCouponList, staffList);
  }

  public void generateDemoShop() {
    createShop("shop1");
    Shop shop = getShop("shop1");
    shop.addStaff("staff");
    shop.addPurchasableCoupon("P1");
    updateShop(shop);
    createShop("shop2");
    Shop shop2 = getShop("shop2");
    shop2.addPurchasableCoupon("P2");
    updateShop(shop2);
  }
}
