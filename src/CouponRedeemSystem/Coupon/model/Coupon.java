package CouponRedeemSystem.Coupon.model;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Shop.model.Shop;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class Coupon {

  private String couponCode;
  private double intrinsicValue;
  private Double points;
  private Shop shop;
  private Account owner;
  private boolean active;
  private String type;
  private Date expirationDate;

  private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

  // Purchasable Coupon
  public Coupon(
    String couponCode,
    double intrinsicValue,
    double points,
    Shop shop,
    Account owner,
    boolean active,
    String type,
    String expirationDate
  ) {
    try {
      this.couponCode = couponCode;
      this.intrinsicValue = intrinsicValue;
      this.points = points;
      this.shop = shop;
      this.owner = owner;
      this.active = active;
      this.type = type;
      this.expirationDate = sdf.parse(expirationDate);
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
      this.expirationDate = sdf.parse(expirationDate);
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
        ", points=" +
        points +
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
