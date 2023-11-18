package CouponRedeemSystem.Account;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Role.model.Role;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import CouponRedeemSystem.System.Password.PasswordManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.LazyDynaBean;

public class AccountManager {

    private static AccountManager instance;
    CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
    PasswordManager passwordManager = PasswordManager.getInstance();

    private AccountManager() {
    }

    public static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    public void createAccInfo(Account account) {
        LazyDynaBean bean = new LazyDynaBean();
        bean.set("userName", account.getUserName());
        bean.set("role", account.getRole());
        bean.set("age", account.getAge());
        bean.set("telNo", account.getTelNo());
        bean.set("points", account.getPoints());
        bean.set("dateOfBirth", account.getDateOfBirth().toString());
        bean.set("couponIDs", account.getCouponIDs());

        try {
            jsonFileManager.modifyJSON("Account", account.getUserName(), bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

       public void createAccInfo(String userName, String role, int age, int telNo, String dob) throws ParseException {
        Account acc = new Account(userName, role, age, telNo, dob);
        LazyDynaBean bean = new LazyDynaBean();
        bean.set("userName", acc.getUserName());
        bean.set("role", acc.getRole());
        bean.set("age", acc.getAge());
        bean.set("telNo", acc.getTelNo());
        bean.set("points", acc.getPoints());
        bean.set("dateOfBirth", acc.getDateOfBirth());
        bean.set("couponIDs", acc.getCouponIDs());

        try {
            jsonFileManager.modifyJSON("Account", userName, bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete existing account
    public void delete(Account account) {
        try {
            jsonFileManager.deleteJSON("Account", account.getUserName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update existing account
    public void update(Account account) {
        // Delete the original JSON file
        delete(account);

        // Save the updated account details
        createAccInfo(account);
    }
    
    public Account getAccount(String userName) throws IOException, ParseException {
        // Search for the JSON file
        JSONObject accountJson = jsonFileManager.searchJSON(userName + ".json", null);

        // Extract account details from JSON and return the Account object
        if (!accountJson.isEmpty()) {
            return extractAccountFromJson(accountJson);
        }

        // Return null if the account was not found
        return null;
    }

    private Account extractAccountFromJson(JSONObject accountJson) throws ParseException {
        String userName = accountJson.getString("userName");
        String role = accountJson.getString("role");
        int age = accountJson.getInt("age");
        int telNo = accountJson.getInt("telNo");
        double points = accountJson.getDouble("points");
        String dateOfBirth = accountJson.getString("dateOfBirth");
        JSONArray couponIDsArray = accountJson.getJSONArray("couponIDs");
        List<String> couponIDs = new ArrayList<>();
        for (int i = 0; i < couponIDsArray.size(); i++) {
            couponIDs.add(couponIDsArray.getString(i));
        }

        return new Account(userName, role, age, telNo, points, dateOfBirth, couponIDs);
    }

    public void createAccount(String userName, String Password) throws IOException{
        passwordManager.createNewPassword(userName, Password);
    }
}
