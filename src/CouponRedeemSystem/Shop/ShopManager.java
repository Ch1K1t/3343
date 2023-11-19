package CouponRedeemSystem.Shop;
import java.io.IOException;
import java.util.HashMap;

import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import net.sf.json.JSONObject;

public class ShopManager {
    static final String SHOP_JSON_FILE_NAME = "Shop.json";
    static final String SHOP_JSON_FILE_PATH = "Shop";
    private HashMap<String, Shop> shops; //better use unordered map?
    private int noOfShops = 0;
    private static ShopManager instance;
    private CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

    private ShopManager() {}

    public static ShopManager getInstance() {
        if (instance == null)
            instance = new ShopManager();
        return instance;
    }

    //Create a json and store into database
    //Call this function on shutdown
    public void writeJsonObjectForStorage() throws IOException {
        JSONObject object = new JSONObject();
        object.put("List of Shops", shops);
        object.put("number of Shops", noOfShops);
        // write to file
        jsonFileManager.modifyJSON(SHOP_JSON_FILE_PATH, SHOP_JSON_FILE_NAME, object);
    }

    public void loadAllShopsFromStorage() throws IOException {
        JSONObject json = jsonFileManager.searchJSON(SHOP_JSON_FILE_NAME);
        for (Object shop : json.getJSONObject("List of Shops").keySet()) {
            String shopId = (String) shop;
            JSONObject shopObject = json.getJSONObject("List of Shops").getJSONObject(shopId);
            Shop s = new Shop(shopObject.getString(shopId), shopObject.getString("shopName"));
            // load approve coupons in shop
            s.loadApprovedCoupons(shopObject);
            shops.put(shopObject.getString(shopId), s);
        }
        noOfShops = json.getInt("number of Shops");
    }

    public Shop createShop(String shopName) {
        Shop shop = new Shop(Integer.toString(noOfShops), shopName);
        noOfShops++;
        shops.put(shopName, shop);
        return shop;
    }

    public Shop RemoveShop(String shopName) {
        Shop shop = null;
        if (shops.get(shopName) != null)
            shops.remove(shopName);
        return shop;
    }

    public Shop findShop(String shopName) {
        Shop shop = null;
        shops.get(shopName);
        return shop;
    }

}
