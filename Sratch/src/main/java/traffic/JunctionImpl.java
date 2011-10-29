package traffic;

import traffic.endtoend.RoadUser;

public class JunctionImpl implements Junction {
	private final String name;

	public JunctionImpl(final String name) {
		this.name = name;
	}

	@Override
	public void enter(final RoadUser roadUser) {

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
