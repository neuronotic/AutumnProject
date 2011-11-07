package traffic;

public class NullCell implements Cell {

	@Override
	public boolean enter(final Vehicle vehicle) {
		return false;
	}

	@Override
	public String name() {
		return null;
	}

	@Override
	public void leave(final Vehicle vehicle) {
	}

	@Override
	public boolean isOccupied() {
		return false;
	}

}
