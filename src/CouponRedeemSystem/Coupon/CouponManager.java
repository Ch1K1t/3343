package CouponRedeemSystem.Coupon;

import java.io.IOException;

import org.apache.commons.beanutils.LazyDynaBean;

import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.System.File.CRSJsonFileManager;

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
        bean.set("expiration_date", coupon.getExpirationDate());
        bean.set("owner", coupon.getOwner().getUserId());
        bean.set("shop", coupon.getShop());
        try {
            jsonFileManager.modifyJSON(null, coupon.getCouponCode(), bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void delete(Coupon coupon) {
        try {
            jsonFileManager.deleteJSON(null, coupon.getCouponCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
