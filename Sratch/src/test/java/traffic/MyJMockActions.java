package traffic;

import static java.util.Arrays.*;

import org.hamcrest.Description;
import org.jmock.api.Action;
import org.jmock.api.Invocation;

public class MyJMockActions {
	//type variable - generics
	public static <T> Action returnList(final T...ts) {
		return new Action(){
			@Override
			public void describeTo(final Description description) {
				description.appendText("return list of ").appendValue(ts);
			}

			@Override
			public Object invoke(final Invocation arg0) throws Throwable {
				return asList(ts);
			}};
	}

}
