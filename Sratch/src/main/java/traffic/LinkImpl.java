package traffic;

import static traffic.TrafficToStringStyle.*;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class LinkImpl implements Link {
	private final String name;
	private final Junction inJunction;
	private final CellChain cellChain;
	private final Junction outJunction;
	private final OccupancyFactory occupancyFactory;
	private final LinkOccupancyFactory linkOccupancyFactory;

	@Inject LinkImpl(
			final LinkOccupancyFactory linkOccupancyFactory,
			final OccupancyFactory occupancyFactory,
			@Assisted final String name,
			@Assisted("inJunction") final Junction inJunction,
			@Assisted final CellChainBuilder cellChainBuilder,
			@Assisted("outJunction") final Junction outJunction) {
		this.linkOccupancyFactory = linkOccupancyFactory;
		this.occupancyFactory = occupancyFactory;
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
		return networkReflectionToString(this);
	}

	@Override
	public Cell getCell(final int index) {
		return cellChain.getCellAtIndex(index);
	}

	@Override
	public int cellCount() {
		return cellChain.cellCount();
	}

	@Override
	public int occupiedCount() {
		int occupiedCount = 0;
		for (final Cell cell : cellChain) {
			occupiedCount += cell.isOccupied() ? 1 : 0;
		}
		return occupiedCount;
	}

	@Override
	public LinkOccupancy occupancy() {
		final Occupancy occupancy = occupancyFactory.create(occupiedCount(), cellCount());
		return linkOccupancyFactory.create(this, occupancy);
	}

	@Override
	public Cell headCell() {
		// TODO Auto-generated method stub
		return null;

	}
}
