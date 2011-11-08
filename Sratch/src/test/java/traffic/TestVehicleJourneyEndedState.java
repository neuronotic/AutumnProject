package traffic;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestVehicleJourneyEndedState {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final VehicleStateContext stateContext = context.mock(VehicleStateContext.class);
	private final Vehicle vehicle = context.mock(Vehicle.class);
	private final VehicleJourneyState vehicleJourneyEndedState = new VehicleJourneyEndedState();

	@Test
	public void stepCausesCallToJourneyEnded() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(stateContext).journeyEnded(vehicle);
			}
		});

		vehicleJourneyEndedState.step(vehicle, stateContext);
	}

}
