package traffic;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


public class NetworkMatchers {
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

	public static Matcher<Link> isLink(
			final String expectedName,
			final Junction expectedInJunction,
			final CellChain cellChain,
			final Junction expectedOutJunction) {
		return new TypeSafeMatcher<Link>() {
			@Override
			public void describeTo(final Description description) {
				description.appendText("Link ").appendValue(expectedName).appendText(" between ").appendValue(expectedInJunction).appendText(" and ").appendValue(expectedOutJunction).appendText(" connected by cell chain ").appendValue(cellChain);
			}

			@Override
			protected boolean matchesSafely(final Link item) {
				return item.inJunction().equals(expectedInJunction) && item.outJunction().equals(expectedOutJunction) && item.name().equals(expectedName);
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

	public static Matcher<Link> linkHasCellChain(
			final CellChain expectedCellChain) {
		return new TypeSafeMatcher<Link>() {
			@Override
			public void describeTo(final Description description) {
				description.appendText("Link Cell Chain ").appendValue(expectedCellChain);
			}

			@Override
			protected boolean matchesSafely(final Link item) {
				return item.cellChain().equals(expectedCellChain);
			}
		};
	}

	public static Matcher<Link> linkNamed(final String expectedName) {
		return new TypeSafeMatcher<Link>() {

			@Override
			public void describeTo(final Description description) {
				description.appendText("Link with name ").appendValue(expectedName);
			}

			@Override
			protected boolean matchesSafely(final Link item) {
				return item.name().equals(expectedName);
			}
		};
	}

	public static Matcher<Cell> cellNamed(final String expectedName) {
		return new TypeSafeMatcher<Cell>(Cell.class) {

			@Override
			public void describeTo(final Description description) {
				description.appendText("Cell named ").appendValue(expectedName);
			}

			@Override
			protected void describeMismatchSafely(final Cell item,
					final Description mismatchDescription) {
				if (item != null) {
					mismatchDescription.appendText("Cell named ").appendValue(item.name());
				} else {
					super.describeMismatchSafely(item, mismatchDescription);
				}
			}

			@Override
			protected boolean matchesSafely(final Cell item) {
				return item.name().equals(expectedName);
			}

		};
	}

	public static Matcher<Link> linkHasLength(final int expectedLength) {
		return new TypeSafeMatcher<Link>(Link.class) {

			@Override
			public void describeTo(final Description description) {
				description.appendText("Link with length ").appendValue(expectedLength);
			}

			@Override
			protected void describeMismatchSafely(final Link item,
					final Description mismatchDescription) {
				if (item != null) {
					mismatchDescription.appendText("Link with length ").appendValue(item.length());
				} else {
					super.describeMismatchSafely(item, mismatchDescription);
				}
			}

			@Override
			protected boolean matchesSafely(final Link item) {
				return item.length() == expectedLength;
			}

		};
	}

	public static Matcher<Junction> junctionNamed(final String expectedName) {
		return new TypeSafeMatcher<Junction>() {

			@Override
			public void describeTo(final Description description) {
				description.appendText("Junction with name ").appendValue(expectedName);
			}
			@Override
			protected boolean matchesSafely(final Junction item) {
				return item.name().equals(expectedName);
			}
		};
	}
}
