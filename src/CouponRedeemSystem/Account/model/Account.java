package CouponRedeemSystem.Account.model;

import CouponRedeemSystem.Coupon.model.Coupon;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
  String userName;
  int age;
  int telNo;
  double points;
  Date dateOfBirth;
  List<String> couponIDs;

  public Account(
      String userName,
      int age,
      int telNo,
      String dateOfBirth) throws ParseException {
    this.userName = userName;
    this.age = age;
    this.telNo = telNo;
    this.points = 0;
    this.dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
    couponIDs = new ArrayList<String>();
  }

  public Account(
      String userName,
      int age,
      int telNo,
      double points,
      String dateOfBirth,
      List<String> coupons) throws ParseException {
    this.userName = userName;
    this.age = age;
    this.telNo = telNo;
    this.points = points;
    this.dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
    couponIDs = new ArrayList<String>();
  }

  public String getUserName() {
    return userName;
  }

  public int getAge() {
    return age;
  }

  public int getTelNo() {
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

  public void setAge(int age) {
    this.age = age;
  }

  public void setTelNo(int telNo) {
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
    if (pointsToDeduct > this.points) {
      // Handle insufficient points scenario (throw an exception, log a message, etc.)
      System.out.println("Insufficient points to deduct.");
    } else {
      this.points -= pointsToDeduct;
    }
  }
}
