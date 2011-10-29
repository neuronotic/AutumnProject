package traffic;

public class TripImpl implements Trip {
	private final Junction originJunction;
	private final Junction destinationJunction;

	public TripImpl(final Junction originJunction, final Junction destinationJunction) {
		this.originJunction = originJunction;
		this.destinationJunction = destinationJunction;
	}

	@Override
	public Junction origin() {
		return originJunction;
	}

	@Override
	public Junction destination() {
		return destinationJunction;
	}

	@Override
	public String toString() {
		return String.format("trip between %s and %s", originJunction, destinationJunction);
	}
}
