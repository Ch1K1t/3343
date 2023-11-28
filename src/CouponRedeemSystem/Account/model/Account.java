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

  String userName;
  String role;
  int age;
  String telNo;
  Date dateOfBirth;
  double points;
  List<String> couponIDs;

  // Constructor for creating a new non-user account
  public Account(String userName, String role) {
    this.userName = userName;
    this.role = role;
  }

  // Constructor for creating a new user account
  public Account(
    String userName,
    String role,
    String telNo,
    String dateOfBirth
  ) {
    try {
      this.userName = userName;
      this.role = role;
      this.dateOfBirth = Util.sdf.parse(dateOfBirth);
      Calendar today = Calendar.getInstance();
      Calendar dob = Calendar.getInstance();
      dob.setTime(this.dateOfBirth);
      int _age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
      if (
        dob.get(Calendar.MONTH) > today.get(Calendar.MONTH) ||
        (
          dob.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
          dob.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH)
        )
      ) {
        _age--;
      }
      this.age = _age;
      this.telNo = telNo;
      this.points = 0;
      this.couponIDs = new ArrayList<String>();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  // Constructor for loading an existing account
  public Account(
    String userName,
    String role,
    int age,
    String telNo,
    Date dateOfBirth,
    double points,
    List<String> coupons
  ) {
    this.userName = userName;
    this.role = role;
    this.age = age;
    this.telNo = telNo;
    this.points = points;
    this.dateOfBirth = dateOfBirth;
    this.couponIDs = coupons;
  }

  public boolean couponToPoints(Coupon coupon) {
    AccountManager accountManager = AccountManager.getInstance();
    CouponManager couponManager = CouponManager.getInstance();

    double points = coupon.pointConversion();
    this.addPoints(points);
    boolean isAccUpdated = accountManager.updateAccount(this);

    coupon.setActive(false);
    boolean isCouponUpdated = couponManager.updateCoupon(coupon);

    return isAccUpdated && isCouponUpdated;
  }

  public boolean pointsToCoupon(Coupon coupon) {
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
        coupon.getPoints() - discountTotal < 1
          ? 1
          : coupon.getPoints() - discountTotal
      );
    this.couponIDs.add(coupon.getCouponCode());
    boolean isAccUpdated = accountManager.updateAccount(this);

    coupon.setOwner(this);
    boolean isCouponUpdated = couponManager.updateCoupon(coupon);

    return isAccUpdated && isCouponUpdated;
  }

  public boolean useCoupon(Coupon coupon) {
    AccountManager accountManager = AccountManager.getInstance();
    CouponManager couponManager = CouponManager.getInstance();

    this.deleteCouponID(coupon.getCouponCode());
    boolean isAccUpdated = accountManager.updateAccount(this);

    coupon.setActive(false);
    boolean isCouponUpdated = couponManager.updateCoupon(coupon);

    return isAccUpdated && isCouponUpdated;
  }

  @Override
  public String toString() {
    if (role.equals("User")) {
      return (
        "Account{userName=\"" +
        userName +
        "\", role=\"" +
        role +
        "\", age=" +
        age +
        ", telNo=\"" +
        telNo +
        "\", dateOfBirth=" +
        dateOfBirth +
        ", points=" +
        points +
        ", couponIDs=" +
        couponIDs +
        "}"
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

  public void setPoints(double score) {
    this.points = score;
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
