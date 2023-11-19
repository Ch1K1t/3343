package CouponRedeemSystem.Role.model;

import java.io.IOException;
import java.text.ParseException;

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
        Account account = null;

        try {
            account = accountManager.getAccount(userName);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        accountManager.delete(account);
    }

    public void deleteCoupon
    
}
