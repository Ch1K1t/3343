package CouponRedeemSystem.Account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.LazyDynaBean;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.System.File.CRSJsonFileManager;

public class AccountManager {
	private AccountManager instance;
    private CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

	private AccountManager() {}
	
	public AccountManager getInstance() {
		if (instance == null) {
			instance = new AccountManager();
		}
		return instance;
	}
	
	public List<Coupon> list(){
		//TODO
		return new ArrayList<Coupon>();
	}
	
	public void create(Account account) {
		LazyDynaBean bean = new LazyDynaBean();
		bean.set("userName", account.getUserName());
		bean.set("point", 0);
		bean.set("dateOfBirth", account.getDateOfBirth());
		bean.set("userOwnsCoupon", "");
        try {
            jsonFileManager.modifyJSON(null, null, bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	/*
	public void modifyAccountInfo(Account account) {
		try {
			String userName = account.getUserName();
			double newPoints = account.getPoints();
	
			CRSJsonFileManager.getInstance().searchFile(userName);

			LazyDynaBean bean = CRSJsonFileManager.("userName", userName);
			if (bean != null) {
				bean.set("point", newPoints);
				jsonFileManager.modifyJSON("userName", userName, bean);
			} else {
				System.out.println("Account not found"); // Handle the case where the account is not found
			}
		} catch (IOException e) {
			e.printStackTrace();
			// Handle the exception appropriately
		}
	}
	 */
	public void delete() {
		//TODO
	}
}
