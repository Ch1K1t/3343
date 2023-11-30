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

public class Account {

  private String userName;
  private String role;
  private double points;
  private List<String> couponIDs;
  private Date dateOfBirth;
  private int age;
  private String telNo;

  // Constructor for creating a new non-user account
  public Account(String userName, String role) {
    this.userName = userName;
    this.role = role;
  }

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

  public void couponToPoints(Coupon coupon) {
    AccountManager accountManager = AccountManager.getInstance();
    CouponManager couponManager = CouponManager.getInstance();

    double points = coupon.pointConversion();
    this.addPoints(points);
    accountManager.updateAccount(this);

    coupon.setActive(false);
    couponManager.updateCoupon(coupon);
  }

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

  public void useCoupon(Coupon coupon) {
    AccountManager accountManager = AccountManager.getInstance();
    CouponManager couponManager = CouponManager.getInstance();

    this.deleteCouponID(coupon.getCouponCode());
    accountManager.updateAccount(this);

    coupon.setActive(false);
    couponManager.updateCoupon(coupon);
  }

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

  public String getUserName() {
    return userName;
  }

  public String getRole() {
    return role;
  }

  public int getAge() {
    return age;
  }

  public String getTelNo() {
    return telNo;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public List<String> getCouponIDs() {
    return couponIDs;
  }

  public double getPoints() {
    return points;
  }

  public void addCouponID(String couponID) {
    couponIDs.add(couponID);
  }

  public void deleteCouponID(String couponID) {
    couponIDs.remove(couponID);
  }

  public void addPoints(double pointsToAdd) {
    this.points += pointsToAdd;
  }

  public void deductPoints(double pointsToDeduct) {
    this.points -= pointsToDeduct;
  }
}
