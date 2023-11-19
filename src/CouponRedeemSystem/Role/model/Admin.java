package CouponRedeemSystem.Role.model;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;

public class Admin extends Role{

    @Override
    public void couponToPoints(String couponId, Account account) {
        // TODO Auto-generated method stub
        System.out.println("Admin cannot convert coupon to points.");
    }

    public void deleteAccount(String userName, String Password) {
        AccountManager accountManager = AccountManager.getInstance();
        accountManager.delete(userName);
    }
}
