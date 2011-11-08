package traffic;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class JourneyHistoryMatchers {
	public static Matcher<JourneyStepper> journeyTime(final int expectedJourneyTime) {
		return new TypeSafeMatcher<JourneyStepper>(JourneyHistory.class){
			@Override
			public void describeTo(final Description description) {
				description.appendText("journey history with journey time ").appendValue(expectedJourneyTime);
			}

			@Override
			protected boolean matchesSafely(final JourneyStepper item) {
				return item.journeyTime() == expectedJourneyTime;
			}};
	}
}
