package CouponRedeemSystem.Discount.model;

import CouponRedeemSystem.Shop.model.Shop;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Discount {

  private String discountName;
  private Shop shop;
  private Date startDate;
  private Date expireDate;
  private double value;

  private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

  public Discount() {}

  public Discount(
    String discountName,
    Shop shop,
    String startDate,
    String expireDate,
    double value
  ) {
    try {
      this.discountName = discountName;
      this.shop = shop;
      this.startDate = sdf.parse(startDate);
      this.expireDate = sdf.parse(expireDate);
      this.value = value;
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public boolean validateTime() {
    return (
      new Date().compareTo(this.startDate) > 0 &&
      new Date().compareTo(this.expireDate) < 0
    );
  }

  public String getDiscountName() {
    return discountName;
  }

  public void setDiscountName(String discountName) {
    this.discountName = discountName;
  }

  public Shop getShop() {
    return shop;
  }

  public void setShop(Shop shop) {
    this.shop = shop;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(Date expireDate) {
    this.expireDate = expireDate;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }
}
