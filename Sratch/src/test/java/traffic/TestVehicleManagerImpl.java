package traffic;

import static java.util.Arrays.*;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.junit.Rule;
import org.junit.Test;


public class TestVehicleManagerImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Vehicle vehicle0 = context.mock(Vehicle.class, "vehicle0");
	private final Vehicle vehicle1 = context.mock(Vehicle.class, "vehicle1");
	private final JourneyEndedMessage journeyEndedMessage = context.mock(JourneyEndedMessage.class);
	private final JourneyStartedMessage journeyStartedMessage = context.mock(JourneyStartedMessage.class);
	private final VehicleUpdateOrdering vehicleUpdateOrdering = context.mock(VehicleUpdateOrdering.class);
	private final VehicleManager vehicleManager = new VehicleManagerImpl(vehicleUpdateOrdering);
	//TODO: ought i to test if the @Subscribe annotations are in place for the message receiving classes?


	@Test
	public void stepCallsStepOnVehiclesInOrderDefinedByVehicleUpdateOrderingForVehiclesForWhichAJourneyStartedMessageHasBeenReceived() throws Exception {
		final Sequence stepOrdering = context.sequence("stepOrdering");
		receiveJourneyStartedMessageFor(vehicle0);
		receiveJourneyStartedMessageFor(vehicle1);
		context.checking(new Expectations() {
			{
				oneOf(vehicleUpdateOrdering).vehicleSequence(asList(vehicle0, vehicle1)); will(returnValue(asList(vehicle1, vehicle0)));
				oneOf(vehicle1).step(); inSequence(stepOrdering);
				oneOf(vehicle0).step(); inSequence(stepOrdering);
			}
		});
		vehicleManager.step();
	}

	@Test
	public void vehicleIsNoLongerSteppedWhenJourneyEndedMessageReceivedAboutIt() throws Exception {
		receiveJourneyStartedMessageFor(vehicle0);
		receiveJourneyStartedMessageFor(vehicle1);
		context.checking(new Expectations() {
			{
				oneOf(journeyEndedMessage).vehicle(); will(returnValue(vehicle0));
			}
		});
		vehicleManager.journeyEnded(journeyEndedMessage);

		context.checking(new Expectations() {
			{
				never(vehicle0).step();
				oneOf(vehicleUpdateOrdering).vehicleSequence(asList(vehicle1)); will(returnValue(asList(vehicle1)));
				oneOf(vehicle1).step();
			}
		});
		vehicleManager.step();
	}

	private void receiveJourneyStartedMessageFor(final Vehicle vehicle) {
		context.checking(new Expectations() {
			{
				oneOf(journeyStartedMessage).vehicle(); will(returnValue(vehicle));
			}
		});
		vehicleManager.journeyStarted(journeyStartedMessage);
	}
}
