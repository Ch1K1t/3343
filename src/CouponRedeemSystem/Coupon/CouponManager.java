package CouponRedeemSystem.Coupon;

import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.IOException;
import org.apache.commons.beanutils.LazyDynaBean;

public class CouponManager {

  private static CouponManager instance;
  private CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

  private CouponManager() {}

  public static CouponManager getInstance() {
    if (instance == null) {
      instance = new CouponManager();
    }
    return instance;
  }

  // Create json record
  public void create(Coupon coupon) {
    LazyDynaBean bean = new LazyDynaBean();
    bean.set("code", coupon.getCouponCode());
    bean.set("value", coupon.getIntrinsicValue());
    bean.set("expiration_date", coupon.getExpirationDate().toString());
    bean.set("owner", null);
    bean.set("shop", coupon.getShop());
    bean.set("active", coupon.isActive());
    try {
      jsonFileManager.modifyJSON("Coupon", coupon.getCouponCode(), bean);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void delete(Coupon coupon) {
    try {
      jsonFileManager.deleteJSON("Coupon", coupon.getCouponCode());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
