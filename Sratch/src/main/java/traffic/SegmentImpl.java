package traffic;

import static traffic.RoadNetworkToStringStyle.*;

import java.util.ArrayList;
import java.util.List;

public class SegmentImpl implements Segment {
	private final String name;
	private final Junction inJunction;
	private final CellChain cellChain;
	private final Junction outJunction;

	public SegmentImpl(final String name, final Junction inJunction, final CellChainFactory cellChainFactory, final Junction outJunction) {
		this.name = name;
		this.inJunction = inJunction;
		cellChain = cellChainFactory.make(this);
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
	public List<Cell> cells() {
		final ArrayList<Cell> result = new ArrayList<Cell>();

		result.add(inJunction);
		for (final Cell cell : cellChain) {
			result.add(cell);
		}
		result.add(outJunction);
		return result;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String toString() {
		return roadNetworkReflectionToString(this);
	}
}
