package CouponRedeemSystem.Coupon;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import net.sf.json.JSONObject;

public class CouponManager {
    private CouponManager instance;
	
	private CouponManager() {}
	
	public CouponManager getInstance() {
		if (instance == null) {
			instance = new CouponManager();
		}
		return instance;
	}

    public void couponToPoints(Coupon coupon) {
        CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

        Date currentDate = new Date();

        if (coupon.isExchanged()) return;
        if (coupon.getExpirationDate().after(currentDate)) return;
        // TODO: check if coupon code is invalid
        JSONObject json;
        try {
            json = jsonFileManager.searchJSON("coupons.json");
        } catch (IOException e) {
            System.out.println("Coupon database not found");
            return;
        }

        double points = 0;

        //TODO: implement point conversion algorithm

        double newPoints = coupon.getOwner().getPoints() + points;
        coupon.getOwner().setPoints(newPoints);
        coupon.setExchanged(true);
    }
}
