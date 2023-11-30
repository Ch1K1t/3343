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
  private Date endDate;

  public Discount() {}

  public Discount(
    String discountName,
    Shop shop,
    double value,
    String startDate,
    String endDate
  ) {
    try {
      this.discountName = discountName;
      this.shop = shop;
      this.value = value;
      this.startDate = Util.sdf.parse(startDate);
      this.endDate = Util.sdf.parse(endDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public boolean validateTime() {
    return (
      Util.today.compareTo(this.startDate) >= 0 &&
      Util.today.compareTo(this.endDate) < 0
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
      ", endDate=" +
      endDate +
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

  public Date getEndDate() {
    return endDate;
  }

  public double getValue() {
    return value;
  }
}
