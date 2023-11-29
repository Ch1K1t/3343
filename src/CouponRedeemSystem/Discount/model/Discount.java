package CouponRedeemSystem.Discount.model;

import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.Util.Util;
import java.text.ParseException;
import java.util.Date;

public class Discount {

  private String discountName;
  private Shop shop;
  private double value;
  private Date startDate;
  private Date expireDate;

  public Discount() {}

  public Discount(
    String discountName,
    Shop shop,
    double value,
    String startDate,
    String expireDate
  ) {
    try {
      this.discountName = discountName;
      this.shop = shop;
      this.value = value;
      this.startDate = Util.sdf.parse(startDate);
      this.expireDate = Util.sdf.parse(expireDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public boolean validateTime() {
    return (
      new Date().compareTo(this.startDate) >= 0 &&
      new Date().compareTo(this.expireDate) < 0
    );
  }

  @Override
  public String toString() {
    return (
      "Discount{discountName=\"" +
      discountName +
      "\", shop=" +
      shop +
      ", startDate=" +
      startDate +
      ", expireDate=" +
      expireDate +
      ", value=" +
      value +
      "}"
    );
  }

  public String getDiscountName() {
    return discountName;
  }

  public Shop getShop() {
    return shop;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getExpireDate() {
    return expireDate;
  }

  public double getValue() {
    return value;
  }
}
