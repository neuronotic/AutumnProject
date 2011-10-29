package traffic;

import traffic.endtoend.RoadUser;

public class RoadUserManagerImpl implements RoadUserManager {
	private final RoadUserFactory roadUserFactory;

	public RoadUserManagerImpl(final RoadUserFactory roadUserFactory) {
		this.roadUserFactory = roadUserFactory;
	}

	@Override
	public RoadUser roadUser(final Itinerary itinerary) {
		return roadUserFactory.createRoadUser(itinerary);
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
	}
}
