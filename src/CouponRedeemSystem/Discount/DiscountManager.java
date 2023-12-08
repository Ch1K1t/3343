package CouponRedeemSystem.Discount;

import CouponRedeemSystem.Discount.model.Discount;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import CouponRedeemSystem.System.Util.Util;
import java.text.ParseException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscountManager.
 */
public class DiscountManager {

  /** The instance. */
  private static DiscountManager instance;

  /** The json file manager. */
  private final CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

  /**
   * Instantiates a new discount manager.
   */
  private DiscountManager() {}

  /**
   * Gets the single instance of DiscountManager.
   *
   * @return single instance of DiscountManager
   */
  public static DiscountManager getInstance() {
    if (instance == null) {
      instance = new DiscountManager();
    }

    return instance;
  }

  /**
   * Creates the discount.
   *
   * @param discountName the discount name
   * @param shop the shop
   * @param value the value
   * @param startDate the start date
   * @param day the day
   * @return the discount
   */
  public Discount createDiscount(
    String discountName,
    Shop shop,
    double value,
    String startDate,
    int day
  ) {
    try {
      String endDate = Util.sdf.format(
        DateUtils.addDays(Util.sdf.parse(startDate), day)
      );

      Discount discount = new Discount(
        discountName,
        shop,
        value,
        startDate,
        endDate
      );
      this.updateDiscount(discount);

      return discount;
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Update discount.
   *
   * @param discount the discount
   */
  public void updateDiscount(Discount discount) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("discountName", discount.getDiscountName());
    jsonObject.put("shop", discount.getShop().getShopName());
    jsonObject.put("startDate", Util.sdf.format(discount.getStartDate()));
    jsonObject.put("endDate", Util.sdf.format(discount.getEndDate()));
    jsonObject.put("value", discount.getValue());

    jsonFileManager.modifyJSON(
      "Discount",
      discount.getDiscountName(),
      jsonObject
    );
  }

  /**
   * Delete discount.
   *
   * @param discount the discount
   */
  public void deleteDiscount(Discount discount) {
    jsonFileManager.deleteJSON("Discount", discount.getDiscountName());
  }

  /**
   * Gets the discount.
   *
   * @param discountName the discount name
   * @return the discount
   */
  public Discount getDiscount(String discountName) {
    JSONObject discountJson = jsonFileManager.searchJSON(discountName);

    if (discountJson == null) {
      return null;
    } else {
      return extractDiscountFromJson(discountJson);
    }
  }

  /**
   * Extract discount from json.
   *
   * @param discountJson the discount json
   * @return the discount
   */
  public Discount extractDiscountFromJson(JSONObject discountJson) {
    ShopManager shopManager = ShopManager.getInstance();

    String discountName = discountJson.getString("discountName");
    Shop shop = shopManager.getShop(discountJson.getString("shop"));
    String startDate = discountJson.getString("startDate");
    String endDate = discountJson.getString("endDate");
    double value = discountJson.getDouble("value");

    return new Discount(discountName, shop, value, startDate, endDate);
  }

  /**
   * Json to string.
   *
   * @param jsonObject the json object
   * @return the string
   */
  public String jsonToString(JSONObject jsonObject) {
    String discountName = jsonObject.getString("discountName");
    String shopName = jsonObject.getString("shop");
    String startDate = jsonObject.getString("startDate");
    String endDate = jsonObject.getString("endDate");
    double value = jsonObject.getDouble("value");

    return (
      "{\"discountName\":\"" +
      discountName +
      "\", \"shop\":\"" +
      shopName +
      "\", \"startDate\":\"" +
      startDate +
      "\", \"endDate\":\"" +
      endDate +
      "\", \"value\":" +
      value +
      "}"
    );
  }
}
