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

  public Discount createDiscount(
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
      this.updateDiscount(discount);

      return discount;
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

  public String jsonToString(JSONObject jsonObject) {
    /*
     *  private String discountName;
  private Shop shop;
  private Date startDate;
  private Date expireDate;
  private double value;
     */
    String discountName = jsonObject.getString("discountName");
    String shopName = jsonObject.getString("shop");
    String startDate = jsonObject.getString("startDate");
    String expireDate = jsonObject.getString("expireDate");
    double value = jsonObject.getDouble("value");

    return (
      "{\"discountName\":\"" +
      discountName +
      "\", \"shop\":\"" +
      shopName +
      "\", \"startDate\":\"" +
      startDate +
      "\", \"expireDate\":\"" +
      expireDate +
      "\", \"value\":" +
      value +
      "}"
    );
  }

  public void generateDemoDiscount() {
    ShopManager shopManager = ShopManager.getInstance();
    Shop shop1 = shopManager.getShop("shop1");
    createDiscount("discount1", shop1, 2, "22/11/2023", 10);
    Shop shop2 = shopManager.getShop("shop2");
    createDiscount("discount2", shop2, 2, "22/11/2023", 5);
  }
}
