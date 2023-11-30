package CouponRedeemSystem.Discount;

import CouponRedeemSystem.Discount.model.Discount;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import CouponRedeemSystem.System.Util.Util;
import java.text.ParseException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;

public class DiscountManager {

  private static DiscountManager instance;

  private final CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

  private DiscountManager() {}

  public static DiscountManager getInstance() {
    if (instance == null) {
      instance = new DiscountManager();
    }

    return instance;
  }

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

  public void deleteDiscount(Discount discount) {
    jsonFileManager.deleteJSON("Discount", discount.getDiscountName());
  }

  public Discount getDiscount(String discountName) {
    JSONObject discountJson = jsonFileManager.searchJSON(discountName);

    if (discountJson == null) {
      return null;
    } else {
      return extractDiscountFromJson(discountJson);
    }
  }

  public Discount extractDiscountFromJson(JSONObject discountJson) {
    ShopManager shopManager = ShopManager.getInstance();

    String discountName = discountJson.getString("discountName");
    Shop shop = shopManager.getShop(discountJson.getString("shop"));
    String startDate = discountJson.getString("startDate");
    String endDate = discountJson.getString("endDate");
    double value = discountJson.getDouble("value");

    return new Discount(discountName, shop, value, startDate, endDate);
  }

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
