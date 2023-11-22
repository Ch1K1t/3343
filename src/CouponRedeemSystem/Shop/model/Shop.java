package CouponRedeemSystem.Shop.model;

import java.util.ArrayList;
import java.util.List;

public class Shop {

  private String shopName;
  private List<String> purchasableCouponList;
  private List<String> staffList;
  private List<String> discountList;

  public Shop(String shopName) {
    this.shopName = shopName;
    this.purchasableCouponList = new ArrayList<String>();
    this.staffList = new ArrayList<String>();
    this.discountList = new ArrayList<String>();
  }

  public Shop(
    String shopName,
    List<String> purchasableCouponList,
    List<String> staffList,
    List<String> discountList
  ) {
    this.shopName = shopName;
    this.purchasableCouponList = purchasableCouponList;
    this.staffList = staffList;
    this.discountList = discountList;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public List<String> getPurchasableCouponList() {
    return purchasableCouponList;
  }

  public void setPurchasableCouponList(List<String> purchasableCouponList) {
    this.purchasableCouponList = purchasableCouponList;
  }

  public void addPurchasableCoupon(String couponCode) {
    purchasableCouponList.add(couponCode);
  }

  public void removePurchasableCoupon(String couponCode) {
    purchasableCouponList.remove(couponCode);
  }

  public List<String> getStaffList() {
    return staffList;
  }

  public void setStaffList(List<String> staffList) {
    this.staffList = staffList;
  }

  public void addStaff(String staff) {
    staffList.add(staff);
  }

  public void removeStaff(String staff) {
    staffList.remove(staff);
  }

  public List<String> getDiscountList() {
    return discountList;
  }

  public void setDiscountList(List<String> discountList) {
    this.discountList = discountList;
  }

  public void addDiscount(String discountCode) {
    discountList.add(discountCode);
  }

  public void removeDiscount(String discountCode) {
    discountList.remove(discountCode);
  }
}
