package traffic;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


public class RoadUserMatchers {
	public static Matcher<RoadUser> isLocatedAt(final Cell expectedLocation) {
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
}
