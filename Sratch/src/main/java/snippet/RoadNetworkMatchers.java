package snippet;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RoadNetworkMatchers {
	public static Matcher<Trip> hasJourneyTimeEqualTo(final int i) {
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
}
