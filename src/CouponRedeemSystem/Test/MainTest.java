package CouponRedeemSystem.Test;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Discount.DiscountManager;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import CouponRedeemSystem.System.Password.PasswordManager;
import java.text.SimpleDateFormat;

public abstract class MainTest {

  protected final PasswordManager passwordManager = PasswordManager.getInstance();
  protected final CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
  protected final AccountManager accountManager = AccountManager.getInstance();
  protected final CouponManager couponManager = CouponManager.getInstance();
  protected final ShopManager shopManager = ShopManager.getInstance();
  protected final DiscountManager discountManager = DiscountManager.getInstance();
  protected final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
}
