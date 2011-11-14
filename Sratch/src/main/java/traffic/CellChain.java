package traffic;

public interface CellChain extends Iterable<Cell> {
	int cellCount();

	Cell getCellAtIndex(int index);

	Cell lastCell();
}
