package CouponRedeemSystem.Coupon;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.PurchasableCoupon;
import CouponRedeemSystem.Coupon.model.RedeemableCoupon;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.text.SimpleDateFormat;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.LazyDynaBean;

public class CouponManager {

  private static CouponManager instance;
  private CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
  private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

  private CouponManager() {}

  public static CouponManager getInstance() {
    if (instance == null) {
      instance = new CouponManager();
    }
    return instance;
  }

  // Create new purchasable coupon
  public Coupon createCoupon(
    String couponCode,
    double intrinsicValue,
    String expirationDate,
    Shop shop,
    Double points,
    String type
  ) {
    JSONObject json = jsonFileManager.searchJSON(couponCode);
    if (json != null) {
      return null;
    }

    Coupon coupon = new PurchasableCoupon(
      couponCode,
      intrinsicValue,
      points,
      shop,
      null,
      true,
      type,
      expirationDate
    );

    LazyDynaBean bean = new LazyDynaBean();
    bean.set("couponCode", coupon.getCouponCode());
    bean.set("intrinsicValue", coupon.getIntrinsicValue());
    bean.set("points", coupon.getPoints());
    bean.set("shop", coupon.getShop().getShopName());
    bean.set("owner", null);
    bean.set("active", coupon.isActive());
    bean.set("type", coupon.getType());
    bean.set("expirationDate", sdf.format(coupon.getExpirationDate()));

    boolean isSuccess = jsonFileManager.modifyJSON(
      "Coupon/" + type,
      couponCode,
      bean
    );

    if (isSuccess) {
      return coupon;
    } else {
      return null;
    }
  }

  // Create new redeemable coupon
  public Coupon createCoupon(
    String couponCode,
    double intrinsicValue,
    String expirationDate,
    String type
  ) {
    JSONObject json = jsonFileManager.searchJSON(couponCode);
    if (json != null) {
      return null;
    }

    Coupon coupon = new RedeemableCoupon(
      couponCode,
      intrinsicValue,
      true,
      type,
      expirationDate
    );

    LazyDynaBean bean = new LazyDynaBean();
    bean.set("couponCode", coupon.getCouponCode());
    bean.set("intrinsicValue", coupon.getIntrinsicValue());
    bean.set("active", coupon.isActive());
    bean.set("type", coupon.getType());
    bean.set("expirationDate", sdf.format(coupon.getExpirationDate()));

    boolean isSuccess = jsonFileManager.modifyJSON(
      "Coupon/" + type,
      couponCode,
      bean
    );

    if (isSuccess) {
      return coupon;
    } else {
      return null;
    }
  }

  public boolean deleteCoupon(Coupon coupon) {
    return jsonFileManager.deleteJSON(
      "Coupon/" + coupon.getType(),
      coupon.getCouponCode()
    );
  }

  public boolean updateCoupon(Coupon coupon) {
    String type = coupon.getType();

    LazyDynaBean bean = new LazyDynaBean();
    if (type.equals("Purchasable")) {
      bean.set("couponCode", coupon.getCouponCode());
      bean.set("intrinsicValue", coupon.getIntrinsicValue());
      bean.set("points", coupon.getPoints());
      bean.set("shop", coupon.getShop().getShopName());
      bean.set("owner", null);
      bean.set("active", coupon.isActive());
      bean.set("type", coupon.getType());
      bean.set("expirationDate", sdf.format(coupon.getExpirationDate()));
    } else {
      bean.set("couponCode", coupon.getCouponCode());
      bean.set("intrinsicValue", coupon.getIntrinsicValue());
      bean.set("active", coupon.isActive());
      bean.set("type", coupon.getType());
      bean.set("expirationDate", sdf.format(coupon.getExpirationDate()));
    }

    return jsonFileManager.modifyJSON(
      "Coupon/" + type,
      coupon.getCouponCode(),
      bean
    );
  }

  public Coupon getCoupon(String couponCode) {
    JSONObject couponJson = jsonFileManager.searchJSON(couponCode);

    if (couponJson == null) {
      return null;
    } else {
      return extractCouponFromJson(couponJson);
    }
  }

  private Coupon extractCouponFromJson(JSONObject couponJson) {
    double intrinsicValue = couponJson.getDouble("intrinsicValue");
    boolean active = couponJson.getBoolean("active");
    String couponCode = couponJson.getString("couponCode");
    String expirationDate = couponJson.getString("expirationDate");
    String type = couponJson.getString("type");
    switch (type) {
      case "Redeemable":
        return new RedeemableCoupon(
          couponCode,
          intrinsicValue,
          active,
          type,
          expirationDate
        );
      case "Purchasable":
        ShopManager shopManager = ShopManager.getInstance();
        Shop shop = shopManager.getShop(couponJson.getString("shop"));
        AccountManager accountManager = AccountManager.getInstance();
        Account owner = accountManager.getAccount(
          couponJson.getString("owner")
        );
        double points = couponJson.getDouble("points");

        return new PurchasableCoupon(
          couponCode,
          intrinsicValue,
          points,
          shop,
          owner,
          active,
          type,
          expirationDate
        );
      default:
        return null;
    }
  }

  public void generateDemoCoupon() {
    ShopManager shopManager = ShopManager.getInstance();
    Shop shop1 = shopManager.getShop("shop1");
    createCoupon("P1", 10.0, "11/11/2025", shop1, 15.0, "Purchasable");
    Shop shop2 = shopManager.getShop("shop2");
    createCoupon("P2", 10.0, "11/11/2025", shop2, 15.0, "Purchasable");
    createCoupon("R1", 1.0, "11/11/2025", "Redeemable");
    createCoupon("R2", 1.0, "11/11/2025", "Redeemable");
  }
}
