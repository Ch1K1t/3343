package CouponRedeemSystem.Coupon.model;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class Coupon {

  private double intrinsicValue;
  private String shop;
  private Date expirationDate;
  private boolean active;
  private String couponCode;
  private String owner;
  private Double points;
  private String type;

  private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

  // Purchasable Coupon
  public Coupon(
    double intrinsicValue,
    String shop,
    String expirationDate,
    String couponCode,
    boolean active,
    double points,
    String type
  ) {
    try {
      this.intrinsicValue = intrinsicValue;
      this.shop = shop;
      this.expirationDate = sdf.parse(expirationDate);
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
      this.expirationDate = sdf.parse(expirationDate);
      this.couponCode = couponCode;
      this.active = active;
      this.type = type;
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

  public double getIntrinsicValue() {
    return intrinsicValue;
  }

  public void setIntrinsicValue(double intrinsicValue) {
    this.intrinsicValue = intrinsicValue;
  }

  public String getShop() {
    return shop;
  }

  public void setShop(String shop) {
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

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
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
