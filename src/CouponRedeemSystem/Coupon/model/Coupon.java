package CouponRedeemSystem.Coupon.model;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.Util.Util;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public abstract class Coupon {

  private String couponCode;
  private double intrinsicValue;
  private Double purchasingValue;
  private Shop shop;
  private Account owner;
  private boolean active;
  private String type;
  private Date expirationDate;

  // Purchasable Coupon
  public Coupon(
    String couponCode,
    double intrinsicValue,
    double purchasingValue,
    Shop shop,
    Account owner,
    boolean active,
    String type,
    String expirationDate
  ) {
    try {
      this.couponCode = couponCode;
      this.intrinsicValue = intrinsicValue;
      this.purchasingValue = purchasingValue;
      this.shop = shop;
      this.owner = owner;
      this.active = active;
      this.type = type;
      this.expirationDate = Util.sdf.parse(expirationDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  // Redeemable Coupon
  public Coupon(
    String couponCode,
    double intrinsicValue,
    boolean active,
    String type,
    String expirationDate
  ) {
    try {
      this.couponCode = couponCode;
      this.intrinsicValue = intrinsicValue;
      this.active = active;
      this.type = type;
      this.expirationDate = Util.sdf.parse(expirationDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public Double pointConversion() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(this.expirationDate);
    Calendar cal2 = Calendar.getInstance();
    cal2.setTime(new Date());

    int daysBeforeExpire =
      cal.get(Calendar.DAY_OF_YEAR) - cal2.get(Calendar.DAY_OF_YEAR);
    int yearBeforeExpire = cal.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
    if (yearBeforeExpire > 0) {
      daysBeforeExpire += yearBeforeExpire * 365;
    }
    DecimalFormat df = new DecimalFormat("###.##");
    return Double.parseDouble(
      df.format(this.intrinsicValue + (daysBeforeExpire * 0.5))
    );
  }

  @Override
  public String toString() {
    if (type.equals("Purchasable")) {
      return (
        "Coupon{couponCode=\"" +
        couponCode +
        "\", intrinsicValue=" +
        intrinsicValue +
        ", purchasingValue=" +
        purchasingValue +
        ", shop=" +
        shop +
        ", owner=" +
        owner +
        ", active=" +
        active +
        ", type=\"" +
        type +
        "\", expirationDate=" +
        expirationDate +
        "}"
      );
    } else {
      return (
        "Coupon{couponCode=\"" +
        couponCode +
        "\", intrinsicValue=" +
        intrinsicValue +
        ", active=" +
        active +
        ", type=\"" +
        type +
        "\", expirationDate=" +
        expirationDate +
        "}"
      );
    }
  }

  public double getIntrinsicValue() {
    return intrinsicValue;
  }

  public Shop getShop() {
    return shop;
  }

  public Date getExpirationDate() {
    return expirationDate;
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

  public Account getOwner() {
    return owner;
  }

  public void setOwner(Account owner) {
    this.owner = owner;
  }

  public Double getPurchasingValue() {
    return purchasingValue;
  }

  public String getType() {
    return type;
  }
}
