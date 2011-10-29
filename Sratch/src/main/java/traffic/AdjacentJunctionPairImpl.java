package traffic;

public class AdjacentJunctionPairImpl implements AdjacentJunctionPair {
	private final Junction inJunction;
	private final Junction outJunction;

	public AdjacentJunctionPairImpl(
			final Junction junction0,
			final Junction junction1) {
				inJunction = junction0;
				outJunction = junction1;
	}

	@Override
	public Segment connectedByCellChain(final CellChain segmentOfLength) {
		return new SegmentImpl(inJunction, outJunction);
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
