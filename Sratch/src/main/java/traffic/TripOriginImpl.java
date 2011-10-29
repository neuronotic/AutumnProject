package traffic;

public class TripOriginImpl implements TripOrigin {
	private final Junction originJunction;

	public TripOriginImpl(final Junction originJunction) {
		this.originJunction = originJunction;
	}

	@Override
	public Trip to(final Junction destinationJunction) {
		return null;
	}

	@Override
	public Junction origin() {
		return originJunction;
	}

	@Override
	public String toString() {
		return String.format("trip origin at %s", originJunction);
	}
}
