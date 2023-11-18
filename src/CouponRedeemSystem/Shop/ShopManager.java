package CouponRedeemSystem.Shop;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.beanutils.LazyDynaBean;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;

public class ShopManager {
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
    public void createRecord(Shop shop) {
        LazyDynaBean bean = new LazyDynaBean();
        bean.set("shopId", shop.getShopId());
        bean.set("ApprovedCouponIds", shop.getApprovedCouponId());
        try {
            jsonFileManager.modifyJSON(null, shop.getShopId(), bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecord(Shop shop) {
        try {
            jsonFileManager.deleteJSON(null, shop.getShopId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Shop createShop(String shopName) {
        Shop shop = new Shop(Integer.toString(noOfShops), shopName);
        noOfShops++;
        shops.add(shop);
        return shop;
    }

    public void modifyShopName(Account account) {
        
    }

    public void writeShopListToJson() {
        
    }

    public ArrayList<Shop> readShopListFromJson() {
        File
    }
    //TODO: load shops from json?

}
