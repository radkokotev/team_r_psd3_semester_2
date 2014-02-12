package data;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import data.DataInterface;

public class Activator implements BundleActivator  {
	
	private Data data;
	private ServiceRegistration<DataInterface> dataRegistration;

  public void start(BundleContext context) throws Exception {
	 data = Data.getSingleton();
	 
	 dataRegistration = context.registerService(DataInterface.class, data, null);
  }
  
  public void stop(BundleContext context) throws Exception {
    dataRegistration.unregister();
  }

}
