package CouponRedeemSystem.Promotion.model;

import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.Util.Util;
import java.text.ParseException;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Discount.
 */
public class Promotion {

  /** The discount name. */
  private String promotionName;

  /** The shop. */
  private Shop shop;

  /** The value. */
  private double value;

  /** The start date. */
  private Date startDate;

  /** The end date. */
  private Date endDate;

  /**
   * Instantiates a new discount.
   */
  public Promotion() {}

  /**
   * Instantiates a new discount.
   *
   * @param promotionName the discount name
   * @param shop the shop
   * @param value the value
   * @param startDate the start date
   * @param endDate the end date
   */
  public Promotion(
    String promotionName,
    Shop shop,
    double value,
    String startDate,
    String endDate
  ) {
    try {
      this.promotionName = promotionName;
      this.shop = shop;
      this.value = value;
      this.startDate = Util.sdf.parse(startDate);
      this.endDate = Util.sdf.parse(endDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * Validate time.
   *
   * @return true, if successful
   */
  public boolean validateTime() {
    return (
      Util.today.compareTo(this.startDate) >= 0 &&
      Util.today.compareTo(this.endDate) < 0
    );
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return (
      "Promotion{promotionName=\"" +
      promotionName +
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

  /**
   * Gets the discount name.
   *
   * @return the discount name
   */
  public String getPromotionName() {
    return promotionName;
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
   * Gets the start date.
   *
   * @return the start date
   */
  public Date getStartDate() {
    return startDate;
  }

  /**
   * Gets the end date.
   *
   * @return the end date
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public double getValue() {
    return value;
  }
}
