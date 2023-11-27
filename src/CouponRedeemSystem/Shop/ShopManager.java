package CouponRedeemSystem.Shop;

import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ShopManager {

  private static ShopManager instance;
  private CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

  private ShopManager() {}

  public static ShopManager getInstance() {
    if (instance == null) instance = new ShopManager();
    return instance;
  }

  public Shop createShop(String shopName) {
    JSONObject jsonObject = jsonFileManager.searchJSON(shopName);
    if (jsonObject != null) {
      return null;
    }

    Shop shop = new Shop(shopName);
    this.updateShop(shop);

    return shop;
  }

  public boolean updateShop(Shop shop) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("shopName", shop.getShopName());
    jsonObject.put("couponList", shop.getPurchasableCouponList());
    jsonObject.put("staffList", shop.getStaffList());
    jsonObject.put("discountList", shop.getDiscountList());

    return jsonFileManager.modifyJSON("Shop", shop.getShopName(), jsonObject);
  }

  public boolean deleteShop(Shop shop) {
    if (shop == null) {
      return false;
    }

    return jsonFileManager.deleteJSON("Shop", shop.getShopName());
  }

  public Shop getShop(String shopName) {
    JSONObject shopJson = jsonFileManager.searchJSON(shopName);

    if (shopJson == null) {
      return null;
    } else {
      return extractShopFromJson(shopJson);
    }
  }

  public Shop getShopByStaff(String staffName) {
    List<Shop> shopList = getShopList();
    for (Shop shop : shopList) {
      if (shop.getStaffList().contains(staffName)) {
        return shop;
      }
    }
    return null;
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

  public Shop extractShopFromJson(JSONObject shopJson) {
    String shopName = shopJson.getString("shopName");
    JSONArray Arr = shopJson.getJSONArray("couponList");
    List<String> couponList = new ArrayList<>();
    for (int i = 0; i < Arr.size(); i++) {
      couponList.add(Arr.getString(i));
    }
    JSONArray Arr2 = shopJson.getJSONArray("staffList");
    List<String> staffList = new ArrayList<>();
    for (int i = 0; i < Arr2.size(); i++) {
      staffList.add(Arr2.getString(i));
    }
    JSONArray Arr3 = shopJson.getJSONArray("discountList");
    List<String> discountList = new ArrayList<>();
    for (int i = 0; i < Arr3.size(); i++) {
      discountList.add(Arr3.getString(i));
    }

    return new Shop(shopName, couponList, staffList, discountList);
  }

  public String jsonToString(JSONObject jsonObject) {
    String shopName = jsonObject.getString("shopName");
    JSONArray couponList = jsonObject.getJSONArray("couponList");
    JSONArray staffList = jsonObject.getJSONArray("staffList");
    JSONArray discountList = jsonObject.getJSONArray("discountList");

    return (
      "{\"shopName\":\"" +
      shopName +
      "\", \"couponList\":" +
      couponList +
      ", \"staffList\":" +
      staffList +
      ", \"discountList\":" +
      discountList +
      "}"
    );
  }
}
