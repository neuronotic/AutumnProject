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

	public static Matcher<Vehicle> isLocatedAt(final Link expectedLink, final int expectedIndex) {
		return new TypeSafeMatcher<Vehicle>(Vehicle.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendValue(Vehicle.class.getSimpleName())
					.appendText(" is located at ")
					.appendValue(expectedLink.getCell(expectedIndex));
			}

			@Override
			protected boolean matchesSafely(final Vehicle item) {
				return item.location().equals(expectedLink.getCell(expectedIndex));
			}
		};
	}


	public static Matcher<Vehicle> hasJourneyTime(final SimulationTime expectedJourneyTime) {
		return new TypeSafeMatcher<Vehicle>(Vehicle.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText("Vehicle with journey time ").appendValue(expectedJourneyTime);
			}

			@Override
			protected void describeMismatchSafely(final Vehicle item, final Description description) {
				description.appendText("Vehicle with journey time ").appendValue(item.journeyTime());
			}

			@Override
			protected boolean matchesSafely(final Vehicle item) {
				return item.journeyTime().equals(expectedJourneyTime);
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
