package traffic;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;


public class TestVehicleManagerImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Vehicle vehicle0 = context.mock(Vehicle.class, "vehicle0");
	private final Vehicle vehicle1 = context.mock(Vehicle.class, "vehicle1");
	private final JourneyEndedMessage journeyEndedMessage = context.mock(JourneyEndedMessage.class);
	private final JourneyStartedMessage journeyStartedMessage = context.mock(JourneyStartedMessage.class);
	private final VehicleManager vehicleManager = new VehicleManagerImpl();
	//TODO: ought i to test if the @Subscribe annotations are in place for the message receiving classes?


	@Test
	public void stepDelegatesCallToVehiclesForWhichJourneyStartedMessagesReceived() throws Exception {
		receiveJourneyStartedMessageFor(vehicle0);
		receiveJourneyStartedMessageFor(vehicle1);
		defineExpectationsForSteping(vehicle0);
		defineExpectationsForSteping(vehicle1);
		vehicleManager.step();
	}

	@Test
	public void managerCanBeSteppedMultipleTimes() throws Exception {
		receiveJourneyStartedMessageFor(vehicle0);
		defineExpectationsForSteping(vehicle0);
		defineExpectationsForSteping(vehicle0);
		vehicleManager.step(2);
	}

	@Test
	public void vehicleIsNoLongerSteppedWhenJourneyEndedMessageReceivedAboutIt() throws Exception {
		receiveJourneyStartedMessageFor(vehicle0);
		context.checking(new Expectations() {
			{
				oneOf(journeyEndedMessage).vehicle(); will(returnValue(vehicle0));
			}
		});
		vehicleManager.journeyEnded(journeyEndedMessage);

		context.checking(new Expectations() {
			{
				never(vehicle0).step();
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

	private void defineExpectationsForSteping(final Vehicle vehicle) {
		context.checking(new Expectations() {
			{
				oneOf(vehicle).step();
			}
		});
	}

}
