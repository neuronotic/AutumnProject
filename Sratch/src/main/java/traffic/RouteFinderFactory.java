package traffic;


public interface RouteFinderFactory {

	RouteFinder createShortestPathRouteFinder(RoadNetwork roadNetwork);

}
