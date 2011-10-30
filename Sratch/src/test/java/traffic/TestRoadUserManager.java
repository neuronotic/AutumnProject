package traffic;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;


public class TestRoadUserManager {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final RoadUser roadUser = context.mock(RoadUser.class);
	private final RoadUserManagerImpl roadUserManagerImpl = new RoadUserManagerImpl();

	@Test
	public void addedRoadUserIsSteppedByManager() throws Exception {
		roadUserManagerImpl.addRoadUser(roadUser);

		context.checking(new Expectations() {{
			oneOf(roadUser).step();
		}});
		roadUserManagerImpl.step();
	}

	@Test
	public void managerCanBeSteppedMultipleTimes() throws Exception {
		roadUserManagerImpl.addRoadUser(roadUser);

		context.checking(new Expectations() {{
			oneOf(roadUser).step();
			oneOf(roadUser).step();
			oneOf(roadUser).step();
		}});
		roadUserManagerImpl.step(3);
	}
}
