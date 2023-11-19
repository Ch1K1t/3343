package CouponRedeemSystem.Shop;
import java.io.IOException;
import java.util.ArrayList;

import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ShopManager {
    static final String SHOP_JSON_FILE_NAME = "Shop.json";
    static final String SHOP_JSON_FILE_PATH = "Shop";
    private ArrayList<Shop> shops; //better use unordered map?
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
        JSONArray array = new JSONArray();
        for (Shop s: shops) {
            array.add(s);
        }
        object.put("List of Shops", array);
        object.put("number of Shops", noOfShops);
        // write to file
        jsonFileManager.modifyJSON(SHOP_JSON_FILE_PATH, SHOP_JSON_FILE_NAME, object);
    }

    public void loadAllShopsFromStorage() throws IOException {
        JSONObject object = jsonFileManager.searchJSON(SHOP_JSON_FILE_NAME);
        JSONArray array = object.getJSONArray("List of Shops");
        for (int i = 0; i < array.size(); i++) {
            JSONObject shop = array.getJSONObject(i);
            Shop s = new Shop(shop.getString("shopId"), shop.getString("shopName"));
            // load approve coupons in shop
            s.loadApprovedCoupons(shop);
            shops.add(s);
        }
        noOfShops = object.getInt("number of Shops");
    }

    public Shop createShop(String shopName) {
        Shop shop = new Shop(Integer.toString(noOfShops), shopName);
        noOfShops++;
        shops.add(shop);
        return shop;
    }

    public Shop RemoveShop(String shopName) {
        Shop shop = null;
        for (Shop s: shops) {
            if (s.getShopName().equals(shopName)) {
                shop = s;
                break;
            }
       }
       shops.remove(shop);
       return shop;
    }


}
