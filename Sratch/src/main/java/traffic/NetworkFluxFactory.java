package traffic;

import java.util.Set;

public interface NetworkFluxFactory {

	NetworkFlux create(Set<LinkFlux> linkFluxes);

}
