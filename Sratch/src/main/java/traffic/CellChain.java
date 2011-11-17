package traffic;

public interface CellChain extends Iterable<Cell> {
	int cellCount();
	int occupancy();

	Cell getCellAtIndex(int index);

	Cell lastCell();

}
