package traffic;

public class SegmentImpl implements Segment {
	private final Junction inJunction;
	private final Junction outJunction;

	public SegmentImpl(final Junction inJunction, final Junction outJunction) {
		this.inJunction = inJunction;
		this.outJunction = outJunction;
	}

	@Override
	public Junction inJunction() {
		return inJunction;
	}

	@Override
	public Junction outJunction() {
		return outJunction;
	}

	@Override
	public String toString() {
		return String.format("Segment from %s to %s", inJunction, outJunction);
	}
}
