package traffic;


public class JunctionImpl implements Junction {
	private final String name;

	public JunctionImpl(final String name) {
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
