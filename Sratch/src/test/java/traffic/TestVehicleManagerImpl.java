package traffic;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;


public class TestVehicleManagerImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final TimeKeeper timeKeeper = context.mock(TimeKeeper.class);
	private final Vehicle vehicle0 = context.mock(Vehicle.class, "vehicle0");
	private final VehicleManagerImpl vehicleManager = new VehicleManagerImpl(timeKeeper);

	@Test
	public void timeKeeperSteppedWithEachStep() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(timeKeeper).step();
			}
		});
		vehicleManager.step();

		context.checking(new Expectations() {
			{
				exactly(2).of(timeKeeper).step();
			}
		});
		vehicleManager.step(2);

	}

	@Test
	public void multipleAddedVehiclesAreSteppedByManager() throws Exception {
		final Vehicle vehicle1 = context.mock(Vehicle.class, "vehicle1");

		context.checking(new Expectations() {{
			ignoring(timeKeeper);
			oneOf(vehicle0).step();
			oneOf(vehicle1).step();
		}});

		vehicleManager.addVehicle(vehicle0);
		vehicleManager.addVehicle(vehicle1);

		vehicleManager.step();
	}


	@Test
	public void managerCanBeSteppedMultipleTimes() throws Exception {

		context.checking(new Expectations() {{
			ignoring(timeKeeper);
			oneOf(vehicle0).step();
			oneOf(vehicle0).step();
			oneOf(vehicle0).step();
		}});
		vehicleManager.addVehicle(vehicle0);
		vehicleManager.step(3);
	}
}
