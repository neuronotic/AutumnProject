package traffic.endtoend;

import traffic.VehicleUpdateOrdering;
import traffic.VehicleUpdateOrderingUnmodified;

import com.google.inject.AbstractModule;

public class TrafficModuleTestSpecific extends AbstractModule {
	@Override
	protected void configure() {
		bind(VehicleUpdateOrdering.class).to(VehicleUpdateOrderingUnmodified.class);
	}
}
