package CouponRedeemSystem.Shop.model;

import java.util.ArrayList;
import java.util.List;

public class Shop {

  private String shopName;
  private List<String> purchasableCoupons;
  private List<String> staffs;

  public Shop(String shopName) {
    this.shopName = shopName;
    this.purchasableCoupons = new ArrayList<String>();
    this.staffs = new ArrayList<String>();
  }

  public Shop(
    String shopName,
    List<String> purchasableCoupons,
    List<String> staffs
  ) {
    this.shopName = shopName;
    this.purchasableCoupons = purchasableCoupons;
    this.staffs = staffs;
  }

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

  public List<String> getStaffs() {
    return staffs;
  }

  public void setStaffs(List<String> staffs) {
    this.staffs = staffs;
  }

  public void addStaffs(String staff) {
    staffs.add(staff);
  }

  public void removeStaffs(String staff) {
    staffs.remove(staff);
  }
}
