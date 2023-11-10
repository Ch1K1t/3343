package CouponRedeemSystem.Main;

import java.io.IOException;

import org.apache.commons.beanutils.LazyDynaBean;

import CouponRedeemSystem.System.File.CRSJsonFileManager;
import net.sf.json.JSONObject;

public class Main {

	public static void main(String[] args) throws IOException {
		CRSJsonFileManager mgr = CRSJsonFileManager.getInstance();
		LazyDynaBean bean = new LazyDynaBean();
		bean.set("Tony", "Good");
		bean.set("Number", 1);
		bean.set("3343", false);
		mgr.modifyJSON("Test", "Bean Test", bean);
		System.out.print(bean.toString());
	}
}
