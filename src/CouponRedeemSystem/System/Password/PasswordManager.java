package CouponRedeemSystem.System.Password;

import java.io.File;
import java.io.IOException;

import CouponRedeemSystem.System.File.CRSJsonFileManager;
import net.sf.json.JSONObject;

public class PasswordManager {
	private static PasswordManager instance;
	
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
			file = mgr.createJson("Password", "Reference Table");
		}
		return mgr.convertFileTextToJSON(file);
	}
	
	public void createNewAccount(String userName, String password, int id) throws IOException {
		JSONObject jsonObject = getPasswordRefTable();
		JSONObject userInfo = new JSONObject();
	}
}
