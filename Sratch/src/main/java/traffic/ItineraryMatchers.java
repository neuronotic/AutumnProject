package traffic;

import static java.util.Arrays.*;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ItineraryMatchers {
	public static Matcher<Itinerary> itineraryRouteIs(final Link...expectedLinkss) {
		return new TypeSafeMatcher<Itinerary>(Itinerary.class) {
			@Override
			public void describeTo(final Description description) {
				description.appendText("itinerary follows route ").appendValue(expectedLinkss);
			}

			@Override
			protected boolean matchesSafely(final Itinerary item) {
				return item.route().equals(asList(expectedLinkss));
			}
		};
	}
}
