package CouponRedeemSystem.Promotion;

import CouponRedeemSystem.Promotion.model.Promotion;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import CouponRedeemSystem.System.Util.Util;
import java.text.ParseException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class PromotionManager.
 */
public class PromotionManager {

  /** The instance. */
  private static PromotionManager instance;

  /** The json file manager. */
  private final CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

  /**
   * Instantiates a new promotion manager.
   */
  private PromotionManager() {}

  /**
   * Gets the single instance of PromotionManager.
   *
   * @return single instance of PromotionManager
   */
  public static PromotionManager getInstance() {
    if (instance == null) {
      instance = new PromotionManager();
    }

    return instance;
  }

  /**
   * Creates the promotion.
   *
   * @param promotionName the promotion name
   * @param shop the shop
   * @param value the value
   * @param startDate the start date
   * @param day the day
   * @return the promotion
   */
  public Promotion createPromotion(
    String promotionName,
    Shop shop,
    double value,
    String startDate,
    int day
  ) {
    try {
      String endDate = Util.sdf.format(
        DateUtils.addDays(Util.sdf.parse(startDate), day)
      );

      Promotion promotion = new Promotion(
        promotionName,
        shop,
        value,
        startDate,
        endDate
      );
      this.updatePromotion(promotion);

      return promotion;
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Update promotion.
   *
   * @param promotion the promotion
   */
  public void updatePromotion(Promotion promotion) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("promotionName", promotion.getPromotionName());
    jsonObject.put("shop", promotion.getShop().getShopName());
    jsonObject.put("startDate", Util.sdf.format(promotion.getStartDate()));
    jsonObject.put("endDate", Util.sdf.format(promotion.getEndDate()));
    jsonObject.put("value", promotion.getValue());

    jsonFileManager.modifyJSON(
      "Promotion",
      promotion.getPromotionName(),
      jsonObject
    );
  }

  /**
   * Delete promotion.
   *
   * @param promotion the promotion
   */
  public void deletePromotion(Promotion promotion) {
    jsonFileManager.deleteJSON("Promotion", promotion.getPromotionName());
  }

  /**
   * Gets the promotion.
   *
   * @param promotionName the promotion name
   * @return the promotion
   */
  public Promotion getPromotion(String promotionName) {
    JSONObject promotionJson = jsonFileManager.searchJSON(promotionName);

    if (promotionJson == null) {
      return null;
    } else {
      return extractPromotionFromJson(promotionJson);
    }
  }

  /**
   * Extract promotion from json.
   *
   * @param promotionJson the promotion json
   * @return the promotion
   */
  public Promotion extractPromotionFromJson(JSONObject promotionJson) {
    ShopManager shopManager = ShopManager.getInstance();

    String promotionName = promotionJson.getString("promotionName");
    Shop shop = shopManager.getShop(promotionJson.getString("shop"));
    String startDate = promotionJson.getString("startDate");
    String endDate = promotionJson.getString("endDate");
    double value = promotionJson.getDouble("value");

    return new Promotion(promotionName, shop, value, startDate, endDate);
  }

  /**
   * Json to string.
   *
   * @param jsonObject the json object
   * @return the string
   */
  public String jsonToString(JSONObject jsonObject) {
    String promotionName = jsonObject.getString("promotionName");
    String shopName = jsonObject.getString("shop");
    String startDate = jsonObject.getString("startDate");
    String endDate = jsonObject.getString("endDate");
    double value = jsonObject.getDouble("value");

    return (
      "{\"promotionName\":\"" +
      promotionName +
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
