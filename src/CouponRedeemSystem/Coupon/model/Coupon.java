package CouponRedeemSystem.Coupon.model;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public abstract class Coupon {

  double intrinsicValue;
  Shop shop;
  Date expirationDate;
  boolean active;
  String couponCode;
  Account owner;

  public Coupon(
    double intrinsicValue,
    Shop shop,
    Date expirationDate,
    String couponCode,
    Account owner
  ) {
    this.intrinsicValue = intrinsicValue;
    this.shop = shop;
    this.expirationDate = expirationDate;
    this.couponCode = couponCode;
    this.owner = owner;
    this.active = true;

    // Add coupons to user's coupons history
    List<Coupon> coupons = this.owner.getCoupons();
    coupons.add(this);
    this.owner.setCoupons(coupons);
  }

  public static void couponToPoints(Coupon coupon) {
    CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

    Date currentDate = new Date();

    if (!coupon.isActive()) return;
    if (coupon.getExpirationDate().compareTo(currentDate) <= 0) return;
    boolean exist =
      jsonFileManager.searchFile(coupon.getCouponCode(), null) == null;
    if (!exist) return;

    double points;
    try {
      points = coupon.pointConversion();
      double newPoints = coupon.getOwner().getPoints() + points;
      coupon.getOwner().setPoints(newPoints);
      coupon.setActive(false);
    } catch (ParseException e) {
      e.printStackTrace();
    }

  }

  public double pointConversion() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
    Date currentDate = sdf.parse(new Date().toString());
    Date expire = sdf.parse(expirationDate.toString());

    long daysBeforeExpire = Math.abs(expire.getTime() - currentDate.getTime());
    // (Coupon Value + (Days to Expiration * Weight)) * Conversion Rate
    // Remarks: conversion rate refers to the amount of points rewarded per dollar
    // 1 -> 1 point per dollar
    // TODO: Add more criteria for judging value?
    return (this.getIntrinsicValue() + (daysBeforeExpire * 0.5)) * 1;
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
