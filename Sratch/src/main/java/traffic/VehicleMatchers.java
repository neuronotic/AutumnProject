package traffic;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


public class VehicleMatchers {
	public static Matcher<Vehicle> isLocatedAt(final Cell expectedLocation) {
		return new TypeSafeMatcher<Vehicle>(Vehicle.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendValue(Vehicle.class.getSimpleName()).appendText(" is located at junction ").appendValue(expectedLocation);
			}

			@Override
			protected boolean matchesSafely(final Vehicle item) {
				return item.location().equals(expectedLocation);
			}
		};
	}

	public static Matcher<Vehicle> hasJourneyTime(final int expectedJourneyTime) {
		return new TypeSafeMatcher<Vehicle>(Vehicle.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText("Road User with journey time ").appendValue(expectedJourneyTime);
			}

			@Override
			protected boolean matchesSafely(final Vehicle item) {
				return item.journeyTime() == expectedJourneyTime;
			}
		};
	}

}
