package CouponRedeemSystem.Shop;

public class ShopManager {
    ArrayList<Shop> shops; //better use unordered map?
    int noOfShops = 0;
    public static ShopManager instance;
    private CRSJsonFileManager jsonFileManager = new CRSJsonFileManager.getInstance();

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

    public void createShop(string shopName) {
        Shop shop = new Shop(Integer.toString(noOfShops), shopName);
        noOfShops++;
        shops.add(shop);
        
    } 
}
