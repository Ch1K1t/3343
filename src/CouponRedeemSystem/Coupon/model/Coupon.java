package CouponRedeemSystem.Coupon.model;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
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
    double points,
    String type
  ) {
    this.intrinsicValue = intrinsicValue;
    this.shop = shop;
    this.expirationDate = expirationDate;
    this.couponCode = couponCode;
    this.active = active;
    this.owner = null;
    this.points = points;
    this.type = type;
  }

  // Redeemable Coupon
  public Coupon(
    double intrinsicValue,
    Date expirationDate,
    String couponCode,
    boolean active,
    String type
  ) {
    this.intrinsicValue = intrinsicValue;
    this.expirationDate = expirationDate;
    this.couponCode = couponCode;
    this.active = active;
    this.type = type;
  }

  public Double pointConversion() {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(
        "EEE MMM dd HH:mm:ss zzz yyyy",
        Locale.ENGLISH
      );
      Date currentDate = sdf.parse(new Date().toString());
      Date expire = sdf.parse(expirationDate.toString());

      System.out.println(expire.getTime());
      System.out.println(currentDate.getTime());
      double daysBeforeExpire =
        Math.abs(expire.getTime() - currentDate.getTime()) / Math.pow(10, 9);
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

  public Double getPoints() {
    return points;
  }

  public void setPoints(Double points) {
    this.points = points;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
