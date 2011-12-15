package traffic;

import java.util.List;

public interface Link {
	Junction inJunction();
	Junction outJunction();
	CellChain cellChain();
	List<Cell> cells();
	Cell headCell();

	String name();
	int length();
	Cell getCell(int index);

	int cellCount();
	int occupiedCount();

	LinkOccupancy occupancy();
	double congestion();
	String linkOccupantsAsString();
}
