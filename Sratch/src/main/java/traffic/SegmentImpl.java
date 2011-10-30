package traffic;

import java.util.ArrayList;
import java.util.List;

public class SegmentImpl implements Segment {
	private final Junction inJunction;
	private final Junction outJunction;
	private final CellChain cellChain;

	public SegmentImpl(final Junction inJunction, final CellChain cellChain, final Junction outJunction) {
		this.inJunction = inJunction;
		this.cellChain = cellChain;
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
	public CellChain cellChain() {
		return cellChain;
	}

	@Override
	public String toString() {
		return String.format("Segment from %s to %s", inJunction, outJunction);
	}

	@Override
	public List<Cell> cells() {
		final ArrayList<Cell> result = new ArrayList<Cell>();

		result.add(inJunction);
		for (final Cell cell : cellChain) {
			result.add(cell);
		}
		result.add(outJunction);
		return result;
	}
}
