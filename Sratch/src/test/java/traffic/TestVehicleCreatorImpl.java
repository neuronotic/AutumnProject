package traffic;

import static java.util.Arrays.*;

import org.junit.Rule;
import org.junit.Test;

public class TestVehicleCreatorImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final FlowGroup flowGroup0 = context.mock(FlowGroup.class, "flowGroup0");
	private final FlowGroup flowGroup1 = context.mock(FlowGroup.class, "flowGroup1");

	private final VehicleCreator vehicleCreator = new VehicleCreatorImpl(asList(flowGroup0, flowGroup1));

	@Test
	public void testName() throws Exception {

	}
}
