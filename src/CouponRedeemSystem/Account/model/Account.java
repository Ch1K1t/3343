package CouponRedeemSystem.Account.model;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Discount.DiscountManager;
import CouponRedeemSystem.Discount.model.Discount;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.Util.Util;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Account.
 */
public class Account {

  /** The user name. */
  private String userName;
  
  /** The role. */
  private String role;
  
  /** The points. */
  private double points;
  
  /** The coupon I ds. */
  private List<String> couponIDs;
  
  /** The date of birth. */
  private Date dateOfBirth;
  
  /** The age. */
  private int age;
  
  /** The tel no. */
  private String telNo;

  /**
   * Instantiates a new account.
   *
   * @param userName the user name
   * @param role the role
   */
  // Constructor for creating a new non-user account
  public Account(String userName, String role) {
    this.userName = userName;
    this.role = role;
  }

  /**
   * Instantiates a new account.
   *
   * @param userName the user name
   * @param role the role
   * @param dateOfBirth the date of birth
   * @param telNo the tel no
   */
  // Constructor for creating a new user account
  public Account(
    String userName,
    String role,
    String dateOfBirth,
    String telNo
  ) {
    try {
      this.userName = userName;
      this.role = role;
      this.points = 0;
      this.couponIDs = new ArrayList<String>();
      this.dateOfBirth = Util.sdf.parse(dateOfBirth);
      this.age = Account.calculateAge(this.dateOfBirth);
      this.telNo = telNo;
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * Instantiates a new account.
   *
   * @param userName the user name
   * @param role the role
   * @param points the points
   * @param coupons the coupons
   * @param dateOfBirth the date of birth
   * @param age the age
   * @param telNo the tel no
   */
  // Constructor for loading an existing account
  public Account(
    String userName,
    String role,
    double points,
    List<String> coupons,
    Date dateOfBirth,
    int age,
    String telNo
  ) {
    this.userName = userName;
    this.role = role;
    this.points = points;
    this.couponIDs = coupons;
    this.dateOfBirth = dateOfBirth;
    this.age = age;
    this.telNo = telNo;
  }

  /**
   * Calculate age.
   *
   * @param dateOfBirth the date of birth
   * @return the int
   */
  public static int calculateAge(Date dateOfBirth) {
    Calendar today = Calendar.getInstance();
    Calendar dob = Calendar.getInstance();
    dob.setTime(dateOfBirth);
    int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
    if (
      dob.get(Calendar.MONTH) > today.get(Calendar.MONTH) ||
      (
        dob.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
        dob.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH)
      )
    ) {
      age--;
    }
    return age;
  }

  /**
   * Coupon to points.
   *
   * @param coupon the coupon
   */
  public void couponToPoints(Coupon coupon) {
    AccountManager accountManager = AccountManager.getInstance();
    CouponManager couponManager = CouponManager.getInstance();

    double points = coupon.pointConversion();
    this.addPoints(points);
    accountManager.updateAccount(this);

    coupon.setActive(false);
    couponManager.updateCoupon(coupon);
  }

  /**
   * Points to coupon.
   *
   * @param coupon the coupon
   */
  public void pointsToCoupon(Coupon coupon) {
    AccountManager accountManager = AccountManager.getInstance();
    CouponManager couponManager = CouponManager.getInstance();

    Shop shop = coupon.getShop();
    List<String> discountList = shop.getDiscountList();
    int discountTotal = 0;
    for (String s : discountList) {
      DiscountManager discountManager = DiscountManager.getInstance();
      Discount discount = discountManager.getDiscount(s);
      if (discount.validateTime()) {
        discountTotal += discount.getValue();
      }
    }

    this.deductPoints(
        coupon.getPurchasingValue() - discountTotal < 1
          ? 1
          : coupon.getPurchasingValue() - discountTotal
      );
    this.couponIDs.add(coupon.getCouponCode());
    accountManager.updateAccount(this);

    coupon.setOwner(this);
    couponManager.updateCoupon(coupon);
  }

  /**
   * Use coupon.
   *
   * @param coupon the coupon
   */
  public void useCoupon(Coupon coupon) {
    AccountManager accountManager = AccountManager.getInstance();
    CouponManager couponManager = CouponManager.getInstance();

    this.deleteCouponID(coupon.getCouponCode());
    accountManager.updateAccount(this);

    coupon.setActive(false);
    couponManager.updateCoupon(coupon);
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    if (role.equals("User")) {
      return (
        "Account{userName=\"" +
        userName +
        "\", role=\"" +
        role +
        "\", points=" +
        points +
        ", couponIDs=" +
        couponIDs +
        ", dateOfBirth=" +
        dateOfBirth +
        ", age=" +
        age +
        ", telNo=\"" +
        telNo +
        "\"}"
      );
    } else {
      return (
        "Account{userName=\"" + "" + userName + "\", role=\"" + role + "\"}"
      );
    }
  }

  /**
   * Gets the user name.
   *
   * @return the user name
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Gets the role.
   *
   * @return the role
   */
  public String getRole() {
    return role;
  }

  /**
   * Gets the age.
   *
   * @return the age
   */
  public int getAge() {
    return age;
  }

  /**
   * Gets the tel no.
   *
   * @return the tel no
   */
  public String getTelNo() {
    return telNo;
  }

  /**
   * Gets the date of birth.
   *
   * @return the date of birth
   */
  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * Gets the coupon I ds.
   *
   * @return the coupon I ds
   */
  public List<String> getCouponIDs() {
    return couponIDs;
  }

  /**
   * Gets the points.
   *
   * @return the points
   */
  public double getPoints() {
    return points;
  }

  /**
   * Adds the coupon ID.
   *
   * @param couponID the coupon ID
   */
  public void addCouponID(String couponID) {
    couponIDs.add(couponID);
  }

  /**
   * Delete coupon ID.
   *
   * @param couponID the coupon ID
   */
  public void deleteCouponID(String couponID) {
    couponIDs.remove(couponID);
  }

  /**
   * Adds the points.
   *
   * @param pointsToAdd the points to add
   */
  public void addPoints(double pointsToAdd) {
    this.points += pointsToAdd;
  }

  /**
   * Deduct points.
   *
   * @param pointsToDeduct the points to deduct
   */
  public void deductPoints(double pointsToDeduct) {
    this.points -= pointsToDeduct;
  }
}
