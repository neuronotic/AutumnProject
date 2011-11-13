package traffic.util;

import static java.util.Arrays.*;

import java.util.HashSet;
import java.util.Set;

import traffic.JunctionOccupancy;
import traffic.LinkFlux;
import traffic.LinkOccupancy;

public class MyCollectionsProcessing {
	public static Set<LinkOccupancy> asSet(final LinkOccupancy...occupancies) {
		return new HashSet<LinkOccupancy>(asList(occupancies));
	}

	public static Set<JunctionOccupancy> asSet(final JunctionOccupancy...occupancies) {
		return new HashSet<JunctionOccupancy>(asList(occupancies));
	}

	public static Set<LinkFlux> asSet(final LinkFlux...linkFluxes) {
		return new HashSet<LinkFlux>(asList(linkFluxes));
	}
}
