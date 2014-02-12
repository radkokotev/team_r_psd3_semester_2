package roles;

import java.util.Date;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import data.DataInterface;

public class Activator implements BundleActivator  {
	
	User user;
	data.DataInterface data;

  public void start(BundleContext context) throws Exception {
	  System.out.println("User started");
	  user = new User(true, true, true, true);
	  ServiceReference<DataInterface> serviceReference = context.getServiceReference(DataInterface.class);
	  data = context.getService(serviceReference);
	  user.setData(data);
	  
	  user.createTimeSlotForSession(new Date(), new Date(),"PSD", "PSD Lab");
	  System.out.println(user.getInformationForSession("PSD Lab"));
  }
  
  public void stop(BundleContext context) throws Exception {
    System.out.println("Goodbye world!");
  }

}
