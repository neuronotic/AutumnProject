package traffic;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class JourneyHistoryMatchers {
	public static Matcher<JourneyHistory> journeyTime(final int expectedJourneyTime) {
		return new TypeSafeMatcher<JourneyHistory>(JourneyHistory.class){
			@Override
			public void describeTo(final Description description) {
				description.appendText("journey history with journey time ").appendValue(expectedJourneyTime);
			}

			@Override
			protected boolean matchesSafely(final JourneyHistory item) {
				return item.journeyTime() == expectedJourneyTime;
			}};
	}
}
