package traffic;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestVehicleJourneyStartedState {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final VehicleStateFactory vehicleStateFactory = context.mock(VehicleStateFactory.class);
	private final VehicleJourneyState state0 = context.mock(VehicleJourneyState.class, "state0");
	private final VehicleJourneyState state1 = context.mock(VehicleJourneyState.class, "state1");
	private final VehicleStateContext stateContext = context.mock(VehicleStateContext.class);
	private final Vehicle vehicle = context.mock(Vehicle.class);
	final VehicleJourneyState vehicleJourneyStartedState = new VehicleJourneyStartedState(vehicleStateFactory);

	@Test
	public void ifContextIteratorIsExhaustedDelgatesStepToVehicleJourneyCompletedStateReturnedBysFactory() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(stateContext).hasNext(); will(returnValue(false));
				oneOf(vehicleStateFactory).postJourneyState(); will(returnValue(state0));
				oneOf(state0).step(vehicle, stateContext); will(returnValue(state1));
			}
		});

		vehicleJourneyStartedState.step(vehicle, stateContext);
	}
}
