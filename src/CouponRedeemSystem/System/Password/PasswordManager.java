package CouponRedeemSystem.System.Password;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import CouponRedeemSystem.System.File.CRSJsonFileManager;
import CouponRedeemSystem.System.ID.IdGenerator;
import net.sf.json.JSONObject;

public class PasswordManager {
	private static PasswordManager instance;
	
	private EncryptionManager mgr = EncryptionManager.getInstance();
	
	private PasswordManager() {
		
	}
	
	public static PasswordManager getInstance() {
		if (instance == null) {
			instance = new PasswordManager();
		}
		
		return instance;
	}
	
	public String getPasswordRefTablePath() { return "Password\\Referrence Table.json"; }
	
	private JSONObject getPasswordRefTable() throws IOException {
		CRSJsonFileManager mgr = CRSJsonFileManager.getInstance();
		File file = mgr.searchFile("Referrence Table.json");
		if (file == null) {
			file = mgr.createJson("Password", "Referrence Table");
		}
		return mgr.convertFileTextToJSON(file);
	}
	
	public void createNewPassword(String userName, String password, int id) throws IOException {
		JSONObject jsonObject = getPasswordRefTable();
		JSONObject userInfo = new JSONObject();
		byte[] encryptedPassword = mgr.encryption(password);
		userInfo.put(userName, encryptedPassword);
		jsonObject.put(id, userInfo);
		
		CRSJsonFileManager.getInstance().modifyJSON("Password", "Referrence Table", jsonObject);
	}
	
	public String checkPasswordValid(String userName, String password) throws IOException {
		JSONObject jsonObject = getPasswordRefTable();
		Set<String> keySet = jsonObject.keySet();
		
		for (String key: keySet) {
			JSONObject passwordObject = (JSONObject) jsonObject.get(key);
			byte[] textBeforeDecrypt = (byte[]) passwordObject.get(key);
			String text = mgr.decryption(textBeforeDecrypt);
			if (text.equals(password)) {
				return key;
			}
		}
		System.out.println("Password is not found!");
		return null;
	}
}
