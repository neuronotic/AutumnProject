package traffic;

import com.google.inject.AbstractModule;


public class TrafficModuleProduction extends AbstractModule {

	@Override
	public void configure() {
		bind(VehicleUpdateOrdering.class).to(VehicleUpdateOrderingShuffle.class);
	}

}
