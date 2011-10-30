package traffic;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;


public class TestVehicleManager {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Vehicle vehicle = context.mock(Vehicle.class);
	private final VehicleManagerImpl vehicleManagerImpl = new VehicleManagerImpl();

	@Test
	public void addedVehicleIsSteppedByManager() throws Exception {
		vehicleManagerImpl.addVehicle(vehicle);

		context.checking(new Expectations() {{
			oneOf(vehicle).step();
		}});
		vehicleManagerImpl.step();
	}

	@Test
	public void managerCanBeSteppedMultipleTimes() throws Exception {
		vehicleManagerImpl.addVehicle(vehicle);

		context.checking(new Expectations() {{
			oneOf(vehicle).step();
			oneOf(vehicle).step();
			oneOf(vehicle).step();
		}});
		vehicleManagerImpl.step(3);
	}
}
