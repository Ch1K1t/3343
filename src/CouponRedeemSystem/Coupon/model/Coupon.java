package CouponRedeemSystem.Coupon.model;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.Util.Util;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Coupon.
 */
public abstract class Coupon {

  /** The coupon code. */
  private String couponCode;
  
  /** The intrinsic value. */
  private double intrinsicValue;
  
  /** The purchasing value. */
  private Double purchasingValue;
  
  /** The shop. */
  private Shop shop;
  
  /** The owner. */
  private Account owner;
  
  /** The active. */
  private boolean active;
  
  /** The type. */
  private String type;
  
  /** The expiration date. */
  private Date expirationDate;

  /**
   * Instantiates a new coupon.
   *
   * @param couponCode the coupon code
   * @param intrinsicValue the intrinsic value
   * @param purchasingValue the purchasing value
   * @param shop the shop
   * @param owner the owner
   * @param active the active
   * @param type the type
   * @param expirationDate the expiration date
   */
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

  /**
   * Instantiates a new coupon.
   *
   * @param couponCode the coupon code
   * @param intrinsicValue the intrinsic value
   * @param active the active
   * @param type the type
   * @param expirationDate the expiration date
   */
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

  /**
   * Point conversion.
   *
   * @return the double
   */
  public Double pointConversion() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(this.expirationDate);
    Calendar cal2 = Calendar.getInstance();
    cal2.setTime(Util.today);

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

  /**
   * To string.
   *
   * @return the string
   */
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

  /**
   * Gets the intrinsic value.
   *
   * @return the intrinsic value
   */
  public double getIntrinsicValue() {
    return intrinsicValue;
  }

  /**
   * Gets the shop.
   *
   * @return the shop
   */
  public Shop getShop() {
    return shop;
  }

  /**
   * Gets the expiration date.
   *
   * @return the expiration date
   */
  public Date getExpirationDate() {
    return expirationDate;
  }

  /**
   * Checks if is active.
   *
   * @return true, if is active
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Sets the active.
   *
   * @param active the new active
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Gets the coupon code.
   *
   * @return the coupon code
   */
  public String getCouponCode() {
    return couponCode;
  }

  /**
   * Gets the owner.
   *
   * @return the owner
   */
  public Account getOwner() {
    return owner;
  }

  /**
   * Sets the owner.
   *
   * @param owner the new owner
   */
  public void setOwner(Account owner) {
    this.owner = owner;
  }

  /**
   * Gets the purchasing value.
   *
   * @return the purchasing value
   */
  public Double getPurchasingValue() {
    return purchasingValue;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }
}
