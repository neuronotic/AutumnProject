package traffic;


public class RoadUserManagerImpl implements RoadUserManager {
	private final RoadUserFactory roadUserFactory;
	private RoadUser roadUser;

	public RoadUserManagerImpl(final RoadUserFactory roadUserFactory) {
		this.roadUserFactory = roadUserFactory;
	}

	@Override
	public RoadUser roadUser(final Itinerary itinerary) {
		roadUser = roadUserFactory.createRoadUser(itinerary);
		return roadUser;
	}

	@Override
	public void step() {
		roadUser.step();
	}
}
