package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static traffic.NetworkMatchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestCellImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final Vehicle vehicle0 = context.mock(Vehicle.class, "vehicle0");
	private final Vehicle vehicle1 = context.mock(Vehicle.class, "vehicle1");

	private final CellOccupantDepartedMessage message = context.mock(CellOccupantDepartedMessage.class);
	private final CellOccupantDepartedMessageFactory messageFactory = context.mock(CellOccupantDepartedMessageFactory.class);

	private final MyEventBus eventBus = context.mock(MyEventBus.class);
	private final Link link = context.mock(Link.class);

	private final Cell cell = cell();

	@Test
	public void messagePostedOnBusWhenOccupantLeavesCell() throws Exception {
		cell.enter(vehicle0);
		context.checking(new Expectations() {
			{
				oneOf(messageFactory).create(cell); will(returnValue(message));
				oneOf(eventBus).post(message);
			}
		});
		cell.leave();
	}

	@Test
	public void cellCanOnlyHaveOneOccupantAtATime() throws Exception {
		assertThat(cell.enter(vehicle0), is(true));
		assertThat(cell.enter(vehicle1), is(false));
	}

	@Test
	public void isOccupiedReturnsTrueOnlyIfVehicleIsInCell() throws Exception {
		assertThat(cell.isOccupied(), is(false));
		cell.enter(vehicle0);
		assertThat(cell.isOccupied(), is(true));
	}

	@Test
	public void leaveUnoccupiesCell() throws Exception {
		cell.enter(vehicle0);
		assertThat(cell.isOccupied(), is(true));
		context.checking(new Expectations() {
			{
				ignoring(messageFactory);
				ignoring(eventBus);
			}
		});
		cell.leave();
		assertThat(cell.isOccupied(), is(false));
	}

	@Test
	public void cellNameIsNameOfLinkAndCellIndex() throws Exception {
		assertThat(cell, cellNamed("myLink[0]"));
	}

	private Cell cell() {
		context.checking(new Expectations() {
			{
				oneOf(link).name(); will(returnValue("myLink"));
			}
		});
		return new CellImpl(link, 0, eventBus, messageFactory);
	}
}
