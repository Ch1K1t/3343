package CouponRedeemSystem.Shop.model;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.NormalCoupon;
import net.sf.json.JSONArray;
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
    public void transaction() {

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
        Coupon coupon = new NormalCoupon(intrinsicValue, null, null, couponCode, false); //create a new Coupon without owner
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

    public void loadApprovedCouponId(JSONObject couponObject) {
        //need manually parse JSONObject to HashMap each by each
        JSONObject approvedCoupon = couponObject.getJSONObject("approvedCoupons");
        
    }

    public String getShopId() {return shopId;}

    public String getShopName() {return shopName;}
}
