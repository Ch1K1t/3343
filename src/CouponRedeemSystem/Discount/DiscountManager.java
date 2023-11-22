package CouponRedeemSystem.Discount;

import CouponRedeemSystem.Discount.model.Discount;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.time.DateUtils;

public class DiscountManager {

  private static DiscountManager instance;

  private final CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
  private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

  private DiscountManager() {}

  public static DiscountManager getInstance() {
    if (instance == null) {
      instance = new DiscountManager();
    }

    return instance;
  }

  public Discount createDiscountByDay(
    String discountName,
    Shop shop,
    double value,
    String startDate,
    int day
  ) {
    try {
      JSONObject jsonObject = jsonFileManager.searchJSON(discountName);
      if (jsonObject != null) {
        return null;
      }

      String expireDate = sdf.format(
        DateUtils.addDays(sdf.parse(startDate), day)
      );
      Discount discount = new Discount(
        discountName,
        shop,
        startDate,
        expireDate,
        value
      );

      LazyDynaBean bean = new LazyDynaBean();
      bean.set("discountName", discount.getDiscountName());
      bean.set("shop", discount.getShop().getShopName());
      bean.set("startDate", sdf.format(discount.getStartDate()));
      bean.set("expireDate", sdf.format(discount.getExpireDate()));
      bean.set("value", discount.getValue());
      bean.set("active", discount.validateTime());

      boolean isSuccess = jsonFileManager.modifyJSON(
        "Discount",
        discountName,
        bean
      );

      if (isSuccess) {
        return discount;
      } else {
        return null;
      }
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Discount createDiscountByMonth(
    String discountName,
    Shop shop,
    double value,
    String startDate,
    int month
  ) {
    try {
      JSONObject jsonObject = jsonFileManager.searchJSON(discountName);
      if (jsonObject != null) {
        return null;
      }

      String expireDate = sdf.format(
        DateUtils.addMonths(sdf.parse(startDate), month)
      );
      Discount discount = new Discount(
        discountName,
        shop,
        startDate,
        expireDate,
        value
      );

      LazyDynaBean bean = new LazyDynaBean();
      bean.set("discountName", discount.getDiscountName());
      bean.set("shop", discount.getShop().getShopName());
      bean.set("startDate", sdf.format(discount.getStartDate()));
      bean.set("expireDate", sdf.format(discount.getExpireDate()));
      bean.set("value", discount.getValue());
      bean.set("active", discount.validateTime());

      boolean isSuccess = jsonFileManager.modifyJSON(
        "Discount",
        discountName,
        bean
      );

      if (isSuccess) {
        return discount;
      } else {
        return null;
      }
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean deleteDiscount(Discount discount) {
    return jsonFileManager.deleteJSON("Discount", discount.getDiscountName());
  }

  public boolean updateDiscount(Discount discount) {
    LazyDynaBean bean = new LazyDynaBean();
    bean.set("discountName", discount.getDiscountName());
    bean.set("shop", discount.getShop().getShopName());
    bean.set("startDate", sdf.format(discount.getStartDate()));
    bean.set("expireDate", sdf.format(discount.getExpireDate()));
    bean.set("value", discount.getValue());
    bean.set("active", discount.validateTime());

    return jsonFileManager.modifyJSON(
      "Discount",
      discount.getDiscountName(),
      bean
    );
  }

  public Discount getDiscount(String discountName) {
    JSONObject discountJson = jsonFileManager.searchJSON(discountName);

    if (discountJson == null) {
      return null;
    } else {
      return extractDiscountFromJson(discountJson);
    }
  }

  private Discount extractDiscountFromJson(JSONObject discountJson) {
    ShopManager shopManager = ShopManager.getInstance();

    String discountName = discountJson.getString("discountName");
    Shop shop = shopManager.getShop(discountJson.getString("shop"));
    String startDate = discountJson.getString("startDate");
    String expireDate = discountJson.getString("expireDate");
    double value = discountJson.getDouble("value");

    return new Discount(discountName, shop, startDate, expireDate, value);
  }

  public void generateDemoDiscount() {
    ShopManager shopManager = ShopManager.getInstance();
    Shop shop1 = shopManager.getShop("shop1");
    createDiscountByDay("discount1", shop1, 2, "22/11/2023", 10);
    Shop shop2 = shopManager.getShop("shop2");
    createDiscountByMonth("discount2", shop2, 2, "22/11/2023", 1);
  }
}
