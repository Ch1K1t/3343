package CouponRedeemSystem.Account;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.LazyDynaBean;

public class AccountManager {

    private static AccountManager instance;
    CRSJsonFileManager JsonFileManager = CRSJsonFileManager.getInstance();

    private AccountManager() {
    }

    public static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    public void create(Account account) {
        LazyDynaBean bean = new LazyDynaBean();
        bean.set("userName", account.getUserName());
        bean.set("age", account.getAge());
        bean.set("telNo", account.getTelNo());
        bean.set("points", account.getPoints());
        bean.set("dateOfBirth", account.getDateOfBirth().toString());
        bean.set("couponIDs", account.getCouponIDs());

        try {
            JsonFileManager.modifyJSON("Account", account.getUserName(), bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete existing account
    public void delete(Account account) {
        try {
            JsonFileManager.deleteJSON("Account", account.getUserName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update existing account
    public void update(Account account) {
        // Delete the original JSON file
        delete(account);

        // Save the updated account details
        create(account);
    }
    
}
