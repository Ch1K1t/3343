package CouponRedeemSystem.System.Password;

import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.File;
import java.io.IOException;
import net.sf.json.JSONObject;

public class PasswordManager {

  private static PasswordManager instance;

  private EncryptionManager mgr = EncryptionManager.getInstance();

  private PasswordManager() {}

  public static PasswordManager getInstance() {
    if (instance == null) {
      instance = new PasswordManager();
    }

    return instance;
  }

  public String getPasswordRefTablePath() {
    return "Password\\Reference Table.json";
  }

  private JSONObject getPasswordRefTable() throws IOException {
    CRSJsonFileManager mgr = CRSJsonFileManager.getInstance();
    File file = mgr.searchFile("Reference Table.json");
    if (file == null) {
      file = mgr.createJson("Password", "Reference Table");
    }
    return mgr.convertFileTextToJSON(file);
  }

  public void createNewPassword(String userName, String password)
    throws IOException {
    JSONObject jsonObject = getPasswordRefTable();
    String encryptedPassword = mgr.encryption(password);
    jsonObject.put(userName, encryptedPassword);

    CRSJsonFileManager
      .getInstance()
      .modifyJSON("Password", "Reference Table", jsonObject);
  }

  public boolean checkPasswordValid(String userName, String password)
    throws IOException {
    JSONObject jsonObject = getPasswordRefTable();
    String textBeforeEncrypt = (String) jsonObject.get(userName);
    String text = mgr.decryption(textBeforeEncrypt);
    if (text.equals(password)) {
      return true;
    } else {
      System.out.println("Password is not found!");
      return false;
    }
  }
}
