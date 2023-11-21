package CouponRedeemSystem.Shop.model;

import java.util.ArrayList;
import java.util.List;

public class Shop {

  private String shopName;
  private List<String> purchasableCoupons;

  //approved coupon -> purchasable coupon

  public Shop(String shopName) {
    this.shopName = shopName;
    this.purchasableCoupons = new ArrayList<String>();
  }

  public Shop(String shopName, List<String> purchasableCoupons) {
    this.shopName = shopName;
    this.purchasableCoupons = purchasableCoupons;
  }

  // public void transaction(Account user, String couponId) {
  //   CouponManager couponManager = CouponManager.getInstance();
  //   //check if user has coupon
  //   if (user.getCouponIDs().contains(couponId)) {
  //     if (couponManager.getCoupon(couponId) == null) {
  //       System.out.println("Coupon not found in database.");
  //     } else {
  //       Coupon coupon = couponManager.getCoupon(couponId);
  //       // check not template coupon
  //       if (coupon instanceof PurchasableCoupon) {
  //         // verify
  //         if (
  //           // coupon.getOwner() == user &&
  //           coupon.getExpirationDate().after(new Date()) &&
  //           coupon.getShop() == this &&
  //           coupon.isActive()
  //         ) System.out.println("Transaction is successful");
  //       } else System.out.println(
  //         "Transaction is unsuccessful, coupon is not purchasable."
  //       );
  //     }
  //   } else {
  //     System.out.println("Coupon not found in user's possession.");
  //   }
  // }

  // public boolean validate(String couponCode, Date expirationDate) {
  //   java.util.Date currentDate = new Date();
  //   //if coupon code found in map and not expired, valid
  //   return (
  //     purchasableCoupons.containsKey(couponCode) &&
  //     expirationDate.after(currentDate)
  //   );
  // }

  // public double getNewIntrinsicValue() { //is this manual inputted?
  //   Scanner s = new Scanner(System.in);
  //   System.out.println(
  //     "Intrinsic value inputted must be after today, please try again."
  //   );
  //   System.out.println("Please input the date in yyyy-MM-dd format.");
  //   Double value = s.nextDouble();
  //   s.close();
  //   return value;
  // }

  //TODO: need check applicable with Purchaseable Coupon
  // public void loadApprovedCoupons(JSONObject couponObject) {
  //   JSONObject approvedCoupon = couponObject.getJSONObject("approvedCoupons");
  //   for (Object c : approvedCoupon.keySet()) {
  //     String couponCode = (String) c;
  //     JSONObject couponfromJson = approvedCoupon.getJSONObject(couponCode);
  //     Coupon coupon = new PurchasableCoupon(
  //       couponfromJson.getDouble("intrinsicValue"),
  //       this,
  //       null,
  //       couponfromJson.getString("couponCode"),
  //       false,
  //       -1,
  //       "Purchasable"
  //     );
  //     purchasableCoupons.put(couponCode, coupon);
  //   }
  // }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public List<String> getPurchasableCoupons() {
    return purchasableCoupons;
  }

  public void setPurchasableCoupons(List<String> purchasableCoupons) {
    this.purchasableCoupons = purchasableCoupons;
  }

  public void addPurchasableCoupons(String couponCode) {
    purchasableCoupons.add(couponCode);
  }

  public void removePurchasableCoupons(String couponCode) {
    purchasableCoupons.remove(couponCode);
  }
}
