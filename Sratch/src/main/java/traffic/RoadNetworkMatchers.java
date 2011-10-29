package traffic;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import traffic.endtoend.RoadUser;

public class RoadNetworkMatchers {
	public static Matcher<RoadUser> isLocatedAt(final Junction expectedLocation) {
		return new TypeSafeMatcher<RoadUser>(RoadUser.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText("road user is located at junction ").appendValue(expectedLocation);
			}

			@Override
			protected boolean matchesSafely(final RoadUser item) {
				return item.location().equals(expectedLocation);
			}
		};
	}

	public static Matcher<RoadUser> hasJourneyTime(final int expectedJourneyTime) {
		return null;
	}

	public static Matcher<AdjacentJunctionPair> isAdjacentPairOfJunctions(
			final Junction expectedJunction0, final Junction expectedJunction1) {
		return new TypeSafeMatcher<AdjacentJunctionPair>(
				AdjacentJunctionPair.class) {
			@Override
			public void describeTo(final Description description) {
				description
					.appendText("adjacent pair of junctions (")
					.appendValue(expectedJunction0)
					.appendText(", ")
					.appendValue(expectedJunction1)
					.appendText(")");
			}

			@Override
			protected boolean matchesSafely(final AdjacentJunctionPair item) {
				return item.inJunction().equals(expectedJunction0) && item.outJunction().equals(expectedJunction1);
			}
		};
	}

	public static Matcher<TripOrigin> isTripOriginAt(final Junction expectedInJunction) {
		return new TypeSafeMatcher<TripOrigin>(TripOrigin.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText("trip origin at ").appendValue(expectedInJunction);
			}

			@Override
			protected boolean matchesSafely(final TripOrigin item) {
				return item.origin().equals(expectedInJunction);
			}
		};
	}
}
