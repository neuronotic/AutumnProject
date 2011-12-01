package traffic;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

public class TestJunctionBuilderImpl {
	@Rule
	public final JUnitRuleMockery context = new JUnitRuleMockery();

	private final JunctionController junctionController = context.mock(JunctionController.class);
	private final JunctionController defaultJunctionController = context.mock(JunctionController.class, "defaultJunctionController");
	private final JunctionControllerBuilder controllerBuilder = context.mock(JunctionControllerBuilder.class);
	private final JunctionFactory junctionFactory = context.mock(JunctionFactory.class);
	private final Junction junction = context.mock(Junction.class);
	private final String junctionName = "myJunction";

	private final JunctionBuilder junctionBuilder = new JunctionBuilderImpl(junctionFactory, defaultJunctionController);

	@Test
	public void junctionCreatedWithDefaultJunctionControllerIfControllerBuilderNotSpecified() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(junctionFactory).createJunction(junctionName, defaultJunctionController); will(returnValue(junction));
			}
		});
		junctionBuilder.withName(junctionName);
		assertThat(junctionBuilder.make(), is(junction));
	}

	@Test
	public void junctionCreatedWithSuppliedDependencies() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(controllerBuilder).make(); will(returnValue(junctionController));
				oneOf(junctionFactory).createJunction(junctionName, junctionController); will(returnValue(junction));
			}
		});
		assertThat(junctionBuilder.withName(junctionName).withControllerBuilder(controllerBuilder).make(), is(junction));
	}
}
