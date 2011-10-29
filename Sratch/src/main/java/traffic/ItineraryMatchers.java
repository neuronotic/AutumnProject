package traffic;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ItineraryMatchers {
	public static Matcher<Itinerary> itineraryRouteIs(final Segment segment) {
		return new TypeSafeMatcher<Itinerary>(Itinerary.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText("itinerary follows route ").appendValue(segment);
			}

			@Override
			protected boolean matchesSafely(final Itinerary item) {
				return item.route().equals(segment);
			}
		};
	}
}
