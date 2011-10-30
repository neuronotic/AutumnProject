package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;


public class JunctionImpl implements Junction {
	private final String name;

	@Inject
	public JunctionImpl(@Assisted final String name) {
		this.name = name;
	}

	@Override
	public void enter(final Vehicle vehicle) {

	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("Junction %s", name);
	}
}
