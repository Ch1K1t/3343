package CouponRedeemSystem.Shop.model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.NormalCoupon;

public class Shop {
    String shopId;
    String shopName;
    ArrayList<String> approvedCouponId; //check Id when transact to validate the coupons


    public Shop(String shopId, String shopName) {
        super();
        this.shopName = shopName;
        this.shopId = shopId;
        this.approvedCouponId = new ArrayList<>();
    }

    //TODO: Transaction

    //TODO: check validity of coupons
    public boolean validate(String couponCode, Date expirationDate) {
        boolean valid = false;
        java.util.Date currentDate = new Date();
        for (String id :approvedCouponId) {
            if (id == couponCode) 
                break;
        }
        return !valid && expirationDate.before(currentDate);
    }

    //
    public void createCoupon(double intrinsicValue, Date expirationDate, String couponCode) throws ParseException {
        while (isBeforeCurrentDate(expirationDate)) {
            expirationDate = getNewDate();
        }
        while (intrinsicValue < 0) {
            intrinsicValue = getNewIntrinsicValue();
        }
        Coupon coupon = new NormalCoupon(intrinsicValue, null, expirationDate, couponCode); //create a new Coupon without owner
        coupon.setShop(this);
        approvedCouponId.add(coupon.getCouponCode());
    }
    
    public Date getNewDate() throws ParseException {
        Scanner s = new Scanner(System.in);
        System.out.println("Date inputted must be after today, please try again.");
        System.out.println("Please input the date in yyyy-MM-dd format.");
        String date = s.nextLine();
        Date dateObject = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        s.close();
        return dateObject;
    }
    
    private double getNewIntrinsicValue() { //is this manual inputted?
        Scanner s = new Scanner(System.in);
        System.out.println("Intrinsic value inputted must be after today, please try again.");
        System.out.println("Please input the date in yyyy-MM-dd format.");
        Double value = s.nextDouble();
        s.close();
        return value;
    }
    
    private boolean isBeforeCurrentDate(Date expirationDate) {
        Date currentDate = new Date();
        return currentDate.before(expirationDate);
    }

    public String getShopId() {return shopId;}
    
    public ArrayList<String> getApprovedCouponId() {return approvedCouponId;}
}
