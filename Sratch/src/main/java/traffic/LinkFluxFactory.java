package traffic;

public interface LinkFluxFactory {

	LinkFlux create(Link link, CellFlux cellFlux);

}
