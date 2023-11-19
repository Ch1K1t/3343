package CouponRedeemSystem.Shop.model;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.RedeemableCoupon;
import net.sf.json.JSONObject;

public class Shop {
    private String shopId;
    private String shopName;
    private HashMap<String, Coupon> approvedCoupons;


    public Shop(String shopId, String shopName) {
        super();
        this.shopName = shopName;
        this.shopId = shopId;
        this.approvedCoupons = new HashMap<>();
    }

    //TODO: Transaction
    public void transaction(List<String> userPossessedCouponIds, String couponId) {
        CouponManager couponManager = CouponManager.getInstance();
        if (userPossessedCouponIds.contains(couponId)) {
            couponManager.getCoupon(couponId, couponId);
        }


    }

    public boolean validate(String couponCode, Date expirationDate) {
        java.util.Date currentDate = new Date();
        //if coupon code found in map and not expired, valid
        return approvedCoupons.containsKey(couponCode) && expirationDate.after(currentDate);
    }

    public void createCoupon(double intrinsicValue, String couponCode) throws ParseException {
        while (intrinsicValue < 0) {
            intrinsicValue = getNewIntrinsicValue();
        }
        Coupon coupon = new RedeemableCoupon(intrinsicValue, null, null, couponCode, false); //create a new Coupon without owner
        coupon.setShop(this);
        //map coupon code to coupon
        //to create customer coupon, need duplicate template object and set other attributes
        approvedCoupons.put(couponCode, coupon);
    }
    
    public double getNewIntrinsicValue() { //is this manual inputted?
        Scanner s = new Scanner(System.in);
        System.out.println("Intrinsic value inputted must be after today, please try again.");
        System.out.println("Please input the date in yyyy-MM-dd format.");
        Double value = s.nextDouble();
        s.close();
        return value;
    }
    
    public boolean isBeforeCurrentDate(Date expirationDate) {
        Date currentDate = new Date();
        return currentDate.before(expirationDate);
    }

    public void loadApprovedCoupons(JSONObject couponObject) {
        JSONObject approvedCoupon = couponObject.getJSONObject("approvedCoupons");
        for (Object c: approvedCoupon.keySet()) {
            String couponCode = (String) c;
            JSONObject couponfromJson = approvedCoupon.getJSONObject(couponCode);
            Coupon coupon = new RedeemableCoupon(couponfromJson.getDouble("intrinsicValue"), this, null, couponfromJson.getString("couponCode"), false);
            approvedCoupons.put(couponCode, coupon);
        }
    }

    public String getShopId() {return shopId;}

    public String getShopName() {return shopName;}
}
