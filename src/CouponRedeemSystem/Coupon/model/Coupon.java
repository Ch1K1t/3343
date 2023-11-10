package CouponRedeemSystem.Coupon.model;

import java.util.Date;
import java.util.List;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Shop.model.Shop;

// public abstract class Coupon {

// }

public class Coupon {
    double intrinsicValue;
    Shop shop;
    Date expirationDate;
    boolean exchanged;
    String couponCode;
    Account owner;

    public Coupon(double intrinsicValue, Shop shop, Date expirationDate, String couponCode, Account owner) {
        this.intrinsicValue = intrinsicValue;
        this.shop = shop;
        this.expirationDate = expirationDate;
        this.couponCode = couponCode;
        this.owner = owner;
        this.exchanged = false;

        // Add coupons to user's coupons history
        List<Coupon> coupons = this.owner.getCoupons();
        coupons.add(this);
        this.owner.setCoupons(coupons);
    }

    public double getIntrinsicValue() {
        return intrinsicValue;
    }
    public void setIntrinsicValue(double intrinsicValue) {
        this.intrinsicValue = intrinsicValue;
    }
    public Shop getShop() {
        return shop;
    }
    public void setShop(Shop shop) {
        this.shop = shop;
    }
    public Date getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    public boolean isExchanged() {
        return exchanged;
    }
    public void setExchanged(boolean exchanged) {
        this.exchanged = exchanged;
    }
    public String getCouponCode() {
        return couponCode;
    }
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    public Account getOwner() {
        return owner;
    }
    public void setOwner(Account owner) {
        this.owner = owner;
    }
}