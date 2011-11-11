package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;


public class TestVehicleManagerImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Vehicle vehicle0 = context.mock(Vehicle.class, "vehicle0");
	private final Vehicle vehicle1 = context.mock(Vehicle.class, "vehicle1");
	private final JourneyEndedMessage journeyEndedMessage = context.mock(JourneyEndedMessage.class);
	private final JourneyHistory journeyHistory = context.mock(JourneyHistory.class);
	private final JourneyStartedMessage journeyStartedMessage = context.mock(JourneyStartedMessage.class);
	private final VehicleManager vehicleManager = new VehicleManagerImpl();
	//TODO: ought i to test if the @Subscribe annotations are in place for the message receiving classes?


	@Test
	public void vehiclesSuppliedByMultipleJourneyStartedMessagesAreStepped() throws Exception {
		setExpectationsForAndReceiveJourneyStartedNotificationAboutVehicle0();
		setExpectationsForAndReceiveJourneyStartedNotificationAboutVehicle1();
		setExpectationForStepForVehicle(vehicle0);
		setExpectationForStepForVehicle(vehicle1);
		vehicleManager.step();
	}

	@Test
	public void managerCanBeSteppedMultipleTimes() throws Exception {
		setExpectationsForAndReceiveJourneyStartedNotificationAboutVehicle0();
		setExpectationForStepForVehicle(vehicle0);
		setExpectationForStepForVehicle(vehicle0);
		vehicleManager.step(2);
	}

	@Test
	public void journeyEndedStoresJourneyHistory() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(journeyEndedMessage).journeyHistory(); will(returnValue(journeyHistory));
				oneOf(journeyEndedMessage).vehicle();
			}
		});
		vehicleManager.journeyEnded(journeyEndedMessage);
		assertThat(vehicleManager.getEndedJourneyHistories(), contains(journeyHistory));
	}

	@Test
	public void journeyEndedResultsInVehicleNoLongerBeingStepped() throws Exception {
		setExpectationsForAndReceiveJourneyStartedNotificationAboutVehicle0();
		context.checking(new Expectations() {
			{
				oneOf(journeyEndedMessage).journeyHistory();
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

	private void setExpectationsForAndReceiveJourneyStartedNotificationAboutVehicle1() {
		context.checking(new Expectations() {
			{
				oneOf(journeyStartedMessage).vehicle(); will(returnValue(vehicle1));
			}
		});
		vehicleManager.journeyStarted(journeyStartedMessage);
	}

	private void setExpectationsForAndReceiveJourneyStartedNotificationAboutVehicle0() {
		context.checking(new Expectations() {
			{
				oneOf(journeyStartedMessage).vehicle(); will(returnValue(vehicle0));
			}
		});
		vehicleManager.journeyStarted(journeyStartedMessage);
	}

	private void setExpectationForStepForVehicle(final Vehicle vehicle) {
		context.checking(new Expectations() {
			{
				oneOf(vehicle).step();
			}
		});
	}

}
