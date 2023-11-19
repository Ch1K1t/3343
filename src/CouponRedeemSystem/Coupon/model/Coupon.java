package CouponRedeemSystem.Coupon.model;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import net.sf.json.JSONObject;

public abstract class Coupon {

  double intrinsicValue;
  Shop shop;
  Date expirationDate;
  boolean active;
  String couponCode;
  Account owner;
  Double points;
  String type;

  // Purchasable Coupon
  public Coupon(
    double intrinsicValue,
    Shop shop,
    Date expirationDate,
    String couponCode,
    boolean active,
    double points
  ) {
    this.intrinsicValue = intrinsicValue;
    this.shop = shop;
    this.expirationDate = expirationDate;
    this.couponCode = couponCode;
    this.active = active;
    this.owner = null;
    this.points = points;
    this.type = "Purchasable";
  }

  // Redeemable Coupon
  public Coupon(
    double intrinsicValue,
    Shop shop,
    Date expirationDate,
    String couponCode,
    boolean active
  ) {
    this.intrinsicValue = intrinsicValue;
    this.shop = shop;
    this.expirationDate = expirationDate;
    this.couponCode = couponCode;
    this.active = active;
    this.type = "Redeemable";
  }

  public static void couponToPoints(String couponCode, Account account) {
    try {
      CouponManager couponManager = CouponManager.getInstance();
      Coupon coupon = couponManager.getCoupon(couponCode);
      if (coupon == null) {
        System.out.println("No coupon found!");
        return;
      }

      if (coupon.type == "Purchasable") {
        System.out.println("This coupon is not redeemable!");
        return;
      }

      if (!coupon.isActive()) {
        System.out.println("Coupon has been used!");
        return;
      }

      Date currentDate = new Date();
      if (coupon.getExpirationDate().compareTo(currentDate) < 0) {
        System.out.println("Coupon has expired!");
        return;
      }

      double points = coupon.pointConversion();
      CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
      JSONObject accountJSON = jsonFileManager.searchJSON(
        account.getUserName() + ".json"
      );
      accountJSON.put("points", account.getPoints() + points);
      jsonFileManager.modifyJSON("Account", account.getUserName(), accountJSON);

      JSONObject couponJSON = jsonFileManager.searchJSON(couponCode + ".json");
      couponJSON.put("active", false);
      jsonFileManager.modifyJSON(
        "Coupon/" + couponJSON.getString("type"),
        couponCode,
        couponJSON
      );

      System.out.println("Coupon redeemed successfully!");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void pointsToCoupon(String couponCode, Account account) {
    try {
      CouponManager couponManager = CouponManager.getInstance();
      CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
      Coupon coupon = couponManager.getCoupon(couponCode);
      if (coupon == null) {
        System.out.println("No coupon found!");
        return;
      }

      if (!coupon.isActive()) {
        System.out.println("Coupon is not available!");
        return;
      }

      // May not be necessary
      Date currentDate = new Date();
      if (coupon.getExpirationDate().before(currentDate)) {
        System.out.println("Coupon has expired!");
        return;
      }

      if (account.getPoints() < coupon.points) {
        System.out.println("Insufficient points!");
        return;
      }

      // Add coupons to user's coupons history
      List<String> coupons = account.getCouponIDs();
      if (coupons.size() > 10) {
        System.out.println("You have reached the account's purchasing limit!");
        return;
      }

      JSONObject accountJSON = jsonFileManager.searchJSON(
        account.getUserName() + ".json"
      );
      accountJSON.put("points", account.getPoints() - coupon.points);
      coupons.add(coupon.getCouponCode());
      accountJSON.put("couponIDs", coupons);
      jsonFileManager.modifyJSON("Account", account.getUserName(), accountJSON);

      JSONObject couponJSON = jsonFileManager.searchJSON(couponCode + ".json");
      couponJSON.put("owner", account.getUserName());
      jsonFileManager.modifyJSON("Coupon/Purchasable", couponCode, couponJSON);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Double pointConversion() {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(
        "EEE MMM dd HH:mm:ss z yyyy",
        Locale.ENGLISH
      );
      Date currentDate = sdf.parse(new Date().toString());
      Date expire = sdf.parse(expirationDate.toString());

      long daysBeforeExpire = Math.abs(
        expire.getTime() - currentDate.getTime()
      );
      // (Coupon Value + (Days to Expiration * Weight)) * Conversion Rate
      // Remarks: conversion rate refers to the amount of points rewarded per dollar
      // 1 -> 1 point per dollar
      DecimalFormat df = new DecimalFormat("###.##");
      return Double.parseDouble(
        df.format((this.getIntrinsicValue() + (daysBeforeExpire * 0.5)) * 1)
      );
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  public double getIntrinsicValue() {
    return intrinsicValue;
  }

  public void setIntrinsicValue(double intrinsicValue) {
    this.intrinsicValue = intrinsicValue;
  }

  public Shop getShop() {
    return shop;
  }

  public void setShop(Shop shop) {
    this.shop = shop;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getCouponCode() {
    return couponCode;
  }

  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }

  public Account getOwner() {
    return owner;
  }

  public void setOwner(Account owner) {
    this.owner = owner;
  }
}
