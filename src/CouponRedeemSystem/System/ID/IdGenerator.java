package CouponRedeemSystem.System.ID;

import java.io.File;
import java.io.IOException;

import CouponRedeemSystem.System.File.CRSJsonFileManager;
import net.sf.json.JSONObject;

public class IdGenerator {
	private static IdGenerator instance;
	
	private IdGenerator() {
		
	}
	
	public static IdGenerator getInstance() {
		if (instance == null) {
			instance = new IdGenerator();
		}
		return instance;
	}
	
	public String getIdJsonPath() { return "ID\\SysNextIdd.json"; }
	
	public int getNextId(String idName) throws IOException {
		CRSJsonFileManager mgr = CRSJsonFileManager.getInstance();
		
		File file = mgr.searchFile("SysNextId.json", null);
		
		if (file == null) {
			file = mgr.createJson("ID", "SysNextId");
		}
		
		JSONObject allIdJson = mgr.convertFileTextToJSON(file);
		int id;
		
		if (allIdJson.has(idName)) {
			id = allIdJson.getInt(idName);
		} else {
			id = 1;
		}
		
		allIdJson.put(idName, id+ 1);
		
		return id;
	}
}
