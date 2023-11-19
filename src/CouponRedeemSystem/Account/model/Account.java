package CouponRedeemSystem.Account.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Account {

  String userName;
  String role;
  int age;
  int telNo;
  Date dateOfBirth;
  double points;
  List<String> couponIDs;

  public Account(
    String userName,
    String role,
    int age,
    int telNo,
    String dateOfBirth
  ) throws ParseException {
    this.userName = userName;
    this.role = role;
    this.age = age;
    this.telNo = telNo;
    this.dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth);
    this.points = 0;
    this.couponIDs = new ArrayList<String>();
  }

  public Account(
    String userName,
    String role,
    int age,
    int telNo,
    String dateOfBirth,
    double points,
    List<String> coupons
  ) throws ParseException {
    this.userName = userName;
    this.role = null;
    this.age = age;
    this.telNo = telNo;
    this.points = points;
    this.dateOfBirth =
      new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        .parse(dateOfBirth);
    this.couponIDs = coupons;
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

  public void setRole(String role) {
    this.role = role;
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
