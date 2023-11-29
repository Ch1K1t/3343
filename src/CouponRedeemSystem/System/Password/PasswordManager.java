package CouponRedeemSystem.System.Password;

import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.File;
import net.sf.json.JSONObject;

public class PasswordManager {

  private static PasswordManager instance;

  private EncryptionManager encryptionManager = EncryptionManager.getInstance();
  private final CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

  private PasswordManager() {}

  public static PasswordManager getInstance() {
    if (instance == null) {
      instance = new PasswordManager();
    }

    return instance;
  }

  public JSONObject getPasswordRefTable() {
    JSONObject jsonObject = jsonFileManager.searchJSON("ReferenceTable");
    if (jsonObject != null) {
      return jsonObject;
    } else {
      File file = jsonFileManager.createJSONFile("Password", "ReferenceTable");
      return jsonFileManager.convertFileTextToJSON(file);
    }
  }

  public void createNewPassword(String userName, String password) {
    JSONObject jsonObject = getPasswordRefTable();

    String encryptedPassword = encryptionManager.encryption(password);
    jsonObject.put(userName, encryptedPassword);

    jsonFileManager.modifyJSON("Password", "ReferenceTable", jsonObject);
  }

  public void deletePassword(String userName) {
    JSONObject jsonObject = getPasswordRefTable();

    jsonObject.remove(userName);

    jsonFileManager.modifyJSON("Password", "ReferenceTable", jsonObject);
  }

  public String checkPasswordValid(String userName, String password) {
    JSONObject jsonObject = getPasswordRefTable();
    Object textBeforeEncrypt = jsonObject.get(userName);
    if (textBeforeEncrypt == null) {
      return "not found";
    }
    String text = encryptionManager.decryption(textBeforeEncrypt.toString());
    if (text.equals(password)) {
      return "success";
    } else {
      return "not correct";
    }
  }
}
