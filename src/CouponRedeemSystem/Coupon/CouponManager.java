package CouponRedeemSystem.Coupon;
import java.util.Date;

import CouponRedeemSystem.Coupon.model.Coupon;

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
        Date currentDate = new Date();

        if (coupon.isExchanged()) return;
        if (coupon.getExpirationDate().compareTo(currentDate) > 0) return;
        // TODO: check if coupon code is invalid

        double points = 0;

        //TODO: implement point conversion algorithm

        double newPoints = coupon.getOwner().getPoints() + points;
        coupon.getOwner().setPoints(newPoints);
        coupon.setExchanged(true);
    }
}
