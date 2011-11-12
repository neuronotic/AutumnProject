package traffic;


public interface RouteFinderFactory {

	RouteFinder createShortestPathRouteFinder(Network network);

}
