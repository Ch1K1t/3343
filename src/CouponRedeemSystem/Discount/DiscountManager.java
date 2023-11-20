package CouponRedeemSystem.Discount;

import CouponRedeemSystem.Discount.model.Discount;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;

public class DiscountManager {

  private static DiscountManager instance;

  private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  private DiscountManager() {}

  public static DiscountManager getInstance() {
    if (instance == null) {
      instance = new DiscountManager();
    }

    return instance;
  }

  public Discount createDiscountByDay(
    String discountName,
    Date startDate,
    int day,
    double valueOff
  ) {
    String startDateInSDF = sdf.format(startDate);
    String expireDate = sdf.format(DateUtils.addDays(startDate, day));
    Discount discount = new Discount(
      discountName,
      startDateInSDF,
      expireDate,
      valueOff
    );

    storeNewDiscount(discount);

    return discount;
  }

  public Discount createDiscountByMonth(
    String discountName,
    Date startDate,
    int month,
    double valueOff
  ) {
    String startDateInSDF = sdf.format(startDate);
    String expireDate = sdf.format(DateUtils.addMonths(startDate, month));
    Discount discount = new Discount(
      discountName,
      startDateInSDF,
      expireDate,
      valueOff
    );

    System.out.println(discount.getJSONString());

    storeNewDiscount(discount);

    return discount;
  }

  public void updateActiveDiscountList() {
    CRSJsonFileManager mgr = CRSJsonFileManager.getInstance();
    JSONObject discountJson = mgr.searchJSON("Discount List.json");
    JSONObject activeDiscountJsonObject = new JSONObject();
    for (Object keyObj : discountJson.keySet()) {
      String key = (String) keyObj;
      Discount discount = (Discount) JSONObject.toBean(
        discountJson.getJSONObject(key),
        Discount.class
      );
      boolean oldStatus = discount.getActive();
      discount.updateStatus();
      boolean newStatus = discount.getActive();
      if (oldStatus != newStatus) {
        modifyDiscount(key, discount);
      }
      if (newStatus) {
        activeDiscountJsonObject.put(key, discount.getJSONObject());
      }
    }
    mgr.modifyJSON(
      "Discount",
      "Active Discount List",
      activeDiscountJsonObject
    );
  }

  private void storeNewDiscount(Discount discount) {
    CRSJsonFileManager mgr = CRSJsonFileManager.getInstance();
    JSONObject discountJsonObject = new JSONObject();
    discountJsonObject.put(discount.getId(), discount.getJSONObject());
    mgr.modifyJSON("Discount", "Discount List", discountJsonObject);
  }

  private void modifyDiscount(String id, Discount discount) {
    CRSJsonFileManager mgr = CRSJsonFileManager.getInstance();
    JSONObject discountJson = mgr.searchJSON("Discount List.json");
    discountJson.put(id, discount.getJSONObject());
  }
}
