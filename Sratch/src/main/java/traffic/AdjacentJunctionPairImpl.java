package traffic;

public class AdjacentJunctionPairImpl implements AdjacentJunctionPair {
	private final Junction inJunction;
	private final Junction outJunction;

	public AdjacentJunctionPairImpl(
			final Junction inJunction,
			final Junction outJunction) {
				this.inJunction = inJunction;
				this.outJunction = outJunction;
	}

	@Override
	public Segment connectedByCellChain(final CellChain cellChain) {
		return new SegmentImpl(inJunction, cellChain, outJunction);
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
		return String.format("Adjacent Junctions (%s -> %s)", inJunction, outJunction);
	}
}
