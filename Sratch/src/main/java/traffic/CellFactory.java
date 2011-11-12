package traffic;

public interface CellFactory {
	Cell createCell(Link link, int index);
}
