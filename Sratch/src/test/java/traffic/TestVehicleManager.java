package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;


public class TestVehicleManager {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Vehicle vehicle0 = context.mock(Vehicle.class, "vehicle0");
	private final VehicleManagerImpl vehicleManager = new VehicleManagerImpl();

	@Test
	public void VehicleResponsibleForJourneyEndNotificationIsRemoved() throws Exception {
		context.checking(new Expectations() {
			{
				ignoring(vehicle0);
			}
		});
		vehicleManager.addVehicle(vehicle0);
		assertThat(vehicleManager.vehicles(), contains(vehicle0));
		vehicleManager.notifyOfJourneyEnd(vehicle0);
		assertThat(vehicleManager.vehicles(), not(contains(vehicle0)));
	}

	@Test
	public void addedVehiclesAreNotifiedOfVehicleManagerAsJourneyEndListener() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(vehicle0).addJourneyEndListener(vehicleManager);
			}
		});
		vehicleManager.addVehicle(vehicle0);
	}

	@Test
	public void multipleAddedVehiclesAreSteppedByManager() throws Exception {
		final Vehicle vehicle1 = context.mock(Vehicle.class, "vehicle1");

		context.checking(new Expectations() {{
			oneOf(vehicle0).addJourneyEndListener(vehicleManager);
			oneOf(vehicle1).addJourneyEndListener(vehicleManager);
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
			oneOf(vehicle0).addJourneyEndListener(vehicleManager);
			oneOf(vehicle0).step();
			oneOf(vehicle0).step();
			oneOf(vehicle0).step();
		}});
		vehicleManager.addVehicle(vehicle0);
		vehicleManager.step(3);
	}
}
