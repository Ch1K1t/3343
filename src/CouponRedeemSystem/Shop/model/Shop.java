package CouponRedeemSystem.Shop.model;
import java.Util.Date;

public class Shop {
    int shopId;
    String shopName;
    ArrayList<String> approvedCouponId; //check Id when transact to validate the coupons


    public Shop(int shopId, String shopName) {
        super();
        this.shopId = shopName;
        this.shopId = shopId;
        this.approvedCouponId = new ArrayList<>();
    }

    //TODO: Transaction

    //TODO: check validity of coupons
    public boolean validate(String couponCode, Date expirationDate) {
        boolean valid = false;
        Date currentDate = new Date();
        for (String id :approvedCouponId) {
            if (id == couponCode) 
                break;
        }
        return !valid && expirationDate.before(currentDate);
    }

    //
    public void createCoupon() {
        Coupon coupon = new Coupon(intrinsicValue, null, expirationDate, couponCode, null); //create a new Coupon without owner
        coupon.setShop(self);
        approvedCouponId.add(coupon.getCouponCode);
    }

    public String getShopId() {return shopId;}

    public ArrayList<String> getApprovedCouponId() {return approvedCouponId;}
    
}
