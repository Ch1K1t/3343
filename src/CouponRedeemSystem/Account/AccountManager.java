package CouponRedeemSystem.Account;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import CouponRedeemSystem.System.Password.PasswordManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.LazyDynaBean;

public class AccountManager {

  private static AccountManager instance;
  private CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
  private PasswordManager passwordManager = PasswordManager.getInstance();

  private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

  private AccountManager() {}

  public static AccountManager getInstance() {
    if (instance == null) {
      instance = new AccountManager();
    }
    return instance;
  }

  public boolean createPassword(String userName, String Password) {
    JSONObject jsonObject = jsonFileManager.searchJSON(userName);
    if (jsonObject != null) {
      return false;
    }

    return passwordManager.createNewPassword(userName, Password);
  }

  // Create new user account
  public Account createAccount(
    String userName,
    String role,
    int age,
    String telNo,
    String dob
  ) {
    Account account = new Account(userName, role, age, telNo, dob);

    LazyDynaBean bean = new LazyDynaBean();
    bean.set("userName", account.getUserName());
    bean.set("role", account.getRole());
    bean.set("age", account.getAge());
    bean.set("telNo", account.getTelNo());
    bean.set("points", account.getPoints());
    bean.set("dateOfBirth", sdf.format(account.getDateOfBirth()));
    bean.set("couponIDs", account.getCouponIDs());

    boolean isSuccess = jsonFileManager.modifyJSON("Account", userName, bean);
    if (isSuccess) {
      return account;
    } else {
      return null;
    }
  }

  // Create new non-user account
  public Account createAccount(String userName, String role) {
    Account account = new Account(userName, role);

    LazyDynaBean bean = new LazyDynaBean();
    bean.set("userName", account.getUserName());
    bean.set("role", account.getRole());

    boolean isSuccess = jsonFileManager.modifyJSON("Account", userName, bean);
    if (isSuccess) {
      return account;
    } else {
      return null;
    }
  }

  public boolean deleteAccount(Account account) {
    return jsonFileManager.deleteJSON("Account", account.getUserName());
  }

  public boolean updateAccount(Account account) {
    String role = account.getRole();

    LazyDynaBean bean = new LazyDynaBean();
    bean.set("userName", account.getUserName());
    bean.set("role", role);
    if (role.equals("User")) {
      bean.set("age", account.getAge());
      bean.set("telNo", account.getTelNo());
      bean.set("points", account.getPoints());
      bean.set("dateOfBirth", sdf.format(account.getDateOfBirth()));
      bean.set("couponIDs", account.getCouponIDs());
    }

    return jsonFileManager.modifyJSON("Account", account.getUserName(), bean);
  }

  public Account getAccount(String userName) {
    JSONObject accountJson = jsonFileManager.searchJSON(userName);

    if (accountJson == null) {
      return null;
    } else {
      return extractAccountFromJson(accountJson);
    }
  }

  private Account extractAccountFromJson(JSONObject accountJson) {
    try {
      String userName = accountJson.getString("userName");
      String role = accountJson.getString("role");
      if (!role.equals("User")) {
        return new Account(userName, role);
      }

      int age = accountJson.getInt("age");
      String telNo = accountJson.getString("telNo");
      double points = accountJson.getDouble("points");
      Date dateOfBirth = sdf.parse(accountJson.getString("dateOfBirth"));
      JSONArray couponIDsArray = accountJson.getJSONArray("couponIDs");
      List<String> couponIDs = new ArrayList<>();
      for (int i = 0; i < couponIDsArray.size(); i++) {
        couponIDs.add(couponIDsArray.getString(i));
      }

      return new Account(
        userName,
        role,
        age,
        telNo,
        dateOfBirth,
        points,
        couponIDs
      );
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void generateDemoAccount() {
    boolean isCreated;
    isCreated = createPassword("admin", "admin");
    if (isCreated) {
      createAccount("admin", "Admin");
    }
    isCreated = createPassword("shop", "shop");
    if (isCreated) {
      createAccount("shop", "Shop Manager");
    }
    isCreated = createPassword("staff1", "staff1");
    if (isCreated) {
      createAccount("staff1", "Staff");
    }
    isCreated = createPassword("staff2", "staff2");
    if (isCreated) {
      createAccount("staff2", "Staff");
    }
    isCreated = createPassword("user", "user");
    if (isCreated) {
      createAccount("user", "User", 20, "12345678", "01/01/2000");
    }
  }
}
