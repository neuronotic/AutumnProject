package traffic;

import static traffic.RoadNetworkToStringStyle.*;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class SegmentImpl implements Segment {
	private final String name;
	private final Junction inJunction;
	private final CellChain cellChain;
	private final Junction outJunction;

	@Inject SegmentImpl(
			@Assisted final String name,
			@Assisted("inJunction") final Junction inJunction,
			@Assisted final CellChainBuilder cellChainBuilder,
			@Assisted("outJunction") final Junction outJunction) {
		this.name = name;
		this.inJunction = inJunction;
		cellChain = cellChainBuilder.make(this);
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
	public int length() {
		return cellChain.cellCount();
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
