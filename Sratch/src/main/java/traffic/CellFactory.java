package traffic;

public interface CellFactory {
	Cell createCell(Segment segment, int index);
}
