package traffic;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


public class SimulationMatchers {

	public static Matcher<SimulationTime> isTime(final SimulationTime expectedTime) {
		return new TypeSafeMatcher<SimulationTime>(SimulationTime.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText("time ").appendValue(expectedTime);
			}

			@Override
			protected boolean matchesSafely(final SimulationTime item) {
				return item.equals(expectedTime);
			}


		};
	}
}
