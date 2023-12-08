package CouponRedeemSystem.Shop;

import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ShopManager.
 */
public class ShopManager {

  /** The instance. */
  private static ShopManager instance;
  
  /** The json file manager. */
  private CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

  /**
   * Instantiates a new shop manager.
   */
  private ShopManager() {}

  /**
   * Gets the single instance of ShopManager.
   *
   * @return single instance of ShopManager
   */
  public static ShopManager getInstance() {
    if (instance == null) instance = new ShopManager();
    return instance;
  }

  /**
   * Creates the shop.
   *
   * @param shopName the shop name
   * @return the shop
   */
  public Shop createShop(String shopName) {
    Shop shop = new Shop(shopName);
    this.updateShop(shop);

    return shop;
  }

  /**
   * Update shop.
   *
   * @param shop the shop
   */
  public void updateShop(Shop shop) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("shopName", shop.getShopName());
    jsonObject.put("couponList", shop.getCouponList());
    jsonObject.put("staffList", shop.getStaffList());
    jsonObject.put("discountList", shop.getDiscountList());

    jsonFileManager.modifyJSON("Shop", shop.getShopName(), jsonObject);
  }

  /**
   * Delete shop.
   *
   * @param shop the shop
   */
  public void deleteShop(Shop shop) {
    jsonFileManager.deleteJSON("Shop", shop.getShopName());
  }

  /**
   * Gets the shop.
   *
   * @param shopName the shop name
   * @return the shop
   */
  public Shop getShop(String shopName) {
    JSONObject shopJson = jsonFileManager.searchJSON(shopName);

    if (shopJson == null) {
      return null;
    } else {
      return extractShopFromJson(shopJson);
    }
  }

  /**
   * Gets the shop by staff.
   *
   * @param staffName the staff name
   * @return the shop by staff
   */
  public Shop getShopByStaff(String staffName) {
    List<Shop> shopList = getShopList();
    for (Shop shop : shopList) {
      if (shop.getStaffList().contains(staffName)) {
        return shop;
      }
    }
    return null;
  }

  /**
   * Gets the shop list.
   *
   * @return the shop list
   */
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

  /**
   * Extract shop from json.
   *
   * @param shopJson the shop json
   * @return the shop
   */
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

  /**
   * Json to string.
   *
   * @param jsonObject the json object
   * @return the string
   */
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
