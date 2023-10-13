package CouponRedeemSystem.Account;

import java.util.ArrayList;
import java.util.List;

import CouponRedeemSystem.Account.model.Account;

public class AccountManager {
	private AccountManager instance;
	
	private AccountManager() {}
	
	public AccountManager getInstance() {
		if (instance == null) {
			instance = new AccountManager();
		}
		return instance;
	}
	
	public List<Account> list(){
		//TODO
		return new ArrayList<Account>();
	}
	
	public void create() {
		//TODO
	}
	
	public void modify() {
		//TODO
	}
	
	public void delete() {
		//TODO
	}
}
