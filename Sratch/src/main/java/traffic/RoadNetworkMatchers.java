package traffic;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


public class RoadNetworkMatchers {
	public static Matcher<RoadUser> hasJourneyTime(final int expectedJourneyTime) {
		return new TypeSafeMatcher<RoadUser>(RoadUser.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText("Road User with journey time ").appendValue(expectedJourneyTime);
			}

			@Override
			protected boolean matchesSafely(final RoadUser item) {
				return item.journeyTime() == expectedJourneyTime;
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

	public static Matcher<Trip> isTripBetween(
			final Junction expectedOrigin,
			final Junction expectedDestination) {
		return new TypeSafeMatcher<Trip>(Trip.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText("trip between ").appendValue(expectedOrigin).appendText(" and ").appendValue(expectedDestination);
			}

			@Override
			protected boolean matchesSafely(final Trip item) {
				return item.origin().equals(expectedOrigin) && item.destination().equals(expectedDestination);
			}
		};
	}

	public static Matcher<Segment> isSegment(
			final Junction expectedInJunction,
			final CellChain cellChain,
			final Junction expectedOutJunction) {
		return new TypeSafeMatcher<Segment>() {
			@Override
			public void describeTo(final Description description) {
				description.appendText("segment between ").appendValue(expectedInJunction).appendText(" and ").appendValue(expectedOutJunction).appendText(" connected by cell chain ").appendValue(cellChain);
			}

			@Override
			protected boolean matchesSafely(final Segment item) {
				return item.inJunction().equals(expectedInJunction) && item.outJunction().equals(expectedOutJunction);
			}
		};
	}

	public static Matcher<Junction> isJunctionCalled(final String name) {
		return new TypeSafeMatcher<Junction>(Junction.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText("junction with name ").appendValue(name);
			}

			@Override
			protected boolean matchesSafely(final Junction item) {
				return item.name().equals(name);
			}
		};
	}

	public static Matcher<CellChain> isCellChainWithCellCount(final int expectedCount) {
		return new TypeSafeMatcher<CellChain>(CellChain.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText("cell chain of length ").appendValue(expectedCount);
			}

			@Override
			protected boolean matchesSafely(final CellChain item) {
				return item.cellCount() == expectedCount;
			}
		};
	}

	public static Matcher<Segment> segmentHasCellChain(
			final CellChain expectedCellChain) {
		return new TypeSafeMatcher<Segment>() {
			@Override
			public void describeTo(final Description description) {
				description.appendText("Segment Cell Chain ").appendValue(expectedCellChain);
			}

			@Override
			protected boolean matchesSafely(final Segment item) {
				return item.cellChain().equals(expectedCellChain);
			}
		};
	}
}
