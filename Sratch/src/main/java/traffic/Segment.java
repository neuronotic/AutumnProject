package traffic;

import java.util.List;

public interface Segment {
	Junction inJunction();

	Junction outJunction();

	CellChain cellChain();

	List<Cell> cells();

	String name();

	int length();

	Cell getCell(int index);
}
