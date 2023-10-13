package CouponRedeemSystem.System.Password;

import java.io.File;
import java.io.IOException;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.System.File.FileManager;
import CouponRedeemSystem.System.File.model.TextFile;
import CouponRedeemSystem.System.Password.model.Password;

public class PasswordManager {
	private PasswordManager instance;
	
	private PasswordManager() {}
	
	public PasswordManager getInstance() {
		if (instance == null) {
			instance = new PasswordManager();
		}
		return instance;
	}
	
	public void addNewPassword(Password password) throws IOException {
		FileManager mgr = FileManager.getInstance();
		String userIdStr = String.valueOf(password.getUserId());
		TextFile textFile = new TextFile(userIdStr, password.toString());
		mgr.modifyFile("Password", textFile);
	}
	
	public void deletePassword(Account account) throws IOException {
		FileManager mgr = FileManager.getInstance();
		String userIdStr = String.valueOf(account.getUserId());
		File userPasswordFile = mgr.searchFile("Password", userIdStr);
		mgr.deleteFile("Password", userPasswordFile);
	}
}
