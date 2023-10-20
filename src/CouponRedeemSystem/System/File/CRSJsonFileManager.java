package CouponRedeemSystem.System.File;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtils;

import net.sf.json.JSONObject;

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
	
	public File create(String dirName, String fileName) throws IOException {
		String pathStr = getDirectoryPath(dirName);
		File dir = new File(pathStr);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File file = new File(pathStr + "\\" + fileName + ".json");
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}
	
	public void modifyJSONByDynaBean(String dirName, String fileName, DynaBean content) {

	}
	
	public void delete() {}
	
	public void search() {}
	
	public void list() {}
}
