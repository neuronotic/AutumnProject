package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;


class JunctionImpl implements Junction {
	private final String name;

	@Inject
	JunctionImpl(@Assisted final String name) {
		this.name = name;
	}

	@Override
	public boolean enter(final Vehicle vehicle) {
		return false;
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
