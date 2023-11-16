package CouponRedeemSystem.Main;
import CouponRedeemSystem.Page.HomePage;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.beanutils.LazyDynaBean;

public class Main {
  public static void main(String[] args) throws IOException {
	CRSJsonFileManager mgr = CRSJsonFileManager.getInstance();
	LazyDynaBean bean = new LazyDynaBean();
	bean.set("Tony", "Good");
	bean.set("Number", 1);
	bean.set("3343", false);
	mgr.modifyJSON("Test01", "Bean Test", bean);
	System.out.print(bean.toString());
	
	JSONObject jsonObject = new JSONObject();
	jsonObject.put("Apple user", "Hendry");
	JSONObject feature = new JSONObject();
	feature.putAll(new HashMap<>());
    new HomePage().execute();
  }
}
