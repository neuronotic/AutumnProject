package snippet;

public interface AdjacentJunctionPair {
	Segment connectedByCellChain(CellChain segmentOfLength);

	Junction inJunction();
	Junction outJunction();
}
