package CouponRedeemSystem.Coupon.model;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Shop.model.Shop;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    String expirationDate,
    String couponCode,
    boolean active,
    double points,
    String type
  ) {
    try {
      this.intrinsicValue = intrinsicValue;
      this.shop = shop;
      this.expirationDate =
        new SimpleDateFormat("dd/MM/yyyy").parse(expirationDate);
      this.couponCode = couponCode;
      this.active = active;
      this.owner = null;
      this.points = points;
      this.type = type;
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  // Redeemable Coupon
  public Coupon(
    double intrinsicValue,
    String expirationDate,
    String couponCode,
    boolean active,
    String type
  ) {
    try {
      this.intrinsicValue = intrinsicValue;
      this.expirationDate =
        new SimpleDateFormat("dd/MM/yyyy").parse(expirationDate);
      this.couponCode = couponCode;
      this.active = active;
      this.type = type;
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public Double pointConversion() {
    Date currentDate = new Date();

    System.out.println(this.expirationDate.getTime());
    System.out.println(currentDate.getTime());
    double daysBeforeExpire =
      Math.abs(this.expirationDate.getTime() - currentDate.getTime()) /
      Math.pow(10, 9);
    // (Coupon Value + (Days to Expiration * Weight)) * Conversion Rate
    // Remarks: conversion rate refers to the amount of points rewarded per dollar
    // 1 -> 1 point per dollar
    DecimalFormat df = new DecimalFormat("###.##");
    return Double.parseDouble(
      df.format((this.intrinsicValue + (daysBeforeExpire * 0.5)) * 1)
    );
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
