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

	public static Matcher<Vehicle> isLocatedAt(final Segment expectedSegment, final int expectedIndex) {
		return new TypeSafeMatcher<Vehicle>(Vehicle.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendValue(Vehicle.class.getSimpleName())
					.appendText(" is located at ")
					.appendValue(expectedSegment.getCell(expectedIndex));
			}

			@Override
			protected boolean matchesSafely(final Vehicle item) {
				return item.location().equals(expectedSegment.getCell(expectedIndex));
			}
		};
	}


	public static Matcher<Vehicle> hasJourneyTime(final int expectedJourneyTime) {
		return new TypeSafeMatcher<Vehicle>(Vehicle.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText("Vehicle with journey time ").appendValue(expectedJourneyTime);
			}

			@Override
			protected boolean matchesSafely(final Vehicle item) {
				return item.journeyTime() == expectedJourneyTime;
			}
		};
	}

	public static Matcher<Vehicle> vehicleIsNamed(final String expectedName) {
		return new TypeSafeMatcher<Vehicle>(Vehicle.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText("Vehicle with name ").appendValue(expectedName);
			}

			@Override
			protected boolean matchesSafely(final Vehicle item) {
				return item.name().equals(expectedName);
			}
		};
	}
}
