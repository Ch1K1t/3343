package CouponRedeemSystem.Shop.model;

public class Shop {
    int shopId;
    ArrayList<Coupon> approvedCouponId; //check Id when transact to validate the coupons


    public Shop(int shopId) {
        this.shopId = shopId;
        this.approvedCouponId = new ArrayList<>();
    }

    public void newApprovedCoupon(String couponCode) {
        approvedCouponId.add(couponCode);
    }

    //TODO: Transaction

    //TODO: check validity of coupons
    public boolean validate(String couponCode, LocalDateTime expirationDate) {
        boolean valid = false;
        LocalDateTime currentDate = LocalDateTime.now();
        for (String id :approvedCouponId) {
            if (id == couponCode) 
                break;
        }
        return !valid && expirationDate < currentDate;
    }

    //TODO: create Coupons own by shops
    public void createCoupon() {
        Coupon coupon = new Coupon(); //create Coupon
        approvedCouponId.add(coupon);
    }

    
}
