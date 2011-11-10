package traffic;

import com.google.inject.Inject;

public class NullCell implements Cell {

	@Inject NullCell() {}

	@Override
	public boolean enter(final Vehicle vehicle) {
		return false;
	}

	@Override
	public String name() {
		return null;
	}

	@Override
	public void leave() {
	}

	@Override
	public boolean isOccupied() {
		return false;
	}

}
