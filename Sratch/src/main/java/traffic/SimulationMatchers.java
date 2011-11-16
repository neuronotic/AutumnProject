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

	public static Matcher<Simulation> hasJourneyHistoryCount(final int expectedCount) {
		return new TypeSafeMatcher<Simulation>(Simulation.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText("number of journey histories is ").appendValue(expectedCount);
			}
			@Override
			protected void describeMismatchSafely(final Simulation item,
					final Description mismatchDescription) {
				if (item != null) {
					mismatchDescription.appendText("number of journey histories is ").appendValue(item.statistics().getEndedJourneyHistories().size());
				} else {
					super.describeMismatchSafely(item, mismatchDescription);
				}
			}
			@Override
			protected boolean matchesSafely(final Simulation item) {
				return item.statistics().getEndedJourneyHistories().size() ==  expectedCount;
			}
		};
	}

	public static Matcher<Simulation> hasJourneyHistoryOriginatingAtJunctionCount(final Junction originatingJunction, final int expectedCount) {
		return new TypeSafeMatcher<Simulation>(Simulation.class) {
			@Override
			public void describeTo(final Description description) {
				description
					.appendText("number of journey histories beginning at ")
					.appendValue(originatingJunction)
					.appendText("is ")
					.appendValue(expectedCount);
			}
			@Override
			protected void describeMismatchSafely(final Simulation item,
					final Description mismatchDescription) {
				if (item != null) {
					int count = 0;
					for (final JourneyHistory journeyHistory : item.statistics().getEndedJourneyHistories()) {
						if (originatingJunction.equals(journeyHistory.vehicle().flow().itinerary().originJunction())) {
							count ++;
						}
					}
					mismatchDescription
						.appendText("number of journey histories beginning at ")
						.appendValue(originatingJunction)
						.appendText("is ")
						.appendValue(count);

				} else {
					super.describeMismatchSafely(item, mismatchDescription);
				}
			}

			@Override
			protected boolean matchesSafely(final Simulation item) {
				int count = 0;
				for (final JourneyHistory journeyHistory : item.statistics().getEndedJourneyHistories()) {
					if (originatingJunction.equals(journeyHistory.vehicle().flow().itinerary().originJunction())) {
						count ++;
					}
				}
				return count == expectedCount;
			}
		};
	}

}
