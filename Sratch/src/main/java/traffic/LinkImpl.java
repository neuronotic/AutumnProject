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
		return cellChain.occupancy();
	}

	@Override
	public LinkOccupancy occupancy() {
		final Occupancy occupancy = occupancyFactory.create(occupiedCount(), cellCount());
		return linkOccupancyFactory.create(this, occupancy);
	}

	@Override
	public Cell headCell() {
		return cellChain.lastCell();
	}

	@Override
	public double congestion() {
		return occupiedCount() / (double) cellCount();
	}

	@Override
	public String linkOccupantsAsString() {
		final StringBuffer stringBuffer = new StringBuffer();
		for (final Cell cell : cells()) {
			if (cell.occupant()!= null){
				stringBuffer.append(vehicleNameToNumber(cell.occupant().name()));
			} else {
				stringBuffer.append(".. ");
			}
		}

		return stringBuffer.toString();
	}

	private Object vehicleNameToNumber(final String name) {
		return String.format("%02d ",Integer.valueOf(name.substring(7)));
	}
}
