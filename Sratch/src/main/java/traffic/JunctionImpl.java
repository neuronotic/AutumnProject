package traffic;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;


class JunctionImpl implements Junction {
	private final String name;
	private boolean occupied = false;

	@Inject
	JunctionImpl(@Assisted final String name) {
		this.name = name;
	}

	@Override
	public boolean enter(final Vehicle vehicle) {
		if (occupied) {
			return false;
		}
		occupied = true;
		return true;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("Junction %s", name);
	}

	@Override
	public boolean isOccupied() {
		return occupied;
	}

	@Override
	public void leave(final Vehicle vehicle) {
		occupied = false;
	}
}
