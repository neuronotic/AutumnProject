package traffic;

import com.google.inject.Inject;

public class FirstComeFirstServedBuilderImpl implements
		FirstComeFirstServedBuilder {

	private final FirstComeFirstServedFactory firstComeFactory;

	@Inject public FirstComeFirstServedBuilderImpl(
			final FirstComeFirstServedFactory firstComeFactory) {
				this.firstComeFactory = firstComeFactory;
	}

	@Override
	public JunctionController make() {
		return firstComeFactory.create();
	}

	@Override
	public String name() {
		return "firstComeFirstServed";
	}

}
