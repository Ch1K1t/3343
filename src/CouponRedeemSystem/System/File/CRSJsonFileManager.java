package CouponRedeemSystem.System.File;

public class CRSJsonFileManager {
	private CRSJsonFileManager instance;
	
	private CRSJsonFileManager() {}
	
	public CRSJsonFileManager getInstance() {
		if (instance == null) {
			instance = new CRSJsonFileManager();
		}
		return instance;
	}
	
	public String getDirectoryPath(String dirName) {
		return "Data\\" + dirName;
	}
	
	public void create() {}
	
	public void modify() {}
	
	public void delete() {}
	
	public void search() {}
}
