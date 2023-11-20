package CouponRedeemSystem.Shop.model;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.PurchasableCoupon;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import net.sf.json.JSONObject;

public class Shop {

  private String shopName;

  private HashMap<String, Coupon> purchasableCoupons;

  //approved coupon -> purchasable coupon

  public Shop(String shopName) {
    this.shopName = shopName;
    this.purchasableCoupons = new HashMap<>();
  }

  public void transaction(Account user, String couponId) {
    CouponManager couponManager = CouponManager.getInstance();
    //check if user has coupon
    if (user.getCouponIDs().contains(couponId)) {
      if (couponManager.getCoupon(couponId) == null) {
        System.out.println("Coupon not found in database.");
      } else {
        Coupon coupon = couponManager.getCoupon(couponId);
        // check not template coupon
        if (coupon instanceof PurchasableCoupon) {
          // verify
          if (
            coupon.getOwner() == user &&
            coupon.getExpirationDate().after(new Date()) &&
            coupon.getShop() == this &&
            coupon.isActive()
          ) System.out.println("Transaction is successful");
        } else System.out.println(
          "Transaction is unsuccessful, coupon is not purchasable."
        );
      }
    } else {
      System.out.println("Coupon not found in user's possession.");
    }
  }

  public boolean validate(String couponCode, Date expirationDate) {
    java.util.Date currentDate = new Date();
    //if coupon code found in map and not expired, valid
    return (
      purchasableCoupons.containsKey(couponCode) &&
      expirationDate.after(currentDate)
    );
  }

  public void appendApprovedCoupons(String couponCode, Coupon coupon) {
    purchasableCoupons.put(couponCode, coupon);
  }

  public double getNewIntrinsicValue() { //is this manual inputted?
    Scanner s = new Scanner(System.in);
    System.out.println(
      "Intrinsic value inputted must be after today, please try again."
    );
    System.out.println("Please input the date in yyyy-MM-dd format.");
    Double value = s.nextDouble();
    s.close();
    return value;
  }

  public boolean isBeforeCurrentDate(Date expirationDate) {
    Date currentDate = new Date();
    return currentDate.before(expirationDate);
  }

  //TODO: need check applicable with Purchaseable Coupon
  public void loadApprovedCoupons(JSONObject couponObject) {
    JSONObject approvedCoupon = couponObject.getJSONObject("approvedCoupons");
    for (Object c : approvedCoupon.keySet()) {
      String couponCode = (String) c;
      JSONObject couponfromJson = approvedCoupon.getJSONObject(couponCode);
      Coupon coupon = new PurchasableCoupon(
        couponfromJson.getDouble("intrinsicValue"),
        this,
        null,
        couponfromJson.getString("couponCode"),
        false,
        -1,
        "Purchasable"
      );
      purchasableCoupons.put(couponCode, coupon);
    }
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public HashMap<String, Coupon> getPurchasableCoupons() {
    return purchasableCoupons;
  }

  public void setPurchasableCoupons(HashMap<String, Coupon> purchasedCoupons) {
    this.purchasableCoupons = purchasedCoupons;
  }
}
