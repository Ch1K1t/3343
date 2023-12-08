package CouponRedeemSystem.Shop.model;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Shop.
 */
public class Shop {

  /** The shop name. */
  private String shopName;
  
  /** The coupon list. */
  private List<String> couponList;
  
  /** The staff list. */
  private List<String> staffList;
  
  /** The discount list. */
  private List<String> discountList;

  /**
   * Instantiates a new shop.
   *
   * @param shopName the shop name
   */
  public Shop(String shopName) {
    this.shopName = shopName;
    this.couponList = new ArrayList<String>();
    this.staffList = new ArrayList<String>();
    this.discountList = new ArrayList<String>();
  }

  /**
   * Instantiates a new shop.
   *
   * @param shopName the shop name
   * @param couponList the coupon list
   * @param staffList the staff list
   * @param discountList the discount list
   */
  public Shop(
    String shopName,
    List<String> couponList,
    List<String> staffList,
    List<String> discountList
  ) {
    this.shopName = shopName;
    this.couponList = couponList;
    this.staffList = staffList;
    this.discountList = discountList;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return (
      "Shop{shopName=\"" +
      shopName +
      "\", couponList=" +
      couponList +
      ", staffList=" +
      staffList +
      ", discountList=" +
      discountList +
      "}"
    );
  }

  /**
   * Gets the shop name.
   *
   * @return the shop name
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * Gets the coupon list.
   *
   * @return the coupon list
   */
  public List<String> getCouponList() {
    return couponList;
  }

  /**
   * Adds the coupon.
   *
   * @param couponCode the coupon code
   */
  public void addCoupon(String couponCode) {
    couponList.add(couponCode);
  }

  /**
   * Removes the coupon.
   *
   * @param couponCode the coupon code
   */
  public void removeCoupon(String couponCode) {
    couponList.remove(couponCode);
  }

  /**
   * Gets the staff list.
   *
   * @return the staff list
   */
  public List<String> getStaffList() {
    return staffList;
  }

  /**
   * Adds the staff.
   *
   * @param staff the staff
   */
  public void addStaff(String staff) {
    staffList.add(staff);
  }

  /**
   * Removes the staff.
   *
   * @param staff the staff
   */
  public void removeStaff(String staff) {
    staffList.remove(staff);
  }

  /**
   * Gets the discount list.
   *
   * @return the discount list
   */
  public List<String> getDiscountList() {
    return discountList;
  }

  /**
   * Adds the discount.
   *
   * @param discountCode the discount code
   */
  public void addDiscount(String discountCode) {
    discountList.add(discountCode);
  }

  /**
   * Removes the discount.
   *
   * @param discountCode the discount code
   */
  public void removeDiscount(String discountCode) {
    discountList.remove(discountCode);
  }
}
