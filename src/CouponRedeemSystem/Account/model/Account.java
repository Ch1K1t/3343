package CouponRedeemSystem.Account.model;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

  // Constructor for creating a new user account
  public Account(
    String userName,
    String role,
    int age,
    String telNo,
    String dateOfBirth
  ) {
    try {
      this.userName = userName;
      this.role = role;
      this.age = age;
      this.telNo = telNo;
      this.dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth);
      this.points = 0;
      this.couponIDs = new ArrayList<String>();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  // Constructor for creating a new non-user account
  public Account(String userName, String role) {
    this.userName = userName;
    this.role = role;
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

  public void couponToPoints(String couponCode) {
    AccountManager accountManager = AccountManager.getInstance();
    CouponManager couponManager = CouponManager.getInstance();

    Coupon coupon = couponManager.getCoupon(couponCode);
    if (coupon == null) {
      System.out.println("Coupon code" + couponCode + " does not exist!");
      return;
    } else if (coupon.getType().equals("Purchasable")) {
      System.out.println("This coupon is not redeemable!");
      return;
    } else if (!coupon.isActive()) {
      System.out.println("Coupon has been used!");
      return;
    } else if (coupon.getExpirationDate().before(new Date())) {
      System.out.println("Coupon has expired!");
      return;
    }

    double points = coupon.pointConversion();
    this.addPoints(points);
    accountManager.updateAccount(this);

    coupon.setActive(false);
    couponManager.updateCoupon(coupon);

    System.out.println();
    System.out.println("Coupon redeemed");
  }

  public void pointsToCoupon(String couponCode) {
    AccountManager accountManager = AccountManager.getInstance();
    CouponManager couponManager = CouponManager.getInstance();

    Coupon coupon = couponManager.getCoupon(couponCode);
    if (coupon == null) {
      System.out.println("Coupon code" + couponCode + " does not exist!");
      return;
    } else if (this.points < coupon.getPoints()) {
      System.out.println("Insufficient points!");
      return;
    } else if (this.couponIDs.size() > 10) {
      System.out.println("You have reached the account's purchasing limit!");
      return;
    }

    this.deductPoints(coupon.getPoints());
    this.couponIDs.add(coupon.getCouponCode());
    accountManager.updateAccount(this);

    coupon.setOwner(this.userName);
    couponManager.updateCoupon(coupon);

    System.out.println();
    System.out.println("Coupon purchased");
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

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setTelNo(String telNo) {
    this.telNo = telNo;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public void setCoupons(List<String> coupons) {
    this.couponIDs = coupons;
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
