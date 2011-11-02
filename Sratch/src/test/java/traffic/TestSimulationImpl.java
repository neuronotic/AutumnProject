package traffic;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestSimulationImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final RoadNetwork roadNetwork = context.mock(RoadNetwork.class);
	private final VehicleManager vehicleManager = context.mock(VehicleManager.class);
	private final Simulation simulation = new SimulationImpl(roadNetwork, vehicleManager);

	@Test
	public void addVehicleCallsAddVehicleOnVehicleManager() throws Exception {
		final Vehicle vehicle = context.mock(Vehicle.class);

		context.checking(new Expectations() {
			{
				oneOf(vehicleManager).addVehicle(vehicle);
			}
		});

		simulation.addVehicle(vehicle);
	}

	@Test
	public void stepCallsVehicleManagerStepWithEquivilantNumberOfTimesteps() throws Exception {
		final int timesteps = 10;

		context.checking(new Expectations() {
			{
				oneOf(vehicleManager).step(timesteps);
			}
		});

		simulation.step(timesteps);
	}
}
